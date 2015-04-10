package com.login.beachstop.garcom.android.models;

import org.droidpersistence.annotation.Column;
import org.droidpersistence.annotation.PrimaryKey;
import org.droidpersistence.annotation.Table;
import org.droidpersistence.annotation.Transient;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
@SuppressWarnings("serial")
@Table(name = "USUARIO_SETOR")
public class UsuarioSetor extends Base {

    @PrimaryKey
    @Column(name = "ID")
    private Long id;

    @Transient
    private String serviceName;

    @Column(name = "SETOR_ID")
    private Long setorId;

    @Column(name = "USUARIO_ID")
    private Long usuarioId;

    @Transient
    private List<Setor> setores;

    public UsuarioSetor() {
        setServiceName("usuarios_setores");
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getServiceName() {
        return this.serviceName;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsuarioSetor that = (UsuarioSetor) o;

        if (!setorId.equals(that.setorId)) return false;
        if (!usuarioId.equals(that.usuarioId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = setorId.hashCode();
        result = 31 * result + usuarioId.hashCode();
        return result;
    }
}
