package model;


/**
 * Class for appointment object
 * @author  An Nguyen
 *
 */
public class Appointment {
    private int appID;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private String appStart;
    private String appEnd;
    private int customerID;
    private int userID;
    private int contactID;
    private String appContact;
    private String appDate;
    private String month;

    /** constructor for appointment
     * @param appID
     * @param appTitle
     * @param appDescription
     * @param appLocation
     * @param appContact
     * @param appType
     * @param appDate
     * @param appStart
     * @param appEnd
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appID, String appTitle, String appDescription, String appLocation, String appContact, String appType, String appDate, String appStart, String appEnd, int customerID, int userID, int contactID) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.appContact=appContact;
        this.appDate= appDate;
    }

    /** overload constructor
     * @param appID
     * @param appTitle
     * @param appDescription
     * @param appLocation
     * @param appContact
     * @param appType
     * @param appStart
     * @param appEnd
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appID, String appTitle, String appDescription, String appLocation, String appContact, String appType, String appStart, String appEnd, int customerID, int userID, int contactID) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.appContact=appContact;
    }


    /** overload constructor
     * @param month
     */
    public Appointment(String month) {
        this.month=month;

    }



    public Appointment(int appID,String appStart, String appEnd) {
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.appID = appID;

    }

    public String getAppDate() {

        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public Appointment(int appID, String appDate,String appStart, String appEnd) {
        this.appID=appID;
        this.appDate= appDate;
        this.appStart = appStart;
        this.appEnd = appEnd;


    }

    public Appointment(String appDate,String appStart, String appEnd) {
        this.appDate= appDate;
        this.appStart = appStart;
        this.appEnd = appEnd;


    }

    public Appointment(int appID, String appTitle, String appDescription, String appType, String appStart, String appEnd, int customerID) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.customerID = customerID;
    }

    public int getAppID() {
        return appID;
    }

    public String getAppContact() {
        return appContact;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppLocation() {
        return appLocation;
    }

    public void setAppLocation(String appLocation) {
        this.appLocation = appLocation;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppStart() {
        return appStart;
    }

    public void setAppStart(String appStart) {
        this.appStart = appStart;
    }

    public String getAppEnd() {
        return appEnd;
    }

    public void setAppEnd(String appEnd) {
        this.appEnd = appEnd;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


}
