package com.google.assign.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.assign.db.UserDao


class Repository(private val apiService: ApiService, private val userDao: UserDao) {

    @OptIn(ExperimentalPagingApi::class)
    val userList = Pager(config = PagingConfig(pageSize = 10),
        remoteMediator = RemoteDataMediator(apiService, userDao),
        pagingSourceFactory = { userDao.getUser() }
    )


}