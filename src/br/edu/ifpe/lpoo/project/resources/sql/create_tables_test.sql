create database testelivro;

USE testelivro;

CREATE TABLE livro(
    id_livro INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20),
    numero_paginas INT NOT NULL,
    genero VARCHAR(255) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano_publicacao INT NOT NULL,
    editora VARCHAR(255) NOT NULL,
    idioma VARCHAR(255) NOT NULL
);

CREATE TABLE item_acervo(
 id_item INT AUTO_INCREMENT PRIMARY KEY,
    tipo_item VARCHAR(50) NOT NULL,
    disponibilidade VARCHAR(50) NOT NULL,
    localCapaPath VARCHAR(2000)
);

CREATE TABLE exemplar(
	id_exemplar INT PRIMARY KEY,
    id_livro INT NOT NULL,
    tipo_item_acervo VARCHAR(50) NOT NULL,
    registro VARCHAR(20),
    status_exemplar VARCHAR(50) NOT NULL,
    tipo_exemplar VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_exemplar) REFERENCES item_acervo (id_item),
    FOREIGN KEY (id_livro) REFERENCES livro (id_livro)
);

CREATE TABLE periodico(
 id_periodico INT AUTO_INCREMENT PRIMARY KEY,
    issn VARCHAR(255) UNIQUE,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    numero_edicao INT NOT NULL,
    volume INT NOT NULL,
    editora VARCHAR(255) NOT NULL,
    idioma VARCHAR(255) NOT NULL,
    ano_publicacao INT NOT NULL,
    genero VARCHAR(255) NOT NULL
);

CREATE TABLE ebook(
 id_ebook INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    numero_paginas INT NOT NULL,
    genero VARCHAR(255) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano_publicacao INT NOT NULL,
    editora VARCHAR(255) NOT NULL,
    idioma VARCHAR(255) NOT NULL,
    formato_digital VARCHAR(255) NOT NULL,
    url_ebook VARCHAR(255) NOT NULL
);

CREATE TABLE membro(
 id_membro INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    matricula VARCHAR(10) UNIQUE NOT NULL,
    tipo_membro VARCHAR(50),
    debito_multas INT DEFAULT 0,
    status_membro VARCHAR(255) DEFAULT 'Ativo'
);

CREATE TABLE aluno(
 id_aluno INT PRIMARY KEY,
    curso VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_aluno) REFERENCES membro (id_membro)
);

CREATE TABLE professor(
 id_professor INT PRIMARY KEY,
 area_atuacao VARCHAR(255),
    departamento VARCHAR(255),
    FOREIGN KEY (id_professor) REFERENCES membro (id_membro)
);

CREATE TABLE pesquisador(
 id_pesquisador INT PRIMARY KEY,
    instituicao VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_pesquisador) REFERENCES membro (id_membro)
);

CREATE TABLE emprestimo(
 id_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
    id_item INT NOT NULL,
    id_membro INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_para_devolucao DATE NOT NULL,
    data_devolucao DATE,
    status_emprestimo VARCHAR (50) DEFAULT 'ABERTO',
    FOREIGN KEY (id_item) REFERENCES item_acervo (id_item),
    FOREIGN KEY (id_membro) REFERENCES membro (id_membro)
);