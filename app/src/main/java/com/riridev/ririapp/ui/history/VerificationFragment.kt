package com.riridev.ririapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.riridev.ririapp.data.remote.response.ProcessedReportsItem
import com.riridev.ririapp.data.result.Result
import com.riridev.ririapp.databinding.FragmentVerificationBinding
import com.riridev.ririapp.ui.ViewModelFactory
import com.riridev.ririapp.ui.adapter.HistoryAdapter

class VerificationFragment : Fragment() {
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding
    private val historyViewModel: HistoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData(){
        historyViewModel.getVerifReport()
        historyViewModel.process.observe(viewLifecycleOwner){result ->
            when(result){
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    val responseData = result.data.processedReports
                    setupRecyclerView(responseData)
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView(history: List<ProcessedReportsItem>) {
        binding?.rvHistory?.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = HistoryAdapter {

        }
        adapter.submitList(history)
        binding?.rvHistory?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding?.loadingIndicator?.visibility = View.VISIBLE
            binding?.rvHistory?.visibility = View.GONE
        } else {
            binding?.loadingIndicator?.visibility = View.GONE
            binding?.rvHistory?.visibility = View.VISIBLE
        }
    }

}