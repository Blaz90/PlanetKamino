package kamino.starwars.com.kamino.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kamino.starwars.com.kamino.R;
import kamino.starwars.com.kamino.model.Networking;

/**
 * Created by blazzajec on 18/04/16.
 */
public class ResidentDetailsActivity extends AppCompatActivity {

    private ArrayList mResidentIds;
    private int mPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_resident);

        Intent intent = getIntent();
        mResidentIds = intent.getParcelableArrayListExtra("residentIds");
        mPosition = intent.getIntExtra("position", 0);

        Log.d("intenttt","oneResident" + mPosition + ", " + mResidentIds);
    }
}
