//package Tema2;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * In aceasta clasa mi-am definit constantele pe care le voi folosi pe parcursul
 * implementarii temei si am realizat si fisierul in care voi afisa
 *
 * @author Micut Andrei-Ion, Grupa 321CB
 *
 */

public class Defines {

    public static final long hour = 3600;
    public static final int day = 24;
    public static final int seconds = 1000;
    public static PrintStream output;
    public static long start;

    /**
     * Imi va seta timpul de start.
     *
     * @param time de tipul long
     */

    public static void setStart(long time) {
        start = time;
    }

    static {
        try {
            output = new PrintStream(new File("therm.out"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imi va returna partea intreaga inferioara a timpului de inceput.
     *
     * @param time de tipul long
     * @return i de tipul int
     */

    public static int floor(long time) {
        int i = 0;
        while (i < 24) {

            if (time < start + (i + 1) * hour)
                break;
            i++;
        }

        return i;
    }

    /**
     * Imi va returna partea intreaga superioara a timpului de sfarsit.
     *
     * @param time de tipul long
     * @return i + 1 de tipul int
     */

    public static int ceil(long time) {
        int i = 0;
        while (i < 24) {

            if (time < start + (i + 1) * hour)
                break;
            i++;
        }
        return i + 1;
    }
}