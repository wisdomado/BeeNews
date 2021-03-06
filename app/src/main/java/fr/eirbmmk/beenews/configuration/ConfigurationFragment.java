package fr.eirbmmk.beenews.configuration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eirbmmk.beenews.BaseFragment;
import fr.eirbmmk.beenews.MainActivityController;
import fr.eirbmmk.beenews.R;

public class ConfigurationFragment extends BaseFragment {
	
	 /* -------------------- Attributes -------------------- */

    /**
     * The controller part of the MVC Configuration pattern
     */
    private ConfigurationController mController;


    /* -------------------- Constructors -------------------- */


    /**
     * Used by the Operating System when it killed the fragment to free memory
     * and need to recreate it.
     *
     * Never use this constructor directly !
     *
     */
    public ConfigurationFragment()
    {
        // NEVER USE DIRECTLY THIS CONSTRUCTOR !
        // IT IS HERE ONLY FOR ANDROID BACKGROUND PROCESS (FRAGMENT RECREATION)
    }


    /**
     * Constructor of the ConfigurationFragment class
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model including Wifi and Email configuration information.
     */
    public ConfigurationFragment(MainActivityController container, ConfigurationModel model)
    {
        mController = new ConfigurationController(container, model, this);
        super.setBaseController(mController);
    }

    /* -------------------- Methods -------------------- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If the fragment is in an illegal state, don't continue, wait the recreation of it.
        if (super.onCreateView(inflater, container, savedInstanceState) == null)
            return null;

        /* ------ Functionality still in development, delete to continue development ------- */

        mRootView = inflater.inflate(R.layout.fragment_in_development, container, false);
        return mRootView;

        /* ------ Uncomment to continue development ------- */

//        mRootView = inflater.inflate(R.layout.fragment_configuration, container, false);
//        return mRootView;
    }
}
