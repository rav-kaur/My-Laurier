package com.example.mylaurier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import android.view.View;

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

import java.util.ArrayList;


public class ScheduleFragment extends Fragment {

    TextView textDisplay1;
    TextView textDisplay2;
    TextView textDisplay3;
    TextView textDisplay4;
    TextView textDisplay5;
    public String text;
    public int count;
    public String [] courseInfo;

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
                    fields.append("Color: ").append(doc.get("color"));
                    fields.append("\nCourse ID: ").append(doc.get("courseID"));
                    fields.append("\nCourse Name: ").append(doc.get("courseName"));
                    fields.append("\nDays: ").append(doc.get("days"));
                    fields.append("\nLocation: ").append(doc.get("location"));
                    fields.append("\nTime: ").append(doc.get("time"));
                    String t = fields.toString();
                    courseInfo[count] = t;

                    if (count==0){
                        textDisplay1.setText(t);
                    } else if (count==1){
                        textDisplay2.setText(t);
                    }else if (count==2){
                        textDisplay3.setText(t);
                    }else if (count==3){
                        textDisplay4.setText(t);
                    }else if (count==4){
                        textDisplay5.setText(t);
                    }

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
        textDisplay1 = view.findViewById(R.id.textDisplay1);
        textDisplay2 = view.findViewById(R.id.textDisplay2);
        textDisplay3 = view.findViewById(R.id.textDisplay3);
        textDisplay4 = view.findViewById(R.id.textDisplay4);
        textDisplay5 = view.findViewById(R.id.textDisplay5);
        count = 0;
        getCourseList();

        return view;
    }

}