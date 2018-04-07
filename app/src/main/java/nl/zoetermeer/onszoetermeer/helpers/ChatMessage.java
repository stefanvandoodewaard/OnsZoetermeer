package nl.zoetermeer.onszoetermeer.helpers;

import java.text.DateFormat;
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
        DateFormat date = DateFormat.getDateTimeInstance();

        return date.format(currentLocalTime);
    }

    public String getSender()
    {
        return "Jolanda";
    }
}