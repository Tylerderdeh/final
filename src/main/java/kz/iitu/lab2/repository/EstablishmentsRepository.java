package kz.iitu.lab2.repository;

import kz.iitu.lab2.entity.Establishments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstablishmentsRepository extends JpaRepository<Establishments, Long> {
    Page<Establishments> findByLocationLike(String location, Pageable pageable);

    Page<Establishments> findAllByGrade(String grade, Pageable pageable);

    Page<Establishments> findByWorkTime(String workTime, Pageable pageable);

    Establishments findByName(String establishmentName);
}
