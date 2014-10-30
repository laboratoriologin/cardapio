package com.login.beachstop.android.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.image.ImageFetcher;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Item> itens;
    private ImageFetcher mImageFetcher;

    public ItemListAdapter(Context context, List<Item> itens, ImageFetcher imageFetcher) {

        this.itens = itens;
        this.mInflater = LayoutInflater.from(context);
        this.mImageFetcher = imageFetcher;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = itens.get(position);

        convertView = mInflater.inflate(R.layout.adapter_list_view_item, null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.adapter_list_view_item_image_view);

        if (item.getImagem().length() != 0) {

            this.mImageFetcher.loadImage(Constantes.URL_IMG + item.getImagem(), imageView);

        }

        ((TextView) convertView.findViewById(R.id.adapter_list_view_item_text_view_nome)).setText(item.getNome());
        ((TextView) convertView.findViewById(R.id.adapter_list_view_item_text_view_ingrediente)).setText(item.getIngrediente());

        // TableLayout tb = (TableLayout)
        // convertView.findViewById(R.id.fragment_list_view_item_categoria_cardapio_table_layout_sub_item);
        // TableRow tr = new TableRow(convertView.getContext());
        //
        // TextView tv = new TextView(convertView.getContext());
        // tv.setText("R$");
        // tv.setTextSize(10);
        // tv.setLayoutParams(new TableRow.LayoutParams(1));
        // tv.setTextColor(Color.parseColor("#2175B1"));
        //
        // tr.addView(tv);
        //
        // tv = new TextView(convertView.getContext());
        // tv.setText(itemCardapio.getSubItens().get(0).getDescricaoTipoQuantidade());
        // tv.setTextSize(10);
        // tv.setLayoutParams(new TableRow.LayoutParams(2));
        // tv.setTextColor(Color.parseColor("#2175B1"));
        //
        // tr.addView(tv);
        //
        // tb.addView(tr);
        //
        // TableRow.LayoutParams tlp = new
        // TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
        // TableRow.LayoutParams.MATCH_PARENT);
        // tlp.gravity = Gravity.CENTER;
        //
        // int i = 0;
        //
        // for (ItemCardapioSubItem subItemCardapio :
        // itemCardapio.getSubItens()) {
        //
        // i++;
        //
        // tr = new TableRow(convertView.getContext());
        //
        // tv = new TextView(convertView.getContext());
        // tv.setText(subItemCardapio.getDescricao());
        // tv.setLayoutParams(new TableRow.LayoutParams(0));
        // tv.setTextSize(10);
        //
        // tr.addView(tv);
        //
        // tv = new TextView(convertView.getContext());
        // tv.setText(subItemCardapio.getValorBigDecimal().toString());
        // tv.setLayoutParams(new TableRow.LayoutParams(1));
        // tv.setTextSize(10);
        //
        // tr.addView(tv);
        //
        // tv = new TextView(convertView.getContext());
        // tv.setText(subItemCardapio.getQuantidade().toString());
        // tv.setLayoutParams(new TableRow.LayoutParams(2));
        // tv.setTextSize(10);
        // tv.setLayoutParams(tlp);
        //
        // if (i <= 3) {
        // tr.addView(tv);
        // tb.addView(tr);
        // }
        //
        // if (i == 4) {
        // tr = new TableRow(convertView.getContext());
        //
        // tv = new TextView(convertView.getContext());
        // tv.setText("Ver mais...");
        // tv.setLayoutParams(new TableRow.LayoutParams(0));
        // tv.setTextColor(Color.parseColor("#65000000"));
        // tv.setTextSize(8);
        //
        // tr.addView(tv);
        // tb.addView(tr);
        // }
        // }

        return convertView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.itens.size();
    }

    @Override
    public Object getItem(int position) {
        return this.itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
