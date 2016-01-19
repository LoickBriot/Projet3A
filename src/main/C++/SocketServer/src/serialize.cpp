/*#include <cstdlib>
#include <vector>
#include <fstream>
#include <iostream>
#include <boost/archive/text_iarchive.hpp>
#include <boost/archive/text_oarchive.hpp>
#include <boost/serialization/vector.hpp>


// enregistrer un vector de string dans un fichier
void writeVector(std::vector<std::string>& d, const char* file) {
	std::ofstream ofile(file);
	boost::archive::text_oarchive ar(ofile);
	ar << d;
	ofile.close();
}

// charger un vector de string depuis un fichier
void readVector(std::vector<std::string>& d, const char* file) {
	std::ifstream ifile(file);
	boost::archive::text_iarchive ar(ifile);
	ar >> d;
	ifile.close();
}

*/
