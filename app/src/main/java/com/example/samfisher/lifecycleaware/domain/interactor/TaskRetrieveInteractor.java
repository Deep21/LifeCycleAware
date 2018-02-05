package com.example.samfisher.lifecycleaware.domain.interactor;

import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.data.TaskRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 07/01/2018.
 */

public class TaskRetrieveInteractor implements TaskInteractorInterface<TaskEntity> {

  private TaskRepository taskRepository;

  @Inject
  public TaskRetrieveInteractor(TaskRepository repository) {
    this.taskRepository = repository;
  }

  @Override
  public Observable<List<TaskEntity>> getList() {
    return taskRepository.getTasks()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<TaskEntity> get(int contactId) {
    return taskRepository.getTask(contactId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<List<TaskEntity>> getLocalTasks() {
    return taskRepository.getTasks().observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<List<TaskEntity>> search(String keyword) {
    return taskRepository.search(keyword)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

}