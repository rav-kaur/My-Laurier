package com.example.mylaurier;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class StudySpacesFragment extends Fragment {

    ListView listView;
    // array for study spaces
    ArrayList<StudySpace> listViewItems = new ArrayList<StudySpace>();
    // hard code items, TO-DO firebase db implentation
    FirebaseDatabase database;
    DatabaseReference reference;

    // study space class
    public class StudySpace {
        // title to be shown
        String title;
        // number of seats
        int seats;
        // building location
        String location;
        // room number/ location
        String room;
        // store image
        String image;

        public StudySpace(String title, int seats, String location, String room, String image) {
            this.title = title;
            this.seats = seats;
            this.location = location;
            this.room = room;
            this.image = image;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image){
            this.image = image;
        }

        public String getLocation() {
            return location;
        }

        public String getTitle() {
            return title;
        }

        public int getSeats() {
            return seats;
        }

        public String getRoom() {
            return room;
        }
    }
    // custom arrayadapter
    public class StudyAdapter extends ArrayAdapter<StudySpace>{
        private Context context;
        private ArrayList<StudySpace> studySpaces;
        public StudyAdapter(@NonNull Context context, ArrayList<StudySpace> studySpaces){
            super(context, 0, studySpaces);
            this.context = context;
            this.studySpaces = studySpaces;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null)
                row = LayoutInflater.from(context).inflate(R.layout.study_spaces, parent,false);

            final StudySpace currentSS = studySpaces.get(position);

            ImageView image = (ImageView)row.findViewById(R.id.studyPicture);

            System.out.println(currentSS.getImage());
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final View rowFinal = row;
            final ImageView imageFinal = image;

            storageReference.child(currentSS.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    System.out.println("TEST: "+ uri);
                    Glide.with(rowFinal.getContext()).load(uri).into(imageFinal);
                }
            });
            System.out.println("CURRENT IMAGE: "+ currentSS.getImage());
            //Glide.with(row).load(currentSS.getImage()).error(R.drawable.calender).into(image);

            //image.setImageResource(currentSS.getImage());

            TextView location = (TextView)row.findViewById(R.id.studyLocation);
            if (currentSS.getRoom().compareTo("")==0)
                location.setText(currentSS.getTitle());
            else
                location.setText(currentSS.getRoom());

            TextView description = (TextView)row.findViewById(R.id.studyDescription);
            String desc = "";
            if (currentSS.getSeats() > 0)
                desc += "Study Room\nSeats: " + currentSS.getSeats() + "\n" + currentSS.getLocation();
            else
                desc += "Study Space\nLimited Seating Available\n" + currentSS.getLocation();
            description.setText(desc);

            return row;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.fragment_spaces, container, false);
        //
        listView = (ListView)view.findViewById(R.id.spacesListView);
        // hard code spaces
//        listViewItems.add(new StudySpace("Laurier Library", 6, "Library", "4-406", R.drawable.studyspace));
//        listViewItems.add(new StudySpace("Lazaridis Hall", 4, "Lazaridis Hall", "LH2063", R.drawable.studyspace));
//        listViewItems.add(new StudySpace("Solarium", -1, "Fred Nichols Campus Centre", "", R.drawable.studyspace));
//        listViewItems.add(new StudySpace("The 24 Lounge", -1, "Above the Terrace Food Court", "", R.drawable.studyspace));
//        listViewItems.add(new StudySpace("Science Atrium", -1, "Science Building", "", R.drawable.studyspace));
//
//
//        ArrayList<String> locations = new ArrayList<String>();
//        for (int i = 0; i < listViewItems.size(); i++){
//            locations.add(listViewItems.get(i).title);
//        }
//        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.study_spaces, R.id.studyLocation, locations);
//        listView.setAdapter(locationAdapter);
//        StudyAdapter studyAdapter = new StudyAdapter(getContext(), listViewItems);
//        listView.setAdapter(studyAdapter);
        System.out.println("1PLEAE WORK");
        System.out.println("2PLEAE WORK");
        System.out.println("3PLEAE WORK");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        System.out.println("Hello World");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        System.out.println(reference.toString());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                System.out.println("onDataChange");
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                    System.out.println("PLEAE WORK");
//                    Log.d("TESTS", ds.toString());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("TEST", databaseError.getMessage());
//            }
//        });
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listViewItems.clear();
                System.out.println("WORKING");
                System.out.println(dataSnapshot.getChildrenCount());
                // loop through parent
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    System.out.println(ds.toString());
                    // loop through buildings
                    for (DataSnapshot buildings : ds.getChildren()){
                        System.out.println(buildings.toString());
                        // check if building has study rooms
                        if (buildings.hasChild("rooms")){
                            DataSnapshot rooms = buildings.child("rooms");
                            for (DataSnapshot room : rooms.getChildren()){
                                System.out.println("Building: " + buildings.child("location").getValue());
                                System.out.println("Room: " +  room.toString());
                                System.out.println("Name: " + room.child("name").getValue());
                                System.out.println("Seats: " + room.child("seats").getValue().toString());
                                System.out.println("room: " + room.child("room").getValue());
                                StudySpace newStudySpace = new StudySpace(room.child("name").getValue().toString(), ((Number)room.child("seats").getValue()).intValue(),
                                        buildings.child("location").getValue().toString() ,room.child("room").getValue().toString(), room.child("imageURL").getValue().toString());
                                listViewItems.add(newStudySpace);
                                //StudyAdapter studyAdapter = new StudyAdapter(getContext(), listViewItems);
                                //listView.setAdapter(studyAdapter);
                            }
                            //StudySpace newStudySpace = new StudySpace(buildings.child("name").toString())
                        }
                    }
                }
                class SortByBuildingName implements Comparator<StudySpace> {
                    @Override
                    public int compare(StudySpace o1, StudySpace o2) {
                        if (o1.getLocation().compareTo(o2.getLocation()) != 0){
                            return o1.getLocation().compareTo(o2.getLocation());
                        }
                        else {
                            return o1.getTitle().compareTo(o2.getTitle());
                        }
                    }
                }
                listViewItems.sort(new SortByBuildingName());
                StudyAdapter studyAdapter = new StudyAdapter(getContext(), listViewItems);
                listView.setAdapter(studyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("TEST", databaseError.getMessage());
            }
        });
    }
}