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
        Order order = orderRepositorys.getOrderById(orderId);
        return order;
    }
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner partner = orderRepositorys.getPartnerById(partnerId);
        return partner;
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        Integer count = orderRepositorys.getOrderCountByPartnerId(partnerId);
        return count;
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders = orderRepositorys.getOrdersByPartnerId(partnerId);
        return orders;
    }
    public List<String> getAllOrders(){
        List<String> orders = orderRepositorys.getAllOrders();
        return orders;
    }
    public Integer getCountOfUnassignedOrders(){
        Integer count = orderRepositorys.getCountOfUnassignedOrders();
        return count;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        Integer count = orderRepositorys.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
        return count;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time = orderRepositorys.getLastDeliveryTimeByPartnerId(partnerId);
        return time;
    }
    public void deletePartnerById(String partnerId){
        orderRepositorys.deletePartnerById(partnerId);
    }
    public void deleteOrderById(String orderId){
        orderRepositorys.deleteOrderById(orderId);
    }
}
