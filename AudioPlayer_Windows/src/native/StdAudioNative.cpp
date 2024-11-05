#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <windows.h>
#include <mmdeviceapi.h>
#include <audioclient.h>
#include <cassert>

// Constants
const int SAMPLE_RATE = 44100;
const int BYTES_PER_SAMPLE = 2; // 16-bit audio
const double MAX_16_BIT = 32767.0;

// Class for Audio Events
enum class AudioEventType { PLAY, LOOP, PAUSE, UNPAUSE, STOP, MUTE, UNMUTE };

class AudioEvent {
public:
    AudioEventType type;
    double duration;

    AudioEvent(AudioEventType type, double duration = 0.0)
        : type(type), duration(duration) {}
};

// Audio System Class
class StdAudio {
private:
    bool muted = false;
    bool paused = false;
    WAVEFORMATEX waveFormat;
    IAudioClient* audioClient = nullptr;
    IAudioRenderClient* renderClient = nullptr;
    UINT32 bufferFrameCount;
    BYTE* audioBuffer = nullptr;

public:
    StdAudio() {
        init();
    }

    ~StdAudio() {
        close();
    }

    void init() {
        CoInitialize(nullptr);

        IMMDeviceEnumerator* deviceEnumerator;
        CoCreateInstance(__uuidof(MMDeviceEnumerator), nullptr, CLSCTX_ALL, __uuidof(IMMDeviceEnumerator), (void**)&deviceEnumerator);
        
        IMMDevice* device;
        deviceEnumerator->GetDefaultAudioEndpoint(eRender, eConsole, &device);
        
        device->Activate(__uuidof(IAudioClient), CLSCTX_ALL, nullptr, (void**)&audioClient);

        ZeroMemory(&waveFormat, sizeof(WAVEFORMATEX));
        waveFormat.wFormatTag = WAVE_FORMAT_PCM;
        waveFormat.nChannels = 1; // Mono
        waveFormat.nSamplesPerSec = SAMPLE_RATE;
        waveFormat.wBitsPerSample = 16;
        waveFormat.nBlockAlign = waveFormat.nChannels * (waveFormat.wBitsPerSample / 8);
        waveFormat.nAvgBytesPerSec = SAMPLE_RATE * waveFormat.nBlockAlign;

        audioClient->Initialize(AUDCLNT_SHAREMODE_SHARED, 0, 0, 0, &waveFormat, nullptr);
        audioClient->GetBufferSize(&bufferFrameCount);

        audioClient->GetService(__uuidof(IAudioRenderClient), (void**)&renderClient);
        audioClient->Start();

        audioBuffer = new BYTE[bufferFrameCount * waveFormat.nBlockAlign];
    }

    void close() {
        if (audioClient) {
            audioClient->Stop();
            audioClient->Release();
            audioClient = nullptr;
        }
        if (renderClient) {
            renderClient->Release();
            renderClient = nullptr;
        }
        if (audioBuffer) {
            delete[] audioBuffer;
            audioBuffer = nullptr;
        }
        CoUninitialize();
    }

    void play(double sample) {
        if (muted) return;

        short s = static_cast<short>(MAX_16_BIT * sample);
        audioBuffer[0] = static_cast<BYTE>(s & 0xFF);
        audioBuffer[1] = static_cast<BYTE>((s >> 8) & 0xFF);

        UINT32 numFramesAvailable;
        renderClient->GetBuffer(1, &numFramesAvailable);
        renderClient->ReleaseBuffer(1, 0);

        // Send data to the audio device
        renderClient->WriteAudioSamples(audioBuffer, 1);
    }

    void play(const std::vector<double>& input) {
        for (double sample : input) {
            play(sample);
        }
    }

    void loop(const std::string& filename) {
        // Loop implementation can be added here
    }

    void save(const std::string& filename, const std::vector<double>& input) {
        // WAV file saving implementation
        std::ofstream outFile(filename, std::ios::binary);
        assert(outFile.is_open());

        // WAV header
        outFile << "RIFF";
        outFile << static_cast<int32_t>(36 + input.size() * BYTES_PER_SAMPLE); // Chunk size
        outFile << "WAVE";
        outFile << "fmt ";
        outFile << static_cast<int32_t>(16); // Subchunk1 size
        outFile << static_cast<int16_t>(1);  // Audio format
        outFile << static_cast<int16_t>(1);  // Number of channels
        outFile << SAMPLE_RATE;                // Sample rate
        outFile << SAMPLE_RATE * BYTES_PER_SAMPLE; // Byte rate
        outFile << static_cast<int16_t>(BYTES_PER_SAMPLE); // Block align
        outFile << static_cast<int16_t>(16); // Bits per sample
        outFile << "data";
        outFile << static_cast<int32_t>(input.size() * BYTES_PER_SAMPLE); // Subchunk2 size

        // Write audio samples
        for (double sample : input) {
            short s = static_cast<short>(MAX_16_BIT * sample);
            outFile.write(reinterpret_cast<char*>(&s), sizeof(short));
        }
        outFile.close();
    }

    void setMute(bool mute) {
        muted = mute;
    }

    void setPaused(bool pause) {
        paused = pause;
    }

    bool isMuted() const { return muted; }
    bool isPaused() const { return paused; }
};

