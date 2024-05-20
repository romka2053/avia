package com.roman.airtickets.presentation

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.airtickets.R
import com.roman.airtickets.databinding.BottomSheetDialogBinding
import com.roman.airtickets.presentation.adapters.MyLinearLayoutManager
import com.roman.airtickets.presentation.adapters.RecommendationsMainAdapter
import com.roman.airtickets.presentation.viewModel.MainViewModel
import kotlinx.coroutines.launch
import java.util.*

class MyBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val adapterRecommendations = RecommendationsMainAdapter { text -> endEdit(text) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        binding.root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        bottomSheetBehavior.isFitToContents = false

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        setClickTips()
        binding.clearToText.setOnClickListener {
            binding.editTextToBottomDialog.setText("")
        }
        binding.editTextToBottomDialog.setOnKeyListener(
            View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
                ) {

                    editDeleteFocus()

                    return@OnKeyListener true

                }
                false
            }
        )
        binding.recommendationsList.adapter = adapterRecommendations
        binding.recommendationsList.layoutManager = MyLinearLayoutManager(view.context)
        binding.editTextFromBottomDialog.setText(viewModel.routFrom)

        AppCompatResources.getDrawable(context!!, R.drawable.decoration_list_recommedation)?.let {
            binding.recommendationsList.addItemDecoration(
                CustomItemDecorationVertical(
                    it
                )
            )
        }

    }

    private fun endEdit(text: String) {
        binding.editTextToBottomDialog.clearFocus()
        viewModel.setRoutTo(text)
        binding.editTextToBottomDialog.setText(viewModel.routTo)
        if (text != "") {
            lifecycleScope.launch {
                viewModel.refreshSearchFragment()
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                findNavController().navigate(R.id.action_airTicketFragment_to_searchFragment)
            }

        }
    }


    private fun setClickTips() {
        binding.route.setOnClickListener {
            findNavController().navigate(R.id.action_airTicketFragment_to_difficultRouteFragment)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        }
        binding.anywhere.setOnClickListener {

            val baseRes = resources
            val config = Configuration(baseRes.configuration)
            config.locale = Locale("ru")
            val localRes = Resources(baseRes.assets, baseRes.displayMetrics, config)
            val text = localRes.getString(R.string.anywhere_text)

            endEdit(text)
        }
        binding.weekend.setOnClickListener {
            findNavController().navigate(R.id.action_airTicketFragment_to_difficultRouteFragment)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.hotTickets.setOnClickListener {
            findNavController().navigate(R.id.action_airTicketFragment_to_difficultRouteFragment)

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }


    private fun editDeleteFocus() {
        val inputMethodManager =
            context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager


        inputMethodManager.hideSoftInputFromWindow(
            binding.editTextToBottomDialog.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )

        endEdit(binding.editTextToBottomDialog.text.toString())


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}