package com.hqyj.SpringBootDemo.modules.test.service;

import com.hqyj.SpringBootDemo.modules.test.entity.Country;

public interface CountryService {

	Country getCountryByCountryId(int countryId);
	
	Country getCountryByCountryName(String countryName);
}
