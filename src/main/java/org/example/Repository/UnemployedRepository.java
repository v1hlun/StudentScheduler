package org.example.Repository;

import org.example.model.Unemployed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnemployedRepository extends JpaRepository<Unemployed, Long> {
    Optional<Unemployed> findByStudentId(Long studentId);

    Slice<Unemployed> findBy(Pageable pageable);
}
