package com.brewcompanion.brewcomp.mysql;

public class Constants {

        // Initializer Constants

        // table definitions

        /*
         * Recipes
         * 
         * id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         * name VARCHAR(255) NOT NULL,
         * hash VARCHAR(255) NOT NULL,
         * author VARCHAR(255) NOT NULL,
         * authorHash VARCHAR(255) NOT NULL,
         * FOREIGN KEY (authorID) REFERENCES Users(id)
         * description TEXT NOT NULL,
         * recipeInstructions TEXT NOT NULL,
         * recipeType VARCHAR(255),
         * parentName VARCHAR(255),
         * parentHash VARCHAR(255),
         * FOREIGN KEY (parentID) REFERENCES Recipes(id)
         * FOREIGN KEY (parentAuthorID) REFERENCES Users(id)
         */

        public final static String CREATE_RECIPES_TABLE = "CREATE TABLE IF NOT EXISTS Recipes (" +
                        "id INT NOT NULL AUTO_INCREMENT," +
                        "name VARCHAR(255) NOT NULL," +
                        "hash VARCHAR(255) NOT NULL," +
                        "author VARCHAR(255) NOT NULL," +
                        "authorHash VARCHAR(255) NOT NULL," +
                        "authorID INT NOT NULL," +
                        "description TEXT NOT NULL," +
                        "recipeInstructions TEXT NOT NULL," +
                        "recipeType VARCHAR(255) NOT NULL," +
                        "parentName VARCHAR(255) NULL," +
                        "parentHash VARCHAR(255) NULL," +
                        "parentID INT NULL," +
                        "parentAuthor VARCHAR(255) NULL," +
                        "parentAuthorHash VARCHAR(255) NULL," +
                        "parentAuthorID INT," +
                        "PRIMARY KEY (id)," +
                        "FOREIGN KEY (authorID) REFERENCES Users(id)," +
                        "FOREIGN KEY (parentID) REFERENCES Recipes(id)," +
                        "FOREIGN KEY (parentAuthorID) REFERENCES Users(id)" +
                        ");";

        /*
         * Users
         * 
         * id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
         * username VARCHAR(255) NOT NULL,
         * hash VARCHAR(255) NOT NULL,
         * email VARCHAR(255) NOT NULL,
         * salt VARCHAR(255) NOT NULL,
         * PRIMARY KEY (id)
         */

        public final static String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS Users (" +
                        "id INT NOT NULL AUTO_INCREMENT," +
                        "username VARCHAR(255) NOT NULL," +
                        "hash VARCHAR(255) NOT NULL," +
                        "email VARCHAR(255) NOT NULL," +
                        "salt VARCHAR(255) NOT NULL," +
                        "PRIMARY KEY (id)" +
                        "CONSTRAINT unique_username UNIQUE (username)," +
                        ");";

        // table checks

        public final static String CHECK_RECIPES_TABLE = "SELECT * FROM Recipes;";
        public final static String CHECK_USERS_TABLE = "SELECT * FROM Users;";

        // table insertions

        public final static String INSERT_RECIPE = "INSERT INTO Recipes (name, hash, author, authorID, authorHash, description, recipeInstructions, recipeType, parentName, parentHash, parentID, parentAuthor, parentAuthorHash, parentAuthorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        public final static String INSERT_USER = "INSERT INTO Users (username, hash, email, salt) VALUES (?, ?, ?, ?);";

        // get commands

        // recipe stuff
        public final static String GET_RECIPE = "SELECT * FROM Recipes WHERE id = ? LIMIT ?;";

        // user stuff

        // get the hash of user with username
        public final static String GET_USER_HASH = "SELECT hash FROM Users WHERE username = ?;";

        // get the id of user with username
        public final static String GET_USER_ID = "SELECT id FROM Users WHERE username = ?;";

        //username in use
        public final static String USERNAME_IN_USE = "SELECT username FROM Users WHERE username = ?;";

        //get the password salt
        public final static String GET_SALT = "SELECT salt FROM Users WHERE username = ?;";

        //get username from id
        public final static String GET_USERNAME = "SELECT username FROM Users WHERE id = ?;";

}
