package com.example.jobsearchapp.presentation.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobsearchapp.MyApplication
import com.example.jobsearchapp.databinding.FragmentMainScreenBinding
import javax.inject.Inject

class MainScreen : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private lateinit var offersAdapter: OffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Внедрение зависимостей Dagger
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация адаптера
        offersAdapter = OffersAdapter(emptyList()) { link ->
            // Открытие ссылки в браузере
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

        binding.rcViewRec.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Установка адаптера
        binding.rcViewRec.adapter = offersAdapter
        android.util.Log.e("MyLog", "Adapter set")

        // Наблюдение за изменениями в offers
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            android.util.Log.e("MyLog", "Offers updated: ${offers.size} items")
            if (offers.isNotEmpty()) {
                offersAdapter.updateOffers(offers) // Обновляем список предложений
                binding.rcViewRec.visibility = View.VISIBLE
            } else {
                binding.rcViewRec.visibility = View.GONE
            }
        }

        // Загрузка данных
        viewModel.loadOffers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем binding, чтобы избежать утечек памяти
    }
}