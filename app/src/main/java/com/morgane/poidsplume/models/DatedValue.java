package com.morgane.poidsplume.models;

/**
 * Class used to manipulate a date and time of measure with a particular value.
 */
public class DatedValue {

    /**
     * The date and time.
     */
    private final long date;

    /**
     * The value.
     */
    private final String value;

    /**
     * Constructor.
     * @param date The date and time.
     * @param value The value.
     */
    public DatedValue(long date, String value) {
        this.date = date;
        this.value = value;
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
    public String getValue() {
        return value;
    }
}
