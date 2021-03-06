package kamino.starwars.com.kamino.model;

import java.util.ArrayList;

/**
 * Created by blazzajec on 11/04/16.
 */
public class PlanetKamino {

    private String mId;
    private String mName;
    private String mRotationPeriod;
    private String mOrbitalPeriod;
    private String mDiameter;
    private String mClimate;
    private String mGravity;
    private String mTerrain;
    private String mSurfaceWater;
    private String mPopulation;
    private String mCreated;
    private String mEdited;
    private String mImageUrl;
    private String mLikes;
    private String mResidents;
    private ArrayList mResidentIds;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOrbitalPeriod() {
        return mOrbitalPeriod;
    }

    public void setOrbitalPeriod(String orbitalPeriod) {
        mOrbitalPeriod = orbitalPeriod;
    }

    public String getRotationPeriod() {
        return mRotationPeriod;
    }

    public void setRotationPeriod(String rotationPeriod) {
        mRotationPeriod = rotationPeriod;
    }

    public String getDiameter() {
        return mDiameter;
    }

    public void setDiameter(String diameter) {
        mDiameter = diameter;
    }

    public String getClimate() {
        return mClimate;
    }

    public void setClimate(String climate) {
        mClimate = climate;
    }

    public String getTerrain() {
        return mTerrain;
    }

    public void setTerrain(String terrain) {
        mTerrain = terrain;
    }

    public String getGravity() {
        return mGravity;
    }

    public void setGravity(String gravity) {
        mGravity = gravity;
    }

    public String getSurfaceWater() {
        return mSurfaceWater;
    }

    public void setSurfaceWater(String surface_water) {
        mSurfaceWater = surface_water;
    }

    public String getPopulation() {
        return mPopulation;
    }

    public void setPopulation(String population) {
        mPopulation = population;
    }

    public String getEdited() {
        return mEdited;
    }

    public void setEdited(String edited) {
        mEdited = formatDateAndTime(edited);
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String created) {
        mCreated = formatDateAndTime(created);
    }

    public String getLikes() {
        return mLikes;
    }

    public void setLikes(String likes) {
        mLikes = likes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getResidents() {
        return mResidents;
    }

    public void setResidents(String residents) {
        this.mResidents = residents;
    }

    public ArrayList getResidentIds() {
        return mResidentIds;
    }

    public void setResidentIds(ArrayList residentIds) {
        mResidentIds = residentIds;
    }

    public String formatDateAndTime(String dateTime){
        dateTime = dateTime.substring(0, 10) + "\n" + dateTime.substring(11, 19);
        return dateTime;
    }

}
