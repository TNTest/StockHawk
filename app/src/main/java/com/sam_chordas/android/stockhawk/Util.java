package com.sam_chordas.android.stockhawk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * Created by TNT on 2016/10/11.
 */

public class Util {

    public static String getChartUrl(String symbol) {
        if (TextUtils.isEmpty(symbol))
            return null;
        else {
            return "http://chart.finance.yahoo.com/z?s=" + symbol + "&t=7d&l=on&z=s&p=m30,m100&q=l";
        }
    }

    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return true if the network is available
     */
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
