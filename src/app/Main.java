
package app;

import dao.ExchangeDAO;
import dao.ReviewDAO;
import dao.SkillDAO;
import dao.SkillRequestDAO;
import dao.UserDAO;
import dao.UserSkillDAO;

import java.util.List;
import java.util.Scanner;

import model.Exchange;
import model.Review;
import model.Skill;
import model.SkillRequest;
import model.User;
import model.UserSkill;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final UserDAO userDAO = new UserDAO();
    private static final SkillDAO skillDAO = new SkillDAO();
    private static final UserSkillDAO userSkillDAO = new UserSkillDAO();
    private static final SkillRequestDAO skillRequestDAO =
            new SkillRequestDAO();
    private static final ExchangeDAO exchangeDAO =
            new ExchangeDAO();
    private static final ReviewDAO reviewDAO =
            new ReviewDAO();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n==============================");
            System.out.println("          SKILLSWAP");
            System.out.println("==============================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println(
                            "\nThank you for using SkillSwap!"
                    );
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid choice!");
            }
        }
    }

    // =====================================================
    // REGISTER
    // =====================================================

    private static void register() {

        System.out.println("\n========== REGISTER ==========");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter bio: ");
        String bio = scanner.nextLine();

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        User user = new User(
                name,
                email,
                password,
                bio,
                location
        );

        boolean success = userDAO.addUser(user);

        if (success) {
            System.out.println(
                    "\nRegistration successful!"
            );
        } else {
            System.out.println(
                    "\nRegistration failed!"
            );
        }
    }

    // =====================================================
    // LOGIN
    // =====================================================

    private static void login() {

        System.out.println("\n========== LOGIN ==========");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDAO.loginUser(
                email,
                password
        );

        if (user != null) {

            System.out.println(
                    "\nLogin successful!"
            );

            System.out.println(
                    "Welcome, "
                            + user.getName()
                            + "!"
            );

            dashboard(user);

        } else {

            System.out.println(
                    "\nInvalid email or password!"
            );
        }
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private static void dashboard(User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println(
                    "\n=============================="
            );

            System.out.println(
                    "       SKILLSWAP DASHBOARD"
            );

            System.out.println(
                    "=============================="
            );

            System.out.println("1. View My Profile");
            System.out.println("2. View My Skills");
            System.out.println("3. Add Skill");
            System.out.println("4. Remove Skill");
            System.out.println("5. Search Users by Skill");
            System.out.println("6. Send Skill Request");
            System.out.println("7. View Received Requests");
            System.out.println("8. Accept / Reject Request");
            System.out.println("9. View My Exchanges");
            System.out.println("10. Complete Exchange");
            System.out.println("11. Add Review");
            System.out.println("12. View Reviews Received");
            System.out.println("13. View Reviews Given");
            System.out.println("14. Logout");

            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    viewProfile(user);
                    break;

                case 2:
                    viewMySkills(user);
                    break;

                case 3:
                    addSkill(user);
                    break;

                case 4:
                    removeSkill(user);
                    break;

                case 5:
                    searchUsersBySkill();
                    break;

                case 6:
                    sendSkillRequest(user);
                    break;

                case 7:
                    viewReceivedRequests(user);
                    break;

                case 8:
                    updateRequestStatus(user);
                    break;

                case 9:
                    viewMyExchanges(user);
                    break;

                case 10:
                    completeExchange(user);
                    break;

                case 11:
                    addReview(user);
                    break;

                case 12:
                    viewReviewsReceived(user);
                    break;

                case 13:
                    viewReviewsGiven(user);
                    break;

                case 14:
                    loggedIn = false;

                    System.out.println(
                            "\nLogged out successfully!"
                    );

                    break;

                default:
                    System.out.println(
                            "\nInvalid choice!"
                    );
            }
        }
    }

    // =====================================================
    // VIEW PROFILE
    // =====================================================

    private static void viewProfile(User user) {

        System.out.println(
                "\n========== MY PROFILE =========="
        );

        System.out.println(
                "ID       : " + user.getId()
        );

        System.out.println(
                "Name     : " + user.getName()
        );

        System.out.println(
                "Email    : " + user.getEmail()
        );

        System.out.println(
                "Bio      : " + user.getBio()
        );

        System.out.println(
                "Location : " + user.getLocation()
        );
    }

    // =====================================================
    // VIEW MY SKILLS
    // =====================================================

    private static void viewMySkills(User user) {

        System.out.println(
                "\n========== MY SKILLS =========="
        );

        List<String> skills =
                userSkillDAO.getUserSkillDetails(
                        user.getId()
                );

        if (skills.isEmpty()) {

            System.out.println(
                    "No skills added yet."
            );

        } else {

            for (String skill : skills) {

                System.out.println(skill);
            }
        }
    }

    // =====================================================
    // ADD SKILL
    // =====================================================

    private static void addSkill(User user) {

        System.out.println(
                "\n========== ADD SKILL =========="
        );

        List<Skill> skills =
                skillDAO.getAllSkills();

        if (skills.isEmpty()) {

            System.out.println(
                    "No skills available."
            );

            return;
        }

        for (Skill skill : skills) {

            System.out.println(
                    skill.getId()
                            + ". "
                            + skill.getSkillName()
            );
        }

        System.out.print(
                "Enter skill ID: "
        );

        int skillId = readInt();

        System.out.println("\n1. OFFER");
        System.out.println("2. LEARN");

        System.out.print(
                "Choose skill type: "
        );

        int typeChoice = readInt();

        String skillType;

        if (typeChoice == 1) {

            skillType = "OFFER";

        } else if (typeChoice == 2) {

            skillType = "LEARN";

        } else {

            System.out.println(
                    "Invalid skill type!"
            );

            return;
        }

        UserSkill userSkill =
                new UserSkill(
                        user.getId(),
                        skillId,
                        skillType
                );

        if (userSkillDAO.userSkillExists(
                userSkill
        )) {

            System.out.println(
                    "\nThis skill is already added!"
            );

            return;
        }

        boolean success =
                userSkillDAO.addUserSkill(
                        userSkill
                );

        if (success) {

            System.out.println(
                    "\nSkill added successfully!"
            );

        } else {

            System.out.println(
                    "\nFailed to add skill!"
            );
        }
    }

    // =====================================================
    // REMOVE SKILL
    // =====================================================

    private static void removeSkill(User user) {

        System.out.println(
                "\n========== REMOVE SKILL =========="
        );

        List<UserSkill> skills =
                userSkillDAO.getUserSkills(
                        user.getId()
                );

        if (skills.isEmpty()) {

            System.out.println(
                    "You have no skills to remove."
            );

            return;
        }

        for (UserSkill skill : skills) {

            Skill skillDetails =
                    skillDAO.getSkillById(
                            skill.getSkillId()
                    );

            System.out.println(
                    "ID: "
                            + skill.getId()
                            + " | "
                            + (
                            skillDetails != null
                                    ? skillDetails.getSkillName()
                                    : "Unknown"
                    )
                            + " | "
                            + skill.getSkillType()
            );
        }

        System.out.print(
                "\nEnter User Skill ID to remove: "
        );

        int id = readInt();

        boolean success =
                userSkillDAO.deleteUserSkill(id);

        if (success) {

            System.out.println(
                    "\nSkill removed successfully!"
            );

        } else {

            System.out.println(
                    "\nFailed to remove skill!"
            );
        }
    }

    // =====================================================
    // SEARCH USERS BY SKILL
    // =====================================================

    private static void searchUsersBySkill() {

        System.out.println(
                "\n========== SEARCH USERS =========="
        );

        System.out.print(
                "Enter skill name: "
        );

        String skillName =
                scanner.nextLine();

        List<String> users =
                userSkillDAO.searchUsersBySkill(
                        skillName
                );

        if (users.isEmpty()) {

            System.out.println(
                    "\nNo users found for this skill."
            );

        } else {

            System.out.println(
                    "\nUsers found:"
            );

            for (String user : users) {

                System.out.println(user);
            }
        }
    }

    // =====================================================
    // SEND SKILL REQUEST
    // =====================================================

    private static void sendSkillRequest(User user) {

        System.out.println(
                "\n========== SEND SKILL REQUEST =========="
        );

        System.out.print(
                "Enter receiver User ID: "
        );

        int receiverId = readInt();

        if (receiverId == user.getId()) {

            System.out.println(
                    "\nYou cannot send a request to yourself."
            );

            return;
        }

        System.out.print(
                "Enter Skill ID: "
        );

        int skillId = readInt();

        scanner.nextLine();

        System.out.print(
                "Enter message: "
        );

        String message =
                scanner.nextLine();

        SkillRequest request =
                new SkillRequest(
                        user.getId(),
                        receiverId,
                        skillId,
                        message
                );

        boolean success =
                skillRequestDAO.sendRequest(
                        request
                );

        if (success) {

            System.out.println(
                    "\nSkill request sent successfully!"
            );

        } else {

            System.out.println(
                    "\nFailed to send skill request!"
            );
        }
    }

    // =====================================================
    // VIEW RECEIVED REQUESTS
    // =====================================================

    private static void viewReceivedRequests(
            User user
    ) {

        System.out.println(
                "\n========== RECEIVED REQUESTS =========="
        );

        List<SkillRequest> requests =
                skillRequestDAO.getReceivedRequests(
                        user.getId()
                );

        if (requests.isEmpty()) {

            System.out.println(
                    "No requests received."
            );

            return;
        }

        for (SkillRequest request : requests) {

            Skill skill =
                    skillDAO.getSkillById(
                            request.getSkillId()
                    );

            System.out.println(
                    "\nRequest ID : "
                            + request.getId()
            );

            System.out.println(
                    "From User ID : "
                            + request.getRequesterId()
            );

            System.out.println(
                    "Skill : "
                            + (
                            skill != null
                                    ? skill.getSkillName()
                                    : "Unknown"
                    )
            );

            System.out.println(
                    "Message : "
                            + request.getMessage()
            );

            System.out.println(
                    "Status : "
                            + request.getStatus()
            );
        }
    }

    // =====================================================
    // ACCEPT / REJECT REQUEST
    // =====================================================

    private static void updateRequestStatus(
            User user
    ) {

        System.out.println(
                "\n========== ACCEPT / REJECT REQUEST =========="
        );

        List<SkillRequest> requests =
                skillRequestDAO.getReceivedRequests(
                        user.getId()
                );

        if (requests.isEmpty()) {

            System.out.println(
                    "No requests available."
            );

            return;
        }

        for (SkillRequest request : requests) {

            Skill skill =
                    skillDAO.getSkillById(
                            request.getSkillId()
                    );

            System.out.println(
                    "Request ID: "
                            + request.getId()
                            + " | From User ID: "
                            + request.getRequesterId()
                            + " | Skill: "
                            + (
                            skill != null
                                    ? skill.getSkillName()
                                    : "Unknown"
                    )
                            + " | Status: "
                            + request.getStatus()
            );
        }

        System.out.print(
                "\nEnter Request ID: "
        );

        int requestId = readInt();

        SkillRequest selectedRequest = null;

        for (SkillRequest request : requests) {

            if (request.getId() == requestId) {

                selectedRequest = request;
                break;
            }
        }

        if (selectedRequest == null) {

            System.out.println(
                    "\nInvalid request ID."
            );

            return;
        }

        if (!"PENDING".equalsIgnoreCase(
                selectedRequest.getStatus()
        )) {

            System.out.println(
                    "\nThis request is already "
                            + selectedRequest.getStatus()
                            + "."
            );

            return;
        }

        System.out.println("\n1. Accept");
        System.out.println("2. Reject");

        System.out.print(
                "Enter choice: "
        );

        int choice = readInt();

        String status;

        if (choice == 1) {

            status = "ACCEPTED";

        } else if (choice == 2) {

            status = "REJECTED";

        } else {

            System.out.println(
                    "Invalid choice!"
            );

            return;
        }

        boolean success =
                skillRequestDAO.acceptOrRejectRequest(
                        requestId,
                        status
                );

        if (success) {

            System.out.println(
                    "\nRequest "
                            + status.toLowerCase()
                            + " successfully!"
            );

            if ("ACCEPTED".equals(status)) {

                createExchangeFromRequest(
                        requestId
                );
            }

        } else {

            System.out.println(
                    "\nFailed to update request!"
            );
        }
    }

    // =====================================================
    // CREATE EXCHANGE
    // =====================================================

    private static void createExchangeFromRequest(
            int requestId
    ) {

        SkillRequest request =
                getRequestById(requestId);

        if (request == null) {

            System.out.println(
                    "Could not find request."
            );

            return;
        }

        Exchange exchange =
                new Exchange(
                        request.getId(),
                        request.getRequesterId(),
                        request.getReceiverId(),
                        request.getSkillId(),
                        0
                );

        boolean success =
                exchangeDAO.createExchange(
                        exchange
                );

        if (success) {

            System.out.println(
                    "Exchange created successfully!"
            );

        } else {

            System.out.println(
                    "Failed to create exchange."
            );
        }
    }

    // =====================================================
    // GET REQUEST BY ID
    // =====================================================

    private static SkillRequest getRequestById(
            int requestId
    ) {

        String sql = """
                SELECT id, requester_id, receiver_id,
                       skill_id, message, status
                FROM skill_requests
                WHERE id = ?
                """;

        try (
                java.sql.Connection connection =
                        config.DBConnection.getConnection();

                java.sql.PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, requestId);

            java.sql.ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new SkillRequest(
                        resultSet.getInt("id"),
                        resultSet.getInt("requester_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getInt("skill_id"),
                        resultSet.getString("message"),
                        resultSet.getString("status")
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =====================================================
    // VIEW MY EXCHANGES
    // =====================================================

    private static void viewMyExchanges(
            User user
    ) {

        System.out.println(
                "\n===== MY EXCHANGES ====="
        );

        List<Exchange> exchanges =
                exchangeDAO.getUserExchanges(
                        user.getId()
                );

        if (exchanges.isEmpty()) {

            System.out.println(
                    "No exchanges found."
            );

            return;
        }

        for (Exchange exchange : exchanges) {

            System.out.println(
                    "Exchange ID : "
                            + exchange.getId()
            );

            System.out.println(
                    "Request ID : "
                            + exchange.getRequestId()
            );

            System.out.println(
                    "User 1 ID : "
                            + exchange.getUser1Id()
            );

            System.out.println(
                    "User 2 ID : "
                            + exchange.getUser2Id()
            );

            System.out.println(
                    "Skill ID : "
                            + exchange.getSkill1Id()
            );

            String skill2 =
                    "Not specified";

            if (exchange.getSkill2Id() > 0) {

                Skill skill =
                        skillDAO.getSkillById(
                                exchange.getSkill2Id()
                        );

                if (skill != null) {

                    skill2 =
                            skill.getSkillName();
                }
            }

            System.out.println(
                    "Second Skill : "
                            + skill2
            );

            System.out.println(
                    "Status : "
                            + exchange.getStatus()
            );

            System.out.println(
                    "Started At : "
                            + exchange.getStartedAt()
            );

            System.out.println(
                    "Completed At : "
                            + exchange.getCompletedAt()
            );

            System.out.println(
                    "------------------------------"
            );
        }
    }

    // =====================================================
    // COMPLETE EXCHANGE
    // =====================================================

    private static void completeExchange(
            User user
    ) {

        System.out.println(
                "\n===== COMPLETE EXCHANGE ====="
        );

        List<Exchange> exchanges =
                exchangeDAO.getUserExchanges(
                        user.getId()
                );

        boolean foundActive = false;

        for (Exchange exchange : exchanges) {

            if ("ACTIVE".equalsIgnoreCase(
                    exchange.getStatus()
            )) {

                foundActive = true;

                System.out.println(
                        "Exchange ID : "
                                + exchange.getId()
                                + " | Request ID : "
                                + exchange.getRequestId()
                                + " | Status : "
                                + exchange.getStatus()
                );
            }
        }

        if (!foundActive) {

            System.out.println(
                    "No active exchanges found."
            );

            return;
        }

        System.out.print(
                "Enter Exchange ID: "
        );

        int exchangeId = readInt();

        boolean isValidActiveExchange = false;

        for (Exchange exchange : exchanges) {

            if (exchange.getId() == exchangeId
                    && "ACTIVE".equalsIgnoreCase(
                    exchange.getStatus()
            )) {

                isValidActiveExchange = true;
                break;
            }
        }

        if (!isValidActiveExchange) {

            System.out.println(
                    "Invalid exchange ID or exchange is not ACTIVE."
            );

            return;
        }

        boolean success =
                exchangeDAO.completeExchange(
                        exchangeId
                );

        if (success) {

            System.out.println(
                    "\nExchange completed successfully!"
            );

        } else {

            System.out.println(
                    "\nFailed to complete exchange."
            );
        }
    }

    // =====================================================
    // ADD REVIEW
    // =====================================================

    private static void addReview(
            User user
    ) {

        System.out.println(
                "\n===== ADD REVIEW ====="
        );

        List<Exchange> exchanges =
                exchangeDAO.getUserExchanges(
                        user.getId()
                );

        boolean foundCompleted = false;

        for (Exchange exchange : exchanges) {

            if ("COMPLETED".equalsIgnoreCase(
                    exchange.getStatus()
            )) {

                foundCompleted = true;

                System.out.println(
                        "Exchange ID: "
                                + exchange.getId()
                                + " | User 1: "
                                + exchange.getUser1Id()
                                + " | User 2: "
                                + exchange.getUser2Id()
                );
            }
        }

        if (!foundCompleted) {

            System.out.println(
                    "No completed exchanges available for review."
            );

            return;
        }

        System.out.print(
                "Enter Exchange ID: "
        );

        int exchangeId = readInt();

        Exchange selectedExchange = null;

        for (Exchange exchange : exchanges) {

            if (exchange.getId() == exchangeId) {

                selectedExchange = exchange;
                break;
            }
        }

        if (selectedExchange == null) {

            System.out.println(
                    "Exchange not found."
            );

            return;
        }

        if (!"COMPLETED".equalsIgnoreCase(
                selectedExchange.getStatus()
        )) {

            System.out.println(
                    "Only completed exchanges can be reviewed."
            );

            return;
        }

        int reviewedUserId;

        if (selectedExchange.getUser1Id()
                == user.getId()) {

            reviewedUserId =
                    selectedExchange.getUser2Id();

        } else if (selectedExchange.getUser2Id()
                == user.getId()) {

            reviewedUserId =
                    selectedExchange.getUser1Id();

        } else {

            System.out.println(
                    "You are not a participant of this exchange."
            );

            return;
        }

        System.out.println(
                "You are reviewing User ID: "
                        + reviewedUserId
        );

        System.out.print(
                "Enter rating (1-5): "
        );

        int rating = readInt();

        scanner.nextLine();

        System.out.print(
                "Enter comment: "
        );

        String comment =
                scanner.nextLine();

        Review review =
                new Review(
                        exchangeId,
                        user.getId(),
                        reviewedUserId,
                        rating,
                        comment
                );

        boolean success =
                reviewDAO.addReview(
                        review
                );

        if (success) {

            System.out.println(
                    "Review added successfully!"
            );

        } else {

            System.out.println(
                    "Failed to add review!"
            );
        }
    }

    // =====================================================
    // VIEW REVIEWS RECEIVED
    // =====================================================

    private static void viewReviewsReceived(
            User user
    ) {

        System.out.println(
                "\n===== REVIEWS RECEIVED ====="
        );

        List<Review> reviews =
                reviewDAO.getReviewsForUser(
                        user.getId()
                );

        if (reviews.isEmpty()) {

            System.out.println(
                    "No reviews received."
            );

            return;
        }

        for (Review review : reviews) {

            System.out.println(
                    "\nReview ID : "
                            + review.getId()
            );

            System.out.println(
                    "Exchange ID : "
                            + review.getExchangeId()
            );

            System.out.println(
                    "Reviewer ID : "
                            + review.getReviewerId()
            );

            System.out.println(
                    "Rating : "
                            + review.getRating()
                            + "/5"
            );

            System.out.println(
                    "Comment : "
                            + review.getComment()
            );

            System.out.println(
                    "------------------------------"
            );
        }
    }

    // =====================================================
    // VIEW REVIEWS GIVEN
    // =====================================================

    private static void viewReviewsGiven(
            User user
    ) {

        System.out.println(
                "\n===== REVIEWS GIVEN ====="
        );

        List<Review> reviews =
                reviewDAO.getReviewsGivenByUser(
                        user.getId()
                );

        if (reviews.isEmpty()) {

            System.out.println(
                    "No reviews given."
            );

            return;
        }

        for (Review review : reviews) {

            System.out.println(
                    "\nReview ID : "
                            + review.getId()
            );

            System.out.println(
                    "Exchange ID : "
                            + review.getExchangeId()
            );

            System.out.println(
                    "Reviewed User ID : "
                            + review.getReviewedUserId()
            );

            System.out.println(
                    "Rating : "
                            + review.getRating()
                            + "/5"
            );

            System.out.println(
                    "Comment : "
                            + review.getComment()
            );

            System.out.println(
                    "------------------------------"
            );
        }
    }

    // =====================================================
    // SAFE INTEGER INPUT
    // =====================================================

    private static int readInt() {

        while (true) {

            try {

                int value =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                return value;

            } catch (NumberFormatException e) {

                System.out.print(
                        "Please enter a valid number: "
                );
            }
        }
    }
}
