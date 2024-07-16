package com.google.assignment.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.assignment.db.AppDB
import com.google.assignment.model.NetworkCat
import com.google.assignment.utils.Resource

class Repository(private val catsRemoteDataSource: CatsRemoteDataSource, private val appDB: AppDB) {

    @OptIn(ExperimentalPagingApi::class)
    val catsList = Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = true),
        remoteMediator = RemoteDataMediator(catsRemoteDataSource.apiService, appDB),
        pagingSourceFactory = { appDB.catsDao().getCats() }
    )

    fun getCatById(catId: String): Resource<NetworkCat> = catsRemoteDataSource.getCatById(catId)

}