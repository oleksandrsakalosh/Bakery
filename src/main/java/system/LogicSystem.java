package system;

import java.io.Serializable;

/**
 * Class for system that holds main settings
 */
public class LogicSystem implements Serializable {
    private double bakerSalary;
    private double sellerSalary;
    private double taxes;
    private int markup;

    /**
     * @param bakerSalary daily salary of bakers
     * @param sellerSalary daily salary of sellers
     * @param taxes daily taxes
     * @param markup mark-up for products
     */
    public LogicSystem(double bakerSalary, double sellerSalary, double taxes, int markup){
        this.bakerSalary=bakerSalary;
        this.sellerSalary=sellerSalary;
        this.taxes=taxes;
        this.markup=markup;
    }

    public void setBakerSalary(double bakerSalary) {
        this.bakerSalary = bakerSalary;
    }

    public void setSellerSalary(double sellerSalary) {
        this.sellerSalary = sellerSalary;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public void setMarkup(int markup) {
        this.markup = markup;
    }

    public double getBakerSalary() {
        return bakerSalary;
    }

    public double getSellerSalary() {
        return sellerSalary;
    }

    public double getTaxes() {
        return taxes;
    }

    public int getMarkup() {
        return markup;
    }
}