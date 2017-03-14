package com.morgane.poidsplume.fragments;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

import java.util.List;

/**
 * The history of body fat results.
 */
public class FatHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.getAllBodyFat();
    }

    @Override
    protected int getTitleStringResource() {
        return R.string.history_title_fat;
    }

    @Override
    protected int getValueTypeStringResource() {
        return R.string.history_value_type_rate;
    }

    @Override
    protected ResultsRange getResultsRange() {
        return ResultsRange.getFatResultsRange();
    }
}
