package jmp.billingapplication.service;

import jmp.billingapplication.entity.Orders;

public interface OrderService {

	public int editOrder(String itemName, Orders orders);

	public void deleteOrder(Orders orders, String itemName);

	public void setFlagValue(int flag);

	public int saveOrder(Orders order, String itemName);

	public int returnCurrentOrderId();
}
