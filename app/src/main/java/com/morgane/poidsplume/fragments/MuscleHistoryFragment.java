package com.morgane.poidsplume.fragments;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.BodyData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The history of muscular mass results.
 */
public class MuscleHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), String.valueOf(data.getMuscle())))
                .collect(Collectors.toList());
    }

    @Override
    protected int getTitleStringResource() {
        return R.string.history_title_muscle;
    }

    @Override
    protected int getValueTypeStringResource() {
        return R.string.history_value_type_rate;
    }
}
