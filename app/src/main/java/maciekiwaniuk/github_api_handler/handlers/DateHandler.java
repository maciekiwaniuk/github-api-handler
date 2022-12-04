package maciekiwaniuk.github_api_handler.handlers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Compares passed date to current date and handles it for specific needs.
 */
public class DateHandler {
    /**
     * Current date
     */
    public final LocalDateTime currentDateTime;

    /**
     * Passed date which will be compared to currentDate
     */
    public final LocalDateTime passedDateTime;

    /**
     * Difference between passedDate and currentDate in seconds.
     */
    public long differenceInSeconds;
    public long differenceInMinutes;
    public long differenceInHours;
    public long differenceInDays;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DateHandler(LocalDateTime passedDateTime) {
        this.passedDateTime = passedDateTime;
        this.currentDateTime = LocalDateTime.now();

        this.calculateDifferenceBetweenDates();
    }

    /**
     * Calculates difference between passedDateTime and currentDateTime
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calculateDifferenceBetweenDates() {
        this.differenceInSeconds = this.passedDateTime.until(this.currentDateTime, ChronoUnit.SECONDS);
        this.differenceInMinutes = differenceInSeconds / 60;
        this.differenceInHours = this.differenceInMinutes / 60;
        this.differenceInDays = this.differenceInHours / 24;
    }

    /**
     * Returns message how much time has passed since passedDate to currentDate.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getMessageAboutTimeDifferenceSinceNow() {
        String message = "Updated ";

        if (this.differenceInDays > 365) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            message += ("on " + passedDateTime.format(formatter));

        } else if (this.differenceInDays > 31) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
            message += ("on " + passedDateTime.format(formatter));

        } else if (this.differenceInDays > 1) {
            message += (this.differenceInDays + " days ago");
        } else if (this.differenceInDays == 1) {
            message += (this.differenceInDays + " day ago");

        } else if (this.differenceInHours > 1) {
            message += (this.differenceInHours + " hours ago");
        } else if (this.differenceInHours == 1) {
            message += (this.differenceInHours + " hour ago");

        } else if (this.differenceInMinutes > 1) {
            message += (this.differenceInMinutes + " minutes ago");
        } else if (this.differenceInMinutes == 1) {
            message += (this.differenceInMinutes + " minute ago");

        } else if (this.differenceInSeconds == 1) {
            message += (this.differenceInSeconds + " second ago");
        } else  {
            message += (this.differenceInSeconds + " seconds ago");
        }

        return message;
    }
}
