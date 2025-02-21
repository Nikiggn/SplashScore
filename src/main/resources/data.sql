ALTER TABLE players
    MODIFY COLUMN goals INT DEFAULT 0,
    MODIFY COLUMN matches_played INT DEFAULT 0;

INSERT INTO players (full_name, birth_date, cap_number, country, club_id) VALUES
('Ivan Petrov', '2001-03-15', 4, 'BULGARIA', 1),
('Georgi Ivanov', '1998-07-22', 7, 'BULGARIA', 1),
('Nikolay Dimitrov', '2003-11-30', 2, 'BULGARIA', 1),
('Stanislav Hristov', '1995-05-18', 9, 'BULGARIA', 1),
('Dimitar Stoyanov', '2000-01-10', 12, 'BULGARIA', 1),

('Vasil Kolev', '1999-06-25', 5, 'BULGARIA', 2),
('Mihail Georgiev', '2002-08-14', 11, 'BULGARIA', 2),
('Atanas Petkov', '1997-12-05', 6, 'BULGARIA', 2),
('Rosen Dimitrov', '1993-09-03', 3, 'BULGARIA', 2),
('Kiril Todorov', '2004-04-21', 1, 'BULGARIA', 2),

('Tsvetan Angelov', '1996-02-28', 10, 'BULGARIA', 3),
('Stefan Nikolov', '2005-05-17', 8, 'BULGARIA', 3),
('Lyuben Hristov', '1994-11-11', 13, 'BULGARIA', 3),
('Petar Stanev', '2001-07-29', 2, 'BULGARIA', 3),
('Alexander Kirov', '2003-10-09', 4, 'BULGARIA', 3),

('Martin Petrov', '2000-03-22', 1, 'BULGARIA', 4),
('Emil Dimitrov', '1997-09-14', 7, 'BULGARIA', 4),
('Hristo Georgiev', '1999-12-01', 9, 'BULGARIA', 4),
('Boris Ivanov', '1995-06-10', 11, 'BULGARIA', 4),
('Plamen Kolev', '2002-01-19', 3, 'BULGARIA', 4),

('Vladimir Stoyanov', '2004-07-08', 5, 'BULGARIA', 5),
('Kristian Angelov', '1998-08-26', 12, 'BULGARIA', 5),
('Denis Nikolov', '2001-02-03', 6, 'BULGARIA', 5),
('Milen Georgiev', '1996-05-12', 10, 'BULGARIA', 5),
('Simeon Petkov', '1993-04-30', 8, 'BULGARIA', 5),

('Yordan Hristov', '2005-01-07', 3, 'BULGARIA', 6),
('Radoslav Dimitrov', '1997-10-25', 9, 'BULGARIA', 6),
('Viktor Stanev', '2003-06-18', 1, 'BULGARIA', 6),
('Deyan Kolev', '1994-12-07', 7, 'BULGARIA', 6),
('Ivan Angelov', '2002-08-02', 4, 'BULGARIA', 6),

('Aleksandar Ivanov', '2000-09-09', 5, 'BULGARIA', 7),
('Boyan Petrov', '1996-02-15', 11, 'BULGARIA', 7),
('Nasko Dimitrov', '1999-07-29', 2, 'BULGARIA', 7),
('Todor Stoyanov', '2003-03-21', 8, 'BULGARIA', 7),
('Pavel Hristov', '1995-06-30', 10, 'BULGARIA', 7),

('Zhivko Georgiev', '1998-04-12', 13, 'BULGARIA', 8),
('Sergey Kolev', '2004-05-23', 3, 'BULGARIA', 8),
('Miroslav Nikolov', '1997-11-08', 7, 'BULGARIA', 8),
('Ivo Petrov', '2002-01-27', 9, 'BULGARIA', 8),
('Dimitar Stanev', '1993-06-15', 6, 'BULGARIA', 8),

('Spas Angelov', '2005-02-14', 2, 'BULGARIA', 9),
('Biser Georgiev', '1996-10-07', 12, 'BULGARIA', 9),
('Georgi Nikolov', '2001-09-03', 4, 'BULGARIA', 9),
('Slavcho Ivanov', '1998-07-20', 1, 'BULGARIA', 9),
('Konstantin Kolev', '2003-05-09', 5, 'BULGARIA', 9),

('Diyan Stoyanov', '1997-03-25', 10, 'BULGARIA', 10),
('Metodi Petrov', '2000-08-14', 8, 'BULGARIA', 10),
('Nikola Dimitrov', '1999-11-06', 7, 'BULGARIA', 10),
('Rumen Hristov', '1994-06-30', 11, 'BULGARIA', 10),
('Mihail Stanev', '2002-12-19', 13, 'BULGARIA', 10),

('Tihomir Georgiev', '2003-04-17', 3, 'BULGARIA', 11),
('Zdravko Angelov', '1995-09-22', 9, 'BULGARIA', 11),
('Borislav Kolev', '1998-06-13', 5, 'BULGARIA', 11),
('Veselin Nikolov', '2001-02-08', 1, 'BULGARIA', 11),
('Daniel Petrov', '2004-07-26', 6, 'BULGARIA', 11),

('Todor Hristov', '1999-01-12', 10, 'BULGARIA', 12),
('Stefan Dimitrov', '2002-05-31', 12, 'BULGARIA', 12),
('Plamen Ivanov', '1996-10-18', 8, 'BULGARIA', 12),
('Petar Kolev', '2005-07-07', 7, 'BULGARIA', 12),
('Stanislav Stanev', '1993-12-03', 2, 'BULGARIA', 12);


INSERT INTO clubs (name, town, country) VALUES ('CSKA', 'Sofia', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Lokomotiv', 'Sofia', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Varna', 'Varna', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Akva Sport', 'Varna', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Komodor', 'Varna', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Levski', 'Sofia', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('CDNA 2013', 'Sofia', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Botev', 'Burgas', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Lokomotiv Nikola Nakov', 'Sofia', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Chernomorets', 'Burgas', 'BULGARIA');
INSERT INTO clubs (name, town, country) VALUES ('Botev 2000', 'Burgas', 'BULGARIA');