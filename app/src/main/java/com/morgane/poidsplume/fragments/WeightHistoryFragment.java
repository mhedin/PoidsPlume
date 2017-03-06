package com.morgane.poidsplume.fragments;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;
import com.morgane.poidsplume.models.ResultsRange;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The history of weight results.
 */
public class WeightHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.listAll(BodyData.class).stream()
                .sorted((data1, data2) -> Long.compare(data2.getMeasureDate(), data1.getMeasureDate()))
                .map(data -> new DatedValue(data.getMeasureDate(), data.getWeight(), R.string.history_value_unit_kg))
                .collect(Collectors.toList());
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
