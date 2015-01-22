package fr.eirbmmk.beenews.clubs;

/**
 * Class representing a Club detail item.
 * Clubs details items are fetched when getting back a particular club details.
 */
public class ClubDetailsItem {

    /**
     * Club detail item Type
     */
    private ClubDetailsItemType mType;

    /**
     * Club details item content
     */
    private String mContent;

    /**
     * Constructor of the ClubDetailsItem class
     *
     * @param type club details item type
     * @param content club details item content
     */
    public ClubDetailsItem(ClubDetailsItemType type, String content){
        mType = type;
        mContent = content;
    }

    /**
     * Returns Club Details item type
     * @return item type
     */
    public ClubDetailsItemType getType() {
        return mType;
    }

    /**
     * Returns Club Details item content
     * @return the content
     */
    public String getContent() {
        return mContent;
    }
}
