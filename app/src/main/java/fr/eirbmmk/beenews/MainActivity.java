package fr.eirbmmk.beenews;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class MainActivity extends Activity {

    /* -------------------- Attributes -------------------- */


    /**
     * Controller part of the mainActivity MVC pattern.
     */
    private MainActivityController mController;

    /**
     * Left and Right menus widget
     */
    private SlidingMenu mMenu;


    /* -------------------- Methods -------------------- */


    /**
     * This method set up the MVC design pattern for the mainActivity of the application.
     * It creates the Activity's Model and Controller.
     */
    public void createMVC()
    {
        MainActivityModel model = new MainActivityModel(getSharedPreferences("EnseirbPrefrences", 0));
        mController = new MainActivityController(model, this);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // XML layout inflation
		setContentView(R.layout.activity_main);

        // set up the MVC design pattern
        createMVC();

        // display custom actionBar layout
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar);
//        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
        // set up left and right menus
        configureSlidingMenus();

        // Launch the News screen considered as the "Home" screen
		mController.createMVCFragment(Screen.LOGIN);
	}

    /**
     * This method initializes the Sliding menu component.
     * Especially, it sets up left and right menus.
     *
     */
    private void configureSlidingMenus()
    {
        // ---- Configure the SlidingMenu component ----

        mMenu = new SlidingMenu(this);
        mMenu.setMode(SlidingMenu.LEFT);
        mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mMenu.setShadowWidthRes(R.dimen.shadow_width);
        mMenu.setShadowDrawable(R.drawable.left_shadow);
        mMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mMenu.setFadeDegree(1.0f);
        mMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        mMenu.setMenu(R.layout.left_menu);

        // ---- Configure left menu ----

        if (mController.isUserAuthenticated())
            setLeftMenuItems(true);
        else
            setLeftMenuItems(false);
    }

    /**
     * This method sets up the left menu items.
     * Depending on if the user is authenticated or not, it gets the corresponding items to display
     * from the strings.xml resources file, creates a list of the corresponding screens,
     * saves it in the Model and displays it.
     *
     * @param authenticatedMode boolean indicated the left menu type (authenticated or not) to load.
     */
    public void setLeftMenuItems(boolean authenticatedMode)
    {
        LeftMenuListAdapter adapter; //adapter for left menu layout items
        ArrayList<Screen> leftMenuItems = new ArrayList<Screen>();
        ListView leftMenu = (ListView) mMenu.getMenu().findViewById(R.id.leftMenu); //List widget of left menu items
        String [] items;

        if (authenticatedMode)
            items = getResources().getStringArray(R.array.auth_left_menu_items);
        else
            items = getResources().getStringArray(R.array.non_auth_left_menu_items);

        for (String item : items)
        {
            Screen correspondingScreen;
            try
            {
                correspondingScreen = Screen.valueOf(item);
            }
            catch(Exception e)
            {
                android.util.Log.i("ERROR", e.getMessage());
                continue; // go to the next element
            }

            leftMenuItems.add(correspondingScreen);
        }

        // save displayed left menu items in the model
        mController.saveLeftMenuItems(leftMenuItems);

        // define the onClick action for items
        leftMenu.setOnItemClickListener(new LeftMenuItemClickListener());

        // set left menu list adapter
        adapter = new LeftMenuListAdapter(getApplicationContext(), leftMenuItems);

        // fill the left menu listView using the adapter
        leftMenu.setAdapter(adapter);
    }

    /**
     * Method called when the left button of the action bar is clicked.
     * Display the left menu.
     *
     * @param v the view
     */
    public void leftMenuButtonClicked(View v)
    {
        // toggle the left Menu
        if (mMenu.isMenuShowing())
            mMenu.showContent();
        else
            mMenu.showMenu();
    }

    /**
	 * Slide menu item click listener
	 * */
	private class LeftMenuItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

            //get the screen type corresponding to the selected item
			Screen screenToDisplay = mController.getLeftItemScreen(position);

            // display the selected screen
            mController.createMVCFragment(screenToDisplay);
		}
	}

    /**
     * Displays in the MainActivity the main fragment given in parameter.
     * A main fragment is the first fragment / screen displayed when a functionality is launched
     * (using the left menu).
     * Each functionality has its own main fragment.
     * A functionality can need others fragments to display additional content, for that,
     * see displaySecondaryFragment method.
     *
     * @param fragment the main fragment to display
     */
    public void displayMainFragment(Fragment fragment)
    {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // replace the fragment currently displayed int the activity Layout by the new fragment
        transaction.replace(R.id.activity_layout,fragment);

        // display changes
        transaction.commit();
    }

    /**
     *
     * This method displays in the MainActivity the secondary fragment given in parameter.
     * A secondary fragment is an internal fragment of a package / functionality which is not
     * automatically displayed when the functionality is loads (using the left menu of the application)
     * and permits to display additional content for the suitable functionality.
     * For instance, the news main fragment is the list of news and the secondary one is another one
     * which displays a selected news in detail.
     *
     * When the back button is pressed on a secondary fragment, it comes back to the main fragment
     * of the functionality.
     *
     * @param fragment the secondary fragment to display
     */
    public void displaySecondaryFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // permit to use the back button to come back to the main fragment.
        transaction.addToBackStack(null);

        // add simple fade in and out transitions for fragment display
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // replace the fragment currently displayed int the activity Layout by the new fragment
        transaction.replace(R.id.activity_layout,fragment);

        // display changes
        transaction.commit();
    }

    /**
     * Close the left menu if its opened.
     *
     */
    public void closeLeftMenu()
    {
        if (mMenu.isMenuShowing())
            mMenu.showContent();
    }

    /**
     * Set the title displayed in the ActionBar to the one given in parameter.
     *
     * @param title the title to display
     */
    public void setActionBarTitle(String title)
    {
        TextView titleView = (TextView) getActionBar().getCustomView().findViewById(R.id.actionBarTitle);

        titleView.setText(title);
    }

    /**
     * This method displays DialogBox to indicates that a screen / functionality failed to load.
     *
     * @param screen the screen which failed to be loaded.
     */
    public void displayLoadingFragmentError(Screen screen)
    {
        // Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Erreur")
               .setMessage("Le module " + screen + " a rencontré une erreur est n'a pu être chargé.")
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User clicked OK button
                   }
               });


        // Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Displays the error message in an alert box
     *
     * @param message the error message to display
     */
    public void displayErrorMessage(String message)
    {
        // Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Erreur")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });


        // Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Asks the MainActivityController to Recreate all the MVC pattern of the current displayed
     * fragment.
     * It is used to resolve the Operating System fragment destruction when the application
     * is inactive. See also the onCreateView of each fragment.
     *
     * Warning : If the application was completely killed by the OS, the current fragment will always
     * be the "home" fragment, at this time, the News fragment.
     */
    public void recreateCurrentMVCFragment()
    {
        mController.recreateCurrentMVCFragment();
    }

    /**
     * Tests if the network is connected and if internet is accessible or not.
     *
     * @return true if connected, false otherwise
     */
    public boolean isNetworkConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
