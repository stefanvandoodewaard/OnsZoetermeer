package nl.zoetermeer.onszoetermeer.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;

@Entity(tableName = "REQUESTS",foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "USER_ID",
                onDelete = ForeignKey.CASCADE
        )})
public class Request
{
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "DETAILS") private String mDetails;
    @ColumnInfo(name = "REQUEST_DATE") private String mRequestDate;

    public Request () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmDetails() {
        return mDetails;
    }

    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public String getmRequestDate() {
        return mRequestDate;
    }

    public void setmRequestDate(String mRequestDate) {
        this.mRequestDate = mRequestDate;
    }
}
