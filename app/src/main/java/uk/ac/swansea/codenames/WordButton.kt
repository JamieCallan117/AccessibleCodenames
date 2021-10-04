package uk.ac.swansea.codenames

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class WordButton(context: Context?, attrs: AttributeSet?) : AppCompatButton(context!!, attrs) {
    private var hasBeenClicked = false
    fun hasBeenClicked(): Boolean {
        return hasBeenClicked
    }

    fun setHasBeenClicked(hasBeenClicked: Boolean) {
        this.hasBeenClicked = hasBeenClicked
    }
}