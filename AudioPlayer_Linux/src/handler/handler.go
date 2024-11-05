package main

import (
    "bufio"
    "fmt"
    "io/ioutil"
    "os"
    "path/filepath"
    "strconv"
    "strings"
)

// Note structure
type Note struct {
    Duration float64
    Pitch    string
    Octave   int
    Accidental string
    IsRest   bool
}

// Function to read notes from a file
func readNotesFromFile(filename string) ([]Note, error) {
    var notes []Note
    file, err := os.Open(filename)
    if err != nil {
        return nil, err
    }
    defer file.Close()

    scanner := bufio.NewScanner(file)
    for scanner.Scan() {
        line := scanner.Text()
        note, err := parseNote(line)
        if err != nil {
            return nil, err
        }
        notes = append(notes, note)
    }

    if err := scanner.Err(); err != nil {
        return nil, err
    }
    return notes, nil
}

// Function to parse a single note line
func parseNote(line string) (Note, error) {
    parts := strings.Fields(line)
    if len(parts) != 5 {
        return Note{}, fmt.Errorf("invalid note format: %s", line)
    }

    duration, err := strconv.ParseFloat(parts[0], 64)
    if err != nil {
        return Note{}, err
    }

    octave, err := strconv.Atoi(parts[2])
    if err != nil {
        return Note{}, err
    }

    isRest := parts[4] == "true"
    return Note{
        Duration:  duration,
        Pitch:     parts[1],
        Octave:    octave,
        Accidental: parts[3],
        IsRest:    isRest,
    }, nil
}

// Function to validate notes
func validateNotes(notes []Note) error {
    for _, note := range notes {
        if note.Duration <= 0 {
            return fmt.Errorf("invalid duration for note: %v", note)
        }
        if note.Octave < 0 || note.Octave > 8 {
            return fmt.Errorf("invalid octave for note: %v", note)
        }
        if !isValidPitch(note.Pitch) {
            return fmt.Errorf("invalid pitch for note: %v", note)
        }
    }
    return nil
}

// Function to check valid pitch
func isValidPitch(pitch string) bool {
    validPitches := []string{"C", "D", "E", "F", "G", "A", "B"}
    for _, valid := range validPitches {
        if pitch == valid || pitch == valid+" SHARP" || pitch == valid+" FLAT" {
            return true
        }
    }
    return false
}

// Function to process notes
func processNotes(notes []Note) {
    for i := range notes {
        // Example processing: Increase duration by 10%
        notes[i].Duration *= 1.1
    }
}

// Function to save notes to a file
func saveNotesToFile(filename string, notes []Note) error {
    outFile, err := os.Create(filename)
    if err != nil {
        return err
    }
    defer outFile.Close()

    for _, note := range notes {
        _, err := fmt.Fprintf(outFile, "%.2f %s %d %s %v\n", note.Duration, note.Pitch, note.Octave, note.Accidental, note.IsRest)
        if err != nil {
            return err
        }
    }

    return nil
}

// Main function
func main() {
    dataDir := "./data"
    files, err := ioutil.ReadDir(dataDir)
    if err != nil {
        fmt.Println("Error reading data directory:", err)
        return
    }

    for _, file := range files {
        if filepath.Ext(file.Name()) == ".txt" {
            fullPath := filepath.Join(dataDir, file.Name())
            notes, err := readNotesFromFile(fullPath)
            if err != nil {
                fmt.Println("Error reading file:", fullPath, "Error:", err)
                continue
            }

            if err := validateNotes(notes); err != nil {
                fmt.Println("Validation error:", err)
                continue
            }

            // Process notes (edit as needed)
            processNotes(notes)

            // Save edited notes to a new file
            outputFile := filepath.Join(dataDir, "output_"+file.Name())
            if err := saveNotesToFile(outputFile, notes); err != nil {
                fmt.Println("Error saving file:", outputFile, "Error:", err)
            }

            // Call Java runtime (JRE) for additional processing (mock example)
            cmd := exec.Command("java", "-jar", "YourJavaApp.jar", outputFile)
            err = cmd.Run()
            if err != nil {
                fmt.Println("Error running Java application:", err)
            }
        }
    }
}
