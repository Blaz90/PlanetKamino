package kamino.starwars.com.kamino.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * Created by Blaž on 11.04.2016.
 */
public class ServerCommProtocol {
    private final static String LOG_TAG = ".ServerCommProtocol";

    private final static String API_BASE_URL = "http://private-anon-ffc6f083f-starwars2.apiary-mock.com/";

    private PlanetKamino mPlanetKamino;
    private ResidentKamino mResidentKamino;

    ProgressDialog prgDialog;

    public interface DataListener {
        void onResponseError(String errorMessage);
    }

    public void invokeGetData(final String object, final String id, final DataListener dataListener) {
        // Show Progress Dialog
        if (prgDialog != null) prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_BASE_URL + object + "/" + id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (prgDialog != null) prgDialog.hide();

                String jsonData = "";
                try {
                    jsonData = new String(responseBody, "UTF-8"); // for UTF-8 encoding
                    if (object == "planets") {
                        mPlanetKamino = getPlanetDetails(jsonData);
                    } else if (object == "residents") {
                        mResidentKamino = getResidentDetails(jsonData);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.e("server said", jsonData);
                // dfds

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Hide Progress Dialog
                if (prgDialog != null) prgDialog.hide();
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
                    dataListener.onResponseError("Unexpected Error occcured! Device might not be connected to Internet or remote server is not up and running");
                }
            }
        });
    }

    public void invokeSendData(String number, final String object, final String id, final DataListener dataListener) {
        // Show Progress Dialog
        if (prgDialog != null) prgDialog.show();

        RequestParams params = new RequestParams();
        params.add("likes", number);

        try {
            StringEntity entity = new StringEntity("{  'planet_id': 10}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(API_BASE_URL + object + "/" + id + "/like", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (prgDialog != null) prgDialog.hide();

                String jsonData = "";
                try {
                    jsonData = new String(responseBody, "UTF-8"); // for UTF-8 encoding
                    Log.d("jsonData", "OnSuccess..." + jsonData);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //Log.e("server said", jsonData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // Hide Progress Dialog
                if (prgDialog != null) prgDialog.hide();
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
                    dataListener.onResponseError("Unexpected Error occcured! Device might not be connected to Internet or remote server is not up and running");
                }
            }
        });
    }

    private ResidentKamino getResidentDetails(String jsonData) throws JSONException {
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

    private PlanetKamino getPlanetDetails(String jsonData) throws JSONException {
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

        // get all ids of residents
        JSONArray jsonArray = planet.getJSONArray("residents");

        String[] arrayIds = new String[jsonArray.length()];
        List<String> dan = Arrays.asList(arrayIds);

        for (int i = 0; i < jsonArray.length(); i++) {
            String str = jsonArray.get(i).toString();
            String id = str.substring(str.lastIndexOf("/") + 1);
            //Log.d("jsonArray", "loop " + id);
            boolean contains = dan.contains(id);
            //Log.d("boolean", ": " + contains + ", " + dan);
            if (!contains) {
                arrayIds[i] = id;
                //Log.d("i", "i " + i);
            }
        }

        //Log.d("jsonArray", " " + planet.getString("likes"));

        planetKamino.setArrayIds(arrayIds);

        return planetKamino;
    }
}