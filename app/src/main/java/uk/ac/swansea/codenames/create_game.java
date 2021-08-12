package uk.ac.swansea.codenames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

public class create_game extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private LinearLayout customWordsLinear;
    private Switch privateSwitch;
    private EditText roomNameEdit;
    private EditText passwordEdit;
    private TextView[] customWordTexts = new TextView[10];
    private TextView bombSquaresText;
    private TextView neutralSquaresText;
    private TextView teamASquaresText;
    private TextView teamBSquaresText;
    private TextView startingTeamText;
    private TextView customWordsText;
    private TextView createGameTitle;
    private Button backButton;
    private Button gameOptionsButton;
    private Button createButton;

    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.create_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        customWordsLinear = findViewById(R.id.customWordsLinear);
        privateSwitch = findViewById(R.id.privateSwitch);
        roomNameEdit = findViewById(R.id.roomNameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        bombSquaresText = findViewById(R.id.bombSquaresText);
        neutralSquaresText = findViewById(R.id.neutralSquaresText);
        teamASquaresText = findViewById(R.id.teamASquaresText);
        teamBSquaresText = findViewById(R.id.teamBSquaresText);
        startingTeamText = findViewById(R.id.startingTeamText);
        customWordsText = findViewById(R.id.customWordsText);
        createGameTitle = findViewById(R.id.createGameTitle);
        backButton = findViewById(R.id.backButton);
        gameOptionsButton = findViewById(R.id.gameOptionsButton);
        createButton = findViewById(R.id.createButton);

        if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
            customWordsText.setVisibility(View.VISIBLE);

            bombSquaresText.setText(getString(R.string.bomb_squares) + " " + String.valueOf(getIntent().getIntExtra("bombSquares", 1)));
            neutralSquaresText.setText(getString(R.string.neutral_squares) + " " + String.valueOf(getIntent().getIntExtra("neutralSquares", 7)));
            teamASquaresText.setText(getString(R.string.team_a_squares) + " " + String.valueOf(getIntent().getIntExtra("teamASquares", 9)));
            teamBSquaresText.setText(getString(R.string.team_b_squares) + " " + String.valueOf(getIntent().getIntExtra("teamBSquares", 8)));

            String startingTeam;

            if (getIntent().getIntExtra("startingTeam", 1) == 1) {
                startingTeam = "A";
            } else {
                startingTeam = "B";
            }

            startingTeamText.setText(getString(R.string.starting_team) + " " + startingTeam);

            if (getIntent().getStringArrayListExtra("customWords") != null) {
                int counter = 0;

                while(counter < getIntent().getStringArrayListExtra("customWords").size()) {
                    TextView newCustomWord = new TextView(this);

                    newCustomWord.setText(getIntent().getStringArrayListExtra("customWords").get(counter));

                    Typeface typeface = ResourcesCompat.getFont(this, R.font.general_font);
                    newCustomWord.setTypeface(typeface);

                    newCustomWord.setTextSize(17);

                    customWordTexts[counter] = newCustomWord;

                    customWordsLinear.addView(newCustomWord);

                    counter++;
                }
            }
        }

        privateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEdit.setVisibility(View.VISIBLE);
                } else {
                    passwordEdit.setVisibility(View.INVISIBLE);
                }

                passwordEdit.setText("");
            }
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i = new Intent(view.getContext(), online_setup.class);
        startActivity(i);
    }

    public void gameOptions(View view) {
        Intent i = new Intent(view.getContext(), game_options.class);

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
    }

    private void updateColours() {
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
        createButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        createGameTitle.setTextColor(defaultColour);
        bombSquaresText.setTextColor(defaultColour);
        neutralSquaresText.setTextColor(defaultColour);
        teamASquaresText.setTextColor(defaultColour);
        teamBSquaresText.setTextColor(defaultColour);
        startingTeamText.setTextColor(defaultColour);
        customWordsText.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        gameOptionsButton.setTextColor(defaultColour);
        createButton.setTextColor(defaultColour);
        privateSwitch.setTextColor(defaultColour);

        for (TextView t : customWordTexts) {
            if (t != null) {
                t.setTextColor(defaultColour);
            }
        }
    }
}
