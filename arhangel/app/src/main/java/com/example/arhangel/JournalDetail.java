package com.example.arhangel;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JournalDetail extends ListActivity {

    //Пересенная типа массив э
    ArrayList<HashMap<String, String>> journalList;
    JSONParser jParser = new JSONParser();
    JSONArray journals;

    // url to get all products list
    private static String url_journal = CMN.URL + "journal/get_journal.php";

    // JSON Node names
    private static final String TAG_DATA = "bo";
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);


        // Hashmap for ListView
        journalList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new JournalDetail.LoadDataFormJSON().execute();

        // Get listview
        ListView lv = getListView();
    }


    class LoadDataFormJSON extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_journal, "GET", params);
            // Check your log cat for JSON reponse
            //Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    journals = json.getJSONArray(TAG_DATA);

                    // looping through All Products
                    for (int i = 0; i < journals.length(); i++) {
                        JSONObject c = journals.getJSONObject(i);

                        // Storing each json item in variable
                        //String id = c.getString("id");
                        //String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put("id", c.getString("id"));
                        map.put("begin_date", c.getString("begin_date"));
                        map.put("end_date", c.getString("end_date"));
                        map.put("trener_id", c.getString("trener_id"));

                        // adding HashList to ArrayList
                        journalList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            NewJournalActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            JournalDetail.this, journalList,
                            R.layout.activity_journal_detail_list, new String[]{"id",
                            "begin_date",
                            "end_date",
                            "trener_id"},
                            new int[]{R.id.id, R.id.begin_date, R.id.end_date, R.id.trener_id});
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }
    }
}

