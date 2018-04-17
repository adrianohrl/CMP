/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.model.production;

import br.com.ceciliaprado.cmp.model.order.ProductionOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class DataBeanList {

    List<DataBean> getDataBeanList(ProductionOrder order) {
        List<DataBean> list = new ArrayList<>();
        Model model = order.getModel();
        for (Part part : model.getParts()) {
            for (ChartSize size: model.getChart()) {
                list.add(new DataBean(part.getName(), size.getName(), part.getProgram(), part.getObservation()));
            }
        }
        return list;
    }
    
}
