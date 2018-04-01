package nl.zoetermeer.onszoetermeer.helpers;

import android.arch.persistence.room.TypeConverter;

import nl.zoetermeer.onszoetermeer.models.Achievement;
import nl.zoetermeer.onszoetermeer.models.Achievement.BadgeType;

public class BadgeTypeConverter
{
    @TypeConverter
    public static Achievement.BadgeType getBadgeType(int code){
        for(Achievement.BadgeType badgeType : BadgeType.values()){
            if(badgeType.code == code){
                return badgeType;
            }
        }
        return null;
    }

    @TypeConverter
    public static int getBadgeTypeInt(Achievement.BadgeType badgeType){
        return badgeType.code;
    }
}
