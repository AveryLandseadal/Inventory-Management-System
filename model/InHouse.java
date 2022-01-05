package model;

/** @author Avery Landseadal*/
public class InHouse extends Part
{

    private int machineId;


    /**
     *
     * @param id id for the part in the InHouse part
     * @param name name for the part in the InHouse part
     * @param price of the in the InHouse class
     * @param stock amount of inventory
     * @param min min amount of inventory
     * @param max max amount of inventory
     * @param machineId machine id for the in house part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *  This is the getter for the MachineID
     */

    public int getMachineId() {
        return machineId;
    }

    /**
     *  This is the setter for the MachineID
     */

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
