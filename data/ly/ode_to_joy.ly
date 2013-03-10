\version "2.16.0"

 \header {
  title = "Ode to Joy"
  composer = "Ludwig van Beethoven"
  arranger = "arr. Peter Edvinsson"
}

\score {

 \new PianoStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key f \major
   \time 4/4
   \tempo "Moderato" 4 = 92
   \set Score.tempoHideNote = ##t
   \relative c' { 
     a' a bes c
     | % 2
     c bes a g
     | % 3
     f f g a
     | % 4
     a4. g8 g2
     | % 5
     a4 a bes c
     | % 6
     c bes a g
     | % 7
     f f g a
     | % 8
     g4. f8 f2
     | % 9
     g4 g a f
     | % 10
     g a8 bes8 a4 f
     |% 11
     g a8 bes8 a4 g
     | % 12
     f g c,2
     | % 13
     a'4 a bes c
     | % 14
     c bes a g
     | % 15
     f f g a
     | % 16
     g4. f8 f2
     | % 17
   }
   \bar "|."
 }

 \new Staff = "down" {
   \clef bass
   \key f \major
   \time 4/4
   \relative c' {
     f,1
     | % 3
     c
     |% 4
     f
     | % 5
     c
     |% 6
     f
     | % 7
     c
     | % 8
     f
     | % 9
     c2 f2
     | % 7
     c f
     | % 8
     c f
     | % 9
     c f
     | % 10
     f c
     | % 11
     f1
     | % 12
     c
     | % 13
     f
     | % 14
     c2 f2
     | % 15
   }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}
