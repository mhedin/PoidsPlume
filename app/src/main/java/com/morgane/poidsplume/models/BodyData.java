package com.morgane.poidsplume.models;

import com.morgane.poidsplume.R;
import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    /**
     * Get all the bone mass values associated with the date of measure and unit.
     * @return The list of all bone mass values associated with the date of measure and unit.
     */
    public static List<DatedValue> getAllBoneMass() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").stream()
                .map(data -> new DatedValue(data.getId(), data.getMeasureDate(), data.getBones(), R.string.history_value_unit_percentage))
                .collect(Collectors.toList());
    }

    /**
     * Get all the body fat values associated with the date of measure and unit.
     * @return The list of all body fat values associated with the date of measure and unit.
     */
    public static List<DatedValue> getAllBodyFat() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").stream()
                .map(data -> new DatedValue(data.getId(), data.getMeasureDate(), data.getFat(), R.string.history_value_unit_percentage))
                .collect(Collectors.toList());
    }

    /**
     * Get all the muscular mass values associated with the date of measure and unit.
     * @return The list of all muscular mass values associated with the date of measure and unit.
     */
    public static List<DatedValue> getAllMuscularMass() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").stream()
                .map(data -> new DatedValue(data.getId(), data.getMeasureDate(), data.getMuscle(), R.string.history_value_unit_percentage))
                .collect(Collectors.toList());
    }

    /**
     * Get all the water mass values associated with the date of measure and unit.
     * @return The list of all water mass values associated with the date of measure and unit.
     */
    public static List<DatedValue> getAllWaterMass() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").stream()
                .map(data -> new DatedValue(data.getId(), data.getMeasureDate(), data.getWater(), R.string.history_value_unit_percentage))
                .collect(Collectors.toList());
    }

    /**
     * Get all the weights values associated with the date of measure and unit.
     * @return The list of all weights values associated with the date of measure and unit.
     */
    public static List<DatedValue> getAllWeights() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").stream()
                .map(data -> new DatedValue(data.getId(), data.getMeasureDate(), data.getWeight(), R.string.history_value_unit_kg))
                .collect(Collectors.toList());
    }

    /**
     * Get the last weight value.
     * @return The last weight value.
     */
    public static DatedValue getLastWeight() {
        BodyData weightData = BodyData.listAll(BodyData.class, "measure_date DESC").get(0);
        return new DatedValue(weightData.getId(), weightData.getMeasureDate(), weightData.getWeight(), R.string.history_value_unit_kg);
    }

    /**
     * Get the last body fat value, in percentage.
     * @return The last body fat value.
     */
    public static double getLastBodyFatInPercentage() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").get(0).getFat()  / 100;
    }

    /**
     * Get the last muscular mass value, in percentage.
     * @return The last muscular mass value.
     */
    public static double getLastMuscularMassInPercentage() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").get(0).getMuscle()  / 100;
    }

    /**
     * Get the last water mass value, in percentage.
     * @return The last water mass value.
     */
    public static double getLastWaterMassInPercentage() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").get(0).getWater()  / 100;
    }

    /**
     * Get the last bone mass value, in percentage.
     * @return The last bone mass value.
     */
    public static double getLastBoneMassInPercentage() {
        return BodyData.listAll(BodyData.class, "measure_date DESC").get(0).getBones() / 100;
    }
}
