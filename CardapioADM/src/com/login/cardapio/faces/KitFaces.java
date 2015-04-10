package com.login.cardapio.faces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;

import com.login.cardapio.model.Kit;
import com.login.cardapio.model.KitSubItem;
import com.login.cardapio.model.SubItem;
import com.login.cardapio.util.CardapioUtil;
import com.login.cardapio.util.Constantes;
import com.login.cardapio.util.Utilitarios;

@ViewScoped
@ManagedBean(name = "kitFaces")
public class KitFaces extends CrudFaces<Kit> {

	private List<SelectItem> comboArea;
	private final int MAX_LARGURA = 5000;
	private final int MAX_ALTURA = 5000;

	private static final long serialVersionUID = 1L;
	private SubItem subItemSelecionado;
	private int quantidadeSelecionada;
	private KitSubItem kitSubItemSelecionado;

	@PostConstruct
	protected void init() {
		this.clearFields();
		setFieldOrdem("nome");

	}
	
	@Override
	protected void prePersist() {
		// TODO Auto-generated method stub
		super.prePersist();
		
		try {
			Utilitarios.gerarNovoCodigoCardapio();
		} catch (TSApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.addErrorMessage("Erro no sistema, entre em contato com o administrador, Erro: 0101!");
		}
	}

	@Override
	public String limpar() {
		super.limpar();
		this.getCrudModel().setFlagAtivo(Boolean.TRUE);
		this.getCrudModel().setListKitSubItem(new ArrayList<KitSubItem>());
		return null;
	}

	public List<String> completeText(String query) {

		return new SubItem().findByNomeItemSubItem(query);

	}
	
	public void addSubItem() {

		KitSubItem kitSubItem = new KitSubItem();

		kitSubItem.setSubItem(this.getSubItemSelecionado());
		kitSubItem.setKit(getCrudModel());
		kitSubItem.setqtd(quantidadeSelecionada);

		if (TSUtil.isEmpty(getCrudModel().getListKitSubItem())) {
			getCrudModel().setListKitSubItem(new ArrayList<KitSubItem>());
		}

		if (!this.getCrudModel().getListKitSubItem().contains(kitSubItem)) {

			this.getCrudModel().getListKitSubItem().add(kitSubItem);

		} else {

			CardapioUtil.addErrorMessage("Esse subitem já foi adicionado");
		}
	}

	public void delSubItem() {
		getCrudModel().getListKitSubItem().remove(this.kitSubItemSelecionado);
	}

	public void uploadMidias(FileUploadEvent event) {

		Calendar now = Calendar.getInstance();
		String nomeArquivo = "kit_";
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

	public SubItem getSubItemSelecionado() {
		return subItemSelecionado;
	}

	public void setSubItemSelecionado(SubItem subItemSelecionado) {
		this.subItemSelecionado = subItemSelecionado;
	}

	public KitSubItem getKitSubItemSelecionado() {
		return kitSubItemSelecionado;
	}

	public void setKitSubItemSelecionado(KitSubItem kitSubItemSelecionado) {
		this.kitSubItemSelecionado = kitSubItemSelecionado;
	}

	public int getQuantidadeSelecionada() {
		return quantidadeSelecionada;
	}

	public void setQuantidadeSelecionada(int quantidadeSelecionada) {
		this.quantidadeSelecionada = quantidadeSelecionada;
	}

}
