package org.example.dao;

import org.example.api.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class TodoDb {
    public static List<TodoModel> todos = new ArrayList<>();

    public static List<TodoModel> getTodos(){
        return todos;
    }

    public static void addTodo(TodoModel todo){
        todos.add(todo);
    }

    public static TodoModel getTodo(int id){
        for(TodoModel todo : todos){
            if(todo.getId() == id) return todo;

        }
        return null;
    }

    public static void updateTodo(int position, TodoModel todo){
        todos.remove(position);
        todos.add(todo);
    }

    public static int getIndex(int id){
        for(int i = 0; i<todos.size(); i++){
            if(todos.get(i).getId() == id) return i;
        }
        return -1;
    }

    public static void deleteTodo(int position){
        todos.remove(position);
    }
}
