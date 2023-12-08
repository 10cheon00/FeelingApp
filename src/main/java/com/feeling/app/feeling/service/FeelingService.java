package com.feeling.app.feeling.service;

import com.feeling.app.feeling.entity.Feeling;
import com.feeling.app.feeling.exception.DuplicatedDateFeelingException;
import com.feeling.app.feeling.repository.JpaFeelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeelingService {
    private final JpaFeelingRepository jpaFeelingRepository;

    @Autowired
    public FeelingService(JpaFeelingRepository feelingRepository) {
        this.jpaFeelingRepository = feelingRepository;
    }

    public Feeling save(Feeling feeling) throws Exception {
        List<Feeling> list =  jpaFeelingRepository.findByCreatedDate(feeling.getCreatedDate());
        if (!jpaFeelingRepository.findByCreatedDate(feeling.getCreatedDate()).isEmpty()) {
            throw new DuplicatedDateFeelingException();
        }

        return jpaFeelingRepository.save(feeling);
    }

    public List<Feeling> findAll() {
        return jpaFeelingRepository.findAll();
    }
}
