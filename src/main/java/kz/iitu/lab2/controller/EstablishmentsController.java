package kz.iitu.lab2.controller;

import kz.iitu.lab2.entity.Establishments;
import kz.iitu.lab2.service.EstablishmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establishments")
public class EstablishmentsController {

    private final EstablishmentsService establishmentsService;

    @Autowired
    public EstablishmentsController(EstablishmentsService establishmentsService) {
        this.establishmentsService = establishmentsService;
    }

    @PostMapping
    public ResponseEntity<Establishments> createEstablishments(@RequestBody Establishments establishments) {
        Establishments createdEstablishments = establishmentsService.createEstablishments(establishments);
        return ResponseEntity.ok(createdEstablishments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establishments> getEstablishmentsById(@PathVariable Long id) {
        return establishmentsService.getEstablishmentsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Establishments>> getAllEstablishments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        List<Establishments> establishments = establishmentsService.getAllEstablishments(page,size,sortBy,order);
        return ResponseEntity.ok(establishments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establishments> updateEstablishments(@PathVariable Long id, @RequestBody Establishments updatedEstablishments) {
        Establishments updated = establishmentsService.updateEstablishments(id, updatedEstablishments);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstablishments(@PathVariable Long id) {
        establishmentsService.deleteEstablishments(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByLocation")
    public ResponseEntity<List<Establishments>> findEstablishmentsByLocationLike(
            @RequestParam String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        List<Establishments> establishments = establishmentsService.findEstablishmentsByLocationLike(location,page,size,sortBy,order);
        return ResponseEntity.ok(establishments);
    }

    @GetMapping("/findAllByGrade")
    public ResponseEntity<List<Establishments>> findAllEstablishmentsByGrade(
            @RequestParam String grade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        List<Establishments> establishments = establishmentsService.findAllEstablishmentsByGrade(grade,page,size,sortBy,order);
        return ResponseEntity.ok(establishments);
    }



    @GetMapping("/findByWorkTime")
    public ResponseEntity<List<Establishments>> findEstablishmentsByWorkTime(
            @RequestParam String workTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        List<Establishments> establishments = establishmentsService.findEstablishmentsByWorkTime(workTime,page,size,sortBy,order);
        return ResponseEntity.ok(establishments);
    }
}
