package kz.iitu.lab2.controller;

import kz.iitu.lab2.dto.FavoriteDTO;
import kz.iitu.lab2.entity.Favorite;
import kz.iitu.lab2.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<?> addToFavorites(@RequestBody FavoriteDTO favoriteDTO) {
        favoriteService.addToFavorites(favoriteDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{favoriteId}")
    public ResponseEntity<Favorite> getFavorite(@PathVariable Long favoriteId) {
        Favorite favorite = favoriteService.getFavorite(favoriteId);
        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long favoriteId) {
        favoriteService.removeFromFavorites(favoriteId);
        return new ResponseEntity<>("Removed from favorites successfully", HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favorite>> getUserFavorites(@PathVariable Long userId) {
        List<Favorite> userFavorites = favoriteService.getUserFavorites(userId);
        return new ResponseEntity<>(userFavorites, HttpStatus.OK);
    }

    // Другие методы для просмотра, удаления из избранного и т.д.
}

