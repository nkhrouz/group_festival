package com.example.adminlocal.group_festival;

import android.widget.Spinner;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.lang.String;
import com.android.volley.toolbox.JsonObjectRequest;


public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    //An ArrayList for Spinner Items
    private ArrayList<String> data;
    //JSON Array
    private JSONArray result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // json object response url
        String urlJsonObj = "http://daviddurand.info/D228/festival/liste";
        getListGroup(urlJsonObj);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String group_name = parent.getItemAtPosition(position).toString(); //this is your selected item
                String url= "http://daviddurand.info/D228/festival/info/"+ group_name;
                getGroupDetail(url);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });




    }


    /**
     * get list group
     * */
    private void getListGroup(String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                String group = null;
                try {
                    // Parsing json object response
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i < data.length(); i++) {
                        list.add(data.getString(i));

                    }
                    spinner = (Spinner) findViewById(R.id.spinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                    spinner.setAdapter(adapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    //cas d erreur a gerer

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // cas d erreur a gerer

            }
        });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

    /**
     * get group detail
     * */
    private void getGroupDetail(String urlJsonDetail) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonDetail, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                String group = null;
                try {
                    JSONObject jObject = new JSONObject();
                    // Parsing json object response
                    JSONObject data = response.getJSONObject("data");

                    String metadata =  data.get("texte").toString();
                    Log.v("SALIMA", "index=" + metadata);
//                    String test =data.toString();

                    EditText text = (EditText) findViewById(R.id.editText);
                    text.setText(metadata);



//                    spinner = (Spinner) findViewById(R.id.spinner);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
//                    spinner.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    //cas d erreur a gerer

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // cas d erreur a gerer

            }
        });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);
    }

}

