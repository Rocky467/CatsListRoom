package com.google.assign.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.google.assign.db.AppDB
import com.google.assign.db.CatsKey
import com.google.assign.model.Cats
import com.google.assign.utils.log
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RemoteDataMediator(
    private val apiService: ApiService,
    private val appDB: AppDB
) : RemoteMediator<Int, Cats>() {

    private val startingPage = 1

    private val catsDao = appDB.catsDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Cats>
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {

            val netResponse = apiService.getCats(order = "Asc", page = page, limit = state.config.pageSize)

            val response = netResponse.body() ?: emptyList()
            log("dataHere", response)

            val endOfPagination = response.isEmpty()

            appDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    catsDao.deleteAllCatKeys()
                    catsDao.deleteAllCats()
                }
                val prevKey = if (page == startingPage) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1
                val keys = response.map {
                    CatsKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                catsDao.insertCatKeys(keys)
                catsDao.insertCats(response)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, Cats>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: startingPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Cats>): CatsKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                catsDao.getCatKeys(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Cats>): CatsKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { cat -> catsDao.getCatKeys(cat.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Cats>): CatsKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { cat -> catsDao.getCatKeys(cat.id) }
    }


}
