package uy.gub.imm.spring.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FiltroLineasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombreLinea;

	private Long codigoLinea;

	private String estadoLinea;

	private Long subsistemaSelected;

	private Long empresaSelected;

	private Long tipoSeleccionado;

	private String tarifaXKm;

	private LocalDateTime fechaVigenciaDesde;

	private LocalDateTime fechaVigenciaHasta;

	private LocalDateTime fechaPubWebDesde;

	private LocalDateTime fechaPubWebHasta;

	private LocalDateTime fechaBajaDesde;

	private LocalDateTime fechaBajaHasta;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	public FiltroLineasDTO() {
		super();
	}

	public String getNombreLinea() {
		return nombreLinea;
	}

	public void setNombreLinea(String nombreLinea) {
		this.nombreLinea = nombreLinea;
	}

	public Long getCodigoLinea() {
		return codigoLinea;
	}

	public void setCodigoLinea(Long codigoLinea) {
		this.codigoLinea = codigoLinea;
	}

	public String getEstadoLinea() {
		return estadoLinea;
	}

	public void setEstadoLinea(String estadoLinea) {
		this.estadoLinea = estadoLinea;
	}

	public Long getSubsistemaSelected() {
		return subsistemaSelected;
	}

	public void setSubsistemaSelected(Long subsistemaSelected) {
		this.subsistemaSelected = subsistemaSelected;
	}

	public Long getEmpresaSelected() {
		return empresaSelected;
	}

	public void setEmpresaSelected(Long empresaSelected) {
		this.empresaSelected = empresaSelected;
	}

	public Long getTipoSeleccionado() {
		return tipoSeleccionado;
	}

	public void setTipoSeleccionado(Long tipoSeleccionado) {
		this.tipoSeleccionado = tipoSeleccionado;
	}

	public String getTarifaXKm() {
		return tarifaXKm;
	}

	public void setTarifaXKm(String tarifaXKm) {
		this.tarifaXKm = tarifaXKm;
	}

	public LocalDateTime getFechaVigenciaDesde() {
		return fechaVigenciaDesde;
	}

	public void setFechaVigenciaDesde(LocalDateTime fechaVigenciaDesde) {
		this.fechaVigenciaDesde = fechaVigenciaDesde;
	}

	public LocalDateTime getFechaVigenciaHasta() {
		return fechaVigenciaHasta;
	}

	public void setFechaVigenciaHasta(LocalDateTime fechaVigenciaHasta) {
		this.fechaVigenciaHasta = fechaVigenciaHasta;
	}

	public LocalDateTime getFechaPubWebDesde() {
		return fechaPubWebDesde;
	}

	public void setFechaPubWebDesde(LocalDateTime fechaPubWebDesde) {
		this.fechaPubWebDesde = fechaPubWebDesde;
	}

	public LocalDateTime getFechaPubWebHasta() {
		return fechaPubWebHasta;
	}

	public void setFechaPubWebHasta(LocalDateTime fechaPubWebHasta) {
		this.fechaPubWebHasta = fechaPubWebHasta;
	}

	public LocalDateTime getFechaBajaDesde() {
		return fechaBajaDesde;
	}

	public void setFechaBajaDesde(LocalDateTime fechaBajaDesde) {
		this.fechaBajaDesde = fechaBajaDesde;
	}

	public LocalDateTime getFechaBajaHasta() {
		return fechaBajaHasta;
	}

	public void setFechaBajaHasta(LocalDateTime fechaBajaHasta) {
		this.fechaBajaHasta = fechaBajaHasta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FiltroLineasDTO [nombreLinea=" + nombreLinea + ", codigoLinea=" + codigoLinea + ", estadoLinea="
				+ estadoLinea + ", subsistemaSelected=" + subsistemaSelected + ", empresaSelected=" + empresaSelected
				+ ", tipoSeleccionado=" + tipoSeleccionado + ", tarifaXKm=" + tarifaXKm + ", fechaVigenciaDesde="
				+ fechaVigenciaDesde + ", fechaVigenciaHasta=" + fechaVigenciaHasta + ", fechaPubWebDesde="
				+ fechaPubWebDesde + ", fechaPubWebHasta=" + fechaPubWebHasta + ", fechaBajaDesde=" + fechaBajaDesde
				+ ", fechaBajaHasta=" + fechaBajaHasta + ", fecha=" + fecha + "]";
	}

}
