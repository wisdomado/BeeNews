package fr.eirbmmk.beenews;

import android.text.format.Time;

import java.util.Calendar;

/**
 * Created by valentin on 08/03/14.
 */
public class Token {

    /**
     * Access token
     */
    private String mAccessToken;

    /**
     * Refresh token
     */
    private String mRefreshToken;

    /**
     * com.Token type
     */
    private String mType;

    /**
     * Expiration date of the Access Token
     */
    private Calendar mExpirationDate;

    /**
     * Constructor of the com.Token class
     * @param accessToken
     * @param refreshToken
     * @param type
     * @param expirationDate
     */
    public Token(String accessToken, String refreshToken, String type, Calendar expirationDate)
    {
        mAccessToken = accessToken;
        mRefreshToken = refreshToken;
        mType = type;
        mExpirationDate = expirationDate;
    }

    /**
     * Returns the access token
     * @return the access token
     */
    public String getAccessToken() {
        return mAccessToken;
    }

    /**
     * Returns the token type
     * @return the token type
     */
    public String getType() {
        return mType;
    }

    /**
     * Returns the refresh token
     * @return the refresh token
     */
    public String getRefreshToken() {
        return mRefreshToken;
    }

    /**
     * Returns the token expiration date
     * @return the token expiration date
     */
    public Calendar getExpirationDate() {
        return mExpirationDate;
    }

    @Override
    public String toString()
    {
        StringBuilder mess = new StringBuilder();

        mess.append("access token : ").append(mAccessToken).append(", ");
        mess.append("refresh token : ").append(mRefreshToken).append(", ");
        mess.append("token type : ").append(mType).append(", ");
        mess.append("expiration date : ").append(mExpirationDate.toString());

        return mess.toString();
    }
}
