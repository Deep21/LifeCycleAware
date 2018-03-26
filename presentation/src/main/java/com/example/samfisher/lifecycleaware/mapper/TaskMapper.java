package com.example.samfisher.lifecycleaware.mapper;

import android.util.Log;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.model.Task;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 24/01/2018.
 */

public class TaskMapper implements MapInterface<Task, TaskEntity>{

  @Inject
  public TaskMapper() {
  }

  @Override
  public List<TaskEntity> mapToList(List<Task> tasks) {
    List<TaskEntity> taskEntities = new ArrayList<>();
    for (Task task : tasks) {
      TaskEntity entity = new TaskEntity();
      entity.setTitle(task.getTitle());
      entity.setDescription(task.getDescription());
      taskEntities.add(entity);
    }
    return taskEntities;
  }

  @Override
  public TaskEntity mapTo(Task task) {
    TaskEntity taskEntity = new TaskEntity();
    Log.d("TaskMapper", "mapTo: " + task.getId());
    taskEntity.setId(task.getId());
    taskEntity.setTitle(task.getTitle());
    taskEntity.setDescription(task.getDescription());
    return taskEntity;
  }


}

