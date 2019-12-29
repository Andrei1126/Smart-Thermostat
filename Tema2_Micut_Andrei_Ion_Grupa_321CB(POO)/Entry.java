//package Tema2;


import java.util.Date;

/**
 * Aceasta clasa reprezinta baza de date in care vor fi introduse temperaturile
 * unei camere la un anumit timp.
 *
 * @author Micut Andrei-Ion, Grupa 321CB
 *
 */

public class Entry implements Comparable<Entry> {

    private Double temperature;
    private Date timestamp;

    /**
     * Imi va returna temperatura de tipul Double.
     *
     * @return temperature de tipul Double
     */

    public Double getTemperature() {
        return temperature;
    }

    /**
     * Imi va seta temperatura.
     *
     * @param temperature de tipul Double
     */

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * Imi va returna timpul de tipul Date.
     *
     * @return timestamp de tipul Date
     */

    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Imi va seta temperatura.
     *
     * @param timestamp de tipul long
     */

    public void setTimestamp(long timestamp) {
        this.timestamp = new Date(timestamp);
    }

    /**
     * Imi va returna un bucket.
     *
     * @return bucket
     */

    public int getHour() {
        return Math.toIntExact((getTimestamp().getTime() - Defines.start) / Defines.hour);
    }

    /**
     * Suprascriu metoda compareTo din Comparable pentru a modela metoda in vederea
     * sortarii temperaturilor din camera.
     */

    @Override
    public int compareTo(Entry entry) {
        if (this.getTemperature() > entry.getTemperature())
            return 1;
        if (this.getTemperature() < entry.getTemperature())
            return -1;
        return 0;
    }
}