package kamino.starwars.com.kamino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import kamino.starwars.com.kamino.model.HMAC;
import kamino.starwars.com.kamino.model.PlanetKamino;
import kamino.starwars.com.kamino.model.ServerCommProtocol;


public class MainActivity extends AppCompatActivity {

    ServerCommProtocol serverCommProtocol;
    private static String mObject;
    private static String mId;

    private Button button;
    HMAC hmac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button)findViewById(R.id.button);

        mObject = "planets";
        mId = "10";

        Log.d("string to hmac", sStringToHMACMD5("bla", "abcd"));

        String[] arg = new String[0];

        hmac = new HMAC();
        try {
            hmac.main(arg);
        } catch (Exception e) {
            e.printStackTrace();
        }


        serverCommProtocol = new ServerCommProtocol();
        getData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverCommProtocol.invokeSendData(mObject, mId, new ServerCommProtocol.DataListener() {
                    @Override
                    public void onResponseError(String errorMessage) {
                        Log.e("response", errorMessage);
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
    }

    private void getData() {
        serverCommProtocol.invokeGetData(mObject, mId, new ServerCommProtocol.DataListener() {
            @Override
            public void onResponseError(String errorMessage) {
                Log.e("response", errorMessage);
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
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

    public static String sStringToHMACMD5(String s, String keyString) {
        String sEncodedString = null;
        try
        {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i=0; i<bytes.length; i++) {
                String hex = Integer.toHexString(0xFF &  bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        }
        catch (UnsupportedEncodingException e) {}
        catch(InvalidKeyException e){}
        catch (NoSuchAlgorithmException e) {}
        return sEncodedString ;
    }
}
