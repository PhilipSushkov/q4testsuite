package pageobjects.admin.usersPage;

/**
 * Created by noelc on 2016-11-22.
 */
public enum UserRole {
    ALL("All"),
    ADMIN("Admin"),
    USER("User"),
    DEVOPS("DevOps"),
    INTELLIGENCE_ANALYST("Intelligence Analyst"),
    CUSTOMER_SUCCESS_MANAGER("Customer Success Manager"),
    IMPLEMENTATION_MANAGER("Implementation Manager"),
    SALES_MANAGER("Sales Manager");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
