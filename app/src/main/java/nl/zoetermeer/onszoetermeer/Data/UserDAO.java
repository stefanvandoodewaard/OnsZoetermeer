package nl.zoetermeer.onszoetermeer.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;

import java.util.List;

import nl.zoetermeer.onszoetermeer.Models.User;

@Dao
public interface UserDAO
{

    @Query("SELECT * FROM USERS WHERE ID = :ID")
    User getByID(int ID);

    @Query("SELECT * FROM USERS WHERE FIRST_NAME LIKE :name OR LAST_NAME LIKE :name")
    List<User> getByName(String name);

    @Query("SELECT * FROM USERS ORDER BY FIRST_NAME ASC")
    LiveData<List<User>> getAll();

    @Query("DELETE FROM USERS") // TBV opschonen/testen
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Delete
    void delete(User user);
}

