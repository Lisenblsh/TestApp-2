package com.lis.testapp2.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.lis.adapter.base.BasePagingAdapter
import com.lis.domain.models.CompanyListModelItem
import com.lis.domain.tools.ImageLoader
import com.lis.testapp2.databinding.CompanyItemBinding

class CompanyPagingAdapter(
    private val idLayout: Int
) : BasePagingAdapter<CompanyListModelItem, CompanyPagingAdapter.CompanyPagingViewHolder>(
    idLayout,
    COMPANY_COMPARISON
) {
    inner class CompanyPagingViewHolder(private val binding: CompanyItemBinding) :
        BasePagingViewHolder(binding.root) {
        override fun bind(item: Any?) {
            if (item != null) {
                showData(item)
            }
        }

        override fun showData(item: Any) {
            if (item is CompanyListModelItem) {
                binding.companyTitle.text = item.name
                ImageLoader().setImage(
                    binding.companyImage,
                    "https://lifehack.studio/test_task/${item.img}"
                )
                binding.root.setOnClickListener {
                    clickListener.onItemClick(item.id)
                }
            }
        }
    }

    override fun createViewHolder(itemView: View): CompanyPagingViewHolder {
        val binding = CompanyItemBinding.bind(itemView)
        return CompanyPagingViewHolder(binding)
    }

    companion object {
        private val COMPANY_COMPARISON = object : DiffUtil.ItemCallback<CompanyListModelItem>() {
            override fun areItemsTheSame(
                oldItem: CompanyListModelItem,
                newItem: CompanyListModelItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CompanyListModelItem,
                newItem: CompanyListModelItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}