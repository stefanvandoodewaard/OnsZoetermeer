package nl.zoetermeer.onszoetermeer.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

import nl.zoetermeer.onszoetermeer.Helpers.GenderConverter;

@Entity(tableName = "USERS")
public class User
{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "EMAIL")
    @NonNull
    private String m_email;
    @ColumnInfo(name = "PASSWORD")
    @NonNull
    private String m_password;
    @ColumnInfo(name = "GENDER")
    @TypeConverters(GenderConverter.class)
    public Gender gender;
    @ColumnInfo(name = "FIRST_NAME")
    private String m_first_name;
    @ColumnInfo(name = "LAST_NAME")
    private String m_last_name;
    @ColumnInfo(name = "PICTURE_SOURCE")
    private String m_picture_src;
    @ColumnInfo(name = "POSTAL_CODE")
    private String m_postal_code;
    @ColumnInfo(name = "GPS")
    private String m_GPS;
    @ColumnInfo(name = "VITALITY_MENTAL")
    private String m_vit_ment;
    @ColumnInfo(name = "VITALITY_PHYSICAL")
    private String m_vit_phys;
    @ColumnInfo(name = "LAST_ACTIVE")
    private Date m_last_active;

    public User() {
    }

    public enum Gender
    {
        Onbekend(0),
        Man(1),
        Vrouw(2);

        public int code;

        Gender(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password;
    }

    public String getM_first_name() {
        return m_first_name;
    }

    public void setM_first_name(String m_first_name) {
        this.m_first_name = m_first_name;
    }

    public String getM_last_name() {
        return m_last_name;
    }

    public void setM_last_name(String m_last_name) {
        this.m_last_name = m_last_name;
    }

    public String getM_picture_src() {
        return m_picture_src;
    }

    public void setM_picture_src(String m_picture_src) {
        this.m_picture_src = m_picture_src;
    }

    public String getM_postal_code() {
        return m_postal_code;
    }

    public void setM_postal_code(String m_postal_code) {
        this.m_postal_code = m_postal_code;
    }

    public String getM_GPS() {
        return m_GPS;
    }

    public void setM_GPS(String m_GPS) {
        this.m_GPS = m_GPS;
    }

    public String getM_vit_ment() {
        return m_vit_ment;
    }

    public void setM_vit_ment(String m_vit_ment) {
        this.m_vit_ment = m_vit_ment;
    }

    public String getM_vit_phys() {
        return m_vit_phys;
    }

    public void setM_vit_phys(String m_vit_phys) {
        this.m_vit_phys = m_vit_phys;
    }

    public Date getM_last_active() {
        return m_last_active;
    }

    public void setM_last_active(Date m_last_active) {
        this.m_last_active = m_last_active;
    }
}