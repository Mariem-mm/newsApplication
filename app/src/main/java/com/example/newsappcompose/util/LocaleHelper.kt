package com.example.newsappcompose.util

import android.content.Context
import java.util.Locale

object LocaleHelper {
    
    fun getCountryCode(context: Context? = null): String {
        val locale = context?.resources?.configuration?.locales?.get(0) 
            ?: Locale.getDefault()
        
        val countryCode = locale.country.lowercase()
        
        val supportedCountries = setOf(
            "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu",
            "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in",
            "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz",
            "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th",
            "tr", "tw", "ua", "us", "ve", "za"
        )
        
        return if (countryCode in supportedCountries) {
            countryCode
        } else {
            "us"
        }
    }
    
    fun getCountryCode(): String {
        return getCountryCode(null)
    }
}
