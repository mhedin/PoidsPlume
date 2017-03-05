package com.morgane.poidsplume.models;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Database table.
 * Used to store the body data given by the scale.
 */
public class BodyData extends SugarRecord {

    /**
     * The date and time of the measure.
     */
    private long mMeasureDate;

    /**
     * The weight.
     */
    private double mWeight;

    /**
     * The body fat.
     */
    private double mFat;

    /**
     * The muscular mass.
     */
    private double mMuscle;

    /**
     * The bone mass.
     */
    private double mBones;

    /**
     * The water mass.
     */
    private double mWater;

    /**
     * Default constructor.
     */
    public BodyData() {
        // Constructor used for SugarORM
    }

    /**
     * Constructor.
     * @param measureDate The date and time of the measure.
     * @param weight The weight;
     * @param fat The body fat.
     * @param muscle The muscular mass.
     * @param bones The bone mass.
     * @param water The water mass.
     */
    public BodyData(long measureDate, double weight, double fat, double muscle, double bones, double water) {
        mMeasureDate = measureDate;
        mWeight = weight;
        mFat = fat;
        mMuscle = muscle;
        mBones = bones;
        mWater = water;
    }

    /**
     * Accessor for date and time of the measure.
     * @return the date and time of the measure.
     */
    public long getMeasureDate() {
        return mMeasureDate;
    }

    /**
     * Accessor for the weight.
     * @return The weight.
     */
    public double getWeight() {
        return mWeight;
    }

    /**
     * Accessor for the body fat.
     * @return The body fat.
     */
    public double getFat() {
        return mFat;
    }

    /**
     * Accessor for the muscular mass.
     * @return The muscular mass.
     */
    public double getMuscle() {
        return mMuscle;
    }

    /**
     * Accessor for the bone mass.
     * @return The bone mass.
     */
    public double getBones() {
        return mBones;
    }

    /**
     * Accessor for the water mass.
     * @return The water mass.
     */
    public double getWater() {
        return mWater;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("measure date: ").append(formatDate.format(mMeasureDate))
                .append(" - weight: ").append(mWeight)
                .append(" - fat: ").append(mFat)
                .append(" - muscle: ").append(mMuscle)
                .append(" - bones: ").append(mBones)
                .append(" - water: ").append(mWater);
        return stringBuilder.toString();
    }
}
