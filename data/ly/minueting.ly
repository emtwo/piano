\header {
  title = "Minuet in G"
  composer = "Bach"
}
\version "2.16.2"

upper = \relative c'' {
  \clef treble
  \key g \major
  \time 3/4
  d4 g,8 a b c | d4 g, g | e' c8 d e fis | g4 g, g | c d8 c b a | b4 c8 b a g |
  fis4 g8 a b g | b4 a2 | d4 g,8 a b c | d4 g, g | e' c8 d e fis | g4 g, g | c d8 c b a | 
  b4 c8 b a g | a4 b8 a g fis | g2.
}

lower = \relative c {
  \clef bass
  \key g \major
  \time 3/4
  <e b'>2 a4 | b2. | c2. | b | a | g | d'4 b g | d'2 c4 |
  b2 a4 | g b g | c2. | b | a | g | c4 d8 c b a | g2.
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
}