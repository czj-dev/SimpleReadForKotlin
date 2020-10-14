package com.rank.basiclib.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/12
 *     desc  :
 * </pre>
 */

@ExperimentalContracts
fun Boolean?.isNullOrEmpty(): Boolean {
    contract {
        returns(false) implies (this@isNullOrEmpty != null)
    }

    return this == null || this
}