package org.example.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class TodoModel {

    int id;

    @NotEmpty
    String name;

    @NotEmpty
    String description;

    @Size(min = 1)
    List<SubTasksModel> tasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubTasksModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<SubTasksModel> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
