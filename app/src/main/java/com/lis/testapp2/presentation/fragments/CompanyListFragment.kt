package com.lis.testapp2.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lis.adapter.base.BasePagingAdapter
import com.lis.testapp2.R
import com.lis.testapp2.databinding.FragmentCompanyListBinding
import com.lis.testapp2.presentation.adapters.CompanyPagingAdapter
import com.lis.testapp2.presentation.viewModels.CompanyListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
            binding.bindSwipeRefresh()
        }
        return binding.root
    }

    private val companyAdapter = CompanyPagingAdapter(R.layout.company_item)

    private fun FragmentCompanyListBinding.bindSwipeRefresh() {
        lifecycleScope.launch {
            companyAdapter.loadStateFlow.collectLatest { loadState ->
                if (loadState.refresh is LoadState.NotLoading) {
                    val position =
                        (companyList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (position == 0) {
                        companyList.scrollToPosition(0)
                    }
                    root.isRefreshing = false
                }
            }
        }
        root.setOnRefreshListener {
            companyAdapter.refresh()
        }
    }

    private fun FragmentCompanyListBinding.viewList() {
        companyAdapter.setOnItemClickListener(object : BasePagingAdapter.OnItemClickListener{
            override fun onItemClick(id: String) {
                val directions = CompanyListFragmentDirections.actionCompanyListFragmentToCompanyFragment(id)
                NavHostFragment.findNavController(this@CompanyListFragment).navigate(directions)
            }

            override fun onButtonOnItemClick(id: String) {}

        })
        companyList.adapter = companyAdapter
        companyList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            viewModel.companyPagingData.collectLatest(companyAdapter::submitData)
        }
    }
}
