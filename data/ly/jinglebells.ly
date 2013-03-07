\header {
  title = "Jingle Bells"
  composer = "-"
}
\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  e4 e e2 e4 e e2 e4 g c, d e1 f4 f f f f e e e e d d e d2 g2 e4 e e2 e4 e e2 e4 g c, d e1 f4 f f f f e e e g g f d c1
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  r1 r1 r1 r1 r r r r r r r r r r r r
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}