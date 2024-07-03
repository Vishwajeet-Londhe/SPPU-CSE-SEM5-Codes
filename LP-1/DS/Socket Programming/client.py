import socket

def main():
    host = '127.0.0.1'  # Use localhost
    port = 8080

    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client.connect((host, port))

    message = input("Enter a message to send to the server: ")

    while True:
        client.send(message.encode())
        data = client.recv(1024)
        print("Received from server: " + data.decode())

        message = input("Enter another message (or press Enter to exit): ")
        if not message:
            break

    client.close()

if __name__ == "__main__":
    main()
