package org.example.Repository;

import org.example.model.Distribution;
import org.example.model.Reassignment;
import org.example.model.Student;
import org.example.model.Unemployed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Slice<Student> findBy(Pageable pageable);

    Student findByDistribution(Distribution distribution);

    Student findByReassignment(Reassignment reassignment);

    Student findByUnemployed(Unemployed unemployed);
}
