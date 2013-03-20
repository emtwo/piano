\header {
  title = "Hot Cross Buns"
  composer = "Traditional"
}

\version "2.15.40"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 4/4
     \tempo "Moderato" 4 = 90
   \set Score.tempoHideNote = ##t
  \relative c' { e2 d2 c1 e2 d2 c1 c4 c c c d d d d e2 d2 c1
}
     }
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 4/4
  \relative c { r1 c4 <e g> <e g> r r1 c4 <e g> <e g> r c r <e g> r b r <f g> r r1 c4 <e g> <e g> r }
}
  >>
  
  \layout { }

 \midi { }
}