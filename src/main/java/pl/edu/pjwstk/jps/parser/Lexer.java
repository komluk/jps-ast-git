/* The following code was generated by JFlex 1.4.3 on 19.01.13 18:25 */

package pl.edu.pjwstk.jps.parser;

import java_cup.runtime.*;
import java.io.IOException;

import java_cup.runtime.Symbol;
import static pl.edu.pjwstk.jps.parser.Symbols.*;
import pl.edu.pjwstk.jps.parser.Symbols;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 19.01.13 18:25 from the specification file
 * <tt>/home/pawel/Dropbox/Documents/pjwstk/3/jps/workspace/ast/src/main/resources/parser/my_lexer.lex</tt>
 */
public class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\20\1\17\2\0\1\16\22\0\1\71\1\33\1\14\2\0"+
    "\1\25\1\37\1\0\1\105\1\106\1\23\1\21\1\51\1\22\1\15"+
    "\1\24\12\1\2\0\1\47\1\46\1\50\2\0\1\35\1\104\1\57"+
    "\1\36\1\44\1\13\1\63\1\100\1\65\1\102\1\13\1\74\1\55"+
    "\1\30\1\31\1\70\1\45\1\40\1\53\1\32\1\54\1\62\1\77"+
    "\1\66\1\73\1\13\4\0\1\12\1\0\1\7\1\103\1\56\1\34"+
    "\1\5\1\6\1\61\1\76\1\64\1\101\1\13\1\10\1\52\1\26"+
    "\1\27\1\67\1\43\1\3\1\11\1\2\1\4\1\60\1\75\1\42"+
    "\1\72\1\13\1\0\1\41\uff83\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\7\2\1\0\1\3\2\4\1\5\1\6"+
    "\1\7\1\10\1\11\4\2\1\12\1\2\1\13\1\14"+
    "\2\2\1\15\1\16\1\17\1\20\20\2\1\21\1\22"+
    "\1\0\3\2\1\15\2\2\1\23\4\2\1\24\1\2"+
    "\1\14\1\2\1\25\3\2\1\13\1\14\2\2\1\15"+
    "\1\26\1\27\13\2\2\30\6\2\1\31\4\2\1\32"+
    "\1\13\1\33\1\34\1\2\1\35\1\12\1\36\1\2"+
    "\1\37\1\40\2\2\1\40\12\2\1\41\1\42\22\2"+
    "\1\43\1\44\4\2\1\45\2\2\1\46\4\2\1\47"+
    "\1\50\1\51\1\52\1\2\1\0\1\2\1\0\2\2"+
    "\1\53\2\0\2\2\1\53\2\2\1\54";

  private static int [] zzUnpackAction() {
    int [] result = new int[178];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\107\0\216\0\325\0\u011c\0\u0163\0\u01aa\0\u01f1"+
    "\0\u0238\0\u027f\0\u02c6\0\u030d\0\u02c6\0\u02c6\0\u02c6\0\u02c6"+
    "\0\u02c6\0\u02c6\0\u0354\0\u039b\0\u03e2\0\u0429\0\u0470\0\u04b7"+
    "\0\u04fe\0\u0545\0\u058c\0\u05d3\0\u061a\0\u0661\0\u06a8\0\u02c6"+
    "\0\u06ef\0\u0736\0\u077d\0\u07c4\0\u080b\0\u0852\0\u0899\0\u08e0"+
    "\0\u0927\0\u096e\0\u09b5\0\u09fc\0\u0a43\0\u0a8a\0\u0ad1\0\u0b18"+
    "\0\u02c6\0\u02c6\0\u0b5f\0\u0ba6\0\u0bed\0\u0c34\0\325\0\u0c7b"+
    "\0\u0cc2\0\325\0\u0d09\0\u0d50\0\u0d97\0\u0dde\0\u02c6\0\u0e25"+
    "\0\325\0\u0e6c\0\u02c6\0\u0eb3\0\u0efa\0\u0f41\0\u02c6\0\u02c6"+
    "\0\u0f88\0\u0fcf\0\u02c6\0\u02c6\0\u02c6\0\u1016\0\u105d\0\u10a4"+
    "\0\u10eb\0\u1132\0\u1179\0\u11c0\0\u1207\0\u124e\0\u1295\0\u12dc"+
    "\0\u1323\0\u136a\0\u13b1\0\u13f8\0\u143f\0\u1486\0\u14cd\0\u1514"+
    "\0\u0b5f\0\u155b\0\u15a2\0\u15e9\0\u1630\0\325\0\325\0\325"+
    "\0\325\0\u1677\0\325\0\325\0\325\0\u16be\0\325\0\u1705"+
    "\0\u174c\0\u1793\0\u17da\0\u1821\0\u1868\0\u18af\0\u18f6\0\u193d"+
    "\0\u1984\0\u19cb\0\u1a12\0\u1a59\0\u1aa0\0\325\0\325\0\u1ae7"+
    "\0\u1b2e\0\u1b75\0\u1bbc\0\u1c03\0\u1c4a\0\u1c91\0\u1cd8\0\u1d1f"+
    "\0\u1d66\0\u1dad\0\u1df4\0\u1e3b\0\u1e82\0\u1ec9\0\u1f10\0\u1f57"+
    "\0\u1f9e\0\325\0\325\0\u1fe5\0\u202c\0\u2073\0\u20ba\0\325"+
    "\0\u2101\0\u2148\0\325\0\u218f\0\u21d6\0\u221d\0\u2264\0\325"+
    "\0\325\0\325\0\325\0\u22ab\0\u22f2\0\u2339\0\u2380\0\u23c7"+
    "\0\u240e\0\325\0\u2455\0\u249c\0\u24e3\0\u252a\0\u02c6\0\u2571"+
    "\0\u25b8\0\325";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[178];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\0\1\2\1\3\1\4\1\5\1\6\1\7\1\10"+
    "\1\4\1\11\2\4\1\12\1\13\1\14\2\15\1\16"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\1\4\1\27\1\4\1\30\1\4\1\31\1\4\1\32"+
    "\1\33\1\4\1\34\1\4\1\35\1\36\1\37\1\40"+
    "\1\41\1\42\1\43\1\44\1\45\1\46\1\4\1\47"+
    "\1\4\1\50\1\51\1\52\3\4\1\15\3\4\1\53"+
    "\1\4\1\54\1\4\1\55\1\56\1\57\1\60\1\61"+
    "\1\62\1\0\1\2\13\0\1\63\72\0\2\4\1\64"+
    "\6\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\1\65\4\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\1\66\1\67\2\4\4\0\17\4"+
    "\1\0\13\4\3\0\6\4\1\70\2\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\7\4\1\71\1\72"+
    "\1\0\1\4\12\0\1\73\4\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\6\4\1\74\10\4\1\0"+
    "\13\4\3\0\1\4\1\75\1\4\1\76\5\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\2\0\14\12\1\77"+
    "\72\12\126\0\1\15\70\0\11\4\1\0\1\4\12\0"+
    "\1\4\1\100\3\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\3\0\2\4\1\101"+
    "\6\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\3\4\1\102\1\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\101\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\50\0\1\103\41\0\11\4\1\0\1\4\12\0"+
    "\2\4\1\104\2\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\1\4\1\72\6\4\1\105\6\4\1\0"+
    "\2\4\1\106\10\4\41\0\1\107\110\0\1\110\46\0"+
    "\11\4\1\0\1\4\12\0\1\4\1\111\3\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\3\4\1\67\4\0\14\4"+
    "\1\112\2\4\1\0\13\4\50\0\1\113\106\0\1\114"+
    "\106\0\1\115\41\0\6\4\1\116\2\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\12\4\1\117\4\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\4\4\1\120\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\2\4\1\121\14\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\2\4\1\122"+
    "\2\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\13\4\3\0\11\4\1\0\1\4\12\0"+
    "\5\4\1\0\1\4\1\123\1\4\1\0\1\4\1\0"+
    "\4\4\4\0\13\4\1\124\3\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\1\4\1\125\3\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\3\4\1\126"+
    "\1\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\13\4\3\0\2\4\1\127\6\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\3\0\11\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\130\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\3\0\11\4\1\0"+
    "\1\4\12\0\1\131\4\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\2\4\1\132\2\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\4\4"+
    "\1\133\6\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\6\4\1\134\4\4\3\0\11\4\1\0\1\4"+
    "\12\0\1\4\1\135\3\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\3\4\1\136\1\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\6\4\1\137\2\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\1\4\1\140\1\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\1\141\106\0\3\4"+
    "\1\142\5\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\12\4\1\143\4\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\12\4"+
    "\1\144\4\4\1\0\13\4\3\0\7\4\1\145\1\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\7\4"+
    "\1\146\1\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\1\147"+
    "\2\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\1\150\12\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\7\4"+
    "\1\151\7\4\1\0\13\4\3\0\2\4\1\152\6\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\1\153\16\4\1\0\13\4\3\0"+
    "\1\4\1\154\7\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\4\4\1\154"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\2\4\1\147\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\1\4\1\150\11\4\3\0\11\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\11\4\1\151\5\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\2\4\1\146"+
    "\10\4\3\0\2\4\1\155\6\4\1\0\1\4\12\0"+
    "\5\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\13\4\3\0\11\4\1\0\1\4\12\0"+
    "\5\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\13\4\1\156\3\4\1\0\13\4\3\0\11\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\1\157\3\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\1\160\4\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\161\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\3\4\1\153\13\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\13\4\1\162"+
    "\3\4\1\0\13\4\3\0\11\4\1\0\1\4\12\0"+
    "\5\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\14\4\1\157\2\4\1\0\13\4\3\0\11\4\1\0"+
    "\1\4\12\0\2\4\1\163\2\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\3\4\1\164\5\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\2\4\1\165"+
    "\14\4\1\0\13\4\3\0\11\4\1\0\1\4\12\0"+
    "\1\4\1\166\3\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\3\0\11\4\1\0"+
    "\1\4\12\0\3\4\1\167\1\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\1\4\1\170\7\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\4\4\1\171"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\13\4\3\0\4\4\1\172\4\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\2\4"+
    "\1\173\1\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\12\4\1\174\4\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\13\4\1\175\3\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\7\4"+
    "\1\176\7\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\11\4\1\176\5\4\1\0\13\4\3\0\4\4"+
    "\1\177\4\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\1\4\1\200\3\4"+
    "\1\0\3\4\1\0\1\4\1\0\1\4\1\201\2\4"+
    "\4\0\17\4\1\0\13\4\3\0\10\4\1\202\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\3\0\10\4\1\142"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\3\4"+
    "\1\203\5\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\1\4\1\204\15\4"+
    "\1\0\13\4\3\0\3\4\1\205\5\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\2\4\1\206\14\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\3\4\1\207\1\4\1\0\3\4"+
    "\1\0\1\4\1\0\3\4\1\210\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\2\4\1\211"+
    "\14\4\1\0\13\4\3\0\11\4\1\0\1\4\12\0"+
    "\1\212\4\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\2\4\1\213\2\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\3\4"+
    "\1\214\5\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\2\4\1\215\14\4"+
    "\1\0\13\4\3\0\4\4\1\216\4\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\2\4"+
    "\1\217\1\4\4\0\17\4\1\0\13\4\3\0\2\4"+
    "\1\220\6\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\221\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\1\222\4\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\17\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\2\4\1\222"+
    "\2\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\13\4\3\0\11\4\1\0\1\4\12\0"+
    "\1\223\4\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\3\4\1\224\5\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\1\4"+
    "\1\225\7\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\4\4\1\226\12\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\4\4"+
    "\1\227\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\13\4\3\0\10\4\1\230\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\5\4\1\231\11\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\2\4\1\223\2\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\2\4\1\232\14\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\1\4"+
    "\1\230\15\4\1\0\13\4\3\0\1\4\1\233\7\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\4\4\1\233\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\15\4\1\234\1\4\1\0"+
    "\13\4\3\0\11\4\1\0\1\4\12\0\5\4\1\0"+
    "\3\4\1\0\1\4\1\0\4\4\4\0\16\4\1\235"+
    "\1\0\13\4\3\0\2\4\1\236\6\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\237\1\0\4\4"+
    "\4\0\17\4\1\0\13\4\3\0\4\4\1\240\4\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\2\4\1\240\1\4\4\0\17\4\1\0\13\4"+
    "\3\0\4\4\1\241\4\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\13\4\3\0\10\4\1\242\1\0\1\4\12\0"+
    "\5\4\1\0\3\4\1\0\1\4\1\0\4\4\4\0"+
    "\17\4\1\0\13\4\3\0\1\4\1\243\7\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\17\4\1\0\13\4\3\0\11\4\1\0"+
    "\1\4\12\0\5\4\1\0\3\4\1\0\1\4\1\0"+
    "\4\4\4\0\1\4\1\242\15\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\4\4\1\243\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\17\4\1\0\13\4"+
    "\3\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\2\4\1\241\1\4\4\0\17\4"+
    "\1\0\13\4\3\0\6\4\1\244\2\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\17\4\1\245\13\4\3\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\1\4\1\246\1\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\247\13\4\3\0\10\4"+
    "\1\250\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\3\0"+
    "\11\4\1\0\1\4\12\0\5\4\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\1\4\1\251\15\4\1\0"+
    "\13\4\3\0\10\4\1\252\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\13\4\11\0\1\253\100\0\11\4\1\0\1\4"+
    "\12\0\5\4\1\0\3\4\1\0\1\4\1\0\4\4"+
    "\4\0\1\4\1\252\15\4\1\0\13\4\37\0\1\254"+
    "\52\0\4\4\1\255\4\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\17\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\2\4\1\256\1\4"+
    "\4\0\17\4\1\0\13\4\13\0\1\257\150\0\1\257"+
    "\34\0\11\4\1\0\1\4\12\0\5\4\1\0\3\4"+
    "\1\0\1\4\1\0\4\4\4\0\4\4\1\260\12\4"+
    "\1\0\13\4\3\0\11\4\1\0\1\4\12\0\5\4"+
    "\1\0\3\4\1\0\1\4\1\0\4\4\4\0\5\4"+
    "\1\261\11\4\1\0\13\4\3\0\1\4\1\262\7\4"+
    "\1\0\1\4\12\0\5\4\1\0\3\4\1\0\1\4"+
    "\1\0\4\4\4\0\17\4\1\0\13\4\3\0\11\4"+
    "\1\0\1\4\12\0\4\4\1\262\1\0\3\4\1\0"+
    "\1\4\1\0\4\4\4\0\17\4\1\0\13\4\2\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[9727];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\10\1\1\0\1\11\1\1\6\11\15\1\1\11"+
    "\20\1\2\11\1\0\13\1\1\11\3\1\1\11\3\1"+
    "\2\11\2\1\3\11\127\1\1\0\1\1\1\0\3\1"+
    "\2\0\2\1\1\11\3\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[178];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
	private Symbol createToken(int id) {
		return new Symbol(id, yyline, yycolumn);
	}
	private Symbol createToken(int id, Object o) {
		//System.out.println(id + " | " + o.toString());
		return new Symbol(id, yyline, yycolumn, o);
	}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 170) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 20: 
          { return createToken(STRING_LITERAL, yytext().substring(1,yytext().length()-1)) ;
          }
        case 45: break;
        case 3: 
          { return createToken(DOT);
          }
        case 46: break;
        case 17: 
          { return createToken(LEFT_ROUND_BRACKET);
          }
        case 47: break;
        case 7: 
          { return createToken(MULTIPLY);
          }
        case 48: break;
        case 40: 
          { return createToken(UNIQUE);
          }
        case 49: break;
        case 22: 
          { return createToken(LESS_EQ_THAN);
          }
        case 50: break;
        case 35: 
          { return createToken(JOIN);
          }
        case 51: break;
        case 44: 
          { return createToken(INTERSECT);
          }
        case 52: break;
        case 26: 
          { return createToken(FOR_ALL);
          }
        case 53: break;
        case 18: 
          { return createToken(RIGHT_ROUND_BRACKET);
          }
        case 54: break;
        case 23: 
          { return createToken(MORE_EQ_THAN);
          }
        case 55: break;
        case 29: 
          { return createToken(SUM);
          }
        case 56: break;
        case 28: 
          { return createToken(AVG);
          }
        case 57: break;
        case 41: 
          { return createToken(EXISTS);
          }
        case 58: break;
        case 10: 
          { return createToken(NOT);
          }
        case 59: break;
        case 11: 
          { return createToken(AND);
          }
        case 60: break;
        case 33: 
          { return createToken(BAG);
          }
        case 61: break;
        case 31: 
          { return createToken(MAX);
          }
        case 62: break;
        case 5: 
          { return createToken(PLUS);
          }
        case 63: break;
        case 42: 
          { return createToken(STRUCT);
          }
        case 64: break;
        case 2: 
          { return createToken(IDENTIFIER, yytext());
          }
        case 65: break;
        case 21: 
          { return createToken(NOT_EQ);
          }
        case 66: break;
        case 43: 
          { return createToken(GROUP_AS);
          }
        case 67: break;
        case 27: 
          { return createToken(FOR_ANY);
          }
        case 68: break;
        case 1: 
          { int val;
		try {
			val = Integer.parseInt(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(INTEGER_LITERAL, new Integer(val));
          }
        case 69: break;
        case 6: 
          { return createToken(MINUS);
          }
        case 70: break;
        case 36: 
          { return createToken(UNION);
          }
        case 71: break;
        case 34: 
          { boolean val;
		try {
			val = Boolean.parseBoolean(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(BOOLEAN_LITERAL, new Boolean(val));
          }
        case 72: break;
        case 16: 
          { return createToken(COMMA);
          }
        case 73: break;
        case 12: 
          { return createToken(OR);
          }
        case 74: break;
        case 24: 
          { return createToken(IN);
          }
        case 75: break;
        case 13: 
          { return createToken(EQ);
          }
        case 76: break;
        case 38: 
          { return createToken(COUNT);
          }
        case 77: break;
        case 8: 
          { return createToken(DIVIDE);
          }
        case 78: break;
        case 37: 
          { return createToken(MINUS_SET);
          }
        case 79: break;
        case 30: 
          { return createToken(XOR);
          }
        case 80: break;
        case 32: 
          { return createToken(MIN);
          }
        case 81: break;
        case 25: 
          { double val;
		try {
			val = Double.parseDouble(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(DOUBLE_LITERAL, new Double(val));
          }
        case 82: break;
        case 19: 
          { return createToken(AS);
          }
        case 83: break;
        case 39: 
          { return createToken(WHERE);
          }
        case 84: break;
        case 14: 
          { return createToken(LESS_THAN);
          }
        case 85: break;
        case 15: 
          { return createToken(MORE_THAN);
          }
        case 86: break;
        case 9: 
          { return createToken(MODULO);
          }
        case 87: break;
        case 4: 
          { 
          }
        case 88: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { 	return createToken(EOF);
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
