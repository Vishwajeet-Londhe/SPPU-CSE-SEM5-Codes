import xmlrpc.client

def main():
    # Create an XML-RPC client
    proxy = xmlrpc.client.ServerProxy("http://localhost:8000/RPC2")

    # Call remote methods
    a = int(input("Enter a : "))
    b = int(input("Enter b : "))
    result_add = proxy.add(a, b)
    result_subtract = proxy.subtract(a, b)

    print(f"Addition result: {result_add}")
    print(f"Subtraction result: {result_subtract}")

if __name__ == "__main__":
    main()
