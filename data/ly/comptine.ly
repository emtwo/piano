\version "2.12.2"

%
% Comptine d'un autre été - L'après-midi by Yann Tiersen
% Transcribed by Ian Chard <ian@chard.org> 5/1/2010
% Version 1
%
% OK, so I'm not brilliant at Lilypond -- to be honest I don't really
% understand most of it! -- but hopefully this should generate a reasonable
% score.
%

#(set-default-paper-size "a4")

lh = \relative c
  { <e e'>8 b' <g e'> b <e, e'> b' <g e'> b |
    <d, d'> b' <g d'> b <d, d'> b' <g d'> b | 
    <d, d'> b' <fis d'> b <d, d'> b' <fis d'> b |
    <d, d'> a' <fis d'> a <d, d'> a' <fis d'> a | }

%NEW STAFF

rh = \relative c''
  { r1\mp r r r

    r8 g16 fis g8 b16 c b2 |
    r8 fis16 g fis8 g16 a g2 |
    r8 fis16 e fis8 b16 c b2 |
    r8 fis16 e fis2. |

    r8 g16 fis g8 b16 c b2 |
    r8 fis16 g fis8 g16 a g2 |
    r8 fis16 e fis8 b16 c b2 |
    r8 fis16 e fis2. |

    e'4. b8~ b2 |
    d4. b8~ b2 |
    fis'4. b,8~ b2 |
    fis'4. a,8~ a2 |

    <g' b,>4. <e g,>8~ <e g,>2 |
    <g b,>4. <d g,>8~ <d g,>2 |
    <fis b,>4. <d fis,>8~ <d fis,>2 |
    <fis a,>4. <d fis,>8~ <d fis,>2 |

    b16 e b' b, e b' b, e b' b, e b' b, e c e |
    b d b' b, d b' b, d b' b, d b' b, d a d |
    fis, b fis' fis, b fis' fis, b fis' fis, b fis' fis, b g b |
    a d a' a, d a' a, d a' a, d a' a, d g, d' |

    b16 e b' b, e b' b, e b' b, e b' b, e c e |
    b d b' b, d b' b, d b' b, d b' b, d a d |
    fis, b fis' fis, b fis' fis, b fis' fis, b fis' fis, b g b |
    a d a' a, d a' a, d a'^"molto rit." a, d a' a, d a'8 |

    r8^"a tempo"  g16 fis g8 b16 c b2 |
    r8 fis16 g fis8 g16 a g2 |
    r8 fis16 e fis8 b16 c b2 |
    r8 fis16 e fis2. |

    r8 g16 fis g8 b16 c b2 |
    r8 fis16 g fis8 g16 a g2 |
    r8 fis16 e fis8 b16 c b2 |
    r8 fis16 e fis2. |

    e'4. b8~ b2 |
    d4. b8~ b2 |
    fis'4. b,8~ b2 |
    fis'4. a,8~ a2 |

    <g' b,>4. <e g,>8~ <e g,>2 |
    <g b,>4. <d g,>8~ <d g,>2 |
    <fis b,>4. <d fis,>8~ <d fis,>2 |
    <fis a,>4. <d fis,>8~ <d fis,>2 |

\ottava #1
    b16 e b' b, e b' b, e b' b, e b' b, e c e |
    b d b' b, d b' b, d b' b, d b' b, d a d |
    fis, b fis' fis, b fis' fis, b fis' fis, b fis' fis, b g b |
    a d a' a, d a' a, d a' a, d a' a, d g, d' |

    b16 e b' b, e b' b, e b' b, e b' b, e c e |
    b d b' b, d b' b, d b' b, d b' b, d a d |
    fis, b fis' fis, b fis' fis, b fis' fis, b fis' fis, b g b |
    a^"rit."\> d a' a, d a' a, d a' a, d a' a, d a' g\! |

    <e g,>1^\fermataMarkup\pp
\ottava #0
  }

right = {
	\time 2/2
	\key e \minor
	\clef treble
	\tempo 4=100

	\rh
	\bar "|."
}


left = {
	\time 2/2
	\key e \minor
	\clef bass

	\set Staff.pedalSustainStyle = #'bracket

	\relative c { <e e'>8\sustainOn b' <g e'> b <e, e'>\sustainOff\sustainOn b' <g e'> b |
	  <d, d'>\sustainOff b'_\markup { \italic Ped. \italic simile } <g d'> b <d, d'> b' <g d'> b | 
	  <d, d'> b' <fis d'> b <d, d'> b' <fis d'> b |
	  <d, d'> a' <fis d'> a <d, d'> a' <fis d'> a | }

	\lh \lh \lh \lh \lh \lh \lh \lh \lh \lh \lh

	\relative c { <e e'>8 b' <g e'> b <e, e'> b' <g e'> b |
	  <d, d'> b' <g d'> b <d, d'> b' <g d'> b | 
	  <d, d'> b' <fis d'> b <d, d'> b' <fis d'> b |
	  <d, d'> a' <fis d'> a <d, d'> a' <fis d'>4 | }

	<e b>1
	\bar "|."
}


\header {
	title = "Comptine d'un autre été - L'après-midi"
	composer = "Yann Tiersen"
	arranger = "Transcribed by Ian Chard"
}

\score {
	\context PianoStaff <<
		\new Staff \right
		\new Staff \with { \override VerticalAxisGroup #'minimum-Y-extent = #'(-6 . 6) } \left
	>>

	\layout { }
	\midi { }
}
