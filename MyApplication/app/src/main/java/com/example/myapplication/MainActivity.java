package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private Spinner conversionTypeSpinner;
    private Spinner sourceUnitSpinner;
    private Spinner destinationUnitSpinner;
    private EditText sourceValueEditText;
    private Button convertButton;
    private TextView convertedValueTextView;

    // Define conversion map
    private Map<String, String[]> conversionMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Initialize UI components
        conversionTypeSpinner = findViewById(R.id.conversion_type_spinner);
        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        destinationUnitSpinner = findViewById(R.id.destination_unit_spinner);
        sourceValueEditText = findViewById(R.id.source_value_edittext);
        convertButton = findViewById(R.id.convert_button);
        convertedValueTextView = findViewById(R.id.converted_value_textview);

        // Initialize conversion map
        conversionMap = new HashMap<>();
        conversionMap.put("Length", new String[]{"Meter", "Centimeter", "Inch"});
        conversionMap.put("Weight", new String[]{"Kilogram", "Gram", "Pound"});
        conversionMap.put("Temperature", new String[]{"Celsius", "Fahrenheit", "Kelvin"});

        // Add more conversion types and their units as needed

        // Populate conversion type spinner
        ArrayAdapter<CharSequence> conversionTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types_array, android.R.layout.simple_spinner_item);
        conversionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(conversionTypeAdapter);

        // Set listener for conversion type spinner
        conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedConversionType = parent.getSelectedItem().toString();
                updateUnitSpinners(selectedConversionType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set listener for convert button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }
    private void updateUnitSpinners(String conversionType) {
        String[] units = conversionMap.get(conversionType);
        if (units != null) {
            ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, units);
            unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sourceUnitSpinner.setAdapter(unitAdapter);
            destinationUnitSpinner.setAdapter(unitAdapter);
        }
    }

    private void performConversion() {
        // Get conversion details
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();
        double valueToConvert = Double.parseDouble(sourceValueEditText.getText().toString());

        // Perform conversion based on the selected units and value
        double convertedValue = calculateConversion(sourceUnit, destinationUnit, valueToConvert);

        // Display the converted value
        convertedValueTextView.setText(String.valueOf(convertedValue));
    }
    private double calculateConversion(String sourceUnit, String destinationUnit, double valueToConvert) {
        double convertedValue = valueToConvert;

        // Length conversions
        if (sourceUnit.equals("Meter") && destinationUnit.equals("Centimeter")) {
            convertedValue *= 100; // 1 meter = 100 centimeters
        } else if (sourceUnit.equals("Centimeter") && destinationUnit.equals("Meter")) {
            convertedValue /= 100; // 1 centimeter = 0.01 meter
        } else if (sourceUnit.equals("Meter") && destinationUnit.equals("Inch")) {
            convertedValue *= 39.3701; // 1 meter = 39.3701 inches
        } else if (sourceUnit.equals("Inch") && destinationUnit.equals("Meter")) {
            convertedValue /= 39.3701; // 1 inch = 0.0254 meter
        } else if (sourceUnit.equals("Centimeter") && destinationUnit.equals("Inch")) {
            convertedValue *= 0.393701; // 1 centimeter = 0.393701 inches
        } else if (sourceUnit.equals("Inch") && destinationUnit.equals("Centimeter")) {
            convertedValue *= 2.54; // 1 inch = 2.54 centimeters
        }


        // Weight conversions
        else if (sourceUnit.equals("Kilogram") && destinationUnit.equals("Gram")) {
            convertedValue *= 1000; // 1 kilogram = 1000 grams
        } else if (sourceUnit.equals("Gram") && destinationUnit.equals("Kilogram")) {
            convertedValue /= 1000; // 1 gram = 0.001 kilogram
        } else if (sourceUnit.equals("Kilogram") && destinationUnit.equals("Pound")) {
            convertedValue *= 2.20462; // 1 kilogram = 2.20462 pounds
        } else if (sourceUnit.equals("Pound") && destinationUnit.equals("Kilogram")) {
            convertedValue /= 2.20462; // 1 pound = 0.453592 kilogram
        } else if (sourceUnit.equals("Gram") && destinationUnit.equals("Pound")) {
            convertedValue *= 0.00220462; // 1 gram = 0.00220462 pounds
        } else if (sourceUnit.equals("Pound") && destinationUnit.equals("Gram")) {
            convertedValue /= 0.00220462; // 1 pound = 453.592 grams
        }


        // Temperature conversions
        else if (sourceUnit.equals("Celsius") && destinationUnit.equals("Fahrenheit")) {
            convertedValue = (valueToConvert * 1.8) + 32; // Celsius to Fahrenheit conversion formula
        } else if (sourceUnit.equals("Fahrenheit") && destinationUnit.equals("Celsius")) {
            convertedValue = (valueToConvert - 32) / 1.8; // Fahrenheit to Celsius conversion formula
        } else if (sourceUnit.equals("Celsius") && destinationUnit.equals("Kelvin")) {
            convertedValue = valueToConvert + 273.15; // Celsius to Kelvin conversion formula
        } else if (sourceUnit.equals("Kelvin") && destinationUnit.equals("Celsius")) {
            convertedValue = valueToConvert - 273.15; // Kelvin to Celsius conversion formula
        } else if (sourceUnit.equals("Fahrenheit") && destinationUnit.equals("Kelvin")) {
            convertedValue = (valueToConvert + 459.67) * 5/9; // Fahrenheit to Kelvin conversion formula
        } else if (sourceUnit.equals("Kelvin") && destinationUnit.equals("Fahrenheit")) {
            convertedValue = (valueToConvert * 9/5) - 459.67; // Kelvin to Fahrenheit conversion formula
        }


        return convertedValue;
    }



}

