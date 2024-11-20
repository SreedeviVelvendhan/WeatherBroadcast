package com.weatherupdate.weatherappforecast.adapter

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherupdate.weatherappforecast.data.forecastModels.ForecastData
import com.weatherupdate.weatherappforecast.databinding.RvItemLayoutBinding
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RvAdapter( private val forecastArray: ArrayList<ForecastData>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    class ViewHolder(val binding : RvItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RvItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = forecastArray[position]
        holder.binding.apply {
            val imageIcon = currentItem.weather[0].icon
            val imageUrl = "https://openweathermap.org/img/w/$imageIcon.png"

            Picasso.get().load(imageUrl).into(imgItem)

            tvItemTemp.text = "${currentItem.main.temp.toInt()} °C"
            tvItemStatus.text = "${currentItem.weather[0].description}"
            //tvItemTime.text = displayTime(currentItem.dt_txt)
            val date =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentItem.dt_txt)
            val calendar = Calendar.getInstance()
            calendar.time = date
            val dayOfWeekName = when(calendar.get(Calendar.DAY_OF_WEEK)){
                1 -> "Sun"
                2 -> "Mon"
                3 -> "Tue"
                4 -> "Wed"
                5 -> "Thu"
                6 -> "Fri"
                7 -> "Sat"
                else -> "-"
            }

            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val amPm = if(hour<12) "am" else "pm"
            val hour12 = calendar.get(Calendar.HOUR)
            tvItemTime.text = dayOfWeekName + "\n"+ hour12.toString()+amPm

            //tvItemTime.text = displayTime(currentItem.dt_txt)
        }
    }

    private fun displayTime(dtTxt: String): CharSequence? {
        val input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val output = DateTimeFormatter.ofPattern("MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(dtTxt,input)
        return output.format(dateTime)

    }

    override fun getItemCount(): Int {
        return forecastArray.size
    }
}