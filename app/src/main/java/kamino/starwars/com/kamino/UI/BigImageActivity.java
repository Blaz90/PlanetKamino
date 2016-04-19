package kamino.starwars.com.kamino.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

import kamino.starwars.com.kamino.MainActivity;
import kamino.starwars.com.kamino.R;

public class BigImageActivity extends AppCompatActivity {

    ImageView planetBigImage;

    private ArrayList mResidentIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_image);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.menu_back);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        String planetImage = intent.getStringExtra("image");
        mResidentIds = intent.getParcelableArrayListExtra("residentIds");

        planetBigImage = (ImageView)findViewById(R.id.planetBigImage);
        Picasso.with(getApplicationContext()).load(planetImage).into(planetBigImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
