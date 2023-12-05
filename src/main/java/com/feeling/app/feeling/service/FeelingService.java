package com.feeling.app.feeling.service;

import com.feeling.app.feeling.entity.Feeling;
import com.feeling.app.feeling.repository.FeelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class FeelingService {
    private final FeelingRepository feelingRepository;

    @Autowired
    public FeelingService(FeelingRepository feelingRepository) {
        this.feelingRepository = feelingRepository;
    }

    public Feeling save(Feeling feeling) throws Exception {
        if (feelingRepository.findByCreatedDate(feeling.getCreatedDate()).isPresent()) {
            throw new IllegalArgumentException();
        }

        return feelingRepository.save(feeling);
    }
}
