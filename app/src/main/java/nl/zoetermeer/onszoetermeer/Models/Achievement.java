package nl.zoetermeer.onszoetermeer.Models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ACHIEVEMENT")
public class Achievement
{

    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "NAME") private String mName;
    @ColumnInfo(name = "PICTURE_SOURCE") String mPctrSrc;

    public Achievement(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPctrSrc() {
        return mPctrSrc;
    }

    public void setmPctrSrc(String mPctrSrc) {
        this.mPctrSrc = mPctrSrc;
    }
}
