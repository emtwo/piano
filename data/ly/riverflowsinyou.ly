\header {
  title = "River flows in you"
  composer = "Yiruma"
}
\version "2.15.40"

\score {
  \new PianoStaff <<
     \new Staff = "upper" {
  \clef treble
  \key a \major
  \time 4/4
     \tempo "Adagio" 4 = 65
   \set Score.tempoHideNote = ##t
  \relative c''' { a8 gis8 a8 gis8 a8 e8 a8 d,8 ~ |
  d2 a4 cis4 |
  a'8 gis8 a8 gis8 a8 e8 a8 d,8 ~ |
  d2 a4 cis4 | 
  a'8 gis16 a16 ~ a16 a,16 gis'16 a16 ~ a16 a,16  e'16 a16 ~ a16 a,16 d16 a16 |
  cis8^[ d8] <a e>8^[ cis] <gis b>4. a16^[ gis16] |
  a4 ~ a16 e16 a16 b16 cis4. cis16 d16 |
  e4. d16 cis16 b2 |
  a'8 gis16 a16 ~ a16 a,16 gis'16 a16 ~ a16 a,16  e'16 a16 ~ a16 a,16 d16 a16 |
  cis8^[ d8] e8^[ cis'8] b8^[ e,8] b'8 a16 gis16 |
  <e a>4. a,16 b16 cis8 e,8 a8 cis16 d16 |
  e8 e,8 cis'8 d16 cis16 b4 a'16 b16 a16 gis16 |
  a16 a,16 e'16 a,16 a'16 b16 a16 gis16 a16 a,16 e'16 a,16 a'16 b16 a16 gis16 |
  a16 b16 cis16 d16 e16 cis16 b16 a16 gis8 b,8 a'16 b16 a16 gis16 |
  a16 a,16 e'16 a,16 a'16 b16 a16 gis16 a16 a,16 e'16 a,16 a'16 b16 a16 gis16 |
  }
}
     \new Staff = "lower" {
  \clef bass
  \key a \major
  \time 4/4
  \relative c { fis8 cis'8 fis4 d,8 a'8 e'4 ~ |
  e1 |
  fis,8 cis'8 fis4 d,8 a'8 e'4 ~ |
  e1 |
  fis,8 cis'8 fis4 d,8 a'8 e'8 d,8 |
  a8 e'8 cis'4 gis8 b8 e4 |
  fis,8 cis'8 fis4 d,8 a'8 e'4 |
  a,,8 e'8 cis'4 e,8 b'8 gis'4 |
  fis,8 cis'8 fis4 d,8 a'8 e'8 d,8 |
  a8 e'8 cis'8 a,8 e'8 b'8 gis'4 |
  a,,8 e'8 cis'4 e,8 b'8 gis'4 |
  a,,8 e'8 cis'4 gis8 b8 e4 |
  fis,8 cis'8 fis8 fis,8 d8 a'8 e'8 d,8 |
  a8 e'8 cis'8 a,8 e'8 b'8 e8 e,8 |
  fis8 cis'8 fis8 fis,8 d8 a'8 e'8 d,8 }
}
  >>
  
  \layout { }

 \midi { }
}