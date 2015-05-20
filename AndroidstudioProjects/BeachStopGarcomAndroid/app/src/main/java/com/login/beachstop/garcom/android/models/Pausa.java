package com.login.beachstop.garcom.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

/**
 * Created by Argus on 30/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "PAUSA")
public class Pausa extends Base {

    @Column(name = "ID")
    protected Long id;

    @Column(name = "USUARIO_ID")
    private Long usuarioId;

    @Column(name = "HORARIO_INICIAL")
    private String horarioInicial;

    @Column(name = "HORARIO_FINAL")
    private String horarioFinal;

    @Transient
    private String horarioDiff;

    @Transient
    protected String serviceName;

    public Pausa() {
        this.setServiceName("pausas");
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }


    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public String getHorarioDiff() {
        return horarioDiff;
    }

    public void setHorarioDiff(String horarioDiff) {
        this.horarioDiff = horarioDiff;
    }
}
