\version "2.16.0"

 \header {
  title = "Some Easy Bullshit"
  composer = "Steven"
}

\score {

 \new GrandStaff
 <<
 \new Staff = "up" {
   \clef treble
   \key c \major
   \time 4/4
   \relative c' { e d c d r4 e <e g> }
   \bar "|."
 }

 \new Staff = "down" {
   \clef treble
   \key c \major
   \time 4/4
   \relative c' { r4 r4 r4 r4 r4 r4 c }
   \bar "|." \bar "|."
 }
>>

 \layout { }

 \midi { }

}