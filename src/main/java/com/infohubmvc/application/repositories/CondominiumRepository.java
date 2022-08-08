package com.infohubmvc.application.repositories;


import com.infohubmvc.application.data.entity.Condominium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CondominiumRepository extends JpaRepository<Condominium, Long> {

    Optional<Condominium> getByName(String name);

    Page<Condominium> findAllByUserId(Long userId, Pageable pageable);

    List<Condominium> findAllByUserId(Long userId);

}
