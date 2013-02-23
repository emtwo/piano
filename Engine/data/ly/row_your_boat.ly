\version "2.15.40"

 \header {
  title = "Row Your Boat"
  composer = "Traditional"
}

\score {

 \new PianoStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 3/4
   \tempo 4 = 200
   \set Score.tempoHideNote = ##t
   \relative c' { 
     c2.\mp c2. c2 d4 e2. e2 d4 e2 f4
     g2.~ g2 r4 c4\mf c4 c4 g4 g4 g4 e4 e4 e4 c4 c4 c4
     g'2 f4 e2 d4 c2. g'2.
     c,2.\mp c2. c2 d4 e2. e2 d4 e2 f4
     g2.~ g2 r4 c4\mf c4 c4 g4 g4 g4 e4 e4 e4 c4 c4 c4
     g'2 f4 e2 d4 c2. c'2.
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 3/4
   \relative c' { 
     <g c,>2.~ <g c,>2. <g c,>2.~ <g c,>2. <g c,>2.~ <g c,>2.
     <g c,>2.~ <g c,>2 r4 r2. r2. r2. r2.
     <g f>2.~ <g f>2 r4 <g c,>2. <g f>2.
      <g c,>2.~ <g c,>2. <g c,>2.~ <g c,>2. <g c,>2.~ <g c,>2.
     <g c,>2.~ <g c,>2 r4 r2. r2. r2. r2.
     <g f>2.~ <g f>2 r4 <g c,>2 r4 r2.
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
