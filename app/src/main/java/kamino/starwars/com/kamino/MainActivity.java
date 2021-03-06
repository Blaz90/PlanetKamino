package kamino.starwars.com.kamino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

import kamino.starwars.com.kamino.UI.ResidentListActivity;
import kamino.starwars.com.kamino.UI.BigImageActivity;
import kamino.starwars.com.kamino.model.Networking;
import kamino.starwars.com.kamino.model.PlanetKamino;

public class MainActivity extends AppCompatActivity {

    private Networking mNetworking;
    private PlanetKamino mPlanetKamino;
    private ImageView mLikeButton;
    private ImageView mPlanetImage;
    private boolean mClicked;
    private String mPlanetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mClicked = false; // set flag for like button
        mPlanetId = "10";

        getPlanetData();
        bigImageListener();
        likePlanetListener();
    }

    // This method listens for user click on planet image
    private void bigImageListener() {
        mPlanetImage = (ImageView)findViewById(R.id.planetImage);
        mPlanetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlanetKamino != null) {
                    openImage();
                }
            }
        });
    }

    // This method opens BigImage activity on planet image click
    private void openImage() {
        Intent intent = new Intent(this, BigImageActivity.class);
        intent.putExtra("image", mPlanetKamino.getImageUrl());
        startActivity(intent);
    }

    // This method listens for user click on like
    private void likePlanetListener(){
        mLikeButton = (ImageView)findViewById(R.id.likeImage);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mClicked) {
                    mClicked = true;
                    mNetworking.sendLike(mPlanetId, new Networking.LikeDataListener() {
                        @Override
                        public void onResponseError(String errorMessage) {
                            Log.e("response", errorMessage);
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onLikeResponseSuccess(PlanetKamino planetKamino) {
                            getPlanetData();
                        }
                    });
                }
            }
        });
    }

    // Get all data from API and save it in PlanetKamino.
    private void getPlanetData() {
        Toast.makeText(MainActivity.this, "Loading data..", Toast.LENGTH_SHORT).show();
        mNetworking = new Networking();
        mNetworking.getPlanet(mPlanetId, new Networking.PlanetDataListener() {

            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlanetResponseSuccess(PlanetKamino planetKamino) {
                mPlanetKamino = planetKamino;
                updateDisplay(mPlanetKamino);
                Toast.makeText(MainActivity.this, "Data received", Toast.LENGTH_LONG).show();
            }
        });
    }

    // When data is succesfuly received, display it on screen
    public void updateDisplay(PlanetKamino planetKamino) {
        TextView planetName = (TextView)findViewById(R.id.planetName);
        TextView rotationPeriod = (TextView)findViewById(R.id.rotationPeriodValue);
        TextView orbitalPeriod = (TextView)findViewById(R.id.orbitalPeriodValue);
        TextView diameter = (TextView)findViewById(R.id.diameterValue);
        TextView climate = (TextView)findViewById(R.id.climateValue);
        TextView gravity = (TextView)findViewById(R.id.gravityValue);
        TextView residents = (TextView)findViewById(R.id.residentsValue);
        TextView terrain = (TextView)findViewById(R.id.terrainValue);
        TextView surfaceWater = (TextView)findViewById(R.id.surfaceValue);
        TextView population = (TextView)findViewById(R.id.populationValue);
        TextView created = (TextView)findViewById(R.id.createdValue);
        TextView edited = (TextView)findViewById(R.id.editedValue);
        ImageView image = (ImageView)findViewById(R.id.planetImage);
        TextView like = (TextView)findViewById(R.id.likeValue);

        planetName.setText(planetKamino.getName());
        rotationPeriod.setText(planetKamino.getRotationPeriod());
        orbitalPeriod.setText(planetKamino.getOrbitalPeriod());
        diameter.setText(planetKamino.getDiameter());
        climate.setText(planetKamino.getClimate());
        gravity.setText(planetKamino.getGravity());
        residents.setText(planetKamino.getResidents());
        terrain.setText(planetKamino.getTerrain());
        surfaceWater.setText(planetKamino.getSurfaceWater());
        population.setText(planetKamino.getPopulation());
        created.setText(planetKamino.getCreated());
        edited.setText(planetKamino.getEdited());
        Picasso.with(getApplicationContext()).load(mPlanetKamino.getImageUrl()).into(image);
        like.setText(mPlanetKamino.getLikes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            openPlanetKamino();
            return true;
        } else if (id == R.id.action_residents && mPlanetKamino != null){
            openResidentList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This method open ResidentListActivity
    private void openResidentList(){
        Intent intent = new Intent(this, ResidentListActivity.class);
        intent.putExtra("residentIds", mPlanetKamino.getResidentIds());
        intent.putExtra("planetName", mPlanetKamino.getName());
        startActivity(intent);
    }
    // This method reopen MainActivity - refresh first screen
    private void openPlanetKamino(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
