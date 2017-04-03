package com.morgane.poidsplume.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.fragments.BonesHistoryFragment;
import com.morgane.poidsplume.fragments.ChartsFragment;
import com.morgane.poidsplume.fragments.FatHistoryFragment;
import com.morgane.poidsplume.fragments.MuscleHistoryFragment;
import com.morgane.poidsplume.fragments.SettingsFragment;
import com.morgane.poidsplume.fragments.WaterHistoryFragment;
import com.morgane.poidsplume.fragments.WeightHistoryFragment;
import com.morgane.poidsplume.models.ResultsRange;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    /**
     * Used to store in the preferences the chart to display, currently and at the next launch.
     */
    public static final String PREFERENCE_CHART_DISPLAYED = "preferenceChartDisplayed";

    /**
     * Used to identify the history charts in the preferences.
     */
    public static final int CHART_HISTORY = 2;

    /**
     * Used to identify the actual chart in the preferences.
     */
    public static final int CHART_ACTUAL = -2;

    /**
     * Button in the toolbar allowing to switch from one chart to another.
     */
    private ImageButton mSwitchChartsButton;

    /**
     * The navigation view.
     */
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new ChartsFragment());
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mSwitchChartsButton = (ImageButton) findViewById(R.id.toolbar_switch_charts);
        mSwitchChartsButton.setOnClickListener(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getInt(PREFERENCE_CHART_DISPLAYED, CHART_HISTORY) == CHART_HISTORY) {
            mSwitchChartsButton.setImageResource(R.drawable.ic_toolbar_chart_actual);
        } else {
            mSwitchChartsButton.setImageResource(R.drawable.ic_toolbar_chart_history);
        }

        //TODO: Remove this code later
        if (ResultsRange.getFatResultsRange(PreferenceManager.getDefaultSharedPreferences(this)) == null) {
            ResultsRange fatResultsRange = new ResultsRange(ResultsRange.RESULT_FAT, true, 0, 100, null, 18.0, 18.0, 23.0, 23.1, 28.0, 28.1, null);
            ResultsRange muscleResultsRange = new ResultsRange(ResultsRange.RESULT_MUSCLE, true, 0, 100, 39.0, null, 34.0, 39.0, null, null, null, 34.0);
            ResultsRange waterResultsRange = new ResultsRange(ResultsRange.RESULT_WATER, true, 0, 100, 60.0, null, 45.0, 60.0, null, null, null, 45.0);

            fatResultsRange.save();
            muscleResultsRange.save();
            waterResultsRange.save();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            // The back pressed redirects everytime on the charts fragment, so show the button to change the chart displayed and select it in the navigation menu
            mSwitchChartsButton.setVisibility(View.VISIBLE);
            mNavigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id) {
            case R.id.nav_home:
                getSupportFragmentManager().popBackStack();

                // Show the button to change the chart displayed
                mSwitchChartsButton.setVisibility(View.VISIBLE);
                break;

            case R.id.nav_history_weight:
                fragment = new WeightHistoryFragment();
                break;

            case R.id.nav_history_bones:
                fragment = new BonesHistoryFragment();
                break;

            case R.id.nav_history_fat:
                fragment = new FatHistoryFragment();
                break;

            case R.id.nav_history_muscle:
                fragment = new MuscleHistoryFragment();
                break;

            case R.id.nav_history_water:
                fragment = new WaterHistoryFragment();
                break;

            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
        }

        if (fragment != null) {
            // Empty the back stack before changing the fragment, to keep only the home page
            getSupportFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();

            // Hide the button to change the chart displayed
            mSwitchChartsButton.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_switch_charts:
                // When the user changes the current chart, register it as its new preference, then switch the view
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                int currentChart = sharedPreferences.getInt(PREFERENCE_CHART_DISPLAYED, CHART_HISTORY);

                sharedPreferences.edit().putInt(PREFERENCE_CHART_DISPLAYED, -currentChart).apply();

                mSwitchChartsButton.setImageResource(currentChart == CHART_ACTUAL ? R.drawable.ic_toolbar_chart_actual : R.drawable.ic_toolbar_chart_history);

                // Refresh the chart and update the chart to display value
                ((ChartsFragment)getSupportFragmentManager().findFragmentById(R.id.fragment)).refreshCharts(-currentChart, false);

                break;
        }
    }
}
