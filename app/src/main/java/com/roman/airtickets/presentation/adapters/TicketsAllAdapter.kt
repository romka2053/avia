package com.roman.airtickets.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.airtickets.R
import com.roman.airtickets.databinding.TicketCardItemBinding
import com.roman.airtickets.entity.TicketsInformFull
import java.time.*
import java.time.format.DateTimeFormatter


class TicketsAllAdapter : Adapter<TicketsAllViewHolder>() {
    private var data: List<TicketsInformFull> = emptyList()
    fun setData(tickets: List<TicketsInformFull>) {
        data = tickets
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsAllViewHolder {
        val binding =
            TicketCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketsAllViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TicketsAllViewHolder, position: Int) {
        val dateDeparture = getDate(data[position].departure.date)
        val dateArrival = getDate(data[position].arrival.date)
        val difference = Duration.between(dateDeparture, dateArrival).toMillis()
        val hourTravel = (difference / (1000 * 60 * 60)) % 24
        val minutesTravel = ((difference / (1000 * 60)) % 60)
        var textHourAndMinutesRoad = hourTravel.toString() +
                holder.itemView.context.getString(R.string.hour) +
                minutesTravel.toString() +
                holder.itemView.context.getString(R.string.minutes) +
                " " +
                holder.itemView.context.getString(R.string.road)

        if (!data[position].hasTransfer) {
            textHourAndMinutesRoad += " / " + holder.itemView.context.getString(R.string.no_transfers)
        }

        with(holder) {


            val formatDate = DateTimeFormatter.ofPattern("HH:mm")
            data[position].badge?.let {
                binding.badge.visibility = View.VISIBLE
                binding.badge.text = it
            }
            binding.textPriceTickets.text = data[position].price.getValue()
            binding.timeFrom.text = dateDeparture.format(formatDate)
            binding.timeTo.text = dateArrival.format(formatDate)
            binding.travelTime.text = textHourAndMinutesRoad
            binding.airportFrom.text = data[position].departure.airport
            binding.airportTo.text = data[position].arrival.airport

        }
    }

    private fun getDate(date: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return LocalDateTime.parse(date, formatter)

    }
}

class TicketsAllViewHolder(val binding: TicketCardItemBinding) : ViewHolder(binding.root)