package com.example.samfisher.lifecycleaware;

import com.example.samfisher.lifecycleaware.datasource.ContactDataStoreFactory;
import com.example.samfisher.lifecycleaware.di.ContactRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 07/01/2018.
 */

public class ContactUseCase extends AbstractUseCase<List<Contact>, Void>{

  private ContactDataStoreFactory contactDataStoreFactory;

  @Inject
  public ContactUseCase(ContactDataStoreFactory contactDataStoreFactory) {
    this.contactDataStoreFactory = contactDataStoreFactory;
  }

  @Override
  protected Observable<List<Contact>> getObservable(Void aVoid) {
    return contactDataStoreFactory
        .createRemoteDataStore()
        .getList();
  }
}
