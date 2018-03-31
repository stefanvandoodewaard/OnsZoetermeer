package nl.zoetermeer.onszoetermeer.activities;

import android.text.format.DateUtils;
import android.text.format.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ChatMessage
{
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message)
    {
        super();
        this.left = left;
        this.message = message;
    }

    public String getCreatedAt()
    {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

        return date.format(currentLocalTime);
    }

    public String getSender()
    {
        return "Jolanda";
    }
}