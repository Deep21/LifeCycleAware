package com.example.samfisher.lifecycleaware.domain.interactor;

import com.example.samfisher.lifecycleaware.data.TaskRepository;
import com.example.samfisher.lifecycleaware.model.Task;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by Samfisher on 07/01/2018.
 */
public class AddTaskInteractor implements TaskInteractorInterface.PostInteractorInterface<Task> {

  private TaskRepository taskRepository;

  @Inject
  public AddTaskInteractor(TaskRepository repository) {
    this.taskRepository = repository;
  }

  @Override
  public Observable<Task> post(Task task) {
    return taskRepository
        .post(task)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }


}
