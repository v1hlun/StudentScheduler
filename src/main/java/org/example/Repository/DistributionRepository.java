package org.example.Repository;

import java.util.Optional;
import org.example.model.Distribution;
import org.example.model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
    
public interface DistributionRepository extends JpaRepository<Distribution, Long> {
    Optional<Distribution> findByStudentId(Long StudentId);

    Slice<Distribution> findBy(Pageable pageable);
}
