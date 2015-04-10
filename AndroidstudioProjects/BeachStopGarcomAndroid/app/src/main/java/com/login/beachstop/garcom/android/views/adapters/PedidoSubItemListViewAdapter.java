package com.login.beachstop.garcom.android.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.garcom.android.R;
import com.login.beachstop.garcom.android.models.SubItem;
import com.login.beachstop.garcom.android.views.quickscroll.Scrollable;

import java.util.Collections;
import java.util.List;

/**
 * Created by Argus on 27/01/2015.
 */
public class PedidoSubItemListViewAdapter extends BaseAdapter implements Scrollable {

    private LayoutInflater layoutInflater;
    private List<SubItem> subItens;

    public PedidoSubItemListViewAdapter(final List<SubItem> subItens, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.subItens = subItens;
    }

    @Override
    public SubItem getItem(int position) {
        return this.subItens.get(position);
    }

    @Override
    public int getCount() {
        return this.subItens.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SubItem subItem = this.subItens.get(position);
        convertView = this.layoutInflater.inflate(R.layout.adapter_list_view_pedido_sub_item, null);

        ImageView imgEsquerda = (ImageView) convertView.findViewById(R.id.adapter_list_view_pedido_sub_item_image_view_menos);
        ImageView imgDireita = (ImageView) convertView.findViewById(R.id.adapter_list_view_pedido_sub_item_image_view_mais);
        final TextView txtQtd = (TextView) convertView.findViewById(R.id.adapter_list_view_pedido_sub_item_text_view_qtd);

        ((TextView)convertView.findViewById(R.id.adapter_list_view_pedido_sub_item_text_view_descricao)).setText(String.format("%s - %s (%s)", subItem.getItem().getNome(), subItem.getNome(), subItem.getCodigo()));
        txtQtd.setText((String.format("%02d", subItem.getQtdSelecionado())));

        imgEsquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaQtdPedido(false, txtQtd, subItem);
            }
        });

        imgDireita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaQtdPedido(true, txtQtd, subItem);
            }
        });

        return convertView;
    }

    @Override
    public String getIndicatorForPosition(int arg0, int arg1) {
        return this.subItens.get(arg0).getIndicator();
    }

    @Override
    public int getScrollPosition(int arg0, int arg1) {
        return arg0;
    }

    public List<SubItem> getSubItens() {
        return subItens;
    }

    public void setSubItens(List<SubItem> subItens) {
        this.subItens = subItens;
    }

    public void zerarQuantidade() {
        for (SubItem subItem : this.subItens) {
            subItem.setQtdSelecionado(0);
        }
        Collections.sort(this.subItens);
        this.notifyDataSetChanged();
    }

    public void atualizaQtdPedido(Boolean isSoma, TextView campoQtd, SubItem subItem) {
        if (isSoma) {
            if (subItem.getQtdSelecionado() < 99) {
                try {
                    subItem.addQtd(1l);
                    campoQtd.setText((String.format("%02d", subItem.getQtdSelecionado())));
                } catch (Exception e) {
                    // TODO: EXIBIR MSG DE ERRO
                }
            }
        } else {
            if (subItem.getQtdSelecionado() > 0) {
                try {
                    subItem.subQtd(1l);
                    campoQtd.setText((String.format("%02d", subItem.getQtdSelecionado())));
                } catch (Exception e) {
                    // TODO: EXIBIR MSG DE ERRO
                }
            }
        }
    }
}
