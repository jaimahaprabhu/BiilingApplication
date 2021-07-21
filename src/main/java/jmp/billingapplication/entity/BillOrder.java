package jmp.billingapplication.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BillOrder {

	private int orderId;
	private String purchaseDate;
	private int itemId;
	private int quantity;
	private float amount;
	private String itemName;
	private float price;
	private float tax;

}