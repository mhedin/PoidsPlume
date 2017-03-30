package com.morgane.poidsplume.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.morgane.poidsplume.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Fragment displaying the settings of the scale, and allowing the user to modify them.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener, Switch.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {

    /**
     * Key used in the preferences to register if the evaluation of the rates is activated.
     */
    public static final String PREFERENCES_EVALUATION_ACTIVATED = "evaluationActivated";

    /**
     * Key used in the preferences to register the gender of the user (if the user is a female, it's true, otherwise false).
     */
    public static final String PREFERENCES_GENDER_IS_FEMALE = "genderIsFemale";

    /**
     * Key used in the preferences to register the date of birth of the user.
     */
    public static final String PREFERENCES_DATE_OF_BIRTH = "dateOfBirth";

    /**
     * Instance of RelativeLayout containing the gender elements.
     */
    private RelativeLayout mGenderRelativeLayout;

    /**
     * Instance of RelativeLayout containing the date of birth elements.
     */
    private RelativeLayout mDateOfBirthRelativeLayout;

    /**
     * Instance of ImageView representing the female icon.
     */
    private ImageView mFemaleImageView;

    /**
     * Instance of ImageView representing the male icon.
     */
    private ImageView mMaleImageView;

    /**
     * Instance of Button representing the date of birth of the user.
     */
    private Button mDateOfBirthButton;

    /**
     * The calendar used to register the date of birth of the user.
     */
    private Calendar mDateOfBirthCalendar;

    /**
     * The format used to display the date of the measure.
     */
    private SimpleDateFormat mFormatDate;

    /**
     * Instance of TextView used to display the warning message indicating all the parameters must be filled.
     */
    private TextView mWarningParametersTextView;

    /**
     * Instance of the SharedPreferences of the application.
     */
    private SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        getActivity().setTitle(R.string.settings_title);

        mGenderRelativeLayout = (RelativeLayout) view.findViewById(R.id.settings_gender_relative_layout);
        mDateOfBirthRelativeLayout = (RelativeLayout) view.findViewById(R.id.settings_date_of_birth_relative_layout);
        mFemaleImageView = (ImageView) view.findViewById(R.id.settings_gender_female_image_view);
        mMaleImageView = (ImageView) view.findViewById(R.id.settings_gender_male_image_view);
        mDateOfBirthButton = (Button) view.findViewById(R.id.settings_date_of_birth_button);
        mWarningParametersTextView = (TextView) view.findViewById(R.id.settings_warning_all_parameters_text_view);

        Switch activationSwitch = (Switch) view.findViewById(R.id.settings_activation_switch);
        activationSwitch.setOnCheckedChangeListener(this);

        mFemaleImageView.setOnClickListener(this);
        mMaleImageView.setOnClickListener(this);
        mDateOfBirthButton.setOnClickListener(this);

        mFormatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        boolean isEvaluationActivated = mSharedPreferences.getBoolean(PREFERENCES_EVALUATION_ACTIVATED, true);
        activationSwitch.setChecked(isEvaluationActivated);
        refreshViewsAccordingToActivation(isEvaluationActivated);

        // Select no gender by default
        if (mSharedPreferences.contains(PREFERENCES_GENDER_IS_FEMALE)) {
            refreshGender(mSharedPreferences.getBoolean(PREFERENCES_GENDER_IS_FEMALE, true));
        }

        // By default, put the current date in the picker
        mDateOfBirthCalendar = Calendar.getInstance();
        if (mSharedPreferences.contains(PREFERENCES_DATE_OF_BIRTH)) {
            mDateOfBirthCalendar.setTimeInMillis(mSharedPreferences.getLong(PREFERENCES_DATE_OF_BIRTH, Long.MIN_VALUE));
            mDateOfBirthButton.setText(mFormatDate.format(mDateOfBirthCalendar.getTime()));
        }

        return view;
    }

    /**
     * Refresh the views according to the activation or deactivation of the evaluation of the rates.
     * @param isEvaluationActivated Flag indicating if the evaluation of the rates is activated or not.
     */
    private void refreshViewsAccordingToActivation(boolean isEvaluationActivated) {
        if (isEvaluationActivated) {
            // If the evaluation is activated, show the parameters to configure
            mGenderRelativeLayout.setVisibility(View.VISIBLE);
            mDateOfBirthRelativeLayout.setVisibility(View.VISIBLE);
            refreshWarningMessage();

        } else {
            // Else hide them
            mGenderRelativeLayout.setVisibility(View.GONE);
            mDateOfBirthRelativeLayout.setVisibility(View.GONE);
            mWarningParametersTextView.setVisibility(View.GONE);
        }
    }

    /**
     * Refresh the gender selection according to the preferences of the user.
     * @param isFemale Flag indicating if the user is a female or a male.
     */
    private void refreshGender(boolean isFemale) {
        if (isFemale) {
            mFemaleImageView.setImageResource(R.drawable.ic_female_selected);
            mMaleImageView.setImageResource(R.drawable.ic_male);
        } else {
            mFemaleImageView.setImageResource(R.drawable.ic_female);
            mMaleImageView.setImageResource(R.drawable.ic_male_selected);
        }
    }

    /**
     * Hide or show the warning message, according to the filling of the gender and date of birth.
     */
    private void refreshWarningMessage() {
        if (mSharedPreferences.contains(PREFERENCES_DATE_OF_BIRTH) && mSharedPreferences.contains(PREFERENCES_GENDER_IS_FEMALE)) {
            mWarningParametersTextView.setVisibility(View.GONE);
        } else {
            mWarningParametersTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_gender_female_image_view:
                mSharedPreferences.edit().putBoolean(PREFERENCES_GENDER_IS_FEMALE, true).apply();
                refreshGender(true);
                refreshWarningMessage();
                break;

            case R.id.settings_gender_male_image_view:
                mSharedPreferences.edit().putBoolean(PREFERENCES_GENDER_IS_FEMALE, false).apply();
                refreshGender(false);
                refreshWarningMessage();
                break;

            case R.id.settings_date_of_birth_button:
                new DatePickerDialog(getActivity(),
                        this,
                        mDateOfBirthCalendar.get(Calendar.YEAR),
                        mDateOfBirthCalendar.get(Calendar.MONTH),
                        mDateOfBirthCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mSharedPreferences.edit().putBoolean(PREFERENCES_EVALUATION_ACTIVATED, isChecked).apply();
        refreshViewsAccordingToActivation(isChecked);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDateOfBirthCalendar.set(Calendar.YEAR, year);
        mDateOfBirthCalendar.set(Calendar.MONTH, month);
        mDateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        mDateOfBirthButton.setText(mFormatDate.format(mDateOfBirthCalendar.getTime()));
        mSharedPreferences.edit().putLong(PREFERENCES_DATE_OF_BIRTH, mDateOfBirthCalendar.getTimeInMillis()).apply();

        refreshWarningMessage();
    }
}
