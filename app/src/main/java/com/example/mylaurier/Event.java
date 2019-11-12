package com.example.mylaurier;

import java.util.Date;
import java.sql.Time;
public class Event {
    Date date;
    String eventTitle, eventDescription ="";
    Time time = null;
    String location = "";

    public Event(Date date, String eventTitle, String eventDescription, Time time, String location) {
        this.date = date;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.time = time;
        this.location = location;
    }

    public Date getDate()
    {
        return this.date;
    }

    public String getEventTitle()
    {
        return this.eventTitle;
    }
    public Time getTime()
    {
        return this.time;
    }
    public String getEventDescription()
    {
        return this.eventDescription;
    }

    public String getLocation()
    {
        return this.location;
    }
}
