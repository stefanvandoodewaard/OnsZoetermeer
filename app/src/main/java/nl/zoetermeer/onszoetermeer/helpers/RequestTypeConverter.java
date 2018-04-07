package nl.zoetermeer.onszoetermeer.helpers;

import android.arch.persistence.room.TypeConverter;

import nl.zoetermeer.onszoetermeer.models.Request;
import nl.zoetermeer.onszoetermeer.models.Request.RequestType;

public class RequestTypeConverter
{
    @TypeConverter
    public static Request.RequestType getType(int code){
        for(Request.RequestType type : RequestType.values()){
            if(type.code == code){
                return type;
            }
        }
        return null;
    }

    @TypeConverter
    public static int getTypeInt(Request.RequestType type){
        return type.code;
    }
}