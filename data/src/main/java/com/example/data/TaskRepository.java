package com.example.data;

import com.example.domain.repository.TaskRepositoryInterface;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by afp on 27/03/18.
 */

public class TaskRepository implements TaskRepositoryInterface {

  public TaskRepository() {
  }

  @Override
  public Completable insertOrUpdate() {
    return null;
  }

  @Override
  public Completable delete() {
    return null;
  }

  @Override
  public Single findTaskById() {
    return null;
  }

  @Override
  public Observable getTasks() {
    return null;
  }
}
