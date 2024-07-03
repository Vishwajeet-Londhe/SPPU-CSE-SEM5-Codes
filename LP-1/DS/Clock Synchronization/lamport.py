class LamportClock:
    def __init__(self):
        self.clock = 0

    def get_time(self):
        return self.clock

    def tick(self):
        self.clock += 1

def main():
    # Create two Lamport clocks
    clock1 = LamportClock()
    clock2 = LamportClock()

    # Simulate events and clock updates
    print(f'Initial Clock 1: {clock1.get_time()}')
    print(f'Initial Clock 2: {clock2.get_time()}')

    clock1.tick()
    print(f'Clock 1 after tick: {clock1.get_time()}')

    clock2.tick()
    print(f'Clock 2 after tick: {clock2.get_time()}')

    # Compare clock values
    if clock1.get_time() < clock2.get_time():
        print('Clock 1 is behind Clock 2')
    elif clock1.get_time() > clock2.get_time():
        print('Clock 2 is behind Clock 1')
    else:
        print('Clock 1 and Clock 2 are synchronized')

if __name__ == "__main__":
    main()
