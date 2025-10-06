package com.example.touristguide2.Repository;

import com.example.touristguide2.Model.TouristAttraction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // CRUD for attraction
    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT id, name, description, location FROM attraction";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            return new TouristAttraction(
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("location"),
                    getTagsForAttraction(id)
            );
        });

    }

    public TouristAttraction addTouristAttraction(TouristAttraction attraction) {
        // indsæt attraction
        jdbcTemplate.update("INSERT INTO attraction (name, description, location) VALUES (?, ?, ?)",
                attraction.getName(), attraction.getDescription(), attraction.getLocation());

        // hent id på attraction
        Integer id = jdbcTemplate.queryForObject("SELECT id FROM attraction WHERE name=?",
                Integer.class, attraction.getName());

        // gem tags
        saveTagsForAttraction(id, attraction.getTags());

        return attraction;
    }

    public TouristAttraction updateAttraction(String name, String description) {
        jdbcTemplate.update("UPDATE attraction SET description=? WHERE name=?", description, name);
        return findTouristAttractionByName(name);
    }

    public TouristAttraction deleteAttraction(String name) {
        TouristAttraction existing = findTouristAttractionByName(name);
        if (existing != null) {
            jdbcTemplate.update("DELETE FROM attraction WHERE name=?", name);
        }
        return existing;
    }

    public TouristAttraction findTouristAttractionByName(String name) {
        String sql = "SELECT * FROM attraction WHERE LOWER(name)=LOWER(?)";
        List<TouristAttraction> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            return new TouristAttraction(
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("location"),
                    getTagsForAttraction(id)
            );
        }, name);
        return list.isEmpty() ? null : list.get(0);
    }

    public TouristAttraction saveAttraction(TouristAttraction attraction) {
        TouristAttraction existing = findTouristAttractionByName(attraction.getName());
        if (existing != null) {
            // update
            jdbcTemplate.update("UPDATE attraction SET description=?, location=? WHERE name=?",
                    attraction.getDescription(), attraction.getLocation(), attraction.getName());

            // slet gamle tags
            Integer id = jdbcTemplate.queryForObject("SELECT id FROM attraction WHERE name=?",
                    Integer.class, attraction.getName());
            jdbcTemplate.update("DELETE FROM attraction_tag WHERE attraction_id=?", id);

            // gem nye tags
            saveTagsForAttraction(id, attraction.getTags());
            return findTouristAttractionByName(attraction.getName());
        } else {
            return addTouristAttraction(attraction);
        }
    }

    // Tags
    public List<String> getTagsForAttraction(int attractionId) {
        String sql = "SELECT t.name FROM tag t JOIN attraction_tag at ON t.id = at.tag_id WHERE at.attraction_id = ?";
        return jdbcTemplate.query(sql, new Object[]{attractionId}, (rs, rowNum) -> rs.getString("name"));
        }

    public List<String> getTouristAttractionTags(int attractionId) {
        String sql = """
            SELECT t.name
            FROM tag t
            JOIN attraction_tag at ON t.id = at.tag_id
            WHERE at.attraction_id=?
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("name"), attractionId);
    }

    public List<String> getAllTags() {
        return jdbcTemplate.query("SELECT name FROM tag ORDER BY name",
                (rs, rowNum) -> rs.getString("name"));
    }

    private void saveTagsForAttraction(Integer attractionId, List<String> tags) {
        if (tags == null) return;
        for (String tag : tags) {
            // indsæt tag hvis den ikke findes
            jdbcTemplate.update("INSERT IGNORE INTO tag (name) VALUES (?)", tag);

            // link attraction og tag
            jdbcTemplate.update("""
                INSERT IGNORE INTO attraction_tag (attraction_id, tag_id)
                VALUES (?, (SELECT id FROM tag WHERE name=?))
                """, attractionId, tag);
        }
    }


    // Location
    public List<String> getAllLocation() {
        return jdbcTemplate.query("SELECT DISTINCT location FROM attraction ORDER BY location",
                (rs, rowNum) -> rs.getString("location"));
    }
}
