package com.nurflower.test.repositories;



import androidx.lifecycle.LiveData;
import com.nurflower.test.api.CurrencyResponse;
import com.nurflower.test.api.CurrencyWebService;
import com.nurflower.test.database.dao.CurrencyDao;
import com.nurflower.test.database.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
@Singleton
public class CurrencyRepository {

    private static int FRESH_TIMEOUT_IN_SECONDS = 1;

    private final CurrencyWebService webservice;
    private final CurrencyDao currencyDao;
    private final Executor executor;
    Disposable subscription;
    Observable<Long> values;


    @Inject
    public CurrencyRepository(CurrencyWebService webservice, CurrencyDao currencyDao, Executor executor) {
        this.webservice = webservice;
        this.currencyDao = currencyDao;
        this.executor = executor;
        this.values = Observable.interval(1000, TimeUnit.MILLISECONDS);
    }

    public LiveData<List<Currency>> getCurrency(String base, BigDecimal input) {
        refreshCurrency(base, input);
        return currencyDao.load(); // return a LiveData directly from the database.
    }

    private void refreshCurrency(String base, BigDecimal input) {
        if (subscription != null){subscription.dispose();}
        subscription = values.subscribe(v-> downloadAndSaveData(base, input), throwable -> {});
    }


    private void downloadAndSaveData(String base, BigDecimal input){

            webservice.getCurrencyRates(base).enqueue(new Callback<CurrencyResponse>() {
                @Override
                public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                    executor.execute(()->{
                        List<Currency> data = new ArrayList<>();

                        Currency baseCurrency = new Currency();
                        baseCurrency.setCurrencyName(response.body().getBase());
                        baseCurrency.setCurrencyDesc(java.util.Currency.getInstance(response.body().getBase()).getDisplayName());
                        baseCurrency.setCurrencyCode(java.util.Currency.getInstance(response.body().getBase()).getNumericCode());
                        baseCurrency.setCurrencyRate(input);
                        baseCurrency.setLastRefresh(new Date());
                        data.add(baseCurrency);

                        response.body().getRates().getAsJsonObject().keySet().forEach(key->{
                            Currency currency = new Currency();
                            currency.setCurrencyName(key);
                            currency.setCurrencyDesc(java.util.Currency.getInstance(key).getDisplayName());
                            currency.setCurrencyRate(response.body().getRates().getAsJsonObject().get(key).getAsBigDecimal().multiply(input).setScale(2, RoundingMode.HALF_UP));
                            currency.setCurrencyCode(java.util.Currency.getInstance(key).getNumericCode());
                            currency.setLastRefresh(new Date());
                            data.add(currency);
                        });

                        currencyDao.deleteAll();
                        currencyDao.insert(data);
                    });

                }

                @Override
                public void onFailure(Call<CurrencyResponse> call, Throwable t) {

                }
            });
    }


}
