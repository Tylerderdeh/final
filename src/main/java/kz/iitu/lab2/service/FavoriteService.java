package kz.iitu.lab2.service;

import jakarta.persistence.EntityNotFoundException;
import kz.iitu.lab2.dto.FavoriteDTO;
import kz.iitu.lab2.entity.Director;
import kz.iitu.lab2.entity.Establishments;
import kz.iitu.lab2.entity.Favorite;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.repository.DirectorRepository;
import kz.iitu.lab2.repository.EstablishmentsRepository;
import kz.iitu.lab2.repository.FavoriteRepository;
import kz.iitu.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final DirectorRepository directorRepository;
    private final EstablishmentsRepository establishmentsRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, DirectorRepository directorRepository, EstablishmentsRepository establishmentsRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.directorRepository = directorRepository;
        this.establishmentsRepository = establishmentsRepository;
    }

    public void addToFavorites(FavoriteDTO favoriteDTO) {
        User user = userRepository.findByEmail(favoriteDTO.getUserEmail()).orElseThrow();
        Director director = directorRepository.findByBin(favoriteDTO.getDirectorBin());
        Establishments establishments = establishmentsRepository.findByName(favoriteDTO.getEstablishmentName());
        Favorite favorite = new Favorite();
        favorite.setRate(favoriteDTO.getRate());
        favorite.setUser(user);
        favorite.setDirector(director);
        favorite.setEstablishments(establishments);
        favoriteRepository.save(favorite);
    }

    public Favorite getFavorite(Long favoriteId) {
        return favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found with id: " + favoriteId));
    }

    public Favorite removeFromFavorites(Long favoriteId) {
        // Реализация удаления из избранного
        Favorite favorite = getFavorite(favoriteId);
        favoriteRepository.delete(favorite);
        return favorite;
    }

    public List<Favorite> getUserFavorites(Long userId) {
        // Реализация получения всех объектов из избранного пользователя
        return favoriteRepository.findByUserId(userId);
    }

    // Другие методы для работы с избранным
}

