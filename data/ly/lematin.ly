\version "2.15.40"
 
\header {
 title = "Le Matin"
 composer = "Yann Tiersen"
 }
 
\score {
 \new PianoStaff
 <<
 \new Staff = "up" {
 \clef treble
 \key ees \major
 \numericTimeSignature
 \time 4/4
 \tempo "Contemplative" 4 = 110
 \set Score.tempoHideNote = ##t
 \relative c' { 
es2 f4. g8~g2 d4. c8~c2 d4. es8~es2 as,4. bes8~bes2  
f'4. g8~g2 d4. c8~c2 d4. es8~es2 f4. g8~g4.
  g'8 bes, g~g4 r4. f'8 bes, f~f4 r4. es'8 g, es~es4 r4. d'8 as d, c' c,~c4.
  bes'8 es, bes~bes4 r4. bes'8 d, bes~bes4 r4. c'8 es, c~c4 r4. bes'8 es, bes as' as, 
g'2 f4 es8 d~d2 es4 f8 g~g2 f4 es8 d~d2 c g' f4 ees8 d~d2 ees4 f8 ees~ees2 d4 c8 bes~bes2 aes
 <ees' ees'> <f f'>4. <g g'>8~<g g'>2 <d d'>4. <c c'>8~<c c'>2 <d d'>4. <ees ees'>8~<ees ees'>2
 <aes, aes'>4. <bes bes'>8~<bes bes'>2 <f' f'>4. <g g'>8~<g g'>2 <d d'>4. <c c'>8~<c c'>2 <d d'>4. <ees ees'>8~<ees ees'>2
 <f f'>4. <g g'>8\fermata
 
}
 \bar "|."
 }
 
\new Staff = "down" {
 \clef bass
 \key ees \major
 \numericTimeSignature
 \time 4/4
 \relative c { 
es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 
es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 \bar "||"
 es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 
es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 \bar "||"
 es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 
es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 \bar "||"
 es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 
es,4 <bes' g'>2 <bes g'>4 g <d' bes'>2 <d bes'>4 c <es g>2 <es g>4 as, <es' f>2 <es f>4 \bar "||"
 
}
 \bar "|." \bar "|."
 }
 >>
 
\layout { }
 
\midi { }
 
}
