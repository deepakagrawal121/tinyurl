package com.example.demo.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.TinyUrlEntity;
import com.example.demo.service.TinyUrlService;

import lombok.Data;

@RestController
@RequestMapping(path = "/tn")
public class TinyUrlController {

	@Autowired
	private TinyUrlService tinyUrlService;
	
	@PostMapping
	public TinyUrlEntity create(@RequestBody TinyUrlRequestDto tinyUrlRequestDto) {
		return tinyUrlService.create(tinyUrlRequestDto);
	}
	
	@GetMapping(value = "/{id}")
	public void method(@PathVariable String id, HttpServletResponse httpServletResponse) {
	    httpServletResponse.setHeader("Location", tinyUrlService.get(id).getUrl());
	    httpServletResponse.setStatus(301);
	}
	
	@Data
	public static class TinyUrlRequestDto {
		String url;
	}
	
	
}
