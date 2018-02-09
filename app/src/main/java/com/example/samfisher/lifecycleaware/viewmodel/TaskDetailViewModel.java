package com.example.samfisher.lifecycleaware.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.samfisher.lifecycleaware.RxLiveData;
import com.example.samfisher.lifecycleaware.SingleLiveEvent;
import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.domain.interactor.TaskRetrieveInteractor;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.reactivestreams.Subscription;
import org.w3c.dom.Entity;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class TaskDetailViewModel extends ViewModel {

  private static final String TAG = "TaskDetailViewModel";
  private TaskRetrieveInteractor taskInteractor;
  private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  private MutableLiveData<List<TaskEntity>> data = new MutableLiveData<>();
  private MediatorLiveData<Resource> tMediatorLiveDatas;
  private RxLiveData<Resource<TaskEntity>> listGenericLiveData = new RxLiveData<>();

  public void setRxSearchLiveData(
      RxLiveData<Resource> rxSearchLiveData) {
    this.rxSearchLiveData = rxSearchLiveData;
  }

  private RxLiveData<Resource> rxSearchLiveData = new RxLiveData<>();
  private SingleLiveEvent<Integer> taskId = new SingleLiveEvent<>();
  private MutableLiveData<CharSequence> charSequenceMutableLiveData = new MutableLiveData<>();
  private MediatorLiveData<Resource> searchMediatorLiveData = new MediatorLiveData<>();
  private BehaviorSubject<CharSequence> behaviorSubject = BehaviorSubject.create();

  @Inject
  public TaskDetailViewModel(TaskRetrieveInteractor taskInteractor) {
    this.taskInteractor = taskInteractor;
  }

  public SingleLiveEvent<Integer> getTaskId() {
    return taskId;
  }

  public MediatorLiveData<Resource> getTask() {
    if (tMediatorLiveDatas == null) {
      tMediatorLiveDatas = new MediatorLiveData<>();
      tMediatorLiveDatas.addSource(taskId, this::loadTask);
      tMediatorLiveDatas.addSource(error, resource -> tMediatorLiveDatas.setValue(resource));
    }
    return tMediatorLiveDatas;
  }

  /**
   *
   * @param id
   */
  public void loadTask(int id) {
    taskInteractor.get(id)
        .doOnSubscribe(this::handleDisposable)
        .subscribe(
            taskEntity -> listGenericLiveData.setValue(Resource.success(taskEntity)),
            throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
        );
  }

  public Flowable<List<TaskEntity>> getT(CharSequence charSequence){
    return taskInteractor.search(charSequence.toString());
  }

  public Flowable<List<TaskEntity>> getSearcheableTask(CharSequence charSequence) {
    return taskInteractor.search(charSequence.toString());
  }

  /**
   *
   * @param s
   */
  public void loadSearchTask(CharSequence s) {
   taskInteractor
        .search(s.toString())
        .subscribe(
            charSequence -> rxSearchLiveData.postValue(Resource.success(charSequence)),
            throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
        );
/*    behaviorSubject
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(charSequence -> Log.d(TAG, "accept: " + charSequence));
 */ }

  /**
   * Search task
   */
  public MediatorLiveData<Resource> searchTask() {
    /*if (searchLiveData == null) {
      searchLiveData = new MediatorLiveData<>();
      searchLiveData.addSource(charSequenceMutableLiveData, this::loadSearchTask);
    }*/
    //Log.d(TAG, "searchTask: " + Thread.currentThread().getName());
    searchMediatorLiveData.addSource(rxSearchLiveData, resource -> searchMediatorLiveData.setValue(resource));
    return searchMediatorLiveData;
  }

  private void handleDisposable(Disposable disposable) {
    listGenericLiveData.addDisposable(disposable);
  }

  public void setTaskId(int taskId) {
    this.taskId.setValue(taskId);
  }

  public void setCharSequence(CharSequence charSequence) {
    //charSequenceMutableLiveData.setValue(charSequence);
    behaviorSubject.onNext(charSequence);
  }

  public MutableLiveData<List<TaskEntity>> getData() {
    return data;
  }

  public void setData(List<TaskEntity> data) {
    this.data.setValue(data);
  }
}
