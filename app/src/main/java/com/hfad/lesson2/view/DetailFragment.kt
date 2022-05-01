package com.hfad.lesson2.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.hfad.lesson2.R
import com.hfad.lesson2.data.Weather
import com.hfad.lesson2.data.WeatherDTO
import com.hfad.lesson2.data.WeatherLoader
import com.hfad.lesson2.databinding.FragmentDetailBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather
    private val onLoadListener: WeatherLoader.WeatherLoaderListener =
        object : WeatherLoader.WeatherLoaderListener {

            override fun onLoaded(weatherDTO: WeatherDTO) {
                displayWeather(weatherDTO)
            }

            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()

        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        val loader = WeatherLoader(onLoadListener, weatherBundle.city.lat, weatherBundle.city.lon)
        loader.loadWeather()
    }

    private fun displayWeather(weatherDTO: WeatherDTO) {
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            val city = weatherBundle.city
            cityName.text = city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            weatherCondition.text = weatherDTO.fact?.condition
            temperatureValue.text = weatherDTO.fact?.temp.toString()
            feelsLikeValue.text = weatherDTO.fact?.feelsLike.toString()
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}


////загрузка данных во фрагменте - это  корне неправлильно! Должно быть в ViewModel
////Фрагмент - это View и он должен заниматься только отображением данных
//
//class DetailsFragment : Fragment() {
//
//    private var _binding: FragmentDetailBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var weatherBundle: Weather
//
//    //реализация LoadListener
//    private val loadListener = object : WeatherLoader.WeatherLoaderListener {
//            override fun onLoaded(weatherDTO: WeatherDTO) {
//                displayWeather(weatherDTO)
//            }
//
//            override fun onFailed(throwable: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentDetailBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //хочу получить экземпляр погоды из аргументов, которые туда передал (из аргументов считаю погоду
//        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
//            //делаю доступ к объекту везде
//            weatherBundle = weather
//        }
//        binding.mainView.visibility = View.GONE
//        binding.loadingLayout.visibility = View.VISIBLE
//        loadWeather()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun loadWeather() {
//        val weatherLoader =
//            WeatherLoader(loadListener, weatherBundle.city.lat, weatherBundle.city.lon)
//        weatherLoader.loadWeather()
//    }
//
//    private fun displayWeather(weatherDTO: WeatherDTO) {
//        with(binding) {
//            //делаю видимой основную mainView
//            mainView.visibility = View.VISIBLE
//            //скрываю loadingLayout
//            loadingLayout.visibility = View.GONE
//            //получаю city из локального хранилища
//            val city = weatherBundle.city
//            cityName.text = city.city
//            //добавляю координаты
//            cityCoordinates.text = String.format(
//                getString(R.string.city_coordinates),
//                city.lat.toString(),
//                city.lon.toString()
//            )
//            //из бэка получаю condition, temp, feels_like
//            weatherCondition.text = weatherDTO.fact?.condition
//            temperatureValue.text = weatherDTO.fact?.temp.toString()
//            feelsLikeValue.text = weatherDTO.fact?.feelsLike.toString()
//        }
//    }
//
//
//
//    companion object {
//        const val BUNDLE_EXTRA = "weather"
//        fun newInstance(bundle: Bundle): DetailsFragment {
//            val fragment = DetailsFragment()
//            fragment.arguments = bundle
//            return fragment
//        }
//    }
//}

