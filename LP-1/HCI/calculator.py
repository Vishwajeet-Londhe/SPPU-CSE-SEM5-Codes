import tkinter as tk

class KeyPad(tk.Canvas):
    def __init__(self,root):
        super().__init__(master=root,background=root.cget("background"),highlightthickness=0,height=380)
        upper_text = ("C","+/-","%")
        operators = ("/","x","-","+","=")
        keypad_text = (
            7, 8, 9,
            4, 5, 6,
            1, 2, 3,
            0, "", "."
        )
        for row in range(5):
            for col in range(4):
                x,y = 20 + 75*col, 75*row
                if row==0 and col<3:
                    button_bg = "#D0CECE"
                    button_text = upper_text[col]
                elif col==3:
                    button_bg = "#FFC000"
                    button_text = operators[row]
                else:
                    button_bg = "#262626"
                    button_text = keypad_text[(row-1)*3 + col]
                if not button_text in (0, ""):
                    button = self.create_oval(x,y,x+60,y+60,fill=button_bg,width=0)
                elif button_text == 0:
                    arc1 = self.create_arc(x,y,x+60,y+60,start = 90, extent = 180, fill = button_bg, outline = button_bg)
                    arc2 = self.create_arc(x+75,y,x+135,y+60,start = -90, extent = 180, fill = button_bg, outline = button_bg)
                    button = self.create_rectangle(x+30,y,x+105,y+60.5,fill=button_bg, width=0)
                text_color = "black" if button_bg == "#D0CECE" else "white"
                text = self.create_text(x+30,y+30, text = button_text,fill=text_color,font=("Arial",20))
                self.hoverColor(button, button, button_bg)
                self.hoverColor(text, button, button_bg)
                button_text = 0 if button_text == "" else button_text
                self.tag_bind(button,"<Button-1>", lambda event, text=button_text: self.keyPress(text))
                self.tag_bind(text, "<Button-1>", lambda event, text=button_text: self.keyPress(text))
                
    def hoverColor(self, item, button, color):
        if color == "#D0CECE": hover_color = "#E7E6E6"
        elif color == "#FFC000": hover_color = "#FFD966"
        elif color == "#262626": hover_color = "#595959"
        else: hover_color = "white"
        self.tag_bind(item, "<Enter>", lambda event: self.itemconfigure(button, fill=hover_color))
        self.tag_bind(item, "<Leave>", lambda event: self.itemconfigure(button, fill=color))
        
    def keyPress(self,text):
        if text=="C": calculator_screen.configure(text=0, font=("Arial",40))
        elif text=="+/-": calculator_screen["text"] = -int(calculator_screen.cget("text"))
        elif text=="=": calculator_screen.equate()
        else: calculator_screen.appendText(str(text))
        
        if calculator_screen.winfo_reqwidth() > 325:
            calculator_screen["font"] = ("Arial", int(calculator_screen.cget("font")[-2:]) - 3)
        
class CalculatorScreen(tk.Message):
    def __init__(self, root):
        super().__init__(master=root,text=0,background=root.cget("background"),foreground="white",width=325,font=("arial",40),anchor="e",pady=0,padx=34)
        
    def appendText(self,text):
        operators = ("/","x","-","+","=")
        eqn = self.cget("text")
        if eqn == "0": self["text"] = text
        elif text in operators and eqn[-1:] in operators : self["text"] = f"{eqn[:-1]}{text}"
        else: self["text"] += text
    
    def equate(self):
        try:
            ans = eval(self.cget("text").replace("x","*").replace("%","*(1/100)"))
            self["text"] = ans
            self["font"] = ("Arial", 40)
        except:
            pass    

root = tk.Tk()
root.title("Calculator")
root.geometry("325x550")
root.resizable(False, False)
root.configure(background = "#202020")

keypad = KeyPad(root)
keypad.pack(fill="both",side="bottom",pady=(15,0))

calculator_screen = CalculatorScreen(root)
calculator_screen.pack(fill="x",side="bottom")

root.mainloop()