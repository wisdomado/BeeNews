package fr.eirbmmk.beenews.about;

import fr.eirbmmk.beenews.BaseController;
import fr.eirbmmk.beenews.MainActivityController;

/**
 * Created by lionel on 21/01/15.
 */
public class AboutController extends BaseController {
/* -------------------- Attributes -------------------- */


    /**
     * Model part of the MVC design pattern
     */
    private AboutModel mModel;

    /**
     * View part of the MVC design pattern
     */
    private AboutFragment mFragment;


      /* -------------------- Constructors -------------------- */


    /**
     * Constructor of ConfigurationController.
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model for the Email and Wifi configuration functionality
     * @param fragment the view of the Email and Wifi configuration functionality
     */
    public AboutController(MainActivityController container, AboutModel model, AboutFragment fragment)
    {
        super(container);
        mModel = model;
        mFragment = fragment;
    }


    /* -------------------- Methods -------------------- */
}
