package com.example.mylaurier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class ScheduleFragment extends Fragment {

    ArrayList scheduleName = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);




        ListView listView = (ListView) view.findViewById(R.id.schedule);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, scheduleName);

        listView.setAdapter(arrayAdapter);
        scheduleName.add("CP164\nTime: 1:30pm\nLocation: BA101");
        scheduleName.add("CP311\nTime: 2:30pm\nLocation: N1001");
        scheduleName.add("CP222\nTime: 4:30pm\nLocation: LH2101");
        scheduleName.add("MA299\nTime: 7:30pm\nLocation: N1201");
        arrayAdapter.notifyDataSetChanged();
        return view;
    }
}