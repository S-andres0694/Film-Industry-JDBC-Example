In this assignment I was asked to create a Relational Database System (Which adopts
SQLite as its querying language and database engine). To populate this database and also
retrieve data from it, we were also tasked with the usage of the JDBC Interface, which
would connect directly to the database to carry out the aforementioned operations.
The database is built around Movies, which contain characteristics such as “a title, a
release date, running time, genre, the director, actors, plot, some ratings (like Rotten
Tomatoes, IMDB etc.) and the awards they have won. “ It will also use Actors as its main
building pillar, which will contain attributes such as “a name, the awards they have won,
what films they have been in, their birthday etc.
This practical was divided in 5 stages:
1. The Design of an ER Diagram: Used to visually represent the tables and
relationships that form our databases.
2. The Creation of the Relational Schema: Which is used to add the constraints
and indications about the different columns and keys that form the tables. Also
establishes how the data is structured. This was stored in a .sql File in the form of
a script.
3. Creation of a Java Program to Create the Relational Schema: By using a JDBC
Connection and the already created Relational Schema File, the program would
instantiate a database with the constraints that are in the relational schema file.
4. Population of the Database: To populate the database, I was asked to read
through a series of files (which were created by me) and add entries to the
database using information directly extracted from the files.
5. Create a Java Program to Query: The JDBC Connection would allow for the
execution of a series of SQL Queries in order to retrieve data from the table.
My program is able to successfully complete all 5 tasks. This submission has an ER
Diagram which accurately represents my database. Additionally, it contains a database
that complies with the given specification and allows for its users to retrieve data from it.
Finally, it also handles all of the related exceptions and problems that could occur during
its usage, such as failure to create the database, failure to retrieve data from a table
(because the query does not give any data back) or the usage of unrecognised files that
are not compliant with my code, just to mention some examples.
