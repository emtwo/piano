\version "2.16.0"

 \header {
  title = "I'm a Little Teapot"
  composer = "Traditional"
}

\score {

 \new PianoStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 4/4
   \tempo "Lively" 4 = 132
   \set Score.tempoHideNote = ##t
   \relative c' { r1 d2 g2 e2 g2 d2. r4
                  r1 r1 r1 r1
                  g4( fis4 e4 g4 fis2 d2 e2 g2 d2.) r4
                  g2( e2 d4 d4 r2 r2 fis2 g2.) r4
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 4/4
   \relative c' { g4 a4 b4 c4 r1 c1 b2. r4
                  c2( c4 c4 b2 b2 a2 a4 a4 g2.) r4
                  r1 r1 c1 b2. r4
                  r1 r2 c2 b2 a2 g2. r4
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
