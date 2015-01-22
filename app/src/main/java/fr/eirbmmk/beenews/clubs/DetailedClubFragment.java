package fr.eirbmmk.beenews.clubs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.eirbmmk.beenews.BaseSecondaryFragment;
import fr.eirbmmk.beenews.R;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * Fragment to display Details of a particular club
 */
public class DetailedClubFragment extends BaseSecondaryFragment {

	 /* -------------------- Attributes -------------------- */

    /**
     * The controller part of the MVC News pattern
     */
    private ClubsController mController;

    /**
     * Item displayed in details in the fragment
     */
    private Club mItem;

    /* -------------------- Constructors -------------------- */


    /**
     * Used by the Operating System when it killed the fragment to free memory
     * and need to recreate it.
     * <p/>
     * Never use this constructor directly !
     */
    public DetailedClubFragment() {
        // NEVER USE DIRECTLY THIS CONSTRUCTOR !
        // IT IS HERE ONLY FOR ANDROID BACKGROUND PROCESS (FRAGMENT RECREATION)
    }


    /**
     * Constructor of the DetailedCubFragment class
     *
     * @param controller the clubs controller of the package
     * @param item       club to display in details
     */
    public DetailedClubFragment(ClubsController controller, Club item) {

        mController = controller;
        mItem = item;

        super.setBaseController(mController);
    }

    /**
     * Private Class to simplify Image Downloading from the Internet
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    /* -------------------- Methods -------------------- */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If the fragment is in an illegal state, don't continue, wait the recreation of it.
        if (super.onCreateView(inflater, container, savedInstanceState) == null)
            return null;


        mRootView = inflater.inflate(R.layout.fragment_detailed_club, container, false);

        if (mController.isNetworkConnected()) {
            mController.getClubDetails(mItem);
        } else {
            mRootView = inflater.inflate(R.layout.no_internet_connection, container, false);
        }

        return mRootView;
    }

    /**
     * displays on the screen the different details of the club
     * @param details list of details for the club
     */
    public void displayClubDetails(final ArrayList<ClubDetailsItem> details) {

        final LinearLayout view = (LinearLayout) mRootView.findViewById(R.id.detailed_club_content);

        for(ClubDetailsItem club : details){
            if(club.getType() ==  ClubDetailsItemType.IMAGE){
                ImageView imageView = new ImageView(getActivity());
                imageView.setPadding(30, 10, 30, 10);
                new DownloadImageTask(imageView)
                        .execute(club.getContent());
                view.addView(imageView);
            } else if(club.getType() ==  ClubDetailsItemType.PARAGRAPH){
                TextView textView = new TextView(getActivity());
                textView.setText(club.getContent());
                textView.setPadding(30, 10, 30, 10);
                view.addView(textView);
            }
        }
    }
}