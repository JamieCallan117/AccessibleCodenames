package uk.ac.swansea.codenames;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
    private TextView joinGameTitle;

    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.join_game);

        constraintLayout = findViewById(R.id.constraintLayout);
        scrollLinear = findViewById(R.id.scrollLinear);
        backButton = findViewById(R.id.backButton);
        joinGameTitle = findViewById(R.id.joinGameTitle);

        socketConnection.socket.emit("getAllRooms");

        socketConnection.socket.on("allRooms", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
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

                            TextView newRoom = new TextView(getApplicationContext());
                            newRoom.setText(name);

                            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.general_font);
                            newRoom.setTypeface(typeface);

                            if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
                                defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
                            } else {
                                defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
                            }

                            newRoom.setTextColor(defaultColour);

                            Button joinButton = new Button(getApplicationContext());
                            joinButton.setText("Join");
                            joinButton.setTextColor(defaultColour);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.gravity = Gravity.RIGHT;

                            joinButton.setLayoutParams(params);


                            if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS).equals("")) {
                                defaultColour = userSettings.getInstance().MENU_BUTTONS_DEFAULT;
                            } else {
                                defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_BUTTONS));
                            }

                            joinButton.setBackgroundColor(defaultColour);

                            joinButton.setOnClickListener(v -> {
                                System.out.println(newRoom.getText().toString());
                            });

                            scrollLinear.addView(roomLinear);
                            roomLinear.addView(newRoom);
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

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        joinGameTitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
    }
}
