package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;

import nl.zoetermeer.onszoetermeer.Models.Request;

@Dao
public interface RequestDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Request request);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Request request);

    @Delete
    void delete(Request request);
}
