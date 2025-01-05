#FCA JavaFX Application

## Overview
The **Factorial Correspondence Analysis Application** is a JavaFX-based project designed to perform hypothesis testing using chi-square calculations. It allows users to input observed values, visualize data through a bar chart, and calculate relevant statistical metrics such as chi-square, degrees of freedom, and contingency coefficients. Results are displayed in a table and printed to the console for detailed analysis.

---

## Features
- **User Input**: Enter observed values via a dialog box.
- **Data Visualization**: Compare observed and theoretical values in a bar chart.
- **Statistical Analysis**:
  - Calculates chi-square values and degrees of freedom.
  - Evaluates the null hypothesis based on a critical chi-square value.
  - Computes the contingency coefficient.
- **Tabular Display**: Shows processed data in a table.
- **Console Output**: Prints detailed calculation results to the console.

---

## Requirements
### Software
- Java Development Kit (JDK) 8 or higher.
- JavaFX SDK (if not bundled with the JDK).

### Hardware
- Compatible with any standard computer capable of running Java applications.

---

## Setup
### 1. Clone the Repository
```bash
git clone https://github.com/your-repo/FCA-javafx.git
cd FCA-javafx
```

### 2. Compile the Project
Ensure you have JavaFX properly set up and included in your environment.
```bash
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -d out TP/FCA.java
```

### 3. Run the Application
```bash
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp out TP.FCA
```

---

## Usage
### Running the Application
1. Launch the application.
2. Enter observed values separated by commas in the input dialog.
3. View the bar chart comparing observed and theoretical values.
4. Check the console for detailed results of the hypothesis test.
5. Review the table displaying the success rates and related details.

### Results Explanation
- **Chi-Square Calculation**: Determines whether to reject the null hypothesis.
- **Degrees of Freedom**: Number of independent values in the dataset.
- **Critical Value**: Predefined value to compare against the calculated chi-square.
- **Contingency Coefficient**: Measures the association strength.

---

## Example Input
**Observed Values**:
```
20, 30, 25, 35
```
**Console Output**:
```
============> Hypothesis Test performed by Houssem Bouagal using JavaFX <============

==========> Sum of Observed Data <============	
110

==========> Theoretical Values <============	
27	

==========> Chi-Square Calculation <============	
1.1852	0.3333	0.1481	1.5185	

==========> Calculated Chi-Square Value (x_calculated) <============	
3.1852

==========> Degree of Freedom  :  <============	
3

The critical Chi-Square value is:	
7.8147

The independence hypothesis is not rejected.

==========> Contingency Coefficient  :  <============	
0.1732
```

---

## Contributions
Contributions are welcome! If you have suggestions or encounter issues, feel free to open an issue or submit a pull request.

---

## Author
Developed by **Houssem Bouagal** using JavaFX.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

