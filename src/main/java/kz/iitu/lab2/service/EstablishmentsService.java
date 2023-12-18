package kz.iitu.lab2.service;

import kz.iitu.lab2.entity.Establishments;
import kz.iitu.lab2.repository.EstablishmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstablishmentsService {

    private final EstablishmentsRepository establishmentsRepository;

    @Autowired
    public EstablishmentsService(EstablishmentsRepository establishmentsRepository) {
        this.establishmentsRepository = establishmentsRepository;
    }

    public Establishments createEstablishments(Establishments establishments) {
        return establishmentsRepository.save(establishments);
    }

    public Optional<Establishments> getEstablishmentsById(Long id) {
        return establishmentsRepository.findById(id);
    }

    public List<Establishments> getAllEstablishments(
            int page, int size, String sortBy, String order
    ) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);


        Page<Establishments> establishments = establishmentsRepository.findAll(pageable);

        return establishments.getContent();
    }

    public Establishments updateEstablishments(Long id, Establishments updatedEstablishments) {
        if (establishmentsRepository.existsById(id)) {
            updatedEstablishments.setId(id);
            return establishmentsRepository.save(updatedEstablishments);
        } else {
            return null;
        }
    }

    public void deleteEstablishments(Long id) {
        establishmentsRepository.deleteById(id);
    }

    public List<Establishments> findEstablishmentsByLocationLike(String location, int page,
                                                                 int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return establishmentsRepository.findByLocationLike(location,pageable).getContent();
    }

    public List<Establishments> findAllEstablishmentsByGrade(String grade, int page,
                                                             int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return establishmentsRepository.findAllByGrade(grade,pageable).getContent();
    }

    public List<Establishments> findEstablishmentsByWorkTime(String workTime, int page,
                                                             int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);

        return establishmentsRepository.findByWorkTime(workTime,pageable).getContent();
    }
}
