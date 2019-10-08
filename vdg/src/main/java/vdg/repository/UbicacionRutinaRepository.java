package vdg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import vdg.model.controladorRutina.UbicacionRutina;

public interface UbicacionRutinaRepository extends Repository<UbicacionRutina, Integer>{
	
	public List<UbicacionRutina> findAll();	
	public UbicacionRutina save(UbicacionRutina ubicacionRutina);
	public void delete(UbicacionRutina ubicacionRutina);
	@Query(value = "SELECT * FROM UbicacionRutina u WHERE u.idPersona=?1", nativeQuery = true)
	public List<UbicacionRutina> findByidPersona(int idPersona);

}
