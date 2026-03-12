import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    static class LoginEntry {
        LocalDateTime timestamp;
        String username;
        String status;

        LoginEntry(String timestamp, String username, String status) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.timestamp = LocalDateTime.parse(timestamp, formatter);
            this.username = username;
            this.status = status;
        }
    }

    public static void main(String[] args) {
        // Step 4: Default values
        String inputFile = "data/sample_log.csv";
        int threshold = 3;
        String outputFile = "data/suspicious_users.csv";

        // Override with CLI arguments
        if (args.length >= 1) inputFile = args[0];
        if (args.length >= 2) threshold = Integer.parseInt(args[1]);
        if (args.length >= 3) outputFile = args[2];

        List<LoginEntry> logins = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue; // skip malformed
                logins.add(new LoginEntry(parts[0], parts[1], parts[2]));
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Count failed logins
        Map<String, Integer> failedCounts = new HashMap<>();
        for (LoginEntry entry : logins) {
            if (entry.status.equalsIgnoreCase("failure")) {
                failedCounts.put(entry.username, failedCounts.getOrDefault(entry.username, 0) + 1);
            }
        }

        // Detect suspicious users
        List<String> suspiciousUsers = new ArrayList<>();
        for (String user : failedCounts.keySet()) {
            if (failedCounts.get(user) >= threshold) suspiciousUsers.add(user);
        }

        // Print suspicious users
        System.out.println("Suspicious Users Detected:");
        for (String user : suspiciousUsers) {
            System.out.println("User: " + user + ", Failed Attempts: " + failedCounts.get(user));
        }

        // Step 4: Save to CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            bw.write("username,failed_attempts\n");
            for (String user : suspiciousUsers) {
                bw.write(user + "," + failedCounts.get(user) + "\n");
            }
            System.out.println("Results saved to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}