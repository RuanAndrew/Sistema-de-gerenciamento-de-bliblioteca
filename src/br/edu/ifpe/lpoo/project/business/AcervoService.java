package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.AcervoRepository;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;

public class AcervoService {
    public void CadastrarLivro (String titulo, String[] autor, String anoPublicacao, String[] editora, String isbn, Integer numeroPaginas, String[] genero, String[] idioma) {
        if (titulo.isBlank()) {
            throw new BusinessExcepition("O campo titulo não pode ser vazio.");
        }
        if (autor.length == 0) {
            throw new BusinessExcepition("O campo autor não pode ser vazio.");
        }
        if (anoPublicacao.isBlank()) {
            throw new BusinessExcepition("O campo ano de publicação não pode ser vazio.");
        }
        if (numeroPaginas == null) {
            throw new BusinessExcepition("O campo numero de paginas não pode ser vazio.");
        }
        if (genero.length == 0) {
            throw new BusinessExcepition("O campo genero não pode ser vazio.");
        }
        if (idioma.length == 0) {
            throw new BusinessExcepition("O campo idioma não pode ser vazio.");
        }
        
        
        
        //Livro livro = new Livro(titulo,autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero);

        /*boolean exist = AcervoRepository.existItem(titulo); //titulo e um identificador temporario

        if (exist) {
            throw new BusinessExcepition("Esse livro ja esta cadastrado no sistema");
        }
        AcervoRepository.insert(livro);*/
    }
}
