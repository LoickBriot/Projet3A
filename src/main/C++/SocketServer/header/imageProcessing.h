#include <cstdlib>
#include <vector>
#include <opencv/cv.hpp>
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/text/erfilter.hpp"

using namespace cv;
using namespace cv::text;

#ifndef _IMAGEPROCESSING_H 
#define _IMAGEPROCESSING_H

cv::Mat resize(cv::Mat image, int nbMaxPixel);
std::vector<cv::Rect> detectLetters(cv::Mat img);

#endif 
