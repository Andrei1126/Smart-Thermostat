//package Tema2;


import java.io.*;
import java.util.Scanner;

/**
 * Clasa Main imi va realiza rularea temei.
 *
 * @author Micut Andrei-Ion, Grupa 321CB
 *
 */

public class Main {

    public static Scanner x;

    /**
     * Realizez deschiderea fisierului de intrare.
     */

    public static void open() {
        try {
            x = new Scanner(new File("therm.in"));
        } catch (Exception e) {
            System.out.println("could not find file");
        }
    }

    /**
     * Realizez citirea fisierului de intrare. Imi va executa instructiunile
     * corespunzatoare, una cate una.
     *
     * @throws NullPointerException arunca exceptie in cazul in care am accesat o ona de memorie nealocata
     */

    public static void read() throws NullPointerException {

        String nr_rooms = x.next();
        int nr_of_rooms = Integer.parseInt(nr_rooms);
        String glo_temp = x.next();
        double global_temperature = Double.parseDouble(glo_temp);
        String tstamp = x.next();
        long timestamp = Long.parseLong(tstamp);
        House house = new House(nr_of_rooms, global_temperature, timestamp);

        while (x.hasNext()) {

            String id = x.next();

            // Verifica daca id-ul contine cuvantul "ROOM" si creeaza o camera.

            if (id.contains("ROOM")) {
                String device_id = x.next();
                int area = Integer.parseInt(x.next());

                house.getRoom().add(new Room(id, device_id, area));

                Room r = new Room(id, device_id, area);
            }

            // Imi va apela functie observe.

            if (id.equals("OBSERVE")) {
                String device_id = x.next();
                long new_timestamp = x.nextLong();
                double temperature = x.nextDouble();

                house.observe(device_id, temperature, new_timestamp);
            }

            // Imi va apela functie trigger.

            if (id.equals("TRIGGER")) {
                x.next();
                if (house.trigger())
                    Defines.output.println("YES");
                else
                    Defines.output.println("NO");
            }

            // Imi va seta temperatura globala a casei.

            if (id.equals("TEMPERATURE")) {
                double new_global_temp = x.nextDouble();

                house.setGlobal_temp(new_global_temp);
            }

            // Imi va apela functia de listare, list.

            if (id.equals("LIST")) {
                String room_id = x.next();
                long start_interval = x.nextLong();
                long end_interval = x.nextLong();

                house.list(room_id, start_interval, end_interval);
            }
        }
    }

    /**
     * Realizez inchiderea fisierului de intrare.
     */

    public static void close() {
        x.close();
    }

    /**
     * Apelez functiile de deschidere, citire si inchidere a fisierului de input.
     */

    public static void main(String[] args){

        Main.open();
        Main.read();
        Main.close();
    }
}
