package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.office.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
