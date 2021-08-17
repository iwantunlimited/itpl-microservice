package io.itpl.microservice.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberHelper {

    public static double round(double value) {
        int places = 2;
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
