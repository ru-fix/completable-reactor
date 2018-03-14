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
		T__17=18, T__18=19, Coordinate=20, SUBGRAPH=21, MERGER=22, HANDLER=23, 
		SUSPEND_HANDLER=24, PAYLOAD=25, COMPLETE=26, ROUTER=27, CLONE=28, VERTEX=29, 
		DecimalNumeral=30, LPAREN=31, RPAREN=32, LBRACE=33, RBRACE=34, SEMI=35, 
		COMMA=36, DOT=37, AT=38, ASSIGN=39, NEW=40, COLONCOLON=41, StringLiteral=42, 
		Identifier=43, WS=44, COMMENT=45, LINE_COMMENT=46, OTHER_SYMBOL=47;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "Coordinate", "SUBGRAPH", "MERGER", "HANDLER", "SUSPEND_HANDLER", 
		"PAYLOAD", "COMPLETE", "ROUTER", "CLONE", "VERTEX", "DecimalNumeral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "SEMI", "COMMA", "DOT", "AT", 
		"ASSIGN", "NEW", "COLONCOLON", "StringLiteral", "StringCharacter", "Identifier", 
		"JavaLetter", "JavaLetterOrDigit", "WS", "COMMENT", "LINE_COMMENT", "OTHER_SYMBOL"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'public'", "'private'", "'static'", "'open'", "'sealed'", "'class'", 
		"'extends'", "':'", "'Graph'", "'<'", "'>'", "'val'", "'withMerger'", 
		"'withoutMerger'", "'handleBy'", "'on'", "'onAny'", "'mergeBy'", "'coordinates'", 
		null, "'subgraph'", "'merger'", "'handler'", "'suspendHandler'", "'payload'", 
		"'complete'", "'router'", "'clone'", "'Vertex'", null, "'('", "')'", "'{'", 
		"'}'", "';'", "','", "'.'", "'@'", "'='", "'new'", "'::'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "Coordinate", "SUBGRAPH", 
		"MERGER", "HANDLER", "SUSPEND_HANDLER", "PAYLOAD", "COMPLETE", "ROUTER", 
		"CLONE", "VERTEX", "DecimalNumeral", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"SEMI", "COMMA", "DOT", "AT", "ASSIGN", "NEW", "COLONCOLON", "StringLiteral", 
		"Identifier", "WS", "COMMENT", "LINE_COMMENT", "OTHER_SYMBOL"
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
		case 44:
			return JavaLetter_sempred((RuleContext)_localctx, predIndex);
		case 45:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\61\u01b3\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\25\5\25\u00e8\n\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\37\6\37\u0139\n\37\r\37\16\37\u013a\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3"+
		"$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3)\3*\3*\3*\3+\3+\7+\u0158\n+\f+"+
		"\16+\u015b\13+\3+\3+\3+\3+\3+\3+\7+\u0163\n+\f+\16+\u0166\13+\3+\3+\3"+
		"+\5+\u016b\n+\3,\3,\3,\5,\u0170\n,\3-\3-\7-\u0174\n-\f-\16-\u0177\13-"+
		"\3-\3-\6-\u017b\n-\r-\16-\u017c\3-\5-\u0180\n-\3.\3.\3.\3.\3.\3.\5.\u0188"+
		"\n.\3/\3/\3/\3/\3/\3/\5/\u0190\n/\3\60\6\60\u0193\n\60\r\60\16\60\u0194"+
		"\3\60\3\60\3\61\3\61\3\61\3\61\7\61\u019d\n\61\f\61\16\61\u01a0\13\61"+
		"\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\7\62\u01ab\n\62\f\62\16"+
		"\62\u01ae\13\62\3\62\3\62\3\63\3\63\4\u0164\u019e\2\64\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K"+
		"\'M(O)Q*S+U,W\2Y-[\2]\2_.a/c\60e\61\3\2\f\4\2\62;aa\3\2$$\3\2bb\6\2&&"+
		"C\\aac|\4\2\2\u0081\ud802\udc01\3\2\ud802\udc01\3\2\udc02\ue001\7\2&&"+
		"\62;C\\aac|\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u01bf\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2"+
		"?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3"+
		"\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2Y\3\2\2"+
		"\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\3g\3\2\2\2\5n\3\2\2\2\7"+
		"v\3\2\2\2\t}\3\2\2\2\13\u0082\3\2\2\2\r\u0089\3\2\2\2\17\u008f\3\2\2\2"+
		"\21\u0097\3\2\2\2\23\u0099\3\2\2\2\25\u009f\3\2\2\2\27\u00a1\3\2\2\2\31"+
		"\u00a3\3\2\2\2\33\u00a7\3\2\2\2\35\u00b2\3\2\2\2\37\u00c0\3\2\2\2!\u00c9"+
		"\3\2\2\2#\u00cc\3\2\2\2%\u00d2\3\2\2\2\'\u00da\3\2\2\2)\u00e7\3\2\2\2"+
		"+\u00eb\3\2\2\2-\u00f4\3\2\2\2/\u00fb\3\2\2\2\61\u0103\3\2\2\2\63\u0112"+
		"\3\2\2\2\65\u011a\3\2\2\2\67\u0123\3\2\2\29\u012a\3\2\2\2;\u0130\3\2\2"+
		"\2=\u0138\3\2\2\2?\u013c\3\2\2\2A\u013e\3\2\2\2C\u0140\3\2\2\2E\u0142"+
		"\3\2\2\2G\u0144\3\2\2\2I\u0146\3\2\2\2K\u0148\3\2\2\2M\u014a\3\2\2\2O"+
		"\u014c\3\2\2\2Q\u014e\3\2\2\2S\u0152\3\2\2\2U\u016a\3\2\2\2W\u016f\3\2"+
		"\2\2Y\u017f\3\2\2\2[\u0187\3\2\2\2]\u018f\3\2\2\2_\u0192\3\2\2\2a\u0198"+
		"\3\2\2\2c\u01a6\3\2\2\2e\u01b1\3\2\2\2gh\7r\2\2hi\7w\2\2ij\7d\2\2jk\7"+
		"n\2\2kl\7k\2\2lm\7e\2\2m\4\3\2\2\2no\7r\2\2op\7t\2\2pq\7k\2\2qr\7x\2\2"+
		"rs\7c\2\2st\7v\2\2tu\7g\2\2u\6\3\2\2\2vw\7u\2\2wx\7v\2\2xy\7c\2\2yz\7"+
		"v\2\2z{\7k\2\2{|\7e\2\2|\b\3\2\2\2}~\7q\2\2~\177\7r\2\2\177\u0080\7g\2"+
		"\2\u0080\u0081\7p\2\2\u0081\n\3\2\2\2\u0082\u0083\7u\2\2\u0083\u0084\7"+
		"g\2\2\u0084\u0085\7c\2\2\u0085\u0086\7n\2\2\u0086\u0087\7g\2\2\u0087\u0088"+
		"\7f\2\2\u0088\f\3\2\2\2\u0089\u008a\7e\2\2\u008a\u008b\7n\2\2\u008b\u008c"+
		"\7c\2\2\u008c\u008d\7u\2\2\u008d\u008e\7u\2\2\u008e\16\3\2\2\2\u008f\u0090"+
		"\7g\2\2\u0090\u0091\7z\2\2\u0091\u0092\7v\2\2\u0092\u0093\7g\2\2\u0093"+
		"\u0094\7p\2\2\u0094\u0095\7f\2\2\u0095\u0096\7u\2\2\u0096\20\3\2\2\2\u0097"+
		"\u0098\7<\2\2\u0098\22\3\2\2\2\u0099\u009a\7I\2\2\u009a\u009b\7t\2\2\u009b"+
		"\u009c\7c\2\2\u009c\u009d\7r\2\2\u009d\u009e\7j\2\2\u009e\24\3\2\2\2\u009f"+
		"\u00a0\7>\2\2\u00a0\26\3\2\2\2\u00a1\u00a2\7@\2\2\u00a2\30\3\2\2\2\u00a3"+
		"\u00a4\7x\2\2\u00a4\u00a5\7c\2\2\u00a5\u00a6\7n\2\2\u00a6\32\3\2\2\2\u00a7"+
		"\u00a8\7y\2\2\u00a8\u00a9\7k\2\2\u00a9\u00aa\7v\2\2\u00aa\u00ab\7j\2\2"+
		"\u00ab\u00ac\7O\2\2\u00ac\u00ad\7g\2\2\u00ad\u00ae\7t\2\2\u00ae\u00af"+
		"\7i\2\2\u00af\u00b0\7g\2\2\u00b0\u00b1\7t\2\2\u00b1\34\3\2\2\2\u00b2\u00b3"+
		"\7y\2\2\u00b3\u00b4\7k\2\2\u00b4\u00b5\7v\2\2\u00b5\u00b6\7j\2\2\u00b6"+
		"\u00b7\7q\2\2\u00b7\u00b8\7w\2\2\u00b8\u00b9\7v\2\2\u00b9\u00ba\7O\2\2"+
		"\u00ba\u00bb\7g\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd\7i\2\2\u00bd\u00be"+
		"\7g\2\2\u00be\u00bf\7t\2\2\u00bf\36\3\2\2\2\u00c0\u00c1\7j\2\2\u00c1\u00c2"+
		"\7c\2\2\u00c2\u00c3\7p\2\2\u00c3\u00c4\7f\2\2\u00c4\u00c5\7n\2\2\u00c5"+
		"\u00c6\7g\2\2\u00c6\u00c7\7D\2\2\u00c7\u00c8\7{\2\2\u00c8 \3\2\2\2\u00c9"+
		"\u00ca\7q\2\2\u00ca\u00cb\7p\2\2\u00cb\"\3\2\2\2\u00cc\u00cd\7q\2\2\u00cd"+
		"\u00ce\7p\2\2\u00ce\u00cf\7C\2\2\u00cf\u00d0\7p\2\2\u00d0\u00d1\7{\2\2"+
		"\u00d1$\3\2\2\2\u00d2\u00d3\7o\2\2\u00d3\u00d4\7g\2\2\u00d4\u00d5\7t\2"+
		"\2\u00d5\u00d6\7i\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8\7D\2\2\u00d8\u00d9"+
		"\7{\2\2\u00d9&\3\2\2\2\u00da\u00db\7e\2\2\u00db\u00dc\7q\2\2\u00dc\u00dd"+
		"\7q\2\2\u00dd\u00de\7t\2\2\u00de\u00df\7f\2\2\u00df\u00e0\7k\2\2\u00e0"+
		"\u00e1\7p\2\2\u00e1\u00e2\7c\2\2\u00e2\u00e3\7v\2\2\u00e3\u00e4\7g\2\2"+
		"\u00e4\u00e5\7u\2\2\u00e5(\3\2\2\2\u00e6\u00e8\7/\2\2\u00e7\u00e6\3\2"+
		"\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00ea\5=\37\2\u00ea"+
		"*\3\2\2\2\u00eb\u00ec\7u\2\2\u00ec\u00ed\7w\2\2\u00ed\u00ee\7d\2\2\u00ee"+
		"\u00ef\7i\2\2\u00ef\u00f0\7t\2\2\u00f0\u00f1\7c\2\2\u00f1\u00f2\7r\2\2"+
		"\u00f2\u00f3\7j\2\2\u00f3,\3\2\2\2\u00f4\u00f5\7o\2\2\u00f5\u00f6\7g\2"+
		"\2\u00f6\u00f7\7t\2\2\u00f7\u00f8\7i\2\2\u00f8\u00f9\7g\2\2\u00f9\u00fa"+
		"\7t\2\2\u00fa.\3\2\2\2\u00fb\u00fc\7j\2\2\u00fc\u00fd\7c\2\2\u00fd\u00fe"+
		"\7p\2\2\u00fe\u00ff\7f\2\2\u00ff\u0100\7n\2\2\u0100\u0101\7g\2\2\u0101"+
		"\u0102\7t\2\2\u0102\60\3\2\2\2\u0103\u0104\7u\2\2\u0104\u0105\7w\2\2\u0105"+
		"\u0106\7u\2\2\u0106\u0107\7r\2\2\u0107\u0108\7g\2\2\u0108\u0109\7p\2\2"+
		"\u0109\u010a\7f\2\2\u010a\u010b\7J\2\2\u010b\u010c\7c\2\2\u010c\u010d"+
		"\7p\2\2\u010d\u010e\7f\2\2\u010e\u010f\7n\2\2\u010f\u0110\7g\2\2\u0110"+
		"\u0111\7t\2\2\u0111\62\3\2\2\2\u0112\u0113\7r\2\2\u0113\u0114\7c\2\2\u0114"+
		"\u0115\7{\2\2\u0115\u0116\7n\2\2\u0116\u0117\7q\2\2\u0117\u0118\7c\2\2"+
		"\u0118\u0119\7f\2\2\u0119\64\3\2\2\2\u011a\u011b\7e\2\2\u011b\u011c\7"+
		"q\2\2\u011c\u011d\7o\2\2\u011d\u011e\7r\2\2\u011e\u011f\7n\2\2\u011f\u0120"+
		"\7g\2\2\u0120\u0121\7v\2\2\u0121\u0122\7g\2\2\u0122\66\3\2\2\2\u0123\u0124"+
		"\7t\2\2\u0124\u0125\7q\2\2\u0125\u0126\7w\2\2\u0126\u0127\7v\2\2\u0127"+
		"\u0128\7g\2\2\u0128\u0129\7t\2\2\u01298\3\2\2\2\u012a\u012b\7e\2\2\u012b"+
		"\u012c\7n\2\2\u012c\u012d\7q\2\2\u012d\u012e\7p\2\2\u012e\u012f\7g\2\2"+
		"\u012f:\3\2\2\2\u0130\u0131\7X\2\2\u0131\u0132\7g\2\2\u0132\u0133\7t\2"+
		"\2\u0133\u0134\7v\2\2\u0134\u0135\7g\2\2\u0135\u0136\7z\2\2\u0136<\3\2"+
		"\2\2\u0137\u0139\t\2\2\2\u0138\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a"+
		"\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b>\3\2\2\2\u013c\u013d\7*\2\2\u013d"+
		"@\3\2\2\2\u013e\u013f\7+\2\2\u013fB\3\2\2\2\u0140\u0141\7}\2\2\u0141D"+
		"\3\2\2\2\u0142\u0143\7\177\2\2\u0143F\3\2\2\2\u0144\u0145\7=\2\2\u0145"+
		"H\3\2\2\2\u0146\u0147\7.\2\2\u0147J\3\2\2\2\u0148\u0149\7\60\2\2\u0149"+
		"L\3\2\2\2\u014a\u014b\7B\2\2\u014bN\3\2\2\2\u014c\u014d\7?\2\2\u014dP"+
		"\3\2\2\2\u014e\u014f\7p\2\2\u014f\u0150\7g\2\2\u0150\u0151\7y\2\2\u0151"+
		"R\3\2\2\2\u0152\u0153\7<\2\2\u0153\u0154\7<\2\2\u0154T\3\2\2\2\u0155\u0159"+
		"\7$\2\2\u0156\u0158\5W,\2\u0157\u0156\3\2\2\2\u0158\u015b\3\2\2\2\u0159"+
		"\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015c\3\2\2\2\u015b\u0159\3\2"+
		"\2\2\u015c\u016b\7$\2\2\u015d\u015e\7$\2\2\u015e\u015f\7$\2\2\u015f\u0160"+
		"\7$\2\2\u0160\u0164\3\2\2\2\u0161\u0163\13\2\2\2\u0162\u0161\3\2\2\2\u0163"+
		"\u0166\3\2\2\2\u0164\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0167\3\2"+
		"\2\2\u0166\u0164\3\2\2\2\u0167\u0168\7$\2\2\u0168\u0169\7$\2\2\u0169\u016b"+
		"\7$\2\2\u016a\u0155\3\2\2\2\u016a\u015d\3\2\2\2\u016bV\3\2\2\2\u016c\u0170"+
		"\n\3\2\2\u016d\u016e\7^\2\2\u016e\u0170\7$\2\2\u016f\u016c\3\2\2\2\u016f"+
		"\u016d\3\2\2\2\u0170X\3\2\2\2\u0171\u0175\5[.\2\u0172\u0174\5]/\2\u0173"+
		"\u0172\3\2\2\2\u0174\u0177\3\2\2\2\u0175\u0173\3\2\2\2\u0175\u0176\3\2"+
		"\2\2\u0176\u0180\3\2\2\2\u0177\u0175\3\2\2\2\u0178\u017a\7b\2\2\u0179"+
		"\u017b\n\4\2\2\u017a\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017a\3\2"+
		"\2\2\u017c\u017d\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u0180\7b\2\2\u017f"+
		"\u0171\3\2\2\2\u017f\u0178\3\2\2\2\u0180Z\3\2\2\2\u0181\u0188\t\5\2\2"+
		"\u0182\u0183\n\6\2\2\u0183\u0188\6.\2\2\u0184\u0185\t\7\2\2\u0185\u0186"+
		"\t\b\2\2\u0186\u0188\6.\3\2\u0187\u0181\3\2\2\2\u0187\u0182\3\2\2\2\u0187"+
		"\u0184\3\2\2\2\u0188\\\3\2\2\2\u0189\u0190\t\t\2\2\u018a\u018b\n\6\2\2"+
		"\u018b\u0190\6/\4\2\u018c\u018d\t\7\2\2\u018d\u018e\t\b\2\2\u018e\u0190"+
		"\6/\5\2\u018f\u0189\3\2\2\2\u018f\u018a\3\2\2\2\u018f\u018c\3\2\2\2\u0190"+
		"^\3\2\2\2\u0191\u0193\t\n\2\2\u0192\u0191\3\2\2\2\u0193\u0194\3\2\2\2"+
		"\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0197"+
		"\b\60\2\2\u0197`\3\2\2\2\u0198\u0199\7\61\2\2\u0199\u019a\7,\2\2\u019a"+
		"\u019e\3\2\2\2\u019b\u019d\13\2\2\2\u019c\u019b\3\2\2\2\u019d\u01a0\3"+
		"\2\2\2\u019e\u019f\3\2\2\2\u019e\u019c\3\2\2\2\u019f\u01a1\3\2\2\2\u01a0"+
		"\u019e\3\2\2\2\u01a1\u01a2\7,\2\2\u01a2\u01a3\7\61\2\2\u01a3\u01a4\3\2"+
		"\2\2\u01a4\u01a5\b\61\3\2\u01a5b\3\2\2\2\u01a6\u01a7\7\61\2\2\u01a7\u01a8"+
		"\7\61\2\2\u01a8\u01ac\3\2\2\2\u01a9\u01ab\n\13\2\2\u01aa\u01a9\3\2\2\2"+
		"\u01ab\u01ae\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ac\u01ad\3\2\2\2\u01ad\u01af"+
		"\3\2\2\2\u01ae\u01ac\3\2\2\2\u01af\u01b0\b\62\3\2\u01b0d\3\2\2\2\u01b1"+
		"\u01b2\13\2\2\2\u01b2f\3\2\2\2\21\2\u00e7\u013a\u0159\u0164\u016a\u016f"+
		"\u0175\u017c\u017f\u0187\u018f\u0194\u019e\u01ac\4\b\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}