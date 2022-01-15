package com.google.assign.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.assign.db.AppDB

class Repository(private val apiService: ApiService, private val appDB: AppDB) {

    @OptIn(ExperimentalPagingApi::class)
    val userList = Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = true),
        remoteMediator = RemoteDataMediator(apiService, appDB),
        pagingSourceFactory = { appDB.catsDao().getCats() }
    )

}