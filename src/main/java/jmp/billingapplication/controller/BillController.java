package jmp.billingapplication.controller;

import jmp.billingapplication.entity.BillOrder;
import jmp.billingapplication.entity.Orders;
import jmp.billingapplication.service.BillService;
import jmp.billingapplication.service.ItemService;
import jmp.billingapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BillController {

	@Autowired
	ItemService itemService;
	@Autowired
	BillService billService;
	@Autowired
	OrderService orderService;

	private int orderId;
	private List<BillOrder> billOrderList;
	private ModelAndView modelAndView = new ModelAndView();

	@PostMapping("/generateBill")
	public ModelAndView generateBill() {

		orderId = orderService.returnCurrentOrderId();
		billOrderList = billService.generateBill(orderId);
		modelAndView.addObject("orders", new Orders());
		modelAndView.addObject("bill", billOrderList);
		modelAndView.setViewName("finalbill");
		return modelAndView;
	}

	@PostMapping("/editorder")
	public ModelAndView editOrder(@RequestParam("itemName") String itemName, @ModelAttribute Orders orders) {

		int status = orderService.editOrder(itemName, orders);
		if (status == 0) {
			modelAndView.addObject("stock", status);
			modelAndView.setViewName("stockerror");
		} else if (status == 1) {
			billOrderList = billService.generateBill(orders.getOrderId());
			modelAndView.addObject("bill", billOrderList);
			modelAndView.setViewName("finalbill");
		} else {
			modelAndView.setViewName("updateError");
		}
		return modelAndView;
	}

	@PostMapping("/deleteorder")
	public ModelAndView deleteOrder(@ModelAttribute Orders orders, @RequestParam("itemName") String itemName) {

		orderService.deleteOrder(orders, itemName);
		billOrderList = billService.generateBill(orderId);
		if (billOrderList.size() > 0) {
			modelAndView.addObject("bill", billOrderList);
			modelAndView.setViewName("finalbill");
		} else
			modelAndView.setViewName("error");
		return modelAndView;
	}

	@GetMapping("/generated")
	public ModelAndView generateFinalBill() {

		boolean flag = billService.saveBill(orderId);
		if (flag) {
			billOrderList = billService.printBill(orderId);
			modelAndView.addObject("orderId", billOrderList.get(0).getOrderId());
			modelAndView.addObject("Date", billOrderList.get(0).getPurchaseDate());
			modelAndView.setViewName("generatebill");
		} else {
			modelAndView.setViewName("billError");
		}
		return modelAndView;
	}

	@PostMapping("/print")
	public ModelAndView printBill() {

		orderId = orderService.returnCurrentOrderId();
		billOrderList = billService.printBill(orderId);
		float total = billService.getTotal();
		modelAndView.addObject("billList", billOrderList);
		modelAndView.addObject("count", total);
		modelAndView.setViewName("printbill");
		return modelAndView;
	}
}