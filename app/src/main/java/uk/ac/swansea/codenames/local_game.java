package uk.ac.swansea.codenames;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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
import static uk.ac.swansea.codenames.localPhase.TEAM_B;
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
    private TextView teamACount;
    private TextView teamBCount;
    private TextView teamCounterLine;
    private ConstraintLayout constraintLayout;

    //TODO: Add confirmation to quit game
    //TODO: Add confirmation of spymaster before showing the colours at start of game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.local_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        exitButton = findViewById(R.id.exitButton);

        teamACount = findViewById(R.id.teamACount);
        teamBCount = findViewById(R.id.teamBCount);
        teamCounterLine = findViewById(R.id.teamCounterLine);

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
            gamePhase = TEAM_A_SPY;
            teamACount.setPaintFlags(teamACount.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        } else {
            gamePhase = TEAM_B_SPY;
            teamBCount.setPaintFlags(teamBCount.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        }

        teamACount.setText(String.valueOf(teamASquaresCount));
        teamBCount.setText(String.valueOf(teamBSquaresCount));

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

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Current phase: " + gamePhase.toString());
            }
        });

        squareOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareOne.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
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
        });

        squareTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwo.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwo.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwo.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwo.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwo.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwo.getText().toString().equals(s)) {
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
        });

        squareThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareThree.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareThree.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareThree.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareThree.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareThree.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareThree.getText().toString().equals(s)) {
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
        });

        squareFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareFour.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareFour.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareFour.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareFour.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareFour.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareFour.getText().toString().equals(s)) {
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
        });

        squareFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareFive.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareFive.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareFive.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareFive.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareFive.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareFive.getText().toString().equals(s)) {
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
        });

        squareSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareSix.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareSix.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareSix.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareSix.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareSix.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareSix.getText().toString().equals(s)) {
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
        });

        squareSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareSeven.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareSeven.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareSeven.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareSeven.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareSeven.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareSeven.getText().toString().equals(s)) {
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
        });

        squareEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareEight.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareEight.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareEight.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareEight.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareEight.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareEight.getText().toString().equals(s)) {
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
        });

        squareNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareNine.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareNine.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareNine.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareNine.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareNine.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareNine.getText().toString().equals(s)) {
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
        });

        squareTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTen.getText().toString().equals(s)) {
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
        });

        squareEleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareEleven.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareEleven.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareEleven.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareEleven.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareEleven.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareEleven.getText().toString().equals(s)) {
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
        });

        squareTwelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwelve.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwelve.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwelve.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwelve.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwelve.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwelve.getText().toString().equals(s)) {
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
        });

        squareThirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareThirteen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareThirteen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareThirteen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareThirteen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareThirteen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareThirteen.getText().toString().equals(s)) {
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
        });

        squareFourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareFourteen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareFourteen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareFourteen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareFourteen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareFourteen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareFourteen.getText().toString().equals(s)) {
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
        });

        squareFifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareFifteen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareFifteen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareFifteen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareFifteen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareFifteen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareFifteen.getText().toString().equals(s)) {
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
        });

        squareSixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareSixteen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareSixteen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareSixteen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareSixteen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareSixteen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareSixteen.getText().toString().equals(s)) {
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
        });

        squareSeventeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareSeventeen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareSeventeen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareSeventeen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareSeventeen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareSeventeen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareSeventeen.getText().toString().equals(s)) {
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
        });

        squareEighteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareEighteen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareEighteen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareEighteen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareEighteen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareEighteen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareEighteen.getText().toString().equals(s)) {
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
        });

        squareNineteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareNineteen.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareNineteen.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareNineteen.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareNineteen.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareNineteen.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareNineteen.getText().toString().equals(s)) {
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
        });

        squareTwenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwenty.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwenty.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwenty.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwenty.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwenty.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwenty.getText().toString().equals(s)) {
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
        });

        squareTwentyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwentyOne.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwentyOne.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwentyOne.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwentyOne.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwentyOne.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwentyOne.getText().toString().equals(s)) {
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
        });

        squareTwentyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwentyTwo.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwentyTwo.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwentyTwo.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwentyTwo.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwentyTwo.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwentyTwo.getText().toString().equals(s)) {
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
        });

        squareTwentyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwentyThree.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwentyThree.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwentyThree.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwentyThree.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwentyThree.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwentyThree.getText().toString().equals(s)) {
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
        });

        squareTwentyFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwentyFour.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwentyFour.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwentyFour.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwentyFour.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwentyFour.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwentyFour.getText().toString().equals(s)) {
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
        });

        squareTwentyFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!squareTwentyFive.hasBeenClicked() && gamePhase != TEAM_A_SPY && gamePhase != TEAM_B_SPY) {
                    squareTwentyFive.setHasBeenClicked(true);

                    String buttonType = "";

                    for (String s : bombWords) {
                        if (squareTwentyFive.getText().toString().equals(s)) {
                            buttonType = "bomb";
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : neutralWords) {
                            if (squareTwentyFive.getText().toString().equals(s)) {
                                buttonType = "neutral";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamAWords) {
                            if (squareTwentyFive.getText().toString().equals(s)) {
                                buttonType = "teamA";
                            }
                        }
                    }

                    if (buttonType.equals("")) {
                        for (String s : teamBWords) {
                            if (squareTwentyFive.getText().toString().equals(s)) {
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
        teamCounterLine.setTextColor(defaultColour);

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
