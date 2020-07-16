DROP TABLE IF EXISTS BookAuthors;
DROP TABLE IF EXISTS BookGenres;
DROP TABLE IF EXISTS Authors;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS Genres;
CREATE TABLE Authors(`authorId` bigint NOT NULL AUTO_INCREMENT,
                                `firstName` varchar(128) NOT NULL, `lastName` varchar(128),  PRIMARY KEY (`authorId`));
CREATE TABLE Books(`bookId` bigint NOT NULL AUTO_INCREMENT, `title` varchar(255) NOT NULL,
                                `ISBN` varchar(64), PRIMARY KEY (`bookId`));
CREATE TABLE Genres(`genreId` int NOT NULL AUTO_INCREMENT, `name` varchar(64), PRIMARY KEY (`genreId`));
CREATE TABLE BookAuthors(`bookId` bigint NOT NULL, `authorId` bigint NOT NULL,
                FOREIGN KEY (`bookId`) REFERENCES Books(`bookId`) ON DELETE CASCADE,
                FOREIGN KEY (`authorId`) REFERENCES Authors(`authorId`) ON DELETE CASCADE,
                PRIMARY KEY (`bookId`, `authorId`));
CREATE TABLE BookGenres(`bookId` bigint NOT NULL, `genreId` int NOT NULL,
                FOREIGN KEY (`bookId`) REFERENCES Books(`bookId`) ON DELETE CASCADE,
                FOREIGN KEY (`genreId`) REFERENCES Genres(`genreId`) ON DELETE CASCADE,
                PRIMARY KEY (`bookId`, `genreId`));