package fr.eirbmmk.beenews.clubs;

import android.os.AsyncTask;

import fr.eirbmmk.beenews.BaseController;
import fr.eirbmmk.beenews.MainActivityController;

import java.util.ArrayList;

/**
 * This class correspond to the controller part of the MVC design pattern for the Clubs
 * functionality of the application.
 */
public class ClubsController extends BaseController {

    /* -------------------- Attributes -------------------- */


    /**
    * Model part of the MVC design pattern 
		*/
    private ClubsModel mModel;

    /**
    * View part of the MVC design pattern
    */
    private ClubsFragment mFragment;

    /**
     * Reference to a possible detailed fragment to display a specific club details
     */
    private DetailedClubFragment mDetailsFragment;


    /* -------------------- Constructors -------------------- */


    /**
     * Constructor of ClubsController.
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model for the Clubs functionality
     * @param fragment the view of the Clubs functionality
     */
    public ClubsController(MainActivityController container, ClubsModel model, ClubsFragment fragment)
    {
        super(container);
        mModel = model;
        mFragment = fragment;
    }


    /* -------------------- Methods -------------------- */

    /**
     * Fetchs the clubs and associations list from eirbmmk.fr
     * running an Async Task
     */
    public void getClubsList()
    {
        AsyncTask<Void, Void, Boolean> clubsRequest = new AsyncTask<Void, Void, Boolean>() {

            private ArrayList<Club> mClubs = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mFragment.showLoadingSpinner();
            }

            @Override
            protected Boolean doInBackground(Void... Params) {
                if (!isCancelled())
                    mClubs = mModel.getClubs();
                return mClubs != null;
            }

            @Override
            protected void onPostExecute(Boolean ok) {
                if (ok.booleanValue()) {
                    mFragment.displayClubsList(mClubs);
                } else {
                    mFragment.displayErrorMessage("Un problème est survenu lors de la récupération de la liste des Clubs & Associations.");
                }

                mFragment.hideLoadingSpinner();

                // remove the task to running tasks list
                mAsyncRunningTasks.remove(this);
            }
        };

        // add the task to running tasks list
        mAsyncRunningTasks.add(clubsRequest);

        // start the async task
        clubsRequest.execute();
    }

    /**
     * Fetchs club details of the given club
     * @param club the club to get details
     */
    public void getClubDetails(Club club)
    {
        AsyncTask<Club, Void, Boolean> detailsRequest = new AsyncTask<Club, Void, Boolean>() {

            private ArrayList<ClubDetailsItem> mDetails = new ArrayList<ClubDetailsItem>();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mFragment.showLoadingSpinner();
            }

            @Override
            protected Boolean doInBackground(Club... params) {
                Club club = params[0];
                if (!isCancelled())
                    mDetails = mModel.getClubDetails(club);

                return mDetails != null;
            }

            @Override
            protected void onPostExecute(Boolean ok) {
                if (ok.booleanValue()) {
                    mDetailsFragment.displayClubDetails(mDetails);
                } else {
                    mFragment.displayErrorMessage("Un problème est survenu lors de la récupération des informations concernant l'association.");
                }
                mDetailsFragment.hideLoadingSpinner();

                // remove the task to running tasks list
                mAsyncRunningTasks.remove(this);
            }
        };

        // add the task to running tasks list
        mAsyncRunningTasks.add(detailsRequest);

        // start the async task
        detailsRequest.execute(club);
    }

    /**
     * Displays the secondary fragment for showing specific club details
     *
     * @param club club to display in details
     */
    public void displayDetailedClubFragment(Club club)
    {
        mDetailsFragment = new DetailedClubFragment(this, club);

        mMainActivityController.displaySecondaryFragment(mDetailsFragment);
    }
}
