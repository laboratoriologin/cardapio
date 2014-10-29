package com.login.beachstop.android.views.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.login.beachstop.android.CardapioActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Categoria;
import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.DrawableManager;
import com.login.beachstop.android.utils.LoadImage;

import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ExpandableAllCategoriaItemAdapter extends BaseExpandableListAdapter {

    private CardapioActivity activity;
    private LayoutInflater inflater;
    private List<Categoria> parentItems;

    public ExpandableAllCategoriaItemAdapter(CardapioActivity activity, List<Categoria> parentItems) {
        this.activity = activity;
        this.parentItems = parentItems;
        this.inflater = LayoutInflater.from(this.activity);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Categoria categoria = this.parentItems.get(groupPosition);


        return this.activity.getDataManager().getItemDAO().getAll(categoria.getId()).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Categoria categoria = this.parentItems.get(groupPosition);

        return this.activity.getDataManager().getItemDAO().getAll(categoria.getId()).get(childPosition).getId();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Categoria categoria = this.parentItems.get(groupPosition);

        Item item = this.activity.getDataManager().getItemDAO().getAll(categoria.getId()).get(childPosition);

        convertView = inflater.inflate(R.layout.adapter_expandable_all_categoria_item_child, null);

        ((TextView) convertView.findViewById(R.id.adapter_expandable_all_categoria_item_child_text_view)).setText(item.getNome());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Categoria categoria = this.parentItems.get(groupPosition);
        return this.activity.getDataManager().getItemDAO().getAll(categoria.getId()).size();
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

        Categoria categoria = this.parentItems.get(groupPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_expandable_all_categoria_item_parent, null);
        }

        Drawable img = DrawableManager.getDrawableManager().getDrawable(Constantes.URL_IMG + categoria.getImagem());

        if (img == null) {

            new LoadImage(((ImageView) (convertView.findViewById(R.id.adapter_expandable_all_categoria_item_parent_image_view))), convertView.getContext()).execute(Constantes.URL_IMG + categoria.getImagem());

        } else {

            ((ImageView) (convertView.findViewById(R.id.adapter_expandable_all_categoria_item_parent_image_view))).setImageDrawable(img);

        }

        ((TextView) convertView.findViewById(R.id.adapter_expandable_all_categoria_item_parent_text_view)).setText(categoria.getDescricao());

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
