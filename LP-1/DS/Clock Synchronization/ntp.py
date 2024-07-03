import ntplib
from time import ctime

def sync_ntp_time(server):
    try:
        c = ntplib.NTPClient()
        response = c.request(server, version=3)
        local_time = ctime(response.tx_time)
        print(f'NTP Server: {server}')
        print(f'Local Time synchronized with NTP Server: {local_time}')
    except Exception as e:
        print(f'Error: {e}')

def main():
    ntp_server = 'pool.ntp.org'  # Replace with a valid NTP server
    sync_ntp_time(ntp_server)

if __name__ == "__main__":
    main()
