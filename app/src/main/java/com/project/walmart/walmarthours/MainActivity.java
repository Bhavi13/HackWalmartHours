package com.project.walmart.walmarthours;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    Button submit;
    EditText firstName;
    EditText storeNumber;
    EditText phoneNumber;
    EditText emailId;
    String storeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(this, StoreMetaData.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Manager  " +firstName.getText()+ "  added to the store  " +storeNumber.getText(),Toast.LENGTH_LONG).show();
                Intent storeMetaData = new Intent(MainActivity.this,StoreMetaData.class);
                startActivity(storeMetaData);
            }
        });

        new LongRunningGetIO().execute("");
    }

    private class LongRunningGetIO extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            InputStream in = null;
            URLConnection urlConnection = null;
            OutputStreamWriter outStreamWriter=null;
            Writer writer = null;
            BufferedReader reader = null;
            InputStream inStream = null;
            try {
                Manager man = new Manager();
                man.setFirstName("Bhavi");
                man.setLastName("Last");
                man.setEmail("abc");
                man.setPhone("123");

                String json = man.toString();
                URL url = new URL("http://172.28.90.219:8082/ca-shipment/gsf-mgmt/activate");
                urlConnection =  url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                outStreamWriter = new OutputStreamWriter(
                        urlConnection.getOutputStream());
                writer = new BufferedWriter(outStreamWriter);
                writer.write(json);
                writer.close();
                inStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = reader.read()) != -1) {
                    sb.append((char) cp);
                }
                String resJson = sb.toString();
                System.out.println("JSON response from Activate User---------> \n" + resJson);
            } catch (IOException e) {
                e.printStackTrace();
            } /*finally {
                urlConnection.disconnect();
            }*/
            return null;
        }

        protected void onPostExecute(String results) {
            if (results!=null) {
                System.out.println("Result--------->"+results);
                storeInfo = results;
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        submit = (Button)findViewById(R.id.submit);
        firstName = (EditText) findViewById(R.id.firstNameEditText);
        storeNumber = (EditText) findViewById(R.id.lastNameEditText);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberEditText);
        emailId = (EditText) findViewById(R.id.emailEditText);

    }
}
