package audio;

import java.util.List;

public class NativeJNI {
    // Load the native library
    static {
        System.loadLibrary("src/Native/StdAudioNative"); 
    }

    // Native method to configure audio settings
    public native void configureAudio(int sampleRate, int channels);

    // Native method to play a single audio sample
    public native void play(double sample);

    // Native method to play an array of audio samples
    public native void play(double[] input);

    // Native method to loop an audio file
    public native void loop(String filename);

    // Native method to save audio to a file
    public native void save(String filename, double[] input);

    // Native method to mute/unmute audio
    public native void setMute(boolean mute);

    // Native method to pause/unpause audio
    public native void setPaused(boolean pause);

    // Native method to check if audio is muted
    public native boolean isMuted();

    // Native method to check if audio is paused
    public native boolean isPaused();
}
