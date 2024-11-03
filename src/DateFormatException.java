/**
 * DateFormatException Class
 * The DateFormatException is used whenever a String of date is used and is in the incorrect format.
 * The proper format for dates is: 00-00-0000 (month-day-year)
 *
 * @author zaynrekhi
 *
 * @version 1.0.0
 */

public class DateFormatException extends Exception implements DateFormatExceptionInterface {
    public DateFormatException(String message) {
      super(message);
    }
}
