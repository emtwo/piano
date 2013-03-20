\header {
  title = "Alouette"
  composer = "Traditional"
}
\version "2.15.40"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
         \clef treble
  \key c \major
  \time 4/4
     \tempo "Allegro" 4 = 120
   \set Score.tempoHideNote = ##t
  \relative c' {
  c4. d8 e4 e d8 c d e c4 r c4. d8 e4 e d8 c d e c2 c8 d e f g g g4 r1
  g8 g g4 r2 g4 f e d c4. d8 e4 e d8 c d e c4 r4 c4. d8 e4 e d8 c d e c2
}
     }
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 4/4
  \relative c' { r1 r2. g4 r1 r1 r1 g8 f e d c c c4 r2 g'8 g g4 g1 r1 r2. g4 r1 r1 }
}
  >>
  
  \layout { }

 \midi { }
}