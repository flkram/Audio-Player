package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"time"

	"github.com/hajimehoshi/oto/v2"
	"github.com/hajimehoshi/wav"
)

type Melody struct {
	Frequency float64
	Duration  time.Duration
}

func playMelody(melody []Melody) error {
	c, err := oto.NewContext(44100, 2, 2, 8192)
	if err != nil {
		return err
	}
	defer c.Close()

	for _, note := range melody {
		playTone(c, note.Frequency, note.Duration)
	}
	return nil
}

func playTone(c *oto.Context, frequency float64, duration time.Duration) {
	n := int(c.SampleRate() * duration.Seconds())
	buf := make([]byte, n*2) // 16-bit PCM
	for i := 0; i < n; i++ {
		sample := int16(32767.0 * 0.5 * (1 + float64(i%int(c.SampleRate()))/float64(c.SampleRate())) * (1 - float64(i)/float64(n)))
		buf[2*i] = byte(sample)
		buf[2*i+1] = byte(sample >> 8)
	}
	player, err := c.NewPlayer()
	if err != nil {
		log.Fatal(err)
	}
	defer player.Close()

	if _, err := player.Write(buf); err != nil {
		log.Fatal(err)
	}
	time.Sleep(duration)
}

func loadMelodyFromFile(filename string) ([]Melody, error) {
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return nil, err
	}
	// Parse the melody data (you can customize this)
	var melody []Melody
	for _, line := range string(data) {
		var freq float64
		var dur float64
		if _, err := fmt.Sscanf(line, "%f %f", &freq, &dur); err == nil {
			melody = append(melody, Melody{
				Frequency: freq,
				Duration:  time.Duration(dur * float64(time.Second)),
			})
		}
	}
	return melody, nil
}

func main() {
	melodyFile := "melody.txt" // Replace with your melody file
	melody, err := loadMelodyFromFile(melodyFile)
	if err != nil {
		log.Fatal(err)
	}
	if err := playMelody(melody); err != nil {
		log.Fatal(err)
	}
}
