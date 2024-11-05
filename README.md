
# Audio Player Desktop Application

## Overview
This project is a desktop audio player application developed using Java and Swing GUI. It reads musical notes from a file and plays them back, providing an interactive experience for users. The application is designed to be cross-platform, supporting both Linux and Windows environments.

## Features
- **File-Based Note Playback**: Reads musical notes from user-defined files and plays them back through a graphical interface.
- **Cross-Platform Audio Management**: Utilizes the ALSA (Advanced Linux Sound Architecture) API for Linux and the Windows Audio Session API (WASAPI) for Windows, ensuring seamless audio session management and device interaction across multiple operating systems.
- **Expandable Architecture**: Implemented using Go libraries, allowing for easy integration of additional features, including:
  - Volume control
  - Audio effects (reverb, echo, etc.)
  - Support for various audio formats (WAV, MP3, etc.)

## Technologies Used
- **Programming Languages**: Java, C++
- **Frameworks**: Swing (Java GUI)
- **APIs**: ALSA (for Linux), WASAPI (for Windows), Go libraries

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- C++ compiler (GCC for Linux or Visual Studio for Windows)
- Go language installed

### Installation
1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/audioplayer.git
   cd audioplayer
   ```

2. **Build the C++ components** (if applicable):
   - For Linux:
     ```bash
     g++ -o audio_manager audio_manager.cpp -lasound
     ```
   - For Windows, use Visual Studio to compile the C++ code.

3. **Compile the Java application**:
   ```bash
   javac src/apps/main.java -d bin
   ```

4. **Run the application**:
   ```bash
   java -cp bin apps.main
   ```

### Usage
1. Launch the application.
2. Load a file containing musical notes (in the supported format).
3. Use the GUI to control playback, adjust volume, and apply effects.


## Acknowledgements
- Thanks to the open-source community for their contributions to ALSA and WASAPI.
- Special thanks to the contributors and collaborators who helped develop this project.
