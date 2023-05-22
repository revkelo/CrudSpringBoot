package co.edu.unbosque.ProyectoSpringBoot.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoSpringBoot.model.UsuarioCliente;

import java.util.List;
import java.util.Optional;

public interface UsuarioClienteRepository extends CrudRepository<UsuarioCliente, Integer> {
	public Optional<UsuarioCliente> findById(Integer id);

	public List<UsuarioCliente> findAll();

}
