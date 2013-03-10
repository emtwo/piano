\header { 
  title = "Swan Lake"
  composer = "Pyotr Tchaikovsky"
}

\version "2.16.0"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
         \clef treble
  \key c \major
  \time 4/4
     \tempo "Marcia moderato" 4 = 83
   \set Score.tempoHideNote = ##t
  \relative c' { e1 | r2 c4 d | e2. c4 | e2. c4 | e2. r4 | r1 | r1 | r4 d c r | e1 |
  r2 c4 d | e2. c4 | e2. c4 | e1 | r2 c4 d | e2. c4 | e2. c4 | r1 | r1 | r1 }
}
     \new Staff = "lower" {
         \clef bass
  \key c \major
  \time 4/4
   \relative c { r1 | a'4 b r2 | r1 | r| r2 r4 a | c a f c' | a1 | r2 r4 b | r1 |
  a4 b r2 | r1 | r | r | a4 b r2 | r1 | r1 | a | a2 a | a1 }
}
  >>
  
  \layout { }

 \midi { }
}