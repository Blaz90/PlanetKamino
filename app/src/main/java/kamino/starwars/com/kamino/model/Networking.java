package kamino.starwars.com.kamino.model;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Blaž on 11.04.2016.
 */
public class Networking extends Activity {

    private final static String LOG_TAG = ".Networking";
    private final static String API_BASE_URL = "http://private-anon-ffc6f083f-starwars2.apiary-mock.com/";
    private String API_REQ_URL;

    private StringEntity entity;
    private ArrayList<String> mResidentIds;

    public interface LikeDataListener {
        void onResponseError(String errorMessage);
        void onLikeResponseSuccess(PlanetKamino planetKamino);
    }

    public interface PlanetDataListener {
        void onResponseError(String errorMessage);
        void onPlanetResponseSuccess(PlanetKamino planetKamino);
    }

    public interface ResidentDataListener {
        void onResponseError(String errorMessage);
        void onResidentResponseSuccess(ResidentKamino residentKamino);
    }

    // API request call - get data
    public void getPlanet(final String planetId, final PlanetDataListener dataListener) {
        String object = "planets";
        API_REQ_URL = API_BASE_URL + object + "/" + planetId;
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        hmacAuthentication(client);

        client.get(API_REQ_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String jsonData = "";
                try {
                    jsonData = new String(responseBody, "UTF-8"); // for UTF-8 encoding

                    dataListener.onPlanetResponseSuccess(savePlanetData(jsonData, planetId));

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // When Http response code is '404'
                if (statusCode == 404) {
                    dataListener.onResponseError("Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    dataListener.onResponseError("Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    dataListener.onResponseError("No Internet connection");
                }
            }
        });
    }

    public void getResident(String residentId, final String planetName,final ResidentDataListener dataListener) {
        String object = "residents";
        API_REQ_URL = API_BASE_URL + object + "/" + residentId;
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        hmacAuthentication(client);

        client.get(API_REQ_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String jsonData = "";
                try {
                    jsonData = new String(responseBody, "UTF-8"); // for UTF-8 encoding

                    dataListener.onResidentResponseSuccess(saveResidentData(jsonData, planetName));

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // When Http response code is '404'
                if (statusCode == 404) {
                    dataListener.onResponseError("Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    dataListener.onResponseError("Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    dataListener.onResponseError("No Internet connection");
                }
            }
        });
    }

    // API request call - send data (like)
    public void sendLike(String planetId, final LikeDataListener dataListener) {
        String object = "planets";
        String like = "like";
        API_REQ_URL = API_BASE_URL + object + "/" + planetId  + "/" + like;
        try {
            entity = new StringEntity("{  'planet_id': "+ planetId +"}", "UTF-8");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        hmacAuthentication(client);
        client.post(this, API_REQ_URL, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String jsonData = "";
                Log.d("","jsonData: " + jsonData);
                try {
                    jsonData = new String(responseBody, "UTF-8"); // for UTF-8 encoding
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // When Http response code is '404'
                if (statusCode == 404) {
                    dataListener.onResponseError("Requested resource not found");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    dataListener.onResponseError("Something went wrong at server end");
                }
                // When Http response code other than 404, 500
                else {
                    dataListener.onResponseError("No Internet connection");
                }            }
        });
    }

    // HMAC (Hash-based message authentication code)
    protected void hmacAuthentication(AsyncHttpClient client){
        // keyString of value is "abcd"
        // and string header is HMAC for now
        String value = HMAC.sStringToHMACMD5("key", "abcd");
        client.addHeader("HMAC", value);
    }

    // fill ResidentKamino object with data from API
    private ResidentKamino saveResidentData(String jsonData, String planetName) throws JSONException {
        JSONObject resident = new JSONObject(jsonData);

        ResidentKamino residentKamino = new ResidentKamino();

        residentKamino.setName(resident.getString("name"));
        residentKamino.setHeight(resident.getString("height"));
        residentKamino.setMass(resident.getString("mass"));
        residentKamino.setHairColor(resident.getString("hair_color"));
        residentKamino.setSkinColor(resident.getString("skin_color"));
        residentKamino.setEyeColor(resident.getString("eye_color"));
        residentKamino.setBirthYear(resident.getString("birth_year"));
        residentKamino.setGender(resident.getString("gender"));
        residentKamino.setHomeworld(planetName);
        residentKamino.setCreated(resident.getString("created"));
        residentKamino.setEdited(resident.getString("edited"));
        residentKamino.setImageUrl(resident.getString("image_url"));

        return residentKamino;
    }

    // fill PlanetKamino object with data from API
    private PlanetKamino savePlanetData(String jsonData, String planetId) throws JSONException {
        JSONObject planet = new JSONObject(jsonData);

        PlanetKamino planetKamino = new PlanetKamino();

        planetKamino.setId(planetId);
        planetKamino.setName(planet.getString("name"));
        planetKamino.setOrbitalPeriod(planet.getString("orbital_period"));
        planetKamino.setRotationPeriod(planet.getString("rotation_period"));
        planetKamino.setDiameter(planet.getString("diameter"));
        planetKamino.setClimate(planet.getString("climate"));
        planetKamino.setGravity(planet.getString("gravity"));
        planetKamino.setTerrain(planet.getString("terrain"));
        planetKamino.setSurfaceWater(planet.getString("surface_water"));
        planetKamino.setPopulation(planet.getString("population"));
        planetKamino.setCreated(planet.getString("created"));
        planetKamino.setEdited(planet.getString("edited"));
        planetKamino.setImageUrl(planet.getString("image_url"));
        planetKamino.setLikes(planet.getString("likes"));

        getResidentIds(planet);
        planetKamino.setResidentIds(mResidentIds);
        planetKamino.setResidents(String.valueOf(mResidentIds.size())); // number of residents

        return planetKamino;
    }

    // get IDs from residents of planet, if ID is repeated do not write it in array
    private void getResidentIds(JSONObject jsonObject){
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("residents");
        } catch (JSONException e){
            e.printStackTrace();
            return;
        }
        mResidentIds = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            String str = null;
            try {
                str = jsonArray.get(i).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String id = str.substring(str.lastIndexOf("/") + 1);
            boolean contains = mResidentIds.contains(id);
            if (!contains) {
                mResidentIds.add(i, id);
            }
        }
    }
}