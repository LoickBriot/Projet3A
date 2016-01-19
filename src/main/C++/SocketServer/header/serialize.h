#include <cstdlib>
#include <vector>
#include <fstream>
#include <iostream>
//#include <boost/archive/text_iarchive.hpp>
//#include <boost/archive/text_oarchive.hpp>
//#include <boost/serialization/vector.hpp>


#ifndef _SERIALIZE_H
#define _SERIALIZE_H

void writeVector(std::vector<std::string>& d, const char* file);
void readVector(std::vector<std::string>& d, const char* file);

#endif 
