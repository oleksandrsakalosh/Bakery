package system;

import products.Products;

import java.io.*;

/**
 * Main class of bakery system
 */
public class BakerySystem {
    private final AccountSystem sysAcc = new AccountSystem();
    private final Products goods = new Products();
    private LogicSystem logic = new LogicSystem(0, 0, 0, 0);

    /**
     * @return system for accounts
     */
    public AccountSystem getAccSys(){ return sysAcc; }

    /**
     * @return system for products
     */
    public Products getGoods(){ return  goods;}

    /**
     * @return system for logic settings
     */
    public LogicSystem getSettings(){ return logic; }

    /**
     * Serializes settings
     * @param bakerSalary baker's salary setting
     * @param sellerSalary seller's salary setting
     * @param taxes daily taxes setting
     * @param markup mark-up on products setting
     */
    public void serializeLogic(double bakerSalary, double sellerSalary, double taxes, int markup) {
        logic.setBakerSalary(bakerSalary);
        logic.setSellerSalary(sellerSalary);
        logic.setTaxes(taxes);
        logic.setMarkup(markup);

        try{
            FileOutputStream file = new FileOutputStream("src/main/java/data/settings.ser");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(logic);

            out.close();
            file.close();
        } catch(
                IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes settings
     */
    public void deserializeLogic(){
        try {
            FileInputStream file = new FileInputStream("src/main/java/data/settings.ser");
            ObjectInputStream in = new ObjectInputStream(file);

            logic = (LogicSystem) in.readObject();

            in.close();
            file.close();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
