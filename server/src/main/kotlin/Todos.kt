import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap

val todos = ConcurrentHashMap<String, Todo>()

fun json() =
  todos.map { (name, todo) ->
    "{\"name\": \"$name\", \"status\": \"${todo.status.name}\"}"
  }.joinToString(", ", "[", "]")

fun add(name: String) {
  todos.put(name, Todo(Status.Active))
}

fun remove(name: String) {
  todos[name]?.status = Status.Done
}

fun save() {
  val file = Paths.get("store.txt").toFile()
  file.writeText("")
  todos.map { (name, todo) ->
    "$name:${todo.status.name}\n"
  }.forEach({text ->
    print("saving: $text")
    file.appendText(text)
  })
}

fun load() {
  val file = Paths.get("store.txt").toFile()
  if (!file.exists()) return
  print("loading: ${file.readText()}")
  val text = file.readLines()
  text
      .map { Pair(
          it.substringBeforeLast(":"),
          it.substringAfterLast(":")) }
      .forEach { todos[it.first] = Todo(Status.valueOf(it.second)) }
}

enum class Status { Active, Done }
data class Todo(var status: Status)