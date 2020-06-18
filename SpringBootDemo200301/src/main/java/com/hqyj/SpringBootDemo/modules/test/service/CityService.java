package com.hqyj.SpringBootDemo.modules.test.service;

import java.util.List;


import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;
import com.hqyj.SpringBootDemo.modules.test.entity.City;

public interface CityService {

	List<City> getCitiesByCountryId(int countryId);
	
	City getCityByName(String cityName, String localCityName);
	
	PageInfo<City> getCitiesByPage(int currentPage, int pageSize, int countryId);
	
	PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);
	
	Result<City> insetCity(City city);
	
	Result<City> updateCity(City city);
	
	Result<Object> deleteCity(int cityId);
	
	Object migrateCitiesByCountryId(int countryId);
}
