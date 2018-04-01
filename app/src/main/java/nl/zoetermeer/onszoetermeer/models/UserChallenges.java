package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "USER_CHALLENGES",
        primaryKeys = { "USER_ID", "CHALLENGE_ID" },
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
}
