package com.fitness.presentation.utils.dialogs

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.fitness.R

class PermissionMessageDialog(context: Context) :
    AlertDialog(context) {

    private var clickListener: () -> Unit = {}
    override fun onCreate(savedInstanceState: Bundle?) {
        setMessage(context.getString(R.string.permission_decline_message))
        setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok)) { _, _ ->
            clickListener.invoke()
        }
        super.onCreate(savedInstanceState)
    }

    fun showDialog(clickListener: () -> Unit) {
        this.clickListener = clickListener
        show()

    }

}