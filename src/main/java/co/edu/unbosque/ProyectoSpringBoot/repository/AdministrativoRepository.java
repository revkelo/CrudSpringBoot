package co.edu.unbosque.ProyectoSpringBoot.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoSpringBoot.model.Administrativo;


import java.util.List;
import java.util.Optional;

public interface AdministrativoRepository extends CrudRepository<Administrativo, Integer> {
	public Optional<Administrativo> findById(Integer id);

	public List<Administrativo> findAll();

}
