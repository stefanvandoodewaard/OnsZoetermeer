package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import nl.zoetermeer.onszoetermeer.helpers.RequestTypeConverter;

@Entity(tableName = "REQUESTS", foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "ID",
                childColumns = "USER_ID",
                onDelete = ForeignKey.CASCADE
        )})
public class Request
{
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int ID;
    @ColumnInfo(name = "USER_ID")
    @NonNull
    private int mUserId;
    @ColumnInfo(name = "DETAILS")
    private String mDetails;
    @ColumnInfo(name = "REQUEST_DATE")
    private String mRequestDate;

    @TypeConverters(RequestTypeConverter.class)
    public RequestType requestType;

    public Request(int ID, int mUserId, String mDetails, String mRequestDate) {
        this.ID = ID;
        this.mUserId = mUserId;
        this.mDetails = mDetails;
        this.mRequestDate = mRequestDate;

    }

    public enum RequestType
    {
        Maaltijd(0),
        Boodschappen(1),
        Klusjes(2),
        Vervoer(3);

        public int code;

        RequestType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = ID;
    }


    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
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
