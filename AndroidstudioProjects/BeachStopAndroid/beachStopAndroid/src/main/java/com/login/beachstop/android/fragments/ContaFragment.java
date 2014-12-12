package com.login.beachstop.android.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.PedidoActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Conta;
import com.login.beachstop.android.models.PedidoSubItem;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.ContaRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Argus on 30/10/2014.
 */
public class ContaFragment extends Fragment implements IPedidoFragment {

    private View view;
    private Conta conta;
    private TableLayout tableLayoutItemConta;
    private TextView textViewSemPedido;
    private PedidoActivity pedidoActivity;
    private TextView textViewValorTotal;
    private CheckBox checkBoxDez;
    private Double valorTotal = 0D;
    private ResponseListener responseListenerConta = new ResponseListener() {

        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        conta = (Conta) serverResponse.getReturnObject();

                        for (PedidoSubItem pedidoSubItem : conta.getPedidos().get(0).getPedidoSubItens()) {

                            pedidoSubItem.setSubItem(pedidoActivity.getDataManager().getSubItemDAO().get(pedidoSubItem.getSubItemId()));

                        }

                        if (conta != null && conta.getPedidos().size() != 0 && conta.getPedidos().get(0).getPedidoSubItens().size() != 0) {

                            textViewSemPedido.setVisibility(TextView.GONE);
                            tableLayoutItemConta.setVisibility(TableLayout.VISIBLE);

                            bindTableItemConta();

                        } else {

                            textViewSemPedido.setVisibility(TextView.VISIBLE);
                            tableLayoutItemConta.setVisibility(TableLayout.GONE);

                        }

                        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                        valorTotal = new Double(conta.getValorTotal()) + new Double(conta.getValorTotalPago());

                        textViewValorTotal.setText(format.format(valorTotal));

                        checkBoxDez.setText(" + 10% (" + format.format(valorTotal * 0.1) + ")");

                    } catch (Exception e) {

                        Toast.makeText(pedidoActivity, Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_LONG).show();

                    }


                } else {

                    Toast.makeText(pedidoActivity, serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();

                }


            } else {

                Toast.makeText(pedidoActivity, Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();

            }

        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container == null) {

            return null;

        }

        this.pedidoActivity = (PedidoActivity) inflater.getContext();

        this.view = inflater.inflate(R.layout.fragment_conta, container, false);
        this.tableLayoutItemConta = (TableLayout) this.view.findViewById(R.id.fragment_conta_list_view_item_conta);
        this.textViewSemPedido = (TextView) this.view.findViewById(R.id.fragment_conta_text_view_sem_conta);
        this.textViewValorTotal = (TextView) this.view.findViewById(R.id.fragment_conta_text_view_valor);
        this.checkBoxDez = (CheckBox) this.view.findViewById(R.id.fragment_conta_checkbox_gorjeta);

        if (this.pedidoActivity.getDataManager().getContaDAO().get() != null) {

            new ContaRequest(responseListenerConta).get(this.pedidoActivity.getDataManager().getContaDAO().get());

        }

        this.checkBoxDez.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                CheckBox checkBox = (CheckBox) v;

                NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                if (checkBox.isChecked()) {

                    textViewValorTotal.setText(format.format(valorTotal + (valorTotal * 0.1)));

                } else {

                    textViewValorTotal.setText(format.format(valorTotal));

                }

            }

        });

        return view;
    }

    @SuppressLint("UseValueOf")
    public void bindTableItemConta() {

        PedidoSubItem pedidoSubItem;
        View itemContaView;
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        this.tableLayoutItemConta.removeAllViews();

        for (int position = 0; position < this.conta.getPedidos().get(0).getPedidoSubItens().size(); position++) {

            pedidoSubItem = this.conta.getPedidos().get(0).getPedidoSubItens().get(position);

            itemContaView = TableRow.inflate(this.getView().getContext(), R.layout.fragment_conta_table_row, null);

            if ((position % 2) == 0) {

                ((TableRow) itemContaView.findViewById(R.id.fragment_conta_table_row_linear_layout)).setBackgroundResource(R.color.branco);

            } else {

                ((TableRow) itemContaView.findViewById(R.id.fragment_conta_table_row_linear_layout)).setBackgroundResource(R.color.bg_system);

            }

            ((TextView) itemContaView.findViewById(R.id.fragment_conta_table_row_text_view_nome)).setText(pedidoSubItem.getSubItem().getDescricao());
            ((TextView) itemContaView.findViewById(R.id.fragment_conta_table_row_text_view_valor_unitario)).setText(format.format(new Double(pedidoSubItem.getSubItem().getValor())));
            ((TextView) itemContaView.findViewById(R.id.fragment_conta_table_row_text_view_qtd)).setText(pedidoSubItem.getQuantidade().toString());
            ((TextView) itemContaView.findViewById(R.id.fragment_conta_table_row_text_view_valor_total)).setText(format.format((new Double(pedidoSubItem.getValorTotal()))));

            this.tableLayoutItemConta.addView(itemContaView);

        }
    }

    @Override
    public void onRefresh() {

        if (this.pedidoActivity.getDataManager().getContaDAO().get() != null) {

            new ContaRequest(responseListenerConta).get(this.pedidoActivity.getDataManager().getContaDAO().get());

        }

    }

}
