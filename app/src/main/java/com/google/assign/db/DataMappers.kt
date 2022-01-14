package com.google.assign.db

import com.google.assign.model.User
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValueMappingStrategy
import org.mapstruct.NullValuePropertyMappingStrategy


@Mapper(
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
interface DataMapper{

    fun mapUserToUserEntity(user: User): UserEntity
}



