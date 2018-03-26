package com.example.samfisher.lifecycleaware.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.samfisher.lifecycleaware.RealmLiveData;
import com.example.samfisher.lifecycleaware.RxLiveData;
import com.example.samfisher.lifecycleaware.SingleLiveEvent;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.domain.interactor.TaskRetrieveInteractor;
import com.example.samfisher.lifecycleaware.model.Task;
import com.example.samfisher.lifecycleaware.realm.RealmTask;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.realm.RealmResults;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class TaskViewModel extends ViewModel {

  private static final String TAG = "TaskViewModel";
  private TaskRetrieveInteractor taskInteractor;
  private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  private MediatorLiveData<Resource> contactsListMediatorLiveData;
  private RxLiveData<Resource<List<TaskEntity>>> rxTaskLiveData = new RxLiveData<>();
  private SingleLiveEvent<Integer> taskId = new SingleLiveEvent<>();


  @Inject
  public TaskViewModel(TaskRetrieveInteractor taskInteractor) {
    this.taskInteractor = taskInteractor;

  }

  public SingleLiveEvent<Integer> getSingleLiveDataTaskId() {
    return taskId;
  }


  public void loadTasks() {
    taskInteractor
        .getList()
        .doOnSubscribe(this::handleDisposable)
        .subscribe(this::notifyOnSuccess, this::notifyOnError);
  }

  private void handleDisposable(Disposable disposable) {
    rxTaskLiveData.addDisposable(disposable);
  }

  private void notifyOnSuccess(List<TaskEntity> tasks) {
    rxTaskLiveData.setValue(Resource.success(tasks));
  }

  private void notifyOnError(Throwable throwable) {
    error.setValue(Resource.error(throwable.getMessage(), throwable));
  }

  private void notifyOnFinish() {
    rxTaskLiveData.setValue(Resource.loading(null));
  }

  public MediatorLiveData<Resource> getTasks() {
    if (contactsListMediatorLiveData == null) {
      contactsListMediatorLiveData = new MediatorLiveData<>();
      //success
      contactsListMediatorLiveData
          .addSource(rxTaskLiveData, resource -> contactsListMediatorLiveData.setValue(resource));
      //error handling
      contactsListMediatorLiveData
          .addSource(error, resource -> contactsListMediatorLiveData.setValue(resource));
      loadTasks();
    }
    return contactsListMediatorLiveData;
  }

  public void setTaskId(int taskId) {
    this.taskId.setValue(taskId);
  }

}
