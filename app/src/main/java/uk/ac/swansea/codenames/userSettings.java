package uk.ac.swansea.codenames;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class userSettings {
    private static userSettings mInstance;
    private Context mContext;

    private SharedPreferences myPreferences;

    public final int YOUR_TEAM_DEFAULT = -16773377;
    public final int OTHER_TEAM_DEFAULT = -65536;
    public final int BOMB_SQUARE_DEFAULT = -14342875;
    public final int NEUTRAL_SQUARE_DEFAULT = -908;
    public final int UNMODIFIED_SQUARE_DEFAULT = -3684409;
    public final int APPLICATION_BACKGROUND_DEFAULT = -10921639;
    public final int MENU_BUTTONS_DEFAULT = -8164501;
    public final int MENU_TEXT_DEFAULT = -1;
    public final String YOUR_TEAM = "yourTeamColour";
    public final String OTHER_TEAM = "otherTeamColour";
    public final String BOMB_SQUARE = "bombSquareColour";
    public final String NEUTRAL_SQUARE = "neutralSquareColour";
    public final String UNMODIFIED_SQUARE = "unmodifiedSquareColour";
    public final String APPLICATION_BACKGROUND = "applicationBackgroundColour";
    public final String MENU_BUTTONS = "menuButtonsColour";
    public final String MENU_TEXT = "menuTextColour";

    private userSettings() {
    }

    public static userSettings getInstance() {
        if (mInstance == null) {
            mInstance = new userSettings();
        }

        return mInstance;
    }

    public void Initialize(Context context) {
        mContext = context;

        myPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void writePreference(String key, String value) {
        SharedPreferences.Editor e = myPreferences.edit();
        e.putString(key, value);
        e.apply();
    }

    public String getPreference(String key) {
        return myPreferences.getString(key, "");
    }
}
