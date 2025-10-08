package com.example.touristguide2.Model;

import java.util.List;
import java.util.Objects;

public class TouristAttraction {
    private Integer id;
    private String name;
    private String description;
    private String location;
    private List<String> tags;

    // Constructor
    public TouristAttraction(Integer id, String name, String description, String location, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;
    }


    // Getters og setters
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TouristAttraction)) return false;
        TouristAttraction that = (TouristAttraction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


