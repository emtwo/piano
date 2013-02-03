package score;

/**
 * MOSTLY TAKEN FROM PRESTO TODO: GIVE CREDIT
 */

public class Glyph {

    public final static int scripts_sforzato = 0;
    public final static int scripts_espr = 1;
    public final static int flags_ugrace = 2;
    public final static int flags_dgrace = 3;
    public final static int accidentals_leftparen = 4;
    public final static int accidentals_rightparen = 5;
    public final static int scripts_arpeggio = 6;
    public final static int scripts_arpeggio_arrow_M1 = 7;
    public final static int scripts_arpeggio_arrow_1 = 8;
    public final static int scripts_trill_element = 9;
    public final static int zero = 10;
    public final static int one = 11;
    public final static int two = 12;
    public final static int three = 13;
    public final static int four = 14;
    public final static int five = 15;
    public final static int six = 16;
    public final static int seven = 17;
    public final static int eight = 18;
    public final static int nine = 19;
    public final static int period = 20;
    public final static int comma = 21;
    public final static int plus = 22;
    public final static int hyphen = 23;
    public final static int scripts_downbow = 24;
    public final static int scripts_upbow = 25;
    public final static int brackettips_down = 26;
    public final static int brackettips_up = 27;
    public final static int scripts_rcomma = 28;
    public final static int scripts_lcomma = 29;
    public final static int scripts_rvarcomma = 30;
    public final static int scripts_lvarcomma = 31;
    public final static int scripts_caesura = 32;
    public final static int scripts_caesura_straight = 33;
    public final static int scripts_caesura_curved = 34;
    public final static int noteheads_sM1 = 35;
    public final static int clefs_C = 36;
    public final static int clefs_F = 37;
    public final static int clefs_G = 38;
    public final static int clefs_tab = 39;
    public final static int clefs_percussion = 40;
    public final static int clefs_C_change = 41;
    public final static int clefs_F_change = 42;
    public final static int clefs_G_change = 43;
    public final static int clefs_tab_change = 44;
    public final static int clefs_percussion_change = 45;
    public final static int scripts_coda = 46;
    public final static int scripts_varcoda = 47;
    public final static int f = 48;
    public final static int m = 49;
    public final static int p = 50;
    public final static int r = 51;
    public final static int s = 52;
    public final static int z = 53;
    public final static int space = 54;
    public final static int scripts_ufermata = 55;
    public final static int scripts_ushortfermata = 56;
    public final static int scripts_ulongfermata = 57;
    public final static int scripts_uverylongfermata = 58;
    public final static int scripts_dfermata = 59;
    public final static int scripts_dshortfermata = 60;
    public final static int scripts_dlongfermata = 61;
    public final static int scripts_dverylongfermata = 62;
    public final static int accidentals_M1 = 63;
    public final static int accidentals_mirroredflat = 64;
    public final static int accidentals_mirroredflat_backslash = 65;
    public final static int accidentals_M2 = 66;
    public final static int accidentals_flat = 67;
    public final static int accidentals_flat_arrowup = 68;
    public final static int accidentals_flat_arrowdown = 69;
    public final static int accidentals_flat_arrowboth = 70;
    public final static int accidentals_flat_slash = 71;
    public final static int accidentals_flat_slashslash = 72;
    public final static int accidentals_M3 = 73;
    public final static int accidentals_mirroredflat_flat = 74;
    public final static int accidentals_M4 = 75;
    public final static int accidentals_flatflat = 76;
    public final static int accidentals_flatflat_slash = 77;
    public final static int noteheads_s0harmonic = 78;
    public final static int noteheads_s2harmonic = 79;
    public final static int scripts_flageolet = 80;
    public final static int scripts_open = 81;
    public final static int scripts_thumb = 82;
    public final static int noteheads_s2 = 83;
    public final static int noteheads_s1 = 84;
    public final static int scripts_tenuto = 85;
    public final static int scripts_uportato = 86;
    public final static int scripts_dportato = 87;
    public final static int scripts_mordent = 88;
    public final static int scripts_prall = 89;
    public final static int scripts_prallprall = 90;
    public final static int scripts_prallmordent = 91;
    public final static int scripts_upprall = 92;
    public final static int scripts_upmordent = 93;
    public final static int scripts_pralldown = 94;
    public final static int scripts_downprall = 95;
    public final static int scripts_downmordent = 96;
    public final static int scripts_prallup = 97;
    public final static int scripts_lineprall = 98;
    public final static int accidentals_0 = 99;
    public final static int accidentals_natural = 100;
    public final static int accidentals_natural_arrowup = 101;
    public final static int accidentals_natural_arrowdown = 102;
    public final static int accidentals_natural_arrowboth = 103;
    public final static int pedal__ = 104;
    public final static int pedal_P = 105;
    public final static int pedal_d = 106;
    public final static int pedal_e = 107;
    public final static int pedal_Ped = 108;
    public final static int pedal_star = 109;
    public final static int pedal_M = 110;
    public final static int rests_M3 = 111;
    public final static int rests_M2 = 112;
    public final static int rests_M1 = 113;
    public final static int rests_2 = 114;
    public final static int rests_2classical = 115;
    public final static int rests_5 = 116;
    public final static int rests_6 = 117;
    public final static int rests_7 = 118;
    public final static int rests_1 = 119;
    public final static int rests_1o = 120;
    public final static int rests_3 = 121;
    public final static int rests_4 = 122;
    public final static int rests_0 = 123;
    public final static int rests_0o = 124;
    public final static int scripts_segno = 125;
    public final static int noteheads_s0 = 126;
    public final static int scripts_umarcato = 127;
    public final static int scripts_dmarcato = 128;
    public final static int accidentals_1 = 129;
    public final static int accidentals_sharp_slashslash_stem = 130;
    public final static int accidentals_sharp_slashslashslash_stem = 131;
    public final static int accidentals_2 = 132;
    public final static int accidentals_sharp = 133;
    public final static int accidentals_sharp_slashslashslash_stemstem = 134;
    public final static int accidentals_sharp_arrowup = 135;
    public final static int accidentals_sharp_arrowdown = 136;
    public final static int accidentals_sharp_arrowboth = 137;
    public final static int accidentals_3 = 138;
    public final static int accidentals_sharp_slashslash_stemstemstem = 139;
    public final static int accidentals_4 = 140;
    public final static int accidentals_doublesharp = 141;
    public final static int scripts_dstaccatissimo = 142;
    public final static int scripts_ustaccatissimo = 143;
    public final static int scripts_staccato = 144;
    public final static int dots_dot = 145;
    public final static int scripts_snappizzicato = 146;
    public final static int scripts_stopped = 147;
    public final static int flags_d3 = 148;
    public final static int flags_u3 = 149;
    public final static int flags_d4 = 150;
    public final static int flags_u4 = 151;
    public final static int flags_d5 = 152;
    public final static int flags_u5 = 153;
    public final static int flags_d6 = 154;
    public final static int flags_u6 = 155;
    public final static int flags_d7 = 156;
    public final static int flags_u7 = 157;
    public final static int timesig_C22 = 158;
    public final static int timesig_C44 = 159;
    public final static int scripts_trill = 160;
    public final static int scripts_turn = 161;
    public final static int scripts_reverseturn = 162;
    public final static int arrowheads_open_11 = 163;
    public final static int arrowheads_open_1M1 = 164;
    public final static int arrowheads_open_0M1 = 165;
    public final static int arrowheads_open_01 = 166;
    public final static int arrowheads_close_11 = 167;
    public final static int arrowheads_close_1M1 = 168;
    public final static int arrowheads_close_0M1 = 169;
    public final static int arrowheads_close_01 = 170;
    public final static int scripts_upedalheel = 171;
    public final static int scripts_dpedalheel = 172;
    public final static int scripts_upedaltoe = 173;
    public final static int scripts_dpedaltoe = 174;
    public final static int accordion_accFreebase = 175;
    public final static int accordion_accDiscant = 176;
    public final static int accordion_accStdbase = 177;
    public final static int accordion_accBayanbase = 178;
    public final static int accordion_accDot = 179;
    public final static int accordion_accOldEE = 180;
    public final static int noteheads_s0diamond = 181;
    public final static int noteheads_s1diamond = 182;
    public final static int noteheads_s2diamond = 183;
    public final static int noteheads_s0triangle = 184;
    public final static int noteheads_d1triangle = 185;
    public final static int noteheads_u1triangle = 186;
    public final static int noteheads_d2triangle = 187;
    public final static int noteheads_u2triangle = 188;
    public final static int noteheads_s0cross = 189;
    public final static int noteheads_s1cross = 190;
    public final static int noteheads_s2cross = 191;
    public final static int noteheads_s2xcircle = 192;
    public final static int noteheads_s0slash = 193;
    public final static int noteheads_s1slash = 194;
    public final static int noteheads_s2slash = 195;



    public static char getChar(int char_id) {
        switch (char_id) {
            case scripts_sforzato:			    return 0xe100;
            case scripts_espr:			        return 0xe101;
            case flags_ugrace:			        return 0xe102;
            case flags_dgrace:			        return 0xe103;
            case accidentals_leftparen:			return 0xe104;
            case accidentals_rightparen:		return 0xe105;
            case scripts_arpeggio:			    return 0xe106;
            case scripts_arpeggio_arrow_M1:		return 0xe107;
            case scripts_arpeggio_arrow_1:		return 0xe108;
            case scripts_trill_element:			return 0xe109;
            case zero:		                	return 0x30;
            case one:			                return 0x31;
            case two:			                return 0x32;
            case three:		                	return 0x33;
            case four:		                	return 0x34;
            case five:		                	return 0x35;
            case six:		                	return 0x36;
            case seven:		                	return 0x37;
            case eight:		                	return 0x38;
            case nine:		                	return 0x39;
            case period:	                	return 0x2e;
            case comma:		                	return 0x2c;
            case plus:			                return 0x2b;
            case hyphen:			            return 0x2d;
            case scripts_downbow:	    		return 0xe10a;
            case scripts_upbow:			        return 0xe10b;
            case brackettips_down:	    		return 0xe10c;
            case brackettips_up:	    		return 0xe10d;
            case scripts_rcomma:	    		return 0xe10e;
            case scripts_lcomma:	    		return 0xe10f;
            case scripts_rvarcomma:	    		return 0xe110;
            case scripts_lvarcomma:	    		return 0xe111;
            case scripts_caesura:	    		return 0xe112;
            case scripts_caesura_straight:		return 0xe113;
            case scripts_caesura_curved:		return 0xe114;
            case noteheads_sM1:		        	return 0xe115;
            case clefs_C:	    	         	return 0xe116;
            case clefs_F:	            		return 0xe117;
            case clefs_G:	            		return 0xe118;
            case clefs_tab:	            		return 0xe119;
            case clefs_percussion:	    		return 0xe11a;
            case clefs_C_change:	    		return 0xe11b;
            case clefs_F_change:	    		return 0xe11c;
            case clefs_G_change:	    		return 0xe11d;
            case clefs_tab_change:	    		return 0xe11e;
            case clefs_percussion_change:		return 0xe11f;
            case scripts_coda:			        return 0xe120;
            case scripts_varcoda:			    return 0xe121;
            case f:	    		                return 0x66;
            case m:		                    	return 0x6d;
            case p:	                    		return 0x70;
            case r:		                    	return 0x72;
            case s:		                    	return 0x73;
            case z:	                	    	return 0x7a;
            case space:			                return 0x20;
            case scripts_ufermata:			    return 0xe122;
            case scripts_ushortfermata:			return 0xe123;
            case scripts_ulongfermata:			return 0xe124;
            case scripts_uverylongfermata:		return 0xe125;
            case scripts_dfermata:			    return 0xe126;
            case scripts_dshortfermata:			return 0xe127;
            case scripts_dlongfermata:			return 0xe128;
            case scripts_dverylongfermata:		return 0xe129;
            case accidentals_M1:		        return 0xe12a;
            case accidentals_mirroredflat:		return 0xe12b;
            case accidentals_mirroredflat_backslash:	return 0xe12c;
            case accidentals_M2:			    return 0xe12d;
            case accidentals_flat:			    return 0xe12e;
            case accidentals_flat_arrowup:		return 0xe12f;
            case accidentals_flat_arrowdown:	return 0xe130;
            case accidentals_flat_arrowboth:	return 0xe131;
            case accidentals_flat_slash:		return 0xe132;
            case accidentals_flat_slashslash:	return 0xe133;
            case accidentals_M3:			    return 0xe134;
            case accidentals_mirroredflat_flat:	return 0xe135;
            case accidentals_M4:			    return 0xe136;
            case accidentals_flatflat:			return 0xe137;
            case accidentals_flatflat_slash:	return 0xe138;
            case noteheads_s0harmonic:			return 0xe139;
            case noteheads_s2harmonic:			return 0xe13a;
            case scripts_flageolet:	    		return 0xe13b;
            case scripts_open:		        	return 0xe13c;
            case scripts_thumb:	        		return 0xe13d;
            case noteheads_s2:		        	return 0xe13e;
            case noteheads_s1:			        return 0xe13f;
            case scripts_tenuto:	    		return 0xe140;
            case scripts_uportato:	    		return 0xe141;
            case scripts_dportato:	    		return 0xe142;
            case scripts_mordent:	    		return 0xe143;
            case scripts_prall:		        	return 0xe144;
            case scripts_prallprall:			return 0xe145;
            case scripts_prallmordent:			return 0xe146;
            case scripts_upprall:	    		return 0xe147;
            case scripts_upmordent:	    		return 0xe148;
            case scripts_pralldown:	    		return 0xe149;
            case scripts_downprall:		    	return 0xe14a;
            case scripts_downmordent:			return 0xe14b;
            case scripts_prallup:		    	return 0xe14c;
            case scripts_lineprall:		    	return 0xe14d;
            case accidentals_0:			        return 0xe14e;
            case accidentals_natural:			return 0xe14f;
            case accidentals_natural_arrowup:	return 0xe150;
            case accidentals_natural_arrowdown:	return 0xe151;
            case accidentals_natural_arrowboth:	return 0xe152;
            case pedal__:			            return 0xe153;
            case pedal_P:	            		return 0xe154;
            case pedal_d:           			return 0xe155;
            case pedal_e:           			return 0xe156;
            case pedal_Ped:	            		return 0xe157;
            case pedal_star:			        return 0xe158;
            case pedal_M:           			return 0xe159;
            case rests_M3:		            	return 0xe15a;
            case rests_M2:	            		return 0xe15b;
            case rests_M1:	            		return 0xe15c;
            case rests_2:		            	return 0xe15d;
            case rests_2classical:		    	return 0xe15e;
            case rests_5:		            	return 0xe15f;
            case rests_6:	            		return 0xe160;
            case rests_7:	            		return 0xe161;
            case rests_1:	            		return 0xe162;
            case rests_1o:          			return 0xe163;
            case rests_3:           			return 0xe164;
            case rests_4:	            		return 0xe165;
            case rests_0:	            		return 0xe166;
            case rests_0o:	            		return 0xe167;
            case scripts_segno:		        	return 0xe168;
            case noteheads_s0:		        	return 0xe169;
            case scripts_umarcato:	    		return 0xe16a;
            case scripts_dmarcato:	    		return 0xe16b;
            case accidentals_1:			        return 0xe16c;
            case accidentals_sharp_slashslash_stem:	return 0xe16d;
            case accidentals_sharp_slashslashslash_stem: return 0xe16e;
            case accidentals_2:			        return 0xe16f;
            case accidentals_sharp:		    	return 0xe170;
            case accidentals_sharp_slashslashslash_stemstem: return 0xe171;
            case accidentals_sharp_arrowup:		return 0xe172;
            case accidentals_sharp_arrowdown:	return 0xe173;
            case accidentals_sharp_arrowboth:	return 0xe174;
            case accidentals_3:			        return 0xe175;
            case accidentals_sharp_slashslash_stemstemstem:	return 0xe176;
            case accidentals_4:			        return 0xe177;
            case accidentals_doublesharp:		return 0xe178;
            case scripts_dstaccatissimo:		return 0xe179;
            case scripts_ustaccatissimo:		return 0xe17a;
            case scripts_staccato:			    return 0xe17b;
            case dots_dot:		            	return 0xe17c;
            case scripts_snappizzicato:			return 0xe17d;
            case scripts_stopped:   			return 0xe17e;
            case flags_d3:	            		return 0xe17f;
            case flags_u3:          			return 0xe180;
            case flags_d4:		            	return 0xe181;
            case flags_u4:          			return 0xe182;
            case flags_d5:          			return 0xe183;
            case flags_u5:	              		return 0xe184;
            case flags_d6:		    	        return 0xe185;
            case flags_u6:		            	return 0xe186;
            case flags_d7:	            		return 0xe187;
            case flags_u7:		            	return 0xe188;
            case timesig_C22:			        return 0xe189;
            case timesig_C44:		            return 0xe18a;
            case scripts_trill:	        		return 0xe18b;
            case scripts_turn:	        		return 0xe18c;
            case scripts_reverseturn:			return 0xe18d;
            case arrowheads_open_11:			return 0xe18e;
            case arrowheads_open_1M1:			return 0xe18f;
            case arrowheads_open_0M1:			return 0xe190;
            case arrowheads_open_01:			return 0xe191;
            case arrowheads_close_11:			return 0xe192;
            case arrowheads_close_1M1:			return 0xe193;
            case arrowheads_close_0M1:			return 0xe194;
            case arrowheads_close_01:			return 0xe195;
            case scripts_upedalheel:			return 0xe196;
            case scripts_dpedalheel:			return 0xe197;
            case scripts_upedaltoe:		        return 0xe198;
            case scripts_dpedaltoe:		        return 0xe199;
            case accordion_accFreebase:			return 0xe19a;
            case accordion_accDiscant:			return 0xe19b;
            case accordion_accStdbase:			return 0xe19c;
            case accordion_accBayanbase:		return 0xe19d;
            case accordion_accDot:			    return 0xe19e;
            case accordion_accOldEE:			return 0xe19f;
            case noteheads_s0diamond:			return 0xe1a0;
            case noteheads_s1diamond:			return 0xe1a1;
            case noteheads_s2diamond:			return 0xe1a2;
            case noteheads_s0triangle:			return 0xe1a3;
            case noteheads_d1triangle:			return 0xe1a4;
            case noteheads_u1triangle:			return 0xe1a5;
            case noteheads_d2triangle:			return 0xe1a6;
            case noteheads_u2triangle:			return 0xe1a7;
            case noteheads_s0cross:	    		return 0xe1a8;
            case noteheads_s1cross:	    		return 0xe1a9;
            case noteheads_s2cross:	    		return 0xe1aa;
            case noteheads_s2xcircle:   		return 0xe1ab;
            case noteheads_s0slash:	    		return 0xe1ac;
            case noteheads_s1slash:	    		return 0xe1ad;
            case noteheads_s2slash:	    		return 0xe1ae;

        }

        // not found
        return (char)-1;
    }

    /*public static int getBaseline(int char_id) {
        switch (char_id) {
            case BASS_CLEF:		return 2;
            case TREBLE_CLEF:	return -2;
            default:			return 0;
        }
    }  */

    public Glyph() {}

    public final static int getGlyph(String glyphName) {
        if(glyphName.equals("scripts.sforzato")) {
            return scripts_sforzato;
        } else if(glyphName.equals("scripts.espr")) {
            return scripts_espr;
        } else if(glyphName.equals("flags.ugrace")) {
            return flags_ugrace;
        } else if(glyphName.equals("flags.dgrace")) {
            return flags_dgrace;
        } else if(glyphName.equals("accidentals.leftparen")) {
            return accidentals_leftparen;
        } else if(glyphName.equals("accidentals.rightparen")) {
            return accidentals_rightparen;
        } else if(glyphName.equals("scripts.arpeggio")) {
            return scripts_arpeggio;
        } else if(glyphName.equals("scripts.arpeggio.arrow.M1")) {
            return scripts_arpeggio_arrow_M1;
        } else if(glyphName.equals("scripts.arpeggio.arrow.1")) {
            return scripts_arpeggio_arrow_1;
        } else if(glyphName.equals("scripts.trill_element")) {
            return scripts_trill_element;
        } else if(glyphName.equals("zero")) {
            return zero;
        } else if(glyphName.equals("one")) {
            return one;
        } else if(glyphName.equals("two")) {
            return two;
        } else if(glyphName.equals("three")) {
            return three;
        } else if(glyphName.equals("four")) {
            return four;
        } else if(glyphName.equals("five")) {
            return five;
        } else if(glyphName.equals("six")) {
            return six;
        } else if(glyphName.equals("seven")) {
            return seven;
        } else if(glyphName.equals("eight")) {
            return eight;
        } else if(glyphName.equals("nine")) {
            return nine;
        } else if(glyphName.equals("period")) {
            return period;
        } else if(glyphName.equals("comma")) {
            return comma;
        } else if(glyphName.equals("plus")) {
            return plus;
        } else if(glyphName.equals("hyphen")) {
            return hyphen;
        } else if(glyphName.equals("scripts.downbow")) {
            return scripts_downbow;
        } else if(glyphName.equals("scripts.upbow")) {
            return scripts_upbow;
        } else if(glyphName.equals("brackettips.down")) {
            return brackettips_down;
        } else if(glyphName.equals("brackettips.up")) {
            return brackettips_up;
        } else if(glyphName.equals("scripts.rcomma")) {
            return scripts_rcomma;
        } else if(glyphName.equals("scripts.lcomma")) {
            return scripts_lcomma;
        } else if(glyphName.equals("scripts.rvarcomma")) {
            return scripts_rvarcomma;
        } else if(glyphName.equals("scripts.lvarcomma")) {
            return scripts_lvarcomma;
        } else if(glyphName.equals("scripts.caesura")) {
            return scripts_caesura;
        } else if(glyphName.equals("scripts.caesura.straight")) {
            return scripts_caesura_straight;
        } else if(glyphName.equals("scripts.caesura.curved")) {
            return scripts_caesura_curved;
        } else if(glyphName.equals("noteheads.sM1")) {
            return noteheads_sM1;
        } else if(glyphName.equals("clefs.C")) {
            return clefs_C;
        } else if(glyphName.equals("clefs.F")) {
            return clefs_F;
        } else if(glyphName.equals("clefs.G")) {
            return clefs_G;
        } else if(glyphName.equals("clefs.tab")) {
            return clefs_tab;
        } else if(glyphName.equals("clefs.percussion")) {
            return clefs_percussion;
        } else if(glyphName.equals("clefs.C_change")) {
            return clefs_C_change;
        } else if(glyphName.equals("clefs.F_change")) {
            return clefs_F_change;
        } else if(glyphName.equals("clefs.G_change")) {
            return clefs_G_change;
        } else if(glyphName.equals("clefs.tab_change")) {
            return clefs_tab_change;
        } else if(glyphName.equals("clefs.percussion_change")) {
            return clefs_percussion_change;
        } else if(glyphName.equals("scripts.coda")) {
            return scripts_coda;
        } else if(glyphName.equals("scripts.varcoda")) {
            return scripts_varcoda;
        } else if(glyphName.equals("f")) {
            return f;
        } else if(glyphName.equals("m")) {
            return m;
        } else if(glyphName.equals("p")) {
            return p;
        } else if(glyphName.equals("r")) {
            return r;
        } else if(glyphName.equals("s")) {
            return s;
        } else if(glyphName.equals("z")) {
            return z;
        } else if(glyphName.equals("space")) {
            return space;
        } else if(glyphName.equals("scripts.ufermata")) {
            return scripts_ufermata;
        } else if(glyphName.equals("scripts.ushortfermata")) {
            return scripts_ushortfermata;
        } else if(glyphName.equals("scripts.ulongfermata")) {
            return scripts_ulongfermata;
        } else if(glyphName.equals("scripts.uverylongfermata")) {
            return scripts_uverylongfermata;
        } else if(glyphName.equals("scripts.dfermata")) {
            return scripts_dfermata;
        } else if(glyphName.equals("scripts.dshortfermata")) {
            return scripts_dshortfermata;
        } else if(glyphName.equals("scripts.dlongfermata")) {
            return scripts_dlongfermata;
        } else if(glyphName.equals("scripts.dverylongfermata")) {
            return scripts_dverylongfermata;
        } else if(glyphName.equals("accidentals.M1")) {
            return accidentals_M1;
        } else if(glyphName.equals("accidentals.mirroredflat")) {
            return accidentals_mirroredflat;
        } else if(glyphName.equals("accidentals.mirroredflat.backslash")) {
            return accidentals_mirroredflat_backslash;
        } else if(glyphName.equals("accidentals.M2")) {
            return accidentals_M2;
        } else if(glyphName.equals("accidentals.flat")) {
            return accidentals_flat;
        } else if(glyphName.equals("accidentals.flat.arrowup")) {
            return accidentals_flat_arrowup;
        } else if(glyphName.equals("accidentals.flat.arrowdown")) {
            return accidentals_flat_arrowdown;
        } else if(glyphName.equals("accidentals.flat.arrowboth")) {
            return accidentals_flat_arrowboth;
        } else if(glyphName.equals("accidentals.flat.slash")) {
            return accidentals_flat_slash;
        } else if(glyphName.equals("accidentals.flat.slashslash")) {
            return accidentals_flat_slashslash;
        } else if(glyphName.equals("accidentals.M3")) {
            return accidentals_M3;
        } else if(glyphName.equals("accidentals.mirroredflat.flat")) {
            return accidentals_mirroredflat_flat;
        } else if(glyphName.equals("accidentals.M4")) {
            return accidentals_M4;
        } else if(glyphName.equals("accidentals.flatflat")) {
            return accidentals_flatflat;
        } else if(glyphName.equals("accidentals.flatflat.slash")) {
            return accidentals_flatflat_slash;
        } else if(glyphName.equals("noteheads.s0harmonic")) {
            return noteheads_s0harmonic;
        } else if(glyphName.equals("noteheads.s2harmonic")) {
            return noteheads_s2harmonic;
        } else if(glyphName.equals("scripts.flageolet")) {
            return scripts_flageolet;
        } else if(glyphName.equals("scripts.open")) {
            return scripts_open;
        } else if(glyphName.equals("scripts.thumb")) {
            return scripts_thumb;
        } else if(glyphName.equals("noteheads.s2")) {
            return noteheads_s2;
        } else if(glyphName.equals("noteheads.s1")) {
            return noteheads_s1;
        } else if(glyphName.equals("scripts.tenuto")) {
            return scripts_tenuto;
        } else if(glyphName.equals("scripts.uportato")) {
            return scripts_uportato;
        } else if(glyphName.equals("scripts.dportato")) {
            return scripts_dportato;
        } else if(glyphName.equals("scripts.mordent")) {
            return scripts_mordent;
        } else if(glyphName.equals("scripts.prall")) {
            return scripts_prall;
        } else if(glyphName.equals("scripts.prallprall")) {
            return scripts_prallprall;
        } else if(glyphName.equals("scripts.prallmordent")) {
            return scripts_prallmordent;
        } else if(glyphName.equals("scripts.upprall")) {
            return scripts_upprall;
        } else if(glyphName.equals("scripts.upmordent")) {
            return scripts_upmordent;
        } else if(glyphName.equals("scripts.pralldown")) {
            return scripts_pralldown;
        } else if(glyphName.equals("scripts.downprall")) {
            return scripts_downprall;
        } else if(glyphName.equals("scripts.downmordent")) {
            return scripts_downmordent;
        } else if(glyphName.equals("scripts.prallup")) {
            return scripts_prallup;
        } else if(glyphName.equals("scripts.lineprall")) {
            return scripts_lineprall;
        } else if(glyphName.equals("accidentals.0")) {
            return accidentals_0;
        } else if(glyphName.equals("accidentals.natural")) {
            return accidentals_natural;
        } else if(glyphName.equals("accidentals.natural.arrowup")) {
            return accidentals_natural_arrowup;
        } else if(glyphName.equals("accidentals.natural.arrowdown")) {
            return accidentals_natural_arrowdown;
        } else if(glyphName.equals("accidentals.natural.arrowboth")) {
            return accidentals_natural_arrowboth;
        } else if(glyphName.equals("pedal..")) {
            return pedal__;
        } else if(glyphName.equals("pedal.P")) {
            return pedal_P;
        } else if(glyphName.equals("pedal.d")) {
            return pedal_d;
        } else if(glyphName.equals("pedal.e")) {
            return pedal_e;
        } else if(glyphName.equals("pedal.Ped")) {
            return pedal_Ped;
        } else if(glyphName.equals("pedal.*")) {
            return pedal_star;
        } else if(glyphName.equals("pedal.M")) {
            return pedal_M;
        } else if(glyphName.equals("rests.M3")) {
            return rests_M3;
        } else if(glyphName.equals("rests.M2")) {
            return rests_M2;
        } else if(glyphName.equals("rests.M1")) {
            return rests_M1;
        } else if(glyphName.equals("rests.2")) {
            return rests_2;
        } else if(glyphName.equals("rests.2classical")) {
            return rests_2classical;
        } else if(glyphName.equals("rests.5")) {
            return rests_5;
        } else if(glyphName.equals("rests.6")) {
            return rests_6;
        } else if(glyphName.equals("rests.7")) {
            return rests_7;
        } else if(glyphName.equals("rests.1")) {
            return rests_1;
        } else if(glyphName.equals("rests.1o")) {
            return rests_1o;
        } else if(glyphName.equals("rests.3")) {
            return rests_3;
        } else if(glyphName.equals("rests.4")) {
            return rests_4;
        } else if(glyphName.equals("rests.0")) {
            return rests_0;
        } else if(glyphName.equals("rests.0o")) {
            return rests_0o;
        } else if(glyphName.equals("scripts.segno")) {
            return scripts_segno;
        } else if(glyphName.equals("noteheads.s0")) {
            return noteheads_s0;
        } else if(glyphName.equals("scripts.umarcato")) {
            return scripts_umarcato;
        } else if(glyphName.equals("scripts.dmarcato")) {
            return scripts_dmarcato;
        } else if(glyphName.equals("accidentals.1")) {
            return accidentals_1;
        } else if(glyphName.equals("accidentals.sharp.slashslash.stem")) {
            return accidentals_sharp_slashslash_stem;
        } else if(glyphName.equals("accidentals.sharp.slashslashslash.stem")) {
            return accidentals_sharp_slashslashslash_stem;
        } else if(glyphName.equals("accidentals.2")) {
            return accidentals_2;
        } else if(glyphName.equals("accidentals.sharp")) {
            return accidentals_sharp;
        } else if(glyphName.equals("accidentals.sharp.slashslashslash.stemstem")) {
            return accidentals_sharp_slashslashslash_stemstem;
        } else if(glyphName.equals("accidentals.sharp.arrowup")) {
            return accidentals_sharp_arrowup;
        } else if(glyphName.equals("accidentals.sharp.arrowdown")) {
            return accidentals_sharp_arrowdown;
        } else if(glyphName.equals("accidentals.sharp.arrowboth")) {
            return accidentals_sharp_arrowboth;
        } else if(glyphName.equals("accidentals.3")) {
            return accidentals_3;
        } else if(glyphName.equals("accidentals.sharp.slashslash.stemstemstem")) {
            return accidentals_sharp_slashslash_stemstemstem;
        } else if(glyphName.equals("accidentals.4")) {
            return accidentals_4;
        } else if(glyphName.equals("accidentals.doublesharp")) {
            return accidentals_doublesharp;
        } else if(glyphName.equals("scripts.dstaccatissimo")) {
            return scripts_dstaccatissimo;
        } else if(glyphName.equals("scripts.ustaccatissimo")) {
            return scripts_ustaccatissimo;
        } else if(glyphName.equals("scripts.staccato")) {
            return scripts_staccato;
        } else if(glyphName.equals("dots.dot")) {
            return dots_dot;
        } else if(glyphName.equals("scripts.snappizzicato")) {
            return scripts_snappizzicato;
        } else if(glyphName.equals("scripts.stopped")) {
            return scripts_stopped;
        } else if(glyphName.equals("flags.d3")) {
            return flags_d3;
        } else if(glyphName.equals("flags.u3")) {
            return flags_u3;
        } else if(glyphName.equals("flags.d4")) {
            return flags_d4;
        } else if(glyphName.equals("flags.u4")) {
            return flags_u4;
        } else if(glyphName.equals("flags.d5")) {
            return flags_d5;
        } else if(glyphName.equals("flags.u5")) {
            return flags_u5;
        } else if(glyphName.equals("flags.d6")) {
            return flags_d6;
        } else if(glyphName.equals("flags.u6")) {
            return flags_u6;
        } else if(glyphName.equals("flags.d7")) {
            return flags_d7;
        } else if(glyphName.equals("flags.u7")) {
            return flags_u7;
        } else if(glyphName.equals("timesig.C22")) {
            return timesig_C22;
        } else if(glyphName.equals("timesig.C44")) {
            return timesig_C44;
        } else if(glyphName.equals("scripts.trill")) {
            return scripts_trill;
        } else if(glyphName.equals("scripts.turn")) {
            return scripts_turn;
        } else if(glyphName.equals("scripts.reverseturn")) {
            return scripts_reverseturn;
        } else if(glyphName.equals("arrowheads.open.11")) {
            return arrowheads_open_11;
        } else if(glyphName.equals("arrowheads.open.1M1")) {
            return arrowheads_open_1M1;
        } else if(glyphName.equals("arrowheads.open.0M1")) {
            return arrowheads_open_0M1;
        } else if(glyphName.equals("arrowheads.open.01")) {
            return arrowheads_open_01;
        } else if(glyphName.equals("arrowheads.close.11")) {
            return arrowheads_close_11;
        } else if(glyphName.equals("arrowheads.close.1M1")) {
            return arrowheads_close_1M1;
        } else if(glyphName.equals("arrowheads.close.0M1")) {
            return arrowheads_close_0M1;
        } else if(glyphName.equals("arrowheads.close.01")) {
            return arrowheads_close_01;
        } else if(glyphName.equals("scripts.upedalheel")) {
            return scripts_upedalheel;
        } else if(glyphName.equals("scripts.dpedalheel")) {
            return scripts_dpedalheel;
        } else if(glyphName.equals("scripts.upedaltoe")) {
            return scripts_upedaltoe;
        } else if(glyphName.equals("scripts.dpedaltoe")) {
            return scripts_dpedaltoe;
        } else if(glyphName.equals("accordion.accFreebase")) {
            return accordion_accFreebase;
        } else if(glyphName.equals("accordion.accDiscant")) {
            return accordion_accDiscant;
        } else if(glyphName.equals("accordion.accStdbase")) {
            return accordion_accStdbase;
        } else if(glyphName.equals("accordion.accBayanbase")) {
            return accordion_accBayanbase;
        } else if(glyphName.equals("accordion.accDot")) {
            return accordion_accDot;
        } else if(glyphName.equals("accordion.accOldEE")) {
            return accordion_accOldEE;
        } else if(glyphName.equals("noteheads.s0diamond")) {
            return noteheads_s0diamond;
        } else if(glyphName.equals("noteheads.s1diamond")) {
            return noteheads_s1diamond;
        } else if(glyphName.equals("noteheads.s2diamond")) {
            return noteheads_s2diamond;
        } else if(glyphName.equals("noteheads.s0triangle")) {
            return noteheads_s0triangle;
        } else if(glyphName.equals("noteheads.d1triangle")) {
            return noteheads_d1triangle;
        } else if(glyphName.equals("noteheads.u1triangle")) {
            return noteheads_u1triangle;
        } else if(glyphName.equals("noteheads.d2triangle")) {
            return noteheads_d2triangle;
        } else if(glyphName.equals("noteheads.u2triangle")) {
            return noteheads_u2triangle;
        } else if(glyphName.equals("noteheads.s0cross")) {
            return noteheads_s0cross;
        } else if(glyphName.equals("noteheads.s1cross")) {
            return noteheads_s1cross;
        } else if(glyphName.equals("noteheads.s2cross")) {
            return noteheads_s2cross;
        } else if(glyphName.equals("noteheads.s2xcircle")) {
            return noteheads_s2xcircle;
        } else if(glyphName.equals("noteheads.s0slash")) {
            return noteheads_s0slash;
        } else if(glyphName.equals("noteheads.s1slash")) {
            return noteheads_s1slash;
        } else if(glyphName.equals("noteheads.s2slash")) {
            return noteheads_s2slash;
        } else {
            return -1;
        }
    }

}
