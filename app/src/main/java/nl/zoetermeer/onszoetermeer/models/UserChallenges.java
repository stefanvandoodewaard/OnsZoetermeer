package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "USER_CHALLENGES",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "ID",
                        childColumns = "USER_ID",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Challenge.class,
                        parentColumns = "ID",
                        childColumns = "CHALLENGE_ID")})
public class UserChallenges
{
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "USER_ID")
    @NonNull
    public int userId;
    @ColumnInfo(name = "CHALLENGE_ID")
    @NonNull
    public int challengeId;
    @ColumnInfo(name = "CHALLENGE_DATE")
    public Date challengeDate;

    public UserChallenges(@NonNull int userId, @NonNull int challengeId, Date challengeDate) {
        this.userId = userId;
        this.challengeId = challengeId;
        this.challengeDate = challengeDate;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    @NonNull
    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(@NonNull int challengeId) {
        this.challengeId = challengeId;
    }

    public Date getChallengeDate() {
        return challengeDate;
    }

    public void setChallengeDate(Date challengeDate) {
        this.challengeDate = challengeDate;
    }
}
