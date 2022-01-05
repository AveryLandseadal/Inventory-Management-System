package model;

/**@author Avery Landseadal*/
public class Outsourced extends Part{

    private String companyName;


    /**
     *
     * @param id id for the part in the Outsourced part
     * @param name name for the part in the Outsourced part
     * @param price of the in the Outsourced part
     * @param stock amount of inventory
     * @param min min amount of inventory
     * @param max max amount of inventory
     * @param companyName Company Name for the Outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return returns company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     * @param companyName sets company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}