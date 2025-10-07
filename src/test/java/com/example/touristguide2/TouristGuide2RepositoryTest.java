package com.example.touristguide2;

import com.example.touristguide2.Model.TouristAttraction;
import com.example.touristguide2.Repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class TouristGuide2RepositoryTest {

    @Autowired
    private TouristRepository repo;

    // Create
    @Test
    void addTouristAttraction() {
        repo.addTouristAttraction(new TouristAttraction(
                null, "TestName", "TestDescription", "Aalborg", null
        ));

        TouristAttraction found = repo.findTouristAttractionByName("TestName");
        assertThat(found).isNotNull();
        assertThat(found.getDescription()).isEqualTo("TestDescription");
        assertThat(found.getLocation()).isEqualTo("Aalborg");
    }

    // Read
    @Test
    void getTouristAttractions() {
        List<TouristAttraction> all = repo.getAllAttractions();
        assertThat(all).isNotNull();
        assertThat(all.size()).isGreaterThanOrEqualTo(0);
    }

    // Update
    @Test
    void updateAttraction() {
        repo.addTouristAttraction(new TouristAttraction(
                null, "UpdateTest", "OldDescription", "Esbjerg", null
        ));

        repo.updateAttraction("UpdateTest", "NewDescription");
        TouristAttraction updated = repo.findTouristAttractionByName("UpdateTest");
        assertThat(updated).isNotNull();
        assertThat(updated.getDescription()).isEqualTo("NewDescription");
    }

    // Delete
    @Test
    void deleteAttraction() {
        repo.addTouristAttraction(new TouristAttraction(
                null, "DeleteTest", "SomeDescription", "Helsingør", null
        ));

        TouristAttraction beforeDelete = repo.findTouristAttractionByName("DeleteTest");
        assertThat(beforeDelete).isNotNull();

        repo.deleteAttraction("DeleteTest");
        TouristAttraction afterDelete = repo.findTouristAttractionByName("DeleteTest");
        assertThat(afterDelete).isNull();
    }
}
