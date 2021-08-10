package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class online_setup extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private androidx.gridlayout.widget.GridLayout messageBox;
    private TextView gameSetupTitle;
    private TextView gameSetupSubtitle;
    private TextView messageText;
    private Button backButton;
    private Button gameOptionsButton;
    private Button playGameButton;
    private Button okButton;

    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.local_setup);

        constraintLayout = findViewById(R.id.constraintLayout);
        gameSetupTitle = findViewById(R.id.gameSetupTitle);
        gameSetupSubtitle = findViewById(R.id.gameSetupSubtitle);
        backButton = findViewById(R.id.backButton);
        gameOptionsButton = findViewById(R.id.joinGameButton);
        playGameButton = findViewById(R.id.createGameButton);
        messageBox = findViewById(R.id.messageBox);
        messageText = findViewById(R.id.messageText);
        okButton = findViewById(R.id.okButton);

        okButton.setOnClickListener(v -> {
            Intent i = new Intent(getBaseContext(), main_menu.class);
            startActivity(i);
        });

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

    public void createGame(View view) {
        Intent i = new Intent(view.getContext(), create_game.class);
        startActivity(i);
    }

    public void joinGame(View view) {
        Intent i = new Intent(view.getContext(), join_game.class);
        startActivity(i);
    }


    public void updateColours() {
        if (userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND).equals("")) {
            defaultColour = userSettings.getInstance().APPLICATION_BACKGROUND_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().APPLICATION_BACKGROUND));
        }

        constraintLayout.setBackgroundColor(defaultColour);
        messageBox.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
            defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
        }

        backButton.setBackgroundColor(defaultColour);
        gameOptionsButton.setBackgroundColor(defaultColour);
        playGameButton.setBackgroundColor(defaultColour);
        okButton.setBackgroundColor(defaultColour);

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
        messageText.setTextColor(defaultColour);
        okButton.setTextColor(defaultColour);
    }

}
