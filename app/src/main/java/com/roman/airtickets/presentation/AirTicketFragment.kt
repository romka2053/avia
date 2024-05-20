package com.roman.airtickets.presentation

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.roman.airtickets.R
import com.roman.airtickets.databinding.FragmentAirTicketBinding
import com.roman.airtickets.presentation.adapters.MusicFlyAdapter
import com.roman.airtickets.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AirTicketFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private val adapterMusicFly = MusicFlyAdapter()
    private var _binding: FragmentAirTicketBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAirTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.editTextFrom.setText(viewModel.routFrom)
        lifecycleScope.launch {
            val musicFly = viewModel.getMusicFly()
            adapterMusicFly.setData(musicFly)
            binding.musicFlyList.adapter = adapterMusicFly
            AppCompatResources.getDrawable(
                context!!,
                R.drawable.decoration_list
            )?.let {
                binding.musicFlyList.addItemDecoration(
                    CustomItemDecorationHorizontal(

                        it
                    )
                )
            }

            binding.editTextFrom.setOnKeyListener(
                View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
                    ) {
                        editDeleteFocus()

                        return@OnKeyListener true

                    }
                    false
                }
            )

            binding.editTextTo.setOnClickListener {
                clickEditTo()
            }


        }
    }

    private fun clickEditTo() {
        val textFrom = binding.editTextFrom.text.toString()
        if (textFrom != "") {
            viewModel.setRoutFrom(textFrom)
            MyBottomSheetFragment().show(parentFragmentManager, "bottomSheet")
            val sharedPreferences=activity!!.getSharedPreferences(MainActivity.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor=sharedPreferences.edit()
            editor.putString(MainActivity.SHARED_PREFERENCE_TOWN,textFrom).apply()
        } else {
            binding.editTextFrom.error = getString(R.string.input_from)
        }

    }

    private fun editDeleteFocus() {
        val inputMethodManager =
            activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity!!.currentFocus != null) if (activity!!.currentFocus!!
                .windowToken != null
        )
            inputMethodManager.hideSoftInputFromWindow(
                activity!!.currentFocus!!.windowToken, 0
            )
        binding.editTextFrom.clearFocus()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}