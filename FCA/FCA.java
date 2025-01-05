package TP;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FCA extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Get the observed values from the user
        List<Float> dataList = getObservedValuesFromUser();

        // Create a scene containing a bar chart and a table
        Scene barChartScene = createBarChartScene(dataList);
        TableView<ObservableList<String>> tableView = createTableView(dataList);

        // Combine the bar chart and table in a VBox layout
        VBox container = new VBox();
        container.getChildren().addAll(barChartScene.getRoot(), tableView);

        // Set up the primary stage
        configurePrimaryStage(primaryStage, new Scene(container, 800, 600));

        // Print the hypothesis test results to the console
        printResults(dataList);
    }

    private void configurePrimaryStage(Stage primaryStage, Scene scene) {
        primaryStage.setTitle("Hypothesis Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void printResults(List<Float> dataList) {
        // Print header
        System.out.println("============> Hypothesis Test performed by Houssam Bouagal using JavaFX <============");
        System.out.println("\n");

        // Calculate the sum of observed values
        System.out.println(" ============> Sum of Observed Data <============\t ");
        float total = dataList.stream().reduce(0f, Float::sum);
        System.out.printf("%.0f ", total);

        // Calculate the theoretical value
        System.out.println("\n");
        System.out.println(" ============> Theoretical Values <============\t ");
        float theoretical = total / dataList.size();
        System.out.printf("%.0f\t", theoretical);

        // Calculate chi-square values for each observed value
        System.out.println("\n");
        System.out.println(" ============> Chi-Square Calculation <============\t ");
        Object[] chiSquareValues = dataList.stream()
                .map(value -> (float) (Math.pow((value - theoretical), 2) / theoretical))
                .collect(Collectors.toList()).toArray();
        for (Object value : chiSquareValues) {
            System.out.printf("%.4f\t", value);
        }

        // Calculate the total chi-square value
        System.out.println("\n");
        System.out.println(" ============> Calculated Chi-Square Value (x_calculated) <============\t ");
        float x_calculated = dataList.stream().map(value -> (float) (Math.pow((value - theoretical), 2) / theoretical))
                .reduce(0f, Float::sum);
        System.out.printf("%.4f \n", x_calculated);

        // Calculate the degree of freedom
        System.out.println("\n");
        System.out.println(" ============> Degree of Freedom : <============\t ");
        int dof = dataList.size() - 1;
        System.out.printf("%d \n", dof);

        // Display critical chi-square value and hypothesis test result
        System.out.println("\n");
        System.out.println("The critical Chi-Square value is: \t ");
        float criticalValue = 7.8147f; // Example value for dof = 3
        System.out.printf("%.4f \n", criticalValue);
        if (x_calculated < criticalValue) {
            System.out.printf(" The independence hypothesis is not rejected.");
        } else {
            System.out.printf(" The null hypothesis is rejected, indicating a link between factors and choice.");
        }

        // Calculate and display the contingency coefficient
        System.out.println("\n");
        System.out.println(" ============> Contingency Coefficient : <============\t ");
        float contingencyCoefficient = (float) Math.sqrt(x_calculated / (100 + x_calculated));
        System.out.printf("%f \n", contingencyCoefficient);
    }

    private TableView<ObservableList<String>> createTableView(List<Float> dataList) {
        TableView<ObservableList<String>> tableView = new TableView<>();

        // Define table columns
        TableColumn<ObservableList<String>, String> successRateColumn = new TableColumn<>("Success Rate");
        TableColumn<ObservableList<String>, String> locationColumn = new TableColumn<>("Location");
        TableColumn<ObservableList<String>, String> coachColumn = new TableColumn<>("Coach");
        TableColumn<ObservableList<String>, String> scheduleColumn = new TableColumn<>("Schedule");

        // Populate table data
        ObservableList<String> data = FXCollections.observableArrayList(
                String.valueOf(dataList.get(0)),
                String.valueOf(dataList.get(1)),
                String.valueOf(dataList.get(2)),
                String.valueOf(dataList.get(3))
        );

        successRateColumn.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(0)));
        locationColumn.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(1)));
        coachColumn.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(2)));
        scheduleColumn.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue().get(3)));

        tableView.getColumns().addAll(successRateColumn, locationColumn, coachColumn, scheduleColumn);
        tableView.getItems().add(data);

        return tableView;
    }

    private Scene createBarChartScene(List<Float> dataList) {
        // Create axes and bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Comparison of Observed and Theoretical Values");

        // Populate chart with data
        XYChart.Series<String, Number> seriesObserved = new XYChart.Series<>();
        seriesObserved.setName("Observed");
        for (int i = 0; i < dataList.size(); i++) {
            seriesObserved.getData().add(new XYChart.Data<>(String.valueOf(i + 1), dataList.get(i)));
        }

        XYChart.Series<String, Number> seriesTheoretical = new XYChart.Series<>();
        seriesTheoretical.setName("Theoretical");
        float theoretical = dataList.stream().reduce(0f, Float::sum) / dataList.size();
        for (int i = 0; i < dataList.size(); i++) {
            seriesTheoretical.getData().add(new XYChart.Data<>(String.valueOf(i + 1), theoretical));
        }

        XYChart.Series<String, Number> seriesDifference = new XYChart.Series<>();
        seriesDifference.setName("Difference");
        for (int i = 0; i < dataList.size(); i++) {
            float difference = dataList.get(i) - theoretical;
            seriesDifference.getData().add(new XYChart.Data<>(String.valueOf(i + 1), difference));
        }

        barChart.getData().addAll(seriesObserved, seriesTheoretical, seriesDifference);

        return new Scene(new VBox(barChart), 600, 400);
    }

    private List<Float> getObservedValuesFromUser() {
        // Prompt user to enter observed values
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Observed Values");
        dialog.setHeaderText("Enter the observed values separated by commas:");
        dialog.setContentText("Values:");

        Optional<String> result = dialog.showAndWait();
        List<Float> dataList = new ArrayList<>();

        if (result.isPresent()) {
            String input = result.get();
            try (Stream<String> stream = Arrays.stream(input.split(","))) {
                dataList = stream.map(Float::parseFloat)
                        .filter(value -> value >= 0)
                        .collect(Collectors.toList());
                if (dataList.isEmpty()) {
                    printErrorDialog("Error: At least one non-negative number must be entered.");
                }
            } catch (NumberFormatException e) {
                printErrorDialog("Format error. Ensure you enter numbers separated by commas.");
            }
        }

        return dataList;
    }

    private void printErrorDialog(String errorMessage) {
        // Show an error dialog
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        Platform.exit();
    }
}
