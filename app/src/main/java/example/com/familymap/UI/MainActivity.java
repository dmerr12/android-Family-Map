package example.com.familymap.UI;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import example.com.familymap.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int REQ_CODE_ORDER_INFO = 1;


    public static String EXTRA_RESULT = "result";


    private LogIn logIn;
    private MapFragment mapFragment;
    private Button button;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        FragmentManager fm = this.getSupportFragmentManager();
        Bundle b = getIntent().getExtras();
        if(b!=null) {
            mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
            if (mapFragment == null) {
                mapFragment = mapFragment.newInstance("first");
                fm.beginTransaction()
                        .add(R.id.mapFrameLayout, mapFragment)
                        .commit();

            }
        }
       else {
                logIn = (LogIn) fm.findFragmentById(R.id.logInFrameLayout);
                if (logIn == null) {
                    logIn = LogIn.newInstance();
                    fm.beginTransaction()
                            .add(R.id.logInFrameLayout, logIn)
                            .commit();
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miSettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.miFilter:
                Intent intent1 = new Intent(this, FilterActivity.class);
                startActivity(intent1);
                return true;
            case R.id.miSearch:
                Intent intent2 = new Intent(this, SearchActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onLogin() {
        FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = mapFragment.newInstance("first");

        fm.beginTransaction()
                .remove(logIn)
                .commit();
            fm.beginTransaction()
                    .add(R.id.mapFrameLayout,mapFragment)
                    .commit();


    }

}

    @Override
public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    LatLng sydney = new LatLng(-34, 151);
    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_ORDER_INFO && resultCode == RESULT_OK && data != null) {

        }
    }
}
