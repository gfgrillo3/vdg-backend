package vdg.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "VistaRestriccionDTO")
public class VistaRestriccionDTO {
	
	@Id
	@Column
	private int idRestriccion;

	@Column
	private int distancia;

	@Column
	private int idAdministrativo;

	@Column
	private int idVictimario;

	@Column
	private String apellidoVictimario;

	@Column
	private String nombreVictimario;

	@Column
	private int idDamnificada;

	@Column
	private String apellidoDamnificada;

	@Column
	private String nombreDamnificada;
	
	public VistaRestriccionDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getIdRestriccion() {
		return idRestriccion;
	}

	public void setIdRestriccion(int idRestriccion) {
		this.idRestriccion = idRestriccion;
	}

	public int getDistanvica() {
		return distancia;
	}

	public void setDistanvica(int distancia) {
		this.distancia = distancia;
	}

	public int getIdAdministrativo() {
		return idAdministrativo;
	}

	public void setIdAdministrativo(int idAdministrativo) {
		this.idAdministrativo = idAdministrativo;
	}

	public int getIdVictimario() {
		return idVictimario;
	}

	public void setIdVictimario(int idVictimario) {
		this.idVictimario = idVictimario;
	}

	public String getApellidoVictimario() {
		return apellidoVictimario;
	}

	public void setApellidoVictimario(String apellidoVictimario) {
		this.apellidoVictimario = apellidoVictimario;
	}

	public String getNombreVictimario() {
		return nombreVictimario;
	}

	public void setNombreVictimario(String nombreVictimario) {
		this.nombreVictimario = nombreVictimario;
	}

	public int getIdDamnificada() {
		return idDamnificada;
	}

	public void setIdDamnificada(int idDamnificada) {
		this.idDamnificada = idDamnificada;
	}

	public String getApellidoDamnificada() {
		return apellidoDamnificada;
	}

	public void setApellidoDamnificada(String apellidoDamnificada) {
		this.apellidoDamnificada = apellidoDamnificada;
	}

	public String getNombreDamnificada() {
		return nombreDamnificada;
	}

	public void setNombreDamnificada(String nombreDamnificada) {
		this.nombreDamnificada = nombreDamnificada;
	}

	
}