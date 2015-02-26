package com.login.cardapio.faces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;

import com.login.cardapio.model.Agenda;
import com.login.cardapio.model.Publicidade;
import com.login.cardapio.model.TipoAgenda;
import com.login.cardapio.model.TipoPublicidade;
import com.login.cardapio.model.Token;
import com.login.cardapio.model.TokenIPhone;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.EnviarMensagem;
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
		if (TipoAgenda.DIARIAMENTE.equals(this.tipoAgenda.getId())) {

			agenda = new Agenda();
			agenda.setPublicidade(getCrudModel());
			agenda.setTipoAgenda(new TipoAgenda(this.tipoAgenda.getId()));
			super.getCrudModel().getAgenda().add(agenda);

		} else if (TipoAgenda.SEMANALMENTE.equals(this.tipoAgenda.getId())) {

			for (String diaSemana : selectedOptionsDiaSemana) {
				agenda = new Agenda();
				agenda.setTipoAgenda(new TipoAgenda(this.tipoAgenda.getId()));
				agenda.setValor(diaSemana);
				agenda.setPublicidade(getCrudModel());
				super.getCrudModel().getAgenda().add(agenda);
			}

		} else if (TipoAgenda.MENSALMENTE.equals(this.tipoAgenda.getId())) {

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

		if (TipoAgenda.SEMANALMENTE.equals(this.tipoAgenda.getId())) {

			this.selectedOptionsDiaSemana = new ArrayList<String>();

			for (Agenda agenda : super.getCrudModel().getAgenda()) {
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

	public String enviarPush() {
		enviarPushIphone();
		enviarPushAndroid();
		this.addInfoMessage("Alerta enviado com sucesso!");
		return null;

	}

	private int enviarPushIphone() {

		int retorno = 0;

		List<TokenIPhone> iphones = new TokenIPhone().findAll();

		PushNotificationPayload payload = PushNotificationPayload.complex();

		try {

			payload.addAlert(getCrudModel().getDescricao());

			payload.addSound("default");

			payload.addBadge(0);

			String[] tokens = new String[iphones.size()];

			for (int i = 0; i < iphones.size(); i++) {
				tokens[i] = iphones.get(i).getToken();
			}

			String caminhoP12 = TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + "assets" + File.separator + Constantes.P12);

			Push.payload(payload, caminhoP12, Constantes.CHAVE_P12, false, tokens);
			
		} catch (JSONException e) {

			e.printStackTrace();
		} catch (CommunicationException e) {

			e.printStackTrace();
		} catch (KeystoreException e) {

			e.printStackTrace();
		}

		return retorno;
	}

	private int enviarPushAndroid() {

		int retorno = 0;
		List<Token> token = new Token().findAll();

		int a = 1000;
		for (int i = 0; i < token.size(); i = i + a) {

			List<String> tokenString = new ArrayList<String>();

			if (token.size() >= (i + a)) {
				for (Token token2 : token.subList(i, i + a - 1)) {
					tokenString.add(token2.getToken());
				}
			} else {
				for (Token token2 : token) {
					tokenString.add(token2.getToken());
					retorno++;
				}
			}

			EnviarMensagem.enviar(tokenString, getCrudModel().getDescricao());

		}

		return retorno;
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
