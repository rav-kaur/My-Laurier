package com.example.mylaurier;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import android.view.View;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;



import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;



public class ScheduleFragment extends Fragment {

    TextView textDisplay1;
    TextView textDisplay2;
    TextView textDisplay3;
    TextView textDisplay4;
    TextView textDisplay5;
    TextView monday1;
    TextView time_mon1;
    TextView monday2;
    TextView monday3;
    TextView monday4;
    TextView time_mon2;
    TextView time_mon3;
    TextView time_mon4;
    TextView count_mon1;
    TextView count_mon2;
    TextView count_mon3;
    TextView count_mon4;
    TableLayout table1;
    TableLayout table2;
    TableLayout table3;
    TableLayout table4;
    TableLayout table5;
    public int mon_count = 1;
    public int tues_count = 1;
    public int wed_count =1;
    public int thu_count = 1;
    public int fri_count = 1;

    public String text;
    public int count;
    public String [] courseInfo;
    public  ArrayList<String> Monday = new ArrayList<String>();
    public ArrayList<String> Tuesday = new ArrayList<String>();
    public ArrayList<String> Wednesday = new ArrayList<String>();
    public ArrayList<String> Thursday = new ArrayList<String>();
    public ArrayList<String> Friday = new ArrayList<String>();


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void getCourseList(){
        DocumentReference user = db.collection("users").document("kaur9100");



        user.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
            @Override
            public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    text = doc.get("coursesArray").toString();
                    text = text.substring(1, text.length()-1);
                    String [] courseList = text.replace("\n", "").replace(" ", "").split(",");


                    courseInfo = new String[courseList.length];
                    for (int i = 0; i < courseList.length; i++) {
                        ReadUser(courseList[i]);
                    }

                    for (int i = 0; i < courseInfo.length; i++){
                        Toast.makeText(getActivity(), courseInfo[i], Toast.LENGTH_SHORT).show();
                    }


                }
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }


    public void ReadUser(String course) {

        DocumentReference user = db.collection("users").document("kaur9100").collection("courses").document(course);

        user.get().addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
            @Override
            public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    StringBuilder fields = new StringBuilder("");
                    //fields.append("Color: ").append(doc.get("color"));
                    fields.append("\n").append(doc.get("courseID"));
                    fields.append("\n").append(doc.get("courseName"));
                    //fields.append("\nDays: ").append(doc.get("days"));
                    fields.append("\nLocation: ").append(doc.get("location"));
                    //fields.append("\nTime: ").append(doc.get("time"));
                    String t = fields.toString();
                    t = t.substring(1, t.length());
                    courseInfo[count] = t;


                    String days = doc.get("days").toString();
                    days = days.substring(1, days.length()-1);
                    String [] daylist = days.replace(" ", "").split(",");


                    //time.setText(daylist.toString());
                    for (int i = 0; i < daylist.length; i++) {
                        //Toast.makeText(getActivity(), daylist[0], Toast.LENGTH_SHORT).show();
                        if (daylist[i].equals("Monday")) {

                            //TableRow row_m = new TableRow(getActivity());
                            TextView monday = new TextView(getActivity());
                            TextView time = new TextView(getActivity());
                            TextView count = new TextView(getActivity());
                            TableRow row = new TableRow(getActivity());

                            count.setText(String.valueOf(mon_count));
                            count.setPadding(30,30,20,30);
                            count.setTextColor(Color.BLACK);
                            count.setTextSize(16);
                            //monday.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            monday.setText(t);
                            monday.setPadding(10,30,300,30);
                            monday.setTextColor(Color.BLACK);
                            monday.setTextSize(16);

                            time.setText(doc.get("time").toString());
                            time.setPadding(50,10,10,10);
                            row.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            row.setPadding(50,50,100,50);
                            time.setTextColor(Color.BLACK);
                            time.setTextSize(16);
                            row.addView(count);
                            row.addView(time);
                            row.addView(monday);


                            //row_m.addView(monday);
                            table1.addView(row);
                            mon_count++;

                            //Monday.add(t);

                            //time_mon.setText(doc.get("time").toString());
                        } else if (daylist[i].equals("Tuesday")) {

                            TextView tuesday = new TextView(getActivity());
                            TextView time = new TextView(getActivity());
                            TextView count = new TextView(getActivity());
                            TableRow row = new TableRow(getActivity());


                            count.setText(String.valueOf(tues_count));
                            count.setPadding(30,30,20,30);
                            count.setTextColor(Color.BLACK);
                            count.setTextSize(16);

                            //tuesday.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            tuesday.setText(t);
                            tuesday.setPadding(10,30,300,30);
                            tuesday.setTextColor(Color.BLACK);
                            tuesday.setTextSize(16);
                            time.setText(doc.get("time").toString());
                            row.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            time.setPadding(50,10,10,10);
                            row.setPadding(50,50,20,50);
                            time.setTextColor(Color.BLACK);
                            time.setTextSize(16);
                            row.addView(count);
                            row.addView(time);
                            row.addView(tuesday);
                            table2.addView(row);
                            tues_count++;
                            //Tuesday.add(t);

                            //time_mon.setText(doc.get("time").toString());
                        } else if (daylist[i].equals("Wednesday")) {

                            TextView wednesday = new TextView(getActivity());

                            TextView time = new TextView(getActivity());
                            TextView count = new TextView(getActivity());
                            TableRow row = new TableRow(getActivity());


                            count.setText(String.valueOf(wed_count));
                            count.setPadding(30,30,20,30);
                            count.setTextColor(Color.BLACK);
                            count.setTextSize(16);


                            //wednesday.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            wednesday.setText(t);
                            wednesday.setPadding(10,30,300,30);
                            wednesday.setTextColor(Color.BLACK);
                            wednesday.setTextSize(16);

                            time.setText(doc.get("time").toString());
                            row.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            row.setPadding(50,50,20,50);
                            time.setTextColor(Color.BLACK);
                            time.setTextSize(16);
                            row.addView(count);
                            row.addView(time);
                            row.addView(wednesday);

                            wed_count++;
                            table3.addView(row);

                            //Wednesday.add(t);

                            //time_mon.setText(doc.get("time").toString());
                        } else if (daylist[i].equals("Thursday")) {

                            TextView thursday = new TextView(getActivity());

                            TextView time = new TextView(getActivity());
                            TextView count = new TextView(getActivity());
                            TableRow row = new TableRow(getActivity());

                            //thursday.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            thursday.setText(t);
                            thursday.setPadding(10,30,300,30);
                            thursday.setTextColor(Color.BLACK);
                            thursday.setTextSize(16);

                            count.setText(String.valueOf(thu_count));
                            count.setPadding(30,30,20,30);
                            count.setTextColor(Color.BLACK);
                            count.setTextSize(16);

                            time.setText(doc.get("time").toString());
                            row.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            row.setPadding(50,50,20,50);
                            time.setTextColor(Color.BLACK);
                            time.setTextSize(16);
                            row.addView(count);
                            row.addView(time);
                            row.addView(thursday);

                            thu_count++;

                            table4.addView(row);
                            //Thursday.add(t);

                            //time_mon.setText(doc.get("time").toString());
                        } else if (daylist[i].equals("Friday")) {

                            TextView friday = new TextView(getActivity());


                            TextView time = new TextView(getActivity());
                            TextView count = new TextView(getActivity());
                            TableRow row = new TableRow(getActivity());

                            count.setText(String.valueOf(fri_count));
                            count.setPadding(30,30,20,30);
                            count.setTextColor(Color.BLACK);
                            count.setTextSize(16);

                            //friday.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            friday.setText(t);
                            friday.setPadding(10,30,300,30);
                            friday.setTextColor(Color.BLACK);
                            friday.setTextSize(16);

                            time.setText(doc.get("time").toString());
                            row.setBackgroundColor(Color.parseColor(doc.get("color").toString()));
                            row.setPadding(50,50,20,50);
                            time.setTextColor(Color.BLACK);
                            time.setTextSize(16);
                            row.addView(count);
                            row.addView(time);
                            row.addView(friday);

                            fri_count++;


                            table5.addView(row);
                            //Friday.add(t);

                            //time_mon.setText(doc.get("time").toString());
                        }
                        //}
                        //Toast.makeText(getActivity(), daylist[i], Toast.LENGTH_SHORT).show();

                    }

                    int mon_size = Monday.size();
                    Toast.makeText(getActivity(), String.valueOf(mon_size), Toast.LENGTH_SHORT).show();
//                    for (int x = 0;x<mon_size;x++){
//                        TextView title = new TextView(getActivity());
//                        title.setText("Tuesday");
//                        title.setTextSize(20);
//                        TableRow row_m = new TableRow(getActivity());
//                        TextView monday = new TextView(getActivity());
//                        monday.setText(Monday.get(x));
//                        row_m.addView(monday);
//                        table1.addView(row_m);
//                        table1.addView(tit);









//                   for (int x = 0;x<Monday.size();x++){
//                        if (x==0){
//                            monday1.setText(Monday.get(x));
//                            time_mon1.setText(doc.get("time").toString());
//                            count_mon1.setText(String.valueOf(x+1));
//                            //int color = Integer.parseInt(doc.get("color").toString());
//                            //Toast.makeText(getActivity(), String.valueOf(color), Toast.LENGTH_SHORT).show();
//                            //monday1.setBackgroundColor(color);
//                        } else if (x==1){
//                            monday2.setText(Monday.get(x));
//                            time_mon2.setText(doc.get("time").toString());
//                            count_mon2.setText(String.valueOf(x+1));
//                        } else if (x==2){
//                            monday3.setText(Monday.get(x));
//                            time_mon3.setText(doc.get("time").toString());
//                            count_mon3.setText(String.valueOf(x+1));
//                        }else if (x==3){
//                            monday4.setText(Monday.get(x));
//                            time_mon4.setText(doc.get("time").toString());
//                            count_mon4.setText(String.valueOf(x+1));
//
//                        }
//                    }

//                    for (int x = 0;x<Tuesday.size();x++){
//                        if (x==0){
//                            tuesday1.setText(Tuesday.get(x));
//                            time_tues1.setText(doc.get("time").toString());
//                            count_tues1.setText(String.valueOf(x+1));
//                            int color = Integer.parseInt(doc.get("color").toString());
//                            //Toast.makeText(getActivity(), String.valueOf(mon_size), Toast.LENGTH_SHORT).show();
//                            tuesday1.setBackgroundColor(color);
//                        } else if (x==1){
//                            tuesday2.setText(Monday.get(x));
//                            time_tues2.setText(doc.get("time").toString());
//                            count_tues2.setText(String.valueOf(x+1));
//                        } else if (x==2){
//                            tuesday3.setText(Monday.get(x));
//                            time_tues3.setText(doc.get("time").toString());
//                            count_tues3.setText(String.valueOf(x+1));
//                        }else if (x==3){
//                            tuesday4.setText(Monday.get(x));
//                            time_tues4.setText(doc.get("time").toString());
//                            count_tues4.setText(String.valueOf(x+1));
//
//                        }
//                    }
//
//                    for (int x = 0;x<Wednesday.size();x++){
//                        if (x==0){
//                            tuesday1.setText(Tuesday.get(x));
//                            time_tues1.setText(doc.get("time").toString());
//                            count_tues1.setText(String.valueOf(x+1));
//                            int color = Integer.parseInt(doc.get("color").toString());
//                            //Toast.makeText(getActivity(), String.valueOf(mon_size), Toast.LENGTH_SHORT).show();
//                            tuesday1.setBackgroundColor(color);
//                        } else if (x==1){
//                            tuesday2.setText(Monday.get(x));
//                            time_tues2.setText(doc.get("time").toString());
//                            count_tues2.setText(String.valueOf(x+1));
//                        } else if (x==2){
//                            tuesday3.setText(Monday.get(x));
//                            time_tues3.setText(doc.get("time").toString());
//                            count_tues3.setText(String.valueOf(x+1));
//                        }else if (x==3){
//                            tuesday4.setText(Monday.get(x));
//                            time_tues4.setText(doc.get("time").toString());
//                            count_tues4.setText(String.valueOf(x+1));
//
//                        }
//                    }



                    // monday1.setText(Monday.toString());
                    //Toast.makeText(getActivity(), Monday.toString(), Toast.LENGTH_SHORT).show();

                    //if (count==0){
                      //  textDisplay1.setText(t);
                    //} else if (count==1){
                      //  textDisplay2.setText(t);
                    //}else if (count==2){
                      //  textDisplay3.setText(t);
                    //}else if (count==3){
                      //  textDisplay4.setText(t);
                    //}else if (count==4){
                      //  textDisplay5.setText(t);
                    //}

                    count++;

                }
            }
        })


                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        //textDisplay1 = view.findViewById(R.id.textDisplay1);
        //textDisplay2 = view.findViewById(R.id.textDisplay2);
        //textDisplay3 = view.findViewById(R.id.textDisplay3);
        //textDisplay4 = view.findViewById(R.id.textDisplay4);
        //textDisplay5 = view.findViewById(R.id.textDisplay5);
//        monday1 = view.findViewById(R.id.monday1);
//        time_mon1 = view.findViewById(R.id.time_mon1);
//        count_mon1 = view.findViewById(R.id.count_mon1);
//        monday2 = view.findViewById(R.id.monday2);
//        time_mon2 = view.findViewById(R.id.time_mon2);
//        count_mon2 = view.findViewById(R.id.count_mon2);
//        monday3 = view.findViewById(R.id.monday3);
//        time_mon3 = view.findViewById(R.id.time_mon3);
//        count_mon3 = view.findViewById(R.id.count_mon3);
//        monday4 = view.findViewById(R.id.monday4);
//        time_mon4 = view.findViewById(R.id.time_mon4);
//        count_mon4 = view.findViewById(R.id.count_mon4);
//
//        count = 0;
        table1 = view.findViewById(R.id.simpleTableLayout);
        table2 = view.findViewById(R.id.secondTableLayout);
        table3 = view.findViewById(R.id.thirdTableLayout);
        table4 = view.findViewById(R.id.fourthTableLayout);
        table5 = view.findViewById(R.id.lastTableLayout);
        getCourseList();

        return view;
    }

}