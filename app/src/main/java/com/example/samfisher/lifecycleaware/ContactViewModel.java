package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.samfisher.lifecycleaware.di.InvoiceRepo;
import com.example.samfisher.lifecycleaware.di.Resource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class ContactViewModel extends ViewModel {

  private static final String TAG = "ContactViewModel";
  private InvoiceRepo invoiceRepo;
  private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  private MutableLiveData<Contact> contactMutableLiveData = new MediatorLiveData<>();
  private MutableLiveData<Resource<List<Contact>>> listContactMediatorLiveData = new MutableLiveData<>();
  private MediatorLiveData<Resource> tMediatorLiveDatas;
  private MediatorLiveData<Resource> contactsListMediatorLiveData;
  private SingleLiveEvent<Integer> contactId = new SingleLiveEvent<>();
  private MutableLiveData<Resource<Contact>> data = new MediatorLiveData<>();

  @Inject
  public ContactViewModel(InvoiceRepo invoiceRepo) {
    this.invoiceRepo = invoiceRepo;
  }

  public LiveData<Contact> getContact() {
    return Transformations.switchMap(contactId, input -> {
      Log.d(TAG, "apply: " + input);
      invoiceRepo.getContact(input).subscribe(s -> contactMutableLiveData.setValue(s));
      return contactMutableLiveData;
    });
  }

  public MediatorLiveData<Resource> getContactById() {
    if (tMediatorLiveDatas == null) {
      tMediatorLiveDatas = new MediatorLiveData<>();
      tMediatorLiveDatas.addSource(contactId, this::loadContactById);
      tMediatorLiveDatas.addSource(data, resource -> tMediatorLiveDatas.setValue(resource));
      tMediatorLiveDatas.addSource(error, resource -> tMediatorLiveDatas.setValue(resource));
    }
    Log.d(TAG, "getContactById: e");
    return tMediatorLiveDatas;
  }

  public void loadContactById(int id) {
    Log.d(TAG, "loadContactById: " + id);
    invoiceRepo.getContact(id).subscribe(
        s -> data.setValue(Resource.success(s)),
        throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
    );
  }

  public void loadContact() {
    invoiceRepo.getContacts()
        .subscribe(
            contacts -> listContactMediatorLiveData.setValue(Resource.success(contacts)),
            throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
        );
  }

  public MediatorLiveData<Resource> getContacts() {
    if (contactsListMediatorLiveData == null) {
      contactsListMediatorLiveData = new MediatorLiveData<>();
      loadContact();
      contactsListMediatorLiveData.addSource(listContactMediatorLiveData,
          resource -> contactsListMediatorLiveData.setValue(resource));
      contactsListMediatorLiveData
          .addSource(error, resource -> contactsListMediatorLiveData.setValue(resource));
    }

    return contactsListMediatorLiveData;
  }

  public void setContactId(Integer id) {
    contactId.setValue(id);
  }

}
