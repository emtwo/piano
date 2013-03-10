\header {
  title="Canon in D"
  composer = "Pachelbel"
}

\version "2.16.2"

upper = \relative c' {
  \clef treble
  \key c \major
  \time 4/4
  r4 e g c |
  r4 d, g b |
  r4 c, e a |
  r4 b, e g |
  r4 c, f a |
  r4 c, e g |
  r4 c, f a |
  r4 d, g b |
  c1 |   b1 |   a1 |   g1 |   f1  |    g1 |   a1 |  b1 |  
  <c e>1 |  <b d>1 |  <a c>1 |  <g b>1 |  <f a>1 |  <e g>1 |  <f a>1 |  <g b>1 |
  e'4 c8 d8 e4 d8 c8 | d b c d e d c b | c4 a8 b8 c4 e,8 f8 | g a g f g c b c |
  a4 c8 b8 a4 g8 f8 | g f e f g a b c | a4 c8 b8 c4 b8 c8 | b g a b c d e f |
  <e g>2. g4 | g a g f | e2. e4 | e4 f e d | c b a b | c2 g | c4 bes a2 | b1 | r2 <c e>2 |
  r2 <b d>2 | r2 <a c> | r <g b> | r <f a> | r <e g> | r <f a> | r <g b> | <e c'>1
}

lower = \relative c {
  \clef bass
  \key c \major
  \time 4/4
  c'1 |  g1 |  a1 |  e1 |  f1 |  c1 |  f1 |  g1 |
  c,4 g' c e | g,, d' g b | a, e' a c | e,, b' e g | g, c f a | c,, g' c e | f, c' f a | g, d' g b | 
  c, g' c e | g,, d' g b | a, e' a c | e,, b' e g | g, c f a | c,, g' c e | f, c' f a | g, d' g b |
  c, e c'2 | g,4 d' g2 | a,4 e' a2 | e,4 b' e2 | g,4 c f2 | c,4 g' c2 | f,4 c' f2 | g,4 d' g2 |
  c,4 g' c g | g, d' g d | a e' a e | e, b' e b | f c' f c | c g' c g | f, c' f c | 
  g d' g2 | c,,4 g' c2 | g4 d' g2 | a,4 e' a2 | e,4 b' e2 | f,4 c' f2| c,4 g' c2 | f,4 c' f2 | g,4 d' g2 | <c, g>1
}

\score {
  \new PianoStaff <<
     \new Staff = "upper" \upper
     \new Staff = "lower" \lower
  >>
  
  \layout { }

 \midi { }
}