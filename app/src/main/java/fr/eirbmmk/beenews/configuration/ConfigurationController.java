package fr.eirbmmk.beenews.configuration;

import fr.eirbmmk.beenews.BaseController;
import fr.eirbmmk.beenews.MainActivityController;

/**
 * This class correspond to the controller part of the MVC design pattern for the Email and Wifi
 * configuration functionality of the application.
 */
public class ConfigurationController extends BaseController {

    /* -------------------- Attributes -------------------- */


    /**
    * Model part of the MVC design pattern
    */
    private ConfigurationModel mModel;

    /**
    * View part of the MVC design pattern
    */
    private ConfigurationFragment mFragment;


      /* -------------------- Constructors -------------------- */


    /**
     * Constructor of ConfigurationController.
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model for the Email and Wifi configuration functionality
     * @param fragment the view of the Email and Wifi configuration functionality
     */
    public ConfigurationController(MainActivityController container, ConfigurationModel model, ConfigurationFragment fragment)
    {
        super(container);
        mModel = model;
        mFragment = fragment;
    }


    /* -------------------- Methods -------------------- */
}
