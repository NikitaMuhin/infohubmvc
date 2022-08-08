package com.infohubmvc.application.repositories;

import com.infohubmvc.application.data.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
