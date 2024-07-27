package com.google.assignment.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.assignment.db.AppDB
import com.google.assignment.utils.Util.validateApi

class Repository(
    private val apiService: ApiService,
    private val appDB: AppDB
) {

    @OptIn(ExperimentalPagingApi::class)
    val catsList = Pager(config = PagingConfig(pageSize = 10),
        remoteMediator = RemoteDataMediator(apiService, appDB),
        pagingSourceFactory = { appDB.catsDao().getCats() }
    )

    suspend fun getCatById(catId: String) = validateApi(apiService.getCatById(catId))

}