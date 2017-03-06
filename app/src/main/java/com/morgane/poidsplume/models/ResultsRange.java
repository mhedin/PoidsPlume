package com.morgane.poidsplume.models;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

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

    public ResultsRange(int resultConcerned, Double highRangeMin, Double highRangeMax, Double goodRangeMin, Double goodRangeMax, Double mediumRangeMin, Double mediumRangeMax,
                        Double badRangeMin, Double badRangeMax) {

        this.resultConcerned = resultConcerned;
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
     * @return The results range for the body fat.
     */
    public static ResultsRange getFatResultsRange() {
        return Select.from(ResultsRange.class)
                .where(Condition.prop("result_Concerned").eq(RESULT_FAT))
                .first();
    }

    /**
     * Get the results range for the muscular mass.
     * @return The results range for the muscular mass.
     */
    public static ResultsRange getMuscleResultsRange() {
        return Select.from(ResultsRange.class)
                .where(Condition.prop("result_Concerned").eq(RESULT_MUSCLE))
                .first();
    }

    /**
     * Get the results range for the water mass.
     * @return The results range for the water mass.
     */
    public static ResultsRange getWaterResultsRange() {
        return Select.from(ResultsRange.class)
                .where(Condition.prop("result_Concerned").eq(RESULT_WATER))
                .first();
    }
}
