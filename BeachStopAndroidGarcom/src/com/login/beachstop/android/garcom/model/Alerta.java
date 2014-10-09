package com.login.beachstop.android.garcom.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Alerta implements Serializable {

	private Long id;
	private TipoAlerta tipoAlerta;
	private Integer mesa;
	private String tempo;
	private String horario;

	public Alerta() {

	}

	public Alerta(Long id, TipoAlerta tipoALerta, Integer mesa, String tempo) {
		this.id = id;
		this.tipoAlerta = tipoALerta;
		this.mesa = mesa;
		this.tempo = tempo;
	}

	public Alerta(JSONObject jsonAlerta) {

		try {

			this.id = jsonAlerta.getLong("id");

			this.tipoAlerta = new TipoAlerta(jsonAlerta.getJSONObject("metrica").getJSONObject("tipoAlerta").getLong("id"), jsonAlerta.getJSONObject("metrica").getJSONObject("tipoAlerta").getString("descricao"));

			this.horario = jsonAlerta.getString("horario");

			this.mesa = jsonAlerta.getJSONObject("conta").getInt("mesa");

		} catch (JSONException e) {

		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoAlerta getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(TipoAlerta tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Integer getMesa() {
		return mesa;
	}

	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alerta other = (Alerta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
