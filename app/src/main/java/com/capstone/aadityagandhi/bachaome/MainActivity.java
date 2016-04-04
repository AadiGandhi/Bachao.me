package com.capstone.aadityagandhi.bachaome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.capstone.aadityagandhi.bachaome.Utils.DataStore;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context context = this.getBaseContext();
    private DataStore dataStore = new DataStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                EditText editText = (EditText) findViewById(R.id.myEditText);
                intent.putExtra("latlng", editText.getText());
                startActivity(intent);
            }
        });
        initializeRadioButtons();
    }
    public void goToMaps(View view){
        EditText editText = (EditText)findViewById(R.id.myEditText);
        dataStore.writeToApplicationStorage(editText.getText().toString(), DataStore.FILE_TYPE.UID, getApplicationContext());
        TextView textView = (TextView) findViewById(R.id.emptyMessage);
        textView.setText("");
        initializeRadioButtons();
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
        if(!mList.isEmpty()){
            editText.setText(String.valueOf(mList.size()));
            addRadioButtons(mList.size(),mList);
        }
        else {
            editText.setText("Empty Array Returned");
            addRadioButtons(mList.size(),mList);
        }
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

    }

    public Context getContext(){
        return getApplicationContext();
    }


}
