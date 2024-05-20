package com.roman.airtickets.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.airtickets.R
import com.roman.airtickets.databinding.ButtonViewAllBinding
import com.roman.airtickets.databinding.TicketRecommendationsItemBinding
import com.roman.airtickets.entity.TicketRecommendations


class DirectFlightsAdapter : Adapter<ViewHolder>() {
    private lateinit var data: List<TicketRecommendations>
    private var listOpen = false
    private val mapDraw = mapOf(
        Pair(1, R.drawable.oval_orange),
        Pair(10, R.drawable.oval_blue),
        Pair(30, R.drawable.oval_white)
    )

    fun setData(tickets: List<TicketRecommendations>) {
        data = tickets
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_TICKET -> {
                val binding = TicketRecommendationsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DirectFlightsViewHolder(binding)
            }
            ITEM_TICKET_BUTTON_OPEN -> {
                val binding =
                    ButtonViewAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ButtonViewAllBindingViewHolder(binding)
            }

            else -> {
                val binding = TicketRecommendationsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DirectFlightsViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        val itemsSize = data.size
        return if (itemsSize <= MAX_COUNT) itemsSize
        else if (listOpen) itemsSize + 1
        else MAX_COUNT + 1

    }

    override fun getItemViewType(position: Int): Int {
        return if (!listOpen && position == MAX_COUNT) ITEM_TICKET_BUTTON_OPEN
        else if (position == data.size) ITEM_TICKET_BUTTON_OPEN
        else ITEM_TYPE_TICKET
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (holder) {

            is DirectFlightsViewHolder -> {
                val item = data[position]
                with(holder) {
                    binding.nameCompanyText.text = item.title
                    binding.timeTickets.text = item.timeRange.joinToString("  ")

                    val priceValue = item.price.getValue()
                    binding.textPriceRecommendations.text = priceValue
                    binding.view.background = AppCompatResources.getDrawable(
                        itemView.context, mapDraw.getOrDefault(
                            item.id,
                            R.drawable.oval_white
                        )
                    )

                }
            }
            is ButtonViewAllBindingViewHolder -> {
                holder.binding.root.setOnClickListener {

                    val text = if (listOpen) holder.itemView.context.getString(R.string.show_all)
                    else holder.itemView.context.getString(R.string.hide)
                    holder.binding.textButtonShow.text = text
                    listOpen = !listOpen
                    setData(data)
                }

            }

        }

    }

    private companion object {
        const val ITEM_TYPE_TICKET = 0
        const val ITEM_TICKET_BUTTON_OPEN = 1
        const val MAX_COUNT = 3

    }

}

class DirectFlightsViewHolder(val binding: TicketRecommendationsItemBinding) :
    ViewHolder(binding.root)

class ButtonViewAllBindingViewHolder(val binding: ButtonViewAllBinding) : ViewHolder(binding.root)