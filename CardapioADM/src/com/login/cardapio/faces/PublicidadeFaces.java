package com.login.cardapio.faces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.Agenda;
import com.login.cardapio.model.Publicidade;
import com.login.cardapio.model.TipoAgenda;
import com.login.cardapio.model.TipoPublicidade;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.UsuarioUtil;

@ViewScoped
@ManagedBean(name = "publicidadeFaces")
public class PublicidadeFaces extends CrudFaces<Publicidade> {

	private List<SelectItem> comboTipoPublicidade;
	private List<SelectItem> comboTipoAgenda;
	private TipoAgenda tipoAgenda;
	private List<String> selectedOptionsDiaSemana;
	private String inicio;
	private String fim;

	private final int MAX_LARGURA_LOGO = 5000;
	private final int MAX_ALTURA_LOGO = 5000;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");
		this.tipoAgenda = new TipoAgenda();
		this.tipoAgenda.setId(TipoAgenda.DIARIAMENTE);
		super.getCrudModel().setTipoPublicidade(new TipoPublicidade());
		this.comboTipoPublicidade = super.initCombo(new TipoPublicidade().findByModel("descricao"), "id", "descricao");
		this.comboTipoAgenda = super.initCombo(new TipoAgenda().findByModel("descricao"), "id", "descricao");
	}

	@Override
	protected void prePersist() {

		super.getCrudModel().setEmpresa(UsuarioUtil.obterUsuarioConectado().getEmpresa());

		super.getCrudModel().setAgenda(new HashSet<Agenda>());
		Agenda agenda;
		if ( TipoAgenda.DIARIAMENTE.equals(this.tipoAgenda.getId()) ) {
		
			agenda = new Agenda();
			agenda.setPublicidade(getCrudModel());
			agenda.setTipoAgenda(new TipoAgenda(this.tipoAgenda.getId()));
			super.getCrudModel().getAgenda().add(agenda);
		
		} else if (TipoAgenda.SEMANALMENTE.equals(this.tipoAgenda.getId()) ) {
		
			for (String diaSemana : selectedOptionsDiaSemana) {
				agenda = new Agenda();
				agenda.setTipoAgenda(new TipoAgenda(this.tipoAgenda.getId()));
				agenda.setValor(diaSemana);
				agenda.setPublicidade(getCrudModel());
				super.getCrudModel().getAgenda().add(agenda);
			}
			
		} else if ( TipoAgenda.MENSALMENTE.equals(this.tipoAgenda.getId()) ) {
		
			agenda = new Agenda();
			agenda.setTipoAgenda(new TipoAgenda(this.tipoAgenda.getId()));
			agenda.setValor(inicio);
			super.getCrudModel().getAgenda().add(agenda);
			agenda.setPublicidade(getCrudModel());
			agenda = new Agenda();
			agenda.setTipoAgenda(new TipoAgenda(this.tipoAgenda.getId()));
			agenda.setValor(fim);
			agenda.setPublicidade(getCrudModel());
			super.getCrudModel().getAgenda().add(agenda);
			
		}
	}

	@Override
	protected String detail() {
		super.detail();

		this.tipoAgenda = (super.getCrudModel().getAgenda().iterator().next()).getTipoAgenda();

		if (TipoAgenda.SEMANALMENTE.equals(this.tipoAgenda.getId()) ) {
		
			this.selectedOptionsDiaSemana = new ArrayList<String>();
			
			for (Agenda agenda: super.getCrudModel().getAgenda()) {
				this.selectedOptionsDiaSemana.add(agenda.getValor());
			}
		
		} else if (TipoAgenda.MENSALMENTE.equals(this.tipoAgenda.getId())) {
		
			this.inicio = ((Agenda) (super.getCrudModel().getAgenda().toArray()[0])).getValor();
			this.fim = ((Agenda) (super.getCrudModel().getAgenda().toArray()[1])).getValor();
		
		}

		return null;
	}

	@Override
	public String limparPesquisa() {
		String retorno = super.limparPesquisa();
		super.getCrudPesquisaModel().setEmpresa(UsuarioUtil.obterUsuarioConectado().getEmpresa());
		return retorno;
	}

	public void uploadMidias(FileUploadEvent event) {

		Calendar now = Calendar.getInstance();
		String nomeArquivo = "";
		nomeArquivo += String.valueOf(now.get(Calendar.YEAR));
		nomeArquivo += String.valueOf(now.get(Calendar.MONTH));
		nomeArquivo += String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		nomeArquivo += String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		nomeArquivo += String.valueOf(now.get(Calendar.MINUTE));
		nomeArquivo += String.valueOf(now.get(Calendar.SECOND));
		nomeArquivo += String.valueOf(now.get(Calendar.MILLISECOND));
		nomeArquivo += "." + FilenameUtils.getExtension(event.getFile().getFileName());

		this.getCrudModel().setImagem(nomeArquivo);

		CardapioUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO + this.getCrudModel().getImagem());

		if (!validarDimensaoMidia()) {
			new File(Constantes.CAMINHO_ARQUIVO + this.getCrudModel().getImagem()).delete();
		}
	}

	private boolean validarDimensaoMidia() {

		boolean valido = true;

		BufferedImage imagem = null;

		try {

			imagem = ImageIO.read(new File(Constantes.CAMINHO_ARQUIVO + this.getCrudModel().getImagem()));

			if (!TSUtil.isEmpty(imagem)) {

				if (imagem.getWidth() > MAX_LARGURA_LOGO || imagem.getHeight() > MAX_ALTURA_LOGO) {

					this.addErrorMessage("Dimensão da imagem está muito grande, máximo de 245px x 95px!");

					valido = false;

				}

			}

		} catch (Exception ex) {

			throw new TSSystemException(ex);

		}

		return valido;

	}

	public List<SelectItem> getComboTipoPublicidade() {
		return comboTipoPublicidade;
	}

	public void setComboTipoPublicidade(List<SelectItem> comboTipoPublicidade) {
		this.comboTipoPublicidade = comboTipoPublicidade;
	}

	public List<SelectItem> getComboTipoAgenda() {
		return comboTipoAgenda;
	}

	public void setComboTipoAgenda(List<SelectItem> comboTipoAgenda) {
		this.comboTipoAgenda = comboTipoAgenda;
	}

	public TipoAgenda getTipoAgenda() {
		return tipoAgenda;
	}

	public void setTipoAgenda(TipoAgenda tipoAgenda) {
		this.tipoAgenda = tipoAgenda;
	}

	public List<String> getSelectedOptionsDiaSemana() {
		return selectedOptionsDiaSemana;
	}

	public void setSelectedOptionsDiaSemana(List<String> selectedOptionsDiaSemana) {
		this.selectedOptionsDiaSemana = selectedOptionsDiaSemana;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}
}
