package com.biblioteko.biblioteko.livro;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo.None;
@Repository
public class LivroRepository {
    private Map<String, Livro> biblioteca = new HashMap<>();

    public String addBook(Livro livro) {
        biblioteca.put(livro.getID(), livro);
        return livro.getID();
    }
    public boolean editBook(String id, String synopsis) {
        biblioteca.get(id).setSynopsis(synopsis);
        return true;
    }
    public boolean removeBook(String id) {
        biblioteca.remove(id);
        return true;
    }

    public String listLivroIdName() {
        String lista = "";
		for (Livro livro: biblioteca.values()) {
			lista += livro.getID() +" --- " + livro.getTitle() + "\n";
		}
		return lista;
    }

    public Livro getBook(String id) {
        return biblioteca.get(id);
    }

    public Collection<Livro> getBooks() {
        return biblioteca.values();
    }
    public String listBook() {
		String lista = "";
		for (Livro livro: biblioteca.values()) {
			lista += livro.toString() + "\n";
		}
		return lista;
	}

    public Livro getBookByName(String title) {
        Livro book = null;
		for (Livro livro: biblioteca.values()) {
			if (livro.getTitle()== title) {
                book = livro;
            }
		}
		return book;
	}
}

