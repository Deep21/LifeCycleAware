package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.samfisher.lifecycleaware.di.InvoiceRepo;
import com.example.samfisher.lifecycleaware.di.Resource;
import io.reactivex.functions.Consumer;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class ContactViewModel extends ViewModel {

  private static final String TAG = "ContactViewModel";
  private InvoiceRepo invoiceRepo;
  private ContactUseCase contactUseCase;
  private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  private MediatorLiveData<Resource> tMediatorLiveDatas;
  private MediatorLiveData<Resource> contactsListMediatorLiveData;
  private RxLiveData<Resource<List<Contact>>> listGenericLiveData;
  private SingleLiveEvent<Integer> contactId = new SingleLiveEvent<>();
  private MutableLiveData<Resource<Contact>> data = new MediatorLiveData<>();

  @Inject
  public ContactViewModel(InvoiceRepo invoiceRepo, ContactUseCase contactUseCase) {
    this.invoiceRepo = invoiceRepo;
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
    invoiceRepo.getContact(id).subscribe(
        s -> data.setValue(Resource.success(s)),
        throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
    );
  }

  public void loadContact() {
    listGenericLiveData = new RxLiveData<>();
    RealmLiveData<Task> taskRealmLiveData = new RealmLiveData<>();
    listGenericLiveData.addDisposable(
        contactUseCase
            .excecute(null)
            .filter(this::isEmptyValue)
            .doOnNext(new Consumer<List<Contact>>() {
              @Override
              public void accept(List<Contact> contacts) throws Exception {
                //contactUseCase.excecute();
              }
            })
            .subscribe(
                contacts -> listGenericLiveData.setValue(Resource.success(contacts)),
                throwable -> error.setValue(Resource.error(throwable.getMessage(), throwable))
            )
    );
  }


  private boolean isEmptyValue(List<Contact> emptyValue) {
    if (emptyValue.isEmpty()) {
      listGenericLiveData.setValue(Resource.empty(emptyValue));
    }
    return true;
  }


  public MediatorLiveData<Resource> getContacts() {
    if (contactsListMediatorLiveData == null) {
      loadContact();
      contactsListMediatorLiveData = new MediatorLiveData<>();
      //success
      contactsListMediatorLiveData.addSource(listGenericLiveData,
          resource -> contactsListMediatorLiveData.setValue(resource));
      //error handling
      contactsListMediatorLiveData
          .addSource(error, resource -> contactsListMediatorLiveData.setValue(resource));
    }
    return contactsListMediatorLiveData;
  }

  public void setContactId(Integer id) {
    contactId.setValue(id);
  }

}
