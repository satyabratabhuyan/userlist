package com.satya.data.repository

import com.satya.core.utils.Resource
import com.satya.data.remote.ApiService
import com.satya.domain.model.User
import com.satya.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val api: ApiService) : UserRepository {
    override fun getUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            val users = api.getUsers()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error fetching users"))
        }
    }
}
