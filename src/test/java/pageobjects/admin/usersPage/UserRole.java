package pageobjects.admin.usersPage;

import org.openqa.selenium.By;

/**
 * Created by noelc on 2016-11-22.
 */
public enum UserRole {
    ADMIN("Admin","//div[contains(@class,'ui-datatable-tablewrapper')]//tr[.//span[contains(text(),'Admin')]]"),
    DEVOPS("DevOps","//div[contains(@class,'ui-datatable-tablewrapper')]//tr[.//span[contains(text(),'DevOps')]]"),
    INTELLIGENCE_ANALYST("Intelligence Analyst","//div[contains(@class,'ui-datatable-tablewrapper')]//tr[.//span[contains(text(),'Intelligence')]]"),
    CUSTOMER_SUCCESS_MANAGER("Customer Success Manager","//div[contains(@class,'ui-datatable-tablewrapper')]//tr[.//span[contains(text(),'Customer')]]"),
    IMPLEMENTATION_MANAGER("Implementation Manager","//div[contains(@class,'ui-datatable-tablewrapper')]//tr[.//span[contains(text(),'Implementation')]]"),
    SALES_MANAGER("Sales Manager","//div[contains(@class,'ui-datatable-tablewrapper')]//tr[.//span[contains(text(),'Sales')]]");

    private final String role;
    private final String roleSelector;

    UserRole(String role, String roleSelector) {
        this.role = role;
        this.roleSelector = roleSelector;
    }

    public String getRole() {
        return role;
    }

    public String returnSelector(){
        return roleSelector;
    }
}
