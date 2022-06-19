package com.fitness.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.fitness.R
import com.fitness.presentation.utils.extensions.gone
import com.fitness.presentation.utils.extensions.show

class LoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        alpha = 0f
        View.inflate(context, R.layout.loading_view, this)
    }

    fun showLoader(isLoading: Boolean = true) {
        if (isLoading) start() else stop()
    }

    private fun start() {
        show()
        animate().alpha(1f).setDuration(100L).start()
    }

    private fun stop() {
        animate().alpha(0f).setDuration(100L).withEndAction {
            gone()
            alpha = 0f
        }.start()
    }
}
