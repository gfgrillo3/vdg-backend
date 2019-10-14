package vdg.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vdg.model.domain.EstadoNotificacion;
import vdg.model.domain.Notificacion;
import vdg.repository.NotificacionRepository;
import vdg.repository.UsuarioRepository;

@RestController
@RequestMapping("/Notificacion")
@CrossOrigin
public class NotificacionController {

	@Autowired
	private NotificacionRepository notificacionRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;

	

	@GetMapping("/{idUsuario}")
	public List<Notificacion> listar(@PathVariable("idUsuario") int idUsuario) {
		return notificacionRepo.findByIdUsuario(idUsuario);
	}
	
	@GetMapping("/getNotificaciones/{emailUsuario}")
	public List<Notificacion> getNotificaciones(@PathVariable("emailUsuario") String emailUsuario) {
		int idUsuario = usuarioRepo.findByEmail(emailUsuario).get(0).getIdUsuario();
		return notificacionRepo.findVistasNoVistas(idUsuario);
	}
	
	@GetMapping("/getArchivadas/{emailUsuario}")
	public List<Notificacion> getArchivadas(@PathVariable("emailUsuario") String emailUsuario) {
		int idUsuario = usuarioRepo.findByEmail(emailUsuario).get(0).getIdUsuario();
		return notificacionRepo.findArchivadas(idUsuario);
	}
	
	@GetMapping("/getCantNoVistas/{emailUsuario}")
	public int getCantNoVistas(@PathVariable("emailUsuario") String emailUsuario) {
		int idUsuario = usuarioRepo.findByEmail(emailUsuario).get(0).getIdUsuario();
		System.out.println("LA CANTIDAD DE NOTIFICACIONES ES: "+notificacionRepo.countByEstadoNotificacionAndIdUsuario(EstadoNotificacion.NoVista,idUsuario));
		return notificacionRepo.countByEstadoNotificacionAndIdUsuario(EstadoNotificacion.NoVista,idUsuario);
	}
	
	@PostMapping("/archivar")
	public Notificacion archivar(@RequestBody int idNotificacion) {
		Notificacion notificacion = notificacionRepo.findByIdNotificacion(idNotificacion);
		notificacion.setEstado(EstadoNotificacion.Archivada);
		notificacionRepo.save(notificacion);
		return notificacion;
	}
	
	@PostMapping("/setVista")
	public Notificacion setVista(@RequestBody int idNotificacion) {
		Notificacion notificacion = notificacionRepo.findByIdNotificacion(idNotificacion);
		notificacion.setEstado(EstadoNotificacion.Vista);
		notificacionRepo.save(notificacion);
		return notificacion;
	}
		
}
