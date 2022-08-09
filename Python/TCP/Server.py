import socket
import threading

socket_server = socket.socket()
localIP = socket.gethostbyname(socket.gethostname())

socket_server.bind((localIP, 2000))
socket_server.listen()
print('Server Started')

def run(client_socket, addr):
    print('Client Address: ', addr)
    while True:
        data = client_socket.recv(1024)
        value = data.decode('utf-8')
        if data == "bye":
            client_socket.close()
            break
        else:
            print(data.decode('utf-8'))
            client_socket.send(('Get ' + value).encode('utf-8'))

while True:
    sock, addr = socket_server.accept()
    thread = threading.Thread(target=run, name='Thread', args=(sock, addr))
    thread.start()
