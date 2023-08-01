package com.umc.mot.search.repository;

import com.umc.mot.search.entity.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<SearchEntity, Integer> {
}
