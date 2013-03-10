\header {
  title = "D Major Scale"
  composer = "-"
}
\version "2.16.2"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key d \major
  \time 4/4
  \relative c' { d8 e fis g a b cis d e fis g a b cis d
  r8 r8 d cis b a g fis e d cis b a g fis e d }
}

     \new Staff = "lower" {       
  \clef bass
  \key d \major
  \time 4/4
  \relative c { r1 r1 r1 r1 }
}
  >>
  
  \layout { }

 \midi { }
}