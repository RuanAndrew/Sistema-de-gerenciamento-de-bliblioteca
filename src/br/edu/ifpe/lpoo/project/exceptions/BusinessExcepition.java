package br.edu.ifpe.lpoo.project.exceptions;

import java.io.Serial;

public class BusinessExcepition extends RuntimeException {

	@Serial
    private static final long serialVersionUID = 1L;

	public BusinessExcepition(String message) {
        super(message);
    }
}
