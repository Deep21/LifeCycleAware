package com.example.samfisher.lifecycleaware.di;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.samfisher.lifecycleaware.di.Status.EMPTY;
import static com.example.samfisher.lifecycleaware.di.Status.ERROR;
import static com.example.samfisher.lifecycleaware.di.Status.LOADING;
import static com.example.samfisher.lifecycleaware.di.Status.SUCCESS;

/**
 * Created by Samfisher on 07/12/2017.
 */

public class Resource<T> {

  @NonNull
  public Status status;
  @Nullable
  public T data;
  @Nullable
  public String message;

  private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
    this.status = status;
    this.data = data;
    this.message = message;
  }

  public static <T> Resource<T> success(@NonNull T data) {
    return new Resource<>(SUCCESS, data, null);
  }

  public static <T> Resource<T> error(String msg, @Nullable T data) {
    return new Resource<>(ERROR, data, msg);
  }

  public static <T> Resource<T> loading(@Nullable T data) {
    return new Resource<>(LOADING, data, null);
  }

  public static <T> Resource<T> empty(@Nullable T data) {
    return new Resource<>(EMPTY, data, null);
  }
}