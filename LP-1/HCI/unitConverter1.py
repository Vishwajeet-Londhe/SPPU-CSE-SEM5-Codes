#Unit Converter 1
import tkinter as tk

# Function to perform weight conversion
def convert_weight():
    try:
        input_value = float(entry.get())
        from_unit = from_units.get()
        to_unit = to_units.get()

        if from_unit == "Kilograms" and to_unit == "Pounds":
            result = input_value * 2.20462
        elif from_unit == "Pounds" and to_unit == "Kilograms":
            result = input_value / 2.20462
        elif from_unit == "Kilograms" and to_unit == "Grams":
            result = input_value * 1000
        elif from_unit == "Grams" and to_unit == "Kilograms":
            result = input_value / 1000
        elif from_unit == "Pounds" and to_unit == "Ounces":
            result = input_value * 16
        elif from_unit == "Ounces" and to_unit == "Pounds":
            result = input_value / 16
        else:
            result = input_value

        result_label.config(text=f"Result: {result} {to_unit}", fg="green")
    except ValueError:
        result_label.config(text="Invalid input", fg="red")

# Create the main application window
root = tk.Tk()
root.title("Weight Converter")
root.geometry("550x250")

# Entry widget for input
entry = tk.Entry(root, width=15, font=("Arial", 12), bg="lightyellow", fg="black")
entry.grid(row=0, column=0, padx=10, pady=10)

# Dropdown menus for unit selection
from_units = tk.StringVar()
from_units.set("Kilograms")
to_units = tk.StringVar()
to_units.set("Pounds")

from_units_menu = tk.OptionMenu(root, from_units, "Kilograms", "Pounds", "Grams", "Ounces")
to_units_menu = tk.OptionMenu(root, to_units, "Kilograms", "Pounds", "Grams", "Ounces")

from_units_menu.config(font=("Arial", 12))
to_units_menu.config(font=("Arial", 12))

from_units_menu.grid(row=0, column=1, padx=10, pady=10)
to_units_menu.grid(row=0, column=2, padx=10, pady=10)

# Convert button
convert_button = tk.Button(root, text="Convert", command=convert_weight, font=("Arial", 12), bg="blue", fg="white")
convert_button.grid(row=0, column=3, padx=10, pady=10)

# Result label
result_label = tk.Label(root, text="Result:", font=("Arial", 14))
result_label.grid(row=1, column=0, columnspan=4, padx=10, pady=10)

# Start the application
root.mainloop()