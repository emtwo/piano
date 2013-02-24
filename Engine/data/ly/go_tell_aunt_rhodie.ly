\version "2.15.40"

 \header {
  title = "Go tell Aunt Rhodie"
  composer = "Traditional"
}

\score {

 \new PianoStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 4/4
   \tempo "Moderato" 4 = 91
      \set Score.tempoHideNote = ##t
   \relative c' { 
     e2(\mp e4 d4 c2 c4) r4 d2( d4 f4 e4 d4 c4) r4
     g'2(\mf g4 f4 e2 e4 c4 d4 c4 d4 e4 c2.) r4 
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 4/4
   \relative c' { 
     <g c,>1~\mp <g c,>2. r4 <g f>2. r4 <g c,>2. r4
     <g c,>1~\mf <g c,>2. r4 <g f>2. r4 <g c,>2. r4
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
