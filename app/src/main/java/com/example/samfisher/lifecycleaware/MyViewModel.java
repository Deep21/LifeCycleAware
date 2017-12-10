package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.samfisher.lifecycleaware.di.InvoiceRepo;
import com.example.samfisher.lifecycleaware.di.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class MyViewModel extends ViewModel {

    private static final String TAG = "MyViewModel";
    private InvoiceRepo invoiceRepo;
    private MediatorLiveData<Resource> tMediatorLiveData;
    private MutableLiveData<Resource<List<Contact>>> contact = new MutableLiveData<>();
    private MutableLiveData<Resource<Throwable>> error = new MutableLiveData<>();

    private List<Contact> contacts;


    @Inject
    public MyViewModel(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    public MediatorLiveData<Resource> init() {

        if (tMediatorLiveData == null) {
            tMediatorLiveData = new MediatorLiveData<>();
            invoiceRepo
                    .getContact()
                    .subscribe(
                            contacts -> {
                                contact.setValue(Resource.success(contacts));
                                this.contacts = contacts;
                            },
                            throwable -> {
                                Log.d(TAG, "onError: " + throwable);
                                error.setValue(Resource.error(throwable.getMessage(), throwable));
                            },
                            () -> Log.d(TAG, "complete: "));

            tMediatorLiveData.addSource(contact, listResource -> tMediatorLiveData.setValue(listResource));
            tMediatorLiveData.addSource(error, resource -> {
                tMediatorLiveData.setValue(resource);
            });
        }

        return tMediatorLiveData;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
