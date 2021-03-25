package com.example.arhangel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button buttonContract;
    Button buttonJournal;
    MyAsyncTask myAsyncTask;

    private class MyAsyncTask extends AsyncTask<String, Void, Void> {
        String mTAG = "myAsyncTask";

        @Override
        protected void onPreExecute() {
            //mProgressBar.progressiveStart();
        }

        @Override
        protected Void doInBackground(String... arg) {
String typeForm =arg[0];
if (typeForm=="Contract") {
                Intent i = new Intent(getApplicationContext(), Contract.class);
    startActivity(i);
            }
else{
                Intent i = new Intent(getApplicationContext(), Journal.class);
    startActivity(i);
            };





       /*     try {
                getData();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return null;
        }


        @Override
        protected void onPostExecute(Void a) {
            //mProgressBar.progressiveStop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        buttonContract = (Button) findViewById(R.id.btnContract);

        buttonContract.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity


                // TODO Auto-generated method stub
                myAsyncTask=new MyAsyncTask();
                // TODO Auto-generated method stub
                myAsyncTask.execute("Contract");


                //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                //startActivity(i);

            }
        });

        buttonJournal = (Button) findViewById(R.id.btnJournal);

        buttonJournal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity


                // TODO Auto-generated method stub
                myAsyncTask=new MyAsyncTask();
                // TODO Auto-generated method stub
                myAsyncTask.execute("Journal");


                //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                //startActivity(i);

            }
        });

    }


}