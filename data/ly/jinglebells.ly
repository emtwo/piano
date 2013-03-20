\header {
  title = "Jingle Bells"
  composer = "James Pierpont"
}
\version "2.15.40"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 4/4
     \tempo "Moderato" 4 = 90
   \set Score.tempoHideNote = ##t
  \relative c' { e4 e e2 e4 e e2 e4 g c, d e1 f4 f f f f e e e e d d e d2 g2 e4 e e2 e4 e e2 e4 g c, d e1 f4 f f f f e e e g g f d c1 }
}
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 4/4
  \relative c { r1 r1 r1 r1 r r r r r r r r r r r r }
}
  >>
  
  \layout { }

 \midi { }
}