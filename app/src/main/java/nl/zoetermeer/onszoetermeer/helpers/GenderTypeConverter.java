package nl.zoetermeer.onszoetermeer.helpers;


import android.arch.persistence.room.TypeConverter;

import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.models.User.Gender;

public class GenderTypeConverter
{
    @TypeConverter
    public static User.Gender getGender(int code){
        for(User.Gender gender : Gender.values()){
            if(gender.code == code){
                return gender;
            }
        }
        return null;
    }

    @TypeConverter
    public static int getGenderInt(User.Gender gender){
        return gender.code;
    }
}
