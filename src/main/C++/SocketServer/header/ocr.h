#include <tesseract/baseapi.h>
#include <tesseract/strngs.h>
#include <leptonica/allheaders.h>
#include <cstdlib>
#include <iostream>
#include <vector>

#ifndef _OCR_H
#define _OCR_H

std::string getText(std::string path);
std::string getText(std::string path, cv::Rect rect, float factor);
#endif 
