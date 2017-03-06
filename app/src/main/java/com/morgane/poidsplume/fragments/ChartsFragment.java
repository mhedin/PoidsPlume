package com.morgane.poidsplume.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.activities.AddDataActivity;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class presents the history into charts.
 */
public class ChartsFragment extends Fragment implements View.OnClickListener {

    /**
     * Constant used to recognize the request of start add data activity.
     */
    private static final int REQUEST_CODE_ADD_DATA = 100;

    private static final int COLOR_GREEN = 0xFF019222;

    private static final int COLOR_BLUE = 0xFF0192C9;

    private static final int COLOR_MAGENTA = 0xFFD3288A;

    private static final int COLOR_ORANGE = 0xFFFF5B00;

    /**
     * The linear layout containing the body rates chart.
     */
    private LinearLayout mRateChartLinearLayout;

    /**
     * The linear layout containing the weight chart.
     */
    private LinearLayout mWeightChartLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_charts, container, false);

        getActivity().setTitle(R.string.app_name);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mRateChartLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_chart_body_rates);
        refreshRatesChart();

        mWeightChartLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_chart_weight);
        refreshWeightChart();

        return view;
    }

    /**
     * Refresh the chart displaying the body rates history.
     */
    private void refreshRatesChart() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        // Bone mass
        XYSeries bonesSeries = new XYSeries(getString(R.string.add_data_bones));
        List<DatedValue> bonesList = BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), data.getBones(), -1))
                .collect(Collectors.toList());
        for (DatedValue datedValue : bonesList) {
            bonesSeries.add(datedValue.getDate(), datedValue.getValue());
        }
        dataset.addSeries(bonesSeries);
        XYSeriesRenderer bonesRenderer = new XYSeriesRenderer();
        bonesRenderer.setColor(COLOR_GREEN);
        setDefaultSeriesRendererValues(bonesRenderer);
        renderer.addSeriesRenderer(bonesRenderer);

        // Body fat
        XYSeries fatMassSeries = new XYSeries(getString(R.string.add_data_fat_mass));
        List<DatedValue> fatMassList = BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), data.getFat(), -1))
                .collect(Collectors.toList());
        for (DatedValue datedValue : fatMassList) {
            fatMassSeries.add(datedValue.getDate(), datedValue.getValue());
        }
        dataset.addSeries(fatMassSeries);
        XYSeriesRenderer fatMassRenderer = new XYSeriesRenderer();
        fatMassRenderer.setColor(COLOR_ORANGE);
        setDefaultSeriesRendererValues(fatMassRenderer);
        renderer.addSeriesRenderer(fatMassRenderer);

        // Muscular mass
        XYSeries muscleSeries = new XYSeries(getString(R.string.add_data_muscular_mass));
        List<DatedValue> muscleMassList = BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), data.getMuscle(), -1))
                .collect(Collectors.toList());
        for (DatedValue datedValue : muscleMassList) {
            muscleSeries.add(datedValue.getDate(), datedValue.getValue());
        }
        dataset.addSeries(muscleSeries);
        XYSeriesRenderer muscleMassRenderer = new XYSeriesRenderer();
        muscleMassRenderer.setColor(COLOR_MAGENTA);
        setDefaultSeriesRendererValues(muscleMassRenderer);
        renderer.addSeriesRenderer(muscleMassRenderer);

        // Water mass
        XYSeries waterSeries = new XYSeries(getString(R.string.add_data_water));
        List<DatedValue> waterList = BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), data.getWater(), -1))
                .collect(Collectors.toList());
        for (DatedValue datedValue : waterList) {
            waterSeries.add(datedValue.getDate(), datedValue.getValue());
        }
        dataset.addSeries(waterSeries);

        XYSeriesRenderer waterRenderer = new XYSeriesRenderer();
        waterRenderer.setColor(COLOR_BLUE);
        setDefaultSeriesRendererValues(waterRenderer);
        renderer.addSeriesRenderer(waterRenderer);

        // Global view
        renderer.setYAxisMax(70);
        renderer.setYAxisMin(0);
        renderer.setChartTitle(getString(R.string.chart_rate_label));
        setDefaultMultipleSeriesRendererValues(renderer);
        GraphicalView chartView = ChartFactory.
                getTimeChartView(getActivity(), dataset, renderer, "dd/MM/yy");
        mRateChartLinearLayout.removeAllViews();
        mRateChartLinearLayout.addView(chartView);
    }

    /**
     * Refresh the chart displaying the weight history.
     */
    private void refreshWeightChart() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        XYSeries weightSeries = new XYSeries(getString(R.string.add_data_weight));
        List<DatedValue> weightList = BodyData.listAll(BodyData.class).stream()
                .map(data -> new DatedValue(data.getMeasureDate(), data.getWeight(), -1))
                .collect(Collectors.toList());
        for (DatedValue datedValue : weightList) {
            weightSeries.add(datedValue.getDate(), datedValue.getValue());
        }
        dataset.addSeries(weightSeries);

        XYSeriesRenderer weightRenderer = new XYSeriesRenderer();
        weightRenderer.setColor(COLOR_BLUE);
        setDefaultSeriesRendererValues(weightRenderer);
        renderer.addSeriesRenderer(weightRenderer);

        renderer.setYAxisMax(60);
        renderer.setYAxisMin(50);
        renderer.setChartTitle(getString(R.string.chart_weight_label));
        setDefaultMultipleSeriesRendererValues(renderer);
        GraphicalView chartView = ChartFactory.
                getTimeChartView(getActivity(), dataset, renderer, "dd/MM/yy");
        mWeightChartLinearLayout.removeAllViews();
        mWeightChartLinearLayout.addView(chartView);
    }

    /**
     * Set the default values to the renderer.
     * @param renderer The renderer to update.
     */
    private void setDefaultSeriesRendererValues(XYSeriesRenderer renderer) {
        renderer.setLineWidth(7);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(1);
    }

    /**
     * Set the default values to the renderer.
     * @param renderer The renderer to update.
     */
    private void setDefaultMultipleSeriesRendererValues(XYMultipleSeriesRenderer renderer) {
        // We want to avoid black border transparent margins
        renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        renderer.setPanEnabled(false, false);
        renderer.setShowGrid(true);
        renderer.setLabelsColor(R.color.black);
        renderer.setAxesColor(R.color.black);
        renderer.setXAxisColor(R.color.black);
        renderer.setYAxisColor(R.color.black);
        renderer.setXLabelsColor(R.color.black);
        renderer.setYLabelsColor(0, R.color.black);
        renderer.setLegendTextSize(25);
        renderer.setAxisTitleTextSize(30);
        renderer.setLabelsTextSize(25);
        renderer.setYLabelsPadding(15);
        renderer.setFitLegend(true);
        renderer.setChartTitleTextSize(40);
        renderer.setMargins(new int[] { 50, 50, 25, 22 });
        renderer.setZoomEnabled(false, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_DATA && resultCode == Activity.RESULT_OK) {
            // Refresh the charts
            refreshRatesChart();
            refreshWeightChart();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent dataActivity = new Intent(getActivity(), AddDataActivity.class);
                startActivityForResult(dataActivity, REQUEST_CODE_ADD_DATA);
                break;
        }
    }
}
