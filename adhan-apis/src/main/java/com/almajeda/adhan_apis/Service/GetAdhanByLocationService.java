package com.almajeda.adhan_apis.Service;

import com.almajeda.adhan_apis.data.dto.Location;
import org.springframework.stereotype.Service;
import com.almajeda.adhan_apis.util.mathUtils;

@Service
public class GetAdhanByLocationService {

    public float[] getAdhanByLocation(Location location) {
        float julianDays = calculateJulianDays(location);
        double sunDeclination = calculateSunDeclination(julianDays);
        double equationOfTime = calculateEquationOfTime(julianDays);
        float transitTime = calculateTransitTime(location, equationOfTime);
        float[] sunAltitudes = calculateSunAltitudes(location, sunDeclination);
        float[] hourAngles = calculateHourAngles(sunAltitudes, location.getLatitude(), sunDeclination);
        float[] prayerTimes = calculatePrayerTimes(transitTime, hourAngles);
        return convertDecimalToTime(prayerTimes);
    }

    private float calculateJulianDays(Location location) {
        int year = location.getCurrentDateTime().getYear();
        int month = location.getCurrentDateTime().getMonthValue();
        int day = location.getCurrentDateTime().getDayOfMonth();
        int hour = location.getCurrentDateTime().getHour();
        int minute = location.getCurrentDateTime().getMinute();
        int second = location.getCurrentDateTime().getSecond();
        float timeZone = location.getTimeZone();

        if (month <= 2) {
            year--;
            month += 12;
        }

        int A = year / 100;
        int B = 2 - A + (A / 4);

        float JD = (float) (Math.floor(365.25 * (year + 4716)) +
                Math.floor(30.6001 * (month + 1)) +
                day + B - 1524.5);
        float dayFraction = (hour + minute / 60.0f + second / 3600.0f) / 24.0f;
        JD += dayFraction - (timeZone / 24.0f);

        return JD;
    }

    private double calculateSunDeclination(float julianDays) {
        double T = 2 * Math.PI * (julianDays - 2451545) / 365.25;
        return 0.37877 + 23.264 * mathUtils.sinDeg(57.297 * T - 79.547)
                + 0.3812 * mathUtils.sinDeg(2 * 57.297 * T - 82.682)
                + 0.17132 * mathUtils.sinDeg(3 * 57.297 * T - 59.722);
    }

    private double calculateEquationOfTime(double JD) {
        double U = (JD - 2451545) / 36525;
        double L0 = 280.46607 + 36000.7698 * U;

        double ET1000 = -(1789 + 237 * U) * mathUtils.sinDeg(L0)
                - (7146 - 62 * U) * mathUtils.cosDeg(L0)
                + (9934 - 14 * U) * mathUtils.sinDeg(2 * L0)
                - (29 + 5 * U) * mathUtils.cosDeg(2 * L0)
                + (74 + 10 * U) * mathUtils.sinDeg(3 * L0)
                + (320 - 4 * U) * mathUtils.cosDeg(3 * L0)
                - 212 * mathUtils.sinDeg(4 * L0);

        return ET1000 / 1000;
    }

    private float calculateTransitTime(Location location, double ET) {
        return (float) (12 + location.getTimeZone() - (location.getLongitude() / 15) - (ET / 60));
    }

    private float[] calculateSunAltitudes(Location location, double delta) {
        double saFajr = -location.getJafariFajrAngle();
        double saSunrise = -0.8333 - (0.0347 * Math.sqrt(location.getElevation()));
        double saAsr = mathUtils
                .acotDeg(location.getShadowFactor() + mathUtils.tanDeg(Math.abs(delta - location.getLatitude())));
        double saMaghrib = saSunrise;
        double saIsha = -location.getJafariIshaAngle();

        return new float[] { (float) saFajr, (float) saSunrise, (float) saAsr, (float) saMaghrib, (float) saIsha };
    }

    private float[] calculateHourAngles(float[] sunAltitudes, float latitude, double delta) {
        float[] hourAngles = new float[5];
        for (int i = 0; i < 5; i++) {
            double cosHA = (mathUtils.sinDeg(sunAltitudes[i]) - mathUtils.sinDeg(latitude) * mathUtils.sinDeg(delta))
                    / (mathUtils.cosDeg(latitude) * mathUtils.cosDeg(delta));
            cosHA = Math.max(-1, Math.min(1, cosHA)); // Ensure the value is within [-1, 1]
            hourAngles[i] = (float) mathUtils.acosDeg(cosHA);
        }
        return hourAngles;
    }

    private float[] calculatePrayerTimes(float TT, float[] hourAngles) {
        float fajr = TT - hourAngles[0] / 15;
        float sunrise = TT - hourAngles[1] / 15;
        float zuhr = TT;
        float asr = TT + hourAngles[2] / 15;
        float maghrib = TT + hourAngles[3] / 15 + (10 / 60.0f);
        float isha = TT + hourAngles[4] / 15;
        return new float[] { fajr, sunrise, zuhr, asr, maghrib, isha };
    }

    private float[] convertDecimalToTime(float[] times) {
        for (int i = 0; i < times.length; i++) {
            float time = times[i];
            int hours = (int) time;
            int minutes = (int) Math.ceil((time - hours) * 60);
            times[i] = hours + minutes / 100.0f;
        }
        return times;
    }

}
