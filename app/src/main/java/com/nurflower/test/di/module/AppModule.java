package com.nurflower.test.di.module;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nurflower.test.api.CurrencyWebService;
import com.nurflower.test.database.MyDatabase;
import com.nurflower.test.database.dao.CurrencyDao;
import com.nurflower.test.repositories.CurrencyRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---
    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, "currency.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    CurrencyDao provideCurrencyDao(MyDatabase database) { return database.currencyDao(); }

    // --- REPOSITORY INJECTION ---
    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    CurrencyRepository provideCurrencyRepository(CurrencyWebService webservice, CurrencyDao currencyDao, Executor executor) {
        return new CurrencyRepository(webservice, currencyDao, executor);
    }

    // --- NETWORK INJECTION ---

    private static String BASE_URL = "https://revolut.duckdns.org/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    CurrencyWebService provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(CurrencyWebService.class);
    }
}
