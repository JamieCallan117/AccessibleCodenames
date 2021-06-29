package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class main_menu extends AppCompatActivity {

    // TODO: Increase button size

    private ConstraintLayout constraintLayout;
    private TextView mainMenuTitle;
    private Button backButton;
    private Button playOnline;
    private Button playLocal;
    private Button settings;
    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_menu);

        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i = new Intent(view.getContext(), start_screen.class);
        startActivity(i);
    }

    public void toSettings(View view) {
        Intent i = new Intent(this, settings.class);
        i.putExtra("from", "main_menu");
        startActivity(i);
    }

    public void playLocal(View view) {
        Intent i = new Intent(view.getContext(), local_setup.class);
        startActivity(i);
    }

    public void playOnline(View view) {
        Intent i = new Intent(view.getContext(), online_setup.class);
        startActivity(i);
    }

    public void updateColours() {
        constraintLayout = findViewById(R.id.saveButton);
        mainMenuTitle = findViewById(R.id.mainMenuTitle);
        backButton = findViewById(R.id.backButton);
        playOnline = findViewById(R.id.playOnline);
        playLocal = findViewById(R.id.playLocal);
        settings = findViewById(R.id.settings);

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
        playOnline.setBackgroundColor(defaultColour);
        playLocal.setBackgroundColor(defaultColour);
        settings.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        mainMenuTitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        playOnline.setTextColor(defaultColour);
        playLocal.setTextColor(defaultColour);
        settings.setTextColor(defaultColour);
    }
}
