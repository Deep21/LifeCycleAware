package com.example.samfisher.lifecycleaware.di;

import com.example.samfisher.lifecycleaware.TaskEntity;
import com.example.samfisher.lifecycleaware.view.viewholder.DefaultTaskHolderFactory;
import com.example.samfisher.lifecycleaware.view.viewholder.ViewHolderFactoryInterface;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

/**
 * Created by Samfisher on 30/01/2018.
 */

@Module
public abstract class CreditDashboardModule {
  @Binds
  @IntoMap
  @IntKey(TaskEntity.TYPE_NORMAL)
  abstract ViewHolderFactoryInterface provideInRepaymentCardViewHolderFactory(DefaultTaskHolderFactory factory);
}
