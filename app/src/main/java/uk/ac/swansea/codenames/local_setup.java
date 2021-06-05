package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class local_setup extends AppCompatActivity {

    // TODO: Add functionality to both buttons.

    private ConstraintLayout constraintLayout;
    private TextView localSetupTitle;
    private TextView localSetupSubtitle;
    private Button backButton;
    private Button gameOptionsButton;
    private Button playGameButton;
    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.local_setup);

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

    }

    public void gameOptions(View view) {
        Intent i = new Intent(view.getContext(), game_options.class);
        i.putExtra("from", "local_setup");
        startActivity(i);
    }

    public void updateColours() {
        constraintLayout = findViewById(R.id.constraintLayout);
        localSetupTitle = findViewById(R.id.localSetupTitle);
        localSetupSubtitle = findViewById(R.id.localSetupSubtitle);
        backButton = findViewById(R.id.backButton);
        gameOptionsButton = findViewById(R.id.gameOptionsButton);
        playGameButton = findViewById(R.id.playGameButton);

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

        localSetupTitle.setTextColor(defaultColour);
        localSetupSubtitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        gameOptionsButton.setTextColor(defaultColour);
        playGameButton.setTextColor(defaultColour);
    }

}
