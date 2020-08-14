package com.jeeves.sample.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeeves.sample.entity.LeadDTO;
import com.jeeves.sample.enums.ErrorTypeEnum;
import com.jeeves.sample.enums.StatusEnum;
import com.jeeves.sample.service.LeadService;
import com.jeeves.sample.utils.ApiResponse;
import com.jeeves.sample.utils.ErrorResponseUtils;
import com.jeeves.sample.utils.StatusResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
public class AppController {

	private final Logger log = LoggerFactory.getLogger(AppController.class);

	@Autowired
	LeadService leadService;

	@Autowired
	ErrorResponseUtils responseUtil;

	@ApiOperation("This is get operation api")
	@GetMapping(value = "/leads/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getLeadsById(@PathVariable("id") Long id) {
		log.info("[AppController][getLeadsById][START][ID] :{} ", id);
		if (id == null) {
			return new ResponseEntity<Object>(new ApiResponse(StatusEnum.failure, "Id cannot be null"), HttpStatus.NOT_FOUND);
		}
		LeadDTO leadDto = leadService.getLeadDetails(id);
		if (Objects.isNull(leadDto)) {
			return new ResponseEntity<Object>(new ApiResponse(StatusEnum.failure, "No record found"), HttpStatus.NOT_FOUND);
		}
		log.info("[AppController][getLeadsById][END]");
		return new ResponseEntity<Object>(leadDto, HttpStatus.OK);

	}

	@ApiOperation("This is an post operation api")
	@PostMapping(value = "/leads", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<Object> saveDetails(@RequestBody LeadDTO leaddto)
	{ 
		log.info("[AppController][saveDetails][START] :{} ", leaddto);
		ResponseEntity<Object> response = null;
		try {
			if (String.valueOf(leaddto.getMobile()).length() != 10) {
				response = responseUtil.errorResponse(
						ErrorTypeEnum.MOBILE_LENGTH_ERROR,
						HttpStatus.BAD_REQUEST, null);
			} else if (leadService.findByMobileAndEmail(leaddto.getMobile(),
					leaddto.getEmail()).size() > 0) {
				response = responseUtil.errorResponse(
						ErrorTypeEnum.MOBILE_EMAIL_NOT_UNIQUE,
						HttpStatus.BAD_REQUEST, null);
			} else if (leadService.findByMobile(leaddto.getMobile()).size() > 0) {
				response = responseUtil.errorResponse(
						ErrorTypeEnum.MOBILE_NOT_UNIQUE,
						HttpStatus.BAD_REQUEST, null);
			} else if (leadService.findByEmail(leaddto.getEmail()).size() > 0) {
				response = responseUtil.errorResponse(
						ErrorTypeEnum.EMAIL_NOT_UNIQUE, HttpStatus.BAD_REQUEST,
						null);
			} else {
				response = ResponseEntity.status(HttpStatus.CREATED).body(
						leadService.postLeadDetails(leaddto));
			}
		} catch (Exception e) {
			return responseUtil.errorResponse(ErrorTypeEnum.UNKNOWN,
					HttpStatus.BAD_REQUEST, e);
		}
		log.info("[AppController][saveDetails][END]");
		return response;

	}

	@ApiOperation("This is an put operation api")
	@PutMapping(value = "/leads/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateDetails( @PathVariable Long id, @RequestBody LeadDTO leadDto){
		log.info("[AppController][updateDetails][START] :{} ", leadDto);
		ResponseEntity<Object> response = null;
		try {
			StatusEnum status = leadService.putLeadDetails(leadDto, id);
			if (status == StatusEnum.success) {
				response = ResponseEntity.status(HttpStatus.ACCEPTED).body(
						new StatusResponse(status));
			} else {
				response = responseUtil.errorResponse(
						ErrorTypeEnum.NO_DATA_FOUND, HttpStatus.BAD_REQUEST,
						null);
			}
		} catch (Exception e) {
			return responseUtil.errorResponse(ErrorTypeEnum.UNKNOWN,
					HttpStatus.BAD_REQUEST, e);
		}
		log.info("[AppController][updateDetails][END]");
		return response;

	}

	@ApiOperation("This is an Delete operation api")
	@DeleteMapping(value ="/leads/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteById(@PathVariable("id") Long id) {
		log.info("[AppController][deleteById][START] :{} ", id);
		if(id == null)
		{
			return new ResponseEntity<Object>(new ApiResponse(StatusEnum.failure, "Id cannot be null"), HttpStatus.NOT_FOUND);
		}
		String leadDel = leadService.deleteDetails(id);
		if(leadDel.equals("success")) {
			return new ResponseEntity<Object>(leadDel,HttpStatus.OK);

		}else {
			log.info("[AppController][deleteById][END]");
			return new ResponseEntity<Object>(new ApiResponse(StatusEnum.failure, "ID not found in the database"), HttpStatus.BAD_REQUEST);
		}

	}
	
	@ApiOperation("This is an Mark operation api")
	@PutMapping(value = "/mark_lead/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateStatus( @PathVariable Long id, @RequestBody LeadDTO leadDto){
		log.info("[AppController][updateStatus][START] :{} ",id);
		log.info("[AppController][updateStatus][START] communication {}:", leadDto.getCommunication());
		ResponseEntity<Object> response = null;
		ApiResponse apiResponse = new ApiResponse();
		try 
		{
			if(id == null)
			{
				apiResponse.setStatus(StatusEnum.failure);
				apiResponse.setReason("ID CANNOT BE NULL");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
			}
			StatusEnum status = leadService.updateStatus(leadDto, id);
			if (status == StatusEnum.Contacted) 
			{
				apiResponse.setStatus(StatusEnum.Contacted);
				apiResponse.setCommunication(leadDto.getCommunication());
				response = ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
			} 
			else
			{
				apiResponse.setStatus(StatusEnum.failure);
				apiResponse.setReason("NO DATA FOUND");
				response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
			}
		} 
		catch (Exception e)
		{
			apiResponse.setStatus(StatusEnum.failure);
			apiResponse.setReason("INTERNAL SERVER ERROR");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
		}
		log.info("[AppController][updateStatus][END]");
		return response;

	}



}
