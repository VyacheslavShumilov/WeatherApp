//package com.hfad.lesson2.view
//
//import androidx.lifecycle.ViewModelProvider
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.Observer
//import com.google.android.material.snackbar.Snackbar
//import com.hfad.lesson2.data.AppState
//import com.hfad.lesson2.viewmodel.MainViewModel
//import com.hfad.lesson2.R
//import com.hfad.lesson2.data.Weather
//import com.hfad.lesson2.databinding.FragmentMainBinding
//
////Приложение на Архитектуре MVVM!
//
//                //region Мануал применения binding во Fragment
////binding следует использовать по возможности ВСЕГДА!
////При использовании binding во ФРАГМЕНТЕ его нужно обязательно обнулить в onDestroyView() - т.е. _binding = null (см. ниже)
////...это ведет к тому, что переменная private var _binding: FragmentMainBinding? = null (должна быть var и нулабельна)
////endregion
//class MainFragment_ex : Fragment() {
//
//                //region Применение binding в фрагменте
//                //во фрагменте в методе onDestroyView нам надо его очистить, чтобы не было ссылки на уничтоженную View
//                //...в Activity это все само под капотом происходит, во Fragment это нужно делать вручную
//                //endregion
//    private var _binding: FragmentMainBinding? = null
//
//                //region 5 создаем свпомогательную переменную private val binding и вызываем функцию get()
//                // чтобы код выглядел понятнее (без операторов "?" в разных местах) создаем ненулабельную обертку binding
//                //переменная создается для удобства!
//                //метод get() это ключевое слово Kotlin
//                //переменная каждый раз при запросе будет запрашивать данные которые вернуть
//                // !! - это значит что тип не нулабельный
//                //endregion
//    private val binding get() = _binding!!
//
//    companion object {
//        fun newInstance() = MainFragment_ex()
//    }
//
//    private lateinit var viewModel: MainViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//                            //7
//        _binding = FragmentMainBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//                    //region 4 Этапы работы функции onViewCreated()
//                    //Когда отработает функция жизненного цикла onViewCreated
//                    //Создается viewModel (создание отложено т.к. private lateinit var viewModel - см. выше)
//                    //Создается observer
//                    //Подписка на изменение liveData
//                    //endregion
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {       //замена onActivityCreated на эту функцию
//        super.onViewCreated(view, savedInstanceState)
//
//                    //region 1обычно получение ViewModel в Activity выглядит так
//                    // Отложенное создание viewModel
//                    // endregion
//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
//
//                    //region 2 Подписка на изменения LiveData из Activity выглядит так (п.1 см. в MainViewModel)
//                    //2_чтобы подписаться на liveData нужен подписчик/наблюдатель Observer, он при изменении будет что-то делать
//                    //обязательно проверить в import androidx.lifecycle.Observer !, иначе не будет работать!
//                    //Observer<Any> - потому что liveData возвращает Any
//
//                    //Когда в observer придут данные, он вызовет renderData() и она отобразит Toast
//                    //endregion
//        val observer = Observer<AppState> {
//            renderData(it)
//        }
//                    //region 3 Подписка на изменение liveData
//                    //3_observer нужно связать с liveData. liveData получаем из ViewModel функцией getLiveData()
//                    //...и подписываемся на нее функцией observe. в observe нужно передать того кто знает о жизненном цикле View и observer...
//                    //...viewLifecycleOwner - поле, из Fragment (указано по умолчанию)
//                    //endregion
//        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
//        //viewModel.getWeatherFromLocalSource()  //просто перестал рабоать после правки остального кода
//
//    //region код до разработки приложения погоды
//    //                    //8 берем TextView с id "message" и кладу в нее текст "Text"
//    //        binding.message.text = "Text"
//    //endregion
//    }
//
//    private fun renderData(appState: AppState) {
//                    //работа с Appstate
//        when(appState) {
//            is AppState.Success -> {
//                showLoading(false)
//                //val weatherData = appState.weather      ////просто перестал рабоать после правки остального кода
//                //setData(weatherData)
//
//                //region Для оптимизации кода чать перенесена в отдельную функцию showLoading
//                //если состояние Success, сначала скрываем loadingLayout
//                //binding.loadingLayout.visibility = View.GONE
//                //затем отобразить главную View
//                //binding.mainView.isVisible = true
//                //endregion
//            }
//            is AppState.Loading -> {
//                showLoading(true)
//
//                //region для оптимизации кода чать перенесена в отдельную функцию showLoading
//                // Было
//                //если состояние loading, сначала скрываем loadingLayout
//                //binding.loadingLayout.isVisible = true     //visibility = View.GONE тоже самое, что isVisible = false
//                //затем скрыть главную View
//                //binding.mainView.isVisible = false
//                //endregion
//
//            }
//            is AppState.Error -> {
//                showLoading(false)
//            ////просто перестал рабоать после правки остального кода
//            //binding.loadingLayout.visibility = View.GONE
//                //binding.mainView.isVisible = true
//                //Snackbar.make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
//                //    .setAction("reload") {viewModel.getWeatherFromLocalSource()}
//                //    .show()
//
//
//            }
//        }
//    }
//
////                    private fun setData(weatherData: Weather) {
////                        binding.cityName.text = weatherData.city.city
////                        binding.cityCoordinates.text = String.format(
////                            getString(R.string.city_coordinates),
////                            weatherData.city.lat.toString(),
////                            weatherData.city.lon.toString()
////                        )
////                        binding.temperatureValue.text = weatherData.temperature.toString()
////                        binding.feelsLikeValue.text = weatherData.feelsLike.toString()
////                    }
//
//
////                    private fun showLoading(isShow: Boolean) {
////         //если состояние loading, сначала скрываем loadingLayout
////         binding.loadingLayout.isVisible = isShow     //visibility = View.GONE тоже самое, что isVisible = false
////         //затем скрыть главную View
////         binding.mainView.isVisible = !isShow
//     }
//
////                    //6 Обязательно! переропределение onDestroyView
////    override fun onDestroyView() {
////        super.onDestroyView()
////        _binding = null
////    }
////
////}