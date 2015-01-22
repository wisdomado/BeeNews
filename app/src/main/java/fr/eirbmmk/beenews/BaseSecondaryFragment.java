package fr.eirbmmk.beenews;

/**
 * Class that all functionality fragments considered as secondary display have to extend.
 * It permit them to be closed and to come back to the main functionality fragment.
 */
public abstract class BaseSecondaryFragment extends BaseFragment {

    /**
     * This methods is used to close the present secondary displayed fragment.
     */
    public void closeFragment()
    {
        getFragmentManager().popBackStack();
    }

    /**
     * Sets the Base controller to be able to stop asynctasks when leaving the screen
     * @param controller the baseController to set
     */
    public void setBaseController(BaseController controller) { super.setBaseController(controller); }
}
