#Color Match Game
import tkinter as tk
import tkinter.messagebox
import random

class ColorMatchGame:
    def __init__(self, master):
        self.master = master
        self.master.title("Color Match Game")
        self.score = 0
        self.wrong_attempts = 0
        self.colors = ["red", "green", "blue", "yellow", "purple", "orange"]
        
        self.label = tk.Label(self.master, text="Match the color:")
        self.label.pack()
        
        self.score_label = tk.Label(self.master, text=f"Score: {self.score}", font=("Helvetica", 14))
        self.score_label.pack()
        
        self.attempts_label = tk.Label(self.master, text=f"Wrong Attempts: {self.wrong_attempts}", font=("Helvetica", 14))
        self.attempts_label.pack()

        self.match_label = tk.Label(self.master, text="", font=("Helvetica", 24))
        self.match_label.pack()

        self.color_buttons_frame = tk.Frame(self.master)
        self.color_buttons_frame.pack()

        self.color_buttons = []
        for color in self.colors:
            button = tk.Button(self.color_buttons_frame, text=color, bg=color, command=lambda c=color: self.check_color(c))
            button.pack(side=tk.LEFT, padx=10, pady=10)
            self.color_buttons.append(button)
        
        self.start_again_button = tk.Button(self.master, text="Start Again", command=self.start_again)
        self.start_again_button.pack()

        self.show_random_color()

    def show_random_color(self):
        random_color = random.choice(self.colors)
        self.match_label.config(text=random_color, fg=random.choice(self.colors))

    def check_color(self, selected_color):
        current_color = self.match_label.cget("text")
        if selected_color == current_color:
            self.score += 1
            self.wrong_attempts = 0
            self.score_label.config(text=f"Score: {self.score}")
        else:
            self.wrong_attempts += 1
            self.score -= 1
            self.attempts_label.config(text=f"Wrong Attempts: {self.wrong_attempts}")
            self.score_label.config(text=f"Score: {self.score}")

            if self.wrong_attempts == 3:
                self.game_over()

        self.show_random_color()

    def game_over(self):
        for button in self.color_buttons:
            button.config(state=tk.DISABLED)
        self.match_label.config(text="Game Over", fg="red")
        self.start_again_button.config(state=tk.NORMAL)
        tkinter.messagebox.showinfo("Game Over", f"Your final score: {self.score}")

    def start_again(self):
        for button in self.color_buttons:
            button.config(state=tk.NORMAL)
        self.score = 0
        self.wrong_attempts = 0
        self.score_label.config(text=f"Score: {self.score}")
        self.attempts_label.config(text=f"Wrong Attempts: {self.wrong_attempts}")
        self.start_again_button.config(state=tk.DISABLED)
        self.show_random_color()

if __name__ == "__main__":
    root = tk.Tk()
    game = ColorMatchGame(root)
    game.show_random_color()
    root.mainloop()