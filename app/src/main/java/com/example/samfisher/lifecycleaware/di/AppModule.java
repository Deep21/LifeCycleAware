package com.example.samfisher.lifecycleaware.di;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Samfisher on 04/09/2017.
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(App app) {
        return app;
    }


}
