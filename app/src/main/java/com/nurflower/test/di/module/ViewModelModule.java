package com.nurflower.test.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nurflower.test.di.key.ViewModelKey;
import com.nurflower.test.viewmodels.CurrencyViewModel;
import com.nurflower.test.viewmodels.FactoryViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel.class)
    abstract ViewModel bindCurrencyViewModel(CurrencyViewModel currencyViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
