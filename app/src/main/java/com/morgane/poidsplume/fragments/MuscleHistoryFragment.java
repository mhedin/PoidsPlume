package com.morgane.poidsplume.fragments;

import android.preference.PreferenceManager;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

import java.util.List;

/**
 * The history of muscular mass results.
 */
public class MuscleHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.getAllMuscularMass();
    }

    @Override
    protected int getTitleStringResource() {
        return R.string.history_title_muscle;
    }

    @Override
    protected int getValueTypeStringResource() {
        return R.string.history_value_type_rate;
    }

    @Override
    protected ResultsRange getResultsRange() {
        return ResultsRange.getMuscleResultsRange(PreferenceManager.getDefaultSharedPreferences(getActivity()));
    }
}
