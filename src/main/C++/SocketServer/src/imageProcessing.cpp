#include <cstdlib>
#include <vector>
#include <opencv/cv.hpp>
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/text/erfilter.hpp"
using namespace cv;
using namespace cv::text;

/*
Traitement d'image basique
*/

cv::Mat resize(cv::Mat image, int nbMaxPixel) {
  cv::Mat copy = image.clone();
  cv::Mat resizeImage;
  if (copy.rows * copy.cols > nbMaxPixel) {
    double factor = std::min(std::sqrt((float)nbMaxPixel / (copy.rows * copy.cols)), 1.0f);
    int destWidth = (int)(copy.cols * factor);
    int destHeight = (int)(copy.rows * factor);
    cv::resize(copy, resizeImage, cv::Size(destWidth, destHeight));
    return resizeImage;
  }
  return copy;
}

std::vector<cv::Rect> detectLetters(cv::Mat img) {
	std::vector<cv::Rect> boundRect;
	cv::Mat img_gray, img_sobel, img_threshold, element;
	cvtColor(img, img_gray, CV_BGR2GRAY);
	cv::Sobel(img_gray, img_sobel, CV_8U, 1, 0, 3, 1, 0, cv::BORDER_DEFAULT);
	cv::threshold(img_sobel, img_threshold, 0, 255,
			CV_THRESH_OTSU + CV_THRESH_BINARY);
	element = getStructuringElement(cv::MORPH_RECT, cv::Size(17, 3));
	cv::morphologyEx(img_threshold, img_threshold, CV_MOP_CLOSE, element); //Does the trick
	element = getStructuringElement(cv::MORPH_RECT, cv::Size(7, 7));
	cv::morphologyEx(img_threshold, img_threshold, CV_MOP_CLOSE, element); //Does the trick
	std::vector<std::vector<cv::Point> > contours;
	cv::findContours(img_threshold, contours, 0, 1);
	std::vector<std::vector<cv::Point> > contours_poly(contours.size());
	for (unsigned int i = 0; i < contours.size(); i++)
		if (contours[i].size() > 500) {
			cv::approxPolyDP(cv::Mat(contours[i]), contours_poly[i], 3, true);
			cv::Rect appRect(boundingRect(cv::Mat(contours_poly[i])));
			if (appRect.width > appRect.height)
				boundRect.push_back(appRect);
		}
	return boundRect;
}


