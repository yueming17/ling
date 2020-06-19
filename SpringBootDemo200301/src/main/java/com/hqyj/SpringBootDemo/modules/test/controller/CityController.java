package com.hqyj.SpringBootDemo.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.hqyj.SpringBootDemo.modules.common.vo.Result;
import com.hqyj.SpringBootDemo.modules.common.vo.SearchVo;
import com.hqyj.SpringBootDemo.modules.test.entity.City;
import com.hqyj.SpringBootDemo.modules.test.service.CityService;

@RestController
@RequestMapping("/api")
public class CityController {
	
	@Autowired
	private CityService cityService;

	/**
	 * 127.0.0.1/api/cities/522 ---- get
	 */
	@RequestMapping("/cities/{countryId}")
	public List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return cityService.getCitiesByCountryId(countryId);
	}
	
	/**
	 * 127.0.0.1/api/city?cityName=Shanghai&localCityName=1111 ---- get
	 */
	@RequestMapping("/city")
	public City getCityByName(@RequestParam String cityName, @RequestParam(required = false) String localCityName) {
		return cityService.getCityByName(cityName, localCityName);
	}
	
	/**
	 * 127.0.0.1/api/cities?currentPage=1&pageSize=5&countryId=522
	 */
	@RequestMapping("/cities")
	public PageInfo<City> getCitiesByPage(@RequestParam int currentPage, 
			@RequestParam int pageSize, @RequestParam int countryId) {
		return cityService.getCitiesByPage(currentPage, pageSize, countryId);
	}
	
	/**
	 * 127.0.0.1/api/cities ---- post
	 */
//	@RequestMapping(value = "/cities", method = RequestMethod.POST, consumes = "apllication/json")
	@PostMapping(value = "/cities", consumes = "application/json")
	public PageInfo<City> getCitiesBySearchVo(@RequestBody SearchVo searchVo) {
		return cityService.getCitiesBySearchVo(searchVo);
	}
	
	/**
	 * 127.0.0.1/api/city ---- post
	 */
	@PostMapping(value = "/city", consumes = "application/json")
	public Result<City> insetCity(@RequestBody City city) {
		return cityService.insetCity(city);
	}
	
	/**
	 * 127.0.0.1/api/city ---- put
	 */
	@PutMapping(value = "/city", consumes = "application/x-www-form-urlencoded")
	public Result<City> updateCity(@ModelAttribute City city) {
		return cityService.updateCity(city);
	}
	
	/**
	 * 127.0.0.1/api/city/2259---- delete
	 */
	@DeleteMapping("/city/{cityId}")
	public Result<Object> deleteCity(@PathVariable int cityId) {
		return cityService.deleteCity(cityId);
	}
	
	/**
	 * http://127.0.0.1/api/redis/cities/522 ---- get
	 */
	@RequestMapping("/redis/cities/{countryId}")
	public Object migrateCitiesByCountryId(@PathVariable int countryId) {
		return cityService.migrateCitiesByCountryId(countryId);
	}
}
