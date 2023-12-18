package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    Director findByBin(String bin);
}
