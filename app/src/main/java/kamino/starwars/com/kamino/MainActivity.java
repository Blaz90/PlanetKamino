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

import kamino.starwars.com.kamino.UI.BigImageActivity;
import kamino.starwars.com.kamino.model.Networking;
import kamino.starwars.com.kamino.model.PlanetKamino;


public class MainActivity extends AppCompatActivity {

    Networking mNetworking;
    private String mObject;
    private String mId;
    private ImageView mLikeButton;
    private ImageView mPlanetImage;
    private String mImageUrl;
    private boolean mClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClicked = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getData();
        bigImageListener();
        likePlanetListener();

    }

    // This method listens for user click on planet image
    private void bigImageListener() {
        mPlanetImage = (ImageView)findViewById(R.id.planetImage);
        mPlanetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });
    }

    // This method opens BigImage activity on planet image click
    private void openImage() {
        Intent intent = new Intent(this, BigImageActivity.class);
        intent.putExtra("image", mImageUrl);
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
                    mNetworking.invokeSendData(mObject, mId, new Networking.DataListener() {
                        @Override
                        public void onResponseError(String errorMessage) {
                            Log.e("response", errorMessage);
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponseSuccess(PlanetKamino planetKamino) {
                            updateDisplay(planetKamino);
                        }

                    });
                }
            }
        });

    }

    // Get all data from API and save it in PlanetKamino or ResidentsKamino.
    private void getData() {
        Toast.makeText(MainActivity.this, "Loading data..", Toast.LENGTH_SHORT).show();
        mObject = "planets";
        mId = "10";
        mNetworking = new Networking();
        mNetworking.invokeGetData(mObject, mId, new Networking.DataListener() {
            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponseSuccess(PlanetKamino planetKamino) {
                updateDisplay(planetKamino);
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
        TextView gravity = (TextView)findViewById(R.id.residentsValue);
        TextView terrain = (TextView)findViewById(R.id.terrainValue);
        TextView surfaceWater = (TextView)findViewById(R.id.surfaceValue);
        TextView population = (TextView)findViewById(R.id.populationValue);
        TextView created = (TextView)findViewById(R.id.createdValue);
        TextView edited = (TextView)findViewById(R.id.editedValue);
        TextView like = (TextView)findViewById(R.id.likeValue);
        ImageView image = (ImageView)findViewById(R.id.planetImage);

        planetName.setText(planetKamino.getName());
        rotationPeriod.setText(planetKamino.getRotationPeriod());
        orbitalPeriod.setText(planetKamino.getOrbitalPeriod());
        diameter.setText(planetKamino.getDiameter());
        climate.setText(planetKamino.getClimate());
        gravity.setText(planetKamino.getGravity());
        terrain.setText(planetKamino.getTerrain());
        surfaceWater.setText(planetKamino.getSurfaceWater());
        population.setText(planetKamino.getPopulation());
        created.setText(planetKamino.getCreated());
        edited.setText(planetKamino.getEdited());
        like.setText(planetKamino.getLikes());
        mImageUrl = planetKamino.getImageUrl();

        Picasso.with(getApplicationContext()).load(mImageUrl).into(image);
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

}
