import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

//Paulina Amelia Vega
//2502026583 - LH01
public class GSLCBluesCafe {
    static ArrayList<TableNumber> Orders = new ArrayList<>();
    static double earnedMoney;
    public static void main(String[] args) {
        menu();
    }

    public static void addOrders(){
        ArrayList<ItemOrder> Item = new ArrayList<>();
        int tableNum;
        String itemName;
        String choice;
        double amount;
        double itemPrice;
        Scanner input = new Scanner(System.in);
        ItemOrder newItem;
        TableNumber newOrders;
            do {
                System.out.print("Enter Table Number : ");
                tableNum = input.nextInt();
            }while (tableNum < 1);
            do {
                System.out.println("Enter Item Name : ");
                do {
                    itemName = input.nextLine();
                }while(itemName.length()<5);
                System.out.println("Enter Amount : ");
                amount = input.nextDouble();
                System.out.println("Enter Item Price : ");
                itemPrice = input.nextDouble();
                newItem = new ItemOrder(itemName, itemPrice, amount);
                Item.add(newItem);
                System.out.println("Input More?[y|n]");
                System.out.print("> ");
                choice = input.next();
            }while (Objects.equals(choice, "y") && !(Objects.equals(choice, "n")) );

            newOrders = new TableNumber(tableNum, Item);
            Orders.add(newOrders);
    }

    public static void removeOrder(){
        int tableNumber;
        double totalPrice = 0;
        String choice;
        Scanner input = new Scanner(System.in);

        if(Orders.size() == 0){
            System.out.println("There is no orders to be removed!");
        }else{
            showTableNumber();
            do {
                System.out.print("Enter table number to be deleted : ");
                tableNumber = input.nextInt();
            }while (tableNumber < 1);
            for (int i = 0; i < Orders.size(); i++) {
                if(Orders.get(i).getTableNum() == tableNumber){
                    System.out.println("Do you want to remove this order?[y|n]");
                    System.out.print("> ");
                    choice = input.next();
                    if (Objects.equals(choice, "y") && !(Objects.equals(choice, "n"))){
                        for (int j = 0; j < Orders.get(i).getItemOrder().size(); j++) {
                            ItemOrder data = (ItemOrder) Orders.get(i).getItemOrder().get(j);
                            totalPrice += (data.getItemOrderPrice() * data.getItemOrderAmount());
                        }
                        double tax = totalPrice * (0.1);
                        double service = totalPrice * (0.075);
                        double finalPrice = totalPrice + tax + service;
                        earnedMoney += finalPrice;
                        Orders.remove(i);
                    }else{
                        break;
                    }
                }
            }
        }

    }
    public static void showTableNumber(){
        System.out.println("Table Number");
        System.out.println("============");
        for (int i = 0; i < Orders.size(); i++) {
            int tableNumber = Orders.get(i).tableNum;
            String dataTableNumber = String.format("> %d",tableNumber);
            System.out.println(dataTableNumber);
        }
        System.out.println("============");
    }
    public static void showAll(){
        Scanner input = new Scanner(System.in);
        String dividerUp = "_";
        String dividerDown = "-";
        if (Orders.size() > 0){
            showTableNumber();
            System.out.print("Enter Table Number : ");
            int tableNumber;
            tableNumber = input.nextInt();
            int titleSpace = 15;
            String itemTitle = String.format("| No. | Item Name%" + titleSpace + "s | Item Amount%" + (titleSpace-10) +"s | Item Price%"+ titleSpace + "s |" , "", "", "");

            double totalPrice = 0;
            for (int i = 0; i < Orders.size(); i++) {
                System.out.println(dividerUp.repeat(itemTitle.length()));
                System.out.println(itemTitle);
                System.out.println(dividerDown.repeat(itemTitle.length()));
                if (Orders.get(i).getTableNum() == tableNumber) {
                    for (int j = 0; j < Orders.get(i).getItemOrder().size(); j++) {
                        ItemOrder data = (ItemOrder) Orders.get(i).getItemOrder().get(j);
                        int no = j+1;
                        int spaceItem = (9 + titleSpace - data.getItemOrderName().length());
                        int spacePrice = (4 + titleSpace - 2);
                        int spaceAmount = (titleSpace);
                        double itemPrice = (data.getItemOrderPrice() * data.getItemOrderAmount());
                        String dataItem = String.format("| %d.  | %s%" + spaceItem + "s | x%.0f%" + spaceAmount + "s | %.2f%" + spacePrice + "s |", no, data.getItemOrderName(), "", data.getItemOrderAmount(), "", itemPrice , "" );
                        System.out.println(dataItem);
                        totalPrice += itemPrice;
                    }
                }
            }
            System.out.println(dividerDown.repeat(itemTitle.length()));
            System.out.println("Bill Details");
            System.out.println("Sub Total = "+ totalPrice);
            double tax = totalPrice * (0.1);
            System.out.println("Tax = "+ tax);
            double service = totalPrice * (0.075);
            System.out.println("Service Fee = "+ service);
            double finalPrice = totalPrice + tax + service;
            System.out.println("Total Price = " + finalPrice);
            System.out.println(dividerDown.repeat(itemTitle.length()));


        }
        else{
            System.out.println("There is no orders yet!");
        }


    }

    public static void menu(){
        String title = String.format("Blues Café -------- Earned Money : Rp %.2f", earnedMoney);
        System.out.println(title);
        System.out.println("1. Add Order");
        System.out.println("2. Show All Order");
        System.out.println("3. Remove Order");
        System.out.println("4. Exit");
        System.out.print("Choose : ");
        Scanner input = new Scanner(System.in);

        int choice = input.nextInt();
        switch (choice) {
            case 1:
                addOrders();
                backToMenu();
                break;
            case 2:
                showAll();
                backToMenu();
                break;
            case 3:
                removeOrder();
                backToMenu();
                break;
            default:
                System.exit(0);
                break;
        }
    }
    public static void backToMenu(){
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        menu();
    }

}


class TableNumber{
    int tableNum;
    ArrayList itemOrder;
    TableNumber(int tableNum, ArrayList itemOrder){
        this.tableNum = tableNum;
        this.itemOrder = itemOrder;
    }
    ArrayList getItemOrder(){
        return this.itemOrder;
    }
    int getTableNum(){
        return this.tableNum;
    }


}

class ItemOrder{
    String itemName;
    double price;
    double amount;
    ItemOrder(String itemName, double price, double amount){
        this.itemName = itemName;
        this.price = price;
        this.amount = amount;
    }
    String getItemOrderName(){
        return this.itemName;
    }
    double getItemOrderPrice(){
        return this.price;
    }

    double getItemOrderAmount(){
        return this.amount;
    }
}

