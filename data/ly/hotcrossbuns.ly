\header {
  title = "Hot Cross Buns"
  composer = "Traditional"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  e2 d2 c1 e2 d2 c1 c4 c c c d d d d e2 d2 c1
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  r1 c4 <e g> <e g> r r1 c4 <e g> <e g> r c r <e g> r b r <f g> r r1 c4 <e g> <e g> r
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}