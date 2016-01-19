################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/MainServer.cpp \
../src/OpenCV.cpp \
../src/SocketManager.cpp \
../src/imageProcessing.cpp \
../src/main.cpp \
../src/ocr.cpp \
../src/other.cpp \
../src/serialize.cpp 

OBJS += \
./src/MainServer.o \
./src/OpenCV.o \
./src/SocketManager.o \
./src/imageProcessing.o \
./src/main.o \
./src/ocr.o \
./src/other.o \
./src/serialize.o 

CPP_DEPS += \
./src/MainServer.d \
./src/OpenCV.d \
./src/SocketManager.d \
./src/imageProcessing.d \
./src/main.d \
./src/ocr.d \
./src/other.d \
./src/serialize.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/usr/local/include -I/usr/local/include/tesseract -I/usr/local/include/leptonica -I/usr/local/include/opencv -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o"$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


