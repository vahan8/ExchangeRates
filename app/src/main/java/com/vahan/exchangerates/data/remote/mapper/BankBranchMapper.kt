package com.vahan.exchangerates.data.remote.mapper

import com.vahan.exchangerates.data.remote.model.BankBranchRemoteModel
import com.vahan.exchangerates.domain.model.BankBranch

object BankBranchMapper {
    fun toLocal(remote: BankBranchRemoteModel) = BankBranch(if(remote.head==1) true else false, remote.title.english, remote.address.english)
}