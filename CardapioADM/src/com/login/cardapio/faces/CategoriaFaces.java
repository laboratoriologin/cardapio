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

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.Area;
import com.login.cardapio.model.Categoria;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;

@ViewScoped
@ManagedBean(name = "categoriaFaces")
public class CategoriaFaces extends CrudFaces<Categoria> {

	private List<SelectItem> comboArea;
	private final int MAX_LARGURA = 5000;
	private final int MAX_ALTURA = 5000;

	private static final long serialVersionUID = 1L;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("descricao");		
		this.comboArea = super.initCombo(new Area().findByModel("descricao"), "id", "descricao");
	}

	@Override
	public String limparPesquisa() {
		String retorno = super.limparPesquisa();
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

				if (imagem.getWidth() > MAX_LARGURA || imagem.getHeight() > MAX_ALTURA) {

					this.addErrorMessage("Dimensão da imagem está muito grande, máximo de 245px x 95px!");

					valido = false;

				}

			}

		} catch (Exception ex) {

			throw new TSSystemException(ex);

		}

		return valido;

	}

	public List<SelectItem> getComboArea() {
		return comboArea;
	}

	public void setComboArea(List<SelectItem> comboArea) {
		this.comboArea = comboArea;
	}

}
