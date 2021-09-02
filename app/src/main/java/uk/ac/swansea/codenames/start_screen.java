package uk.ac.swansea.codenames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class start_screen extends AppCompatActivity {

    // TODO: Add an exit button.

    private androidx.gridlayout.widget.GridLayout quitBox;
    private ConstraintLayout constraintLayout;
    private Button playButton;
    private Button settingsButton;
    private Button yesButton;
    private Button noButton;
    private TextView quitText;
    private TextView startScreenTitle;
    private int defaultColour;
    private boolean exiting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.start_screen);

        quitBox = findViewById(R.id.quitBox);
        playButton = findViewById(R.id.playButton);
        settingsButton = findViewById(R.id.settingsButton);
        constraintLayout = findViewById(R.id.saveButton);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        quitText = findViewById(R.id.quitText);
        startScreenTitle = findViewById(R.id.startScreenTitle);

        userSettings.getInstance().Initialize(getApplicationContext());

        //This is how TTS will work. Pressing the button quickly performs it's default action. Holding it
        //with a return true in this listener, will cause it to run the code in here, and not run the code
        //for a regular click.
        //For actual TTS it won't print out the word, it'll obviously read it aloud
        playButton.setOnLongClickListener(v -> {
            System.out.println(playButton.getText().toString());
            return true;
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        if (exiting) {
            quitBox.setVisibility(View.INVISIBLE);
            playButton.setVisibility(View.VISIBLE);
            settingsButton.setVisibility(View.VISIBLE);

            exiting = false;
        } else {
            quitBox.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.INVISIBLE);
            settingsButton.setVisibility(View.INVISIBLE);

            exiting = true;
        }
    }

    public void toMainMenu(View view) {
        Intent i = new Intent(this, main_menu.class);
        startActivity(i);
    }

    public void toSettings(View view) {
        Intent i = new Intent(this, settings.class);
        i.putExtra("from", "start_screen");
        startActivity(i);
    }

    public void quitGame(View view) {
        this.finishAffinity();
        System.exit(0);
    }

    public void noQuit(View view) {
        quitBox.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.VISIBLE);
        settingsButton.setVisibility(View.VISIBLE);

        exiting = false;
    }

    public void updateColours() {
        if (userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND).equals("")) {
            defaultColour = userSettings.getInstance().APPLICATION_BACKGROUND_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND));
        }

        constraintLayout.setBackgroundColor(defaultColour);
        quitBox.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
            defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
        }

        playButton.setBackgroundColor(defaultColour);
        settingsButton.setBackgroundColor(defaultColour);
        yesButton.setBackgroundColor(defaultColour);
        noButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        quitText.setTextColor(defaultColour);
        startScreenTitle.setTextColor(defaultColour);
        playButton.setTextColor(defaultColour);
        settingsButton.setTextColor(defaultColour);
    }
}