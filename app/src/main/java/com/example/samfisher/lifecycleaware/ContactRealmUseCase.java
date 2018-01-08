package com.example.samfisher.lifecycleaware;

import com.example.samfisher.lifecycleaware.di.InvoiceRepo;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 07/01/2018.
 */

public class ContactRealmUseCase extends AbstractUseCase<RealmLiveData<Task>, Void>{

  private InvoiceRepo invoiceRepo;

  @Inject
  public ContactRealmUseCase(InvoiceRepo invoiceRepo) {
    this.invoiceRepo = invoiceRepo;
  }

  @Override
  protected Observable<RealmLiveData<Task>> getObservable(Void aVoid) {
    return null;
  }
}
