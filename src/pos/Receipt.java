package pos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


import main.Main;
import utils.Database;
import utils.Utility;

public class Receipt {
	
	private final String BR = "\n";
	private final int WIDTH = 50;
    private String horizontalLine = "";
	
	private String businessName1 = "PRIME BUSINESS SUPERMARKET";
	private String businessName2 = Main.SYSTEM_NAME;
	private String tinNo = "123-456-789-000";
	private String businessAddress = "Cavite State University - Imus Campus";
	private String footer = "Thank you for visiting our store, you can contact us via email "
			+ "castilloglenn@ymail.com and via our social media facebook.com/ccastilloglenn "
			+ "or our landline 0956-899-0812. Please come again to our store.";
	private String legalNotice = "This receipt is valid up to five (5) years since the date "
			+ "it has been printed. Items purchased can be returned and exchange for goods "
			+ "but cannot be refunded.";
	
	private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa");
	private String dateTime;
	private long transactionNo;
	private Object[] cashier;
	private String cashierName;
	private String customerName = "WALK-IN";

	private HashMap<Integer, Object[]> purchases = new HashMap<>();
	
	private double subTotal, tax, total, totalItems;
	private Database db;
	private Utility ut;

	Receipt(Database db, Utility ut, long id) {
		this.db = db; this.ut = ut;
		cashier = db.fetchEmployeeByID(id);
		cashierName = cashier[1] + " " + cashier[3].toString().substring(0, 1) + ".";
		setHorizontalLine();
		resetTransaction();
	}
	
	private void resetTransaction() {
		dateTime = null;
		transactionNo = 0;
		customerName = "WALK-IN";
		subTotal = 0;
		tax = 0;
		total = 0;
		totalItems = 0;
	}
	
	private void setHorizontalLine() {
        for (int space = 0; space < WIDTH; space++) {
            horizontalLine += "=";
        }
    }
	
	private void setDateTime() {
		dateTime = sdf.format(Calendar.getInstance().getTime());
		transactionNo = ut.generateTransactionID(1);
	}
	
	public void addPurchase(long product_id, double quantity) {
		int key;
		
		if (purchases.isEmpty()) key = 1;
		else for (key = 1; purchases.containsKey(key); key++);
		
		Object[] product = db.fetchProductByID(product_id);
		Object[] withQuantity = new Object[8];
		withQuantity[0] = quantity;
		
		for (int index = 1; index < product.length; index++) {
			withQuantity[index + 1] = product[index];
		}
		
		Object[] promo = db.fetchPromoByID(product_id);
		if (promo != null) {
			withQuantity[5] = withQuantity[4] + " " + withQuantity[5] + " " + promo[1] + " " + promo[0];
			withQuantity[7] = ((double) (promo[2]) < 1) 
				? (double) (withQuantity[7]) * (1 - (double) (promo[2])) 
				: (double) (withQuantity[7]) - (double) (promo[2]);
		}
		
		purchases.put(key, withQuantity);
		setDateTime();
	}
	
	public Object removePurchase(int index) {
		return purchases.remove(index);
	}
	
	private String leftAlign(String message) {
		int length = message.length();
        if (length == WIDTH - 4) return "  " + message + "  ";
        
        if (length > WIDTH - 4) {
            return "  " + message.substring(0, WIDTH - 4) + "\n" + 
                center(message.substring(WIDTH - 4));
        }
        
        return "  " + message;
	}
	
	private String center(String message) {
        int length = message.length();
        if (length == WIDTH - 4) return "  " + message + "  ";
        
        if (length > WIDTH - 4) {
            return "  " + message.substring(0, WIDTH - 4) + "\n" + 
                center(message.substring(WIDTH - 4));
        }
        
        int padding = (WIDTH - length) / 2;
        for (int space = 0; space < padding; space++) {
            message = " " + message;
        }
        
        return message;
    }

    private String center(HashMap<Integer, Object[]> purchases) {
        if (purchases.isEmpty()) return center("Please provide atleast 1 purchase to continue.") + "\n";
        
        StringBuilder purchaseList = new StringBuilder();
        for (int key : purchases.keySet()) {
            Object[] purchase = purchases.get(key);
            double quantity = (double) purchase[0];
            String description = (String) purchase[5];
            double price = (double) purchase[7];

            String firstLine = String.format("  %03d: %-" + description.length() + "s", 
                    key, quantity + " " + description);
            String individual = String.format("Php %,.2f", price);
            String totalPrice = String.format("Php %,.2f", quantity * price);

            String secondLine = String.format("    @ %s ",individual);
            int centerSpace = WIDTH - (11 + individual.length() + totalPrice.length());
            String totalLine = "";
            for (int dot = 0; dot < centerSpace; dot++) {
                totalLine += ".";
            }
            totalLine += " ";

            purchaseList.append(
                (firstLine.length() > WIDTH - 4)
                ? firstLine.substring(0, WIDTH - 5) + "..\n"
                : firstLine + "\n"
            );
            purchaseList.append(secondLine + totalLine + totalPrice + "\n");
            
            total += quantity * price;
            totalItems += quantity;
            tax = total * 0.12;
            subTotal = total - tax;
        }
        
        String subTotalFormat = String.format("Php %,.2f", subTotal);
        String taxFormat = String.format("Php %,.2f", tax);
        String totalFormat = String.format("Php %,.2f", total);

        purchaseList.append(
    		String.format("\n  TOTAL ITEMS %," + (WIDTH - 16) + ".2f\n", 
    			totalItems));
        purchaseList.append(
        	String.format("\n  SUB-TOTAL %" + (WIDTH - 14) + "s\n", 
        		subTotalFormat));
        purchaseList.append(
        	String.format("  TAX %" + (WIDTH - 8) + "s\n",
        		taxFormat));
        purchaseList.append(
    		String.format("  TOTAL %" + (WIDTH - 10) + "s\n", 
    			totalFormat));

        return purchaseList.toString();
    }
    
    public String get() {
    	StringBuilder receipt = new StringBuilder();
    	resetTransaction();
    	setDateTime();
    	
    	receipt.append(center(businessName1) + BR);
    	receipt.append(center(businessName2) + BR);
    	receipt.append(center(businessAddress) + BR);
    	receipt.append(center(tinNo) + BR);
    	receipt.append(horizontalLine + BR);
    	receipt.append(leftAlign("Date: " + dateTime) + BR);
    	receipt.append(leftAlign("Transaction No: " + transactionNo) + BR);
    	receipt.append(leftAlign("Cashier: " + cashierName) + BR);
    	receipt.append(leftAlign("Customer: " + customerName) + BR);
    	receipt.append(horizontalLine + BR + BR);
    	receipt.append(center(purchases) + BR);
    	receipt.append(horizontalLine + BR);
    	receipt.append(center(footer) + BR + BR);
    	receipt.append(center(legalNotice) + BR);
    	
    	return receipt.toString();
    }
    
    public double getTotal() {return total;}
    public double getSubTotal() {return subTotal;}
    public double getTax() {return tax;}
}
