package kamino.starwars.com.kamino.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kamino.starwars.com.kamino.R;
import kamino.starwars.com.kamino.model.Networking;
import kamino.starwars.com.kamino.model.ResidentKamino;

/**
 * Created by blazzajec on 15/04/16.
 */
public class ResidentListActivity extends AppCompatActivity {

    Networking mNetworking;
    private String mObject;
    private String mId;
    private TextView mResidentName;
    private ImageView mResidentImage;
    private ArrayList mResidentIds;
    private ArrayList<String> mNames;
    private ArrayList<String> mUrls;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residents_all);

        Intent intent = getIntent();
        mResidentIds = intent.getParcelableArrayListExtra("residentIds");
        counter = 0;
        Toast.makeText(ResidentListActivity.this, "Loading data..", Toast.LENGTH_SHORT).show();
        mObject = "residents";
        mNetworking = new Networking();

        getResidentNameAndUrl();

    }

    // Get all data from API and save it in ResidentKamino
    private void getResidentNameAndUrl() {
        mNames = new ArrayList<String>();
        mUrls = new ArrayList<String>();
        for (int i =0; i < mResidentIds.size(); i++) {
            String id = mResidentIds.get(i).toString();
            mNetworking.getResident(id, new Networking.ResidentDataListener() {
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
                    }
                    counter ++;
                }

            });
        }

    }

    private void generateList(){
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        //recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //llm.setReverseLayout(true);
        recList.setLayoutManager(llm);
        ResidentsAdapter ca = new ResidentsAdapter(createList());
        recList.setAdapter(ca);
    }

    private List<ContactInfo> createList() {
        final List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=0; i < mResidentIds.size(); i++) {

            ContactInfo ci = new ContactInfo();
            ci.ciResidentName = mNames.get(i);
            ci.ciResidentUrl = mUrls.get(i);

            result.add(ci);
        }
        return result;
    }

    public class ContactInfo {
        public String ciResidentName;
        public String ciResidentUrl;
    }
}
