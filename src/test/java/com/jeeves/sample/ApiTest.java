package com.jeeves.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.jeeves.sample.controller.AppController;
import com.jeeves.sample.entity.Lead;
import com.jeeves.sample.entity.LeadDTO;
import com.jeeves.sample.enums.LocationTypeEnum;
import com.jeeves.sample.enums.StatusEnum;
import com.jeeves.sample.repository.LeadRepository;
import com.jeeves.sample.service.LeadService;


@RunWith(MockitoJUnitRunner.class)
public class ApiTest {


	@InjectMocks
	LeadService leadService;
	
	@InjectMocks
	AppController appController;

	@Mock
	LeadRepository leadRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getLeadIdTest()
	{
		when(leadRepository.findById(1L)).thenReturn(Optional.of(new Lead(1L, "Rahul", "Singh", 9986182012L, "rahul@gmail.com",
				LocationTypeEnum.City, "India", StatusEnum.Created,"Good"))); 

		LeadDTO lead = leadService.getLeadDetails(1L);
		assertEquals("Rahul", lead.getFirst_name());
	}

	@Test
	public void checkForNullLead() 
	{		
		LeadDTO lead = leadService.getLeadDetails(Long.MIN_VALUE);
		assertNull(lead);
		// Comment out leadOpt.isPresent() to cover the exception part
		LeadDTO lead1 = leadService.getLeadDetails(null);  
		assertNull(lead1);
	}

	@Test
	public void createLead() 
	{
		LeadDTO lead1 = new LeadDTO(2L, "Rajiv", "Singh", 9985182012L, "rajiv@gmail.com",
				LocationTypeEnum.City, "India", StatusEnum.Created,"Good");
		leadService.postLeadDetails(lead1);
		assertNotNull(lead1);

	}

}



