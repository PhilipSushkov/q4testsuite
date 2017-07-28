package pageobjects.user.activityPage;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.user.Calendar;
import pageobjects.user.noteDetailsPage.NoteDetailsPage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by philipsushkov on 2016-08-07.
 */
public class ActivityPage extends AbstractPageObject {

    private final By notesSection = By.xpath("//div[contains(@class,'note-manager-list')]//div[contains(@class,'x-dataview-item')]");
    private final By firstNoteInList = By.xpath("//div[contains(@class,'note-item')][1]//div[contains(@class,'title')]");
    private final By firstNoteInListDate = By.xpath("//div[1][contains(@class,'note-item')]//div[contains(@class,'column')][7]");
    private final By firstNoteInListNewTag = By.xpath("//div[1][contains(@class,'note-item')]//div[contains(@class,'column')][8]/a[1]");
    private final By firstNoteInListLocation = By.xpath("//div[1][contains(@class,'note-item')]//div[contains(@class,'column')][6]");
    private final By newActivityIcon = By.xpath("//div[contains(@class, 'x-unsized x-button x-iconalign-left primary-action btn btn-primary btn-icon-only x-dock-item x-docked-right')]/span[contains(@class, 'x-button-icon x-shown q4i-add-4pt')]");
    private final By activitySearchField = By.cssSelector(".toolbar-panel .search .x-field-input .x-input-el");
    private final By deleteButton = By.xpath("//span[contains(@class, 'q4i-trashbin-4pt')]");
    private final By confirmDeleteButton = By.xpath("//span[@class='x-button-label'][text()='Yes']");
    private final By emptyResults = By.cssSelector(".note-manager-list .x-dataview-emptytext");
    private final By notesCount = By.xpath("//*[@class=\"counter\"][1]");
    private final By callCount = By.xpath("(//*[@class=\"counter\"])[2]");
    private final By emailCount = By.xpath("(//*[@class=\"counter\"])[3]");
    private final By meetingCount = By.xpath("(//*[@class=\"counter\"])[4]");
    private final By yourActivityToggle = By.cssSelector("#ext-thumb-3");
    private final By rowCheckBox = By.xpath("//div[contains(@class,'checkmark') and contains(@class,'checkbox')]");
    private final By deleteIcon = By.xpath("//div[span[contains(@class,'q4i-trashbin-4pt')]]");
    private final By deleteConfirm = By.xpath("//div[span[contains(text(),'Yes')]]");
    private final By bulkCheckBox = By.xpath("//div[contains(@class,'bulk-checkbox')]");

    //This is actually the text beside the checkbox. Clicking the checkbox is proving to be difficult
    private final By filterDropDown = By.xpath("//div[contains(@class,'filters-toggle')]");
    private final By noteFilterCheckbox = By.xpath("//div[contains(@class,'x-button') and .//span[contains(text(),'Notes')]]");
    private final By phoneFilterCheckbox = By.xpath("//div[contains(@class,'x-button') and .//span[contains(text(),'Calls')]]");
    private final By emailFilterCheckbox = By.xpath("//div[contains(@class,'x-button') and .//span[contains(text(),'Emails')]]");
    private final By meetingFilterCheckbox = By.xpath("//div[contains(@class,'x-button') and .//span[contains(text(),'Meetings')]]");
    private final By roadshowFilterCheckbox = By.xpath("//div[contains(@class,'x-button') and .//span[contains(text(),'Roadshows')]]");
    private final By activityDataTable = By.cssSelector(".x-dataview-container");


    //Column headers

    private final By typeHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Type')]]");
    private final By titleHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Title')]]");
    private final By contactHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Contact')]]");
    private final By institutionHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Institution')]]");
    private final By locationHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Location')]]");
    private final By dateHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Date')]]");
    private final By tagsHeader = By.xpath("//div[contains(@class,'column') and contains(@class,'x-button-no-icon') and .//span[contains(text(),'Tags')]]");

    //for calendars
    private final By startTimeSelector = By.xpath("//input[contains(@name,'startDate')]");
    private final By endTimeSelector = By.xpath("//input[contains(@name,'endDate')]");
    private final By previousMonthButton = By.xpath("//div[@class='pmu-prev pmu-button']");
    private final By nextMonthButton = By.xpath("//div[@class='pmu-next pmu-button']");
    private final By selectedMonth = By.xpath("//div[@class='pmu-month pmu-button']");
    private final By selectedDay = By.xpath("//div[@class='pmu-days']/div[@class='pmu-button'][11]");
    private final By dateFilterButton = By.xpath("//div[contains(@class,'go-button')]");


    public ActivityPage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> returnTableRows (){
        List<WebElement> rowList = findVisibleElements(By.xpath("//div[contains(@class,'x-dataview-container')]//div[contains(@class,'row')]"));
        ArrayList<WebElement> tableRowsList = new ArrayList<>(rowList);
        return tableRowsList;

    }

    public String getNewNote() {
        // Waits for the load more button to appear at the bottom of the page.
        waitForLoadingScreen();
        waitForAnyElementToAppear(notesSection);
        return findVisibleElement(notesSection).getText();
    }

    public NoteDetailsPage selectFirstNoteInList() {
        waitForLoadingScreen();
        waitForElement(firstNoteInList);
        findVisibleElement(firstNoteInList).click();

        return new NoteDetailsPage(getDriver());
    }

    public LogActivityPage logNote() {
        waitForLoadingScreen();
        waitForElement(newActivityIcon);
        findVisibleElement(newActivityIcon).click();

        return new LogActivityPage(getDriver());
    }

    public ActivityPage searchForNote(String note) {
        waitForLoadingScreen();
        waitForElement(activitySearchField);
        findVisibleElement(activitySearchField).click();
        findVisibleElement(activitySearchField).clear();
        findVisibleElement(activitySearchField).sendKeys(note);
        //findElement(activitySearchField).sendKeys(Keys.RETURN);
        waitForLoadingScreen();
        if (doesElementExist(notesSection)) {
            waitForElementToRest(notesSection, 2000L);
        }

        return this;
    }

    /**
     * FILTERS
     */

    private By getColumnSelector(ColumnType column){
        By selector =null;

        switch(column){
            case TYPE:
                selector = typeHeader;
                break;
            case TITLE:
                selector = titleHeader;
                break;
            case CONTACT:
                selector = contactHeader;
                break;
            case INSTITUTION:
                selector = institutionHeader;
                break;
            case LOCATION:
                selector = locationHeader;
                break;
            case DATE:
                selector = dateHeader;
                break;
            case TAGS:
                selector = tagsHeader;
                break;
        }
        return selector;
    }

    private By getFilterSelector(FilterType filter){
        By selector=null;
        switch(filter){
            case EMAIL:
                selector = emailFilterCheckbox;
                break;
            case PHONE:
                selector = phoneFilterCheckbox;
                break;
            case NOTE:
                selector = noteFilterCheckbox;
                break;
            case MEETING:
                selector = meetingFilterCheckbox;
                break;
            case ROADHSHOW:
                selector = roadshowFilterCheckbox;
                break;
        }
        return selector;
    }

    public ActivityPage clickColumnHeader(ColumnType column){
        By selector = getColumnSelector(column);
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(selector));
        findElement(selector).click();
        return this;
    }


    public boolean isColumnAscending(ColumnType column){
        By selector = getColumnSelector(column);
        return findElement(selector).getAttribute("class").contains("asc");

    }

    public ActivityPage clickFilterCheckbox(FilterType filter){
        By selector = getFilterSelector(filter);
        waitForLoadingScreen();
        wait.until(ExpectedConditions.elementToBeClickable(filterDropDown)).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(selector));
        findElement(selector).click();
        waitForLoadingScreen();
        return this;

    }

    public boolean isFilteredCorrectly(FilterType filter){
        List<WebElement> rows = returnTableRows();

        for (WebElement i : rows){
         if(!(i.findElement(By.xpath(".//i")).getAttribute("class").equals(filter.iconClass()))){
                return false;
            }
        }
        return true;
    }

    private boolean isTitleSorted (List<WebElement> rows){

        ArrayList<WebElement> titles = new ArrayList<>();
        for(WebElement i : rows){
            titles.add(i.findElement(By.className("title")));
        }
        if(isColumnAscending(ColumnType.TITLE)){
            return elementsAreAlphaUpSorted(titles);
        }
        else
            return elementsAreAlphaDownSorted(titles);
    }

    private boolean isContactSorted (List<WebElement> rows){

        ArrayList<WebElement> contacts = new ArrayList<>();
        for(WebElement i : rows){
            contacts.add(i.findElement(By.className("contact")));
        }
        if(isColumnAscending(ColumnType.CONTACT)){
            return elementsAreAlphaUpSorted(contacts);
        }
        else
            return elementsAreAlphaDownSorted(contacts);
    }

    private boolean isInstitutionSorted(List<WebElement> rows){
        ArrayList<WebElement> institutions = new ArrayList<>();
        for(WebElement i : rows){
            institutions.add(i.findElement(By.className("institution")));
        }
        if(isColumnAscending(ColumnType.INSTITUTION)){
            return elementsAreAlphaUpSorted(institutions);
        }
        else
            return elementsAreAlphaDownSorted(institutions);
    }


    private boolean isTypeSorted(List<WebElement> rows){
        FilterType email = FilterType.EMAIL;
        FilterType note = FilterType.NOTE;
        FilterType meeting = FilterType.MEETING;
        FilterType phone = FilterType.PHONE;
        FilterType roadshow = FilterType.ROADHSHOW;
        FilterType none = FilterType.NONE;
        FilterType savedIcon = null;
        FilterType currentIcon= FilterType.PHONE;

        for (WebElement i : rows){
            if(savedIcon!=null){
                currentIcon=currentIcon.returnType(i.findElement(By.xpath("//div[@class='column centered type']/i")).getAttribute("class"));
                if (currentIcon!=savedIcon) {
                    switch (currentIcon) {
                        case EMAIL:
                            if (email.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = email;
                            }
                            break;
                        case NOTE:
                            if (note.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = note;
                            }
                            break;
                        case MEETING:
                            if (meeting.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = meeting;
                            }
                            break;
                        case PHONE:
                            if (phone.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = phone;
                            }
                        case ROADHSHOW:
                            if (roadshow.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = roadshow;
                            }
                        case NONE:
                            if (none.isChecked()) {
                                return false;
                            } else {
                                switch(savedIcon){
                                    case EMAIL:
                                        email.setChecked(true);
                                        break;
                                    case NOTE:
                                        note.setChecked(true);
                                        break;
                                    case MEETING:
                                        meeting.setChecked(true);
                                        break;
                                    case PHONE:
                                        phone.setChecked(true);
                                        break;
                                    case ROADHSHOW:
                                        roadshow.setChecked(true);
                                        break;
                                    case NONE:
                                        none.setChecked(true);
                                        break;
                                }
                                savedIcon = none;
                            }
                    }
                }

            }
            else {
                if (i.findElement(By.xpath(".//i")).getAttribute("class").equals(email.iconClass())) {
                    savedIcon = FilterType.EMAIL;
                } else if (i.findElement(By.xpath(".//i")).getAttribute("class").equals(note.iconClass())) {
                    savedIcon = FilterType.NOTE;
                } else if (i.findElement(By.xpath(".//i")).getAttribute("class").equals(meeting.iconClass())){
                    savedIcon = FilterType.MEETING;
                } else if(i.findElement(By.xpath(".//i")).getAttribute("class").equals(roadshow.iconClass())) {
                    savedIcon = FilterType.ROADHSHOW;
                }else {
                    savedIcon = FilterType.PHONE;
                }
            }
        }
           return true;
    }


    private boolean isDateSorted(List<WebElement> rows){
        ArrayList<WebElement> dates = new ArrayList<>();
        for(WebElement i : rows){
           dates.add(i.findElement(By.className("date")));
        }
        if(isColumnAscending(ColumnType.DATE)){
            return elementsAreDateUpSorted(dates);
        }else{
            return elementsAreDateDownSorted(dates);
        }
    }



    public boolean isColumnSorted(ColumnType column){
        waitForLoadingScreen();
        switch(column){
            case TYPE:
                return isTypeSorted(returnTableRows());
            case TITLE:
                return isTitleSorted(returnTableRows());
            case CONTACT:
                return isContactSorted(returnTableRows());
            case INSTITUTION:
                return isInstitutionSorted(returnTableRows());
            case LOCATION:
                break;
            case DATE:
                return isDateSorted(returnTableRows());
            case TAGS:
                break;
        }
        return false;
    }

    /**
     *
     *
     */
    public String getDate() throws ParseException {
        //Gets date from first note on the activity page and format it
        DateFormat activityFormat = new SimpleDateFormat("MM/dd/yy");
        DateFormat detailsFormat = new SimpleDateFormat("MMMM d, yyyy");
        Date strToDate = activityFormat.parse(findVisibleElement(firstNoteInListDate).getText());
        String dateToString = detailsFormat.format(strToDate);
        return dateToString;
    }

    public String getActivityPageTag(){ return findElement(firstNoteInListNewTag).getText(); }

    public String getActivityPageLocation(){return findVisibleElement(firstNoteInListLocation).getText(); }

    public String getNoNote() {
        return findElement(emptyResults).getText();
    }

    public String getSearchResults() {
        return findElement(notesSection).getText();
    }

    public int getNoteCount() {
        pause(1000L);
        return Integer.parseInt(findElement(notesCount).getText());
    }

    public int getCallCount() {
        pause(1000L);
        return Integer.parseInt(findElement(callCount).getText());
    }

    public int getEmailCount() {
        pause(1000L);
        return Integer.parseInt(findElement(emailCount).getText());
    }

    public int getMeetingCount() {
        pause(1000L);
        return Integer.parseInt(findElement(meetingCount).getText());
    }

    public Calendar filterDate(Calendar calendar) {
        calendar.selectStartDate(startTimeSelector, previousMonthButton, selectedMonth, selectedDay);
        calendar.selectEndDate(endTimeSelector, nextMonthButton, selectedMonth, selectedDay);
        calendar.filter(dateFilterButton);
        pause(500L);
        // helps keep track of which days were selected
        calendar.print();
        return calendar;
    }

    public boolean verifyDateFilter(Calendar calendar) {
        waitForLoadingScreen();
        // sorting the date by earliest to latest
        findVisibleElement(dateHeader).click();
        pause(2000L);
        boolean Sorted = true;
        if (!calendar.EarliestDateWithinRange(findVisibleElement(firstNoteInListDate).getText())) {
            System.out.println("Earliest date in the table is earlier than the selected end time");
            Sorted = false;
        }
        waitForLoadingScreen();
        // sorting the date by latest to earliest
        findVisibleElement(dateHeader).click();
        pause(2000L);
        if (!calendar.latestDateWithinRange(findVisibleElement(firstNoteInListDate).getText())) {
            System.out.println("Latest date in the table is later than the selected end time");
            Sorted = false;
        }
        return Sorted;
    }

    public ActivityPage clickNthActivityCheckBox(int n) {
        try {
            findVisibleElements(rowCheckBox).get(n).click();
        } catch (StaleElementReferenceException e) {
            findVisibleElements(rowCheckBox).get(n).click();
        }

        return this;
    }

    public ActivityPage clickDeleteButton() {
        findVisibleElement(deleteButton).click();
        waitForElementToAppear(confirmDeleteButton);
        findVisibleElement(confirmDeleteButton).click();

        return this;
    }

    public ActivityPage yourActivityFilter(){
        findElement(yourActivityToggle).click();
        pause(200L);

        return this;
    }

    public ActivityPage deleteAllNotes(String title){
        List<WebElement> searchResults;
        waitForLoadingScreen();
        searchForNote(title);
        if(!findVisibleElement(bulkCheckBox).getAttribute("class").contains("x-item-disabled")) {
            findVisibleElement(bulkCheckBox).click();
            findVisibleElement(deleteIcon).click();
            waitForElementToAppear(deleteConfirm);
            findVisibleElement(deleteConfirm).click();
        }
        return this;
    }

    public int getNumberOfDisplayedActivities(){
        try {
            return findVisibleElements(rowCheckBox).size();
        } catch (StaleElementReferenceException e) {
            return findVisibleElements(rowCheckBox).size();
        }
    }
}

