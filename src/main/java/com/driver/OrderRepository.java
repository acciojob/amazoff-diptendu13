package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderMap;
    HashMap<String, DeliveryPartner> partnerMap;
    HashMap<String, String> orderPartnerPairMap;
    HashMap<String, List<String>> orderPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<>();
        this.partnerMap = new HashMap<>();
        this.orderPartnerPairMap = new HashMap<>();
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
            orderPartnerPairMap.put(orderId, partnerId);
            List<String> orders = new ArrayList<>();
            if (orderPartnerMap.containsKey(partnerId)){
                orders = orderPartnerMap.get(partnerId);
            }
            orders.add(orderId);
            orderPartnerMap.put(partnerId, orders);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
        }
    }
    public Order getOrderById(String orderId){
        return orderMap.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return partnerMap.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        DeliveryPartner partner = partnerMap.get(partnerId);
        return partner.getNumberOfOrders();
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderPartnerMap.get(partnerId);
    }
    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for (String order : orderMap.keySet()){
            orders.add(order);
        }
        return orders;
    }
    public Integer getCountOfUnassignedOrders(){
        return orderMap.size() - orderPartnerPairMap.size();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int minutes, String partnerId){
        int count = 0;
        List<String> orders = orderPartnerMap.get(partnerId);
        for (String orderId : orders){
            int deliveryTime = orderMap.get(orderId).getDeliveryTime();
            if (deliveryTime > minutes){
                count++;
            }
        }
        return count;
    }
    public int getLastDeliveryTimeByPartnerId(String partnerId){
        int maxTime = 0;
        List<String> orders = orderPartnerMap.get(partnerId);
        for (String orderId : orders){
            int currentTime = orderMap.get(orderId).getDeliveryTime();
            maxTime = Math.max(maxTime, currentTime);
        }
        return maxTime;
    }
    public void deletePartnerById(String partnerId){
        partnerMap.remove(partnerId);

        List<String> orders = orderPartnerMap.get(partnerId);
        orderPartnerMap.remove(partnerId);

        for (String order : orders){
            orderPartnerPairMap.remove(order);
        }

    }
    public void deleteOrderById(String orderId){
        orderMap.remove(orderId);

        String partnerId = orderPartnerPairMap.get(orderId);
        orderPartnerPairMap.remove(orderId);

        orderPartnerMap.get(partnerId).remove(orderId);

        partnerMap.get(partnerId).setNumberOfOrders(orderPartnerMap.get(partnerId).size());
    }
}
