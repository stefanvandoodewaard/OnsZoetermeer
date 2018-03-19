package nl.zoetermeer.onszoetermeer.helpers;


import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.Challenge.VitalityType;

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
