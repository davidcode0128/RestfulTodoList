package com.Davidcode.RESTfulMyWeb.controller;

import com.Davidcode.RESTfulMyWeb.model.entity.RESTfulTodo;
import com.Davidcode.RESTfulMyWeb.service.RESTfulTodoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "RESTful Todo list 相關api")
@RestController
@RequestMapping("/api")
public class RESTfulTodoController {
	@Autowired
	RESTfulTodoService todoService;
	
	@ApiOperation("取得所有代辦事項列表")
    @ApiResponses({
            @ApiResponse(code=401,message="沒有權限"),
            @ApiResponse(code=404,message="找不到路徑")
    })
		
	@GetMapping("/todos")
	public ResponseEntity<Object> getTodos() {
		Iterable<RESTfulTodo> todoList = todoService.getTodos();
		return ResponseEntity.status(HttpStatus.OK).body(todoList);
	}

	@GetMapping("/todos/{id}")
	public Optional<RESTfulTodo> getTodo(@PathVariable Integer id) {
		Optional<RESTfulTodo> todo = todoService.findById(id);
		return todo;
	}

	@PostMapping("/todos")
	public ResponseEntity<Object> createTodo(@RequestBody RESTfulTodo todo) {
		Integer rlt = todoService.createTodo(todo);
		return ResponseEntity.status(HttpStatus.CREATED).body(rlt);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<Object> upadteTodo(@PathVariable Integer id,
			@RequestBody RESTfulTodo todo) {
		Boolean rlt = todoService.updateTodo(id, todo);
		if (!rlt) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Status 欄位不能為空");
		}
		return ResponseEntity.status(HttpStatus.OK).body("");
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Object> deleteTodo(@PathVariable Integer id) {
		Boolean rlt = todoService.deleteTodo(id);
		if (!rlt) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id 不存在");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
	}
          
}