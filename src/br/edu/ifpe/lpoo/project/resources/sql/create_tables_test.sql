USE testelivro;

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

CREATE TABLE item_acervo(
	id_item INT AUTO_INCREMENT PRIMARY KEY,
    tipo_item VARCHAR(50) NOT NULL,
    disponibilidade VARCHAR(50) NOT NULL
);

CREATE TABLE exemplar(
	id_exemplar INT PRIMARY KEY,
    id_livro INT,
    registro VARCHAR(20),
    FOREIGN KEY (id_exemplar) REFERENCES item_acervo (id_item),
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
	id_ebook INT PRIMARY KEY,
    isbn VARCHAR(20) UNIQUE,
    numero_paginas INT,
    genero VARCHAR(255),
    titulo VARCHAR(255),
    autor VARCHAR(255),
    ano_publicacao INT,
    editora VARCHAR(255),
    idioma VARCHAR(255),
    formato_digital VARCHAR(255),
    url_ebook VARCHAR(255),
    FOREIGN KEY (id_ebook) REFERENCES item_acervo (id_item)
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

CREATE TABLE reserva(
	id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_membro INT NOT NULL,
    id_item INT NOT NULL,
	data_reserva Date NOT NULL,
	data_expiracao Date NOT NULL,
    status_reserva VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_membro) REFERENCES membro (id_membro),
    FOREIGN KEY (id_item) REFERENCES item_acervo (id_item)
);