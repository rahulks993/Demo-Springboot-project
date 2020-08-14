package com.jeeves.sample.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeeves.sample.enums.LocationTypeEnum;
import com.jeeves.sample.enums.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeadDTO 
{
	
	private Long id;
	private String first_name;
	private String last_name;
	private Long mobile;
	private String email;
	private LocationTypeEnum location_type;
	private String location_string;
	private StatusEnum status;
	private String communication;

}
