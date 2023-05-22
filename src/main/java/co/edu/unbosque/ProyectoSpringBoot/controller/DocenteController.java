package co.edu.unbosque.ProyectoSpringBoot.controller;

import java.sql.Date;
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

import co.edu.unbosque.ProyectoSpringBoot.model.*;
import co.edu.unbosque.ProyectoSpringBoot.repository.DocenteRepository;
import co.edu.unbosque.ProyectoSpringBoot.repository.UsuarioClienteRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DocenteController {
	@Autowired
	private DocenteRepository usrdao;

	@PostMapping(path = "/docente")
	public ResponseEntity<String> add(@RequestParam String nombre, @RequestParam Integer edad, @RequestParam Date fecha,
			@RequestParam String facultad, @RequestParam String programa) {
		Docente uc = new Docente();
		uc.setNombre(nombre);
		uc.setEdad(edad);
		uc.setFecha(fecha);
		uc.setFacultad(facultad);
		uc.setPrograma(programa);
		usrdao.save(uc);
		return ResponseEntity.status(HttpStatus.CREATED).body("CREATED (CODE 201)\n");
	}

	@GetMapping("/docente")
	public ResponseEntity<Iterable<Docente>> getAll() {
		List<Docente> all = (List<Docente>) usrdao.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping("/docente/{id}")
	public ResponseEntity<Docente> getOne(@PathVariable Integer id) {
		Optional<Docente> op = usrdao.findById(id);
		if (op.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(op.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@DeleteMapping("/docente/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		Optional<Docente> op = usrdao.findById(id);
		if (!op.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
		usrdao.deleteById(id);
		return ResponseEntity.status(HttpStatus.FOUND).body("Deleted");
	}

	@PutMapping("/docente/{id}")
	public ResponseEntity<String> update(@RequestBody Docente nuevo, @PathVariable Integer id) {

		Optional<Docente> op = usrdao.findById(id);
		if (!op.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
		return op.map(usr -> {
			usr.setNombre(nuevo.getNombre());
			usr.setEdad(nuevo.getEdad());
			usr.setFecha(nuevo.getFecha());
			usr.setFacultad(nuevo.getFacultad());
			usr.setPrograma(nuevo.getPrograma());
			usrdao.save(usr);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Data updated");
		}).orElseGet(() -> {
			nuevo.setId(id);
			usrdao.save(nuevo);
			return ResponseEntity.status(HttpStatus.CREATED).body("Data created");
		});
	}

}
