package br.edu.ifpe.lpoo.project.entities.membros;

public abstract class Pessoa {
	
	private String nome;
	private String email;
	private String cpf;
	private int id;
	
	public Pessoa(String nome,String email,String cpf) {
		this.cpf = cpf;
		this.email = email;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public int getId(){
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}