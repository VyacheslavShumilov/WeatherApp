package com.hfad.lesson2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hfad.lesson2.R
import com.hfad.lesson2.data.Weather

        //этот адаптер расставляет данные по RecyclerView
        //Адаптер Типизирован MainViewHolder (который создан здесь, см. ниже)

class MainFragmentAdapter (private var onItemViewClickListener: MainFragment.OnItemViewClickListener?): RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

                //поле в котором хранится список погоды
    private var weatherData: List<Weather> = listOf()

                //функция для установки списка в адаптер
    fun setWeather(data: List<Weather>) {
        weatherData = data
                //функция для перерисовки списка
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city, parent, false) as View     //указываем элемент, который должен отображаться в списке
        )
    }

    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount() = weatherData.size


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                    //заполнение элемента списком
        fun bind(weather: Weather) {
                        //ищу TextView, которую указал в layout "item_city" ее id и устанавливаю ей текст "text = weather.city.city" моего элемента погоды
            itemView.apply {
                findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.city
                //вешаю на него setOnClickListener и просто отображаю Toast  в котором буду выводить название города
                setOnClickListener {
                    onItemViewClickListener?.onItemClick(weather)
                }
            }
        }
    }
}
