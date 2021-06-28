package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class settings extends AppCompatActivity {

    // TODO: Make the high contrast checkbox work.
    // TODO: Make the TTS checkbox work.
    // TODO: Move the text on left side to a better position.

    private ConstraintLayout constraintLayout;
    private Button backButton;
    private Button colourButton;
    private TextView settingsTitle;
    private TextView musicVolumeText;
    private TextView soundFXVolumeText;
    private TextView vibrationText;
    private TextView contrastText;
    private TextView textToSpeechText;
    private SeekBar musicVolumeSeek;
    private SeekBar soundFXVolumeSeek;
    private CheckBox vibrationCheck;
    private CheckBox contrastCheck;
    private CheckBox textToSpeechCheck;
    private int musicVolume;
    private int soundFXVolume;
    private int defaultColour;
    private boolean vibration;
    private boolean contrast;
    private boolean textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings);

        updateColours();

        musicVolumeSeek = findViewById(R.id.musicVolumeSeek);

        if (userSettings.getInstance().getPreference("musicVolume").equals("")) {
            musicVolume = 100;
        } else {
            musicVolume = Integer.parseInt(userSettings.getInstance().getPreference("musicVolume"));
        }

        musicVolumeSeek.setProgress(musicVolume);

        musicVolumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                userSettings.getInstance().writePreference("musicVolume", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        soundFXVolumeSeek = findViewById(R.id.soundFXVolumeSeek);

        if (userSettings.getInstance().getPreference("soundFXVolume").equals("")) {
            soundFXVolume = 100;
        } else {
            soundFXVolume = Integer.parseInt(userSettings.getInstance().getPreference("soundFXVolume"));
        }

        soundFXVolumeSeek.setProgress(soundFXVolume);

        soundFXVolumeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                userSettings.getInstance().writePreference("soundFXVolume", String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vibrationCheck = findViewById(R.id.vibrationCheck);

        if (userSettings.getInstance().getPreference("vibration").equals("true")) {
            vibration = true;
        } else if (userSettings.getInstance().getPreference("vibration").equals("false")) {
            vibration = false;
        } else {
            vibration = true;
        }

        vibrationCheck.setChecked(vibration);

        vibrationCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userSettings.getInstance().writePreference("vibration", "true");
                } else {
                    userSettings.getInstance().writePreference("vibration", "false");
                }
            }
        });

        contrastCheck = findViewById(R.id.contrastCheck);

        if (userSettings.getInstance().getPreference("contrast").equals("true")) {
            contrast = true;
        } else if (userSettings.getInstance().getPreference("contrast").equals("false")) {
            contrast = false;
        } else {
            contrast = false;
        }

        contrastCheck.setChecked(contrast);

        contrastCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userSettings.getInstance().writePreference("contrast", "true");
                } else {
                    userSettings.getInstance().writePreference("contrast", "false");
                }
            }
        });

        textToSpeechCheck = findViewById(R.id.textToSpeechCheck);

        if (userSettings.getInstance().getPreference("textToSpeech").equals("true")) {
            textToSpeech = true;
        } else if (userSettings.getInstance().getPreference("textToSpeech").equals("false")) {
            textToSpeech = false;
        } else {
            textToSpeech = false;
        }

        textToSpeechCheck.setChecked(textToSpeech);

        textToSpeechCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    userSettings.getInstance().writePreference("textToSpeech", "true");
                } else {
                    userSettings.getInstance().writePreference("textToSpeech", "false");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i;

        if (getIntent().getStringExtra("from").equals("start_screen")) {
            i = new Intent(view.getContext(), start_screen.class);
            //i.putExtra("from", "start_screen");
            startActivity(i);
        } else if (getIntent().getStringExtra("from").equals("main_menu")) {
            i = new Intent(view.getContext(), main_menu.class);
            //i.putExtra("from", "main_menu");
            startActivity(i);
        }
    }

    public void toColour(View view) {
        Intent i = new Intent(view.getContext(), colour_options.class);

        if (getIntent().getStringExtra("from").equals("start_screen")) {
            i.putExtra("from", "start_screen");
        } else if (getIntent().getStringExtra("from").equals("main_menu")) {
            i.putExtra("from", "main_menu");
        }

        startActivity(i);
    }

    public void updateColours() {
       constraintLayout = findViewById(R.id.saveButton);
       settingsTitle = findViewById(R.id.settingsTitle);
       musicVolumeText = findViewById(R.id.musicVolumeText);
       soundFXVolumeText = findViewById(R.id.soundFXVolumeText);
       vibrationText = findViewById(R.id.vibrationText);
       contrastText = findViewById(R.id.contrastText);
       textToSpeechText = findViewById(R.id.textToSpeechText);
       backButton = findViewById(R.id.backButton);
       colourButton = findViewById(R.id.colourButton);

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
        colourButton.setBackgroundColor(defaultColour);

        if (userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT).equals("")) {
            defaultColour = userSettings.getInstance().MENU_TEXT_DEFAULT;
        } else {
            defaultColour = Integer.parseInt(userSettings.getInstance().getPreference(userSettings.getInstance().MENU_TEXT));
        }

        settingsTitle.setTextColor(defaultColour);
        musicVolumeText.setTextColor(defaultColour);
        soundFXVolumeText.setTextColor(defaultColour);
        vibrationText.setTextColor(defaultColour);
        contrastText.setTextColor(defaultColour);
        textToSpeechText.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
        colourButton.setTextColor(defaultColour);
    }
}
