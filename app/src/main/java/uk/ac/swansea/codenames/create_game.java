package uk.ac.swansea.codenames;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import io.socket.emitter.Emitter;

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
    private boolean validGame = true;

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

        createButton.setOnClickListener(v -> {
            validGame = true;

            String nickname = userSettings.getInstance().getPreference("username");

            if (nickname.equals("")) {
                toggleNicknameBox();
                validGame = false;
            } else if (!nickname.matches("[A-Za-z0-9]+")) {
                toggleNicknameBox();
                validGame = false;
            }

            String roomName = roomNameEdit.getText().toString();

            if (roomName.equals("")) {
                toggleMessageBox("Room name cannot be empty.", true);
                validGame = false;
            } else if (!roomName.matches("[A-Za-z0-9]+")) {
                toggleMessageBox("Room name must be alphanumerical.", true);
                validGame = false;
            }

            String password = passwordEdit.getText().toString();

            if (privateSwitch.isChecked()) {
                if (password.equals("")) {
                    toggleMessageBox("Please enter a password.", true);
                    validGame = false;
                } else if (!password.matches("[A-Za-z0-9]+")) {
                    toggleMessageBox("Password must be alphanumerical.", true);
                    validGame = false;
                }
            }

            String wordsFromFile = "";

            try {
                InputStream input = getAssets().open("gameWords");

                int size = input.available();

                byte[] buffer = new byte[size];

                input.read(buffer);
                input.close();

                wordsFromFile = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] temp = wordsFromFile.split(",");

            ArrayList<String> defaultWords = new ArrayList<>(Arrays.asList(temp));

            int MAX_WORDS = 25;
            int defaultWordsNeeded = MAX_WORDS;

            String[] gameWords = new String[MAX_WORDS];

            ArrayList<String> customWords = getIntent().getStringArrayListExtra("customWords");

            if (customWords != null) {
                for (int i = 0; i < customWords.size(); i++) {
                    gameWords[i] = customWords.get(i);
                    defaultWordsNeeded--;
                }
            }

            ArrayList<Integer> list = new ArrayList<>();

            for (int i = 0; i < defaultWords.size(); i++) {
                list.add(i);
            }

            Collections.shuffle(list);

            for (int i = 0; i < defaultWordsNeeded; i++) {
                gameWords[i + (MAX_WORDS - defaultWordsNeeded)] = defaultWords.get(list.get(i));
            }

            Collections.shuffle(Arrays.asList(gameWords));

            list.clear();

            for (int i = 0; i < MAX_WORDS; i++) {
                list.add(i);
            }

            Collections.shuffle(list);

            int counter = 0;
            int bombSquaresCount = getIntent().getIntExtra("bombSquares", 1);
            int neutralSquaresCount = getIntent().getIntExtra("neutralSquares", 7);
            int teamASquaresCount = getIntent().getIntExtra("teamASquares", 9);
            int teamBSquaresCount = getIntent().getIntExtra("teamBSquares", 8);

            String[] bombWords = new String[bombSquaresCount];
            String[] neutralWords = new String[neutralSquaresCount];
            String[] teamAWords = new String[teamASquaresCount];
            String[] teamBWords = new String[teamBSquaresCount];

            for (int i = 0; i < bombSquaresCount; i++) {
                bombWords[i] = gameWords[list.get(counter)];
                counter++;
            }

            for (int i = 0; i < neutralSquaresCount; i++) {
                neutralWords[i] = gameWords[list.get(counter)];
                counter++;
            }

            for (int i = 0; i < teamASquaresCount; i++) {
                teamAWords[i] = gameWords[list.get(counter)];
                counter++;
            }

            for (int i = 0; i < teamBSquaresCount; i++) {
                teamBWords[i] = gameWords[list.get(counter)];
                counter++;
            }

            int startingTeam = getIntent().getIntExtra("startingTeam", 1);

            JSONArray jsonGameWords = new JSONArray(Arrays.asList(gameWords));
            JSONArray jsonBombWords = new JSONArray(Arrays.asList(bombWords));
            JSONArray jsonNeutralWords = new JSONArray(Arrays.asList(neutralWords));
            JSONArray jsonTeamAWords = new JSONArray(Arrays.asList(teamAWords));
            JSONArray jsonTeamBWords = new JSONArray(Arrays.asList(teamBWords));

            if (validGame) {
                socketConnection.socket.emit("createRoom", nickname, roomName, password, jsonGameWords,
                        jsonBombWords, jsonNeutralWords, jsonTeamAWords, jsonTeamBWords, startingTeam);
            }

            backButton.setEnabled(false);
            gameOptionsButton.setEnabled(false);
            createButton.setEnabled(false);
            roomNameEdit.setEnabled(false);
            passwordEdit.setEnabled(false);
            privateSwitch.setEnabled(false);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (validGame) {
                        Intent i = new Intent(getApplicationContext(), online_game.class);
                        i.putExtra("username", userSettings.getInstance().getPreference("username"));
                        i.putExtra("roomName", roomName);
                        i.putExtra("isHost", true);
                        startActivity(i);
                    } else {
                        backButton.setEnabled(true);
                        gameOptionsButton.setEnabled(true);
                        createButton.setEnabled(true);
                        roomNameEdit.setEnabled(true);
                        passwordEdit.setEnabled(true);
                        privateSwitch.setEnabled(true);
                    }
                }
            }, 3000);
        });

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

        socketConnection.socket.on("createFail", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                toggleMessageBox((String) args[0], false);
                validGame = false;
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

    private void toggleMessageBox(String message, boolean visibleButton) {

    }

    private void toggleNicknameBox() {

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
