import androidx.test.runner.AndroidJUnit4
import com.main.prodapp.database.ListRepository
import com.main.prodapp.database.TodoData
import com.main.prodapp.database.TodoListRepository
import com.main.prodapp.viewModel.CalendarViewModel
import junit.framework.TestCase.assertEquals
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
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.RunWith

class CalendarViewModelTest {

    private lateinit var viewModel: CalendarViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val fakeRepo = FakeTodoListRepository()
        viewModel = CalendarViewModel(fakeRepo)
    }

    @Test
    fun updateDateTest(){
        assertEquals(1,1)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}

private class FakeTodoListRepository:ListRepository<TodoData>() {

    private val todoListFlow = MutableStateFlow<List<TodoData>>(emptyList())

    override fun getTodoList(): Flow<List<TodoData>> = todoListFlow.asStateFlow()

    override suspend fun getTodoItem(title: String): TodoData {
        return todoListFlow.value.first { it.title == title }
    }

//    fun updateTodo(todoData: TodoData) {
//        val updatedList = todoListFlow.value.map {
//            if (it.title == todoData.title) todoData else it
//        }
//        todoListFlow.value = updatedList
//    }

    override suspend fun insertTodo(todoData: TodoData) {
        todoListFlow.value += todoData
    }

    override suspend fun removeTodo(todoData: TodoData) {
        todoListFlow.value = todoListFlow.value.filter { it.title != todoData.title }
    }

    override suspend fun deleteAll() {
        todoListFlow.value = emptyList()
    }
}
