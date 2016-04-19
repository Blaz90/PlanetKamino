package kamino.starwars.com.kamino.model;

/**
 * Created by blazzajec on 12/04/16.
 */
public class ResidentKamino {

    private String mName;
    private String mHeight;
    private String mMass;
    private String mHairColor;
    private String mSkinColor;
    private String mEyeColor;
    private String mBirthYear;
    private String mGender;
    private String mHomeworld;
    private String mCreated;
    private String mEdited;
    private String mImageUrl;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
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

    public String getHomeworld() {
        return mHomeworld;
    }

    public void setHomeworld(String homeworld) {
        mHomeworld = homeworld;
    }

    public String getBirthYear() {
        return mBirthYear;
    }

    public void setBirthYear(String birthYear) {
        mBirthYear = birthYear;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getEyeColor() {
        return mEyeColor;
    }

    public void setEyeColor(String eyeColor) {
        mEyeColor = eyeColor;
    }

    public String getSkinColor() {
        return mSkinColor;
    }

    public void setSkinColor(String skinColor) {
        mSkinColor = skinColor;
    }

    public String getHairColor() {
        return mHairColor;
    }

    public void setHairColor(String hairColor) {
        mHairColor = hairColor;
    }

    public String getMass() {
        return mMass;
    }

    public void setMass(String mass) {
        mMass = mass;
    }

    public String formatDateAndTime(String dateTime){
        dateTime = dateTime.substring(0, 10) + ", " + dateTime.substring(11, 19);
        return dateTime;
    }
}
