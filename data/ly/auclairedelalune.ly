\header {
  title = "Au Claire de la Lune"
  composer = "Traditional"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  c4 c c d e2 d2 c4 e d d c1 c4 c c d e2 d2c4 e d d c1 d4 d d d r1 d4 c r2 r1 c4 c c d e2 d2 c4 e d d c1 
}

lower = \relative c' {
  \clef bass
  \key c \major
  \time 4/4
  r1 r1 r1 r1 r1 r1 r1 r1  r a2 a r2 b4 a g1 r1 r1 r1 r1 
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}