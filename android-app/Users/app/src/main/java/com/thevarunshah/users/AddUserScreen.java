package com.thevarunshah.users;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddUserScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //fetch button from layout
        Button submit = (Button) findViewById(R.id.submit);
        //attach on click listener to the button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext objects of the user details entered
                EditText firstName = (EditText) findViewById(R.id.first_name);
                EditText lastName = (EditText) findViewById(R.id.last_name);
                EditText email = (EditText) findViewById(R.id.email);
                EditText phone = (EditText) findViewById(R.id.phone);

                //make post request
                AddUserTask task = new AddUserTask(firstName.getText().toString(), lastName.getText().toString(),
                        email.getText().toString(), phone.getText().toString());
                task.execute("http://01e1e14f.ngrok.io/user");
            }
        });
    }

    //how to run an asynchronous task in the background
    private class AddUserTask extends AsyncTask<String, Void, String> {

        String first;
        String last;
        String email;
        String phone;

        public AddUserTask(String first, String last, String email, String phone){
            this.first = first;
            this.last = last;
            this.email = email;
            this.phone = phone;
        }

        @Override
        protected String doInBackground(String... urls) {

            String response = "";
            for (String url : urls) {
                HttpURLConnection urlConnection = null;
                try {
                    //make post-request
                    URL _url = new URL(url);
                    urlConnection = (HttpURLConnection) _url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    // Write payload
                    byte[] userPayload = getPayload(first, last, email, phone).getBytes();
                    OutputStream userOut = urlConnection.getOutputStream();
                    userOut.write(userPayload);
                    urlConnection.getResponseCode(); //this will execute the request
                }
                catch(Exception e){
                    Log.i("add user screen", e.getMessage());
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

            //when done with the asynchronous task, go back to main view
            setResult(RESULT_OK);
            finish();
        }
    }

    private String getPayload(String first, String last, String email, String phone) {

        String response = "";

        try {

            JSONObject user = new JSONObject();
            user.put("first", first);
            user.put("last", last);
            user.put("email", email);
            user.put("phone", phone);
            response = user.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}
