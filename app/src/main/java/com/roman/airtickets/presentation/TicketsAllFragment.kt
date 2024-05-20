package com.roman.airtickets.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.roman.airtickets.R
import com.roman.airtickets.databinding.FragmentTicketsAllBinding
import com.roman.airtickets.entity.TicketsInformFull
import com.roman.airtickets.presentation.adapters.TicketsAllAdapter
import com.roman.airtickets.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketsAllFragment : Fragment() {

    private var _binding: FragmentTicketsAllBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var tickets: List<TicketsInformFull> = emptyList()
    private val adapter = TicketsAllAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTicketsAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateAllTickets.collect{
                when(it){
                    StateLoading.Loading->{
                                    //Loader
                    }
                    StateLoading.Loaded->{
                        setHeader()
                        tickets = viewModel.getTicketInformFull().tickets
                        adapter.setData(tickets)
                        binding.fullInformTicketList.adapter = adapter
                        AppCompatResources.getDrawable(context!!, R.drawable.full_tickets_decoration)?.let {draw->
                            binding.fullInformTicketList.addItemDecoration(
                                CustomItemDecorationVertical(
                                    draw
                                )
                            )
                        }

                    }
                    is StateLoading.Error->{}
                }


            }
        }

    }

    private fun setHeader() {

        val textFromTo = viewModel.routFrom + "–" + viewModel.routTo
        val countPeople = viewModel.countPeople
        val dateFrom = viewModel.getFromDayAndMouth()
        val getTo = viewModel.getToDayAndMouth()
        val dateTo = if (getTo != "") ", назад:" + getTo else ""
        val textBottom = dateFrom + ", " + countPeople + " пассажир" + dateTo

        binding.townFromTownTo.text = textFromTo
        binding.infoText.text = textBottom

        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }


}