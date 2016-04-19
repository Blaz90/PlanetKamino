package kamino.starwars.com.kamino.UI;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kamino.starwars.com.kamino.R;
import kamino.starwars.com.kamino.model.Networking;
import kamino.starwars.com.kamino.model.PlanetKamino;
import kamino.starwars.com.kamino.model.ResidentKamino;

/**
 * Created by blazzajec on 18/04/16.
 */
public class ResidentDetailsActivity extends AppCompatActivity {

    private ArrayList mResidentIds;
    private int mPosition;

    Networking mNetworking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_resident);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.menu_back);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        mResidentIds = intent.getParcelableArrayListExtra("residentIds");
        mPosition = intent.getIntExtra("position", 0);

        getResidentDetails(mPosition);
        getResidentHomeworld();
    }

    private void getResidentDetails(int position) {
        Toast.makeText(ResidentDetailsActivity.this, "Loading data..", Toast.LENGTH_SHORT).show();
        String id = mResidentIds.get(position).toString();
        mNetworking = new Networking();
        mNetworking.getResident(id, new Networking.ResidentDataListener() {
            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
            }

            @Override
            public void onResidentResponseSuccess(ResidentKamino residentKamino) {
                Toast.makeText(ResidentDetailsActivity.this, "Data received", Toast.LENGTH_LONG).show();
                updateDisplay(residentKamino);
            }
        });
    }

    private void getResidentHomeworld() {
        mNetworking = new Networking();
        mNetworking.getPlanet(new Networking.PlanetDataListener() {

            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
                Toast.makeText(ResidentDetailsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlanetResponseSuccess(PlanetKamino planetKamino) {
                updateHomeworld(planetKamino);
            }
        });
    }

    private void updateHomeworld(PlanetKamino planetKamino) {
        TextView homeworldName = (TextView)findViewById(R.id.homeworldValue);
        homeworldName.setText(planetKamino.getName());
    }

    private void updateDisplay(ResidentKamino residentKamino) {
        ImageView image = (ImageView)findViewById(R.id.residentImage);
        TextView residentName = (TextView)findViewById(R.id.residentName);
        TextView gender = (TextView)findViewById(R.id.genderValue);
        TextView birthYear = (TextView)findViewById(R.id.birthYearValue);
        TextView height = (TextView)findViewById(R.id.heightValue);
        TextView mass = (TextView)findViewById(R.id.massValue);
        TextView hairColor = (TextView)findViewById(R.id.hairColorValue);
        TextView skinColor = (TextView)findViewById(R.id.skinColorValue);
        TextView eyeColor = (TextView)findViewById(R.id.eyeColorValue);
        TextView created = (TextView)findViewById(R.id.createdValue);
        TextView edited = (TextView)findViewById(R.id.editedValue);

        Picasso.with(getApplicationContext()).load(residentKamino.getImageUrl()).into(image);
        residentName.setText(residentKamino.getName());
        gender.setText(residentKamino.getGender());
        birthYear.setText(residentKamino.getBirthYear());
        height.setText(residentKamino.getHeight());
        mass.setText(residentKamino.getMass());
        hairColor.setText(residentKamino.getHairColor());
        skinColor.setText(residentKamino.getSkinColor());
        eyeColor.setText(residentKamino.getEyeColor());
        created.setText(residentKamino.getCreated());
        edited.setText(residentKamino.getEdited());
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
