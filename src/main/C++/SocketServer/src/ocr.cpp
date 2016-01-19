#include <tesseract/baseapi.h>
#include <tesseract/strngs.h>
#include <leptonica/allheaders.h>
#include <cstdlib>
#include <iostream>
#include <vector>
#include <locale.h>
#include <opencv/cv.hpp>
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/text/erfilter.hpp"


/*
Méthode pour OCRiser
*/

std::string getText(std::string path) {
	setlocale (LC_NUMERIC, "C");
	std::string outText;

	tesseract::TessBaseAPI *api = new tesseract::TessBaseAPI();
	// Initialize tesseract-ocr with English, without specifying tessdata path
	if (api->Init(NULL, "eng")) {
		fprintf(stderr, "Could not initialize tesseract.\n");
		exit(1);
	}

	// Open input image with leptonica library
	Pix *image = pixRead((char*) path.c_str());
	api->SetImage(image);

	// Get OCR result
	outText = std::string(api->GetUTF8Text());
	

	// Destroy used object and release memory
	api->End();
//	delete[] outText;
	pixDestroy(&image);
	return outText;
}

std::string getText(std::string path,cv::Rect rect, float factorRect) {
	setlocale (LC_NUMERIC, "C");
	std::string outText;

	tesseract::TessBaseAPI *api = new tesseract::TessBaseAPI();
	// Initialize tesseract-ocr with English, without specifying tessdata path
	if (api->Init(NULL, "eng")) {
		fprintf(stderr, "Could not initialize tesseract.\n");
		exit(1);
	}

	// Open input image with leptonica library
	Pix *image = pixRead((char*) path.c_str());
	api->SetImage(image);
	api->SetRectangle((int)(factorRect*rect.x),(int)(factorRect*rect.y),(int)(factorRect*rect.width), (int)(factorRect*rect.height));


	// Get OCR result
	outText = std::string(api->GetUTF8Text());


	// Destroy used object and release memory
	api->End();
//	delete[] outText;
	pixDestroy(&image);
	return outText;
}


