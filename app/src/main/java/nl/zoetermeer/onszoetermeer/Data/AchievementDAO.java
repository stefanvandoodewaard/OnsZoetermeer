package nl.zoetermeer.onszoetermeer.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.OnConflictStrategy;

import nl.zoetermeer.onszoetermeer.Models.Achievement;

@Dao
public interface AchievementDAO
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Achievement achievement);

    @Delete
    void delete(Achievement achievement);
}
