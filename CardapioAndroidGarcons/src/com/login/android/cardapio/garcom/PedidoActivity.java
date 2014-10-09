package com.login.android.cardapio.garcom;

import static ch.lambdaj.Lambda.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hamcrest.Matcher;
import static ch.lambdaj.Lambda.*;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ch.lambdaj.function.matcher.Predicate;

import com.login.android.cardapio.garcom.adapter.PedidoListViewAdapter;
import com.login.android.cardapio.garcom.business.BusinessResult;
import com.login.android.cardapio.garcom.business.PedidoBS;
import com.login.android.cardapio.garcom.model.ItemCardapioSubItem;
import com.login.android.cardapio.garcom.model.Pedido;
import com.login.android.cardapio.garcom.model.PedidoItem;
import com.login.android.cardapio.garcom.model.ServerResponse;
import com.login.android.cardapio.garcom.model.Usuario;
import com.login.android.cardapio.garcom.quickscroll.QuickScroll;
import com.login.android.cardapio.garcom.util.Constantes;
import com.login.android.cardapio.garcom.view.ActionBar;

public class PedidoActivity extends DefaultActivity implements BusinessResult {

	private List<ItemCardapioSubItem> listItemCardapioSubItem;
	private PedidoListViewAdapter pedidoListViewAdapter;
	private ListView listViewItemCardapioSubItem;
	private QuickScroll fastTrack;
	private RelativeLayout relativeLayoutItemPedido;
	private EditText filtroProduto;
	private Dialog dialog;
	private Pedido pedidoEditar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedido);
		((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

		((ActionBar) findViewById(R.id.actionbar)).findViewById(R.id.imagem_action_bar_enviar).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openDialog();
			}
		});

		this.pedidoEditar = (Pedido) getIntent().getSerializableExtra(Constantes.PARAMETRO_PEDIDO_EDITAR);

		this.loadItens();

		this.listViewItemCardapioSubItem = (ListView) this.findViewById(R.id.activity_pedido_listview_item_cardapio);

		this.pedidoListViewAdapter = new PedidoListViewAdapter(this.listItemCardapioSubItem, this);

		this.listViewItemCardapioSubItem.setAdapter(this.pedidoListViewAdapter);

		this.relativeLayoutItemPedido = (RelativeLayout) this.findViewById(R.id.activity_pedido_relativelayout_item_cardapio);
		this.relativeLayoutItemPedido.addView(createAlphabetTrack());

		this.fastTrack = QuickScroll.class.cast(findViewById(R.id.activity_pedido_quickscroll));
		fastTrack.init(QuickScroll.TYPE_POPUP, this.listViewItemCardapioSubItem, this.pedidoListViewAdapter, QuickScroll.STYLE_NONE);
		fastTrack.setFixedSize(2);
		fastTrack.setPopupColor(QuickScroll.BLUE_LIGHT, QuickScroll.BLUE_LIGHT_SEMITRANSPARENT, 1, Color.WHITE, 1);

		this.filtroProduto = (EditText) this.findViewById(R.id.activity_pedido_edittext_pesquisa_codigo);
		this.filtroProduto.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(final Editable s) {

				if (s.toString().length() != 0) {

					Matcher<ItemCardapioSubItem> odd = new Predicate<ItemCardapioSubItem>() {
						public boolean apply(ItemCardapioSubItem item) {
							return item.getCodSubItem().equalsIgnoreCase(s.toString());
						}
					};

					pedidoListViewAdapter.setListItemCardapioSubItem(filter(odd, listItemCardapioSubItem));
					pedidoListViewAdapter.notifyDataSetChanged();
				} else {
					pedidoListViewAdapter.setListItemCardapioSubItem(listItemCardapioSubItem);
					pedidoListViewAdapter.notifyDataSetChanged();
				}
			}
		});

	}

	public void loadItens() {
		List<ItemCardapioSubItem> listTemp = this.getDataManager().getAll(this.getListaItemCardapio());
		List<ItemCardapioSubItem> listTempTopo = new ArrayList<ItemCardapioSubItem>();
		ItemCardapioSubItem itemTemp;

		if (pedidoEditar != null) {
			int index;
			for (PedidoItem item : this.pedidoEditar.getListPedidoItem()) {
				index = listTemp.indexOf(item.getSubItem());
				itemTemp = listTemp.get(index);
				itemTemp.setQtdSelecionado(item.getSubItem().getQuantidade());
				itemTemp.setIdPedidoSubItem(item.getId());
				listTempTopo.add(itemTemp);
				listTemp.remove(index);
			}

			Collections.sort(listTemp);

			this.listItemCardapioSubItem = new ArrayList<ItemCardapioSubItem>();
			this.listItemCardapioSubItem.addAll(listTempTopo);
			this.listItemCardapioSubItem.addAll(listTemp);

		} else {

			this.listItemCardapioSubItem = listTemp;
		}
	}

	public void openDialog() {
		this.dialog = new Dialog(this, R.style.style_bg_dialog_enviar_pedido);

		this.dialog.setContentView(R.layout.dialog_mesa_obs_enviar_pedido);
		this.dialog.setTitle("Enviar pedido");

		if (pedidoEditar != null) {
			((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).setText(pedidoEditar.getMesa().toString());
			((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).setText(pedidoEditar.getObservacao());
		}

		Button dialogButton = (Button) this.dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_button_enviar);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (pedidoEditar != null) {

					String strMesa = ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).getText().toString();

					if (strMesa.length() != 0) {

						pedidoEditar.setMesa(Integer.valueOf(((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).getText().toString()));
						pedidoEditar.setObservacao(((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).getText().toString());
						pedidoEditar.setUsuario(getUsuario());
						pedidoEditar.setListPedidoItem(new ArrayList<PedidoItem>());

						PedidoItem pedidoItem;
						for (ItemCardapioSubItem item : pedidoListViewAdapter.getListItemCardapioSubItem()) {
							if (item.getQtdSelecionado() != 0) {
								pedidoItem = new PedidoItem();
								pedidoItem.setQuantidade(item.getQtdSelecionado());
								pedidoItem.setIdSubItem(item.getItemCardapio().getId());
								pedidoItem.setId(item.getIdPedidoSubItem());

								pedidoEditar.getListPedidoItem().add(pedidoItem);
							}
						}

						if (pedidoEditar.getListPedidoItem().size() != 0) {
							new PedidoBS(PedidoActivity.this).update(pedidoEditar);
							Toast.makeText(PedidoActivity.this, "Enviando pedido...", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						} else {
							Toast.makeText(PedidoActivity.this, "Escolha algum item para o pedido!", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(PedidoActivity.this, "Escolha a mesa!", Toast.LENGTH_SHORT).show();
					}

				} else {

					String strMesa = ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).getText().toString();

					if (strMesa.length() != 0) {

						Pedido pedido = new Pedido();
						pedido.setMesa(Integer.valueOf(strMesa));
						pedido.setObservacao(((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).getText().toString());
						pedido.setUsuario(getUsuario());
						pedido.setListPedidoItem(new ArrayList<PedidoItem>());

						PedidoItem pedidoItem;
						for (ItemCardapioSubItem item : pedidoListViewAdapter.getListItemCardapioSubItem()) {
							if (item.getQtdSelecionado() != 0) {
								pedidoItem = new PedidoItem();
								pedidoItem.setQuantidade(item.getQtdSelecionado());
								pedidoItem.setIdSubItem(item.getId());

								pedido.getListPedidoItem().add(pedidoItem);
							}
						}

						if (pedido.getListPedidoItem().size() != 0) {
							new PedidoBS(PedidoActivity.this).insert(pedido);
							Toast.makeText(PedidoActivity.this, "Enviando pedido...", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
						} else {
							Toast.makeText(PedidoActivity.this, "Escolha algum item para o pedido!", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(PedidoActivity.this, "Escolha a mesa!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		Button dialogButtonCancelar = (Button) this.dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_button_cancelar);
		dialogButtonCancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).setText("");
				((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).setText("");

				dialog.dismiss();
			}
		});

		this.dialog.show();
	}

	private ViewGroup createAlphabetTrack() {

		LinearLayout layout = new LinearLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (30 * getResources().getDisplayMetrics().density), LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.color.bg_system);

		LinearLayout.LayoutParams textparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		textparams.weight = 1;
		int height = getResources().getDisplayMetrics().heightPixels;
		int iterate = 0;
		if (height >= 1024) {
			iterate = 1;
			layout.setWeightSum(26);
		} else {
			iterate = 2;
			layout.setWeightSum(13);
		}

		TextView textview;

		for (char character = 'a'; character <= 'z'; character += iterate) {
			textview = new TextView(this);
			textview.setBackgroundColor(Color.WHITE);
			textview.setLayoutParams(textparams);
			textview.setGravity(Gravity.CENTER_HORIZONTAL);
			textview.setText(Character.toString(character));
			layout.addView(textview);
		}
		return layout;
	}

	@Override
	public void getBusinessResult(Object result) {
		ServerResponse serverResponse = (ServerResponse) result;

		if (serverResponse.isOK()) {
			try {
				Toast.makeText(this, "Pedido Enviado!", Toast.LENGTH_SHORT).show();
				((EditText) this.dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).setText("");
				((EditText) this.dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).setText("");
				this.pedidoListViewAdapter.zerarQuantidade();

			} catch (Exception e) {
				Toast.makeText(this, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, serverResponse.getMsgErro(), Toast.LENGTH_SHORT).show();
		}

	}
}