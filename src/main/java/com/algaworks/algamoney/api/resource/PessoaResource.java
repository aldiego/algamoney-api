package com.algaworks.algamoney.api.resource;

import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

  @Autowired
  private PessoaRepository repository;

  @Autowired
  private ApplicationEventPublisher publisher;

  @PostMapping
  public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa,
      HttpServletResponse response) {
    Pessoa pessoaSalva = repository.save(pessoa);
    publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
    return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
  }

  @GetMapping("/{codigo}")
  public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
    Optional<Pessoa> pessoa = repository.findById(codigo);
    return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get())
        : ResponseEntity.notFound().build();
  }
}