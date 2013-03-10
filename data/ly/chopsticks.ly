\header { 
  title = "Chopsticks"
  composer = "E. Allen"
}

\version "2.16.2"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 6/8
     \tempo "Allegro" 4 = 120
   \set Score.tempoHideNote = ##t
  \relative c' { <f g>8 <f g> <f g> <f g> <f g> <f g> |
  <e g> <e g> <e g> <e g> <e g> <e g> |
  <d b'> <d b'> <d b'> <d b'> <d b'> <d b'>  |
  <c c'> <c c'> <c c'> <c c'> <d b'> <e a> }
}
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 6/8
  \relative c { r2. r2. r2. r2. }
}
  >>
  
  \layout { }

 \midi { }
}