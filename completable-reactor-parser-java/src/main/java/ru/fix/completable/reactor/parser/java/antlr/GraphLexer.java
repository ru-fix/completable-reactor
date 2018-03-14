// Generated from Graph.g4 by ANTLR 4.7.1
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GraphLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, Coordinate=24, 
		SUBGRAPH=25, MERGER=26, HANDLER=27, SUSPEND_HANDLER=28, PAYLOAD=29, COMPLETE=30, 
		ROUTER=31, CLONE=32, VERTEX=33, DecimalNumeral=34, LPAREN=35, RPAREN=36, 
		LBRACE=37, RBRACE=38, SEMI=39, COMMA=40, DOT=41, AT=42, ASSIGN=43, NEW=44, 
		COLONCOLON=45, StringLiteral=46, Identifier=47, WS=48, COMMENT=49, LINE_COMMENT=50, 
		OTHER_SYMBOL=51;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "Coordinate", "SUBGRAPH", 
		"MERGER", "HANDLER", "SUSPEND_HANDLER", "PAYLOAD", "COMPLETE", "ROUTER", 
		"CLONE", "VERTEX", "DecimalNumeral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"SEMI", "COMMA", "DOT", "AT", "ASSIGN", "NEW", "COLONCOLON", "StringLiteral", 
		"StringCharacter", "Identifier", "JavaLetter", "JavaLetterOrDigit", "WS", 
		"COMMENT", "LINE_COMMENT", "OTHER_SYMBOL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'extends'", "':'", "'Graph'", "'<'", "'>'", "'public'", 
		"'protected'", "'private'", "'abstract'", "'static'", "'final'", "'open'", 
		"'sealed'", "'val'", "'withMerger'", "'withoutMerger'", "'handleBy'", 
		"'on'", "'onAny'", "'mergeBy'", "'coordinates'", "'enum'", null, "'subgraph'", 
		"'merger'", "'handler'", "'suspendHandler'", "'payload'", "'complete'", 
		"'router'", "'clone'", "'Vertex'", null, "'('", "')'", "'{'", "'}'", "';'", 
		"','", "'.'", "'@'", "'='", "'new'", "'::'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"Coordinate", "SUBGRAPH", "MERGER", "HANDLER", "SUSPEND_HANDLER", "PAYLOAD", 
		"COMPLETE", "ROUTER", "CLONE", "VERTEX", "DecimalNumeral", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "SEMI", "COMMA", "DOT", "AT", "ASSIGN", "NEW", "COLONCOLON", 
		"StringLiteral", "Identifier", "WS", "COMMENT", "LINE_COMMENT", "OTHER_SYMBOL"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GraphLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Graph.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 48:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 49:
			return JavaLetterOrDigit_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean JavaLetter_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return Character.isJavaIdentifierStart(_input.LA(-1));
		case 1:
			return Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}
	private boolean JavaLetterOrDigit_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return Character.isJavaIdentifierPart(_input.LA(-1));
		case 3:
			return Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)));
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\65\u01d9\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\31\5\31\u010e"+
		"\n\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3#\6#\u015f\n#\r#\16#\u0160\3$\3$\3%\3%\3&\3&\3\'\3\'\3("+
		"\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3-\3-\3.\3.\3.\3/\3/\7/\u017e\n/\f/"+
		"\16/\u0181\13/\3/\3/\3/\3/\3/\3/\7/\u0189\n/\f/\16/\u018c\13/\3/\3/\3"+
		"/\5/\u0191\n/\3\60\3\60\3\60\5\60\u0196\n\60\3\61\3\61\7\61\u019a\n\61"+
		"\f\61\16\61\u019d\13\61\3\61\3\61\6\61\u01a1\n\61\r\61\16\61\u01a2\3\61"+
		"\5\61\u01a6\n\61\3\62\3\62\3\62\3\62\3\62\3\62\5\62\u01ae\n\62\3\63\3"+
		"\63\3\63\3\63\3\63\3\63\5\63\u01b6\n\63\3\64\6\64\u01b9\n\64\r\64\16\64"+
		"\u01ba\3\64\3\64\3\65\3\65\3\65\3\65\7\65\u01c3\n\65\f\65\16\65\u01c6"+
		"\13\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\7\66\u01d1\n\66\f"+
		"\66\16\66\u01d4\13\66\3\66\3\66\3\67\3\67\4\u018a\u01c4\28\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G"+
		"%I&K\'M(O)Q*S+U,W-Y.[/]\60_\2a\61c\2e\2g\62i\63k\64m\65\3\2\f\4\2\62;"+
		"aa\3\2$$\3\2bb\6\2&&C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01\3"+
		"\2\udc02\ue001\7\2&&\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u01e5"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2a\3\2\2\2\2g\3"+
		"\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\3o\3\2\2\2\5u\3\2\2\2\7}\3\2\2"+
		"\2\t\177\3\2\2\2\13\u0085\3\2\2\2\r\u0087\3\2\2\2\17\u0089\3\2\2\2\21"+
		"\u0090\3\2\2\2\23\u009a\3\2\2\2\25\u00a2\3\2\2\2\27\u00ab\3\2\2\2\31\u00b2"+
		"\3\2\2\2\33\u00b8\3\2\2\2\35\u00bd\3\2\2\2\37\u00c4\3\2\2\2!\u00c8\3\2"+
		"\2\2#\u00d3\3\2\2\2%\u00e1\3\2\2\2\'\u00ea\3\2\2\2)\u00ed\3\2\2\2+\u00f3"+
		"\3\2\2\2-\u00fb\3\2\2\2/\u0107\3\2\2\2\61\u010d\3\2\2\2\63\u0111\3\2\2"+
		"\2\65\u011a\3\2\2\2\67\u0121\3\2\2\29\u0129\3\2\2\2;\u0138\3\2\2\2=\u0140"+
		"\3\2\2\2?\u0149\3\2\2\2A\u0150\3\2\2\2C\u0156\3\2\2\2E\u015e\3\2\2\2G"+
		"\u0162\3\2\2\2I\u0164\3\2\2\2K\u0166\3\2\2\2M\u0168\3\2\2\2O\u016a\3\2"+
		"\2\2Q\u016c\3\2\2\2S\u016e\3\2\2\2U\u0170\3\2\2\2W\u0172\3\2\2\2Y\u0174"+
		"\3\2\2\2[\u0178\3\2\2\2]\u0190\3\2\2\2_\u0195\3\2\2\2a\u01a5\3\2\2\2c"+
		"\u01ad\3\2\2\2e\u01b5\3\2\2\2g\u01b8\3\2\2\2i\u01be\3\2\2\2k\u01cc\3\2"+
		"\2\2m\u01d7\3\2\2\2op\7e\2\2pq\7n\2\2qr\7c\2\2rs\7u\2\2st\7u\2\2t\4\3"+
		"\2\2\2uv\7g\2\2vw\7z\2\2wx\7v\2\2xy\7g\2\2yz\7p\2\2z{\7f\2\2{|\7u\2\2"+
		"|\6\3\2\2\2}~\7<\2\2~\b\3\2\2\2\177\u0080\7I\2\2\u0080\u0081\7t\2\2\u0081"+
		"\u0082\7c\2\2\u0082\u0083\7r\2\2\u0083\u0084\7j\2\2\u0084\n\3\2\2\2\u0085"+
		"\u0086\7>\2\2\u0086\f\3\2\2\2\u0087\u0088\7@\2\2\u0088\16\3\2\2\2\u0089"+
		"\u008a\7r\2\2\u008a\u008b\7w\2\2\u008b\u008c\7d\2\2\u008c\u008d\7n\2\2"+
		"\u008d\u008e\7k\2\2\u008e\u008f\7e\2\2\u008f\20\3\2\2\2\u0090\u0091\7"+
		"r\2\2\u0091\u0092\7t\2\2\u0092\u0093\7q\2\2\u0093\u0094\7v\2\2\u0094\u0095"+
		"\7g\2\2\u0095\u0096\7e\2\2\u0096\u0097\7v\2\2\u0097\u0098\7g\2\2\u0098"+
		"\u0099\7f\2\2\u0099\22\3\2\2\2\u009a\u009b\7r\2\2\u009b\u009c\7t\2\2\u009c"+
		"\u009d\7k\2\2\u009d\u009e\7x\2\2\u009e\u009f\7c\2\2\u009f\u00a0\7v\2\2"+
		"\u00a0\u00a1\7g\2\2\u00a1\24\3\2\2\2\u00a2\u00a3\7c\2\2\u00a3\u00a4\7"+
		"d\2\2\u00a4\u00a5\7u\2\2\u00a5\u00a6\7v\2\2\u00a6\u00a7\7t\2\2\u00a7\u00a8"+
		"\7c\2\2\u00a8\u00a9\7e\2\2\u00a9\u00aa\7v\2\2\u00aa\26\3\2\2\2\u00ab\u00ac"+
		"\7u\2\2\u00ac\u00ad\7v\2\2\u00ad\u00ae\7c\2\2\u00ae\u00af\7v\2\2\u00af"+
		"\u00b0\7k\2\2\u00b0\u00b1\7e\2\2\u00b1\30\3\2\2\2\u00b2\u00b3\7h\2\2\u00b3"+
		"\u00b4\7k\2\2\u00b4\u00b5\7p\2\2\u00b5\u00b6\7c\2\2\u00b6\u00b7\7n\2\2"+
		"\u00b7\32\3\2\2\2\u00b8\u00b9\7q\2\2\u00b9\u00ba\7r\2\2\u00ba\u00bb\7"+
		"g\2\2\u00bb\u00bc\7p\2\2\u00bc\34\3\2\2\2\u00bd\u00be\7u\2\2\u00be\u00bf"+
		"\7g\2\2\u00bf\u00c0\7c\2\2\u00c0\u00c1\7n\2\2\u00c1\u00c2\7g\2\2\u00c2"+
		"\u00c3\7f\2\2\u00c3\36\3\2\2\2\u00c4\u00c5\7x\2\2\u00c5\u00c6\7c\2\2\u00c6"+
		"\u00c7\7n\2\2\u00c7 \3\2\2\2\u00c8\u00c9\7y\2\2\u00c9\u00ca\7k\2\2\u00ca"+
		"\u00cb\7v\2\2\u00cb\u00cc\7j\2\2\u00cc\u00cd\7O\2\2\u00cd\u00ce\7g\2\2"+
		"\u00ce\u00cf\7t\2\2\u00cf\u00d0\7i\2\2\u00d0\u00d1\7g\2\2\u00d1\u00d2"+
		"\7t\2\2\u00d2\"\3\2\2\2\u00d3\u00d4\7y\2\2\u00d4\u00d5\7k\2\2\u00d5\u00d6"+
		"\7v\2\2\u00d6\u00d7\7j\2\2\u00d7\u00d8\7q\2\2\u00d8\u00d9\7w\2\2\u00d9"+
		"\u00da\7v\2\2\u00da\u00db\7O\2\2\u00db\u00dc\7g\2\2\u00dc\u00dd\7t\2\2"+
		"\u00dd\u00de\7i\2\2\u00de\u00df\7g\2\2\u00df\u00e0\7t\2\2\u00e0$\3\2\2"+
		"\2\u00e1\u00e2\7j\2\2\u00e2\u00e3\7c\2\2\u00e3\u00e4\7p\2\2\u00e4\u00e5"+
		"\7f\2\2\u00e5\u00e6\7n\2\2\u00e6\u00e7\7g\2\2\u00e7\u00e8\7D\2\2\u00e8"+
		"\u00e9\7{\2\2\u00e9&\3\2\2\2\u00ea\u00eb\7q\2\2\u00eb\u00ec\7p\2\2\u00ec"+
		"(\3\2\2\2\u00ed\u00ee\7q\2\2\u00ee\u00ef\7p\2\2\u00ef\u00f0\7C\2\2\u00f0"+
		"\u00f1\7p\2\2\u00f1\u00f2\7{\2\2\u00f2*\3\2\2\2\u00f3\u00f4\7o\2\2\u00f4"+
		"\u00f5\7g\2\2\u00f5\u00f6\7t\2\2\u00f6\u00f7\7i\2\2\u00f7\u00f8\7g\2\2"+
		"\u00f8\u00f9\7D\2\2\u00f9\u00fa\7{\2\2\u00fa,\3\2\2\2\u00fb\u00fc\7e\2"+
		"\2\u00fc\u00fd\7q\2\2\u00fd\u00fe\7q\2\2\u00fe\u00ff\7t\2\2\u00ff\u0100"+
		"\7f\2\2\u0100\u0101\7k\2\2\u0101\u0102\7p\2\2\u0102\u0103\7c\2\2\u0103"+
		"\u0104\7v\2\2\u0104\u0105\7g\2\2\u0105\u0106\7u\2\2\u0106.\3\2\2\2\u0107"+
		"\u0108\7g\2\2\u0108\u0109\7p\2\2\u0109\u010a\7w\2\2\u010a\u010b\7o\2\2"+
		"\u010b\60\3\2\2\2\u010c\u010e\7/\2\2\u010d\u010c\3\2\2\2\u010d\u010e\3"+
		"\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110\5E#\2\u0110\62\3\2\2\2\u0111\u0112"+
		"\7u\2\2\u0112\u0113\7w\2\2\u0113\u0114\7d\2\2\u0114\u0115\7i\2\2\u0115"+
		"\u0116\7t\2\2\u0116\u0117\7c\2\2\u0117\u0118\7r\2\2\u0118\u0119\7j\2\2"+
		"\u0119\64\3\2\2\2\u011a\u011b\7o\2\2\u011b\u011c\7g\2\2\u011c\u011d\7"+
		"t\2\2\u011d\u011e\7i\2\2\u011e\u011f\7g\2\2\u011f\u0120\7t\2\2\u0120\66"+
		"\3\2\2\2\u0121\u0122\7j\2\2\u0122\u0123\7c\2\2\u0123\u0124\7p\2\2\u0124"+
		"\u0125\7f\2\2\u0125\u0126\7n\2\2\u0126\u0127\7g\2\2\u0127\u0128\7t\2\2"+
		"\u01288\3\2\2\2\u0129\u012a\7u\2\2\u012a\u012b\7w\2\2\u012b\u012c\7u\2"+
		"\2\u012c\u012d\7r\2\2\u012d\u012e\7g\2\2\u012e\u012f\7p\2\2\u012f\u0130"+
		"\7f\2\2\u0130\u0131\7J\2\2\u0131\u0132\7c\2\2\u0132\u0133\7p\2\2\u0133"+
		"\u0134\7f\2\2\u0134\u0135\7n\2\2\u0135\u0136\7g\2\2\u0136\u0137\7t\2\2"+
		"\u0137:\3\2\2\2\u0138\u0139\7r\2\2\u0139\u013a\7c\2\2\u013a\u013b\7{\2"+
		"\2\u013b\u013c\7n\2\2\u013c\u013d\7q\2\2\u013d\u013e\7c\2\2\u013e\u013f"+
		"\7f\2\2\u013f<\3\2\2\2\u0140\u0141\7e\2\2\u0141\u0142\7q\2\2\u0142\u0143"+
		"\7o\2\2\u0143\u0144\7r\2\2\u0144\u0145\7n\2\2\u0145\u0146\7g\2\2\u0146"+
		"\u0147\7v\2\2\u0147\u0148\7g\2\2\u0148>\3\2\2\2\u0149\u014a\7t\2\2\u014a"+
		"\u014b\7q\2\2\u014b\u014c\7w\2\2\u014c\u014d\7v\2\2\u014d\u014e\7g\2\2"+
		"\u014e\u014f\7t\2\2\u014f@\3\2\2\2\u0150\u0151\7e\2\2\u0151\u0152\7n\2"+
		"\2\u0152\u0153\7q\2\2\u0153\u0154\7p\2\2\u0154\u0155\7g\2\2\u0155B\3\2"+
		"\2\2\u0156\u0157\7X\2\2\u0157\u0158\7g\2\2\u0158\u0159\7t\2\2\u0159\u015a"+
		"\7v\2\2\u015a\u015b\7g\2\2\u015b\u015c\7z\2\2\u015cD\3\2\2\2\u015d\u015f"+
		"\t\2\2\2\u015e\u015d\3\2\2\2\u015f\u0160\3\2\2\2\u0160\u015e\3\2\2\2\u0160"+
		"\u0161\3\2\2\2\u0161F\3\2\2\2\u0162\u0163\7*\2\2\u0163H\3\2\2\2\u0164"+
		"\u0165\7+\2\2\u0165J\3\2\2\2\u0166\u0167\7}\2\2\u0167L\3\2\2\2\u0168\u0169"+
		"\7\177\2\2\u0169N\3\2\2\2\u016a\u016b\7=\2\2\u016bP\3\2\2\2\u016c\u016d"+
		"\7.\2\2\u016dR\3\2\2\2\u016e\u016f\7\60\2\2\u016fT\3\2\2\2\u0170\u0171"+
		"\7B\2\2\u0171V\3\2\2\2\u0172\u0173\7?\2\2\u0173X\3\2\2\2\u0174\u0175\7"+
		"p\2\2\u0175\u0176\7g\2\2\u0176\u0177\7y\2\2\u0177Z\3\2\2\2\u0178\u0179"+
		"\7<\2\2\u0179\u017a\7<\2\2\u017a\\\3\2\2\2\u017b\u017f\7$\2\2\u017c\u017e"+
		"\5_\60\2\u017d\u017c\3\2\2\2\u017e\u0181\3\2\2\2\u017f\u017d\3\2\2\2\u017f"+
		"\u0180\3\2\2\2\u0180\u0182\3\2\2\2\u0181\u017f\3\2\2\2\u0182\u0191\7$"+
		"\2\2\u0183\u0184\7$\2\2\u0184\u0185\7$\2\2\u0185\u0186\7$\2\2\u0186\u018a"+
		"\3\2\2\2\u0187\u0189\13\2\2\2\u0188\u0187\3\2\2\2\u0189\u018c\3\2\2\2"+
		"\u018a\u018b\3\2\2\2\u018a\u0188\3\2\2\2\u018b\u018d\3\2\2\2\u018c\u018a"+
		"\3\2\2\2\u018d\u018e\7$\2\2\u018e\u018f\7$\2\2\u018f\u0191\7$\2\2\u0190"+
		"\u017b\3\2\2\2\u0190\u0183\3\2\2\2\u0191^\3\2\2\2\u0192\u0196\n\3\2\2"+
		"\u0193\u0194\7^\2\2\u0194\u0196\7$\2\2\u0195\u0192\3\2\2\2\u0195\u0193"+
		"\3\2\2\2\u0196`\3\2\2\2\u0197\u019b\5c\62\2\u0198\u019a\5e\63\2\u0199"+
		"\u0198\3\2\2\2\u019a\u019d\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2"+
		"\2\2\u019c\u01a6\3\2\2\2\u019d\u019b\3\2\2\2\u019e\u01a0\7b\2\2\u019f"+
		"\u01a1\n\4\2\2\u01a0\u019f\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2\u01a0\3\2"+
		"\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a4\3\2\2\2\u01a4\u01a6\7b\2\2\u01a5"+
		"\u0197\3\2\2\2\u01a5\u019e\3\2\2\2\u01a6b\3\2\2\2\u01a7\u01ae\t\5\2\2"+
		"\u01a8\u01a9\n\6\2\2\u01a9\u01ae\6\62\2\2\u01aa\u01ab\t\7\2\2\u01ab\u01ac"+
		"\t\b\2\2\u01ac\u01ae\6\62\3\2\u01ad\u01a7\3\2\2\2\u01ad\u01a8\3\2\2\2"+
		"\u01ad\u01aa\3\2\2\2\u01aed\3\2\2\2\u01af\u01b6\t\t\2\2\u01b0\u01b1\n"+
		"\6\2\2\u01b1\u01b6\6\63\4\2\u01b2\u01b3\t\7\2\2\u01b3\u01b4\t\b\2\2\u01b4"+
		"\u01b6\6\63\5\2\u01b5\u01af\3\2\2\2\u01b5\u01b0\3\2\2\2\u01b5\u01b2\3"+
		"\2\2\2\u01b6f\3\2\2\2\u01b7\u01b9\t\n\2\2\u01b8\u01b7\3\2\2\2\u01b9\u01ba"+
		"\3\2\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc"+
		"\u01bd\b\64\2\2\u01bdh\3\2\2\2\u01be\u01bf\7\61\2\2\u01bf\u01c0\7,\2\2"+
		"\u01c0\u01c4\3\2\2\2\u01c1\u01c3\13\2\2\2\u01c2\u01c1\3\2\2\2\u01c3\u01c6"+
		"\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c5\u01c7\3\2\2\2\u01c6"+
		"\u01c4\3\2\2\2\u01c7\u01c8\7,\2\2\u01c8\u01c9\7\61\2\2\u01c9\u01ca\3\2"+
		"\2\2\u01ca\u01cb\b\65\3\2\u01cbj\3\2\2\2\u01cc\u01cd\7\61\2\2\u01cd\u01ce"+
		"\7\61\2\2\u01ce\u01d2\3\2\2\2\u01cf\u01d1\n\13\2\2\u01d0\u01cf\3\2\2\2"+
		"\u01d1\u01d4\3\2\2\2\u01d2\u01d0\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01d5"+
		"\3\2\2\2\u01d4\u01d2\3\2\2\2\u01d5\u01d6\b\66\3\2\u01d6l\3\2\2\2\u01d7"+
		"\u01d8\13\2\2\2\u01d8n\3\2\2\2\21\2\u010d\u0160\u017f\u018a\u0190\u0195"+
		"\u019b\u01a2\u01a5\u01ad\u01b5\u01ba\u01c4\u01d2\4\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}