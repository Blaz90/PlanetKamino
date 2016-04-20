package kamino.starwars.com.kamino.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kamino.starwars.com.kamino.MainActivity;
import kamino.starwars.com.kamino.R;
import kamino.starwars.com.kamino.model.Networking;
import kamino.starwars.com.kamino.model.ResidentKamino;

/**
 * Created by blazzajec on 15/04/16.
 */
public class ResidentListActivity extends AppCompatActivity {

    Networking mNetworking;
    private ArrayList mResidentIds;
    private ArrayList<String> mNames;
    private ArrayList<String> mUrls;
    private int counter;
    private int i;
    private String mPlanetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residents_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(ResidentListActivity.this, "Loading data..", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        mResidentIds = intent.getParcelableArrayListExtra("residentIds");
        mPlanetName = intent.getStringExtra("planetName");
        mNetworking = new Networking();
        mNames = new ArrayList<String>();
        mUrls = new ArrayList<String>();
        counter = 0;
        i = 0;

        getResidentNameAndUrl(i);
    }

    // Get all data from API and save it in ResidentKamino
    private void getResidentNameAndUrl(final int i) {
        String id = mResidentIds.get(i).toString();
        mNetworking.getResident(id, mPlanetName, new Networking.ResidentDataListener() {
            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
            }

            @Override
            public void onResidentResponseSuccess(ResidentKamino residentKamino) {
                mNames.add(residentKamino.getName());
                mUrls.add(residentKamino.getImageUrl());
                if (counter >= mResidentIds.size() - 1) {
                    generateList();
                    Toast.makeText(ResidentListActivity.this, "Data received", Toast.LENGTH_LONG).show();
                } if (counter < mResidentIds.size() - 1) {
                    getResidentNameAndUrl(i + 1);
                }
                counter++;
            }
        });
    }

    // This method generate list of all residents
    private void generateList(){
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ResidentsAdapter ca = new ResidentsAdapter(createList(), mResidentIds, mPlanetName);
        recList.setAdapter(ca);
    }

    // set name, URL and id for all residents
    private List<ResidentInfo> createList() {
        final List<ResidentInfo> result = new ArrayList<ResidentInfo>();
        for (int i=0; i < mResidentIds.size(); i++) {

            ResidentInfo ci = new ResidentInfo();
            ci.ciResidentName = mNames.get(i);
            ci.ciResidentUrl = mUrls.get(i);
            ci.ciResidentId = mResidentIds.get(i).toString();
            result.add(ci);
        }
        return result;
    }

    public class ResidentInfo {
        public String ciResidentName;
        public String ciResidentUrl;
        public String ciResidentId;
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
        } else if (id == R.id.action_residents){
            openResidentList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This method reopen ResidentListActivity - refresh screen
    private void openResidentList(){
        Intent intent = new Intent(this, ResidentListActivity.class);
        intent.putExtra("residentIds", mResidentIds);
        startActivity(intent);
    }
    // This method open MainActivity - first activity
    private void openPlanetKamino(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
