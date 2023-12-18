package kz.iitu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorTranslate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "translated_name")
    private String translatedName;

    @Column(name = "translated_address")
    private String translatedSurname;
}
