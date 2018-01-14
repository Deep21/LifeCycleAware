package com.example.samfisher.lifecycleaware;

import com.example.samfisher.lifecycleaware.di.ContactRepository;
import com.example.samfisher.lifecycleaware.domain.ContactInteractorInterface;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 07/01/2018.
 */

public class ContactResultInteractor implements ContactInteractorInterface<Contact> {

  private ContactRepository contactRepository;

  @Inject
  public ContactResultInteractor(ContactRepository repository) {
    this.contactRepository = repository;
  }

  @Override
  public Observable<List<Contact>> getList() {
    return contactRepository
        .getContacts()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override
  public Observable<Contact> get(int contactId) {
    return contactRepository.getContact(contactId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());

  }

}
