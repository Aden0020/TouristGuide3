package com.example.touristguide2.Service;

import com.example.touristguide2.Model.TouristAttraction;
import com.example.touristguide2.Repository.TouristRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TouristServiceTest {

    @Mock
    private TouristRepository touristRepository;

    @InjectMocks
    private TouristService touristService;

    private TouristAttraction touristAttraction;

    private List<TouristAttraction> testList;


    @BeforeEach
    void setUp() {
        ArrayList<String> list = new ArrayList<>();
        list.add("god");
        list.add("dårlig");

         touristAttraction = new TouristAttraction(2, "aden", "god mand", "london", list);

        testList = new ArrayList<>();
        testList.add(new TouristAttraction(1, "mo", "dårlig mand", "sweden", list));
        testList.add(touristAttraction);
    }

    @AfterEach
    void tearDown() {
    }

    @Test

    void getAllAttractions() {
        when(touristRepository.getAllAttractions()).thenReturn(testList);
        List<TouristAttraction> result = touristService.getAllAttractions();

        assertNotNull(result);
        assertEquals(2, testList.size());
        assertEquals(2, testList.get(1).getId());
        verify(touristRepository,times(1)).getAllAttractions();
    }

    @Test
    void deleteAttraction() {
        doNothing().when(touristRepository).deleteAttraction(touristAttraction.getName());

        touristService.deleteAttraction(touristAttraction.getName());

        verify(touristRepository, times(1)).deleteAttraction(touristAttraction.getName());
    }

    @Test
    void findTouristAttractionByName() {

        when(touristRepository.getAllAttractions()).thenReturn(List.of(touristAttraction));

        TouristAttraction result = touristService.findTouristAttractionByName("aden");

        assertNotNull(result);
        assertEquals("aden", result.getName());
        verify(touristRepository, times(1)).getAllAttractions();


}

    @Test
    void addTouristAttraction() {

        TouristAttraction attraction = new TouristAttraction(
                3, "Alan", "Smukt", "Hello", List.of("nature", "walk")
        );

        when(touristRepository.addTouristAttraction(attraction)).thenReturn(attraction);

        TouristAttraction result = touristService.addTouristAttraction(attraction);

        assertNotNull(result);
        assertEquals("Alan", result.getName());
        assertEquals("Hello", result.getLocation());
        assertEquals(2, result.getTags().size());

        verify(touristRepository, times(1)).addTouristAttraction(attraction);
    }

    @Test
    void getTouristAttractionTags() {

        List<String> tags = List.of("god", "dårlig");

        when(touristRepository.findTouristAttractionByName("mo")).thenReturn(touristAttraction);
        when(touristRepository.getTouristAttractionTags(touristAttraction.getId())).thenReturn(tags);

        List<String> result = touristService.getTouristAttractionTags("mo");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("god"));
        assertTrue(result.contains("dårlig"));

        verify(touristRepository, times(1)).findTouristAttractionByName("mo");
        verify(touristRepository, times(1)).getTouristAttractionTags(touristAttraction.getId());
    }

    @Test
    void saveAttraction() {
        TouristAttraction attraction = new TouristAttraction(
                3, "Alan", "Smukt", "Hello", List.of("nature", "walk")
        );

        when(touristRepository.saveAttraction(attraction)).thenReturn(attraction);

        TouristAttraction result = touristService.saveAttraction(attraction);

        assertNotNull(result);
        assertEquals(attraction.getId(), result.getId());
        assertEquals(attraction.getName(), result.getName());

        verify(touristRepository, times(1)).saveAttraction(attraction);
    }

    @Test
    void updateAttraction() {
        TouristAttraction updatedAttraction = new TouristAttraction(
                2, "aden", "fantastisk mand", "London", List.of("god", "dårlig")
        );

        when(touristRepository.updateAttraction(touristAttraction.getName(), "fantastisk mand"))
                .thenReturn(updatedAttraction);

        TouristAttraction result = touristService.updateAttraction(touristAttraction.getName(), "fantastisk mand");

        assertNotNull(result);
        assertEquals("fantastisk mand", result.getDescription());
        assertEquals(touristAttraction.getName(), result.getName());

        verify(touristRepository, times(1)).updateAttraction(touristAttraction.getName(), "fantastisk mand");
    }

    @Test
    void getAllTags() {
        List<String> mockTags = List.of("historical", "family-friendly", "adventure");
        when(touristRepository.getAllTags()).thenReturn(mockTags);

        List<String> result = touristService.getAllTags();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("historical"));
        assertTrue(result.contains("family-friendly"));
        assertTrue(result.contains("adventure"));

        verify(touristRepository, times(1)).getAllTags();
    }

    @Test
    void getAllLocations() {

        List<String> mockLocations = List.of("London", "Paris", "Copenhagen");
        when(touristRepository.getAllLocations()).thenReturn(mockLocations);

        List<String> result = touristService.getAllLocations();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("London"));
        assertTrue(result.contains("Paris"));
        assertTrue(result.contains("Copenhagen"));

        verify(touristRepository, times(1)).getAllLocations();
    }
}