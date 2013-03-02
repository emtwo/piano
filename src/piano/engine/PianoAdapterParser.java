package piano.engine;
/*
 *	PianoAdapterParser.java
 *
 */

import org.jfugue.elements.Note;
import org.jfugue.parsers.Parser;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.ActionEvent;


import java.io.IOException;
import	java.io.PrintStream;
import java.util.HashMap;


/**	Displays the file format information of a MIDI file.
 */
public class PianoAdapterParser extends Parser implements Receiver
{

    private static final String[] sm_astrKeyNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    HashMap<String, Byte> noteHash = new HashMap<String, Byte>();
    private MidiDevice inputDevice = null;
    private static PianoAdapterParser _instance = null;

    protected PianoAdapterParser()
    {
        tryAttachPiano();

        // dictionary for note byte values
        for (int i = 0; i <= 10; ++i) {
            noteHash.put("C" + i, (byte) (i * 12));
            noteHash.put("C#" + i, (byte) (i * 12 + 1));
            noteHash.put("Db" + i, (byte) (i * 12 + 1));
            noteHash.put("D" + i, (byte) (i * 12 + 2));
            noteHash.put("D#" + i, (byte) (i * 12 + 3));
            noteHash.put("Eb" + i, (byte) (i * 12 + 3));
            noteHash.put("E" + i, (byte) (i * 12 + 4));
            noteHash.put("F" + i, (byte) (i * 12 + 5));
            noteHash.put("F#" + i, (byte) (i * 12 + 6));
            noteHash.put("Gb" + i, (byte) (i * 12 + 6));
            noteHash.put("G" + i, (byte) (i * 12 + 7));
            noteHash.put("G#" + i, (byte) (i * 12 + 8));
            noteHash.put("Ab" + i, (byte) (i * 12 + 8));
            noteHash.put("A" + i, (byte) (i * 12 + 9));
            noteHash.put("A#" + i, (byte) (i * 12 + 10));
            noteHash.put("Bb" + i, (byte) (i * 12 + 10));
            noteHash.put("B" + i, (byte) (i * 12 + 11));
        }
    }

    public static PianoAdapterParser instance() {
        if (_instance == null) {
            _instance = new PianoAdapterParser();
        }

        return _instance;
    }

    public void close()
    {
        inputDevice.close();
    }

    public void send(MidiMessage message, long lTimeStamp)
    {
        String	strMessage = null;
        if (message instanceof ShortMessage)
        {
            decodeNote((ShortMessage) message);
        }
        else
        {
            System.out.println("unknown midi message type");
        }

    }

    public void decodeNote(ShortMessage message) {
        if (message.getCommand() != 0x80 && message.getCommand() != 0x90) {
            return;
        }

        int keyNumber = message.getData1();
        int noteValue = keyNumber % 12;
        int octave = keyNumber / 12;
        int velocity = message.getData2();
        String noteStr = sm_astrKeyNames[noteValue] + (octave);
        Note n = new Note(noteHash.get(noteStr));
        n.setAttackVelocity((byte)velocity);

/*        if (message.getCommand() == 0x80)
            System.out.print("note off ");
        else if (message.getCommand() == 0x90)
            System.out.print("note on ");
        System.out.println(noteStr);   */

        fireNoteEvent(n);
    }

    public void tryAttachPiano() {
        String pianoDeviceName = "UM-1G";
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

        int index = -1;
        for (int i = 0; i < devices.length; i++)
        {
            if (devices[i].getName().equals(pianoDeviceName))
            {
                try
                {
                    inputDevice = MidiSystem.getMidiDevice(devices[i]);
                    inputDevice.open();
                    System.out.println("Found device");

                    Transmitter t = inputDevice.getTransmitter();
                    t.setReceiver(this);

                }
                catch (MidiUnavailableException e)
                {
                    System.out.println("Failed to attach piano.");
                }

                break;
            }
        }

        if (inputDevice == null) {
            System.out.println("No device to attach.");
        }

    }



}
