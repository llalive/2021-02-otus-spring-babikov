INSERT INTO Genres(`GenreId`, `Name`) VALUES (1, 'комедия'),(2, 'трагедия'),(3, 'драма'),(4, 'ужасы'),
                                             (5, 'рассказ'),(6, 'роман'),(7, 'повесть'),(8, 'пьеса');
INSERT INTO Books(`BookId`, `Title`, `ISBN`) VALUES (1, 'Молчание ягнят', '5-17-017776-3');
INSERT INTO Authors(`AuthorId`, `FirstName`, `LastName`) VALUES (1, 'Томас', 'Харрис');
INSERT INTO BookAuthors(`BookId`, `AuthorId`) VALUES (1, 1);
INSERT INTO BookGenres(`BookId`, `GenreId`) VALUES (1, 3), (1, 6);