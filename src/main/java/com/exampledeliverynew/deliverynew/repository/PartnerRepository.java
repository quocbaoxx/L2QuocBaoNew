package com.exampledeliverynew.deliverynew.repository;

import com.exampledeliverynew.deliverynew.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    @Query(value = "SELECT count(p.partner_id) " +
            "FROM bp_partner p " +
            "WHERE (p.pn_type = 122 and  p.partner_id = :ffmId ) or " +
            "   (p.pn_type = 121 and p.partner_id = :lmId);", nativeQuery = true)
   Long checkExits(@Param("ffmId") Long ffmId, @Param("lmId") Long lmId);
}
