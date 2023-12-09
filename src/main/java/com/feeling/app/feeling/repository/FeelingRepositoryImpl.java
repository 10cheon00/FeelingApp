package com.feeling.app.feeling.repository;

import com.feeling.app.feeling.entity.Feeling;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class FeelingRepositoryImpl implements FeelingRepository {
    private final EntityManager entityManager;

    @Autowired
    public FeelingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
