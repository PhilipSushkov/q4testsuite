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


}
