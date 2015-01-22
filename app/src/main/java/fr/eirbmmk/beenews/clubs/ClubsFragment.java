package fr.eirbmmk.beenews.clubs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import fr.eirbmmk.beenews.BaseFragment;
import fr.eirbmmk.beenews.MainActivityController;
import fr.eirbmmk.beenews.R;

import java.util.ArrayList;

/**
 * View part of the MVC pattern of the Club and Associations list display
 */
public class ClubsFragment extends BaseFragment {

	 /* -------------------- Attributes -------------------- */

    /**
     * The controller part of the MVC Clubs pattern
     */
    private ClubsController mController;


    /* -------------------- Constructors -------------------- */


    /**
     * Used by the Operating System when it killed the fragment to free memory
     * and need to recreate it.
     *
     * Never use this constructor directly !
     *
     */
    public ClubsFragment()
    {
        // NEVER USE DIRECTLY THIS CONSTRUCTOR !
        // IT IS HERE ONLY FOR ANDROID BACKGROUND PROCESS (FRAGMENT RECREATION)
    }



    /**
     * Constructor of the ClubsFragment class
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model including clubs information.
     */
    public ClubsFragment(MainActivityController container, ClubsModel model)
    {
        mController = new ClubsController(container, model, this);
        super.setBaseController(mController);
    }

    /* -------------------- Methods -------------------- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If the fragment is in an illegal state, don't continue, wait the recreation of it.
        if (super.onCreateView(inflater, container, savedInstanceState) == null)
            return null;

        mRootView = inflater.inflate(R.layout.fragment_clubs, container, false);

        if(!mController.isNetworkConnected()){
            mRootView = inflater.inflate(R.layout.no_internet_connection, container, false);
        } else {
            mController.getClubsList();
        }
        return mRootView;
    }

    /**
     * Displays the club list given in parameter
     * @param clubs club list to display in the list
     */
    public void displayClubsList(final ArrayList<Club> clubs)
    {
        ClubsListAdapter adapter = new ClubsListAdapter(mRootView.getContext(),clubs);
        ListView listView = (ListView) mRootView.findViewById(R.id.clubsListView);
        listView.setAdapter(adapter);
        listView.invalidate();

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mController.displayDetailedClubFragment(clubs.get(position));
            }
        });
    }
}
