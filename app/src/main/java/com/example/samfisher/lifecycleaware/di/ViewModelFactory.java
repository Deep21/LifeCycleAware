package com.example.samfisher.lifecycleaware.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.samfisher.lifecycleaware.MyViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Samfisher on 27/11/2017.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private MyViewModel myViewModel;

    @Inject
    public ViewModelFactory(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MyViewModel.class)) {
            return (T) myViewModel;
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}
