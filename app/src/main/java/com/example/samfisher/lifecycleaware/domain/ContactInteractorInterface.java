package com.example.samfisher.lifecycleaware.domain;

import com.example.samfisher.lifecycleaware.Contact;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Samfisher on 11/01/2018.
 */

public interface ContactInteractorInterface<T> {

  Observable<List<Contact>> getList();

}
