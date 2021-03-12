package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tiny_url")
public class TinyUrlEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(updatable = false, nullable = false)
	private String id;
	
	private String url;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date updatedTime;
	private String updatedBy;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createdTime;
	private String createdBy;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date expiry;
	
	public TinyUrlEntity(String id){
		this.id = id;
	}

}
