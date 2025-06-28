package br.edu.ifpe.lpoo.project.data;


import br.edu.ifpe.lpoo.project.data.implement.EbookRepository;
import br.edu.ifpe.lpoo.project.data.implement.LivroRepository;
import br.edu.ifpe.lpoo.project.entities.acervo.Ebook;
import br.edu.ifpe.lpoo.project.entities.acervo.Livro;
import br.edu.ifpe.lpoo.project.enums.FormatoDigital;
public class Teste {

	public static void main(String[] args) {

		IEbookRepository ebookRepository = new EbookRepository();
		
		Ebook hobbit = new Ebook("O Hobbit", "J.R.R. Tolkien", 1937, "HarperCollins Brasil", "Português", "9788595084742", 336, "Fantasia", FormatoDigital.PDF,
			    "https://www.exemplo.com/o-hobbit.pdf");
		
		hobbit.setId(1);
		
		ebookRepository.atualizar(hobbit);
		
	}
}
