package com.example.samfisher.lifecycleaware;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.samfisher.lifecycleaware.di.InvoiceRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Samfisher on 22/11/2017.
 */

public class MyViewModel extends ViewModel {

    private LiveData<List<Contact>> listLiveData;

    private GenericLiveData<List<Contact>> stringGenericLiveData;

    private InvoiceRepo invoiceRepo;


    @Inject
    public MyViewModel(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    protected void onCleared() {
        Timber.d("onCleared");
        super.onCleared();
    }

    public GenericLiveData<List<Contact>> getName() {
        if (null == stringGenericLiveData) {
            Log.d("MyViewModel", "getListContact:" + stringGenericLiveData);
            //long running task
            stringGenericLiveData = new GenericLiveData<>();
            stringGenericLiveData.get(invoiceRepo.getContact());
            return stringGenericLiveData;
        }
        Log.d("MyViewModel", "getListContact: return" + stringGenericLiveData.getValue());
        return stringGenericLiveData;
    }

    public LiveData<List<Contact>> getListContact() {
        if (null == listLiveData) {
            listLiveData = new MutableLiveData<>();
            Log.d("MyViewModel", "getListContact: " + listLiveData);
            Flowable<List<Contact>> contactFlowable = invoiceRepo.getContact()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable(BackpressureStrategy.BUFFER);
            listLiveData = LiveDataReactiveStreams.fromPublisher(contactFlowable);
            return listLiveData;
        }
        Log.d("MyViewModel", "getListContact: " + listLiveData.getValue());
        return listLiveData;
    }

}
