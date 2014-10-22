package com.login.beachstop.android.fragment;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.model.Conta;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.ItemCardapioSubItem;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.util.Constantes;
import com.login.beachstop.android.util.DrawableManager;
import com.login.beachstop.android.util.LoadImage;
import com.login.beachstop.android.util.Utilitarios;
import com.login.beachstop.android.view.ActionBar;

public class DetalheItemCardapioFragment extends Fragment {

	private View view;
	private HomeActivity homeActivity;
	private ItemCardapio itemCardapio;
	private List<RowSubItem> listRowSubItem;
	private ImageView imageView;
	private SocialAuthAdapter socialAuthAdapter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.homeActivity = (HomeActivity) activity;
		Bundle args = getArguments();
		itemCardapio = (ItemCardapio) args.getSerializable(Constantes.ARG_ITEM_CARDAPIO);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = inflater.inflate(R.layout.fragment_detalhe_item_cardapio, container, false);

		((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
		((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText(this.itemCardapio.getNome());

		((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_descricao)).setText(itemCardapio.getDescricao());
		((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_ingredientes)).setText(itemCardapio.getIngredientes());
		
		if (itemCardapio.getGuarnicoes().length() == 0){
			
			((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_acompanhamentos)).setVisibility(TextView.GONE);
			((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_lbl_acompanhamentos)).setVisibility(TextView.GONE);
			
		}else{
			
			((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_acompanhamentos)).setText(itemCardapio.getGuarnicoes());
		}
		

		this.imageView = (ImageView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_image_view);

		if (itemCardapio.getImagem().length() != 0) {

			Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + itemCardapio.getImagem());

			if (img == null) {
				new LoadImage(this.imageView, this.view.getContext()).execute(Constantes.URL_IMG + itemCardapio.getImagem());
			} else {
				imageView.setImageDrawable(img);
			}
		}

		if (itemCardapio.getTempoMedioPreparo().toString().length() == 0){
			
			((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_tempo_preparo)).setVisibility(TextView.GONE);
			((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_lbl_tempo_preparo)).setVisibility(TextView.GONE);			
			
		}else{
			
			((TextView) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_text_view_tempo_preparo)).setText(itemCardapio.getTempoMedioPreparo().toString() + " Mim");
		}
		
		socialAuthAdapter = new SocialAuthAdapter(new ResponseListener());

		// Add providers
		socialAuthAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);

		socialAuthAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);

		TableLayout tableLayout = (TableLayout) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_table_layout_sub_item);
		startRowSubITem(tableLayout, this.itemCardapio.getSubItens());

		// this.view.findViewById(R.id.fragment_detalhe_item_cardapio_button_finalizar_pedido).setOnClickListener(new
		// OnClickListener() {

		// @Override
		// public void onClick(View v) {
		// Boolean enviarPedido = false;
		//
		// for (ItemCardapioSubItem item : itemCardapio.getSubItens()) {
		// if (item.getQtdSelecionado() != 0)
		// enviarPedido = true;
		// }
		//
		// if (!enviarPedido)
		// Toast.makeText(homeActivity,
		// "Selecione algum item para prosseguir com o pedido!",
		// Toast.LENGTH_SHORT).show();
		// else {
		//
		// Conta contaAberta = homeActivity.getDataManager().getConta();
		//
		// if (contaAberta != null) {
		// addItemPedido(contaAberta);
		// homeActivity.getTabHost().setCurrentTab(2);
		// } else {
		// Toast.makeText(homeActivity,
		// "Ops! Você aidna não realizaou o seu check-in!",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// }
		// });
		//
		// this.view.findViewById(R.id.fragment_detalhe_item_cardapio_button_add_item).setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// Boolean enviarPedido = false;
		//
		// for (ItemCardapioSubItem item : itemCardapio.getSubItens()) {
		// if (item.getQtdSelecionado() != 0)
		// enviarPedido = true;
		// }
		//
		// if (!enviarPedido)
		// Toast.makeText(homeActivity,
		// "Selecione algum item para prosseguir com o pedido!",
		// Toast.LENGTH_SHORT).show();
		// else {
		//
		// Conta contaAberta = homeActivity.getDataManager().getConta();
		//
		// if (contaAberta != null) {
		// addItemPedido(contaAberta);
		// } else {
		// Toast.makeText(homeActivity,
		// "Ops! Você aidna não realizaou o seu check-in!",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// }
		// });

		socialAuthAdapter.enable((Button) this.view.findViewById(R.id.fragment_detalhe_item_cardapio_button_compartilhar));
		return this.view;
	}

	public void zeraItemTabela() {
		if (this.listRowSubItem.size() != 0) {
			for (RowSubItem rowSubItem : this.listRowSubItem) {
				rowSubItem.getSubItemCardapio().setQtdSelecionado(0l);
				rowSubItem.getTxtQtd().setText(String.format("%02d", 0));
			}
		}
	}

	public void addItemPedido(Conta contaAberta) {
		Pedido pedidoAtual = this.homeActivity.getDataManager().getPedido(false);

		if (pedidoAtual == null) {
			pedidoAtual = new Pedido();
			pedidoAtual.setId(this.homeActivity.getDataManager().getNextId(Pedido.class));
			pedidoAtual.setFinalizadoSys(false);
			pedidoAtual.setIdConta(contaAberta.getId());
			pedidoAtual.setObservacao("");
			pedidoAtual.setListPedidoItem(new ArrayList<PedidoItem>());

			try {
				this.homeActivity.getDataManager().getPedidoDAO().save(pedidoAtual);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(homeActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}
		}

		List<ItemCardapioSubItem> listSubItem = itemCardapio.getSubItens();

		PedidoItem pedidoItem;
		Long idPedidoItem = this.homeActivity.getDataManager().getNextId(PedidoItem.class);
		for (ItemCardapioSubItem subItemCardapio : listSubItem) {
			if (subItemCardapio.getQtdSelecionado() != 0) {

				pedidoItem = new PedidoItem();
				pedidoItem.setId(idPedidoItem);
				pedidoItem.setIdPedido(pedidoAtual.getId());
				pedidoItem.setIdSubItem(subItemCardapio.getId());
				pedidoItem.setQuantidade(subItemCardapio.getQtdSelecionado());
				pedidoItem.setSubItem(subItemCardapio);

				if (pedidoAtual.getListPedidoItem().contains(pedidoItem)) {
					pedidoAtual.getListPedidoItem().get(pedidoAtual.getListPedidoItem().indexOf(pedidoItem)).addQtd(pedidoItem.getQuantidade());
					try {
						this.homeActivity.getDataManager().getPedidoItemDAO().update(pedidoAtual.getListPedidoItem().get(pedidoAtual.getListPedidoItem().indexOf(pedidoItem)), pedidoAtual.getListPedidoItem().get(pedidoAtual.getListPedidoItem().indexOf(pedidoItem)).getId());
					} catch (Exception e) {
						Toast.makeText(homeActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
					}
				} else {
					pedidoAtual.getListPedidoItem().add(pedidoItem);

					try {
						this.homeActivity.getDataManager().getPedidoItemDAO().save(pedidoItem);
					} catch (Exception e) {
						Toast.makeText(homeActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
					}

				}
				idPedidoItem++;
			}
		}

		Toast.makeText(homeActivity, "Item inserido no pedido!", Toast.LENGTH_SHORT).show();
		zeraItemTabela();
	}

	public void startRowSubITem(TableLayout tableLayout, List<ItemCardapioSubItem> listSubItem) {

		View subItemView;
		String descricao;
		RowSubItem rowSubItem;

		Boolean hasConta = this.homeActivity.getDataManager().getConta() != null;
		NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		this.listRowSubItem = new ArrayList<RowSubItem>();

		for (ItemCardapioSubItem subItemCardapio : listSubItem) {

			subItemView = TableRow.inflate(this.homeActivity, R.layout.fragment_detalhe_item_cardapio_linha_sub_item, null);
			// rowSubItem = new RowSubItem(((ImageView)
			// subItemView.findViewById(R.id.linha_sub_item_image_view_seta_esquerda)),
			// ((ImageView)
			// subItemView.findViewById(R.id.linha_sub_item_image_view_seta_direita)),
			// ((TextView)
			// subItemView.findViewById(R.id.linha_sub_item_text_view_qtd)),
			// subItemCardapio);
			rowSubItem = new RowSubItem(null, null, null, subItemCardapio);

			if (hasConta) {
				descricao = subItemCardapio.getDescricao() + " ...........................................................";
				((TextView) subItemView.findViewById(R.id.linha_sub_item_text_view_descricao)).setText(descricao);

			} else {
				// descricao = subItemCardapio.getDescricao() + " - " +
				// subItemCardapio.getQuantidade() + " " +
				// subItemCardapio.getDescricaoTipoQuantidade();
				descricao = subItemCardapio.getDescricao();
				((TextView) subItemView.findViewById(R.id.linha_sub_item_text_view_descricao)).setText(descricao);
			}

			if (hasConta) {
				((TextView) subItemView.findViewById(R.id.linha_sub_item_text_view_valor)).setText(format.format(new BigDecimal(subItemCardapio.getValor())));
			} else {
				// ((TextView)
				// subItemView.findViewById(R.id.linha_sub_item_text_view_valor)).setText("                      ");
				((TextView) subItemView.findViewById(R.id.linha_sub_item_text_view_valor)).setText("");
			}

			// rowSubItem.getSubItemCardapio().setQtdSelecionado(0l);
			// rowSubItem.getTxtQtd().setText(String.format("%02d",
			// rowSubItem.getSubItemCardapio().getQtdSelecionado()));

			// rowSubItem.getBtnDireita().setTag(rowSubItem);
			// rowSubItem.getBtnEsquerda().setTag(rowSubItem);

			// rowSubItem.getBtnDireita().setOnClickListener(new
			// OnClickListener() {
			//
			// public void onClick(View v) {
			// RowSubItem rowSubItem = (RowSubItem) v.getTag();
			// if (rowSubItem.getSubItemCardapio().getQtdSelecionado() <= 99) {
			// rowSubItem.getSubItemCardapio().setQtdSelecionado((rowSubItem.getSubItemCardapio().getQtdSelecionado()
			// + 1));
			// rowSubItem.getTxtQtd().setText(String.format("%02d",
			// rowSubItem.getSubItemCardapio().getQtdSelecionado()));
			// }
			// }
			//
			// });
			//
			// rowSubItem.getBtnEsquerda().setOnClickListener(new
			// OnClickListener() {
			//
			// public void onClick(View v) {
			// RowSubItem rowSubItem = (RowSubItem) v.getTag();
			// if (rowSubItem.getSubItemCardapio().getQtdSelecionado() >= 1) {
			// rowSubItem.getSubItemCardapio().setQtdSelecionado((rowSubItem.getSubItemCardapio().getQtdSelecionado()
			// - 1));
			// rowSubItem.getTxtQtd().setText(String.format("%02d",
			// rowSubItem.getSubItemCardapio().getQtdSelecionado()));
			// }
			// }
			//
			// });

			this.listRowSubItem.add(rowSubItem);

			tableLayout.addView(subItemView);
		}
	}

	public class RowSubItem {

		private ImageView btnEsquerda;
		private ImageView btnDireita;
		private TextView txtQtd;
		private ItemCardapioSubItem subItemCardapio;

		public RowSubItem(ImageView _btnEsquerda, ImageView _btnDireita, TextView _txtQtd, ItemCardapioSubItem _subItemCardapio) {
			this.btnEsquerda = _btnEsquerda;
			this.btnDireita = _btnDireita;
			this.txtQtd = _txtQtd;
			this.subItemCardapio = _subItemCardapio;
		}

		public ImageView getBtnEsquerda() {
			return btnEsquerda;
		}

		public void setBtnEsquerda(ImageView btnEsquerda) {
			this.btnEsquerda = btnEsquerda;
		}

		public ImageView getBtnDireita() {
			return btnDireita;
		}

		public void setBtnDireita(ImageView btnDireita) {
			this.btnDireita = btnDireita;
		}

		public TextView getTxtQtd() {
			return txtQtd;
		}

		public void setTxtQtd(TextView txtQtd) {
			this.txtQtd = txtQtd;
		}

		public ItemCardapioSubItem getSubItemCardapio() {
			return subItemCardapio;
		}

		public void setSubItemCardapio(ItemCardapioSubItem subItemCardapio) {
			this.subItemCardapio = subItemCardapio;
		}

	}

	private final class ResponseListener implements DialogListener {
		public void onComplete(Bundle values) {

			Bitmap bitmap = Utilitarios.drawableToBitmap(imageView.getDrawable());

			try {
				if (values.get("provider").equals(Provider.FACEBOOK.name().toLowerCase())) {
					socialAuthAdapter.uploadImageAsync("Beach Stop Ipitanga\n " + itemCardapio.getNome() + "\n" + itemCardapio.getDescricao(), "png", bitmap, 1, new MessageListener());
				} else if (values.get("provider").equals(Provider.TWITTER.name().toLowerCase())) {
					socialAuthAdapter.uploadImageAsync("Beach Stop Ipitanga " + itemCardapio.getNome(), "png", bitmap, 1, new MessageListener());
				}

			} catch (Exception e) {

				e.printStackTrace();

				Toast.makeText(homeActivity, "Erro ao compartilhar na rede social. Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();

			}
		}

		public void onCancel() {
			Log.d("ShareButton", "Cancelado");
		}

		@Override
		public void onBack() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(SocialAuthError arg0) {
			Log.d("ShareButton", "Cancelado");
		}
	}

	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {

		public void onError(SocialAuthError e) {
			System.out.println(e.getMessage());
		}

		@Override
		public void onExecute(String arg0, Integer status) {
			// TODO Auto-generated method stubInteger status = t;

			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(homeActivity, "Compartilhado!", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(homeActivity, "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();

		}
	}

}
