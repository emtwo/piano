package piano.engine;
/*
 *	PianoAdapterParser.java
 *
 *	This file is part of jsresources.org
 */

/*
 * Copyright (c) 1999 - 2001 by Matthias Pfisterer
 * Copyright (c) 2003 by Florian Bomers
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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

    private static final String[] sm_astrKeySignatures = {"Cb", "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#"};
    private static final String[] SYSTEM_MESSAGE_TEXT =
            {
                    "System Exclusive (should not be in ShortMessage!)",
                    "MTC Quarter Frame: ",
                    "Song Position: ",
                    "Song Select: ",
                    "Undefined",
                    "Undefined",
                    "Tune Request",
                    "End of SysEx (should not be in ShortMessage!)",
                    "Timing clock",
                    "Undefined",
                    "Start",
                    "Continue",
                    "Stop",
                    "Undefined",
                    "Active Sensing",
                    "System Reset"
            };

    private static final String[]		QUARTER_FRAME_MESSAGE_TEXT =
            {
                    "frame count LS: ",
                    "frame count MS: ",
                    "seconds count LS: ",
                    "seconds count MS: ",
                    "minutes count LS: ",
                    "minutes count MS: ",
                    "hours count LS: ",
                    "hours count MS: "
            };

    private static final String[]		FRAME_TYPE_TEXT =
            {
                    "24 frames/second",
                    "25 frames/second",
                    "30 frames/second (drop)",
                    "30 frames/second (non-drop)",
            };


    HashMap<String, Byte> noteHash = new HashMap<String, Byte>();
    public PianoAdapterParser()
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

    public void close()
    {
        // TODO: close device?
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
        String pianoDeviceName = "2- UM-1G";
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();
        MidiDevice inputDevice = null;
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
