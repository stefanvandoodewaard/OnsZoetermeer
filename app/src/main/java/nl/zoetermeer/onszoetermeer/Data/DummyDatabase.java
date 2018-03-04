package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import nl.zoetermeer.onszoetermeer.Models.User;
import nl.zoetermeer.onszoetermeer.Models.UserDAO;

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class DummyDatabase extends RoomDatabase
{
    public abstract UserDAO userDAO();
}
