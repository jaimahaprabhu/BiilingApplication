package jmp.billingapplication.service.impl;

import jmp.billingapplication.entity.Item;
import jmp.billingapplication.entity.Orders;
import jmp.billingapplication.repository.ItemRepository;
import jmp.billingapplication.repository.OrderRepository;
import jmp.billingapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ItemRepository itemRepository;

	private boolean orderFlag;
	private int orderId;

	private List<Orders> orderList;
	private List<Item> itemList;

	public void setFlagValue(int flag) {
		orderFlag = flag != 0;
	}

	public int saveOrder(Orders order, String itemName) {

		if (!orderFlag)
			orderId = 0;
		orderList = orderRepository.findRedundantOrder(orderId, order.getItemId());
		int result;
		if (!orderList.isEmpty()) {
			result = -1;
		} else {
			itemList = itemRepository.findByItemId(order.getItemId());
			int quantity = itemList.get(0).getQuantity();
			if (quantity >= order.getQuantity()) {
				float amount = itemList.get(0).getPrice() * order.getQuantity();
				float taxAmount = itemList.get(0).getTax() / 100;
				float finalAmount = (amount + (taxAmount * amount));
				order.setAmount(finalAmount);
				if (orderFlag) {					
					order.setOrderId(orderId);
					orderRepository.save(order);
				} else {
					orderRepository.save(order);
					orderRepository.updateOrderId(0);
					orderId = getOrderId(order);
					orderFlag = true;
				}
				result = 0;
			} else {
				result = quantity;
			}
		}
		return result;
	}

	public int returnCurrentOrderId() {
		return orderId;
	}

	public int getOrderId(Orders order) {

		orderList = orderRepository.getOrderId(order.getItemId());
		int orderId = 0;
		if (!orderList.isEmpty()) {
			orderId = orderList.get(0).getOrderId();
		}
		return orderId;
	}

	public int editOrder(String itemName, Orders orders) {
		int itemId=getItemId(orders,itemName);
		if (orderRepository.findRedundantOrder(orders.getOrderId(), itemId).isEmpty()) {
			return 0;
		} else {
			itemList = itemRepository.findByItemId(orders.getItemId());
			int quantity = itemList.get(0).getQuantity();
			if (quantity >= orders.getQuantity()) {
				orderRepository.updateQuantity(orders.getOrderId(), itemId, orders.getQuantity());
				return 1;
			}
			else {
				return quantity;
			}
		}
	}

	public void deleteOrder(Orders orders, String itemName) {
		int itemId=getItemId(orders,itemName);
		if (!orderRepository.findRedundantOrder(orders.getOrderId(), itemId).isEmpty()) {
			orderRepository.deleteOrder(orders.getOrderId(), itemId);
		}
	}

	private int  getItemId(Orders orders, String itemName){
		itemList = itemRepository.findItemId(itemName);
		int size = itemList.size();
		int count = 0;
		int itemId = 0;
		while (count < size) {
			if (orderRepository.findRedundantOrder(orders.getOrderId(), itemList.get(count).getItemId()).isEmpty()) {
				count++;
			} else {
				itemId = itemList.get(count).getItemId();
				count = size;
			}
		}
		return itemId;
	}
}