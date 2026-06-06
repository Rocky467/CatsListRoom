package com.google.assignment.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.assignment.base.BaseRepository
import com.google.assignment.db.AppDB
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val appDB: AppDB
) : BaseRepository() {

    @OptIn(ExperimentalPagingApi::class)
    fun getCatsList() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = RemoteDataMediator(apiService, appDB),
        pagingSourceFactory = { appDB.catsDao().getCats() }
    )

    suspend fun getCatById(catId: String) = safeApiCall { apiService.getCatById(catId) }

}