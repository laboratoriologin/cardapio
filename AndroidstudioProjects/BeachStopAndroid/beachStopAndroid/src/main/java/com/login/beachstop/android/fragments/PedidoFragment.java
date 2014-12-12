package com.login.beachstop.android.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.android.PedidoActivity;
import com.login.beachstop.android.R;
import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.PedidoRequest;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.views.adapters.SubItemListAdapter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Argus on 30/10/2014.
 */
public class PedidoFragment extends Fragment implements IPedidoFragment {

    private View view;
    private ListView listViewPedidoItem;
    private SubItemListAdapter subItemListAdapter;
    private Pedido pedido;
    private PedidoActivity pedidoActivity;
    private TextView valorTotal;
    private Button buttonEnviarPedido;
    private TextView textViewSemPedido;
    private ResponseListener responseListenerPedido = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            if (serverResponse != null) {

                if (serverResponse.isOK()) {

                    try {

                        pedido.setFinalizadoSys(true);

                        pedidoActivity.getDataManager().getPedidoDAO().update(pedido, pedido.getId());

                        pedido = null;

                        atualizaValorTotalPedido();

                        Toast.makeText(pedidoActivity, "Estamos preparando o seu pedido, divirta-se!", Toast.LENGTH_SHORT).show();

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

        this.view = inflater.inflate(R.layout.fragment_pedido, container, false);

        this.listViewPedidoItem = (ListView) this.view.findViewById(R.id.fragment_pedido_list_view_item_pedido);

        this.pedido = this.pedidoActivity.getDataManager().getPedidoDAO().get(false);

        this.valorTotal = ((TextView) this.view.findViewById(R.id.fragment_pedido_text_view_valor));

        this.textViewSemPedido = ((TextView) this.view.findViewById(R.id.fragment_pedido_text_view_sem_pedido));

        if (this.pedido != null && this.pedido.getPedidoSubItens().size() != 0) {

            this.subItemListAdapter = new SubItemListAdapter(this.view.getContext(), this.pedido, this.pedidoActivity.getDataManager(), this.valorTotal, this);

            this.listViewPedidoItem.setAdapter(this.subItemListAdapter);

            this.atualizaValorTotalPedido();
        }

        changeViewPedido();

        this.buttonEnviarPedido = ((Button) this.view.findViewById(R.id.fragment_pedido_button_enviar_pedido));

        this.buttonEnviarPedido.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                pedido = pedidoActivity.getDataManager().getPedidoDAO().get(false);

                if (pedido != null && pedido.getPedidoSubItens().size() != 0) {

                    for (PedidoSubItem pedidoSubItem : pedido.getPedidoSubItens()) {

                        if (pedidoSubItem.getQuantidade() == 0) {

                            if (pedidoActivity.getDataManager().getPedidoSubItemDAO().delete(pedidoSubItem.getId())) {

                                pedido.getPedidoSubItens().remove(pedido.getPedidoSubItens().indexOf(pedidoSubItem));

                            }

                        }

                    }

                    openDialog();

                } else {

                    Toast.makeText(pedidoActivity, "Hum! Não pediu nada! \n Olhe as promoções!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return view;
    }

    public void openDialog() {

        final Dialog dialog = new Dialog(this.pedidoActivity, R.style.style_bg_dialog_enviar_pedido);

        dialog.setContentView(R.layout.fragment_pedido_dialog);

        dialog.setTitle("Alguma observação?");

        Button dialogButton = (Button) dialog.findViewById(R.id.fragment_pedido_dialog_button_enviar_pedido);

        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonEnviarPedido.setText("Enviando...");

                buttonEnviarPedido.setEnabled(false);

                pedido.setObservacao(((EditText) dialog.findViewById(R.id.fragment_pedido_dialog_text_view_observacao)).getText().toString());

                new PedidoRequest(responseListenerPedido).post(pedido);

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    @Override
    public void onResume() {

        super.onResume();

        this.pedido = this.pedidoActivity.getDataManager().getPedidoDAO().get(false);

        changeViewPedido();

        if (this.pedido != null && this.pedido.getPedidoSubItens().size() != 0) {

            if (this.subItemListAdapter != null) {

                this.subItemListAdapter.setPedido(this.pedido);

                this.subItemListAdapter.notifyDataSetChanged();

                this.atualizaValorTotalPedido();

            } else {

                this.subItemListAdapter = new SubItemListAdapter(this.view.getContext(), this.pedido, this.pedidoActivity.getDataManager(), this.valorTotal, this);

                this.listViewPedidoItem.setAdapter(this.subItemListAdapter);

                this.atualizaValorTotalPedido();

            }

        }

    }

    public void atualizaValorTotalPedido() {

        BigDecimal valorTotalPedido = new BigDecimal(0);

        if (this.pedido != null) {

            for (PedidoSubItem pedidoSubItem : this.pedido.getPedidoSubItens()) {

                valorTotalPedido = valorTotalPedido.add(new BigDecimal(pedidoSubItem.getQuantidade()).multiply(pedidoSubItem.getSubItem().getValorBigDecimal()));

            }

        }

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        this.valorTotal.setText(format.format(valorTotalPedido.doubleValue()));

        changeViewPedido();

    }

    public void changeViewPedido() {

        if (this.pedido != null && this.pedido.getPedidoSubItens().size() != 0) {

            this.textViewSemPedido.setVisibility(TextView.GONE);

            this.listViewPedidoItem.setVisibility(ListView.VISIBLE);

        } else {

            this.textViewSemPedido.setVisibility(TextView.VISIBLE);

            this.listViewPedidoItem.setVisibility(ListView.GONE);

        }
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

    }

}
