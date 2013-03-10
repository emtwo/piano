\header {
  title = "Clementine"
  composer = "-"
}
\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 3/4
  r2 c8 c  c4 r4 e8 e e4 c c8 e g4 g f8 e d2 d8 e f4 f e8 d e4 c c8 e d4 r4 b8 d c2.
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 3/4
  r2. r4 g' r r2. r r r r r4 g r4 r2.
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  \layout { }

 \midi { }
}