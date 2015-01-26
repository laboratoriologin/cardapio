package com.login.beachstop.garcom.android.views.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.garcom.android.DefaultActivity;
import com.login.beachstop.garcom.android.HomeActivity;
import com.login.beachstop.garcom.android.R;
import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.AcaoContaRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import java.util.List;

/**
 * Created by Argus on 13/01/2015.
 */
public class AcaoContaListViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<AcaoConta> acaoContas;
    private HomeActivity homeActivity;

    public AcaoContaListViewAdapter(HomeActivity homeActivity, List<AcaoConta> acaoContas) {
        this.homeActivity = homeActivity;
        this.acaoContas = acaoContas;
        this.layoutInflater = LayoutInflater.from(homeActivity);
    }

    @Override
    public int getCount() {
        return this.acaoContas.size();
    }

    @Override
    public Object getItem(int i) {
        return this.acaoContas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.acaoContas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AcaoConta acaoConta = this.acaoContas.get(i);
        if (Constantes.Acao.CHAMARGARCOM.equals(acaoConta.getAcao().getId()) || Constantes.Acao.PEDIRCONTA.equals(acaoConta.getAcao().getId())) {
            return this.getViewSynthetic(i, acaoConta, viewGroup);
        } else if (Constantes.Acao.PEDIDOS.equals(acaoConta.getAcao().getId())) {
            return this.getViewAnalytic(i, acaoConta, viewGroup);
        }
        return null;
    }

    private View getViewSynthetic(int i, AcaoConta acaoConta, View viewGroup) {
        viewGroup = layoutInflater.inflate(R.layout.adapter_list_view_acao_item, null);

        TableLayout tableLayout = (TableLayout) viewGroup.findViewById(R.id.adapter_list_view_acao_table_layout);
        tableLayout.removeAllViews();

        View viewRowItem = layoutInflater.inflate(R.layout.adapter_list_view_acao_item_row, null);

        viewRowItem.findViewById(R.id.adapter_list_view_acao_item_row_linear_layout).setBackgroundColor(Color.WHITE);
        ((TextView) viewRowItem.findViewById(R.id.adapter_list_view_acao_item_row_text_view_mesa)).setText(String.format("Mesa %s ", acaoConta.getConta().getNumero().toString()));
        ((TextView) viewRowItem.findViewById(R.id.adapter_list_view_acao_item_row_text_view_tipo_acao)).setText(this.homeActivity.getDataManager().getAcaoDAO().get(acaoConta.getAcao().getId()).getDescricao());

        ((TextView) viewRowItem.findViewById(R.id.adapter_list_view_acao_item_row_text_view_tempo)).setText(acaoConta.getDiffHorarioSolicitacao() + " Min ");

        ((Button) viewRowItem.findViewById(R.id.adapter_list_view_acao_item_row_button_aprovar)).setTag(acaoConta);
        ((Button) viewRowItem.findViewById(R.id.adapter_list_view_acao_item_row_button_aprovar)).setOnClickListener(homeActivity.resolverAlerta);


        tableLayout.addView(viewRowItem);

        return viewGroup;
    }

    private View getViewAnalytic(int i, AcaoConta acaoConta, View viewGroup) {
        return null;
    }
}
