package br.edu.ifpe.lpoo.project.data;

import br.edu.ifpe.lpoo.project.entities.ItemArcevo;

public interface IAcervoRepository {

    public void insert(ItemArcevo item) {

    }

    public boolean existItem(String indentifier) {
        return false;
    }
}
