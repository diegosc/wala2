-- Crear esquema
CREATE TABLE IF NOT EXISTS App_User (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         age INT,
                         mail VARCHAR(255) UNIQUE,
                         password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Page (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      date_creation TIMESTAMP,
                      title VARCHAR(255) UNIQUE,
                      id_User INT UNIQUE,
                      FOREIGN KEY (id_User) REFERENCES App_User(id)
);

CREATE TABLE IF NOT EXISTS Post (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      date_creation TIMESTAMP,
                      content VARCHAR(255),
                      img VARCHAR(255),
                      id_page INT,
                      FOREIGN KEY (id_page) REFERENCES Page(id) ON DELETE CASCADE
);