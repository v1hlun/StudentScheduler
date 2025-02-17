package org.example.Repository;

import org.example.model.Reassignment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReassignmentRepository extends JpaRepository<Reassignment, Long> {
    Optional<Reassignment> findByStudentId(Long studentId);

    Slice<Reassignment> findBy(Pageable pageable);
}
