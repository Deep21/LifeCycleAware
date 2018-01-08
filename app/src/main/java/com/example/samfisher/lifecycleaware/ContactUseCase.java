package com.example.samfisher.lifecycleaware;

import com.example.samfisher.lifecycleaware.di.InvoiceRepo;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Samfisher on 07/01/2018.
 */

public class ContactUseCase extends AbstractUseCase<List<Contact>, Void>{

  private InvoiceRepo invoiceRepo;

  @Inject
  public ContactUseCase(InvoiceRepo invoiceRepo) {
    this.invoiceRepo = invoiceRepo;
  }

  @Override
  protected Observable<List<Contact>> getObservable(Void aVoid) {
    return invoiceRepo.getContacts();
  }
}
