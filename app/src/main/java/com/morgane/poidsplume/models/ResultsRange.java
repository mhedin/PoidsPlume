package com.morgane.poidsplume.models;

import android.content.SharedPreferences;

import com.morgane.poidsplume.fragments.SettingsFragment;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Calendar;

/**
 * Database table.
 * Used to store the results range of the scale.
 */
public class ResultsRange extends SugarRecord {

    /**
     * Constant used to identify the body fat results ranges.
     */
    public final static int RESULT_FAT = 1;

    /**
     * Constant used to identify the muscular mass results ranges.
     */
    public final static int RESULT_MUSCLE = 2;

    /**
     * Constant used to identify the water mass results ranges.
     */
    public final static int RESULT_WATER = 3;

    /**
     * The result concerned by the ranges described.
     */
    private int resultConcerned;

    /**
     * The gender concerned by the ranges described (true : female, false : male).
     */
    private boolean isFemale;

    /**
     * The minimum age concerned by the ranges described.
     */
    private int minAge;

    /**
     * The maximum age concerned by the ranges described.
     */
    private int maxAge;

    /**
     * The min value of the high range.
     */
    private Double highRangeMin;

    /**
     * The max value of the high range.
     */
    private Double highRangeMax;

    /**
     * The min value of the good range.
     */
    private Double goodRangeMin;

    /**
     * The max value of the good range.
     */
    private Double goodRangeMax;

    /**
     * The min value of the medium range.
     */
    private Double mediumRangeMin;

    /**
     * The max value of the medium range.
     */
    private Double mediumRangeMax;

    /**
     * The min value of the bad range.
     */
    private Double badRangeMin;

    /**
     * The max value of the bad range.
     */
    private Double badRangeMax;

    /**
     * Default constructor.
     */
    public ResultsRange() {
        // Constructor used for SugarORM
    }

    public ResultsRange(int resultConcerned, boolean isFemale, int minAge, int maxAge, Double highRangeMin, Double highRangeMax, Double goodRangeMin, Double goodRangeMax,
                        Double mediumRangeMin, Double mediumRangeMax, Double badRangeMin, Double badRangeMax) {

        this.resultConcerned = resultConcerned;
        this.isFemale = isFemale;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.highRangeMin = highRangeMin;
        this.highRangeMax = highRangeMax;
        this.goodRangeMin = goodRangeMin;
        this.goodRangeMax = goodRangeMax;
        this.mediumRangeMin = mediumRangeMin;
        this.mediumRangeMax = mediumRangeMax;
        this.badRangeMin = badRangeMin;
        this.badRangeMax = badRangeMax;
    }

    /**
     * Accessor for result concerned by the ranges.
     * @return the result concerned.
     */
    public int getResultConcerned() {
        return resultConcerned;
    }

    /**
     * Accessor for min value of the high range.
     * @return The min value of the high range.
     */
    public Double getHighRangeMin() {
        return highRangeMin;
    }

    /**
     * Accessor for max value of the high range.
     * @return The max value of the high range.
     */
    public Double getHighRangeMax() {
        return highRangeMax;
    }

    /**
     * Accessor for min value of the good range.
     * @return The min value of the good range.
     */
    public Double getGoodRangeMin() {
        return goodRangeMin;
    }

    /**
     * Accessor for max value of the good range.
     * @return The max value of the good range.
     */
    public Double getGoodRangeMax() {
        return goodRangeMax;
    }

    /**
     * Accessor for min value of the medium range.
     * @return The min value of the medium range.
     */
    public Double getMediumRangeMin() {
        return mediumRangeMin;
    }

    /**
     * Accessor for max value of the medium range.
     * @return The max value of the medium range.
     */
    public Double getMediumRangeMax() {
        return mediumRangeMax;
    }

    /**
     * Accessor for min value of the bad range.
     * @return The min value of the bad range.
     */
    public Double getBadRangeMin() {
        return badRangeMin;
    }

    /**
     * Accessor for max value of the bad range.
     * @return The max value of the bad range.
     */
    public Double getBadRangeMax() {
        return badRangeMax;
    }

    /**
     * Get the results range for the body fat.
     * @param preferences The shared preferences of the application
     * @return The results range for the body fat.
     */
    public static ResultsRange getFatResultsRange(SharedPreferences preferences) {
        return getResultsRangeByResultConcerned(preferences, RESULT_FAT);
    }

    /**
     * Get the results range for the muscular mass.
     * @param preferences The shared preferences of the application
     * @return The results range for the muscular mass.
     */
    public static ResultsRange getMuscleResultsRange(SharedPreferences preferences) {
        return getResultsRangeByResultConcerned(preferences, RESULT_MUSCLE);
    }

    /**
     * Get the results range for the water mass.
     * @param preferences The shared preferences of the application
     * @return The results range for the water mass.
     */
    public static ResultsRange getWaterResultsRange(SharedPreferences preferences) {
        return getResultsRangeByResultConcerned(preferences, RESULT_WATER);
    }

    /**
     * Get the results range for a specific result.
     * @param preferences The shared preferences of the application.
     * @param resultConcerned The result concerned by the range.
     * @return The result range for the specified result.
     */
    private static ResultsRange getResultsRangeByResultConcerned(SharedPreferences preferences, int resultConcerned) {
        // The range can be found only if the evaluation is activated and the gender and date of birth defined
        if (preferences.getBoolean(SettingsFragment.PREFERENCES_EVALUATION_ACTIVATED, false)
                && preferences.contains(SettingsFragment.PREFERENCES_GENDER_IS_FEMALE)
                && preferences.contains(SettingsFragment.PREFERENCES_DATE_OF_BIRTH)) {

            int age = getAge(preferences.getLong(SettingsFragment.PREFERENCES_DATE_OF_BIRTH, Long.MIN_VALUE));

            return Select.from(ResultsRange.class)
                    .whereOr(Condition.prop("min_Age").lt(age), Condition.prop("min_Age").eq(age))
                    .and(Condition.prop("result_Concerned").eq(resultConcerned),
                            Condition.prop("is_Female").eq(preferences.getBoolean(SettingsFragment.PREFERENCES_GENDER_IS_FEMALE, true) ? 1 : 0),
                            Condition.prop("max_Age").gt(age))
                    .first();
        }

        return null;
    }

    /**
     * Get the age in years, according to a given date of birth.
     * @param dateOfBirth The date of birth.
     * @return The age.
     */
    private static int getAge(long dateOfBirth) {
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(dateOfBirth);
        Calendar today = Calendar.getInstance();

        int yearDifference = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }
        }

        return yearDifference;
    }
}
