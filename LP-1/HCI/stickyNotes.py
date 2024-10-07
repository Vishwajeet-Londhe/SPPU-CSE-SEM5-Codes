#Sticky Notes
import tkinter as tk
from tkinter import messagebox, simpledialog

def save_note():
    note_text = text_entry.get("1.0", "end-1c")
    if note_text.strip():
        with open("notes.txt", "a") as file:
            file.write(note_text + "\n")
        text_entry.delete("1.0", tk.END)
        messagebox.showinfo("Note Saved", "Your note has been saved.")
    else:
        messagebox.showwarning("Empty Note", "Please enter some text before saving.")

def view_notes():
    try:
        with open("notes.txt", "r") as file:
            notes = file.read()
        notes_window = tk.Toplevel(root)
        notes_window.title("Notes")
        notes_text = tk.Text(notes_window, bg="lightyellow", fg="blue")
        notes_text.insert(tk.END, notes)
        notes_text.pack()
    except FileNotFoundError:
        messagebox.showwarning("No Notes Found", "There are no notes to display.")

def clear_notes():
    confirmed = messagebox.askyesno("Confirm", "Are you sure you want to clear all notes?")
    if confirmed:
        with open("notes.txt", "w") as file:
            file.truncate(0)
        messagebox.showinfo("Notes Cleared", "All notes have been cleared.")

def open_specific_note():
    try:
        with open("notes.txt", "r") as file:
            notes = file.readlines()
        
        note_index = simpledialog.askinteger("Open Note", "Enter the note number you want to open:", minvalue=1, maxvalue=len(notes))
        
        if note_index:
            note_index -= 1  # Adjust to 0-based index
            note = notes[note_index]
            notes_window = tk.Toplevel(root)
            notes_window.title(f"Note {note_index + 1}")
            notes_text = tk.Text(notes_window, bg="lightyellow", fg="blue")
            notes_text.insert(tk.END, note)
            notes_text.pack()
    except FileNotFoundError:
        messagebox.showwarning("No Notes Found", "There are no notes to display.")

root = tk.Tk()
root.title("Note-taking App")

# Text Entry
text_entry = tk.Text(root, height=10, width=40, bg="lightblue", fg="black")
text_entry.pack(pady=10)

# Buttons
save_button = tk.Button(root, text="Save Note", command=save_note, bg="green", fg="white")
view_button = tk.Button(root, text="View Notes", command=view_notes, bg="orange", fg="white")
clear_button = tk.Button(root, text="Clear Notes", command=clear_notes, bg="red", fg="white")
open_button = tk.Button(root, text="Open Note", command=open_specific_note, bg="purple", fg="white")

save_button.pack()
view_button.pack()
clear_button.pack()
open_button.pack()

root.mainloop()
