package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.Models.User.Gender;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}