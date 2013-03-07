\header { 
  title = "Chopsticks"
  composer = "E. Allen"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 6/8
  <f g>8 <f g> <f g> <f g> <f g> <f g> |
  <e g> <e g> <e g> <e g> <e g> <e g> |
  <d b'> <d b'> <d b'> <d b'> <d b'> <d b'>  |
  <c c'> <c c'> <c c'> <c c'> <d b'> <e a>
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 6/8
  r2. r2. r2. r2.
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}