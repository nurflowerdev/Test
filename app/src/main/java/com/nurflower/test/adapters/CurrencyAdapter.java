package com.nurflower.test.adapters;

import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.blongho.country_data.World;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nurflower.test.R;
import com.nurflower.test.callback.ItemClickListener;
import com.nurflower.test.database.entity.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Currency> currencyList;
    private Context context;
    private ItemClickListener itemClickListener;
    private TextWatcher textWatcher;


    public CurrencyAdapter(Context context, ItemClickListener itemClickListener, TextWatcher textWatcher) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.textWatcher = textWatcher;
        World.init(context);
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.currency_itemview, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        if (currencyList != null){
            holder.currencyNameTv.setText(currencyList.get(position).getCurrencyName());
            holder.currencyDescTv.setText(currencyList.get(position).getCurrencyDesc());
            Glide.with(context).load(World.getFlagOf(currencyList.get(position).getCurrencyCode())).apply(RequestOptions.circleCropTransform()).into(holder.currencyIv);
            holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(position));
            holder.currencyInputEt.removeTextChangedListener(textWatcher);
            holder.currencyInputEt.setText(String.valueOf(currencyList.get(position).getCurrencyRate()));
            if (position == 0){
                holder.currencyInputEt.addTextChangedListener(textWatcher);
                holder.currencyInputEt.setSelection(holder.currencyInputEt.getText().length());
            }
        }
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
        notifyDataSetChanged();
    }

    public Currency getItem(int position){
        return currencyList.get(position);
    }


    @Override
    public int getItemCount() {
        if (currencyList != null){
            return currencyList.size();
        }
        return 0;
    }


    class CurrencyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.currencyNameTv) TextView currencyNameTv;
        @BindView(R.id.currencyDescTv) TextView currencyDescTv;
        @BindView(R.id.currencyInputEt) EditText currencyInputEt;
        @BindView(R.id.currencyIconIv) ImageView currencyIv;

        CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
