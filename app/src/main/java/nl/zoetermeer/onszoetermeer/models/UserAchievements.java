package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.ColumnInfo;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "USER_ACHIEVEMENTS",
        primaryKeys = { "USER_ID", "ACHIEVEMENT_ID" },
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "ID",
                        childColumns = "USER_ID",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Achievement.class,
                        parentColumns = "ID",
                        childColumns = "ACHIEVEMENT_ID")})
public class UserAchievements
{
    @ColumnInfo(name = "USER_ID")
    @NonNull
    public int mUserId;
    @ColumnInfo(name = "ACHIEVEMENT_ID")
    @NonNull
    public int mAchievementId;
    @ColumnInfo(name = "ACHIEVEMENT_DATE")
    public Date mAchievementDate;

    public UserAchievements(int mUserId, int mAchievementId, Date mAchievementDate) {
        this.mUserId = mUserId;
        this.mAchievementId = mAchievementId;
        this.mAchievementDate = mAchievementDate;
    }

}
