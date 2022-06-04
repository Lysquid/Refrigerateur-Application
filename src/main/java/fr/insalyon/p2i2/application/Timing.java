package fr.insalyon.p2i2.application;

import java.util.HashMap;

public class Timing {

    private static HashMap<Integer, Long> times = new HashMap<>();

    public static void start(int id) {
        times.put(id, System.currentTimeMillis());
    }

    public static void start() {
        start(0);
    }

    public static void stop(int id) {
        long delta = System.currentTimeMillis() - times.get(id);
        times.remove(id);
        System.out.println("Timer " + id + ": " + delta + " ms");

    }

    public static void stop() {
        stop(0);
    }

}
