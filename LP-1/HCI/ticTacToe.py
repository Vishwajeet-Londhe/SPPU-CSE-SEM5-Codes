import tkinter as tk

# Define a class for the Tic Tac Toe game
class TicTacToe:
  def __init__(self, master):
      # Initialize the game window
      self.master = master
      self.master.title("Tic Tac Toe")

      # Keep track of the current player (either "X" or "O")
      self.current_player = "X"

      # Initialize the game board as an empty 3x3 array
      self.board = [
          ["", "", ""],
          ["", "", ""],
          ["", "", ""]
      ]

      # Create the buttons for the game board
      self.buttons = []
      for row in range(3):
          button_row = []
          for col in range(3):
              # Each button has a command that calls the handle_click method
              button = tk.Button(
                  self.master,
                  text="",
                  font=("Helvetica", 30),
                  width=3,
                  height=1,
                  command=lambda row=row, col=col: self.handle_click(row, col)
              )
              # Position the button in the game grid
              button.grid(row=row, column=col, sticky="nsew")
              button_row.append(button)
          self.buttons.append(button_row)

  def handle_click(self, row, col):
      # Check if the clicked cell is empty
      if self.board[row][col] == "":
          # Update the board and the button text with the current player's symbol
          self.board[row][col] = self.current_player
          self.buttons[row][col].config(text=self.current_player)
          # Check if the current player has won or the game is tied
          if self.check_win() or self.check_tie():
              # If so, call the game_over method
              self.game_over()
          else:
              # If not, switch to the other player's turn
              self.switch_player()

  def switch_player(self):
      # Alternate between "X" and "O" after each move
      if self.current_player == "X":
          self.current_player = "O"
      else:
          self.current_player = "X"

  def check_win(self):
      # Check if any row has three of the same symbol in a row
      for i in range(3):
          if self.board[i][0] == self.board[i][1] == self.board[i][2] != "":
              return True
      # Check if any column has three of the same symbol in a row
          if self.board[0][i] == self.board[1][i] == self.board[2][i] != "":
              return True
      # Check if the diagonal from top-left to bottom-right has three of the same symbol
      if self.board[0][0] == self.board[1][1] == self.board[2][2] != "":
          return True
      # Check if the diagonal from top-right to bottom-left has three of the same symbol
      if self.board[2][0] == self.board[1][1] == self.board[0][2] != "":
          return True
      # If no winning combination is found, return False
      return False

  def check_tie(self):
      # Check if all cells are filled
      for i in range(3):
          for j in range(3):
              if self.board[i][j] == "":
                  return False
      # If all cells are filled and no one has won, the game is tied
      return True

  def game_over(self):
      # Disable all buttons and display a message announcing the winner
      for row in self.buttons:
          for button in row:
              button.config(state="disabled")
              if self.check_win():
                  winner = self.current_player
                  message = f"Player {winner} wins!"
              else:
                  message = "It's a tie!"
      # Create a label to display the game-over message
      msg_label = tk.Label(self.master, text=message, font=("Helvetica", 20))
      msg_label.grid(row=3, column=0, columnspan=3)
  
# Create the main window and start the game
root = tk.Tk()
game = TicTacToe(root)
root.mainloop()