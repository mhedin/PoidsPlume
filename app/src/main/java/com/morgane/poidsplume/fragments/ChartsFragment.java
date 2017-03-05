package com.morgane.poidsplume.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.activities.AddDataActivity;
import com.morgane.poidsplume.models.BodyData;

import java.util.List;

/**
 * This class presents the history into charts.
 */
public class ChartsFragment extends Fragment implements View.OnClickListener {

    /**
     * Constant used to recognize the request of start add data activity.
     */
    private static final int REQUEST_CODE_ADD_DATA = 100;

    /**
     * The text view containing the content of the database.
     */
    private TextView mBodyDataTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_charts, container, false);

        getActivity().setTitle(R.string.app_name);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        List<BodyData> bodyDataList = BodyData.listAll(BodyData.class);
        mBodyDataTextView = (TextView) view.findViewById(R.id.main_text_view);
        mBodyDataTextView.setText(bodyDataList.toString());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_DATA && resultCode == Activity.RESULT_OK) {
            // Refresh the text, before the future refresh of the graph
            List<BodyData> bodyDataList = BodyData.listAll(BodyData.class);
            mBodyDataTextView.setText(bodyDataList.toString());
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
