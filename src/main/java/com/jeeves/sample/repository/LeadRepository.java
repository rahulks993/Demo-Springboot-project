package com.jeeves.sample.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeeves.sample.entity.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

	public List<Lead> findByMobile(Long mobile);

	public List<Lead> findByEmail(String email);

	public List<Lead> findByMobileAndEmail(long mobile,
			String email);
}
