package pageobjects.user.activityPage;

/**
 * Created by noelc on 2016-11-17.
 */
public enum FilterType {
    NOTE("q4i-note-2pt"),
    PHONE("q4i-phone-2pt"),
    EMAIL("q4i-mail-2pt"),
    MEETING("q4i-meeting-2pt");

    private final String iconClass;

    FilterType(String iconClass){
        this.iconClass=iconClass;
    }

    public String iconClass(){
        return iconClass;
    }
}
