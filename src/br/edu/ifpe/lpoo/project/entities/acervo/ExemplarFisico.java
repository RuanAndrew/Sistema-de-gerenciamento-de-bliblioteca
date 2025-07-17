package br.edu.ifpe.lpoo.project.entities.acervo;

import br.edu.ifpe.lpoo.project.enums.StatusExemplar;
import br.edu.ifpe.lpoo.project.enums.TipoItemAcervo;

public class ExemplarFisico extends Exemplar{

    public ExemplarFisico(int idItem, TipoItemAcervo tipoItemAcervo, String registro, StatusExemplar status) {
        super(idItem, tipoItemAcervo, registro, status);
    }

}
