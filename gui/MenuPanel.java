package gui;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MenuPanel extends JPanel implements ItemListener {
    private String x_bound;
    private String y_bound;
    private int previousScore;
    private int difficulty;
    private MenuFrame frame;
    private JTextField xField;
    private JTextField yField;
    private JComboBox<String> combo;
    public MenuPanel(MenuFrame frame, int prev) {
        this.difficulty = 0;
        this.frame = frame;
        this.previousScore = prev;
        this.x_bound = "";
        this.y_bound = "";
        this.setLayout(new GridLayout(4, 1));
        this.add(xPanel());
        this.add(yPanel());
        this.add(difficultySelector());
        this.add(addBtn());

    }

    private JPanel xPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel xLabel = new JLabel("X Bound:");
        xLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(xLabel);
        xField = new JTextField();
        panel.add(xField);

        return panel;
    }

    private JPanel yPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel yLabel = new JLabel("X Bound:");
        yLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(yLabel);
        yField = new JTextField();
        panel.add(yField);

        return panel;
    }

    private JPanel difficultySelector() {
        JPanel panel = new JPanel(new GridLayout(1, 2));

        String[] options = {"Easy", "Normal", "Hard"};

        combo = new JComboBox<String>(options);

        combo.addItemListener(this);

        panel.add(new JLabel("Choose a difficulty: "));
        panel.add(combo);

        return panel;
    }

    private JButton addBtn() {
        JButton btn = new JButton("Start");
        if (previousScore != -1) {
            btn = new JButton("Start | Previous Score: " + previousScore);
        }
        btn.addActionListener(new ChangeFrameListener(this.frame));
        return btn;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(combo)) {
            this.difficulty = combo.getSelectedIndex();
        }
    }


    private class ChangeFrameListener implements ActionListener {
        private MenuFrame frame;
        public ChangeFrameListener(MenuFrame f) {
            this.frame = f;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            x_bound = xField.getText();
            y_bound = yField.getText();
            if (!isNumber(x_bound)) {
                JOptionPane.showMessageDialog(frame, "X should be a number!");
            } else if (!isNumber(y_bound)) {
                JOptionPane.showMessageDialog(frame, "Y should be a number!");
            } else {
                frame.fm.menuFrame.setVisible(false);
                frame.fm.snakeFrame = new SnakeFrame(this.frame.fm, Integer.parseInt(x_bound), Integer.parseInt(y_bound), difficulty);
            }
        }
        
    }

    private static boolean isNumber(String s) {
        boolean number = true;
        if (s.length() < 1) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                number = false;
            }
        }
        return number;
    }
}
