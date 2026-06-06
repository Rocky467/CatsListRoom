package com.google.assignment.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.google.assignment.db.AppDB
import com.google.assignment.db.Cats
import com.google.assignment.db.RemoteKey
import javax.inject.Inject

@ExperimentalPagingApi
class RemoteDataMediator @Inject constructor(
    private val apiService: ApiService,
    private val appDB: AppDB
) : RemoteMediator<Int, Cats>() {

    private val catsDao = appDB.catsDao()
    private val remoteKeyDao = appDB.remoteKeyDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Cats>): MediatorResult {

        return try {

            val currentPage = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getFirstRemoteKey(state)
                    val prevPage = remoteKeys?.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getLastRemoteKey(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val res =
                apiService.getCats(order = "Asc", page = currentPage, limit = state.config.pageSize)
            val response = res.body() ?: emptyList()

            val endOfPagination = response.isEmpty()

            val prevKey = if (currentPage == 1) null else currentPage - 1
            val nextKey = if (endOfPagination) null else currentPage + 1

            appDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteAllKeys()
                    catsDao.deleteAllCats()
                }
                val keys = response.map {
                    RemoteKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeyDao.insertKeys(keys)
                catsDao.insertCats(response)
            }
            MediatorResult.Success(endOfPagination)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Cats>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getKeys(id)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Cats>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { cat -> remoteKeyDao.getKeys(cat.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Cats>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { cat -> remoteKeyDao.getKeys(cat.id) }
    }

}
