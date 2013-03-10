\header {
  title = "London Bridge"
  composer = "Traditional"
}
\version "2.16.0"

\score {
  
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 4/4
     \tempo "Andante" 4 = 80
   \set Score.tempoHideNote = ##t
  \relative c'' { g a g f e f g2 d4 e f2 e4 f g2 g4 a g f e f g2 d g e4 c2. }
}
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 4/4
  \relative c { c4 r <e g> r c r <e g> r b r <f' g> r  c r <e g> r c r <e g> r c r <e g> r b2 <f' g> c4 <e g> <e g>2 }
}
  >>
  \layout { }

 \midi { }
  }