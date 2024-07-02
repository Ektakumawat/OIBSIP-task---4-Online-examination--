import java.util.*;

class User {
    private String userId;
    private String password;
    private String profile;
    private boolean loggedIn;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.profile = profile;
        this.loggedIn = false;
    }

    public String getUserId() {
        return userId;
    }

    public boolean login(String password) {
        if (this.password.equals(password)) {
            this.loggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void updateProfile(String newProfile) {
        if (loggedIn) {
            this.profile = newProfile;
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("Please login to update profile.");
        }
    }

    public void updatePassword(String newPassword) {
        if (loggedIn) {
            this.password = newPassword;
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Please login to update password.");
        }
    }
}

class Exam {
    private String[] questions = {
            "What is the capital of France?",
            "What is 2 + 2?",
            "What is the color of the sky?"
    };
    private String[][] options = {
            {"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"},
            {"1. 3", "2. 4", "3. 5", "4. 6"},
            {"1. Green", "2. Blue", "3. Red", "4. Yellow"}
    };
    private int[] correctAnswers = {3, 2, 2};
    private int[] userAnswers;
    private long startTime;
    private long duration;

    public Exam(int numQuestions, long durationInMinutes) {
        this.userAnswers = new int[numQuestions];
        Arrays.fill(this.userAnswers, -1); // Initialize with -1 indicating no answer
        this.duration = durationInMinutes * 60 * 1000; // Convert minutes to milliseconds
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < questions.length; i++) {
            if (isTimeUp()) {
                System.out.println("Time's up! Submitting your answers automatically.");
                break;
            }

            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }

            System.out.print("Your answer (1-4): ");
            int answer = scanner.nextInt();
            if (answer >= 1 && answer <= 4) {
                userAnswers[i] = answer;
            } else {
                System.out.println("Invalid answer. Skipping question.");
            }
        }

        submit();
    }

    private boolean isTimeUp() {
        return (System.currentTimeMillis() - startTime) >= duration;
    }

    private void submit() {
        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }

        System.out.println("Exam finished. Your score is: " + score + "/" + correctAnswers.length);
    }
}

public class task4 {
    private static User currentUser;
    private static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a demo user
        users.put("user1", new User("user1", "pass123"));

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Update Profile");
            System.out.println("3. Update Password");
            System.out.println("4. Start Exam");
            System.out.println("5. Logout");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    updateProfile(scanner);
                    break;
                case 3:
                    updatePassword(scanner);
                    break;
                case 4:
                    startExam(scanner);
                    break;
                case 5:
                    logout();
                    break;
                case 6:
                    System.out.println("Exiting. Thank you!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter user ID: ");
        String userId = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        User user = users.get(userId);
        if (user != null && user.login(password)) {
            currentUser = user;
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid user ID or password.");
        }
    }

    private static void updateProfile(Scanner scanner) {
        if (currentUser != null && currentUser.isLoggedIn()) {
            System.out.print("Enter new profile: ");
            scanner.nextLine();  // consume newline
            String newProfile = scanner.nextLine();
            currentUser.updateProfile(newProfile);
        } else {
            System.out.println("Please login first.");
        }
    }

    private static void updatePassword(Scanner scanner) {
        if (currentUser != null && currentUser.isLoggedIn()) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.next();
            currentUser.updatePassword(newPassword);
        } else {
            System.out.println("Please login first.");
        }
    }

    private static void startExam(Scanner scanner) {
        if (currentUser != null && currentUser.isLoggedIn()) {
            Exam exam = new Exam(3, 1); // 3 questions, 1 minute duration
            exam.start();
        } else {
            System.out.println("Please login first.");
        }
    }

    private static void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
            System.out.println("Logged out successfully.");
        } else {
            System.out.println("No user is logged in.");
        }
    }
}
