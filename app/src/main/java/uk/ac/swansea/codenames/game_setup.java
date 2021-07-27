package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class game_setup extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView gameSetupTitle;
    private TextView gameSetupSubtitle;
    private Button backButton;
    private Button gameOptionsButton;
    private Button playGameButton;
    private int defaultColour;

    private boolean creatingGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_setup);

        constraintLayout = findViewById(R.id.saveButton);
        gameSetupTitle = findViewById(R.id.gameSetupTitle);
        gameSetupSubtitle = findViewById(R.id.gameSetupSubtitle);
        backButton = findViewById(R.id.backButton);
        gameOptionsButton = findViewById(R.id.gameOptionsButton);
        playGameButton = findViewById(R.id.playGameButton);

        creatingGame = getIntent().getBooleanExtra("creatingGame", false);
        
        if (getIntent().getStringExtra("type").equals("local")) {
            gameSetupSubtitle.setText("Local");
            gameOptionsButton.setText("Game Options");
            playGameButton.setText("Play");
        } else {
            gameSetupSubtitle.setText("Online");
            playGameButton.setText("Create Game");

            if (creatingGame) {
                gameOptionsButton.setText("Game Options");
            } else {
                gameOptionsButton.setText("Join Game");
            }
        }

        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i = new Intent(view.getContext(), main_menu.class);
        startActivity(i);
    }

    public void playGame(View view) {
        Intent i = new Intent(view.getContext(), local_game.class);

        if (getIntent().getStringExtra("type").equals("local")) {
            if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
                i.putExtra("hasCustomSettings", true);
                i.putExtra("bombSquares", getIntent().getIntExtra("bombSquares", 1));
                i.putExtra("neutralSquares", getIntent().getIntExtra("neutralSquares", 7));
                i.putExtra("teamASquares", getIntent().getIntExtra("teamASquares", 9));
                i.putExtra("teamBSquares", getIntent().getIntExtra("teamBSquares", 8));
                i.putExtra("startingTeam", getIntent().getIntExtra("startingTeam", 1));
                i.putStringArrayListExtra("customWords", getIntent().getStringArrayListExtra("customWords"));
            }

            startActivity(i);
        } else {
            if (creatingGame) {
                //Check that the server name used is valid and not in use already with checking if the num of users is 0.
            } else {
                gameOptionsButton.setText("Game Options");
                creatingGame = true;
            }
        }
    }

    public void gameOptions(View view) {
        Intent i;

        if (getIntent().getStringExtra("type").equals("local")) {
            i = new Intent(view.getContext(), game_options.class);

            i.putExtra("type", "local");

            if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
                i.putExtra("hasCustomSettings", true);
                i.putExtra("bombSquares", getIntent().getIntExtra("bombSquares", 1));
                i.putExtra("neutralSquares", getIntent().getIntExtra("neutralSquares", 7));
                i.putExtra("teamASquares", getIntent().getIntExtra("teamASquares", 9));
                i.putExtra("teamBSquares", getIntent().getIntExtra("teamBSquares", 8));
                i.putExtra("startingTeam", getIntent().getIntExtra("startingTeam", 1));
                i.putStringArrayListExtra("customWords", getIntent().getStringArrayListExtra("customWords"));
            }

            startActivity(i);
        } else {
            if (creatingGame) {
                i = new Intent(view.getContext(), game_options.class);

                i.putExtra("type", "online");

                if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
                    i.putExtra("hasCustomSettings", true);
                    i.putExtra("bombSquares", getIntent().getIntExtra("bombSquares", 1));
                    i.putExtra("neutralSquares", getIntent().getIntExtra("neutralSquares", 7));
                    i.putExtra("teamASquares", getIntent().getIntExtra("teamASquares", 9));
                    i.putExtra("teamBSquares", getIntent().getIntExtra("teamBSquares", 8));
                    i.putExtra("startingTeam", getIntent().getIntExtra("startingTeam", 1));
                    i.putStringArrayListExtra("customWords", getIntent().getStringArrayListExtra("customWords"));
                }

                startActivity(i);
            } else {
                //Joining game stuff goes here so checking if room exists (0 users or maybe 1 if this connection counts)
                i = new Intent(view.getContext(), online_game.class);

                startActivity(i);
            }
        }
    }

    public void updateColours() {
        if (userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND).equals("")) {
            defaultColour = userSettings.getInstance().APPLICATION_BACKGROUND_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND));
        }

        constraintLayout.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
            defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
        }

        backButton.setBackgroundColor(defaultColour);
        gameOptionsButton.setBackgroundColor(defaultColour);
        playGameButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        gameSetupTitle.setTextColor(defaultColour);
        gameSetupSubtitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        gameOptionsButton.setTextColor(defaultColour);
        playGameButton.setTextColor(defaultColour);
    }

}
