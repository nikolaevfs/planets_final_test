package org.example.repository;

import org.example.entity.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LordRepository extends JpaRepository<Lord, Long> {
    Lord getLordById(Long id);

    Lord getLordByName(String name);

    @Query(value = "SELECT * FROM lords ORDER BY age ASC LIMIT 10", nativeQuery = true)
    List<Lord> getYoungest();
}
