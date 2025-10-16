package com.satya.domain.repository

import com.satya.core.utils.Resource
import com.satya.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Resource<List<User>>>
}
