package nl.zoetermeer.onszoetermeer.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

import java.sql.Date;

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
    @ColumnInfo(name = "USER_ID") public int mUserId;
    @ColumnInfo(name = "ACHIEVEMENT_ID") public int mAchievementId;
    @ColumnInfo(name = "ACHIEVEMENT_DATE") public Date mAchievementDate;

    public UserAchievements(int mUserId, int mAchievementId, Date mAchievementDate) {
        this.mUserId = mUserId;
        this.mAchievementId = mAchievementId;
        this.mAchievementDate = mAchievementDate;
    }

}