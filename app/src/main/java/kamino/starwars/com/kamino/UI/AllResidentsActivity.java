package kamino.starwars.com.kamino.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kamino.starwars.com.kamino.R;
import kamino.starwars.com.kamino.model.Networking;
import kamino.starwars.com.kamino.model.PlanetKamino;

/**
 * Created by blazzajec on 15/04/16.
 */
public class AllResidentsActivity extends AppCompatActivity {

    Networking mNetworking;
    private String mObject;
    private String mId;
    private TextView mResidentName;
    private ArrayList mResidentIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residents_all);

        Intent intent = getIntent();
        mResidentIds = intent.getParcelableArrayListExtra("residentIds");

        //getData();

        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        //recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setReverseLayout(true);
        recList.setLayoutManager(llm);

        Integer sizeOfMessages = 3;
        ResidentsAdapter ca = new ResidentsAdapter(createList(sizeOfMessages));
        recList.setAdapter(ca);
    }

    // Get all data from API and save it in ResidentKamino
    /*private void getData() {
        Toast.makeText(AllResidentsActivity.this, "Loading data..", Toast.LENGTH_SHORT).show();
        mObject = "residents";
        mNetworking = new Networking();
        mNetworking.getPlanet(new Networking.DataListener() {
            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
                Toast.makeText(AllResidentsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlanetResponseSuccess(PlanetKamino planetKamino) {

                Toast.makeText(AllResidentsActivity.this, "Data received", Toast.LENGTH_LONG).show();
            }

        });
    }*/


    private List<ContactInfo> createList(int size) {
        mResidentName = (TextView)findViewById(R.id.residentName);

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i <= size; i++) {


            ContactInfo ci = new ContactInfo();
            ci.ciResidentName = "Janez Novak";
            result.add(ci);
        }
        return result;
    }

    public class ContactInfo {
        public String ciResidentName;
    }
}
