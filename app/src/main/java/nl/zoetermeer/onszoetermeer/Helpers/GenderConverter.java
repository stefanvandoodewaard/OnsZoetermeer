package nl.zoetermeer.onszoetermeer.Helpers;


import android.arch.persistence.room.TypeConverter;

import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.Models.User.Gender;

public class GenderConverter
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
