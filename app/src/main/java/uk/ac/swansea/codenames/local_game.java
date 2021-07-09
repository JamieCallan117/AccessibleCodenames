package uk.ac.swansea.codenames;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static uk.ac.swansea.codenames.localPhase.START;
import static uk.ac.swansea.codenames.localPhase.TEAM_A;
import static uk.ac.swansea.codenames.localPhase.TEAM_A_INTERMISSION;
import static uk.ac.swansea.codenames.localPhase.TEAM_A_SPY;
import static uk.ac.swansea.codenames.localPhase.TEAM_A_WIN;
import static uk.ac.swansea.codenames.localPhase.TEAM_B;
import static uk.ac.swansea.codenames.localPhase.TEAM_B_INTERMISSION;
import static uk.ac.swansea.codenames.localPhase.TEAM_B_SPY;
import static uk.ac.swansea.codenames.localPhase.TEAM_B_WIN;

public class local_game extends AppCompatActivity {
    private localPhase gamePhase;
    private int bombSquaresCount = 1;
    private int neutralSquaresCount = 7;
    private int teamASquaresCount = 9;
    private int teamBSquaresCount = 8;
    private int startingTeam = 1;
    private int defaultColour;
    private final int MAX_WORDS = 25;
    private ArrayList<String> customWords;
    private String[] bombWords;
    private String[] neutralWords;
    private String[] teamAWords;
    private String[] teamBWords;
    private String[] words = new String[25];
    private WordButton[] wordButtons;
    private Button exitButton;
    private Button messageButton;
    private Button turnAction;
    private TextView teamACount;
    private TextView teamBCount;
    private TextView teamCounterLine;
    private TextView messageText;
    private TextView hintText;
    private EditText editHint;
    private androidx.gridlayout.widget.GridLayout messageBox;
    private ConstraintLayout constraintLayout;
    private WordButton squareOne;
    private WordButton squareTwo;
    private WordButton squareThree;
    private WordButton squareFour;
    private WordButton squareFive;
    private WordButton squareSix;
    private WordButton squareSeven;
    private WordButton squareEight;
    private WordButton squareNine;
    private WordButton squareTen;
    private WordButton squareEleven;
    private WordButton squareTwelve;
    private WordButton squareThirteen;
    private WordButton squareFourteen;
    private WordButton squareFifteen;
    private WordButton squareSixteen;
    private WordButton squareSeventeen;
    private WordButton squareEighteen;
    private WordButton squareNineteen;
    private WordButton squareTwenty;
    private WordButton squareTwentyOne;
    private WordButton squareTwentyTwo;
    private WordButton squareTwentyThree;
    private WordButton squareTwentyFour;
    private WordButton squareTwentyFive;
    private Spinner hintNumber;

    //TODO: Add confirmation to quit game
    //TODO: Add confirmation of spymaster before showing the colours at start of game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.local_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        exitButton = findViewById(R.id.exitButton);
        messageBox = findViewById(R.id.messageBox);
        messageText = findViewById(R.id.messageText);
        messageButton = findViewById(R.id.messageButton);

        teamACount = findViewById(R.id.teamACount);
        teamBCount = findViewById(R.id.teamBCount);
        teamCounterLine = findViewById(R.id.teamCounterLine);

        hintText = findViewById(R.id.hintText);
        editHint = findViewById(R.id.editHint);
        hintNumber = findViewById(R.id.hintNumber);

        turnAction = findViewById(R.id.turnAction);

        gamePhase = START;

        if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
            bombSquaresCount = getIntent().getIntExtra("bombSquares", 1);
            neutralSquaresCount = getIntent().getIntExtra("neutralSquares", 7);
            teamASquaresCount = getIntent().getIntExtra("teamASquares", 9);
            teamBSquaresCount = getIntent().getIntExtra("teamBSquares", 8);
            startingTeam = getIntent().getIntExtra("startingTeam", 1);
            customWords = getIntent().getStringArrayListExtra("customWords");
        }

        bombWords = new String[bombSquaresCount];
        neutralWords = new String[neutralSquaresCount];
        teamAWords = new String[teamASquaresCount];
        teamBWords = new String[teamBSquaresCount];

        if (startingTeam == 1) {
            gamePhase = TEAM_A_INTERMISSION;
            teamACount.setPaintFlags(teamACount.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        } else {
            gamePhase = TEAM_B_INTERMISSION;
            teamBCount.setPaintFlags(teamBCount.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        }

        teamACount.setText(String.valueOf(teamASquaresCount));
        teamBCount.setText(String.valueOf(teamBSquaresCount));

        squareOne = findViewById(R.id.squareOne);
        squareTwo = findViewById(R.id.squareTwo);
        squareThree = findViewById(R.id.squareThree);
        squareFour = findViewById(R.id.squareFour);
        squareFive = findViewById(R.id.squareFive);
        squareSix = findViewById(R.id.squareSix);
        squareSeven = findViewById(R.id.squareSeven);
        squareEight = findViewById(R.id.squareEight);
        squareNine = findViewById(R.id.squareNine);
        squareTen = findViewById(R.id.squareTen);
        squareEleven = findViewById(R.id.squareEleven);
        squareTwelve = findViewById(R.id.squareTwelve);
        squareThirteen = findViewById(R.id.squareThirteen);
        squareFourteen = findViewById(R.id.squareFourteen);
        squareFifteen = findViewById(R.id.squareFifteen);
        squareSixteen = findViewById(R.id.squareSixteen);
        squareSeventeen = findViewById(R.id.squareSeventeen);
        squareEighteen = findViewById(R.id.squareEighteen);
        squareNineteen = findViewById(R.id.squareNineteen);
        squareTwenty = findViewById(R.id.squareTwenty);
        squareTwentyOne = findViewById(R.id.squareTwentyOne);
        squareTwentyTwo = findViewById(R.id.squareTwentyTwo);
        squareTwentyThree = findViewById(R.id.squareTwentyThree);
        squareTwentyFour = findViewById(R.id.squareTwentyFour);
        squareTwentyFive = findViewById(R.id.squareTwentyFive);

        wordButtons = new WordButton[]{squareOne, squareTwo, squareThree, squareFour, squareFive,
                squareSix, squareSeven, squareEight, squareNine, squareTen, squareEleven,
                squareTwelve, squareThirteen, squareFourteen, squareFifteen, squareSixteen,
                squareSeventeen, squareEighteen, squareNineteen, squareTwenty, squareTwentyOne,
                squareTwentyTwo, squareTwentyThree, squareTwentyFour, squareTwentyFive};

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

        int defaultWordsNeeded = MAX_WORDS;

        if (customWords != null) {
            for (int i = 0; i < customWords.size(); i++) {
                words[i] = customWords.get(i);
                defaultWordsNeeded--;
            }
        }

        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < defaultWords.size(); i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for (int i = 0; i < defaultWordsNeeded; i++) {
            words[i + (MAX_WORDS - defaultWordsNeeded)] = defaultWords.get(list.get(i));
        }

        Collections.shuffle(Arrays.asList(words));

        for (int i = 0; i < MAX_WORDS; i++) {
            wordButtons[i].setText(words[i]);
        }

        list.clear();

        for (int i = 0; i < MAX_WORDS; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        int counter = 0;

        for (int i = 0; i < bombSquaresCount; i++) {
            bombWords[i] = words[list.get(counter)];
            counter++;
        }

        for (int i = 0; i < neutralSquaresCount; i++) {
            neutralWords[i] = words[list.get(counter)];
            counter++;
        }

        for (int i = 0; i < teamASquaresCount; i++) {
            teamAWords[i] = words[list.get(counter)];
            counter++;
        }

        for (int i = 0; i < teamBSquaresCount; i++) {
            teamBWords[i] = words[list.get(counter)];
            counter++;
        }

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Current phase: " + gamePhase.toString());
            }
        });

        squareOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareOne);
            }
        });

        squareTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwo);
            }
        });

        squareThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareThree);
            }
        });

        squareFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareFour);
            }
        });

        squareFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareFive);
            }
        });

        squareSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareSix);
            }
        });

        squareSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareSeven);
            }
        });

        squareEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareEight);
            }
        });

        squareNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareNine);
            }
        });

        squareTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTen);
            }
        });

        squareEleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareEleven);
            }
        });

        squareTwelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwelve);
            }
        });

        squareThirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareThirteen);
            }
        });

        squareFourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareFourteen);
            }
        });

        squareFifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareFifteen);
            }
        });

        squareSixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareSixteen);
            }
        });

        squareSeventeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareSeventeen);
            }
        });

        squareEighteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareEighteen);
            }
        });

        squareNineteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareNineteen);
            }
        });

        squareTwenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwenty);
            }
        });

        squareTwentyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwentyOne);
            }
        });

        squareTwentyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwentyTwo);
            }
        });

        squareTwentyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwentyThree);
            }
        });

        squareTwentyFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwentyFour);
            }
        });

        squareTwentyFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordButtonPress(squareTwentyFive);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (gamePhase) {
                    case TEAM_A_INTERMISSION:
                        gamePhase = TEAM_A_SPY;
                        break;
                    case TEAM_B_INTERMISSION:
                        gamePhase = TEAM_B_SPY;
                        break;
                }

                messageBox.setVisibility(View.INVISIBLE);

                updateSpinner();

                updateColours();
            }
        });

        turnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        exitButton(getWindow().getDecorView());
    }

    public void exitButton(View view) {
        Intent i = new Intent(view.getContext(), main_menu.class);
        startActivity(i);
    }

    public void wordButtonPress(WordButton buttonPressed) {
        if (!buttonPressed.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY &&
                gamePhase != TEAM_A_INTERMISSION && gamePhase != TEAM_B_INTERMISSION) {
            buttonPressed.setHasBeenClicked(true);

            String buttonType = "";

            for (String s : bombWords) {
                if (buttonPressed.getText().toString().equals(s)) {
                    buttonType = "bomb";
                }
            }

            if (buttonType.equals("")) {
                for (String s : neutralWords) {
                    if (buttonPressed.getText().toString().equals(s)) {
                        buttonType = "neutral";
                    }
                }
            }

            if (buttonType.equals("")) {
                for (String s : teamAWords) {
                    if (buttonPressed.getText().toString().equals(s)) {
                        buttonType = "teamA";
                    }
                }
            }

            if (buttonType.equals("")) {
                for (String s : teamBWords) {
                    if (buttonPressed.getText().toString().equals(s)) {
                        buttonType = "teamB";
                    }
                }
            }

            System.out.println(buttonType);

            switch (buttonType) {
                case "bomb":
                    if (gamePhase == TEAM_A) {
                        gamePhase = TEAM_B_WIN;
                    } else {
                        gamePhase = TEAM_A_WIN;
                    }

                    break;
                case "neutral":
                    if (gamePhase == TEAM_A) {
                        gamePhase = TEAM_B;
                    } else {
                        gamePhase = TEAM_A;
                    }

                    break;
                case "teamA":
                    if (gamePhase == TEAM_B) {
                        gamePhase = TEAM_A;
                    }

                    break;
                case "teamB":
                    if (gamePhase == TEAM_A) {
                        gamePhase = TEAM_B;
                    }

                    break;
            }

            updateColours();
        }
    }

    public void updateSpinner() {
        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("∞");

        if (gamePhase == TEAM_A_SPY) {
            for (int i = 0; i < teamASquaresCount + 1; i++) {
                spinnerArray.add(String.valueOf(i));
                System.out.println(i);
            }
        } else {
            for (int i = 0; i < teamBSquaresCount + 1; i++) {
                spinnerArray.add(String.valueOf(i));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hintNumber.setAdapter(adapter);
    }

    public void updateColours() {
        if (userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_A).equals("")) {
            defaultColour = userSettings.getInstance().TEAM_A_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_A));
        }

        teamACount.setTextColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_B).equals("")) {
            defaultColour = userSettings.getInstance().TEAM_B_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_B));
        }

        teamBCount.setTextColor(defaultColour);

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

        exitButton.setBackgroundColor(defaultColour);
        messageButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        exitButton.setTextColor(defaultColour);
        messageButton.setTextColor(defaultColour);
        teamCounterLine.setTextColor(defaultColour);
        messageText.setTextColor(defaultColour);

        switch(gamePhase) {
            case START:
            case TEAM_A_INTERMISSION:
            case TEAM_B_INTERMISSION:
                if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
                    defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
                }

                for (WordButton wb : wordButtons) {
                    wb.setBackgroundColor(defaultColour);
                }

                break;
            case TEAM_A:
            case TEAM_B:
                for (WordButton wb : wordButtons) {
                    if (wb.hasBeenClicked()) {
                        String buttonType = "";

                        for (String s : neutralWords) {
                            if (wb.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }

                        if (buttonType.equals("")) {
                            for (String s : teamAWords) {
                                if (wb.getText().toString().equals(s)) {
                                    buttonType = "teamA";
                                }
                            }
                        }

                        if (buttonType.equals("")) {
                            for (String s : teamBWords) {
                                if (wb.getText().toString().equals(s)) {
                                    buttonType = "teamB";
                                }
                            }
                        }

                        switch (buttonType) {
                            case "teamA":
                                if (userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_A).equals("")) {
                                    defaultColour = userSettings.getInstance().TEAM_A_DEFAULT;
                                } else {
                                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_A));
                                }

                                wb.setBackgroundColor(defaultColour);

                                break;
                            case "teamB":
                                if (userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_B).equals("")) {
                                    defaultColour = userSettings.getInstance().TEAM_B_DEFAULT;
                                } else {
                                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_B));
                                }

                                wb.setBackgroundColor(defaultColour);

                                break;
                            case "neutral":
                                if (userSettings.getInstance().getPreference(userSettings.getInstance().NEUTRAL_SQUARE).equals("")) {
                                    defaultColour = userSettings.getInstance().NEUTRAL_SQUARE_DEFAULT;
                                } else {
                                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().NEUTRAL_SQUARE));
                                }

                                wb.setBackgroundColor(defaultColour);

                                break;
                        }
                    } else {
                        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
                            defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
                        } else {
                            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
                        }

                        wb.setBackgroundColor(defaultColour);
                    }
                }
                break;
            case TEAM_A_SPY:
            case TEAM_B_SPY:
            case TEAM_A_WIN:
            case TEAM_B_WIN:
                if (userSettings.getInstance().getPreference(userSettings.getInstance().BOMB_SQUARE).equals("")) {
                    defaultColour = userSettings.getInstance().BOMB_SQUARE_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().BOMB_SQUARE));
                }

                for (WordButton wb : wordButtons) {
                    for (String s : bombWords) {
                        if (wb.getText().toString().equals(s)) {
                            wb.setBackgroundColor(defaultColour);
                        }
                    }
                }

                if (userSettings.getInstance().getPreference(userSettings.getInstance().NEUTRAL_SQUARE).equals("")) {
                    defaultColour = userSettings.getInstance().NEUTRAL_SQUARE_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().NEUTRAL_SQUARE));
                }

                for (WordButton wb : wordButtons) {
                    for (String s : neutralWords) {
                        if (wb.getText().toString().equals(s)) {
                            wb.setBackgroundColor(defaultColour);
                        }
                    }
                }

                if (userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_A).equals("")) {
                    defaultColour = userSettings.getInstance().TEAM_A_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_A));
                }

                for (WordButton wb : wordButtons) {
                    for (String s : teamAWords) {
                        if (wb.getText().toString().equals(s)) {
                            wb.setBackgroundColor(defaultColour);
                        }
                    }
                }

                if (userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_B).equals("")) {
                    defaultColour = userSettings.getInstance().TEAM_B_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().TEAM_B));
                }

                for (WordButton wb : wordButtons) {
                    for (String s : teamBWords) {
                        if (wb.getText().toString().equals(s)) {
                            wb.setBackgroundColor(defaultColour);
                        }
                    }
                }

                if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
                    defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
                }

                for (WordButton wb : wordButtons) {
                    wb.setTextColor(defaultColour);
                }

                for (WordButton wb : wordButtons) {
                    if (wb.hasBeenClicked()) {
                        wb.getBackground().setAlpha(128);
                    }
                }

                break;
        }
    }
}
