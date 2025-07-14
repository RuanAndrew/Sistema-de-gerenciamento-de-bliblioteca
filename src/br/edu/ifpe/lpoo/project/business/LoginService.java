package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.entities.CredencialAcesso;
import br.edu.ifpe.lpoo.project.repository.CredencialAcessoRepository;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {
    private CredencialAcessoRepository credRepo = new CredencialAcessoRepository();

    public boolean fazerLogin(String usuario, String senhaDigitada) {
        CredencialAcesso cred = credRepo.buscarPorLogin(usuario);

        if (cred != null && BCrypt.checkpw(senhaDigitada, cred.getSenha())) {
            System.out.println("Login realizado com sucesso.");
            return true;
        } else {
            System.out.println("usuario ou senha inválidos.");
            return false;
        }
    }
    public void alterarSenhaPorUsuario(String usuario, String senhaAntiga, String novaSenha) {
        CredencialAcessoRepository credRepo = new CredencialAcessoRepository();
        CredencialAcesso credencial = credRepo.buscarPorUsuario(usuario);

        if (credencial == null) {
            throw new BusinessExcepition("Credencial não encontrada para o usuário informado.");
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

}
