package com.example.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by afp on 27/03/18.
 */

public interface TaskRepositoryInterface {

  Completable insertOrUpdate();

  Completable delete();

  Single findTaskById();

  Observable getTasks();
}
