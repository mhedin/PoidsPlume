package com.morgane.poidsplume.fragments;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

import java.util.List;

/**
 * The history of weight results.
 */
public class WeightHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.getAllWeights();
    }

    @Override
    protected int getTitleStringResource() {
        return R.string.history_title_weight;
    }

    @Override
    protected int getValueTypeStringResource() {
        return R.string.history_weight_table;
    }

    @Override
    protected ResultsRange getResultsRange() {
        return null;
    }
}
