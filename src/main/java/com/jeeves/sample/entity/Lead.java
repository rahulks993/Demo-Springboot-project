package com.jeeves.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeves.sample.enums.LocationTypeEnum;
import com.jeeves.sample.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="Lead")
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id",nullable= false)
	private  Long id;

	@Column(nullable= false)
	private String first_name;

	@Column(nullable= false)
	private String last_name;

	@Column(length =10, columnDefinition = "BIGINT", nullable = false, unique = true )
	private Long mobile;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private LocationTypeEnum location_type;

	@Column(nullable = false)
	private String location_string;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private StatusEnum status;
	
	@JsonIgnore
	private String communication;

}
