package com.jeeves.sample.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeves.sample.entity.Lead;
import com.jeeves.sample.entity.LeadDTO;
import com.jeeves.sample.enums.StatusEnum;
import com.jeeves.sample.repository.LeadRepository;

@Service
public class LeadService {

	@Autowired
	LeadRepository leadRepository;

	private final Logger log = LoggerFactory.getLogger(LeadService.class);

	public LeadDTO getLeadDetails(Long id) {

		LeadDTO leadDto = null;
		try 
		{
			log.info("[LeadService][getLeadDetails][START] : {}", id);
			Optional<Lead> leadOpt = this.leadRepository.findById(id);
			if(leadOpt.isPresent()) {
				Lead lead=  leadOpt.get();
				leadDto = new LeadDTO();
				leadDto.setId(lead.getId());
				leadDto.setEmail(lead.getEmail());
				leadDto.setFirst_name(lead.getFirst_name());
				leadDto.setLast_name(lead.getLast_name());
				leadDto.setLocation_string(lead.getLocation_string());
				leadDto.setLocation_type(lead.getLocation_type());
				leadDto.setMobile(lead.getMobile());
				leadDto.setStatus(lead.getStatus());
				leadDto.setCommunication(lead.getCommunication());
			}

		}
		catch(Exception ex) {
			log.error("Error in [LeadService][getLeadDetails]", ex);
		}
		log.info("[LeadService][getLeadDetails][END]");
		return leadDto;
	}

	public Lead postLeadDetails(LeadDTO postleaddto) {
		log.info("[LeadService][postLeadDetails][START] :{} ", postleaddto);
		Lead lead = new Lead();
		log.info("Lead Object Details: {}" , lead);
		lead.setId(lead.getId());
		lead.setEmail(postleaddto.getEmail());
		lead.setFirst_name(postleaddto.getFirst_name());
		lead.setLast_name(postleaddto.getLast_name());
		lead.setLocation_string(postleaddto.getLocation_string());
		lead.setLocation_type(postleaddto.getLocation_type());
		lead.setMobile(postleaddto.getMobile());
		lead.setStatus(StatusEnum.Created);	
		leadRepository.save(lead);
		log.info("[LeadService][postLeadDetails][END]");
		return lead;
	}

	public StatusEnum putLeadDetails(LeadDTO leaddto, Long id) {
		StatusEnum status = null;
		try {
			log.info("[LeadService][putLeadDetails][START][DTO] : {}", leaddto);
			Optional<Lead> leadOpt1 = this.leadRepository.findById(id);
			if(leadOpt1.isPresent()) 
			{
				Lead getLead  = leadOpt1.get();	
				log.info("Lead Entity Details: {}",  getLead);
				if(leaddto.getEmail() != null) getLead.setEmail(leaddto.getEmail());
				if(leaddto.getFirst_name() != null)	getLead.setFirst_name(leaddto.getFirst_name()); 
				if(leaddto.getLast_name() != null) getLead.setLast_name(leaddto.getLast_name());
				if(leaddto.getLocation_string() != null)getLead.setLocation_string(leaddto.getLocation_string());
				if(leaddto.getLocation_type() != null) getLead.setLocation_type(leaddto.getLocation_type()); 
				if(leaddto.getMobile() != null) getLead.setMobile(leaddto.getMobile());
				leadRepository.save(getLead);
				status = StatusEnum.success;
			}
		}
		catch(Exception e) {
			status = StatusEnum.failure;
			log.error("Error in [LeadService][putLeadDetails]: ", e);
		}
		log.info("[LeadService][putLeadDetails][END]");
		return status;
	}

	public String deleteDetails(Long id) {
		log.info("[LeadService][deleteDetails][START] :{}", id);
		Optional<Lead> optLead = leadRepository.findById(id);
		if(optLead.isPresent()) {
			Lead lead = optLead.get();
			leadRepository.delete(lead);
			return "success";
		}
		else {
			log.error("No related ID found in DB");
			log.info("[LeadService][deleteDetails][END]");
			return "No Id  found in DB";
		}
	}


	public List<Lead> findByMobile(long mobile) {
		return leadRepository.findByMobile(mobile);
	}

	public List<Lead> findByEmail(String email) {
		return leadRepository.findByEmail(email);
	}

	public List<Lead> findByMobileAndEmail(long mobile,
			String email) {
		return leadRepository.findByMobileAndEmail(mobile, email);
	}

	public StatusEnum updateStatus(LeadDTO leadDto , Long id) {
		
		StatusEnum status = null;
		try {
			log.info("[LeadService][updateStatus][START][DTO] : {}", id);
			Optional<Lead> leadOpt1 = this.leadRepository.findById(id);
			if(leadOpt1.isPresent()) 
			{
				Lead getLead  = leadOpt1.get();	
				log.info("Lead Entity Details: {}",  getLead);
				getLead.setCommunication(leadDto.getCommunication());
				getLead.setStatus(StatusEnum.Contacted);
				leadRepository.save(getLead);
				status = StatusEnum.Contacted;
			}
		}
		catch(Exception e) {
			status = StatusEnum.failure;
			log.error("Error in [LeadService][updateStatus]: ", e);
		}
		log.info("[LeadService][updateStatus][END]");
		return status;
	}

}

