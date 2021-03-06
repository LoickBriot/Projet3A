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
#include "../header/serialize.h"
#include "../header/imageProcessing.h"
#include "../header/ocr.h"


using namespace std;
using namespace cv;
using namespace cv::text;


/*
Autres méthodes
*/


std::vector<std::string> listFile(const char* folderPath) {
	std::vector<std::string> filesList;
	DIR *pDIR;
	struct dirent *entry;
	if (pDIR = opendir(folderPath)) {
		while (entry = readdir(pDIR)) {
			if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0)
				filesList.push_back(std::string(entry->d_name));
		}
		closedir(pDIR);
	}
	return filesList;
}
/*



// helper functions

void show_help_and_exit(const char *cmd) {
cout << "    Usage: " << cmd << " <input_image> " << endl;
cout
		<< "    Default classifier files (trained_classifierNM*.xml) must be in current directory"
		<< endl << endl;
exit(-1);
}

void groups_draw(Mat &src, vector<Rect> &groups) {
for (int i = (int) groups.size() - 1; i >= 0; i--) {
	if (src.type() == CV_8UC3)
		rectangle(src, groups.at(i).tl(), groups.at(i).br(),
				Scalar(0, 255, 255), 3, 8);
	else
		rectangle(src, groups.at(i).tl(), groups.at(i).br(), Scalar(255), 3, 8);
}
}

void er_show(vector<Mat> &channels, vector<vector<ERStat> > &regions) {
for (int c = 0; c < (int) channels.size(); c++) {
	Mat dst = Mat::zeros(channels[0].rows + 2, channels[0].cols + 2,
	CV_8UC1);
	for (int r = 0; r < (int) regions[c].size(); r++) {
		ERStat er = regions[c][r];
		if (er.parent != NULL) // deprecate the root region
		{
			int newMaskVal = 255;
			int flags = 4 + (newMaskVal << 8) + FLOODFILL_FIXED_RANGE
					+ FLOODFILL_MASK_ONLY;
			floodFill(channels[c], dst,
					Point(er.pixel % channels[c].cols,
							er.pixel / channels[c].cols), Scalar(255), 0,
					Scalar(er.level), Scalar(0), flags);
		}
	}
	char buff[10];
	char *buff_ptr = buff;
	sprintf(buff, "channel %d", c);
	namedWindow(buff_ptr, WINDOW_NORMAL);
	imshow(buff_ptr, dst);
}
waitKey(-1);
}





// Méthode extract of opencv::text module
void textDetection(const std::string filePath) {
cout << endl << filePath << endl << endl;
//cout << "Demo program of the Extremal Region Filter algorithm described in " << endl;
//cout << "Neumann L., Matas J.: Real-Time Scene Text Localization and Recognition, CVPR 2012" << endl << endl;

namedWindow(filePath, WINDOW_NORMAL);
Mat src = imread(filePath);
//cvtColor(src,src,CV_RGB2GRAY);
//cvtColor(src,src,CV_GRAY2RGB);
//cv::GaussianBlur(img, img, cv::Size(1, 1), 1.0, 1.0);
//imshow("",src);
//waitKey(10);
// Extract channels to be processed individually
vector<Mat> channels;
computeNMChannels(src, channels);

int cn = (int) channels.size();
// Append negative channels to detect ER- (bright regions over dark background)
for (int c = 0; c < cn - 1; c++)
	channels.push_back(255 - channels[c]);

// Create ERFilter objects with the 1st and 2nd stage default classifiers
Ptr<ERFilter> er_filter1 = createERFilterNM1(
		loadClassifierNM1("src/trained_classifierNM1.xml"), 16, 0.00015f, 0.13f,
		0.2f, true, 0.1f);
Ptr<ERFilter> er_filter2 = createERFilterNM2(
		loadClassifierNM2("src/trained_classifierNM2.xml"), 0.5);

vector<vector<ERStat> > regions(channels.size());
// Apply the default cascade classifier to each independent channel (could be done in parallel)
cout << "Extracting Class Specific Extremal Regions from "
		<< (int) channels.size() << " channels ..." << endl;
cout << "    (...) this may take a while (...)" << endl << endl;
for (int c = 0; c < (int) channels.size(); c++) {
	er_filter1->run(channels[c], regions[c]);
	er_filter2->run(channels[c], regions[c]);
}

// Detect character groups
cout << "Grouping extracted ERs ... ";
vector<vector<Vec2i> > region_groups;
vector<Rect> groups_boxes;
erGrouping(src, channels, regions, region_groups, groups_boxes,
		ERGROUPING_ORIENTATION_HORIZ);
//erGrouping(src, channels, regions, region_groups, groups_boxes, ERGROUPING_ORIENTATION_HORIZ, "src/trained_classifier_erGrouping.xml", 0.01);

// draw groups
groups_draw(src, groups_boxes);
imshow(filePath, src);

cout << "Done!" << endl << endl;
cout
		<< "Press 'e' to show the extracted Extremal Regions, any other key to exit."
		<< endl << endl;
//er_show(channels,regions);
if (waitKey(-1) == 101)
	er_show(channels, regions);

// memory clean-up
er_filter1.release();
er_filter2.release();
regions.clear();
if (!groups_boxes.empty()) {
	groups_boxes.clear();
}
}

*/
