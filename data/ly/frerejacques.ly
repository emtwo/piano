\header {
  title = "Frere Jacques"
  composer = "Traditional"
}

\version "2.16.0"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 4/4
     \tempo "Moderato" 4 = 90
   \set Score.tempoHideNote = ##t
  \relative c' { c d e c c d e c e f g2 e4 f g2 g8 a g f e4 c g'8 a g f e4 c c  r c2 c4 r c2 }
}
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 4/4
  \relative c { r1 r1 c4 d e c c d e c e f g2 e4 f g2 e4 <f g> e2 e4 <f g> e2 }
}
  >>
  \layout { }

 \midi { }
}