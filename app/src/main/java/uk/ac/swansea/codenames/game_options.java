package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class game_options extends AppCompatActivity {

    //TODO: Check for duplicate custom words

    private ConstraintLayout constraintLayout;
    private androidx.gridlayout.widget.GridLayout messageBox;
    private TextView gameOptionsTitle;
    private TextView gameOptionsSubtitle;
    private TextView squaresInUse;
    private TextView bombSquareCount;
    private TextView neutralSquareCount;
    private TextView teamASquaresCount;
    private TextView teamBSquaresCount;
    private TextView messageBoxText;
    private EditText customWordText1;
    private EditText customWordText2;
    private EditText customWordText3;
    private EditText customWordText4;
    private EditText customWordText5;
    private EditText customWordText6;
    private EditText customWordText7;
    private EditText customWordText8;
    private EditText customWordText9;
    private EditText customWordText10;
    private Button backButton;
    private Button saveButton;
    private Button yesButton;
    private Button noButton;
    private Button okButton;
    private ImageButton deleteCustomWord1;
    private ImageButton deleteCustomWord2;
    private ImageButton deleteCustomWord3;
    private ImageButton deleteCustomWord4;
    private ImageButton deleteCustomWord5;
    private ImageButton deleteCustomWord6;
    private ImageButton deleteCustomWord7;
    private ImageButton deleteCustomWord8;
    private ImageButton deleteCustomWord9;
    private ImageButton deleteCustomWord10;
    private ImageView addBombsButton;
    private ImageView subtractBombsButton;
    private ImageView addNeutralsButton;
    private ImageView subtractNeutralsButton;
    private ImageView teamASqrInc;
    private ImageView teamASqrDec;
    private ImageView teamBSqrInc;
    private ImageView teamBSqrDec;
    private ToggleButton startingTeamButton;
    private int defaultColour;
    private int bombSquares = 1;
    private int neutralSquares = 7;
    private int teamASquares = 9;
    private int teamBSquares = 8;
    private int startingTeam = 1;
    private int totalSquaresInUse = 25;
    private ArrayList<String> customWords = new ArrayList<>();
    private boolean hasCustomSettings;
    private boolean disabled = false;
    private boolean windowOpen = false;
    private final int MAX_SQUARES = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_options);

        messageBox = findViewById(R.id.messageBox);
        messageBoxText = findViewById(R.id.messageBoxText);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        okButton = findViewById(R.id.okButton);

        customWordText1 = findViewById(R.id.customWordText1);
        customWordText2 = findViewById(R.id.customWordText2);
        customWordText3 = findViewById(R.id.customWordText3);
        customWordText4 = findViewById(R.id.customWordText4);
        customWordText5 = findViewById(R.id.customWordText5);
        customWordText6 = findViewById(R.id.customWordText6);
        customWordText7 = findViewById(R.id.customWordText7);
        customWordText8 = findViewById(R.id.customWordText8);
        customWordText9 = findViewById(R.id.customWordText9);
        customWordText10 = findViewById(R.id.customWordText10);

        deleteCustomWord1 = findViewById(R.id.deleteCustomWord1);
        deleteCustomWord2 = findViewById(R.id.deleteCustomWord2);
        deleteCustomWord3 = findViewById(R.id.deleteCustomWord3);
        deleteCustomWord4 = findViewById(R.id.deleteCustomWord4);
        deleteCustomWord5 = findViewById(R.id.deleteCustomWord5);
        deleteCustomWord6 = findViewById(R.id.deleteCustomWord6);
        deleteCustomWord7 = findViewById(R.id.deleteCustomWord7);
        deleteCustomWord8 = findViewById(R.id.deleteCustomWord8);
        deleteCustomWord9 = findViewById(R.id.deleteCustomWord9);
        deleteCustomWord10 = findViewById(R.id.deleteCustomWord10);

        addBombsButton = findViewById(R.id.addBombsButton);
        addNeutralsButton = findViewById(R.id.addNeutralsButton);
        subtractBombsButton = findViewById(R.id.subtractBombsButton);
        subtractNeutralsButton = findViewById(R.id.subtractNeutralsButton);
        teamASqrInc = findViewById(R.id.teamASqrInc);
        teamBSqrInc = findViewById(R.id.teamBSqrInc);
        teamASqrDec = findViewById(R.id.teamASqrDec);
        teamBSqrDec = findViewById(R.id.teamBSqrDec);

        bombSquareCount = findViewById(R.id.bombSquareCount);
        neutralSquareCount = findViewById(R.id.neutralSquareCount);
        teamASquaresCount = findViewById(R.id.teamASquaresCount);
        teamBSquaresCount = findViewById(R.id.teamBSquaresCount);

        startingTeamButton = findViewById(R.id.startingTeamButton);

        squaresInUse = findViewById(R.id.squaresInUse);

        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle);

        if (getIntent().getStringExtra("from").equals("local_setup")) {
            gameOptionsSubtitle.setText("Local");
        } else if (getIntent().getStringExtra("from").equals("online_setup")) {
            gameOptionsSubtitle.setText("Online");
        }

        saveButton = findViewById(R.id.saveButton);

        if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
            saveButton.setVisibility(View.VISIBLE);

            bombSquares = getIntent().getIntExtra("bombSquares", 1);
            bombSquareCount.setText(String.valueOf(bombSquares));
            neutralSquares = getIntent().getIntExtra("neutralSquares", 7);
            neutralSquareCount.setText(String.valueOf(neutralSquares));
            teamASquares = getIntent().getIntExtra("teamASquares", 9);
            teamASquaresCount.setText(String.valueOf(teamASquares));
            teamBSquares = getIntent().getIntExtra("teamBSquares", 8);
            teamBSquaresCount.setText(String.valueOf(teamBSquares));

            startingTeamButton.setChecked(getIntent().getIntExtra("startingTeam", 1) == 1);

            EditText[] customWordTexts = {customWordText1, customWordText2, customWordText3,
                   customWordText4, customWordText5, customWordText6, customWordText7,
                   customWordText8, customWordText9, customWordText10};

            int counter = 0;

            while(counter < getIntent().getStringArrayListExtra("customWords").size()) {
                customWordTexts[counter].setText(getIntent().getStringArrayListExtra("customWords").get(counter));

                counter++;
            }
        }

        teamASqrInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse != MAX_SQUARES) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse++;
                    teamASquares++;

                    teamASquaresCount.setText(String.valueOf(teamASquares));

                    updateTotalSquares();
                }
            }
        });

        teamASqrDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse >= 1 && teamASquares >= 2) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse--;
                    teamASquares--;

                    teamASquaresCount.setText(String.valueOf(teamASquares));

                    updateTotalSquares();
                }
            }
        });

        teamBSqrInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse != MAX_SQUARES) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse++;
                    teamBSquares++;

                    teamBSquaresCount.setText(String.valueOf(teamBSquares));

                    updateTotalSquares();
                }
            }
        });

        teamBSqrDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse >= 1 && teamBSquares >= 2) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse--;
                    teamBSquares--;

                    teamBSquaresCount.setText(String.valueOf(teamBSquares));

                    updateTotalSquares();
                }
            }
        });

        addBombsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse != MAX_SQUARES) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse++;
                    bombSquares++;

                    bombSquareCount.setText(String.valueOf(bombSquares));

                    updateTotalSquares();
                }
            }
        });

        subtractBombsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse >= 1 && bombSquares >= 1) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse--;
                    bombSquares--;

                    bombSquareCount.setText(String.valueOf(bombSquares));

                    updateTotalSquares();
                }
            }
        });

        addNeutralsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse != MAX_SQUARES) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse++;
                    neutralSquares++;

                    neutralSquareCount.setText(String.valueOf(neutralSquares));

                    updateTotalSquares();
                }
            }
        });

        subtractNeutralsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSquaresInUse >= 1 && neutralSquares >= 1) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                    totalSquaresInUse--;
                    neutralSquares--;

                    neutralSquareCount.setText(String.valueOf(neutralSquares));

                    updateTotalSquares();
                }
            }
        });

        startingTeamButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveButton.setVisibility(View.VISIBLE);
                hasCustomSettings = true;
            }
        });

        customWordText1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText2.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText3.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText4.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText5.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText5.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText6.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText6.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText7.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText7.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText8.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText8.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText9.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText9.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        customWordText10.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText10.getText().toString().equals("")) {
                    saveButton.setVisibility(View.VISIBLE);
                    hasCustomSettings = true;
                }

                return false;
            }
        });

        deleteCustomWord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText1.setText("");
            }
        });

        deleteCustomWord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText2.setText("");
            }
        });

        deleteCustomWord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText3.setText("");
            }
        });

        deleteCustomWord4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText4.setText("");
            }
        });

        deleteCustomWord5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText5.setText("");
            }
        });

        deleteCustomWord6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText6.setText("");
            }
        });

        deleteCustomWord7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText7.setText("");
            }
        });

        deleteCustomWord8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText8.setText("");
            }
        });

        deleteCustomWord9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText9.setText("");
            }
        });

        deleteCustomWord10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customWordText10.setText("");
            }
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        if (windowOpen) {
            backNo(getCurrentFocus());
        } else {
            backButton(getWindow().getDecorView());
        }
    }

    public void backButton(View view) {
        if (hasCustomSettings) {
            windowOpen = true;
            messageBox.setVisibility(View.VISIBLE);
            swapEnableStates();
            messageBoxText.setText("Are you sure you want to exit without saving your settings?");
            yesButton.setVisibility(View.VISIBLE);
            noButton.setVisibility(View.VISIBLE);
            okButton.setVisibility(View.INVISIBLE);
        } else {
            backYes(view);
        }
    }

    public void backYes(View view) {
        Intent i;

        if (getIntent().getStringExtra("from").equals("local_setup")) {
            i = new Intent(view.getContext(), local_setup.class);
            startActivity(i);
        } else if (getIntent().getStringExtra("from").equals("online_setup")) {
            i = new Intent(view.getContext(), online_setup.class);
            startActivity(i);
        }
    }

    public void backNo(View view) {
        swapEnableStates();
        windowOpen = false;
        messageBox.setVisibility(View.INVISIBLE);
    }

    public void saveButton(View view) {
        Intent i;
        String error = "";
        boolean validSave = true;

        //Reads words from the text file to store in the defaultWords array
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

        ArrayList<String> customWordsTemp = new ArrayList<>();

        EditText[] customWordTextsTemp = {customWordText1, customWordText2, customWordText3,
                customWordText4, customWordText5, customWordText6, customWordText7,
                customWordText8, customWordText9, customWordText10};


        for (int j = 0; j < customWordTextsTemp.length; j++) {
            if (!customWordTextsTemp[j].getText().toString().equals("")) {
                customWordsTemp.add(customWordTextsTemp[j].getText().toString());
            }
        }

        for (String s : customWordsTemp) {
            if (defaultWords.contains(s.toUpperCase())) {
                validSave = false;
                error = s.toUpperCase() + " already exists as a default word.";
                break;
            }
        }

        if (totalSquaresInUse != MAX_SQUARES) {
            validSave = false;
            error = "You do not have the required number of squares assigned.";
        }

        if (validSave) {
            if (getIntent().getStringExtra("from").equals("local_setup")) {
                i = new Intent(view.getContext(), local_setup.class);

                if (startingTeamButton.isChecked()) {
                    startingTeam = 1;
                } else {
                    startingTeam = 2;
                }

                i.putExtra("hasCustomSettings", true);
                i.putExtra("bombSquares", bombSquares);
                i.putExtra("neutralSquares", neutralSquares);
                i.putExtra("teamASquares", teamASquares);
                i.putExtra("teamBSquares", teamBSquares);
                i.putExtra("startingTeam", startingTeam);

                EditText[] customWordTexts = {customWordText1, customWordText2, customWordText3,
                        customWordText4, customWordText5, customWordText6, customWordText7,
                        customWordText8, customWordText9, customWordText10};

                for (EditText customWordText : customWordTexts) {
                    if (!customWordText.getText().toString().equals("")) {
                        customWords.add(customWordText.getText().toString());
                    }
                }

                i.putExtra("customWords", customWords);

                startActivity(i);
            } else if (getIntent().getStringExtra("from").equals("online_setup")) {
                i = new Intent(view.getContext(), online_setup.class);

                if (startingTeamButton.isChecked()) {
                    startingTeam = 1;
                } else {
                    startingTeam = 2;
                }

                i.putExtra("hasCustomSettings", true);
                i.putExtra("bombSquares", bombSquares);
                i.putExtra("neutralSquares", neutralSquares);
                i.putExtra("teamASquares", teamASquares);
                i.putExtra("teamBSquares", teamBSquares);
                i.putExtra("startingTeam", startingTeam);

                EditText[] customWordTexts = {customWordText1, customWordText2, customWordText3,
                        customWordText4, customWordText5, customWordText6, customWordText7,
                        customWordText8, customWordText9, customWordText10};

                for (EditText customWordText : customWordTexts) {
                    if (!customWordText.getText().toString().equals("")) {
                        customWords.add(customWordText.getText().toString());
                    }
                }

                i.putExtra("customWords", customWords);

                startActivity(i);
            }
        } else {
            windowOpen = true;
            messageBox.setVisibility(View.VISIBLE);
            yesButton.setVisibility(View.INVISIBLE);
            noButton.setVisibility(View.INVISIBLE);
            okButton.setVisibility(View.VISIBLE);
            swapEnableStates();
            messageBoxText.setText(error);
        }
    }

    public void saveOk(View view) {
        messageBox.setVisibility(View.INVISIBLE);
        swapEnableStates();
    }

    public void swapEnableStates() {
        backButton.setEnabled(disabled);
        saveButton.setEnabled(disabled);
        addBombsButton.setEnabled(disabled);
        subtractBombsButton.setEnabled(disabled);
        addNeutralsButton.setEnabled(disabled);
        subtractNeutralsButton.setEnabled(disabled);
        teamASqrInc.setEnabled(disabled);
        teamASqrDec.setEnabled(disabled);
        teamBSqrInc.setEnabled(disabled);
        teamBSqrDec.setEnabled(disabled);
        startingTeamButton.setEnabled(disabled);
        customWordText1.setEnabled(disabled);
        customWordText2.setEnabled(disabled);
        customWordText3.setEnabled(disabled);
        customWordText4.setEnabled(disabled);
        customWordText5.setEnabled(disabled);
        customWordText6.setEnabled(disabled);
        customWordText7.setEnabled(disabled);
        customWordText8.setEnabled(disabled);
        customWordText9.setEnabled(disabled);
        customWordText10.setEnabled(disabled);
        deleteCustomWord1.setEnabled(disabled);
        deleteCustomWord2.setEnabled(disabled);
        deleteCustomWord3.setEnabled(disabled);
        deleteCustomWord4.setEnabled(disabled);
        deleteCustomWord5.setEnabled(disabled);
        deleteCustomWord6.setEnabled(disabled);
        deleteCustomWord7.setEnabled(disabled);
        deleteCustomWord8.setEnabled(disabled);
        deleteCustomWord9.setEnabled(disabled);
        deleteCustomWord10.setEnabled(disabled);

        disabled = !disabled;
    }

    public void updateTotalSquares() {
        String text = totalSquaresInUse + "/" + MAX_SQUARES;

        squaresInUse.setText(text);
    }

    public void updateColours() {
        constraintLayout = findViewById(R.id.constraintLayout);
        messageBox = findViewById(R.id.messageBox);
        gameOptionsTitle = findViewById(R.id.gameOptionsTitle);
        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle);
        backButton = findViewById(R.id.backButton);

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
        saveButton.setBackgroundColor(defaultColour);
        yesButton.setBackgroundColor(defaultColour);
        noButton.setBackgroundColor(defaultColour);
        okButton.setBackgroundColor(defaultColour);
        addBombsButton.setColorFilter(defaultColour);
        subtractBombsButton.setColorFilter(defaultColour);
        addNeutralsButton.setColorFilter(defaultColour);
        subtractNeutralsButton.setColorFilter(defaultColour);
        teamASqrInc.setColorFilter(defaultColour);
        teamASqrDec.setColorFilter(defaultColour);
        teamBSqrInc.setColorFilter(defaultColour);
        teamBSqrDec.setColorFilter(defaultColour);
        deleteCustomWord1.setBackgroundColor(defaultColour);
        deleteCustomWord2.setBackgroundColor(defaultColour);
        deleteCustomWord3.setBackgroundColor(defaultColour);
        deleteCustomWord4.setBackgroundColor(defaultColour);
        deleteCustomWord5.setBackgroundColor(defaultColour);
        deleteCustomWord6.setBackgroundColor(defaultColour);
        deleteCustomWord7.setBackgroundColor(defaultColour);
        deleteCustomWord8.setBackgroundColor(defaultColour);
        deleteCustomWord9.setBackgroundColor(defaultColour);
        deleteCustomWord10.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        gameOptionsTitle.setTextColor(defaultColour);
        gameOptionsSubtitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        saveButton.setTextColor(defaultColour);
        yesButton.setTextColor(defaultColour);
        noButton.setTextColor(defaultColour);
        okButton.setTextColor(defaultColour);
        messageBoxText.setTextColor(defaultColour);
        deleteCustomWord1.setColorFilter(defaultColour);
        deleteCustomWord2.setColorFilter(defaultColour);
        deleteCustomWord3.setColorFilter(defaultColour);
        deleteCustomWord4.setColorFilter(defaultColour);
        deleteCustomWord5.setColorFilter(defaultColour);
        deleteCustomWord6.setColorFilter(defaultColour);
        deleteCustomWord7.setColorFilter(defaultColour);
        deleteCustomWord8.setColorFilter(defaultColour);
        deleteCustomWord9.setColorFilter(defaultColour);
        deleteCustomWord10.setColorFilter(defaultColour);
    }
}
