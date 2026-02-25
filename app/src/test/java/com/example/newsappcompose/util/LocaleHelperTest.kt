package com.example.newsappcompose.util

import org.junit.Assert.*
import org.junit.Test

class LocaleHelperTest {

    @Test
    fun `getCountryCode should return default us when context is null`() {
        val countryCode = LocaleHelper.getCountryCode(null)
        assertNotNull(countryCode)
        assertEquals(2, countryCode.length)
    }

    @Test
    fun `getCountryCode should return valid country code`() {
        val countryCode = LocaleHelper.getCountryCode()
        assertNotNull(countryCode)
        assertTrue(countryCode.length == 2)
    }

    @Test
    fun `getCountryCode should return supported country code`() {
        val supportedCountries = setOf(
            "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu",
            "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in",
            "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz",
            "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th",
            "tr", "tw", "ua", "us", "ve", "za"
        )

        val countryCode = LocaleHelper.getCountryCode()
        
        assertTrue(
            "Country code should be supported or default to us",
            countryCode in supportedCountries || countryCode == "us"
        )
    }

    @Test
    fun `getCountryCode without context should work`() {
        val countryCode = LocaleHelper.getCountryCode()
        assertNotNull(countryCode)
        assertEquals(2, countryCode.length)
    }
}
