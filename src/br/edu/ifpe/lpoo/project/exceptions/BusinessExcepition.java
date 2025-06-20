package br.edu.ifpe.lpoo.project.exceptions;

public class BusinessExcepition extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessExcepition(String message) {
        super(message);
    }
}
