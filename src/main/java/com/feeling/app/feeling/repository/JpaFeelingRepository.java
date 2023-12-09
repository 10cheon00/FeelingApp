package com.feeling.app.feeling.repository;

import com.feeling.app.feeling.entity.Feeling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface JpaFeelingRepository extends
        JpaRepository<Feeling, Long>,
        JpaSpecificationExecutor<Feeling> {
}
