// Generated from KotlinParser.g4 by ANTLR 4.7
package ru.fix.completable.reactor.parser.java.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class KotlinParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		MULTILINE_COMMENT=1, SINGLELINE_COMMENT=2, WHITESPACE=3, IntegerLiteral=4, 
		SEMI=5, OPEN_BLOCK=6, CLOSE_BLOCK=7, HexadecimalLiteral=8, CharacterLiteral=9, 
		TRIPLE_QUOTE=10, SINGLE_QUOTE=11, PACKAGE=12, IMPORT=13, DOT=14, STAR=15, 
		COMMA=16, LT=17, LTE=18, GT=19, GTE=20, EQ=21, EQ_EQ=22, EQ_EQ_EQ=23, 
		NEQ=24, COLON=25, BRACE_OPEN=26, BRACE_CLOSE=27, Q=28, DA=29, DISJ=30, 
		CONJ=31, ELVIS=32, LONG_RANGE=33, RANGE=34, REFERENCE=35, TRUE=36, FALSE=37, 
		NULL=38, OP_ASTERISK=39, OP_DIV=40, OP_MOD=41, OP_PLUS=42, OP_MUNUS=43, 
		OP_IN=44, OP_NOT_IN=45, OP_IS=46, OP_NOT_IS=47, OP_AS=48, OP_AS_SAFE=49, 
		OP_PLUS_ASSIGN=50, OP_MINUS_ASSIGN=51, OP_MULT_ASSIGN=52, OP_DIV_ASSIGN=53, 
		OP_MOD_ASSIGN=54, OP_DECREMENT=55, OP_INCREMENT=56, OP_NULL_ASSERT=57, 
		OP_NOT=58, SQ_OPEN=59, SQ_CLOSE=60, KEYWORD_val=61, KEYWORD_var=62, KEYWORD_by=63, 
		KEYWORD_dynamic=64, KEYWORD_where=65, GET=66, SET=67, HierarchyModifier_abstract=68, 
		HierarchyModifier_open=69, HierarchyModifier_final=70, HierarchyModifier_override=71, 
		ClassModifier_enum=72, ClassModifier_annotation=73, ClassModifier_data=74, 
		AccessModifier_private=75, AccessModifier_protected=76, AccessModifier_public=77, 
		AccessModifier_internal=78, VarianceAnnotation_out=79, DOG=80, AnnotationUseSiteTarget_file=81, 
		AnnotationUseSiteTarget_field=82, AnnotationUseSiteTarget_property=83, 
		AnnotationUseSiteTarget_param=84, AnnotationUseSiteTarget_sparam=85, Jump_throw=86, 
		Jump_continue=87, Jump_return=88, Jump_break=89, KEYWORD_constructor=90, 
		ConstructorDelegationCall_this=91, ConstructorDelegationCall_super=92, 
		Declaration_class=93, Declaration_interface=94, Declaration_object=95, 
		Declaration_companion=96, Declaration_fun=97, Declaration_init=98, CF_if=99, 
		CF_else=100, CF_when=101, CF_while=102, CF_for=103, CF_do=104, CF_try=105, 
		CF_catch=106, CF_FINALLY=107, BAX=108, SimpleName=109, SINLE_QUOTE_WHITESPACE=110, 
		SINGLE_TEXT=111, SINLE_QUOTE_CLOSE=112, SINLE_QUOTE_ESCAPED_CHAR=113, 
		SINLE_QUOTE_EXPRESSION_START=114, SINGLE_QUOTE_REF=115, MULTILINE_QUOTE_TEXT=116, 
		MULTILINE_QUOTE_CLOSE=117, MULTILINE_QUOTES=118, MULTILINE_QUOTE_EXPRESSION_START=119, 
		MULTILINE_QUOTE_REF=120;
	public static final int
		RULE_kotlinFile = 0, RULE_preamble = 1, RULE_packageHeader = 2, RULE_importHeader = 3, 
		RULE_toplevelObject = 4, RULE_memberDeclaration = 5, RULE_optionalProjection = 6, 
		RULE_typeParameters = 7, RULE_typeParameter = 8, RULE_typeArguments = 9, 
		RULE_typeConstraints = 10, RULE_typeConstraint = 11, RULE_type = 12, RULE_typeDescriptor = 13, 
		RULE_userType = 14, RULE_simpleUserType_typeParam = 15, RULE_simpleUserType = 16, 
		RULE_variableDeclarationEntry = 17, RULE_functionType_paramOrType = 18, 
		RULE_extensionFunctionTypeNoReceiver = 19, RULE_functionTypeNoReceiver = 20, 
		RULE_multipleVariableDeclarations = 21, RULE_propertyDeclaration = 22, 
		RULE_getter = 23, RULE_setter = 24, RULE_modifiers = 25, RULE_modifier = 26, 
		RULE_modifierKeyword = 27, RULE_hierarchyModifier = 28, RULE_classModifier = 29, 
		RULE_accessModifier = 30, RULE_varianceAnnotation = 31, RULE_annotations = 32, 
		RULE_annotation = 33, RULE_annotationUseSiteTarget = 34, RULE_valueArgument = 35, 
		RULE_valueArguments = 36, RULE_unescapedAnnotation = 37, RULE_jump = 38, 
		RULE_labelReference = 39, RULE_labelDefinition = 40, RULE_parameter = 41, 
		RULE_functionParameter = 42, RULE_primaryConstructor = 43, RULE_secondaryConstructor = 44, 
		RULE_constructorDelegationCall = 45, RULE_callSuffix = 46, RULE_constructorInvocation = 47, 
		RULE_explicitDelegation = 48, RULE_delegationSpecifier = 49, RULE_classDeclaration = 50, 
		RULE_supertypesSpecifiers = 51, RULE_objectDeclaration = 52, RULE_companionObject = 53, 
		RULE_classBody = 54, RULE_members = 55, RULE_valueParameters = 56, RULE_functionDeclaration = 57, 
		RULE_statements = 58, RULE_functionBody = 59, RULE_block = 60, RULE_anonymousInitializer = 61, 
		RULE_enumClassBody = 62, RULE_enumEntries = 63, RULE_enumEntry = 64, RULE_ifExpression = 65, 
		RULE_tryExpression = 66, RULE_catchBlock = 67, RULE_finallyBlock = 68, 
		RULE_loop = 69, RULE_forLoop = 70, RULE_whileLoop = 71, RULE_doWhileLoop = 72, 
		RULE_expression = 73, RULE_disjunction = 74, RULE_conjunction = 75, RULE_equalityComparison = 76, 
		RULE_comparison = 77, RULE_namedInfix = 78, RULE_elvisExpression = 79, 
		RULE_infixFunctionCall = 80, RULE_rangeExpression = 81, RULE_additiveExpression = 82, 
		RULE_multiplicativeExpression = 83, RULE_typeRHS = 84, RULE_prefixUnaryExpression = 85, 
		RULE_postfixUnaryExpression = 86, RULE_callableReference = 87, RULE_identifier = 88, 
		RULE_stringLiteral = 89, RULE_atomicExpression = 90, RULE_literalConstant = 91, 
		RULE_declaration = 92, RULE_statement = 93, RULE_multiplicativeOperation = 94, 
		RULE_additiveOperation = 95, RULE_inOperation = 96, RULE_typeOperation = 97, 
		RULE_isOperation = 98, RULE_comparisonOperation = 99, RULE_equalityOperation = 100, 
		RULE_assignmentOperator = 101, RULE_prefixUnaryOperation = 102, RULE_postfixUnaryOperation = 103, 
		RULE_annotatedLambda = 104, RULE_memberAccessOperation = 105, RULE_functionLiteral = 106, 
		RULE_arrayAccess = 107, RULE_objectLiteral = 108, RULE_when = 109, RULE_whenEntry = 110, 
		RULE_whenCondition = 111;
	public static final String[] ruleNames = {
		"kotlinFile", "preamble", "packageHeader", "importHeader", "toplevelObject", 
		"memberDeclaration", "optionalProjection", "typeParameters", "typeParameter", 
		"typeArguments", "typeConstraints", "typeConstraint", "type", "typeDescriptor", 
		"userType", "simpleUserType_typeParam", "simpleUserType", "variableDeclarationEntry", 
		"functionType_paramOrType", "extensionFunctionTypeNoReceiver", "functionTypeNoReceiver", 
		"multipleVariableDeclarations", "propertyDeclaration", "getter", "setter", 
		"modifiers", "modifier", "modifierKeyword", "hierarchyModifier", "classModifier", 
		"accessModifier", "varianceAnnotation", "annotations", "annotation", "annotationUseSiteTarget", 
		"valueArgument", "valueArguments", "unescapedAnnotation", "jump", "labelReference", 
		"labelDefinition", "parameter", "functionParameter", "primaryConstructor", 
		"secondaryConstructor", "constructorDelegationCall", "callSuffix", "constructorInvocation", 
		"explicitDelegation", "delegationSpecifier", "classDeclaration", "supertypesSpecifiers", 
		"objectDeclaration", "companionObject", "classBody", "members", "valueParameters", 
		"functionDeclaration", "statements", "functionBody", "block", "anonymousInitializer", 
		"enumClassBody", "enumEntries", "enumEntry", "ifExpression", "tryExpression", 
		"catchBlock", "finallyBlock", "loop", "forLoop", "whileLoop", "doWhileLoop", 
		"expression", "disjunction", "conjunction", "equalityComparison", "comparison", 
		"namedInfix", "elvisExpression", "infixFunctionCall", "rangeExpression", 
		"additiveExpression", "multiplicativeExpression", "typeRHS", "prefixUnaryExpression", 
		"postfixUnaryExpression", "callableReference", "identifier", "stringLiteral", 
		"atomicExpression", "literalConstant", "declaration", "statement", "multiplicativeOperation", 
		"additiveOperation", "inOperation", "typeOperation", "isOperation", "comparisonOperation", 
		"equalityOperation", "assignmentOperator", "prefixUnaryOperation", "postfixUnaryOperation", 
		"annotatedLambda", "memberAccessOperation", "functionLiteral", "arrayAccess", 
		"objectLiteral", "when", "whenEntry", "whenCondition"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, "';'", "'{'", "'}'", null, null, null, null, 
		"'package'", "'import'", "'.'", "'*'", "','", "'<'", "'<='", "'>'", "'>='", 
		"'='", "'=='", "'==='", "'!='", "':'", "'('", "')'", "'?'", "'!!.'", "'||'", 
		"'&&'", "'?:'", "'...'", "'..'", "'::'", "'true'", "'false'", "'null'", 
		"'->'", "'/'", "'%'", "'+'", "'-'", "'in'", "'!in'", "'is'", "'!is'", 
		"'as'", "'as?'", "'+='", "'-='", "'*='", "'/='", "'%='", "'--'", "'++'", 
		"'!!'", "'!'", "'['", "']'", "'val'", "'var'", "'by'", "'dynamic'", "'where'", 
		"'get'", "'set'", "'abstract'", "'open'", "'final'", "'override'", "'enum'", 
		"'annotation'", "'data'", "'private'", "'protected'", "'public'", "'internal'", 
		"'out'", "'@'", "'file'", "'field'", "'property'", "'param'", "'sparam'", 
		"'throw'", "'continue'", "'return'", "'break'", "'constructor'", "'this'", 
		"'super'", "'class'", "'interface'", "'object'", "'companion'", "'fun'", 
		"'init'", "'if'", "'else'", "'when'", "'while'", "'for'", "'do'", "'try'", 
		"'catch'", "'finally'", "'$'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "MULTILINE_COMMENT", "SINGLELINE_COMMENT", "WHITESPACE", "IntegerLiteral", 
		"SEMI", "OPEN_BLOCK", "CLOSE_BLOCK", "HexadecimalLiteral", "CharacterLiteral", 
		"TRIPLE_QUOTE", "SINGLE_QUOTE", "PACKAGE", "IMPORT", "DOT", "STAR", "COMMA", 
		"LT", "LTE", "GT", "GTE", "EQ", "EQ_EQ", "EQ_EQ_EQ", "NEQ", "COLON", "BRACE_OPEN", 
		"BRACE_CLOSE", "Q", "DA", "DISJ", "CONJ", "ELVIS", "LONG_RANGE", "RANGE", 
		"REFERENCE", "TRUE", "FALSE", "NULL", "OP_ASTERISK", "OP_DIV", "OP_MOD", 
		"OP_PLUS", "OP_MUNUS", "OP_IN", "OP_NOT_IN", "OP_IS", "OP_NOT_IS", "OP_AS", 
		"OP_AS_SAFE", "OP_PLUS_ASSIGN", "OP_MINUS_ASSIGN", "OP_MULT_ASSIGN", "OP_DIV_ASSIGN", 
		"OP_MOD_ASSIGN", "OP_DECREMENT", "OP_INCREMENT", "OP_NULL_ASSERT", "OP_NOT", 
		"SQ_OPEN", "SQ_CLOSE", "KEYWORD_val", "KEYWORD_var", "KEYWORD_by", "KEYWORD_dynamic", 
		"KEYWORD_where", "GET", "SET", "HierarchyModifier_abstract", "HierarchyModifier_open", 
		"HierarchyModifier_final", "HierarchyModifier_override", "ClassModifier_enum", 
		"ClassModifier_annotation", "ClassModifier_data", "AccessModifier_private", 
		"AccessModifier_protected", "AccessModifier_public", "AccessModifier_internal", 
		"VarianceAnnotation_out", "DOG", "AnnotationUseSiteTarget_file", "AnnotationUseSiteTarget_field", 
		"AnnotationUseSiteTarget_property", "AnnotationUseSiteTarget_param", "AnnotationUseSiteTarget_sparam", 
		"Jump_throw", "Jump_continue", "Jump_return", "Jump_break", "KEYWORD_constructor", 
		"ConstructorDelegationCall_this", "ConstructorDelegationCall_super", "Declaration_class", 
		"Declaration_interface", "Declaration_object", "Declaration_companion", 
		"Declaration_fun", "Declaration_init", "CF_if", "CF_else", "CF_when", 
		"CF_while", "CF_for", "CF_do", "CF_try", "CF_catch", "CF_FINALLY", "BAX", 
		"SimpleName", "SINLE_QUOTE_WHITESPACE", "SINGLE_TEXT", "SINLE_QUOTE_CLOSE", 
		"SINLE_QUOTE_ESCAPED_CHAR", "SINLE_QUOTE_EXPRESSION_START", "SINGLE_QUOTE_REF", 
		"MULTILINE_QUOTE_TEXT", "MULTILINE_QUOTE_CLOSE", "MULTILINE_QUOTES", "MULTILINE_QUOTE_EXPRESSION_START", 
		"MULTILINE_QUOTE_REF"
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

	@Override
	public String getGrammarFileName() { return "KotlinParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public KotlinParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class KotlinFileContext extends ParserRuleContext {
		public PreambleContext preamble() {
			return getRuleContext(PreambleContext.class,0);
		}
		public List<ToplevelObjectContext> toplevelObject() {
			return getRuleContexts(ToplevelObjectContext.class);
		}
		public ToplevelObjectContext toplevelObject(int i) {
			return getRuleContext(ToplevelObjectContext.class,i);
		}
		public KotlinFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_kotlinFile; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterKotlinFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitKotlinFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitKotlinFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KotlinFileContext kotlinFile() throws RecognitionException {
		KotlinFileContext _localctx = new KotlinFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_kotlinFile);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			preamble();
			setState(228);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 44)) & ~0x3f) == 0 && ((1L << (_la - 44)) & ((1L << (OP_IN - 44)) | (1L << (KEYWORD_val - 44)) | (1L << (KEYWORD_var - 44)) | (1L << (HierarchyModifier_abstract - 44)) | (1L << (HierarchyModifier_open - 44)) | (1L << (HierarchyModifier_final - 44)) | (1L << (HierarchyModifier_override - 44)) | (1L << (ClassModifier_enum - 44)) | (1L << (ClassModifier_annotation - 44)) | (1L << (ClassModifier_data - 44)) | (1L << (AccessModifier_private - 44)) | (1L << (AccessModifier_protected - 44)) | (1L << (AccessModifier_public - 44)) | (1L << (AccessModifier_internal - 44)) | (1L << (VarianceAnnotation_out - 44)) | (1L << (DOG - 44)) | (1L << (Declaration_class - 44)) | (1L << (Declaration_interface - 44)) | (1L << (Declaration_object - 44)) | (1L << (Declaration_fun - 44)))) != 0)) {
				{
				{
				setState(225);
				toplevelObject();
				}
				}
				setState(230);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PreambleContext extends ParserRuleContext {
		public PackageHeaderContext packageHeader() {
			return getRuleContext(PackageHeaderContext.class,0);
		}
		public List<ImportHeaderContext> importHeader() {
			return getRuleContexts(ImportHeaderContext.class);
		}
		public ImportHeaderContext importHeader(int i) {
			return getRuleContext(ImportHeaderContext.class,i);
		}
		public PreambleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_preamble; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPreamble(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPreamble(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPreamble(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PreambleContext preamble() throws RecognitionException {
		PreambleContext _localctx = new PreambleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_preamble);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PACKAGE) {
				{
				setState(231);
				packageHeader();
				}
			}

			setState(237);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(234);
				importHeader();
				}
				}
				setState(239);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PackageHeaderContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public PackageHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPackageHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPackageHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPackageHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PackageHeaderContext packageHeader() throws RecognitionException {
		PackageHeaderContext _localctx = new PackageHeaderContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_packageHeader);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(PACKAGE);
			setState(241);
			identifier();
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(242);
				match(DOT);
				setState(243);
				identifier();
				}
				}
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(249);
				match(SEMI);
				}
				}
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportHeaderContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public ImportHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterImportHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitImportHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitImportHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportHeaderContext importHeader() throws RecognitionException {
		ImportHeaderContext _localctx = new ImportHeaderContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_importHeader);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			match(IMPORT);
			setState(256);
			identifier();
			setState(261);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(257);
					match(DOT);
					setState(258);
					identifier();
					}
					} 
				}
				setState(263);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(268);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				{
				setState(264);
				match(DOT);
				setState(265);
				match(STAR);
				}
				break;
			case OP_AS:
				{
				setState(266);
				match(OP_AS);
				setState(267);
				match(SimpleName);
				}
				break;
			case EOF:
			case SEMI:
			case IMPORT:
			case OP_IN:
			case KEYWORD_val:
			case KEYWORD_var:
			case HierarchyModifier_abstract:
			case HierarchyModifier_open:
			case HierarchyModifier_final:
			case HierarchyModifier_override:
			case ClassModifier_enum:
			case ClassModifier_annotation:
			case ClassModifier_data:
			case AccessModifier_private:
			case AccessModifier_protected:
			case AccessModifier_public:
			case AccessModifier_internal:
			case VarianceAnnotation_out:
			case DOG:
			case Declaration_class:
			case Declaration_interface:
			case Declaration_object:
			case Declaration_fun:
				break;
			default:
				break;
			}
			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(270);
				match(SEMI);
				}
				}
				setState(275);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ToplevelObjectContext extends ParserRuleContext {
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public ObjectDeclarationContext objectDeclaration() {
			return getRuleContext(ObjectDeclarationContext.class,0);
		}
		public ToplevelObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_toplevelObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterToplevelObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitToplevelObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitToplevelObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ToplevelObjectContext toplevelObject() throws RecognitionException {
		ToplevelObjectContext _localctx = new ToplevelObjectContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_toplevelObject);
		try {
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(276);
				propertyDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(277);
				functionDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(278);
				classDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(279);
				objectDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclarationContext extends ParserRuleContext {
		public CompanionObjectContext companionObject() {
			return getRuleContext(CompanionObjectContext.class,0);
		}
		public ObjectDeclarationContext objectDeclaration() {
			return getRuleContext(ObjectDeclarationContext.class,0);
		}
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public AnonymousInitializerContext anonymousInitializer() {
			return getRuleContext(AnonymousInitializerContext.class,0);
		}
		public SecondaryConstructorContext secondaryConstructor() {
			return getRuleContext(SecondaryConstructorContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitMemberDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_memberDeclaration);
		try {
			setState(289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				companionObject();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(283);
				objectDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(284);
				functionDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(285);
				propertyDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(286);
				classDeclaration();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(287);
				anonymousInitializer();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(288);
				secondaryConstructor();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionalProjectionContext extends ParserRuleContext {
		public VarianceAnnotationContext varianceAnnotation() {
			return getRuleContext(VarianceAnnotationContext.class,0);
		}
		public OptionalProjectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionalProjection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterOptionalProjection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitOptionalProjection(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitOptionalProjection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionalProjectionContext optionalProjection() throws RecognitionException {
		OptionalProjectionContext _localctx = new OptionalProjectionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_optionalProjection);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_IN || _la==VarianceAnnotation_out) {
				{
				setState(291);
				varianceAnnotation();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParametersContext extends ParserRuleContext {
		public List<TypeParameterContext> typeParameter() {
			return getRuleContexts(TypeParameterContext.class);
		}
		public TypeParameterContext typeParameter(int i) {
			return getRuleContext(TypeParameterContext.class,i);
		}
		public TypeParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParametersContext typeParameters() throws RecognitionException {
		TypeParametersContext _localctx = new TypeParametersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_typeParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			match(LT);
			setState(295);
			typeParameter();
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(296);
				match(COMMA);
				setState(297);
				typeParameter();
				}
				}
				setState(302);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(303);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParameterContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_typeParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			modifiers();
			setState(306);
			match(SimpleName);
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(307);
				match(COLON);
				setState(308);
				userType();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeArgumentsContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TypeArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeArgumentsContext typeArguments() throws RecognitionException {
		TypeArgumentsContext _localctx = new TypeArgumentsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_typeArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			match(LT);
			{
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PACKAGE || _la==BRACE_OPEN || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (KEYWORD_dynamic - 64)) | (1L << (DOG - 64)) | (1L << (SimpleName - 64)))) != 0)) {
				{
				setState(312);
				type();
				}
			}

			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(315);
				match(COMMA);
				setState(316);
				type();
				}
				}
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(322);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeConstraintsContext extends ParserRuleContext {
		public List<TypeConstraintContext> typeConstraint() {
			return getRuleContexts(TypeConstraintContext.class);
		}
		public TypeConstraintContext typeConstraint(int i) {
			return getRuleContext(TypeConstraintContext.class,i);
		}
		public TypeConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeConstraints; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeConstraints(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeConstraints(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeConstraints(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeConstraintsContext typeConstraints() throws RecognitionException {
		TypeConstraintsContext _localctx = new TypeConstraintsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_typeConstraints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==KEYWORD_where) {
				{
				setState(324);
				match(KEYWORD_where);
				setState(325);
				typeConstraint();
				setState(330);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(326);
					match(COMMA);
					setState(327);
					typeConstraint();
					}
					}
					setState(332);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeConstraintContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TypeConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeConstraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeConstraint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeConstraint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeConstraintContext typeConstraint() throws RecognitionException {
		TypeConstraintContext _localctx = new TypeConstraintContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_typeConstraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOG) {
				{
				setState(335);
				annotations();
				}
			}

			setState(338);
			match(SimpleName);
			setState(339);
			match(COLON);
			setState(340);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeDescriptorContext typeDescriptor() {
			return getRuleContext(TypeDescriptorContext.class,0);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOG) {
				{
				setState(342);
				annotations();
				}
			}

			setState(345);
			typeDescriptor();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeDescriptorContext extends ParserRuleContext {
		public TypeDescriptorContext typeDescriptor() {
			return getRuleContext(TypeDescriptorContext.class,0);
		}
		public ExtensionFunctionTypeNoReceiverContext extensionFunctionTypeNoReceiver() {
			return getRuleContext(ExtensionFunctionTypeNoReceiverContext.class,0);
		}
		public FunctionTypeNoReceiverContext functionTypeNoReceiver() {
			return getRuleContext(FunctionTypeNoReceiverContext.class,0);
		}
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public TypeDescriptorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDescriptor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeDescriptor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeDescriptor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeDescriptor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeDescriptorContext typeDescriptor() throws RecognitionException {
		TypeDescriptorContext _localctx = new TypeDescriptorContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_typeDescriptor);
		try {
			setState(374);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(347);
				match(BRACE_OPEN);
				setState(348);
				typeDescriptor();
				setState(349);
				match(BRACE_CLOSE);
				setState(351);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(350);
					match(Q);
					}
					break;
				}
				setState(354);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(353);
					extensionFunctionTypeNoReceiver();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(356);
				functionTypeNoReceiver();
				setState(358);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(357);
					extensionFunctionTypeNoReceiver();
					}
					break;
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(360);
				userType();
				setState(362);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					{
					setState(361);
					match(Q);
					}
					break;
				}
				setState(365);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(364);
					extensionFunctionTypeNoReceiver();
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(367);
				match(KEYWORD_dynamic);
				setState(369);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
				case 1:
					{
					setState(368);
					match(Q);
					}
					break;
				}
				setState(372);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
				case 1:
					{
					setState(371);
					extensionFunctionTypeNoReceiver();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UserTypeContext extends ParserRuleContext {
		public List<SimpleUserTypeContext> simpleUserType() {
			return getRuleContexts(SimpleUserTypeContext.class);
		}
		public SimpleUserTypeContext simpleUserType(int i) {
			return getRuleContext(SimpleUserTypeContext.class,i);
		}
		public UserTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_userType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterUserType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitUserType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitUserType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UserTypeContext userType() throws RecognitionException {
		UserTypeContext _localctx = new UserTypeContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_userType);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PACKAGE) {
				{
				setState(376);
				match(PACKAGE);
				setState(377);
				match(DOT);
				}
			}

			setState(380);
			simpleUserType();
			setState(385);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(381);
					match(DOT);
					setState(382);
					simpleUserType();
					}
					} 
				}
				setState(387);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleUserType_typeParamContext extends ParserRuleContext {
		public OptionalProjectionContext optionalProjection() {
			return getRuleContext(OptionalProjectionContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public SimpleUserType_typeParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleUserType_typeParam; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterSimpleUserType_typeParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitSimpleUserType_typeParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitSimpleUserType_typeParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleUserType_typeParamContext simpleUserType_typeParam() throws RecognitionException {
		SimpleUserType_typeParamContext _localctx = new SimpleUserType_typeParamContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_simpleUserType_typeParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STAR:
				{
				setState(388);
				match(STAR);
				}
				break;
			case PACKAGE:
			case BRACE_OPEN:
			case OP_IN:
			case KEYWORD_dynamic:
			case VarianceAnnotation_out:
			case DOG:
			case SimpleName:
				{
				{
				setState(389);
				optionalProjection();
				setState(390);
				type();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleUserTypeContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public List<SimpleUserType_typeParamContext> simpleUserType_typeParam() {
			return getRuleContexts(SimpleUserType_typeParamContext.class);
		}
		public SimpleUserType_typeParamContext simpleUserType_typeParam(int i) {
			return getRuleContext(SimpleUserType_typeParamContext.class,i);
		}
		public SimpleUserTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleUserType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterSimpleUserType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitSimpleUserType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitSimpleUserType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleUserTypeContext simpleUserType() throws RecognitionException {
		SimpleUserTypeContext _localctx = new SimpleUserTypeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_simpleUserType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			match(SimpleName);
			setState(406);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(395);
				match(LT);
				setState(396);
				simpleUserType_typeParam();
				setState(401);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(397);
					match(COMMA);
					setState(398);
					simpleUserType_typeParam();
					}
					}
					setState(403);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(404);
				match(GT);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationEntryContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VariableDeclarationEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarationEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterVariableDeclarationEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitVariableDeclarationEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitVariableDeclarationEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationEntryContext variableDeclarationEntry() throws RecognitionException {
		VariableDeclarationEntryContext _localctx = new VariableDeclarationEntryContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_variableDeclarationEntry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(408);
			match(SimpleName);
			setState(411);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(409);
				match(COLON);
				setState(410);
				type();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionType_paramOrTypeContext extends ParserRuleContext {
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionType_paramOrTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionType_paramOrType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFunctionType_paramOrType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFunctionType_paramOrType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFunctionType_paramOrType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionType_paramOrTypeContext functionType_paramOrType() throws RecognitionException {
		FunctionType_paramOrTypeContext _localctx = new FunctionType_paramOrTypeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_functionType_paramOrType);
		try {
			setState(417);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(413);
				parameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(414);
				modifiers();
				setState(415);
				type();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExtensionFunctionTypeNoReceiverContext extends ParserRuleContext {
		public FunctionTypeNoReceiverContext functionTypeNoReceiver() {
			return getRuleContext(FunctionTypeNoReceiverContext.class,0);
		}
		public ExtensionFunctionTypeNoReceiverContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extensionFunctionTypeNoReceiver; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterExtensionFunctionTypeNoReceiver(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitExtensionFunctionTypeNoReceiver(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitExtensionFunctionTypeNoReceiver(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExtensionFunctionTypeNoReceiverContext extensionFunctionTypeNoReceiver() throws RecognitionException {
		ExtensionFunctionTypeNoReceiverContext _localctx = new ExtensionFunctionTypeNoReceiverContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_extensionFunctionTypeNoReceiver);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			match(DOT);
			setState(420);
			functionTypeNoReceiver();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTypeNoReceiverContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<FunctionType_paramOrTypeContext> functionType_paramOrType() {
			return getRuleContexts(FunctionType_paramOrTypeContext.class);
		}
		public FunctionType_paramOrTypeContext functionType_paramOrType(int i) {
			return getRuleContext(FunctionType_paramOrTypeContext.class,i);
		}
		public FunctionTypeNoReceiverContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionTypeNoReceiver; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFunctionTypeNoReceiver(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFunctionTypeNoReceiver(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFunctionTypeNoReceiver(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionTypeNoReceiverContext functionTypeNoReceiver() throws RecognitionException {
		FunctionTypeNoReceiverContext _localctx = new FunctionTypeNoReceiverContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_functionTypeNoReceiver);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			match(BRACE_OPEN);
			setState(431);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PACKAGE) | (1L << BRACE_OPEN) | (1L << OP_IN))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (KEYWORD_dynamic - 64)) | (1L << (HierarchyModifier_abstract - 64)) | (1L << (HierarchyModifier_open - 64)) | (1L << (HierarchyModifier_final - 64)) | (1L << (HierarchyModifier_override - 64)) | (1L << (ClassModifier_enum - 64)) | (1L << (ClassModifier_annotation - 64)) | (1L << (ClassModifier_data - 64)) | (1L << (AccessModifier_private - 64)) | (1L << (AccessModifier_protected - 64)) | (1L << (AccessModifier_public - 64)) | (1L << (AccessModifier_internal - 64)) | (1L << (VarianceAnnotation_out - 64)) | (1L << (DOG - 64)) | (1L << (SimpleName - 64)))) != 0)) {
				{
				setState(423);
				functionType_paramOrType();
				setState(428);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(424);
					match(COMMA);
					setState(425);
					functionType_paramOrType();
					}
					}
					setState(430);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(433);
			match(BRACE_CLOSE);
			setState(434);
			match(OP_ASTERISK);
			setState(435);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultipleVariableDeclarationsContext extends ParserRuleContext {
		public List<VariableDeclarationEntryContext> variableDeclarationEntry() {
			return getRuleContexts(VariableDeclarationEntryContext.class);
		}
		public VariableDeclarationEntryContext variableDeclarationEntry(int i) {
			return getRuleContext(VariableDeclarationEntryContext.class,i);
		}
		public MultipleVariableDeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multipleVariableDeclarations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterMultipleVariableDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitMultipleVariableDeclarations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitMultipleVariableDeclarations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultipleVariableDeclarationsContext multipleVariableDeclarations() throws RecognitionException {
		MultipleVariableDeclarationsContext _localctx = new MultipleVariableDeclarationsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_multipleVariableDeclarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(437);
			match(BRACE_OPEN);
			setState(438);
			variableDeclarationEntry();
			setState(443);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(439);
				match(COMMA);
				setState(440);
				variableDeclarationEntry();
				}
				}
				setState(445);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(446);
			match(BRACE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TypeConstraintsContext typeConstraints() {
			return getRuleContext(TypeConstraintsContext.class,0);
		}
		public MultipleVariableDeclarationsContext multipleVariableDeclarations() {
			return getRuleContext(MultipleVariableDeclarationsContext.class,0);
		}
		public VariableDeclarationEntryContext variableDeclarationEntry() {
			return getRuleContext(VariableDeclarationEntryContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public GetterContext getter() {
			return getRuleContext(GetterContext.class,0);
		}
		public SetterContext setter() {
			return getRuleContext(SetterContext.class,0);
		}
		public PropertyDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPropertyDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPropertyDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPropertyDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyDeclarationContext propertyDeclaration() throws RecognitionException {
		PropertyDeclarationContext _localctx = new PropertyDeclarationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_propertyDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(448);
			modifiers();
			setState(449);
			_la = _input.LA(1);
			if ( !(_la==KEYWORD_val || _la==KEYWORD_var) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(451);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(450);
				typeParameters();
				}
			}

			setState(456);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(453);
				type();
				setState(454);
				match(DOT);
				}
				break;
			}
			setState(460);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BRACE_OPEN:
				{
				setState(458);
				multipleVariableDeclarations();
				}
				break;
			case SimpleName:
				{
				setState(459);
				variableDeclarationEntry();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(462);
			typeConstraints();
			setState(471);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQ || _la==KEYWORD_by) {
				{
				setState(463);
				_la = _input.LA(1);
				if ( !(_la==EQ || _la==KEYWORD_by) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(464);
				expression();
				setState(468);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(465);
						match(SEMI);
						}
						} 
					}
					setState(470);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
				}
				}
			}

			setState(485);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				{
				{
				setState(474);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
				case 1:
					{
					setState(473);
					getter();
					}
					break;
				}
				setState(477);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
				case 1:
					{
					setState(476);
					setter();
					}
					break;
				}
				}
				}
				break;
			case 2:
				{
				{
				setState(480);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,44,_ctx) ) {
				case 1:
					{
					setState(479);
					setter();
					}
					break;
				}
				setState(483);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
				case 1:
					{
					setState(482);
					getter();
					}
					break;
				}
				}
				}
				break;
			}
			setState(490);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(487);
					match(SEMI);
					}
					} 
				}
				setState(492);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GetterContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public GetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_getter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterGetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitGetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitGetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GetterContext getter() throws RecognitionException {
		GetterContext _localctx = new GetterContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_getter);
		int _la;
		try {
			setState(506);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(493);
				modifiers();
				setState(494);
				match(GET);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(496);
				modifiers();
				setState(497);
				match(GET);
				setState(498);
				match(BRACE_OPEN);
				setState(499);
				match(BRACE_CLOSE);
				setState(502);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLON) {
					{
					setState(500);
					match(COLON);
					setState(501);
					type();
					}
				}

				setState(504);
				functionBody();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetterContext extends ParserRuleContext {
		public List<ModifiersContext> modifiers() {
			return getRuleContexts(ModifiersContext.class);
		}
		public ModifiersContext modifiers(int i) {
			return getRuleContext(ModifiersContext.class,i);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public SetterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterSetter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitSetter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitSetter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetterContext setter() throws RecognitionException {
		SetterContext _localctx = new SetterContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_setter);
		try {
			setState(522);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(508);
				modifiers();
				setState(509);
				match(SET);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(511);
				modifiers();
				setState(512);
				match(SET);
				setState(513);
				match(BRACE_OPEN);
				setState(514);
				modifiers();
				setState(517);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
				case 1:
					{
					setState(515);
					match(SimpleName);
					}
					break;
				case 2:
					{
					setState(516);
					parameter();
					}
					break;
				}
				setState(519);
				match(BRACE_CLOSE);
				setState(520);
				functionBody();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifiersContext extends ParserRuleContext {
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ModifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterModifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitModifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitModifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifiersContext modifiers() throws RecognitionException {
		ModifiersContext _localctx = new ModifiersContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_modifiers);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(527);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(524);
					modifier();
					}
					} 
				}
				setState(529);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierContext extends ParserRuleContext {
		public ModifierKeywordContext modifierKeyword() {
			return getRuleContext(ModifierKeywordContext.class,0);
		}
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_modifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(530);
			modifierKeyword();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierKeywordContext extends ParserRuleContext {
		public HierarchyModifierContext hierarchyModifier() {
			return getRuleContext(HierarchyModifierContext.class,0);
		}
		public AccessModifierContext accessModifier() {
			return getRuleContext(AccessModifierContext.class,0);
		}
		public VarianceAnnotationContext varianceAnnotation() {
			return getRuleContext(VarianceAnnotationContext.class,0);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public ClassModifierContext classModifier() {
			return getRuleContext(ClassModifierContext.class,0);
		}
		public ModifierKeywordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifierKeyword; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterModifierKeyword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitModifierKeyword(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitModifierKeyword(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierKeywordContext modifierKeyword() throws RecognitionException {
		ModifierKeywordContext _localctx = new ModifierKeywordContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_modifierKeyword);
		try {
			setState(537);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HierarchyModifier_abstract:
			case HierarchyModifier_open:
			case HierarchyModifier_final:
			case HierarchyModifier_override:
				enterOuterAlt(_localctx, 1);
				{
				setState(532);
				hierarchyModifier();
				}
				break;
			case AccessModifier_private:
			case AccessModifier_protected:
			case AccessModifier_public:
			case AccessModifier_internal:
				enterOuterAlt(_localctx, 2);
				{
				setState(533);
				accessModifier();
				}
				break;
			case OP_IN:
			case VarianceAnnotation_out:
				enterOuterAlt(_localctx, 3);
				{
				setState(534);
				varianceAnnotation();
				}
				break;
			case DOG:
				enterOuterAlt(_localctx, 4);
				{
				setState(535);
				annotations();
				}
				break;
			case ClassModifier_enum:
			case ClassModifier_annotation:
			case ClassModifier_data:
				enterOuterAlt(_localctx, 5);
				{
				setState(536);
				classModifier();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HierarchyModifierContext extends ParserRuleContext {
		public HierarchyModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hierarchyModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterHierarchyModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitHierarchyModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitHierarchyModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HierarchyModifierContext hierarchyModifier() throws RecognitionException {
		HierarchyModifierContext _localctx = new HierarchyModifierContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_hierarchyModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(539);
			_la = _input.LA(1);
			if ( !(((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (HierarchyModifier_abstract - 68)) | (1L << (HierarchyModifier_open - 68)) | (1L << (HierarchyModifier_final - 68)) | (1L << (HierarchyModifier_override - 68)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassModifierContext extends ParserRuleContext {
		public ClassModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterClassModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitClassModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitClassModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassModifierContext classModifier() throws RecognitionException {
		ClassModifierContext _localctx = new ClassModifierContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_classModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(541);
			_la = _input.LA(1);
			if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ClassModifier_enum - 72)) | (1L << (ClassModifier_annotation - 72)) | (1L << (ClassModifier_data - 72)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AccessModifierContext extends ParserRuleContext {
		public AccessModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accessModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAccessModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAccessModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAccessModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AccessModifierContext accessModifier() throws RecognitionException {
		AccessModifierContext _localctx = new AccessModifierContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_accessModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(543);
			_la = _input.LA(1);
			if ( !(((((_la - 75)) & ~0x3f) == 0 && ((1L << (_la - 75)) & ((1L << (AccessModifier_private - 75)) | (1L << (AccessModifier_protected - 75)) | (1L << (AccessModifier_public - 75)) | (1L << (AccessModifier_internal - 75)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarianceAnnotationContext extends ParserRuleContext {
		public VarianceAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varianceAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterVarianceAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitVarianceAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitVarianceAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarianceAnnotationContext varianceAnnotation() throws RecognitionException {
		VarianceAnnotationContext _localctx = new VarianceAnnotationContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_varianceAnnotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(545);
			_la = _input.LA(1);
			if ( !(_la==OP_IN || _la==VarianceAnnotation_out) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationsContext extends ParserRuleContext {
		public List<AnnotationContext> annotation() {
			return getRuleContexts(AnnotationContext.class);
		}
		public AnnotationContext annotation(int i) {
			return getRuleContext(AnnotationContext.class,i);
		}
		public AnnotationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAnnotations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAnnotations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAnnotations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationsContext annotations() throws RecognitionException {
		AnnotationsContext _localctx = new AnnotationsContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_annotations);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(548); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(547);
					annotation();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(550); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationContext extends ParserRuleContext {
		public UnescapedAnnotationContext unescapedAnnotation() {
			return getRuleContext(UnescapedAnnotationContext.class,0);
		}
		public AnnotationUseSiteTargetContext annotationUseSiteTarget() {
			return getRuleContext(AnnotationUseSiteTargetContext.class,0);
		}
		public AnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationContext annotation() throws RecognitionException {
		AnnotationContext _localctx = new AnnotationContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_annotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(552);
			match(DOG);
			setState(556);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (GET - 66)) | (1L << (SET - 66)) | (1L << (AnnotationUseSiteTarget_file - 66)) | (1L << (AnnotationUseSiteTarget_field - 66)) | (1L << (AnnotationUseSiteTarget_property - 66)) | (1L << (AnnotationUseSiteTarget_param - 66)) | (1L << (AnnotationUseSiteTarget_sparam - 66)))) != 0)) {
				{
				setState(553);
				annotationUseSiteTarget();
				setState(554);
				match(COLON);
				}
			}

			setState(558);
			unescapedAnnotation();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotationUseSiteTargetContext extends ParserRuleContext {
		public AnnotationUseSiteTargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotationUseSiteTarget; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAnnotationUseSiteTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAnnotationUseSiteTarget(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAnnotationUseSiteTarget(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotationUseSiteTargetContext annotationUseSiteTarget() throws RecognitionException {
		AnnotationUseSiteTargetContext _localctx = new AnnotationUseSiteTargetContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_annotationUseSiteTarget);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(560);
			_la = _input.LA(1);
			if ( !(((((_la - 66)) & ~0x3f) == 0 && ((1L << (_la - 66)) & ((1L << (GET - 66)) | (1L << (SET - 66)) | (1L << (AnnotationUseSiteTarget_file - 66)) | (1L << (AnnotationUseSiteTarget_field - 66)) | (1L << (AnnotationUseSiteTarget_property - 66)) | (1L << (AnnotationUseSiteTarget_param - 66)) | (1L << (AnnotationUseSiteTarget_sparam - 66)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueArgumentContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public ValueArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterValueArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitValueArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitValueArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueArgumentContext valueArgument() throws RecognitionException {
		ValueArgumentContext _localctx = new ValueArgumentContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_valueArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(565);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
			case 1:
				{
				setState(562);
				identifier();
				setState(563);
				match(EQ);
				}
				break;
			}
			setState(568);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STAR) {
				{
				setState(567);
				match(STAR);
				}
			}

			setState(570);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueArgumentsContext extends ParserRuleContext {
		public List<ValueArgumentContext> valueArgument() {
			return getRuleContexts(ValueArgumentContext.class);
		}
		public ValueArgumentContext valueArgument(int i) {
			return getRuleContext(ValueArgumentContext.class,i);
		}
		public ValueArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueArguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterValueArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitValueArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitValueArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueArgumentsContext valueArguments() throws RecognitionException {
		ValueArgumentsContext _localctx = new ValueArgumentsContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_valueArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(572);
			match(BRACE_OPEN);
			setState(581);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntegerLiteral) | (1L << OPEN_BLOCK) | (1L << HexadecimalLiteral) | (1L << CharacterLiteral) | (1L << TRIPLE_QUOTE) | (1L << SINGLE_QUOTE) | (1L << PACKAGE) | (1L << IMPORT) | (1L << STAR) | (1L << BRACE_OPEN) | (1L << REFERENCE) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << OP_PLUS) | (1L << OP_MUNUS) | (1L << OP_DECREMENT) | (1L << OP_INCREMENT) | (1L << OP_NOT))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ClassModifier_enum - 72)) | (1L << (DOG - 72)) | (1L << (Jump_throw - 72)) | (1L << (Jump_continue - 72)) | (1L << (Jump_return - 72)) | (1L << (Jump_break - 72)) | (1L << (ConstructorDelegationCall_this - 72)) | (1L << (ConstructorDelegationCall_super - 72)) | (1L << (Declaration_object - 72)) | (1L << (Declaration_companion - 72)) | (1L << (Declaration_init - 72)) | (1L << (CF_if - 72)) | (1L << (CF_when - 72)) | (1L << (CF_while - 72)) | (1L << (CF_for - 72)) | (1L << (CF_do - 72)) | (1L << (CF_try - 72)) | (1L << (BAX - 72)) | (1L << (SimpleName - 72)))) != 0)) {
				{
				setState(573);
				valueArgument();
				setState(578);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(574);
					match(COMMA);
					setState(575);
					valueArgument();
					}
					}
					setState(580);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(583);
			match(BRACE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnescapedAnnotationContext extends ParserRuleContext {
		public List<IdentifierContext> identifier() {
			return getRuleContexts(IdentifierContext.class);
		}
		public IdentifierContext identifier(int i) {
			return getRuleContext(IdentifierContext.class,i);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ValueArgumentsContext valueArguments() {
			return getRuleContext(ValueArgumentsContext.class,0);
		}
		public UnescapedAnnotationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unescapedAnnotation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterUnescapedAnnotation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitUnescapedAnnotation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitUnescapedAnnotation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnescapedAnnotationContext unescapedAnnotation() throws RecognitionException {
		UnescapedAnnotationContext _localctx = new UnescapedAnnotationContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_unescapedAnnotation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(585);
			identifier();
			setState(590);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(586);
				match(DOT);
				setState(587);
				identifier();
				}
				}
				setState(592);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(594);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(593);
				typeArguments();
				}
			}

			setState(597);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				{
				setState(596);
				valueArguments();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LabelReferenceContext labelReference() {
			return getRuleContext(LabelReferenceContext.class,0);
		}
		public JumpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jump; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterJump(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitJump(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitJump(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JumpContext jump() throws RecognitionException {
		JumpContext _localctx = new JumpContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_jump);
		try {
			setState(616);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Jump_throw:
				enterOuterAlt(_localctx, 1);
				{
				setState(599);
				match(Jump_throw);
				setState(600);
				expression();
				}
				break;
			case Jump_return:
				enterOuterAlt(_localctx, 2);
				{
				setState(601);
				match(Jump_return);
				setState(603);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
				case 1:
					{
					setState(602);
					labelReference();
					}
					break;
				}
				setState(606);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
				case 1:
					{
					setState(605);
					expression();
					}
					break;
				}
				}
				break;
			case Jump_continue:
				enterOuterAlt(_localctx, 3);
				{
				setState(608);
				match(Jump_continue);
				setState(610);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,65,_ctx) ) {
				case 1:
					{
					setState(609);
					labelReference();
					}
					break;
				}
				}
				break;
			case Jump_break:
				enterOuterAlt(_localctx, 4);
				{
				setState(612);
				match(Jump_break);
				setState(614);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
				case 1:
					{
					setState(613);
					labelReference();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelReferenceContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LabelReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterLabelReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitLabelReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitLabelReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelReferenceContext labelReference() throws RecognitionException {
		LabelReferenceContext _localctx = new LabelReferenceContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_labelReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(618);
			match(DOG);
			setState(623);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IMPORT:
			case ClassModifier_enum:
			case Declaration_companion:
			case Declaration_init:
			case SimpleName:
				{
				setState(619);
				identifier();
				}
				break;
			case CF_for:
				{
				setState(620);
				match(CF_for);
				}
				break;
			case CF_while:
				{
				setState(621);
				match(CF_while);
				}
				break;
			case CF_when:
				{
				setState(622);
				match(CF_when);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelDefinitionContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public LabelDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterLabelDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitLabelDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitLabelDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelDefinitionContext labelDefinition() throws RecognitionException {
		LabelDefinitionContext _localctx = new LabelDefinitionContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_labelDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(625);
			identifier();
			setState(626);
			match(DOG);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(628);
			match(SimpleName);
			setState(629);
			match(COLON);
			setState(630);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParameterContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public ParameterContext parameter() {
			return getRuleContext(ParameterContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFunctionParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFunctionParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFunctionParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterContext functionParameter() throws RecognitionException {
		FunctionParameterContext _localctx = new FunctionParameterContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_functionParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(632);
			modifiers();
			setState(634);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==KEYWORD_val || _la==KEYWORD_var) {
				{
				setState(633);
				_la = _input.LA(1);
				if ( !(_la==KEYWORD_val || _la==KEYWORD_var) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(636);
			parameter();
			setState(639);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EQ) {
				{
				setState(637);
				match(EQ);
				setState(638);
				expression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryConstructorContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
		}
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
		}
		public PrimaryConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryConstructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPrimaryConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPrimaryConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPrimaryConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryConstructorContext primaryConstructor() throws RecognitionException {
		PrimaryConstructorContext _localctx = new PrimaryConstructorContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_primaryConstructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(644);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 44)) & ~0x3f) == 0 && ((1L << (_la - 44)) & ((1L << (OP_IN - 44)) | (1L << (HierarchyModifier_abstract - 44)) | (1L << (HierarchyModifier_open - 44)) | (1L << (HierarchyModifier_final - 44)) | (1L << (HierarchyModifier_override - 44)) | (1L << (ClassModifier_enum - 44)) | (1L << (ClassModifier_annotation - 44)) | (1L << (ClassModifier_data - 44)) | (1L << (AccessModifier_private - 44)) | (1L << (AccessModifier_protected - 44)) | (1L << (AccessModifier_public - 44)) | (1L << (AccessModifier_internal - 44)) | (1L << (VarianceAnnotation_out - 44)) | (1L << (DOG - 44)) | (1L << (KEYWORD_constructor - 44)))) != 0)) {
				{
				setState(641);
				modifiers();
				setState(642);
				match(KEYWORD_constructor);
				}
			}

			{
			setState(646);
			match(BRACE_OPEN);
			setState(655);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OP_IN) | (1L << KEYWORD_val) | (1L << KEYWORD_var))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (HierarchyModifier_abstract - 68)) | (1L << (HierarchyModifier_open - 68)) | (1L << (HierarchyModifier_final - 68)) | (1L << (HierarchyModifier_override - 68)) | (1L << (ClassModifier_enum - 68)) | (1L << (ClassModifier_annotation - 68)) | (1L << (ClassModifier_data - 68)) | (1L << (AccessModifier_private - 68)) | (1L << (AccessModifier_protected - 68)) | (1L << (AccessModifier_public - 68)) | (1L << (AccessModifier_internal - 68)) | (1L << (VarianceAnnotation_out - 68)) | (1L << (DOG - 68)) | (1L << (SimpleName - 68)))) != 0)) {
				{
				setState(647);
				functionParameter();
				setState(652);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(648);
					match(COMMA);
					setState(649);
					functionParameter();
					}
					}
					setState(654);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(657);
			match(BRACE_CLOSE);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SecondaryConstructorContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public ValueParametersContext valueParameters() {
			return getRuleContext(ValueParametersContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstructorDelegationCallContext constructorDelegationCall() {
			return getRuleContext(ConstructorDelegationCallContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public SecondaryConstructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_secondaryConstructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterSecondaryConstructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitSecondaryConstructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitSecondaryConstructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SecondaryConstructorContext secondaryConstructor() throws RecognitionException {
		SecondaryConstructorContext _localctx = new SecondaryConstructorContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_secondaryConstructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(659);
			modifiers();
			setState(660);
			match(KEYWORD_constructor);
			setState(661);
			valueParameters();
			setState(664);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(662);
				match(COLON);
				setState(663);
				constructorDelegationCall();
				}
			}

			setState(666);
			block();
			setState(670);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(667);
				match(SEMI);
				}
				}
				setState(672);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDelegationCallContext extends ParserRuleContext {
		public ValueArgumentsContext valueArguments() {
			return getRuleContext(ValueArgumentsContext.class,0);
		}
		public ConstructorDelegationCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDelegationCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterConstructorDelegationCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitConstructorDelegationCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitConstructorDelegationCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorDelegationCallContext constructorDelegationCall() throws RecognitionException {
		ConstructorDelegationCallContext _localctx = new ConstructorDelegationCallContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_constructorDelegationCall);
		try {
			setState(677);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ConstructorDelegationCall_this:
				enterOuterAlt(_localctx, 1);
				{
				setState(673);
				match(ConstructorDelegationCall_this);
				setState(674);
				valueArguments();
				}
				break;
			case ConstructorDelegationCall_super:
				enterOuterAlt(_localctx, 2);
				{
				setState(675);
				match(ConstructorDelegationCall_super);
				setState(676);
				valueArguments();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallSuffixContext extends ParserRuleContext {
		public ValueArgumentsContext valueArguments() {
			return getRuleContext(ValueArgumentsContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public AnnotatedLambdaContext annotatedLambda() {
			return getRuleContext(AnnotatedLambdaContext.class,0);
		}
		public CallSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterCallSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitCallSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitCallSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallSuffixContext callSuffix() throws RecognitionException {
		CallSuffixContext _localctx = new CallSuffixContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_callSuffix);
		int _la;
		try {
			setState(690);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,79,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(680);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(679);
					typeArguments();
					}
				}

				setState(682);
				valueArguments();
				setState(684);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
				case 1:
					{
					setState(683);
					annotatedLambda();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(686);
				typeArguments();
				setState(687);
				annotatedLambda();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(689);
				annotatedLambda();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorInvocationContext extends ParserRuleContext {
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public ValueArgumentsContext valueArguments() {
			return getRuleContext(ValueArgumentsContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public ConstructorInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterConstructorInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitConstructorInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitConstructorInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstructorInvocationContext constructorInvocation() throws RecognitionException {
		ConstructorInvocationContext _localctx = new ConstructorInvocationContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_constructorInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(692);
			userType();
			setState(694);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(693);
				typeArguments();
				}
			}

			setState(696);
			valueArguments();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplicitDelegationContext extends ParserRuleContext {
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExplicitDelegationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitDelegation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterExplicitDelegation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitExplicitDelegation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitExplicitDelegation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExplicitDelegationContext explicitDelegation() throws RecognitionException {
		ExplicitDelegationContext _localctx = new ExplicitDelegationContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_explicitDelegation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(698);
			userType();
			setState(699);
			match(KEYWORD_by);
			setState(700);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DelegationSpecifierContext extends ParserRuleContext {
		public ConstructorInvocationContext constructorInvocation() {
			return getRuleContext(ConstructorInvocationContext.class,0);
		}
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public ExplicitDelegationContext explicitDelegation() {
			return getRuleContext(ExplicitDelegationContext.class,0);
		}
		public DelegationSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delegationSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterDelegationSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitDelegationSpecifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitDelegationSpecifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DelegationSpecifierContext delegationSpecifier() throws RecognitionException {
		DelegationSpecifierContext _localctx = new DelegationSpecifierContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_delegationSpecifier);
		try {
			setState(705);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(702);
				constructorInvocation();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(703);
				userType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(704);
				explicitDelegation();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public TypeConstraintsContext typeConstraints() {
			return getRuleContext(TypeConstraintsContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public EnumClassBodyContext enumClassBody() {
			return getRuleContext(EnumClassBodyContext.class,0);
		}
		public TypeParametersContext typeParameters() {
			return getRuleContext(TypeParametersContext.class,0);
		}
		public PrimaryConstructorContext primaryConstructor() {
			return getRuleContext(PrimaryConstructorContext.class,0);
		}
		public List<DelegationSpecifierContext> delegationSpecifier() {
			return getRuleContexts(DelegationSpecifierContext.class);
		}
		public DelegationSpecifierContext delegationSpecifier(int i) {
			return getRuleContext(DelegationSpecifierContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitClassDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_classDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(707);
			modifiers();
			setState(708);
			_la = _input.LA(1);
			if ( !(_la==Declaration_class || _la==Declaration_interface) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(709);
			match(SimpleName);
			setState(711);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(710);
				typeParameters();
				}
			}

			setState(714);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,83,_ctx) ) {
			case 1:
				{
				setState(713);
				primaryConstructor();
				}
				break;
			}
			setState(728);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(716);
				match(COLON);
				setState(718);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DOG) {
					{
					setState(717);
					annotations();
					}
				}

				setState(720);
				delegationSpecifier();
				setState(725);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(721);
					match(COMMA);
					setState(722);
					delegationSpecifier();
					}
					}
					setState(727);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(730);
			typeConstraints();
			setState(733);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,87,_ctx) ) {
			case 1:
				{
				setState(731);
				classBody();
				}
				break;
			case 2:
				{
				setState(732);
				enumClassBody();
				}
				break;
			}
			setState(738);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,88,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(735);
					match(SEMI);
					}
					} 
				}
				setState(740);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,88,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SupertypesSpecifiersContext extends ParserRuleContext {
		public List<DelegationSpecifierContext> delegationSpecifier() {
			return getRuleContexts(DelegationSpecifierContext.class);
		}
		public DelegationSpecifierContext delegationSpecifier(int i) {
			return getRuleContext(DelegationSpecifierContext.class,i);
		}
		public SupertypesSpecifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_supertypesSpecifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterSupertypesSpecifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitSupertypesSpecifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitSupertypesSpecifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SupertypesSpecifiersContext supertypesSpecifiers() throws RecognitionException {
		SupertypesSpecifiersContext _localctx = new SupertypesSpecifiersContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_supertypesSpecifiers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(741);
			match(COLON);
			setState(742);
			delegationSpecifier();
			setState(747);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(743);
				match(COMMA);
				setState(744);
				delegationSpecifier();
				}
				}
				setState(749);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectDeclarationContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public PrimaryConstructorContext primaryConstructor() {
			return getRuleContext(PrimaryConstructorContext.class,0);
		}
		public SupertypesSpecifiersContext supertypesSpecifiers() {
			return getRuleContext(SupertypesSpecifiersContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public ObjectDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterObjectDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitObjectDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitObjectDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectDeclarationContext objectDeclaration() throws RecognitionException {
		ObjectDeclarationContext _localctx = new ObjectDeclarationContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_objectDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(750);
			match(Declaration_object);
			setState(751);
			match(SimpleName);
			setState(753);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,90,_ctx) ) {
			case 1:
				{
				setState(752);
				primaryConstructor();
				}
				break;
			}
			setState(756);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(755);
				supertypesSpecifiers();
				}
			}

			setState(758);
			classBody();
			setState(762);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(759);
					match(SEMI);
					}
					} 
				}
				setState(764);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,92,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompanionObjectContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public SupertypesSpecifiersContext supertypesSpecifiers() {
			return getRuleContext(SupertypesSpecifiersContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public CompanionObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_companionObject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterCompanionObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitCompanionObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitCompanionObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompanionObjectContext companionObject() throws RecognitionException {
		CompanionObjectContext _localctx = new CompanionObjectContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_companionObject);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(765);
			modifiers();
			setState(766);
			match(Declaration_companion);
			setState(767);
			match(Declaration_object);
			setState(769);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(768);
				supertypesSpecifiers();
				}
			}

			setState(771);
			classBody();
			setState(775);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(772);
				match(SEMI);
				}
				}
				setState(777);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public MembersContext members() {
			return getRuleContext(MembersContext.class,0);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_classBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(782);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
			case 1:
				{
				setState(778);
				match(OPEN_BLOCK);
				setState(779);
				members();
				setState(780);
				match(CLOSE_BLOCK);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MembersContext extends ParserRuleContext {
		public List<MemberDeclarationContext> memberDeclaration() {
			return getRuleContexts(MemberDeclarationContext.class);
		}
		public MemberDeclarationContext memberDeclaration(int i) {
			return getRuleContext(MemberDeclarationContext.class,i);
		}
		public MembersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_members; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterMembers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitMembers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitMembers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MembersContext members() throws RecognitionException {
		MembersContext _localctx = new MembersContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_members);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(787);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 44)) & ~0x3f) == 0 && ((1L << (_la - 44)) & ((1L << (OP_IN - 44)) | (1L << (KEYWORD_val - 44)) | (1L << (KEYWORD_var - 44)) | (1L << (HierarchyModifier_abstract - 44)) | (1L << (HierarchyModifier_open - 44)) | (1L << (HierarchyModifier_final - 44)) | (1L << (HierarchyModifier_override - 44)) | (1L << (ClassModifier_enum - 44)) | (1L << (ClassModifier_annotation - 44)) | (1L << (ClassModifier_data - 44)) | (1L << (AccessModifier_private - 44)) | (1L << (AccessModifier_protected - 44)) | (1L << (AccessModifier_public - 44)) | (1L << (AccessModifier_internal - 44)) | (1L << (VarianceAnnotation_out - 44)) | (1L << (DOG - 44)) | (1L << (KEYWORD_constructor - 44)) | (1L << (Declaration_class - 44)) | (1L << (Declaration_interface - 44)) | (1L << (Declaration_object - 44)) | (1L << (Declaration_companion - 44)) | (1L << (Declaration_fun - 44)) | (1L << (Declaration_init - 44)))) != 0)) {
				{
				{
				setState(784);
				memberDeclaration();
				}
				}
				setState(789);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueParametersContext extends ParserRuleContext {
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
		}
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
		}
		public ValueParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterValueParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitValueParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitValueParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueParametersContext valueParameters() throws RecognitionException {
		ValueParametersContext _localctx = new ValueParametersContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_valueParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(790);
			match(BRACE_OPEN);
			setState(799);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OP_IN) | (1L << KEYWORD_val) | (1L << KEYWORD_var))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (HierarchyModifier_abstract - 68)) | (1L << (HierarchyModifier_open - 68)) | (1L << (HierarchyModifier_final - 68)) | (1L << (HierarchyModifier_override - 68)) | (1L << (ClassModifier_enum - 68)) | (1L << (ClassModifier_annotation - 68)) | (1L << (ClassModifier_data - 68)) | (1L << (AccessModifier_private - 68)) | (1L << (AccessModifier_protected - 68)) | (1L << (AccessModifier_public - 68)) | (1L << (AccessModifier_internal - 68)) | (1L << (VarianceAnnotation_out - 68)) | (1L << (DOG - 68)) | (1L << (SimpleName - 68)))) != 0)) {
				{
				setState(791);
				functionParameter();
				setState(796);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(792);
					match(COMMA);
					setState(793);
					functionParameter();
					}
					}
					setState(798);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(801);
			match(BRACE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public ValueParametersContext valueParameters() {
			return getRuleContext(ValueParametersContext.class,0);
		}
		public TypeConstraintsContext typeConstraints() {
			return getRuleContext(TypeConstraintsContext.class,0);
		}
		public List<TypeParametersContext> typeParameters() {
			return getRuleContexts(TypeParametersContext.class);
		}
		public TypeParametersContext typeParameters(int i) {
			return getRuleContext(TypeParametersContext.class,i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFunctionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFunctionDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_functionDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(803);
			modifiers();
			setState(804);
			match(Declaration_fun);
			setState(806);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(805);
				typeParameters();
				}
			}

			setState(812);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,100,_ctx) ) {
			case 1:
				{
				setState(808);
				type();
				setState(809);
				match(DOT);
				}
				break;
			case 2:
				{
				setState(811);
				annotations();
				}
				break;
			}
			setState(814);
			match(SimpleName);
			setState(816);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(815);
				typeParameters();
				}
			}

			setState(818);
			valueParameters();
			setState(821);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(819);
				match(COLON);
				setState(820);
				type();
				}
			}

			setState(823);
			typeConstraints();
			setState(825);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,103,_ctx) ) {
			case 1:
				{
				setState(824);
				functionBody();
				}
				break;
			}
			setState(830);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,104,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(827);
					match(SEMI);
					}
					} 
				}
				setState(832);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,104,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_statements);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(836);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(833);
					match(SEMI);
					}
					} 
				}
				setState(838);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,105,_ctx);
			}
			setState(852);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntegerLiteral) | (1L << OPEN_BLOCK) | (1L << HexadecimalLiteral) | (1L << CharacterLiteral) | (1L << TRIPLE_QUOTE) | (1L << SINGLE_QUOTE) | (1L << PACKAGE) | (1L << IMPORT) | (1L << BRACE_OPEN) | (1L << REFERENCE) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << OP_PLUS) | (1L << OP_MUNUS) | (1L << OP_IN) | (1L << OP_DECREMENT) | (1L << OP_INCREMENT) | (1L << OP_NOT) | (1L << KEYWORD_val) | (1L << KEYWORD_var))) != 0) || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (HierarchyModifier_abstract - 68)) | (1L << (HierarchyModifier_open - 68)) | (1L << (HierarchyModifier_final - 68)) | (1L << (HierarchyModifier_override - 68)) | (1L << (ClassModifier_enum - 68)) | (1L << (ClassModifier_annotation - 68)) | (1L << (ClassModifier_data - 68)) | (1L << (AccessModifier_private - 68)) | (1L << (AccessModifier_protected - 68)) | (1L << (AccessModifier_public - 68)) | (1L << (AccessModifier_internal - 68)) | (1L << (VarianceAnnotation_out - 68)) | (1L << (DOG - 68)) | (1L << (Jump_throw - 68)) | (1L << (Jump_continue - 68)) | (1L << (Jump_return - 68)) | (1L << (Jump_break - 68)) | (1L << (ConstructorDelegationCall_this - 68)) | (1L << (ConstructorDelegationCall_super - 68)) | (1L << (Declaration_class - 68)) | (1L << (Declaration_interface - 68)) | (1L << (Declaration_object - 68)) | (1L << (Declaration_companion - 68)) | (1L << (Declaration_fun - 68)) | (1L << (Declaration_init - 68)) | (1L << (CF_if - 68)) | (1L << (CF_when - 68)) | (1L << (CF_while - 68)) | (1L << (CF_for - 68)) | (1L << (CF_do - 68)) | (1L << (CF_try - 68)) | (1L << (BAX - 68)) | (1L << (SimpleName - 68)))) != 0)) {
				{
				setState(839);
				statement();
				setState(849);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,107,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(843);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==SEMI) {
							{
							{
							setState(840);
							match(SEMI);
							}
							}
							setState(845);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(846);
						statement();
						}
						} 
					}
					setState(851);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,107,_ctx);
				}
				}
			}

			setState(857);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(854);
				match(SEMI);
				}
				}
				setState(859);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionBodyContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFunctionBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFunctionBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFunctionBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionBodyContext functionBody() throws RecognitionException {
		FunctionBodyContext _localctx = new FunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_functionBody);
		try {
			setState(863);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPEN_BLOCK:
				enterOuterAlt(_localctx, 1);
				{
				setState(860);
				block();
				}
				break;
			case EQ:
				enterOuterAlt(_localctx, 2);
				{
				setState(861);
				match(EQ);
				setState(862);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_block);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(865);
			match(OPEN_BLOCK);
			setState(866);
			statements();
			setState(867);
			match(CLOSE_BLOCK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnonymousInitializerContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public AnonymousInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_anonymousInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAnonymousInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAnonymousInitializer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAnonymousInitializer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnonymousInitializerContext anonymousInitializer() throws RecognitionException {
		AnonymousInitializerContext _localctx = new AnonymousInitializerContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_anonymousInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(869);
			match(Declaration_init);
			setState(870);
			block();
			setState(874);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMI) {
				{
				{
				setState(871);
				match(SEMI);
				}
				}
				setState(876);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumClassBodyContext extends ParserRuleContext {
		public EnumEntriesContext enumEntries() {
			return getRuleContext(EnumEntriesContext.class,0);
		}
		public MembersContext members() {
			return getRuleContext(MembersContext.class,0);
		}
		public EnumClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumClassBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterEnumClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitEnumClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitEnumClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumClassBodyContext enumClassBody() throws RecognitionException {
		EnumClassBodyContext _localctx = new EnumClassBodyContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_enumClassBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(877);
			match(OPEN_BLOCK);
			setState(878);
			enumEntries();
			setState(881);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMI) {
				{
				setState(879);
				match(SEMI);
				setState(880);
				members();
				}
			}

			setState(883);
			match(CLOSE_BLOCK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumEntriesContext extends ParserRuleContext {
		public List<EnumEntryContext> enumEntry() {
			return getRuleContexts(EnumEntryContext.class);
		}
		public EnumEntryContext enumEntry(int i) {
			return getRuleContext(EnumEntryContext.class,i);
		}
		public EnumEntriesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumEntries; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterEnumEntries(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitEnumEntries(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitEnumEntries(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumEntriesContext enumEntries() throws RecognitionException {
		EnumEntriesContext _localctx = new EnumEntriesContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_enumEntries);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(891);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OP_IN || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (HierarchyModifier_abstract - 68)) | (1L << (HierarchyModifier_open - 68)) | (1L << (HierarchyModifier_final - 68)) | (1L << (HierarchyModifier_override - 68)) | (1L << (ClassModifier_enum - 68)) | (1L << (ClassModifier_annotation - 68)) | (1L << (ClassModifier_data - 68)) | (1L << (AccessModifier_private - 68)) | (1L << (AccessModifier_protected - 68)) | (1L << (AccessModifier_public - 68)) | (1L << (AccessModifier_internal - 68)) | (1L << (VarianceAnnotation_out - 68)) | (1L << (DOG - 68)) | (1L << (SimpleName - 68)))) != 0)) {
				{
				{
				setState(885);
				enumEntry();
				setState(887);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(886);
					match(COMMA);
					}
				}

				}
				}
				setState(893);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumEntryContext extends ParserRuleContext {
		public ModifiersContext modifiers() {
			return getRuleContext(ModifiersContext.class,0);
		}
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ValueArgumentsContext valueArguments() {
			return getRuleContext(ValueArgumentsContext.class,0);
		}
		public ConstructorInvocationContext constructorInvocation() {
			return getRuleContext(ConstructorInvocationContext.class,0);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public EnumEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterEnumEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitEnumEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitEnumEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumEntryContext enumEntry() throws RecognitionException {
		EnumEntryContext _localctx = new EnumEntryContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_enumEntry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(894);
			modifiers();
			setState(895);
			match(SimpleName);
			setState(902);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case COLON:
				{
				{
				setState(896);
				match(COLON);
				setState(898);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DOG) {
					{
					setState(897);
					annotations();
					}
				}

				setState(900);
				constructorInvocation();
				}
				}
				break;
			case BRACE_OPEN:
				{
				setState(901);
				valueArguments();
				}
				break;
			case SEMI:
			case OPEN_BLOCK:
			case CLOSE_BLOCK:
			case COMMA:
			case OP_IN:
			case HierarchyModifier_abstract:
			case HierarchyModifier_open:
			case HierarchyModifier_final:
			case HierarchyModifier_override:
			case ClassModifier_enum:
			case ClassModifier_annotation:
			case ClassModifier_data:
			case AccessModifier_private:
			case AccessModifier_protected:
			case AccessModifier_public:
			case AccessModifier_internal:
			case VarianceAnnotation_out:
			case DOG:
			case SimpleName:
				break;
			default:
				break;
			}
			setState(904);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfExpressionContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public IfExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterIfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitIfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitIfExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfExpressionContext ifExpression() throws RecognitionException {
		IfExpressionContext _localctx = new IfExpressionContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_ifExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(906);
			match(CF_if);
			setState(907);
			match(BRACE_OPEN);
			setState(908);
			expression();
			setState(909);
			match(BRACE_CLOSE);
			setState(910);
			expression();
			setState(914);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,117,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(911);
					match(SEMI);
					}
					} 
				}
				setState(916);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,117,_ctx);
			}
			setState(919);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,118,_ctx) ) {
			case 1:
				{
				setState(917);
				match(CF_else);
				setState(918);
				expression();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TryExpressionContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<CatchBlockContext> catchBlock() {
			return getRuleContexts(CatchBlockContext.class);
		}
		public CatchBlockContext catchBlock(int i) {
			return getRuleContext(CatchBlockContext.class,i);
		}
		public FinallyBlockContext finallyBlock() {
			return getRuleContext(FinallyBlockContext.class,0);
		}
		public TryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryExpressionContext tryExpression() throws RecognitionException {
		TryExpressionContext _localctx = new TryExpressionContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_tryExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(921);
			match(CF_try);
			setState(922);
			block();
			setState(926);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CF_catch) {
				{
				{
				setState(923);
				catchBlock();
				}
				}
				setState(928);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(930);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CF_FINALLY) {
				{
				setState(929);
				finallyBlock();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CatchBlockContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public CatchBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_catchBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterCatchBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitCatchBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitCatchBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CatchBlockContext catchBlock() throws RecognitionException {
		CatchBlockContext _localctx = new CatchBlockContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_catchBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(932);
			match(CF_catch);
			setState(933);
			match(BRACE_OPEN);
			setState(935);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOG) {
				{
				setState(934);
				annotations();
				}
			}

			setState(937);
			match(SimpleName);
			setState(938);
			match(COLON);
			setState(939);
			userType();
			setState(940);
			match(BRACE_CLOSE);
			setState(941);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FinallyBlockContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FinallyBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finallyBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFinallyBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFinallyBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFinallyBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinallyBlockContext finallyBlock() throws RecognitionException {
		FinallyBlockContext _localctx = new FinallyBlockContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_finallyBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(943);
			match(CF_FINALLY);
			setState(944);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopContext extends ParserRuleContext {
		public ForLoopContext forLoop() {
			return getRuleContext(ForLoopContext.class,0);
		}
		public WhileLoopContext whileLoop() {
			return getRuleContext(WhileLoopContext.class,0);
		}
		public DoWhileLoopContext doWhileLoop() {
			return getRuleContext(DoWhileLoopContext.class,0);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_loop);
		try {
			setState(949);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CF_for:
				enterOuterAlt(_localctx, 1);
				{
				setState(946);
				forLoop();
				}
				break;
			case CF_while:
				enterOuterAlt(_localctx, 2);
				{
				setState(947);
				whileLoop();
				}
				break;
			case CF_do:
				enterOuterAlt(_localctx, 3);
				{
				setState(948);
				doWhileLoop();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForLoopContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public MultipleVariableDeclarationsContext multipleVariableDeclarations() {
			return getRuleContext(MultipleVariableDeclarationsContext.class,0);
		}
		public VariableDeclarationEntryContext variableDeclarationEntry() {
			return getRuleContext(VariableDeclarationEntryContext.class,0);
		}
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public ForLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forLoop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterForLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitForLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitForLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForLoopContext forLoop() throws RecognitionException {
		ForLoopContext _localctx = new ForLoopContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_forLoop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(951);
			match(CF_for);
			setState(952);
			match(BRACE_OPEN);
			setState(954);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DOG) {
				{
				setState(953);
				annotations();
				}
			}

			setState(958);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BRACE_OPEN:
				{
				setState(956);
				multipleVariableDeclarations();
				}
				break;
			case SimpleName:
				{
				setState(957);
				variableDeclarationEntry();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(960);
			match(OP_IN);
			setState(961);
			expression();
			setState(962);
			match(BRACE_CLOSE);
			setState(963);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileLoopContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public WhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileLoop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterWhileLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitWhileLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileLoopContext whileLoop() throws RecognitionException {
		WhileLoopContext _localctx = new WhileLoopContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_whileLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(965);
			match(CF_while);
			setState(966);
			match(BRACE_OPEN);
			setState(967);
			expression();
			setState(968);
			match(BRACE_CLOSE);
			setState(969);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DoWhileLoopContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public DoWhileLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doWhileLoop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterDoWhileLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitDoWhileLoop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitDoWhileLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoWhileLoopContext doWhileLoop() throws RecognitionException {
		DoWhileLoopContext _localctx = new DoWhileLoopContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_doWhileLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(971);
			match(CF_do);
			setState(972);
			expression();
			setState(973);
			match(CF_while);
			setState(974);
			match(BRACE_OPEN);
			setState(975);
			expression();
			setState(976);
			match(BRACE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<DisjunctionContext> disjunction() {
			return getRuleContexts(DisjunctionContext.class);
		}
		public DisjunctionContext disjunction(int i) {
			return getRuleContext(DisjunctionContext.class,i);
		}
		public List<AssignmentOperatorContext> assignmentOperator() {
			return getRuleContexts(AssignmentOperatorContext.class);
		}
		public AssignmentOperatorContext assignmentOperator(int i) {
			return getRuleContext(AssignmentOperatorContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_expression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(978);
			disjunction();
			setState(984);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(979);
					assignmentOperator();
					setState(980);
					disjunction();
					}
					} 
				}
				setState(986);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjunctionContext extends ParserRuleContext {
		public List<ConjunctionContext> conjunction() {
			return getRuleContexts(ConjunctionContext.class);
		}
		public ConjunctionContext conjunction(int i) {
			return getRuleContext(ConjunctionContext.class,i);
		}
		public DisjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterDisjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitDisjunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitDisjunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DisjunctionContext disjunction() throws RecognitionException {
		DisjunctionContext _localctx = new DisjunctionContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_disjunction);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(987);
			conjunction();
			setState(992);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,126,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(988);
					match(DISJ);
					setState(989);
					conjunction();
					}
					} 
				}
				setState(994);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,126,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConjunctionContext extends ParserRuleContext {
		public List<EqualityComparisonContext> equalityComparison() {
			return getRuleContexts(EqualityComparisonContext.class);
		}
		public EqualityComparisonContext equalityComparison(int i) {
			return getRuleContext(EqualityComparisonContext.class,i);
		}
		public ConjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conjunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterConjunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitConjunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitConjunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConjunctionContext conjunction() throws RecognitionException {
		ConjunctionContext _localctx = new ConjunctionContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_conjunction);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(995);
			equalityComparison();
			setState(1000);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,127,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(996);
					match(CONJ);
					setState(997);
					equalityComparison();
					}
					} 
				}
				setState(1002);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,127,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityComparisonContext extends ParserRuleContext {
		public List<ComparisonContext> comparison() {
			return getRuleContexts(ComparisonContext.class);
		}
		public ComparisonContext comparison(int i) {
			return getRuleContext(ComparisonContext.class,i);
		}
		public List<EqualityOperationContext> equalityOperation() {
			return getRuleContexts(EqualityOperationContext.class);
		}
		public EqualityOperationContext equalityOperation(int i) {
			return getRuleContext(EqualityOperationContext.class,i);
		}
		public EqualityComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityComparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterEqualityComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitEqualityComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitEqualityComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityComparisonContext equalityComparison() throws RecognitionException {
		EqualityComparisonContext _localctx = new EqualityComparisonContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_equalityComparison);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1003);
			comparison();
			setState(1009);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,128,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1004);
					equalityOperation();
					setState(1005);
					comparison();
					}
					} 
				}
				setState(1011);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,128,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonContext extends ParserRuleContext {
		public List<NamedInfixContext> namedInfix() {
			return getRuleContexts(NamedInfixContext.class);
		}
		public NamedInfixContext namedInfix(int i) {
			return getRuleContext(NamedInfixContext.class,i);
		}
		public List<ComparisonOperationContext> comparisonOperation() {
			return getRuleContexts(ComparisonOperationContext.class);
		}
		public ComparisonOperationContext comparisonOperation(int i) {
			return getRuleContext(ComparisonOperationContext.class,i);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_comparison);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1012);
			namedInfix();
			setState(1018);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,129,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1013);
					comparisonOperation();
					setState(1014);
					namedInfix();
					}
					} 
				}
				setState(1020);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,129,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedInfixContext extends ParserRuleContext {
		public List<ElvisExpressionContext> elvisExpression() {
			return getRuleContexts(ElvisExpressionContext.class);
		}
		public ElvisExpressionContext elvisExpression(int i) {
			return getRuleContext(ElvisExpressionContext.class,i);
		}
		public List<InOperationContext> inOperation() {
			return getRuleContexts(InOperationContext.class);
		}
		public InOperationContext inOperation(int i) {
			return getRuleContext(InOperationContext.class,i);
		}
		public IsOperationContext isOperation() {
			return getRuleContext(IsOperationContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public NamedInfixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedInfix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterNamedInfix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitNamedInfix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitNamedInfix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamedInfixContext namedInfix() throws RecognitionException {
		NamedInfixContext _localctx = new NamedInfixContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_namedInfix);
		try {
			int _alt;
			setState(1036);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,132,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1021);
				elvisExpression();
				setState(1027);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1022);
						inOperation();
						setState(1023);
						elvisExpression();
						}
						} 
					}
					setState(1029);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1030);
				elvisExpression();
				setState(1034);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,131,_ctx) ) {
				case 1:
					{
					setState(1031);
					isOperation();
					setState(1032);
					type();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElvisExpressionContext extends ParserRuleContext {
		public List<InfixFunctionCallContext> infixFunctionCall() {
			return getRuleContexts(InfixFunctionCallContext.class);
		}
		public InfixFunctionCallContext infixFunctionCall(int i) {
			return getRuleContext(InfixFunctionCallContext.class,i);
		}
		public ElvisExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elvisExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterElvisExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitElvisExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitElvisExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElvisExpressionContext elvisExpression() throws RecognitionException {
		ElvisExpressionContext _localctx = new ElvisExpressionContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_elvisExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1038);
			infixFunctionCall();
			setState(1043);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,133,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1039);
					match(ELVIS);
					setState(1040);
					infixFunctionCall();
					}
					} 
				}
				setState(1045);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,133,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InfixFunctionCallContext extends ParserRuleContext {
		public List<RangeExpressionContext> rangeExpression() {
			return getRuleContexts(RangeExpressionContext.class);
		}
		public RangeExpressionContext rangeExpression(int i) {
			return getRuleContext(RangeExpressionContext.class,i);
		}
		public List<TerminalNode> SimpleName() { return getTokens(KotlinParser.SimpleName); }
		public TerminalNode SimpleName(int i) {
			return getToken(KotlinParser.SimpleName, i);
		}
		public InfixFunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infixFunctionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterInfixFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitInfixFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitInfixFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InfixFunctionCallContext infixFunctionCall() throws RecognitionException {
		InfixFunctionCallContext _localctx = new InfixFunctionCallContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_infixFunctionCall);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1046);
			rangeExpression();
			setState(1051);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,134,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1047);
					match(SimpleName);
					setState(1048);
					rangeExpression();
					}
					} 
				}
				setState(1053);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,134,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RangeExpressionContext extends ParserRuleContext {
		public List<AdditiveExpressionContext> additiveExpression() {
			return getRuleContexts(AdditiveExpressionContext.class);
		}
		public AdditiveExpressionContext additiveExpression(int i) {
			return getRuleContext(AdditiveExpressionContext.class,i);
		}
		public RangeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rangeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterRangeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitRangeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitRangeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RangeExpressionContext rangeExpression() throws RecognitionException {
		RangeExpressionContext _localctx = new RangeExpressionContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_rangeExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1054);
			additiveExpression();
			setState(1059);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,135,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1055);
					match(RANGE);
					setState(1056);
					additiveExpression();
					}
					} 
				}
				setState(1061);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,135,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveExpressionContext extends ParserRuleContext {
		public List<MultiplicativeExpressionContext> multiplicativeExpression() {
			return getRuleContexts(MultiplicativeExpressionContext.class);
		}
		public MultiplicativeExpressionContext multiplicativeExpression(int i) {
			return getRuleContext(MultiplicativeExpressionContext.class,i);
		}
		public List<AdditiveOperationContext> additiveOperation() {
			return getRuleContexts(AdditiveOperationContext.class);
		}
		public AdditiveOperationContext additiveOperation(int i) {
			return getRuleContext(AdditiveOperationContext.class,i);
		}
		public AdditiveExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAdditiveExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAdditiveExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAdditiveExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExpressionContext additiveExpression() throws RecognitionException {
		AdditiveExpressionContext _localctx = new AdditiveExpressionContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_additiveExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1062);
			multiplicativeExpression();
			setState(1068);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,136,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1063);
					additiveOperation();
					setState(1064);
					multiplicativeExpression();
					}
					} 
				}
				setState(1070);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,136,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExpressionContext extends ParserRuleContext {
		public List<TypeRHSContext> typeRHS() {
			return getRuleContexts(TypeRHSContext.class);
		}
		public TypeRHSContext typeRHS(int i) {
			return getRuleContext(TypeRHSContext.class,i);
		}
		public List<MultiplicativeOperationContext> multiplicativeOperation() {
			return getRuleContexts(MultiplicativeOperationContext.class);
		}
		public MultiplicativeOperationContext multiplicativeOperation(int i) {
			return getRuleContext(MultiplicativeOperationContext.class,i);
		}
		public MultiplicativeExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterMultiplicativeExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitMultiplicativeExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitMultiplicativeExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExpressionContext multiplicativeExpression() throws RecognitionException {
		MultiplicativeExpressionContext _localctx = new MultiplicativeExpressionContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_multiplicativeExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1071);
			typeRHS();
			setState(1077);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,137,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1072);
					multiplicativeOperation();
					setState(1073);
					typeRHS();
					}
					} 
				}
				setState(1079);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,137,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeRHSContext extends ParserRuleContext {
		public List<PrefixUnaryExpressionContext> prefixUnaryExpression() {
			return getRuleContexts(PrefixUnaryExpressionContext.class);
		}
		public PrefixUnaryExpressionContext prefixUnaryExpression(int i) {
			return getRuleContext(PrefixUnaryExpressionContext.class,i);
		}
		public List<TypeOperationContext> typeOperation() {
			return getRuleContexts(TypeOperationContext.class);
		}
		public TypeOperationContext typeOperation(int i) {
			return getRuleContext(TypeOperationContext.class,i);
		}
		public TypeRHSContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeRHS; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeRHS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeRHS(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeRHS(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeRHSContext typeRHS() throws RecognitionException {
		TypeRHSContext _localctx = new TypeRHSContext(_ctx, getState());
		enterRule(_localctx, 168, RULE_typeRHS);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1080);
			prefixUnaryExpression();
			setState(1086);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,138,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1081);
					typeOperation();
					setState(1082);
					prefixUnaryExpression();
					}
					} 
				}
				setState(1088);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,138,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixUnaryExpressionContext extends ParserRuleContext {
		public PostfixUnaryExpressionContext postfixUnaryExpression() {
			return getRuleContext(PostfixUnaryExpressionContext.class,0);
		}
		public List<PrefixUnaryOperationContext> prefixUnaryOperation() {
			return getRuleContexts(PrefixUnaryOperationContext.class);
		}
		public PrefixUnaryOperationContext prefixUnaryOperation(int i) {
			return getRuleContext(PrefixUnaryOperationContext.class,i);
		}
		public PrefixUnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixUnaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPrefixUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPrefixUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPrefixUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixUnaryExpressionContext prefixUnaryExpression() throws RecognitionException {
		PrefixUnaryExpressionContext _localctx = new PrefixUnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_prefixUnaryExpression);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1092);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,139,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1089);
					prefixUnaryOperation();
					}
					} 
				}
				setState(1094);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,139,_ctx);
			}
			setState(1095);
			postfixUnaryExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixUnaryExpressionContext extends ParserRuleContext {
		public AtomicExpressionContext atomicExpression() {
			return getRuleContext(AtomicExpressionContext.class,0);
		}
		public CallSuffixContext callSuffix() {
			return getRuleContext(CallSuffixContext.class,0);
		}
		public List<PostfixUnaryOperationContext> postfixUnaryOperation() {
			return getRuleContexts(PostfixUnaryOperationContext.class);
		}
		public PostfixUnaryOperationContext postfixUnaryOperation(int i) {
			return getRuleContext(PostfixUnaryOperationContext.class,i);
		}
		public CallableReferenceContext callableReference() {
			return getRuleContext(CallableReferenceContext.class,0);
		}
		public PostfixUnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixUnaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPostfixUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPostfixUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPostfixUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixUnaryExpressionContext postfixUnaryExpression() throws RecognitionException {
		PostfixUnaryExpressionContext _localctx = new PostfixUnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_postfixUnaryExpression);
		try {
			int _alt;
			setState(1114);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,142,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1097);
				atomicExpression();
				setState(1098);
				callSuffix();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1100);
				atomicExpression();
				setState(1104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,140,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1101);
						postfixUnaryOperation();
						}
						} 
					}
					setState(1106);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,140,_ctx);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1107);
				callableReference();
				setState(1111);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,141,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1108);
						postfixUnaryOperation();
						}
						} 
					}
					setState(1113);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,141,_ctx);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallableReferenceContext extends ParserRuleContext {
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public UserTypeContext userType() {
			return getRuleContext(UserTypeContext.class,0);
		}
		public TypeArgumentsContext typeArguments() {
			return getRuleContext(TypeArgumentsContext.class,0);
		}
		public CallableReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callableReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterCallableReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitCallableReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitCallableReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallableReferenceContext callableReference() throws RecognitionException {
		CallableReferenceContext _localctx = new CallableReferenceContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_callableReference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PACKAGE || _la==SimpleName) {
				{
				setState(1116);
				userType();
				setState(1120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Q) {
					{
					{
					setState(1117);
					match(Q);
					}
					}
					setState(1122);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1125);
			match(REFERENCE);
			setState(1128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IMPORT:
			case ClassModifier_enum:
			case Declaration_companion:
			case Declaration_init:
			case SimpleName:
				{
				setState(1126);
				identifier();
				}
				break;
			case Declaration_class:
				{
				setState(1127);
				match(Declaration_class);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(1131);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,146,_ctx) ) {
			case 1:
				{
				setState(1130);
				typeArguments();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentifierContext extends ParserRuleContext {
		public TerminalNode SimpleName() { return getToken(KotlinParser.SimpleName, 0); }
		public TerminalNode ClassModifier_enum() { return getToken(KotlinParser.ClassModifier_enum, 0); }
		public TerminalNode IMPORT() { return getToken(KotlinParser.IMPORT, 0); }
		public TerminalNode Declaration_companion() { return getToken(KotlinParser.Declaration_companion, 0); }
		public TerminalNode Declaration_init() { return getToken(KotlinParser.Declaration_init, 0); }
		public IdentifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterIdentifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitIdentifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentifierContext identifier() throws RecognitionException {
		IdentifierContext _localctx = new IdentifierContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_identifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1133);
			_la = _input.LA(1);
			if ( !(_la==IMPORT || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ClassModifier_enum - 72)) | (1L << (Declaration_companion - 72)) | (1L << (Declaration_init - 72)) | (1L << (SimpleName - 72)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode SINGLE_QUOTE() { return getToken(KotlinParser.SINGLE_QUOTE, 0); }
		public List<TerminalNode> SINLE_QUOTE_CLOSE() { return getTokens(KotlinParser.SINLE_QUOTE_CLOSE); }
		public TerminalNode SINLE_QUOTE_CLOSE(int i) {
			return getToken(KotlinParser.SINLE_QUOTE_CLOSE, i);
		}
		public List<TerminalNode> SINLE_QUOTE_ESCAPED_CHAR() { return getTokens(KotlinParser.SINLE_QUOTE_ESCAPED_CHAR); }
		public TerminalNode SINLE_QUOTE_ESCAPED_CHAR(int i) {
			return getToken(KotlinParser.SINLE_QUOTE_ESCAPED_CHAR, i);
		}
		public List<TerminalNode> SINLE_QUOTE_EXPRESSION_START() { return getTokens(KotlinParser.SINLE_QUOTE_EXPRESSION_START); }
		public TerminalNode SINLE_QUOTE_EXPRESSION_START(int i) {
			return getToken(KotlinParser.SINLE_QUOTE_EXPRESSION_START, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> CLOSE_BLOCK() { return getTokens(KotlinParser.CLOSE_BLOCK); }
		public TerminalNode CLOSE_BLOCK(int i) {
			return getToken(KotlinParser.CLOSE_BLOCK, i);
		}
		public List<TerminalNode> SINGLE_QUOTE_REF() { return getTokens(KotlinParser.SINGLE_QUOTE_REF); }
		public TerminalNode SINGLE_QUOTE_REF(int i) {
			return getToken(KotlinParser.SINGLE_QUOTE_REF, i);
		}
		public TerminalNode TRIPLE_QUOTE() { return getToken(KotlinParser.TRIPLE_QUOTE, 0); }
		public List<TerminalNode> MULTILINE_QUOTE_CLOSE() { return getTokens(KotlinParser.MULTILINE_QUOTE_CLOSE); }
		public TerminalNode MULTILINE_QUOTE_CLOSE(int i) {
			return getToken(KotlinParser.MULTILINE_QUOTE_CLOSE, i);
		}
		public List<TerminalNode> MULTILINE_QUOTE_EXPRESSION_START() { return getTokens(KotlinParser.MULTILINE_QUOTE_EXPRESSION_START); }
		public TerminalNode MULTILINE_QUOTE_EXPRESSION_START(int i) {
			return getToken(KotlinParser.MULTILINE_QUOTE_EXPRESSION_START, i);
		}
		public List<TerminalNode> MULTILINE_QUOTE_REF() { return getTokens(KotlinParser.MULTILINE_QUOTE_REF); }
		public TerminalNode MULTILINE_QUOTE_REF(int i) {
			return getToken(KotlinParser.MULTILINE_QUOTE_REF, i);
		}
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_stringLiteral);
		int _la;
		try {
			setState(1162);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SINGLE_QUOTE:
				enterOuterAlt(_localctx, 1);
				{
				setState(1135);
				match(SINGLE_QUOTE);
				setState(1145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULTILINE_COMMENT) | (1L << SINGLELINE_COMMENT) | (1L << WHITESPACE) | (1L << IntegerLiteral) | (1L << SEMI) | (1L << OPEN_BLOCK) | (1L << CLOSE_BLOCK) | (1L << HexadecimalLiteral) | (1L << CharacterLiteral) | (1L << TRIPLE_QUOTE) | (1L << SINGLE_QUOTE) | (1L << PACKAGE) | (1L << IMPORT) | (1L << DOT) | (1L << STAR) | (1L << COMMA) | (1L << LT) | (1L << LTE) | (1L << GT) | (1L << GTE) | (1L << EQ) | (1L << EQ_EQ) | (1L << EQ_EQ_EQ) | (1L << NEQ) | (1L << COLON) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << Q) | (1L << DA) | (1L << DISJ) | (1L << CONJ) | (1L << ELVIS) | (1L << LONG_RANGE) | (1L << RANGE) | (1L << REFERENCE) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << OP_ASTERISK) | (1L << OP_DIV) | (1L << OP_MOD) | (1L << OP_PLUS) | (1L << OP_MUNUS) | (1L << OP_IN) | (1L << OP_NOT_IN) | (1L << OP_IS) | (1L << OP_NOT_IS) | (1L << OP_AS) | (1L << OP_AS_SAFE) | (1L << OP_PLUS_ASSIGN) | (1L << OP_MINUS_ASSIGN) | (1L << OP_MULT_ASSIGN) | (1L << OP_DIV_ASSIGN) | (1L << OP_MOD_ASSIGN) | (1L << OP_DECREMENT) | (1L << OP_INCREMENT) | (1L << OP_NULL_ASSERT) | (1L << OP_NOT) | (1L << SQ_OPEN) | (1L << SQ_CLOSE) | (1L << KEYWORD_val) | (1L << KEYWORD_var) | (1L << KEYWORD_by))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (KEYWORD_dynamic - 64)) | (1L << (KEYWORD_where - 64)) | (1L << (GET - 64)) | (1L << (SET - 64)) | (1L << (HierarchyModifier_abstract - 64)) | (1L << (HierarchyModifier_open - 64)) | (1L << (HierarchyModifier_final - 64)) | (1L << (HierarchyModifier_override - 64)) | (1L << (ClassModifier_enum - 64)) | (1L << (ClassModifier_annotation - 64)) | (1L << (ClassModifier_data - 64)) | (1L << (AccessModifier_private - 64)) | (1L << (AccessModifier_protected - 64)) | (1L << (AccessModifier_public - 64)) | (1L << (AccessModifier_internal - 64)) | (1L << (VarianceAnnotation_out - 64)) | (1L << (DOG - 64)) | (1L << (AnnotationUseSiteTarget_file - 64)) | (1L << (AnnotationUseSiteTarget_field - 64)) | (1L << (AnnotationUseSiteTarget_property - 64)) | (1L << (AnnotationUseSiteTarget_param - 64)) | (1L << (AnnotationUseSiteTarget_sparam - 64)) | (1L << (Jump_throw - 64)) | (1L << (Jump_continue - 64)) | (1L << (Jump_return - 64)) | (1L << (Jump_break - 64)) | (1L << (KEYWORD_constructor - 64)) | (1L << (ConstructorDelegationCall_this - 64)) | (1L << (ConstructorDelegationCall_super - 64)) | (1L << (Declaration_class - 64)) | (1L << (Declaration_interface - 64)) | (1L << (Declaration_object - 64)) | (1L << (Declaration_companion - 64)) | (1L << (Declaration_fun - 64)) | (1L << (Declaration_init - 64)) | (1L << (CF_if - 64)) | (1L << (CF_else - 64)) | (1L << (CF_when - 64)) | (1L << (CF_while - 64)) | (1L << (CF_for - 64)) | (1L << (CF_do - 64)) | (1L << (CF_try - 64)) | (1L << (CF_catch - 64)) | (1L << (CF_FINALLY - 64)) | (1L << (BAX - 64)) | (1L << (SimpleName - 64)) | (1L << (SINLE_QUOTE_WHITESPACE - 64)) | (1L << (SINGLE_TEXT - 64)) | (1L << (SINLE_QUOTE_ESCAPED_CHAR - 64)) | (1L << (SINLE_QUOTE_EXPRESSION_START - 64)) | (1L << (SINGLE_QUOTE_REF - 64)) | (1L << (MULTILINE_QUOTE_TEXT - 64)) | (1L << (MULTILINE_QUOTE_CLOSE - 64)) | (1L << (MULTILINE_QUOTES - 64)) | (1L << (MULTILINE_QUOTE_EXPRESSION_START - 64)) | (1L << (MULTILINE_QUOTE_REF - 64)))) != 0)) {
					{
					setState(1143);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,147,_ctx) ) {
					case 1:
						{
						setState(1136);
						match(SINLE_QUOTE_ESCAPED_CHAR);
						}
						break;
					case 2:
						{
						setState(1137);
						match(SINLE_QUOTE_EXPRESSION_START);
						setState(1138);
						expression();
						setState(1139);
						match(CLOSE_BLOCK);
						}
						break;
					case 3:
						{
						setState(1141);
						match(SINGLE_QUOTE_REF);
						}
						break;
					case 4:
						{
						setState(1142);
						_la = _input.LA(1);
						if ( _la <= 0 || (_la==SINLE_QUOTE_CLOSE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					}
					setState(1147);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1148);
				match(SINLE_QUOTE_CLOSE);
				}
				break;
			case TRIPLE_QUOTE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1149);
				match(TRIPLE_QUOTE);
				setState(1158);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULTILINE_COMMENT) | (1L << SINGLELINE_COMMENT) | (1L << WHITESPACE) | (1L << IntegerLiteral) | (1L << SEMI) | (1L << OPEN_BLOCK) | (1L << CLOSE_BLOCK) | (1L << HexadecimalLiteral) | (1L << CharacterLiteral) | (1L << TRIPLE_QUOTE) | (1L << SINGLE_QUOTE) | (1L << PACKAGE) | (1L << IMPORT) | (1L << DOT) | (1L << STAR) | (1L << COMMA) | (1L << LT) | (1L << LTE) | (1L << GT) | (1L << GTE) | (1L << EQ) | (1L << EQ_EQ) | (1L << EQ_EQ_EQ) | (1L << NEQ) | (1L << COLON) | (1L << BRACE_OPEN) | (1L << BRACE_CLOSE) | (1L << Q) | (1L << DA) | (1L << DISJ) | (1L << CONJ) | (1L << ELVIS) | (1L << LONG_RANGE) | (1L << RANGE) | (1L << REFERENCE) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << OP_ASTERISK) | (1L << OP_DIV) | (1L << OP_MOD) | (1L << OP_PLUS) | (1L << OP_MUNUS) | (1L << OP_IN) | (1L << OP_NOT_IN) | (1L << OP_IS) | (1L << OP_NOT_IS) | (1L << OP_AS) | (1L << OP_AS_SAFE) | (1L << OP_PLUS_ASSIGN) | (1L << OP_MINUS_ASSIGN) | (1L << OP_MULT_ASSIGN) | (1L << OP_DIV_ASSIGN) | (1L << OP_MOD_ASSIGN) | (1L << OP_DECREMENT) | (1L << OP_INCREMENT) | (1L << OP_NULL_ASSERT) | (1L << OP_NOT) | (1L << SQ_OPEN) | (1L << SQ_CLOSE) | (1L << KEYWORD_val) | (1L << KEYWORD_var) | (1L << KEYWORD_by))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (KEYWORD_dynamic - 64)) | (1L << (KEYWORD_where - 64)) | (1L << (GET - 64)) | (1L << (SET - 64)) | (1L << (HierarchyModifier_abstract - 64)) | (1L << (HierarchyModifier_open - 64)) | (1L << (HierarchyModifier_final - 64)) | (1L << (HierarchyModifier_override - 64)) | (1L << (ClassModifier_enum - 64)) | (1L << (ClassModifier_annotation - 64)) | (1L << (ClassModifier_data - 64)) | (1L << (AccessModifier_private - 64)) | (1L << (AccessModifier_protected - 64)) | (1L << (AccessModifier_public - 64)) | (1L << (AccessModifier_internal - 64)) | (1L << (VarianceAnnotation_out - 64)) | (1L << (DOG - 64)) | (1L << (AnnotationUseSiteTarget_file - 64)) | (1L << (AnnotationUseSiteTarget_field - 64)) | (1L << (AnnotationUseSiteTarget_property - 64)) | (1L << (AnnotationUseSiteTarget_param - 64)) | (1L << (AnnotationUseSiteTarget_sparam - 64)) | (1L << (Jump_throw - 64)) | (1L << (Jump_continue - 64)) | (1L << (Jump_return - 64)) | (1L << (Jump_break - 64)) | (1L << (KEYWORD_constructor - 64)) | (1L << (ConstructorDelegationCall_this - 64)) | (1L << (ConstructorDelegationCall_super - 64)) | (1L << (Declaration_class - 64)) | (1L << (Declaration_interface - 64)) | (1L << (Declaration_object - 64)) | (1L << (Declaration_companion - 64)) | (1L << (Declaration_fun - 64)) | (1L << (Declaration_init - 64)) | (1L << (CF_if - 64)) | (1L << (CF_else - 64)) | (1L << (CF_when - 64)) | (1L << (CF_while - 64)) | (1L << (CF_for - 64)) | (1L << (CF_do - 64)) | (1L << (CF_try - 64)) | (1L << (CF_catch - 64)) | (1L << (CF_FINALLY - 64)) | (1L << (BAX - 64)) | (1L << (SimpleName - 64)) | (1L << (SINLE_QUOTE_WHITESPACE - 64)) | (1L << (SINGLE_TEXT - 64)) | (1L << (SINLE_QUOTE_CLOSE - 64)) | (1L << (SINLE_QUOTE_ESCAPED_CHAR - 64)) | (1L << (SINLE_QUOTE_EXPRESSION_START - 64)) | (1L << (SINGLE_QUOTE_REF - 64)) | (1L << (MULTILINE_QUOTE_TEXT - 64)) | (1L << (MULTILINE_QUOTES - 64)) | (1L << (MULTILINE_QUOTE_EXPRESSION_START - 64)) | (1L << (MULTILINE_QUOTE_REF - 64)))) != 0)) {
					{
					setState(1156);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,149,_ctx) ) {
					case 1:
						{
						setState(1150);
						match(MULTILINE_QUOTE_EXPRESSION_START);
						setState(1151);
						expression();
						setState(1152);
						match(CLOSE_BLOCK);
						}
						break;
					case 2:
						{
						setState(1154);
						match(MULTILINE_QUOTE_REF);
						}
						break;
					case 3:
						{
						setState(1155);
						_la = _input.LA(1);
						if ( _la <= 0 || (_la==MULTILINE_QUOTE_CLOSE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					}
					setState(1160);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1161);
				match(MULTILINE_QUOTE_CLOSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomicExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LiteralConstantContext literalConstant() {
			return getRuleContext(LiteralConstantContext.class,0);
		}
		public FunctionLiteralContext functionLiteral() {
			return getRuleContext(FunctionLiteralContext.class,0);
		}
		public LabelReferenceContext labelReference() {
			return getRuleContext(LabelReferenceContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IfExpressionContext ifExpression() {
			return getRuleContext(IfExpressionContext.class,0);
		}
		public WhenContext when() {
			return getRuleContext(WhenContext.class,0);
		}
		public TryExpressionContext tryExpression() {
			return getRuleContext(TryExpressionContext.class,0);
		}
		public ObjectLiteralContext objectLiteral() {
			return getRuleContext(ObjectLiteralContext.class,0);
		}
		public JumpContext jump() {
			return getRuleContext(JumpContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public IdentifierContext identifier() {
			return getRuleContext(IdentifierContext.class,0);
		}
		public AtomicExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomicExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAtomicExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAtomicExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAtomicExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomicExpressionContext atomicExpression() throws RecognitionException {
		AtomicExpressionContext _localctx = new AtomicExpressionContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_atomicExpression);
		try {
			setState(1194);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BRACE_OPEN:
				enterOuterAlt(_localctx, 1);
				{
				setState(1164);
				match(BRACE_OPEN);
				setState(1165);
				expression();
				setState(1166);
				match(BRACE_CLOSE);
				}
				break;
			case IntegerLiteral:
			case HexadecimalLiteral:
			case CharacterLiteral:
			case TRIPLE_QUOTE:
			case SINGLE_QUOTE:
			case TRUE:
			case FALSE:
			case NULL:
				enterOuterAlt(_localctx, 2);
				{
				setState(1168);
				literalConstant();
				}
				break;
			case OPEN_BLOCK:
				enterOuterAlt(_localctx, 3);
				{
				setState(1169);
				functionLiteral();
				}
				break;
			case ConstructorDelegationCall_this:
				enterOuterAlt(_localctx, 4);
				{
				setState(1170);
				match(ConstructorDelegationCall_this);
				setState(1172);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,152,_ctx) ) {
				case 1:
					{
					setState(1171);
					labelReference();
					}
					break;
				}
				}
				break;
			case ConstructorDelegationCall_super:
				enterOuterAlt(_localctx, 5);
				{
				setState(1174);
				match(ConstructorDelegationCall_super);
				setState(1179);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,153,_ctx) ) {
				case 1:
					{
					setState(1175);
					match(LT);
					setState(1176);
					type();
					setState(1177);
					match(GT);
					}
					break;
				}
				setState(1182);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,154,_ctx) ) {
				case 1:
					{
					setState(1181);
					labelReference();
					}
					break;
				}
				}
				break;
			case CF_if:
				enterOuterAlt(_localctx, 6);
				{
				setState(1184);
				ifExpression();
				}
				break;
			case CF_when:
				enterOuterAlt(_localctx, 7);
				{
				setState(1185);
				when();
				}
				break;
			case CF_try:
				enterOuterAlt(_localctx, 8);
				{
				setState(1186);
				tryExpression();
				}
				break;
			case Declaration_object:
				enterOuterAlt(_localctx, 9);
				{
				setState(1187);
				objectLiteral();
				}
				break;
			case Jump_throw:
			case Jump_continue:
			case Jump_return:
			case Jump_break:
				enterOuterAlt(_localctx, 10);
				{
				setState(1188);
				jump();
				}
				break;
			case CF_while:
			case CF_for:
			case CF_do:
				enterOuterAlt(_localctx, 11);
				{
				setState(1189);
				loop();
				}
				break;
			case IMPORT:
			case ClassModifier_enum:
			case Declaration_companion:
			case Declaration_init:
			case SimpleName:
				enterOuterAlt(_localctx, 12);
				{
				setState(1190);
				identifier();
				}
				break;
			case BAX:
				enterOuterAlt(_localctx, 13);
				{
				setState(1191);
				match(BAX);
				setState(1192);
				identifier();
				}
				break;
			case PACKAGE:
				enterOuterAlt(_localctx, 14);
				{
				setState(1193);
				match(PACKAGE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralConstantContext extends ParserRuleContext {
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public TerminalNode IntegerLiteral() { return getToken(KotlinParser.IntegerLiteral, 0); }
		public TerminalNode HexadecimalLiteral() { return getToken(KotlinParser.HexadecimalLiteral, 0); }
		public TerminalNode CharacterLiteral() { return getToken(KotlinParser.CharacterLiteral, 0); }
		public LiteralConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literalConstant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterLiteralConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitLiteralConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitLiteralConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralConstantContext literalConstant() throws RecognitionException {
		LiteralConstantContext _localctx = new LiteralConstantContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_literalConstant);
		try {
			setState(1203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(1196);
				match(TRUE);
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(1197);
				match(FALSE);
				}
				break;
			case TRIPLE_QUOTE:
			case SINGLE_QUOTE:
				enterOuterAlt(_localctx, 3);
				{
				setState(1198);
				stringLiteral();
				}
				break;
			case IntegerLiteral:
				enterOuterAlt(_localctx, 4);
				{
				setState(1199);
				match(IntegerLiteral);
				}
				break;
			case HexadecimalLiteral:
				enterOuterAlt(_localctx, 5);
				{
				setState(1200);
				match(HexadecimalLiteral);
				}
				break;
			case CharacterLiteral:
				enterOuterAlt(_localctx, 6);
				{
				setState(1201);
				match(CharacterLiteral);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 7);
				{
				setState(1202);
				match(NULL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public PropertyDeclarationContext propertyDeclaration() {
			return getRuleContext(PropertyDeclarationContext.class,0);
		}
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public ObjectDeclarationContext objectDeclaration() {
			return getRuleContext(ObjectDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_declaration);
		try {
			setState(1209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,157,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1205);
				functionDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1206);
				propertyDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1207);
				classDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1208);
				objectDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_statement);
		try {
			setState(1213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,158,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1211);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1212);
				expression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeOperationContext extends ParserRuleContext {
		public MultiplicativeOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterMultiplicativeOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitMultiplicativeOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitMultiplicativeOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeOperationContext multiplicativeOperation() throws RecognitionException {
		MultiplicativeOperationContext _localctx = new MultiplicativeOperationContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_multiplicativeOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1215);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STAR) | (1L << OP_DIV) | (1L << OP_MOD))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveOperationContext extends ParserRuleContext {
		public AdditiveOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAdditiveOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAdditiveOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAdditiveOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveOperationContext additiveOperation() throws RecognitionException {
		AdditiveOperationContext _localctx = new AdditiveOperationContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_additiveOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1217);
			_la = _input.LA(1);
			if ( !(_la==OP_PLUS || _la==OP_MUNUS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InOperationContext extends ParserRuleContext {
		public TerminalNode OP_IN() { return getToken(KotlinParser.OP_IN, 0); }
		public InOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterInOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitInOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitInOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InOperationContext inOperation() throws RecognitionException {
		InOperationContext _localctx = new InOperationContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_inOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1219);
			_la = _input.LA(1);
			if ( !(_la==OP_IN || _la==OP_NOT_IN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeOperationContext extends ParserRuleContext {
		public TerminalNode OP_AS() { return getToken(KotlinParser.OP_AS, 0); }
		public TypeOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterTypeOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitTypeOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitTypeOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeOperationContext typeOperation() throws RecognitionException {
		TypeOperationContext _localctx = new TypeOperationContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_typeOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1221);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << COLON) | (1L << OP_AS) | (1L << OP_AS_SAFE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IsOperationContext extends ParserRuleContext {
		public IsOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_isOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterIsOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitIsOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitIsOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IsOperationContext isOperation() throws RecognitionException {
		IsOperationContext _localctx = new IsOperationContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_isOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1223);
			_la = _input.LA(1);
			if ( !(_la==OP_IS || _la==OP_NOT_IS) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonOperationContext extends ParserRuleContext {
		public ComparisonOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterComparisonOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitComparisonOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitComparisonOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOperationContext comparisonOperation() throws RecognitionException {
		ComparisonOperationContext _localctx = new ComparisonOperationContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_comparisonOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1225);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << LTE) | (1L << GT) | (1L << GTE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityOperationContext extends ParserRuleContext {
		public EqualityOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterEqualityOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitEqualityOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitEqualityOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityOperationContext equalityOperation() throws RecognitionException {
		EqualityOperationContext _localctx = new EqualityOperationContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_equalityOperation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1227);
			_la = _input.LA(1);
			if ( !(_la==EQ_EQ || _la==NEQ) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentOperatorContext extends ParserRuleContext {
		public AssignmentOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAssignmentOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAssignmentOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAssignmentOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentOperatorContext assignmentOperator() throws RecognitionException {
		AssignmentOperatorContext _localctx = new AssignmentOperatorContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_assignmentOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1229);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << OP_PLUS_ASSIGN) | (1L << OP_MINUS_ASSIGN) | (1L << OP_MULT_ASSIGN) | (1L << OP_DIV_ASSIGN) | (1L << OP_MOD_ASSIGN))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrefixUnaryOperationContext extends ParserRuleContext {
		public AnnotationsContext annotations() {
			return getRuleContext(AnnotationsContext.class,0);
		}
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public PrefixUnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefixUnaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPrefixUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPrefixUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPrefixUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixUnaryOperationContext prefixUnaryOperation() throws RecognitionException {
		PrefixUnaryOperationContext _localctx = new PrefixUnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_prefixUnaryOperation);
		try {
			setState(1238);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_MUNUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(1231);
				match(OP_MUNUS);
				}
				break;
			case OP_PLUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(1232);
				match(OP_PLUS);
				}
				break;
			case OP_INCREMENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(1233);
				match(OP_INCREMENT);
				}
				break;
			case OP_DECREMENT:
				enterOuterAlt(_localctx, 4);
				{
				setState(1234);
				match(OP_DECREMENT);
				}
				break;
			case OP_NOT:
				enterOuterAlt(_localctx, 5);
				{
				setState(1235);
				match(OP_NOT);
				}
				break;
			case DOG:
				enterOuterAlt(_localctx, 6);
				{
				setState(1236);
				annotations();
				}
				break;
			case IMPORT:
			case ClassModifier_enum:
			case Declaration_companion:
			case Declaration_init:
			case SimpleName:
				enterOuterAlt(_localctx, 7);
				{
				setState(1237);
				labelDefinition();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixUnaryOperationContext extends ParserRuleContext {
		public CallSuffixContext callSuffix() {
			return getRuleContext(CallSuffixContext.class,0);
		}
		public ArrayAccessContext arrayAccess() {
			return getRuleContext(ArrayAccessContext.class,0);
		}
		public MemberAccessOperationContext memberAccessOperation() {
			return getRuleContext(MemberAccessOperationContext.class,0);
		}
		public PostfixUnaryExpressionContext postfixUnaryExpression() {
			return getRuleContext(PostfixUnaryExpressionContext.class,0);
		}
		public PostfixUnaryOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixUnaryOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterPostfixUnaryOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitPostfixUnaryOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitPostfixUnaryOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixUnaryOperationContext postfixUnaryOperation() throws RecognitionException {
		PostfixUnaryOperationContext _localctx = new PostfixUnaryOperationContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_postfixUnaryOperation);
		try {
			setState(1248);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_INCREMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(1240);
				match(OP_INCREMENT);
				}
				break;
			case OP_DECREMENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(1241);
				match(OP_DECREMENT);
				}
				break;
			case OP_NULL_ASSERT:
				enterOuterAlt(_localctx, 3);
				{
				setState(1242);
				match(OP_NULL_ASSERT);
				}
				break;
			case OPEN_BLOCK:
			case IMPORT:
			case LT:
			case BRACE_OPEN:
			case ClassModifier_enum:
			case DOG:
			case Declaration_companion:
			case Declaration_init:
			case SimpleName:
				enterOuterAlt(_localctx, 4);
				{
				setState(1243);
				callSuffix();
				}
				break;
			case SQ_OPEN:
				enterOuterAlt(_localctx, 5);
				{
				setState(1244);
				arrayAccess();
				}
				break;
			case DOT:
			case Q:
				enterOuterAlt(_localctx, 6);
				{
				setState(1245);
				memberAccessOperation();
				setState(1246);
				postfixUnaryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AnnotatedLambdaContext extends ParserRuleContext {
		public FunctionLiteralContext functionLiteral() {
			return getRuleContext(FunctionLiteralContext.class,0);
		}
		public List<UnescapedAnnotationContext> unescapedAnnotation() {
			return getRuleContexts(UnescapedAnnotationContext.class);
		}
		public UnescapedAnnotationContext unescapedAnnotation(int i) {
			return getRuleContext(UnescapedAnnotationContext.class,i);
		}
		public LabelDefinitionContext labelDefinition() {
			return getRuleContext(LabelDefinitionContext.class,0);
		}
		public AnnotatedLambdaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_annotatedLambda; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterAnnotatedLambda(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitAnnotatedLambda(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitAnnotatedLambda(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnnotatedLambdaContext annotatedLambda() throws RecognitionException {
		AnnotatedLambdaContext _localctx = new AnnotatedLambdaContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_annotatedLambda);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1254);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOG) {
				{
				{
				setState(1250);
				match(DOG);
				setState(1251);
				unescapedAnnotation();
				}
				}
				setState(1256);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPORT || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ClassModifier_enum - 72)) | (1L << (Declaration_companion - 72)) | (1L << (Declaration_init - 72)) | (1L << (SimpleName - 72)))) != 0)) {
				{
				setState(1257);
				labelDefinition();
				}
			}

			setState(1260);
			functionLiteral();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberAccessOperationContext extends ParserRuleContext {
		public MemberAccessOperationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberAccessOperation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterMemberAccessOperation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitMemberAccessOperation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitMemberAccessOperation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberAccessOperationContext memberAccessOperation() throws RecognitionException {
		MemberAccessOperationContext _localctx = new MemberAccessOperationContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_memberAccessOperation);
		int _la;
		try {
			setState(1267);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(1262);
				match(DOT);
				}
				break;
			case Q:
				enterOuterAlt(_localctx, 2);
				{
				setState(1263);
				match(Q);
				setState(1265);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DOT) {
					{
					setState(1264);
					match(DOT);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionLiteralContext extends ParserRuleContext {
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public List<ModifiersContext> modifiers() {
			return getRuleContexts(ModifiersContext.class);
		}
		public ModifiersContext modifiers(int i) {
			return getRuleContext(ModifiersContext.class,i);
		}
		public List<TerminalNode> SimpleName() { return getTokens(KotlinParser.SimpleName); }
		public TerminalNode SimpleName(int i) {
			return getToken(KotlinParser.SimpleName, i);
		}
		public FunctionLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterFunctionLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitFunctionLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitFunctionLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionLiteralContext functionLiteral() throws RecognitionException {
		FunctionLiteralContext _localctx = new FunctionLiteralContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_functionLiteral);
		int _la;
		try {
			setState(1292);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,167,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1269);
				match(OPEN_BLOCK);
				setState(1270);
				statements();
				setState(1271);
				match(CLOSE_BLOCK);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1273);
				match(OPEN_BLOCK);
				setState(1286);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OP_IN || ((((_la - 68)) & ~0x3f) == 0 && ((1L << (_la - 68)) & ((1L << (HierarchyModifier_abstract - 68)) | (1L << (HierarchyModifier_open - 68)) | (1L << (HierarchyModifier_final - 68)) | (1L << (HierarchyModifier_override - 68)) | (1L << (ClassModifier_enum - 68)) | (1L << (ClassModifier_annotation - 68)) | (1L << (ClassModifier_data - 68)) | (1L << (AccessModifier_private - 68)) | (1L << (AccessModifier_protected - 68)) | (1L << (AccessModifier_public - 68)) | (1L << (AccessModifier_internal - 68)) | (1L << (VarianceAnnotation_out - 68)) | (1L << (DOG - 68)) | (1L << (SimpleName - 68)))) != 0)) {
					{
					{
					setState(1274);
					modifiers();
					setState(1275);
					match(SimpleName);
					}
					setState(1283);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(1277);
						match(COMMA);
						setState(1278);
						modifiers();
						setState(1279);
						match(SimpleName);
						}
						}
						setState(1285);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(1288);
				match(OP_ASTERISK);
				setState(1289);
				statements();
				setState(1290);
				match(CLOSE_BLOCK);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayAccessContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayAccess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterArrayAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitArrayAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitArrayAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayAccessContext arrayAccess() throws RecognitionException {
		ArrayAccessContext _localctx = new ArrayAccessContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_arrayAccess);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1294);
			match(SQ_OPEN);
			setState(1303);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntegerLiteral) | (1L << OPEN_BLOCK) | (1L << HexadecimalLiteral) | (1L << CharacterLiteral) | (1L << TRIPLE_QUOTE) | (1L << SINGLE_QUOTE) | (1L << PACKAGE) | (1L << IMPORT) | (1L << BRACE_OPEN) | (1L << REFERENCE) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << OP_PLUS) | (1L << OP_MUNUS) | (1L << OP_DECREMENT) | (1L << OP_INCREMENT) | (1L << OP_NOT))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ClassModifier_enum - 72)) | (1L << (DOG - 72)) | (1L << (Jump_throw - 72)) | (1L << (Jump_continue - 72)) | (1L << (Jump_return - 72)) | (1L << (Jump_break - 72)) | (1L << (ConstructorDelegationCall_this - 72)) | (1L << (ConstructorDelegationCall_super - 72)) | (1L << (Declaration_object - 72)) | (1L << (Declaration_companion - 72)) | (1L << (Declaration_init - 72)) | (1L << (CF_if - 72)) | (1L << (CF_when - 72)) | (1L << (CF_while - 72)) | (1L << (CF_for - 72)) | (1L << (CF_do - 72)) | (1L << (CF_try - 72)) | (1L << (BAX - 72)) | (1L << (SimpleName - 72)))) != 0)) {
				{
				setState(1295);
				expression();
				setState(1300);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1296);
					match(COMMA);
					setState(1297);
					expression();
					}
					}
					setState(1302);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(1305);
			match(SQ_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectLiteralContext extends ParserRuleContext {
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public List<DelegationSpecifierContext> delegationSpecifier() {
			return getRuleContexts(DelegationSpecifierContext.class);
		}
		public DelegationSpecifierContext delegationSpecifier(int i) {
			return getRuleContext(DelegationSpecifierContext.class,i);
		}
		public ObjectLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_objectLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterObjectLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitObjectLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitObjectLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectLiteralContext objectLiteral() throws RecognitionException {
		ObjectLiteralContext _localctx = new ObjectLiteralContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_objectLiteral);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1307);
			match(Declaration_object);
			setState(1317);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
			case 1:
				{
				setState(1308);
				match(COLON);
				setState(1309);
				delegationSpecifier();
				setState(1314);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,170,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1310);
						match(COMMA);
						setState(1311);
						delegationSpecifier();
						}
						} 
					}
					setState(1316);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,170,_ctx);
				}
				}
				break;
			}
			setState(1319);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhenContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<WhenEntryContext> whenEntry() {
			return getRuleContexts(WhenEntryContext.class);
		}
		public WhenEntryContext whenEntry(int i) {
			return getRuleContext(WhenEntryContext.class,i);
		}
		public WhenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_when; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterWhen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitWhen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitWhen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenContext when() throws RecognitionException {
		WhenContext _localctx = new WhenContext(_ctx, getState());
		enterRule(_localctx, 218, RULE_when);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1321);
			match(CF_when);
			setState(1326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BRACE_OPEN) {
				{
				setState(1322);
				match(BRACE_OPEN);
				setState(1323);
				expression();
				setState(1324);
				match(BRACE_CLOSE);
				}
			}

			setState(1328);
			match(OPEN_BLOCK);
			setState(1332);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntegerLiteral) | (1L << OPEN_BLOCK) | (1L << HexadecimalLiteral) | (1L << CharacterLiteral) | (1L << TRIPLE_QUOTE) | (1L << SINGLE_QUOTE) | (1L << PACKAGE) | (1L << IMPORT) | (1L << BRACE_OPEN) | (1L << REFERENCE) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << OP_PLUS) | (1L << OP_MUNUS) | (1L << OP_IN) | (1L << OP_NOT_IN) | (1L << OP_IS) | (1L << OP_NOT_IS) | (1L << OP_DECREMENT) | (1L << OP_INCREMENT) | (1L << OP_NOT))) != 0) || ((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (ClassModifier_enum - 72)) | (1L << (DOG - 72)) | (1L << (Jump_throw - 72)) | (1L << (Jump_continue - 72)) | (1L << (Jump_return - 72)) | (1L << (Jump_break - 72)) | (1L << (ConstructorDelegationCall_this - 72)) | (1L << (ConstructorDelegationCall_super - 72)) | (1L << (Declaration_object - 72)) | (1L << (Declaration_companion - 72)) | (1L << (Declaration_init - 72)) | (1L << (CF_if - 72)) | (1L << (CF_else - 72)) | (1L << (CF_when - 72)) | (1L << (CF_while - 72)) | (1L << (CF_for - 72)) | (1L << (CF_do - 72)) | (1L << (CF_try - 72)) | (1L << (BAX - 72)) | (1L << (SimpleName - 72)))) != 0)) {
				{
				{
				setState(1329);
				whenEntry();
				}
				}
				setState(1334);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1335);
			match(CLOSE_BLOCK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhenEntryContext extends ParserRuleContext {
		public List<WhenConditionContext> whenCondition() {
			return getRuleContexts(WhenConditionContext.class);
		}
		public WhenConditionContext whenCondition(int i) {
			return getRuleContext(WhenConditionContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<TerminalNode> SEMI() { return getTokens(KotlinParser.SEMI); }
		public TerminalNode SEMI(int i) {
			return getToken(KotlinParser.SEMI, i);
		}
		public WhenEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterWhenEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitWhenEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitWhenEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenEntryContext whenEntry() throws RecognitionException {
		WhenEntryContext _localctx = new WhenEntryContext(_ctx, getState());
		enterRule(_localctx, 220, RULE_whenEntry);
		int _la;
		try {
			setState(1362);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntegerLiteral:
			case OPEN_BLOCK:
			case HexadecimalLiteral:
			case CharacterLiteral:
			case TRIPLE_QUOTE:
			case SINGLE_QUOTE:
			case PACKAGE:
			case IMPORT:
			case BRACE_OPEN:
			case REFERENCE:
			case TRUE:
			case FALSE:
			case NULL:
			case OP_PLUS:
			case OP_MUNUS:
			case OP_IN:
			case OP_NOT_IN:
			case OP_IS:
			case OP_NOT_IS:
			case OP_DECREMENT:
			case OP_INCREMENT:
			case OP_NOT:
			case ClassModifier_enum:
			case DOG:
			case Jump_throw:
			case Jump_continue:
			case Jump_return:
			case Jump_break:
			case ConstructorDelegationCall_this:
			case ConstructorDelegationCall_super:
			case Declaration_object:
			case Declaration_companion:
			case Declaration_init:
			case CF_if:
			case CF_when:
			case CF_while:
			case CF_for:
			case CF_do:
			case CF_try:
			case BAX:
			case SimpleName:
				enterOuterAlt(_localctx, 1);
				{
				setState(1337);
				whenCondition();
				setState(1342);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(1338);
					match(COMMA);
					setState(1339);
					whenCondition();
					}
					}
					setState(1344);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(1345);
				match(OP_ASTERISK);
				setState(1346);
				expression();
				setState(1350);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEMI) {
					{
					{
					setState(1347);
					match(SEMI);
					}
					}
					setState(1352);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case CF_else:
				enterOuterAlt(_localctx, 2);
				{
				setState(1353);
				match(CF_else);
				setState(1354);
				match(OP_ASTERISK);
				setState(1355);
				expression();
				setState(1359);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEMI) {
					{
					{
					setState(1356);
					match(SEMI);
					}
					}
					setState(1361);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhenConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public WhenConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).enterWhenCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof KotlinParserListener ) ((KotlinParserListener)listener).exitWhenCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof KotlinParserVisitor ) return ((KotlinParserVisitor<? extends T>)visitor).visitWhenCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenConditionContext whenCondition() throws RecognitionException {
		WhenConditionContext _localctx = new WhenConditionContext(_ctx, getState());
		enterRule(_localctx, 222, RULE_whenCondition);
		int _la;
		try {
			setState(1369);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntegerLiteral:
			case OPEN_BLOCK:
			case HexadecimalLiteral:
			case CharacterLiteral:
			case TRIPLE_QUOTE:
			case SINGLE_QUOTE:
			case PACKAGE:
			case IMPORT:
			case BRACE_OPEN:
			case REFERENCE:
			case TRUE:
			case FALSE:
			case NULL:
			case OP_PLUS:
			case OP_MUNUS:
			case OP_DECREMENT:
			case OP_INCREMENT:
			case OP_NOT:
			case ClassModifier_enum:
			case DOG:
			case Jump_throw:
			case Jump_continue:
			case Jump_return:
			case Jump_break:
			case ConstructorDelegationCall_this:
			case ConstructorDelegationCall_super:
			case Declaration_object:
			case Declaration_companion:
			case Declaration_init:
			case CF_if:
			case CF_when:
			case CF_while:
			case CF_for:
			case CF_do:
			case CF_try:
			case BAX:
			case SimpleName:
				enterOuterAlt(_localctx, 1);
				{
				setState(1364);
				expression();
				}
				break;
			case OP_IN:
			case OP_NOT_IN:
				enterOuterAlt(_localctx, 2);
				{
				setState(1365);
				_la = _input.LA(1);
				if ( !(_la==OP_IN || _la==OP_NOT_IN) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1366);
				expression();
				}
				break;
			case OP_IS:
			case OP_NOT_IS:
				enterOuterAlt(_localctx, 3);
				{
				setState(1367);
				_la = _input.LA(1);
				if ( !(_la==OP_IS || _la==OP_NOT_IS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(1368);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3z\u055e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\4p\tp\4q\tq\3\2\3\2\7\2\u00e5\n\2\f\2\16\2\u00e8"+
		"\13\2\3\3\5\3\u00eb\n\3\3\3\7\3\u00ee\n\3\f\3\16\3\u00f1\13\3\3\4\3\4"+
		"\3\4\3\4\7\4\u00f7\n\4\f\4\16\4\u00fa\13\4\3\4\7\4\u00fd\n\4\f\4\16\4"+
		"\u0100\13\4\3\5\3\5\3\5\3\5\7\5\u0106\n\5\f\5\16\5\u0109\13\5\3\5\3\5"+
		"\3\5\3\5\5\5\u010f\n\5\3\5\7\5\u0112\n\5\f\5\16\5\u0115\13\5\3\6\3\6\3"+
		"\6\3\6\5\6\u011b\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u0124\n\7\3\b\5\b"+
		"\u0127\n\b\3\t\3\t\3\t\3\t\7\t\u012d\n\t\f\t\16\t\u0130\13\t\3\t\3\t\3"+
		"\n\3\n\3\n\3\n\5\n\u0138\n\n\3\13\3\13\5\13\u013c\n\13\3\13\3\13\7\13"+
		"\u0140\n\13\f\13\16\13\u0143\13\13\3\13\3\13\3\f\3\f\3\f\3\f\7\f\u014b"+
		"\n\f\f\f\16\f\u014e\13\f\5\f\u0150\n\f\3\r\5\r\u0153\n\r\3\r\3\r\3\r\3"+
		"\r\3\16\5\16\u015a\n\16\3\16\3\16\3\17\3\17\3\17\3\17\5\17\u0162\n\17"+
		"\3\17\5\17\u0165\n\17\3\17\3\17\5\17\u0169\n\17\3\17\3\17\5\17\u016d\n"+
		"\17\3\17\5\17\u0170\n\17\3\17\3\17\5\17\u0174\n\17\3\17\5\17\u0177\n\17"+
		"\5\17\u0179\n\17\3\20\3\20\5\20\u017d\n\20\3\20\3\20\3\20\7\20\u0182\n"+
		"\20\f\20\16\20\u0185\13\20\3\21\3\21\3\21\3\21\5\21\u018b\n\21\3\22\3"+
		"\22\3\22\3\22\3\22\7\22\u0192\n\22\f\22\16\22\u0195\13\22\3\22\3\22\5"+
		"\22\u0199\n\22\3\23\3\23\3\23\5\23\u019e\n\23\3\24\3\24\3\24\3\24\5\24"+
		"\u01a4\n\24\3\25\3\25\3\25\3\26\3\26\3\26\3\26\7\26\u01ad\n\26\f\26\16"+
		"\26\u01b0\13\26\5\26\u01b2\n\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27"+
		"\7\27\u01bc\n\27\f\27\16\27\u01bf\13\27\3\27\3\27\3\30\3\30\3\30\5\30"+
		"\u01c6\n\30\3\30\3\30\3\30\5\30\u01cb\n\30\3\30\3\30\5\30\u01cf\n\30\3"+
		"\30\3\30\3\30\3\30\7\30\u01d5\n\30\f\30\16\30\u01d8\13\30\5\30\u01da\n"+
		"\30\3\30\5\30\u01dd\n\30\3\30\5\30\u01e0\n\30\3\30\5\30\u01e3\n\30\3\30"+
		"\5\30\u01e6\n\30\5\30\u01e8\n\30\3\30\7\30\u01eb\n\30\f\30\16\30\u01ee"+
		"\13\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u01f9\n\31\3"+
		"\31\3\31\5\31\u01fd\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\5\32\u0208\n\32\3\32\3\32\3\32\5\32\u020d\n\32\3\33\7\33\u0210\n\33\f"+
		"\33\16\33\u0213\13\33\3\34\3\34\3\35\3\35\3\35\3\35\3\35\5\35\u021c\n"+
		"\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\6\"\u0227\n\"\r\"\16\"\u0228\3"+
		"#\3#\3#\3#\5#\u022f\n#\3#\3#\3$\3$\3%\3%\3%\5%\u0238\n%\3%\5%\u023b\n"+
		"%\3%\3%\3&\3&\3&\3&\7&\u0243\n&\f&\16&\u0246\13&\5&\u0248\n&\3&\3&\3\'"+
		"\3\'\3\'\7\'\u024f\n\'\f\'\16\'\u0252\13\'\3\'\5\'\u0255\n\'\3\'\5\'\u0258"+
		"\n\'\3(\3(\3(\3(\5(\u025e\n(\3(\5(\u0261\n(\3(\3(\5(\u0265\n(\3(\3(\5"+
		"(\u0269\n(\5(\u026b\n(\3)\3)\3)\3)\3)\5)\u0272\n)\3*\3*\3*\3+\3+\3+\3"+
		"+\3,\3,\5,\u027d\n,\3,\3,\3,\5,\u0282\n,\3-\3-\3-\5-\u0287\n-\3-\3-\3"+
		"-\3-\7-\u028d\n-\f-\16-\u0290\13-\5-\u0292\n-\3-\3-\3.\3.\3.\3.\3.\5."+
		"\u029b\n.\3.\3.\7.\u029f\n.\f.\16.\u02a2\13.\3/\3/\3/\3/\5/\u02a8\n/\3"+
		"\60\5\60\u02ab\n\60\3\60\3\60\5\60\u02af\n\60\3\60\3\60\3\60\3\60\5\60"+
		"\u02b5\n\60\3\61\3\61\5\61\u02b9\n\61\3\61\3\61\3\62\3\62\3\62\3\62\3"+
		"\63\3\63\3\63\5\63\u02c4\n\63\3\64\3\64\3\64\3\64\5\64\u02ca\n\64\3\64"+
		"\5\64\u02cd\n\64\3\64\3\64\5\64\u02d1\n\64\3\64\3\64\3\64\7\64\u02d6\n"+
		"\64\f\64\16\64\u02d9\13\64\5\64\u02db\n\64\3\64\3\64\3\64\5\64\u02e0\n"+
		"\64\3\64\7\64\u02e3\n\64\f\64\16\64\u02e6\13\64\3\65\3\65\3\65\3\65\7"+
		"\65\u02ec\n\65\f\65\16\65\u02ef\13\65\3\66\3\66\3\66\5\66\u02f4\n\66\3"+
		"\66\5\66\u02f7\n\66\3\66\3\66\7\66\u02fb\n\66\f\66\16\66\u02fe\13\66\3"+
		"\67\3\67\3\67\3\67\5\67\u0304\n\67\3\67\3\67\7\67\u0308\n\67\f\67\16\67"+
		"\u030b\13\67\38\38\38\38\58\u0311\n8\39\79\u0314\n9\f9\169\u0317\139\3"+
		":\3:\3:\3:\7:\u031d\n:\f:\16:\u0320\13:\5:\u0322\n:\3:\3:\3;\3;\3;\5;"+
		"\u0329\n;\3;\3;\3;\3;\5;\u032f\n;\3;\3;\5;\u0333\n;\3;\3;\3;\5;\u0338"+
		"\n;\3;\3;\5;\u033c\n;\3;\7;\u033f\n;\f;\16;\u0342\13;\3<\7<\u0345\n<\f"+
		"<\16<\u0348\13<\3<\3<\7<\u034c\n<\f<\16<\u034f\13<\3<\7<\u0352\n<\f<\16"+
		"<\u0355\13<\5<\u0357\n<\3<\7<\u035a\n<\f<\16<\u035d\13<\3=\3=\3=\5=\u0362"+
		"\n=\3>\3>\3>\3>\3?\3?\3?\7?\u036b\n?\f?\16?\u036e\13?\3@\3@\3@\3@\5@\u0374"+
		"\n@\3@\3@\3A\3A\5A\u037a\nA\7A\u037c\nA\fA\16A\u037f\13A\3B\3B\3B\3B\5"+
		"B\u0385\nB\3B\3B\5B\u0389\nB\3B\3B\3C\3C\3C\3C\3C\3C\7C\u0393\nC\fC\16"+
		"C\u0396\13C\3C\3C\5C\u039a\nC\3D\3D\3D\7D\u039f\nD\fD\16D\u03a2\13D\3"+
		"D\5D\u03a5\nD\3E\3E\3E\5E\u03aa\nE\3E\3E\3E\3E\3E\3E\3F\3F\3F\3G\3G\3"+
		"G\5G\u03b8\nG\3H\3H\3H\5H\u03bd\nH\3H\3H\5H\u03c1\nH\3H\3H\3H\3H\3H\3"+
		"I\3I\3I\3I\3I\3I\3J\3J\3J\3J\3J\3J\3J\3K\3K\3K\3K\7K\u03d9\nK\fK\16K\u03dc"+
		"\13K\3L\3L\3L\7L\u03e1\nL\fL\16L\u03e4\13L\3M\3M\3M\7M\u03e9\nM\fM\16"+
		"M\u03ec\13M\3N\3N\3N\3N\7N\u03f2\nN\fN\16N\u03f5\13N\3O\3O\3O\3O\7O\u03fb"+
		"\nO\fO\16O\u03fe\13O\3P\3P\3P\3P\7P\u0404\nP\fP\16P\u0407\13P\3P\3P\3"+
		"P\3P\5P\u040d\nP\5P\u040f\nP\3Q\3Q\3Q\7Q\u0414\nQ\fQ\16Q\u0417\13Q\3R"+
		"\3R\3R\7R\u041c\nR\fR\16R\u041f\13R\3S\3S\3S\7S\u0424\nS\fS\16S\u0427"+
		"\13S\3T\3T\3T\3T\7T\u042d\nT\fT\16T\u0430\13T\3U\3U\3U\3U\7U\u0436\nU"+
		"\fU\16U\u0439\13U\3V\3V\3V\3V\7V\u043f\nV\fV\16V\u0442\13V\3W\7W\u0445"+
		"\nW\fW\16W\u0448\13W\3W\3W\3X\3X\3X\3X\3X\7X\u0451\nX\fX\16X\u0454\13"+
		"X\3X\3X\7X\u0458\nX\fX\16X\u045b\13X\5X\u045d\nX\3Y\3Y\7Y\u0461\nY\fY"+
		"\16Y\u0464\13Y\5Y\u0466\nY\3Y\3Y\3Y\5Y\u046b\nY\3Y\5Y\u046e\nY\3Z\3Z\3"+
		"[\3[\3[\3[\3[\3[\3[\3[\7[\u047a\n[\f[\16[\u047d\13[\3[\3[\3[\3[\3[\3["+
		"\3[\3[\7[\u0487\n[\f[\16[\u048a\13[\3[\5[\u048d\n[\3\\\3\\\3\\\3\\\3\\"+
		"\3\\\3\\\3\\\5\\\u0497\n\\\3\\\3\\\3\\\3\\\3\\\5\\\u049e\n\\\3\\\5\\\u04a1"+
		"\n\\\3\\\3\\\3\\\3\\\3\\\3\\\3\\\3\\\3\\\3\\\5\\\u04ad\n\\\3]\3]\3]\3"+
		"]\3]\3]\3]\5]\u04b6\n]\3^\3^\3^\3^\5^\u04bc\n^\3_\3_\5_\u04c0\n_\3`\3"+
		"`\3a\3a\3b\3b\3c\3c\3d\3d\3e\3e\3f\3f\3g\3g\3h\3h\3h\3h\3h\3h\3h\5h\u04d9"+
		"\nh\3i\3i\3i\3i\3i\3i\3i\3i\5i\u04e3\ni\3j\3j\7j\u04e7\nj\fj\16j\u04ea"+
		"\13j\3j\5j\u04ed\nj\3j\3j\3k\3k\3k\5k\u04f4\nk\5k\u04f6\nk\3l\3l\3l\3"+
		"l\3l\3l\3l\3l\3l\3l\3l\3l\7l\u0504\nl\fl\16l\u0507\13l\5l\u0509\nl\3l"+
		"\3l\3l\3l\5l\u050f\nl\3m\3m\3m\3m\7m\u0515\nm\fm\16m\u0518\13m\5m\u051a"+
		"\nm\3m\3m\3n\3n\3n\3n\3n\7n\u0523\nn\fn\16n\u0526\13n\5n\u0528\nn\3n\3"+
		"n\3o\3o\3o\3o\3o\5o\u0531\no\3o\3o\7o\u0535\no\fo\16o\u0538\13o\3o\3o"+
		"\3p\3p\3p\7p\u053f\np\fp\16p\u0542\13p\3p\3p\3p\7p\u0547\np\fp\16p\u054a"+
		"\13p\3p\3p\3p\3p\7p\u0550\np\fp\16p\u0553\13p\5p\u0555\np\3q\3q\3q\3q"+
		"\3q\5q\u055c\nq\3q\2\2r\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*"+
		",.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c"+
		"\u009e\u00a0\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4"+
		"\u00b6\u00b8\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc"+
		"\u00ce\u00d0\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\u00de\u00e0\2\25\3\2"+
		"?@\4\2\27\27AA\3\2FI\3\2JL\3\2MP\4\2..QQ\4\2DESW\3\2_`\7\2\17\17JJbbd"+
		"doo\3\2rr\3\2ww\4\2\21\21*+\3\2,-\3\2./\4\2\33\33\62\63\3\2\60\61\3\2"+
		"\23\26\4\2\30\30\32\32\4\2\27\27\648\2\u05d7\2\u00e2\3\2\2\2\4\u00ea\3"+
		"\2\2\2\6\u00f2\3\2\2\2\b\u0101\3\2\2\2\n\u011a\3\2\2\2\f\u0123\3\2\2\2"+
		"\16\u0126\3\2\2\2\20\u0128\3\2\2\2\22\u0133\3\2\2\2\24\u0139\3\2\2\2\26"+
		"\u014f\3\2\2\2\30\u0152\3\2\2\2\32\u0159\3\2\2\2\34\u0178\3\2\2\2\36\u017c"+
		"\3\2\2\2 \u018a\3\2\2\2\"\u018c\3\2\2\2$\u019a\3\2\2\2&\u01a3\3\2\2\2"+
		"(\u01a5\3\2\2\2*\u01a8\3\2\2\2,\u01b7\3\2\2\2.\u01c2\3\2\2\2\60\u01fc"+
		"\3\2\2\2\62\u020c\3\2\2\2\64\u0211\3\2\2\2\66\u0214\3\2\2\28\u021b\3\2"+
		"\2\2:\u021d\3\2\2\2<\u021f\3\2\2\2>\u0221\3\2\2\2@\u0223\3\2\2\2B\u0226"+
		"\3\2\2\2D\u022a\3\2\2\2F\u0232\3\2\2\2H\u0237\3\2\2\2J\u023e\3\2\2\2L"+
		"\u024b\3\2\2\2N\u026a\3\2\2\2P\u026c\3\2\2\2R\u0273\3\2\2\2T\u0276\3\2"+
		"\2\2V\u027a\3\2\2\2X\u0286\3\2\2\2Z\u0295\3\2\2\2\\\u02a7\3\2\2\2^\u02b4"+
		"\3\2\2\2`\u02b6\3\2\2\2b\u02bc\3\2\2\2d\u02c3\3\2\2\2f\u02c5\3\2\2\2h"+
		"\u02e7\3\2\2\2j\u02f0\3\2\2\2l\u02ff\3\2\2\2n\u0310\3\2\2\2p\u0315\3\2"+
		"\2\2r\u0318\3\2\2\2t\u0325\3\2\2\2v\u0346\3\2\2\2x\u0361\3\2\2\2z\u0363"+
		"\3\2\2\2|\u0367\3\2\2\2~\u036f\3\2\2\2\u0080\u037d\3\2\2\2\u0082\u0380"+
		"\3\2\2\2\u0084\u038c\3\2\2\2\u0086\u039b\3\2\2\2\u0088\u03a6\3\2\2\2\u008a"+
		"\u03b1\3\2\2\2\u008c\u03b7\3\2\2\2\u008e\u03b9\3\2\2\2\u0090\u03c7\3\2"+
		"\2\2\u0092\u03cd\3\2\2\2\u0094\u03d4\3\2\2\2\u0096\u03dd\3\2\2\2\u0098"+
		"\u03e5\3\2\2\2\u009a\u03ed\3\2\2\2\u009c\u03f6\3\2\2\2\u009e\u040e\3\2"+
		"\2\2\u00a0\u0410\3\2\2\2\u00a2\u0418\3\2\2\2\u00a4\u0420\3\2\2\2\u00a6"+
		"\u0428\3\2\2\2\u00a8\u0431\3\2\2\2\u00aa\u043a\3\2\2\2\u00ac\u0446\3\2"+
		"\2\2\u00ae\u045c\3\2\2\2\u00b0\u0465\3\2\2\2\u00b2\u046f\3\2\2\2\u00b4"+
		"\u048c\3\2\2\2\u00b6\u04ac\3\2\2\2\u00b8\u04b5\3\2\2\2\u00ba\u04bb\3\2"+
		"\2\2\u00bc\u04bf\3\2\2\2\u00be\u04c1\3\2\2\2\u00c0\u04c3\3\2\2\2\u00c2"+
		"\u04c5\3\2\2\2\u00c4\u04c7\3\2\2\2\u00c6\u04c9\3\2\2\2\u00c8\u04cb\3\2"+
		"\2\2\u00ca\u04cd\3\2\2\2\u00cc\u04cf\3\2\2\2\u00ce\u04d8\3\2\2\2\u00d0"+
		"\u04e2\3\2\2\2\u00d2\u04e8\3\2\2\2\u00d4\u04f5\3\2\2\2\u00d6\u050e\3\2"+
		"\2\2\u00d8\u0510\3\2\2\2\u00da\u051d\3\2\2\2\u00dc\u052b\3\2\2\2\u00de"+
		"\u0554\3\2\2\2\u00e0\u055b\3\2\2\2\u00e2\u00e6\5\4\3\2\u00e3\u00e5\5\n"+
		"\6\2\u00e4\u00e3\3\2\2\2\u00e5\u00e8\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6"+
		"\u00e7\3\2\2\2\u00e7\3\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00eb\5\6\4\2"+
		"\u00ea\u00e9\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00ef\3\2\2\2\u00ec\u00ee"+
		"\5\b\5\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef"+
		"\u00f0\3\2\2\2\u00f0\5\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3\7\16\2"+
		"\2\u00f3\u00f8\5\u00b2Z\2\u00f4\u00f5\7\20\2\2\u00f5\u00f7\5\u00b2Z\2"+
		"\u00f6\u00f4\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9"+
		"\3\2\2\2\u00f9\u00fe\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fd\7\7\2\2\u00fc"+
		"\u00fb\3\2\2\2\u00fd\u0100\3\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2"+
		"\2\2\u00ff\7\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0102\7\17\2\2\u0102\u0107"+
		"\5\u00b2Z\2\u0103\u0104\7\20\2\2\u0104\u0106\5\u00b2Z\2\u0105\u0103\3"+
		"\2\2\2\u0106\u0109\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108"+
		"\u010e\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010b\7\20\2\2\u010b\u010f\7"+
		"\21\2\2\u010c\u010d\7\62\2\2\u010d\u010f\7o\2\2\u010e\u010a\3\2\2\2\u010e"+
		"\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0113\3\2\2\2\u0110\u0112\7\7"+
		"\2\2\u0111\u0110\3\2\2\2\u0112\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113"+
		"\u0114\3\2\2\2\u0114\t\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u011b\5.\30\2"+
		"\u0117\u011b\5t;\2\u0118\u011b\5f\64\2\u0119\u011b\5j\66\2\u011a\u0116"+
		"\3\2\2\2\u011a\u0117\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u0119\3\2\2\2\u011b"+
		"\13\3\2\2\2\u011c\u0124\5l\67\2\u011d\u0124\5j\66\2\u011e\u0124\5t;\2"+
		"\u011f\u0124\5.\30\2\u0120\u0124\5f\64\2\u0121\u0124\5|?\2\u0122\u0124"+
		"\5Z.\2\u0123\u011c\3\2\2\2\u0123\u011d\3\2\2\2\u0123\u011e\3\2\2\2\u0123"+
		"\u011f\3\2\2\2\u0123\u0120\3\2\2\2\u0123\u0121\3\2\2\2\u0123\u0122\3\2"+
		"\2\2\u0124\r\3\2\2\2\u0125\u0127\5@!\2\u0126\u0125\3\2\2\2\u0126\u0127"+
		"\3\2\2\2\u0127\17\3\2\2\2\u0128\u0129\7\23\2\2\u0129\u012e\5\22\n\2\u012a"+
		"\u012b\7\22\2\2\u012b\u012d\5\22\n\2\u012c\u012a\3\2\2\2\u012d\u0130\3"+
		"\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0131\3\2\2\2\u0130"+
		"\u012e\3\2\2\2\u0131\u0132\7\25\2\2\u0132\21\3\2\2\2\u0133\u0134\5\64"+
		"\33\2\u0134\u0137\7o\2\2\u0135\u0136\7\33\2\2\u0136\u0138\5\36\20\2\u0137"+
		"\u0135\3\2\2\2\u0137\u0138\3\2\2\2\u0138\23\3\2\2\2\u0139\u013b\7\23\2"+
		"\2\u013a\u013c\5\32\16\2\u013b\u013a\3\2\2\2\u013b\u013c\3\2\2\2\u013c"+
		"\u0141\3\2\2\2\u013d\u013e\7\22\2\2\u013e\u0140\5\32\16\2\u013f\u013d"+
		"\3\2\2\2\u0140\u0143\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142"+
		"\u0144\3\2\2\2\u0143\u0141\3\2\2\2\u0144\u0145\7\25\2\2\u0145\25\3\2\2"+
		"\2\u0146\u0147\7C\2\2\u0147\u014c\5\30\r\2\u0148\u0149\7\22\2\2\u0149"+
		"\u014b\5\30\r\2\u014a\u0148\3\2\2\2\u014b\u014e\3\2\2\2\u014c\u014a\3"+
		"\2\2\2\u014c\u014d\3\2\2\2\u014d\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014f"+
		"\u0146\3\2\2\2\u014f\u0150\3\2\2\2\u0150\27\3\2\2\2\u0151\u0153\5B\"\2"+
		"\u0152\u0151\3\2\2\2\u0152\u0153\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0155"+
		"\7o\2\2\u0155\u0156\7\33\2\2\u0156\u0157\5\32\16\2\u0157\31\3\2\2\2\u0158"+
		"\u015a\5B\"\2\u0159\u0158\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015b\3\2"+
		"\2\2\u015b\u015c\5\34\17\2\u015c\33\3\2\2\2\u015d\u015e\7\34\2\2\u015e"+
		"\u015f\5\34\17\2\u015f\u0161\7\35\2\2\u0160\u0162\7\36\2\2\u0161\u0160"+
		"\3\2\2\2\u0161\u0162\3\2\2\2\u0162\u0164\3\2\2\2\u0163\u0165\5(\25\2\u0164"+
		"\u0163\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0179\3\2\2\2\u0166\u0168\5*"+
		"\26\2\u0167\u0169\5(\25\2\u0168\u0167\3\2\2\2\u0168\u0169\3\2\2\2\u0169"+
		"\u0179\3\2\2\2\u016a\u016c\5\36\20\2\u016b\u016d\7\36\2\2\u016c\u016b"+
		"\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016f\3\2\2\2\u016e\u0170\5(\25\2\u016f"+
		"\u016e\3\2\2\2\u016f\u0170\3\2\2\2\u0170\u0179\3\2\2\2\u0171\u0173\7B"+
		"\2\2\u0172\u0174\7\36\2\2\u0173\u0172\3\2\2\2\u0173\u0174\3\2\2\2\u0174"+
		"\u0176\3\2\2\2\u0175\u0177\5(\25\2\u0176\u0175\3\2\2\2\u0176\u0177\3\2"+
		"\2\2\u0177\u0179\3\2\2\2\u0178\u015d\3\2\2\2\u0178\u0166\3\2\2\2\u0178"+
		"\u016a\3\2\2\2\u0178\u0171\3\2\2\2\u0179\35\3\2\2\2\u017a\u017b\7\16\2"+
		"\2\u017b\u017d\7\20\2\2\u017c\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d"+
		"\u017e\3\2\2\2\u017e\u0183\5\"\22\2\u017f\u0180\7\20\2\2\u0180\u0182\5"+
		"\"\22\2\u0181\u017f\3\2\2\2\u0182\u0185\3\2\2\2\u0183\u0181\3\2\2\2\u0183"+
		"\u0184\3\2\2\2\u0184\37\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u018b\7\21\2"+
		"\2\u0187\u0188\5\16\b\2\u0188\u0189\5\32\16\2\u0189\u018b\3\2\2\2\u018a"+
		"\u0186\3\2\2\2\u018a\u0187\3\2\2\2\u018b!\3\2\2\2\u018c\u0198\7o\2\2\u018d"+
		"\u018e\7\23\2\2\u018e\u0193\5 \21\2\u018f\u0190\7\22\2\2\u0190\u0192\5"+
		" \21\2\u0191\u018f\3\2\2\2\u0192\u0195\3\2\2\2\u0193\u0191\3\2\2\2\u0193"+
		"\u0194\3\2\2\2\u0194\u0196\3\2\2\2\u0195\u0193\3\2\2\2\u0196\u0197\7\25"+
		"\2\2\u0197\u0199\3\2\2\2\u0198\u018d\3\2\2\2\u0198\u0199\3\2\2\2\u0199"+
		"#\3\2\2\2\u019a\u019d\7o\2\2\u019b\u019c\7\33\2\2\u019c\u019e\5\32\16"+
		"\2\u019d\u019b\3\2\2\2\u019d\u019e\3\2\2\2\u019e%\3\2\2\2\u019f\u01a4"+
		"\5T+\2\u01a0\u01a1\5\64\33\2\u01a1\u01a2\5\32\16\2\u01a2\u01a4\3\2\2\2"+
		"\u01a3\u019f\3\2\2\2\u01a3\u01a0\3\2\2\2\u01a4\'\3\2\2\2\u01a5\u01a6\7"+
		"\20\2\2\u01a6\u01a7\5*\26\2\u01a7)\3\2\2\2\u01a8\u01b1\7\34\2\2\u01a9"+
		"\u01ae\5&\24\2\u01aa\u01ab\7\22\2\2\u01ab\u01ad\5&\24\2\u01ac\u01aa\3"+
		"\2\2\2\u01ad\u01b0\3\2\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01af\3\2\2\2\u01af"+
		"\u01b2\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b1\u01a9\3\2\2\2\u01b1\u01b2\3\2"+
		"\2\2\u01b2\u01b3\3\2\2\2\u01b3\u01b4\7\35\2\2\u01b4\u01b5\7)\2\2\u01b5"+
		"\u01b6\5\32\16\2\u01b6+\3\2\2\2\u01b7\u01b8\7\34\2\2\u01b8\u01bd\5$\23"+
		"\2\u01b9\u01ba\7\22\2\2\u01ba\u01bc\5$\23\2\u01bb\u01b9\3\2\2\2\u01bc"+
		"\u01bf\3\2\2\2\u01bd\u01bb\3\2\2\2\u01bd\u01be\3\2\2\2\u01be\u01c0\3\2"+
		"\2\2\u01bf\u01bd\3\2\2\2\u01c0\u01c1\7\35\2\2\u01c1-\3\2\2\2\u01c2\u01c3"+
		"\5\64\33\2\u01c3\u01c5\t\2\2\2\u01c4\u01c6\5\20\t\2\u01c5\u01c4\3\2\2"+
		"\2\u01c5\u01c6\3\2\2\2\u01c6\u01ca\3\2\2\2\u01c7\u01c8\5\32\16\2\u01c8"+
		"\u01c9\7\20\2\2\u01c9\u01cb\3\2\2\2\u01ca\u01c7\3\2\2\2\u01ca\u01cb\3"+
		"\2\2\2\u01cb\u01ce\3\2\2\2\u01cc\u01cf\5,\27\2\u01cd\u01cf\5$\23\2\u01ce"+
		"\u01cc\3\2\2\2\u01ce\u01cd\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01d9\5\26"+
		"\f\2\u01d1\u01d2\t\3\2\2\u01d2\u01d6\5\u0094K\2\u01d3\u01d5\7\7\2\2\u01d4"+
		"\u01d3\3\2\2\2\u01d5\u01d8\3\2\2\2\u01d6\u01d4\3\2\2\2\u01d6\u01d7\3\2"+
		"\2\2\u01d7\u01da\3\2\2\2\u01d8\u01d6\3\2\2\2\u01d9\u01d1\3\2\2\2\u01d9"+
		"\u01da\3\2\2\2\u01da\u01e7\3\2\2\2\u01db\u01dd\5\60\31\2\u01dc\u01db\3"+
		"\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01df\3\2\2\2\u01de\u01e0\5\62\32\2\u01df"+
		"\u01de\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e8\3\2\2\2\u01e1\u01e3\5\62"+
		"\32\2\u01e2\u01e1\3\2\2\2\u01e2\u01e3\3\2\2\2\u01e3\u01e5\3\2\2\2\u01e4"+
		"\u01e6\5\60\31\2\u01e5\u01e4\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01e8\3"+
		"\2\2\2\u01e7\u01dc\3\2\2\2\u01e7\u01e2\3\2\2\2\u01e8\u01ec\3\2\2\2\u01e9"+
		"\u01eb\7\7\2\2\u01ea\u01e9\3\2\2\2\u01eb\u01ee\3\2\2\2\u01ec\u01ea\3\2"+
		"\2\2\u01ec\u01ed\3\2\2\2\u01ed/\3\2\2\2\u01ee\u01ec\3\2\2\2\u01ef\u01f0"+
		"\5\64\33\2\u01f0\u01f1\7D\2\2\u01f1\u01fd\3\2\2\2\u01f2\u01f3\5\64\33"+
		"\2\u01f3\u01f4\7D\2\2\u01f4\u01f5\7\34\2\2\u01f5\u01f8\7\35\2\2\u01f6"+
		"\u01f7\7\33\2\2\u01f7\u01f9\5\32\16\2\u01f8\u01f6\3\2\2\2\u01f8\u01f9"+
		"\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fb\5x=\2\u01fb\u01fd\3\2\2\2\u01fc"+
		"\u01ef\3\2\2\2\u01fc\u01f2\3\2\2\2\u01fd\61\3\2\2\2\u01fe\u01ff\5\64\33"+
		"\2\u01ff\u0200\7E\2\2\u0200\u020d\3\2\2\2\u0201\u0202\5\64\33\2\u0202"+
		"\u0203\7E\2\2\u0203\u0204\7\34\2\2\u0204\u0207\5\64\33\2\u0205\u0208\7"+
		"o\2\2\u0206\u0208\5T+\2\u0207\u0205\3\2\2\2\u0207\u0206\3\2\2\2\u0208"+
		"\u0209\3\2\2\2\u0209\u020a\7\35\2\2\u020a\u020b\5x=\2\u020b\u020d\3\2"+
		"\2\2\u020c\u01fe\3\2\2\2\u020c\u0201\3\2\2\2\u020d\63\3\2\2\2\u020e\u0210"+
		"\5\66\34\2\u020f\u020e\3\2\2\2\u0210\u0213\3\2\2\2\u0211\u020f\3\2\2\2"+
		"\u0211\u0212\3\2\2\2\u0212\65\3\2\2\2\u0213\u0211\3\2\2\2\u0214\u0215"+
		"\58\35\2\u0215\67\3\2\2\2\u0216\u021c\5:\36\2\u0217\u021c\5> \2\u0218"+
		"\u021c\5@!\2\u0219\u021c\5B\"\2\u021a\u021c\5<\37\2\u021b\u0216\3\2\2"+
		"\2\u021b\u0217\3\2\2\2\u021b\u0218\3\2\2\2\u021b\u0219\3\2\2\2\u021b\u021a"+
		"\3\2\2\2\u021c9\3\2\2\2\u021d\u021e\t\4\2\2\u021e;\3\2\2\2\u021f\u0220"+
		"\t\5\2\2\u0220=\3\2\2\2\u0221\u0222\t\6\2\2\u0222?\3\2\2\2\u0223\u0224"+
		"\t\7\2\2\u0224A\3\2\2\2\u0225\u0227\5D#\2\u0226\u0225\3\2\2\2\u0227\u0228"+
		"\3\2\2\2\u0228\u0226\3\2\2\2\u0228\u0229\3\2\2\2\u0229C\3\2\2\2\u022a"+
		"\u022e\7R\2\2\u022b\u022c\5F$\2\u022c\u022d\7\33\2\2\u022d\u022f\3\2\2"+
		"\2\u022e\u022b\3\2\2\2\u022e\u022f\3\2\2\2\u022f\u0230\3\2\2\2\u0230\u0231"+
		"\5L\'\2\u0231E\3\2\2\2\u0232\u0233\t\b\2\2\u0233G\3\2\2\2\u0234\u0235"+
		"\5\u00b2Z\2\u0235\u0236\7\27\2\2\u0236\u0238\3\2\2\2\u0237\u0234\3\2\2"+
		"\2\u0237\u0238\3\2\2\2\u0238\u023a\3\2\2\2\u0239\u023b\7\21\2\2\u023a"+
		"\u0239\3\2\2\2\u023a\u023b\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u023d\5\u0094"+
		"K\2\u023dI\3\2\2\2\u023e\u0247\7\34\2\2\u023f\u0244\5H%\2\u0240\u0241"+
		"\7\22\2\2\u0241\u0243\5H%\2\u0242\u0240\3\2\2\2\u0243\u0246\3\2\2\2\u0244"+
		"\u0242\3\2\2\2\u0244\u0245\3\2\2\2\u0245\u0248\3\2\2\2\u0246\u0244\3\2"+
		"\2\2\u0247\u023f\3\2\2\2\u0247\u0248\3\2\2\2\u0248\u0249\3\2\2\2\u0249"+
		"\u024a\7\35\2\2\u024aK\3\2\2\2\u024b\u0250\5\u00b2Z\2\u024c\u024d\7\20"+
		"\2\2\u024d\u024f\5\u00b2Z\2\u024e\u024c\3\2\2\2\u024f\u0252\3\2\2\2\u0250"+
		"\u024e\3\2\2\2\u0250\u0251\3\2\2\2\u0251\u0254\3\2\2\2\u0252\u0250\3\2"+
		"\2\2\u0253\u0255\5\24\13\2\u0254\u0253\3\2\2\2\u0254\u0255\3\2\2\2\u0255"+
		"\u0257\3\2\2\2\u0256\u0258\5J&\2\u0257\u0256\3\2\2\2\u0257\u0258\3\2\2"+
		"\2\u0258M\3\2\2\2\u0259\u025a\7X\2\2\u025a\u026b\5\u0094K\2\u025b\u025d"+
		"\7Z\2\2\u025c\u025e\5P)\2\u025d\u025c\3\2\2\2\u025d\u025e\3\2\2\2\u025e"+
		"\u0260\3\2\2\2\u025f\u0261\5\u0094K\2\u0260\u025f\3\2\2\2\u0260\u0261"+
		"\3\2\2\2\u0261\u026b\3\2\2\2\u0262\u0264\7Y\2\2\u0263\u0265\5P)\2\u0264"+
		"\u0263\3\2\2\2\u0264\u0265\3\2\2\2\u0265\u026b\3\2\2\2\u0266\u0268\7["+
		"\2\2\u0267\u0269\5P)\2\u0268\u0267\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026b"+
		"\3\2\2\2\u026a\u0259\3\2\2\2\u026a\u025b\3\2\2\2\u026a\u0262\3\2\2\2\u026a"+
		"\u0266\3\2\2\2\u026bO\3\2\2\2\u026c\u0271\7R\2\2\u026d\u0272\5\u00b2Z"+
		"\2\u026e\u0272\7i\2\2\u026f\u0272\7h\2\2\u0270\u0272\7g\2\2\u0271\u026d"+
		"\3\2\2\2\u0271\u026e\3\2\2\2\u0271\u026f\3\2\2\2\u0271\u0270\3\2\2\2\u0272"+
		"Q\3\2\2\2\u0273\u0274\5\u00b2Z\2\u0274\u0275\7R\2\2\u0275S\3\2\2\2\u0276"+
		"\u0277\7o\2\2\u0277\u0278\7\33\2\2\u0278\u0279\5\32\16\2\u0279U\3\2\2"+
		"\2\u027a\u027c\5\64\33\2\u027b\u027d\t\2\2\2\u027c\u027b\3\2\2\2\u027c"+
		"\u027d\3\2\2\2\u027d\u027e\3\2\2\2\u027e\u0281\5T+\2\u027f\u0280\7\27"+
		"\2\2\u0280\u0282\5\u0094K\2\u0281\u027f\3\2\2\2\u0281\u0282\3\2\2\2\u0282"+
		"W\3\2\2\2\u0283\u0284\5\64\33\2\u0284\u0285\7\\\2\2\u0285\u0287\3\2\2"+
		"\2\u0286\u0283\3\2\2\2\u0286\u0287\3\2\2\2\u0287\u0288\3\2\2\2\u0288\u0291"+
		"\7\34\2\2\u0289\u028e\5V,\2\u028a\u028b\7\22\2\2\u028b\u028d\5V,\2\u028c"+
		"\u028a\3\2\2\2\u028d\u0290\3\2\2\2\u028e\u028c\3\2\2\2\u028e\u028f\3\2"+
		"\2\2\u028f\u0292\3\2\2\2\u0290\u028e\3\2\2\2\u0291\u0289\3\2\2\2\u0291"+
		"\u0292\3\2\2\2\u0292\u0293\3\2\2\2\u0293\u0294\7\35\2\2\u0294Y\3\2\2\2"+
		"\u0295\u0296\5\64\33\2\u0296\u0297\7\\\2\2\u0297\u029a\5r:\2\u0298\u0299"+
		"\7\33\2\2\u0299\u029b\5\\/\2\u029a\u0298\3\2\2\2\u029a\u029b\3\2\2\2\u029b"+
		"\u029c\3\2\2\2\u029c\u02a0\5z>\2\u029d\u029f\7\7\2\2\u029e\u029d\3\2\2"+
		"\2\u029f\u02a2\3\2\2\2\u02a0\u029e\3\2\2\2\u02a0\u02a1\3\2\2\2\u02a1["+
		"\3\2\2\2\u02a2\u02a0\3\2\2\2\u02a3\u02a4\7]\2\2\u02a4\u02a8\5J&\2\u02a5"+
		"\u02a6\7^\2\2\u02a6\u02a8\5J&\2\u02a7\u02a3\3\2\2\2\u02a7\u02a5\3\2\2"+
		"\2\u02a8]\3\2\2\2\u02a9\u02ab\5\24\13\2\u02aa\u02a9\3\2\2\2\u02aa\u02ab"+
		"\3\2\2\2\u02ab\u02ac\3\2\2\2\u02ac\u02ae\5J&\2\u02ad\u02af\5\u00d2j\2"+
		"\u02ae\u02ad\3\2\2\2\u02ae\u02af\3\2\2\2\u02af\u02b5\3\2\2\2\u02b0\u02b1"+
		"\5\24\13\2\u02b1\u02b2\5\u00d2j\2\u02b2\u02b5\3\2\2\2\u02b3\u02b5\5\u00d2"+
		"j\2\u02b4\u02aa\3\2\2\2\u02b4\u02b0\3\2\2\2\u02b4\u02b3\3\2\2\2\u02b5"+
		"_\3\2\2\2\u02b6\u02b8\5\36\20\2\u02b7\u02b9\5\24\13\2\u02b8\u02b7\3\2"+
		"\2\2\u02b8\u02b9\3\2\2\2\u02b9\u02ba\3\2\2\2\u02ba\u02bb\5J&\2\u02bba"+
		"\3\2\2\2\u02bc\u02bd\5\36\20\2\u02bd\u02be\7A\2\2\u02be\u02bf\5\u0094"+
		"K\2\u02bfc\3\2\2\2\u02c0\u02c4\5`\61\2\u02c1\u02c4\5\36\20\2\u02c2\u02c4"+
		"\5b\62\2\u02c3\u02c0\3\2\2\2\u02c3\u02c1\3\2\2\2\u02c3\u02c2\3\2\2\2\u02c4"+
		"e\3\2\2\2\u02c5\u02c6\5\64\33\2\u02c6\u02c7\t\t\2\2\u02c7\u02c9\7o\2\2"+
		"\u02c8\u02ca\5\20\t\2\u02c9\u02c8\3\2\2\2\u02c9\u02ca\3\2\2\2\u02ca\u02cc"+
		"\3\2\2\2\u02cb\u02cd\5X-\2\u02cc\u02cb\3\2\2\2\u02cc\u02cd\3\2\2\2\u02cd"+
		"\u02da\3\2\2\2\u02ce\u02d0\7\33\2\2\u02cf\u02d1\5B\"\2\u02d0\u02cf\3\2"+
		"\2\2\u02d0\u02d1\3\2\2\2\u02d1\u02d2\3\2\2\2\u02d2\u02d7\5d\63\2\u02d3"+
		"\u02d4\7\22\2\2\u02d4\u02d6\5d\63\2\u02d5\u02d3\3\2\2\2\u02d6\u02d9\3"+
		"\2\2\2\u02d7\u02d5\3\2\2\2\u02d7\u02d8\3\2\2\2\u02d8\u02db\3\2\2\2\u02d9"+
		"\u02d7\3\2\2\2\u02da\u02ce\3\2\2\2\u02da\u02db\3\2\2\2\u02db\u02dc\3\2"+
		"\2\2\u02dc\u02df\5\26\f\2\u02dd\u02e0\5n8\2\u02de\u02e0\5~@\2\u02df\u02dd"+
		"\3\2\2\2\u02df\u02de\3\2\2\2\u02e0\u02e4\3\2\2\2\u02e1\u02e3\7\7\2\2\u02e2"+
		"\u02e1\3\2\2\2\u02e3\u02e6\3\2\2\2\u02e4\u02e2\3\2\2\2\u02e4\u02e5\3\2"+
		"\2\2\u02e5g\3\2\2\2\u02e6\u02e4\3\2\2\2\u02e7\u02e8\7\33\2\2\u02e8\u02ed"+
		"\5d\63\2\u02e9\u02ea\7\22\2\2\u02ea\u02ec\5d\63\2\u02eb\u02e9\3\2\2\2"+
		"\u02ec\u02ef\3\2\2\2\u02ed\u02eb\3\2\2\2\u02ed\u02ee\3\2\2\2\u02eei\3"+
		"\2\2\2\u02ef\u02ed\3\2\2\2\u02f0\u02f1\7a\2\2\u02f1\u02f3\7o\2\2\u02f2"+
		"\u02f4\5X-\2\u02f3\u02f2\3\2\2\2\u02f3\u02f4\3\2\2\2\u02f4\u02f6\3\2\2"+
		"\2\u02f5\u02f7\5h\65\2\u02f6\u02f5\3\2\2\2\u02f6\u02f7\3\2\2\2\u02f7\u02f8"+
		"\3\2\2\2\u02f8\u02fc\5n8\2\u02f9\u02fb\7\7\2\2\u02fa\u02f9\3\2\2\2\u02fb"+
		"\u02fe\3\2\2\2\u02fc\u02fa\3\2\2\2\u02fc\u02fd\3\2\2\2\u02fdk\3\2\2\2"+
		"\u02fe\u02fc\3\2\2\2\u02ff\u0300\5\64\33\2\u0300\u0301\7b\2\2\u0301\u0303"+
		"\7a\2\2\u0302\u0304\5h\65\2\u0303\u0302\3\2\2\2\u0303\u0304\3\2\2\2\u0304"+
		"\u0305\3\2\2\2\u0305\u0309\5n8\2\u0306\u0308\7\7\2\2\u0307\u0306\3\2\2"+
		"\2\u0308\u030b\3\2\2\2\u0309\u0307\3\2\2\2\u0309\u030a\3\2\2\2\u030am"+
		"\3\2\2\2\u030b\u0309\3\2\2\2\u030c\u030d\7\b\2\2\u030d\u030e\5p9\2\u030e"+
		"\u030f\7\t\2\2\u030f\u0311\3\2\2\2\u0310\u030c\3\2\2\2\u0310\u0311\3\2"+
		"\2\2\u0311o\3\2\2\2\u0312\u0314\5\f\7\2\u0313\u0312\3\2\2\2\u0314\u0317"+
		"\3\2\2\2\u0315\u0313\3\2\2\2\u0315\u0316\3\2\2\2\u0316q\3\2\2\2\u0317"+
		"\u0315\3\2\2\2\u0318\u0321\7\34\2\2\u0319\u031e\5V,\2\u031a\u031b\7\22"+
		"\2\2\u031b\u031d\5V,\2\u031c\u031a\3\2\2\2\u031d\u0320\3\2\2\2\u031e\u031c"+
		"\3\2\2\2\u031e\u031f\3\2\2\2\u031f\u0322\3\2\2\2\u0320\u031e\3\2\2\2\u0321"+
		"\u0319\3\2\2\2\u0321\u0322\3\2\2\2\u0322\u0323\3\2\2\2\u0323\u0324\7\35"+
		"\2\2\u0324s\3\2\2\2\u0325\u0326\5\64\33\2\u0326\u0328\7c\2\2\u0327\u0329"+
		"\5\20\t\2\u0328\u0327\3\2\2\2\u0328\u0329\3\2\2\2\u0329\u032e\3\2\2\2"+
		"\u032a\u032b\5\32\16\2\u032b\u032c\7\20\2\2\u032c\u032f\3\2\2\2\u032d"+
		"\u032f\5B\"\2\u032e\u032a\3\2\2\2\u032e\u032d\3\2\2\2\u032e\u032f\3\2"+
		"\2\2\u032f\u0330\3\2\2\2\u0330\u0332\7o\2\2\u0331\u0333\5\20\t\2\u0332"+
		"\u0331\3\2\2\2\u0332\u0333\3\2\2\2\u0333\u0334\3\2\2\2\u0334\u0337\5r"+
		":\2\u0335\u0336\7\33\2\2\u0336\u0338\5\32\16\2\u0337\u0335\3\2\2\2\u0337"+
		"\u0338\3\2\2\2\u0338\u0339\3\2\2\2\u0339\u033b\5\26\f\2\u033a\u033c\5"+
		"x=\2\u033b\u033a\3\2\2\2\u033b\u033c\3\2\2\2\u033c\u0340\3\2\2\2\u033d"+
		"\u033f\7\7\2\2\u033e\u033d\3\2\2\2\u033f\u0342\3\2\2\2\u0340\u033e\3\2"+
		"\2\2\u0340\u0341\3\2\2\2\u0341u\3\2\2\2\u0342\u0340\3\2\2\2\u0343\u0345"+
		"\7\7\2\2\u0344\u0343\3\2\2\2\u0345\u0348\3\2\2\2\u0346\u0344\3\2\2\2\u0346"+
		"\u0347\3\2\2\2\u0347\u0356\3\2\2\2\u0348\u0346\3\2\2\2\u0349\u0353\5\u00bc"+
		"_\2\u034a\u034c\7\7\2\2\u034b\u034a\3\2\2\2\u034c\u034f\3\2\2\2\u034d"+
		"\u034b\3\2\2\2\u034d\u034e\3\2\2\2\u034e\u0350\3\2\2\2\u034f\u034d\3\2"+
		"\2\2\u0350\u0352\5\u00bc_\2\u0351\u034d\3\2\2\2\u0352\u0355\3\2\2\2\u0353"+
		"\u0351\3\2\2\2\u0353\u0354\3\2\2\2\u0354\u0357\3\2\2\2\u0355\u0353\3\2"+
		"\2\2\u0356\u0349\3\2\2\2\u0356\u0357\3\2\2\2\u0357\u035b\3\2\2\2\u0358"+
		"\u035a\7\7\2\2\u0359\u0358\3\2\2\2\u035a\u035d\3\2\2\2\u035b\u0359\3\2"+
		"\2\2\u035b\u035c\3\2\2\2\u035cw\3\2\2\2\u035d\u035b\3\2\2\2\u035e\u0362"+
		"\5z>\2\u035f\u0360\7\27\2\2\u0360\u0362\5\u0094K\2\u0361\u035e\3\2\2\2"+
		"\u0361\u035f\3\2\2\2\u0362y\3\2\2\2\u0363\u0364\7\b\2\2\u0364\u0365\5"+
		"v<\2\u0365\u0366\7\t\2\2\u0366{\3\2\2\2\u0367\u0368\7d\2\2\u0368\u036c"+
		"\5z>\2\u0369\u036b\7\7\2\2\u036a\u0369\3\2\2\2\u036b\u036e\3\2\2\2\u036c"+
		"\u036a\3\2\2\2\u036c\u036d\3\2\2\2\u036d}\3\2\2\2\u036e\u036c\3\2\2\2"+
		"\u036f\u0370\7\b\2\2\u0370\u0373\5\u0080A\2\u0371\u0372\7\7\2\2\u0372"+
		"\u0374\5p9\2\u0373\u0371\3\2\2\2\u0373\u0374\3\2\2\2\u0374\u0375\3\2\2"+
		"\2\u0375\u0376\7\t\2\2\u0376\177\3\2\2\2\u0377\u0379\5\u0082B\2\u0378"+
		"\u037a\7\22\2\2\u0379\u0378\3\2\2\2\u0379\u037a\3\2\2\2\u037a\u037c\3"+
		"\2\2\2\u037b\u0377\3\2\2\2\u037c\u037f\3\2\2\2\u037d\u037b\3\2\2\2\u037d"+
		"\u037e\3\2\2\2\u037e\u0081\3\2\2\2\u037f\u037d\3\2\2\2\u0380\u0381\5\64"+
		"\33\2\u0381\u0388\7o\2\2\u0382\u0384\7\33\2\2\u0383\u0385\5B\"\2\u0384"+
		"\u0383\3\2\2\2\u0384\u0385\3\2\2\2\u0385\u0386\3\2\2\2\u0386\u0389\5`"+
		"\61\2\u0387\u0389\5J&\2\u0388\u0382\3\2\2\2\u0388\u0387\3\2\2\2\u0388"+
		"\u0389\3\2\2\2\u0389\u038a\3\2\2\2\u038a\u038b\5n8\2\u038b\u0083\3\2\2"+
		"\2\u038c\u038d\7e\2\2\u038d\u038e\7\34\2\2\u038e\u038f\5\u0094K\2\u038f"+
		"\u0390\7\35\2\2\u0390\u0394\5\u0094K\2\u0391\u0393\7\7\2\2\u0392\u0391"+
		"\3\2\2\2\u0393\u0396\3\2\2\2\u0394\u0392\3\2\2\2\u0394\u0395\3\2\2\2\u0395"+
		"\u0399\3\2\2\2\u0396\u0394\3\2\2\2\u0397\u0398\7f\2\2\u0398\u039a\5\u0094"+
		"K\2\u0399\u0397\3\2\2\2\u0399\u039a\3\2\2\2\u039a\u0085\3\2\2\2\u039b"+
		"\u039c\7k\2\2\u039c\u03a0\5z>\2\u039d\u039f\5\u0088E\2\u039e\u039d\3\2"+
		"\2\2\u039f\u03a2\3\2\2\2\u03a0\u039e\3\2\2\2\u03a0\u03a1\3\2\2\2\u03a1"+
		"\u03a4\3\2\2\2\u03a2\u03a0\3\2\2\2\u03a3\u03a5\5\u008aF\2\u03a4\u03a3"+
		"\3\2\2\2\u03a4\u03a5\3\2\2\2\u03a5\u0087\3\2\2\2\u03a6\u03a7\7l\2\2\u03a7"+
		"\u03a9\7\34\2\2\u03a8\u03aa\5B\"\2\u03a9\u03a8\3\2\2\2\u03a9\u03aa\3\2"+
		"\2\2\u03aa\u03ab\3\2\2\2\u03ab\u03ac\7o\2\2\u03ac\u03ad\7\33\2\2\u03ad"+
		"\u03ae\5\36\20\2\u03ae\u03af\7\35\2\2\u03af\u03b0\5z>\2\u03b0\u0089\3"+
		"\2\2\2\u03b1\u03b2\7m\2\2\u03b2\u03b3\5z>\2\u03b3\u008b\3\2\2\2\u03b4"+
		"\u03b8\5\u008eH\2\u03b5\u03b8\5\u0090I\2\u03b6\u03b8\5\u0092J\2\u03b7"+
		"\u03b4\3\2\2\2\u03b7\u03b5\3\2\2\2\u03b7\u03b6\3\2\2\2\u03b8\u008d\3\2"+
		"\2\2\u03b9\u03ba\7i\2\2\u03ba\u03bc\7\34\2\2\u03bb\u03bd\5B\"\2\u03bc"+
		"\u03bb\3\2\2\2\u03bc\u03bd\3\2\2\2\u03bd\u03c0\3\2\2\2\u03be\u03c1\5,"+
		"\27\2\u03bf\u03c1\5$\23\2\u03c0\u03be\3\2\2\2\u03c0\u03bf\3\2\2\2\u03c1"+
		"\u03c2\3\2\2\2\u03c2\u03c3\7.\2\2\u03c3\u03c4\5\u0094K\2\u03c4\u03c5\7"+
		"\35\2\2\u03c5\u03c6\5\u0094K\2\u03c6\u008f\3\2\2\2\u03c7\u03c8\7h\2\2"+
		"\u03c8\u03c9\7\34\2\2\u03c9\u03ca\5\u0094K\2\u03ca\u03cb\7\35\2\2\u03cb"+
		"\u03cc\5\u0094K\2\u03cc\u0091\3\2\2\2\u03cd\u03ce\7j\2\2\u03ce\u03cf\5"+
		"\u0094K\2\u03cf\u03d0\7h\2\2\u03d0\u03d1\7\34\2\2\u03d1\u03d2\5\u0094"+
		"K\2\u03d2\u03d3\7\35\2\2\u03d3\u0093\3\2\2\2\u03d4\u03da\5\u0096L\2\u03d5"+
		"\u03d6\5\u00ccg\2\u03d6\u03d7\5\u0096L\2\u03d7\u03d9\3\2\2\2\u03d8\u03d5"+
		"\3\2\2\2\u03d9\u03dc\3\2\2\2\u03da\u03d8\3\2\2\2\u03da\u03db\3\2\2\2\u03db"+
		"\u0095\3\2\2\2\u03dc\u03da\3\2\2\2\u03dd\u03e2\5\u0098M\2\u03de\u03df"+
		"\7 \2\2\u03df\u03e1\5\u0098M\2\u03e0\u03de\3\2\2\2\u03e1\u03e4\3\2\2\2"+
		"\u03e2\u03e0\3\2\2\2\u03e2\u03e3\3\2\2\2\u03e3\u0097\3\2\2\2\u03e4\u03e2"+
		"\3\2\2\2\u03e5\u03ea\5\u009aN\2\u03e6\u03e7\7!\2\2\u03e7\u03e9\5\u009a"+
		"N\2\u03e8\u03e6\3\2\2\2\u03e9\u03ec\3\2\2\2\u03ea\u03e8\3\2\2\2\u03ea"+
		"\u03eb\3\2\2\2\u03eb\u0099\3\2\2\2\u03ec\u03ea\3\2\2\2\u03ed\u03f3\5\u009c"+
		"O\2\u03ee\u03ef\5\u00caf\2\u03ef\u03f0\5\u009cO\2\u03f0\u03f2\3\2\2\2"+
		"\u03f1\u03ee\3\2\2\2\u03f2\u03f5\3\2\2\2\u03f3\u03f1\3\2\2\2\u03f3\u03f4"+
		"\3\2\2\2\u03f4\u009b\3\2\2\2\u03f5\u03f3\3\2\2\2\u03f6\u03fc\5\u009eP"+
		"\2\u03f7\u03f8\5\u00c8e\2\u03f8\u03f9\5\u009eP\2\u03f9\u03fb\3\2\2\2\u03fa"+
		"\u03f7\3\2\2\2\u03fb\u03fe\3\2\2\2\u03fc\u03fa\3\2\2\2\u03fc\u03fd\3\2"+
		"\2\2\u03fd\u009d\3\2\2\2\u03fe\u03fc\3\2\2\2\u03ff\u0405\5\u00a0Q\2\u0400"+
		"\u0401\5\u00c2b\2\u0401\u0402\5\u00a0Q\2\u0402\u0404\3\2\2\2\u0403\u0400"+
		"\3\2\2\2\u0404\u0407\3\2\2\2\u0405\u0403\3\2\2\2\u0405\u0406\3\2\2\2\u0406"+
		"\u040f\3\2\2\2\u0407\u0405\3\2\2\2\u0408\u040c\5\u00a0Q\2\u0409\u040a"+
		"\5\u00c6d\2\u040a\u040b\5\32\16\2\u040b\u040d\3\2\2\2\u040c\u0409\3\2"+
		"\2\2\u040c\u040d\3\2\2\2\u040d\u040f\3\2\2\2\u040e\u03ff\3\2\2\2\u040e"+
		"\u0408\3\2\2\2\u040f\u009f\3\2\2\2\u0410\u0415\5\u00a2R\2\u0411\u0412"+
		"\7\"\2\2\u0412\u0414\5\u00a2R\2\u0413\u0411\3\2\2\2\u0414\u0417\3\2\2"+
		"\2\u0415\u0413\3\2\2\2\u0415\u0416\3\2\2\2\u0416\u00a1\3\2\2\2\u0417\u0415"+
		"\3\2\2\2\u0418\u041d\5\u00a4S\2\u0419\u041a\7o\2\2\u041a\u041c\5\u00a4"+
		"S\2\u041b\u0419\3\2\2\2\u041c\u041f\3\2\2\2\u041d\u041b\3\2\2\2\u041d"+
		"\u041e\3\2\2\2\u041e\u00a3\3\2\2\2\u041f\u041d\3\2\2\2\u0420\u0425\5\u00a6"+
		"T\2\u0421\u0422\7$\2\2\u0422\u0424\5\u00a6T\2\u0423\u0421\3\2\2\2\u0424"+
		"\u0427\3\2\2\2\u0425\u0423\3\2\2\2\u0425\u0426\3\2\2\2\u0426\u00a5\3\2"+
		"\2\2\u0427\u0425\3\2\2\2\u0428\u042e\5\u00a8U\2\u0429\u042a\5\u00c0a\2"+
		"\u042a\u042b\5\u00a8U\2\u042b\u042d\3\2\2\2\u042c\u0429\3\2\2\2\u042d"+
		"\u0430\3\2\2\2\u042e\u042c\3\2\2\2\u042e\u042f\3\2\2\2\u042f\u00a7\3\2"+
		"\2\2\u0430\u042e\3\2\2\2\u0431\u0437\5\u00aaV\2\u0432\u0433\5\u00be`\2"+
		"\u0433\u0434\5\u00aaV\2\u0434\u0436\3\2\2\2\u0435\u0432\3\2\2\2\u0436"+
		"\u0439\3\2\2\2\u0437\u0435\3\2\2\2\u0437\u0438\3\2\2\2\u0438\u00a9\3\2"+
		"\2\2\u0439\u0437\3\2\2\2\u043a\u0440\5\u00acW\2\u043b\u043c\5\u00c4c\2"+
		"\u043c\u043d\5\u00acW\2\u043d\u043f\3\2\2\2\u043e\u043b\3\2\2\2\u043f"+
		"\u0442\3\2\2\2\u0440\u043e\3\2\2\2\u0440\u0441\3\2\2\2\u0441\u00ab\3\2"+
		"\2\2\u0442\u0440\3\2\2\2\u0443\u0445\5\u00ceh\2\u0444\u0443\3\2\2\2\u0445"+
		"\u0448\3\2\2\2\u0446\u0444\3\2\2\2\u0446\u0447\3\2\2\2\u0447\u0449\3\2"+
		"\2\2\u0448\u0446\3\2\2\2\u0449\u044a\5\u00aeX\2\u044a\u00ad\3\2\2\2\u044b"+
		"\u044c\5\u00b6\\\2\u044c\u044d\5^\60\2\u044d\u045d\3\2\2\2\u044e\u0452"+
		"\5\u00b6\\\2\u044f\u0451\5\u00d0i\2\u0450\u044f\3\2\2\2\u0451\u0454\3"+
		"\2\2\2\u0452\u0450\3\2\2\2\u0452\u0453\3\2\2\2\u0453\u045d\3\2\2\2\u0454"+
		"\u0452\3\2\2\2\u0455\u0459\5\u00b0Y\2\u0456\u0458\5\u00d0i\2\u0457\u0456"+
		"\3\2\2\2\u0458\u045b\3\2\2\2\u0459\u0457\3\2\2\2\u0459\u045a\3\2\2\2\u045a"+
		"\u045d\3\2\2\2\u045b\u0459\3\2\2\2\u045c\u044b\3\2\2\2\u045c\u044e\3\2"+
		"\2\2\u045c\u0455\3\2\2\2\u045d\u00af\3\2\2\2\u045e\u0462\5\36\20\2\u045f"+
		"\u0461\7\36\2\2\u0460\u045f\3\2\2\2\u0461\u0464\3\2\2\2\u0462\u0460\3"+
		"\2\2\2\u0462\u0463\3\2\2\2\u0463\u0466\3\2\2\2\u0464\u0462\3\2\2\2\u0465"+
		"\u045e\3\2\2\2\u0465\u0466\3\2\2\2\u0466\u0467\3\2\2\2\u0467\u046a\7%"+
		"\2\2\u0468\u046b\5\u00b2Z\2\u0469\u046b\7_\2\2\u046a\u0468\3\2\2\2\u046a"+
		"\u0469\3\2\2\2\u046b\u046d\3\2\2\2\u046c\u046e\5\24\13\2\u046d\u046c\3"+
		"\2\2\2\u046d\u046e\3\2\2\2\u046e\u00b1\3\2\2\2\u046f\u0470\t\n\2\2\u0470"+
		"\u00b3\3\2\2\2\u0471\u047b\7\r\2\2\u0472\u047a\7s\2\2\u0473\u0474\7t\2"+
		"\2\u0474\u0475\5\u0094K\2\u0475\u0476\7\t\2\2\u0476\u047a\3\2\2\2\u0477"+
		"\u047a\7u\2\2\u0478\u047a\n\13\2\2\u0479\u0472\3\2\2\2\u0479\u0473\3\2"+
		"\2\2\u0479\u0477\3\2\2\2\u0479\u0478\3\2\2\2\u047a\u047d\3\2\2\2\u047b"+
		"\u0479\3\2\2\2\u047b\u047c\3\2\2\2\u047c\u047e\3\2\2\2\u047d\u047b\3\2"+
		"\2\2\u047e\u048d\7r\2\2\u047f\u0488\7\f\2\2\u0480\u0481\7y\2\2\u0481\u0482"+
		"\5\u0094K\2\u0482\u0483\7\t\2\2\u0483\u0487\3\2\2\2\u0484\u0487\7z\2\2"+
		"\u0485\u0487\n\f\2\2\u0486\u0480\3\2\2\2\u0486\u0484\3\2\2\2\u0486\u0485"+
		"\3\2\2\2\u0487\u048a\3\2\2\2\u0488\u0486\3\2\2\2\u0488\u0489\3\2\2\2\u0489"+
		"\u048b\3\2\2\2\u048a\u0488\3\2\2\2\u048b\u048d\7w\2\2\u048c\u0471\3\2"+
		"\2\2\u048c\u047f\3\2\2\2\u048d\u00b5\3\2\2\2\u048e\u048f\7\34\2\2\u048f"+
		"\u0490\5\u0094K\2\u0490\u0491\7\35\2\2\u0491\u04ad\3\2\2\2\u0492\u04ad"+
		"\5\u00b8]\2\u0493\u04ad\5\u00d6l\2\u0494\u0496\7]\2\2\u0495\u0497\5P)"+
		"\2\u0496\u0495\3\2\2\2\u0496\u0497\3\2\2\2\u0497\u04ad\3\2\2\2\u0498\u049d"+
		"\7^\2\2\u0499\u049a\7\23\2\2\u049a\u049b\5\32\16\2\u049b\u049c\7\25\2"+
		"\2\u049c\u049e\3\2\2\2\u049d\u0499\3\2\2\2\u049d\u049e\3\2\2\2\u049e\u04a0"+
		"\3\2\2\2\u049f\u04a1\5P)\2\u04a0\u049f\3\2\2\2\u04a0\u04a1\3\2\2\2\u04a1"+
		"\u04ad\3\2\2\2\u04a2\u04ad\5\u0084C\2\u04a3\u04ad\5\u00dco\2\u04a4\u04ad"+
		"\5\u0086D\2\u04a5\u04ad\5\u00dan\2\u04a6\u04ad\5N(\2\u04a7\u04ad\5\u008c"+
		"G\2\u04a8\u04ad\5\u00b2Z\2\u04a9\u04aa\7n\2\2\u04aa\u04ad\5\u00b2Z\2\u04ab"+
		"\u04ad\7\16\2\2\u04ac\u048e\3\2\2\2\u04ac\u0492\3\2\2\2\u04ac\u0493\3"+
		"\2\2\2\u04ac\u0494\3\2\2\2\u04ac\u0498\3\2\2\2\u04ac\u04a2\3\2\2\2\u04ac"+
		"\u04a3\3\2\2\2\u04ac\u04a4\3\2\2\2\u04ac\u04a5\3\2\2\2\u04ac\u04a6\3\2"+
		"\2\2\u04ac\u04a7\3\2\2\2\u04ac\u04a8\3\2\2\2\u04ac\u04a9\3\2\2\2\u04ac"+
		"\u04ab\3\2\2\2\u04ad\u00b7\3\2\2\2\u04ae\u04b6\7&\2\2\u04af\u04b6\7\'"+
		"\2\2\u04b0\u04b6\5\u00b4[\2\u04b1\u04b6\7\6\2\2\u04b2\u04b6\7\n\2\2\u04b3"+
		"\u04b6\7\13\2\2\u04b4\u04b6\7(\2\2\u04b5\u04ae\3\2\2\2\u04b5\u04af\3\2"+
		"\2\2\u04b5\u04b0\3\2\2\2\u04b5\u04b1\3\2\2\2\u04b5\u04b2\3\2\2\2\u04b5"+
		"\u04b3\3\2\2\2\u04b5\u04b4\3\2\2\2\u04b6\u00b9\3\2\2\2\u04b7\u04bc\5t"+
		";\2\u04b8\u04bc\5.\30\2\u04b9\u04bc\5f\64\2\u04ba\u04bc\5j\66\2\u04bb"+
		"\u04b7\3\2\2\2\u04bb\u04b8\3\2\2\2\u04bb\u04b9\3\2\2\2\u04bb\u04ba\3\2"+
		"\2\2\u04bc\u00bb\3\2\2\2\u04bd\u04c0\5\u00ba^\2\u04be\u04c0\5\u0094K\2"+
		"\u04bf\u04bd\3\2\2\2\u04bf\u04be\3\2\2\2\u04c0\u00bd\3\2\2\2\u04c1\u04c2"+
		"\t\r\2\2\u04c2\u00bf\3\2\2\2\u04c3\u04c4\t\16\2\2\u04c4\u00c1\3\2\2\2"+
		"\u04c5\u04c6\t\17\2\2\u04c6\u00c3\3\2\2\2\u04c7\u04c8\t\20\2\2\u04c8\u00c5"+
		"\3\2\2\2\u04c9\u04ca\t\21\2\2\u04ca\u00c7\3\2\2\2\u04cb\u04cc\t\22\2\2"+
		"\u04cc\u00c9\3\2\2\2\u04cd\u04ce\t\23\2\2\u04ce\u00cb\3\2\2\2\u04cf\u04d0"+
		"\t\24\2\2\u04d0\u00cd\3\2\2\2\u04d1\u04d9\7-\2\2\u04d2\u04d9\7,\2\2\u04d3"+
		"\u04d9\7:\2\2\u04d4\u04d9\79\2\2\u04d5\u04d9\7<\2\2\u04d6\u04d9\5B\"\2"+
		"\u04d7\u04d9\5R*\2\u04d8\u04d1\3\2\2\2\u04d8\u04d2\3\2\2\2\u04d8\u04d3"+
		"\3\2\2\2\u04d8\u04d4\3\2\2\2\u04d8\u04d5\3\2\2\2\u04d8\u04d6\3\2\2\2\u04d8"+
		"\u04d7\3\2\2\2\u04d9\u00cf\3\2\2\2\u04da\u04e3\7:\2\2\u04db\u04e3\79\2"+
		"\2\u04dc\u04e3\7;\2\2\u04dd\u04e3\5^\60\2\u04de\u04e3\5\u00d8m\2\u04df"+
		"\u04e0\5\u00d4k\2\u04e0\u04e1\5\u00aeX\2\u04e1\u04e3\3\2\2\2\u04e2\u04da"+
		"\3\2\2\2\u04e2\u04db\3\2\2\2\u04e2\u04dc\3\2\2\2\u04e2\u04dd\3\2\2\2\u04e2"+
		"\u04de\3\2\2\2\u04e2\u04df\3\2\2\2\u04e3\u00d1\3\2\2\2\u04e4\u04e5\7R"+
		"\2\2\u04e5\u04e7\5L\'\2\u04e6\u04e4\3\2\2\2\u04e7\u04ea\3\2\2\2\u04e8"+
		"\u04e6\3\2\2\2\u04e8\u04e9\3\2\2\2\u04e9\u04ec\3\2\2\2\u04ea\u04e8\3\2"+
		"\2\2\u04eb\u04ed\5R*\2\u04ec\u04eb\3\2\2\2\u04ec\u04ed\3\2\2\2\u04ed\u04ee"+
		"\3\2\2\2\u04ee\u04ef\5\u00d6l\2\u04ef\u00d3\3\2\2\2\u04f0\u04f6\7\20\2"+
		"\2\u04f1\u04f3\7\36\2\2\u04f2\u04f4\7\20\2\2\u04f3\u04f2\3\2\2\2\u04f3"+
		"\u04f4\3\2\2\2\u04f4\u04f6\3\2\2\2\u04f5\u04f0\3\2\2\2\u04f5\u04f1\3\2"+
		"\2\2\u04f6\u00d5\3\2\2\2\u04f7\u04f8\7\b\2\2\u04f8\u04f9\5v<\2\u04f9\u04fa"+
		"\7\t\2\2\u04fa\u050f\3\2\2\2\u04fb\u0508\7\b\2\2\u04fc\u04fd\5\64\33\2"+
		"\u04fd\u04fe\7o\2\2\u04fe\u0505\3\2\2\2\u04ff\u0500\7\22\2\2\u0500\u0501"+
		"\5\64\33\2\u0501\u0502\7o\2\2\u0502\u0504\3\2\2\2\u0503\u04ff\3\2\2\2"+
		"\u0504\u0507\3\2\2\2\u0505\u0503\3\2\2\2\u0505\u0506\3\2\2\2\u0506\u0509"+
		"\3\2\2\2\u0507\u0505\3\2\2\2\u0508\u04fc\3\2\2\2\u0508\u0509\3\2\2\2\u0509"+
		"\u050a\3\2\2\2\u050a\u050b\7)\2\2\u050b\u050c\5v<\2\u050c\u050d\7\t\2"+
		"\2\u050d\u050f\3\2\2\2\u050e\u04f7\3\2\2\2\u050e\u04fb\3\2\2\2\u050f\u00d7"+
		"\3\2\2\2\u0510\u0519\7=\2\2\u0511\u0516\5\u0094K\2\u0512\u0513\7\22\2"+
		"\2\u0513\u0515\5\u0094K\2\u0514\u0512\3\2\2\2\u0515\u0518\3\2\2\2\u0516"+
		"\u0514\3\2\2\2\u0516\u0517\3\2\2\2\u0517\u051a\3\2\2\2\u0518\u0516\3\2"+
		"\2\2\u0519\u0511\3\2\2\2\u0519\u051a\3\2\2\2\u051a\u051b\3\2\2\2\u051b"+
		"\u051c\7>\2\2\u051c\u00d9\3\2\2\2\u051d\u0527\7a\2\2\u051e\u051f\7\33"+
		"\2\2\u051f\u0524\5d\63\2\u0520\u0521\7\22\2\2\u0521\u0523\5d\63\2\u0522"+
		"\u0520\3\2\2\2\u0523\u0526\3\2\2\2\u0524\u0522\3\2\2\2\u0524\u0525\3\2"+
		"\2\2\u0525\u0528\3\2\2\2\u0526\u0524\3\2\2\2\u0527\u051e\3\2\2\2\u0527"+
		"\u0528\3\2\2\2\u0528\u0529\3\2\2\2\u0529\u052a\5n8\2\u052a\u00db\3\2\2"+
		"\2\u052b\u0530\7g\2\2\u052c\u052d\7\34\2\2\u052d\u052e\5\u0094K\2\u052e"+
		"\u052f\7\35\2\2\u052f\u0531\3\2\2\2\u0530\u052c\3\2\2\2\u0530\u0531\3"+
		"\2\2\2\u0531\u0532\3\2\2\2\u0532\u0536\7\b\2\2\u0533\u0535\5\u00dep\2"+
		"\u0534\u0533\3\2\2\2\u0535\u0538\3\2\2\2\u0536\u0534\3\2\2\2\u0536\u0537"+
		"\3\2\2\2\u0537\u0539\3\2\2\2\u0538\u0536\3\2\2\2\u0539\u053a\7\t\2\2\u053a"+
		"\u00dd\3\2\2\2\u053b\u0540\5\u00e0q\2\u053c\u053d\7\22\2\2\u053d\u053f"+
		"\5\u00e0q\2\u053e\u053c\3\2\2\2\u053f\u0542\3\2\2\2\u0540\u053e\3\2\2"+
		"\2\u0540\u0541\3\2\2\2\u0541\u0543\3\2\2\2\u0542\u0540\3\2\2\2\u0543\u0544"+
		"\7)\2\2\u0544\u0548\5\u0094K\2\u0545\u0547\7\7\2\2\u0546\u0545\3\2\2\2"+
		"\u0547\u054a\3\2\2\2\u0548\u0546\3\2\2\2\u0548\u0549\3\2\2\2\u0549\u0555"+
		"\3\2\2\2\u054a\u0548\3\2\2\2\u054b\u054c\7f\2\2\u054c\u054d\7)\2\2\u054d"+
		"\u0551\5\u0094K\2\u054e\u0550\7\7\2\2\u054f\u054e\3\2\2\2\u0550\u0553"+
		"\3\2\2\2\u0551\u054f\3\2\2\2\u0551\u0552\3\2\2\2\u0552\u0555\3\2\2\2\u0553"+
		"\u0551\3\2\2\2\u0554\u053b\3\2\2\2\u0554\u054b\3\2\2\2\u0555\u00df\3\2"+
		"\2\2\u0556\u055c\5\u0094K\2\u0557\u0558\t\17\2\2\u0558\u055c\5\u0094K"+
		"\2\u0559\u055a\t\21\2\2\u055a\u055c\5\32\16\2\u055b\u0556\3\2\2\2\u055b"+
		"\u0557\3\2\2\2\u055b\u0559\3\2\2\2\u055c\u00e1\3\2\2\2\u00b5\u00e6\u00ea"+
		"\u00ef\u00f8\u00fe\u0107\u010e\u0113\u011a\u0123\u0126\u012e\u0137\u013b"+
		"\u0141\u014c\u014f\u0152\u0159\u0161\u0164\u0168\u016c\u016f\u0173\u0176"+
		"\u0178\u017c\u0183\u018a\u0193\u0198\u019d\u01a3\u01ae\u01b1\u01bd\u01c5"+
		"\u01ca\u01ce\u01d6\u01d9\u01dc\u01df\u01e2\u01e5\u01e7\u01ec\u01f8\u01fc"+
		"\u0207\u020c\u0211\u021b\u0228\u022e\u0237\u023a\u0244\u0247\u0250\u0254"+
		"\u0257\u025d\u0260\u0264\u0268\u026a\u0271\u027c\u0281\u0286\u028e\u0291"+
		"\u029a\u02a0\u02a7\u02aa\u02ae\u02b4\u02b8\u02c3\u02c9\u02cc\u02d0\u02d7"+
		"\u02da\u02df\u02e4\u02ed\u02f3\u02f6\u02fc\u0303\u0309\u0310\u0315\u031e"+
		"\u0321\u0328\u032e\u0332\u0337\u033b\u0340\u0346\u034d\u0353\u0356\u035b"+
		"\u0361\u036c\u0373\u0379\u037d\u0384\u0388\u0394\u0399\u03a0\u03a4\u03a9"+
		"\u03b7\u03bc\u03c0\u03da\u03e2\u03ea\u03f3\u03fc\u0405\u040c\u040e\u0415"+
		"\u041d\u0425\u042e\u0437\u0440\u0446\u0452\u0459\u045c\u0462\u0465\u046a"+
		"\u046d\u0479\u047b\u0486\u0488\u048c\u0496\u049d\u04a0\u04ac\u04b5\u04bb"+
		"\u04bf\u04d8\u04e2\u04e8\u04ec\u04f3\u04f5\u0505\u0508\u050e\u0516\u0519"+
		"\u0524\u0527\u0530\u0536\u0540\u0548\u0551\u0554\u055b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}