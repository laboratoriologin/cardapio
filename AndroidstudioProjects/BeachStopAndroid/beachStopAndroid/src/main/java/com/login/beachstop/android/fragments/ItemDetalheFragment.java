package com.login.beachstop.android.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Conta;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;
import com.login.beachstop.android.models.SubItem;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.Utilitarios;
import com.login.beachstop.android.views.actionbar.ActionBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemDetalheFragment extends Fragment {

    private View view;
    private Item item;
    private List<RowSubItem> listRowSubItem;
    private ImageView imageView;
    private SocialAuthAdapter socialAuthAdapter;
    private NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private DisplayImageOptions options;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle args = getArguments();
        this.item = (Item) args.getSerializable(Constantes.ARG_ITEM_CARDAPIO);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.placeholder)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .delayBeforeLoading(100)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.view = inflater.inflate(R.layout.fragment_item_detalhe, container, false);

        ((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
        ((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText(this.item.getNome());

        ((TextView) this.view.findViewById(R.id.fragment_item_detalhe_text_view_descricao)).setText(item.getDescricao());
        ((TextView) this.view.findViewById(R.id.fragment_item_detalhe_text_view_ingredientes)).setText(item.getIngrediente());

        this.imageView = (ImageView) this.view.findViewById(R.id.fragment_item_detalhe_image_view);

        ImageLoader.getInstance().displayImage(!TextUtils.isEmpty(this.item.getImagem()) ? Constantes.URL_IMG + item.getImagem() : "", this.imageView, options);

        Boolean hasConta = ((CardapioActivity) getActivity()).getDataManager().getContaDAO().get() != null;

        if (hasConta) {
            this.view.findViewById(R.id.fragment_item_detalhe_linear_layout_btn).setVisibility(LinearLayout.VISIBLE);
        }

        if (item.getTempoPreparo().toString().length() == 0) {
            ((TextView) this.view.findViewById(R.id.fragment_item_detalhe_text_view_tempo_preparo)).setVisibility(TextView.GONE);
            ((TextView) this.view.findViewById(R.id.fragment_item_detalhe_text_view_lbl_tempo_preparo)).setVisibility(TextView.GONE);
        } else {
            ((TextView) this.view.findViewById(R.id.fragment_item_detalhe_text_view_tempo_preparo)).setText(item.getTempoPreparo().toString() + " Mim");
        }

        socialAuthAdapter = new SocialAuthAdapter(new ResponseListener());
        socialAuthAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
        socialAuthAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);

        TableLayout tableLayout = (TableLayout) this.view.findViewById(R.id.fragment_item_detalhe_table_layout_sub_item);
        startRowSubITem(tableLayout, this.item.getSubItens());

        this.view.findViewById(R.id.fragment_item_detalhe_button_finalizar_pedido).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Boolean enviarPedido = false;

                for (SubItem subItem : item.getSubItens()) {
                    if (subItem.getQtdSelecionado() != 0)
                        enviarPedido = true;
                }

                if (!enviarPedido)
                    Toast.makeText(getActivity(), "Selecione algum item para prosseguir com o pedido!", Toast.LENGTH_SHORT).show();
                else {

                    Conta contaAberta = ((CardapioActivity) getActivity()).getDataManager().getContaDAO().get();
                    if (contaAberta != null) {
                        addItemPedido(contaAberta);
                        ((CardapioActivity) getActivity()).getTabHost().setCurrentTab(3);
                    } else {
                        Toast.makeText(getActivity(), "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        this.view.findViewById(R.id.fragment_item_detalhe_button_add_item).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean enviarPedido = false;

                for (SubItem subItem : item.getSubItens()) {
                    if (subItem.getQtdSelecionado() != 0)
                        enviarPedido = true;
                }

                if (!enviarPedido)
                    Toast.makeText(getActivity(), "Selecione algum item para prosseguir com o pedido!", Toast.LENGTH_SHORT).show();
                else {

                    Conta contaAberta = ((CardapioActivity) getActivity()).getDataManager().getContaDAO().get();
                    if (contaAberta != null)
                        addItemPedido(contaAberta);
                    else
                        Toast.makeText(getActivity(), "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        socialAuthAdapter.enable((Button) this.view.findViewById(R.id.fragment_item_detalhe_button_compartilhar));
        return this.view;
    }

    public void zeraItemTabela() {
        if (this.listRowSubItem.size() != 0) {
            for (RowSubItem rowSubItem : this.listRowSubItem) {
                rowSubItem.getSubItem().setQtdSelecionado(0l);
                rowSubItem.getTxtQtd().setText(String.format("%02d", 0));
            }
        }
    }

    public void addItemPedido(Conta contaAberta) {
        Pedido pedidoAtual = ((CardapioActivity)getActivity()).getDataManager().getPedidoDAO().get(false);

        if (pedidoAtual == null) {

            pedidoAtual = new Pedido();
            pedidoAtual.setId(((CardapioActivity)getActivity()).getDataManager().getNextId(Pedido.class));
            pedidoAtual.setFinalizadoSys(false);
            pedidoAtual.setContaId(contaAberta.getId());
            pedidoAtual.setObservacao("");
            pedidoAtual.setPedidoSubItens(new ArrayList<PedidoSubItem>());

            try {
                ((CardapioActivity)getActivity()).getDataManager().getPedidoDAO().save(pedidoAtual);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
            }
        }

        PedidoSubItem pedidoSubItemContains;
        PedidoSubItem pedidoSubItem;

        List<SubItem> subItens = this.item.getSubItens();
        Long pedidoSubItemId = ((CardapioActivity)getActivity()).getDataManager().getNextId(PedidoSubItem.class);

        for (SubItem subItem : subItens) {
            if (subItem.getQtdSelecionado() != 0) {

                pedidoSubItem = new PedidoSubItem();
                pedidoSubItem.setId(pedidoSubItemId);
                pedidoSubItem.setPedidoId(pedidoAtual.getId());
                pedidoSubItem.setSubItemId(subItem.getId());
                pedidoSubItem.setQuantidade(subItem.getQtdSelecionado());
                pedidoSubItem.setSubItem(subItem);

                if (pedidoAtual.getPedidoSubItens().contains(pedidoSubItem)) {
                    pedidoSubItemContains = pedidoAtual.getPedidoSubItens().get(pedidoAtual.getPedidoSubItens().indexOf(pedidoSubItem));
                    pedidoSubItemContains.addQtd(pedidoSubItem.getQuantidade());

                    try {
                        ((CardapioActivity)getActivity()).getDataManager().getPedidoSubItemDAO().update(pedidoSubItemContains, pedidoSubItemContains.getId());
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
                    }
                }else{

                    pedidoAtual.getPedidoSubItens().add(pedidoSubItem);

                    try {
                        ((CardapioActivity)getActivity()).getDataManager().getPedidoSubItemDAO().save(pedidoSubItem);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
                    }
                }
                pedidoSubItemId++;
            }
        }
        Toast.makeText(getActivity(), "Item inserido no pedido!", Toast.LENGTH_SHORT).show();
        zeraItemTabela();
    }

    public void startRowSubITem(TableLayout tableLayout, List<SubItem> subItens) {

        Boolean hasConta = ((CardapioActivity)getActivity()).getDataManager().getContaDAO().get() != null;

        if (hasConta) loadRow(tableLayout, subItens);
        else loadRowSemConta(tableLayout, subItens);
    }

    private void loadRow(TableLayout tableLayout, List<SubItem> subItens) {

        View subItemView;
        String descricao;
        RowSubItem rowSubItem;

        this.listRowSubItem = new ArrayList<RowSubItem>();

        for (SubItem subItemCardapio : subItens) {

            subItemView = TableRow.inflate(((CardapioActivity)getActivity()), R.layout.fragment_item_detalhe_table_row, null);

            rowSubItem = new RowSubItem(((ImageView) subItemView.findViewById(R.id.fragment_item_detalhe_table_row_image_view_seta_esquerda)), ((ImageView) subItemView.findViewById(R.id.fragment_item_detalhe_table_row_image_view_seta_direita)), ((TextView) subItemView.findViewById(R.id.fragment_item_detalhe_table_row_text_view_qtd)), subItemCardapio);
            rowSubItem.getSubItem().setQtdSelecionado(0l);
            rowSubItem.getTxtQtd().setText(String.format("%02d", rowSubItem.getSubItem().getQtdSelecionado()));
            rowSubItem.getBtnDireita().setTag(rowSubItem);
            rowSubItem.getBtnEsquerda().setTag(rowSubItem);
            rowSubItem.getBtnDireita().setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                RowSubItem rowSubItem = (RowSubItem) v.getTag();

                if (rowSubItem.getSubItem().getQtdSelecionado() <= 99) {
                    rowSubItem.getSubItem().setQtdSelecionado((rowSubItem.getSubItem().getQtdSelecionado() + 1));
                    rowSubItem.getTxtQtd().setText(String.format("%02d", rowSubItem.getSubItem().getQtdSelecionado()));
                }
                }
            });
            rowSubItem.getBtnEsquerda().setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                RowSubItem rowSubItem = (RowSubItem) v.getTag();

                if (rowSubItem.getSubItem().getQtdSelecionado() >= 1) {
                    rowSubItem.getSubItem().setQtdSelecionado((rowSubItem.getSubItem().getQtdSelecionado() - 1));
                    rowSubItem.getTxtQtd().setText(String.format("%02d", rowSubItem.getSubItem().getQtdSelecionado()));
                }
                }
            });

            descricao = subItemCardapio.getNome();
            ((TextView) subItemView.findViewById(R.id.fragment_item_detalhe_table_row_text_view_descricao)).setText(descricao);
            ((TextView) subItemView.findViewById(R.id.fragment_item_detalhe_table_row_text_view_valor)).setText(format.format(new BigDecimal(subItemCardapio.getValor())));

            this.listRowSubItem.add(rowSubItem);
            tableLayout.addView(subItemView);
        }
    }

    private void loadRowSemConta(TableLayout tableLayout, List<SubItem> subItens) {

        View subItemView;
        String descricao;

        for (SubItem subItemCardapio : subItens) {
            subItemView = TableRow.inflate(getActivity(), R.layout.fragment_item_detalhe_table_row_sem_conta, null);
            descricao = " • " + subItemCardapio.getNome();
            ((TextView) subItemView.findViewById(R.id.linha_sub_item_sem_conta_text_view_descricao)).setText(descricao);
            tableLayout.addView(subItemView);
        }
    }

    public class RowSubItem {

        private ImageView btnEsquerda;
        private ImageView btnDireita;
        private TextView txtQtd;
        private SubItem subItem;

        public RowSubItem(ImageView btnEsquerda, ImageView btnDireita, TextView txtQtd, SubItem subItem) {
            this.btnEsquerda = btnEsquerda;
            this.btnDireita = btnDireita;
            this.txtQtd = txtQtd;
            this.subItem = subItem;
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

        public SubItem getSubItem() {
            return subItem;
        }

        public void setSubItem(SubItem subItem) {
            this.subItem = subItem;
        }

    }

    private final class ResponseListener implements DialogListener {
        public void onComplete(Bundle values) {
            Bitmap bitmap = Utilitarios.drawableToBitmap(imageView.getDrawable());
            try {
                if (values.get("provider").equals(Provider.FACEBOOK.name().toLowerCase())) {
                    socialAuthAdapter.uploadImageAsync("Beach Stop Ipitanga\n " + item.getNome() + "\n" + item.getDescricao(), "png", bitmap, 1, new MessageListener());
                } else if (values.get("provider").equals(Provider.TWITTER.name().toLowerCase())) {
                    socialAuthAdapter.uploadImageAsync("Beach Stop Ipitanga " + item.getNome(), "png", bitmap, 1, new MessageListener());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Erro ao compartilhar na rede social. Tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
            }
        }

        public void onCancel() {
            Log.d("ShareButton", "Cancelado");
        }

        @Override
        public void onBack() {}

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
                Toast.makeText(getActivity(), "Compartilhado!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();
        }
    }
}