package pageobjects.admin.morningCoffeePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by noelc on 2017-01-18.
 */
public class MorningCoffeePreview extends AbstractPageObject{

   private final By mailIcon = By.className("mail");
   private final By deleteIcon = By.className("remove");
   private final By saveIcon =  By.className("button-dark-grey");
   private final By detailsSetion = By.xpath("//button[@contains(text(),'save')]");
   private final By saveConfirmation = By.xpath("//h2[contains(text(),'Success!')]");
   private final By cancelDelete = By.xpath("//q4-delete-confirm//button[contains(text(),'Cancel')]");
   private final By confirmDelete = By.xpath("//q4-delete-confirm//button[contains(text(),'Confirm')]");
   private final By marketCommentaryText = By.xpath("//div[contains(@class,'col-1') and .//span[contains(text(),'Market Commentary')]]//div[contains(@class,'ql-editor')]");
    private final By sectorCommentaryText = By.xpath("//div[contains(@class,'col-1') and .//span[contains(text(),'Sector Commentary')]]//div[contains(@class,'ql-editor')]");
    private final By companyCommentaryText = By.xpath("//div[contains(@class,'col-1') and .//span[contains(text(),'Company Commentary')]]//div[contains(@class,'ql-editor')]");
    private final By morningCoffeeTitle = By.xpath("//q4-morning-coffee-details//div[contains(text(),'Morning Coffee Report')]");

    //Ugly selectors below. No real unique styling for this group of elements.
    //Stock summary selectors
    private final By stockSummary = By.xpath("//h3[contains(text(),'Stock Summary')]");
    private final By stockSummaryStockTitle = By.xpath("//q4-table-stock-summary/p-datatable//span[contains(text(),'Stock ($)')]");
    private final By stockSummaryChangeTitle= By.xpath("//q4-table-stock-summary/p-datatable//span[contains(text(),'Change ($)')]");
    private final By stockSummaryVolumeTitle = By.xpath("//q4-table-stock-summary/p-datatable//span[contains(text(),'volume')]");
    private final By stockSummaryStockAmount = By.xpath("//q4-table-stock-summary/p-datatable//table/tbody/tr/td[1]");
    private final By stockSummaryChangeAmount =By.xpath("//q4-table-stock-summary/p-datatable//table/tbody/tr/td[2]");
    private final By stockSummaryVolumeAmount =By.xpath("//q4-table-stock-summary/p-datatable//table/tbody/tr/td[3]");


    public MorningCoffeePreview(WebDriver driver){
        super(driver);
    }

    public MorningCoffeePreview clickMailIcon(){
        findElement(mailIcon).click();
        return this;
    }

    public MorningCoffeePreview clickDeleteIcon(){
        findElement(deleteIcon).click();
        waitForLoadingScreen();
        return this;
    }

    public MorningCoffeePreview clickSaveIcon(){
        findElement(saveIcon).click();
        return this;
    }

    public boolean canSaveMorningReport(){
        clickSaveIcon();
        waitForLoadingScreen();
        try{
        findElement(saveConfirmation);
        return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean previewPageLoaded(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(morningCoffeeTitle));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean canCancelDelete(){
        clickDeleteIcon();
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cancelDelete));
        findElement(cancelDelete).click();
        try{
            findElement(deleteIcon);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void typeStuffs(){
        findElement(companyCommentaryText).sendKeys("THIS WORKS!");
    }

    public MorningCoffeePage confirmDelete(){
        clickDeleteIcon();
        waitForLoadingScreen();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDelete));
        findVisibleElement(confirmDelete).click();
        waitForLoadingScreen();
        return new MorningCoffeePage(driver);
    }

    public boolean stockSummaryPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummary)));
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummaryStockTitle)));
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummaryChangeTitle)));
            wait.until(ExpectedConditions.visibilityOf(findElement(stockSummaryVolumeTitle)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
