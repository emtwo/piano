\header {
  title = "D Major Scale"
  composer = "-"
}
\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key d \major
  \time 4/4
  d8 e fis g a b cis d e fis g a b cis d
  r8 r8 d cis b a g fis e d cis b a g fis e d
}

lower = \relative c {
  \clef bass
  \key d \major
  \time 4/4
  r1 r1 r1 r1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}