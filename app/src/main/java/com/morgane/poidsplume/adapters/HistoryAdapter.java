package com.morgane.poidsplume.adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.fragments.RemoveDataDialogFragment;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

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
    private FragmentActivity mContext;

    /**
     * The list of items to display.
     */
    private List<DatedValue> mItems;

    /**
     * The results range of the history.
     */
    private ResultsRange mResultsRange;

    /**
     * Constructor of the class.
     * @param datedValueList The items to display.
     * @param context The context of the application.
     */
    public HistoryAdapter(List<DatedValue> datedValueList, FragmentActivity context, ResultsRange resultsRange) {
        mContext = context;
        mItems = datedValueList;
        mResultsRange = resultsRange;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        TextView date = (TextView) layout.findViewById(R.id.text_view_history_date);
        TextView value = (TextView) layout.findViewById(R.id.text_view_history_value);
        ImageView result = (ImageView) layout.findViewById(R.id.text_view_history_result);

        return new ViewHolder(layout, date, value, result);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DatedValue datedValue = mItems.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault());
        String formattedDate = simpleDateFormat.format(new Date(datedValue.getDate()));
        holder.mDate.setText(formattedDate);
        holder.mValue.setText(mContext.getString(datedValue.getUnit(), datedValue.getValue()));

        // If there is no results range for the current data, make the view disappear
        if (mResultsRange == null) {
            holder.mResult.setVisibility(View.GONE);

        } else {
            if ((mResultsRange.getHighRangeMax() != null && datedValue.getValue() < mResultsRange.getHighRangeMax())
                || (mResultsRange.getHighRangeMin() != null && datedValue.getValue() > mResultsRange.getHighRangeMin())){
                // If the max high range is not null, it means a high value is a value lower than this value
                // If the min high range is not null, it means a high value is a value higher than this value
                holder.mResult.setImageResource(R.drawable.ic_value_high);

            } else if ((mResultsRange.getBadRangeMax() != null && datedValue.getValue() < mResultsRange.getBadRangeMax())
                    || (mResultsRange.getBadRangeMin() != null && datedValue.getValue() > mResultsRange.getBadRangeMin())) {
                // If the max bad range is not null, it means a bad value is a value lower than this value
                // If the min bad range is not null, it means a bad value is a value higher than this value
                holder.mResult.setImageResource(R.drawable.ic_value_bad);

            } else if (datedValue.getValue() >= mResultsRange.getGoodRangeMin() && datedValue.getValue() <= mResultsRange.getGoodRangeMax()) {
                // If the value is in the good range
                holder.mResult.setImageResource(R.drawable.ic_value_good_normal);

            } else if (datedValue.getValue() >= mResultsRange.getMediumRangeMin() && datedValue.getValue() <= mResultsRange.getMediumRangeMax()) {
                // If the value is in the medium range
                holder.mResult.setImageResource(R.drawable.ic_value_medium);
            }
        }

        if (position % 2 == 0) {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.evenTableLine));
        } else {
            holder.mLayout.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
        }

        holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Display a dialog to confirm the removal
                RemoveDataDialogFragment removeDataDialogFragment = RemoveDataDialogFragment.newInstance(datedValue.getId(), HistoryAdapter.this, position);
                removeDataDialogFragment.show(mContext.getSupportFragmentManager(), RemoveDataDialogFragment.class.toString());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Delete an item and refresh the view.
     * @param position The position of the item to remove.
     */
    public void remove(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mLayout;
        public TextView mDate;
        public TextView mValue;
        public ImageView mResult;

        public ViewHolder(View layout, TextView date, TextView value, ImageView result) {
            super(layout);
            mLayout = layout;
            mDate = date;
            mValue = value;
            mResult = result;
        }
    }
}
