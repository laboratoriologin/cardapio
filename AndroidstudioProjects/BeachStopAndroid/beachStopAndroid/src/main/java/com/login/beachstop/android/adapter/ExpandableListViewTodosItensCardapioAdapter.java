package com.login.beachstop.android.adapter;

import java.util.List;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.login.beachstop.android.HomeActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.model.CategoriaCardapioItemSys;
import com.login.beachstop.android.model.ItemCardapio;
import com.login.beachstop.android.model.ItemCardapioSubItem;

public class ExpandableListViewTodosItensCardapioAdapter extends BaseExpandableListAdapter {

	private HomeActivity activity;
	private LayoutInflater inflater;
	private List<CategoriaCardapioItemSys> parentItems;

	public ExpandableListViewTodosItensCardapioAdapter(HomeActivity _activity, List<CategoriaCardapioItemSys> _parentItems) {
		this.activity = _activity;
		this.parentItems = _parentItems;
		this.inflater = LayoutInflater.from(this.activity);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		CategoriaCardapioItemSys itemMenu = this.parentItems.get(groupPosition);

		return this.activity.getDataManager().getAll((long) itemMenu.getId()).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		CategoriaCardapioItemSys itemMenu = this.parentItems.get(groupPosition);

		return this.activity.getDataManager().getAll((long) itemMenu.getId()).get(childPosition).getId();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		CategoriaCardapioItemSys itemMenu = this.parentItems.get(groupPosition);

		ItemCardapio itemCardapio = this.activity.getDataManager().getAll((long) itemMenu.getId()).get(childPosition);

		convertView = inflater.inflate(R.layout.fragment_expandable_list_view_todos_item_cardapio_child, null);

		((TextView) convertView.findViewById(R.id.fragment_expandable_list_view_todos_item_cardapio_child_text_view)).setText(itemCardapio.getNome());

//		TableLayout tb = (TableLayout) convertView.findViewById(R.id.fragment_expandable_list_view_todos_item_cardapio_child_table_layout_sub_item);
//		TableRow tr = new TableRow(convertView.getContext());
//
//		TextView tv = new TextView(convertView.getContext());
//		// tv.setText("R$");
//		// tv.setTextSize(10);
//		// tv.setLayoutParams(new TableRow.LayoutParams(1));
//		// tv.setTextColor(Color.parseColor("#2175B1"));
//		//
//		// tr.addView(tv);
//
//		tv = new TextView(convertView.getContext());
//		tv.setText(itemCardapio.getSubItens().get(0).getDescricaoTipoQuantidade());
//		tv.setTextSize(10);
//		tv.setLayoutParams(new TableRow.LayoutParams(2));
//		tv.setTextColor(Color.parseColor("#2175B1"));
//
//		tr.addView(tv);
//
//		tb.addView(tr);
//
//		TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
//		tlp.gravity = Gravity.CENTER;
//
//		int i = 0;
//
//		for (ItemCardapioSubItem subItemCardapio : itemCardapio.getSubItens()) {
//
//			i++;
//
//			tr = new TableRow(convertView.getContext());
//
//			tv = new TextView(convertView.getContext());
//			tv.setText(subItemCardapio.getDescricao());
//			tv.setLayoutParams(new TableRow.LayoutParams(0));
//			tv.setTextSize(10);
//
//			tr.addView(tv);
//
//			// tv = new TextView(convertView.getContext());
//			// tv.setText(subItemCardapio.getValorBigDecimal().toString());
//			// tv.setLayoutParams(new TableRow.LayoutParams(1));
//			// tv.setTextSize(10);
//			//
//			// tr.addView(tv);
//
//			tv = new TextView(convertView.getContext());
//			tv.setText(subItemCardapio.getQuantidade().toString());
//			tv.setLayoutParams(new TableRow.LayoutParams(2));
//			tv.setTextSize(10);
//			tv.setLayoutParams(tlp);
//
//			if (i <= 3) {
//				tr.addView(tv);
//				tb.addView(tr);
//			}
//
//			if (i == 4) {
//				tr = new TableRow(convertView.getContext());
//
//				tv = new TextView(convertView.getContext());
//				tv.setText("Ver mais...");
//				tv.setLayoutParams(new TableRow.LayoutParams(0));
//				tv.setTextColor(Color.parseColor("#65000000"));
//				tv.setTextSize(8);
//
//				tr.addView(tv);
//				tb.addView(tr);
//			}
//		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		CategoriaCardapioItemSys itemMenu = this.parentItems.get(groupPosition);
		return this.activity.getDataManager().getAll((long) itemMenu.getId()).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.parentItems.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.parentItems.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return this.parentItems.get(groupPosition).getId();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		CategoriaCardapioItemSys itemMenu = this.parentItems.get(groupPosition);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_expandable_list_view_todos_item_cardapio_parent, null);
		}

		((ImageView) (convertView.findViewById(R.id.fragment_expandable_list_view_todos_item_cardapio_parent_image_view))).setBackgroundResource(itemMenu.getResourceImgTopoCardapio());
		((TextView) convertView.findViewById(R.id.fragment_expandable_list_view_todos_item_cardapio_parent_text_view)).setText(itemMenu.getNome());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
