package com.sam_chordas.android.stockhawk.rest;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.touch_helper.ItemTouchHelperAdapter;
import com.sam_chordas.android.stockhawk.touch_helper.ItemTouchHelperViewHolder;

/**
 * Created by sam_chordas on 10/6/15.
 * Credit to skyfishjy gist:
 * https://gist.github.com/skyfishjy/443b7448f59be978bc59
 * for the code structure
 */
public class QuoteCursorAdapter extends CursorRecyclerViewAdapter<QuoteCursorAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private static Context mContext;
    private static Typeface robotoLight;
    private boolean isPercent;

    public QuoteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_quote, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    @TargetApi(16)
    public void onBindViewHolder(final ViewHolder viewHolder, final Cursor cursor) {
        String symbolStr = cursor.getString(cursor.getColumnIndex("symbol"));
        viewHolder.symbol.setText(symbolStr);
        viewHolder.symbol.setContentDescription(symbolStr);
        String bidPrice = cursor.getString(cursor.getColumnIndex("bid_price"));
        viewHolder.bidPrice.setText(bidPrice);
        viewHolder.bidPrice.setContentDescription(bidPrice);
        int sdk = Build.VERSION.SDK_INT;
        if (cursor.getInt(cursor.getColumnIndex("is_up")) == 1) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            } else {
                viewHolder.change.setBackground(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_green));
            }
            viewHolder.change.setContentDescription(mContext.getString(R.string.up_description));
        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.change.setBackgroundDrawable(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            } else {
                viewHolder.change.setBackground(
                        mContext.getResources().getDrawable(R.drawable.percent_change_pill_red));
            }
            viewHolder.change.setContentDescription(mContext.getString(R.string.down_description));
        }
        if (Utils.showPercent) {
            String p_change = cursor.getString(cursor.getColumnIndex("percent_change"));
            viewHolder.change.setText(p_change);
            viewHolder.change.setContentDescription(viewHolder.change.getContentDescription() + " " + p_change);

        } else {
            String change = cursor.getString(cursor.getColumnIndex("change"));
            viewHolder.change.setText(change);
            viewHolder.change.setContentDescription(viewHolder.change.getContentDescription() + " " + change);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        Cursor c = getCursor();
        c.moveToPosition(position);
        String symbol = c.getString(c.getColumnIndex(QuoteColumns.SYMBOL));
        mContext.getContentResolver().delete(QuoteProvider.Quotes.withSymbol(symbol), null, null);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, View.OnClickListener {
        public final TextView symbol;
        public final TextView bidPrice;
        public final TextView change;

        public ViewHolder(View itemView) {
            super(itemView);
            symbol = (TextView) itemView.findViewById(R.id.stock_symbol);
            symbol.setTypeface(robotoLight);
            bidPrice = (TextView) itemView.findViewById(R.id.bid_price);
            change = (TextView) itemView.findViewById(R.id.change);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
