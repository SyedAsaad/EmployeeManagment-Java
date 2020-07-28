package com.logex.demo.repository;

import com.logex.demo.model.Termination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerminationRepository extends JpaRepository<Termination,Long> {
}
