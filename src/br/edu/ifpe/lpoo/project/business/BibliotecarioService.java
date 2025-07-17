package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.entities.funcionarios.Bibliotecario;
import br.edu.ifpe.lpoo.project.data.BibliotecarioRepository;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.enums.StatusMembro;
import br.edu.ifpe.lpoo.project.entities.CredencialAcesso;
import br.edu.ifpe.lpoo.project.data.CredencialAcessoRepository;

public class BibliotecarioService {
	
    public void cadastrarBibliotecario(String nome, String email, String cpf, String matricula,double salario, String cargo,String setor, String numRegistroConselho, String senha,String usuario) {

        if (nome == null || nome.isBlank()) {
            throw new BusinessExcepition("O nome é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new BusinessExcepition("O email é obrigatório");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new BusinessExcepition("O CPF é obrigatório");
        }
        if (!cpf.matches("\\d{11}")) {
            throw new BusinessExcepition("O CPF deve conter exatamente 11 números.");
        }
        if (matricula == null || matricula.isBlank()) {
            throw new BusinessExcepition("A matrícula é obrigatória");
        }
        if (salario <= 0) {
            throw new BusinessExcepition("O salário deve ser maior que zero.");
        }
        if (cargo == null || cargo.isBlank()) {
            throw new BusinessExcepition("O cargo é obrigatório");
        }
        if (usuario == null || usuario.isBlank()) {
            throw new BusinessExcepition("O usuario é obrigatório");
        }
        if (senha == null || senha.isBlank()) {
            throw new BusinessExcepition("a senha é obrigatória");
        }

        CredencialAcessoRepository credRepo = new CredencialAcessoRepository();
        String senhaCriptografada = BCrypt.hashpw(senha, BCrypt.gensalt());
        CredencialAcesso credencial = new CredencialAcesso(usuario, senhaCriptografada);

        try {
            credRepo.inserir(credencial);
        } catch (DbException e) {
            throw new BusinessExcepition("Erro ao cadastrar credencial: " + e.getMessage());
        }
        
        Bibliotecario bibliotecario = new Bibliotecario(nome, email, cpf, matricula, salario, cargo, setor, numRegistroConselho);

        BibliotecarioRepository bibliotecarioRepository = new BibliotecarioRepository();

        boolean existe = bibliotecarioRepository.existMembro(bibliotecario);

        if (!existe) {
            try {
                bibliotecarioRepository.insert(bibliotecario);
                System.out.println("Bibliotecário cadastrado com sucesso.");
            } catch (DbException e) {
                throw new BusinessExcepition("Erro ao cadastrar bibliotecário: " + e.getMessage());
            }
        } else {
            throw new BusinessExcepition("Esse bibliotecário já está cadastrado no sistema.");
        }
    }
    
    //atualizar bibliotecario
    public void atualizarBibliotecario(Bibliotecario bibliotecarioAtualizado, String novoStatus) {
        if (bibliotecarioAtualizado == null || bibliotecarioAtualizado.getCpf() == null || bibliotecarioAtualizado.getCpf().isBlank()) {
            throw new BusinessExcepition("CPF obrigatório para atualizar o bibliotecário.");
        }

        // Valida os campos editáveis
        if (bibliotecarioAtualizado.getNome() == null || bibliotecarioAtualizado.getNome().isBlank()) {
            throw new BusinessExcepition("O nome é obrigatório.");
        }
        if (bibliotecarioAtualizado.getEmail() == null || bibliotecarioAtualizado.getEmail().isBlank()) {
            throw new BusinessExcepition("O email é obrigatório.");
        }
        if (bibliotecarioAtualizado.getMatricula() == null || bibliotecarioAtualizado.getMatricula().isBlank()) {
            throw new BusinessExcepition("A matrícula é obrigatória.");
        }
        if (bibliotecarioAtualizado.getCargo() == null || bibliotecarioAtualizado.getCargo().isBlank()) {
            throw new BusinessExcepition("O cargo é obrigatório.");
        }
        if (bibliotecarioAtualizado.getSalario() <= 0) {
            throw new BusinessExcepition("O salário deve ser maior que zero.");
        }

        BibliotecarioRepository bibliotecarioRepository = new BibliotecarioRepository();

        boolean existe = bibliotecarioRepository.existMembro(bibliotecarioAtualizado);

        if (existe) {
            try {
                // Se o status foi informado, atualiza
                if (novoStatus != null && !novoStatus.isBlank()) {
                    StatusMembro statusConvertido = StatusMembro.valueOf(novoStatus.trim().toUpperCase());
                    bibliotecarioAtualizado.setStatusAcesso(statusConvertido);
                }

                bibliotecarioRepository.atualizar(bibliotecarioAtualizado);
                System.out.println("Bibliotecário atualizado com sucesso.");
            } catch (IllegalArgumentException e) {
                throw new BusinessExcepition("Status inválido. Use: ATIVO, INATIVO, SUSPENSO ou BLOQUEADO.");
            } catch (DbException e) {
                throw new BusinessExcepition("Erro ao atualizar o bibliotecário: " + e.getMessage());
            }
        } else {
            throw new BusinessExcepition("Bibliotecário não encontrado para atualização.");
        }
    }
    public void validarSenhaForte(String senha, String usuario, String cpf) {
        if (senha.length() < 8) {
            throw new BusinessExcepition("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!senha.matches(".*[A-Z].*")) {
            throw new BusinessExcepition("A senha deve conter pelo menos uma letra maiúscula.");
        }
        if (!senha.matches(".*[a-z].*")) {
            throw new BusinessExcepition("A senha deve conter pelo menos uma letra minúscula.");
        }
        if (!senha.matches(".*\\d.*")) {
            throw new BusinessExcepition("A senha deve conter pelo menos um número.");
        }
        if (!senha.matches(".*[!@#$%^&*()_+\\-=[\\]{}|;':\",.<>/?].*")) {
            throw new BusinessExcepition("A senha deve conter pelo menos um caractere especial.");
        }
        if (senha.contains(" ")) {
            throw new BusinessExcepition("A senha não pode conter espaços.");
        }
        if (senha.equalsIgnoreCase(usuario) || senha.equals(cpf)) {
            throw new BusinessExcepition("A senha não pode ser igual ao nome de usuário ou CPF.");
        }
    }
}