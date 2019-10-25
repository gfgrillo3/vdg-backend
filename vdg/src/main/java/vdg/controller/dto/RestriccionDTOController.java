package vdg.controller.dto;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vdg.controller.PersonaController;
import vdg.controller.UbicacionController;
import vdg.controller.UsuarioController;
import vdg.model.domain.Persona;
import vdg.model.domain.RestriccionPerimetral;
import vdg.model.domain.RolDeUsuario;
import vdg.model.domain.Usuario;
import vdg.model.dto.RestriccionDTO;
import vdg.model.dto.UbicacionDTO;
import vdg.model.validadores.ValidadoresFormPersona;
import vdg.repository.RestriccionPerimetralRepository;

@RestController
@RequestMapping("/RestriccionDTO")
@CrossOrigin
public class RestriccionDTOController {
	@Autowired
	PersonaController personaController;
	@Autowired
	UsuarioController usuarioController;
	@Autowired
	UbicacionController ubicacionController;
	@Autowired
	ValidadoresFormPersona validador = new ValidadoresFormPersona();
	@Autowired
	private RestriccionPerimetralRepository restriccionPerimetralRepo;

	@GetMapping
	public List<RestriccionDTO> listar() {
		List<RestriccionPerimetral> restricciones = restriccionPerimetralRepo.findAll();
		return crearRestriccionesDto(restricciones);

	}

	@GetMapping("/getByUsuario/{email}")
	public List<RestriccionDTO> getByUsuario(@PathVariable("email") String email) {
		Usuario usuario = usuarioController.findByEmail(email);
		List<RestriccionPerimetral> restricciones = restriccionPerimetralRepo.findByIdUsuario(usuario.getIdUsuario());
		return crearRestriccionesDto(restricciones);
	}

	@GetMapping("/getByUsuarioApp/{email}")
	public List<RestriccionDTO> getByUsuarioApp(@PathVariable("email") String email) {
		Usuario usuario = usuarioController.findByEmail(email);
		Persona persona = personaController.getByIdUsuario(usuario.getIdUsuario());
		List<RestriccionDTO> restricciones = getByPersona(persona, usuario.getRolDeUsuario());
		// Ahora recorro las restricciones. Obtengo la ubiDTO de cada una, y me fijo si
		// infringe. Si infringe, la agrego para devolver Para que vea la ubicacion
		List<RestriccionDTO> ret = new ArrayList<>();
		for (RestriccionDTO restriccion : restricciones) {
			int idRestriccion = restriccion.getRestriccion().getIdRestriccion();
			UbicacionDTO ubicacionDTO = ubicacionController.findByRestriccion(idRestriccion);
			if(ubicacionController.estaInfringiendo(idRestriccion, ubicacionDTO)) {
				ret.add(restriccion);
			}
		}
		return ret;
	}

	@GetMapping("/getByDamnificada/{id}")
	public List<RestriccionDTO> getByDamnificada(@PathVariable("id") int idDamnificada) {
		Persona damnificada = personaController.getById(idDamnificada);
		return getByPersona(damnificada, RolDeUsuario.DAMNIFICADA);
	}

	@GetMapping("/getByVictimario/{id}")
	public List<RestriccionDTO> getByVictimario(@PathVariable("id") int idVictimario) {
		Persona victimario = personaController.getById(idVictimario);
		return getByPersona(victimario, RolDeUsuario.VICTIMARIO);
	}

	private List<RestriccionDTO> getByPersona(Persona persona, RolDeUsuario rol) {
		List<RestriccionPerimetral> restricciones = new ArrayList<>();
		if (rol.equals(RolDeUsuario.DAMNIFICADA))
			restricciones = restriccionPerimetralRepo.findByIdDamnificada(persona.getIdPersona());
		else
			restricciones = restriccionPerimetralRepo.findByIdVictimario(persona.getIdPersona());
		return crearRestriccionesDto(restricciones);
	}

	private List<RestriccionDTO> crearRestriccionesDto(List<RestriccionPerimetral> restricciones) {
		List<RestriccionDTO> ret = new ArrayList<RestriccionDTO>();
		Persona victimario;
		Persona damnificada;
		Usuario usuario;
		RestriccionDTO restriccionDTO;

		for (RestriccionPerimetral restriccion : restricciones) {
			usuario = usuarioController.findByIdUsuario(restriccion.getIdUsuario());
			victimario = personaController.getById(restriccion.getIdVictimario());
			damnificada = personaController.getById(restriccion.getIdDamnificada());

			restriccionDTO = new RestriccionDTO(damnificada, victimario, usuario, restriccion);
			ret.add(restriccionDTO);
		}
		return ret;
	}

}
