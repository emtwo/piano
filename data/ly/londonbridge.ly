\header {
  title = "London Bridge"
  composer = "Traditional"
}
\version "2.16.2"


upper = \relative c'' {
  \clef treble
  \key c \major
  \time 4/4
  g a g f e f g2 d4 e f2 e4 f g2 g4 a g f e f g2 d g e4 c2.
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  c4 r <e g> r c r <e g> r b r <f' g> r  c r <e g> r c r <e g> r c r <e g> r b2 <f' g> c4 <e g> <e g>2
}

\score {
  
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  }