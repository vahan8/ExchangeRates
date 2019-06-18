package com.vahan.exchangerates.ui.bankinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vahan.exchangerates.data.repository.BankBranchesRepository
import com.vahan.exchangerates.domain.model.BankBranch
import com.vahan.exchangerates.domain.model.Result

class BankBranchesViewModel(val organizationId: String) : ViewModel() {
    private val bankBranchesObservable: MutableLiveData<List<BankBranch>> = MutableLiveData()

    fun getBankBranchesObservable(): LiveData<List<BankBranch>> {
        return bankBranchesObservable
    }

    fun loadData() {
        BankBranchesRepository.getBankBranchesAsync(organizationId) {
            when (it) {
                is Result.Success -> {
                    bankBranchesObservable.postValue(it.data)
                }
                //Error handling not done
                is Error -> {
                }
            }
        }
    }

    class Factory(private val organizationId: String) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BankBranchesViewModel(organizationId) as T
        }
    }

}