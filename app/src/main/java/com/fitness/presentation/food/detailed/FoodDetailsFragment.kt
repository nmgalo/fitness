package com.fitness.presentation.food.detailed

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.fitness.R
import com.fitness.databinding.FragmentFoodDetailedBinding
import com.fitness.presentation.common.BaseFragment
import com.fitness.presentation.utils.extensions.collectSharedFlowStarted
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailsFragment : BaseFragment<FoodDetailsViewModel>(R.layout.fragment_food_detailed) {
    override val viewModelClass = FoodDetailsViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentFoodDetailedBinding.bind(view).onViewBind()
    }

    private fun FragmentFoodDetailedBinding.onViewBind() {
        toolbar.root.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        collectSharedFlowStarted(viewModel.information) { information ->
            Picasso.get().load(information.image).fit().into(foodImage)
            foodName.text = information.title
            foodSummary.text = Html.fromHtml(information.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}
