package jmp.billingapplication.service;

import java.util.List;

import jmp.billingapplication.entity.BillOrder;

public interface BillService {

	public List<BillOrder> generateBill(int orderId);

	public List<BillOrder> printBill(int orderId);

	public boolean saveBill(int orderId);

	public float getTotal();

}
