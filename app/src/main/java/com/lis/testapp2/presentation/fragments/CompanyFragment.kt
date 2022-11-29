package com.lis.testapp2.presentation.fragments

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lis.domain.tools.ImageLoader
import com.lis.testapp2.databinding.FragmentCompanyBinding
import com.lis.testapp2.presentation.viewModels.CompanyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class CompanyFragment : Fragment() {

    private lateinit var binding: FragmentCompanyBinding

    private val viewModel by viewModel<CompanyViewModel>()

    override fun onStart() {
        val args = CompanyFragmentArgs.fromBundle(requireArguments())
        viewModel.getCompany(args.id)

        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyBinding.inflate(inflater, container, false)
        binding.viewCompany()
        return binding.root
    }

    private fun FragmentCompanyBinding.viewCompany() {
        viewModel.companyData.observe(viewLifecycleOwner) { company ->
            Log.e("company","$company")
            ImageLoader().setImage(companyImage,"https://lifehack.studio/test_task/${company.img}")
            companyName.text= company.name
            companyPhone.text = company.phone
            companyWeb.text = company.www
            companyDescription.text = company.description

            val addresses: List<Address>
            val geocoder = Geocoder(requireContext(), Locale.getDefault())

            addresses = geocoder.getFromLocation(
                company.lat,
                company.lon,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            val address: String =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            val city: String = addresses[0].locality
            val state: String = addresses[0].adminArea
            val country: String = addresses[0].countryName
            val postalCode: String = addresses[0].postalCode
            val knownName: String =
                addresses[0].featureName // Only if available else return NULL

            companyAddress.text = address
            companyAddress.setOnClickListener {
                val uri = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", company.lat, company.lon)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                requireContext().startActivity(intent)
            }

        }
    }
}
