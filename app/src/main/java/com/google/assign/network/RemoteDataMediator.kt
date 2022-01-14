package com.google.assign.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.google.assign.db.DataMapper
import com.google.assign.db.UserDao
import com.google.assign.db.UserEntity
import com.google.assign.db.UserKey
import com.google.assign.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers
import java.io.IOException


@ExperimentalPagingApi
class RemoteDataMediator(
    private val apiService: ApiService,
    private val userDao: UserDao
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, UserEntity>
    ): MediatorResult {

        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    userDao.getKeys().firstOrNull()
                }
            }

            var after = loadKey?.nextKey ?: 0

            val response = apiService.getUsers(10).body() ?: emptyList()
            log("$response")

            val user = ArrayList<UserEntity>()
            val dataMapper = Mappers.getMapper(DataMapper::class.java)

            response.forEach {
                val users = dataMapper.mapUserToUserEntity(it)
                user.add(users)
            }

            withContext(Dispatchers.IO) {
                if (loadType == LoadType.REFRESH) {
                    userDao.deleteKey()
                    userDao.deleteUser()
                }
                after += 1
                userDao.insertKeys(UserKey(0, after,0))
                userDao.insertUser(user)
            }

            MediatorResult.Success(endOfPaginationReached = user.isEmpty())

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }


}
