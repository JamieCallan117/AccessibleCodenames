package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class game_options extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView gameOptionsTitle;
    private TextView gameOptionsSubtitle;
    private Button backButton;
    private boolean hasCustomSettings;
    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_options);

        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle);

        if (getIntent().getStringExtra("from").equals("local_setup")) {
            gameOptionsSubtitle.setText("Local");
        } else if (getIntent().getStringExtra("from").equals("online_setup")) {
            gameOptionsSubtitle.setText("Online");
        }

        /*LinearLayout scroll = findViewById(R.id.scrollLinear);

        for (int i = 0; i < 100; i++) {
            TextView temp = new TextView(this);

            temp.setText("Ahoy: " + i);

            scroll.addView(temp);
        }*/


        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i;

        if (getIntent().getStringExtra("from").equals("local_setup")) {
            if (hasCustomSettings) {
                //Add the custom stuff in here i.putExtra() etc
                i = new Intent(view.getContext(), local_setup.class);
                startActivity(i);
            } else {
                i = new Intent(view.getContext(), local_setup.class);
                startActivity(i);
            }
        } else   if (getIntent().getStringExtra("from").equals("online_setup")) {
            if (hasCustomSettings) {
                //Add the custom stuff in here i.putExtra() etc
                i = new Intent(view.getContext(), online_setup.class);
                startActivity(i);
            } else {
                i = new Intent(view.getContext(), online_setup.class);
                startActivity(i);
            }
        }
    }

    public void updateColours() {
        constraintLayout = findViewById(R.id.constraintLayout);
        gameOptionsTitle = findViewById(R.id.gameOptionsTitle);
        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle);
        backButton = findViewById(R.id.backButton);

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

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        gameOptionsTitle.setTextColor(defaultColour);
        gameOptionsSubtitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
    }
}
