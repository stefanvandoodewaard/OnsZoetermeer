package nl.zoetermeer.onszoetermeer.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "ACHIEVEMENTS")
public class Achievement
{

    @PrimaryKey(autoGenerate = true) private int ID;
    @ColumnInfo(name = "NAME") private String mName;
    @ColumnInfo(name = "PICTURE_SOURCE") String mPctrSrc;

    public Achievement(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
