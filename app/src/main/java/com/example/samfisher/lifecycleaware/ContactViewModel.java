package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.samfisher.lifecycleaware.di.Resource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class ContactViewModel extends ViewModel {

  private static final String TAG = "ContactViewModel";
  private ContactResultInteractor contactUseCase;
  private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  private MediatorLiveData<Resource> tMediatorLiveDatas;
  private MediatorLiveData<Resource> contactsListMediatorLiveData;
  //  private MutableLiveData<Resource<List<Contact>>> listGenericLiveData = new MutableLiveData<>();
  private RxLiveData<Resource<List<Contact>>> listGenericLiveData = new RxLiveData<>();
  private SingleLiveEvent<Integer> contactId = new SingleLiveEvent<>();
  private MutableLiveData<Resource<Contact>> data = new MediatorLiveData<>();

  @Inject
  public ContactViewModel(ContactResultInteractor contactUseCase) {
    this.contactUseCase = contactUseCase;
  }

  public SingleLiveEvent<Integer> getContactId() {
    return contactId;
  }

  public MediatorLiveData<Resource> getContactById() {
    if (tMediatorLiveDatas == null) {
      tMediatorLiveDatas = new MediatorLiveData<>();
      tMediatorLiveDatas.addSource(contactId, this::loadContactById);
      tMediatorLiveDatas.addSource(data, resource -> tMediatorLiveDatas.setValue(resource));
      tMediatorLiveDatas.addSource(error, resource -> tMediatorLiveDatas.setValue(resource));
    }
    return tMediatorLiveDatas;
  }

  public void loadContactById(int id) {
/*    contactRepository.getContact(id).subscribe(
        s -> data.setValue(Resource.success(s)),
        throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
    );*/
  }

  public void loadContact() {
    contactUseCase.getList()
        .doOnSubscribe(this::handleDisposable)
        .subscribe(contacts -> listGenericLiveData.setValue(Resource.success(contacts)),
            this::notifyOnError);
  }

  private void handleDisposable(Disposable disposable) {
    listGenericLiveData.addDisposable(disposable);
  }

  private void notifyOnSuccess(List<Contact> contacts) {
    Log.d(TAG, "notifyOnSuccess: " + contacts);
    listGenericLiveData.setValue(Resource.success(contacts));
  }

  private void notifyOnError(Throwable throwable) {
    error.setValue(Resource.error(throwable.getMessage(), throwable));
  }

  private void notifyOnFinish() {
    listGenericLiveData.setValue(Resource.loading(null));
  }

  private boolean isEmptyValue(List<Contact> emptyValue) {
    if (emptyValue.isEmpty()) {
      listGenericLiveData.setValue(Resource.empty(emptyValue));
    }
    return true;
  }

  public MediatorLiveData<Resource> getContacts() {
    if (contactsListMediatorLiveData == null) {
      contactsListMediatorLiveData = new MediatorLiveData<>();
      //success
      contactsListMediatorLiveData.addSource(listGenericLiveData,
          resource -> contactsListMediatorLiveData.setValue(resource));
      //error handling
      contactsListMediatorLiveData
          .addSource(error, resource -> contactsListMediatorLiveData.setValue(resource));
      loadContact();
    }

    return contactsListMediatorLiveData;
  }

  public void setContactId(Integer id) {
    contactId.setValue(id);
  }

}
