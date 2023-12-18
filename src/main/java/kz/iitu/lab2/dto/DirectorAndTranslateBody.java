package kz.iitu.lab2.dto;


import kz.iitu.lab2.entity.DirectorTranslate;
import lombok.Data;

import java.util.List;

@Data
public class DirectorAndTranslateBody {
    private DirectorDTO directorDTO;
    private List<DirectorTranslate> directorTranslates;
}
