package com.example.samfisher.lifecycleaware.data;

import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.datasource.TaskDataStoreFactory;
import com.example.samfisher.lifecycleaware.datasource.TaskLocalDataStore;
import com.example.samfisher.lifecycleaware.mapper.TaskMapper;
import com.example.samfisher.lifecycleaware.model.Task;
import com.example.samfisher.lifecycleaware.realm.RealmTask;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Samfisher on 27/11/2017.
 */
@Singleton
public class TaskRepository {

  private static final String TAG = "TaskRepository";
  private TaskDataStoreFactory dataStoreFactory;
  private TaskMapper taskMapper;

  @Inject
  public TaskRepository(TaskDataStoreFactory dataStoreFactory, TaskMapper taskMapper) {
    this.dataStoreFactory = dataStoreFactory;
    this.taskMapper = taskMapper;
  }

  /**
   * Get task
   */
  public Observable<List<TaskEntity>> getTasks() {
    TaskLocalDataStore<RealmTask> localTasks = dataStoreFactory.createLocalStore();
    Observable<List<TaskEntity>> localeTaskEntity = localTasks
        .getList()
        .map(taskRS -> {
          List<TaskEntity> taskEntities = new ArrayList<>();
          for (RealmTask taskR : taskRS) {
            TaskEntity entity = new TaskEntity();
            entity.setTitle(taskR.getTitle());
            entity.setDescription(taskR.getDescription());
            taskEntities.add(entity);
          }
          return taskEntities;
        });

    Observable<List<TaskEntity>> remoteTasks = dataStoreFactory
        .create()
        .getList()
        .map(tasks -> taskMapper.mapToList(tasks));

    return Observable.concat(localeTaskEntity, remoteTasks);
  }


  /**
   * Get single Task
   */
  public Observable<TaskEntity> getTask(int taskId) {
    return dataStoreFactory
        .create()
        .get(taskId)
        .map(task -> taskMapper.mapTo(task));
  }

  /**
   * Search task
   *
   * @param keyword
   * @return
   */
  public Observable<List<TaskEntity>> search(String keyword) {
    return dataStoreFactory
        .create()
        .search(keyword)
        .map(tasks -> taskMapper.mapToList(tasks));
  }

  /**
   * @param task
   * @return
   */
  public Observable<Task> post(Task task) {
    TaskLocalDataStore<RealmTask> localTasks = dataStoreFactory.createLocalStore();
    return dataStoreFactory
        .create()
        .post(task)
        .flatMap(task1 -> {
          RealmTask realmTask = new RealmTask();
          realmTask.setTitle(task1.getTitle());
          realmTask.setDescription(task1.getDescription());
          return localTasks.postTask(realmTask).toObservable();
        });
  }

}
