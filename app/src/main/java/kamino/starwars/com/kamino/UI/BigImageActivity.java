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
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import kamino.starwars.com.kamino.MainActivity;
import kamino.starwars.com.kamino.R;

public class BigImageActivity extends AppCompatActivity {

    ImageView planetBigImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planet_image);

        Intent intent = getIntent();
        String planetImage = intent.getStringExtra("image");

        planetBigImage = (ImageView)findViewById(R.id.planetBigImage);
        Picasso.with(getApplicationContext()).load(planetImage).into(planetBigImage);

    }
}
