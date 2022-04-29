package uk.ac.swansea.codenames

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

/**
 * WordButton is a slightly different button as it can tell if it has been pressed or not.
 */
class WordButton(context: Context?, attrs: AttributeSet?) : MaterialButton(context!!, attrs) {
    private var hasBeenClicked = false

    /**
     * Returns if the button has been clicked or not.
     */
    fun hasBeenClicked(): Boolean {
        return hasBeenClicked
    }

    /**
     * Sets if the button has been clicked or not.
     */
    fun setHasBeenClicked(hasBeenClicked: Boolean) {
        this.hasBeenClicked = hasBeenClicked
    }
}