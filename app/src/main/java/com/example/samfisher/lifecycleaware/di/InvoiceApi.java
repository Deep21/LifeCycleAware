package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.Contact;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Samfisher on 24/11/2017.
 */
interface InvoiceApi {

  @GET("contacts")
  Observable<List<Contact>> getListContacts();

  @GET("contact/{id}")
  Observable<Contact> getContact(@Path("id") int id);
}
