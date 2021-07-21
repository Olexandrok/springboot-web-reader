package com.example.test.repos;

import com.example.test.entities.Fandom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FandomRepository extends JpaRepository<Fandom,Long> {
}
