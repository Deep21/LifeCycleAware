package com.example.samfisher.lifecycleaware.viewmodel;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.samfisher.lifecycleaware.RxLiveData;
import com.example.samfisher.lifecycleaware.di.Resource;
import com.example.samfisher.lifecycleaware.domain.interactor.AddTaskInteractor;
import com.example.samfisher.lifecycleaware.model.Task;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class AddTaskViewModel extends ViewModel {

  private static final String TAG = "AddTaskViewModel";
  private final AddTaskInteractor addContactInteractor;
  RxLiveData<Resource<Void>> data = new RxLiveData<>();
  MutableLiveData<Task> contactMutableLiveData = new MutableLiveData<>();
  MediatorLiveData<Resource> mediatorLiveData;

  @Inject
  public AddTaskViewModel(AddTaskInteractor addContactInteractor) {
    this.addContactInteractor = addContactInteractor;
  }

  /**
   * Save task
   *
   * @return MediatorLiveData<Resource> res
   */
  public MediatorLiveData<Resource> saveTask() {
    if (mediatorLiveData == null) {
      mediatorLiveData = new MediatorLiveData<>();
      mediatorLiveData.addSource(contactMutableLiveData,
          task -> addContactInteractor
              .post(task)
              .subscribe(o -> mediatorLiveData.setValue(Resource.loading(false))));
    }
    return mediatorLiveData;
  }

  public void setTask(Task task) {
    contactMutableLiveData.setValue(task);
  }
}
