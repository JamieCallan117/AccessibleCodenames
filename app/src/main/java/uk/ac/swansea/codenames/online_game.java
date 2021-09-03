package uk.ac.swansea.codenames;

import static uk.ac.swansea.codenames.onlinePhase.START;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import io.socket.emitter.Emitter;

public class online_game extends AppCompatActivity {
    private Player player;
    private String roomName;
    private ArrayList<Player> teamAUsers = new ArrayList<>();
    private ArrayList<Player> teamBUsers = new ArrayList<>();
    private Player teamASpymaster;
    private Player teamBSpymaster;
    private ArrayList<String> allWords = new ArrayList<>();
    private ArrayList<String> bombWords = new ArrayList<>();
    private ArrayList<String> neutralWords = new ArrayList<>();
    private ArrayList<String> teamAWords = new ArrayList<>();
    private ArrayList<String> teamBWords = new ArrayList<>();
    private int startingTeam;
    private int defaultColour;
    private boolean teamsBoxOpen = false;
    private boolean wordButtonsEnabled = false;
    private onlinePhase gamePhase = START;

    private ConstraintLayout constraintLayout;
    private WordButton[] wordButtons;
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
    private Button exitButton;
    private Button startGame;
    private Button requestSpymaster;
    private Button changeTeamButton;
    private Button turnAction;
    private Button textToSpeechButton;
    private Button viewTeams;
    private Button teamAButton;
    private Button teamBButton;
    private Button chatButton;
    private Button closeTeamsBox;
    private ScrollView gameOperationsScroll;
    private LinearLayout gameOperationsLinear;
    private LinearLayout teamALinear;
    private LinearLayout teamBLinear;
    private TextView teamACount;
    private TextView teamBCount;
    private TextView teamAHeader;
    private TextView teamBHeader;
    private TextView teamCounterLine;
    private TextView hintText;
    private EditText editHint;
    private Spinner hintNumber;
    private androidx.gridlayout.widget.GridLayout chooseTeamBox;
    private androidx.gridlayout.widget.GridLayout viewTeamsBox;
    private View teamSeparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.online_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        exitButton = findViewById(R.id.exitButton);
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
        startGame = findViewById(R.id.startGame);
        requestSpymaster = findViewById(R.id.requestSpymaster);
        changeTeamButton = findViewById(R.id.changeTeamButton);
        turnAction = findViewById(R.id.turnAction);
        textToSpeechButton = findViewById(R.id.textToSpeechButton);
        teamAButton = findViewById(R.id.teamAButton);
        teamBButton = findViewById(R.id.teamBButton);
        viewTeams = findViewById(R.id.viewTeams);
        chatButton = findViewById(R.id.chatButton);
        gameOperationsScroll = findViewById(R.id.gameOperationsScroll);
        gameOperationsLinear = findViewById(R.id.gameOperationsLinear);
        hintText = findViewById(R.id.hintText);
        editHint = findViewById(R.id.editHint);
        hintNumber = findViewById(R.id.hintNumber);
        teamACount = findViewById(R.id.teamACount);
        teamBCount = findViewById(R.id.teamBCount);
        teamCounterLine = findViewById(R.id.teamCounterLine);
        chooseTeamBox = findViewById(R.id.chooseTeamBox);
        viewTeamsBox = findViewById(R.id.viewTeamsBox);
        teamAHeader = findViewById(R.id.teamAHeader);
        teamBHeader = findViewById(R.id.teamBHeader);
        teamALinear = findViewById(R.id.teamALinear);
        teamBLinear = findViewById(R.id.teamBLinear);
        closeTeamsBox = findViewById(R.id.closeTeamsBox);
        teamSeparator = findViewById(R.id.teamSeparator);

        wordButtons = new WordButton[]{squareOne, squareTwo, squareThree, squareFour, squareFive,
                squareSix, squareSeven, squareEight, squareNine, squareTen, squareEleven,
                squareTwelve, squareThirteen, squareFourteen, squareFifteen, squareSixteen,
                squareSeventeen, squareEighteen, squareNineteen, squareTwenty, squareTwentyOne,
                squareTwentyTwo, squareTwentyThree, squareTwentyFour, squareTwentyFive};

        player = new Player(userSettings.getInstance().getPreference("username"));

        player.setHost(getIntent().getBooleanExtra("isHost", false));

        roomName = getIntent().getStringExtra("roomName");

        socketConnection.socket.emit("getGameDetails", roomName);

        startGame.setEnabled(false);
        requestSpymaster.setEnabled(false);
        changeTeamButton.setEnabled(false);
        chatButton.setEnabled(false);
        viewTeams.setEnabled(false);

        hintText.setVisibility(View.GONE);
        editHint.setVisibility(View.GONE);
        hintNumber.setVisibility(View.GONE);
        turnAction.setVisibility(View.GONE);
        textToSpeechButton.setVisibility(View.GONE);

        if (!player.isHost()) {
            gameOperationsLinear.removeView(startGame);
            viewTeams.setVisibility(View.GONE);
        }

        toggleWordButtons();

        startGame.setOnClickListener(v -> {
            toggleWordButtons();
        });

        teamAButton.setOnClickListener(v -> {
            socketConnection.socket.emit("chooseTeam", player.getNickname(), "A", roomName);
            constraintLayout.removeView(chooseTeamBox);
            startGame.setEnabled(true);
            requestSpymaster.setEnabled(true);
            changeTeamButton.setEnabled(true);
            chatButton.setEnabled(true);
            viewTeams.setEnabled(true);
        });

        teamBButton.setOnClickListener(v -> {
            socketConnection.socket.emit("chooseTeam", player.getNickname(),"B", roomName);
            constraintLayout.removeView(chooseTeamBox);
            startGame.setEnabled(true);
            requestSpymaster.setEnabled(true);
            changeTeamButton.setEnabled(true);
            chatButton.setEnabled(true);
            viewTeams.setEnabled(true);
        });

        requestSpymaster.setOnClickListener(v -> {
            socketConnection.socket.emit("requestSpymaster", player.getNickname(), roomName, player.getTeam());
            requestSpymaster.setVisibility(View.GONE); //When a spymaster quits bring the button back
        });

        changeTeamButton.setOnClickListener(v -> {
            for (Player p : teamAUsers) {
                if (p.getNickname().equals(player.getNickname())) {
                    socketConnection.socket.emit("chooseTeam", player.getNickname(), "B", roomName);
                    break;
                }
            }

            for (Player p : teamBUsers) {
                if (p.getNickname().equals(player.getNickname())) {
                    socketConnection.socket.emit("chooseTeam", player.getNickname(), "A", roomName);
                    break;
                }
            }
        });

        closeTeamsBox.setOnClickListener(v -> {
            toggleTeamsBox();
        });

        viewTeams.setOnClickListener(v -> {
            toggleTeamsBox();
        });

        socketConnection.socket.on("gameDetails", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    allWords = jsonArrayToStrings((JSONArray) args[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    teamAUsers = jsonArrayToPlayers((JSONArray) args[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    teamBUsers = jsonArrayToPlayers((JSONArray) args[2]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (args[3] != null) {
                    teamASpymaster = new Player((String) args[3]);
                } else {
                    teamASpymaster = null;
                }

                if (args[4] != null) {
                    teamBSpymaster = new Player((String) args[4]);
                } else {
                    teamBSpymaster = null;
                }

                try {
                    bombWords = jsonArrayToStrings((JSONArray) args[5]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    neutralWords = jsonArrayToStrings((JSONArray) args[6]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    teamAWords = jsonArrayToStrings((JSONArray) args[7]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    teamBWords = jsonArrayToStrings((JSONArray) args[8]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startingTeam =  (int) args[9];

                System.out.println("All words: " + allWords);
                System.out.println("Bomb words: " + bombWords);
                System.out.println("Neutral words: " + neutralWords);
                System.out.println("Team A words: " + teamAWords);
                System.out.println("Team B words: " + teamBWords);
                System.out.println("Team A Spymaster: " + teamASpymaster);
                System.out.println("Team B Spymaster: " + teamBSpymaster);
                System.out.println("Team A Users: " + teamAUsers);
                System.out.println("Team B Users: " + teamBUsers);
                System.out.println("Starting Team: " + startingTeam);

                for (int i = 0; i < wordButtons.length; i++) {
                    wordButtons[i].setText(allWords.get(i));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        teamACount.setText(String.valueOf(teamAWords.size()));
                        teamBCount.setText(String.valueOf(teamBWords.size()));
                    }
                });
            }
        });

        socketConnection.socket.on("teamChange", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String playerName = (String) args[0];
                String team = (String) args[1];

                Player newPlayer = new Player(playerName, team);

                for (Player p : teamAUsers) {
                    if (p.getNickname().equals(playerName)) {
                        teamAUsers.remove(p);
                        break;
                    }
                }

                for (Player p : teamBUsers) {
                    if (p.getNickname().equals(playerName)) {
                        teamBUsers.remove(p);
                        break;
                    }
                }

                if (team.equals("A")) {
                    teamAUsers.add(newPlayer);
                } else {
                    teamBUsers.add(newPlayer);
                }

                if (newPlayer.getNickname().equals(player.getNickname())) {
                    player.setTeam(team);
                }
            }
        });

        socketConnection.socket.on("teamASpymaster", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String newUser = (String) args[0];
                teamASpymaster = new Player(newUser, "A");

                for (Player p : teamAUsers) {
                    if (p.getNickname().equals(newUser)) {
                        p.setSpymaster(true);
                    }
                }
            }
        });

        socketConnection.socket.on("teamBSpymaster", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String newUser = (String) args[0];
                teamBSpymaster = new Player(newUser, "B");

                for (Player p : teamBUsers) {
                    if (p.getNickname().equals(newUser)) {
                        p.setSpymaster(true);
                    }
                }
            }
        });

        socketConnection.socket.on("spymasterFail", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                toggleMessageBox(); //"The Spymaster for your team has already been selected"
            }
        });

        socketConnection.socket.on("hostQuit", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                //Maybe change to pop up box saying host quit, return to main menu with button
                Intent i = new Intent(getApplicationContext(), main_menu.class);
                startActivity(i);
            }
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        exitButton(getWindow().getDecorView());
    }

    public void exitButton(View view) {
        socketConnection.socket.emit("leaveRoom");
        Intent i = new Intent(view.getContext(), main_menu.class);
        startActivity(i);
    }

    //Either take in a string for the displayed message or numbered switch case for each potential message
    private void toggleMessageBox() {

    }

    private void toggleWordButtons() {
        for (WordButton wb : wordButtons) {
            wb.setEnabled(wordButtonsEnabled);
        }

        wordButtonsEnabled = !wordButtonsEnabled;
    }

    private void toggleTeamsBox() {
        if (!teamsBoxOpen) {
            teamALinear.removeAllViews();
            teamBLinear.removeAllViews();

            exitButton.setEnabled(false);
            startGame.setEnabled(false);
            requestSpymaster.setEnabled(false);
            changeTeamButton.setEnabled(false);
            chatButton.setEnabled(false);
            viewTeams.setEnabled(false);
            hintText.setEnabled(false);
            editHint.setEnabled(false);
            hintNumber.setEnabled(false);
            turnAction.setEnabled(false);
            textToSpeechButton.setEnabled(false);

            viewTeamsBox.setVisibility(View.VISIBLE);
            teamsBoxOpen = true;

            for (Player p : teamAUsers) {
                TextView newPlayer = new TextView(this);

                newPlayer.setText(p.getNickname());

                if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
                    defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
                }

                newPlayer.setTextColor(defaultColour);
                newPlayer.setTextSize(30);

                teamALinear.addView(newPlayer);
            }

            for (Player p : teamBUsers) {
                TextView newPlayer = new TextView(this);

                newPlayer.setText(p.getNickname());

                if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
                    defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
                } else {
                    defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
                }

                newPlayer.setTextColor(defaultColour);
                newPlayer.setTextSize(30);

                teamBLinear.addView(newPlayer);
            }
        } else {
            viewTeamsBox.setVisibility(View.INVISIBLE);
            teamsBoxOpen = false;

            exitButton.setEnabled(true);
            startGame.setEnabled(true);
            requestSpymaster.setEnabled(true);
            changeTeamButton.setEnabled(true);
            chatButton.setEnabled(true);
            viewTeams.setEnabled(true);
            hintText.setEnabled(true);
            editHint.setEnabled(true);
            hintNumber.setEnabled(true);
            turnAction.setEnabled(true);
            textToSpeechButton.setEnabled(true);
        }
    }

    private ArrayList<Player> jsonArrayToPlayers(JSONArray jsonArray) throws JSONException {
        ArrayList<Player> players = new ArrayList<>();

        if (jsonArray != null) {
            int len = jsonArray.length();

            for (int i = 0; i < len; i++) {
                players.add(new Player(jsonArray.getString(i)));
            }
        }

        return players;
    }

    private ArrayList<String> jsonArrayToStrings(JSONArray jsonArray) throws JSONException {
        ArrayList<String> words = new ArrayList<>();

        if (jsonArray != null) {
            int len = jsonArray.length();

            for (int i = 0; i < len; i++) {
                words.add(jsonArray.getString(i));
            }
        }

        return words;
    }

    private void updateColours() {
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
        chooseTeamBox.setBackgroundColor(defaultColour);
        viewTeamsBox.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
            defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
        }

        exitButton.setBackgroundColor(defaultColour);
        startGame.setBackgroundColor(defaultColour);
        requestSpymaster.setBackgroundColor(defaultColour);
        changeTeamButton.setBackgroundColor(defaultColour);
        teamAButton.setBackgroundColor(defaultColour);
        teamBButton.setBackgroundColor(defaultColour);
        closeTeamsBox.setBackgroundColor(defaultColour);
        turnAction.setBackgroundColor(defaultColour);
        textToSpeechButton.setBackgroundColor(defaultColour);
        viewTeams.setBackgroundColor(defaultColour);
        chatButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        teamCounterLine.setTextColor(defaultColour);
        teamAHeader.setTextColor(defaultColour);
        teamBHeader.setTextColor(defaultColour);
        exitButton.setTextColor(defaultColour);
        startGame.setTextColor(defaultColour);
        requestSpymaster.setTextColor(defaultColour);
        changeTeamButton.setTextColor(defaultColour);
        teamAButton.setTextColor(defaultColour);
        teamBButton.setTextColor(defaultColour);
        closeTeamsBox.setTextColor(defaultColour);
        turnAction.setTextColor(defaultColour);
        textToSpeechButton.setTextColor(defaultColour);
        viewTeams.setTextColor(defaultColour);
        chatButton.setTextColor(defaultColour);
        teamSeparator.setBackgroundColor(defaultColour);
    }
}
