package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;
import java.util.List;

import nl.zoetermeer.onszoetermeer.models.Achievement;
import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.models.UserAchievements;

@Dao
public interface UserAchievementsDAO
{

    @Query("SELECT * FROM ACHIEVEMENTS INNER JOIN USER_ACHIEVEMENTS " +
            "ON ACHIEVEMENTS.id = USER_ACHIEVEMENTS.ACHIEVEMENT_ID " +
            "WHERE USER_ACHIEVEMENTS.USER_ID = :userId " +
            "AND USER_ACHIEVEMENTS.ACHIEVEMENT_DATE IS NOT NULL")
    List<Achievement> getAchievementsForUser(final int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserAchievements> userAchievements);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserAchievements userAchievements);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserAchievements userAchievements);

    @Delete
    void delete(UserAchievements userAchievements);
}
