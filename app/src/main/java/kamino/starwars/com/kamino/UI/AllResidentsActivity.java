package kamino.starwars.com.kamino.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kamino.starwars.com.kamino.R;
import kamino.starwars.com.kamino.model.PlanetKamino;

/**
 * Created by blazzajec on 15/04/16.
 */
public class AllResidentsActivity extends AppCompatActivity {

    private TextView mResidentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residents_all);

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
