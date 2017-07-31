package pageobjects;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public interface PageObject {

    final class ElementNotFoundException extends RuntimeException {
        private ElementNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    WebDriver getDriver();

    Predicate<WebElement> getDisplayedElementPredicate();

    WebDriverWait getWait();

    default void typeInSelect2Input(String keys) {
        findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys(keys);
    }

    default boolean retryTypeInSelect2Input(String keys) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                findElement(By.cssSelector("input.select2-input.select2-focused")).clear();
                findElement(By.cssSelector("input.select2-input.select2-focused")).sendKeys(keys);
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }

    default void chooseSelect2Match() {
        findElement(By.className("select2-match")).click();
    }

    default void chooseFirstSelect2Result() {
        retryClick(By.className("select2-result"));
    }

    default WebElement findElement(By selector) {
        try {
            return getDriver().findElement(selector);
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException("Could not find element " + selector, e);
        }
    }

    default boolean doesElementExist(By selector) {
        try {
            getDriver().findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    default List<WebElement> findElements(By selector) {
        return getDriver().findElements(selector);
    }

    default WebElement findChildOfVisibleParent(By parentSelector, By childSelector) {
        return findVisibleElement(parentSelector).findElement(childSelector);
    }

    default WebElement findVisibleElement(By selector) {
        List<WebElement> elements = getDriver().findElements(selector);

        return CollectionUtils.find(elements, getDisplayedElementPredicate());
    }

    default List<WebElement> findVisibleElements(By selector) {
        List<WebElement> elements = getDriver().findElements(selector);
        List<WebElement> visible = new ArrayList<WebElement>();
        for (WebElement element : elements){
            if (element.isDisplayed()){
                visible.add(element);
            }
        }
        return visible;
    }

    default WebElement findParentOf(By selector) {
        return findElement(selector).findElement(By.xpath("parent::*"));
    }

    default WebElement waitForElement(By selector) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(selector));
    }

    default void waitForElementToDissapear(By selector) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(selector));
    }

    default WebElement waitForElementToAppear(By selector) {
         return getWait().until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    default void waitForAnyElementToAppear(By selector) {

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long loopStartTime = startTime;
        long loopTime = 0;

        while (elapsedTime < 10000) {
            if (loopTime > 500) {
                List<WebElement> elements = findElements(selector);
                for (WebElement element : elements) {
                    if (element.isDisplayed()) return;
                }
                loopStartTime = System.currentTimeMillis();
                loopTime = 0;
            } else {
                loopTime = System.currentTimeMillis()-loopStartTime;
            }
            elapsedTime = System.currentTimeMillis()-startTime;
        }
        throw new TimeoutException("Timed out after 10 seconds waiting for element to appear: " + selector);
    }

    default WebElement waitForElementToBeClickable(By selector) {
        return getWait().until(ExpectedConditions.elementToBeClickable(selector));
    }

    default void waitForText(String text) {
        waitForElement(By.xpath("//*[contains(text(), '"+ text +"')]"));
    }

    default void waitForTextToChange(By selector) {
        waitForElement(selector);
        String currentText = findElement(selector).getText();
        getWait().until(new ExpectedCondition() {
            @Override
            public Object apply(Object o) {
                return !findElement(selector).getText().equals(currentText);
            }
        });
    }

    default void waitForTextToChange(By selector, String from) {
        try {
            if (findVisibleElement(selector).getText().contains(from)) {
                waitForTextToChange(selector);
            }
        } catch (StaleElementReferenceException e) {
           pause(500L);
           if (findVisibleElement(selector).getText().contains(from)) {
               waitForTextToChange(selector);
           }
        }
    }

    default WebElement waitForElementToRest(By selector, long restTime) {
        // Waits for element to remain unchanged for the given time (in millis), or for 10s

        WebElement element = findElement(selector);
        long startTime = System.currentTimeMillis();
        long sinceChanged = startTime;
        long elapsedTime = 0;
        while (elapsedTime < restTime) {
            if (!element.equals(findElement(selector))) {
                sinceChanged = System.currentTimeMillis();
            }
            elapsedTime = System.currentTimeMillis()-sinceChanged;

            if (System.currentTimeMillis()-startTime > 10000) {
                throw new TimeoutException("Timed out after 10 seconds waiting for element to rest: " + selector);
            }
        }
        return element;
    }

    default void disableAnimations() {

        // See https://stackoverflow.com/questions/14791094/how-to-set-the-universal-css-selector-with-javascript

        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript(";(function(exports) {var style = document.querySelector(\"head\").appendChild(document.createElement(\"style\"));\n" +
                "var styleSheet = document.styleSheets[document.styleSheets.length - 1];\n" +
                "styleSheet.insertRule(\"* {}\", 0);\n" +
                "exports.universal = styleSheet.cssRules[0];}(window));");
        executor.executeScript("window.universal.style.cssText += (\"; animation-duration: 0s !important; transition-duration: 0s !important\")");
    }

    default <T extends PageObject> T pause(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return (T) this;
    }

    default void searchSelect2For(By selector, String thingToSelect) {
        findParentOf(selector).findElement(By.cssSelector(".select2-container > a")).click();
        typeInSelect2Input(thingToSelect);
        chooseSelect2Match();
    }

    // Attempts to click an element 3 times to prevent stale element reference.
    //   This is caused when the driver tries to click an element but the element
    //   reloads for some reason
    default boolean retryClick(By by) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                findElement(by).click();
                result = true;
                break;
            } catch (Exception e) {
                // Retry if exception occurs
            }
        }
        return result;
    }

    default boolean retryClick(WebElement element) {
        boolean result = false;

        for (int i = 0; i < 3; i++) {
            try {
                element.click();
                result = true;
                break;
            } catch (Exception e) {
                // Retry if exception occurs
            }
        }
        return result;
    }

    default String retryGetText(By by) {
        String result = null;
        for (int i = 0; i < 3; i++) {
            try {
                result = findElement(by).getText();
                break;
            } catch (StaleElementReferenceException e) {
                // Retry if Stale Element Reference Exception occurs
            }
        }
        return result;
    }
}
