package com.morgane.poidsplume.fragments;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The history of bone mass results.
 */
public class BonesHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.listAll(BodyData.class).stream()
                .sorted((data1, data2) -> Long.compare(data2.getMeasureDate(), data1.getMeasureDate()))
                .map(data -> new DatedValue(data.getMeasureDate(), data.getBones(), R.string.history_value_unit_percentage))
                .collect(Collectors.toList());
    }

    @Override
    protected int getTitleStringResource() {
        return R.string.history_title_bones;
    }

    @Override
    protected int getValueTypeStringResource() {
        return R.string.history_value_type_rate;
    }

    @Override
    protected ResultsRange getResultsRange() {
        return null;
    }
}
