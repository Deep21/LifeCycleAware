package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.Contact;
import com.example.samfisher.lifecycleaware.Task;
import com.example.samfisher.lifecycleaware.datasource.ContactLocalDataStore;
import com.example.samfisher.lifecycleaware.datasource.ContactRemoteDataStore;
import com.example.samfisher.lifecycleaware.datasource.ContactDataStoreFactory;
import com.example.samfisher.lifecycleaware.datasource.LocalContactDataStore;
import io.reactivex.Observable;
import io.realm.RealmResults;
import java.util.List;
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

  public ContactLocalDataStore<RealmResults<Task>> createLocal() {
    return dataStoreFactory.createLocalStore();
  }

  public Observable<List<Contact>> getContacts() {
    return dataStoreFactory.create().getList();

  }

}
