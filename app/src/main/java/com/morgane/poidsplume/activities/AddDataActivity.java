package com.morgane.poidsplume.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.morgane.poidsplume.models.BodyData;
import com.morgane.poidsplume.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Class used to add data in the history.
 */
public class AddDataActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /**
     * The editable field to enter weight.
     */
    private EditText mWeightEditText;

    /**
     * The editable field to enter body fat.
     */
    private EditText mFatEditText;

    /**
     * The editable field to enter water mass.
     */
    private EditText mWaterEditText;

    /**
     * The editable field to enter bone mass.
     */
    private EditText mBonesEditText;

    /**
     * The editable field to enter muscular mass.
     */
    private EditText mMuscleEditText;

    /**
     * The label of weight field.
     */
    private TextInputLayout mWeightTextInputLayout;

    /**
     * The label of body fat field.
     */
    private TextInputLayout mFatETextInputLayout;

    /**
     * The label of water mass field.
     */
    private TextInputLayout mWaterTextInputLayout;

    /**
     * The label of bone mass field.
     */
    private TextInputLayout mBonesTextInputLayout;

    /**
     * The label of muscular mass field.
     */
    private TextInputLayout mMuscleTextInputLayout;

    /**
     * The button used to set the date of the measure.
     */
    private Button mDateButton;

    /**
     * The button used to set the time of the measure.
     */
    private Button mTimeButton;

    /**
     * The calendar used to register the date and time of the measure.
     */
    private Calendar mDataCalendar;

    /**
     * The format used to display the date of the measure.
     */
    private SimpleDateFormat mFormatDate;

    /**
     * The format used to display the time of the measure.
     */
    private SimpleDateFormat mFormatTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWeightEditText = (EditText) findViewById(R.id.edit_text_add_weight);
        mFatEditText = (EditText) findViewById(R.id.edit_text_add_fat);
        mWaterEditText = (EditText) findViewById(R.id.edit_text_add_water);
        mBonesEditText = (EditText) findViewById(R.id.edit_text_add_bones);
        mMuscleEditText = (EditText) findViewById(R.id.edit_text_add_muscle);
        mWeightTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_add_weight);
        mFatETextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_add_fat);
        mWaterTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_add_water);
        mBonesTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_add_bones);
        mMuscleTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_add_muscle);

        mDateButton = (Button) findViewById(R.id.button_add_date);
        mTimeButton = (Button) findViewById(R.id.button_add_time);

        findViewById(R.id.button_add_cancel).setOnClickListener(this);
        findViewById(R.id.button_add_validate).setOnClickListener(this);

        // By default, put the current date and time in the pickers
        mDataCalendar = Calendar.getInstance();

        mFormatDate = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        mFormatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

        mDateButton.setText(mFormatDate.format(mDataCalendar.getTime()));
        mTimeButton.setText(mFormatTime.format(mDataCalendar.getTime()));

        mDateButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_date:
                new DatePickerDialog(this,
                        this,
                        mDataCalendar.get(Calendar.YEAR),
                        mDataCalendar.get(Calendar.MONTH),
                        mDataCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.button_add_time:
                new TimePickerDialog(this,
                        this,
                        mDataCalendar.get(Calendar.HOUR_OF_DAY),
                        mDataCalendar.get(Calendar.MINUTE),
                        true).show();
                break;

            case R.id.button_add_cancel:
                // The user cancels the adding, just close the view
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.button_add_validate:
                // The user wants to register the new data
                // Check all the fields are filled
                boolean hasError = false;
                if (mWeightEditText.getText().toString().isEmpty()) {
                    mWeightTextInputLayout.setError(getString(R.string.add_data_error_empty_field));
                    hasError = true;
                }
                if (mFatEditText.getText().toString().isEmpty()) {
                    mFatETextInputLayout.setError(getString(R.string.add_data_error_empty_field));
                    hasError = true;
                }
                if (mWaterEditText.getText().toString().isEmpty()) {
                    mWaterTextInputLayout.setError(getString(R.string.add_data_error_empty_field));
                    hasError = true;
                }
                if (mBonesEditText.getText().toString().isEmpty()) {
                    mBonesTextInputLayout.setError(getString(R.string.add_data_error_empty_field));
                    hasError = true;
                }
                if (mMuscleEditText.getText().toString().isEmpty()) {
                    mMuscleTextInputLayout.setError(getString(R.string.add_data_error_empty_field));
                    hasError = true;
                }

                if (!hasError) {
                    BodyData newBodyData = new BodyData(mDataCalendar.getTimeInMillis(),
                            Double.valueOf(mWeightEditText.getText().toString()),
                            Double.valueOf(mFatEditText.getText().toString()),
                            Double.valueOf(mMuscleEditText.getText().toString()),
                            Double.valueOf(mBonesEditText.getText().toString()),
                            Double.valueOf(mWaterEditText.getText().toString()));
                    newBodyData.save();
                    setResult(RESULT_OK);
                    finish();
                }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDataCalendar.set(Calendar.YEAR, year);
        mDataCalendar.set(Calendar.MONTH, month);
        mDataCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDateButton.setText(mFormatDate.format(mDataCalendar.getTime()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mDataCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mDataCalendar.set(Calendar.MINUTE, minute);
        mTimeButton.setText(mFormatTime.format(mDataCalendar.getTime()));
    }
}
