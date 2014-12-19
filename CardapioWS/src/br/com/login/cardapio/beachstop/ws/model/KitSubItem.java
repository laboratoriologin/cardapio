package br.com.login.cardapio.beachstop.ws.model;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement(name ="kitsubitem")
public final class KitSubItem extends RestModel {

	@FormParam("kit")
	private Kit kit;

	public Kit getKit() {
		return kit;
	}

	public void setKit(Kit kit) {
		this.kit=kit;
	}

	@FormParam("subitem")
	private SubItem subItem;

	public SubItem getSubItem() {
		return subItem;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem=subItem;
	}
	
	@FormParam("quantidade")
	private int quantidade;
	
	public int getQuantidade(){
		return this.quantidade;
	}
	
	public void setQuantidade(int quantidade){
		this.quantidade = quantidade;
	}

	public KitSubItem(){}

	public KitSubItem(String id){
		this.id = Long.valueOf(id);
	}
}