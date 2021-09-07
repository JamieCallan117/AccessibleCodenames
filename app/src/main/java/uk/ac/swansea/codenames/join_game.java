package uk.ac.swansea.codenames;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import io.socket.emitter.Emitter;

public class join_game extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private LinearLayout scrollLinear;
    private Button backButton;
    private Button refreshButton;
    private Button joinPrivateButton;
    private TextView joinGameTitle;
    private TextView publicRoomText;
    private TextView privateRoomText;
    private EditText roomNameEdit;
    private EditText passwordEdit;

    private ArrayList<Button> publicJoinButtons = new ArrayList<>();

    private int defaultColour;
    private String username;
    private boolean validJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.join_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        scrollLinear = findViewById(R.id.scrollLinear);
        backButton = findViewById(R.id.backButton);
        refreshButton = findViewById(R.id.refreshButton);
        joinGameTitle = findViewById(R.id.joinGameTitle);
        joinPrivateButton = findViewById(R.id.joinPrivateButton);
        publicRoomText = findViewById(R.id.publicRoomText);
        privateRoomText = findViewById(R.id.privateRoomText);
        roomNameEdit = findViewById(R.id.roomNameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);

        username = userSettings.getInstance().getPreference("username");

        socketConnection.socket.emit("getAllRooms");

        joinPrivateButton.setOnClickListener(v -> {
            validJoin = true;

            socketConnection.socket.emit("joinRoom", username, roomNameEdit.getText().toString(),
                    passwordEdit.getText().toString());

            backButton.setEnabled(false);
            refreshButton.setEnabled(false);
            roomNameEdit.setEnabled(false);
            passwordEdit.setEnabled(false);
            joinPrivateButton.setEnabled(false);

            for (Button b : publicJoinButtons) {
                b.setEnabled(false);
            }

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (validJoin) {
                        Intent i = new Intent(getApplicationContext(), online_game.class);
                        i.putExtra("username", userSettings.getInstance().getPreference("username"));
                        i.putExtra("roomName", roomNameEdit.getText().toString());
                        i.putExtra("isHost", false);
                        startActivity(i);
                    } else {
                        backButton.setEnabled(true);
                        refreshButton.setEnabled(true);
                        roomNameEdit.setEnabled(true);
                        passwordEdit.setEnabled(true);
                        joinPrivateButton.setEnabled(true);
                    }
                }
            }, 3000);
        });

        refreshButton.setOnClickListener(v -> {
            scrollLinear.removeAllViews();

            socketConnection.socket.emit("getAllRooms");
        });

        socketConnection.socket.on("joinFail", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                toggleMessageBox((String) args[0]);
                validJoin = false;
            }
        });

        socketConnection.socket.on("joinFailNick", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                toggleNicknameBox((String) args[0]);
                validJoin = false;
            }
        });

        socketConnection.socket.on("allRooms", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                publicJoinButtons.clear();

                JSONObject data = (JSONObject) args[0];

                ArrayList<String> allRoomNames = new ArrayList<>();
                ArrayList<String> publicRooms = new ArrayList<>();

                JSONArray jArray = (JSONArray) data.names();

                if (jArray != null) {
                    for (int i = 0; i < jArray.length(); i++) {
                        try {
                            allRoomNames.add(jArray.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                for (String s : allRoomNames) {
                    try {
                        JSONObject j = (JSONObject) data.get(s);

                        String temp = j.toString();

                        String[] parts = temp.split(",");

                        parts[0] = parts[0].replace("{\"roomName\":", "");
                        parts[1] = parts[1].replace("\"password\":", "");
                        parts[3] = parts[3].replace("\"closed\":", "");
                        parts[3] = parts[3].replace("}", "");

                        String roomName = parts[0].replace("\"", "");
                        String password = parts[1].replace("\"", "");
                        boolean closed = Boolean.parseBoolean(parts[3]);

                        if (password.equals("") && !closed) {
                            publicRooms.add(roomName);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (String name : publicRooms) {
                            LinearLayout roomLinear = new LinearLayout(getApplicationContext());
                            roomLinear.setOrientation(LinearLayout.HORIZONTAL);

                            if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
                                defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
                            } else {
                                defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
                            }

                            Button joinButton = new Button(getApplicationContext());
                            joinButton.setText(name);
                            joinButton.setTextColor(defaultColour);

                            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            joinButton.setLayoutParams(buttonParams);

                            if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
                                defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
                            } else {
                                defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
                            }

                            joinButton.setBackgroundColor(defaultColour);

                            joinButton.setOnClickListener(v -> {
                                validJoin = true;

                                socketConnection.socket.emit("joinRoom", username, name, "");

                                backButton.setEnabled(false);
                                refreshButton.setEnabled(false);
                                roomNameEdit.setEnabled(false);
                                passwordEdit.setEnabled(false);
                                joinPrivateButton.setEnabled(false);

                                for (Button b : publicJoinButtons) {
                                    b.setEnabled(false);
                                }

                                Handler handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (validJoin) {
                                            Intent i = new Intent(getApplicationContext(), online_game.class);
                                            i.putExtra("username", userSettings.getInstance().getPreference("username"));
                                            i.putExtra("roomName", name);
                                            i.putExtra("isHost", false);
                                            startActivity(i);
                                        } else {
                                            backButton.setEnabled(true);
                                            refreshButton.setEnabled(true);
                                            roomNameEdit.setEnabled(true);
                                            passwordEdit.setEnabled(true);
                                            joinPrivateButton.setEnabled(true);
                                        }
                                    }
                                }, 3000);
                            });

                            publicJoinButtons.add(joinButton);

                            scrollLinear.addView(roomLinear);
                            roomLinear.addView(joinButton);
                        }
                    }
                });
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

    private void toggleMessageBox(String message) {

    }

    private void toggleNicknameBox(String message) {

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
        refreshButton.setBackgroundColor(defaultColour);
        joinPrivateButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        joinGameTitle.setTextColor(defaultColour);
        publicRoomText.setTextColor(defaultColour);
        privateRoomText.setTextColor(defaultColour);
        roomNameEdit.setTextColor(defaultColour);
        passwordEdit.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        refreshButton.setTextColor(defaultColour);
        joinPrivateButton.setTextColor(defaultColour);
    }
}
