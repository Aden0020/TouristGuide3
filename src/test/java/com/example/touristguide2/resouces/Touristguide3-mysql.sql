-- Den her sleyter gamle tabeller hvis de findes
DROP TABLE IF EXISTS attraction_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS attraction;

-- Opretter tables
CREATE TABLE attraction (
                            ID INT AUTO_INCREMENT PRIMARY KEY,
                            NAME VARCHAR(100) NOT NULL,
                            DESCRIPTION TEXT,
                            LOCATION VARCHAR(100) NOT NULL
);

CREATE TABLE tag (
                     ID INT AUTO_INCREMENT PRIMARY KEY,
                     NAME VARCHAR(100) NOT NULL
);

CREATE TABLE attraction_tag (
                                ATTRACTION_ID INT NOT NULL,
                                TAG_ID INT NOT NULL,
                                PRIMARY KEY (ATTRACTION_ID, TAG_ID),
                                FOREIGN KEY (ATTRACTION_ID) REFERENCES attraction(ID) ON DELETE CASCADE,
                                FOREIGN KEY (TAG_ID) REFERENCES tag(ID) ON DELETE CASCADE
);

-- Eksempel på data
INSERT INTO attraction (NAME, LOCATION, DESCRIPTION) VALUES
                                                         ('Junes', 'Brønshøj', 'Useriøs spiller'),
                                                         ('Den lille havfrue', 'København', 'En ikonisk statue på Langelinie'),
                                                         ('Bellasky', 'Amager', 'Et af Skandinaviens flotteste hoteller'),
                                                         ('Amalienborg', 'København', 'Det danske kongehus residens'),
                                                         ('SMK', 'København', 'Museum for kunst');

INSERT INTO tag (NAME) VALUES
                           ('Historie'),
                           ('Kunst'),
                           ('Arkitektur'),
                           ('Sport'),
                           ('Natur'),
                           ('Museum'),
                           ('Fodboldspiller');

INSERT INTO attraction_tag (ATTRACTION_ID, TAG_ID) VALUES
                                                       (1, 4),
                                                       (1, 7),
                                                       (2, 1),
                                                       (2, 3),
                                                       (3, 3),
                                                       (4, 1),
                                                       (4, 3),
                                                       (5, 2),
                                                       (5, 6);
