package view;

/** Interface Lambda Expression used to efficiently validate overlapping appointments.
 * This is expression is used whenever adding or modifying an appointment */
public interface checkExistInterface {

    /** Lambda expression used to prevent overlapping appointments */
    boolean checkApp(String date, String startT, String endT);
}
