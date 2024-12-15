import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class CurrencyConverter extends Frame {
    // Data storage for exchange rates
    private final HashMap<String, Double> exchangeRates;

    // Components
    private TextField amountField;
    private Choice sourceCurrency;
    private Choice targetCurrency;
    private Label resultLabel;

    public CurrencyConverter() {
        exchangeRates = new HashMap<>();
        initializeExchangeRates();

        // GUI Setup
        setupUI();

        // Closing action
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    // Initialize some predefined exchange rates
    private void initializeExchangeRates() {
        exchangeRates.put("USD", 1.0); // Base currency
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("INR", 82.67);
        exchangeRates.put("GBP", 0.78);
        exchangeRates.put("JPY", 130.5);
    }

    // Create GUI
    private void setupUI() {
        setTitle("Currency Converter");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        Label amountLabel = new Label("Amount:");
        amountField = new TextField();

        Label sourceLabel = new Label("From Currency:");
        sourceCurrency = new Choice();
        for (String currency : exchangeRates.keySet()) {
            sourceCurrency.add(currency);
        }

        Label targetLabel = new Label("To Currency:");
        targetCurrency = new Choice();
        for (String currency : exchangeRates.keySet()) {
            targetCurrency.add(currency);
        }

        Button convertButton = new Button("Convert");
        resultLabel = new Label("Converted Amount: ");

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Add components to the frame
        add(amountLabel);
        add(amountField);
        add(sourceLabel);
        add(sourceCurrency);
        add(targetLabel);
        add(targetCurrency);
        add(new Label()); // Placeholder
        add(convertButton);
        add(new Label()); // Placeholder
        add(resultLabel);
    }

    // Perform currency conversion
    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String from = sourceCurrency.getSelectedItem();
            String to = targetCurrency.getSelectedItem();

            if (from.equals(to)) {
                showErrorDialog("Source and target currencies must be different.");
                return;
            }

            double convertedAmount = calculateConversion(amount, from, to);
            resultLabel.setText("Converted Amount: " + String.format("%.2f", convertedAmount));
        } catch (NumberFormatException ex) {
            showErrorDialog("Please enter a valid numeric amount.");
        }
    }

    // Calculate conversion
    private double calculateConversion(double amount, String from, String to) {
        double fromRate = exchangeRates.get(from);
        double toRate = exchangeRates.get(to);
        return amount * (toRate / fromRate);
    }

    // Display error dialog
    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog(this, "Error", true);
        errorDialog.setSize(300, 150);
        errorDialog.setLayout(new FlowLayout());

        Label errorMessage = new Label(message);
        Button okButton = new Button("OK");

        okButton.addActionListener(e -> errorDialog.dispose());

        errorDialog.add(errorMessage);
        errorDialog.add(okButton);
        errorDialog.setVisible(true);
    }

    // Update exchange rates dynamically (future use or manual updates)
    public void updateExchangeRates(String currency, double rate) {
        exchangeRates.put(currency, rate);
    }

    // Main entry point
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.setVisible(true);
    }
}
