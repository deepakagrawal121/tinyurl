package com.example.demo.service;

import com.example.demo.entity.TinyUrlEntity;
import com.example.demo.web.TinyUrlController.TinyUrlRequestDto;

public interface TinyUrlService {

	TinyUrlEntity create(TinyUrlRequestDto tinyUrlRequestDto);
	
	TinyUrlEntity get(String id);

}
