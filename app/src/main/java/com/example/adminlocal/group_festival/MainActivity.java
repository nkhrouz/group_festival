package com.example.adminlocal.group_festival;
import android.os.Bundle;
import android.widget.Spinner;
import 	java.nio.charset.Charset;
import java.net.URL;
import android.text.Html;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import org.apache.commons.io.IOUtils;
import 	java.io.IOException;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.view.View;
import android.util.Log;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.EditText;
import java.util.Calendar;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.lang.String;
import com.android.volley.toolbox.JsonObjectRequest;
import java.text.SimpleDateFormat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    //An ArrayList for Spinner Items
    private ArrayList<String> data;
    //JSON Array
    private JSONArray result;
    private Button btnDisplay;
    // spiner list des scenes
    private Spinner spScene;
    // Edit Box
    private EditText txtJour;
    private TextView text;
    private ArrayList<String> listGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        spScene = (Spinner) findViewById(R.id.scene);
        spinner = (Spinner) findViewById(R.id.spinner);
        txtJour = (EditText) findViewById(R.id.jour);
        DBHandler db = new DBHandler(this);
//        db.deleteGroup();
        // Stockage en BDD des noms des groupes
        getListGroup(db);
        List<String> groupList = db.getAllGroupsName();

        // sauvegarde des details des artistes en BDD
        for (int i = 0; i < groupList.size(); i++) {
            String group_name = groupList.get(i);
            String url= "http://daviddurand.info/D228/festival/info/"+ group_name;
            getGroupDetail(url,i,db);
        }
        List<Group> groupdetail = db.getAllGroupsDetail();





        // Spinner scene
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String group_name = parent.getItemAtPosition(position).toString(); //this is your selected item
                String url= "http://daviddurand.info/D228/festival/info/"+ group_name;
//                getGroupDetail(url);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        // Spinner list group
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String group_name = parent.getItemAtPosition(position).toString(); //this is your selected item
                String url= "http://daviddurand.info/D228/festival/info/"+ group_name;
//                getGroupDetail(url);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //Edit Text
        txtJour.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(MainActivity.this, new OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar calendar=Calendar.getInstance();
                        calendar.set(Calendar.YEAR, selectedyear);
                        calendar.set(Calendar.MONTH, selectedmonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        txtJour.setText(sdf.format(calendar.getTime()));
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();  }
        });


    }


    /**
     * get list group
     * */
    private void getListGroup(final DBHandler db) {
        // json object response url
        String urlJsonObj = "http://daviddurand.info/D228/festival/liste";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    // Parsing json object response
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<String> list = new ArrayList<String>();
                    list.add("Selectionner un Artiste");

                    for (int i = 0; i < data.length(); i++) {
                        list.add(data.getString(i));
                        Group group = new Group();
                        group.setArtiste(data.getString(i));
                        group.setId(i);
                        db.addGroup(group);
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
    private void getGroupDetail(String urlJsonDetail,final int id,final DBHandler db) {



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonDetail, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

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

                    Group group = new Group();
                    group.setArtiste(artiste);
                    group.setId(id);
                    group.setHeure(heure);
                    group.setImage(image);
                    group.setTexte(texte);
                    group.setScene(scene);
                    group.setJour(jour);
                    db.updateObject(group);

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
        text = (TextView) findViewById(R.id.textview);

        btnDisplay.setOnClickListener(new OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {


            }
        });
    }


//    public interface CallBack {
//        void onSuccess( ArrayList<String> list) throws JSONException;
//
//        void onFail(String msg);
//    }



//
//    getListGroup(new CallBack(){
//        @Override
//        public void onSuccess(ArrayList<String> list)  {
//
//            for (int i = 1; i < list.size(); i++) {
////                    String url_name= "http://daviddurand.info/D228/festival/info/"+list.get(i);
//
//
//            }
//        }
//
//        @Override
//        public void onFail(String msg) {
//            // Do Stuff
//        }
//    });




}

