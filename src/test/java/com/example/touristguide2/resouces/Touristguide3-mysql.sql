DROP USER IF EXISTS 'Junes2003'@'localhost';
CREATE USER 'Junes2003'@'localhost' IDENTIFIED BY 'Hma86kyk....';
GRANT ALL PRIVILEGES ON touristguide3.* TO 'Junes2003'@'localhost';
FLUSH PRIVILEGES;


-- 2. Opret database
DROP DATABASE IF EXISTS touristguide3;
CREATE DATABASE touristguide3;
USE touristguide3;

-- 3. Opret tabeller
CREATE TABLE IF NOT EXISTS attraction (
                                          ID INT PRIMARY KEY AUTO_INCREMENT,
                                          NAME VARCHAR(100) NOT NULL,
    DESCRIPTION TEXT,
    LOCATION VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS tag (
                                   ID INT PRIMARY KEY AUTO_INCREMENT,
                                   NAME VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS attraction_tag (
                                              ATTRACTION_ID INT NOT NULL,
                                              TAG_ID INT NOT NULL,
                                              PRIMARY KEY (ATTRACTION_ID, TAG_ID),
    FOREIGN KEY (ATTRACTION_ID) REFERENCES attraction(ID) ON DELETE CASCADE,
    FOREIGN KEY (TAG_ID) REFERENCES tag(ID) ON DELETE CASCADE
    );

-- 4. Indsæt data i tabeller
INSERT INTO attraction(NAME, LOCATION, DESCRIPTION) VALUES
                                                        ("Junes", "Brønshøj", "Useriøs spiller"),
                                                        ("Den lille havefrue", "København", "En ikonisk statue på langlinje"),
                                                        ("Bellasky", "Amager", "EN af Skandinavieske flotteste hoteller"),
                                                        ("Amalienborg", "København", "Det danske kongehus residens"),
                                                        ("SMK", "København", "Museum for kunst");

INSERT INTO tag(NAME) VALUES
                          ("Historie"),
                          ("Kunst"),
                          ("Arkitektur"),
                          ("Sport"),
                          ("Natur"),
                          ("Museum"),
                          ("Fodboldspiller");

-- 5. Indsæt relationer mellem attraction og tag
TRUNCATE TABLE attraction_tag;

INSERT INTO attraction_tag(ATTRACTION_ID, TAG_ID) VALUES
                                                      (1, 4),
                                                      (1, 7),
                                                      (2, 1),
                                                      (2, 3),
                                                      (3, 3),
                                                      (4, 1),
                                                      (4, 3),
                                                      (5, 2),
                                                      (5, 6);
