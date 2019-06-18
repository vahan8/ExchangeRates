package com.vahan.exchangerates.data.remote.mapper

import com.vahan.exchangerates.data.remote.model.BankBranchRemoteModel

object BankInfoMapper {
    fun toLocal(rates: Map<String, BankBranchRemoteModel>) = rates.map { BankBranchMapper.toLocal(it.value) }
}