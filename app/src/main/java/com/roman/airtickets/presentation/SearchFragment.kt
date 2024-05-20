package com.roman.airtickets.presentation

import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.roman.airtickets.R
import com.roman.airtickets.databinding.CalendarBinding
import com.roman.airtickets.databinding.FragmentSearchBinding
import com.roman.airtickets.entity.TicketRecommendations
import com.roman.airtickets.presentation.adapters.DirectFlightsAdapter
import com.roman.airtickets.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter = DirectFlightsAdapter()
    private var tickets: List<TicketRecommendations> = emptyList()
    private val timeNow = Calendar.getInstance().time.time
    private var calendarThere = timeNow
    private var calendarHere: Long = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateSearchTickets.collect {
                when (it) {
                    StateLoading.Loading -> {}
                    StateLoading.Loaded -> {
                        setDateFromViewModel()
                        setAdapter()
                        setInput()
                        iconListener()
                        setListeners()

                    }
                    is StateLoading.Error -> {}
                }
            }
        }


    }

    private fun setListeners() {
        binding.backImage.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.dateChip.setOnClickListener {
            openCalendar(TypeDate.THERE)
        }
        binding.hereChip.setOnClickListener {
            if (calendarHere != 0L) clearCalendarHere()
            else openCalendar(TypeDate.HERE)
        }
        binding.buttonViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_ticketsAllFragment)
        }
    }

    private fun setDateFromViewModel() {
        if (viewModel.dateFrom != 0L) calendarThere = viewModel.dateFrom
        if (viewModel.dateTo != 0L) calendarHere = viewModel.dateTo

        setDateThere(calendarThere, TypeDate.THERE)
        setDateThere(calendarHere, TypeDate.HERE)
    }

    private fun setDateThere(calendar: Long, type: TypeDate) {

        val formatDate = SimpleDateFormat("d MMM, EE", Locale.getDefault())
        val date = formatDate.format(calendar).replace(".", "")

        val text = SpannableStringBuilder(date)
        val bias = if (Locale.getDefault().language == "en") 5 else 4
        val style = ForegroundColorSpan(context!!.getColor(R.color.grey_6))
        text.setSpan(style, date.count() - bias, date.count(), Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        when (type) {
            TypeDate.THERE -> {
                calendarThere = calendar
                binding.dateChip.text = text
                viewModel.setDateFrom(calendar)
                calendarHere.let {
                    if (calendar > it && it != 0L) clearCalendarHereError()
                }
            }
            TypeDate.HERE -> {
                if (calendarThere <= calendar) {
                    calendarHere = calendar
                    binding.hereChip.text = text
                    binding.hereChip.setChipIconResource(R.drawable.close_chip_icon)
                    viewModel.setDateTo(calendar)

                } else
                    if (calendar == 0L) {
                        clearCalendarHere()
                    } else {
                        clearCalendarHereError()
                    }

            }

        }
    }

    private fun clearCalendarHere() {
        calendarHere = 0L
        viewModel.setDateTo(0L)
        binding.hereChip.text = getString(R.string.plus_here)
        binding.hereChip.setChipIconResource(R.drawable.plus_icon)

    }

    private fun clearCalendarHereError() {
        clearCalendarHere()
        val snack =
            Snackbar.make(binding.root, getString(R.string.here_error), Snackbar.LENGTH_LONG)
        snack.show()

    }

    private fun setAdapter() {
        tickets = viewModel.tickets
        val tickets2 = List(30) { tickets[0] }
        adapter.setData(tickets)
        binding.recommendationsList.adapter = adapter
        AppCompatResources.getDrawable(context!!, R.drawable.decoration_list_recommedation)?.let {
            binding.recommendationsList.addItemDecoration(
                CustomItemDecorationVertical(
                    it
                )
            )
        }


    }

    private fun openCalendar(type: TypeDate) {
        var time: Long = when (type) {
            TypeDate.THERE -> {
                calendarThere
            }
            TypeDate.HERE -> {
                if (calendarHere == 0L) {
                    Calendar.getInstance().time.time
                } else {
                    calendarHere
                }

            }
        }
        val dialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        dialog.setCancelable(true)
        val bindingCalendar = CalendarBinding.inflate(layoutInflater, binding.root, false)
        bindingCalendar.calendarView.minDate = timeNow
        bindingCalendar.calendarView.setDate(time, true, true)
        bindingCalendar.buttonCancel.setOnClickListener {
            dialog.cancel()
        }
        bindingCalendar.calendarView.setOnDateChangeListener { view, i, i2, i3 ->
            view.date = Calendar.getInstance()
                .apply {
                    set(i, i2, i3)
                }.timeInMillis

        }
        bindingCalendar.buttonChoose.setOnClickListener {
            time = bindingCalendar.calendarView.date
            setDateThere(time, type)


            dialog.cancel()
        }
        dialog.setContentView(bindingCalendar.root)
        dialog.show()
    }

    private fun setInput() {
        binding.editTextFromSearchFragment.setText(viewModel.routFrom)
        binding.editTextToSearchFragment.setText(viewModel.routTo)

    }

    private fun iconListener() {
        binding.replaceIcon.setOnClickListener {
            viewModel.replace()
            Log.d("1111", viewModel.routTo)
            setInput()
        }
        binding.clearIcon.setOnClickListener {
            viewModel.setRoutTo()
            setInput()
        }

    }

    private companion object {
        enum class TypeDate {
            THERE,
            HERE
        }
    }


}