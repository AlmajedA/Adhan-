package com.almajeda.adhan_apis.util;

public class mathUtils {
    public static double sinDeg(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }

    public static double cosDeg(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }

    public static double tanDeg(double degrees) {
        return Math.tan(Math.toRadians(degrees));
    }

    public static double acosDeg(double value) {
        return Math.toDegrees(Math.acos(value));
    }

    public static double acotDeg(double value) {
        return Math.toDegrees(Math.atan(1.0 / value));
    }
}
