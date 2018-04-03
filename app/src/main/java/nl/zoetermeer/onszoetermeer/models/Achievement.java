package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import nl.zoetermeer.onszoetermeer.helpers.BadgeTypeConverter;

@Entity(tableName = "ACHIEVEMENTS")
public class Achievement
{

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "BADGE_TYPE")
    @TypeConverters(BadgeTypeConverter.class)
    public BadgeType badgeType;

    public Achievement(int id, String name, BadgeType badgeType){
        this.id = id;
        this.name = name;
        this.badgeType = badgeType;
}

    public enum BadgeType
    {
        Goud(0),
        Zilver(1),
        Brons(2);

        public int code;

        BadgeType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
