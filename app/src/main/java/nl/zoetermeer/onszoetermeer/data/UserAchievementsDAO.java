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

@Dao
public interface UserAchievementsDAO
{

    @Query("SELECT * FROM USERS INNER JOIN USER_ACHIEVEMENTS " +
            "ON USERS.ID = USER_ACHIEVEMENTS.USER_ID " +
            "WHERE USER_ACHIEVEMENTS.ACHIEVEMENT_ID = :achievID")
    List<User> getUsersWithAchievement(final int achievID);

    @Query("SELECT * FROM ACHIEVEMENTS INNER JOIN USER_ACHIEVEMENTS " +
            "ON ACHIEVEMENTS.id = USER_ACHIEVEMENTS.ACHIEVEMENT_ID " +
            "WHERE USER_ACHIEVEMENTS.USER_ID = :userId")
    List<Achievement> getAchievementsForUser(final int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserAchievementsDAO userAchievementsDAO);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserAchievementsDAO userAchievementsDAO);

    @Delete
    void delete(UserAchievementsDAO userAchievementsDAO);
}
