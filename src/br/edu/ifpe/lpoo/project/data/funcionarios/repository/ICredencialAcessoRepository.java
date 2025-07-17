package br.edu.ifpe.lpoo.project.data.funcionarios.repository;

import java.util.List;
import br.edu.ifpe.lpoo.project.entities.CredencialAcesso;

public interface ICredencialAcessoRepository {

    void insert(CredencialAcesso credencial);

    boolean existCredencial(String usuario);

    void delete(int idCredencial);

    CredencialAcesso buscarPorId(int idCredencial);

    List<CredencialAcesso> buscarTodos();

    List<CredencialAcesso> buscarPorUsuario(String usuario);

    void atualizar(CredencialAcesso credencial);
}
