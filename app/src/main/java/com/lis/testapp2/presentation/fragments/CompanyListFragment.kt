package com.lis.testapp2.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lis.adapter.base.BasePagingAdapter
import com.lis.testapp2.R
import com.lis.testapp2.databinding.FragmentCompanyListBinding
import com.lis.testapp2.presentation.adapters.CompanyPagingAdapter
import com.lis.testapp2.presentation.viewModels.CompanyListViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

import org.koin.androidx.viewmodel.ext.android.viewModel


class CompanyListFragment : Fragment() {

    private lateinit var binding: FragmentCompanyListBinding

    private val viewModel by viewModel<CompanyListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!this::binding.isInitialized){
            binding = FragmentCompanyListBinding.inflate(inflater, container, false)
            binding.viewList()
        }
        return binding.root
    }

    private fun FragmentCompanyListBinding.viewList() {
        val adapter = CompanyPagingAdapter(R.layout.company_item)
        adapter.setOnItemClickListener(object : BasePagingAdapter.OnItemClickListener{
            override fun onItemClick(id: String) {
                val directions = CompanyListFragmentDirections.actionCompanyListFragmentToCompanyFragment(id)
                NavHostFragment.findNavController(this@CompanyListFragment).navigate(directions)
            }

            override fun onButtonOnItemClick(id: String) {}

        })
        companyList.adapter = adapter
        companyList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            viewModel.companyPagingData.collectLatest(adapter::submitData)
        }
    }
}
