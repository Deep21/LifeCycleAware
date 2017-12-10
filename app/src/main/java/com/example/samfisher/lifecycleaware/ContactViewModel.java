package com.example.samfisher.lifecycleaware;

import android.annotation.SuppressLint;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.example.samfisher.lifecycleaware.di.InvoiceRepo;
import com.example.samfisher.lifecycleaware.di.Resource;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class ContactViewModel extends ViewModel {

  private static final String TAG = "ContactViewModel";
  private InvoiceRepo invoiceRepo;
  public MediatorLiveData<Resource> liveData;
  public MutableLiveData<Resource<List<ContactModel>>> mutableLiveDataContact = new MutableLiveData<>();
  public MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();
  MutableLiveData<Integer> idContactLiveData = new MutableLiveData<>();

  private MutableLiveData<Contact> contactLiveData = new MutableLiveData<>();

  @Inject
  public ContactViewModel(InvoiceRepo invoiceRepo) {
    this.invoiceRepo = invoiceRepo;
  }

  public LiveData<Contact> getContact() {
    return Transformations.switchMap(idContactLiveData, new Function<Integer, LiveData<Contact>>() {
      @Override
      public LiveData<Contact> apply(Integer input) {
        Log.d(TAG, "apply: " + input);
        return null;
      }
    });
  }

  public MediatorLiveData<Resource> getContacts() {
    if (liveData == null) {
      liveData = new MediatorLiveData<>();
      invoiceRepo.getContacts().map(contacts -> {
        List<ContactModel> contactModels = new ArrayList<>();
        for (Contact contact : contacts) {
          ContactModel contactModel = new ContactModel();
          contactModel.setId(contact.getId());
          contactModel.setFirstname(contact.getFirstname());
          contactModel.setLastname(contact.getLastname());
          contactModel.setEmail(contact.getEmail());
          contactModel.setCompany(contact.getCompany());
          contactModel.setBirthday(contact.getBirthday());
          contactModels.add(contactModel);
        }
        return contactModels;

      }).subscribe(contactModels -> {
            mutableLiveDataContact.setValue(Resource.success(contactModels));
            Log.d(TAG, "getContacts: " + contactModels);
          },
          throwable -> {
            Log.d(TAG, "onError: " + throwable);
            error.setValue(Resource.error(throwable.getMessage(), throwable));
          },
          () -> Log.d(TAG, "complete: "));

      liveData.addSource(mutableLiveDataContact,
          listResource -> liveData.setValue(listResource));
      liveData.addSource(error, resource -> liveData.setValue(resource));

    }
    return liveData;
  }

  @SuppressLint("NewApi")
  public void setContact(int position) {
    if (Objects.equals(this.idContactLiveData.getValue(), idContactLiveData)) {
      return;
    }
    this.idContactLiveData.setValue(position);
    idContactLiveData.setValue(position);

  }
}