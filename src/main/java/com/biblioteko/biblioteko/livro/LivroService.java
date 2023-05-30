package com.biblioteko.biblioteko.livro;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LivroService {
    @Autowired
    private LivroRepository bibliotecaLivroRepository;

    public String createLivro(LivroDTO livroDTO) {
       
        // para subir essa parte abaixo
        Livro livro = new Livro (livroDTO.getTitle(), livroDTO.getAuthor(), livroDTO.getYear(), livroDTO.getGender(), livroDTO.getSynopsis(), livroDTO.getPublisher(), livroDTO.getLinkString()) ;
        return this.bibliotecaLivroRepository.addBook(livro);
    }

    public Livro getBookByName(String name) {
        return this.bibliotecaLivroRepository.getBookByName(name);
    }

    public Livro getBookById(String id) {
        return this.bibliotecaLivroRepository.getBook(id);
    }

    public boolean editBook(String id,String synopsis) {
        this.bibliotecaLivroRepository.editBook(id, synopsis);
        return true;
    }

    public boolean removeBook(String id) {
        this.bibliotecaLivroRepository.removeBook(id);
        return true;
    }

    public Collection<Livro> getBooks() {
        return this.bibliotecaLivroRepository.getBooks();
    }

    public String getDetailsBooks() {
        String descricao = "";
        for (Livro livro: bibliotecaLivroRepository.getBooks()) {
            descricao += livro.toString() + " \n ";
        }
        return descricao;
    }
}
