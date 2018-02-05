package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.model.Task;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Samfisher on 24/11/2017.
 */
public interface InvoiceApi {

  @GET("tasks")
  Observable<List<Task>> getList();

  @GET("task/{id}")
  Observable<Task> get(@Path("id") int id);

  @POST("task")
  Observable<Task> addTask(@Body Task task);

  @FormUrlEncoded
  @POST("task/search")
  Observable<List<Task>> search(@Field("search") String search);
}
