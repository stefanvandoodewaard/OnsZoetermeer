package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import nl.zoetermeer.onszoetermeer.models.Achievement;

@Dao
public interface AchievementDAO
{
    @Query("SELECT * FROM ACHIEVEMENTS")
    List<Achievement> getAllAchievements();

    @Query("SELECT * FROM ACHIEVEMENTS WHERE USER_ID = :userId")
    List<Achievement> getUsersAchievements(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achievement achievement);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Achievement> achievements);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Achievement achievement);

    @Delete
    void delete(Achievement achievement);

    @Query("DELETE FROM ACHIEVEMENTS") // TBV opschonen/testen
    void deleteAll();
}
