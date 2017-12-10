package com.example.samfisher.lifecycleaware.di;

import android.arch.lifecycle.LiveData;

import com.example.samfisher.lifecycleaware.Contact;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Samfisher on 27/11/2017.
 */

public class InvoiceRepo {

  private InvoiceApi invoiceApi;

  @Inject
  public InvoiceRepo(InvoiceApi invoiceApi) {
    this.invoiceApi = invoiceApi;
  }

  public Observable<List<Contact>> getContacts() {
    return invoiceApi.getListContacts()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  public Observable<Contact> getContact(int id) {
    return invoiceApi.getContact(id)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }
}
