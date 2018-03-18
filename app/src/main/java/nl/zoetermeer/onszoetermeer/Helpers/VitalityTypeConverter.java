package nl.zoetermeer.onszoetermeer.Helpers;


import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import nl.zoetermeer.onszoetermeer.Models.Challenge;
import nl.zoetermeer.onszoetermeer.Models.Challenge.VitalityType;

public class VitalityTypeConverter
{
    @Nullable
    @TypeConverter
    public static Challenge.VitalityType getType(int code){
        for(Challenge.VitalityType type : VitalityType.values()){
            if(type.code == code){
                return type;
            }
        }
        return null;
    }

    @Nullable
    @TypeConverter
    public static int getTypeInt(Challenge.VitalityType type){
        return type.code;
    }

}
