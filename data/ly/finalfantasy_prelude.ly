\version "2.15.40"

 \header {
  title = "Prelude (from Final Fantasy VII)"
  composer = "Nobuo Uematsu"
}

 \new PianoStaff <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 4/4
   \tempo "Andante" 4 = 80
   \relative c {
  r4 c'16 d e g c d e g c d e g 
  | % 2
 c g e d c g e d c g e d c r8.
  | % 3
  r4 a16 b c e a b c e a b c e 
  | % 4
  a e c b a e c b a e c b a r8. 
  | % 5
  r4 c16 d e g c d e g c d e g 
  | % 6
  c g e d c g e d c g e d c r8. 
  | % 7
  r4 a16 b c e a b c e a b c e 
  | % 8
  a e c b a e c b a e c b a r8. 
  | % 9
  r4 a16 c f g a c f g a c f g 
  | % 10
  a g f c a g f c a g f c a r8. 
  | % 11
  r4 b16 d g a b d g a b d g a 
  | % 12
  r4 g,,,16 c d f g c d f g c d g 
  | % 13
  <c>1
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 4/4
   \relative c {
     c16 d e g r2.
     | % 2
     r2. r16 g e d
     | % 3
     a16 b c e r2.
     | % 4
     r2. r16 e c b
     | % 5
     c16 d e g r2.
     | % 6
     r2. r16 g16 e d
     | % 7
     a b c e r2.
     | % 8
     r2. r16 e c b
     | % 9
     a16 c f g r2.
     | % 10
     r2. r16 g f c
     | % 11
     g c d f r2.
     |% 12
     g16 c d f r2.
     | % 13
     r1
   }
   \bar "|." \bar "|."
 }
 >>

 \layout { }

 \midi { }

}
