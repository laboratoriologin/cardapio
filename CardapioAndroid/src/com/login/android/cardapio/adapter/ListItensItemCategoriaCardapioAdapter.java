package com.login.android.cardapio.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.login.android.cardapio.R;
import com.login.android.cardapio.model.ItemCardapio;
import com.login.android.cardapio.model.ItemCardapioSubItem;
import com.login.android.cardapio.util.Constantes;
import com.login.android.cardapio.util.DrawableManager;
import com.login.android.cardapio.util.LoadImage;

public class ListItensItemCategoriaCardapioAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ItemCardapio> listaItemCategoriaCardapio;	

	public ListItensItemCategoriaCardapioAdapter(Context _context, List<ItemCardapio> _listaItemCategoriaCardapio) {
		this.listaItemCategoriaCardapio = _listaItemCategoriaCardapio;
		this.mInflater = LayoutInflater.from(_context);		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ItemCardapio itemCardapio = listaItemCategoriaCardapio.get(position);

		convertView = mInflater.inflate(R.layout.fragment_list_view_item_categoria_cardapio, null);

		ImageView imageView = (ImageView) convertView.findViewById(R.id.fragment_list_view_item_categoria_cardapio_image_view);

		Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + itemCardapio.getImagem());

		if (img == null) {

			new LoadImage(imageView, convertView.getContext()).execute(Constantes.URL_IMG + itemCardapio.getImagem());

		} else {

			imageView.setImageDrawable(img);

		}

		((TextView) convertView.findViewById(R.id.fragment_list_view_item_categoria_cardapio_text_view_nome)).setText(itemCardapio.getNome());
		((TextView) convertView.findViewById(R.id.fragment_list_view_item_categoria_cardapio_text_view_ingrediente)).setText(itemCardapio.getIngredientes());

		TableLayout tb = (TableLayout) convertView.findViewById(R.id.fragment_list_view_item_categoria_cardapio_table_layout_sub_item);
		TableRow tr = new TableRow(convertView.getContext());

		TextView tv = new TextView(convertView.getContext());
		tv.setText("R$");
		tv.setTextSize(10);
		tv.setLayoutParams(new TableRow.LayoutParams(1));
		tv.setTextColor(Color.parseColor("#83C900"));

		tr.addView(tv);

		tv = new TextView(convertView.getContext());
		tv.setText(itemCardapio.getSubItens().get(0).getDescricaoTipoQuantidade());
		tv.setTextSize(10);
		tv.setLayoutParams(new TableRow.LayoutParams(2));
		tv.setTextColor(Color.parseColor("#83C900"));

		tr.addView(tv);

		tb.addView(tr);

		TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
		tlp.gravity = Gravity.CENTER;

		int i = 0;

		for (ItemCardapioSubItem subItemCardapio : itemCardapio.getSubItens()) {

			i++;

			tr = new TableRow(convertView.getContext());

			tv = new TextView(convertView.getContext());
			tv.setText(subItemCardapio.getDescricao());
			tv.setLayoutParams(new TableRow.LayoutParams(0));
			tv.setTextSize(10);

			tr.addView(tv);

			tv = new TextView(convertView.getContext());
			tv.setText(subItemCardapio.getValorBigDecimal().toString());
			tv.setLayoutParams(new TableRow.LayoutParams(1));
			tv.setTextSize(10);

			tr.addView(tv);

			tv = new TextView(convertView.getContext());
			tv.setText(subItemCardapio.getQuantidade().toString());
			tv.setLayoutParams(new TableRow.LayoutParams(2));
			tv.setTextSize(10);
			tv.setLayoutParams(tlp);

			if (i <= 3) {
				tr.addView(tv);
				tb.addView(tr);
			}
			
			if(i == 4){
				tr = new TableRow(convertView.getContext());

				tv = new TextView(convertView.getContext());
				tv.setText("Ver mais...");
				tv.setLayoutParams(new TableRow.LayoutParams(0));
				tv.setTextColor(Color.parseColor("#65000000"));
				tv.setTextSize(8);

				tr.addView(tv);
				tb.addView(tr);
			}
		}

		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listaItemCategoriaCardapio.size();
	}

	@Override
	public Object getItem(int position) {
		return listaItemCategoriaCardapio.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
