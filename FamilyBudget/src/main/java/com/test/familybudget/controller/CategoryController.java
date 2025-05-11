package com.test.familybudget.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.test.familybudget.model.Category;
import com.test.familybudget.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryController {
    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, String> nameColumn;
    @FXML
    private TableColumn<Category, String> typeColumn;
    
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        setupTableColumns();
        setupTypeComboBox();
        loadCategories();
        setupEventHandlers();
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void setupTypeComboBox() {
        typeComboBox.getItems().addAll("Доход", "Расход");
    }

    private void loadCategories() {
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
            categoryTable.getItems().setAll(categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupEventHandlers() {
        addButton.setOnAction(event -> addCategory());
        deleteButton.setOnAction(event -> deleteCategory());
    }

    @FXML
    public void addCategory() {
        String name = nameField.getText();
        String type = typeComboBox.getValue();

        if (name.isEmpty() || type == null) {
            showAlert("Ошибка", "Пожалуйста, заполните все поля");
            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO categories (name, type) VALUES (?, ?)")) {
            
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.executeUpdate();
            
            loadCategories();

            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось добавить категорию");
        }
    }

    @FXML
    public void deleteCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Ошибка", "Выберите категорию для удаления");
            return;
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM categories WHERE id = ?")) {

            stmt.setInt(1, selected.getId());
            stmt.executeUpdate();
            loadCategories();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось удалить категорию");
        }
    }

    private void clearFields() {
        nameField.clear();
        typeComboBox.setValue(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
} 