CREATE DATABASE testelivro;

use testelivro;

CREATE TABLE livro (
    id_livro INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20),
    numero_paginas INT,
    genero VARCHAR(255),
    titulo VARCHAR(200),
    autor VARCHAR(255),
    ano_publicacao INT,
    editora VARCHAR(255),
    idioma VARCHAR(255)
);  

CREATE TABLE exemplar(
	id_exemplar INT AUTO_INCREMENT PRIMARY KEY,
    id_livro INT,
    registro VARCHAR(10),
    status_exemplar VARCHAR(20),
    FOREIGN KEY (id_livro) REFERENCES livro (id_livro)
);
