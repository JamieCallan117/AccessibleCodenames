package uk.ac.swansea.codenames;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static uk.ac.swansea.codenames.localPhase.START;
import static uk.ac.swansea.codenames.localPhase.TEAM_A;
import static uk.ac.swansea.codenames.localPhase.TEAM_A_SPY;
import static uk.ac.swansea.codenames.localPhase.TEAM_A_WIN;
import static uk.ac.swansea.codenames.localPhase.TEAM_B_WIN;

public class local_game extends AppCompatActivity {
    private localPhase gamePhase;
    private int bombSquaresCount = 1;
    private int neutralSquaresCount = 7;
    private int teamASquaresCount = 9;
    private int teamBSquaresCount = 8;
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
    private ConstraintLayout constraintLayout;

    //TODO: Add confirmation to quit game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.local_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        exitButton = findViewById(R.id.exitButton);

        gamePhase = START;
        //gamePhase = TEAM_A_SPY;

        if (getIntent().getBooleanExtra("hasCustomSettings", false)) {
            bombSquaresCount = getIntent().getIntExtra("bombSquares", 1);
            neutralSquaresCount = getIntent().getIntExtra("neutralSquares", 7);
            teamASquaresCount = getIntent().getIntExtra("teamASquares", 9);
            teamBSquaresCount = getIntent().getIntExtra("teamBSquares", 8);
            customWords = getIntent().getStringArrayListExtra("customWords");
        }

        bombWords = new String[bombSquaresCount];
        neutralWords = new String[neutralSquaresCount];
        teamAWords = new String[teamASquaresCount];
        teamBWords = new String[teamBSquaresCount];

        WordButton squareOne = findViewById(R.id.squareOne);
        WordButton squareTwo = findViewById(R.id.squareTwo);
        WordButton squareThree = findViewById(R.id.squareThree);
        WordButton squareFour = findViewById(R.id.squareFour);
        WordButton squareFive = findViewById(R.id.squareFive);
        WordButton squareSix = findViewById(R.id.squareSix);
        WordButton squareSeven = findViewById(R.id.squareSeven);
        WordButton squareEight = findViewById(R.id.squareEight);
        WordButton squareNine = findViewById(R.id.squareNine);
        WordButton squareTen = findViewById(R.id.squareTen);
        WordButton squareEleven = findViewById(R.id.squareEleven);
        WordButton squareTwelve = findViewById(R.id.squareTwelve);
        WordButton squareThirteen = findViewById(R.id.squareThirteen);
        WordButton squareFourteen = findViewById(R.id.squareFourteen);
        WordButton squareFifteen = findViewById(R.id.squareFifteen);
        WordButton squareSixteen = findViewById(R.id.squareSixteen);
        WordButton squareSeventeen = findViewById(R.id.squareSeventeen);
        WordButton squareEighteen = findViewById(R.id.squareEighteen);
        WordButton squareNineteen = findViewById(R.id.squareNineteen);
        WordButton squareTwenty = findViewById(R.id.squareTwenty);
        WordButton squareTwentyOne = findViewById(R.id.squareTwentyOne);
        WordButton squareTwentyTwo = findViewById(R.id.squareTwentyTwo);
        WordButton squareTwentyThree = findViewById(R.id.squareTwentyThree);
        WordButton squareTwentyFour = findViewById(R.id.squareTwentyFour);
        WordButton squareTwentyFive = findViewById(R.id.squareTwentyFive);

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

        squareOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareOne.hasBeenClicked()) {
                    squareOne.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareOne.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    for (String s : neutralWords) {
                        if (squareOne.getText().toString().equals(s)) {
                            buttonType = "neutral";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareOne.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareOne.getText().toString().equals(s)) {
                                buttonType = "teamB";
                            }
                        }
                    }

                    switch (buttonType) {
                        case "bomb":
                            if (gamePhase == TEAM_A) {
                                gamePhase = TEAM_B_WIN;
                            } else {
                                gamePhase = TEAM_A_WIN;
                            }

                            break;
                    }
                }
            }
        });

        squareTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwo.setHasBeenClicked(true);
            }
        });

        squareThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareThree.setHasBeenClicked(true);
            }
        });

        squareFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareFour.setHasBeenClicked(true);
            }
        });

        squareFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareFive.setHasBeenClicked(true);
            }
        });

        squareSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareSix.setHasBeenClicked(true);
            }
        });

        squareSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareSeven.setHasBeenClicked(true);
            }
        });

        squareEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareEight.setHasBeenClicked(true);
            }
        });

        squareNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareNine.setHasBeenClicked(true);
            }
        });

        squareTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTen.setHasBeenClicked(true);
            }
        });

        squareEleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareEleven.setHasBeenClicked(true);
            }
        });

        squareTwelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwelve.setHasBeenClicked(true);
            }
        });

        squareThirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareThirteen.setHasBeenClicked(true);
            }
        });

        squareFourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareFourteen.setHasBeenClicked(true);
            }
        });

        squareFifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareFifteen.setHasBeenClicked(true);
            }
        });

        squareSixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareSixteen.setHasBeenClicked(true);
            }
        });

        squareSeventeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareSeventeen.setHasBeenClicked(true);
            }
        });

        squareEighteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareEighteen.setHasBeenClicked(true);
            }
        });

        squareNineteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareNineteen.setHasBeenClicked(true);
            }
        });

        squareTwenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwenty.setHasBeenClicked(true);
            }
        });

        squareTwentyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwentyOne.setHasBeenClicked(true);
            }
        });

        squareTwentyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwentyTwo.setHasBeenClicked(true);
            }
        });

        squareTwentyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwentyThree.setHasBeenClicked(true);
            }
        });

        squareTwentyFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwentyFour.setHasBeenClicked(true);
            }
        });

        squareTwentyFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                squareTwentyFive.setHasBeenClicked(true);
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

        exitButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        exitButton.setTextColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        exitButton.setTextColor(defaultColour);

        switch(gamePhase) {
            case START:
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
                System.out.println("HIIII");
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
                        System.out.println("HELLOOOOO");
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
