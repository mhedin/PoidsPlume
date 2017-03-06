package com.morgane.poidsplume.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.adapters.HistoryAdapter;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

import java.util.List;

/**
 * Abstract fragment of the history. Used to get and display all the history in a table.
 */
public abstract class HistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        getActivity().setTitle(getTitleStringResource());

        TextView valueTextView = (TextView) view.findViewById(R.id.text_view_history_title_value);
        valueTextView.setText(getValueTypeStringResource());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        ResultsRange resultsRange = getResultsRange();
        // If there is no results range for the current data, make the view disappear
        if (resultsRange == null) {
            view.findViewById(R.id.text_view_history_title_result).setVisibility(View.GONE);
        }

        HistoryAdapter adapter = new HistoryAdapter(getData(), getActivity(), resultsRange);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * Method to override to register the data to display.
     * @return The data to display.
     */
    protected abstract List<DatedValue> getData();

    /**
     * Method to override to register the title to display.
     * @return The title to display.
     */
    protected abstract int getTitleStringResource();

    /**
     * Method to override to register the name of the value type column.
     * @return The name of the value type column.
     */
    protected abstract int getValueTypeStringResource();

    /**
     * Method to override to register the results range of the history.
     * @return The results range of the history.
     */
    protected abstract ResultsRange getResultsRange();
}
