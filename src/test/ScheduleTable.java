package test;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScheduleTable extends JFrame {

    private final Color clickedColor = Color.YELLOW;
    private final Set<Point> clickedCells = new HashSet<>();
    private final Map<Integer, Set<String>> selectedTimesPerDay = new HashMap<>();
    private String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

    public ScheduleTable() {
        // Set up the frame
        setTitle("Schedule Table");
        setSize(1200, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Data for days and times
        String[] times = new String[14];
        times[0] = "Day/Time";
        for (int i = 6; i <= 18; i++) {
            times[i - 5] = i + ":00 - " + (i + 1) + ":00";
        }

        DefaultTableModel model = new DefaultTableModel(null, times) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        for (String day : days) {
            model.addRow(new Object[] { day });
            selectedTimesPerDay.put(model.getRowCount() - 1, new HashSet<>());
        }

        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(30);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column > 0 && clickedCells.contains(new Point(row, column))) {
                    c.setBackground(clickedColor);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col > 0) {
                    Point point = new Point(row, col);
                    if (clickedCells.contains(point)) {
                        clickedCells.remove(point);
                        selectedTimesPerDay.get(row).remove(times[col]);
                    } else {
                        clickedCells.add(point);
                        selectedTimesPerDay.get(row).add(times[col]);
                    }
                    // Clear selections in other rows
                    for (final int[] r = new int[1]; r[0] < table.getRowCount(); r[0]++) {
                        if (r[0] != row) {
                            clickedCells.removeIf(p -> p.x == r[0]);
                            selectedTimesPerDay.get(r[0]).clear();
                        }
                    }
                    // Print and repaint as before
                    printSelectedTimesAndTotalHours(row);
                    table.repaint();
                }
            }
        });

        add(new JScrollPane(table));
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScheduleTable());
    }

    private int calculateTotalHours(Set<String> selectedTimes) {
        int totalHours = 0;
        for (String time : selectedTimes) {
            String[] parts = time.split(" - ");
            int startHour = Integer.parseInt(parts[0].split(":")[0]);
            int endHour = Integer.parseInt(parts[1].split(":")[0]);
            totalHours += endHour - startHour;
        }
        return totalHours;
    }

    private void printSelectedTimesAndTotalHours(int row) {
        Set<String> selectedTimes = selectedTimesPerDay.get(row);
        if (!selectedTimes.isEmpty()) {
            StringBuilder timesBuilder = new StringBuilder();
            timesBuilder.append("Selected times for ").append(days[row]).append(": [");
            String delimiter = "";
            for (String time : selectedTimes) {
                timesBuilder.append(delimiter).append(time);
                delimiter = ", ";
            }
            timesBuilder.append("]");
            System.out.println(timesBuilder);

            int totalHours = calculateTotalHours(selectedTimes);
            System.out.println("Total hours selected for " + days[row] + ": " + totalHours);
        }
    }
}
