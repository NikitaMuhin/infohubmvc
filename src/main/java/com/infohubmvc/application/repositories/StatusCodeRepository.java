package com.infohubmvc.application.repositories;

import com.infohubmvc.application.data.entity.StatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusCodeRepository extends JpaRepository<StatusCode, String> {

    Optional<StatusCode> getByCode(String code);

    Optional<StatusCode> findByCode(String code);

}
