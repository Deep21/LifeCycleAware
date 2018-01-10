package com.example.samfisher.lifecycleaware.datasource;

import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.di.InvoiceApi;
import javax.inject.Inject;

/**
 * Created by Samfisher on 09/01/2018.
 */

public class ContactDataStoreFactory {

  private InvoiceApi invoiceApi;

  @Inject
  public ContactDataStoreFactory(InvoiceApi invoiceApi) {
    this.invoiceApi = invoiceApi;
  }

  public LocalContactDataStore createLocalStore() {
    return new LocalContactDataStore();
  }

  public ContactRemoteDataStore<Contact> createRemoteDataStore() {
    return new RemoteContactDataStore(invoiceApi);
  }
}
