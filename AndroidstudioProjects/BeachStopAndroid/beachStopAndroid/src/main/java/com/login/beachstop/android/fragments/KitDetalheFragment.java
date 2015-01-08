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
import com.login.beachstop.android.models.Kit;
import com.login.beachstop.android.models.KitSubItem;
import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;
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
public class KitDetalheFragment extends Fragment {

    private View view;
    private Kit kit;
    private List<RowSubItem> listRowSubItem;
    private ImageView imageView;
    private SocialAuthAdapter socialAuthAdapter;
    private NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private DisplayImageOptions options;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle args = getArguments();
        this.kit = (Kit) args.getSerializable(Constantes.ARG_ITEM_CARDAPIO);
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

        this.view = inflater.inflate(R.layout.fragment_kit_detalhe, container, false);

        ((ActionBar) this.view.findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);
        ((TextView) (this.view.findViewById(R.id.actionbar)).findViewById(R.id.text_view_action_bar)).setText(this.kit.getNome());

        ((TextView) this.view.findViewById(R.id.fragment_kit_detalhe_text_view_descricao)).setText(kit.getDescricao());

        this.imageView = (ImageView) this.view.findViewById(R.id.fragment_kit_detalhe_image_view);

        ImageLoader.getInstance().displayImage(!TextUtils.isEmpty(this.kit.getImagem()) ? Constantes.URL_IMG + kit.getImagem() : "", this.imageView, options);

        Boolean hasConta = ((CardapioActivity) getActivity()).getDataManager().getContaDAO().get() != null;

        if (hasConta) {
            this.view.findViewById(R.id.fragment_kit_detalhe_linear_layout_btn).setVisibility(LinearLayout.VISIBLE);
        }

        socialAuthAdapter = new SocialAuthAdapter(new ResponseListener());
        socialAuthAdapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
        socialAuthAdapter.addProvider(Provider.TWITTER, R.drawable.twitter);

        TableLayout tableLayout = (TableLayout) this.view.findViewById(R.id.fragment_kit_detalhe_table_layout_kit);
        startRowSubITem(tableLayout);

        loadRowKitSubItem((TableLayout) this.view.findViewById(R.id.fragment_kit_detalhe_table_layout_sub_item), this.kit.getKitSubItens());

        this.view.findViewById(R.id.fragment_kit_detalhe_button_finalizar_pedido).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Boolean enviarPedido = (kit.getQuantidade() != 0);

                if (!enviarPedido)
                    Toast.makeText(getActivity(), "Adicione um kit para prosseguir com o pedido!", Toast.LENGTH_SHORT).show();
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

        this.view.findViewById(R.id.fragment_kit_detalhe_button_add_item).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean enviarPedido = (kit.getQuantidade() != 0);

                if (!enviarPedido)
                    Toast.makeText(getActivity(), "Adicione um kit para prosseguir com o pedido!", Toast.LENGTH_SHORT).show();
                else {

                    Conta contaAberta = ((CardapioActivity) getActivity()).getDataManager().getContaDAO().get();
                    if (contaAberta != null)
                        addItemPedido(contaAberta);
                    else
                        Toast.makeText(getActivity(), "Ops! Você aidna não realizaou o seu check-in!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        socialAuthAdapter.enable((Button) this.view.findViewById(R.id.fragment_kit_detalhe_button_compartilhar));
        return this.view;
    }

    public void zeraItemTabela() {
        if (this.listRowSubItem.size() != 0) {
            for (RowSubItem rowSubItem : this.listRowSubItem) {
                rowSubItem.getKit().setQuantidade(0l);
                rowSubItem.getTxtQtd().setText(String.format("%02d", 0));
            }
        }
    }

    public void addItemPedido(Conta contaAberta) {
        Pedido pedidoAtual = ((CardapioActivity) getActivity()).getDataManager().getPedidoDAO().get(false);

        if (pedidoAtual == null) {

            pedidoAtual = new Pedido();
            pedidoAtual.setId(((CardapioActivity) getActivity()).getDataManager().getNextId(Pedido.class));
            pedidoAtual.setFinalizadoSys(false);
            pedidoAtual.setContaId(contaAberta.getId());
            pedidoAtual.setObservacao("");
            pedidoAtual.setPedidoSubItens(new ArrayList<PedidoSubItem>());

            try {
                ((CardapioActivity) getActivity()).getDataManager().getPedidoDAO().save(pedidoAtual);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
            }
        }

        PedidoSubItem pedidoSubItemContains;
        Long pedidoSubItemId = ((CardapioActivity) getActivity()).getDataManager().getNextId(PedidoSubItem.class);

        PedidoSubItem pedidoSubItem = new PedidoSubItem();
        pedidoSubItem.setId(pedidoSubItemId);
        pedidoSubItem.setPedidoId(pedidoAtual.getId());
        pedidoSubItem.setQuantidade(this.kit.getQuantidade());
        pedidoSubItem.setKitId(this.kit.getId());
        pedidoSubItem.setSubItemId(this.kit.getId());

        if (pedidoAtual.getPedidoSubItens().contains(pedidoSubItem)) {
            pedidoSubItemContains = pedidoAtual.getPedidoSubItens().get(pedidoAtual.getPedidoSubItens().indexOf(pedidoSubItem));
            pedidoSubItemContains.addQtd(pedidoSubItem.getQuantidade());

            try {
                ((CardapioActivity) getActivity()).getDataManager().getPedidoSubItemDAO().update(pedidoSubItemContains, pedidoSubItemContains.getId());
            } catch (Exception e) {
                Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
            }
        } else {

            pedidoAtual.getPedidoSubItens().add(pedidoSubItem);

            try {
                ((CardapioActivity) getActivity()).getDataManager().getPedidoSubItemDAO().save(pedidoSubItem);
            } catch (Exception e) {
                Toast.makeText(getActivity(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
            }
        }
        pedidoSubItemId++;


        Toast.makeText(getActivity(), "Item inserido no pedido!", Toast.LENGTH_SHORT).show();
        zeraItemTabela();
    }

    public void startRowSubITem(TableLayout tableLayout) {

        Boolean hasConta = ((CardapioActivity) getActivity()).getDataManager().getContaDAO().get() != null;

        if (hasConta) loadRow(tableLayout);
        else {
            tableLayout.setVisibility(TableLayout.GONE);
        }
    }

    private void loadRow(TableLayout tableLayout) {

        View subItemView;
        String descricao;
        RowSubItem rowSubItem;

        this.listRowSubItem = new ArrayList<RowSubItem>();

        subItemView = TableRow.inflate(((CardapioActivity) getActivity()), R.layout.fragment_kit_detalhe_table_row, null);

        rowSubItem = new RowSubItem(((ImageView) subItemView.findViewById(R.id.fragment_kit_detalhe_table_row_image_view_seta_esquerda)), ((ImageView) subItemView.findViewById(R.id.fragment_kit_detalhe_table_row_image_view_seta_direita)), ((TextView) subItemView.findViewById(R.id.fragment_kit_detalhe_table_row_text_view_qtd)), this.kit);
        rowSubItem.getKit().setQuantidade(0l);
        rowSubItem.getTxtQtd().setText(String.format("%02d", rowSubItem.getKit().getQuantidade()));
        rowSubItem.getBtnDireita().setTag(rowSubItem);
        rowSubItem.getBtnEsquerda().setTag(rowSubItem);
        rowSubItem.getBtnDireita().setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RowSubItem rowSubItem = (RowSubItem) v.getTag();

                if (rowSubItem.getKit().getQuantidade() <= 99) {
                    rowSubItem.getKit().setQuantidade((rowSubItem.getKit().getQuantidade() + 1));
                    rowSubItem.getTxtQtd().setText(String.format("%02d", rowSubItem.getKit().getQuantidade()));
                }
            }
        });
        rowSubItem.getBtnEsquerda().setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                RowSubItem rowSubItem = (RowSubItem) v.getTag();

                if (rowSubItem.getKit().getQuantidade() >= 1) {
                    rowSubItem.getKit().setQuantidade((rowSubItem.getKit().getQuantidade() - 1));
                    rowSubItem.getTxtQtd().setText(String.format("%02d", rowSubItem.getKit().getQuantidade()));
                }
            }
        });

        descricao = this.kit.getNome();
        ((TextView) subItemView.findViewById(R.id.fragment_kit_detalhe_table_row_text_view_descricao)).setText(descricao);

        BigDecimal valorTotalKit = new BigDecimal("0");
        for (KitSubItem kitSubItem : this.kit.getKitSubItens()) {
            valorTotalKit = valorTotalKit.add(kitSubItem.getItem().getSubItens().get(0).getValorBigDecimal().multiply(new BigDecimal(kitSubItem.getQuantidade())));
        }

        ((TextView) subItemView.findViewById(R.id.fragment_kit_detalhe_table_row_text_view_valor)).setText(format.format(valorTotalKit.subtract(new BigDecimal(this.kit.getDesconto()))));

        this.listRowSubItem.add(rowSubItem);
        tableLayout.addView(subItemView);

    }

    private void loadRowKitSubItem(TableLayout tableLayout, List<KitSubItem> kitSubItens) {

        View subItemView;
        String descricao;

        for (KitSubItem kitSubItem : kitSubItens) {
            subItemView = TableRow.inflate(getActivity(), R.layout.fragment_kit_detalhe_table_row_sem_conta, null);

            descricao = " • " + String.format("%02d", kitSubItem.getQuantidade()) + " " + kitSubItem.getItem().getNome() + " - " + kitSubItem.getItem().getSubItens().get(0).getNome();
            ((TextView) subItemView.findViewById(R.id.linha_sub_kit_sem_conta_text_view_descricao)).setText(descricao);
            tableLayout.addView(subItemView);
        }
    }

    public class RowSubItem {

        private ImageView btnEsquerda;
        private ImageView btnDireita;
        private TextView txtQtd;
        private Kit kit;

        public RowSubItem(ImageView btnEsquerda, ImageView btnDireita, TextView txtQtd, Kit kit) {
            this.btnEsquerda = btnEsquerda;
            this.btnDireita = btnDireita;
            this.txtQtd = txtQtd;
            this.kit = kit;
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

        public Kit getKit() {
            return kit;
        }

        public void setKit(Kit kit) {
            this.kit = kit;
        }
    }

    private final class ResponseListener implements DialogListener {
        public void onComplete(Bundle values) {
            Bitmap bitmap = Utilitarios.drawableToBitmap(imageView.getDrawable());
            try {
                if (values.get("provider").equals(Provider.FACEBOOK.name().toLowerCase())) {
                    socialAuthAdapter.uploadImageAsync("Beach Stop Ipitanga\n " + kit.getNome() + "\n" + kit.getDescricao(), "png", bitmap, 1, new MessageListener());
                } else if (values.get("provider").equals(Provider.TWITTER.name().toLowerCase())) {
                    socialAuthAdapter.uploadImageAsync("Beach Stop Ipitanga " + kit.getNome(), "png", bitmap, 1, new MessageListener());
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
        public void onBack() {
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
                Toast.makeText(getActivity(), "Compartilhado!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "Erro ao compartilhar! Tente novamente mais tarde!", Toast.LENGTH_LONG).show();
        }
    }
}