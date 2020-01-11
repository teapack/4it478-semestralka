package cz.vratislavjindra.rukovoditel.selenium.utils;

/**
 * Enum with task statuses definition.
 *
 * @author Vratislav Jindra
 * @version 202001112005
 */
public enum TaskStatus {

    NEW("New", "46"),
    OPEN("Open", "47"),
    WAITING("Waiting", "48"),
    DONE("Done", "49"),
    CLOSED("Closed", "50"),
    PAID("Paid", "51"),
    CANCELED("Canceled", "52");

    private String statusTitle;
    private String statusComboBoxValue;

    TaskStatus(String statusTitle, String statusComboBoxValue) {
        this.statusTitle = statusTitle;
        this.statusComboBoxValue = statusComboBoxValue;
    }

    /**
     * Returns the status title.
     *
     * @return The status title.
     */
    public String getStatusTitle() {
        return statusTitle;
    }

    /**
     * Returns the status combo box value.
     *
     * @return The status combo box value.
     */
    public String getStatusComboBoxValue() {
        return statusComboBoxValue;
    }
}