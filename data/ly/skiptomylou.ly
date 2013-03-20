\header {
  title = "Skip to My Lou"
  composer = "American Frontier Song"
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
  \relative c' { e4 e8 e c4 c e e g2 d4 d8 d b4 b4 d d f2 e4 e8 e c4 c e e g2 d4 e8 f e4 d 
  c2 c e c e4 e8 e g2 d b d4 d8 d f2 e2 c e4 e8 e g2 d4 e8 f e4 d c2 c }
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