package com.example.samfisher.lifecycleaware.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import javax.inject.Inject;

/**
 * Created by Samfisher on 27/11/2017.
 */

public class TaskDetailViewModelFactory implements ViewModelProvider.Factory {

  private TaskDetailViewModel myViewModel;

  @Inject
  public TaskDetailViewModelFactory(TaskDetailViewModel myViewModel) {
    this.myViewModel = myViewModel;
  }

  @NonNull
  @Override
  @SuppressWarnings("unchecked")
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(TaskDetailViewModel.class)) {
      return (T) myViewModel;
    }
    throw new IllegalArgumentException("Unknown class name");
  }
}
