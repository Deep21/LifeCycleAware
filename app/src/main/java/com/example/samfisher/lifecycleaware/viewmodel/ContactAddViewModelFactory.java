package com.example.samfisher.lifecycleaware.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import javax.inject.Inject;

/**
 * Created by Samfisher on 27/11/2017.
 */

public class ContactAddViewModelFactory implements ViewModelProvider.Factory {

    private AddTaskViewModel myViewModel;

    @Inject
    public ContactAddViewModelFactory(AddTaskViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) myViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
