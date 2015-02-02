package com.login.beachstop.garcom.android;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.Pedido;
import com.login.beachstop.garcom.android.models.PedidoSubItem;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.SubItem;
import com.login.beachstop.garcom.android.network.PedidoRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.views.actionbar.ActionBar;
import com.login.beachstop.garcom.android.views.adapters.PedidoSubItemListViewAdapter;
import com.login.beachstop.garcom.android.views.quickscroll.QuickScroll;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ch.lambdaj.function.matcher.Predicate;

import static ch.lambdaj.Lambda.filter;


/**
 * Created by Argus on 27/01/2015.
 */
public class PedidoActivity extends DefaultActivity {

    private List<SubItem> subItens;
    private PedidoSubItemListViewAdapter pedidoSubItemListViewAdapter;
    private ListView listViewSubItem;
    private QuickScroll quickScroll;
    private RelativeLayout relativeLayoutItemPedido;
    private EditText filtroItem;
    private Dialog dialog;
    private AcaoConta acaoConta;
    private ResponseListener responseListenerPedido = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    Toast.makeText(getApplicationContext(), "Pedido Enviado!", Toast.LENGTH_SHORT).show();
                    ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).setText("");
                    ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).setText("");
                    pedidoSubItemListViewAdapter.zerarQuantidade();
                } else {
                    Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        this.acaoConta = (AcaoConta) getIntent().getSerializableExtra(Constantes.PARAMETRO_PEDIDO_EDITAR);
        loadDados();

        ((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
        findViewById(R.id.actionbar).findViewById(R.id.imagem_action_bar_enviar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        this.listViewSubItem = (ListView) this.findViewById(R.id.activity_pedido_listview_item_cardapio);
        this.pedidoSubItemListViewAdapter = new PedidoSubItemListViewAdapter(this.subItens, this);
        this.listViewSubItem.setAdapter(this.pedidoSubItemListViewAdapter);

        this.relativeLayoutItemPedido = (RelativeLayout) this.findViewById(R.id.activity_pedido_relativelayout_item_cardapio);
        this.relativeLayoutItemPedido.addView(createAlphabetTrack());

        this.quickScroll = QuickScroll.class.cast(findViewById(R.id.activity_pedido_quickscroll));
        this.quickScroll.init(QuickScroll.TYPE_POPUP, this.listViewSubItem, this.pedidoSubItemListViewAdapter, QuickScroll.STYLE_NONE);
        this.quickScroll.setFixedSize(2);
        this.quickScroll.setPopupColor(QuickScroll.BLUE_LIGHT, QuickScroll.BLUE_LIGHT_SEMITRANSPARENT, 1, Color.WHITE, 1);

        this.filtroItem = (EditText) this.findViewById(R.id.activity_pedido_edittext_pesquisa_codigo);
        this.filtroItem.addTextChangedListener(new TextWatcher() {

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
                    Matcher<SubItem> odd = new Predicate<SubItem>() {
                        public boolean apply(SubItem item) {
                            return item.getCodigo().equalsIgnoreCase(s.toString());
                        }
                    };
                    pedidoSubItemListViewAdapter.setSubItens(filter(odd, subItens));
                    pedidoSubItemListViewAdapter.notifyDataSetChanged();
                } else {
                    pedidoSubItemListViewAdapter.setSubItens(subItens);
                    pedidoSubItemListViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void loadDados() {
        List<SubItem> subItensTemp = new ArrayList<SubItem>(getSubItens());
        List<SubItem> subItensTempTopo = new ArrayList<SubItem>();
        SubItem subItemTemp;

        this.subItens = subItensTemp;

        if (this.acaoConta != null) {
            int index;
            for (PedidoSubItem pedidoSubItem : this.acaoConta.getPedido().getPedidoSubItens()) {
                index = subItensTemp.indexOf(new SubItem(pedidoSubItem.getSubItemId()));
                subItemTemp = subItensTemp.get(index);
                subItemTemp.setQtdSelecionado(pedidoSubItem.getQuantidade());
                subItemTemp.setPedidoSubItemId(pedidoSubItem.getId());

                subItensTempTopo.add(subItemTemp);
                subItensTemp.remove(index);
            }

            this.subItens = new ArrayList<SubItem>();
            this.subItens.addAll(subItensTempTopo);
            this.subItens.addAll(subItensTemp);
        }
    }

    public void openDialog() {
        this.dialog = new Dialog(this, R.style.style_bg_dialog_enviar_pedido);

        this.dialog.setContentView(R.layout.dialog_mesa_obs_enviar_pedido);
        this.dialog.setTitle("Enviar pedido");

        if (acaoConta != null) {
            ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).setText(acaoConta.getConta().getNumero().toString());
            ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).setText(acaoConta.getPedido().getObservacao());
        }

        Button dialogButton = (Button) this.dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_button_enviar);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acaoConta != null) {
                    String strMesa = ((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).getText().toString();
                    if (strMesa.length() != 0) {
                        acaoConta.getPedido().setNumero(Integer.valueOf(((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_mesa)).getText().toString()));
                        acaoConta.getPedido().setObservacao(((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).getText().toString());
                        acaoConta.getPedido().setPedidoSubItens(new ArrayList<PedidoSubItem>());

                        PedidoSubItem pedidoSubItem;
                        for (SubItem subItem : pedidoSubItemListViewAdapter.getSubItens()) {
                            if (subItem.getQtdSelecionado() != 0) {
                                pedidoSubItem = new PedidoSubItem();
                                pedidoSubItem.setQuantidade(subItem.getQtdSelecionado());
                                pedidoSubItem.setSubItemId(subItem.getId());
                                pedidoSubItem.setId(subItem.getPedidoSubItemId());
                                acaoConta.getPedido().getPedidoSubItens().add(pedidoSubItem);
                            }
                        }

                        if (acaoConta.getPedido().getPedidoSubItens().size() != 0) {
                            new PedidoRequest(responseListenerPedido).update(acaoConta.getPedido(), getUsuario(), acaoConta);
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
                        pedido.setNumero(Integer.valueOf(strMesa));
                        pedido.setObservacao(((EditText) dialog.findViewById(R.id.dialog_mesa_obs_enviar_pedido_text_view_observacao)).getText().toString());
                        pedido.setPedidoSubItens(new ArrayList<PedidoSubItem>());

                        PedidoSubItem pedidoSubItem;
                        for (SubItem subItem : pedidoSubItemListViewAdapter.getSubItens()) {
                            if (subItem.getQtdSelecionado() != 0) {
                                pedidoSubItem = new PedidoSubItem();
                                pedidoSubItem.setQuantidade(subItem.getQtdSelecionado());
                                pedidoSubItem.setSubItemId(subItem.getId());
                                pedido.getPedidoSubItens().add(pedidoSubItem);
                            }
                        }

                        if (pedido.getPedidoSubItens().size() != 0) {
                            new PedidoRequest(responseListenerPedido).insert(pedido, getUsuario());
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
        dialogButtonCancelar.setOnClickListener(new View.OnClickListener() {
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
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (30 * getResources().getDisplayMetrics().density), RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.color.bg_system);

        LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textparams.weight = 1;
        int height = getResources().getDisplayMetrics().heightPixels;
        int iterate;
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
}