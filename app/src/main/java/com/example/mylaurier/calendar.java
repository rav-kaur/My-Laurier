package com.example.mylaurier;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class calendar extends AppCompatActivity implements View.OnClickListener {

    Button button;
//    Intent intent = new Intent(calendar.this,calendarview.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        button = (Button) findViewById(R.id.btncal);
        button.setOnClickListener(this);

    }


    Cursor cursor;

    @Override
    public void onClick(View v) {
        Log.d("TEST", "Hello World");
        switch (v.getId()) {
            case R.id.btncal:
                Intent intent = new Intent(calendar.this,calendarview.class);
                startActivity(intent);
                if (checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }


                cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);
                while(cursor.moveToNext()){
                    if(cursor!=null){
                        int id_1 = cursor.getColumnIndex(CalendarContract.Events._ID);
                        int id_2 = cursor.getColumnIndex(CalendarContract.Events.TITLE);
                        int id_3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
                        int id_4 = cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);

                        String id_value = cursor.getString(id_1);
                        String title_value = cursor.getString(id_2);
                        String desc_value = cursor.getString(id_3);
                        String location_value = cursor.getString(id_4);

                        Toast.makeText(this, id_value+", "+title_value+", "+desc_value+", "+location_value, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Event Is Not Present",Toast.LENGTH_SHORT).show();
                    }
                }


               break;
        }
    }
}
