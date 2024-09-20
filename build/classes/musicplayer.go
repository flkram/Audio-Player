package main

import (
	"bufio"
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
	"time"

	"github.com/hajimehoshi/oto/v2"
)

// Melody structure represents a single note with its frequency, duration, and volume.
type Melody struct {
	Frequency float64
	Duration  time.Duration
	Volume    float64
}

// main function to execute the melody player
func main() {
	melodyFiles := []string{"melody1.txt", "melody2.txt"} // Predefined melody files
	for {
		fmt.Println("Available melodies:")
		for i, file := range melodyFiles {
			fmt.Printf("%d: %s\n", i+1, file)
		}
		fmt.Print("Choose a melody number to play or 'exit' to quit: ")
		if choice, err := bufio.NewReader(os.Stdin).ReadString('\n'); err == nil {
			choice = strings.TrimSpace(choice)
			if choice == "exit" {
				break
			}
			if index, err := strconv.Atoi(choice); err == nil && index > 0 && index <= len(melodyFiles) {
				melody, err := loadMelodyFromFile(melodyFiles[index-1])
				if err != nil {
					log.Fatalf("Error loading melody: %v", err)
				}
				if err := playMelody(melody); err != nil {
					log.Fatalf("Error playing melody: %v", err)
				}
				handleMelodyEditing(melodyFiles[index-1])
			} else {
				fmt.Println("Invalid choice. Please select a valid melody number.")
			}
		}
	}
}

// playMelody plays the given melody slice.
func playMelody(melody []Melody) error {
	c, err := oto.NewContext(44100, 2, 2, 8192)
	if err != nil {
		return fmt.Errorf("failed to create audio context: %v", err)
	}
	defer c.Close()

	for _, note := range melody {
		if err := playToneWithVolume(c, note.Frequency, note.Duration, note.Volume); err != nil {
			return err
		}
	}
	return nil
}

// playToneWithVolume plays a tone with specified frequency, duration, and volume.
func playToneWithVolume(c *oto.Context, frequency float64, duration time.Duration, volume float64) error {
	n := int(c.SampleRate() * duration.Seconds())
	buf := make([]byte, n*2)

	for i := 0; i < n; i++ {
		sample := int16(32767.0 * volume * 0.5 * (1 + float64(i%int(c.SampleRate()))/float64(c.SampleRate())) * (1 - float64(i)/float64(n)))
		buf[2*i] = byte(sample)
		buf[2*i+1] = byte(sample >> 8)
	}
	player, err := c.NewPlayer()
	if err != nil {
		return fmt.Errorf("failed to create player: %v", err)
	}
	defer player.Close()

	if _, err := player.Write(buf); err != nil {
		return fmt.Errorf("failed to write to player: %v", err)
	}
	time.Sleep(duration)
	return nil
}

// loadMelodyFromFile loads a melody from a specified text file.
func loadMelodyFromFile(filename string) ([]Melody, error) {
	data, err := ioutil.ReadFile(filename)
	if err != nil {
		return nil, fmt.Errorf("error reading file: %v", err)
	}

	var melody []Melody
	for _, line := range strings.Split(string(data), "\n") {
		if strings.TrimSpace(line) == "" {
			continue // Skip empty lines
		}
		var freq, dur, vol float64
		if _, err := fmt.Sscanf(line, "%f %f %f", &freq, &dur, &vol); err == nil {
			melody = append(melody, Melody{
				Frequency: freq,
				Duration:  time.Duration(dur * float64(time.Second)),
				Volume:    vol,
			})
		} else {
			log.Printf("Ignoring invalid line: %s", line)
		}
	}
	return melody, nil
}

// handleMelodyEditing allows the user to edit the melody after playing it.
func handleMelodyEditing(filename string) {
	reader := bufio.NewReader(os.Stdin)

	for {
		fmt.Print("Do you want to edit the melody? (y/n): ")
		if response, _ := reader.ReadString('\n'); strings.TrimSpace(response) == "y" {
			editMelody(filename)
			break
		} else if strings.TrimSpace(response) == "n" {
			break
		}
	}
}

// editMelody allows the user to edit the melody file interactively.
func editMelody(filename string) {
	fmt.Println("Editing melody file:", filename)
	reader := bufio.NewReader(os.Stdin)

	melody, err := loadMelodyFromFile(filename)
	if err != nil {
		log.Fatalf("Error loading melody for editing: %v", err)
	}

	for {
		fmt.Println("Current melody:")
		for i, note := range melody {
			fmt.Printf("%d: %.2f %.2fs %.2f\n", i+1, note.Frequency, note.Duration.Seconds(), note.Volume)
		}

		fmt.Print("Enter command (add/remove/reorder/done): ")
		command, _ := reader.ReadString('\n')
		command = strings.TrimSpace(command)

		switch command {
		case "add":
			fmt.Print("Enter note (frequency duration volume): ")
			input, _ := reader.ReadString('\n')
			input = strings.TrimSpace(input)
			var freq, dur, vol float64
			if _, err := fmt.Sscanf(input, "%f %f %f", &freq, &dur, &vol); err == nil {
				melody = append(melody, Melody{Frequency: freq, Duration: time.Duration(dur * float64(time.Second)), Volume: vol})
			} else {
				fmt.Println("Invalid input. Please enter three numbers.")
			}
		case "remove":
			fmt.Print("Enter note number to remove: ")
			var index int
			if _, err := fmt.Scanf("%d", &index); err == nil && index > 0 && index <= len(melody) {
				melody = append(melody[:index-1], melody[index:]...)
			} else {
				fmt.Println("Invalid note number.")
			}
		case "reorder":
			fmt.Print("Enter the note number to move: ")
			var oldIndex, newIndex int
			if _, err := fmt.Scanf("%d", &oldIndex); err == nil && oldIndex > 0 && oldIndex <= len(melody) {
				fmt.Print("Enter new position: ")
				if _, err := fmt.Scanf("%d", &newIndex); err == nil && newIndex > 0 && newIndex <= len(melody) {
					note := melody[oldIndex-1]
					melody = append(melody[:oldIndex-1], melody[oldIndex:]...) // Remove the note
					melody = append(melody[:newIndex-1], append([]Melody{note}, melody[newIndex-1:]...)...) // Insert it in the new position
				} else {
					fmt.Println("Invalid new position.")
				}
			} else {
				fmt.Println("Invalid note number.")
			}
		case "done":
			saveMelodyToFile(filename, melody)
			fmt.Println("Melody updated successfully.")
			return
		default:
			fmt.Println("Unknown command. Please use add, remove, reorder, or done.")
		}
	}
}

// saveMelodyToFile saves the updated melody to the specified file.
func saveMelodyToFile(filename string, melody []Melody) {
	var sb strings.Builder
	for _, note := range melody {
		sb.WriteString(fmt.Sprintf("%.2f %.2fs %.2f\n", note.Frequency, note.Duration.Seconds(), note.Volume))
	}
	if err := ioutil.WriteFile(filename, []byte(sb.String()), 0644); err != nil {
		log.Fatalf("Error saving melody: %v", err)
	}
}

