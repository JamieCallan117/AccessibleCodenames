package uk.ac.swansea.codenames;

import android.content.Context;
import android.util.AttributeSet;

public class WordButton extends androidx.appcompat.widget.AppCompatButton {
    private boolean hasBeenClicked;

    public WordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        hasBeenClicked = false;
    }

    public boolean hasBeenClicked() {
        return hasBeenClicked;
    }

    public void setHasBeenClicked(boolean hasBeenClicked) {
        this.hasBeenClicked = hasBeenClicked;
    }
}
