\header {
  title = "Hungarian Dance 5"
  composer = "Johannes Brahms"
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
   \relative c' { r2. e4 | g2. e4 | dis2 e4 gis | e1 | c2 d4 e | r1 | r1 | r1 }
}

\new Staff = "lower"  {
  \clef bass
  \key c \major
  \time 4/4
   \relative c { b'1 | r1 | r | r | r | b | a4 g fis b | e,1 }
}
  >>
  \layout { }

 \midi { }
}