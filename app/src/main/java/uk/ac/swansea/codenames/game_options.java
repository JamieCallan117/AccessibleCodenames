package uk.ac.swansea.codenames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class game_options extends AppCompatActivity {

    //TODO: Make it so any interaction runs sortWords(10)
    //TODO: Add the other game options

    private ConstraintLayout constraintLayout;
    private TextView gameOptionsTitle;
    private TextView gameOptionsSubtitle;
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
    private boolean hasCustomSettings;
    private int defaultColour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_options);

        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle);

        if (getIntent().getStringExtra("from").equals("local_setup")) {
            gameOptionsSubtitle.setText("Local");
        } else if (getIntent().getStringExtra("from").equals("online_setup")) {
            gameOptionsSubtitle.setText("Online");
        }

        //Reads words from the text file to store in the defaultWords array
        String wordsFromFile = "";

        try {
            InputStream input = getAssets().open("gameWords.txt");

            int size = input.available();

            byte[] buffer = new byte[size];

            input.read(buffer);
            input.close();

            wordsFromFile = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] defaultWords = wordsFromFile.split("\n");

        //Logs the words in the array
        for (String s : defaultWords) {
            Log.i("Word", s);
        }

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

        //Don't think this is needed.
//        customWordText1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText1.getText().toString().equals("")) {
//                    sortWords(1);
//                }
//
//                return false;
//            }
//        });

        customWordText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText2.getText().toString().equals("")) {
                    sortWords(2);
                }

                return false;
            }
        });

        customWordText3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText3.getText().toString().equals("")) {
                    sortWords(3);
                }

                return false;
            }
        });

        customWordText4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText4.getText().toString().equals("")) {
                    sortWords(4);
                }

                return false;
            }
        });

        customWordText5.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText5.getText().toString().equals("")) {
                    sortWords(5);
                }

                return false;
            }
        });

        customWordText6.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText6.getText().toString().equals("")) {
                    sortWords(6);
                }

                return false;
            }
        });

        customWordText7.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText7.getText().toString().equals("")) {
                    sortWords(7);
                }

                return false;
            }
        });

        customWordText8.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText8.getText().toString().equals("")) {
                    sortWords(8);
                }

                return false;
            }
        });

        customWordText9.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText9.getText().toString().equals("")) {
                    sortWords(9);
                }

                return false;
            }
        });

        customWordText10.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !customWordText10.getText().toString().equals("")) {
                    sortWords(10);
                }

                return false;
            }
        });

        deleteCustomWord1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(1);
            }
        });

        deleteCustomWord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(2);
            }
        });

        deleteCustomWord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(3);
            }
        });

        deleteCustomWord4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(4);
            }
        });

        deleteCustomWord5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(5);
            }
        });

        deleteCustomWord6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(6);
            }
        });

        deleteCustomWord7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(7);
            }
        });

        deleteCustomWord8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(8);
            }
        });

        deleteCustomWord9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(9);
            }
        });

        deleteCustomWord10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWords(10);
            }
        });

        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i;

        if (getIntent().getStringExtra("from").equals("local_setup")) {
            if (hasCustomSettings) {
                //Add the custom stuff in here i.putExtra() etc
                i = new Intent(view.getContext(), local_setup.class);
                startActivity(i);
            } else {
                i = new Intent(view.getContext(), local_setup.class);
                startActivity(i);
            }
        } else if (getIntent().getStringExtra("from").equals("online_setup")) {
            if (hasCustomSettings) {
                //Add the custom stuff in here i.putExtra() etc
                i = new Intent(view.getContext(), online_setup.class);
                startActivity(i);
            } else {
                i = new Intent(view.getContext(), online_setup.class);
                startActivity(i);
            }
        }
    }

    private void clearWords(int num) {
        switch (num) {
            case 1:
                customWordText1.setText("");

                if (!customWordText2.getText().toString().equals("")) {
                    customWordText1.setText(customWordText2.getText().toString());
                    customWordText2.setText("");
                    clearWords(2);
                }

                break;
            case 2:
                customWordText2.setText("");

                if (!customWordText3.getText().toString().equals("")) {
                    customWordText2.setText(customWordText3.getText().toString());
                    customWordText3.setText("");
                    clearWords(3);
                }

                break;
            case 3:
                customWordText3.setText("");

                if (!customWordText4.getText().toString().equals("")) {
                    customWordText3.setText(customWordText4.getText().toString());
                    customWordText4.setText("");
                    clearWords(4);
                }

                break;
            case 4:
                customWordText4.setText("");

                if (!customWordText5.getText().toString().equals("")) {
                    customWordText4.setText(customWordText5.getText().toString());
                    customWordText5.setText("");
                    clearWords(5);
                }

                break;
            case 5:
                customWordText5.setText("");

                if (!customWordText6.getText().toString().equals("")) {
                    customWordText5.setText(customWordText6.getText().toString());
                    customWordText6.setText("");
                    clearWords(6);
                }

                break;
            case 6:
                customWordText6.setText("");

                if (!customWordText7.getText().toString().equals("")) {
                    customWordText6.setText(customWordText7.getText().toString());
                    customWordText7.setText("");
                    clearWords(7);
                }

                break;
            case 7:
                customWordText7.setText("");

                if (!customWordText8.getText().toString().equals("")) {
                    customWordText7.setText(customWordText8.getText().toString());
                    customWordText8.setText("");
                    clearWords(8);
                }

                break;
            case 8:
                customWordText8.setText("");

                if (!customWordText9.getText().toString().equals("")) {
                    customWordText8.setText(customWordText9.getText().toString());
                    customWordText9.setText("");
                    clearWords(9);
                }

                break;
            case 9:
                customWordText9.setText("");

                if (!customWordText10.getText().toString().equals("")) {
                    customWordText9.setText(customWordText10.getText().toString());
                    customWordText10.setText("");
                    clearWords(10);
                }

                break;
            default:
                customWordText10.setText("");
                break;
        }
    }

    private void sortWords(int num) {
        switch (num) {
            case 2:
                if (customWordText1.getText().toString().equals("")) {
                    customWordText1.setText(customWordText2.getText().toString());
                    customWordText2.setText("");
                }

                break;
            case 3:
                if (customWordText2.getText().toString().equals("")) {
                    customWordText2.setText(customWordText3.getText().toString());
                    customWordText3.setText("");
                    sortWords(2);
                }

                break;
            case 4:
                if (customWordText3.getText().toString().equals("")) {
                    customWordText3.setText(customWordText4.getText().toString());
                    customWordText4.setText("");
                    sortWords(3);
                }

                break;
            case 5:
                if (customWordText4.getText().toString().equals("")) {
                    customWordText4.setText(customWordText5.getText().toString());
                    customWordText5.setText("");
                    sortWords(4);
                }

                break;
            case 6:
                if (customWordText5.getText().toString().equals("")) {
                    customWordText5.setText(customWordText6.getText().toString());
                    customWordText6.setText("");
                    sortWords(5);
                }

                break;
            case 7:
                if (customWordText6.getText().toString().equals("")) {
                    customWordText6.setText(customWordText7.getText().toString());
                    customWordText7.setText("");
                    sortWords(6);
                }

                break;
            case 8:
                if (customWordText7.getText().toString().equals("")) {
                    customWordText7.setText(customWordText8.getText().toString());
                    customWordText8.setText("");
                    sortWords(7);
                }

                break;
            case 9:
                if (customWordText8.getText().toString().equals("")) {
                    customWordText8.setText(customWordText9.getText().toString());
                    customWordText9.setText("");
                    sortWords(8);
                }

                break;
            default:
                if (customWordText9.getText().toString().equals("")) {
                    customWordText9.setText(customWordText10.getText().toString());
                    customWordText10.setText("");
                    sortWords(9);
                }

                break;
        }
    }

    public void updateColours() {
        constraintLayout = findViewById(R.id.constraintLayout);
        gameOptionsTitle = findViewById(R.id.gameOptionsTitle);
        gameOptionsSubtitle = findViewById(R.id.gameOptionsSubtitle);
        backButton = findViewById(R.id.backButton);

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

        gameOptionsTitle.setTextColor(defaultColour);
        gameOptionsSubtitle.setTextColor(defaultColour);
        backButton.setTextColor(defaultColour);
    }
}
