package com.morgane.poidsplume.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.fragments.BonesHistoryFragment;
import com.morgane.poidsplume.fragments.ChartsFragment;
import com.morgane.poidsplume.fragments.FatHistoryFragment;
import com.morgane.poidsplume.fragments.MuscleHistoryFragment;
import com.morgane.poidsplume.fragments.WaterHistoryFragment;
import com.morgane.poidsplume.fragments.WeightHistoryFragment;

/**
 * Main Activity.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        }

        if (fragment != null) {
            // Empty the back stack before changing the fragment, to keep only the home page
            getSupportFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
