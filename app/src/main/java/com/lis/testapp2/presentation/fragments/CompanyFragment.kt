package com.lis.testapp2.presentation.fragments

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lis.domain.tools.ImageLoader
import com.lis.domain.tools.addUrlToPath
import com.lis.testapp2.databinding.FragmentCompanyBinding
import com.lis.testapp2.presentation.viewModels.CompanyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CompanyFragment : Fragment() {

    private lateinit var binding: FragmentCompanyBinding

    private val viewModel by viewModel<CompanyViewModel>()

    private var id: String? = null

    override fun onStart() {
        val args = CompanyFragmentArgs.fromBundle(requireArguments())
        id = args.id
        viewModel.getCompany(id!!)
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyBinding.inflate(inflater, container, false)
        binding.viewCompany()
        viewError()
        binding.bindSwipeRefresh()
        return binding.root
    }

    private fun FragmentCompanyBinding.bindSwipeRefresh() {
        root.setOnRefreshListener {
            if (id != null) {
                viewModel.getCompany(id!!)
            }
            root.isRefreshing = false
        }
    }

    private fun FragmentCompanyBinding.viewCompany() {
        viewModel.companyData.observe(viewLifecycleOwner) { company ->
            ImageLoader().setImage(companyImage, company.img.addUrlToPath())
            companyName.text = company.name
            companyPhone.text = company.phone
            companyWeb.text = company.www
            companyDescription.text = company.description

            val (address, visible) = getAddress(company.lat, company.lon)
            companyAddress.text = address
            if (visible) {
                companyAddress.setOnClickListener {
                    openMap(company.lat, company.lon)
                }
            }
        }
    }

    private fun viewError() {
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openMap(lat: Double, lon: Double) {
        val uri =
            java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", lat, lon)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        requireContext().startActivity(intent)
    }

    private fun getAddress(lat: Double, lon: Double): Pair<String, Boolean> {
        return if (lat != 0.0 && lon != 0.0) {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())

            val geoCoderAddresses = geocoder.getFromLocation(lat, lon, 1)

            val address = geoCoderAddresses[0].getAddressLine(0)
            Pair(address, true)
        } else Pair("", false)

    }
}
