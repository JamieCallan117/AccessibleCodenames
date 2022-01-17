package uk.ac.swansea.codenames

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class WordButton(context: Context?, attrs: AttributeSet?) : MaterialButton(context!!, attrs) {
    private var hasBeenClicked = false

    fun hasBeenClicked(): Boolean {
        return hasBeenClicked
    }

    fun setHasBeenClicked(hasBeenClicked: Boolean) {
        this.hasBeenClicked = hasBeenClicked
    }
}