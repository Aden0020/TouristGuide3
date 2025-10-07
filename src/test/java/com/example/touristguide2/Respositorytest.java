package com.example.touristguide2;

import com.example.touristguide2.Model.TouristAttraction;
import com.example.touristguide2.Repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class RespositoryTest {

    @Autowired
    private TouristRepository repo;

    @Test
    void readAllAttractions() {
        List<TouristAttraction> all = Collections.singletonList(repo.findTouristAttractionByName("Zoo"));
        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getName()).isEqualTo("Museum");
        assertThat(all.get(1).getName()).isEqualTo("Park");
    }

    @Test
    void insertAndReadBack() {
        repo.saveAttraction(new TouristAttraction(8, "Hej", "abe", "Junes", null));
        var zoo = repo.findTouristAttractionByName("zoo");
        assertThat(zoo).isNotNull();
        assertThat(zoo.getClass().getName()).isEqualTo("Zoo");
    }
}
