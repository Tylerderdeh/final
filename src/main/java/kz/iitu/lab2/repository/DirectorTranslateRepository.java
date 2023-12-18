package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.DirectorTranslate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorTranslateRepository extends JpaRepository<DirectorTranslate,Long> {
}
