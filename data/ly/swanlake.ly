\header { 
  title = "Swan Lake"
  composer = "Tchaikovsky"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  e1 | r2 c4 d | e2. c4 | e2. c4 | e2. r4 | r1 | r1 | r4 d c r | e1 |
  r2 c4 d | e2. c4 | e2. c4 | e1 | r2 c4 d | e2. c4 | e2. c4 | r1 | r1 | r1
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  r1 | a'4 b r2 | r1 | r| r2 r4 a | c a f c' | a1 | r2 r4 b | r1 |
  a4 b r2 | r1 | r | r | a4 b r2 | r1 | r1 | a | a2 a | a1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  
  \layout { }

 \midi { }
}