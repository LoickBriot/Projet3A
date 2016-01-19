#include <stdio.h>
#include <cstdlib>
#include <iostream>
#include <string.h>
#include <vector>
#include <iostream>
#include <unistd.h>
#include "../header/serialize.h"
#include "../header/imageProcessing.h"
#include "../header/ocr.h"
#include "../header/other.h"


/*
int main(int argc, const char* argv[]) {

	std::string path = "/home/loick/workspace_CPP/SocketServer/image/";
	std::vector<std::string> filesList = listFile("/home/loick/workspace_CPP/SocketServer/image/");

	std::vector<std::string>::iterator filesListStart(filesList.begin()),filesListEnd(filesList.end());
	for (; filesListStart != filesListEnd; ++filesListStart) {

		std::cout << std::endl << std::endl << *filesListStart << std::endl << std::endl;
		cv::Mat img = cv::imread("/home/loick/workspace_CPP/SocketServer/image/" + *filesListStart);
		float factor = std::max(std::sqrt((float)(img.rows * img.cols)/1000000), 1.0f);
		img = resize(img,1000000);

		//cv::imwrite(path+*filesListStart,img);

		//usleep(10000000);
		std::vector<cv::Rect> rectList = detectLetters(img);
		std::cout << rectList.size() << std::endl;
		std::vector<cv::Rect>::iterator rectListStart(rectList.begin()),rectListEnd(rectList.end());
		std::string text ="";
		for (; rectListStart != rectListEnd; ++ rectListStart) {
			 text += getText("/home/loick/workspace_CPP/SocketServer/image/"+*filesListStart, *rectListStart, factor);
		}
		text += getText("/home/loick/workspace_CPP/SocketServer/image/"+*filesListStart);
		std::cout << text << std::endl<< std::endl<< std::endl<< std::endl;
	}




		return 0;

	}
*/

	/*
	 //JNIEXPORT void JNICALL Java_Sample_process(JNIEnv *, jobject){
	 int matchResult;
	 matchResult = RE2::PartialMatch("16 Octobre 2016 loulouse ",
	 "\\b.oulouse\\b");
	 cout << "matchResult = " << matchResult << endl;
	 std::string path = "../image/";
	 std::string line;
	 boost::regex pat("^Subject: (Re: |Aw: )*(.*)");

	 // créer un tableau d'objets
	 vector<string> d;
	 d.push_back("coucou");
	 d.push_back("toi");

	 // sauver le tableau d'objets dans un fichier
	 writeVector(d, "out.txt");

	 std::cout << std::endl << std::endl;
	 d.clear();

	 // relire les données depuis le fichier
	 readVector(d, "out.txt");
	 cout << d[0] << " " << d[1] << endl;

	 std::vector<std::string> filesList = listFile("/home/loick/workspacecpp/firstOpenCV/image/");

	 std::vector<std::string>::iterator filesListStart(filesList.begin()), filesListEnd(filesList.end());
	 for (; filesListStart != filesListEnd; ++filesListStart) {
	 cv::Mat img = cv::imread(path + *filesListStart);
	 std::vector<cv::Rect> rectList = detectLetters(img);
	 std::vector<cv::Rect>::iterator rectListStart(rectList.begin()), rectListEnd(rectList.end());
	 for (; rectListStart != rectListEnd; ++rectListStart){
	 cv::rectangle(img, *rectListStart, cv::Scalar(0, 255, 0), 3, 8, 0);
	 }
	 namedWindow(*filesListStart, WINDOW_NORMAL);
	 imshow(*filesListStart, img);
	 tesseractOCR(path+*filesListStart);
	 //textDetection(path+*start);
	 }

	 waitKey(0);

	 //cv::imwrite( "imgOut1.jpg", img1);

	 return 0;
	 //std::string file1 = "image/affiche.jpg";
	 //std::string file2 = "image/scenetext03.jpg";
	 //textDetection(file1);
	 //textDetection(file2);

	 }
	 */

	/*
	 int main( int argc, char** argv ) {
	 cv::Mat img = cv::imread("/home/loick/image.jpg");
	 cv::imshow("poisson start",img);
	 cv::GaussianBlur(img, img, cv::Size(1, 1), 1.0, 1.0);
	 //cv::threshold(img,img,0.0,255.0,0);
	 cv::Laplacian(img,img,0);
	 cv::imshow("poisson threshold",img);
	 cv:cvtColor(img,img,CV_RGB2GRAY);
	 cv::imwrite( "/home/loick/image1.jpg" , img);
	 cv::imshow("poisson",img);
	 cv::waitKey(0);

	 return 0;
	 }*/
