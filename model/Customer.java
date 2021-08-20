package model;


/** Class for customer object */
public class  Customer {

    private int customer_ID;
    private String customer_Name;
    private String phone;
    private String address;
    private String postalCode;
    private String division;
    private String country;


    /**
     * constructor
     * @param customer_ID
     * @param customer_Name
     * @param phone
     * @param address
     * @param postalCode
     * @param division
     * @param country
     */
    public Customer(int customer_ID, String customer_Name, String phone, String address, String postalCode, String division, String country) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.phone = phone;
        this.address= address;
        this.postalCode= postalCode;
        this.division=division;
        this.country= country;
    }

    /**
     * Constructor
     * @param customer_ID
     * @param customer_Name
     * @param address
     * @param postalCode
     * @param phone
     */
    public Customer(int customer_ID, String customer_Name,  String address, String postalCode, String phone) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.phone = phone;
        this.address= address;
        this.postalCode= postalCode;

    }

    /**
     * getter
     * @return customer_ID
     */
    public int getCustomer_ID() {
        return customer_ID;
    }

    /**
     * setter customer_ID
     * @param customer_ID
     */
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    /**
     * getter
     * @return customer_Name
     */
    public String getCustomer_Name() {
        return customer_Name;
    }

    /**
     * setter customer_Name
     * @param customer_Name
     */
    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    /**
     * getter phone
     * @return Phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * getter address
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter
     * @return getPostalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * setter
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * getter
     * @return getDivision
     */
    public String getDivision() {
        return division;
    }

    /**
     * setter
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * getter
     * @return getCountry
     */
    public String getCountry() {
        return country;
    }

    /**
     * setter
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }



}
