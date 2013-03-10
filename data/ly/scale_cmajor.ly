\header {
  title = "C Major Scale"
  composer = "-"
}
\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  c8 d e f g a b c d e f g a b c
  r8 r8 c8 b a g f e d c b a g f e d c
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  r1 r1 r1 r1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  
  \layout { }

 \midi { }
}