package com.lis.testapp2.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.lis.domain.HttpException
import com.lis.domain.converters.ResponseToModel
import com.lis.domain.models.CompanyModelItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CompanyViewModel(
    private val responseToModel: ResponseToModel,
) : ViewModel() {

    val companyData: MutableLiveData<CompanyModelItem> = MutableLiveData()

    fun getCompany(id: String) = viewModelScope.launch {
        try {
            companyData.value = responseToModel.getCompanyData(id)

            Log.e("idNull", id)
        } catch (e: HttpException) {

        } catch (e: IllegalStateException) {

        }
    }
}