package com.feeling.app.feeling.service;

import com.feeling.app.feeling.entity.Feeling;
import com.feeling.app.feeling.exception.DuplicatedDateFeelingException;
import com.feeling.app.feeling.repository.JpaFeelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.feeling.app.feeling.service.FeelingSpecifications.*;

@Service
public class FeelingService {
    private final JpaFeelingRepository jpaFeelingRepository;

    @Autowired
    public FeelingService(JpaFeelingRepository feelingRepository) {
        this.jpaFeelingRepository = feelingRepository;
    }

    public Feeling save(Feeling feeling) throws Exception {
        Optional<Feeling> result = jpaFeelingRepository.findOne(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                        root.get("createdDate"),
                        feeling.getCreatedDate()));

        if (result.isPresent()) {
            throw new DuplicatedDateFeelingException();
        }
        return jpaFeelingRepository.save(feeling);
    }

    public List<Feeling> findAll(
            Optional<Integer> year,
            Optional<Integer> month,
            Optional<Integer> day) {
        Specification<Feeling> spec = Specification.where(null);
        if(year.isPresent()) {
            spec = spec.and(IsYearEqualTo(year.get()));
        }
        if(month.isPresent()) {
            spec = spec.and(IsMonthEqualTo(month.get()));
        }
        if(day.isPresent()) {
            spec = spec.and(IsDayEqualTo(day.get()));
        }

        return jpaFeelingRepository.findAll(spec);
    }
}
