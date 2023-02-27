package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderMap;
    HashMap<String, DeliveryPartner> partnerMap;
    HashMap<String, List<String>> orderPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<>();
        this.partnerMap = new HashMap<>();
        this.orderPartnerMap = new HashMap<>();
    }

    public void addOrder(Order order){
        orderMap.put(order.getId(), order);
    }
    public void addPartner(String partnerId){
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, partner);
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        if (orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            if (orderPartnerMap.containsKey(partnerId)){
                List<String> orders = orderPartnerMap.get(partnerId);
                orders.add(orderId);
                orderPartnerMap.put(partnerId, orders);
            }
            else{
                List<String> orders = new ArrayList<>();
                orders.add(orderId);
                orderPartnerMap.put(partnerId, orders);
            }
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
            partnerMap.put(partnerId, partner);
        }
    }
    public Order getOrderById(String orderId){
        Order order = orderMap.get(orderId);
        return order;
    }
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner partner = partnerMap.get(partnerId);
        return partner;
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        DeliveryPartner partner = partnerMap.get(partnerId);
        return partner.getNumberOfOrders();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> orders = orderPartnerMap.get(partnerId);
        return orders;
    }
    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for (String order : orderMap.keySet()){
            orders.add(order);
        }
        return orders;
    }
    public Integer getCountOfUnassignedOrders(){
        int countOfAssignedOrders = 0;
        for (String partnerId : orderPartnerMap.keySet()){
            List<String> orders = orderPartnerMap.get(partnerId);
            countOfAssignedOrders += orders.size();
        }
        return orderMap.size() - countOfAssignedOrders;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        int countOfOrdersDelivered = 0;
        int timeUsedForDelivery = 0;
        String[] str = time.split(":");
        int minutes = Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]);
        List<String> orders = orderPartnerMap.get(partnerId);
        for (String orderId : orders){
            Order order = orderMap.get(orderId);
            timeUsedForDelivery += order.getDeliveryTime();
            if (timeUsedForDelivery <= minutes){
                countOfOrdersDelivered += 1;
            }
            else{
                break;
            }
        }
        return orders.size() - countOfOrdersDelivered;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        List<String> orders = orderPartnerMap.get(partnerId);
        String lastOrderId = orders.get(orders.size()-1);
        Order lastOrder = orderMap.get(lastOrderId);
        int totalTime = lastOrder.getDeliveryTime();
        String hours = String.valueOf(totalTime / 60);
        String minutes = String.valueOf(totalTime % 60);
        return hours + ":" + minutes;
    }
    public void deletePartnerById(String partnerId){
        partnerMap.remove(partnerId);
        orderPartnerMap.remove(partnerId);
    }
    public void deleteOrderById(String orderId){
        boolean flag = false;
        for (String partner : orderPartnerMap.keySet()){
            List<String> orders = orderPartnerMap.get(partner);
            int sizeOfOrders = orders.size();
            for (int i=0; i<sizeOfOrders; i++){
                if (orders.get(i).equals(orderId)){
                    if (sizeOfOrders > 1){
                        orders.remove(i);
                        orderPartnerMap.put(partner, orders);
                    }
                    else{
                        orderPartnerMap.remove(partner);
                    }
                    flag  = true;
                    break;
                }
            }
            if (flag){
                break;
            }
        }
        orderMap.remove(orderId);
    }

}
