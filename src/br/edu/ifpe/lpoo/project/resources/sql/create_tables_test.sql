CREATE DATABASE testelivro;

use testelivro;


CREATE TABLE item_acervo(
	id_item INT AUTO_INCREMENT PRIMARY KEY,
    tipo_item VARCHAR(50) NOT NULL,
    disponibilidade VARCHAR(50) NOT NULL
);

CREATE TABLE livro(
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

CREATE TABLE periodico(
	id_periodico INT PRIMARY KEY,
    issn VARCHAR(255) UNIQUE,
    titulo VARCHAR(255),
    autor VARCHAR(255),
    numero_edicao INT,
    volume INT,
    editora VARCHAR(255),
    idioma VARCHAR(255),
    ano_publicacao INT,
    genero VARCHAR(255),
    FOREIGN KEY (id_periodico) REFERENCES item_acervo (id_item)
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
    FOREIGN KEY (id_ebook) REFERENCES item_acervo (id_item)
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

CREATE TABLE bibliotecario (
        id_bibliotecario INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255),
email VARCHAR(255) UNIQUE,
cpf VARCHAR(11) UNIQUE,
matricula VARCHAR(10) UNIQUE,
salario DECIMAL(10,2),
cargo VARCHAR(100),
id_credencial_acesso INT,
setor VARCHAR(100),
num_registro_conselho VARCHAR(100)
);

CREATE TABLE bibliotecario_chefe (
        id_bibliotecario_chefe INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(255),
email VARCHAR(255) UNIQUE,
cpf VARCHAR(11) UNIQUE,
matricula VARCHAR(10) UNIQUE,
salario DECIMAL(10,2),
cargo VARCHAR(100),
id_credencial_acesso INT,
setor VARCHAR(100),
num_registro_conselho VARCHAR(100),
pode_gerenciar_contas BOOLEAN DEFAULT TRUE,
data_inicio_chefia DATE
);