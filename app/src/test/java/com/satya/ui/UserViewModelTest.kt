package com.satya.ui

import com.satya.data.UserRepository
import com.satya.domain.User
import com.satya.core.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {
    private val repo = mockk<UserRepository>()
    private lateinit var viewModel: UserViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repo.getUsers() } returns flow { emit(Resource.Success(listOf(User(1, "A", "a@example.com")))) }
        viewModel = UserViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadUsers() = runTest {
        viewModel.load()
        advanceUntilIdle()
        val state = viewModel.state.value
        assertEquals(1, state.users.size)
        assertEquals("A", state.users[0].name)
    }
}
