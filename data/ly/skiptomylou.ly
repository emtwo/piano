\header {
  title = "Skip to My Lou"
  composer = "American Frontier Song"
}
\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  e4 e8 e c4 c e e g2 d4 d8 d b4 b4 d d f2 e4 e8 e c4 c e e g2 d4 e8 f e4 d 
  c2 c e c e4 e8 e g2 d b d4 d8 d f2 e2 c e4 e8 e g2 d4 e8 f e4 d c2 c
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