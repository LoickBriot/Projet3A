#include <opencv/cv.hpp>
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/text/erfilter.hpp"
#include <stdio.h>
#include <cstdlib>
#include <iostream>
#include <string.h>
#include <fstream>
#include <dirent.h>
#include <vector>
#include <iostream>
#include <iomanip>
#include <tesseract/baseapi.h>
#include <tesseract/strngs.h>
#include <leptonica/allheaders.h>
#include <cstdlib>
#include <stdio.h>
#include <iostream>
//#include <re2/re2.h>
//#include <boost/regex.hpp>
#include <fstream>
#include <vector>
#include <iostream>
#include <sstream>
#include "serialize.h"
#include "imageProcessing.h"
#include "ocr.h"


using namespace std;
using namespace cv;
using namespace cv::text;

#ifndef _OTHER_H
#define _OTHER_H

std::vector<std::string> listFile(const char* folderPath);
void textDetection(const std::string filePath);
void show_help_and_exit(const char *cmd);
void groups_draw(Mat &src, vector<Rect> &groups);
void er_show(vector<Mat> &channels, vector<vector<ERStat> > &regions);

#endif 
