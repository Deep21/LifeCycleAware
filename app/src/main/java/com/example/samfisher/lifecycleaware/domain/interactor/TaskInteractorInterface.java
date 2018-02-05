package com.example.samfisher.lifecycleaware.domain.interactor;

import com.example.samfisher.lifecycleaware.model.Task;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Samfisher on 11/01/2018.
 */

public interface TaskInteractorInterface<T> {

  Observable<List<T>> getList();
  Observable<T> get(int i);

  interface PostInteractorInterface<T>{
    Observable<Task> post(T model);
  }

}
