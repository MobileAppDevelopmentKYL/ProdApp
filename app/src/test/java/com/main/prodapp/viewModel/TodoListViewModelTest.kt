package com.main.prodapp.viewModel

import androidx.test.runner.AndroidJUnit4
import com.main.prodapp.database.ListRepository
import com.main.prodapp.database.TodoData
import com.main.prodapp.database.TodoListRepository
import com.main.prodapp.viewModel.CalendarViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

class TodoListViewModelTest{
    private lateinit var viewModel: TodoListViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val fakeRepo = FakeTodoListRepository()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepo.emitTodos(listOf(TodoData("ID", "title", "desc", false)))
        viewModel = TodoListViewModel("title",fakeRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addTodoTest(): Unit = runTest {
        val newItem = TodoData("ID1","title1", "desc1", false)
        viewModel.addTodo(newItem)
        advanceUntilIdle()
        assertTrue(viewModel.todoList.value.contains(newItem))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remvoeTodoTest(): Unit = runTest {
        val newItem = TodoData("ID1","title1", "desc1", false)
        viewModel.addTodo(newItem)
        viewModel.removeTodo(newItem)
        advanceUntilIdle()
        assertFalse(viewModel.todoList.value.contains(newItem))
    }

    @Test
    fun updateTodoTest() = runTest {
        val newItem = TodoData("ID1","title1", "desc1", false)
        viewModel.addTodo(newItem)
        val updated = TodoData("ID1","title1","Updated", false)
        viewModel.updateTodo(updated)
        advanceUntilIdle()
        assertTrue(viewModel.todoList.value.contains(updated))
    }



}

private class FakeTodoListRepository:ListRepository<TodoData>() {

    private val todoListFlow = MutableStateFlow<List<TodoData>>(emptyList())

    override fun getTodoList(): Flow<List<TodoData>> = todoListFlow.asStateFlow()

    override suspend fun getTodoItem(title: String): TodoData {
        return todoListFlow.value.first { it.title == title }
    }

    override fun updateTodo(todoData: TodoData) {
        val updatedList = todoListFlow.value.map {
            if (it.title == todoData.title) todoData else it
        }
        todoListFlow.value = updatedList
    }

    override suspend fun insertTodo(todoData: TodoData) {
        todoListFlow.value += todoData
    }

    override suspend fun removeTodo(todoData: TodoData) {
        todoListFlow.value = todoListFlow.value.filter { it.title != todoData.title }
    }

    override suspend fun deleteAll() {
        todoListFlow.value = emptyList()
    }

    fun emitTodos(list: List<TodoData>) {
        todoListFlow.value = list
    }
}