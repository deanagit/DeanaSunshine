package com.example.dlg.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> mForecastAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
//        Create fake data
        String[] forecastArray = {
                "Mon 7/11 - Sunny - 95/75",
                "Tue 7/12 - Foggy - 94/73",
                "Wed 7/13 - Cloudy - 78/69",
                "Thurs 7/14 - Rainy - 98/80",
                "Fri 7/15 - Gross - 100/80",
                "Sat 7/16 - Hot as Hell - 109/88",
                "Sun 7/17 - Sunny - 93/77"
        };

        List<String> weekForecast= new ArrayList<String>(Arrays.asList(forecastArray));
//Create an ArrayAdapter - adapter takes data from a source and populates a ListView

        mForecastAdapter =
                new ArrayAdapter<String>(
//                        Current context is this fragments parent activity
                        getActivity(),
//                        Pass in ID of list item layout file
                        R.layout.list_item_forecast,
//                        Pass in ID of the textview we want to populate with data
                        R.id.list_item_forecast_textview,
//                        Pass in the fake data
                        weekForecast);


        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);

        return rootView;
    }
}
