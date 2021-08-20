package view;

/** Interface Lambda Express used to efficiently obtain local time and correctly format for SQL
 * this express is used whenever local time conversion is needed*/
public interface TimeInterface {
    /** Lambda expression used to format and adjust according to local time Zone */
    String timeConvert(String datetime);
}
