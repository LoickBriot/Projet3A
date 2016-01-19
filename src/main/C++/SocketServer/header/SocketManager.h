/*
 * SocketManager.h
 *
 *  Created on: 13 janv. 2016
 *      Author: loick
 */

#ifndef SOCKETMANAGER_H_
#define SOCKETMANAGER_H_


struct socketStruct {
	int listenSocket;
	struct sockaddr_in Addr;
	std::string path;
	socketStruct() : listenSocket(),Addr(),path(){}
	//socketStruct(int d=0, struct sockaddr_in m=NULL):listenSocket(d),
		//           Addr(m){}
};


void error(const char *msg);
std::string receiveStringMessage(int listenSocket, struct sockaddr_in myAddr);
int receiveIntMessage(int listenSocket, struct sockaddr_in myAddr);
void sendStringMessage(int listenSocket, std::string message);
void sendIntMessage(int listenSocket, int i);
#endif /* SOCKETMANAGER_H_ */
