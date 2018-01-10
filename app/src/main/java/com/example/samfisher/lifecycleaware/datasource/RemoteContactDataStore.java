package com.example.samfisher.lifecycleaware.datasource;

import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.di.InvoiceApi;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 09/01/2018.
 */

public class RemoteContactDataStore implements ContactRemoteDataStore<Contact> {

  private InvoiceApi invoiceApi;

  @Inject
  public RemoteContactDataStore(InvoiceApi invoiceApi) {
    this.invoiceApi = invoiceApi;
  }

  @Override
  public Observable<List<Contact>> getList() {
    return invoiceApi.getListContacts();
  }

  @Override
  public Observable<Contact> getContact(int id) {
    return invoiceApi.getContact(id);
  }

}
