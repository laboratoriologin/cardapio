package com.login.beachstop.android.managers.sqlite.dao;

import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;

import java.util.List;

public class PedidoDAO extends DroidDao<Pedido, Long> {

    protected DataManager dataManager;

    public PedidoDAO(TableDefinition<Pedido> tableDefinition, DataManager dataManager) {
        super(Pedido.class, tableDefinition, dataManager.getDatabase());

        this.dataManager = dataManager;
    }

    public Pedido get(Boolean finalizado) {

        String strFinalizado = (finalizado) ? "1" : "0";
        Pedido pedido = this.getByClause("FINALIZADO =" + strFinalizado, null);

        if (pedido != null) {

            List<PedidoSubItem> pedidosSubItens = this.dataManager.getPedidoSubItemDAO().getAllbyClause("PEDIDO_ID = ?", new String[]{pedido.getId().toString()}, null, null, null);
            pedido.setPedidoSubItens(pedidosSubItens);
            for (PedidoSubItem pedidoSubItem : pedido.getPedidoSubItens()) {
                if (pedidoSubItem.getKitId() == 0) {
                    pedidoSubItem.setSubItem(this.dataManager.getSubItemDAO().get(pedidoSubItem.getSubItemId()));
                } else {
                    pedidoSubItem.setKit(this.dataManager.getKitDAO().get(pedidoSubItem.getKitId()));
                }
            }
        }
        return pedido;
    }
}
