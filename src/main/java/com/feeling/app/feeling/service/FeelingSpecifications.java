package com.feeling.app.feeling.service;

import com.feeling.app.feeling.entity.Feeling;
import org.springframework.data.jpa.domain.Specification;

public class FeelingSpecifications {
    public static Specification<Feeling> IsYearEqualTo(int year) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.function("YEAR", Integer.class, root.get("createdDate")),
                year
        ));
    }

    public static Specification<Feeling> IsMonthEqualTo(int month) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.function("MONTH", Integer.class, root.get("createdDate")),
                month
        ));
    }

    public static Specification<Feeling> IsDayEqualTo(int day) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.function("DAY", Integer.class, root.get("createdDate")),
                day
        ));
    }
}
