package com.example.touristguide2.controller;

import com.example.touristguide2.Controller.TouristController;
import com.example.touristguide2.Model.TouristAttraction;
import com.example.touristguide2.Service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService; // KUN denne, korrekt @MockBean

    @Test
    void getTouristAttractions_shouldReturnAttractionsView() throws Exception {
        List<TouristAttraction> attractions = List.of(
                new TouristAttraction(1, "Tivoli", "Forlystelsespark", "København", null)
        );
        when(touristService.getAllAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions"))
                .andExpect(model().attributeExists("attractions"));

        verify(touristService).getAllAttractions();
    }

    @Test
    void getTouristAttractionTags_shouldReturnTagsView() throws Exception {
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark", "København", null);
        when(touristService.findTouristAttractionByName("Tivoli")).thenReturn(attraction);
        when(touristService.getTouristAttractionTags("Tivoli")).thenReturn(List.of("Familie", "Sjov"));

        mockMvc.perform(get("/attractions/Tivoli/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"));

        verify(touristService).findTouristAttractionByName("Tivoli");
        verify(touristService).getTouristAttractionTags("Tivoli");
    }

    @Test
    void saveAttraction_shouldRedirectAfterSave() throws Exception {
        TouristAttraction attraction = new TouristAttraction(
                1, "Lollands Bank Park", "Fodboldstadion", "Nykøbing Falster", null
        );

        mockMvc.perform(post("/attractions/save")
                        .flashAttr("attraction", attraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).saveAttraction(attraction);
    }

    @Test
    void deleteAttraction_shouldRedirectAfterDelete() throws Exception {
        mockMvc.perform(post("/attractions/delete/Tivoli"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService).deleteAttraction("Tivoli");
    }

    @Test
    void showAddForm_shouldReturnAddFormView() throws Exception {
        when(touristService.getAllLocations()).thenReturn(List.of("København", "Odense", "Nykøbing Falster"));
        when(touristService.getAllTags()).thenReturn(List.of());

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/addAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("location"))
                .andExpect(model().attributeExists("Tags"));

        verify(touristService).getAllLocations();
        verify(touristService).getAllTags();
    }

    @Test
    void showEditForm_shouldReturnEditFormView() throws Exception {
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark", "København", null);
        when(touristService.findTouristAttractionByName("Tivoli")).thenReturn(attraction);
        when(touristService.getAllLocations()).thenReturn(List.of("København", "Odense", "Nykøbing Falster"));
        when(touristService.getAllTags()).thenReturn(List.of());

        mockMvc.perform(get("/attractions/Tivoli/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateAttraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attributeExists("tags"));

        verify(touristService).findTouristAttractionByName("Tivoli");
        verify(touristService).getAllLocations();
        verify(touristService).getAllTags();
    }
}
