package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<WareHouse, Long> {
}
