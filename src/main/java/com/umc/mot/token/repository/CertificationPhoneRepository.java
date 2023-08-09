package com.umc.mot.token.repository;

import com.umc.mot.token.entity.CertificationPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationPhoneRepository extends JpaRepository<CertificationPhone, Integer> {
    Optional<CertificationPhone> findByPhoneNumber(String phoneNumber);
}
