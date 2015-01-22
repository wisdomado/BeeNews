package fr.eirbmmk.beenews.clubs;

/**
 * Class representing a Club
 */
public class Club {

    /**
     * Club ID
     */
    private String mId;

    /**
     * Club Name
     */
    private String mName;

    /**
     * Club logo URL
     */
    private String mIconURL;

    /**
     * Constructor of the Club class
     * @param id club ID
     * @param name club name
     * @param iconURL club download logo URL
     */
    public Club(String id, String name, String iconURL){
        mId = id;
        mName = name;
        mIconURL = iconURL;
    }

    /**
     * Returns Club ID
     * @return the Club ID
     */
    public String getId() {
        return mId;
    }

    /**
     * Returns Club Name
     * @return club name
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns Club logo URL to download it
     * @return the logo URL
     */
    public String getIconURL() {
        return mIconURL;
    }
}
