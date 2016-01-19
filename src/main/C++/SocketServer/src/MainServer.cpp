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
#include<string.h>
#include "../header/serialize.h"
#include "../header/imageProcessing.h"
#include "../header/ocr.h"
#include "../header/other.h"

#include "../header/SocketManager.h"

int portNumber = 19370;
static int i = 0;
static pthread_mutex_t lock;

void *dialogThread(void* arg) {
	try {

		// ... bound to any local address on the specified port
		int listenSocket = (*(struct socketStruct *) arg).listenSocket;
		struct sockaddr_in myAddr = (*(struct socketStruct *) arg).Addr;
		std::string path = (*(struct socketStruct *) arg).path;
		std::cout << "Begin of thread: ";
		std::cout << ntohs(myAddr.sin_port) << std::endl;
		free(arg);
		listen(listenSocket, 1);


		cv::Mat img = cv::imread(path);
		float factor = std::max(std::sqrt((float) (img.rows * img.cols) / 1000000),
				1.0f);
		img = resize(img, 1000000);

		//cv::imwrite(path+*filesListStart,img);

		//usleep(10000000);
		std::vector<cv::Rect> rectList = detectLetters(img);
		//std::cout << rectList.size() << std::endl;
		std::vector<cv::Rect>::iterator rectListStart(rectList.begin()),
				rectListEnd(rectList.end());
		std::string text = "";
		for (; rectListStart != rectListEnd; ++rectListStart) {
			text += " " + getText(path, *rectListStart, factor);
		}
		text += " " + getText(path);
		//std::cout << text << std::endl << std::endl << std::endl << std::endl;



		sendStringMessage(listenSocket, text);
		close(listenSocket);
		pthread_mutex_lock(&lock);
		i--;
		std::cout << "End thread of: ";
		std::cout << ntohs(myAddr.sin_port);
		std::cout << "   i  =  ";
		std::cout << i << std::endl;
		pthread_mutex_unlock(&lock);
	} catch (...) {
		std::cout << "Error while Thread" << std::endl;
		i--;
	}
	return NULL;
}
//int listenSocket = *(int *) arg; // this pointer was an int * at thread creation
//free(arg);
//---- dialog with client ----



int main(void) {
	//---- create listen socket ----
	int listenSocket = socket(PF_INET, SOCK_STREAM, 0);

	// ... bound to any local address on the specified port
	struct sockaddr_in myAddr;
	myAddr.sin_family = AF_INET;
	myAddr.sin_port = htons(portNumber);
	myAddr.sin_addr.s_addr = INADDR_ANY;
	bind(listenSocket, (const struct sockaddr *) &myAddr, sizeof(myAddr));
	listen(listenSocket, 10);

	while (1) {
		if (i <= 1) {
			//---- start a new dialog thread ----
			std::string path = receiveStringMessage(listenSocket, myAddr);
			//std::cout << "receive" << std::endl;
			int errorBool = 0;
			if (path != "") {

				int threadSocket = socket(PF_INET, SOCK_STREAM, 0);
				struct sockaddr_in threadAddr;
				threadAddr.sin_family = AF_INET;
				threadAddr.sin_port = 0;
				threadAddr.sin_addr.s_addr = INADDR_ANY;
				bind(threadSocket, (const struct sockaddr *) &threadAddr,
						sizeof(threadAddr));
				socklen_t len = sizeof(threadAddr);
				getsockname(threadSocket, (struct sockaddr *) &threadAddr, &len);
				int portThread = ntohs(threadAddr.sin_port);

				struct socketStruct* sockStruct = new socketStruct();
				(*sockStruct).listenSocket = threadSocket;
				(*sockStruct).Addr = threadAddr;
				(*sockStruct).path = path;
				//std::cout << portThread << std::endl;

				pthread_t th;
				int n = pthread_create(&th, NULL, dialogThread, sockStruct);
				pthread_detach(th);
				usleep(100000);
				if (n == 0) {
					pthread_mutex_lock(&lock);
					i++;
					pthread_mutex_unlock(&lock);
					//std::cout << "Success" << std::endl;
					errorBool = portThread;
				} else {
					//std::cout << "Fail" << std::endl;
					errorBool = 0;
				}
			}
			
			sendIntMessage(listenSocket, errorBool);

			//std::cout << "send" << std::endl;
		}
	}

	//---- close listen socket ----
	close(listenSocket);
	return 0;
}

