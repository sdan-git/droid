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

public class Contract extends ListActivity {

    //Пересенная типа массив э
    ArrayList<HashMap<String, String>> contractList;
    JSONParser jParser = new JSONParser();
    JSONArray contracts;

    // url to get all products list
    private static String url_contract = CMN.URL+"contract/get_all_contracts.php";

    // JSON Node names
    private static final String TAG_DATA = "bo";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);


        // Hashmap for ListView
         contractList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadDataFormJSON().execute();

        // Get listview
        ListView lv = getListView();
    }


class LoadDataFormJSON extends AsyncTask <String, String, String> {
    @Override
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_contract, "GET", params);
        // Check your log cat for JSON reponse
        //Log.d("All Products: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                contracts = json.getJSONArray(TAG_DATA);

                // looping through All Products
                for (int i = 0; i < contracts.length(); i++) {
                    JSONObject c = contracts.getJSONObject(i);

                    // Storing each json item in variable
                    //String id = c.getString("id");
                    //String name = c.getString(TAG_NAME);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("id", c.getString("id"));
                    map.put("begin_date", c.getString("begin_date"));
                    map.put("end_date", c.getString("end_date"));
                    map.put("student_id", c.getString("student_id"));
                    map.put("tarif_id", c.getString("tarif_id"));

                    // adding HashList to ArrayList
                    contractList.add(map);
                }
            } else {
                // no products found
                // Launch Add New product Activity
                Intent i = new Intent(getApplicationContext(),
                        NewContractActivity.class);
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
                        Contract.this, contractList,
                        R.layout.activity_contract_list, new String[] { TAG_ID,
                        "begin_date",
                        "end_date",
                        "sudent_id"},
                        new int[] { R.id.id, R.id.begin_date , R.id.end_date, R.id.sudent_id});
                // updating listview
                setListAdapter(adapter);
            }
        });

    }
}

}