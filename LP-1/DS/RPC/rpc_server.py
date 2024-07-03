from xmlrpc.server import *

# Create a simple class with some methods
class MathOperations:
    def add(self, x, y):
        return x + y

    def subtract(self, x, y):
        return x - y

# Restrict to a particular path to avoid arbitrary code execution
class RequestHandler(SimpleXMLRPCRequestHandler):
    rpc_paths = ('/RPC2',)

def main():
    server = SimpleXMLRPCServer(('localhost', 8000), requestHandler=RequestHandler)
    server.register_instance(MathOperations())

    print("RPC Server is listening on port 8000...")
    server.serve_forever()

if __name__ == "__main__":
    main()
