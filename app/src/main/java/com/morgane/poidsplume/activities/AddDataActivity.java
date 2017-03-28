package com.morgane.poidsplume.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.morgane.poidsplume.R;
import com.morgane.poidsplume.models.BodyData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Class used to add data in the history.
 */
public class AddDataActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        View.OnKeyListener, View.OnFocusChangeListener {

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
     * The unit of weight field.
     */
    private TextView mWeightUnitTextView;

    /**
     * The unit of body fat field.
     */
    private TextView mFatUnitTextView;

    /**
     * The unit of water mass field.
     */
    private TextView mWaterUnitTextView;

    /**
     * The unit of bone mass field.
     */
    private TextView mBonesUnitTextView;

    /**
     * The unit of muscular mass field.
     */
    private TextView mMuscleUnitTextView;

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
        mWeightUnitTextView = (TextView) findViewById(R.id.text_view_add_weight_unit);
        mFatUnitTextView = (TextView) findViewById(R.id.text_view_add_fat_unit);
        mWaterUnitTextView = (TextView) findViewById(R.id.text_view_add_water_unit);
        mBonesUnitTextView = (TextView) findViewById(R.id.text_view_add_bones_unit);
        mMuscleUnitTextView = (TextView) findViewById(R.id.text_view_add_muscle_unit);

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

        mWeightEditText.setOnKeyListener(this);
        mFatEditText.setOnKeyListener(this);
        mBonesEditText.setOnKeyListener(this);
        mWaterEditText.setOnKeyListener(this);
        mMuscleEditText.setOnKeyListener(this);

        mWeightEditText.setOnFocusChangeListener(this);
        mFatEditText.setOnFocusChangeListener(this);
        mBonesEditText.setOnFocusChangeListener(this);
        mWaterEditText.setOnFocusChangeListener(this);
        mMuscleEditText.setOnFocusChangeListener(this);

        // Give the same padding to the value and its unit
        mWeightUnitTextView.setPadding(mWeightUnitTextView.getPaddingLeft(), mWeightUnitTextView.getPaddingTop(), mWeightUnitTextView.getPaddingRight(), mWeightEditText.getPaddingBottom());
        mFatUnitTextView.setPadding(mFatUnitTextView.getPaddingLeft(), mFatUnitTextView.getPaddingTop(), mFatUnitTextView.getPaddingRight(), mFatEditText.getPaddingBottom());
        mWaterUnitTextView.setPadding(mWaterUnitTextView.getPaddingLeft(), mWaterUnitTextView.getPaddingTop(), mWaterUnitTextView.getPaddingRight(), mWaterEditText.getPaddingBottom());
        mBonesUnitTextView.setPadding(mBonesUnitTextView.getPaddingLeft(), mBonesUnitTextView.getPaddingTop(), mBonesUnitTextView.getPaddingRight(), mBonesEditText.getPaddingBottom());
        mMuscleUnitTextView.setPadding(mMuscleUnitTextView.getPaddingLeft(), mMuscleUnitTextView.getPaddingTop(), mMuscleUnitTextView.getPaddingRight(), mBonesEditText.getPaddingBottom());
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
                // Check all the fields are filled and are valid
                boolean hasError = false;

                if (mWeightEditText.getText().toString().isEmpty()) {
                    mWeightTextInputLayout.setErrorEnabled(true);
                    mWeightTextInputLayout.setError(getString(R.string.add_data_error_empty_field));
                    hasError = true;

                } else {
                    // Check if the value is valid
                    Double doubleValue = Double.valueOf(mWeightEditText.getText().toString());
                    if (doubleValue == null || doubleValue < 0 || doubleValue > 180) {
                        mWeightTextInputLayout.setErrorEnabled(true);
                        mWeightTextInputLayout.setError(getString(R.string.add_data_error_invalid_weight));
                        hasError = true;

                    } else {
                        // Empty the possible previous error if now the field is valid
                        mWeightTextInputLayout.setErrorEnabled(false);
                    }
                }

                if (!isAValidPercentValue(mFatEditText, mFatETextInputLayout)) {
                    hasError = true;
                }

                if (!isAValidPercentValue(mWaterEditText, mWaterTextInputLayout)) {
                    hasError = true;
                }

                if (!isAValidPercentValue(mBonesEditText, mBonesTextInputLayout)) {
                    hasError = true;
                }

                if (!isAValidPercentValue(mMuscleEditText, mMuscleTextInputLayout)) {
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

    /**
     * Check if the value is a percentage, if not add an error to the TextInputLayout.
     * @param editText The EditText containing the value to test.
     * @param textInputLayout The TextInputLayout where displaying an error if necessary.
     * @return True if the value is a valid percentage, false otherwise.
     */
    private boolean isAValidPercentValue(EditText editText, TextInputLayout textInputLayout) {
        // Check if the EditText is empty
        if (editText.getText().toString().isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.add_data_error_empty_field));
            return false;

        } else {
            // Check if the value is a percentage
            Double doubleValue = Double.valueOf(editText.getText().toString());
            if (doubleValue == null || doubleValue < 0 || doubleValue > 100) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(getString(R.string.add_data_error_percent_value));
                return false;
            }
        }

        // Empty the possible previous error if now the field is valid
        textInputLayout.setErrorEnabled(false);
        return true;
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

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (event != null) {
            TextView unitTextView = null;
            switch (view.getId()) {
                case R.id.edit_text_add_weight:
                    unitTextView = (TextView) findViewById(R.id.text_view_add_weight_unit);
                    break;

                case R.id.edit_text_add_fat:
                    unitTextView = (TextView) findViewById(R.id.text_view_add_fat_unit);
                    break;

                case R.id.edit_text_add_water:
                    unitTextView = (TextView) findViewById(R.id.text_view_add_water_unit);
                    break;

                case R.id.edit_text_add_bones:
                    unitTextView = (TextView) findViewById(R.id.text_view_add_bones_unit);
                    break;

                case R.id.edit_text_add_muscle:
                    unitTextView = (TextView) findViewById(R.id.text_view_add_muscle_unit);
                    break;
            }

            if (unitTextView != null) {
                // Make the unit follow the content of the field
                Paint textPaint = mWeightEditText.getPaint();
                float valueWidth = textPaint.measureText(((EditText)view).getText().toString());
                unitTextView.setPadding((int) (valueWidth + getResources().getDimension(R.dimen.unit_and_value_padding)),
                        unitTextView.getPaddingTop(), unitTextView.getPaddingRight(), view.getPaddingBottom());
            }
        }

        return false;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        TextView unitTextView = null;
        switch (view.getId()) {
            case R.id.edit_text_add_weight:
                unitTextView = (TextView) findViewById(R.id.text_view_add_weight_unit);
                break;

            case R.id.edit_text_add_fat:
                unitTextView = (TextView) findViewById(R.id.text_view_add_fat_unit);
                break;

            case R.id.edit_text_add_water:
                unitTextView = (TextView) findViewById(R.id.text_view_add_water_unit);
                break;

            case R.id.edit_text_add_bones:
                unitTextView = (TextView) findViewById(R.id.text_view_add_bones_unit);
                break;

            case R.id.edit_text_add_muscle:
                unitTextView = (TextView) findViewById(R.id.text_view_add_muscle_unit);
                break;
        }

        if (unitTextView != null) {
            // If the field has not the focus and is empty, don't show the unit, else show it
            if (!hasFocus && ((EditText) view).getText().toString().isEmpty()) {
                unitTextView.setVisibility(View.INVISIBLE);
            } else {
                unitTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
