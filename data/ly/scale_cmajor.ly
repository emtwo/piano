\header {
  title = "C Major Scale"
  composer = "-"
}
\version "2.16.2"

upper = 

lower = 
\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 4/4
  \relative c' { c8 d e f g a b c d e f g a b c
  r8 r8 c8 b a g f e d c b a g f e d c }
}

     \new Staff = "lower" {       
  \clef bass
  \key c \major
  \time 4/4
  \relative c { r1 r1 r1 r1 }
}

  >>
  
  \layout { }

 \midi { }
}