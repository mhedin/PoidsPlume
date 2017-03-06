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
    private long measureDate;

    /**
     * The weight.
     */
    private double weight;

    /**
     * The body fat.
     */
    private double fat;

    /**
     * The muscular mass.
     */
    private double muscle;

    /**
     * The bone mass.
     */
    private double bones;

    /**
     * The water mass.
     */
    private double water;

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
        this.measureDate = measureDate;
        this.weight = weight;
        this.fat = fat;
        this.muscle = muscle;
        this.bones = bones;
        this.water = water;
    }

    /**
     * Accessor for date and time of the measure.
     * @return the date and time of the measure.
     */
    public long getMeasureDate() {
        return measureDate;
    }

    /**
     * Accessor for the weight.
     * @return The weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Accessor for the body fat.
     * @return The body fat.
     */
    public double getFat() {
        return fat;
    }

    /**
     * Accessor for the muscular mass.
     * @return The muscular mass.
     */
    public double getMuscle() {
        return muscle;
    }

    /**
     * Accessor for the bone mass.
     * @return The bone mass.
     */
    public double getBones() {
        return bones;
    }

    /**
     * Accessor for the water mass.
     * @return The water mass.
     */
    public double getWater() {
        return water;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("measure date: ").append(formatDate.format(measureDate))
                .append(" - weight: ").append(weight)
                .append(" - fat: ").append(fat)
                .append(" - muscle: ").append(muscle)
                .append(" - bones: ").append(bones)
                .append(" - water: ").append(water);
        return stringBuilder.toString();
    }
}
