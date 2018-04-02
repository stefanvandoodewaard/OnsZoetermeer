package nl.zoetermeer.onszoetermeer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nl.zoetermeer.onszoetermeer.models.Challenge;

@Dao
public interface ChallengeDAO
{

    @Query("SELECT * FROM CHALLENGES")
    List<Challenge> getAllChallenges();

    @Query("SELECT * FROM CHALLENGES WHERE ID = :ID")
    Challenge getByID(int ID);

    @Query("SELECT * FROM CHALLENGES WHERE VITALITY_TYPE = 1 ORDER BY NAME")
    List<Challenge> getMentalChallenges();

    @Query("SELECT * FROM CHALLENGES WHERE VITALITY_TYPE = 2 ORDER BY NAME")
    List<Challenge> getPhysicalChallenges();

    @Query("DELETE FROM CHALLENGES") // TBV opschonen/testen
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Challenge challenge);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Challenge> challenges);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Challenge challenge);

    @Delete
    void delete(Challenge challenge);
}
