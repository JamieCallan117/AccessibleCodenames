package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class online_setup extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private androidx.gridlayout.widget.GridLayout messageBox;
    private TextView gameSetupTitle;
    private TextView gameSetupSubtitle;
    private TextView messageText;
    private TextView usernameText;
    private EditText usernameEdit;
    private Button backButton;
    private Button joinGameButton;
    private Button createGameButton;
    private Button okButton;

    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.online_setup);

        constraintLayout = findViewById(R.id.constraintLayout);
        gameSetupTitle = findViewById(R.id.gameSetupTitle);
        gameSetupSubtitle = findViewById(R.id.gameSetupSubtitle);
        usernameText = findViewById(R.id.usernameText);
        usernameEdit = findViewById(R.id.usernameEdit);
        backButton = findViewById(R.id.backButton);
        joinGameButton = findViewById(R.id.joinGameButton);
        createGameButton = findViewById(R.id.createGameButton);
        messageBox = findViewById(R.id.messageBox);
        messageText = findViewById(R.id.messageText);
        okButton = findViewById(R.id.okButton);

        usernameEdit.setText(userSettings.getInstance().getPreference("username"));

        socketConnection.socket.connect();

        if (!socketConnection.socket.connected()) {
            messageBox.setVisibility(View.VISIBLE);
            messageText.setText(R.string.connecting_server);
            okButton.setVisibility(View.INVISIBLE);

            backButton.setEnabled(false);
            joinGameButton.setEnabled(false);
            createGameButton.setEnabled(false);
            usernameEdit.setEnabled(false);

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    messageBox.setVisibility(View.INVISIBLE);

                    if (!socketConnection.socket.connected()) {
                        messageText.setText(R.string.unable_server);
                        messageBox.setVisibility(View.VISIBLE);
                        okButton.setVisibility(View.VISIBLE);
                    } else {
                        backButton.setEnabled(true);
                        joinGameButton.setEnabled(true);
                        createGameButton.setEnabled(true);
                        usernameEdit.setEnabled(true);
                    }
                }
            }, 3000);
        }

        okButton.setOnClickListener(v -> {
            Intent i = new Intent(getBaseContext(), main_menu.class);
            startActivity(i);
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        userSettings.getInstance().writePreference("username", usernameEdit.getText().toString().trim());

        Intent i = new Intent(view.getContext(), main_menu.class);
        startActivity(i);
    }

    public void createGame(View view) {
        userSettings.getInstance().writePreference("username", usernameEdit.getText().toString().trim());

        Intent i = new Intent(view.getContext(), create_game.class);
        i.putExtra("hasCustomSettings", false);
        startActivity(i);
    }

    public void joinGame(View view) {
        userSettings.getInstance().writePreference("username", usernameEdit.getText().toString().trim());

        Intent i = new Intent(view.getContext(), join_game.class);
        startActivity(i);
    }


    public void updateColours() {
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
        joinGameButton.setBackgroundColor(defaultColour);
        createGameButton.setBackgroundColor(defaultColour);
        okButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        gameSetupTitle.setTextColor(defaultColour);
        gameSetupSubtitle.setTextColor(defaultColour);
        usernameText.setTextColor(defaultColour);
        usernameEdit.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        joinGameButton.setTextColor(defaultColour);
        createGameButton.setTextColor(defaultColour);
        messageText.setTextColor(defaultColour);
        okButton.setTextColor(defaultColour);
    }

}
