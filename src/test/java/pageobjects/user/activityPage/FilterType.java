package pageobjects.user.activityPage;

import java.util.logging.Filter;

/**
 * Created by noelc on 2016-11-17.
 */
public enum FilterType {
    NOTE("q4i-note-2pt",false),
    PHONE("q4i-phone-2pt",false),
    EMAIL("q4i-mail-2pt",false),
    MEETING("q4i-meeting-2pt",false),
    ROADHSHOW("q4i-roadshow-2pt",false);

    private final String iconClass;
    private boolean checked;

    FilterType(String iconClass,boolean checked){
        this.iconClass=iconClass;
        this.checked= checked;
    }


    public String iconClass(){
        return this.iconClass;
    }

    public void setChecked(boolean value){
        this.checked=value;
    }

    public boolean isChecked() {
        return checked;
    }

    public FilterType returnType (String cssClass){

        switch(cssClass){
            case "q4i-note-2pt":
                return FilterType.NOTE;
            case "q4i-phone-2pt":
                return FilterType.PHONE;
            case "q4i-mail-2pt":
                return FilterType.EMAIL;
            case "q4i-meeting-2pt":
                return FilterType.MEETING;
            case "q4i-roadshow-2pt":
                return FilterType.ROADHSHOW;
            default:
                return null;

        }
    }

}
