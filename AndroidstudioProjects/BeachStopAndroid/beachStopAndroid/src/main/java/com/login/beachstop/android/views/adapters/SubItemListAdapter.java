package com.login.beachstop.android.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.fragments.PedidoFragment;
import com.login.beachstop.android.managers.sqlite.dao.DataManager;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.KitSubItem;
import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Argus on 30/10/2014.
 */
public class SubItemListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Pedido pedido;
    private DataManager dataManager;
    private TextView valorTotal;
    private PedidoFragment pedidoFragment;

    public SubItemListAdapter(Context context, Pedido pedido, DataManager dataManager, TextView valorTotal, PedidoFragment pedidoFragment) {

        this.setPedido(pedido);
        this.setmInflater(LayoutInflater.from(context));
        this.setDataManager(dataManager);
        this.setValorTotal(valorTotal);
        this.pedidoFragment = pedidoFragment;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.adapter_list_sub_item, null);

        final PedidoSubItem pedidoSubItem = this.getPedido().getPedidoSubItens().get(position);
        final TextView tv;

        if (pedidoSubItem.getKitId() == 0) {
            Item item = this.dataManager.getItemDAO().getBySubItem(pedidoSubItem.getSubItem());
            ((TextView) convertView.findViewById(R.id.adapter_list_sub_item_text_view_descricao)).setText(item.getNome() + " - " + pedidoSubItem.getSubItem().getNome());
        } else {
            ((TextView) convertView.findViewById(R.id.adapter_list_sub_item_text_view_descricao)).setText(pedidoSubItem.getKit().getNome());
        }

        tv = (TextView) convertView.findViewById(R.id.adapter_list_sub_item_text_view_qtd);
        tv.setText((String.format("%02d", pedidoSubItem.getQuantidade())));

        convertView.findViewById(R.id.adapter_list_sub_item_image_view_seta_esquerda).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                atualizaQtdPedido(false, tv, pedidoSubItem);
            }
        });

        convertView.findViewById(R.id.adapter_list_sub_item_seta_direita).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                atualizaQtdPedido(true, tv, pedidoSubItem);
            }
        });

        convertView.findViewById(R.id.adapter_list_sub_item_text_view_menos).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteItemPedido(pedidoSubItem);
            }
        });

        return convertView;
    }

    public void atualizaQtdPedido(Boolean isSoma, TextView campoQtd, PedidoSubItem pedidoSubItem) {

        if (isSoma) {
            if (pedidoSubItem.getQuantidade() < 99) {
                try {
                    pedidoSubItem.addQtd(1l);
                    this.dataManager.getPedidoSubItemDAO().update(pedidoSubItem, pedidoSubItem.getId());
                    campoQtd.setText((String.format("%02d", pedidoSubItem.getQuantidade())));
                } catch (Exception e) {
                    // TODO: EXIBIR MSG DE ERRO
                }
            }
        } else {
            if (pedidoSubItem.getQuantidade() > 1) {
                try {
                    pedidoSubItem.subQtd(1l);
                    this.dataManager.getPedidoSubItemDAO().update(pedidoSubItem, pedidoSubItem.getId());
                    campoQtd.setText((String.format("%02d", pedidoSubItem.getQuantidade())));
                } catch (Exception e) {
                    // TODO: EXIBIR MSG DE ERRO
                }
            }
        }
        atualizaValorTotalPedido();
        this.notifyDataSetChanged();
    }

    public void deleteItemPedido(PedidoSubItem pedidoSubItem) {
        if (this.dataManager.getPedidoSubItemDAO().delete(pedidoSubItem.getId())) {
            this.pedido.getPedidoSubItens().remove(this.pedido.getPedidoSubItens().indexOf(pedidoSubItem));
            this.notifyDataSetChanged();
            atualizaValorTotalPedido();
        }
    }

    public void atualizaValorTotalPedido() {

        BigDecimal valorTotalPedido = new BigDecimal(0);

        for (PedidoSubItem pedidoSubItem : this.pedido.getPedidoSubItens()) {
            //Se o id do kit estiver zero é por que é um item
            if (pedidoSubItem.getKitId() == 0) {
                valorTotalPedido = valorTotalPedido.add(new BigDecimal(pedidoSubItem.getQuantidade()).multiply(pedidoSubItem.getSubItem().getValorBigDecimal()));
            } else {
                BigDecimal valorTotalKit = new BigDecimal(0);
                for (KitSubItem kitSubItem : pedidoSubItem.getKit().getKitSubItens()) {
                    valorTotalKit = valorTotalKit.add(kitSubItem.getItem().getSubItens().get(0).getValorBigDecimal().multiply(new BigDecimal(kitSubItem.getQuantidade())));
                }
                valorTotalKit = valorTotalKit.subtract(new BigDecimal(pedidoSubItem.getKit().getDesconto()));
                valorTotalPedido = valorTotalPedido.add(valorTotalKit);
            }
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        this.valorTotal.setText(format.format(valorTotalPedido.doubleValue()));
        this.pedidoFragment.changeViewPedido();
    }

    public int getCount() {
        return this.getPedido().getPedidoSubItens().size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.getPedido().getPedidoSubItens().get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public TextView getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(TextView valorTotal) {
        this.valorTotal = valorTotal;
    }

    public PedidoFragment getPedidoFragment() {
        return pedidoFragment;
    }

    public void setPedidoFragment(PedidoFragment pedidoFragment) {
        this.pedidoFragment = pedidoFragment;
    }
}
