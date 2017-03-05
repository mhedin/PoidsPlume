package com.morgane.poidsplume.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.DatedValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter of the history, to display all the data in a table.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    /**
     * The context of the application.
     */
    private Context mContext;

    /**
     * The list of items to display.
     */
    private List<DatedValue> mItems;

    /**
     * Constructor of the class.
     * @param datedValueList The items to display.
     * @param context The context of the application.
     */
    public HistoryAdapter(List<DatedValue> datedValueList, Context context) {
        mContext = context;
        mItems = datedValueList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        TextView date = (TextView) layout.findViewById(R.id.text_view_history_date);
        TextView value = (TextView) layout.findViewById(R.id.text_view_history_value);

        return new ViewHolder(layout, date, value);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault());
        String formattedDate = simpleDateFormat.format(new Date(mItems.get(position).getDate()));
        holder.mDate.setText(formattedDate);
        holder.mValue.setText(mItems.get(position).getValue());

        if (position % 2 == 0) {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.evenTableLine));
        } else {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mLayout;
        public TextView mDate;
        public TextView mValue;

        public ViewHolder(View layout, TextView date, TextView value) {
            super(layout);
            mLayout = layout;
            mDate = date;
            mValue = value;
        }
    }
}
