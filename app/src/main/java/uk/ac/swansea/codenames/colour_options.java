package uk.ac.swansea.codenames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import yuku.ambilwarna.AmbilWarnaDialog;

public class colour_options extends AppCompatActivity {

    // TODO: Figure out how to get inverse colours. And then make the button text inverse of the button.
    // TODO: Add confirmation box to the reset button.

    private Button yourTeamButton;
    private Button otherTeamButton;
    private Button bombSquareButton;
    private Button neutralSquareButton;
    private Button unmodifiedSquareButton;
    private Button applicationBackgroundButton;
    private Button menuButtonsButton;
    private Button menuTextButton;
    private Button backButton;
    private Button resetColoursButton;
    private TextView colourTitle;
    private ConstraintLayout constraintLayout;
    private int defaultColour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.colour_options);

        updateColours();

        yourTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().YOUR_TEAM);
            }
        });

        otherTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().OTHER_TEAM);
            }
        });

        bombSquareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().BOMB_SQUARE);
            }
        });

        neutralSquareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().NEUTRAL_SQUARE);
            }
        });

        unmodifiedSquareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().UNMODIFIED_SQUARE);
            }
        });

        applicationBackgroundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().APPLICATION_BACKGROUND);
            }
        });

        menuButtonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().MENU_BUTTONS);
            }
        });

        menuTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker(userSettings.getInstance().MENU_TEXT);
            }
        });

    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i = new Intent(view.getContext(), settings.class);;

        if (getIntent().getStringExtra("from").equals("start_screen")) {
            i.putExtra("from", "start_screen");
        } else if (getIntent().getStringExtra("from").equals("main_menu")) {
            i.putExtra("from", "main_menu");
        }

        startActivity(i);
    }

    public void resetColours(View view) {
        userSettings.getInstance().writePreference(userSettings.getInstance().YOUR_TEAM, String.valueOf(userSettings.getInstance().YOUR_TEAM_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().OTHER_TEAM, String.valueOf(userSettings.getInstance().OTHER_TEAM_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().BOMB_SQUARE, String.valueOf(userSettings.getInstance().BOMB_SQUARE_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().NEUTRAL_SQUARE, String.valueOf(userSettings.getInstance().NEUTRAL_SQUARE_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().UNMODIFIED_SQUARE, String.valueOf(userSettings.getInstance().UNMODIFIED_SQUARE_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().APPLICATION_BACKGROUND, String.valueOf(userSettings.getInstance().APPLICATION_BACKGROUND_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().MENU_BUTTONS, String.valueOf(userSettings.getInstance().MENU_BUTTONS_DEFAULT));
        userSettings.getInstance().writePreference(userSettings.getInstance().MENU_TEXT, String.valueOf(userSettings.getInstance().MENU_TEXT_DEFAULT));

        updateColours();
    }

    public void updateColours() {
        yourTeamButton = findViewById(R.id.yourTeamButton);
        otherTeamButton = findViewById(R.id.otherTeamButton);
        bombSquareButton = findViewById(R.id.bombSquareButton);
        neutralSquareButton = findViewById(R.id.neutralSquareButton);
        unmodifiedSquareButton = findViewById(R.id.unmodifiedSquareButton);
        applicationBackgroundButton = findViewById(R.id.applicationBackgroundButton);
        menuButtonsButton = findViewById(R.id.menuButtonsButton);
        menuTextButton = findViewById(R.id.menuTextButton);
        backButton = findViewById(R.id.backButton);
        resetColoursButton = findViewById(R.id.resetColoursButton);
        colourTitle = findViewById(R.id.colourTitle);
        constraintLayout = findViewById(R.id.constraintLayout);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().YOUR_TEAM).equals("")) {
            defaultColour = userSettings.getInstance().YOUR_TEAM_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().YOUR_TEAM));
        }

        yourTeamButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().OTHER_TEAM).equals("")) {
            defaultColour = userSettings.getInstance().OTHER_TEAM_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().OTHER_TEAM));
        }

        otherTeamButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().BOMB_SQUARE).equals("")) {
            defaultColour = userSettings.getInstance().BOMB_SQUARE_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().BOMB_SQUARE));
        }

        bombSquareButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().NEUTRAL_SQUARE).equals("")) {
            defaultColour = userSettings.getInstance().NEUTRAL_SQUARE_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().NEUTRAL_SQUARE));
        }

        neutralSquareButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().UNMODIFIED_SQUARE).equals("")) {
            defaultColour = userSettings.getInstance().UNMODIFIED_SQUARE_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().UNMODIFIED_SQUARE));
        }

        unmodifiedSquareButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND).equals("")) {
            defaultColour = userSettings.getInstance().APPLICATION_BACKGROUND_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND));
        }

        applicationBackgroundButton.setBackgroundColor(defaultColour);
        constraintLayout.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
            defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
        }

        menuButtonsButton.setBackgroundColor(defaultColour);
        backButton.setBackgroundColor(defaultColour);
        resetColoursButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        menuTextButton.setBackgroundColor(defaultColour);
        colourTitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        resetColoursButton.setTextColor(defaultColour);
    }

    public void openColourPicker(String colourToChange) {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColour, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColour = color;
                userSettings.getInstance().writePreference(colourToChange, String.valueOf(defaultColour));
                Log.i("Chosen colour", String.valueOf(defaultColour)); //Outputs the chosen colour
                updateColours();
            }
        });

        ambilWarnaDialog.show();
    }
}
