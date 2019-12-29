//package Tema2;


import java.util.*;

/**
 * In aceasta clasa voi retine informatiile referitoare la camera.(cum ar fi
 * id-ul camerei, id-ul device-ului, suprafata si o lista inlantuita pentru
 * senzor)
 *
 * @author Micut Andrei-Ion, Grupa 321CB
 */
public class Room {

    private String room_id;
    private String device_id;
    private int area;
    private LinkedList<LinkedList<Entry>> sensor;

    /**
     * Constructorul clasei Room imi va initializa campurile clasei.
     *
     * @param room_id   de tipul String
     * @param device_id de tipul String
     * @param area      de tipul int
     */

    public Room(String room_id, String device_id, int area) {
        this.room_id = room_id;
        this.device_id = device_id;
        this.area = area;
        sensor = new LinkedList<>();
        for (int i = 0; i <= 23; i++) {
            sensor.add(new LinkedList<>());
        }

    }

    /**
     * Imi va returna id-ul room-ului de tipul String.
     *
     * @return room_id de tipul String
     */

    public String getRoom_id() {
        return room_id;
    }

    /**
     * Imi va seta id-ul camerei.
     *
     * @param room_id de tipul String
     */

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    /**
     * Imi va returna id-ul device-ului de tipul String.
     *
     * @return device_id de tipul String
     */

    public String getDevice_id() {
        return device_id;
    }

    /**
     * Imi va seta id-ul device-ului.
     *
     * @param device_id de tipul String
     */

    public void setDevice_id(String device_id) {
         this.device_id = device_id;
    }

    /**
     * Imi va returna suprafata de tipul int.
     *
     * @return area de tipul int
     */

    public int getArea() {
        return area;
    }

    /**
     * Imi va seta suprafata.
     *
     * @param area de tipul int
     */

    public void setArea(int area) {
        this.area = area;
    }

    /**
     * Imi va returna o baza de data pentru senzor de tipul
     * LinkedList.
     *
     * @return sensor de tipul LinkedList
     */

    public LinkedList<LinkedList<Entry>> getSensor() {
        return sensor;
    }

    /**
     * Imi va seta o baza de date pentru senzor.
     *
     * @param sensor de tipul LinkedList
     */

    public void setSensor(LinkedList<LinkedList<Entry>> sensor) {
        this.sensor = sensor;
    }

    /**
     * Imi va adauga intrarea care contine temperatura si timpul pentru fiecare
     * camera prin intermediul senzorului.
     *
     * @param entry de tipul Entry
     */

    public void add(Entry entry) {
        sensor.get(entry.getHour()).add(entry);
        sensor.get(entry.getHour()).sort(null);
    }

    /**
     * Imi va returna ultima ora.
     *
     * @return sensor.get(i) de tipul LinkedList sau null
     */

    LinkedList<Entry> lastHour() {
        for (int i = 23; i >= 0; i--) {
            if (sensor.get(i).isEmpty() == false) {
                return sensor.get(i);
            }
        }
        return null;
    }

    /**
     * Aceasta metoda imi ia temperaturile dintr-un bucket care se afla in
     * intervalul [start; end].
     *
     * @param start       de tipul long
     * @param i           de tipul int
     * @param end         de tipul long
     * @param final_temps de tipul LinkedList
     */

    public void from_bucket(long start, int i, long end, LinkedList<Entry> final_temps) {
        LinkedList<Entry> partial = new LinkedList<>();

        // Adaugare

        for (Entry entry : sensor.get(i)) {
            if (entry.getTimestamp().getTime() >= start && entry.getTimestamp().getTime() <= end) {
                partial.add(entry);
            }
        }

        // Sortare

        Collections.sort(partial);
        LinkedList<Integer> to_remove = new LinkedList<>();
        LinkedList<Entry> toErase = new LinkedList<>();

        // Eliminarea duplicatelor

        for (Iterator<Entry> it = partial.iterator(); it.hasNext();) {
            Entry e = it.next();
            for (int j = partial.indexOf(e); j < partial.size(); j++) {
                Entry f = partial.get(j);
                if (e.getTemperature().equals(f.getTemperature()) && e.getTimestamp() != f.getTimestamp())
                    to_remove.add(Integer.valueOf(partial.indexOf(e)));
            }
        }

        // Finalizeaza stergerea

        for (Integer I : to_remove)
            toErase.add(partial.get(I.intValue()));
        partial.removeAll(toErase);

        final_temps.addAll(partial);
    }

    /**
     * Aceasta metoda imi returna temperaturile ce se gasesc in intervalul [start;
     * end].
     *
     * @param start de tipul long
     * @param end   de tipul long
     * @return final_temps de tipul LinkedList
     */
    public LinkedList<Entry> Get_Temps(long start, long end) {
        long dif_start = start - Defines.start;
        long dif_end = end - Defines.start;

        // Identific bucket-ul de inceput.

        dif_start = Defines.ceil(start);

        // Identific bucket-ul de final.

        dif_end = Defines.floor(end);

        LinkedList<Entry> final_temps = new LinkedList<Entry>();

        // Realizez adaugarea din fiecare bucket in parte.

        for (int i = (int) dif_end; i >= dif_start - 1; i--) {
            from_bucket(start, i, end, final_temps);
        }

        return final_temps;
    }
}