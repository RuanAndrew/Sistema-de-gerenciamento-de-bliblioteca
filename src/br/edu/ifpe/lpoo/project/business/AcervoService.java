package br.edu.ifpe.lpoo.project.business;

import br.edu.ifpe.lpoo.project.data.IExemplarRepository;
import br.edu.ifpe.lpoo.project.data.ILivroRepository;
import br.edu.ifpe.lpoo.project.data.implement.ExemplarRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.Exemplar;
import br.edu.ifpe.lpoo.project.entities.Livro;
import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.exceptions.BusinessExcepition;
import br.edu.ifpe.lpoo.project.exceptions.DbException;


public class AcervoService {
    public void CadastrarLivro (String titulo, String autor, Integer anoPublicacao, String editora, String isbn, Integer numeroPaginas, String genero, String idioma, int quantidadeExemplares) {
        if (titulo.isBlank()) {
            throw new BusinessExcepition("O campo titulo não pode ser vazio.");
        }
        if (autor.isBlank()) {
            throw new BusinessExcepition("O campo autor não pode ser vazio.");
        }
        if (anoPublicacao == null) {
            throw new BusinessExcepition("O campo ano de publicação não pode ser vazio.");
        }
        if (numeroPaginas == null) {
            throw new BusinessExcepition("O campo numero de paginas não pode ser vazio.");
        }
        if (genero.isBlank()) {
            throw new BusinessExcepition("O campo genero não pode ser vazio.");
        }
        if (idioma.isBlank()) {
            throw new BusinessExcepition("O campo idioma não pode ser vazio.");
        }

        //Uma sugestão para testar

         Livro livro = new Livro(titulo,autor, anoPublicacao, editora, idioma, isbn, numeroPaginas, genero);
        ILivroRepository livroRepository = new LivroRepository();
        IExemplarRepository exemplarRepository = new ExemplarRepository();
        
        boolean exist = livroRepository.existItem(titulo);
        
        if(!exist) {
        	int idLivro = -1;
        	try {
        		livroRepository.insert(livro);
        		idLivro = livro.getId();
        	}catch(DbException e) {
        		throw new BusinessExcepition(e.getMessage());
        	}
        	
        	for (int i = 1; i <= quantidadeExemplares; i++) {
        		
        		try {
        			String registro = idLivro + "EXP" + i;
            		Exemplar exemplar = new Exemplar(idLivro, registro, StatusExemplar.DISPONIVEL);
            		exemplarRepository.insert(exemplar, idLivro);
        		}catch (DbException e) {
        			throw new BusinessExcepition(e.getMessage());
        		}
        	}
        	
        }else {
        	throw new BusinessExcepition("Esse livro ja esta cadastrado no sistema");
        }

    }
}
