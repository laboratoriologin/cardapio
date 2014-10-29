package com.login.cardapio.faces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.Area;
import com.login.cardapio.model.Categoria;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.Utilitarios;

@ViewScoped
@ManagedBean(name = "categoriaFaces")
public class CategoriaFaces extends CrudFaces<Categoria> {

	private static final String TOPO = "topo_";
	private List<SelectItem> comboArea;
	private final int MAX_LARGURA = 230;
	private final int MAX_ALTURA = 260;
	private final int MAX_SIZE_TOPO = 51;
	private String nomeArquivo = "";
	private boolean uploadImagemMenu = false;
	private boolean uploadImagemTopo = false;

	private static final long serialVersionUID = 1L;

	@PostConstruct
	protected void init() {

		this.clearFields();

		setFieldOrdem("descricao");

		this.comboArea = super.initCombo(new Area().findByModel("descricao"), "id", "descricao");

	}

	@Override
	public String limpar() {
		super.limpar();
		this.nomeArquivo = "";
		Calendar now = Calendar.getInstance();
		nomeArquivo += String.valueOf(now.get(Calendar.YEAR));
		nomeArquivo += String.valueOf(now.get(Calendar.MONTH));
		nomeArquivo += String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		nomeArquivo += String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		nomeArquivo += String.valueOf(now.get(Calendar.MINUTE));
		nomeArquivo += String.valueOf(now.get(Calendar.SECOND));
		nomeArquivo += String.valueOf(now.get(Calendar.MILLISECOND));
		nomeArquivo += ".jpg";
		return null;
	}

	@Override
	public String limparPesquisa() {

		String retorno = super.limparPesquisa();

		return retorno;

	}

	@Override
	protected String detail() {
		super.detail();
		this.nomeArquivo = this.getCrudModel().getImagem();
		return null;
	}

	@Override
	protected void prePersist() {

		super.prePersist();

		this.getCrudModel().setImagem(this.nomeArquivo);

		try {

			Utilitarios.gerarNovoCodigoCardapio();

		} catch (TSApplicationException e) {

			e.printStackTrace();

			this.addErrorMessage("Erro no sistema, entre em contato com o administrador, Erro: 0101!");

		}

	}

	public void uploadMidia(FileUploadEvent event) {

		this.getCrudModel().setImagem(nomeArquivo);

		CardapioUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO + this.nomeArquivo);

		if (!validarDimensaoMidia()) {
			new File(Constantes.CAMINHO_ARQUIVO + this.nomeArquivo).delete();
		} else {
			uploadImagemMenu = true;
		}
	}

	public void uploadMidiaTopo(FileUploadEvent event) {

		CardapioUtil.criaArquivo(event.getFile(), Constantes.CAMINHO_ARQUIVO + TOPO + nomeArquivo);

		if (!validarDimensaoMidiaTopo()) {
			new File(Constantes.CAMINHO_ARQUIVO + Constantes.CAMINHO_ARQUIVO + TOPO + nomeArquivo).delete();
		} else {
			uploadImagemTopo = true;
		}
	}

	private boolean validarDimensaoMidia() {

		boolean valido = true;

		BufferedImage imagem = null;

		try {

			imagem = ImageIO.read(new File(Constantes.CAMINHO_ARQUIVO + this.nomeArquivo));

			if (!TSUtil.isEmpty(imagem)) {

				if (imagem.getWidth() > MAX_LARGURA || imagem.getHeight() > MAX_ALTURA) {

					this.addErrorMessage("Dimensão da imagem está muito grande, máximo de 230px x 260px!");

					valido = false;

				}

			}

		} catch (Exception ex) {

			throw new TSSystemException(ex);

		}

		return valido;

	}

	private boolean validarDimensaoMidiaTopo() {

		boolean valido = true;

		BufferedImage imagem = null;

		try {

			imagem = ImageIO.read(new File(Constantes.CAMINHO_ARQUIVO + TOPO + nomeArquivo));

			if (!TSUtil.isEmpty(imagem)) {

				if (imagem.getWidth() > MAX_SIZE_TOPO || imagem.getHeight() > MAX_SIZE_TOPO) {

					this.addErrorMessage("Dimensão da imagem do topo está muito grande, máximo de 51px x 51px!");

					valido = false;

				}

			}

		} catch (Exception ex) {

			throw new TSSystemException(ex);

		}

		return valido;

	}

	@Override
	protected void posPersist() throws TSApplicationException {

		super.posPersist();

		this.uploadImagemMenu = false;

		this.uploadImagemTopo = false;

	}

	@Override
	protected boolean validaCampos() {

		if (!this.uploadImagemMenu || !this.uploadImagemTopo) {

			this.addErrorMessage("Faça o upload das duas imagens!");

			return false;

		}

		return true;

	}

	public List<SelectItem> getComboArea() {
		return comboArea;
	}

	public void setComboArea(List<SelectItem> comboArea) {
		this.comboArea = comboArea;
	}

}
