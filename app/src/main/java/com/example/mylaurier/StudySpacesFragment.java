package com.example.mylaurier;

import android.content.Context;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class StudySpacesFragment extends Fragment {

    ListView listView;
    // array for study spaces
    ArrayList<StudySpace> listViewItems = new ArrayList<StudySpace>();
    // hard code items, TO-DO firebase db implentation

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
        int image;

        public StudySpace(String title, int seats, String location, String room, int image) {
            this.title = title;
            this.seats = seats;
            this.location = location;
            this.room = room;
            this.image = image;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image){
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
    }
    // custom arrayadapter
    public class StudyAdapter extends ArrayAdapter<StudySpace>{
        private Context context;
        private ArrayList<StudySpace> studySpaces;
        public StudyAdapter(@NonNull Context context, @LayoutRes ArrayList<StudySpace> studySpaces){
            super(context, 0, studySpaces);
            this.context = context;
            this.studySpaces = studySpaces;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null)
                row = LayoutInflater.from(context).inflate(R.layout.study_spaces, parent,false);

            StudySpace currentSS = studySpaces.get(position);

            ImageView image = (ImageView)row.findViewById(R.id.studyPicture);

            image.setImageResource(currentSS.getImage());

            TextView location = (TextView)row.findViewById(R.id.studyLocation);
            location.setText(currentSS.getTitle());

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
        listViewItems.add(new StudySpace("Laurier Library", 6, "Library", "4-406", R.drawable.studyspace));
        listViewItems.add(new StudySpace("Lazaridis Hall", 4, "Lazaridis Hall", "LH2063", R.drawable.studyspace));
        listViewItems.add(new StudySpace("Solarium", -1, "Fred Nichols Campus Centre", "", R.drawable.studyspace));
        listViewItems.add(new StudySpace("The 24 Lounge", -1, "Above the Terrace Food Court", "", R.drawable.studyspace));
        listViewItems.add(new StudySpace("Science Atrium", -1, "Science Building", "", R.drawable.studyspace));

//        ArrayList<String> locations = new ArrayList<String>();
//        for (int i = 0; i < listViewItems.size(); i++){
//            locations.add(listViewItems.get(i).title);
//        }
//        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.study_spaces, R.id.studyLocation, locations);
//        listView.setAdapter(locationAdapter);
        StudyAdapter studyAdapter = new StudyAdapter(getContext(), listViewItems);
        listView.setAdapter(studyAdapter);
        return view;
    }
}