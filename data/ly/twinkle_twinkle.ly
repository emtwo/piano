\version "2.15.40"

 \header {
  title = "Twinkle Twinkle Little Star"
  composer = "Traditional"
}

\score {

 \new PianoStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 2/4
   \tempo 4 = 100
   \relative c {
  c'4 c g' g 
  | % 2
  a a g2 
  | % 3
  f4 f e e 
  | % 4
  d4 d4 c2 
  | % 5
  g'4 g f f 
  | % 6
  e e <d>2 
  | % 7
  g4 g f f 
  | % 8
  e e8( f8) e4( d4) 
  | % 9
  c4 c g' g 
  | % 10
  a a <g >2 
  | % 11
  f4 f e e 
  | % 12
  <d>4 <d>8( e8) <c>2 
  | % 13
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 2/4
   \relative c {
       c e 
  | % 2
  f e 
  | % 3
  d c 
  | % 4
  f4 g4 e2 
  | % 5
  e d
  | % 6
  c b4 
  | % 7
  g' e2 
  | % 8
  d c2 
  | % 9
  c4( b4) c2 e 
  | % 10
  f e 
  | % 11
  d c 
  | % 12
  f4 g4 c,2
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
