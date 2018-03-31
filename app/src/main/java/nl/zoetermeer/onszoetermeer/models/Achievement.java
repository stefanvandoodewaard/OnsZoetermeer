package nl.zoetermeer.onszoetermeer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "ACHIEVEMENTS", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "ID",
        childColumns = "USER_ID",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Achievement
{

    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "USER_ID")
    private int userId;
    @ColumnInfo(name = "NAME")
    private String name;
    @ColumnInfo(name = "PICTURE_SOURCE")
    private String pctrSrc;

    public Achievement(String name, int userId){
        this.name = name;
        this.userId = userId;
    }

    @NonNull
    public int getID() {
        return ID;
    }

    public void setID(@NonNull int ID) {
        this.ID = ID;
    }

    @NonNull
    public int getUserId() {
        return userId;
    }

    public void setUserId(@NonNull int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPctrSrc() {
        return pctrSrc;
    }

    public void setPctrSrc(String pctrSrc) {
        this.pctrSrc = pctrSrc;
    }


}
