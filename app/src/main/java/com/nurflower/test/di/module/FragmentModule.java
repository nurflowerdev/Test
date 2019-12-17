package com.nurflower.test.di.module;

import com.nurflower.test.fragments.CurrencyListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract CurrencyListFragment contributeCurrencyListFragment();
}

