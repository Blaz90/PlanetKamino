package kamino.starwars.com.kamino;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kamino.starwars.com.kamino.model.PlanetKamino;
import kamino.starwars.com.kamino.model.ServerCommProtocol;


public class MainActivity extends AppCompatActivity {

    ServerCommProtocol serverCommProtocol;
    PlanetKamino mPlanetKamino;
    private static String mObject;
    private static String mId;

    private ImageView likeButton;
    private String imageUrl;

    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(MainActivity.this, "Loading data..", Toast.LENGTH_LONG).show();
        ImageView planetImage = (ImageView)findViewById(R.id.planetImage);
        likeButton = (ImageView)findViewById(R.id.likeImage);
        likeButton.setClickable(true);

        mObject = "planets";
        mId = "10";

        serverCommProtocol = new ServerCommProtocol();
        getData();

        planetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    Log.d("like", "like!!!!");
                    clicked = true;
                    serverCommProtocol.invokeSendData(mObject, mId, new ServerCommProtocol.DataListener() {
                        @Override
                        public void onResponseError(String errorMessage) {
                            Log.e("response", errorMessage);
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponseSuccess(PlanetKamino planetKamino) {
                            updateDisplay(planetKamino);
                            likeButton.setOnClickListener(null);
                        }

                    });
                }
            }
        });

    }

    private void openImage() {
        Intent intent = new Intent(this, KaminoPictureActivity.class);
        intent.putExtra("image", imageUrl);
        startActivity(intent);
    }

    private void getData() {
        serverCommProtocol.invokeGetData(mObject, mId, new ServerCommProtocol.DataListener() {
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

    public void updateDisplay(PlanetKamino planetKamino) {
        TextView planetName = (TextView)findViewById(R.id.planetName);
        TextView rotationPeriod = (TextView)findViewById(R.id.rotationPeriodValue);
        TextView orbitalPeriod = (TextView)findViewById(R.id.orbitalPeriodValue);
        TextView diameter = (TextView)findViewById(R.id.diameterValue);
        TextView climate = (TextView)findViewById(R.id.climateValue);
        TextView gravity = (TextView)findViewById(R.id.gravityValue);
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
        imageUrl = planetKamino.getImageUrl();
        new ImageDownloader(image).execute(imageUrl);
    }

    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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
