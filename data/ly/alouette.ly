\header {
  title = "Alouette"
  composer = "-"
}
\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  c4. d8 e4 e d8 c d e c4 r c4. d8 e4 e d8 c d e c2 c8 d e f g g g4 r1
  g8 g g4 r2 g4 f e d c4. d8 e4 e d8 c d e c4 r4 c4. d8 e4 e d8 c d e c2
}

lower = \relative c' {
  \clef bass
  \key c \major
  \time 4/4
  r1 r2. g4 r1 r1 r1 g8 f e d c c c4 r2 g'8 g g4 g1 r1 r2. g4 r1 r1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}