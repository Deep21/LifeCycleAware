package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.Contact;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Samfisher on 24/11/2017.
 */
interface InvoiceApi {

    @GET("contacts")
    Observable<List<Contact>> getListContacts();
}
