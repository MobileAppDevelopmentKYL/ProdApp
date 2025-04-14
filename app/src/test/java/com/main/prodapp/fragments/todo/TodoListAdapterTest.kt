import com.main.prodapp.database.TodoData
import com.main.prodapp.fragments.todo.TodoListAdapter
import org.junit.Assert.assertEquals
import org.junit.Test

class TodoListAdapterTest {

    @Test
    fun testItemCount() {
        val sampleData = listOf(
            TodoData("Task 1", "title","desc",false),
            TodoData("Task 2", "title","desc",false),
        )

        val adapter = TodoListAdapter(
            todo = sampleData,
            onDelete = {},
            onUpdate = {},
            onCapture = {}
        )

        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun testItemCountEmpty() {

        val adapter = TodoListAdapter(
            todo = emptyList(),
            onDelete = {},
            onUpdate = {},
            onCapture = {}
        )

        assertEquals(0, adapter.itemCount)
    }

    @Test
    fun testItemCountMore() {
        val sampleData = listOf(
            TodoData("Task 1", "title","desc",false),
            TodoData("Task 2", "title","desc",false),
            TodoData("Task 3", "title","desc",false),
            TodoData("Task 4", "title","desc",false),
            TodoData("Task 5", "title","desc",false),
        )

        val adapter = TodoListAdapter(
            todo = sampleData,
            onDelete = {},
            onUpdate = {},
            onCapture = {}
        )

        assertEquals(5, adapter.itemCount)
    }
}
