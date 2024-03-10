package test; // Use the same package as your MainPage 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate; // Or use an appropriate date/time library
import java.time.temporal.ChronoUnit;

// ... other imports if needed

public class AdminPage extends JFrame {

    DefaultListModel<String> userlistModel = new DefaultListModel<>();
    JList<String> userList = new JList<>(userlistModel);
    DefaultListModel<String> courtListModel = new DefaultListModel<>();
    JList<String> courtList = new JList<>(courtListModel);

    private long baseKey = 6606021420059L; // Default value

    public AdminPage() {
        displayUsers();
        displayCourts();
        loadBaseKey();
        setResizable(false);
        setTitle("Admin Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Or use whatever close behavior you need
        setBounds(100, 100, 600, 550); // Set size of the new window
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("What do you want to do?");
        lblNewLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 24));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(109, 11, 364, 53);
        getContentPane().add(lblNewLabel);

        JLabel lblManageUser = new JLabel("Manage User");
        lblManageUser.setHorizontalAlignment(SwingConstants.CENTER);
        lblManageUser.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        lblManageUser.setBounds(10, 52, 133, 53);
        getContentPane().add(lblManageUser);

        JLabel lblManageCourt = new JLabel("Manage Court");
        lblManageCourt.setHorizontalAlignment(SwingConstants.CENTER);
        lblManageCourt.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        lblManageCourt.setBounds(10, 95, 133, 53);
        getContentPane().add(lblManageCourt);

        JLabel lblUsersList = new JLabel("Users List");
        lblUsersList.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsersList.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        lblUsersList.setBounds(0, 135, 133, 53);
        getContentPane().add(lblUsersList);

        JLabel lblCourtList = new JLabel("Court List");
        lblCourtList.setHorizontalAlignment(SwingConstants.CENTER);
        lblCourtList.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 16));
        lblCourtList.setBounds(268, 134, 133, 53);
        getContentPane().add(lblCourtList);

        JButton addUser = new JButton("Add User");
        addUser.setBounds(156, 67, 97, 23);
        getContentPane().add(addUser);

        JButton remUser = new JButton("Remove User");
        remUser.setFont(new Font("Tahoma", Font.PLAIN, 9));
        remUser.setBounds(370, 67, 97, 23);
        getContentPane().add(remUser);

        JButton editUser = new JButton("Edit User");
        editUser.setBounds(263, 67, 97, 23);
        getContentPane().add(editUser);

        JButton findUser = new JButton("Find");
        findUser.setBounds(477, 67, 97, 23);
        getContentPane().add(findUser);

        JButton addCourt = new JButton("Add Court");
        addCourt.setBounds(156, 110, 97, 23);
        getContentPane().add(addCourt);

        JButton remCourt = new JButton("Remove Court");
        remCourt.setFont(new Font("Tahoma", Font.PLAIN, 9));
        remCourt.setBounds(370, 110, 97, 23);
        getContentPane().add(remCourt);

        JButton editCourt = new JButton("Edit Court");
        editCourt.setBounds(263, 110, 97, 23);
        getContentPane().add(editCourt);

        JButton findCourt = new JButton("Find");
        findCourt.setBounds(477, 110, 97, 23);
        getContentPane().add(findCourt);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(250, 337, 97, 25);
        getContentPane().add(refreshButton);

        JButton reportButton = new JButton("Show Report");
        reportButton.setBounds(360, 337, 120, 25); // Adjust the position and size as needed
        getContentPane().add(reportButton);

        userList.setBounds(31, 243, 324, 148);
        JScrollPane userlistPane = new JScrollPane(userList);
        userlistPane.setBounds(32, 174, 258, 148);
        getContentPane().add(userlistPane);

        courtList.setBounds(22, 243, 324, 148);
        JScrollPane courtlistPane = new JScrollPane(courtList);
        courtlistPane.setBounds(300, 174, 258, 148);
        getContentPane().add(courtlistPane);

        // Action listener list

        // User management
        addUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("Enter username");
                String surname = JOptionPane.showInputDialog("Enter surname");

                savetoFile(username, surname, "user.txt");

                displayUsers();
            }
        });

        remUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = userList.getSelectedIndex();
                if (selectedIndex != -1
                        && showConfirmationDialog("Are you sure you want to delete this user?", "Confirm Deletion")) {
                    removeItemFromList(userlistModel, "user.txt", selectedIndex);
                }
            }
        });

        editUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = userList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedUser = userlistModel.getElementAt(selectedIndex);
                    editUserData(selectedUser); // Call a new method to handle editing
                }
            }
        });

        findUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchUser();
            }
        });
        // End of user management

        // Court management
        addCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courtName = showInputDialog("Enter court name", "Input Court Name", null);
                String normalDayPrice = showInputDialog("Enter price for normal days", "Input Price", null);
                String weekendPrice = showInputDialog("Enter price for weekends", "Input Price", null);
                if (courtName != null && !courtName.trim().isEmpty() &&
                        normalDayPrice != null && !normalDayPrice.trim().isEmpty() &&
                        weekendPrice != null && !weekendPrice.trim().isEmpty()) {
                    // Ensure that normalDayPrice and weekendPrice are valid numbers (optional
                    // validation)
                    try {
                        Double.parseDouble(normalDayPrice);
                        Double.parseDouble(weekendPrice);
                        String courtDetails = courtName + ", " + normalDayPrice + ", " + weekendPrice;
                        saveCourtToFile(courtDetails, "courtList.txt");
                        displayCourts();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Normal day price and weekend price must be valid numbers.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Court name, normal day price, and weekend price cannot be empty.");
                }
            }
        });

        remCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = courtList.getSelectedIndex();
                if (selectedIndex != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this court?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        removeItemFromList(courtListModel, "courtList.txt", selectedIndex);
                    }
                }
            }
        });

        editCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int courtselectedIndex = courtList.getSelectedIndex();
                if (courtselectedIndex != -1) {
                    String selectedCourt = courtListModel.getElementAt(courtselectedIndex);
                    editCourtData(selectedCourt); // Call a new method to handle editing
                }
            }
        });

        findCourt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = JOptionPane.showInputDialog("Enter search term:");
                if (searchTerm == null || searchTerm.trim().isEmpty()) {
                    // Handle cancel action or empty input
                    return;
                }

                boolean found = false;
                String foundDetails = "";

                // First pass: Look for an exact match in the court names
                for (int i = 0; i < courtListModel.getSize(); i++) {
                    String courtDetails = courtListModel.getElementAt(i);
                    String[] details = courtDetails.split(", ");
                    if (details[0].contains(searchTerm)) {
                        found = true;
                        foundDetails = courtDetails;
                        break; // Stop searching after finding the first match in names
                    }
                }

                // Second pass: If no name matches, look for a match in prices
                if (!found) {
                    for (int i = 0; i < courtListModel.getSize(); i++) {
                        String courtDetails = courtListModel.getElementAt(i);
                        String[] details = courtDetails.split(", ");
                        if (details[1].contains(searchTerm) || details[2].contains(searchTerm)) {
                            found = true;
                            foundDetails = courtDetails;
                            break; // Stop searching after finding the first match in prices
                        }
                    }
                }

                if (found) {
                    // Court found
                    String[] details = foundDetails.split(", ");
                    String message = "Court found: " + details[0] + "\nNormalday Price: " + details[1]
                            + "\nWeekend Price: " + details[2];
                    JOptionPane.showMessageDialog(null, message);
                } else {
                    JOptionPane.showMessageDialog(null, "No matching court found.");
                }
            }
        });

        // End of court management

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayUsers();
                displayCourts();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowReport reportDialog = new ShowReport(AdminPage.this);
                reportDialog.setLocationRelativeTo(null); // Center on screen
                reportDialog.setVisible(true);
            }
        });

        // End of action listener list

    } // End of constructor

    // User management methods

    private void savetoFile(String username, String surname, String fileName) {
        LocalDate currentDate = LocalDate.now();
        String primaryKey = generatePrimaryKey();
        String userData = primaryKey + ", " + username + ", " + surname + ", " + currentDate + ", " + 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(userData);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void editUserData(String selectedUser) {
        String[] data = selectedUser.split(", ");
        String primaryKey = data[0]; // Preserve the primary key
        String oldUsername = data[1];
        String oldSurname = data[2];

        String newUsername = JOptionPane.showInputDialog("Enter new username", oldUsername);
        String newSurname = JOptionPane.showInputDialog("Enter new surname", oldSurname);

        // Ensure the new values aren't empty; if they are, use the old values
        if (newUsername == null || newUsername.isEmpty()) {
            newUsername = oldUsername;
        }
        if (newSurname == null || newSurname.isEmpty()) {
            newSurname = oldSurname;
        }

        // Construct the updated user string; primary key remains unaltered
        String updatedUser = primaryKey + ", " + newUsername + ", " + newSurname + ", " + data[3] + ", " + data[4];

        // Update the file
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(selectedUser)) {
                    lines.set(i, updatedUser);
                    break; // Update only the first matching entry
                }
            }
            Files.write(Paths.get("user.txt"), lines);
            displayUsers(); // Refresh the list
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void searchUser() {
        String searchInput = JOptionPane.showInputDialog("Enter ID or name to search:");
        if (searchInput == null || searchInput.trim().isEmpty()) {
            // Handle cancel action or empty input
            return;
        }

        // Iterate through the user list to find matching users
        for (int i = 0; i < userlistModel.getSize(); i++) {
            String userDetails = userlistModel.getElementAt(i);
            String[] details = userDetails.split(", ");
            // Check if the searchInput matches the primary key or username
            if (details[0].equals(searchInput) || details[1].equalsIgnoreCase(searchInput)) {
                // User found
                String primaryKey = details[0];
                String username = details[1];
                String surname = details[2];
                String dateRegistry = details[3];
                String promotion = details[4].equals("1") ? "Free 7 Days" : "20% Discount";

                // Display user details in a JOptionPane
                String message = "ID: " + primaryKey + "\nName: " + username + "\nSurname: " + surname
                        + "\nDate-Registry: " + dateRegistry + "\nPromotion: " + promotion;
                Object[] options = { "Edit", "Delete", "Cancel" };
                int choice = JOptionPane.showOptionDialog(null, message, "User Details",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);

                if (choice == JOptionPane.YES_OPTION) {
                    // Edit option selected
                    editUserData(userDetails);
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Delete option selected, ask for confirmation
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        userlistModel.removeElement(userDetails);
                        try {
                            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
                            lines.removeIf(line -> line.contains(userDetails));
                            Files.write(Paths.get("user.txt"), lines);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                return; // Exit after processing the first match
            }
        }

        JOptionPane.showMessageDialog(null, "No matching user found.");
    }

    private void purgeOldUsers() {
        LocalDate today = LocalDate.now();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] details = line.split(", ");
                LocalDate dateRegistry = LocalDate.parse(details[3]);
                long yearsBetween = ChronoUnit.YEARS.between(dateRegistry, today);

                if (yearsBetween <= 1) {
                    updatedLines.add(line); // Keep the user if they have been in the system for 1 year or less
                }
            }

            // Overwrite the user.txt file with the updated lines
            Files.write(Paths.get("user.txt"), updatedLines);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateUserPromotions() {
        LocalDate today = LocalDate.now();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] details = line.split(", ");
                LocalDate dateRegistry = LocalDate.parse(details[3]);
                long daysBetween = ChronoUnit.DAYS.between(dateRegistry, today);

                if (daysBetween > 7) {
                    // Change the promotion status to "0" if the registration was more than 7 days
                    // ago
                    details[4] = "0";
                }else{
                    details[4] = "1";
                }
                // Reconstruct the line with updated details
                String updatedLine = String.join(", ", details);
                updatedLines.add(updatedLine);
            }

            // Overwrite the user.txt file with the updated lines
            Files.write(Paths.get("user.txt"), updatedLines);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void displayUsers() {
        purgeOldUsers(); // Call the method to purge old users before displaying the list
        updateUserPromotions();
        userlistModel.clear();
        try {
            List<String> lines = Files.readAllLines(Paths.get("user.txt"));
            for (String line : lines) {
                userlistModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            userlistModel.addElement("Error reading file");
        }
    }

    // end of user management methods

    // Court management methods
    private void saveCourtToFile(String courtDetails, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(courtDetails);
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void editCourtData(String selectedCourt) {
        String[] data = selectedCourt.split(", ");
        String oldCourtName = data[0];
        String oldNormalDayPrice = data[1];
        String oldWeekendPrice = data[2];

        String newCourtName = JOptionPane.showInputDialog("Enter new court name", oldCourtName);
        String newNormalDayPrice = JOptionPane.showInputDialog("Enter new normal day price", oldNormalDayPrice);
        String newWeekendPrice = JOptionPane.showInputDialog("Enter new weekend price", oldWeekendPrice);

        // Validate the input to ensure that prices are numbers
        try {
            Double.parseDouble(newNormalDayPrice);
            Double.parseDouble(newWeekendPrice);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Normal day price and weekend price must be valid numbers.");
            return;
        }

        if (newCourtName == null || newCourtName.isEmpty()) {
            newCourtName = oldCourtName;
        }
        if (newNormalDayPrice == null || newNormalDayPrice.isEmpty()) {
            newNormalDayPrice = oldNormalDayPrice;
        }
        if (newWeekendPrice == null || newWeekendPrice.isEmpty()) {
            newWeekendPrice = oldWeekendPrice;
        }

        String updatedCourt = newCourtName + ", " + newNormalDayPrice + ", " + newWeekendPrice;

        // Update the file
        try {
            List<String> lines = Files.readAllLines(Paths.get("courtList.txt"));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals(selectedCourt)) {
                    lines.set(i, updatedCourt);
                    break; // Update only the first matching entry
                }
            }
            Files.write(Paths.get("courtList.txt"), lines);
            displayCourts(); // Refresh the list
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void displayCourts() {
        try {
            courtListModel.clear();
            List<String> lines = Files.readAllLines(Paths.get("courtList.txt"));

            for (String line : lines) {
                courtListModel.addElement(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            courtListModel.addElement("Error reading file");
        }
    }
    // end of court management methods

    // Generate a unique primary key

    private void loadBaseKey() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("lastkey.txt"));
            if (!lines.isEmpty()) {
                baseKey = Long.parseLong(lines.get(0));
            }
        } catch (IOException ex) {
            System.err.println("Error loading last key, using default.");
        }
    }

    private void saveBaseKey() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lastkey.txt"))) {
            writer.write(String.valueOf(baseKey));
        } catch (IOException ex) {
            System.err.println("Error saving last key.");
        }
    }

    private String generatePrimaryKey() {

        // Increment on each call
        long currentKey = baseKey++;

        String currentKeyStr = String.valueOf(currentKey);

        // Pad with leading zeros if needed
        while (currentKeyStr.length() < 13) {
            currentKeyStr = "0" + currentKeyStr;
        }

        saveBaseKey();

        return currentKeyStr;
    }

    private List<String> readFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeFile(String filePath, List<String> lines) {
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeItemFromList(DefaultListModel<String> listModel, String filePath, int index) {
        if (index != -1) {
            String selectedItem = listModel.remove(index);
            List<String> lines = readFile(filePath);
            lines.removeIf(line -> line.contains(selectedItem));
            writeFile(filePath, lines);
        }
    }

    private String showInputDialog(String message, String title, String defaultValue) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
            if (input == null || input.trim().isEmpty()) {
                if (defaultValue != null) {
                    return defaultValue;
                }
                JOptionPane.showMessageDialog(null, "Input cannot be empty. Please try again.");
            } else {
                return input;
            }
        }
    }

    private boolean showConfirmationDialog(String message, String title) {
        int choice = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return choice == JOptionPane.YES_OPTION;
    }

}