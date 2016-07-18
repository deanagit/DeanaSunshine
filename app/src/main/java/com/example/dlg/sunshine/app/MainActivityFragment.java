package com.example.dlg.sunshine.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
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

        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));
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

// Get reference to the ListView and attach the adapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);


        return rootView;
    }

       public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {

           private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

           @Override
           protected Void doInBackground(Void... params){

               // These two need to be declared outside the try/catch
               // so that they can be closed in the finally block.
               HttpURLConnection urlConnection = null;
               BufferedReader reader = null;

               // Will contain the raw JSON response as a string.
               String forecastJsonStr = null;

               try {
                   // Construct the URL for the OpenWeatherMap query
                   // Possible parameters are avaiable at OWM's forecast API page, at
                   // http://openweathermap.org/API#forecast
                   URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Chicago&appid=beb2a32a485a0bbc1ed6873fb69f33fc&units=metric&cnt=7");

                   // Create the request to OpenWeatherMap, and open the connection
                   urlConnection = (HttpURLConnection) url.openConnection();
                   urlConnection.setRequestMethod("GET");
                   urlConnection.connect();

                   // Read the input stream into a String
                   InputStream inputStream = urlConnection.getInputStream();
                   StringBuffer buffer = new StringBuffer();
                   if (inputStream == null) {
                       // Nothing to do.
                       return null;
                   }
                   reader = new BufferedReader(new InputStreamReader(inputStream));

                   String line;
                   while ((line = reader.readLine()) != null) {
                       // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                       // But it does make debugging a *lot* easier if you print out the completed
                       // buffer for debugging.
                       buffer.append(line + "\n");
                   }

                   if (buffer.length() == 0) {
                       // Stream was empty.  No point in parsing.
                       return null;
                   }
                   forecastJsonStr = buffer.toString();
               } catch (IOException e) {
                   Log.e(LOG_TAG, "Error ", e);
                   // If the code didn't successfully get the weather data, there's no point in attemping
                   // to parse it.
                   return null;
               } finally{
                   if (urlConnection != null) {
                       urlConnection.disconnect();
                   }
                   if (reader != null) {
                       try {
                           reader.close();
                       } catch (final IOException e) {
                           Log.e(LOG_TAG, "Error closing stream", e);
                       }
                   }
               }

               return null;
           }

       }
    }

