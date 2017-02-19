package com.thevarunshah.users;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<>(); //the actual list
    ArrayAdapter<String> listAdapter = null; //how to manage the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //obtain list view and create new list custom adapter
        ListView listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        //attach adapter to list view
        listView.setAdapter(listAdapter);

        //fetch button from layout
        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //how to go from one activity to another
                //start add user activity and wait for result (in onActivityResult(...))
                Intent i = new Intent(HomeScreen.this, AddUserScreen.class);
                startActivityForResult(i, 0);
            }
        });

        //perform a get-request
        UpdateListTask task = new UpdateListTask();
        task.execute("http://01e1e14f.ngrok.io/users");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && requestCode == 0){

            //perform a get-request
            UpdateListTask task = new UpdateListTask();
            task.execute("http://01e1e14f.ngrok.io/users");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //how to run an asynchronous task in the background
    private class UpdateListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String response = "";
            for (String url : urls) {
                HttpURLConnection urlConnection = null;
                try {
                    //make get-request
                    URL _url = new URL(url);
                    urlConnection = (HttpURLConnection) _url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);

                    //parse the JSON response
                    JSONArray jsonArray = new JSONArray(response);
                    list.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        list.add(obj.getString("first") + " " + obj.getString("last"));
                    }
                }
                catch(Exception e){
                    Log.i("home screen", e.getMessage());
                }
                finally {
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            //when done with the asynchronous task, update the list view
            if(!result.equals("")){
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    //parsing the response to a string
    private String readStream(InputStream is) {

        try {
            String response = "";
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                response += s;
            }

            return response;
        } catch (IOException e) {
            return "error";
        }
    }
}
