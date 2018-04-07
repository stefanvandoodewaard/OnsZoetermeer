package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;

import java.util.List;

import nl.zoetermeer.onszoetermeer.models.Request;

@Dao
public interface RequestDAO
{
    @Query("SELECT * FROM REQUESTS")
    List<Request> getAll();

    @Query("SELECT * FROM REQUESTS WHERE USER_ID = :userId")
    List<Request>  getRequestsByUser(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Request request);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Request request);

    @Delete
    void delete(Request request);
}
