\version "2.16.0"

 \header {
  title = "This Old Man"
  composer = "Traditional"
}

\score {

 \new PianoStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 4/4
   \tempo "Happily" 4 = 132
   \set Score.tempoHideNote = ##t
   \relative c' { c4 r4 c2 c4 r4 c2 d4 c4 r2 r1 
   c4 r4 r2 r2 c2 c4( r4 r2 r4 e4 f2)
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 4/4
   \relative c' { r4 a4 r2 r4 a4 r2 r2 bes4 a4 g4 a4 bes2
   r4 f4 f4 f4 f4 a4 r2 r4 g4 g4 bes4 a4 g4 f2
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
