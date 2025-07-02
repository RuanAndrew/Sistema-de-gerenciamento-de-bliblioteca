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

CREATE TABLE ebook(
	id_ebook INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    numero_paginas INT,
    genero VARCHAR(255),
    titulo VARCHAR(255),
    autor VARCHAR(255),
    ano_publicacao INT,
    editora VARCHAR(255),
    idioma VARCHAR(255),
	formato_digital VARCHAR(255),
    url_ebook VARCHAR(255)
);


CREATE TABLE periodico (
	id_periodico INT AUTO_INCREMENT PRIMARY KEY,
    issn VARCHAR(255) UNIQUE, 
    titulo VARCHAR(255), 
    autor VARCHAR(255), 
    numero_edicao INT, 
    volume INT, 
    editora VARCHAR(255),
    idioma VARCHAR(255), 
    ano_publicacao INT,
    genero VARCHAR(255)
);

CREATE TABLE aluno(
	id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255), 
    email VARCHAR(255) UNIQUE, 
    cpf VARCHAR(11) UNIQUE, 
    matricula VARCHAR(10) UNIQUE, 
    tipo_membro VARCHAR(20), 
    debito_multas INT, 
    status_membro VARCHAR(255), 
    curso VARCHAR(255)
);

CREATE TABLE professor(
	id_professor INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255), 
    email VARCHAR(255) UNIQUE, 
    cpf VARCHAR(11) UNIQUE, 
    matricula VARCHAR(10) UNIQUE, 
    tipo_membro VARCHAR(20), 
    debito_multas INT, 
    status_membro VARCHAR(255),
    area_atuacao VARCHAR(255), 
    departamento VARCHAR(255)
);

CREATE TABLE pesquisador(
	id_pesquisador INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255), 
    email VARCHAR(255) UNIQUE, 
    cpf VARCHAR(11) UNIQUE, 
    matricula VARCHAR(10) UNIQUE, 
    tipo_membro VARCHAR(20), 
    debito_multas INT, 
    status_membro VARCHAR(255),
    instituicao VARCHAR(255)
);