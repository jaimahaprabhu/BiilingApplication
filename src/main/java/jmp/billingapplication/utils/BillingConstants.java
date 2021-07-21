package jmp.billingapplication.utils;

public class BillingConstants {

    public static final String findBillByOrderId = "SELECT DISTINCT B FROM Bill B WHERE order_id=:orderId";
    public static final String findCategoryName = "SELECT i FROM Category i WHERE category_name= :categoryName";
    public static final String updateCategory = "UPDATE Category SET category_name= :categoryName WHERE category_id= :id";
    public  static final String findCategoryId = "SELECT i FROM Category i WHERE category_id= :id";
    public static final String findByCompositeKey = "SELECT i FROM Item i WHERE (item_name= :ITEMNAME AND price= :PRICE) AND (tax= :TAX AND category_name= :CATEGORYNAME)";
    public static final String findByItemId = "SELECT i FROM Item i where item_id= :itemId";
    public static final String findItemId = "SELECT  i FROM Item  i WHERE item_name= :itemName ";
    public static final String updateItemPrice = "UPDATE Item SET price= :price WHERE item_id= :itemId";
    public static final String updateItemTax = "UPDATE Item SET tax= :tax WHERE item_id= :itemId";
    public static final String updateItemQuantity = "UPDATE Item SET quantity= :quantity WHERE item_id= :itemId";
    public static final String deleteByItemId = "Delete FROM Item WHERE item_id= :itemId";
    public static final String findRedundantOrder = "SELECT b FROM Orders b WHERE item_id= :itemId AND order_id= :orderId";
    public static final String updateOrderId = "UPDATE Orders SET order_id = LPAD(convert(rand()*99999.9,signed),6,'1') WHERE order_id=:orderId";
    public static final String getOrderId = "SELECT b FROM Orders b WHERE item_id= :itemId";
    public static final String updateQuantity = "UPDATE Orders SET quantity= :quantity WHERE item_id=:itemId AND order_id= :orderId";
    public static final String findByOrderId = "SELECT DISTINCT b FROM Orders b WHERE order_id=:orderId";
    public static final String deleteOrder = "DELETE  FROM Orders WHERE item_id= :itemId AND order_id= :orderId";

}
