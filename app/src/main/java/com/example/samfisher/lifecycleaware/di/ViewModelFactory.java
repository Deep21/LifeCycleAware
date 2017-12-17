package com.example.samfisher.lifecycleaware.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.example.samfisher.lifecycleaware.ContactViewModel;
import javax.inject.Inject;

/**
 * Created by Samfisher on 27/11/2017.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private ContactViewModel myViewModel;

    @Inject
    public ViewModelFactory(ContactViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ContactViewModel.class)) {
            return (T) myViewModel;
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}
