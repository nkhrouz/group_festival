package com.example.adminlocal.group_festival;
import android.os.Bundle;
import android.widget.Spinner;
import android.text.Html;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

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
    private Button btnDisplay;
    // checkbox
    private CheckBox chScene;
    private CheckBox chJour;
    private TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        // json object response url
        String urlJsonObj = "http://daviddurand.info/D228/festival/liste";
        getListGroup(urlJsonObj);

        addListenerOnButton();
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
//                Log.v("salima", "apres=" + pos);
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
                    list.add("Selectionner un Artiste");
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
                    // recuperation des attributs du JSON
                    String texte =  data.get("texte").toString().toString();
                    String artiste =  data.get("artiste").toString().toString();
                    String web =  data.get("web").toString().toString();
                    String scene =  data.get("scene").toString().toString();
                    String image =  data.get("image").toString().toString();
                    String jour =  data.get("jour").toString().toString();
                    String heure =  data.get("heure").toString().toString();
                    String time =  data.get("time").toString().toString();
                    TextView text = (TextView) findViewById(R.id.textview);
                    text.setText(Html.fromHtml("<h1 color='black'>"+artiste+"</h1><br />" +
                                    "<small color='black'>Scene "+scene+" Le "+jour+" a "+heure+"</small><br/>"+
                                    " <a href="+ web +"></a><br/>"+
                                    "<i>"+texte+"</i><br/>" ));

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


    public void addListenerOnButton() {

        btnDisplay = (Button) findViewById(R.id.btnDisplay);
        chScene = (CheckBox) findViewById(R.id.scene);
        chJour = (CheckBox) findViewById(R.id.jour);
        text = (TextView) findViewById(R.id.textview);

        btnDisplay.setOnClickListener(new OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {
                if(chScene.isChecked() && chJour.isChecked() ){

                    text.setText("TOUS EST CHECKER");

                }else if (chScene.isChecked()){
                    text.setText("scene");

                }else if (chJour.isChecked()){
                    text.setText("jour est checker");
                }else{
                    text.setText("rien est CHECKER");

                }

//                StringBuffer result = new StringBuffer();
//                result.append("IPhone check : ").append(chkIos.isChecked());
//                result.append("\nAndroid check : ").append(chkAndroid.isChecked());
//                result.append("\nWindows Mobile check :").append(chkWindows.isChecked());
//
//                Toast.makeText(MyAndroidAppActivity.this, result.toString(),
//                        Toast.LENGTH_LONG).show();

            }
        });
    }


    private void getAllGroup(String urlJsonDetail) {

        String urlJsonObj = "http://daviddurand.info/D228/festival/liste";
        getListGroup(urlJsonObj);
    }

}

