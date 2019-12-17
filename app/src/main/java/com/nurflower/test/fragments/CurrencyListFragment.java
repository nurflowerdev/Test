package com.nurflower.test.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nurflower.test.utils.Base;
import com.nurflower.test.R;
import com.nurflower.test.adapters.CurrencyAdapter;
import com.nurflower.test.database.entity.Currency;
import com.nurflower.test.viewmodels.CurrencyViewModel;

import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;


public class CurrencyListFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CurrencyViewModel viewModel;

    @BindView(R.id.currencyRv) RecyclerView currencyRv;

    private Base base;

    private CurrencyAdapter currencyAdapter;

    public CurrencyListFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_list, container, false);
        ButterKnife.bind(this, view);

        currencyRv.setHasFixedSize(true);
        currencyRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        base = new Base("EUR", BigDecimal.valueOf(100));

        currencyAdapter = new CurrencyAdapter(getActivity(),
                position -> {
                    base.setBase(currencyAdapter.getItem(position).getCurrencyName());
                    base.setInput(currencyAdapter.getItem(position).getCurrencyRate());
                    viewModel.baseMutableLiveData.setValue(base);
                },
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() > 0){
                            base.setInput(new BigDecimal(charSequence.toString()));
                            viewModel.baseMutableLiveData.setValue(base);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                }
        );

        currencyRv.setAdapter(currencyAdapter);


        return view;
    }


    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyViewModel.class);
        viewModel.baseMutableLiveData.setValue(base);
        viewModel.init();
        viewModel.getCurrency().observe(this, this::updateUI);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }


    private void updateUI(List<Currency> currencyList){
        if (currencyList != null){
            if (currencyList.size() > 0){
                currencyAdapter.setCurrencyList(currencyList);
            }
        }
    }
}
