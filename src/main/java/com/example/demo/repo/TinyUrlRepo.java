package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.TinyUrlEntity;

@Repository
public interface TinyUrlRepo extends JpaRepository<TinyUrlEntity, String> {

}
