from http import client
import socket
import threading

socket_client = socket.socket()
localIP = socket.gethostbyname(socket.gethostname())

socket_client.connect((localIP, 2000))
print('Client Started')

# Send data
while True:
    send_data = input('>>')
    socket_client.send(send_data.encode('utf-8'))
    if send_data == 'bye':
        break

    # Receive data
    receive_data = socket_client.recv(1024)
    print(receive_data.decode('utf-8'))
