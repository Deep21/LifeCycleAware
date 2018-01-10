package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.datasource.ContactRemoteDataStore;
import com.example.samfisher.lifecycleaware.datasource.ContactDataStoreFactory;
import com.example.samfisher.lifecycleaware.datasource.LocalContactDataStore;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Samfisher on 27/11/2017.
 */
@Singleton
public class ContactRepository {

  private ContactDataStoreFactory dataStoreFactory;

  @Inject
  public ContactRepository(ContactDataStoreFactory dataStoreFactory) {
    this.dataStoreFactory = dataStoreFactory;
  }

  public LocalContactDataStore createLocal() {
    return dataStoreFactory.createLocalStore();
  }

  public ContactRemoteDataStore create() {
    return dataStoreFactory.createRemoteDataStore();

  }

}
