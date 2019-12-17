package com.nurflower.test.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.nurflower.test.utils.Base;
import com.nurflower.test.database.entity.Currency;
import com.nurflower.test.repositories.CurrencyRepository;
import java.util.List;
import javax.inject.Inject;

public class CurrencyViewModel extends ViewModel {

    public MutableLiveData<Base> baseMutableLiveData = new MutableLiveData<>();
    private CurrencyRepository currencyRepository;
    private LiveData<List<Currency>> currency;


    @Inject
    public CurrencyViewModel(CurrencyRepository currencyRepo) {
        this.currencyRepository = currencyRepo;
    }

    public void init(){
        baseMutableLiveData.observeForever(base -> currency = currencyRepository.getCurrency(base.getBase(), base.getInput()));
    }

    public LiveData<List<Currency>> getCurrency() { return this.currency; }
}
