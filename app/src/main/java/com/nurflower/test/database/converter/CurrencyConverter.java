package com.nurflower.test.database.converter;

import androidx.room.TypeConverter;

import java.math.BigDecimal;

public class CurrencyConverter {
    @TypeConverter
    public static String fromBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.toString();
    }

    @TypeConverter
    public static BigDecimal toBigDecimal(String bigDecimal) {
        return new BigDecimal(bigDecimal);
    }
}
