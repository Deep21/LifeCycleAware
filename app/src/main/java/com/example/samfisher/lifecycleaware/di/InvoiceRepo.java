package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.Task;
import io.reactivex.Observable;
import io.realm.Realm;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 27/11/2017.
 */

public class InvoiceRepo {

  private InvoiceApi invoiceApi;
  Realm realm;

  @Inject
  public InvoiceRepo(InvoiceApi invoiceApi) {
    this.invoiceApi = invoiceApi;
    realm = Realm.getDefaultInstance();
  }

/*  public Observable<Task> getLocalContact(){
    return realm.where(Task.class);
  }*/

  public Observable<List<Contact>> getContacts() {
    return invoiceApi.getListContacts();
  }

  public Observable<Contact> getContact(int id) {
    return invoiceApi.getContact(id);
  }
}
