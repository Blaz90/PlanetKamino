package kamino.starwars.com.kamino.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blazzajec on 15/04/16.
 */
public class ResidentList {

    private ArrayList mResidentNames;
    private ArrayList mResidentPictures;


    public ArrayList getResidentNames() {
        return mResidentNames;
    }

    public void setResidentNames(ArrayList residentNames) {
        mResidentNames = residentNames;
    }

    public ArrayList getResidentPictures() {
        return mResidentPictures;
    }

    public void setResidentPictures(ArrayList residentPictures) {
        mResidentPictures = residentPictures;
    }
}
