\header {
  title = "G Major Scale"
  composer = "-"
}
\version "2.15.40"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key g \major
  \time 4/4
   \relative c'' { g8 a b c d e fis g a b c d e fis g
  r8 r8 g fis e d c b a g fis e d c b a g }
}

     \new Staff = "lower" {
  \clef bass
  \key g \major
  \time 4/4
  \relative c { r1 r1 r1 r1 }
}
  >>
  
  \layout { }

 \midi { }
}