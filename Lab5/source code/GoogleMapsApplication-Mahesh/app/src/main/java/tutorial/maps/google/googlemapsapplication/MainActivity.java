package tutorial.maps.google.googlemapsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button_map, button_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  getActionBar().setTitle("Home page");
        button_map = (Button) findViewById(R.id.main_btn_maps);
        button_photo = (Button) findViewById(R.id.main_btn_photo);

    }

    public void onClickOfMapButton(View v) {
        //This code redirects the from main page to the maps page.
        Intent redirect = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(redirect);
    }

    public void onClickOfPhotoButton(View v) {
        //This code redirects to the photo activity.
        Intent redirect = new Intent(MainActivity.this, PhotoActivity.class);
        startActivity(redirect);
    }

}
