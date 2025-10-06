package com.example.touristguide2.Controller;

import com.example.touristguide2.Model.TouristAttraction;
import com.example.touristguide2.Service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public String getTouristAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions";
    }

    @GetMapping("/{name}/tags")
    public String getTouristAttractionTags(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.findTouristAttractionByName(name);
        if (attraction == null) {
            return "redirect:/attractions";
        }
        model.addAttribute("attraction", attraction);
        model.addAttribute("tags", touristService.getTouristAttractionTags(name));

        return "tags";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction(null,"", "", "", new ArrayList<>()));
        model.addAttribute("location", touristService.getAllLocations());
        model.addAttribute("Tags", touristService.getAllTags());
        return "/addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
        touristService.saveAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/edit")
    public String showEditForm(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.findTouristAttractionByName(name);
        if (attraction == null) {
            return "redirect:/attractions";
        }

        model.addAttribute("attraction", attraction);
        model.addAttribute("cities", touristService.getAllLocations()); // liste af byer
        model.addAttribute("tags", touristService.getAllTags());        // liste af tags

        return "updateAttraction";
    }

    @PostMapping("/{name}/update")
    public String updateAttraction(@PathVariable String name,
                                   @ModelAttribute TouristAttraction attraction) {
        touristService.saveAttraction(attraction);
        return "redirect:/attractions";
    }

    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        touristService.deleteAttraction(name);
        return "redirect:/attractions";
    }



}
