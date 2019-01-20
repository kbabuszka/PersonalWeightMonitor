package net.babuszka.personalweightmonitor.data.db;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDate;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }
}