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

    private PlanetKamino mPlanetKamino;
    private ResidentKamino mResidentKamino;
    private ResidentList mResidentList;
    StringEntity entity;
    ArrayList<String> mResidentIds;

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

    public interface ResidentListDataListener {
        void onResponseError(String errorMessage);
        void onResidentListResponseSuccess(ResidentList residentList);
    }

    // API request call - get data
    public void getPlanet(final PlanetDataListener dataListener) {
        String object = "planets";
        String id = "10";
        API_REQ_URL = API_BASE_URL + object + "/" + id;
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        hmacAuthentication(client);

        client.get(API_REQ_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String jsonData = "";
                try {
                    jsonData = new String(responseBody, "UTF-8"); // for UTF-8 encoding

                    dataListener.onPlanetResponseSuccess(savePlanetData(jsonData));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("server said", jsonData);
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

    public void getResident(int residentId, final ResidentDataListener dataListener) {
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

                    dataListener.onResidentResponseSuccess(saveResidentData(jsonData));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("server said", jsonData);
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
    public void sendLike(final LikeDataListener dataListener) {
        String object = "planets";
        String id = "10";
        String like = "like";
        API_REQ_URL = API_BASE_URL + object + "/" + id  + "/" + like;
        try {
            entity = new StringEntity("{  'planet_id': "+ id +"}", "UTF-8");
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
                     //dataListener.onLikeResponseSuccess(mPlanetKamino);
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
        //Log.d("hmac",value);
    }

    // fill ResidentKamino object with data from API
    private ResidentKamino saveResidentData(String jsonData) throws JSONException {
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
        residentKamino.setHomeworld(resident.getString("homeworld"));
        residentKamino.setCreated(resident.getString("created"));
        residentKamino.setEdited(resident.getString("edited"));
        residentKamino.setImageUrl(resident.getString("image_url"));

        return residentKamino;
    }

    // fill PlanetKamino object with data from API
    private PlanetKamino savePlanetData(String jsonData) throws JSONException {
        JSONObject planet = new JSONObject(jsonData);

        PlanetKamino planetKamino = new PlanetKamino();

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

    // fill PlanetKamino object with data from API
    /*
    private PlanetKamino getArrayIds(String jsonData) throws JSONException {
        JSONObject residents = new JSONObject(jsonData);

        PlanetKamino planetKamino = new PlanetKamino();

        getResidentIds(residents);
        planetKamino.setResidentIds(mResidentIds);
        Log.d("planet kamino","array: " + mResidentIds);

        return planetKamino;
    }
    */

    private ResidentList getNamesAndPictures(String jsonData) throws JSONException{
        JSONObject residents = new JSONObject(jsonData);
        ResidentList residentKamino = new ResidentList();

        ArrayList residentNames = new ArrayList();
        residentNames.add(0, residents.getString("name"));
        residentKamino.setResidentNames(residentNames);

        return residentKamino;
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