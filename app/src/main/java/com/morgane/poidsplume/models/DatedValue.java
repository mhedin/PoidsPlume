package com.morgane.poidsplume.models;

/**
 * Class used to manipulate a date and time of measure with a particular value.
 */
public class DatedValue {

    /**
     * The date and time.
     */
    private long date;

    /**
     * The value.
     */
    private double value;

    /**
     * The unit of the value.
     */
    private int unit;

    /**
     * Constructor.
     * @param date The date and time.
     * @param value The value.
     */
    public DatedValue(long date, double value, int unit) {
        this.date = date;
        this.value = value;
        this.unit = unit;
    }

    /**
     * Accessor for the date and time.
     * @return The date and time.
     */
    public long getDate() {
        return date;
    }

    /**
     * Accessor for the value.
     * @return The value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Accessor for the unit.
     * @return The unit.
     */
    public int getUnit() {
        return unit;
    }
}
