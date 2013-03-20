\header {
  title = "Clementine"
  composer = "Traditional"
}
\version "2.15.40"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key c \major
  \time 3/4
     \tempo "Andante" 4 = 80
   \set Score.tempoHideNote = ##t
  \relative c' { r2 c8 c  c4 r4 e8 e e4 c c8 e g4 g f8 e d2 d8 e f4 f e8 d e4 c c8 e d4 r4 b8 d c2. }
}
     \new Staff = "lower" {
  \clef bass
  \key c \major
  \time 3/4
  \relative c { r2. r4 g' r r2. r r r r r4 g r4 r2. }
}
  >>
  \layout { }

 \midi { }
}