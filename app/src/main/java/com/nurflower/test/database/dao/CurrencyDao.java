package com.nurflower.test.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.nurflower.test.database.entity.Currency;
import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Currency> currencyList);

    @Query("DELETE FROM ST_CURRENCY_RATE")
    void deleteAll();

    @Query("SELECT id, currencyName, currencyDesc, currencyCode, currencyRate AS currencyRate FROM ST_CURRENCY_RATE")
    LiveData<List<Currency>> load();


}
