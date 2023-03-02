package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepositorys;
    public void addOrder(Order order){
        orderRepositorys.addOrder(order);
    }
    public void addPartner(String partnerId){
        orderRepositorys.addPartner(partnerId);
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        orderRepositorys.addOrderPartnerPair(orderId, partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepositorys.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepositorys.getPartnerById(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return orderRepositorys.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepositorys.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrders(){
        return orderRepositorys.getAllOrders();
    }
    public Integer getCountOfUnassignedOrders(){
        return orderRepositorys.getCountOfUnassignedOrders();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        String[] str = time.split(":");
        int minutes = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
        return orderRepositorys.getOrdersLeftAfterGivenTimeByPartnerId(minutes, partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int minutes = orderRepositorys.getLastDeliveryTimeByPartnerId(partnerId);
        String HH = String.valueOf(minutes / 60);
        String MM = String.valueOf(minutes % 60);
        if (HH.length() < 2){
            HH = '0' + HH;
        }
        if (MM.length() < 2){
            MM = '0' + MM;
        }
        return HH + ":" + MM;
    }
    public void deletePartnerById(String partnerId){
        orderRepositorys.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId){
        orderRepositorys.deleteOrderById(orderId);
    }
}
