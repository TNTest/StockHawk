package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sam_chordas.android.stockhawk.R;

public class StockDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = StockDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);
    }

}
