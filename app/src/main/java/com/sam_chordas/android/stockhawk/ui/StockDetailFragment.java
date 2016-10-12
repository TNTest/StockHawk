package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.Util;

/**
 */
public class StockDetailFragment extends Fragment {

    private static final String LOG_TAG = StockDetailFragment.class.getSimpleName();

    public StockDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        String symbolStr = getActivity().getIntent().getStringExtra(getString(R.string.extra_symbol_tag));
        Log.d(LOG_TAG, symbolStr);
        String chartUrl = Util.getChartUrl(symbolStr);
        ImageView iv = (ImageView) view.findViewById(R.id.stock_line_chart_content);
        Glide.with(getActivity()).load(chartUrl).into(iv);
        iv.setContentDescription(getString(R.string.chart_description));
        TextView tv = (TextView) view.findViewById(R.id.stock_line_chart_title);
        tv.setText(symbolStr);
        tv.setContentDescription(symbolStr);
        return view;
    }

}
