package co.edu.unbosque.ProyectoSpringBoot.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoSpringBoot.model.Docente;


import java.util.List;
import java.util.Optional;

public interface DocenteRepository extends CrudRepository<Docente, Integer> {
	public Optional<Docente> findById(Integer id);

	public List<Docente> findAll();

}
