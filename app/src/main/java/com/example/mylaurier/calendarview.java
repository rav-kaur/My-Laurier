package com.example.mylaurier;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

public class calendarview extends AppCompatActivity {

    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    ArrayList <Event> eventList = new ArrayList<>();
    ArrayList eventName = new ArrayList();
    Time time1 = new Time(10,30,0);
    Date date1 = new Date(2019,10,22);

    Event randEvent1 = new Event(date1,"Guess Speaker","Today there will be a guess speaker",time1,"BA101");
    Time time2 = new Time(1,45,0);
    Date date2 = new Date(2019,10,20);
    Event randEvent2 = new Event(date2,"Graduation Party","Graduation Party for John Smith",time2,"101 Somewhere Street");

    Time time3 = new Time(5,11,0);
    Date date3 = new Date(2019,10,22);
    Event randEvent3 = new Event(date3,"Birthday Party","Brithday Party for Annie Smith",time3,"55 Somewhere Street");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        eventList.add(randEvent1);
        eventList.add(randEvent2);
        eventList.add(randEvent3);

        ListView listView = (ListView) findViewById(R.id.eventList);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventName);



        listView.setAdapter(arrayAdapter);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/" + dayOfMonth;
                eventName.clear();
                for (int i = 0; i < eventList.size(); i++) {
                    Date tempDate = new Date(year, month, dayOfMonth);
                    Log.d("Date 1", eventList.get(i).getDate().toString());
                    Log.d("Date 2", tempDate.toString());
                    if(eventList.get(i).getDate().toString().equals(tempDate.toString()))
                    {
                        //DO SOMETHING

                        String hours = Integer.toString(eventList.get(i).getTime().getHours());
                        String mins = Integer.toString(eventList.get(i).getTime().getMinutes());
                        String location = eventList.get(i).getLocation();
                        String eventDescription = eventList.get(i).getEventDescription();
                        if (eventList.get(i).getTime().getHours() > 9) {
                            eventName.add("Time: " + hours + ":" + mins + "\nTitle: " + eventList.get(i).getEventTitle() + "\nLocation: " + location + "\nDetails: " + eventDescription);
                        }
                        else
                        {
                            eventName.add("Time: 0" + hours + ":" + mins + "\nTitle: " + eventList.get(i).getEventTitle() + "\nLocation: " + location + "\nDetails: " + eventDescription);

                        }


                    }
                    arrayAdapter.notifyDataSetChanged();
                }
                Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
                Intent intent = new Intent(calendarview.this, calendar.class);
                intent.putExtra("date", date);
                //startActivity(intent);

            }
        });

    }

}

