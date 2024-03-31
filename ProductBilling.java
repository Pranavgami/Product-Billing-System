import java.io.*;
import java.util.Scanner;

class PBS1 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("=====WELCOME=====");
        System.out.println("Enter Customer Name : ");
        String name = sc.nextLine();
        ProductBilling bill = new ProductBilling();
        bill.name = name;
        int choice, id, quantity;
        String p_name;
        double price;
        boolean b = true;
        while (b) {
            System.out.println(
                    "Enter \n1.Add Product\n2.Remove Product\n3.Search Product\n4.View Bill\n5.Print Bill\n6.Exit");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID : ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Product Name : ");
                    p_name = sc.nextLine();
                    System.out.println("Enter Product Price : ");
                    price = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Enter Product Quantity : ");
                    quantity = sc.nextInt();
                    sc.nextLine();
                    bill.addProduct(id, p_name, quantity, price);
                    break;

                case 2:
                    System.out.println("Enter Product ID to Remove It : ");
                    id = sc.nextInt();
                    sc.nextLine();
                    bill.removeProduct(id);
                    break;

                case 3:
                    System.out.println("Enter Product Id : ");
                    id = sc.nextInt();
                    sc.nextLine();
                    bill.searchProduct(id);
                    break;

                case 4:
                    System.out.println("Bill : ");
                    bill.displayBill();
                    break;

                case 5:
                    try {
                        bill.PrintBill();
                    } catch (IOException e) {
                        System.out.println("Printing Error!!!!!");
                    }

                    System.out.println("=======Thank You!!!=======");
                    System.out.println("=====PLEASE COME BACK AGAIN!!!!=====");
                    System.out.println();
                    System.exit(0);
                    break;

                case 6:
                    System.out.println("=======Thank You!!!=======");
                    System.out.println("=====PLEASE COME BACK AGAIN!!!!=====");
                    System.out.println();
                    System.exit(0);

                default:
                    System.out.println("Enter Valid Choice!!!");
                    break;
            }
        }

    }
}

class ProductBilling {
    String name;

    class Product {
        Product link;
        int p_id;
        String p_name;
        int p_qnt;
        double p_price;

        public Product(int p_id, String p_name, int p_qnt, double p_price) {
            this.link = null;
            this.p_id = p_id;
            this.p_name = p_name;
            this.p_qnt = p_qnt;
            this.p_price = p_price;
        }

        @Override
        public String toString() {
            return "Product [p_id=" + p_id + ", p_name=" + p_name + ", p_qnt=" + p_qnt + ", p_price=" + p_price + "]";
        }

        public int getP_qnt() {
            return p_qnt;
        }

        public void setP_qnt(int p_qnt) {
            this.p_qnt = p_qnt;
        }

    }

    Product first = null;

    void addProduct(int p_id, String p_name, int p_qnt, double p_price) {
        Product p = repeatProduct(p_id, p_name);
        if (p != null) {
            int q = p_qnt + p.getP_qnt();
            p.setP_qnt(q);
            return;
        }
        if (idExist(p_id)) {
            System.out.println("ID Alredy Exist");
            return;
        }

        Product product = new Product(p_id, p_name, p_qnt, p_price);
        if (first == null) {
            first = product;
        } else {
            Product temp = first;
            while (temp.link != null) {
                temp = temp.link;
            }
            temp.link = product;
        }
    }

    boolean idExist(int p_id) {
        Product temp = first;
        while (temp != null) {
            if (temp.p_id == p_id) {
                return true;
            }
            temp = temp.link;
        }
        return false;
    }

    void removeProduct(int p_id) {
        if (first == null) {
            System.out.println("No Product Available!!!");
        } else if (first.p_id == p_id) {
            first = first.link;
        } else {
            Product temp = first;
            while (temp.link != null && temp.link.p_id != p_id) {
                temp = temp.link;
            }
            if (temp.link != null) {
                temp.link = temp.link.link;
            } else {
                System.out.println("No Product Found!!!");
            }
        }
    }

    void searchProduct(int p_id) {
        if (first == null) {
            System.out.println("No Product Available!!!");
        } else if (first.p_id == p_id) {
            System.out.println(first.toString());
        } else {
            Product temp = first;
            while (temp.link != null && temp.link.p_id != p_id) {
                temp = temp.link;
            }
            if (temp.link != null) {
                System.out.println(temp.toString());
            } else {
                System.out.println("No Product Found!!!");
            }
        }
    }

    void displayBill() {
        Product temp = first;
        if (temp == null) {
            System.out.println("Empty!!!");
        } else {
            while (temp != null) {
                System.out.println(temp.toString());
                temp = temp.link;
            }
        }

    }

    private Product repeatProduct(int p_id, String p_name) {
        Product temp = first;
        while (temp != null) {
            if (temp.p_id == p_id && temp.p_name.equalsIgnoreCase(p_name)) {
                return temp;
            }
            temp = temp.link;
        }
        return null;
    }

    void PrintBill() throws IOException {
        File f = new File("Pranav" + name + ".txt");
        f.createNewFile();
        if (f != null) {
            System.out.println();
            System.out.println("File Created");
            System.out.println();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("NAME OF CUSTOMER:" + name);
        bw.newLine();
        Product temp = first;
        double initial_total = 0;
        double totalBill = 0;
        bw.write("ID    NAME OF PRODUCT     PRICE X QUANTITY    TOTAL");
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------");
        bw.newLine();
        while (temp != null) {
            initial_total = temp.p_price * temp.p_qnt;
            totalBill += initial_total;
            bw.write(temp.p_id + "     " + temp.p_name + "     " + temp.p_price + " X " + temp.p_qnt + " = "
                    + initial_total);
            bw.newLine();
            temp = temp.link;
            bw.flush();
        }
        bw.write("----------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.write("Total : " + totalBill);
        bw.newLine();
        bw.close();
    }
}