package uk.ac.swansea.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class local_game extends AppCompatActivity {
    localPhase gamePhase;
    int bombSquares;
    int neutralSquares;
    int teamOneSquares;
    int teamTwoSquares;
    String[] words;

    //TODO: Add confirmation to quit game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.local_setup);

        if (getIntent().getBooleanExtra("hasCustomSettings", false)) {

        }

        updateColours();
    }

    @Override
    public void onBackPressed() {
        backButton(getWindow().getDecorView());
    }

    public void backButton(View view) {
        Intent i = new Intent(view.getContext(), main_menu.class);
        startActivity(i);
    }

    public void updateColours() {

    }
}
