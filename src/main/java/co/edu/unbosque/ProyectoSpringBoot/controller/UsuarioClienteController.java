package co.edu.unbosque.ProyectoSpringBoot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.ProyectoSpringBoot.model.UsuarioCliente;
import co.edu.unbosque.ProyectoSpringBoot.repository.UsuarioClienteRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UsuarioClienteController {
	@Autowired
	private UsuarioClienteRepository usrdao;

	@PostMapping(path = "/cliente")
	public ResponseEntity<String> add(@RequestParam String nombre, @RequestParam Integer edad,
			@RequestParam String email) {
		UsuarioCliente uc = new UsuarioCliente();
		uc.setNombre(nombre);
		uc.setEdad(edad);
		uc.setEmail(email);
		usrdao.save(uc);
		return ResponseEntity.status(HttpStatus.CREATED).body("CREATED (CODE 201)\n");
	}

	@GetMapping("/cliente")
	public ResponseEntity<Iterable<UsuarioCliente>> getAll() {
		List<UsuarioCliente> all = (List<UsuarioCliente>) usrdao.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<UsuarioCliente> getOne(@PathVariable Integer id) {
		Optional<UsuarioCliente> op = usrdao.findById(id);
		if (op.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(op.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		Optional<UsuarioCliente> op = usrdao.findById(id);
		if (!op.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
		usrdao.deleteById(id);
		return ResponseEntity.status(HttpStatus.FOUND).body("Deleted");
	}

	@PutMapping("/cliente/{id}")
	public ResponseEntity<String> update(@RequestBody UsuarioCliente nuevo, @PathVariable Integer id) {

		Optional<UsuarioCliente> op = usrdao.findById(id);
		if (!op.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
		return op.map(usr -> {
			usr.setNombre(nuevo.getNombre());
			usr.setEdad(nuevo.getEdad());
			usr.setEmail(nuevo.getEmail());
			usrdao.save(usr);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Data updated");
		}).orElseGet(() -> {
			nuevo.setId(id);
			usrdao.save(nuevo);
			return ResponseEntity.status(HttpStatus.CREATED).body("Data created");
		});
	}

}
