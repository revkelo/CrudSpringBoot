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
import co.edu.unbosque.ProyectoSpringBoot.repository.AdministrativoRepository;
import co.edu.unbosque.ProyectoSpringBoot.repository.UsuarioClienteRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AdministrativoController {
	@Autowired
	private AdministrativoRepository usrdao;

	@PostMapping(path = "/admin")
	public ResponseEntity<String> add(@RequestParam String nombre, @RequestParam Integer cedula,@RequestParam Date fecha,
			@RequestParam String facultad, @RequestParam String cargo) {
		Administrativo uc = new Administrativo();
		uc.setNombre(nombre);
		uc.setCedula(cedula);
		uc.setFecha(fecha);
		uc.setFacultad(facultad);
		uc.setCargo(cargo);
		usrdao.save(uc);
		return ResponseEntity.status(HttpStatus.CREATED).body("CREATED (CODE 201)\n");
	}

	@GetMapping("/admin")
	public ResponseEntity<Iterable<Administrativo>> getAll() {
		List<Administrativo> all = (List<Administrativo>) usrdao.findAll();
		if (all.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(all);
	}

	@GetMapping("/admin/{id}")
	public ResponseEntity<Administrativo> getOne(@PathVariable Integer id) {
		Optional<Administrativo> op = usrdao.findById(id);
		if (op.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(op.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@DeleteMapping("/admin/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		Optional<Administrativo> op = usrdao.findById(id);
		if (!op.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
		usrdao.deleteById(id);
		return ResponseEntity.status(HttpStatus.FOUND).body("Deleted");
	}

	@PutMapping("/admin/{id}")
	public ResponseEntity<String> update(@RequestBody Administrativo nuevo, @PathVariable Integer id) {

		Optional<Administrativo> op = usrdao.findById(id);
		if (!op.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
		return op.map(usr -> {
			usr.setNombre(nuevo.getNombre());
			usr.setCedula(nuevo.getCedula());
			usr.setFecha(nuevo.getFecha());
			usr.setCargo(nuevo.getCargo());
			usr.setFacultad(nuevo.getFacultad());
			usrdao.save(usr);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("Data updated");
		}).orElseGet(() -> {
			nuevo.setId(id);
			usrdao.save(nuevo);
			return ResponseEntity.status(HttpStatus.CREATED).body("Data created");
		});
	}

}
