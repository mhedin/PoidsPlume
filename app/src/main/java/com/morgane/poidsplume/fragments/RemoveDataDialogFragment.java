package com.morgane.poidsplume.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.adapters.HistoryAdapter;
import com.morgane.poidsplume.models.BodyData;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Dialog fragment allowing the user to remove a data from the database.
 */
public class RemoveDataDialogFragment extends DialogFragment {

    /**
     * Used to identify the body data Id argument.
     */
    private static final String ARGUMENT_BODY_DATA_ID = "bodyDataId";

    /**
     * Used to identify the position of the body data in the adapter list argument.
     */
    private static final String ARGUMENT_BODY_DATA_POSITION = "bodyDataPosition";

    /**
     * Instance of the adapter which need to be refreshed after the deletion.
     */
    private HistoryAdapter mAdapterToRefresh;

    /**
     * Method used to create a new instance of the fragment, with the necessary arguments.
     * @param bodyDataId The id of the body data in the database.
     * @param adapter The adapter calling the dialog.
     * @param position The position of the body data in the list of the adapter.
     * @return A new instance of RemoveDataDialogFragment.
     */
    public static RemoveDataDialogFragment newInstance(long bodyDataId, HistoryAdapter adapter, int position) {
        RemoveDataDialogFragment fragment = new RemoveDataDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(ARGUMENT_BODY_DATA_ID, bodyDataId);
        arguments.putInt(ARGUMENT_BODY_DATA_POSITION, position);
        fragment.setArguments(arguments);
        fragment.mAdapterToRefresh = adapter;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the data to remove
        final BodyData bodyData = BodyData.findById(BodyData.class, getArguments().getLong(ARGUMENT_BODY_DATA_ID));

        // Build the message to display
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.data_date)).append(formatDate.format(bodyData.getMeasureDate())).append("\n")
                .append(getString(R.string.data_weight)).append(getString(R.string.history_value_unit_kg, String.valueOf(bodyData.getWeight()))).append("\n")
                .append(getString(R.string.data_body_fat)).append(getString(R.string.history_value_unit_percentage, String.valueOf(bodyData.getFat()))).append("\n")
                .append(getString(R.string.data_muscular_mass)).append(getString(R.string.history_value_unit_percentage, String.valueOf(bodyData.getMuscle()))).append("\n")
                .append(getString(R.string.data_bone_mass)).append(getString(R.string.history_value_unit_percentage, String.valueOf(bodyData.getBones()))).append("\n")
                .append(getString(R.string.data_water_mass)).append(getString(R.string.history_value_unit_percentage, String.valueOf(bodyData.getWater())));

        return new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.history_remove_data_information, stringBuilder.toString()))
                .setPositiveButton(R.string.history_remove_data_validate, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the data from the database and refresh the recycler view
                        BodyData.delete(bodyData);
                        mAdapterToRefresh.remove(getArguments().getInt(ARGUMENT_BODY_DATA_POSITION));
                    }
                })
                .setNegativeButton(R.string.history_remove_data_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do nothing
                    }
                }).create();
    }
}
