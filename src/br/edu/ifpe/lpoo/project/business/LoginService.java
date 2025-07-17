package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.entities.CredencialAcesso;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;
import br.edu.ifpe.lpoo.project.data.CredencialAcessoRepository;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    private CredencialAcessoRepository credRepo = new CredencialAcessoRepository();

    public boolean fazerLogin(String usuario, String senhaDigitada) {
        CredencialAcesso cred = credRepo.buscarPorLogin(usuario);

        if (cred != null && BCrypt.checkpw(senhaDigitada, cred.getSenha())) {
            System.out.println("Login realizado com sucesso.");
            return true;
        } else {
            System.out.println("Usuário ou senha inválidos.");
            return false;
        }
    }

    public void alterarSenhaPorUsuario(String usuario, String senhaAntiga, String novaSenha) {
        CredencialAcesso credencial = credRepo.buscarPorUsuario(usuario);

        if (credencial == null) {
            throw new BusinessExcepition("Credencial não encontrada para o usuário informado.");
        }

        if (!BCrypt.checkpw(senhaAntiga, credencial.getSenha())) {
            throw new BusinessExcepition("Senha antiga incorreta.");
        }

        validarSenhaForte(novaSenha, usuario); 

        String novaSenhaCriptografada = BCrypt.hashpw(novaSenha, BCrypt.gensalt());
        credencial.setSenha(novaSenhaCriptografada);

        try {
            credRepo.atualizar(credencial);
            System.out.println("Senha alterada com sucesso.");
        } catch (DbException e) {
            throw new BusinessExcepition("Erro ao atualizar senha: " + e.getMessage());
        }
    }

    private void validarSenhaForte(String senha, String usuario) {
        if (senha.length() < 8 || senha.toLowerCase().contains(usuario.toLowerCase())) {
            throw new BusinessExcepition("A senha deve ter pelo menos 8 caracteres e não pode conter o nome de usuário.");
        }
    }
}
