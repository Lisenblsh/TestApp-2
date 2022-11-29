package com.lis.testapp2.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.HttpException
import com.lis.domain.converters.ResponseToModel
import com.lis.domain.models.CompanyModelItem
import kotlinx.coroutines.launch

class CompanyViewModel(
    private val responseToModel: ResponseToModel,
) : ViewModel() {

    val companyData: MutableLiveData<CompanyModelItem> = MutableLiveData()

    val error: MutableLiveData<Exception> = MutableLiveData()

    fun getCompany(id: String) = viewModelScope.launch {
        try {
            companyData.value = responseToModel.getCompanyData(id)

        } catch (e: HttpException) {
            error.value = e
        } catch (e: IllegalStateException) {
            error.value = e
        } catch(e: Exception){
            error.value = e
        }
    }
}