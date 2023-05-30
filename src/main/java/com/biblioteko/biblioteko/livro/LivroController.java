package com.biblioteko.biblioteko.livro;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Books")
public class LivroController {
    @Autowired 
    private LivroService bibliotecaService;

    @RequestMapping(value = "/book/", method = RequestMethod.POST)
	public ResponseEntity<?> createBook(@RequestBody LivroDTO livroDTO, UriComponentsBuilder ucBuilder) {

		String bookId = bibliotecaService.createLivro(livroDTO);
		return new ResponseEntity<String>("Livro cadastrado com ID:" + bookId, HttpStatus.CREATED);
	}

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultBook(@PathVariable("id") String id) {

		Livro livro;
		livro = bibliotecaService.getBookById(id);
		
			
		return new ResponseEntity<Livro>(livro, HttpStatus.OK);

    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
	public ResponseEntity<?> listBooks() {
		List<Livro> books = (List<Livro>) bibliotecaService.getBooks();
		return new ResponseEntity<List<Livro>>(books, HttpStatus.OK);
	}

    @RequestMapping(value = "/book/{id}", method =  RequestMethod.PUT)
	public ResponseEntity<?> editBook(@PathVariable("id") String id, @RequestParam(value = "synopsis") String synopsis,UriComponentsBuilder ucBuilder) {
		boolean resp = bibliotecaService.editBook(id, synopsis);
		if (resp) {
            return new ResponseEntity<String>(id, HttpStatus.OK);
        }
        return new ResponseEntity<String>("Livro não encontrado", HttpStatus.NO_CONTENT);
 
    }

    @RequestMapping(value = "/book/{id}", method =  RequestMethod.DELETE)
	public ResponseEntity<?> removerProduto(@PathVariable("id") String id) {
		Livro book;
		
		book = bibliotecaService.getBookById(id);
		bibliotecaService.removeBook(id);
		
		return new ResponseEntity<String>("Livro Removido com sucesso", HttpStatus.OK);
	}
}