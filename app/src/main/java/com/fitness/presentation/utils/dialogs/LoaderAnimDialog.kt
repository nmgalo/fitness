package com.fitness.presentation.utils.dialogs

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.fitness.databinding.LayoutLottieLoaderBinding

class LoaderAnimDialog(context: Context) : AlertDialog(context) {

    lateinit var binding: LayoutLottieLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        binding = LayoutLottieLoaderBinding.inflate(layoutInflater)
        this.setView(binding.root)
        this.setCancelable(false)
        super.onCreate(savedInstanceState)
    }

    fun showDialog(): LoaderAnimDialog {
        this.show()
        binding.vLoader.playAnimation()
        return this
    }

}