\version "2.15.40"

 \header {
  title = "Some Easy Notes"
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
