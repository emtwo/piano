\header {
  title = "G Major Scale"
  composer = "-"
}
\version "2.16.2"

upper = \relative c'' {
  \clef treble
  \key g \major
  \time 4/4
  g8 a b c d e fis g a b c d e fis g
  r8 r8 g fis e d c b a g fis e d c b a g
}

lower = \relative c {
  \clef bass
  \key g \major
  \time 4/4
  r1 r1 r1 r1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}