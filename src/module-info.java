/**
 * 
 */
/**
 * 
 */
module SistemaGenrenciamentoBiblioteca {
    requires java.net.http;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires java.desktop;
    requires java.sql;

    exports br.edu.ifpe.lpoo.project.business;
    exports br.edu.ifpe.lpoo.project.business.dto.api;
    exports br.edu.ifpe.lpoo.project.entities.acervo;
    exports br.edu.ifpe.lpoo.project.entities.membros;
    exports br.edu.ifpe.lpoo.project.entities.gerenciamento;
    exports br.edu.ifpe.lpoo.project.enums;
    exports br.edu.ifpe.lpoo.project.exceptions;
}