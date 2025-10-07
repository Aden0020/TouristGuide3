package com.example.touristguide2.Service;

import com.example.touristguide2.Model.TouristAttraction;
import com.example.touristguide2.Repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository){
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return touristRepository.getAllAttractions();
    }

    public TouristAttraction deleteAttraction(String name) {
        return touristRepository.deleteAttraction(name);
    }

    public TouristAttraction findTouristAttractionByName(String name) {
        return touristRepository.getAllAttractions().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

//hh

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        return touristRepository.addTouristAttraction(touristAttraction);
    }

    public List<String> getTouristAttractionTags(String name) {
        TouristAttraction a = touristRepository.findTouristAttractionByName(name);
        if (a == null) return List.of();
        return touristRepository.getTouristAttractionTags(a.getId());
    }

    public TouristAttraction saveAttraction(TouristAttraction attraction) {
        return touristRepository.saveAttraction(attraction);
    }

    public TouristAttraction updateAttraction(String name, String newDescription) {
        return touristRepository.updateAttraction(name, newDescription);
    }

    public List<String> getAllTags() {
        return touristRepository.getAllTags();
    }

    public List<String> getAllLocations() {
        return touristRepository.getAllLocations();
    }


//    public List<String> getTagsForAttraction(int attractionId){
//        return touristRepository.getTagsForAttraction(attractionId);
//    }
}

