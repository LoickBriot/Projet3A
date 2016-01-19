#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <iostream>
#include <string>
#include <arpa/inet.h>
#include <sstream>

#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <errno.h>

namespace patch {
	template<typename T> std::string to_string(const T& n) {
		std::ostringstream stm;
		stm << n;
		return stm.str();
	}
}


void error(const char *msg) {
	perror(msg);
	//    exit(0);
}

std::string receiveStringMessage(int listenSocket, struct sockaddr_in myAddr) {
	struct sockaddr_in fromAddr;
	socklen_t len = sizeof(fromAddr);
	int dialogSocket =
			accept(listenSocket, (struct sockaddr *) &fromAddr, &len);

	char buffer[256];
	try {

		int n = connect(dialogSocket, (struct sockaddr *) &myAddr,
				sizeof(myAddr));
		bzero(buffer, 256);
		read(dialogSocket, buffer, 255);

	} catch (...) {
		std::cout << "Error while receive string message" << std::endl;
	}
	close(dialogSocket);
	return std::string(buffer);
}

int receiveIntMessage(int listenSocket, struct sockaddr_in myAddr) {
	struct sockaddr_in fromAddr;
	socklen_t len = sizeof(fromAddr);
	int dialogSocket =
			accept(listenSocket, (struct sockaddr *) &fromAddr, &len);
	//dialogThread(&dialogSocket);
	char buffer[256];
	try {
		int n = connect(dialogSocket, (struct sockaddr *) &myAddr,
				sizeof(myAddr));
		bzero(buffer, 256);
		read(dialogSocket, buffer, 255);
	} catch (...) {
		std::cout << "Error while receive Integer message" << std::endl;
	}
	close(dialogSocket);

	return atoi(buffer);
}

void sendStringMessage(int listenSocket, std::string message) {
	struct sockaddr_in fromAddr;
	socklen_t len = sizeof(fromAddr);
	int dialogSocket =
			accept(listenSocket, (struct sockaddr *) &fromAddr, &len);

	try {
		int n = write(dialogSocket, message.c_str(), strlen(message.c_str()));

	} catch (...) {
		std::cout << "Error while send string message" << std::endl;
	}
	close(dialogSocket);
}

void sendIntMessage(int listenSocket, int i) {
	struct sockaddr_in fromAddr;
	socklen_t len = sizeof(fromAddr);
	int dialogSocket =
			accept(listenSocket, (struct sockaddr *) &fromAddr, &len);
	try {
		std::string message = patch::to_string(i);
		int n = write(dialogSocket, message.c_str(), strlen(message.c_str()));

	} catch (...) {
		std::cout << "Error while send Integer message" << std::endl;
	}
	close(dialogSocket);
}
