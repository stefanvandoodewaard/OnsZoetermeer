package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nl.zoetermeer.onszoetermeer.models.Challenge;
import nl.zoetermeer.onszoetermeer.models.User;
import nl.zoetermeer.onszoetermeer.models.UserChallenges;

@Dao
public interface UserChallengesDAO
{
    @Query("SELECT * FROM CHALLENGES INNER JOIN USER_CHALLENGES " +
            "ON CHALLENGES.id = USER_CHALLENGES.CHALLENGE_ID " +
            "WHERE USER_CHALLENGES.USER_ID = :userId AND CHALLENGES.VITALITY_TYPE = 1")
    List<Challenge> getMentalChallengesForUser(final int userId);

    @Query("SELECT * FROM CHALLENGES INNER JOIN USER_CHALLENGES " +
            "ON CHALLENGES.id = USER_CHALLENGES.CHALLENGE_ID " +
            "WHERE USER_CHALLENGES.USER_ID = :userId AND CHALLENGES.VITALITY_TYPE = 2")
    List<Challenge> getPhysicalChallengesForUser(final int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserChallenges> userchallenges);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserChallenges userchallenges);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UserChallenges userchallenges);

    @Delete
    void delete(UserChallenges userchallenges);
}
