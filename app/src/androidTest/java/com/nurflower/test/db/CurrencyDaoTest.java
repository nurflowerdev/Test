package com.nurflower.test.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.runner.AndroidJUnit4;
import com.nurflower.test.database.entity.Currency;
import com.nurflower.test.utils.LiveDataTestUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class CurrencyDaoTest extends DbTest{

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void insertAndLoad() throws InterruptedException {
        final Currency baseCurrency = new Currency();
        baseCurrency.setCurrencyName("EUR");
        baseCurrency.setCurrencyDesc(java.util.Currency.getInstance("EUR").getDisplayName());
        baseCurrency.setCurrencyCode(java.util.Currency.getInstance("EUR").getNumericCode());
        baseCurrency.setCurrencyRate(BigDecimal.valueOf(100));
        baseCurrency.setLastRefresh(new Date());
        List<Currency> currencies = new ArrayList<>();
        currencies.add(baseCurrency);
        db.currencyDao().insert(currencies);

        final Currency loaded = LiveDataTestUtil.getValue(db.currencyDao().load());
        assertThat(loaded.getCurrencyName(), is("EUR"));
    }
}
