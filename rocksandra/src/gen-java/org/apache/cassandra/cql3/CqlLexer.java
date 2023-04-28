// $ANTLR 3.5.2 /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g 2023-04-24 17:22:10

    package org.apache.cassandra.cql3;

    import org.apache.cassandra.exceptions.SyntaxException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class CqlLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__173=173;
	public static final int T__174=174;
	public static final int T__175=175;
	public static final int T__176=176;
	public static final int T__177=177;
	public static final int T__178=178;
	public static final int T__179=179;
	public static final int T__180=180;
	public static final int T__181=181;
	public static final int T__182=182;
	public static final int T__183=183;
	public static final int T__184=184;
	public static final int T__185=185;
	public static final int T__186=186;
	public static final int T__187=187;
	public static final int T__188=188;
	public static final int T__189=189;
	public static final int T__190=190;
	public static final int T__191=191;
	public static final int T__192=192;
	public static final int A=4;
	public static final int B=5;
	public static final int BOOLEAN=6;
	public static final int C=7;
	public static final int COMMENT=8;
	public static final int D=9;
	public static final int DIGIT=10;
	public static final int E=11;
	public static final int EXPONENT=12;
	public static final int F=13;
	public static final int FLOAT=14;
	public static final int G=15;
	public static final int H=16;
	public static final int HEX=17;
	public static final int HEXNUMBER=18;
	public static final int I=19;
	public static final int IDENT=20;
	public static final int INTEGER=21;
	public static final int J=22;
	public static final int K=23;
	public static final int K_ADD=24;
	public static final int K_AGGREGATE=25;
	public static final int K_ALL=26;
	public static final int K_ALLOW=27;
	public static final int K_ALTER=28;
	public static final int K_AND=29;
	public static final int K_APPLY=30;
	public static final int K_AS=31;
	public static final int K_ASC=32;
	public static final int K_ASCII=33;
	public static final int K_AUTHORIZE=34;
	public static final int K_BATCH=35;
	public static final int K_BEGIN=36;
	public static final int K_BIGINT=37;
	public static final int K_BLOB=38;
	public static final int K_BOOLEAN=39;
	public static final int K_BY=40;
	public static final int K_CALLED=41;
	public static final int K_CLUSTERING=42;
	public static final int K_COLUMNFAMILY=43;
	public static final int K_COMPACT=44;
	public static final int K_CONTAINS=45;
	public static final int K_COUNT=46;
	public static final int K_COUNTER=47;
	public static final int K_CREATE=48;
	public static final int K_CUSTOM=49;
	public static final int K_DATE=50;
	public static final int K_DECIMAL=51;
	public static final int K_DELETE=52;
	public static final int K_DESC=53;
	public static final int K_DESCRIBE=54;
	public static final int K_DISTINCT=55;
	public static final int K_DOUBLE=56;
	public static final int K_DROP=57;
	public static final int K_ENTRIES=58;
	public static final int K_EXECUTE=59;
	public static final int K_EXISTS=60;
	public static final int K_FILTERING=61;
	public static final int K_FINALFUNC=62;
	public static final int K_FLOAT=63;
	public static final int K_FROM=64;
	public static final int K_FROZEN=65;
	public static final int K_FULL=66;
	public static final int K_FUNCTION=67;
	public static final int K_FUNCTIONS=68;
	public static final int K_GRANT=69;
	public static final int K_IF=70;
	public static final int K_IN=71;
	public static final int K_INDEX=72;
	public static final int K_INET=73;
	public static final int K_INFINITY=74;
	public static final int K_INITCOND=75;
	public static final int K_INPUT=76;
	public static final int K_INSERT=77;
	public static final int K_INT=78;
	public static final int K_INTO=79;
	public static final int K_IS=80;
	public static final int K_JSON=81;
	public static final int K_KEY=82;
	public static final int K_KEYS=83;
	public static final int K_KEYSPACE=84;
	public static final int K_KEYSPACES=85;
	public static final int K_LANGUAGE=86;
	public static final int K_LIKE=87;
	public static final int K_LIMIT=88;
	public static final int K_LIST=89;
	public static final int K_LOGIN=90;
	public static final int K_MAP=91;
	public static final int K_MATERIALIZED=92;
	public static final int K_MODIFY=93;
	public static final int K_NAN=94;
	public static final int K_NOLOGIN=95;
	public static final int K_NORECURSIVE=96;
	public static final int K_NOSUPERUSER=97;
	public static final int K_NOT=98;
	public static final int K_NULL=99;
	public static final int K_OF=100;
	public static final int K_ON=101;
	public static final int K_OPTIONS=102;
	public static final int K_OR=103;
	public static final int K_ORDER=104;
	public static final int K_PARTITION=105;
	public static final int K_PASSWORD=106;
	public static final int K_PER=107;
	public static final int K_PERMISSION=108;
	public static final int K_PERMISSIONS=109;
	public static final int K_PRIMARY=110;
	public static final int K_RENAME=111;
	public static final int K_REPLACE=112;
	public static final int K_RETURNS=113;
	public static final int K_REVOKE=114;
	public static final int K_ROLE=115;
	public static final int K_ROLES=116;
	public static final int K_SELECT=117;
	public static final int K_SET=118;
	public static final int K_SFUNC=119;
	public static final int K_SMALLINT=120;
	public static final int K_STATIC=121;
	public static final int K_STORAGE=122;
	public static final int K_STYPE=123;
	public static final int K_SUPERUSER=124;
	public static final int K_TEXT=125;
	public static final int K_TIME=126;
	public static final int K_TIMESTAMP=127;
	public static final int K_TIMEUUID=128;
	public static final int K_TINYINT=129;
	public static final int K_TO=130;
	public static final int K_TOKEN=131;
	public static final int K_TRIGGER=132;
	public static final int K_TRUNCATE=133;
	public static final int K_TTL=134;
	public static final int K_TUPLE=135;
	public static final int K_TYPE=136;
	public static final int K_UNLOGGED=137;
	public static final int K_UPDATE=138;
	public static final int K_USE=139;
	public static final int K_USER=140;
	public static final int K_USERS=141;
	public static final int K_USING=142;
	public static final int K_UUID=143;
	public static final int K_VALUES=144;
	public static final int K_VARCHAR=145;
	public static final int K_VARINT=146;
	public static final int K_VIEW=147;
	public static final int K_WHERE=148;
	public static final int K_WITH=149;
	public static final int K_WRITETIME=150;
	public static final int L=151;
	public static final int LETTER=152;
	public static final int M=153;
	public static final int MULTILINE_COMMENT=154;
	public static final int N=155;
	public static final int O=156;
	public static final int P=157;
	public static final int Q=158;
	public static final int QMARK=159;
	public static final int QUOTED_NAME=160;
	public static final int R=161;
	public static final int S=162;
	public static final int STRING_LITERAL=163;
	public static final int T=164;
	public static final int U=165;
	public static final int UUID=166;
	public static final int V=167;
	public static final int W=168;
	public static final int WS=169;
	public static final int X=170;
	public static final int Y=171;
	public static final int Z=172;

	    List<Token> tokens = new ArrayList<Token>();

	    public void emit(Token token)
	    {
	        state.token = token;
	        tokens.add(token);
	    }

	    public Token nextToken()
	    {
	        super.nextToken();
	        if (tokens.size() == 0)
	            return new CommonToken(Token.EOF);
	        return tokens.remove(0);
	    }

	    private final List<ErrorListener> listeners = new ArrayList<ErrorListener>();

	    public void addErrorListener(ErrorListener listener)
	    {
	        this.listeners.add(listener);
	    }

	    public void removeErrorListener(ErrorListener listener)
	    {
	        this.listeners.remove(listener);
	    }

	    public void displayRecognitionError(String[] tokenNames, RecognitionException e)
	    {
	        for (int i = 0, m = listeners.size(); i < m; i++)
	            listeners.get(i).syntaxError(this, tokenNames, e);
	    }


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public CqlLexer() {} 
	public CqlLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public CqlLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g"; }

	// $ANTLR start "T__173"
	public final void mT__173() throws RecognitionException {
		try {
			int _type = T__173;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:48:8: ( '!=' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:48:10: '!='
			{
			match("!="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__173"

	// $ANTLR start "T__174"
	public final void mT__174() throws RecognitionException {
		try {
			int _type = T__174;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:49:8: ( '(' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:49:10: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__174"

	// $ANTLR start "T__175"
	public final void mT__175() throws RecognitionException {
		try {
			int _type = T__175;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:50:8: ( ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:50:10: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__175"

	// $ANTLR start "T__176"
	public final void mT__176() throws RecognitionException {
		try {
			int _type = T__176;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:51:8: ( '+' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:51:10: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__176"

	// $ANTLR start "T__177"
	public final void mT__177() throws RecognitionException {
		try {
			int _type = T__177;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:52:8: ( ',' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:52:10: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__177"

	// $ANTLR start "T__178"
	public final void mT__178() throws RecognitionException {
		try {
			int _type = T__178;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:53:8: ( '-' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:53:10: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__178"

	// $ANTLR start "T__179"
	public final void mT__179() throws RecognitionException {
		try {
			int _type = T__179;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:54:8: ( '.' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:54:10: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__179"

	// $ANTLR start "T__180"
	public final void mT__180() throws RecognitionException {
		try {
			int _type = T__180;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:55:8: ( ':' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:55:10: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__180"

	// $ANTLR start "T__181"
	public final void mT__181() throws RecognitionException {
		try {
			int _type = T__181;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:56:8: ( ';' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:56:10: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__181"

	// $ANTLR start "T__182"
	public final void mT__182() throws RecognitionException {
		try {
			int _type = T__182;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:57:8: ( '<' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:57:10: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__182"

	// $ANTLR start "T__183"
	public final void mT__183() throws RecognitionException {
		try {
			int _type = T__183;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:58:8: ( '<=' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:58:10: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__183"

	// $ANTLR start "T__184"
	public final void mT__184() throws RecognitionException {
		try {
			int _type = T__184;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:59:8: ( '=' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:59:10: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__184"

	// $ANTLR start "T__185"
	public final void mT__185() throws RecognitionException {
		try {
			int _type = T__185;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:60:8: ( '>' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:60:10: '>'
			{
			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__185"

	// $ANTLR start "T__186"
	public final void mT__186() throws RecognitionException {
		try {
			int _type = T__186;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:61:8: ( '>=' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:61:10: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__186"

	// $ANTLR start "T__187"
	public final void mT__187() throws RecognitionException {
		try {
			int _type = T__187;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:62:8: ( '[' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:62:10: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__187"

	// $ANTLR start "T__188"
	public final void mT__188() throws RecognitionException {
		try {
			int _type = T__188;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:63:8: ( '\\*' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:63:10: '\\*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__188"

	// $ANTLR start "T__189"
	public final void mT__189() throws RecognitionException {
		try {
			int _type = T__189;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:64:8: ( ']' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:64:10: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__189"

	// $ANTLR start "T__190"
	public final void mT__190() throws RecognitionException {
		try {
			int _type = T__190;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:65:8: ( 'expr(' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:65:10: 'expr('
			{
			match("expr("); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__190"

	// $ANTLR start "T__191"
	public final void mT__191() throws RecognitionException {
		try {
			int _type = T__191;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:66:8: ( '{' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:66:10: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__191"

	// $ANTLR start "T__192"
	public final void mT__192() throws RecognitionException {
		try {
			int _type = T__192;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:67:8: ( '}' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:67:10: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__192"

	// $ANTLR start "K_SELECT"
	public final void mK_SELECT() throws RecognitionException {
		try {
			int _type = K_SELECT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1644:9: ( S E L E C T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1644:16: S E L E C T
			{
			mS(); 

			mE(); 

			mL(); 

			mE(); 

			mC(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_SELECT"

	// $ANTLR start "K_FROM"
	public final void mK_FROM() throws RecognitionException {
		try {
			int _type = K_FROM;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1645:7: ( F R O M )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1645:16: F R O M
			{
			mF(); 

			mR(); 

			mO(); 

			mM(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FROM"

	// $ANTLR start "K_AS"
	public final void mK_AS() throws RecognitionException {
		try {
			int _type = K_AS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1646:5: ( A S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1646:16: A S
			{
			mA(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_AS"

	// $ANTLR start "K_WHERE"
	public final void mK_WHERE() throws RecognitionException {
		try {
			int _type = K_WHERE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1647:8: ( W H E R E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1647:16: W H E R E
			{
			mW(); 

			mH(); 

			mE(); 

			mR(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_WHERE"

	// $ANTLR start "K_AND"
	public final void mK_AND() throws RecognitionException {
		try {
			int _type = K_AND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1648:6: ( A N D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1648:16: A N D
			{
			mA(); 

			mN(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_AND"

	// $ANTLR start "K_KEY"
	public final void mK_KEY() throws RecognitionException {
		try {
			int _type = K_KEY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1649:6: ( K E Y )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1649:16: K E Y
			{
			mK(); 

			mE(); 

			mY(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_KEY"

	// $ANTLR start "K_KEYS"
	public final void mK_KEYS() throws RecognitionException {
		try {
			int _type = K_KEYS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1650:7: ( K E Y S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1650:16: K E Y S
			{
			mK(); 

			mE(); 

			mY(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_KEYS"

	// $ANTLR start "K_ENTRIES"
	public final void mK_ENTRIES() throws RecognitionException {
		try {
			int _type = K_ENTRIES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1651:10: ( E N T R I E S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1651:16: E N T R I E S
			{
			mE(); 

			mN(); 

			mT(); 

			mR(); 

			mI(); 

			mE(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ENTRIES"

	// $ANTLR start "K_FULL"
	public final void mK_FULL() throws RecognitionException {
		try {
			int _type = K_FULL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1652:7: ( F U L L )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1652:16: F U L L
			{
			mF(); 

			mU(); 

			mL(); 

			mL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FULL"

	// $ANTLR start "K_INSERT"
	public final void mK_INSERT() throws RecognitionException {
		try {
			int _type = K_INSERT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1653:9: ( I N S E R T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1653:16: I N S E R T
			{
			mI(); 

			mN(); 

			mS(); 

			mE(); 

			mR(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INSERT"

	// $ANTLR start "K_UPDATE"
	public final void mK_UPDATE() throws RecognitionException {
		try {
			int _type = K_UPDATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1654:9: ( U P D A T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1654:16: U P D A T E
			{
			mU(); 

			mP(); 

			mD(); 

			mA(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_UPDATE"

	// $ANTLR start "K_WITH"
	public final void mK_WITH() throws RecognitionException {
		try {
			int _type = K_WITH;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1655:7: ( W I T H )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1655:16: W I T H
			{
			mW(); 

			mI(); 

			mT(); 

			mH(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_WITH"

	// $ANTLR start "K_LIMIT"
	public final void mK_LIMIT() throws RecognitionException {
		try {
			int _type = K_LIMIT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1656:8: ( L I M I T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1656:16: L I M I T
			{
			mL(); 

			mI(); 

			mM(); 

			mI(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_LIMIT"

	// $ANTLR start "K_LIKE"
	public final void mK_LIKE() throws RecognitionException {
		try {
			int _type = K_LIKE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1657:7: ( L I K E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1657:16: L I K E
			{
			mL(); 

			mI(); 

			mK(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_LIKE"

	// $ANTLR start "K_PER"
	public final void mK_PER() throws RecognitionException {
		try {
			int _type = K_PER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1658:6: ( P E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1658:16: P E R
			{
			mP(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_PER"

	// $ANTLR start "K_PARTITION"
	public final void mK_PARTITION() throws RecognitionException {
		try {
			int _type = K_PARTITION;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1659:12: ( P A R T I T I O N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1659:16: P A R T I T I O N
			{
			mP(); 

			mA(); 

			mR(); 

			mT(); 

			mI(); 

			mT(); 

			mI(); 

			mO(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_PARTITION"

	// $ANTLR start "K_USING"
	public final void mK_USING() throws RecognitionException {
		try {
			int _type = K_USING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1660:8: ( U S I N G )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1660:16: U S I N G
			{
			mU(); 

			mS(); 

			mI(); 

			mN(); 

			mG(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_USING"

	// $ANTLR start "K_USE"
	public final void mK_USE() throws RecognitionException {
		try {
			int _type = K_USE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1661:6: ( U S E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1661:16: U S E
			{
			mU(); 

			mS(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_USE"

	// $ANTLR start "K_DISTINCT"
	public final void mK_DISTINCT() throws RecognitionException {
		try {
			int _type = K_DISTINCT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1662:11: ( D I S T I N C T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1662:16: D I S T I N C T
			{
			mD(); 

			mI(); 

			mS(); 

			mT(); 

			mI(); 

			mN(); 

			mC(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DISTINCT"

	// $ANTLR start "K_COUNT"
	public final void mK_COUNT() throws RecognitionException {
		try {
			int _type = K_COUNT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1663:8: ( C O U N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1663:16: C O U N T
			{
			mC(); 

			mO(); 

			mU(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_COUNT"

	// $ANTLR start "K_SET"
	public final void mK_SET() throws RecognitionException {
		try {
			int _type = K_SET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1664:6: ( S E T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1664:16: S E T
			{
			mS(); 

			mE(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_SET"

	// $ANTLR start "K_BEGIN"
	public final void mK_BEGIN() throws RecognitionException {
		try {
			int _type = K_BEGIN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1665:8: ( B E G I N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1665:16: B E G I N
			{
			mB(); 

			mE(); 

			mG(); 

			mI(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_BEGIN"

	// $ANTLR start "K_UNLOGGED"
	public final void mK_UNLOGGED() throws RecognitionException {
		try {
			int _type = K_UNLOGGED;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1666:11: ( U N L O G G E D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1666:16: U N L O G G E D
			{
			mU(); 

			mN(); 

			mL(); 

			mO(); 

			mG(); 

			mG(); 

			mE(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_UNLOGGED"

	// $ANTLR start "K_BATCH"
	public final void mK_BATCH() throws RecognitionException {
		try {
			int _type = K_BATCH;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1667:8: ( B A T C H )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1667:16: B A T C H
			{
			mB(); 

			mA(); 

			mT(); 

			mC(); 

			mH(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_BATCH"

	// $ANTLR start "K_APPLY"
	public final void mK_APPLY() throws RecognitionException {
		try {
			int _type = K_APPLY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1668:8: ( A P P L Y )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1668:16: A P P L Y
			{
			mA(); 

			mP(); 

			mP(); 

			mL(); 

			mY(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_APPLY"

	// $ANTLR start "K_TRUNCATE"
	public final void mK_TRUNCATE() throws RecognitionException {
		try {
			int _type = K_TRUNCATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1669:11: ( T R U N C A T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1669:16: T R U N C A T E
			{
			mT(); 

			mR(); 

			mU(); 

			mN(); 

			mC(); 

			mA(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TRUNCATE"

	// $ANTLR start "K_DELETE"
	public final void mK_DELETE() throws RecognitionException {
		try {
			int _type = K_DELETE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1670:9: ( D E L E T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1670:16: D E L E T E
			{
			mD(); 

			mE(); 

			mL(); 

			mE(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DELETE"

	// $ANTLR start "K_IN"
	public final void mK_IN() throws RecognitionException {
		try {
			int _type = K_IN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1671:5: ( I N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1671:16: I N
			{
			mI(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_IN"

	// $ANTLR start "K_CREATE"
	public final void mK_CREATE() throws RecognitionException {
		try {
			int _type = K_CREATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1672:9: ( C R E A T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1672:16: C R E A T E
			{
			mC(); 

			mR(); 

			mE(); 

			mA(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_CREATE"

	// $ANTLR start "K_KEYSPACE"
	public final void mK_KEYSPACE() throws RecognitionException {
		try {
			int _type = K_KEYSPACE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1673:11: ( ( K E Y S P A C E | S C H E M A ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1673:16: ( K E Y S P A C E | S C H E M A )
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1673:16: ( K E Y S P A C E | S C H E M A )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0=='K'||LA1_0=='k') ) {
				alt1=1;
			}
			else if ( (LA1_0=='S'||LA1_0=='s') ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1673:18: K E Y S P A C E
					{
					mK(); 

					mE(); 

					mY(); 

					mS(); 

					mP(); 

					mA(); 

					mC(); 

					mE(); 

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1674:20: S C H E M A
					{
					mS(); 

					mC(); 

					mH(); 

					mE(); 

					mM(); 

					mA(); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_KEYSPACE"

	// $ANTLR start "K_KEYSPACES"
	public final void mK_KEYSPACES() throws RecognitionException {
		try {
			int _type = K_KEYSPACES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1675:12: ( K E Y S P A C E S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1675:16: K E Y S P A C E S
			{
			mK(); 

			mE(); 

			mY(); 

			mS(); 

			mP(); 

			mA(); 

			mC(); 

			mE(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_KEYSPACES"

	// $ANTLR start "K_COLUMNFAMILY"
	public final void mK_COLUMNFAMILY() throws RecognitionException {
		try {
			int _type = K_COLUMNFAMILY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1676:15: ( ( C O L U M N F A M I L Y | T A B L E ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1676:16: ( C O L U M N F A M I L Y | T A B L E )
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1676:16: ( C O L U M N F A M I L Y | T A B L E )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0=='C'||LA2_0=='c') ) {
				alt2=1;
			}
			else if ( (LA2_0=='T'||LA2_0=='t') ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1676:18: C O L U M N F A M I L Y
					{
					mC(); 

					mO(); 

					mL(); 

					mU(); 

					mM(); 

					mN(); 

					mF(); 

					mA(); 

					mM(); 

					mI(); 

					mL(); 

					mY(); 

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1677:20: T A B L E
					{
					mT(); 

					mA(); 

					mB(); 

					mL(); 

					mE(); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_COLUMNFAMILY"

	// $ANTLR start "K_MATERIALIZED"
	public final void mK_MATERIALIZED() throws RecognitionException {
		try {
			int _type = K_MATERIALIZED;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1678:15: ( M A T E R I A L I Z E D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1678:16: M A T E R I A L I Z E D
			{
			mM(); 

			mA(); 

			mT(); 

			mE(); 

			mR(); 

			mI(); 

			mA(); 

			mL(); 

			mI(); 

			mZ(); 

			mE(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_MATERIALIZED"

	// $ANTLR start "K_VIEW"
	public final void mK_VIEW() throws RecognitionException {
		try {
			int _type = K_VIEW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1679:7: ( V I E W )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1679:16: V I E W
			{
			mV(); 

			mI(); 

			mE(); 

			mW(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_VIEW"

	// $ANTLR start "K_INDEX"
	public final void mK_INDEX() throws RecognitionException {
		try {
			int _type = K_INDEX;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1680:8: ( I N D E X )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1680:16: I N D E X
			{
			mI(); 

			mN(); 

			mD(); 

			mE(); 

			mX(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INDEX"

	// $ANTLR start "K_CUSTOM"
	public final void mK_CUSTOM() throws RecognitionException {
		try {
			int _type = K_CUSTOM;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1681:9: ( C U S T O M )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1681:16: C U S T O M
			{
			mC(); 

			mU(); 

			mS(); 

			mT(); 

			mO(); 

			mM(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_CUSTOM"

	// $ANTLR start "K_ON"
	public final void mK_ON() throws RecognitionException {
		try {
			int _type = K_ON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1682:5: ( O N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1682:16: O N
			{
			mO(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ON"

	// $ANTLR start "K_TO"
	public final void mK_TO() throws RecognitionException {
		try {
			int _type = K_TO;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1683:5: ( T O )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1683:16: T O
			{
			mT(); 

			mO(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TO"

	// $ANTLR start "K_DROP"
	public final void mK_DROP() throws RecognitionException {
		try {
			int _type = K_DROP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1684:7: ( D R O P )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1684:16: D R O P
			{
			mD(); 

			mR(); 

			mO(); 

			mP(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DROP"

	// $ANTLR start "K_PRIMARY"
	public final void mK_PRIMARY() throws RecognitionException {
		try {
			int _type = K_PRIMARY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1685:10: ( P R I M A R Y )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1685:16: P R I M A R Y
			{
			mP(); 

			mR(); 

			mI(); 

			mM(); 

			mA(); 

			mR(); 

			mY(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_PRIMARY"

	// $ANTLR start "K_INTO"
	public final void mK_INTO() throws RecognitionException {
		try {
			int _type = K_INTO;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1686:7: ( I N T O )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1686:16: I N T O
			{
			mI(); 

			mN(); 

			mT(); 

			mO(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INTO"

	// $ANTLR start "K_VALUES"
	public final void mK_VALUES() throws RecognitionException {
		try {
			int _type = K_VALUES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1687:9: ( V A L U E S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1687:16: V A L U E S
			{
			mV(); 

			mA(); 

			mL(); 

			mU(); 

			mE(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_VALUES"

	// $ANTLR start "K_TIMESTAMP"
	public final void mK_TIMESTAMP() throws RecognitionException {
		try {
			int _type = K_TIMESTAMP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1688:12: ( T I M E S T A M P )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1688:16: T I M E S T A M P
			{
			mT(); 

			mI(); 

			mM(); 

			mE(); 

			mS(); 

			mT(); 

			mA(); 

			mM(); 

			mP(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TIMESTAMP"

	// $ANTLR start "K_TTL"
	public final void mK_TTL() throws RecognitionException {
		try {
			int _type = K_TTL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1689:6: ( T T L )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1689:16: T T L
			{
			mT(); 

			mT(); 

			mL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TTL"

	// $ANTLR start "K_ALTER"
	public final void mK_ALTER() throws RecognitionException {
		try {
			int _type = K_ALTER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1690:8: ( A L T E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1690:16: A L T E R
			{
			mA(); 

			mL(); 

			mT(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ALTER"

	// $ANTLR start "K_RENAME"
	public final void mK_RENAME() throws RecognitionException {
		try {
			int _type = K_RENAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1691:9: ( R E N A M E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1691:16: R E N A M E
			{
			mR(); 

			mE(); 

			mN(); 

			mA(); 

			mM(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_RENAME"

	// $ANTLR start "K_ADD"
	public final void mK_ADD() throws RecognitionException {
		try {
			int _type = K_ADD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1692:6: ( A D D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1692:16: A D D
			{
			mA(); 

			mD(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ADD"

	// $ANTLR start "K_TYPE"
	public final void mK_TYPE() throws RecognitionException {
		try {
			int _type = K_TYPE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1693:7: ( T Y P E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1693:16: T Y P E
			{
			mT(); 

			mY(); 

			mP(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TYPE"

	// $ANTLR start "K_COMPACT"
	public final void mK_COMPACT() throws RecognitionException {
		try {
			int _type = K_COMPACT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1694:10: ( C O M P A C T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1694:16: C O M P A C T
			{
			mC(); 

			mO(); 

			mM(); 

			mP(); 

			mA(); 

			mC(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_COMPACT"

	// $ANTLR start "K_STORAGE"
	public final void mK_STORAGE() throws RecognitionException {
		try {
			int _type = K_STORAGE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1695:10: ( S T O R A G E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1695:16: S T O R A G E
			{
			mS(); 

			mT(); 

			mO(); 

			mR(); 

			mA(); 

			mG(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_STORAGE"

	// $ANTLR start "K_ORDER"
	public final void mK_ORDER() throws RecognitionException {
		try {
			int _type = K_ORDER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1696:8: ( O R D E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1696:16: O R D E R
			{
			mO(); 

			mR(); 

			mD(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ORDER"

	// $ANTLR start "K_BY"
	public final void mK_BY() throws RecognitionException {
		try {
			int _type = K_BY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1697:5: ( B Y )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1697:16: B Y
			{
			mB(); 

			mY(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_BY"

	// $ANTLR start "K_ASC"
	public final void mK_ASC() throws RecognitionException {
		try {
			int _type = K_ASC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1698:6: ( A S C )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1698:16: A S C
			{
			mA(); 

			mS(); 

			mC(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ASC"

	// $ANTLR start "K_DESC"
	public final void mK_DESC() throws RecognitionException {
		try {
			int _type = K_DESC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1699:7: ( D E S C )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1699:16: D E S C
			{
			mD(); 

			mE(); 

			mS(); 

			mC(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DESC"

	// $ANTLR start "K_ALLOW"
	public final void mK_ALLOW() throws RecognitionException {
		try {
			int _type = K_ALLOW;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1700:8: ( A L L O W )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1700:16: A L L O W
			{
			mA(); 

			mL(); 

			mL(); 

			mO(); 

			mW(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ALLOW"

	// $ANTLR start "K_FILTERING"
	public final void mK_FILTERING() throws RecognitionException {
		try {
			int _type = K_FILTERING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1701:12: ( F I L T E R I N G )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1701:16: F I L T E R I N G
			{
			mF(); 

			mI(); 

			mL(); 

			mT(); 

			mE(); 

			mR(); 

			mI(); 

			mN(); 

			mG(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FILTERING"

	// $ANTLR start "K_IF"
	public final void mK_IF() throws RecognitionException {
		try {
			int _type = K_IF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1702:5: ( I F )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1702:16: I F
			{
			mI(); 

			mF(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_IF"

	// $ANTLR start "K_IS"
	public final void mK_IS() throws RecognitionException {
		try {
			int _type = K_IS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1703:5: ( I S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1703:16: I S
			{
			mI(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_IS"

	// $ANTLR start "K_CONTAINS"
	public final void mK_CONTAINS() throws RecognitionException {
		try {
			int _type = K_CONTAINS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1704:11: ( C O N T A I N S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1704:16: C O N T A I N S
			{
			mC(); 

			mO(); 

			mN(); 

			mT(); 

			mA(); 

			mI(); 

			mN(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_CONTAINS"

	// $ANTLR start "K_GRANT"
	public final void mK_GRANT() throws RecognitionException {
		try {
			int _type = K_GRANT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1706:8: ( G R A N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1706:16: G R A N T
			{
			mG(); 

			mR(); 

			mA(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_GRANT"

	// $ANTLR start "K_ALL"
	public final void mK_ALL() throws RecognitionException {
		try {
			int _type = K_ALL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1707:6: ( A L L )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1707:16: A L L
			{
			mA(); 

			mL(); 

			mL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ALL"

	// $ANTLR start "K_PERMISSION"
	public final void mK_PERMISSION() throws RecognitionException {
		try {
			int _type = K_PERMISSION;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1708:13: ( P E R M I S S I O N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1708:16: P E R M I S S I O N
			{
			mP(); 

			mE(); 

			mR(); 

			mM(); 

			mI(); 

			mS(); 

			mS(); 

			mI(); 

			mO(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_PERMISSION"

	// $ANTLR start "K_PERMISSIONS"
	public final void mK_PERMISSIONS() throws RecognitionException {
		try {
			int _type = K_PERMISSIONS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1709:14: ( P E R M I S S I O N S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1709:16: P E R M I S S I O N S
			{
			mP(); 

			mE(); 

			mR(); 

			mM(); 

			mI(); 

			mS(); 

			mS(); 

			mI(); 

			mO(); 

			mN(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_PERMISSIONS"

	// $ANTLR start "K_OF"
	public final void mK_OF() throws RecognitionException {
		try {
			int _type = K_OF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1710:5: ( O F )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1710:16: O F
			{
			mO(); 

			mF(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_OF"

	// $ANTLR start "K_REVOKE"
	public final void mK_REVOKE() throws RecognitionException {
		try {
			int _type = K_REVOKE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1711:9: ( R E V O K E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1711:16: R E V O K E
			{
			mR(); 

			mE(); 

			mV(); 

			mO(); 

			mK(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_REVOKE"

	// $ANTLR start "K_MODIFY"
	public final void mK_MODIFY() throws RecognitionException {
		try {
			int _type = K_MODIFY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1712:9: ( M O D I F Y )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1712:16: M O D I F Y
			{
			mM(); 

			mO(); 

			mD(); 

			mI(); 

			mF(); 

			mY(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_MODIFY"

	// $ANTLR start "K_AUTHORIZE"
	public final void mK_AUTHORIZE() throws RecognitionException {
		try {
			int _type = K_AUTHORIZE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1713:12: ( A U T H O R I Z E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1713:16: A U T H O R I Z E
			{
			mA(); 

			mU(); 

			mT(); 

			mH(); 

			mO(); 

			mR(); 

			mI(); 

			mZ(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_AUTHORIZE"

	// $ANTLR start "K_DESCRIBE"
	public final void mK_DESCRIBE() throws RecognitionException {
		try {
			int _type = K_DESCRIBE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1714:11: ( D E S C R I B E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1714:16: D E S C R I B E
			{
			mD(); 

			mE(); 

			mS(); 

			mC(); 

			mR(); 

			mI(); 

			mB(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DESCRIBE"

	// $ANTLR start "K_EXECUTE"
	public final void mK_EXECUTE() throws RecognitionException {
		try {
			int _type = K_EXECUTE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1715:10: ( E X E C U T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1715:16: E X E C U T E
			{
			mE(); 

			mX(); 

			mE(); 

			mC(); 

			mU(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_EXECUTE"

	// $ANTLR start "K_NORECURSIVE"
	public final void mK_NORECURSIVE() throws RecognitionException {
		try {
			int _type = K_NORECURSIVE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1716:14: ( N O R E C U R S I V E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1716:16: N O R E C U R S I V E
			{
			mN(); 

			mO(); 

			mR(); 

			mE(); 

			mC(); 

			mU(); 

			mR(); 

			mS(); 

			mI(); 

			mV(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_NORECURSIVE"

	// $ANTLR start "K_USER"
	public final void mK_USER() throws RecognitionException {
		try {
			int _type = K_USER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1718:7: ( U S E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1718:16: U S E R
			{
			mU(); 

			mS(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_USER"

	// $ANTLR start "K_USERS"
	public final void mK_USERS() throws RecognitionException {
		try {
			int _type = K_USERS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1719:8: ( U S E R S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1719:16: U S E R S
			{
			mU(); 

			mS(); 

			mE(); 

			mR(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_USERS"

	// $ANTLR start "K_ROLE"
	public final void mK_ROLE() throws RecognitionException {
		try {
			int _type = K_ROLE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1720:7: ( R O L E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1720:16: R O L E
			{
			mR(); 

			mO(); 

			mL(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ROLE"

	// $ANTLR start "K_ROLES"
	public final void mK_ROLES() throws RecognitionException {
		try {
			int _type = K_ROLES;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1721:8: ( R O L E S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1721:16: R O L E S
			{
			mR(); 

			mO(); 

			mL(); 

			mE(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ROLES"

	// $ANTLR start "K_SUPERUSER"
	public final void mK_SUPERUSER() throws RecognitionException {
		try {
			int _type = K_SUPERUSER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1722:12: ( S U P E R U S E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1722:16: S U P E R U S E R
			{
			mS(); 

			mU(); 

			mP(); 

			mE(); 

			mR(); 

			mU(); 

			mS(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_SUPERUSER"

	// $ANTLR start "K_NOSUPERUSER"
	public final void mK_NOSUPERUSER() throws RecognitionException {
		try {
			int _type = K_NOSUPERUSER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1723:14: ( N O S U P E R U S E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1723:16: N O S U P E R U S E R
			{
			mN(); 

			mO(); 

			mS(); 

			mU(); 

			mP(); 

			mE(); 

			mR(); 

			mU(); 

			mS(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_NOSUPERUSER"

	// $ANTLR start "K_PASSWORD"
	public final void mK_PASSWORD() throws RecognitionException {
		try {
			int _type = K_PASSWORD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1724:11: ( P A S S W O R D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1724:16: P A S S W O R D
			{
			mP(); 

			mA(); 

			mS(); 

			mS(); 

			mW(); 

			mO(); 

			mR(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_PASSWORD"

	// $ANTLR start "K_LOGIN"
	public final void mK_LOGIN() throws RecognitionException {
		try {
			int _type = K_LOGIN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1725:8: ( L O G I N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1725:16: L O G I N
			{
			mL(); 

			mO(); 

			mG(); 

			mI(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_LOGIN"

	// $ANTLR start "K_NOLOGIN"
	public final void mK_NOLOGIN() throws RecognitionException {
		try {
			int _type = K_NOLOGIN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1726:10: ( N O L O G I N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1726:16: N O L O G I N
			{
			mN(); 

			mO(); 

			mL(); 

			mO(); 

			mG(); 

			mI(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_NOLOGIN"

	// $ANTLR start "K_OPTIONS"
	public final void mK_OPTIONS() throws RecognitionException {
		try {
			int _type = K_OPTIONS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1727:10: ( O P T I O N S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1727:16: O P T I O N S
			{
			mO(); 

			mP(); 

			mT(); 

			mI(); 

			mO(); 

			mN(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_OPTIONS"

	// $ANTLR start "K_CLUSTERING"
	public final void mK_CLUSTERING() throws RecognitionException {
		try {
			int _type = K_CLUSTERING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1729:13: ( C L U S T E R I N G )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1729:16: C L U S T E R I N G
			{
			mC(); 

			mL(); 

			mU(); 

			mS(); 

			mT(); 

			mE(); 

			mR(); 

			mI(); 

			mN(); 

			mG(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_CLUSTERING"

	// $ANTLR start "K_ASCII"
	public final void mK_ASCII() throws RecognitionException {
		try {
			int _type = K_ASCII;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1730:8: ( A S C I I )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1730:16: A S C I I
			{
			mA(); 

			mS(); 

			mC(); 

			mI(); 

			mI(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_ASCII"

	// $ANTLR start "K_BIGINT"
	public final void mK_BIGINT() throws RecognitionException {
		try {
			int _type = K_BIGINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1731:9: ( B I G I N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1731:16: B I G I N T
			{
			mB(); 

			mI(); 

			mG(); 

			mI(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_BIGINT"

	// $ANTLR start "K_BLOB"
	public final void mK_BLOB() throws RecognitionException {
		try {
			int _type = K_BLOB;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1732:7: ( B L O B )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1732:16: B L O B
			{
			mB(); 

			mL(); 

			mO(); 

			mB(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_BLOB"

	// $ANTLR start "K_BOOLEAN"
	public final void mK_BOOLEAN() throws RecognitionException {
		try {
			int _type = K_BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1733:10: ( B O O L E A N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1733:16: B O O L E A N
			{
			mB(); 

			mO(); 

			mO(); 

			mL(); 

			mE(); 

			mA(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_BOOLEAN"

	// $ANTLR start "K_COUNTER"
	public final void mK_COUNTER() throws RecognitionException {
		try {
			int _type = K_COUNTER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1734:10: ( C O U N T E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1734:16: C O U N T E R
			{
			mC(); 

			mO(); 

			mU(); 

			mN(); 

			mT(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_COUNTER"

	// $ANTLR start "K_DECIMAL"
	public final void mK_DECIMAL() throws RecognitionException {
		try {
			int _type = K_DECIMAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1735:10: ( D E C I M A L )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1735:16: D E C I M A L
			{
			mD(); 

			mE(); 

			mC(); 

			mI(); 

			mM(); 

			mA(); 

			mL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DECIMAL"

	// $ANTLR start "K_DOUBLE"
	public final void mK_DOUBLE() throws RecognitionException {
		try {
			int _type = K_DOUBLE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1736:9: ( D O U B L E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1736:16: D O U B L E
			{
			mD(); 

			mO(); 

			mU(); 

			mB(); 

			mL(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DOUBLE"

	// $ANTLR start "K_FLOAT"
	public final void mK_FLOAT() throws RecognitionException {
		try {
			int _type = K_FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1737:8: ( F L O A T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1737:16: F L O A T
			{
			mF(); 

			mL(); 

			mO(); 

			mA(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FLOAT"

	// $ANTLR start "K_INET"
	public final void mK_INET() throws RecognitionException {
		try {
			int _type = K_INET;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1738:7: ( I N E T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1738:16: I N E T
			{
			mI(); 

			mN(); 

			mE(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INET"

	// $ANTLR start "K_INT"
	public final void mK_INT() throws RecognitionException {
		try {
			int _type = K_INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1739:6: ( I N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1739:16: I N T
			{
			mI(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INT"

	// $ANTLR start "K_SMALLINT"
	public final void mK_SMALLINT() throws RecognitionException {
		try {
			int _type = K_SMALLINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1740:11: ( S M A L L I N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1740:16: S M A L L I N T
			{
			mS(); 

			mM(); 

			mA(); 

			mL(); 

			mL(); 

			mI(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_SMALLINT"

	// $ANTLR start "K_TINYINT"
	public final void mK_TINYINT() throws RecognitionException {
		try {
			int _type = K_TINYINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1741:10: ( T I N Y I N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1741:16: T I N Y I N T
			{
			mT(); 

			mI(); 

			mN(); 

			mY(); 

			mI(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TINYINT"

	// $ANTLR start "K_TEXT"
	public final void mK_TEXT() throws RecognitionException {
		try {
			int _type = K_TEXT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1742:7: ( T E X T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1742:16: T E X T
			{
			mT(); 

			mE(); 

			mX(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TEXT"

	// $ANTLR start "K_UUID"
	public final void mK_UUID() throws RecognitionException {
		try {
			int _type = K_UUID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1743:7: ( U U I D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1743:16: U U I D
			{
			mU(); 

			mU(); 

			mI(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_UUID"

	// $ANTLR start "K_VARCHAR"
	public final void mK_VARCHAR() throws RecognitionException {
		try {
			int _type = K_VARCHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1744:10: ( V A R C H A R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1744:16: V A R C H A R
			{
			mV(); 

			mA(); 

			mR(); 

			mC(); 

			mH(); 

			mA(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_VARCHAR"

	// $ANTLR start "K_VARINT"
	public final void mK_VARINT() throws RecognitionException {
		try {
			int _type = K_VARINT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1745:9: ( V A R I N T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1745:16: V A R I N T
			{
			mV(); 

			mA(); 

			mR(); 

			mI(); 

			mN(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_VARINT"

	// $ANTLR start "K_TIMEUUID"
	public final void mK_TIMEUUID() throws RecognitionException {
		try {
			int _type = K_TIMEUUID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1746:11: ( T I M E U U I D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1746:16: T I M E U U I D
			{
			mT(); 

			mI(); 

			mM(); 

			mE(); 

			mU(); 

			mU(); 

			mI(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TIMEUUID"

	// $ANTLR start "K_TOKEN"
	public final void mK_TOKEN() throws RecognitionException {
		try {
			int _type = K_TOKEN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1747:8: ( T O K E N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1747:16: T O K E N
			{
			mT(); 

			mO(); 

			mK(); 

			mE(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TOKEN"

	// $ANTLR start "K_WRITETIME"
	public final void mK_WRITETIME() throws RecognitionException {
		try {
			int _type = K_WRITETIME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1748:12: ( W R I T E T I M E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1748:16: W R I T E T I M E
			{
			mW(); 

			mR(); 

			mI(); 

			mT(); 

			mE(); 

			mT(); 

			mI(); 

			mM(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_WRITETIME"

	// $ANTLR start "K_DATE"
	public final void mK_DATE() throws RecognitionException {
		try {
			int _type = K_DATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1749:7: ( D A T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1749:16: D A T E
			{
			mD(); 

			mA(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_DATE"

	// $ANTLR start "K_TIME"
	public final void mK_TIME() throws RecognitionException {
		try {
			int _type = K_TIME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1750:7: ( T I M E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1750:16: T I M E
			{
			mT(); 

			mI(); 

			mM(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TIME"

	// $ANTLR start "K_NULL"
	public final void mK_NULL() throws RecognitionException {
		try {
			int _type = K_NULL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1752:7: ( N U L L )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1752:16: N U L L
			{
			mN(); 

			mU(); 

			mL(); 

			mL(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_NULL"

	// $ANTLR start "K_NOT"
	public final void mK_NOT() throws RecognitionException {
		try {
			int _type = K_NOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1753:6: ( N O T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1753:16: N O T
			{
			mN(); 

			mO(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_NOT"

	// $ANTLR start "K_EXISTS"
	public final void mK_EXISTS() throws RecognitionException {
		try {
			int _type = K_EXISTS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1754:9: ( E X I S T S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1754:16: E X I S T S
			{
			mE(); 

			mX(); 

			mI(); 

			mS(); 

			mT(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_EXISTS"

	// $ANTLR start "K_MAP"
	public final void mK_MAP() throws RecognitionException {
		try {
			int _type = K_MAP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1756:6: ( M A P )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1756:16: M A P
			{
			mM(); 

			mA(); 

			mP(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_MAP"

	// $ANTLR start "K_LIST"
	public final void mK_LIST() throws RecognitionException {
		try {
			int _type = K_LIST;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1757:7: ( L I S T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1757:16: L I S T
			{
			mL(); 

			mI(); 

			mS(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_LIST"

	// $ANTLR start "K_NAN"
	public final void mK_NAN() throws RecognitionException {
		try {
			int _type = K_NAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1758:6: ( N A N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1758:16: N A N
			{
			mN(); 

			mA(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_NAN"

	// $ANTLR start "K_INFINITY"
	public final void mK_INFINITY() throws RecognitionException {
		try {
			int _type = K_INFINITY;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1759:11: ( I N F I N I T Y )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1759:16: I N F I N I T Y
			{
			mI(); 

			mN(); 

			mF(); 

			mI(); 

			mN(); 

			mI(); 

			mT(); 

			mY(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INFINITY"

	// $ANTLR start "K_TUPLE"
	public final void mK_TUPLE() throws RecognitionException {
		try {
			int _type = K_TUPLE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1760:8: ( T U P L E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1760:16: T U P L E
			{
			mT(); 

			mU(); 

			mP(); 

			mL(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TUPLE"

	// $ANTLR start "K_TRIGGER"
	public final void mK_TRIGGER() throws RecognitionException {
		try {
			int _type = K_TRIGGER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1762:10: ( T R I G G E R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1762:16: T R I G G E R
			{
			mT(); 

			mR(); 

			mI(); 

			mG(); 

			mG(); 

			mE(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_TRIGGER"

	// $ANTLR start "K_STATIC"
	public final void mK_STATIC() throws RecognitionException {
		try {
			int _type = K_STATIC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1763:9: ( S T A T I C )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1763:16: S T A T I C
			{
			mS(); 

			mT(); 

			mA(); 

			mT(); 

			mI(); 

			mC(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_STATIC"

	// $ANTLR start "K_FROZEN"
	public final void mK_FROZEN() throws RecognitionException {
		try {
			int _type = K_FROZEN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1764:9: ( F R O Z E N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1764:16: F R O Z E N
			{
			mF(); 

			mR(); 

			mO(); 

			mZ(); 

			mE(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FROZEN"

	// $ANTLR start "K_FUNCTION"
	public final void mK_FUNCTION() throws RecognitionException {
		try {
			int _type = K_FUNCTION;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1766:11: ( F U N C T I O N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1766:16: F U N C T I O N
			{
			mF(); 

			mU(); 

			mN(); 

			mC(); 

			mT(); 

			mI(); 

			mO(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FUNCTION"

	// $ANTLR start "K_FUNCTIONS"
	public final void mK_FUNCTIONS() throws RecognitionException {
		try {
			int _type = K_FUNCTIONS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1767:12: ( F U N C T I O N S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1767:16: F U N C T I O N S
			{
			mF(); 

			mU(); 

			mN(); 

			mC(); 

			mT(); 

			mI(); 

			mO(); 

			mN(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FUNCTIONS"

	// $ANTLR start "K_AGGREGATE"
	public final void mK_AGGREGATE() throws RecognitionException {
		try {
			int _type = K_AGGREGATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1768:12: ( A G G R E G A T E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1768:16: A G G R E G A T E
			{
			mA(); 

			mG(); 

			mG(); 

			mR(); 

			mE(); 

			mG(); 

			mA(); 

			mT(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_AGGREGATE"

	// $ANTLR start "K_SFUNC"
	public final void mK_SFUNC() throws RecognitionException {
		try {
			int _type = K_SFUNC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1769:8: ( S F U N C )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1769:16: S F U N C
			{
			mS(); 

			mF(); 

			mU(); 

			mN(); 

			mC(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_SFUNC"

	// $ANTLR start "K_STYPE"
	public final void mK_STYPE() throws RecognitionException {
		try {
			int _type = K_STYPE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1770:8: ( S T Y P E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1770:16: S T Y P E
			{
			mS(); 

			mT(); 

			mY(); 

			mP(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_STYPE"

	// $ANTLR start "K_FINALFUNC"
	public final void mK_FINALFUNC() throws RecognitionException {
		try {
			int _type = K_FINALFUNC;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1771:12: ( F I N A L F U N C )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1771:16: F I N A L F U N C
			{
			mF(); 

			mI(); 

			mN(); 

			mA(); 

			mL(); 

			mF(); 

			mU(); 

			mN(); 

			mC(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_FINALFUNC"

	// $ANTLR start "K_INITCOND"
	public final void mK_INITCOND() throws RecognitionException {
		try {
			int _type = K_INITCOND;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1772:11: ( I N I T C O N D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1772:16: I N I T C O N D
			{
			mI(); 

			mN(); 

			mI(); 

			mT(); 

			mC(); 

			mO(); 

			mN(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INITCOND"

	// $ANTLR start "K_RETURNS"
	public final void mK_RETURNS() throws RecognitionException {
		try {
			int _type = K_RETURNS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1773:10: ( R E T U R N S )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1773:16: R E T U R N S
			{
			mR(); 

			mE(); 

			mT(); 

			mU(); 

			mR(); 

			mN(); 

			mS(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_RETURNS"

	// $ANTLR start "K_CALLED"
	public final void mK_CALLED() throws RecognitionException {
		try {
			int _type = K_CALLED;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1774:9: ( C A L L E D )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1774:16: C A L L E D
			{
			mC(); 

			mA(); 

			mL(); 

			mL(); 

			mE(); 

			mD(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_CALLED"

	// $ANTLR start "K_INPUT"
	public final void mK_INPUT() throws RecognitionException {
		try {
			int _type = K_INPUT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1775:8: ( I N P U T )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1775:16: I N P U T
			{
			mI(); 

			mN(); 

			mP(); 

			mU(); 

			mT(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_INPUT"

	// $ANTLR start "K_LANGUAGE"
	public final void mK_LANGUAGE() throws RecognitionException {
		try {
			int _type = K_LANGUAGE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1776:11: ( L A N G U A G E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1776:16: L A N G U A G E
			{
			mL(); 

			mA(); 

			mN(); 

			mG(); 

			mU(); 

			mA(); 

			mG(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_LANGUAGE"

	// $ANTLR start "K_OR"
	public final void mK_OR() throws RecognitionException {
		try {
			int _type = K_OR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1777:5: ( O R )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1777:16: O R
			{
			mO(); 

			mR(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_OR"

	// $ANTLR start "K_REPLACE"
	public final void mK_REPLACE() throws RecognitionException {
		try {
			int _type = K_REPLACE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1778:10: ( R E P L A C E )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1778:16: R E P L A C E
			{
			mR(); 

			mE(); 

			mP(); 

			mL(); 

			mA(); 

			mC(); 

			mE(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_REPLACE"

	// $ANTLR start "K_JSON"
	public final void mK_JSON() throws RecognitionException {
		try {
			int _type = K_JSON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1780:7: ( J S O N )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1780:16: J S O N
			{
			mJ(); 

			mS(); 

			mO(); 

			mN(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K_JSON"

	// $ANTLR start "A"
	public final void mA() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1783:11: ( ( 'a' | 'A' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "A"

	// $ANTLR start "B"
	public final void mB() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1784:11: ( ( 'b' | 'B' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "B"

	// $ANTLR start "C"
	public final void mC() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1785:11: ( ( 'c' | 'C' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "C"

	// $ANTLR start "D"
	public final void mD() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1786:11: ( ( 'd' | 'D' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "D"

	// $ANTLR start "E"
	public final void mE() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1787:11: ( ( 'e' | 'E' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "E"

	// $ANTLR start "F"
	public final void mF() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1788:11: ( ( 'f' | 'F' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "F"

	// $ANTLR start "G"
	public final void mG() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1789:11: ( ( 'g' | 'G' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "G"

	// $ANTLR start "H"
	public final void mH() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1790:11: ( ( 'h' | 'H' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "H"

	// $ANTLR start "I"
	public final void mI() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1791:11: ( ( 'i' | 'I' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "I"

	// $ANTLR start "J"
	public final void mJ() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1792:11: ( ( 'j' | 'J' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "J"

	// $ANTLR start "K"
	public final void mK() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1793:11: ( ( 'k' | 'K' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "K"

	// $ANTLR start "L"
	public final void mL() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1794:11: ( ( 'l' | 'L' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "L"

	// $ANTLR start "M"
	public final void mM() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1795:11: ( ( 'm' | 'M' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "M"

	// $ANTLR start "N"
	public final void mN() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1796:11: ( ( 'n' | 'N' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "N"

	// $ANTLR start "O"
	public final void mO() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1797:11: ( ( 'o' | 'O' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "O"

	// $ANTLR start "P"
	public final void mP() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1798:11: ( ( 'p' | 'P' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "P"

	// $ANTLR start "Q"
	public final void mQ() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1799:11: ( ( 'q' | 'Q' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Q"

	// $ANTLR start "R"
	public final void mR() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1800:11: ( ( 'r' | 'R' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "R"

	// $ANTLR start "S"
	public final void mS() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1801:11: ( ( 's' | 'S' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "S"

	// $ANTLR start "T"
	public final void mT() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1802:11: ( ( 't' | 'T' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T"

	// $ANTLR start "U"
	public final void mU() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1803:11: ( ( 'u' | 'U' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "U"

	// $ANTLR start "V"
	public final void mV() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1804:11: ( ( 'v' | 'V' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "V"

	// $ANTLR start "W"
	public final void mW() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1805:11: ( ( 'w' | 'W' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "W"

	// $ANTLR start "X"
	public final void mX() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1806:11: ( ( 'x' | 'X' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "X"

	// $ANTLR start "Y"
	public final void mY() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1807:11: ( ( 'y' | 'Y' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Y"

	// $ANTLR start "Z"
	public final void mZ() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1808:11: ( ( 'z' | 'Z' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( input.LA(1)=='Z'||input.LA(1)=='z' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "Z"

	// $ANTLR start "STRING_LITERAL"
	public final void mSTRING_LITERAL() throws RecognitionException {
		try {
			int _type = STRING_LITERAL;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			int c;


			        StringBuilder txt = new StringBuilder(); // temporary to build pg-style-string
			    
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1815:5: ( ( '\\$' '\\$' ({...}? =>c= . )* '\\$' '\\$' ) | ( '\\'' (c=~ ( '\\'' ) | '\\'' '\\'' )* '\\'' ) )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0=='$') ) {
				alt5=1;
			}
			else if ( (LA5_0=='\'') ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1817:7: ( '\\$' '\\$' ({...}? =>c= . )* '\\$' '\\$' )
					{
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1817:7: ( '\\$' '\\$' ({...}? =>c= . )* '\\$' '\\$' )
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1818:9: '\\$' '\\$' ({...}? =>c= . )* '\\$' '\\$'
					{
					match('$'); 
					match('$'); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1819:9: ({...}? =>c= . )*
					loop3:
					while (true) {
						int alt3=2;
						int LA3_0 = input.LA(1);
						if ( (LA3_0=='$') ) {
							int LA3_1 = input.LA(2);
							if ( (LA3_1=='$') ) {
								int LA3_3 = input.LA(3);
								if ( ((LA3_3 >= '\u0000' && LA3_3 <= '\uFFFF')) && ((  (input.size() - input.index() > 1)
								               && !"$$".equals(input.substring(input.index(), input.index() + 1)) ))) {
									alt3=1;
								}

							}
							else if ( ((LA3_1 >= '\u0000' && LA3_1 <= '#')||(LA3_1 >= '%' && LA3_1 <= '\uFFFF')) && ((  (input.size() - input.index() > 1)
							               && !"$$".equals(input.substring(input.index(), input.index() + 1)) ))) {
								alt3=1;
							}

						}
						else if ( ((LA3_0 >= '\u0000' && LA3_0 <= '#')||(LA3_0 >= '%' && LA3_0 <= '\uFFFF')) && ((  (input.size() - input.index() > 1)
						               && !"$$".equals(input.substring(input.index(), input.index() + 1)) ))) {
							alt3=1;
						}

						switch (alt3) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1820:11: {...}? =>c= .
							{
							if ( !((  (input.size() - input.index() > 1)
							               && !"$$".equals(input.substring(input.index(), input.index() + 1)) )) ) {
								throw new FailedPredicateException(input, "STRING_LITERAL", "  (input.size() - input.index() > 1)\n               && !\"$$\".equals(input.substring(input.index(), input.index() + 1)) ");
							}
							c = input.LA(1);
							matchAny(); 
							 txt.appendCodePoint(c); 
							}
							break;

						default :
							break loop3;
						}
					}

					match('$'); 
					match('$'); 
					}

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1828:7: ( '\\'' (c=~ ( '\\'' ) | '\\'' '\\'' )* '\\'' )
					{
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1828:7: ( '\\'' (c=~ ( '\\'' ) | '\\'' '\\'' )* '\\'' )
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1829:9: '\\'' (c=~ ( '\\'' ) | '\\'' '\\'' )* '\\''
					{
					match('\''); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1829:14: (c=~ ( '\\'' ) | '\\'' '\\'' )*
					loop4:
					while (true) {
						int alt4=3;
						int LA4_0 = input.LA(1);
						if ( (LA4_0=='\'') ) {
							int LA4_1 = input.LA(2);
							if ( (LA4_1=='\'') ) {
								alt4=2;
							}

						}
						else if ( ((LA4_0 >= '\u0000' && LA4_0 <= '&')||(LA4_0 >= '(' && LA4_0 <= '\uFFFF')) ) {
							alt4=1;
						}

						switch (alt4) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1829:15: c=~ ( '\\'' )
							{
							c= input.LA(1);
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							 txt.appendCodePoint(c);
							}
							break;
						case 2 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1829:54: '\\'' '\\''
							{
							match('\''); 
							match('\''); 
							 txt.appendCodePoint('\''); 
							}
							break;

						default :
							break loop4;
						}
					}

					match('\''); 
					}

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
			 setText(txt.toString()); 
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING_LITERAL"

	// $ANTLR start "QUOTED_NAME"
	public final void mQUOTED_NAME() throws RecognitionException {
		try {
			int _type = QUOTED_NAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			int c;

			 StringBuilder b = new StringBuilder(); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1836:5: ( '\\\"' (c=~ ( '\\\"' ) | '\\\"' '\\\"' )+ '\\\"' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1836:7: '\\\"' (c=~ ( '\\\"' ) | '\\\"' '\\\"' )+ '\\\"'
			{
			match('\"'); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1836:12: (c=~ ( '\\\"' ) | '\\\"' '\\\"' )+
			int cnt6=0;
			loop6:
			while (true) {
				int alt6=3;
				int LA6_0 = input.LA(1);
				if ( (LA6_0=='\"') ) {
					int LA6_1 = input.LA(2);
					if ( (LA6_1=='\"') ) {
						alt6=2;
					}

				}
				else if ( ((LA6_0 >= '\u0000' && LA6_0 <= '!')||(LA6_0 >= '#' && LA6_0 <= '\uFFFF')) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1836:13: c=~ ( '\\\"' )
					{
					c= input.LA(1);
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					 b.appendCodePoint(c); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1836:51: '\\\"' '\\\"'
					{
					match('\"'); 
					match('\"'); 
					 b.appendCodePoint('\"'); 
					}
					break;

				default :
					if ( cnt6 >= 1 ) break loop6;
					EarlyExitException eee = new EarlyExitException(6, input);
					throw eee;
				}
				cnt6++;
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
			 setText(b.toString()); 
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "QUOTED_NAME"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1840:5: ( '0' .. '9' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "LETTER"
	public final void mLETTER() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1844:5: ( ( 'A' .. 'Z' | 'a' .. 'z' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "LETTER"

	// $ANTLR start "HEX"
	public final void mHEX() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1848:5: ( ( 'A' .. 'F' | 'a' .. 'f' | '0' .. '9' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX"

	// $ANTLR start "EXPONENT"
	public final void mEXPONENT() throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1852:5: ( E ( '+' | '-' )? ( DIGIT )+ )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1852:7: E ( '+' | '-' )? ( DIGIT )+
			{
			mE(); 

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1852:9: ( '+' | '-' )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0=='+'||LA7_0=='-') ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
					{
					if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1852:22: ( DIGIT )+
			int cnt8=0;
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt8 >= 1 ) break loop8;
					EarlyExitException eee = new EarlyExitException(8, input);
					throw eee;
				}
				cnt8++;
			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EXPONENT"

	// $ANTLR start "INTEGER"
	public final void mINTEGER() throws RecognitionException {
		try {
			int _type = INTEGER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1856:5: ( ( '-' )? ( DIGIT )+ )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1856:7: ( '-' )? ( DIGIT )+
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1856:7: ( '-' )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0=='-') ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1856:7: '-'
					{
					match('-'); 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1856:12: ( DIGIT )+
			int cnt10=0;
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( ((LA10_0 >= '0' && LA10_0 <= '9')) ) {
					alt10=1;
				}

				switch (alt10) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt10 >= 1 ) break loop10;
					EarlyExitException eee = new EarlyExitException(10, input);
					throw eee;
				}
				cnt10++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGER"

	// $ANTLR start "QMARK"
	public final void mQMARK() throws RecognitionException {
		try {
			int _type = QMARK;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1860:5: ( '?' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1860:7: '?'
			{
			match('?'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "QMARK"

	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException {
		try {
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1868:5: ( INTEGER EXPONENT | INTEGER '.' ( DIGIT )* ( EXPONENT )? )
			int alt13=2;
			alt13 = dfa13.predict(input);
			switch (alt13) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1868:7: INTEGER EXPONENT
					{
					mINTEGER(); 

					mEXPONENT(); 

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1869:7: INTEGER '.' ( DIGIT )* ( EXPONENT )?
					{
					mINTEGER(); 

					match('.'); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1869:19: ( DIGIT )*
					loop11:
					while (true) {
						int alt11=2;
						int LA11_0 = input.LA(1);
						if ( ((LA11_0 >= '0' && LA11_0 <= '9')) ) {
							alt11=1;
						}

						switch (alt11) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop11;
						}
					}

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1869:26: ( EXPONENT )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0=='E'||LA12_0=='e') ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1869:26: EXPONENT
							{
							mEXPONENT(); 

							}
							break;

					}

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT"

	// $ANTLR start "BOOLEAN"
	public final void mBOOLEAN() throws RecognitionException {
		try {
			int _type = BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1876:5: ( T R U E | F A L S E )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0=='T'||LA14_0=='t') ) {
				alt14=1;
			}
			else if ( (LA14_0=='F'||LA14_0=='f') ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1876:7: T R U E
					{
					mT(); 

					mR(); 

					mU(); 

					mE(); 

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1876:17: F A L S E
					{
					mF(); 

					mA(); 

					mL(); 

					mS(); 

					mE(); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOLEAN"

	// $ANTLR start "IDENT"
	public final void mIDENT() throws RecognitionException {
		try {
			int _type = IDENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1880:5: ( LETTER ( LETTER | DIGIT | '_' )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1880:7: LETTER ( LETTER | DIGIT | '_' )*
			{
			mLETTER(); 

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1880:14: ( LETTER | DIGIT | '_' )*
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( ((LA15_0 >= '0' && LA15_0 <= '9')||(LA15_0 >= 'A' && LA15_0 <= 'Z')||LA15_0=='_'||(LA15_0 >= 'a' && LA15_0 <= 'z')) ) {
					alt15=1;
				}

				switch (alt15) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop15;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IDENT"

	// $ANTLR start "HEXNUMBER"
	public final void mHEXNUMBER() throws RecognitionException {
		try {
			int _type = HEXNUMBER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1884:5: ( '0' X ( HEX )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1884:7: '0' X ( HEX )*
			{
			match('0'); 
			mX(); 

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1884:13: ( HEX )*
			loop16:
			while (true) {
				int alt16=2;
				int LA16_0 = input.LA(1);
				if ( ((LA16_0 >= '0' && LA16_0 <= '9')||(LA16_0 >= 'A' && LA16_0 <= 'F')||(LA16_0 >= 'a' && LA16_0 <= 'f')) ) {
					alt16=1;
				}

				switch (alt16) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop16;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEXNUMBER"

	// $ANTLR start "UUID"
	public final void mUUID() throws RecognitionException {
		try {
			int _type = UUID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1888:5: ( HEX HEX HEX HEX HEX HEX HEX HEX '-' HEX HEX HEX HEX '-' HEX HEX HEX HEX '-' HEX HEX HEX HEX '-' HEX HEX HEX HEX HEX HEX HEX HEX HEX HEX HEX HEX )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1888:7: HEX HEX HEX HEX HEX HEX HEX HEX '-' HEX HEX HEX HEX '-' HEX HEX HEX HEX '-' HEX HEX HEX HEX '-' HEX HEX HEX HEX HEX HEX HEX HEX HEX HEX HEX HEX
			{
			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			match('-'); 
			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			match('-'); 
			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			match('-'); 
			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			match('-'); 
			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			mHEX(); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UUID"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1896:5: ( ( ' ' | '\\t' | '\\n' | '\\r' )+ )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1896:7: ( ' ' | '\\t' | '\\n' | '\\r' )+
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1896:7: ( ' ' | '\\t' | '\\n' | '\\r' )+
			int cnt17=0;
			loop17:
			while (true) {
				int alt17=2;
				int LA17_0 = input.LA(1);
				if ( ((LA17_0 >= '\t' && LA17_0 <= '\n')||LA17_0=='\r'||LA17_0==' ') ) {
					alt17=1;
				}

				switch (alt17) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt17 >= 1 ) break loop17;
					EarlyExitException eee = new EarlyExitException(17, input);
					throw eee;
				}
				cnt17++;
			}

			 _channel = HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:5: ( ( '--' | '//' ) ( . )* ( '\\n' | '\\r' ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:7: ( '--' | '//' ) ( . )* ( '\\n' | '\\r' )
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:7: ( '--' | '//' )
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0=='-') ) {
				alt18=1;
			}
			else if ( (LA18_0=='/') ) {
				alt18=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				throw nvae;
			}

			switch (alt18) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:8: '--'
					{
					match("--"); 

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:15: '//'
					{
					match("//"); 

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:21: ( . )*
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0=='\n'||LA19_0=='\r') ) {
					alt19=2;
				}
				else if ( ((LA19_0 >= '\u0000' && LA19_0 <= '\t')||(LA19_0 >= '\u000B' && LA19_0 <= '\f')||(LA19_0 >= '\u000E' && LA19_0 <= '\uFFFF')) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1900:21: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop19;
				}
			}

			if ( input.LA(1)=='\n'||input.LA(1)=='\r' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			 _channel = HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "MULTILINE_COMMENT"
	public final void mMULTILINE_COMMENT() throws RecognitionException {
		try {
			int _type = MULTILINE_COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1904:5: ( '/*' ( . )* '*/' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1904:7: '/*' ( . )* '*/'
			{
			match("/*"); 

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1904:12: ( . )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( (LA20_0=='*') ) {
					int LA20_1 = input.LA(2);
					if ( (LA20_1=='/') ) {
						alt20=2;
					}
					else if ( ((LA20_1 >= '\u0000' && LA20_1 <= '.')||(LA20_1 >= '0' && LA20_1 <= '\uFFFF')) ) {
						alt20=1;
					}

				}
				else if ( ((LA20_0 >= '\u0000' && LA20_0 <= ')')||(LA20_0 >= '+' && LA20_0 <= '\uFFFF')) ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1904:12: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop20;
				}
			}

			match("*/"); 

			 _channel = HIDDEN; 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "MULTILINE_COMMENT"

	@Override
	public void mTokens() throws RecognitionException {
		// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:8: ( T__173 | T__174 | T__175 | T__176 | T__177 | T__178 | T__179 | T__180 | T__181 | T__182 | T__183 | T__184 | T__185 | T__186 | T__187 | T__188 | T__189 | T__190 | T__191 | T__192 | K_SELECT | K_FROM | K_AS | K_WHERE | K_AND | K_KEY | K_KEYS | K_ENTRIES | K_FULL | K_INSERT | K_UPDATE | K_WITH | K_LIMIT | K_LIKE | K_PER | K_PARTITION | K_USING | K_USE | K_DISTINCT | K_COUNT | K_SET | K_BEGIN | K_UNLOGGED | K_BATCH | K_APPLY | K_TRUNCATE | K_DELETE | K_IN | K_CREATE | K_KEYSPACE | K_KEYSPACES | K_COLUMNFAMILY | K_MATERIALIZED | K_VIEW | K_INDEX | K_CUSTOM | K_ON | K_TO | K_DROP | K_PRIMARY | K_INTO | K_VALUES | K_TIMESTAMP | K_TTL | K_ALTER | K_RENAME | K_ADD | K_TYPE | K_COMPACT | K_STORAGE | K_ORDER | K_BY | K_ASC | K_DESC | K_ALLOW | K_FILTERING | K_IF | K_IS | K_CONTAINS | K_GRANT | K_ALL | K_PERMISSION | K_PERMISSIONS | K_OF | K_REVOKE | K_MODIFY | K_AUTHORIZE | K_DESCRIBE | K_EXECUTE | K_NORECURSIVE | K_USER | K_USERS | K_ROLE | K_ROLES | K_SUPERUSER | K_NOSUPERUSER | K_PASSWORD | K_LOGIN | K_NOLOGIN | K_OPTIONS | K_CLUSTERING | K_ASCII | K_BIGINT | K_BLOB | K_BOOLEAN | K_COUNTER | K_DECIMAL | K_DOUBLE | K_FLOAT | K_INET | K_INT | K_SMALLINT | K_TINYINT | K_TEXT | K_UUID | K_VARCHAR | K_VARINT | K_TIMEUUID | K_TOKEN | K_WRITETIME | K_DATE | K_TIME | K_NULL | K_NOT | K_EXISTS | K_MAP | K_LIST | K_NAN | K_INFINITY | K_TUPLE | K_TRIGGER | K_STATIC | K_FROZEN | K_FUNCTION | K_FUNCTIONS | K_AGGREGATE | K_SFUNC | K_STYPE | K_FINALFUNC | K_INITCOND | K_RETURNS | K_CALLED | K_INPUT | K_LANGUAGE | K_OR | K_REPLACE | K_JSON | STRING_LITERAL | QUOTED_NAME | INTEGER | QMARK | FLOAT | BOOLEAN | IDENT | HEXNUMBER | UUID | WS | COMMENT | MULTILINE_COMMENT )
		int alt21=159;
		alt21 = dfa21.predict(input);
		switch (alt21) {
			case 1 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:10: T__173
				{
				mT__173(); 

				}
				break;
			case 2 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:17: T__174
				{
				mT__174(); 

				}
				break;
			case 3 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:24: T__175
				{
				mT__175(); 

				}
				break;
			case 4 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:31: T__176
				{
				mT__176(); 

				}
				break;
			case 5 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:38: T__177
				{
				mT__177(); 

				}
				break;
			case 6 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:45: T__178
				{
				mT__178(); 

				}
				break;
			case 7 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:52: T__179
				{
				mT__179(); 

				}
				break;
			case 8 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:59: T__180
				{
				mT__180(); 

				}
				break;
			case 9 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:66: T__181
				{
				mT__181(); 

				}
				break;
			case 10 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:73: T__182
				{
				mT__182(); 

				}
				break;
			case 11 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:80: T__183
				{
				mT__183(); 

				}
				break;
			case 12 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:87: T__184
				{
				mT__184(); 

				}
				break;
			case 13 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:94: T__185
				{
				mT__185(); 

				}
				break;
			case 14 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:101: T__186
				{
				mT__186(); 

				}
				break;
			case 15 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:108: T__187
				{
				mT__187(); 

				}
				break;
			case 16 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:115: T__188
				{
				mT__188(); 

				}
				break;
			case 17 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:122: T__189
				{
				mT__189(); 

				}
				break;
			case 18 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:129: T__190
				{
				mT__190(); 

				}
				break;
			case 19 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:136: T__191
				{
				mT__191(); 

				}
				break;
			case 20 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:143: T__192
				{
				mT__192(); 

				}
				break;
			case 21 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:150: K_SELECT
				{
				mK_SELECT(); 

				}
				break;
			case 22 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:159: K_FROM
				{
				mK_FROM(); 

				}
				break;
			case 23 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:166: K_AS
				{
				mK_AS(); 

				}
				break;
			case 24 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:171: K_WHERE
				{
				mK_WHERE(); 

				}
				break;
			case 25 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:179: K_AND
				{
				mK_AND(); 

				}
				break;
			case 26 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:185: K_KEY
				{
				mK_KEY(); 

				}
				break;
			case 27 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:191: K_KEYS
				{
				mK_KEYS(); 

				}
				break;
			case 28 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:198: K_ENTRIES
				{
				mK_ENTRIES(); 

				}
				break;
			case 29 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:208: K_FULL
				{
				mK_FULL(); 

				}
				break;
			case 30 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:215: K_INSERT
				{
				mK_INSERT(); 

				}
				break;
			case 31 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:224: K_UPDATE
				{
				mK_UPDATE(); 

				}
				break;
			case 32 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:233: K_WITH
				{
				mK_WITH(); 

				}
				break;
			case 33 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:240: K_LIMIT
				{
				mK_LIMIT(); 

				}
				break;
			case 34 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:248: K_LIKE
				{
				mK_LIKE(); 

				}
				break;
			case 35 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:255: K_PER
				{
				mK_PER(); 

				}
				break;
			case 36 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:261: K_PARTITION
				{
				mK_PARTITION(); 

				}
				break;
			case 37 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:273: K_USING
				{
				mK_USING(); 

				}
				break;
			case 38 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:281: K_USE
				{
				mK_USE(); 

				}
				break;
			case 39 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:287: K_DISTINCT
				{
				mK_DISTINCT(); 

				}
				break;
			case 40 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:298: K_COUNT
				{
				mK_COUNT(); 

				}
				break;
			case 41 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:306: K_SET
				{
				mK_SET(); 

				}
				break;
			case 42 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:312: K_BEGIN
				{
				mK_BEGIN(); 

				}
				break;
			case 43 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:320: K_UNLOGGED
				{
				mK_UNLOGGED(); 

				}
				break;
			case 44 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:331: K_BATCH
				{
				mK_BATCH(); 

				}
				break;
			case 45 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:339: K_APPLY
				{
				mK_APPLY(); 

				}
				break;
			case 46 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:347: K_TRUNCATE
				{
				mK_TRUNCATE(); 

				}
				break;
			case 47 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:358: K_DELETE
				{
				mK_DELETE(); 

				}
				break;
			case 48 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:367: K_IN
				{
				mK_IN(); 

				}
				break;
			case 49 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:372: K_CREATE
				{
				mK_CREATE(); 

				}
				break;
			case 50 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:381: K_KEYSPACE
				{
				mK_KEYSPACE(); 

				}
				break;
			case 51 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:392: K_KEYSPACES
				{
				mK_KEYSPACES(); 

				}
				break;
			case 52 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:404: K_COLUMNFAMILY
				{
				mK_COLUMNFAMILY(); 

				}
				break;
			case 53 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:419: K_MATERIALIZED
				{
				mK_MATERIALIZED(); 

				}
				break;
			case 54 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:434: K_VIEW
				{
				mK_VIEW(); 

				}
				break;
			case 55 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:441: K_INDEX
				{
				mK_INDEX(); 

				}
				break;
			case 56 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:449: K_CUSTOM
				{
				mK_CUSTOM(); 

				}
				break;
			case 57 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:458: K_ON
				{
				mK_ON(); 

				}
				break;
			case 58 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:463: K_TO
				{
				mK_TO(); 

				}
				break;
			case 59 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:468: K_DROP
				{
				mK_DROP(); 

				}
				break;
			case 60 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:475: K_PRIMARY
				{
				mK_PRIMARY(); 

				}
				break;
			case 61 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:485: K_INTO
				{
				mK_INTO(); 

				}
				break;
			case 62 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:492: K_VALUES
				{
				mK_VALUES(); 

				}
				break;
			case 63 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:501: K_TIMESTAMP
				{
				mK_TIMESTAMP(); 

				}
				break;
			case 64 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:513: K_TTL
				{
				mK_TTL(); 

				}
				break;
			case 65 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:519: K_ALTER
				{
				mK_ALTER(); 

				}
				break;
			case 66 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:527: K_RENAME
				{
				mK_RENAME(); 

				}
				break;
			case 67 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:536: K_ADD
				{
				mK_ADD(); 

				}
				break;
			case 68 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:542: K_TYPE
				{
				mK_TYPE(); 

				}
				break;
			case 69 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:549: K_COMPACT
				{
				mK_COMPACT(); 

				}
				break;
			case 70 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:559: K_STORAGE
				{
				mK_STORAGE(); 

				}
				break;
			case 71 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:569: K_ORDER
				{
				mK_ORDER(); 

				}
				break;
			case 72 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:577: K_BY
				{
				mK_BY(); 

				}
				break;
			case 73 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:582: K_ASC
				{
				mK_ASC(); 

				}
				break;
			case 74 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:588: K_DESC
				{
				mK_DESC(); 

				}
				break;
			case 75 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:595: K_ALLOW
				{
				mK_ALLOW(); 

				}
				break;
			case 76 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:603: K_FILTERING
				{
				mK_FILTERING(); 

				}
				break;
			case 77 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:615: K_IF
				{
				mK_IF(); 

				}
				break;
			case 78 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:620: K_IS
				{
				mK_IS(); 

				}
				break;
			case 79 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:625: K_CONTAINS
				{
				mK_CONTAINS(); 

				}
				break;
			case 80 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:636: K_GRANT
				{
				mK_GRANT(); 

				}
				break;
			case 81 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:644: K_ALL
				{
				mK_ALL(); 

				}
				break;
			case 82 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:650: K_PERMISSION
				{
				mK_PERMISSION(); 

				}
				break;
			case 83 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:663: K_PERMISSIONS
				{
				mK_PERMISSIONS(); 

				}
				break;
			case 84 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:677: K_OF
				{
				mK_OF(); 

				}
				break;
			case 85 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:682: K_REVOKE
				{
				mK_REVOKE(); 

				}
				break;
			case 86 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:691: K_MODIFY
				{
				mK_MODIFY(); 

				}
				break;
			case 87 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:700: K_AUTHORIZE
				{
				mK_AUTHORIZE(); 

				}
				break;
			case 88 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:712: K_DESCRIBE
				{
				mK_DESCRIBE(); 

				}
				break;
			case 89 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:723: K_EXECUTE
				{
				mK_EXECUTE(); 

				}
				break;
			case 90 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:733: K_NORECURSIVE
				{
				mK_NORECURSIVE(); 

				}
				break;
			case 91 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:747: K_USER
				{
				mK_USER(); 

				}
				break;
			case 92 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:754: K_USERS
				{
				mK_USERS(); 

				}
				break;
			case 93 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:762: K_ROLE
				{
				mK_ROLE(); 

				}
				break;
			case 94 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:769: K_ROLES
				{
				mK_ROLES(); 

				}
				break;
			case 95 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:777: K_SUPERUSER
				{
				mK_SUPERUSER(); 

				}
				break;
			case 96 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:789: K_NOSUPERUSER
				{
				mK_NOSUPERUSER(); 

				}
				break;
			case 97 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:803: K_PASSWORD
				{
				mK_PASSWORD(); 

				}
				break;
			case 98 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:814: K_LOGIN
				{
				mK_LOGIN(); 

				}
				break;
			case 99 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:822: K_NOLOGIN
				{
				mK_NOLOGIN(); 

				}
				break;
			case 100 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:832: K_OPTIONS
				{
				mK_OPTIONS(); 

				}
				break;
			case 101 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:842: K_CLUSTERING
				{
				mK_CLUSTERING(); 

				}
				break;
			case 102 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:855: K_ASCII
				{
				mK_ASCII(); 

				}
				break;
			case 103 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:863: K_BIGINT
				{
				mK_BIGINT(); 

				}
				break;
			case 104 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:872: K_BLOB
				{
				mK_BLOB(); 

				}
				break;
			case 105 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:879: K_BOOLEAN
				{
				mK_BOOLEAN(); 

				}
				break;
			case 106 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:889: K_COUNTER
				{
				mK_COUNTER(); 

				}
				break;
			case 107 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:899: K_DECIMAL
				{
				mK_DECIMAL(); 

				}
				break;
			case 108 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:909: K_DOUBLE
				{
				mK_DOUBLE(); 

				}
				break;
			case 109 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:918: K_FLOAT
				{
				mK_FLOAT(); 

				}
				break;
			case 110 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:926: K_INET
				{
				mK_INET(); 

				}
				break;
			case 111 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:933: K_INT
				{
				mK_INT(); 

				}
				break;
			case 112 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:939: K_SMALLINT
				{
				mK_SMALLINT(); 

				}
				break;
			case 113 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:950: K_TINYINT
				{
				mK_TINYINT(); 

				}
				break;
			case 114 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:960: K_TEXT
				{
				mK_TEXT(); 

				}
				break;
			case 115 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:967: K_UUID
				{
				mK_UUID(); 

				}
				break;
			case 116 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:974: K_VARCHAR
				{
				mK_VARCHAR(); 

				}
				break;
			case 117 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:984: K_VARINT
				{
				mK_VARINT(); 

				}
				break;
			case 118 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:993: K_TIMEUUID
				{
				mK_TIMEUUID(); 

				}
				break;
			case 119 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1004: K_TOKEN
				{
				mK_TOKEN(); 

				}
				break;
			case 120 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1012: K_WRITETIME
				{
				mK_WRITETIME(); 

				}
				break;
			case 121 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1024: K_DATE
				{
				mK_DATE(); 

				}
				break;
			case 122 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1031: K_TIME
				{
				mK_TIME(); 

				}
				break;
			case 123 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1038: K_NULL
				{
				mK_NULL(); 

				}
				break;
			case 124 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1045: K_NOT
				{
				mK_NOT(); 

				}
				break;
			case 125 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1051: K_EXISTS
				{
				mK_EXISTS(); 

				}
				break;
			case 126 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1060: K_MAP
				{
				mK_MAP(); 

				}
				break;
			case 127 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1066: K_LIST
				{
				mK_LIST(); 

				}
				break;
			case 128 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1073: K_NAN
				{
				mK_NAN(); 

				}
				break;
			case 129 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1079: K_INFINITY
				{
				mK_INFINITY(); 

				}
				break;
			case 130 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1090: K_TUPLE
				{
				mK_TUPLE(); 

				}
				break;
			case 131 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1098: K_TRIGGER
				{
				mK_TRIGGER(); 

				}
				break;
			case 132 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1108: K_STATIC
				{
				mK_STATIC(); 

				}
				break;
			case 133 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1117: K_FROZEN
				{
				mK_FROZEN(); 

				}
				break;
			case 134 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1126: K_FUNCTION
				{
				mK_FUNCTION(); 

				}
				break;
			case 135 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1137: K_FUNCTIONS
				{
				mK_FUNCTIONS(); 

				}
				break;
			case 136 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1149: K_AGGREGATE
				{
				mK_AGGREGATE(); 

				}
				break;
			case 137 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1161: K_SFUNC
				{
				mK_SFUNC(); 

				}
				break;
			case 138 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1169: K_STYPE
				{
				mK_STYPE(); 

				}
				break;
			case 139 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1177: K_FINALFUNC
				{
				mK_FINALFUNC(); 

				}
				break;
			case 140 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1189: K_INITCOND
				{
				mK_INITCOND(); 

				}
				break;
			case 141 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1200: K_RETURNS
				{
				mK_RETURNS(); 

				}
				break;
			case 142 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1210: K_CALLED
				{
				mK_CALLED(); 

				}
				break;
			case 143 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1219: K_INPUT
				{
				mK_INPUT(); 

				}
				break;
			case 144 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1227: K_LANGUAGE
				{
				mK_LANGUAGE(); 

				}
				break;
			case 145 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1238: K_OR
				{
				mK_OR(); 

				}
				break;
			case 146 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1243: K_REPLACE
				{
				mK_REPLACE(); 

				}
				break;
			case 147 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1253: K_JSON
				{
				mK_JSON(); 

				}
				break;
			case 148 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1260: STRING_LITERAL
				{
				mSTRING_LITERAL(); 

				}
				break;
			case 149 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1275: QUOTED_NAME
				{
				mQUOTED_NAME(); 

				}
				break;
			case 150 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1287: INTEGER
				{
				mINTEGER(); 

				}
				break;
			case 151 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1295: QMARK
				{
				mQMARK(); 

				}
				break;
			case 152 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1301: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 153 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1307: BOOLEAN
				{
				mBOOLEAN(); 

				}
				break;
			case 154 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1315: IDENT
				{
				mIDENT(); 

				}
				break;
			case 155 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1321: HEXNUMBER
				{
				mHEXNUMBER(); 

				}
				break;
			case 156 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1331: UUID
				{
				mUUID(); 

				}
				break;
			case 157 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1336: WS
				{
				mWS(); 

				}
				break;
			case 158 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1339: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 159 :
				// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1:1347: MULTILINE_COMMENT
				{
				mMULTILINE_COMMENT(); 

				}
				break;

		}
	}


	protected DFA13 dfa13 = new DFA13(this);
	protected DFA21 dfa21 = new DFA21(this);
	static final String DFA13_eotS =
		"\5\uffff";
	static final String DFA13_eofS =
		"\5\uffff";
	static final String DFA13_minS =
		"\1\55\1\60\1\56\2\uffff";
	static final String DFA13_maxS =
		"\2\71\1\145\2\uffff";
	static final String DFA13_acceptS =
		"\3\uffff\1\1\1\2";
	static final String DFA13_specialS =
		"\5\uffff}>";
	static final String[] DFA13_transitionS = {
			"\1\1\2\uffff\12\2",
			"\12\2",
			"\1\4\1\uffff\12\2\13\uffff\1\3\37\uffff\1\3",
			"",
			""
	};

	static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
	static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
	static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
	static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
	static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
	static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
	static final short[][] DFA13_transition;

	static {
		int numStates = DFA13_transitionS.length;
		DFA13_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
		}
	}

	protected class DFA13 extends DFA {

		public DFA13(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 13;
			this.eot = DFA13_eot;
			this.eof = DFA13_eof;
			this.min = DFA13_min;
			this.max = DFA13_max;
			this.accept = DFA13_accept;
			this.special = DFA13_special;
			this.transition = DFA13_transition;
		}
		@Override
		public String getDescription() {
			return "1867:1: FLOAT : ( INTEGER EXPONENT | INTEGER '.' ( DIGIT )* ( EXPONENT )? );";
		}
	}

	static final String DFA21_eotS =
		"\6\uffff\1\61\3\uffff\1\64\1\uffff\1\66\3\uffff\1\54\2\uffff\25\54\2\uffff"+
		"\1\u0085\2\uffff\1\u0085\4\uffff\1\u0085\4\uffff\17\54\1\u00a1\12\54\1"+
		"\u00ae\1\u00b6\1\u00b7\26\54\1\u00d7\5\54\1\u00de\11\54\1\u00ec\1\u00ed"+
		"\1\u00ef\10\54\1\uffff\1\u0085\5\uffff\6\54\1\u0107\16\54\1\uffff\1\u0117"+
		"\1\u0119\2\54\1\u011c\1\u011e\5\54\1\u0124\1\uffff\2\54\1\u0128\4\54\2"+
		"\uffff\2\54\1\u0130\7\54\1\u0139\24\54\1\uffff\6\54\1\uffff\3\54\1\u0159"+
		"\4\54\1\u015e\4\54\2\uffff\1\54\1\uffff\12\54\1\u016f\1\54\1\u0171\1\54"+
		"\1\u0085\1\uffff\1\u0088\6\54\1\uffff\7\54\1\u0183\1\54\1\u0185\5\54\1"+
		"\uffff\1\54\1\uffff\2\54\1\uffff\1\54\1\uffff\3\54\1\u0192\1\54\1\uffff"+
		"\1\u0194\2\54\1\uffff\1\u0198\1\u0199\5\54\1\uffff\1\u019f\1\54\1\u01a2"+
		"\1\54\1\u01a4\1\u01a5\2\54\1\uffff\6\54\1\u01ae\1\54\1\u01b1\1\54\1\u01b3"+
		"\13\54\1\u01bf\2\54\1\u01c2\3\54\1\u01c6\1\54\1\uffff\1\u01ca\1\u01cb"+
		"\2\54\1\uffff\1\54\1\u01cf\11\54\1\u01d9\4\54\1\uffff\1\u01df\1\uffff"+
		"\1\u01e0\1\u0085\1\uffff\1\u0088\1\uffff\10\54\1\u01ec\2\54\1\u01ef\1"+
		"\uffff\1\54\1\uffff\3\54\1\u01f4\1\u01c2\1\u01f5\1\u01f6\1\u01f7\1\u01f8"+
		"\2\54\1\u01fb\1\uffff\1\54\1\uffff\2\54\1\u01ff\2\uffff\2\54\1\u0202\1"+
		"\54\1\u0204\1\uffff\1\u0205\1\54\1\uffff\1\u0207\2\uffff\1\u0208\7\54"+
		"\1\uffff\2\54\1\uffff\1\54\1\uffff\1\u0213\7\54\1\u021c\1\u021d\1\54\1"+
		"\uffff\2\54\1\uffff\1\54\1\u0222\1\u0223\1\uffff\3\54\2\uffff\1\u0227"+
		"\2\54\1\uffff\3\54\1\u022d\5\54\1\uffff\1\u0233\1\u0234\3\54\2\uffff\1"+
		"\u0085\1\uffff\1\u0088\1\54\1\u023c\2\54\1\u023f\1\u0240\1\54\1\u0242"+
		"\1\uffff\2\54\1\uffff\1\u0245\3\54\5\uffff\2\54\1\uffff\2\54\1\u024d\1"+
		"\uffff\2\54\1\uffff\1\u0250\2\uffff\1\54\2\uffff\6\54\1\u0258\2\54\1\u025b"+
		"\1\uffff\4\54\1\u0260\1\u0261\1\54\1\u0263\2\uffff\1\u0264\3\54\2\uffff"+
		"\3\54\1\uffff\1\54\1\u026c\1\u026d\1\54\1\u026f\1\uffff\1\54\1\u0271\1"+
		"\u0272\2\54\2\uffff\3\54\1\u0085\1\uffff\1\u0088\1\u027b\1\uffff\1\u027c"+
		"\1\54\2\uffff\1\u027e\1\uffff\2\54\1\uffff\7\54\1\uffff\2\54\1\uffff\5"+
		"\54\1\u028f\1\54\1\uffff\1\54\1\u0292\1\uffff\1\u0293\1\54\1\u0295\1\54"+
		"\2\uffff\1\54\2\uffff\1\u0298\1\54\1\u029a\2\54\1\u029d\1\54\2\uffff\1"+
		"\u029f\1\uffff\1\u02a0\2\uffff\1\u02a1\1\u02a2\2\54\1\u02a5\1\u0085\1"+
		"\uffff\1\u0088\2\uffff\1\54\1\uffff\1\54\1\u02aa\1\u02ab\5\54\1\u0240"+
		"\1\u02b3\1\u02b4\1\u02b5\1\u02b6\2\54\1\u02b9\1\uffff\1\u02ba\1\u02bb"+
		"\2\uffff\1\54\1\uffff\1\u02bd\1\54\1\uffff\1\u02bf\1\uffff\1\54\1\u02c1"+
		"\1\uffff\1\54\4\uffff\2\54\1\uffff\1\u0085\1\uffff\1\u0088\1\u02c6\2\uffff"+
		"\1\u02c7\1\u02c8\1\u02c9\1\u02ca\1\u02cb\1\u02cc\1\u02cd\4\uffff\1\54"+
		"\1\u02cf\3\uffff\1\54\1\uffff\1\54\1\uffff\1\u02d2\1\uffff\3\54\11\uffff"+
		"\1\u02d7\1\uffff\1\54\1\u02da\1\uffff\3\54\1\u0088\1\uffff\1\u02df\1\54"+
		"\1\uffff\1\54\1\u02e2\1\u02e3\1\u0088\1\uffff\1\u0222\1\u02e5\2\uffff"+
		"\1\u0088\1\uffff\1\u0088";
	static final String DFA21_eofS =
		"\u02e7\uffff";
	static final String DFA21_minS =
		"\1\11\5\uffff\1\55\3\uffff\1\75\1\uffff\1\75\3\uffff\1\60\2\uffff\1\103"+
		"\2\60\1\110\1\105\1\60\1\106\1\116\2\101\3\60\3\101\1\106\1\105\1\122"+
		"\1\101\1\123\2\uffff\1\56\2\uffff\1\56\1\uffff\1\52\2\uffff\1\56\4\uffff"+
		"\1\105\1\124\1\105\1\60\1\114\1\110\1\101\1\120\1\101\1\125\1\117\2\114"+
		"\1\117\2\60\1\104\1\120\1\114\1\60\1\124\1\107\1\105\1\124\1\111\1\131"+
		"\3\60\1\104\1\105\1\114\1\111\1\113\1\107\1\116\2\122\1\111\1\123\1\60"+
		"\1\117\1\125\1\60\1\114\1\105\1\123\1\125\4\60\1\107\2\117\1\111\1\102"+
		"\1\60\1\115\1\114\1\120\1\130\2\120\1\104\1\105\1\114\3\60\1\124\1\116"+
		"\1\114\1\101\2\114\1\116\1\117\1\uffff\1\56\1\53\4\uffff\1\162\1\103\1"+
		"\123\1\122\1\60\1\105\1\60\1\105\1\122\1\124\1\120\1\105\1\114\1\116\1"+
		"\115\1\114\1\103\1\124\2\101\1\123\1\uffff\2\60\1\114\1\105\2\60\1\110"+
		"\2\122\1\110\1\124\1\60\1\uffff\2\105\1\60\1\124\1\111\1\124\1\125\2\uffff"+
		"\1\101\1\116\1\60\1\117\1\104\1\111\1\105\1\124\1\111\1\107\1\60\1\124"+
		"\1\123\1\115\1\124\1\105\1\103\1\60\1\120\1\102\1\105\1\116\1\125\1\120"+
		"\1\124\1\101\1\124\1\123\1\114\1\111\1\103\1\uffff\1\111\1\102\1\114\1"+
		"\105\1\107\1\114\1\uffff\2\105\1\131\1\60\1\105\1\124\1\114\1\105\1\60"+
		"\1\111\1\127\1\125\1\103\2\uffff\1\105\1\uffff\1\111\1\101\1\117\1\125"+
		"\1\114\1\105\1\116\1\105\1\125\1\117\1\60\1\114\1\60\1\116\1\56\1\53\1"+
		"\60\1\50\1\125\1\124\1\111\1\60\1\103\1\uffff\1\115\1\101\1\111\1\105"+
		"\1\122\1\114\1\103\1\60\1\105\1\60\1\124\1\105\1\114\1\124\1\105\1\uffff"+
		"\1\111\1\uffff\1\131\1\122\1\uffff\1\127\1\uffff\1\117\2\105\1\60\1\105"+
		"\1\uffff\1\60\1\122\1\130\1\uffff\2\60\1\116\1\103\2\124\1\107\1\uffff"+
		"\1\60\1\107\1\60\1\124\2\60\1\116\1\125\1\uffff\2\111\1\127\1\101\1\111"+
		"\1\124\1\60\1\115\1\60\1\114\1\60\1\124\1\115\2\101\1\124\1\117\1\124"+
		"\1\105\1\116\1\110\1\116\1\60\1\105\1\103\1\60\1\107\1\105\1\116\1\60"+
		"\1\111\1\uffff\2\60\1\105\1\122\1\uffff\1\106\1\60\1\105\1\110\1\116\1"+
		"\122\1\117\1\115\1\113\1\122\1\101\1\60\1\124\1\103\1\120\1\107\1\uffff"+
		"\1\60\1\uffff\1\60\1\56\1\53\1\60\1\uffff\1\124\1\123\1\105\1\60\1\124"+
		"\1\101\1\107\1\103\1\60\1\125\1\111\1\60\1\uffff\1\116\1\uffff\1\111\1"+
		"\122\1\106\6\60\1\122\1\107\1\60\1\uffff\1\124\1\uffff\1\101\1\124\1\60"+
		"\2\uffff\1\111\1\117\1\60\1\105\1\60\1\uffff\1\60\1\107\1\uffff\1\60\2"+
		"\uffff\1\60\1\101\1\123\1\124\1\117\1\122\1\116\1\105\1\uffff\1\111\1"+
		"\101\1\uffff\1\105\1\uffff\1\60\1\116\1\103\1\111\1\105\1\115\1\105\1"+
		"\104\2\60\1\124\1\uffff\2\101\1\uffff\1\105\2\60\1\uffff\1\124\1\125\1"+
		"\116\2\uffff\1\60\1\111\1\131\1\uffff\1\123\1\101\1\124\1\60\1\116\2\105"+
		"\1\116\1\103\1\uffff\2\60\1\125\1\105\1\111\2\uffff\1\56\1\53\1\60\1\105"+
		"\1\60\1\123\3\60\1\105\1\60\1\uffff\1\123\1\116\1\uffff\1\60\1\117\1\111"+
		"\1\125\5\uffff\1\111\1\101\1\uffff\1\111\1\103\1\60\1\uffff\1\124\1\116"+
		"\1\uffff\1\60\2\uffff\1\105\2\uffff\1\107\1\123\1\111\1\122\1\131\1\103"+
		"\1\60\1\102\1\114\1\60\1\uffff\1\122\1\106\1\124\1\116\2\60\1\122\1\60"+
		"\2\uffff\1\60\1\116\1\124\1\122\2\uffff\1\101\1\111\1\124\1\uffff\1\101"+
		"\2\60\1\122\1\60\1\uffff\1\123\2\60\1\123\1\105\2\uffff\2\122\1\116\1"+
		"\56\1\53\2\60\1\uffff\2\60\2\uffff\1\60\1\uffff\1\105\1\124\1\uffff\3"+
		"\116\1\132\1\124\1\115\1\105\1\uffff\1\131\1\104\1\uffff\1\104\1\105\1"+
		"\111\1\117\1\104\1\60\1\124\1\uffff\1\105\1\60\1\uffff\1\60\1\101\1\60"+
		"\1\123\2\uffff\1\111\2\uffff\1\60\1\105\1\60\1\115\1\104\1\60\1\114\2"+
		"\uffff\1\60\1\uffff\1\60\2\uffff\2\60\1\123\1\125\1\60\1\56\1\53\1\60"+
		"\2\uffff\1\55\1\uffff\1\122\2\60\1\107\1\103\3\105\5\60\1\117\1\116\1"+
		"\60\1\uffff\2\60\2\uffff\1\115\1\uffff\1\60\1\116\1\uffff\1\60\1\uffff"+
		"\1\120\1\60\1\uffff\1\111\4\uffff\1\111\1\123\1\uffff\1\55\1\53\1\55\1"+
		"\60\2\uffff\7\60\4\uffff\1\116\1\60\3\uffff\1\111\1\uffff\1\107\1\uffff"+
		"\1\60\1\uffff\1\132\1\126\1\105\1\60\10\uffff\1\60\1\uffff\1\114\1\60"+
		"\1\uffff\2\105\1\122\1\60\1\uffff\1\60\1\131\1\uffff\1\104\3\60\1\uffff"+
		"\2\60\2\uffff\1\60\1\uffff\1\55";
	static final String DFA21_maxS =
		"\1\175\5\uffff\1\71\3\uffff\1\75\1\uffff\1\75\3\uffff\1\170\2\uffff\3"+
		"\165\1\162\1\145\1\170\1\163\1\165\1\157\2\162\1\165\2\171\1\157\1\151"+
		"\1\162\1\157\1\162\1\165\1\163\2\uffff\1\170\2\uffff\1\146\1\uffff\1\57"+
		"\2\uffff\1\145\4\uffff\1\160\1\164\1\151\1\146\1\164\1\150\1\171\1\160"+
		"\1\141\1\165\1\157\2\156\1\157\1\154\1\172\1\144\1\160\1\164\1\146\1\164"+
		"\1\147\1\145\1\164\1\151\1\171\3\172\1\144\1\151\1\154\1\151\1\163\1\147"+
		"\1\156\1\162\1\163\1\151\2\163\1\157\1\165\1\164\1\165\1\145\1\163\1\165"+
		"\1\154\1\147\1\164\1\172\1\147\2\157\1\165\1\142\1\172\1\156\1\154\1\160"+
		"\1\170\1\160\1\164\1\144\1\145\1\162\3\172\1\164\1\166\1\154\1\141\1\164"+
		"\1\154\1\156\1\157\1\uffff\2\146\4\uffff\1\162\1\143\1\163\1\162\1\146"+
		"\1\145\1\172\1\145\1\162\1\164\1\160\1\145\1\154\1\156\1\172\1\154\1\143"+
		"\1\164\2\141\1\163\1\uffff\2\172\1\154\1\145\2\172\1\150\2\162\1\150\1"+
		"\164\1\172\1\uffff\2\145\1\172\1\164\1\151\1\164\1\165\2\uffff\1\141\1"+
		"\156\1\172\1\157\1\144\1\151\1\145\1\164\1\151\1\147\1\172\1\164\1\163"+
		"\1\155\1\164\1\145\1\143\1\151\1\160\1\142\1\145\1\156\1\165\1\160\1\164"+
		"\1\141\1\164\1\163\1\154\1\151\1\143\1\uffff\1\151\1\142\1\154\1\156\1"+
		"\147\1\154\1\uffff\2\145\1\171\1\172\1\145\1\164\1\154\1\145\1\172\1\151"+
		"\1\167\1\165\1\151\2\uffff\1\145\1\uffff\1\151\1\141\1\157\1\165\1\154"+
		"\1\145\1\156\1\145\1\165\1\157\1\172\1\154\1\172\1\156\3\146\1\50\1\165"+
		"\1\164\1\151\1\146\1\143\1\uffff\1\155\1\141\1\151\1\145\1\162\1\154\1"+
		"\143\1\172\1\145\1\172\1\164\1\145\1\154\1\164\1\145\1\uffff\1\151\1\uffff"+
		"\1\171\1\162\1\uffff\1\167\1\uffff\1\157\2\145\1\172\1\145\1\uffff\1\172"+
		"\1\162\1\170\1\uffff\2\172\1\156\1\143\2\164\1\147\1\uffff\1\172\1\147"+
		"\1\172\1\164\2\172\1\156\1\165\1\uffff\2\151\1\167\1\141\1\151\1\164\1"+
		"\172\1\155\1\172\1\154\1\172\1\164\1\155\2\141\1\164\1\157\1\164\1\145"+
		"\1\156\1\150\1\156\1\172\1\145\1\143\1\172\1\147\1\145\1\156\1\172\1\151"+
		"\1\uffff\2\172\1\145\1\162\1\uffff\1\146\1\172\1\145\1\150\1\156\1\162"+
		"\1\157\1\155\1\153\1\162\1\141\1\172\1\164\1\143\1\160\1\147\1\uffff\1"+
		"\172\1\uffff\1\172\3\146\1\uffff\1\164\1\163\1\145\1\146\1\164\1\141\1"+
		"\147\1\143\1\172\1\165\1\151\1\172\1\uffff\1\156\1\uffff\1\151\1\162\1"+
		"\146\6\172\1\162\1\147\1\172\1\uffff\1\164\1\uffff\1\141\1\164\1\172\2"+
		"\uffff\1\151\1\157\1\172\1\145\1\172\1\uffff\1\172\1\147\1\uffff\1\172"+
		"\2\uffff\1\172\1\141\1\163\1\164\1\157\1\162\1\156\1\145\1\uffff\1\151"+
		"\1\141\1\uffff\1\145\1\uffff\1\172\1\156\1\143\1\151\1\145\1\155\1\145"+
		"\1\144\2\172\1\164\1\uffff\2\141\1\uffff\1\145\2\172\1\uffff\1\164\1\165"+
		"\1\156\2\uffff\1\172\1\151\1\171\1\uffff\1\163\1\141\1\164\1\172\1\156"+
		"\2\145\1\156\1\143\1\uffff\2\172\1\165\1\145\1\151\2\uffff\3\146\1\145"+
		"\1\172\1\163\1\146\2\172\1\145\1\172\1\uffff\1\163\1\156\1\uffff\1\172"+
		"\1\157\1\151\1\165\5\uffff\1\151\1\141\1\uffff\1\151\1\143\1\172\1\uffff"+
		"\1\164\1\156\1\uffff\1\172\2\uffff\1\145\2\uffff\1\147\1\163\1\151\1\162"+
		"\1\171\1\143\1\172\1\142\1\154\1\172\1\uffff\1\162\1\146\1\164\1\156\2"+
		"\172\1\162\1\172\2\uffff\1\172\1\156\1\164\1\162\2\uffff\1\141\1\151\1"+
		"\164\1\uffff\1\141\2\172\1\162\1\172\1\uffff\1\163\2\172\1\163\1\145\2"+
		"\uffff\2\162\1\156\3\146\1\172\1\uffff\1\172\1\146\2\uffff\1\172\1\uffff"+
		"\1\145\1\164\1\uffff\3\156\1\172\1\164\1\155\1\145\1\uffff\1\171\1\144"+
		"\1\uffff\1\144\1\145\1\151\1\157\1\144\1\172\1\164\1\uffff\1\145\1\172"+
		"\1\uffff\1\172\1\141\1\172\1\163\2\uffff\1\151\2\uffff\1\172\1\145\1\172"+
		"\1\155\1\144\1\172\1\154\2\uffff\1\172\1\uffff\1\172\2\uffff\2\172\1\163"+
		"\1\165\1\172\3\146\2\uffff\1\55\1\uffff\1\162\2\172\1\147\1\143\3\145"+
		"\5\172\1\157\1\156\1\172\1\uffff\2\172\2\uffff\1\155\1\uffff\1\172\1\156"+
		"\1\uffff\1\172\1\uffff\1\160\1\172\1\uffff\1\151\4\uffff\1\151\1\163\1"+
		"\uffff\1\145\1\71\1\55\1\172\2\uffff\7\172\4\uffff\1\156\1\172\3\uffff"+
		"\1\151\1\uffff\1\147\1\uffff\1\172\1\uffff\1\172\1\166\1\145\1\146\10"+
		"\uffff\1\172\1\uffff\1\154\1\172\1\uffff\2\145\1\162\1\146\1\uffff\1\172"+
		"\1\171\1\uffff\1\144\2\172\1\146\1\uffff\2\172\2\uffff\1\146\1\uffff\1"+
		"\55";
	static final String DFA21_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\uffff\1\7\1\10\1\11\1\uffff\1\14\1\uffff"+
		"\1\17\1\20\1\21\1\uffff\1\23\1\24\25\uffff\1\u0094\1\u0095\1\uffff\1\u0097"+
		"\1\u009a\1\uffff\1\u009d\1\uffff\1\u009e\1\6\1\uffff\1\13\1\12\1\16\1"+
		"\15\116\uffff\1\u0096\2\uffff\1\u0098\1\u009b\1\u009c\1\u009f\25\uffff"+
		"\1\27\14\uffff\1\60\7\uffff\1\115\1\116\37\uffff\1\110\6\uffff\1\72\15"+
		"\uffff\1\71\1\u0091\1\uffff\1\124\27\uffff\1\51\17\uffff\1\111\1\uffff"+
		"\1\31\2\uffff\1\121\1\uffff\1\103\5\uffff\1\32\3\uffff\1\157\7\uffff\1"+
		"\46\10\uffff\1\43\37\uffff\1\100\4\uffff\1\176\20\uffff\1\174\1\uffff"+
		"\1\u0080\4\uffff\1\22\14\uffff\1\26\1\uffff\1\35\14\uffff\1\40\1\uffff"+
		"\1\33\3\uffff\1\75\1\156\5\uffff\1\133\2\uffff\1\163\1\uffff\1\42\1\177"+
		"\10\uffff\1\112\2\uffff\1\73\1\uffff\1\171\13\uffff\1\150\2\uffff\1\u0099"+
		"\3\uffff\1\172\3\uffff\1\104\1\162\3\uffff\1\66\11\uffff\1\135\5\uffff"+
		"\1\173\1\u0093\13\uffff\1\u008a\2\uffff\1\u0089\4\uffff\1\155\1\146\1"+
		"\55\1\101\1\113\2\uffff\1\30\3\uffff\1\67\2\uffff\1\u008f\1\uffff\1\45"+
		"\1\134\1\uffff\1\41\1\142\12\uffff\1\50\10\uffff\1\52\1\54\4\uffff\1\64"+
		"\1\167\3\uffff\1\u0082\5\uffff\1\107\5\uffff\1\136\1\120\7\uffff\1\175"+
		"\2\uffff\1\25\1\62\1\uffff\1\u0084\2\uffff\1\u0085\7\uffff\1\36\2\uffff"+
		"\1\37\7\uffff\1\57\2\uffff\1\154\4\uffff\1\61\1\70\1\uffff\1\u008e\1\147"+
		"\7\uffff\1\126\1\76\1\uffff\1\165\1\uffff\1\102\1\125\10\uffff\1\131\1"+
		"\34\1\uffff\1\106\20\uffff\1\74\2\uffff\1\153\1\152\1\uffff\1\105\2\uffff"+
		"\1\151\1\uffff\1\u0083\2\uffff\1\161\1\uffff\1\164\1\144\1\u008d\1\u0092"+
		"\2\uffff\1\143\4\uffff\1\160\1\u0086\7\uffff\1\u0081\1\u008c\1\53\1\u0090"+
		"\2\uffff\1\141\1\47\1\130\1\uffff\1\117\1\uffff\1\56\1\uffff\1\166\4\uffff"+
		"\1\137\1\u0087\1\114\1\u008b\1\127\1\u0088\1\170\1\63\1\uffff\1\44\2\uffff"+
		"\1\77\4\uffff\1\122\2\uffff\1\145\4\uffff\1\123\2\uffff\1\132\1\140\1"+
		"\uffff\1\65\1\uffff";
	static final String DFA21_specialS =
		"\u02e7\uffff}>";
	static final String[] DFA21_transitionS = {
			"\2\56\2\uffff\1\56\22\uffff\1\56\1\1\1\51\1\uffff\1\50\2\uffff\1\50\1"+
			"\2\1\3\1\16\1\4\1\5\1\6\1\7\1\57\1\52\11\55\1\10\1\11\1\12\1\13\1\14"+
			"\1\53\1\uffff\1\25\1\37\1\36\1\35\1\30\1\24\1\45\1\54\1\31\1\47\1\27"+
			"\1\33\1\41\1\46\1\43\1\34\1\54\1\44\1\23\1\40\1\32\1\42\1\26\3\54\1\15"+
			"\1\uffff\1\17\3\uffff\1\25\1\37\1\36\1\35\1\20\1\24\1\45\1\54\1\31\1"+
			"\47\1\27\1\33\1\41\1\46\1\43\1\34\1\54\1\44\1\23\1\40\1\32\1\42\1\26"+
			"\3\54\1\21\1\uffff\1\22",
			"",
			"",
			"",
			"",
			"",
			"\1\60\2\uffff\12\62",
			"",
			"",
			"",
			"\1\63",
			"",
			"\1\65",
			"",
			"",
			"",
			"\12\72\7\uffff\6\72\7\uffff\1\70\11\uffff\1\71\10\uffff\6\72\7\uffff"+
			"\1\70\11\uffff\1\67",
			"",
			"",
			"\1\74\1\uffff\1\73\1\100\6\uffff\1\77\6\uffff\1\75\1\76\15\uffff\1\74"+
			"\1\uffff\1\73\1\100\6\uffff\1\77\6\uffff\1\75\1\76",
			"\12\72\7\uffff\1\105\5\72\2\uffff\1\103\2\uffff\1\104\5\uffff\1\101"+
			"\2\uffff\1\102\13\uffff\1\105\5\72\2\uffff\1\103\2\uffff\1\104\5\uffff"+
			"\1\101\2\uffff\1\102",
			"\12\72\7\uffff\3\72\1\112\2\72\1\114\4\uffff\1\111\1\uffff\1\107\1\uffff"+
			"\1\110\2\uffff\1\106\1\uffff\1\113\13\uffff\3\72\1\112\2\72\1\114\4\uffff"+
			"\1\111\1\uffff\1\107\1\uffff\1\110\2\uffff\1\106\1\uffff\1\113",
			"\1\115\1\116\10\uffff\1\117\25\uffff\1\115\1\116\10\uffff\1\117",
			"\1\120\37\uffff\1\120",
			"\12\72\7\uffff\6\72\7\uffff\1\70\11\uffff\1\71\10\uffff\6\72\7\uffff"+
			"\1\70\11\uffff\1\71",
			"\1\122\7\uffff\1\121\4\uffff\1\123\22\uffff\1\122\7\uffff\1\121\4\uffff"+
			"\1\123",
			"\1\126\1\uffff\1\124\2\uffff\1\125\1\uffff\1\127\30\uffff\1\126\1\uffff"+
			"\1\124\2\uffff\1\125\1\uffff\1\127",
			"\1\132\7\uffff\1\130\5\uffff\1\131\21\uffff\1\132\7\uffff\1\130\5\uffff"+
			"\1\131",
			"\1\134\3\uffff\1\133\14\uffff\1\135\16\uffff\1\134\3\uffff\1\133\14"+
			"\uffff\1\135",
			"\12\72\7\uffff\1\142\3\72\1\137\1\72\2\uffff\1\136\5\uffff\1\141\2\uffff"+
			"\1\140\16\uffff\1\142\3\72\1\137\1\72\2\uffff\1\136\5\uffff\1\141\2\uffff"+
			"\1\140",
			"\12\72\7\uffff\1\147\5\72\5\uffff\1\146\2\uffff\1\143\2\uffff\1\144"+
			"\2\uffff\1\145\13\uffff\1\147\5\72\5\uffff\1\146\2\uffff\1\143\2\uffff"+
			"\1\144\2\uffff\1\145",
			"\12\72\7\uffff\1\151\3\72\1\150\1\72\2\uffff\1\153\2\uffff\1\154\2\uffff"+
			"\1\155\11\uffff\1\152\7\uffff\1\151\3\72\1\150\1\72\2\uffff\1\153\2\uffff"+
			"\1\154\2\uffff\1\155\11\uffff\1\152",
			"\1\157\3\uffff\1\164\3\uffff\1\161\5\uffff\1\160\2\uffff\1\156\1\uffff"+
			"\1\162\1\165\3\uffff\1\163\7\uffff\1\157\3\uffff\1\164\3\uffff\1\161"+
			"\5\uffff\1\160\2\uffff\1\156\1\uffff\1\162\1\165\3\uffff\1\163",
			"\1\166\15\uffff\1\167\21\uffff\1\166\15\uffff\1\167",
			"\1\171\7\uffff\1\170\27\uffff\1\171\7\uffff\1\170",
			"\1\174\7\uffff\1\172\1\uffff\1\175\1\uffff\1\173\23\uffff\1\174\7\uffff"+
			"\1\172\1\uffff\1\175\1\uffff\1\173",
			"\1\176\11\uffff\1\177\25\uffff\1\176\11\uffff\1\177",
			"\1\u0080\37\uffff\1\u0080",
			"\1\u0083\15\uffff\1\u0081\5\uffff\1\u0082\13\uffff\1\u0083\15\uffff"+
			"\1\u0081\5\uffff\1\u0082",
			"\1\u0084\37\uffff\1\u0084",
			"",
			"",
			"\1\u0088\1\uffff\12\u0086\7\uffff\4\u008a\1\u0087\1\u008a\21\uffff\1"+
			"\u0089\10\uffff\4\u008a\1\u0087\1\u008a\21\uffff\1\u0089",
			"",
			"",
			"\1\u0088\1\uffff\12\u0086\7\uffff\4\u008a\1\u0087\1\u008a\32\uffff\4"+
			"\u008a\1\u0087\1\u008a",
			"",
			"\1\u008b\4\uffff\1\60",
			"",
			"",
			"\1\u0088\1\uffff\12\62\13\uffff\1\u0088\37\uffff\1\u0088",
			"",
			"",
			"",
			"",
			"\1\u008d\3\uffff\1\u008e\33\uffff\1\u008d\3\uffff\1\u008e\6\uffff\1"+
			"\u008c",
			"\1\u008f\37\uffff\1\u008f",
			"\1\u008d\3\uffff\1\u008e\33\uffff\1\u008d\3\uffff\1\u008e",
			"\12\u0090\7\uffff\6\u0090\32\uffff\6\u0090",
			"\1\u0091\7\uffff\1\u0092\27\uffff\1\u0091\7\uffff\1\u0092",
			"\1\u0093\37\uffff\1\u0093",
			"\1\u0095\15\uffff\1\u0094\11\uffff\1\u0096\7\uffff\1\u0095\15\uffff"+
			"\1\u0094\11\uffff\1\u0096",
			"\1\u0097\37\uffff\1\u0097",
			"\1\u0098\37\uffff\1\u0098",
			"\1\u0099\37\uffff\1\u0099",
			"\1\u009a\37\uffff\1\u009a",
			"\1\u009b\1\uffff\1\u009c\35\uffff\1\u009b\1\uffff\1\u009c",
			"\1\u009d\1\uffff\1\u009e\35\uffff\1\u009d\1\uffff\1\u009e",
			"\1\u009f\37\uffff\1\u009f",
			"\12\u0090\7\uffff\6\u0090\5\uffff\1\u00a0\24\uffff\6\u0090\5\uffff\1"+
			"\u00a0",
			"\12\54\7\uffff\2\54\1\u00a2\27\54\4\uffff\1\54\1\uffff\2\54\1\u00a2"+
			"\27\54",
			"\1\u00a3\37\uffff\1\u00a3",
			"\1\u00a4\37\uffff\1\u00a4",
			"\1\u00a6\7\uffff\1\u00a5\27\uffff\1\u00a6\7\uffff\1\u00a5",
			"\12\u0090\7\uffff\3\u0090\1\u00a7\2\u0090\32\uffff\3\u0090\1\u00a7\2"+
			"\u0090",
			"\1\u00a8\37\uffff\1\u00a8",
			"\1\u00a9\37\uffff\1\u00a9",
			"\1\u00aa\37\uffff\1\u00aa",
			"\1\u00ab\37\uffff\1\u00ab",
			"\1\u00ac\37\uffff\1\u00ac",
			"\1\u00ad\37\uffff\1\u00ad",
			"\12\54\7\uffff\3\54\1\u00b0\1\u00b2\1\u00b3\2\54\1\u00b4\6\54\1\u00b5"+
			"\2\54\1\u00af\1\u00b1\6\54\4\uffff\1\54\1\uffff\3\54\1\u00b0\1\u00b2"+
			"\1\u00b3\2\54\1\u00b4\6\54\1\u00b5\2\54\1\u00af\1\u00b1\6\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u00b8\37\uffff\1\u00b8",
			"\1\u00ba\3\uffff\1\u00b9\33\uffff\1\u00ba\3\uffff\1\u00b9",
			"\1\u00bb\37\uffff\1\u00bb",
			"\1\u00bc\37\uffff\1\u00bc",
			"\1\u00be\1\uffff\1\u00bd\5\uffff\1\u00bf\27\uffff\1\u00be\1\uffff\1"+
			"\u00bd\5\uffff\1\u00bf",
			"\1\u00c0\37\uffff\1\u00c0",
			"\1\u00c1\37\uffff\1\u00c1",
			"\1\u00c2\37\uffff\1\u00c2",
			"\1\u00c3\1\u00c4\36\uffff\1\u00c3\1\u00c4",
			"\1\u00c5\37\uffff\1\u00c5",
			"\1\u00c6\37\uffff\1\u00c6",
			"\12\u0090\7\uffff\2\u0090\1\u00c9\3\u0090\5\uffff\1\u00c7\6\uffff\1"+
			"\u00c8\15\uffff\2\u0090\1\u00c9\3\u0090\5\uffff\1\u00c7\6\uffff\1\u00c8",
			"\1\u00ca\37\uffff\1\u00ca",
			"\1\u00cb\37\uffff\1\u00cb",
			"\12\u0090\7\uffff\6\u0090\15\uffff\1\u00cc\14\uffff\6\u0090\15\uffff"+
			"\1\u00cc",
			"\1\u00ce\1\u00cf\1\u00d0\6\uffff\1\u00cd\26\uffff\1\u00ce\1\u00cf\1"+
			"\u00d0\6\uffff\1\u00cd",
			"\1\u00d1\37\uffff\1\u00d1",
			"\1\u00d2\37\uffff\1\u00d2",
			"\1\u00d3\37\uffff\1\u00d3",
			"\12\u0090\7\uffff\6\u0090\5\uffff\1\u00d4\24\uffff\6\u0090\5\uffff\1"+
			"\u00d4",
			"\12\u0090\7\uffff\6\u0090\1\u00d5\31\uffff\6\u0090\1\u00d5",
			"\12\u0090\7\uffff\6\u0090\15\uffff\1\u00d6\14\uffff\6\u0090\15\uffff"+
			"\1\u00d6",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u00d8\37\uffff\1\u00d8",
			"\1\u00d9\37\uffff\1\u00d9",
			"\1\u00da\37\uffff\1\u00da",
			"\1\u00dc\13\uffff\1\u00db\23\uffff\1\u00dc\13\uffff\1\u00db",
			"\1\u00dd\37\uffff\1\u00dd",
			"\12\54\7\uffff\12\54\1\u00df\17\54\4\uffff\1\54\1\uffff\12\54\1\u00df"+
			"\17\54",
			"\1\u00e0\1\u00e1\36\uffff\1\u00e0\1\u00e1",
			"\1\u00e2\37\uffff\1\u00e2",
			"\1\u00e3\37\uffff\1\u00e3",
			"\1\u00e4\37\uffff\1\u00e4",
			"\1\u00e5\37\uffff\1\u00e5",
			"\1\u00e7\3\uffff\1\u00e6\33\uffff\1\u00e7\3\uffff\1\u00e6",
			"\1\u00e8\37\uffff\1\u00e8",
			"\1\u00e9\37\uffff\1\u00e9",
			"\1\u00ea\5\uffff\1\u00eb\31\uffff\1\u00ea\5\uffff\1\u00eb",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\3\54\1\u00ee\26\54\4\uffff\1\54\1\uffff\3\54\1\u00ee"+
			"\26\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u00f0\37\uffff\1\u00f0",
			"\1\u00f1\1\uffff\1\u00f4\3\uffff\1\u00f3\1\uffff\1\u00f2\27\uffff\1"+
			"\u00f1\1\uffff\1\u00f4\3\uffff\1\u00f3\1\uffff\1\u00f2",
			"\1\u00f5\37\uffff\1\u00f5",
			"\1\u00f6\37\uffff\1\u00f6",
			"\1\u00f9\5\uffff\1\u00f7\1\u00f8\1\u00fa\27\uffff\1\u00f9\5\uffff\1"+
			"\u00f7\1\u00f8\1\u00fa",
			"\1\u00fb\37\uffff\1\u00fb",
			"\1\u00fc\37\uffff\1\u00fc",
			"\1\u00fd\37\uffff\1\u00fd",
			"",
			"\1\u0088\1\uffff\12\u00fe\7\uffff\4\u008a\1\u00ff\1\u008a\32\uffff\4"+
			"\u008a\1\u00ff\1\u008a",
			"\1\u0088\1\uffff\1\u0088\2\uffff\12\u0100\7\uffff\6\u008a\32\uffff\6"+
			"\u008a",
			"",
			"",
			"",
			"",
			"\1\u0101",
			"\1\u0102\37\uffff\1\u0102",
			"\1\u0103\37\uffff\1\u0103",
			"\1\u0104\37\uffff\1\u0104",
			"\12\u0105\7\uffff\6\u0105\32\uffff\6\u0105",
			"\1\u0106\37\uffff\1\u0106",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0108\37\uffff\1\u0108",
			"\1\u0109\37\uffff\1\u0109",
			"\1\u010a\37\uffff\1\u010a",
			"\1\u010b\37\uffff\1\u010b",
			"\1\u010c\37\uffff\1\u010c",
			"\1\u010d\37\uffff\1\u010d",
			"\1\u010e\37\uffff\1\u010e",
			"\1\u010f\14\uffff\1\u0110\22\uffff\1\u010f\14\uffff\1\u0110",
			"\1\u0111\37\uffff\1\u0111",
			"\1\u0112\37\uffff\1\u0112",
			"\1\u0113\37\uffff\1\u0113",
			"\1\u0114\37\uffff\1\u0114",
			"\1\u0115\37\uffff\1\u0115",
			"\1\u0116\37\uffff\1\u0116",
			"",
			"\12\54\7\uffff\10\54\1\u0118\21\54\4\uffff\1\54\1\uffff\10\54\1\u0118"+
			"\21\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u011a\37\uffff\1\u011a",
			"\1\u011b\37\uffff\1\u011b",
			"\12\54\7\uffff\16\54\1\u011d\13\54\4\uffff\1\54\1\uffff\16\54\1\u011d"+
			"\13\54",
			"\12\u0105\7\uffff\6\u0105\24\54\4\uffff\1\54\1\uffff\6\u0105\24\54",
			"\1\u011f\37\uffff\1\u011f",
			"\1\u0120\37\uffff\1\u0120",
			"\1\u0121\37\uffff\1\u0121",
			"\1\u0122\37\uffff\1\u0122",
			"\1\u0123\37\uffff\1\u0123",
			"\12\54\7\uffff\22\54\1\u0125\7\54\4\uffff\1\54\1\uffff\22\54\1\u0125"+
			"\7\54",
			"",
			"\1\u0126\37\uffff\1\u0126",
			"\1\u0127\37\uffff\1\u0127",
			"\12\54\7\uffff\16\54\1\u0129\13\54\4\uffff\1\54\1\uffff\16\54\1\u0129"+
			"\13\54",
			"\1\u012a\37\uffff\1\u012a",
			"\1\u012b\37\uffff\1\u012b",
			"\1\u012c\37\uffff\1\u012c",
			"\1\u012d\37\uffff\1\u012d",
			"",
			"",
			"\1\u012e\37\uffff\1\u012e",
			"\1\u012f\37\uffff\1\u012f",
			"\12\54\7\uffff\21\54\1\u0131\10\54\4\uffff\1\54\1\uffff\21\54\1\u0131"+
			"\10\54",
			"\1\u0132\37\uffff\1\u0132",
			"\1\u0133\37\uffff\1\u0133",
			"\1\u0134\37\uffff\1\u0134",
			"\1\u0135\37\uffff\1\u0135",
			"\1\u0136\37\uffff\1\u0136",
			"\1\u0137\37\uffff\1\u0137",
			"\1\u0138\37\uffff\1\u0138",
			"\12\54\7\uffff\14\54\1\u013a\15\54\4\uffff\1\54\1\uffff\14\54\1\u013a"+
			"\15\54",
			"\1\u013b\37\uffff\1\u013b",
			"\1\u013c\37\uffff\1\u013c",
			"\1\u013d\37\uffff\1\u013d",
			"\1\u013e\37\uffff\1\u013e",
			"\1\u013f\37\uffff\1\u013f",
			"\1\u0140\37\uffff\1\u0140",
			"\12\u0105\7\uffff\6\u0105\2\uffff\1\u0141\27\uffff\6\u0105\2\uffff\1"+
			"\u0141",
			"\1\u0142\37\uffff\1\u0142",
			"\1\u0143\37\uffff\1\u0143",
			"\1\u0144\37\uffff\1\u0144",
			"\1\u0145\37\uffff\1\u0145",
			"\1\u0146\37\uffff\1\u0146",
			"\1\u0147\37\uffff\1\u0147",
			"\1\u0148\37\uffff\1\u0148",
			"\1\u0149\37\uffff\1\u0149",
			"\1\u014a\37\uffff\1\u014a",
			"\1\u014b\37\uffff\1\u014b",
			"\1\u014c\37\uffff\1\u014c",
			"\1\u014d\37\uffff\1\u014d",
			"\1\u014e\37\uffff\1\u014e",
			"",
			"\1\u014f\37\uffff\1\u014f",
			"\1\u0150\37\uffff\1\u0150",
			"\1\u0151\37\uffff\1\u0151",
			"\1\u0153\10\uffff\1\u0152\26\uffff\1\u0153\10\uffff\1\u0152",
			"\1\u0154\37\uffff\1\u0154",
			"\1\u0155\37\uffff\1\u0155",
			"",
			"\1\u0156\37\uffff\1\u0156",
			"\1\u0157\37\uffff\1\u0157",
			"\1\u0158\37\uffff\1\u0158",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u015a\37\uffff\1\u015a",
			"\1\u015b\37\uffff\1\u015b",
			"\1\u015c\37\uffff\1\u015c",
			"\1\u015d\37\uffff\1\u015d",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u015f\37\uffff\1\u015f",
			"\1\u0160\37\uffff\1\u0160",
			"\1\u0161\37\uffff\1\u0161",
			"\1\u0162\5\uffff\1\u0163\31\uffff\1\u0162\5\uffff\1\u0163",
			"",
			"",
			"\1\u0164\37\uffff\1\u0164",
			"",
			"\1\u0165\37\uffff\1\u0165",
			"\1\u0166\37\uffff\1\u0166",
			"\1\u0167\37\uffff\1\u0167",
			"\1\u0168\37\uffff\1\u0168",
			"\1\u0169\37\uffff\1\u0169",
			"\1\u016a\37\uffff\1\u016a",
			"\1\u016b\37\uffff\1\u016b",
			"\1\u016c\37\uffff\1\u016c",
			"\1\u016d\37\uffff\1\u016d",
			"\1\u016e\37\uffff\1\u016e",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0170\37\uffff\1\u0170",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0172\37\uffff\1\u0172",
			"\1\u0088\1\uffff\12\u0173\7\uffff\4\u008a\1\u0174\1\u008a\32\uffff\4"+
			"\u008a\1\u0174\1\u008a",
			"\1\u0088\1\uffff\1\u0088\2\uffff\12\u0175\7\uffff\6\u008a\32\uffff\6"+
			"\u008a",
			"\12\u0175\7\uffff\6\u008a\32\uffff\6\u008a",
			"\1\u0176",
			"\1\u0177\37\uffff\1\u0177",
			"\1\u0178\37\uffff\1\u0178",
			"\1\u0179\37\uffff\1\u0179",
			"\12\u017a\7\uffff\6\u017a\32\uffff\6\u017a",
			"\1\u017b\37\uffff\1\u017b",
			"",
			"\1\u017c\37\uffff\1\u017c",
			"\1\u017d\37\uffff\1\u017d",
			"\1\u017e\37\uffff\1\u017e",
			"\1\u017f\37\uffff\1\u017f",
			"\1\u0180\37\uffff\1\u0180",
			"\1\u0181\37\uffff\1\u0181",
			"\1\u0182\37\uffff\1\u0182",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0184\37\uffff\1\u0184",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0186\37\uffff\1\u0186",
			"\1\u0187\37\uffff\1\u0187",
			"\1\u0188\37\uffff\1\u0188",
			"\1\u0189\37\uffff\1\u0189",
			"\1\u018a\37\uffff\1\u018a",
			"",
			"\1\u018b\37\uffff\1\u018b",
			"",
			"\1\u018c\37\uffff\1\u018c",
			"\1\u018d\37\uffff\1\u018d",
			"",
			"\1\u018e\37\uffff\1\u018e",
			"",
			"\1\u018f\37\uffff\1\u018f",
			"\1\u0190\37\uffff\1\u0190",
			"\1\u0191\37\uffff\1\u0191",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0193\37\uffff\1\u0193",
			"",
			"\12\54\7\uffff\17\54\1\u0195\12\54\4\uffff\1\54\1\uffff\17\54\1\u0195"+
			"\12\54",
			"\1\u0196\37\uffff\1\u0196",
			"\1\u0197\37\uffff\1\u0197",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u019a\37\uffff\1\u019a",
			"\1\u019b\37\uffff\1\u019b",
			"\1\u019c\37\uffff\1\u019c",
			"\1\u019d\37\uffff\1\u019d",
			"\1\u019e\37\uffff\1\u019e",
			"",
			"\12\54\7\uffff\22\54\1\u01a0\7\54\4\uffff\1\54\1\uffff\22\54\1\u01a0"+
			"\7\54",
			"\1\u01a1\37\uffff\1\u01a1",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01a3\37\uffff\1\u01a3",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01a6\37\uffff\1\u01a6",
			"\1\u01a7\37\uffff\1\u01a7",
			"",
			"\1\u01a8\37\uffff\1\u01a8",
			"\1\u01a9\37\uffff\1\u01a9",
			"\1\u01aa\37\uffff\1\u01aa",
			"\1\u01ab\37\uffff\1\u01ab",
			"\1\u01ac\37\uffff\1\u01ac",
			"\1\u01ad\37\uffff\1\u01ad",
			"\12\54\7\uffff\21\54\1\u01af\10\54\4\uffff\1\54\1\uffff\21\54\1\u01af"+
			"\10\54",
			"\1\u01b0\37\uffff\1\u01b0",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01b2\37\uffff\1\u01b2",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01b4\37\uffff\1\u01b4",
			"\1\u01b5\37\uffff\1\u01b5",
			"\1\u01b6\37\uffff\1\u01b6",
			"\1\u01b7\37\uffff\1\u01b7",
			"\1\u01b8\37\uffff\1\u01b8",
			"\1\u01b9\37\uffff\1\u01b9",
			"\1\u01ba\37\uffff\1\u01ba",
			"\1\u01bb\37\uffff\1\u01bb",
			"\1\u01bc\37\uffff\1\u01bc",
			"\1\u01bd\37\uffff\1\u01bd",
			"\1\u01be\37\uffff\1\u01be",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01c0\37\uffff\1\u01c0",
			"\1\u01c1\37\uffff\1\u01c1",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01c3\37\uffff\1\u01c3",
			"\1\u01c4\37\uffff\1\u01c4",
			"\1\u01c5\37\uffff\1\u01c5",
			"\12\54\7\uffff\22\54\1\u01c7\1\54\1\u01c8\5\54\4\uffff\1\54\1\uffff"+
			"\22\54\1\u01c7\1\54\1\u01c8\5\54",
			"\1\u01c9\37\uffff\1\u01c9",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01cc\37\uffff\1\u01cc",
			"\1\u01cd\37\uffff\1\u01cd",
			"",
			"\1\u01ce\37\uffff\1\u01ce",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01d0\37\uffff\1\u01d0",
			"\1\u01d1\37\uffff\1\u01d1",
			"\1\u01d2\37\uffff\1\u01d2",
			"\1\u01d3\37\uffff\1\u01d3",
			"\1\u01d4\37\uffff\1\u01d4",
			"\1\u01d5\37\uffff\1\u01d5",
			"\1\u01d6\37\uffff\1\u01d6",
			"\1\u01d7\37\uffff\1\u01d7",
			"\1\u01d8\37\uffff\1\u01d8",
			"\12\54\7\uffff\22\54\1\u01da\7\54\4\uffff\1\54\1\uffff\22\54\1\u01da"+
			"\7\54",
			"\1\u01db\37\uffff\1\u01db",
			"\1\u01dc\37\uffff\1\u01dc",
			"\1\u01dd\37\uffff\1\u01dd",
			"\1\u01de\37\uffff\1\u01de",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0088\1\uffff\12\u01e1\7\uffff\4\u008a\1\u01e2\1\u008a\32\uffff\4"+
			"\u008a\1\u01e2\1\u008a",
			"\1\u0088\1\uffff\1\u0088\2\uffff\12\u01e3\7\uffff\6\u008a\32\uffff\6"+
			"\u008a",
			"\12\u01e3\7\uffff\6\u008a\32\uffff\6\u008a",
			"",
			"\1\u01e4\37\uffff\1\u01e4",
			"\1\u01e5\37\uffff\1\u01e5",
			"\1\u01e6\37\uffff\1\u01e6",
			"\12\u01e7\7\uffff\6\u01e7\32\uffff\6\u01e7",
			"\1\u01e8\37\uffff\1\u01e8",
			"\1\u01e9\37\uffff\1\u01e9",
			"\1\u01ea\37\uffff\1\u01ea",
			"\1\u01eb\37\uffff\1\u01eb",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01ed\37\uffff\1\u01ed",
			"\1\u01ee\37\uffff\1\u01ee",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u01f0\37\uffff\1\u01f0",
			"",
			"\1\u01f1\37\uffff\1\u01f1",
			"\1\u01f2\37\uffff\1\u01f2",
			"\1\u01f3\37\uffff\1\u01f3",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u01f9\37\uffff\1\u01f9",
			"\1\u01fa\37\uffff\1\u01fa",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u01fc\37\uffff\1\u01fc",
			"",
			"\1\u01fd\37\uffff\1\u01fd",
			"\1\u01fe\37\uffff\1\u01fe",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\1\u0200\37\uffff\1\u0200",
			"\1\u0201\37\uffff\1\u0201",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0203\37\uffff\1\u0203",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0206\37\uffff\1\u0206",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0209\37\uffff\1\u0209",
			"\1\u020a\37\uffff\1\u020a",
			"\1\u020b\37\uffff\1\u020b",
			"\1\u020c\37\uffff\1\u020c",
			"\1\u020d\37\uffff\1\u020d",
			"\1\u020e\37\uffff\1\u020e",
			"\1\u020f\37\uffff\1\u020f",
			"",
			"\1\u0210\37\uffff\1\u0210",
			"\1\u0211\37\uffff\1\u0211",
			"",
			"\1\u0212\37\uffff\1\u0212",
			"",
			"\12\54\7\uffff\4\54\1\u0214\25\54\4\uffff\1\54\1\uffff\4\54\1\u0214"+
			"\25\54",
			"\1\u0215\37\uffff\1\u0215",
			"\1\u0216\37\uffff\1\u0216",
			"\1\u0217\37\uffff\1\u0217",
			"\1\u0218\37\uffff\1\u0218",
			"\1\u0219\37\uffff\1\u0219",
			"\1\u021a\37\uffff\1\u021a",
			"\1\u021b\37\uffff\1\u021b",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u021e\37\uffff\1\u021e",
			"",
			"\1\u021f\37\uffff\1\u021f",
			"\1\u0220\37\uffff\1\u0220",
			"",
			"\1\u0221\37\uffff\1\u0221",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u0224\37\uffff\1\u0224",
			"\1\u0225\37\uffff\1\u0225",
			"\1\u0226\37\uffff\1\u0226",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0228\37\uffff\1\u0228",
			"\1\u0229\37\uffff\1\u0229",
			"",
			"\1\u022a\37\uffff\1\u022a",
			"\1\u022b\37\uffff\1\u022b",
			"\1\u022c\37\uffff\1\u022c",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u022e\37\uffff\1\u022e",
			"\1\u022f\37\uffff\1\u022f",
			"\1\u0230\37\uffff\1\u0230",
			"\1\u0231\37\uffff\1\u0231",
			"\1\u0232\37\uffff\1\u0232",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0235\37\uffff\1\u0235",
			"\1\u0236\37\uffff\1\u0236",
			"\1\u0237\37\uffff\1\u0237",
			"",
			"",
			"\1\u0088\1\uffff\12\u0238\7\uffff\4\u008a\1\u0239\1\u008a\32\uffff\4"+
			"\u008a\1\u0239\1\u008a",
			"\1\u0088\1\uffff\1\u0088\2\uffff\12\u023a\7\uffff\6\u008a\32\uffff\6"+
			"\u008a",
			"\12\u023a\7\uffff\6\u008a\32\uffff\6\u008a",
			"\1\u023b\37\uffff\1\u023b",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u023d\37\uffff\1\u023d",
			"\12\u023e\7\uffff\6\u023e\32\uffff\6\u023e",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0241\37\uffff\1\u0241",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u0243\37\uffff\1\u0243",
			"\1\u0244\37\uffff\1\u0244",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0246\37\uffff\1\u0246",
			"\1\u0247\37\uffff\1\u0247",
			"\1\u0248\37\uffff\1\u0248",
			"",
			"",
			"",
			"",
			"",
			"\1\u0249\37\uffff\1\u0249",
			"\1\u024a\37\uffff\1\u024a",
			"",
			"\1\u024b\37\uffff\1\u024b",
			"\1\u024c\37\uffff\1\u024c",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u024e\37\uffff\1\u024e",
			"\1\u024f\37\uffff\1\u024f",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\1\u0251\37\uffff\1\u0251",
			"",
			"",
			"\1\u0252\37\uffff\1\u0252",
			"\1\u0253\37\uffff\1\u0253",
			"\1\u0254\37\uffff\1\u0254",
			"\1\u0255\37\uffff\1\u0255",
			"\1\u0256\37\uffff\1\u0256",
			"\1\u0257\37\uffff\1\u0257",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0259\37\uffff\1\u0259",
			"\1\u025a\37\uffff\1\u025a",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u025c\37\uffff\1\u025c",
			"\1\u025d\37\uffff\1\u025d",
			"\1\u025e\37\uffff\1\u025e",
			"\1\u025f\37\uffff\1\u025f",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0262\37\uffff\1\u0262",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0265\37\uffff\1\u0265",
			"\1\u0266\37\uffff\1\u0266",
			"\1\u0267\37\uffff\1\u0267",
			"",
			"",
			"\1\u0268\37\uffff\1\u0268",
			"\1\u0269\37\uffff\1\u0269",
			"\1\u026a\37\uffff\1\u026a",
			"",
			"\1\u026b\37\uffff\1\u026b",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u026e\37\uffff\1\u026e",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u0270\37\uffff\1\u0270",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0273\37\uffff\1\u0273",
			"\1\u0274\37\uffff\1\u0274",
			"",
			"",
			"\1\u0275\37\uffff\1\u0275",
			"\1\u0276\37\uffff\1\u0276",
			"\1\u0277\37\uffff\1\u0277",
			"\1\u0088\1\uffff\12\u0278\7\uffff\4\u008a\1\u0279\1\u008a\32\uffff\4"+
			"\u008a\1\u0279\1\u008a",
			"\1\u0088\1\uffff\1\u0088\2\uffff\12\u027a\7\uffff\6\u008a\32\uffff\6"+
			"\u008a",
			"\12\u027a\7\uffff\6\u008a\32\uffff\6\u008a",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\u027d\7\uffff\6\u027d\32\uffff\6\u027d",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u027f\37\uffff\1\u027f",
			"\1\u0280\37\uffff\1\u0280",
			"",
			"\1\u0281\37\uffff\1\u0281",
			"\1\u0282\37\uffff\1\u0282",
			"\1\u0283\37\uffff\1\u0283",
			"\1\u0284\37\uffff\1\u0284",
			"\1\u0285\37\uffff\1\u0285",
			"\1\u0286\37\uffff\1\u0286",
			"\1\u0287\37\uffff\1\u0287",
			"",
			"\1\u0288\37\uffff\1\u0288",
			"\1\u0289\37\uffff\1\u0289",
			"",
			"\1\u028a\37\uffff\1\u028a",
			"\1\u028b\37\uffff\1\u028b",
			"\1\u028c\37\uffff\1\u028c",
			"\1\u028d\37\uffff\1\u028d",
			"\1\u028e\37\uffff\1\u028e",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0290\37\uffff\1\u0290",
			"",
			"\1\u0291\37\uffff\1\u0291",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0294\37\uffff\1\u0294",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0296\37\uffff\1\u0296",
			"",
			"",
			"\1\u0297\37\uffff\1\u0297",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0299\37\uffff\1\u0299",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u029b\37\uffff\1\u029b",
			"\1\u029c\37\uffff\1\u029c",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u029e\37\uffff\1\u029e",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u02a3\37\uffff\1\u02a3",
			"\1\u02a4\37\uffff\1\u02a4",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u0088\1\uffff\12\u02a6\7\uffff\4\u008a\1\u02a7\1\u008a\32\uffff\4"+
			"\u008a\1\u02a7\1\u008a",
			"\1\u0088\1\uffff\1\u0088\2\uffff\12\u02a8\7\uffff\6\u008a\32\uffff\6"+
			"\u008a",
			"\12\u02a8\7\uffff\6\u008a\32\uffff\6\u008a",
			"",
			"",
			"\1\u008a",
			"",
			"\1\u02a9\37\uffff\1\u02a9",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\22\54\1\u02ac\7\54\4\uffff\1\54\1\uffff\22\54\1\u02ac"+
			"\7\54",
			"\1\u02ad\37\uffff\1\u02ad",
			"\1\u02ae\37\uffff\1\u02ae",
			"\1\u02af\37\uffff\1\u02af",
			"\1\u02b0\37\uffff\1\u02b0",
			"\1\u02b1\37\uffff\1\u02b1",
			"\12\54\7\uffff\22\54\1\u02b2\7\54\4\uffff\1\54\1\uffff\22\54\1\u02b2"+
			"\7\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u02b7\37\uffff\1\u02b7",
			"\1\u02b8\37\uffff\1\u02b8",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\1\u02bc\37\uffff\1\u02bc",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u02be\37\uffff\1\u02be",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u02c0\37\uffff\1\u02c0",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u02c2\37\uffff\1\u02c2",
			"",
			"",
			"",
			"",
			"\1\u02c3\37\uffff\1\u02c3",
			"\1\u02c4\37\uffff\1\u02c4",
			"",
			"\1\u008a\1\u0088\1\uffff\12\62\13\uffff\1\u0088\37\uffff\1\u0088",
			"\1\u0088\1\uffff\1\u02c5\2\uffff\12\u0088",
			"\1\u008a",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"",
			"",
			"\1\u02ce\37\uffff\1\u02ce",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"",
			"\1\u02d0\37\uffff\1\u02d0",
			"",
			"\1\u02d1\37\uffff\1\u02d1",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u02d3\37\uffff\1\u02d3",
			"\1\u02d4\37\uffff\1\u02d4",
			"\1\u02d5\37\uffff\1\u02d5",
			"\12\u02d6\7\uffff\6\u008a\32\uffff\6\u008a",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\12\54\7\uffff\22\54\1\u02d8\7\54\4\uffff\1\54\1\uffff\22\54\1\u02d8"+
			"\7\54",
			"",
			"\1\u02d9\37\uffff\1\u02d9",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"\1\u02db\37\uffff\1\u02db",
			"\1\u02dc\37\uffff\1\u02dc",
			"\1\u02dd\37\uffff\1\u02dd",
			"\12\u02de\7\uffff\6\u008a\32\uffff\6\u008a",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\1\u02e0\37\uffff\1\u02e0",
			"",
			"\1\u02e1\37\uffff\1\u02e1",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\u02e4\7\uffff\6\u008a\32\uffff\6\u008a",
			"",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"\12\54\7\uffff\32\54\4\uffff\1\54\1\uffff\32\54",
			"",
			"",
			"\12\u02e6\7\uffff\6\u008a\32\uffff\6\u008a",
			"",
			"\1\u008a"
	};

	static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
	static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
	static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
	static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
	static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
	static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
	static final short[][] DFA21_transition;

	static {
		int numStates = DFA21_transitionS.length;
		DFA21_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
		}
	}

	protected class DFA21 extends DFA {

		public DFA21(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 21;
			this.eot = DFA21_eot;
			this.eof = DFA21_eof;
			this.min = DFA21_min;
			this.max = DFA21_max;
			this.accept = DFA21_accept;
			this.special = DFA21_special;
			this.transition = DFA21_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__173 | T__174 | T__175 | T__176 | T__177 | T__178 | T__179 | T__180 | T__181 | T__182 | T__183 | T__184 | T__185 | T__186 | T__187 | T__188 | T__189 | T__190 | T__191 | T__192 | K_SELECT | K_FROM | K_AS | K_WHERE | K_AND | K_KEY | K_KEYS | K_ENTRIES | K_FULL | K_INSERT | K_UPDATE | K_WITH | K_LIMIT | K_LIKE | K_PER | K_PARTITION | K_USING | K_USE | K_DISTINCT | K_COUNT | K_SET | K_BEGIN | K_UNLOGGED | K_BATCH | K_APPLY | K_TRUNCATE | K_DELETE | K_IN | K_CREATE | K_KEYSPACE | K_KEYSPACES | K_COLUMNFAMILY | K_MATERIALIZED | K_VIEW | K_INDEX | K_CUSTOM | K_ON | K_TO | K_DROP | K_PRIMARY | K_INTO | K_VALUES | K_TIMESTAMP | K_TTL | K_ALTER | K_RENAME | K_ADD | K_TYPE | K_COMPACT | K_STORAGE | K_ORDER | K_BY | K_ASC | K_DESC | K_ALLOW | K_FILTERING | K_IF | K_IS | K_CONTAINS | K_GRANT | K_ALL | K_PERMISSION | K_PERMISSIONS | K_OF | K_REVOKE | K_MODIFY | K_AUTHORIZE | K_DESCRIBE | K_EXECUTE | K_NORECURSIVE | K_USER | K_USERS | K_ROLE | K_ROLES | K_SUPERUSER | K_NOSUPERUSER | K_PASSWORD | K_LOGIN | K_NOLOGIN | K_OPTIONS | K_CLUSTERING | K_ASCII | K_BIGINT | K_BLOB | K_BOOLEAN | K_COUNTER | K_DECIMAL | K_DOUBLE | K_FLOAT | K_INET | K_INT | K_SMALLINT | K_TINYINT | K_TEXT | K_UUID | K_VARCHAR | K_VARINT | K_TIMEUUID | K_TOKEN | K_WRITETIME | K_DATE | K_TIME | K_NULL | K_NOT | K_EXISTS | K_MAP | K_LIST | K_NAN | K_INFINITY | K_TUPLE | K_TRIGGER | K_STATIC | K_FROZEN | K_FUNCTION | K_FUNCTIONS | K_AGGREGATE | K_SFUNC | K_STYPE | K_FINALFUNC | K_INITCOND | K_RETURNS | K_CALLED | K_INPUT | K_LANGUAGE | K_OR | K_REPLACE | K_JSON | STRING_LITERAL | QUOTED_NAME | INTEGER | QMARK | FLOAT | BOOLEAN | IDENT | HEXNUMBER | UUID | WS | COMMENT | MULTILINE_COMMENT );";
		}
	}

}
