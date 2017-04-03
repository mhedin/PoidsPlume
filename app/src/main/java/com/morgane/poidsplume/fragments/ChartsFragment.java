package com.morgane.poidsplume.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.activities.AddDataActivity;
import com.morgane.poidsplume.activities.MainActivity;
import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.models.DatedValue;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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

    private static final int COLOR_WHITE = 0xFFFFFFFF;

    /**
     * The linear layout containing the body rates chart.
     */
    private LinearLayout mRateChartLinearLayout;

    /**
     * The linear layout containing the weight chart.
     */
    private LinearLayout mWeightChartLinearLayout;

    /**
     * Indicating which chart shall be displayed : the timeline or the actual values.
     */
    private int mChartToDisplay;

    /**
     * Graph displaying the rates in a line chart.
     */
    private GraphicalView mRatesLineChartView;

    /**
     * Graph displaying the rates in a pie chart.
     */
    private GraphicalView mRatesPieChartView;

    /**
     * Graph displaying the weight in a line chart.
     */
    private GraphicalView mWeightLineChartView;

    /**
     * Dated value of the current weight.
     */
    private DatedValue mCurrentWeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_charts, container, false);

        getActivity().setTitle(R.string.app_name);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        mRateChartLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_chart_body_rates);
        mWeightChartLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_chart_weight);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mChartToDisplay = sharedPreferences.getInt(MainActivity.PREFERENCE_CHART_DISPLAYED, MainActivity.CHART_HISTORY);

        refreshCharts(mChartToDisplay, true);

        return view;
    }

    /**
     * Refresh the charts, depending on whether the chart to display is the history or the actual values.
     * @param chartToDisplay The new value of the chart to display, if it must be changed.
     * @param refreshContent Flag indicating if the content of the charts must be refreshed, or if the current values must be displayed.
     */
    public void refreshCharts(int chartToDisplay, boolean refreshContent) {
        mChartToDisplay = chartToDisplay;

        // If the content must be refreshed, force the current values to null
        if (refreshContent) {
            mRatesLineChartView = null;
            mRatesPieChartView = null;
            mWeightLineChartView = null;
            mCurrentWeight = null;
        }

        if (mChartToDisplay == MainActivity.CHART_HISTORY) {
            refreshRatesLineChart();
            refreshWeightLineChart();
        } else {
            refreshRatesPieChart();
            displayWeightActualValue();
        }
    }

    /**
     * Refresh the chart displaying the body rates history.
     */
    private void refreshRatesLineChart() {
        if (mRatesLineChartView == null) {
            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
            XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

            // Bone mass
            XYSeries bonesSeries = new XYSeries(getString(R.string.add_data_bones));
            List<DatedValue> bonesList = BodyData.getAllBoneMass();
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
            List<DatedValue> fatMassList = BodyData.getAllBodyFat();
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
            List<DatedValue> muscleMassList = BodyData.getAllMuscularMass();
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
            List<DatedValue> waterList = BodyData.getAllWaterMass();
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
            renderer.setChartTitle(getString(R.string.line_chart_rate_label));
            setDefaultMultipleSeriesRendererValues(renderer);
            mRatesLineChartView = ChartFactory.getTimeChartView(getActivity(), dataset, renderer, "dd/MM/yy");
        }

        mRateChartLinearLayout.removeAllViews();
        mRateChartLinearLayout.addView(mRatesLineChartView);
        ((LinearLayout.LayoutParams)mRateChartLinearLayout.getLayoutParams()).weight = 1;
    }

    /**
     * Refresh the chart displaying the weight history.
     */
    private void refreshWeightLineChart() {
        if (mWeightLineChartView == null) {
            XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
            XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

            XYSeries weightSeries = new XYSeries(getString(R.string.add_data_weight));
            List<DatedValue> weightList = BodyData.getAllWeights();
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
            renderer.setChartTitle(getString(R.string.line_chart_weight_label));
            setDefaultMultipleSeriesRendererValues(renderer);
            mWeightLineChartView = ChartFactory.getTimeChartView(getActivity(), dataset, renderer, "dd/MM/yy");
        }

        mWeightChartLinearLayout.removeAllViews();
        mWeightChartLinearLayout.addView(mWeightLineChartView);
        ((LinearLayout.LayoutParams)mWeightChartLinearLayout.getLayoutParams()).weight = 1;
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
        renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        renderer.setPanEnabled(false, false);
        renderer.setShowGrid(true);
        renderer.setXLabelsColor(R.color.black);
        renderer.setYLabelsColor(0, R.color.black);
        renderer.setAxisTitleTextSize(30);
        renderer.setYLabelsPadding(15);
        renderer.setZoomEnabled(false, false);
        renderer.setLabelsTextSize(25);
        renderer.setLegendTextSize(25);
        renderer.setLabelsColor(R.color.black);
        renderer.setAxesColor(R.color.black);
        renderer.setXAxisColor(R.color.black);
        renderer.setYAxisColor(R.color.black);
        renderer.setChartTitleTextSize(40);
        renderer.setFitLegend(true);
        renderer.setMargins(new int[] { 50, 50, 25, 22 });
    }

    /**
     * Refresh the chart displaying the actual rates values.
     */
    private void refreshRatesPieChart() {
        if (mRatesPieChartView == null) {
            CategorySeries dataset = new CategorySeries(getString(R.string.pie_chart_rate_label));
            DefaultRenderer renderer = new DefaultRenderer();

            NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.FRENCH);
            percentFormatter.setMinimumFractionDigits(2);
            percentFormatter.setMaximumFractionDigits(2);

            dataset.add(getString(R.string.add_data_water), BodyData.getLastWaterMassInPercentage());
            SimpleSeriesRenderer waterMassRenderer = new SimpleSeriesRenderer();
            waterMassRenderer.setColor(COLOR_BLUE);
            waterMassRenderer.setChartValuesFormat(percentFormatter);
            renderer.addSeriesRenderer(waterMassRenderer);

            dataset.add(getString(R.string.add_data_fat_mass), BodyData.getLastBodyFatInPercentage());
            SimpleSeriesRenderer fatMassRenderer = new SimpleSeriesRenderer();
            fatMassRenderer.setColor(COLOR_ORANGE);
            fatMassRenderer.setChartValuesFormat(percentFormatter);
            renderer.addSeriesRenderer(fatMassRenderer);

            dataset.add(getString(R.string.add_data_muscular_mass), BodyData.getLastMuscularMassInPercentage());
            SimpleSeriesRenderer muscleMassRenderer = new SimpleSeriesRenderer();
            muscleMassRenderer.setColor(COLOR_MAGENTA);
            muscleMassRenderer.setChartValuesFormat(percentFormatter);
            renderer.addSeriesRenderer(muscleMassRenderer);

            dataset.add(getString(R.string.add_data_bones), BodyData.getLastBoneMassInPercentage());
            SimpleSeriesRenderer bonesRenderer = new SimpleSeriesRenderer();
            bonesRenderer.setColor(COLOR_GREEN);
            bonesRenderer.setChartValuesFormat(percentFormatter);
            renderer.addSeriesRenderer(bonesRenderer);

            renderer.setShowLegend(true);
            renderer.setZoomEnabled(false);
            renderer.setPanEnabled(false);
            renderer.setDisplayValues(true);
            renderer.setLegendTextSize(30);
            renderer.setShowLabels(false);
            renderer.setLabelsTextSize(35);
            renderer.setLabelsColor(COLOR_WHITE);

            mRatesPieChartView = ChartFactory.getPieChartView(getActivity(), dataset, renderer);
        }

        TextView ratesLabelTextView = new TextView(getActivity());
        ratesLabelTextView.setText(R.string.pie_chart_rate_label);
        ratesLabelTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ratesLabelTextView.setTextSize(16);

        mRateChartLinearLayout.removeAllViews();
        mRateChartLinearLayout.addView(ratesLabelTextView);
        mRateChartLinearLayout.addView(mRatesPieChartView);
        ((LinearLayout.LayoutParams)mRateChartLinearLayout.getLayoutParams()).weight = 6;
    }

    /**
     * Display the actual weight value.
     */
    private void displayWeightActualValue() {
        if (mCurrentWeight == null) {
            mCurrentWeight = BodyData.getLastWeight();
        }

        TextView weightLabelTextView = new TextView(getActivity());
        weightLabelTextView.setText(R.string.weight_label);
        weightLabelTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        weightLabelTextView.setTextSize(16);

        TextView weightValueTextView = new TextView(getActivity());
        weightValueTextView.setText(getString(mCurrentWeight.getUnit(), mCurrentWeight.getValue()));
        weightValueTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        weightValueTextView.setTextColor(COLOR_BLUE);
        weightValueTextView.setTextSize(40);

        mWeightChartLinearLayout.removeAllViews();
        mWeightChartLinearLayout.addView(weightLabelTextView);
        mWeightChartLinearLayout.addView(weightValueTextView);
        ((LinearLayout.LayoutParams)mWeightChartLinearLayout.getLayoutParams()).weight = 2;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_DATA && resultCode == Activity.RESULT_OK) {
            // Refresh the charts
            refreshCharts(mChartToDisplay, true);
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
