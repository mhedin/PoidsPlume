package com.morgane.poidsplume.fragments;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The history of weight results.
 */
public class WeightHistoryFragment extends HistoryFragment {

    @Override
    protected List<DatedValue> getData() {
        return BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), String.valueOf(data.getWeight())))
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
}
