package CodSoft;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ATM {
    private UserAccount account;

    public ATM(UserAccount account) {
        this.account = account;
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }

    public boolean deposit(double amount) {
        return account.deposit(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public boolean hasInitialDeposit() {
        return account.getBalance() > 0;
    }
}

public class ATMInterface {
    private ATM atm;
    private JFrame frame;
    private JTextField amountField;
    private JLabel balanceLabel;

    public ATMInterface(ATM atm) {
        this.atm = atm;
        createUI();
    }

    private void createUI() {
        frame = new JFrame("ATM Machine");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setBounds(50, 50, 100, 30);
        frame.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(150, 50, 150, 30);
        frame.add(amountField);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(50, 100, 100, 30);
        frame.add(withdrawButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(200, 100, 100, 30);
        frame.add(depositButton);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setBounds(50, 150, 250, 30);
        frame.add(balanceButton);

        balanceLabel = new JLabel("Balance: ₹" + atm.checkBalance());
        balanceLabel.setBounds(50, 200, 250, 30);
        frame.add(balanceLabel);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (atm.hasInitialDeposit()) {
                    performWithdraw();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please make an initial deposit before withdrawing.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (atm.hasInitialDeposit()) {
                    checkBalance();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please make an initial deposit before checking balance.");
                }
            }
        });

        frame.setVisible(true);
    }

    private void performWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.withdraw(amount)) {
                JOptionPane.showMessageDialog(frame, "Withdrawal successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Insufficient funds or invalid amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount.");
        }
        updateBalance();
    }

    private void performDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.deposit(amount)) {
                JOptionPane.showMessageDialog(frame, "Deposit successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount.");
        }
        updateBalance();
    }

    private void checkBalance() {
        updateBalance();
        JOptionPane.showMessageDialog(frame, "Your balance is ₹" + atm.checkBalance());
    }

    private void updateBalance() {
        balanceLabel.setText("Balance: ₹" + atm.checkBalance());
    }

    public static void main(String[] args) {
        double initialBalance = 0;

        // Loop until a valid initial balance is entered
        while (true) {
            String initialBalanceStr = JOptionPane.showInputDialog("Enter the initial balance (must be ₹100 or greater):");

            if (initialBalanceStr == null) {
                JOptionPane.showMessageDialog(null, "Input canceled. Exiting.");
                System.exit(0);
            }

            try {
                initialBalance = Double.parseDouble(initialBalanceStr);

                if (initialBalance >= 100) {
                    break; // Exit loop if valid input
                } else {
                    JOptionPane.showMessageDialog(null, "Initial balance must be ₹100 or greater than ₹100. Please enter a valid amount.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
            }
        }

        UserAccount account = new UserAccount(initialBalance);
        ATM atm = new ATM(account);
        new ATMInterface(atm);
    }
}

class UserAccount {
    private double balance;

    public UserAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
