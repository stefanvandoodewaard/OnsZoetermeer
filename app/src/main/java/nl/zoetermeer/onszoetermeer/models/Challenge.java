package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import nl.zoetermeer.onszoetermeer.helpers.VitalityTypeConverter;

@Entity(tableName = "CHALLENGES")
public class Challenge
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "DETAILS")
    private String details;
    @ColumnInfo(name = "VITALITY_TYPE")
    @TypeConverters(VitalityTypeConverter.class)
    public VitalityType vitalityType;
    @ColumnInfo(name = "VITALITY_WEIGHT")
    private int VitalityWeight;

    public Challenge(){

    }

    public enum VitalityType
    {
        Mentaal(1),
        Fysiek(2),;

        public int code;

        VitalityType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        @TypeConverter
        public static Challenge.VitalityType getType(int code){
            for(Challenge.VitalityType type : VitalityType.values()){
                if(type.code == code){
                    return type;
                }
            }
            return null;
        }

        @TypeConverter
        public static int getTypeInt(Challenge.VitalityType type){
            return type.code;
        }
    }

    @Override
    public String toString() {
        return this.name;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getVitalityWeight() {
        return VitalityWeight;
    }

    public void setVitalityWeight(int vitalityWeight) {
        VitalityWeight = vitalityWeight;
    }
}
