package com.test.familybudget.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.test.familybudget.model.Transaction;
import com.test.familybudget.model.Category;
import com.test.familybudget.database.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private TableColumn<Transaction, String> categoryColumn;
    @FXML
    private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML
    private TableColumn<Transaction, String> descriptionColumn;
    
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button manageCategoriesButton;

    @FXML
    private void initialize() {
        setupTableColumns();
        loadCategories();
        loadTransactions();
        setupEventHandlers();
    }

    private void setupTableColumns() {
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void loadCategories() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categories")) {
            
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type")
                ));
            }
            categoryComboBox.getItems().setAll(categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTransactions() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transactions")) {
            
            List<Transaction> transactions = new ArrayList<>();
            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = getCategoryNameById(categoryId);

                transactions.add(new Transaction(
                    rs.getInt("id"),
                    rs.getDouble("amount"),
                    categoryId,
                    categoryName,
                    LocalDate.parse(rs.getString("date")),
                    rs.getString("description")
                ));
            }
            transactionTable.getItems().setAll(transactions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCategoryNameById(int categoryId) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT name FROM categories WHERE id = ?")) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";  // Возвращаем пустую строку, если категория не найдена
    }

    private void setupEventHandlers() {
        addButton.setOnAction(event -> addTransaction());
        deleteButton.setOnAction(event -> deleteTransaction());
        manageCategoriesButton.setOnAction(event -> openCategoryWindow());
    }

    @FXML
    public void addTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            Category category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();
            String description = descriptionArea.getText();

            if (category == null || date == null) {
                showAlert("Ошибка", "Пожалуйста, заполните все поля");
                return;
            }

            try (Connection conn = Database.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO transactions (amount, category_id, date, description) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setDouble(1, amount);
                stmt.setInt(2, category.getId());
                stmt.setString(3, date.toString());
                stmt.setString(4, description);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        Transaction transaction = new Transaction(
                            rs.getInt(1),
                            amount,
                            category.getId(),
                            category.getName(),
                            date,
                            description
                        );
                        transactionTable.getItems().add(transaction);
                    }
                }
            }
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Пожалуйста, введите корректную сумму");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось добавить транзакцию");
        }
    }

    @FXML
    public void deleteTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction == null) {
            showAlert("Ошибка", "Пожалуйста, выберите транзакцию для удаления");
            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM transactions WHERE id = ?")) {
            
            stmt.setInt(1, selectedTransaction.getId());
            stmt.executeUpdate();
            transactionTable.getItems().remove(selectedTransaction);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось удалить транзакцию");
        }
    }

    @FXML
    public void openCategoryWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/category.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Управление категориями");
            stage.setScene(new Scene(root));

            stage.setOnHiding(e -> loadCategories());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть окно управления категориями");
        }
    }

    private void clearFields() {
        amountField.clear();
        categoryComboBox.setValue(null);
        datePicker.setValue(null);
        descriptionArea.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 