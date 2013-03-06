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
   \set Score.tempoHideNote = ##t
   \relative c {
  c'4 c
  | % 2
  g' g 
  | % 3
  a a
  | % 4
  g2 
  | % 5
  f4 f
  | % 6
  e e 
  | % 7
  d4 d4
  | % 8
  c2 
  | % 9
  g'4 g
  | % 10
  f f
  | % 11
  e e
  | % 12
  d2 
  | % 13
  g4 g
  | % 14
  f f 
  | % 15
  e e8( f8)
  | % 16
  e4( d4) 
  | % 17
  c4 c
  | % 18
  g' g 
  | % 19
  a a
  |% 20
  <g >2 
  | % 21
  f4 f
  |% 22
  e e 
  | % 23
  <d>4 <d>8( e8)
  | % 24
  <c>2 
  | % 25
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key c \major
   \time 2/4
   \relative c {
  c
  |% 2
  e
  | % 3
  f
  |% 4
  e 
  | % 5
  d
  | % 6
  c 
  | % 7
  f4 g4
  |% 8
  e2 
  | % 9
  e d
  | % 10
  c
  | %11
  b4 g'
  | % 12
  e2
  | % 13
  d 
  |% 14
  c2 
  | % 15
  c4( b4)
  | % 16
  c2 
  | % 17
  e 
  | % 18
  f
  | % 19
  e 
  | % 20
  d
  | % 21
  c 
  | % 22
  f4 g4
  | % 23
  c,2
  | % 24
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
