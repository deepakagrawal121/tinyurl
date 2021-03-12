package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.validator.routines.UrlValidator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.TinyUrlEntity;
import com.example.demo.repo.TinyUrlRepo;
import com.example.demo.web.TinyUrlController.TinyUrlRequestDto;
import com.google.common.hash.Hashing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TinyUrlServiceImpl implements TinyUrlService {

	@Autowired
	private TinyUrlRepo tinyUrlRepo;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional()
	public TinyUrlEntity create(@RequestBody TinyUrlRequestDto tinyUrlRequestDto) {
		
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(tinyUrlRequestDto.getUrl())) {
            throw new RuntimeException("Invalid URL.");
        }
        
        int counter = 0;
		
        boolean unique = false;
        String id = null;
        while(!unique && counter < 10000) {
        	
        	id = getEncodedUrl(tinyUrlRequestDto.getUrl());

        	log.debug("counter: " + counter++ + " id: " + id);
        	
        	try {
            	Query query = em.createNativeQuery("insert into tiny_url (id,created_by,created_time,expiry,updated_by,updated_time,url) values (:id,:createdBy,:createdTime,:expiry,:updatedBy,:updatedTime,:url);", TinyUrlEntity.class);
                
                query.setParameter("id", id);
                query.setParameter("createdBy",null);
                query.setParameter("createdTime", new Date());
                query.setParameter("expiry",new Date());
                query.setParameter("updatedBy",null);
                query.setParameter("updatedTime",new Date());
                query.setParameter("url",tinyUrlRequestDto.getUrl());
                
                int result = query.executeUpdate();
                
                log.debug("found unique id in " + (counter-1) + " retries");
                
                unique = (result == 1);
                
            }
            catch(PersistenceException e) {
            	if(!(e.getCause() instanceof ConstraintViolationException)) {
            		throw e;
            	}
            }
        }
        
		return new TinyUrlEntity(id);
	}
	
	private static String getEncodedUrl(String url) {
		//TODO: replace current millis with userId
		return Hashing.murmur3_32((int)System.currentTimeMillis()).hashString(url, StandardCharsets.UTF_8).toString();
	}
	
	@Override
	public TinyUrlEntity get(String id) {
		return tinyUrlRepo.findById(id).orElseThrow();
	}
	
}
