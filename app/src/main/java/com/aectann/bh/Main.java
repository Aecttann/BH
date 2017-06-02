package com.aectann.bh;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main extends AppCompatActivity {
    TextView tvRes;
    boolean flagAsync = false;
    private LocationManager locationManager;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mconditionRef = myRef.child("USDrate");
    DatabaseReference mconditionRef2 = myRef.child("EURrate");
    DatabaseReference mconditionRef3 = myRef.child("RUBrate");
    DatabaseReference mconditionRef4 = myRef.child("DEPrate");
    double hryvnia;
    double dollar;
    double euro;
    double r;
    static Drawer result;
    static String[] choose;
    int choosedPos;
    static EditText et1;
    static Spinner spinner;
    static TextView tvchoose;
    CharSequence finalS;
    String res;
    double fromet;
    static PrimaryDrawerItem item1;
    static PrimaryDrawerItem item2;
    static SectionDrawerItem item3;
    static SecondaryDrawerItem item4;
    static SecondaryDrawerItem item5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_new);

        tvchoose = (TextView) findViewById(R.id.tvchoose);
        et1 = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);
        choose = getResources().getStringArray(R.array.currencylist);
        //гео
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

      //Боковая менюха
        item1 = new PrimaryDrawerItem().withName(R.string.converter).withIcon(FontAwesome.Icon.faw_location_arrow).withIdentifier(1);
        item2 = new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home);
        item3 = new SectionDrawerItem().withName(R.string.drawer_item_settings);
        item4 = new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog);
        item5 = new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_phone)/*.withBadge("12+")*/.withIdentifier(1);
        result = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        new DividerDrawerItem(),
                        item5
                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (item1.isSelected()){
                            tvchoose.setVisibility(1);
                            converter();
                            result.closeDrawer();
                        }
                        if (item2.isSelected()){
                            tvchoose.setVisibility(View.GONE);
                            result.closeDrawer();
                        }
                        if (item4.isSelected()){
                            Intent intentHelp = new Intent(Main.this, Help.class);
                            startActivity(intentHelp);
                            result.closeDrawer();
                        }
                        if (item5.isSelected()){
                            Intent intentContacts = new Intent(Main.this, Contacts.class);
                            startActivity(intentContacts);
                            result.closeDrawer();
                        }
                        return true;
                    }
                })
                .build();
        result.setSelection(item2);
    }
    // там для гео
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    //гео
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            }
        }
    };
    public static double lat(Location location) {
        if (location == null)
            return 0;
        return location.getLatitude();
    }
    public static double longtitude(Location location) {
        if (location == null)
            return 0;
        return location.getLongitude();
    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
        }
    }

    private void checkEnabled() {

    }
    private String formatLocation(Location location) {
        if (location == null)
            return "";

        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));

//
//        txtSpeechInput.setText("Coordinates: lat" + strLatit + ", " + strLongit);
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    //Async for weather
    AsyncTask<String, String, String> sendTask = new AsyncTask<String, String, String>() {
        protected String doInBackground(String... title) {
            return Weather.getweather();        }

    protected void onPostExecute(String result) {
        txtSpeechInput.setText(result);
    }

};
//распознавание
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));


                }
                if(txtSpeechInput.getText().toString().contains("Погода") ||
                        txtSpeechInput.getText().toString().contains("погода")||
                        txtSpeechInput.getText().toString().contains("Температура")||
                        txtSpeechInput.getText().toString().contains("температура")){
                    sendTask = new WeatherService();
                    sendTask.execute();
                }
                if(txtSpeechInput.getText().toString().contains("доллар") || txtSpeechInput.getText().toString().contains("Доллар")){
                    mconditionRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double text = dataSnapshot.getValue(Double.class);
                            txtSpeechInput.setText("     1$ = "+ text.toString() + " коп.");
                            dollar = text;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if(txtSpeechInput.getText().toString().contains("евро") || txtSpeechInput.getText().toString().contains("Евро")){
                    mconditionRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double text = dataSnapshot.getValue(Double.class);
                            txtSpeechInput.setText("     1€ = "+ text.toString() + " коп.");
                            euro = text;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if(txtSpeechInput.getText().toString().contains("рубль") ||
                        txtSpeechInput.getText().toString().contains("Рубля") ||
                        txtSpeechInput.getText().toString().contains("рубля") ||
                        txtSpeechInput.getText().toString().contains("Рубль")){
                    mconditionRef3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Double text = dataSnapshot.getValue(Double.class);
                            txtSpeechInput.setText("     1\u20BD = "+ text.toString() + " коп.");
                            r = text;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if(txtSpeechInput.getText().toString().contains("депозит") ||
                        txtSpeechInput.getText().toString().contains("Депозит") ||
                        txtSpeechInput.getText().toString().contains("Депозита") ||
                        txtSpeechInput.getText().toString().contains("депозита")||
                        txtSpeechInput.getText().toString().contains("Банк")||
                        txtSpeechInput.getText().toString().contains("банк")){
                    mconditionRef4.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String text = dataSnapshot.getValue(String.class);
                            txtSpeechInput.setText("     Лучший банк для депозита: "+ text.toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                break;
            }
        }
    }
    public void converter(){

        final String selected = spinner.getSelectedItem().toString();
//        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
//        String selected2 = spinner.getSelectedItem().toString();
//        if(selected.equals("Гривна")){
//            txtSpeechInput.setText("Работает");
//        }
        et1.addTextChangedListener(inputTW);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
                choosedPos = selectedItemPosition;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        et1.addTextChangedListener(inputTW);
    }
    //слшуатель для едита
    static TextWatcher inputTW = new TextWatcher() {

        public void afterTextChanged(Editable s) {

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //value = Double.parseDouble(et1.getText().toString());

//        if(choose[choosedPos].equals("Доллар")) {
//            finalS = s;
//            if(et1.getText().length() == 0)
//                fromet = 0;
//             else
//                 fromet = Double.parseDouble(et1.getText().toString());
//                mconditionRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        double text = dataSnapshot.getValue(Double.class);
//
//                        double result = fromet * 1;
//                        res = "   " + Double.toString(result) + "₴";
////                        txtSpeechInput.setText(res);
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                    });
//                mconditionRef2.addValueEventListener(new ValueEventListener() {
//                    @Override
//
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        double text = dataSnapshot.getValue(Double.class);
//
//                        double result = fromet * text;
//                        res = res + "\n" + Double.toString(result) + "€";
////                        txtSpeechInput.setText(res);
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                    });
//                mconditionRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        double text = dataSnapshot.getValue(Double.class);
//
//                        double result = fromet * text;
//                        res = res + "\n" + Double.toString(result) + "$";
////                        txtSpeechInput.setText(res);
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                    });
//                mconditionRef3.addValueEventListener(new ValueEventListener() {
//                    @Override
//
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        double text = dataSnapshot.getValue(Double.class);
//
//                        double result = fromet * text;
//                        res = res + "\n" + Double.toString(result) + "\u20BD";
////                        txtSpeechInput.setText(res);
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                    });
//            txtSpeechInput.setText(res);

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //Async for weather
    public class WeatherService extends AsyncTask<String, String, String>{


        protected String doInBackground(String... title) {
            return Weather.getweather();        }

        protected void onPostExecute(String result) {
            txtSpeechInput.setText(result);
        }
    }

}
class TextWatcherP implements TextWatcher {

    public EditText editText;

    public TextWatcherP(EditText et){
        super();
        editText = et;
    }
    public void afterTextChanged(Editable s) {

    }
    public void beforeTextChanged(CharSequence s, int start, int count, int after){

    }
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
