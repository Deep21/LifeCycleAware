package com.example.samfisher.lifecycleaware.datasource;

import com.example.samfisher.lifecycleaware.model.Task;
import com.example.samfisher.lifecycleaware.realm.RealmTask;
import com.example.samfisher.lifecycleaware.realm.RealmTask;
import com.example.samfisher.lifecycleaware.di.InvoiceApi;
import javax.inject.Inject;

/**
 * Created by Samfisher on 09/01/2018.
 */

public class TaskDataStoreFactory {

  private InvoiceApi invoiceApi;

  @Inject
  public TaskDataStoreFactory(InvoiceApi invoiceApi) {
    this.invoiceApi = invoiceApi;
  }

  public TaskLocalDataStore<RealmTask> createLocalStore() {
    return new LocalTaskDataStore();
  }

  public TaskRemoteDataStore<Task> create() {
    return new RemoteTaskDataStore(invoiceApi);
  }
}
