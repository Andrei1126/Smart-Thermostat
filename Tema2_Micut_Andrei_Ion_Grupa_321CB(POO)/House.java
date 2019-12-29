//package Tema2;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * In aceasta clasa am introdus numarul de camere, o lista de camere,
 * temperatura globala si timpul de referinta, timestamp-ul. Am implementat
 * metodele: observe, trigger heat si list.
 *
 * @author Micut Andrei-Ion, Grupa 321CB
 *
 */

public class House {

    private int nr_of_rooms;
    List<Room> room;
    private double global_temp;
    private static long timestamp;

    /**
     * Constructorul clasei House imi va initializa fiecare cand am clasei.
     *
     * @param nr_of_rooms de tipul int
     * @param global_temp de tipul double
     * @param timestamp de tipul long
     */

    public House(int nr_of_rooms, double global_temp, long timestamp) {

        this.nr_of_rooms = nr_of_rooms;
        this.global_temp = global_temp;
        this.timestamp = timestamp - (Defines.day * Defines.hour);
        Defines.setStart(this.timestamp);
        room = new LinkedList<>();
    }

    /**
     * Imi va returna valoarea timpului de referinta de tipul long.
     *
     * @return timestamp de tipul long
     */

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Imi va seta timpul de referinta.
     *
     * @param timestamp de tipul long
     */

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Imi va returna numarul de camere de tipul int.
     *
     * @return nr_of_rooms de tipul int
     */

    public int getNr_of_rooms() {
        return nr_of_rooms;
    }

    /**
     * Imi va seta numarul de camere ale casei.
     *
     * @param nr_of_rooms de tipul int
     */

    public void setNr_of_rooms(int nr_of_rooms) {
        this.nr_of_rooms = nr_of_rooms;
    }

    /**
     * Imi va returna temperatura globala a casei de tipul double.
     *
     * @return global_temp de tipul double
     */

    public double getGlobal_temp() {
        return global_temp;
    }

    /**
     * Imi va seta temperatura globala a casei.
     *
     * @param global_temp de tipul double
     */

    public void setGlobal_temp(double global_temp) {
        this.global_temp = global_temp;
    }

    /**
     * Imi va returna lista de camere a casei de tipul List.
     *
     * @return room de tipul List
     */

    public List<Room> getRoom() {
        return room;
    }

    /**
     * Imi va seta o lista de camere pentru casa.
     *
     * @param room de tipul List
     */

    public void setRoom(List<Room> room) {
        this.room = room;
    }

    /**
     * In metoda observe voi realiza inserarea temperaturii corespunzatoare id-ului
     * device-ului in intervalul conrespunzator cu timestamp-ul.
     *
     * @param device      de tipul String
     * @param temperature de tipul double
     * @param time        de tipul long
     */

    public void observe(String device, double temperature, long time) {

        Room r = null;

        // In cazul in care depasesc 24 de ore, atunci ies imediat din functi, deoarece
        // s-a produs o greseala.

        if (time - timestamp > Defines.day * Defines.hour) {
            return;
        }

        // Voi cauta in lista de camere device id-ul cautat si voi retine intr-o
        // variabila
        // de tipul Room, camera in care a fost gasit si voi iesi din for.

        for (Room i : this.room) {
            if (i.getDevice_id().equals(device)) {
                r = i;
                break;
            }
        }

        // Voi introduce in baza de date temperatura si ora la care a fost inregistrata
        // aceasta.

        Entry entry = new Entry();
        entry.setTemperature(temperature);
        entry.setTimestamp(time);

        // Ii asociez camerei r baza de date respectiva.

        r.add(entry);
    }

    /**
     * Calculeaza conform algoritmului din cerinta necesitatea de a porni centrala,
     * raportandu-se la temperatura globala a casei.
     *
     * @return false in cazul in care nu am crescut temperatura globala a casei sau
     *         true in caz afirmativ
     */

    public boolean trigger() {

        double temperature = 0;
        double total_surface = 0;

        // Iau fiecare camera in parte si realizez media ponderata a temperaturilor

        for (Room r : this.room) {
            double min = 100;
            for (Entry e : r.lastHour()) {
                if (e.getTemperature() < min) {
                    min = e.getTemperature();
                }
            }

            temperature += (min * ((double) (r.getArea())));
            total_surface += (double) (r.getArea());
        }

        temperature /= total_surface;

        return global_temp > temperature;
    }

    /**
     * Metoda list imi va realiza listarea temperaturilor din intervalul de timp
     * [timestamp_beg; timestamp_end] in ordine crescatoare.
     *
     * @param room          de tipul String
     * @param timestamp_beg de tipul long
     * @param timestamp_end de tipul long
     */

    public void list(String room, long timestamp_beg, long timestamp_end) {

        Room r = null;
        NumberFormat formatter = new DecimalFormat("#0.00");

        Defines.output.print(room);

        // Iau fiecare camera in parte pana cand gasesc camera data ca parametru

        for (Room i : this.room) {
            if (i.getRoom_id().equals(room)) {
                r = i;
                break;
            }
        }
        if (r == null)
            return;

        // Filtram din baza de data acele entry-uri care sunt intre timpul de inceput
        // si timpul de final.

        LinkedList<Entry> to_list = null;
        to_list = r.Get_Temps(timestamp_beg, timestamp_end);

        // Parcurg baza de date si realizez afisarea temperaturilor.
        // Folosesc formatter pentru a putea afisa si cel de-al doilea 0 care
        // se afla dupa virgula.

        for (Entry entry : to_list) {
            Defines.output.print(" " + formatter.format(entry.getTemperature()));
        }

        Defines.output.println();

    }
}