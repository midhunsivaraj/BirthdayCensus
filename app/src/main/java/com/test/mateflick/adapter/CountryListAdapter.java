package com.test.mateflick.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.mateflick.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Xtron005 on 01-07-2016.
 */
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {


    private Context mContext;
    private ArrayList<String> mLocales;
    private ISelectCountry mISelectCountry;

    public CountryListAdapter(Context context) {
        mContext = context;
        mLocales = new ArrayList<>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale l : locales) {
            if (l.getDisplayCountry().trim().length() > 0 && !mLocales.contains(l.getDisplayCountry()))
                mLocales.add(l.getDisplayCountry());
        }
        Collections.sort(mLocales);
    }

    public void setmISelectCountry(ISelectCountry mISelectCountry) {
        this.mISelectCountry = mISelectCountry;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.country_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.mTextView.setText(mLocales.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocales.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;


        public CountryViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.lblCountryName);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mISelectCountry.setSelectedCountry(mLocales.get(getAdapterPosition()));
        }
    }

    public interface ISelectCountry {
        void setSelectedCountry(String country);
    }

}
