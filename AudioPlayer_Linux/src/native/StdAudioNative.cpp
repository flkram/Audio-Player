#include <iostream>
#include <sndfile.h>
#include <alsa/asoundlib.h>
#include <cmath>
#include <vector>
#include <set>
#include <thread>
#include <chrono>

class StdAudio {
public:
    static const int SAMPLE_RATE = 44100;
    static const int BYTES_PER_SAMPLE = 2; // 16-bit audio
    static const int BITS_PER_SAMPLE = 16; // 16-bit audio
    static const double MAX_16_BIT; // 32,767
    static const int SAMPLE_BUFFER_SIZE = 4096;

private:
    static snd_pcm_t* pcm_handle; // to play the sound
    static std::vector<char> buffer; // internal buffer
    static int bufferSize; // number of samples currently in internal buffer
    static bool muted;
    static bool paused;
    static std::set<std::function<void(int, double)>> listeners;

public:
    // Constructor
    StdAudio() {
        init();
    }

    // Destructor
    ~StdAudio() {
        close();
    }

    static void init() {
        // Open PCM device for playback
        int err;
        if ((err = snd_pcm_open(&pcm_handle, "default", SND_PCM_STREAM_PLAYBACK, 0)) < 0) {
            std::cerr << "Error opening PCM device: " << snd_strerror(err) << std::endl;
            exit(EXIT_FAILURE);
        }

        // Set parameters
        snd_pcm_set_params(pcm_handle,
                           SND_PCM_FORMAT_S16_LE,
                           SND_PCM_ACCESS_RW_INTERLEAVED,
                           1,
                           SAMPLE_RATE,
                           1,
                           500000); // 0.5 seconds

        buffer.resize(SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE / 3);
        bufferSize = 0;
    }

    static void close() {
        snd_pcm_drain(pcm_handle);
        snd_pcm_close(pcm_handle);
    }

    static void play(double in) {
        if (muted) return;

        // Clip if outside [-1, +1]
        if (in < -1.0) in = -1.0;
        if (in > +1.0) in = +1.0;

        // Convert to bytes
        short s = static_cast<short>(MAX_16_BIT * in);
        buffer[bufferSize++] = (char)(s & 0xFF);
        buffer[bufferSize++] = (char)((s >> 8) & 0xFF); // little Endian

        // Send to sound card if buffer is full
        if (bufferSize >= buffer.size()) {
            snd_pcm_writei(pcm_handle, buffer.data(), buffer.size() / BYTES_PER_SAMPLE);
            bufferSize = 0;
        }
    }

    static void play(double* input, size_t length) {
        for (size_t i = 0; i < length; ++i) {
            play(input[i]);
        }
    }

    static double* note(double hz, double duration, double amplitude) {
        int N = static_cast<int>(SAMPLE_RATE * duration);
        double* a = new double[N + 1];
        for (int i = 0; i <= N; i++) {
            a[i] = amplitude * std::sin(2 * M_PI * i * hz / SAMPLE_RATE);
        }
        return a;
    }

    static void setMute(bool mute) {
        muted = mute;
        // Notify listeners about mute/unmute
    }

    static void setPaused(bool pause) {
        paused = pause;
        // Notify listeners about pause/unpause
    }

    static double readSample(const std::string& filename, size_t index) {
        SF_INFO sfinfo;
        SNDFILE* infile = sf_open(filename.c_str(), SFM_READ, &sfinfo);
        if (!infile) {
            std::cerr << "Could not open file: " << filename << std::endl;
            return 0;
        }

        double* buffer = new double[sfinfo.frames];
        sf_readf_double(infile, buffer, sfinfo.frames);
        double sample = buffer[index];
        delete[] buffer;
        sf_close(infile);
        return sample;
    }

    static void save(const std::string& filename, double* input, size_t length) {
        SF_INFO sfinfo;
        sfinfo.format = SF_FORMAT_WAV | SF_FORMAT_PCM_16;
        sfinfo.samplerate = SAMPLE_RATE;
        sfinfo.channels = 1;

        SNDFILE* outfile = sf_open(filename.c_str(), SFM_WRITE, &sfinfo);
        if (!outfile) {
            std::cerr << "Could not open file: " << filename << std::endl;
            return;
        }

        sf_writef_double(outfile, input, length);
        sf_close(outfile);
    }

    static void notifyListeners(int type, double duration) {
        for (const auto& listener : listeners) {
            listener(type, duration);
        }
    }

    static void playFrequency(double frequency, double duration) {
        double* audioNote = note(frequency, duration, 0.5);
        play(audioNote, static_cast<size_t>(SAMPLE_RATE * duration));
        delete[] audioNote;
    }

    static void main() {
        // Test client: Play an A major scale
        double frequencies[] = { 440.0, 493.88, 523.25, 587.33, 659.26, 739.99, 830.61, 880.0 };
        for (double freq : frequencies) {
            playFrequency(freq, 1.0);
        }

        close();
    }
};

// Define static members
const double StdAudio::MAX_16_BIT = 32767.0;
snd_pcm_t* StdAudio::pcm_handle = nullptr;
std::vector<char> StdAudio::buffer;
int StdAudio::bufferSize = 0;
bool StdAudio::muted = false;
bool StdAudio::paused = false;
std::set<std::function<void(int, double)>> StdAudio::listeners;

// Entry point
int main() {
    StdAudio audio;
    StdAudio::main();
    return 0;
}
