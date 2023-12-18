package kz.iitu.lab2.service;

import kz.iitu.lab2.dto.DirectorAndTranslateBody;
import kz.iitu.lab2.dto.DirectorDTO;
import kz.iitu.lab2.dto.DirectorResponseForCreate;
import kz.iitu.lab2.entity.Director;
import kz.iitu.lab2.entity.DirectorTranslate;
import kz.iitu.lab2.entity.Establishments;
import kz.iitu.lab2.repository.DirectorRepository;
import kz.iitu.lab2.repository.DirectorTranslateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final EstablishmentsService establishmentsService;
    private final DirectorTranslateRepository directorTranslateRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository, EstablishmentsService establishmentsService, DirectorTranslateRepository directorTranslateRepository) {
        this.directorRepository = directorRepository;
        this.establishmentsService = establishmentsService;
        this.directorTranslateRepository = directorTranslateRepository;
    }

    public DirectorResponseForCreate createDirector(DirectorAndTranslateBody directorAndTranslateBody, Long establishmentId) {
        DirectorDTO directorDTO = directorAndTranslateBody.getDirectorDTO();
        List<DirectorTranslate> directorTranslates = directorAndTranslateBody.getDirectorTranslates();
        Establishments establishmentsById = establishmentsService.getEstablishmentsById(establishmentId).orElseThrow();

        Director director = new Director();
        director.setBin(directorDTO.getBin());
        director.setEstablishments(establishmentsById);
        director.setDirectorTranslates(directorTranslates);
        directorRepository.save(director);

        for (DirectorTranslate translation : directorTranslates) {
            translation.setDirector(director);
            directorTranslateRepository.save(translation);
        }


        DirectorResponseForCreate directorResponse = new DirectorResponseForCreate();
        directorResponse.setId(director.getId());
        directorResponse.setBin(directorDTO.getBin());
        return directorResponse;
    }

    public Optional<DirectorResponse> getDirectorById(Long id,String code) {
        Optional<Director> direc = directorRepository.findById(id);

        return direc.map(director -> {
            if (director.getDirectorTranslates() != null && !director.getDirectorTranslates().isEmpty()) {
                DirectorTranslate selectedTranslation = director.getDirectorTranslates().stream()
                        .filter(translation -> code.equals(translation.getLanguageCode()))
                        .findFirst()
                        .orElse(null);

                if (selectedTranslation != null) {
                    DirectorResponse directorResponse = new DirectorResponse();
                    directorResponse.setId(director.getId());
                    directorResponse.setTranslatedName(selectedTranslation.getTranslatedName());
                    directorResponse.setTranslatedSurname(selectedTranslation.getTranslatedSurname());
                    return directorResponse;
                }
            }
            return null;
        });
    }

    public List<DirectorResponse> getAllDirectors(int page, int size, String sortBy, String order, String ln) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Director> directors = directorRepository.findAll(pageable);
        
        return directors.stream()
                .map(product -> convertToDTO(product, ln))
                .collect(Collectors.toList());
    }

    private DirectorResponse convertToDTO(Director director, String ln) {
        DirectorResponse directorResponse = new DirectorResponse();
        directorResponse.setId(director.getId());

        if (director.getDirectorTranslates() != null && !director.getDirectorTranslates().isEmpty()) {
            DirectorTranslate selectedTranslation = director.getDirectorTranslates().stream()
                    .filter(translation -> ln.equals(translation.getLanguageCode()))
                    .findFirst()
                    .orElse(null);

            if (selectedTranslation != null) {
                directorResponse.setTranslatedName(selectedTranslation.getTranslatedName());
                directorResponse.setTranslatedSurname(selectedTranslation.getTranslatedSurname());
            }
        }
        return directorResponse;
    }

    public Director updateDirector(Long id, Director updatedDirector) {
        if (directorRepository.existsById(id)) {
            updatedDirector.setId(id);
            return directorRepository.save(updatedDirector);
        } else {
            return null;
        }
    }

    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }
}
