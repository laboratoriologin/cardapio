package com.login.beachstop.garcom.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.Conta;
import com.login.beachstop.garcom.android.models.PedidoSubItem;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.SubItem;
import com.login.beachstop.garcom.android.network.AcaoContaRequest;
import com.login.beachstop.garcom.android.network.ContaRequest;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.views.actionbar.ActionBar;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Argus on 02/02/2015.
 */
public class HistoricoContaActivity extends DefaultActivity {

    private Conta conta;
    private Button buttonFecharConta;
    private TableLayout tableLayout;
    private TextView textViewSemPedido;
    private TextView textViewValorTotal;
    private TextView textViewFecharConta;
    private Double valorTotal;
    private ProgressDialog progressDialog;
    private ResponseListener responseListenerGetContaByNumero = new ResponseListener() {
        @Override
        public void onResult(ServerResponse serverResponse) {
            progressDialog.dismiss();
            if (serverResponse != null) {
                if (serverResponse.isOK()) {
                    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                    try {
                        conta = (Conta) serverResponse.getReturnObject();
                        if (conta != null && conta.getPedidos() != null && conta.getPedidos().size() != 0 && conta.getPedidos().get(0).getPedidoSubItens().size() != 0) {
                            textViewSemPedido.setVisibility(TextView.GONE);
                            tableLayout.setVisibility(TableLayout.VISIBLE);
                            bindTableItemConta();
                        } else {
                            conta = null;
                            textViewSemPedido.setVisibility(TextView.VISIBLE);
                            tableLayout.setVisibility(TableLayout.GONE);
                        }

                        valorTotal = new Double(conta.getValorTotal());
                        textViewValorTotal.setText(format.format(valorTotal));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_GRAVAR_DADOS, Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), serverResponse.getMsgErro(), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), Constantes.MSG_ERRO_NET, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_historico_conta);
        ((ActionBar) findViewById(R.id.actionbar)).setDisplayHomeAsUpEnabled(Boolean.TRUE);

        this.tableLayout = (TableLayout) this.findViewById(R.id.activity_conta_list_view_item_conta);
        this.textViewSemPedido = (TextView) this.findViewById(R.id.activity_conta_text_view_sem_conta);
        this.textViewValorTotal = (TextView) this.findViewById(R.id.activity_conta_text_view_valor);
        this.textViewFecharConta = (TextView) this.findViewById(R.id.activity_conta_textview_fechar_conta);

        this.findViewById(R.id.activity_conta_button_pesquisa_mesa).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nMesa = ((EditText) findViewById(R.id.activity_conta_edittext_pesquisa_mesa)).getEditableText().toString();
                if (!"".equals(nMesa)) {
                    progressDialog = new ProgressDialog(HistoricoContaActivity.this);
                    progressDialog.setMessage("Buscando histórico...");
                    progressDialog.show();
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    new ContaRequest(responseListenerGetContaByNumero).getByNumero(Long.parseLong(nMesa));
                }
            }
        });

        this.buttonFecharConta = (Button) this.findViewById(R.id.activity_conta_button_fechar_conta);
        this.buttonFecharConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (conta != null) {
                    progressDialog = new ProgressDialog(HistoricoContaActivity.this);
                    progressDialog.setMessage("Processando solicitação...");
                    progressDialog.show();
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    AcaoConta acaoConta = new AcaoConta();
                    acaoConta.setUsuario(getUsuario());
                    acaoConta.setConta(conta);

                    new AcaoContaRequest(new ResponseListener() {
                        @Override
                        public void onResult(ServerResponse serverResponse) {
                            if (serverResponse != null) {
                                if (serverResponse.isOK()) {
                                    textViewFecharConta.setVisibility(TextView.VISIBLE);
                                    buttonFecharConta.setVisibility(Button.GONE);
                                } else
                                    progressDialog.setMessage(serverResponse.getMsgErro());
                            } else
                                progressDialog.setMessage(Constantes.MSG_ERRO_NET);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    conta = null;
                                }
                            }, 1000);
                        }
                    }).fecharConta(acaoConta);
                } else {
                    Toast.makeText(getApplicationContext(), "Procure por uma conta em aberta...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void bindTableItemConta() {

        PedidoSubItem pedidoSubItem;
        SubItem subItem;
        View itemContaView;
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        this.tableLayout.removeAllViews();

        for (int position = 0; position < this.conta.getPedidos().get(0).getPedidoSubItens().size(); position++) {
            pedidoSubItem = this.conta.getPedidos().get(0).getPedidoSubItens().get(position);
            subItem = getDataManager().getSubItemDAO().getWithItem(pedidoSubItem.getSubItemId());
            itemContaView = TableRow.inflate(this, R.layout.activity_historico_conta_item, null);

            if ((position % 2) == 0)
                itemContaView.findViewById(R.id.activity_conta_item_adapeter_linear_layout).setBackgroundResource(R.color.branco);
            else
                itemContaView.findViewById(R.id.activity_conta_item_adapeter_linear_layout).setBackgroundResource(R.color.bg_system);

            ((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_nome)).setText(String.format("%s - %s (%s)", subItem.getItem().getNome(), subItem.getNome(), subItem.getCodigo()));
            ((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_valor_unitario)).setText(format.format(new Double(subItem.getValor())));
            ((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_qtd)).setText(String.valueOf(pedidoSubItem.getQuantidade()));
            ((TextView) itemContaView.findViewById(R.id.activity_conta_item_adapeter_text_view_valor_total)).setText(format.format((new Double(pedidoSubItem.getValorUnitario()) * new Double(pedidoSubItem.getQuantidade()))));

            this.tableLayout.addView(itemContaView);

        }
    }
}