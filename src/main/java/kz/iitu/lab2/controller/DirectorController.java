package kz.iitu.lab2.controller;

import kz.iitu.lab2.dto.DirectorAndTranslateBody;
import kz.iitu.lab2.dto.DirectorResponseForCreate;
import kz.iitu.lab2.entity.Director;
import kz.iitu.lab2.service.DirectorResponse;
import kz.iitu.lab2.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping("/{establishmentId}")
    public ResponseEntity<DirectorResponseForCreate> createDirector(@RequestBody DirectorAndTranslateBody translateBody, @PathVariable("establishmentId") Long establishmentId) {
        DirectorResponseForCreate createdDirector = directorService.createDirector(translateBody, establishmentId);
        return ResponseEntity.ok(createdDirector);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorResponse> getDirectorById(@PathVariable Long id, @RequestHeader("translate") String code) {
        return directorService.getDirectorById(id,code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DirectorResponse>> getAllDirectors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestHeader("lang") String ln
    ) {
        List<DirectorResponse> directors = directorService.getAllDirectors(page,size,sortBy,order,ln);
        return ResponseEntity.ok(directors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable Long id, @RequestBody Director updatedDirector) {
        Director updated = directorService.updateDirector(id, updatedDirector);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}
