DROP DATABASE IF EXISTS touristguide3;
CREATE DATABASE touristguide3;
USE touristguide3;

CREATE TABLE IF NOT EXISTS attraction(
ID INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(100) NOT NULL,
DESCRIPTION TEXT,
LOCATION VARCHAR(100) NOT NULL 
);


CREATE TABLE IF NOT EXISTS tag(
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
