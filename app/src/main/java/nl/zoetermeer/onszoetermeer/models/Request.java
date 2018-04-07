package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

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
    private int userId;
    @ColumnInfo(name = "DETAILS")
    private String details;
    @ColumnInfo(name = "REQUEST_DATE")
    private Date requestDate;

    @TypeConverters(RequestTypeConverter.class)
    public RequestType requestType;

    public Request(int userId, String details, Date requestDate, RequestType requestType) {
        this.userId = userId;
        this.details = details;
        this.requestDate = requestDate;
        this.requestType = requestType;

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

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
