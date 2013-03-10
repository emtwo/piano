\header {
  title = "Frere Jacques"
  composer = "Traditional"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  c d e c c d e c e f g2 e4 f g2 g8 a g f e4 c g'8 a g f e4 c c  r c2 c4 r c2
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  r1 r1 c4 d e c c d e c e f g2 e4 f g2 e4 <f g> e2 e4 <f g> e2
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  \layout { }

 \midi { }
}