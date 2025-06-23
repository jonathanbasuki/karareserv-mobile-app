package com.pemrograman_platform.karareserve.utils

import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(value: String): String {
    val amount = value.toDoubleOrNull() ?: return "Rp0"
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.maximumFractionDigits = 0

    return formatter.format(amount)
}