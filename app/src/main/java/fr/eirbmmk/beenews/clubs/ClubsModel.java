package fr.eirbmmk.beenews.clubs;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Contains all the needed information for getting clubs information.
 */
public class ClubsModel {

    /* -------------------- Attributes -------------------- */


    private static final String mClubsURL = "http://eirbmobile.enseirb-matmeca.fr/Alpha/Webservice/data.php";


    /* -------------------- Constructors -------------------- */

    /**
     * Constructor of the ClubsModel
     */
    public ClubsModel()
    {
    }


    /* -------------------- Methods -------------------- */


    /**
     * Does an HTTP POST request to get clubs list.
     * @return the club list
     */
    public ArrayList<Club> getClubs()
    {
        ArrayList<Club> clubs = new ArrayList<Club>();
        HttpClient httpclient = new DefaultHttpClient();
        String jsonResult = null;

        // HTTP POST Creation request
        HttpPost httpPost = new HttpPost(mClubsURL);
        HttpResponse httpResponse = null;

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // Sends HTTP POST Request
        try {
            httpResponse = httpclient.execute(httpPost);
            // get HTTP POST result
            jsonResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            android.util.Log.e("ERROR", e.toString());
            e.printStackTrace();
            return null;
        }

        try {
            JSONArray dataArray = new JSONArray(jsonResult);

            for(int i = 0 ; i < dataArray.length() ; i++){
                JSONObject object = (JSONObject) dataArray.get(i);
                Club clubToAdd = new Club(object.getString("id"), object.getString("name"), object.getString("icon"));
                Log.d("Insert", "" + object.getString("icon"));
                clubs.add(clubToAdd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return clubs;
    }

    /**
     * Get Club details of the given club
     * @param club the club to get details
     * @return list of all club details
     */
    public ArrayList<ClubDetailsItem> getClubDetails(Club club)
    {
        ArrayList<ClubDetailsItem> clubDetails = new ArrayList<ClubDetailsItem>();

        HttpClient httpclient = new DefaultHttpClient();
        String jsonResult = null;

        // HTTP GET Creation request
        HttpGet httpGet = new HttpGet(mClubsURL + "?id=" + club.getId());
        HttpResponse httpResponse = null;

        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // Sends HTTP GET Request
        try {
            httpResponse = httpclient.execute(httpGet);
            // get HTTP result
            jsonResult = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            android.util.Log.e("ERROR", e.toString());
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject dataObject = new JSONObject(jsonResult);
            JSONArray dataArray = dataObject.getJSONArray("content");

            for(int i = 0 ; i < dataArray.length() ; i++){
                JSONObject object = (JSONObject) dataArray.get(i);
                if (object.getString("type").equals("i"))
                    clubDetails.add(new ClubDetailsItem(ClubDetailsItemType.IMAGE,object.getString("content")));
                else if (object.getString("type").equals("p"))
                    clubDetails.add(new ClubDetailsItem(ClubDetailsItemType.PARAGRAPH,object.getString("content")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return clubDetails;
    }
}
