package audio;

public class NativeJNI {
    // Load the native library
    static {
        System.loadLibrary("src/Native/StdAudioNative"); 
    }

    // Native method to configure audio settings
    public native void configureAudio(int sampleRate, int channels);

    // Native method to play audio from a byte array
    public native void playAudio(byte[] audioData);

    // Native method to play a single audio sample
    public native void play(double sample);

    // Native method to play an array of audio samples
    public native void play(double[] input, int length);

    // Native method to generate a note
    public native double[] note(double hz, double duration, double amplitude);

    // Native method to mute/unmute audio
    public native void setMute(boolean mute);

    // Native method to pause/unpause audio
    public native void setPaused(boolean pause);

    // Native method to read a sample from a file
    public native double readSample(String filename, int index);

    // Native method to save audio to a file
    public native void save(String filename, double[] input, int length);

    // Native method to play a frequency
    public native void playFrequency(double frequency, double duration);

    // Native method to notify listeners
    public native void notifyListeners(int type, double duration);
}


