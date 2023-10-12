package no.hvl.dat250.rest.todos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:4200")  // Accept requests from Angular-app
public class TodoController {

  public static final String TODO_WITH_THE_ID_X_NOT_FOUND = "Todo with the id %s not found!";

  private final Map<Long, Todo> todos = new HashMap<>();
  private long todoIdCounter = 1;

  @PostMapping
  public Todo createTodo(@RequestBody Todo newTodo) {
    long id = todoIdCounter++;
    newTodo.setId(id);
    todos.put(id, newTodo);
    return newTodo;
  }

  @GetMapping("/{id}")
  public Todo getTodo(@PathVariable Long id) {
    Todo todo = todos.get(id);
    if (todo == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
    }
    return todo;
  }

  @GetMapping
  public Collection<Todo> getAllTodos() {
    return todos.values();
  }

  @PutMapping("/{id}")
  public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
    Todo existingTodo = todos.get(id);
    if (existingTodo == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
    }
    updatedTodo.setId(id);
    todos.put(id, updatedTodo);
    return updatedTodo;
  }

  @DeleteMapping("/{id}")
  public void deleteTodo(@PathVariable Long id) {
    if (!todos.containsKey(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(TODO_WITH_THE_ID_X_NOT_FOUND, id));
    }
    todos.remove(id);
  }
}

