\header {
  title = "Hungarian Dance"
  composer = "Johannes Brahms"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
   r2. e4 | g2. e4 | dis2 e4 gis | e1 | c2 d4 e | r1 | r1 | r1 
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
   b'1 | r1 | r | r | r | b | a4 g fis b | e,1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  \layout { }

 \midi { }
}