package com.nurflower.test.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nurflower.test.database.converter.CurrencyConverter;
import com.nurflower.test.database.converter.DateConverter;
import com.nurflower.test.database.dao.CurrencyDao;
import com.nurflower.test.database.entity.Currency;

@Database(entities = {Currency.class}, version = 4)
@TypeConverters({DateConverter.class, CurrencyConverter.class})
public abstract class MyDatabase extends RoomDatabase {

    //singleton
    private static volatile MyDatabase INSTANCE;

    //dao
    public abstract CurrencyDao currencyDao();
}
