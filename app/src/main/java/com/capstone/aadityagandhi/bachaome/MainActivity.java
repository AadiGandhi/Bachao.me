package com.capstone.aadityagandhi.bachaome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.aadityagandhi.bachaome.Utils.DataStore;
import com.capstone.aadityagandhi.bachaome.Utils.NetworkRequest;
import com.capstone.aadityagandhi.bachaome.Utils.RegistrationIntentService;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DataStore dataStore = new DataStore();
    private NetworkRequest networkRequest;
    public static JSONObject jsonObject;
    public static Context context;
    public static String reg_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
                if (radioGroup.getChildCount() == 0) {
                    Snackbar.make(view, "Add a UID first", Snackbar.LENGTH_LONG)
                            .setAction("OK!", null).show();
                    return;
                }
                networkRequest = new NetworkRequest(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Making Request for location!", Toast.LENGTH_LONG).show();
                RadioButton radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                networkRequest.requestLocation(radioButton.getText().toString());
            }
        });
        initializeRadioButtons();
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

    }

    public static void launchActivity(Context context){
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("jsonObject", jsonObject.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    public void Add(View view){
        EditText editText = (EditText)findViewById(R.id.myEditText);
        dataStore.writeToApplicationStorage(editText.getText().toString(), DataStore.FILE_TYPE.UID, getApplicationContext());
        TextView textView = (TextView) findViewById(R.id.emptyMessage);
        textView.setText("");
        editText.setText("");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
        initializeRadioButtons();
    }


    public void delete(View view){
        Snackbar.make(view, "Confirm Delete?", Snackbar.LENGTH_INDEFINITE)
                .setAction("Delete!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup rgp= (RadioGroup) findViewById(R.id.radiogroup);
                        RadioButton radioButton = (RadioButton)findViewById(rgp.getCheckedRadioButtonId());
                        String value = radioButton.getText().toString();
                        dataStore.deleteFromApplicationData(DataStore.FILE_TYPE.UID, getApplicationContext(),value);
                        initializeRadioButtons();
                    }
                }).show();
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

    public void addRadioButtons(int number, ArrayList<String> mList) {
        if(number == 0){
            TextView textView = (TextView) findViewById(R.id.emptyMessage);
            textView.setText("NONE! Add one now!");
            return;
        }

        RadioGroup rgp= (RadioGroup) findViewById(R.id.radiogroup);
        RadioGroup.LayoutParams rprms;

        for(int i=0;i<number;i++){
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(mList.get(i));
            //radioButton.setId("rbtn"+i);
            rprms= new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rgp.addView(radioButton, rprms);
        }
        rgp.check(rgp.getChildAt(0).getId());

    }


    public void initializeRadioButtons(){
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);
        int j=rg.getChildCount();
        for (int i=0; i< j; i++){
            rg.removeViewAt(0);
        }
        EditText editText = (EditText)findViewById(R.id.myEditText);
        ArrayList<String> mList = new ArrayList<>();
        mList = dataStore.readFromApplicationData(getApplicationContext(), DataStore.FILE_TYPE.UID.toString());
        addRadioButtons(mList.size(),mList);
    }

    public Context getContext(){
        return getApplicationContext();
    }


}
