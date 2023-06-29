// $ANTLR 3.5.2 /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g 2023-06-29 08:30:39

    package org.apache.cassandra.cql3;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.EnumSet;
    import java.util.HashSet;
    import java.util.HashMap;
    import java.util.LinkedHashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Set;

    import org.apache.cassandra.auth.*;
    import org.apache.cassandra.cql3.*;
    import org.apache.cassandra.cql3.restrictions.CustomIndexExpression;
    import org.apache.cassandra.cql3.statements.*;
    import org.apache.cassandra.cql3.selection.*;
    import org.apache.cassandra.cql3.functions.*;
    import org.apache.cassandra.db.marshal.CollectionType;
    import org.apache.cassandra.exceptions.ConfigurationException;
    import org.apache.cassandra.exceptions.InvalidRequestException;
    import org.apache.cassandra.exceptions.SyntaxException;
    import org.apache.cassandra.utils.Pair;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class CqlParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "A", "B", "BOOLEAN", "C", "COMMENT", 
		"D", "DIGIT", "E", "EXPONENT", "F", "FLOAT", "G", "H", "HEX", "HEXNUMBER", 
		"I", "IDENT", "INTEGER", "J", "K", "K_ADD", "K_AGGREGATE", "K_ALL", "K_ALLOW", 
		"K_ALTER", "K_AND", "K_APPLY", "K_AS", "K_ASC", "K_ASCII", "K_AUTHORIZE", 
		"K_BATCH", "K_BEGIN", "K_BIGINT", "K_BLOB", "K_BOOLEAN", "K_BY", "K_CALLED", 
		"K_CLUSTERING", "K_COLUMNFAMILY", "K_COMPACT", "K_CONTAINS", "K_COUNT", 
		"K_COUNTER", "K_CREATE", "K_CUSTOM", "K_DATE", "K_DECIMAL", "K_DELETE", 
		"K_DESC", "K_DESCRIBE", "K_DISTINCT", "K_DOUBLE", "K_DROP", "K_ENTRIES", 
		"K_EXECUTE", "K_EXISTS", "K_FILTERING", "K_FINALFUNC", "K_FLOAT", "K_FROM", 
		"K_FROZEN", "K_FULL", "K_FUNCTION", "K_FUNCTIONS", "K_GRANT", "K_IF", 
		"K_IN", "K_INDEX", "K_INET", "K_INFINITY", "K_INITCOND", "K_INPUT", "K_INSERT", 
		"K_INT", "K_INTO", "K_IS", "K_JSON", "K_KEY", "K_KEYS", "K_KEYSPACE", 
		"K_KEYSPACES", "K_LANGUAGE", "K_LIKE", "K_LIMIT", "K_LIST", "K_LOGIN", 
		"K_MAP", "K_MATERIALIZED", "K_MODIFY", "K_NAN", "K_NOLOGIN", "K_NORECURSIVE", 
		"K_NOSUPERUSER", "K_NOT", "K_NULL", "K_OF", "K_ON", "K_OPTIONS", "K_OR", 
		"K_ORDER", "K_PARTITION", "K_PASSWORD", "K_PER", "K_PERMISSION", "K_PERMISSIONS", 
		"K_PRIMARY", "K_RENAME", "K_REPLACE", "K_RETURNS", "K_REVOKE", "K_ROLE", 
		"K_ROLES", "K_SELECT", "K_SET", "K_SFUNC", "K_SMALLINT", "K_STATIC", "K_STORAGE", 
		"K_STYPE", "K_SUPERUSER", "K_TEXT", "K_TIME", "K_TIMESTAMP", "K_TIMEUUID", 
		"K_TINYINT", "K_TO", "K_TOKEN", "K_TRIGGER", "K_TRUNCATE", "K_TTL", "K_TUPLE", 
		"K_TYPE", "K_UNLOGGED", "K_UPDATE", "K_USE", "K_USER", "K_USERS", "K_USING", 
		"K_UUID", "K_VALUES", "K_VARCHAR", "K_VARINT", "K_VIEW", "K_WHERE", "K_WITH", 
		"K_WRITETIME", "L", "LETTER", "M", "MULTILINE_COMMENT", "N", "O", "P", 
		"Q", "QMARK", "QUOTED_NAME", "R", "S", "STRING_LITERAL", "T", "U", "UUID", 
		"V", "W", "WS", "X", "Y", "Z", "'!='", "'('", "')'", "'+'", "','", "'-'", 
		"'.'", "':'", "';'", "'<'", "'<='", "'='", "'>'", "'>='", "'['", "'\\*'", 
		"']'", "'expr('", "'{'", "'}'"
	};
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

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public CqlParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public CqlParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return CqlParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g"; }


	    private final List<ErrorListener> listeners = new ArrayList<ErrorListener>();
	    private final List<ColumnIdentifier> bindVariables = new ArrayList<ColumnIdentifier>();

	    public static final Set<String> reservedTypeNames = new HashSet<String>()
	    {{
	        add("byte");
	        add("complex");
	        add("enum");
	        add("date");
	        add("interval");
	        add("macaddr");
	        add("bitstring");
	    }};

	    public AbstractMarker.Raw newBindVariables(ColumnIdentifier name)
	    {
	        AbstractMarker.Raw marker = new AbstractMarker.Raw(bindVariables.size());
	        bindVariables.add(name);
	        return marker;
	    }

	    public AbstractMarker.INRaw newINBindVariables(ColumnIdentifier name)
	    {
	        AbstractMarker.INRaw marker = new AbstractMarker.INRaw(bindVariables.size());
	        bindVariables.add(name);
	        return marker;
	    }

	    public Tuples.Raw newTupleBindVariables(ColumnIdentifier name)
	    {
	        Tuples.Raw marker = new Tuples.Raw(bindVariables.size());
	        bindVariables.add(name);
	        return marker;
	    }

	    public Tuples.INRaw newTupleINBindVariables(ColumnIdentifier name)
	    {
	        Tuples.INRaw marker = new Tuples.INRaw(bindVariables.size());
	        bindVariables.add(name);
	        return marker;
	    }

	    public Json.Marker newJsonBindVariables(ColumnIdentifier name)
	    {
	        Json.Marker marker = new Json.Marker(bindVariables.size());
	        bindVariables.add(name);
	        return marker;
	    }

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

	    private void addRecognitionError(String msg)
	    {
	        for (int i = 0, m = listeners.size(); i < m; i++)
	            listeners.get(i).syntaxError(this, msg);
	    }

	    public Map<String, String> convertPropertyMap(Maps.Literal map)
	    {
	        if (map == null || map.entries == null || map.entries.isEmpty())
	            return Collections.<String, String>emptyMap();

	        Map<String, String> res = new HashMap<String, String>(map.entries.size());

	        for (Pair<Term.Raw, Term.Raw> entry : map.entries)
	        {
	            // Because the parser tries to be smart and recover on error (to
	            // allow displaying more than one error I suppose), we have null
	            // entries in there. Just skip those, a proper error will be thrown in the end.
	            if (entry.left == null || entry.right == null)
	                break;

	            if (!(entry.left instanceof Constants.Literal))
	            {
	                String msg = "Invalid property name: " + entry.left;
	                if (entry.left instanceof AbstractMarker.Raw)
	                    msg += " (bind variables are not supported in DDL queries)";
	                addRecognitionError(msg);
	                break;
	            }
	            if (!(entry.right instanceof Constants.Literal))
	            {
	                String msg = "Invalid property value: " + entry.right + " for property: " + entry.left;
	                if (entry.right instanceof AbstractMarker.Raw)
	                    msg += " (bind variables are not supported in DDL queries)";
	                addRecognitionError(msg);
	                break;
	            }

	            res.put(((Constants.Literal)entry.left).getRawText(), ((Constants.Literal)entry.right).getRawText());
	        }

	        return res;
	    }

	    public void addRawUpdate(List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key, Operation.RawUpdate update)
	    {
	        for (Pair<ColumnIdentifier.Raw, Operation.RawUpdate> p : operations)
	        {
	            if (p.left.equals(key) && !p.right.isCompatibleWith(update))
	                addRecognitionError("Multiple incompatible setting of column " + key);
	        }
	        operations.add(Pair.create(key, update));
	    }

	    public Set<Permission> filterPermissions(Set<Permission> permissions, IResource resource)
	    {
	        if (resource == null)
	            return Collections.emptySet();
	        Set<Permission> filtered = new HashSet<>(permissions);
	        filtered.retainAll(resource.applicablePermissions());
	        if (filtered.isEmpty())
	            addRecognitionError("Resource type " + resource.getClass().getSimpleName() +
	                                    " does not support any of the requested permissions");

	        return filtered;
	    }



	// $ANTLR start "query"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:231:1: query returns [ParsedStatement stmnt] : st= cqlStatement ( ';' )* EOF ;
	public final ParsedStatement query() throws RecognitionException {
		ParsedStatement stmnt = null;


		ParsedStatement st =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:232:5: (st= cqlStatement ( ';' )* EOF )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:232:7: st= cqlStatement ( ';' )* EOF
			{
			pushFollow(FOLLOW_cqlStatement_in_query72);
			st=cqlStatement();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:232:23: ( ';' )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==181) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:232:24: ';'
					{
					match(input,181,FOLLOW_181_in_query75); 
					}
					break;

				default :
					break loop1;
				}
			}

			match(input,EOF,FOLLOW_EOF_in_query79); 
			 stmnt = st; 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmnt;
	}
	// $ANTLR end "query"



	// $ANTLR start "cqlStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:235:1: cqlStatement returns [ParsedStatement stmt] : (st1= selectStatement |st2= insertStatement |st3= updateStatement |st4= batchStatement |st5= deleteStatement |st6= useStatement |st7= truncateStatement |st8= createKeyspaceStatement |st9= createTableStatement |st10= createIndexStatement |st11= dropKeyspaceStatement |st12= dropTableStatement |st13= dropIndexStatement |st14= alterTableStatement |st15= alterKeyspaceStatement |st16= grantPermissionsStatement |st17= revokePermissionsStatement |st18= listPermissionsStatement |st19= createUserStatement |st20= alterUserStatement |st21= dropUserStatement |st22= listUsersStatement |st23= createTriggerStatement |st24= dropTriggerStatement |st25= createTypeStatement |st26= alterTypeStatement |st27= dropTypeStatement |st28= createFunctionStatement |st29= dropFunctionStatement |st30= createAggregateStatement |st31= dropAggregateStatement |st32= createRoleStatement |st33= alterRoleStatement |st34= dropRoleStatement |st35= listRolesStatement |st36= grantRoleStatement |st37= revokeRoleStatement |st38= createMaterializedViewStatement |st39= dropMaterializedViewStatement |st40= alterMaterializedViewStatement );
	public final ParsedStatement cqlStatement() throws RecognitionException {
		ParsedStatement stmt = null;


		SelectStatement.RawStatement st1 =null;
		ModificationStatement.Parsed st2 =null;
		UpdateStatement.ParsedUpdate st3 =null;
		BatchStatement.Parsed st4 =null;
		DeleteStatement.Parsed st5 =null;
		UseStatement st6 =null;
		TruncateStatement st7 =null;
		CreateKeyspaceStatement st8 =null;
		CreateTableStatement.RawStatement st9 =null;
		CreateIndexStatement st10 =null;
		DropKeyspaceStatement st11 =null;
		DropTableStatement st12 =null;
		DropIndexStatement st13 =null;
		AlterTableStatement st14 =null;
		AlterKeyspaceStatement st15 =null;
		GrantPermissionsStatement st16 =null;
		RevokePermissionsStatement st17 =null;
		ListPermissionsStatement st18 =null;
		CreateRoleStatement st19 =null;
		AlterRoleStatement st20 =null;
		DropRoleStatement st21 =null;
		ListRolesStatement st22 =null;
		CreateTriggerStatement st23 =null;
		DropTriggerStatement st24 =null;
		CreateTypeStatement st25 =null;
		AlterTypeStatement st26 =null;
		DropTypeStatement st27 =null;
		CreateFunctionStatement st28 =null;
		DropFunctionStatement st29 =null;
		CreateAggregateStatement st30 =null;
		DropAggregateStatement st31 =null;
		CreateRoleStatement st32 =null;
		AlterRoleStatement st33 =null;
		DropRoleStatement st34 =null;
		ListRolesStatement st35 =null;
		GrantRoleStatement st36 =null;
		RevokeRoleStatement st37 =null;
		CreateViewStatement st38 =null;
		DropViewStatement st39 =null;
		AlterViewStatement st40 =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:237:5: (st1= selectStatement |st2= insertStatement |st3= updateStatement |st4= batchStatement |st5= deleteStatement |st6= useStatement |st7= truncateStatement |st8= createKeyspaceStatement |st9= createTableStatement |st10= createIndexStatement |st11= dropKeyspaceStatement |st12= dropTableStatement |st13= dropIndexStatement |st14= alterTableStatement |st15= alterKeyspaceStatement |st16= grantPermissionsStatement |st17= revokePermissionsStatement |st18= listPermissionsStatement |st19= createUserStatement |st20= alterUserStatement |st21= dropUserStatement |st22= listUsersStatement |st23= createTriggerStatement |st24= dropTriggerStatement |st25= createTypeStatement |st26= alterTypeStatement |st27= dropTypeStatement |st28= createFunctionStatement |st29= dropFunctionStatement |st30= createAggregateStatement |st31= dropAggregateStatement |st32= createRoleStatement |st33= alterRoleStatement |st34= dropRoleStatement |st35= listRolesStatement |st36= grantRoleStatement |st37= revokeRoleStatement |st38= createMaterializedViewStatement |st39= dropMaterializedViewStatement |st40= alterMaterializedViewStatement )
			int alt2=40;
			alt2 = dfa2.predict(input);
			switch (alt2) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:237:7: st1= selectStatement
					{
					pushFollow(FOLLOW_selectStatement_in_cqlStatement113);
					st1=selectStatement();
					state._fsp--;

					 stmt = st1; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:238:7: st2= insertStatement
					{
					pushFollow(FOLLOW_insertStatement_in_cqlStatement142);
					st2=insertStatement();
					state._fsp--;

					 stmt = st2; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:239:7: st3= updateStatement
					{
					pushFollow(FOLLOW_updateStatement_in_cqlStatement171);
					st3=updateStatement();
					state._fsp--;

					 stmt = st3; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:240:7: st4= batchStatement
					{
					pushFollow(FOLLOW_batchStatement_in_cqlStatement200);
					st4=batchStatement();
					state._fsp--;

					 stmt = st4; 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:241:7: st5= deleteStatement
					{
					pushFollow(FOLLOW_deleteStatement_in_cqlStatement230);
					st5=deleteStatement();
					state._fsp--;

					 stmt = st5; 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:242:7: st6= useStatement
					{
					pushFollow(FOLLOW_useStatement_in_cqlStatement259);
					st6=useStatement();
					state._fsp--;

					 stmt = st6; 
					}
					break;
				case 7 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:243:7: st7= truncateStatement
					{
					pushFollow(FOLLOW_truncateStatement_in_cqlStatement291);
					st7=truncateStatement();
					state._fsp--;

					 stmt = st7; 
					}
					break;
				case 8 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:244:7: st8= createKeyspaceStatement
					{
					pushFollow(FOLLOW_createKeyspaceStatement_in_cqlStatement318);
					st8=createKeyspaceStatement();
					state._fsp--;

					 stmt = st8; 
					}
					break;
				case 9 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:245:7: st9= createTableStatement
					{
					pushFollow(FOLLOW_createTableStatement_in_cqlStatement339);
					st9=createTableStatement();
					state._fsp--;

					 stmt = st9; 
					}
					break;
				case 10 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:246:7: st10= createIndexStatement
					{
					pushFollow(FOLLOW_createIndexStatement_in_cqlStatement362);
					st10=createIndexStatement();
					state._fsp--;

					 stmt = st10; 
					}
					break;
				case 11 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:247:7: st11= dropKeyspaceStatement
					{
					pushFollow(FOLLOW_dropKeyspaceStatement_in_cqlStatement385);
					st11=dropKeyspaceStatement();
					state._fsp--;

					 stmt = st11; 
					}
					break;
				case 12 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:248:7: st12= dropTableStatement
					{
					pushFollow(FOLLOW_dropTableStatement_in_cqlStatement407);
					st12=dropTableStatement();
					state._fsp--;

					 stmt = st12; 
					}
					break;
				case 13 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:249:7: st13= dropIndexStatement
					{
					pushFollow(FOLLOW_dropIndexStatement_in_cqlStatement432);
					st13=dropIndexStatement();
					state._fsp--;

					 stmt = st13; 
					}
					break;
				case 14 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:250:7: st14= alterTableStatement
					{
					pushFollow(FOLLOW_alterTableStatement_in_cqlStatement457);
					st14=alterTableStatement();
					state._fsp--;

					 stmt = st14; 
					}
					break;
				case 15 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:251:7: st15= alterKeyspaceStatement
					{
					pushFollow(FOLLOW_alterKeyspaceStatement_in_cqlStatement481);
					st15=alterKeyspaceStatement();
					state._fsp--;

					 stmt = st15; 
					}
					break;
				case 16 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:252:7: st16= grantPermissionsStatement
					{
					pushFollow(FOLLOW_grantPermissionsStatement_in_cqlStatement502);
					st16=grantPermissionsStatement();
					state._fsp--;

					 stmt = st16; 
					}
					break;
				case 17 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:253:7: st17= revokePermissionsStatement
					{
					pushFollow(FOLLOW_revokePermissionsStatement_in_cqlStatement520);
					st17=revokePermissionsStatement();
					state._fsp--;

					 stmt = st17; 
					}
					break;
				case 18 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:254:7: st18= listPermissionsStatement
					{
					pushFollow(FOLLOW_listPermissionsStatement_in_cqlStatement537);
					st18=listPermissionsStatement();
					state._fsp--;

					 stmt = st18; 
					}
					break;
				case 19 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:255:7: st19= createUserStatement
					{
					pushFollow(FOLLOW_createUserStatement_in_cqlStatement556);
					st19=createUserStatement();
					state._fsp--;

					 stmt = st19; 
					}
					break;
				case 20 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:256:7: st20= alterUserStatement
					{
					pushFollow(FOLLOW_alterUserStatement_in_cqlStatement580);
					st20=alterUserStatement();
					state._fsp--;

					 stmt = st20; 
					}
					break;
				case 21 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:257:7: st21= dropUserStatement
					{
					pushFollow(FOLLOW_dropUserStatement_in_cqlStatement605);
					st21=dropUserStatement();
					state._fsp--;

					 stmt = st21; 
					}
					break;
				case 22 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:258:7: st22= listUsersStatement
					{
					pushFollow(FOLLOW_listUsersStatement_in_cqlStatement631);
					st22=listUsersStatement();
					state._fsp--;

					 stmt = st22; 
					}
					break;
				case 23 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:259:7: st23= createTriggerStatement
					{
					pushFollow(FOLLOW_createTriggerStatement_in_cqlStatement656);
					st23=createTriggerStatement();
					state._fsp--;

					 stmt = st23; 
					}
					break;
				case 24 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:260:7: st24= dropTriggerStatement
					{
					pushFollow(FOLLOW_dropTriggerStatement_in_cqlStatement677);
					st24=dropTriggerStatement();
					state._fsp--;

					 stmt = st24; 
					}
					break;
				case 25 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:261:7: st25= createTypeStatement
					{
					pushFollow(FOLLOW_createTypeStatement_in_cqlStatement700);
					st25=createTypeStatement();
					state._fsp--;

					 stmt = st25; 
					}
					break;
				case 26 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:262:7: st26= alterTypeStatement
					{
					pushFollow(FOLLOW_alterTypeStatement_in_cqlStatement724);
					st26=alterTypeStatement();
					state._fsp--;

					 stmt = st26; 
					}
					break;
				case 27 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:263:7: st27= dropTypeStatement
					{
					pushFollow(FOLLOW_dropTypeStatement_in_cqlStatement749);
					st27=dropTypeStatement();
					state._fsp--;

					 stmt = st27; 
					}
					break;
				case 28 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:264:7: st28= createFunctionStatement
					{
					pushFollow(FOLLOW_createFunctionStatement_in_cqlStatement775);
					st28=createFunctionStatement();
					state._fsp--;

					 stmt = st28; 
					}
					break;
				case 29 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:265:7: st29= dropFunctionStatement
					{
					pushFollow(FOLLOW_dropFunctionStatement_in_cqlStatement795);
					st29=dropFunctionStatement();
					state._fsp--;

					 stmt = st29; 
					}
					break;
				case 30 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:266:7: st30= createAggregateStatement
					{
					pushFollow(FOLLOW_createAggregateStatement_in_cqlStatement817);
					st30=createAggregateStatement();
					state._fsp--;

					 stmt = st30; 
					}
					break;
				case 31 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:267:7: st31= dropAggregateStatement
					{
					pushFollow(FOLLOW_dropAggregateStatement_in_cqlStatement836);
					st31=dropAggregateStatement();
					state._fsp--;

					 stmt = st31; 
					}
					break;
				case 32 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:268:7: st32= createRoleStatement
					{
					pushFollow(FOLLOW_createRoleStatement_in_cqlStatement857);
					st32=createRoleStatement();
					state._fsp--;

					 stmt = st32; 
					}
					break;
				case 33 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:269:7: st33= alterRoleStatement
					{
					pushFollow(FOLLOW_alterRoleStatement_in_cqlStatement881);
					st33=alterRoleStatement();
					state._fsp--;

					 stmt = st33; 
					}
					break;
				case 34 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:270:7: st34= dropRoleStatement
					{
					pushFollow(FOLLOW_dropRoleStatement_in_cqlStatement906);
					st34=dropRoleStatement();
					state._fsp--;

					 stmt = st34; 
					}
					break;
				case 35 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:271:7: st35= listRolesStatement
					{
					pushFollow(FOLLOW_listRolesStatement_in_cqlStatement932);
					st35=listRolesStatement();
					state._fsp--;

					 stmt = st35; 
					}
					break;
				case 36 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:272:7: st36= grantRoleStatement
					{
					pushFollow(FOLLOW_grantRoleStatement_in_cqlStatement957);
					st36=grantRoleStatement();
					state._fsp--;

					 stmt = st36; 
					}
					break;
				case 37 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:273:7: st37= revokeRoleStatement
					{
					pushFollow(FOLLOW_revokeRoleStatement_in_cqlStatement982);
					st37=revokeRoleStatement();
					state._fsp--;

					 stmt = st37; 
					}
					break;
				case 38 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:274:7: st38= createMaterializedViewStatement
					{
					pushFollow(FOLLOW_createMaterializedViewStatement_in_cqlStatement1006);
					st38=createMaterializedViewStatement();
					state._fsp--;

					 stmt = st38; 
					}
					break;
				case 39 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:275:7: st39= dropMaterializedViewStatement
					{
					pushFollow(FOLLOW_dropMaterializedViewStatement_in_cqlStatement1018);
					st39=dropMaterializedViewStatement();
					state._fsp--;

					 stmt = st39; 
					}
					break;
				case 40 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:276:7: st40= alterMaterializedViewStatement
					{
					pushFollow(FOLLOW_alterMaterializedViewStatement_in_cqlStatement1032);
					st40=alterMaterializedViewStatement();
					state._fsp--;

					 stmt = st40; 
					}
					break;

			}
			 if (stmt != null) stmt.setBoundVariables(bindVariables); 
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "cqlStatement"



	// $ANTLR start "useStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:282:1: useStatement returns [UseStatement stmt] : K_USE ks= keyspaceName ;
	public final UseStatement useStatement() throws RecognitionException {
		UseStatement stmt = null;


		String ks =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:283:5: ( K_USE ks= keyspaceName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:283:7: K_USE ks= keyspaceName
			{
			match(input,K_USE,FOLLOW_K_USE_in_useStatement1058); 
			pushFollow(FOLLOW_keyspaceName_in_useStatement1062);
			ks=keyspaceName();
			state._fsp--;

			 stmt = new UseStatement(ks); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "useStatement"



	// $ANTLR start "selectStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:292:1: selectStatement returns [SelectStatement.RawStatement expr] : K_SELECT ( K_JSON )? ( ( K_DISTINCT )? sclause= selectClause ) K_FROM cf= columnFamilyName ( K_WHERE wclause= whereClause )? ( K_ORDER K_BY orderByClause[orderings] ( ',' orderByClause[orderings] )* )? ( K_PER K_PARTITION K_LIMIT rows= intValue )? ( K_LIMIT rows= intValue )? ( K_ALLOW K_FILTERING )? ;
	public final SelectStatement.RawStatement selectStatement() throws RecognitionException {
		SelectStatement.RawStatement expr = null;


		List<RawSelector> sclause =null;
		CFName cf =null;
		WhereClause.Builder wclause =null;
		Term.Raw rows =null;


		        boolean isDistinct = false;
		        Term.Raw limit = null;
		        Term.Raw perPartitionLimit = null;
		        Map<ColumnIdentifier.Raw, Boolean> orderings = new LinkedHashMap<ColumnIdentifier.Raw, Boolean>();
		        boolean allowFiltering = false;
		        boolean isJson = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:301:5: ( K_SELECT ( K_JSON )? ( ( K_DISTINCT )? sclause= selectClause ) K_FROM cf= columnFamilyName ( K_WHERE wclause= whereClause )? ( K_ORDER K_BY orderByClause[orderings] ( ',' orderByClause[orderings] )* )? ( K_PER K_PARTITION K_LIMIT rows= intValue )? ( K_LIMIT rows= intValue )? ( K_ALLOW K_FILTERING )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:301:7: K_SELECT ( K_JSON )? ( ( K_DISTINCT )? sclause= selectClause ) K_FROM cf= columnFamilyName ( K_WHERE wclause= whereClause )? ( K_ORDER K_BY orderByClause[orderings] ( ',' orderByClause[orderings] )* )? ( K_PER K_PARTITION K_LIMIT rows= intValue )? ( K_LIMIT rows= intValue )? ( K_ALLOW K_FILTERING )?
			{
			match(input,K_SELECT,FOLLOW_K_SELECT_in_selectStatement1096); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:302:7: ( K_JSON )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==K_JSON) ) {
				int LA3_1 = input.LA(2);
				if ( (LA3_1==IDENT||(LA3_1 >= K_AGGREGATE && LA3_1 <= K_ALL)||LA3_1==K_ASCII||(LA3_1 >= K_BIGINT && LA3_1 <= K_BOOLEAN)||(LA3_1 >= K_CALLED && LA3_1 <= K_CLUSTERING)||(LA3_1 >= K_COMPACT && LA3_1 <= K_COUNTER)||(LA3_1 >= K_CUSTOM && LA3_1 <= K_DECIMAL)||(LA3_1 >= K_DISTINCT && LA3_1 <= K_DOUBLE)||(LA3_1 >= K_EXISTS && LA3_1 <= K_FLOAT)||LA3_1==K_FROZEN||(LA3_1 >= K_FUNCTION && LA3_1 <= K_FUNCTIONS)||LA3_1==K_INET||(LA3_1 >= K_INITCOND && LA3_1 <= K_INPUT)||LA3_1==K_INT||(LA3_1 >= K_JSON && LA3_1 <= K_KEYS)||(LA3_1 >= K_KEYSPACES && LA3_1 <= K_LIKE)||(LA3_1 >= K_LIST && LA3_1 <= K_MAP)||LA3_1==K_NOLOGIN||LA3_1==K_NOSUPERUSER||LA3_1==K_OPTIONS||(LA3_1 >= K_PARTITION && LA3_1 <= K_PERMISSIONS)||LA3_1==K_RETURNS||(LA3_1 >= K_ROLE && LA3_1 <= K_ROLES)||(LA3_1 >= K_SFUNC && LA3_1 <= K_TINYINT)||(LA3_1 >= K_TOKEN && LA3_1 <= K_TRIGGER)||(LA3_1 >= K_TTL && LA3_1 <= K_TYPE)||(LA3_1 >= K_USER && LA3_1 <= K_USERS)||(LA3_1 >= K_UUID && LA3_1 <= K_VARINT)||LA3_1==K_WRITETIME||(LA3_1 >= QMARK && LA3_1 <= QUOTED_NAME)||LA3_1==188) ) {
					alt3=1;
				}
				else if ( (LA3_1==K_AS) ) {
					int LA3_4 = input.LA(3);
					if ( (LA3_4==K_FROM||LA3_4==174||LA3_4==177||LA3_4==179) ) {
						alt3=1;
					}
					else if ( (LA3_4==K_AS) ) {
						int LA3_5 = input.LA(4);
						if ( (LA3_5==IDENT||(LA3_5 >= K_AGGREGATE && LA3_5 <= K_ALL)||LA3_5==K_AS||LA3_5==K_ASCII||(LA3_5 >= K_BIGINT && LA3_5 <= K_BOOLEAN)||(LA3_5 >= K_CALLED && LA3_5 <= K_CLUSTERING)||(LA3_5 >= K_COMPACT && LA3_5 <= K_COUNTER)||(LA3_5 >= K_CUSTOM && LA3_5 <= K_DECIMAL)||(LA3_5 >= K_DISTINCT && LA3_5 <= K_DOUBLE)||(LA3_5 >= K_EXISTS && LA3_5 <= K_FLOAT)||LA3_5==K_FROZEN||(LA3_5 >= K_FUNCTION && LA3_5 <= K_FUNCTIONS)||LA3_5==K_INET||(LA3_5 >= K_INITCOND && LA3_5 <= K_INPUT)||LA3_5==K_INT||(LA3_5 >= K_JSON && LA3_5 <= K_KEYS)||(LA3_5 >= K_KEYSPACES && LA3_5 <= K_LIKE)||(LA3_5 >= K_LIST && LA3_5 <= K_MAP)||LA3_5==K_NOLOGIN||LA3_5==K_NOSUPERUSER||LA3_5==K_OPTIONS||(LA3_5 >= K_PARTITION && LA3_5 <= K_PERMISSIONS)||LA3_5==K_RETURNS||(LA3_5 >= K_ROLE && LA3_5 <= K_ROLES)||(LA3_5 >= K_SFUNC && LA3_5 <= K_TINYINT)||LA3_5==K_TRIGGER||(LA3_5 >= K_TTL && LA3_5 <= K_TYPE)||(LA3_5 >= K_USER && LA3_5 <= K_USERS)||(LA3_5 >= K_UUID && LA3_5 <= K_VARINT)||LA3_5==K_WRITETIME||LA3_5==QUOTED_NAME) ) {
							alt3=1;
						}
					}
				}
			}
			switch (alt3) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:302:9: K_JSON
					{
					match(input,K_JSON,FOLLOW_K_JSON_in_selectStatement1107); 
					 isJson = true; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:303:7: ( ( K_DISTINCT )? sclause= selectClause )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:303:9: ( K_DISTINCT )? sclause= selectClause
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:303:9: ( K_DISTINCT )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==K_DISTINCT) ) {
				int LA4_1 = input.LA(2);
				if ( (LA4_1==IDENT||(LA4_1 >= K_AGGREGATE && LA4_1 <= K_ALL)||LA4_1==K_ASCII||(LA4_1 >= K_BIGINT && LA4_1 <= K_BOOLEAN)||(LA4_1 >= K_CALLED && LA4_1 <= K_CLUSTERING)||(LA4_1 >= K_COMPACT && LA4_1 <= K_COUNTER)||(LA4_1 >= K_CUSTOM && LA4_1 <= K_DECIMAL)||(LA4_1 >= K_DISTINCT && LA4_1 <= K_DOUBLE)||(LA4_1 >= K_EXISTS && LA4_1 <= K_FLOAT)||LA4_1==K_FROZEN||(LA4_1 >= K_FUNCTION && LA4_1 <= K_FUNCTIONS)||LA4_1==K_INET||(LA4_1 >= K_INITCOND && LA4_1 <= K_INPUT)||LA4_1==K_INT||(LA4_1 >= K_JSON && LA4_1 <= K_KEYS)||(LA4_1 >= K_KEYSPACES && LA4_1 <= K_LIKE)||(LA4_1 >= K_LIST && LA4_1 <= K_MAP)||LA4_1==K_NOLOGIN||LA4_1==K_NOSUPERUSER||LA4_1==K_OPTIONS||(LA4_1 >= K_PARTITION && LA4_1 <= K_PERMISSIONS)||LA4_1==K_RETURNS||(LA4_1 >= K_ROLE && LA4_1 <= K_ROLES)||(LA4_1 >= K_SFUNC && LA4_1 <= K_TINYINT)||(LA4_1 >= K_TOKEN && LA4_1 <= K_TRIGGER)||(LA4_1 >= K_TTL && LA4_1 <= K_TYPE)||(LA4_1 >= K_USER && LA4_1 <= K_USERS)||(LA4_1 >= K_UUID && LA4_1 <= K_VARINT)||LA4_1==K_WRITETIME||(LA4_1 >= QMARK && LA4_1 <= QUOTED_NAME)||LA4_1==188) ) {
					alt4=1;
				}
				else if ( (LA4_1==K_AS) ) {
					int LA4_4 = input.LA(3);
					if ( (LA4_4==K_FROM||LA4_4==174||LA4_4==177||LA4_4==179) ) {
						alt4=1;
					}
					else if ( (LA4_4==K_AS) ) {
						int LA4_5 = input.LA(4);
						if ( (LA4_5==IDENT||(LA4_5 >= K_AGGREGATE && LA4_5 <= K_ALL)||LA4_5==K_AS||LA4_5==K_ASCII||(LA4_5 >= K_BIGINT && LA4_5 <= K_BOOLEAN)||(LA4_5 >= K_CALLED && LA4_5 <= K_CLUSTERING)||(LA4_5 >= K_COMPACT && LA4_5 <= K_COUNTER)||(LA4_5 >= K_CUSTOM && LA4_5 <= K_DECIMAL)||(LA4_5 >= K_DISTINCT && LA4_5 <= K_DOUBLE)||(LA4_5 >= K_EXISTS && LA4_5 <= K_FLOAT)||LA4_5==K_FROZEN||(LA4_5 >= K_FUNCTION && LA4_5 <= K_FUNCTIONS)||LA4_5==K_INET||(LA4_5 >= K_INITCOND && LA4_5 <= K_INPUT)||LA4_5==K_INT||(LA4_5 >= K_JSON && LA4_5 <= K_KEYS)||(LA4_5 >= K_KEYSPACES && LA4_5 <= K_LIKE)||(LA4_5 >= K_LIST && LA4_5 <= K_MAP)||LA4_5==K_NOLOGIN||LA4_5==K_NOSUPERUSER||LA4_5==K_OPTIONS||(LA4_5 >= K_PARTITION && LA4_5 <= K_PERMISSIONS)||LA4_5==K_RETURNS||(LA4_5 >= K_ROLE && LA4_5 <= K_ROLES)||(LA4_5 >= K_SFUNC && LA4_5 <= K_TINYINT)||LA4_5==K_TRIGGER||(LA4_5 >= K_TTL && LA4_5 <= K_TYPE)||(LA4_5 >= K_USER && LA4_5 <= K_USERS)||(LA4_5 >= K_UUID && LA4_5 <= K_VARINT)||LA4_5==K_WRITETIME||LA4_5==QUOTED_NAME) ) {
							alt4=1;
						}
					}
				}
			}
			switch (alt4) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:303:11: K_DISTINCT
					{
					match(input,K_DISTINCT,FOLLOW_K_DISTINCT_in_selectStatement1124); 
					 isDistinct = true; 
					}
					break;

			}

			pushFollow(FOLLOW_selectClause_in_selectStatement1133);
			sclause=selectClause();
			state._fsp--;

			}

			match(input,K_FROM,FOLLOW_K_FROM_in_selectStatement1143); 
			pushFollow(FOLLOW_columnFamilyName_in_selectStatement1147);
			cf=columnFamilyName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:305:7: ( K_WHERE wclause= whereClause )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==K_WHERE) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:305:9: K_WHERE wclause= whereClause
					{
					match(input,K_WHERE,FOLLOW_K_WHERE_in_selectStatement1157); 
					pushFollow(FOLLOW_whereClause_in_selectStatement1161);
					wclause=whereClause();
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:306:7: ( K_ORDER K_BY orderByClause[orderings] ( ',' orderByClause[orderings] )* )?
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==K_ORDER) ) {
				alt7=1;
			}
			switch (alt7) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:306:9: K_ORDER K_BY orderByClause[orderings] ( ',' orderByClause[orderings] )*
					{
					match(input,K_ORDER,FOLLOW_K_ORDER_in_selectStatement1174); 
					match(input,K_BY,FOLLOW_K_BY_in_selectStatement1176); 
					pushFollow(FOLLOW_orderByClause_in_selectStatement1178);
					orderByClause(orderings);
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:306:47: ( ',' orderByClause[orderings] )*
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( (LA6_0==177) ) {
							alt6=1;
						}

						switch (alt6) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:306:49: ',' orderByClause[orderings]
							{
							match(input,177,FOLLOW_177_in_selectStatement1183); 
							pushFollow(FOLLOW_orderByClause_in_selectStatement1185);
							orderByClause(orderings);
							state._fsp--;

							}
							break;

						default :
							break loop6;
						}
					}

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:307:7: ( K_PER K_PARTITION K_LIMIT rows= intValue )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==K_PER) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:307:9: K_PER K_PARTITION K_LIMIT rows= intValue
					{
					match(input,K_PER,FOLLOW_K_PER_in_selectStatement1202); 
					match(input,K_PARTITION,FOLLOW_K_PARTITION_in_selectStatement1204); 
					match(input,K_LIMIT,FOLLOW_K_LIMIT_in_selectStatement1206); 
					pushFollow(FOLLOW_intValue_in_selectStatement1210);
					rows=intValue();
					state._fsp--;

					 perPartitionLimit = rows; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:308:7: ( K_LIMIT rows= intValue )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==K_LIMIT) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:308:9: K_LIMIT rows= intValue
					{
					match(input,K_LIMIT,FOLLOW_K_LIMIT_in_selectStatement1225); 
					pushFollow(FOLLOW_intValue_in_selectStatement1229);
					rows=intValue();
					state._fsp--;

					 limit = rows; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:309:7: ( K_ALLOW K_FILTERING )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==K_ALLOW) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:309:9: K_ALLOW K_FILTERING
					{
					match(input,K_ALLOW,FOLLOW_K_ALLOW_in_selectStatement1244); 
					match(input,K_FILTERING,FOLLOW_K_FILTERING_in_selectStatement1246); 
					 allowFiltering = true; 
					}
					break;

			}


			          SelectStatement.Parameters params = new SelectStatement.Parameters(orderings,
			                                                                             isDistinct,
			                                                                             allowFiltering,
			                                                                             isJson);
			          WhereClause where = wclause == null ? WhereClause.empty() : wclause.build();
			          expr = new SelectStatement.RawStatement(cf, params, sclause, where, limit, perPartitionLimit);
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "selectStatement"



	// $ANTLR start "selectClause"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:320:1: selectClause returns [List<RawSelector> expr] : (t1= selector ( ',' tN= selector )* | '\\*' );
	public final List<RawSelector> selectClause() throws RecognitionException {
		List<RawSelector> expr = null;


		RawSelector t1 =null;
		RawSelector tN =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:321:5: (t1= selector ( ',' tN= selector )* | '\\*' )
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==IDENT||(LA12_0 >= K_AGGREGATE && LA12_0 <= K_ALL)||LA12_0==K_AS||LA12_0==K_ASCII||(LA12_0 >= K_BIGINT && LA12_0 <= K_BOOLEAN)||(LA12_0 >= K_CALLED && LA12_0 <= K_CLUSTERING)||(LA12_0 >= K_COMPACT && LA12_0 <= K_COUNTER)||(LA12_0 >= K_CUSTOM && LA12_0 <= K_DECIMAL)||(LA12_0 >= K_DISTINCT && LA12_0 <= K_DOUBLE)||(LA12_0 >= K_EXISTS && LA12_0 <= K_FLOAT)||LA12_0==K_FROZEN||(LA12_0 >= K_FUNCTION && LA12_0 <= K_FUNCTIONS)||LA12_0==K_INET||(LA12_0 >= K_INITCOND && LA12_0 <= K_INPUT)||LA12_0==K_INT||(LA12_0 >= K_JSON && LA12_0 <= K_KEYS)||(LA12_0 >= K_KEYSPACES && LA12_0 <= K_LIKE)||(LA12_0 >= K_LIST && LA12_0 <= K_MAP)||LA12_0==K_NOLOGIN||LA12_0==K_NOSUPERUSER||LA12_0==K_OPTIONS||(LA12_0 >= K_PARTITION && LA12_0 <= K_PERMISSIONS)||LA12_0==K_RETURNS||(LA12_0 >= K_ROLE && LA12_0 <= K_ROLES)||(LA12_0 >= K_SFUNC && LA12_0 <= K_TINYINT)||(LA12_0 >= K_TOKEN && LA12_0 <= K_TRIGGER)||(LA12_0 >= K_TTL && LA12_0 <= K_TYPE)||(LA12_0 >= K_USER && LA12_0 <= K_USERS)||(LA12_0 >= K_UUID && LA12_0 <= K_VARINT)||LA12_0==K_WRITETIME||(LA12_0 >= QMARK && LA12_0 <= QUOTED_NAME)) ) {
				alt12=1;
			}
			else if ( (LA12_0==188) ) {
				alt12=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 12, 0, input);
				throw nvae;
			}

			switch (alt12) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:321:7: t1= selector ( ',' tN= selector )*
					{
					pushFollow(FOLLOW_selector_in_selectClause1283);
					t1=selector();
					state._fsp--;

					 expr = new ArrayList<RawSelector>(); expr.add(t1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:321:76: ( ',' tN= selector )*
					loop11:
					while (true) {
						int alt11=2;
						int LA11_0 = input.LA(1);
						if ( (LA11_0==177) ) {
							alt11=1;
						}

						switch (alt11) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:321:77: ',' tN= selector
							{
							match(input,177,FOLLOW_177_in_selectClause1288); 
							pushFollow(FOLLOW_selector_in_selectClause1292);
							tN=selector();
							state._fsp--;

							 expr.add(tN); 
							}
							break;

						default :
							break loop11;
						}
					}

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:322:7: '\\*'
					{
					match(input,188,FOLLOW_188_in_selectClause1304); 
					 expr = Collections.<RawSelector>emptyList();
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "selectClause"



	// $ANTLR start "selector"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:325:1: selector returns [RawSelector s] : us= unaliasedSelector ( K_AS c= noncol_ident )? ;
	public final RawSelector selector() throws RecognitionException {
		RawSelector s = null;


		Selectable.Raw us =null;
		ColumnIdentifier c =null;

		 ColumnIdentifier alias = null; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:327:5: (us= unaliasedSelector ( K_AS c= noncol_ident )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:327:7: us= unaliasedSelector ( K_AS c= noncol_ident )?
			{
			pushFollow(FOLLOW_unaliasedSelector_in_selector1337);
			us=unaliasedSelector();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:327:28: ( K_AS c= noncol_ident )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==K_AS) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:327:29: K_AS c= noncol_ident
					{
					match(input,K_AS,FOLLOW_K_AS_in_selector1340); 
					pushFollow(FOLLOW_noncol_ident_in_selector1344);
					c=noncol_ident();
					state._fsp--;

					 alias = c; 
					}
					break;

			}

			 s = new RawSelector(us, alias); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return s;
	}
	// $ANTLR end "selector"



	// $ANTLR start "unaliasedSelector"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:330:1: unaliasedSelector returns [Selectable.Raw s] : (c= cident | K_COUNT '(' countArgument ')' | K_WRITETIME '(' c= cident ')' | K_TTL '(' c= cident ')' |f= functionName args= selectionFunctionArgs ) ( '.' fi= cident )* ;
	public final Selectable.Raw unaliasedSelector() throws RecognitionException {
		Selectable.Raw s = null;


		ColumnIdentifier.Raw c =null;
		FunctionName f =null;
		List<Selectable.Raw> args =null;
		ColumnIdentifier.Raw fi =null;

		 Selectable.Raw tmp = null; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:332:5: ( (c= cident | K_COUNT '(' countArgument ')' | K_WRITETIME '(' c= cident ')' | K_TTL '(' c= cident ')' |f= functionName args= selectionFunctionArgs ) ( '.' fi= cident )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:332:8: (c= cident | K_COUNT '(' countArgument ')' | K_WRITETIME '(' c= cident ')' | K_TTL '(' c= cident ')' |f= functionName args= selectionFunctionArgs ) ( '.' fi= cident )*
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:332:8: (c= cident | K_COUNT '(' countArgument ')' | K_WRITETIME '(' c= cident ')' | K_TTL '(' c= cident ')' |f= functionName args= selectionFunctionArgs )
			int alt14=5;
			alt14 = dfa14.predict(input);
			switch (alt14) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:332:10: c= cident
					{
					pushFollow(FOLLOW_cident_in_unaliasedSelector1385);
					c=cident();
					state._fsp--;

					 tmp = c; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:333:10: K_COUNT '(' countArgument ')'
					{
					match(input,K_COUNT,FOLLOW_K_COUNT_in_unaliasedSelector1431); 
					match(input,174,FOLLOW_174_in_unaliasedSelector1433); 
					pushFollow(FOLLOW_countArgument_in_unaliasedSelector1435);
					countArgument();
					state._fsp--;

					match(input,175,FOLLOW_175_in_unaliasedSelector1437); 
					 tmp = new Selectable.WithFunction.Raw(FunctionName.nativeFunction("countRows"), Collections.<Selectable.Raw>emptyList());
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:334:10: K_WRITETIME '(' c= cident ')'
					{
					match(input,K_WRITETIME,FOLLOW_K_WRITETIME_in_unaliasedSelector1462); 
					match(input,174,FOLLOW_174_in_unaliasedSelector1464); 
					pushFollow(FOLLOW_cident_in_unaliasedSelector1468);
					c=cident();
					state._fsp--;

					match(input,175,FOLLOW_175_in_unaliasedSelector1470); 
					 tmp = new Selectable.WritetimeOrTTL.Raw(c, true); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:335:10: K_TTL '(' c= cident ')'
					{
					match(input,K_TTL,FOLLOW_K_TTL_in_unaliasedSelector1496); 
					match(input,174,FOLLOW_174_in_unaliasedSelector1504); 
					pushFollow(FOLLOW_cident_in_unaliasedSelector1508);
					c=cident();
					state._fsp--;

					match(input,175,FOLLOW_175_in_unaliasedSelector1510); 
					 tmp = new Selectable.WritetimeOrTTL.Raw(c, false); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:336:10: f= functionName args= selectionFunctionArgs
					{
					pushFollow(FOLLOW_functionName_in_unaliasedSelector1538);
					f=functionName();
					state._fsp--;

					pushFollow(FOLLOW_selectionFunctionArgs_in_unaliasedSelector1542);
					args=selectionFunctionArgs();
					state._fsp--;

					 tmp = new Selectable.WithFunction.Raw(f, args); 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:337:10: ( '.' fi= cident )*
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( (LA15_0==179) ) {
					alt15=1;
				}

				switch (alt15) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:337:12: '.' fi= cident
					{
					match(input,179,FOLLOW_179_in_unaliasedSelector1557); 
					pushFollow(FOLLOW_cident_in_unaliasedSelector1561);
					fi=cident();
					state._fsp--;

					 tmp = new Selectable.WithFieldSelection.Raw(tmp, fi); 
					}
					break;

				default :
					break loop15;
				}
			}

			 s = tmp; 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return s;
	}
	// $ANTLR end "unaliasedSelector"



	// $ANTLR start "selectionFunctionArgs"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:340:1: selectionFunctionArgs returns [List<Selectable.Raw> a] : ( '(' ')' | '(' s1= unaliasedSelector ( ',' sn= unaliasedSelector )* ')' );
	public final List<Selectable.Raw> selectionFunctionArgs() throws RecognitionException {
		List<Selectable.Raw> a = null;


		Selectable.Raw s1 =null;
		Selectable.Raw sn =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:341:5: ( '(' ')' | '(' s1= unaliasedSelector ( ',' sn= unaliasedSelector )* ')' )
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==174) ) {
				int LA17_1 = input.LA(2);
				if ( (LA17_1==175) ) {
					alt17=1;
				}
				else if ( (LA17_1==IDENT||(LA17_1 >= K_AGGREGATE && LA17_1 <= K_ALL)||LA17_1==K_AS||LA17_1==K_ASCII||(LA17_1 >= K_BIGINT && LA17_1 <= K_BOOLEAN)||(LA17_1 >= K_CALLED && LA17_1 <= K_CLUSTERING)||(LA17_1 >= K_COMPACT && LA17_1 <= K_COUNTER)||(LA17_1 >= K_CUSTOM && LA17_1 <= K_DECIMAL)||(LA17_1 >= K_DISTINCT && LA17_1 <= K_DOUBLE)||(LA17_1 >= K_EXISTS && LA17_1 <= K_FLOAT)||LA17_1==K_FROZEN||(LA17_1 >= K_FUNCTION && LA17_1 <= K_FUNCTIONS)||LA17_1==K_INET||(LA17_1 >= K_INITCOND && LA17_1 <= K_INPUT)||LA17_1==K_INT||(LA17_1 >= K_JSON && LA17_1 <= K_KEYS)||(LA17_1 >= K_KEYSPACES && LA17_1 <= K_LIKE)||(LA17_1 >= K_LIST && LA17_1 <= K_MAP)||LA17_1==K_NOLOGIN||LA17_1==K_NOSUPERUSER||LA17_1==K_OPTIONS||(LA17_1 >= K_PARTITION && LA17_1 <= K_PERMISSIONS)||LA17_1==K_RETURNS||(LA17_1 >= K_ROLE && LA17_1 <= K_ROLES)||(LA17_1 >= K_SFUNC && LA17_1 <= K_TINYINT)||(LA17_1 >= K_TOKEN && LA17_1 <= K_TRIGGER)||(LA17_1 >= K_TTL && LA17_1 <= K_TYPE)||(LA17_1 >= K_USER && LA17_1 <= K_USERS)||(LA17_1 >= K_UUID && LA17_1 <= K_VARINT)||LA17_1==K_WRITETIME||(LA17_1 >= QMARK && LA17_1 <= QUOTED_NAME)) ) {
					alt17=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 17, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 17, 0, input);
				throw nvae;
			}

			switch (alt17) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:341:7: '(' ')'
					{
					match(input,174,FOLLOW_174_in_selectionFunctionArgs1589); 
					match(input,175,FOLLOW_175_in_selectionFunctionArgs1591); 
					 a = Collections.emptyList(); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:342:7: '(' s1= unaliasedSelector ( ',' sn= unaliasedSelector )* ')'
					{
					match(input,174,FOLLOW_174_in_selectionFunctionArgs1601); 
					pushFollow(FOLLOW_unaliasedSelector_in_selectionFunctionArgs1605);
					s1=unaliasedSelector();
					state._fsp--;

					 List<Selectable.Raw> args = new ArrayList<Selectable.Raw>(); args.add(s1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:343:11: ( ',' sn= unaliasedSelector )*
					loop16:
					while (true) {
						int alt16=2;
						int LA16_0 = input.LA(1);
						if ( (LA16_0==177) ) {
							alt16=1;
						}

						switch (alt16) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:343:13: ',' sn= unaliasedSelector
							{
							match(input,177,FOLLOW_177_in_selectionFunctionArgs1621); 
							pushFollow(FOLLOW_unaliasedSelector_in_selectionFunctionArgs1625);
							sn=unaliasedSelector();
							state._fsp--;

							 args.add(sn); 
							}
							break;

						default :
							break loop16;
						}
					}

					match(input,175,FOLLOW_175_in_selectionFunctionArgs1638); 
					 a = args; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return a;
	}
	// $ANTLR end "selectionFunctionArgs"



	// $ANTLR start "countArgument"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:347:1: countArgument : ( '\\*' |i= INTEGER );
	public final void countArgument() throws RecognitionException {
		Token i=null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:348:5: ( '\\*' |i= INTEGER )
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==188) ) {
				alt18=1;
			}
			else if ( (LA18_0==INTEGER) ) {
				alt18=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				throw nvae;
			}

			switch (alt18) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:348:7: '\\*'
					{
					match(input,188,FOLLOW_188_in_countArgument1657); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:349:7: i= INTEGER
					{
					i=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_countArgument1667); 
					 if (!i.getText().equals("1")) addRecognitionError("Only COUNT(1) is supported, got COUNT(" + i.getText() + ")");
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "countArgument"



	// $ANTLR start "whereClause"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:352:1: whereClause returns [WhereClause.Builder clause] : relationOrExpression[$clause] ( K_AND relationOrExpression[$clause] )* ;
	public final WhereClause.Builder whereClause() throws RecognitionException {
		WhereClause.Builder clause = null;


		 clause = new WhereClause.Builder(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:354:5: ( relationOrExpression[$clause] ( K_AND relationOrExpression[$clause] )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:354:7: relationOrExpression[$clause] ( K_AND relationOrExpression[$clause] )*
			{
			pushFollow(FOLLOW_relationOrExpression_in_whereClause1698);
			relationOrExpression(clause);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:354:37: ( K_AND relationOrExpression[$clause] )*
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==K_AND) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:354:38: K_AND relationOrExpression[$clause]
					{
					match(input,K_AND,FOLLOW_K_AND_in_whereClause1702); 
					pushFollow(FOLLOW_relationOrExpression_in_whereClause1704);
					relationOrExpression(clause);
					state._fsp--;

					}
					break;

				default :
					break loop19;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return clause;
	}
	// $ANTLR end "whereClause"



	// $ANTLR start "relationOrExpression"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:357:1: relationOrExpression[WhereClause.Builder clause] : ( relation[$clause] | customIndexExpression[$clause] );
	public final void relationOrExpression(WhereClause.Builder clause) throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:358:5: ( relation[$clause] | customIndexExpression[$clause] )
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( (LA20_0==IDENT||(LA20_0 >= K_AGGREGATE && LA20_0 <= K_ALL)||LA20_0==K_AS||LA20_0==K_ASCII||(LA20_0 >= K_BIGINT && LA20_0 <= K_BOOLEAN)||(LA20_0 >= K_CALLED && LA20_0 <= K_CLUSTERING)||(LA20_0 >= K_COMPACT && LA20_0 <= K_COUNTER)||(LA20_0 >= K_CUSTOM && LA20_0 <= K_DECIMAL)||(LA20_0 >= K_DISTINCT && LA20_0 <= K_DOUBLE)||(LA20_0 >= K_EXISTS && LA20_0 <= K_FLOAT)||LA20_0==K_FROZEN||(LA20_0 >= K_FUNCTION && LA20_0 <= K_FUNCTIONS)||LA20_0==K_INET||(LA20_0 >= K_INITCOND && LA20_0 <= K_INPUT)||LA20_0==K_INT||(LA20_0 >= K_JSON && LA20_0 <= K_KEYS)||(LA20_0 >= K_KEYSPACES && LA20_0 <= K_LIKE)||(LA20_0 >= K_LIST && LA20_0 <= K_MAP)||LA20_0==K_NOLOGIN||LA20_0==K_NOSUPERUSER||LA20_0==K_OPTIONS||(LA20_0 >= K_PARTITION && LA20_0 <= K_PERMISSIONS)||LA20_0==K_RETURNS||(LA20_0 >= K_ROLE && LA20_0 <= K_ROLES)||(LA20_0 >= K_SFUNC && LA20_0 <= K_TINYINT)||(LA20_0 >= K_TOKEN && LA20_0 <= K_TRIGGER)||(LA20_0 >= K_TTL && LA20_0 <= K_TYPE)||(LA20_0 >= K_USER && LA20_0 <= K_USERS)||(LA20_0 >= K_UUID && LA20_0 <= K_VARINT)||LA20_0==K_WRITETIME||LA20_0==QUOTED_NAME||LA20_0==174) ) {
				alt20=1;
			}
			else if ( (LA20_0==190) ) {
				alt20=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 20, 0, input);
				throw nvae;
			}

			switch (alt20) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:358:7: relation[$clause]
					{
					pushFollow(FOLLOW_relation_in_relationOrExpression1726);
					relation(clause);
					state._fsp--;

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:359:7: customIndexExpression[$clause]
					{
					pushFollow(FOLLOW_customIndexExpression_in_relationOrExpression1735);
					customIndexExpression(clause);
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "relationOrExpression"



	// $ANTLR start "customIndexExpression"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:362:1: customIndexExpression[WhereClause.Builder clause] : 'expr(' idxName[name] ',' t= term ')' ;
	public final void customIndexExpression(WhereClause.Builder clause) throws RecognitionException {
		Term.Raw t =null;

		IndexName name = new IndexName();
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:364:5: ( 'expr(' idxName[name] ',' t= term ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:364:7: 'expr(' idxName[name] ',' t= term ')'
			{
			match(input,190,FOLLOW_190_in_customIndexExpression1763); 
			pushFollow(FOLLOW_idxName_in_customIndexExpression1765);
			idxName(name);
			state._fsp--;

			match(input,177,FOLLOW_177_in_customIndexExpression1768); 
			pushFollow(FOLLOW_term_in_customIndexExpression1772);
			t=term();
			state._fsp--;

			match(input,175,FOLLOW_175_in_customIndexExpression1774); 
			 clause.add(new CustomIndexExpression(name, t));
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "customIndexExpression"



	// $ANTLR start "orderByClause"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:367:1: orderByClause[Map<ColumnIdentifier.Raw, Boolean> orderings] : c= cident ( K_ASC | K_DESC )? ;
	public final void orderByClause(Map<ColumnIdentifier.Raw, Boolean> orderings) throws RecognitionException {
		ColumnIdentifier.Raw c =null;


		        boolean reversed = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:371:5: (c= cident ( K_ASC | K_DESC )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:371:7: c= cident ( K_ASC | K_DESC )?
			{
			pushFollow(FOLLOW_cident_in_orderByClause1804);
			c=cident();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:371:16: ( K_ASC | K_DESC )?
			int alt21=3;
			int LA21_0 = input.LA(1);
			if ( (LA21_0==K_ASC) ) {
				alt21=1;
			}
			else if ( (LA21_0==K_DESC) ) {
				alt21=2;
			}
			switch (alt21) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:371:17: K_ASC
					{
					match(input,K_ASC,FOLLOW_K_ASC_in_orderByClause1807); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:371:25: K_DESC
					{
					match(input,K_DESC,FOLLOW_K_DESC_in_orderByClause1811); 
					 reversed = true; 
					}
					break;

			}

			 orderings.put(c, reversed); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "orderByClause"



	// $ANTLR start "insertStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:380:1: insertStatement returns [ModificationStatement.Parsed expr] : K_INSERT K_INTO cf= columnFamilyName (st1= normalInsertStatement[cf] | K_JSON st2= jsonInsertStatement[cf] ) ;
	public final ModificationStatement.Parsed insertStatement() throws RecognitionException {
		ModificationStatement.Parsed expr = null;


		CFName cf =null;
		UpdateStatement.ParsedInsert st1 =null;
		UpdateStatement.ParsedInsertJson st2 =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:381:5: ( K_INSERT K_INTO cf= columnFamilyName (st1= normalInsertStatement[cf] | K_JSON st2= jsonInsertStatement[cf] ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:381:7: K_INSERT K_INTO cf= columnFamilyName (st1= normalInsertStatement[cf] | K_JSON st2= jsonInsertStatement[cf] )
			{
			match(input,K_INSERT,FOLLOW_K_INSERT_in_insertStatement1840); 
			match(input,K_INTO,FOLLOW_K_INTO_in_insertStatement1842); 
			pushFollow(FOLLOW_columnFamilyName_in_insertStatement1846);
			cf=columnFamilyName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:382:9: (st1= normalInsertStatement[cf] | K_JSON st2= jsonInsertStatement[cf] )
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( (LA22_0==174) ) {
				alt22=1;
			}
			else if ( (LA22_0==K_JSON) ) {
				alt22=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 22, 0, input);
				throw nvae;
			}

			switch (alt22) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:382:11: st1= normalInsertStatement[cf]
					{
					pushFollow(FOLLOW_normalInsertStatement_in_insertStatement1860);
					st1=normalInsertStatement(cf);
					state._fsp--;

					 expr = st1; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:383:11: K_JSON st2= jsonInsertStatement[cf]
					{
					match(input,K_JSON,FOLLOW_K_JSON_in_insertStatement1875); 
					pushFollow(FOLLOW_jsonInsertStatement_in_insertStatement1879);
					st2=jsonInsertStatement(cf);
					state._fsp--;

					 expr = st2; 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "insertStatement"



	// $ANTLR start "normalInsertStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:386:1: normalInsertStatement[CFName cf] returns [UpdateStatement.ParsedInsert expr] : '(' c1= cident ( ',' cn= cident )* ')' K_VALUES '(' v1= term ( ',' vn= term )* ')' ( K_IF K_NOT K_EXISTS )? ( usingClause[attrs] )? ;
	public final UpdateStatement.ParsedInsert normalInsertStatement(CFName cf) throws RecognitionException {
		UpdateStatement.ParsedInsert expr = null;


		ColumnIdentifier.Raw c1 =null;
		ColumnIdentifier.Raw cn =null;
		Term.Raw v1 =null;
		Term.Raw vn =null;


		        Attributes.Raw attrs = new Attributes.Raw();
		        List<ColumnIdentifier.Raw> columnNames  = new ArrayList<ColumnIdentifier.Raw>();
		        List<Term.Raw> values = new ArrayList<Term.Raw>();
		        boolean ifNotExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:393:5: ( '(' c1= cident ( ',' cn= cident )* ')' K_VALUES '(' v1= term ( ',' vn= term )* ')' ( K_IF K_NOT K_EXISTS )? ( usingClause[attrs] )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:393:7: '(' c1= cident ( ',' cn= cident )* ')' K_VALUES '(' v1= term ( ',' vn= term )* ')' ( K_IF K_NOT K_EXISTS )? ( usingClause[attrs] )?
			{
			match(input,174,FOLLOW_174_in_normalInsertStatement1915); 
			pushFollow(FOLLOW_cident_in_normalInsertStatement1919);
			c1=cident();
			state._fsp--;

			 columnNames.add(c1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:393:47: ( ',' cn= cident )*
			loop23:
			while (true) {
				int alt23=2;
				int LA23_0 = input.LA(1);
				if ( (LA23_0==177) ) {
					alt23=1;
				}

				switch (alt23) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:393:49: ',' cn= cident
					{
					match(input,177,FOLLOW_177_in_normalInsertStatement1926); 
					pushFollow(FOLLOW_cident_in_normalInsertStatement1930);
					cn=cident();
					state._fsp--;

					 columnNames.add(cn); 
					}
					break;

				default :
					break loop23;
				}
			}

			match(input,175,FOLLOW_175_in_normalInsertStatement1937); 
			match(input,K_VALUES,FOLLOW_K_VALUES_in_normalInsertStatement1945); 
			match(input,174,FOLLOW_174_in_normalInsertStatement1953); 
			pushFollow(FOLLOW_term_in_normalInsertStatement1957);
			v1=term();
			state._fsp--;

			 values.add(v1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:395:39: ( ',' vn= term )*
			loop24:
			while (true) {
				int alt24=2;
				int LA24_0 = input.LA(1);
				if ( (LA24_0==177) ) {
					alt24=1;
				}

				switch (alt24) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:395:41: ',' vn= term
					{
					match(input,177,FOLLOW_177_in_normalInsertStatement1963); 
					pushFollow(FOLLOW_term_in_normalInsertStatement1967);
					vn=term();
					state._fsp--;

					 values.add(vn); 
					}
					break;

				default :
					break loop24;
				}
			}

			match(input,175,FOLLOW_175_in_normalInsertStatement1974); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:396:7: ( K_IF K_NOT K_EXISTS )?
			int alt25=2;
			int LA25_0 = input.LA(1);
			if ( (LA25_0==K_IF) ) {
				alt25=1;
			}
			switch (alt25) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:396:9: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_normalInsertStatement1984); 
					match(input,K_NOT,FOLLOW_K_NOT_in_normalInsertStatement1986); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_normalInsertStatement1988); 
					 ifNotExists = true; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:397:7: ( usingClause[attrs] )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==K_USING) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:397:9: usingClause[attrs]
					{
					pushFollow(FOLLOW_usingClause_in_normalInsertStatement2003);
					usingClause(attrs);
					state._fsp--;

					}
					break;

			}


			          expr = new UpdateStatement.ParsedInsert(cf, attrs, columnNames, values, ifNotExists);
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "normalInsertStatement"



	// $ANTLR start "jsonInsertStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:403:1: jsonInsertStatement[CFName cf] returns [UpdateStatement.ParsedInsertJson expr] : val= jsonValue ( K_IF K_NOT K_EXISTS )? ( usingClause[attrs] )? ;
	public final UpdateStatement.ParsedInsertJson jsonInsertStatement(CFName cf) throws RecognitionException {
		UpdateStatement.ParsedInsertJson expr = null;


		Json.Raw val =null;


		        Attributes.Raw attrs = new Attributes.Raw();
		        boolean ifNotExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:408:5: (val= jsonValue ( K_IF K_NOT K_EXISTS )? ( usingClause[attrs] )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:408:7: val= jsonValue ( K_IF K_NOT K_EXISTS )? ( usingClause[attrs] )?
			{
			pushFollow(FOLLOW_jsonValue_in_jsonInsertStatement2049);
			val=jsonValue();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:409:7: ( K_IF K_NOT K_EXISTS )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==K_IF) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:409:9: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_jsonInsertStatement2059); 
					match(input,K_NOT,FOLLOW_K_NOT_in_jsonInsertStatement2061); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_jsonInsertStatement2063); 
					 ifNotExists = true; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:410:7: ( usingClause[attrs] )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==K_USING) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:410:9: usingClause[attrs]
					{
					pushFollow(FOLLOW_usingClause_in_jsonInsertStatement2078);
					usingClause(attrs);
					state._fsp--;

					}
					break;

			}


			          expr = new UpdateStatement.ParsedInsertJson(cf, attrs, val, ifNotExists);
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "jsonInsertStatement"



	// $ANTLR start "jsonValue"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:416:1: jsonValue returns [Json.Raw value] : (|s= STRING_LITERAL | ':' id= noncol_ident | QMARK );
	public final Json.Raw jsonValue() throws RecognitionException {
		Json.Raw value = null;


		Token s=null;
		ColumnIdentifier id =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:417:5: (|s= STRING_LITERAL | ':' id= noncol_ident | QMARK )
			int alt29=4;
			switch ( input.LA(1) ) {
			case EOF:
			case K_APPLY:
			case K_DELETE:
			case K_IF:
			case K_INSERT:
			case K_UPDATE:
			case K_USING:
			case 181:
				{
				alt29=1;
				}
				break;
			case STRING_LITERAL:
				{
				alt29=2;
				}
				break;
			case 180:
				{
				alt29=3;
				}
				break;
			case QMARK:
				{
				alt29=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 29, 0, input);
				throw nvae;
			}
			switch (alt29) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:418:5: 
					{
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:418:7: s= STRING_LITERAL
					{
					s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_jsonValue2119); 
					 value = new Json.Literal((s!=null?s.getText():null)); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:419:7: ':' id= noncol_ident
					{
					match(input,180,FOLLOW_180_in_jsonValue2129); 
					pushFollow(FOLLOW_noncol_ident_in_jsonValue2133);
					id=noncol_ident();
					state._fsp--;

					 value = newJsonBindVariables(id); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:420:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_jsonValue2147); 
					 value = newJsonBindVariables(null); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "jsonValue"



	// $ANTLR start "usingClause"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:423:1: usingClause[Attributes.Raw attrs] : K_USING usingClauseObjective[attrs] ( K_AND usingClauseObjective[attrs] )* ;
	public final void usingClause(Attributes.Raw attrs) throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:424:5: ( K_USING usingClauseObjective[attrs] ( K_AND usingClauseObjective[attrs] )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:424:7: K_USING usingClauseObjective[attrs] ( K_AND usingClauseObjective[attrs] )*
			{
			match(input,K_USING,FOLLOW_K_USING_in_usingClause2178); 
			pushFollow(FOLLOW_usingClauseObjective_in_usingClause2180);
			usingClauseObjective(attrs);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:424:43: ( K_AND usingClauseObjective[attrs] )*
			loop30:
			while (true) {
				int alt30=2;
				int LA30_0 = input.LA(1);
				if ( (LA30_0==K_AND) ) {
					alt30=1;
				}

				switch (alt30) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:424:45: K_AND usingClauseObjective[attrs]
					{
					match(input,K_AND,FOLLOW_K_AND_in_usingClause2185); 
					pushFollow(FOLLOW_usingClauseObjective_in_usingClause2187);
					usingClauseObjective(attrs);
					state._fsp--;

					}
					break;

				default :
					break loop30;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "usingClause"



	// $ANTLR start "usingClauseObjective"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:427:1: usingClauseObjective[Attributes.Raw attrs] : ( K_TIMESTAMP ts= intValue | K_TTL t= intValue );
	public final void usingClauseObjective(Attributes.Raw attrs) throws RecognitionException {
		Term.Raw ts =null;
		Term.Raw t =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:428:5: ( K_TIMESTAMP ts= intValue | K_TTL t= intValue )
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==K_TIMESTAMP) ) {
				alt31=1;
			}
			else if ( (LA31_0==K_TTL) ) {
				alt31=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 31, 0, input);
				throw nvae;
			}

			switch (alt31) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:428:7: K_TIMESTAMP ts= intValue
					{
					match(input,K_TIMESTAMP,FOLLOW_K_TIMESTAMP_in_usingClauseObjective2209); 
					pushFollow(FOLLOW_intValue_in_usingClauseObjective2213);
					ts=intValue();
					state._fsp--;

					 attrs.timestamp = ts; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:429:7: K_TTL t= intValue
					{
					match(input,K_TTL,FOLLOW_K_TTL_in_usingClauseObjective2223); 
					pushFollow(FOLLOW_intValue_in_usingClauseObjective2227);
					t=intValue();
					state._fsp--;

					 attrs.timeToLive = t; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "usingClauseObjective"



	// $ANTLR start "updateStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:439:1: updateStatement returns [UpdateStatement.ParsedUpdate expr] : K_UPDATE cf= columnFamilyName ( usingClause[attrs] )? K_SET columnOperation[operations] ( ',' columnOperation[operations] )* K_WHERE wclause= whereClause ( K_IF ( K_EXISTS |conditions= updateConditions ) )? ;
	public final UpdateStatement.ParsedUpdate updateStatement() throws RecognitionException {
		UpdateStatement.ParsedUpdate expr = null;


		CFName cf =null;
		WhereClause.Builder wclause =null;
		List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> conditions =null;


		        Attributes.Raw attrs = new Attributes.Raw();
		        List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations = new ArrayList<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>>();
		        boolean ifExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:445:5: ( K_UPDATE cf= columnFamilyName ( usingClause[attrs] )? K_SET columnOperation[operations] ( ',' columnOperation[operations] )* K_WHERE wclause= whereClause ( K_IF ( K_EXISTS |conditions= updateConditions ) )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:445:7: K_UPDATE cf= columnFamilyName ( usingClause[attrs] )? K_SET columnOperation[operations] ( ',' columnOperation[operations] )* K_WHERE wclause= whereClause ( K_IF ( K_EXISTS |conditions= updateConditions ) )?
			{
			match(input,K_UPDATE,FOLLOW_K_UPDATE_in_updateStatement2261); 
			pushFollow(FOLLOW_columnFamilyName_in_updateStatement2265);
			cf=columnFamilyName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:446:7: ( usingClause[attrs] )?
			int alt32=2;
			int LA32_0 = input.LA(1);
			if ( (LA32_0==K_USING) ) {
				alt32=1;
			}
			switch (alt32) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:446:9: usingClause[attrs]
					{
					pushFollow(FOLLOW_usingClause_in_updateStatement2275);
					usingClause(attrs);
					state._fsp--;

					}
					break;

			}

			match(input,K_SET,FOLLOW_K_SET_in_updateStatement2287); 
			pushFollow(FOLLOW_columnOperation_in_updateStatement2289);
			columnOperation(operations);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:447:41: ( ',' columnOperation[operations] )*
			loop33:
			while (true) {
				int alt33=2;
				int LA33_0 = input.LA(1);
				if ( (LA33_0==177) ) {
					alt33=1;
				}

				switch (alt33) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:447:42: ',' columnOperation[operations]
					{
					match(input,177,FOLLOW_177_in_updateStatement2293); 
					pushFollow(FOLLOW_columnOperation_in_updateStatement2295);
					columnOperation(operations);
					state._fsp--;

					}
					break;

				default :
					break loop33;
				}
			}

			match(input,K_WHERE,FOLLOW_K_WHERE_in_updateStatement2306); 
			pushFollow(FOLLOW_whereClause_in_updateStatement2310);
			wclause=whereClause();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:449:7: ( K_IF ( K_EXISTS |conditions= updateConditions ) )?
			int alt35=2;
			int LA35_0 = input.LA(1);
			if ( (LA35_0==K_IF) ) {
				alt35=1;
			}
			switch (alt35) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:449:9: K_IF ( K_EXISTS |conditions= updateConditions )
					{
					match(input,K_IF,FOLLOW_K_IF_in_updateStatement2320); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:449:14: ( K_EXISTS |conditions= updateConditions )
					int alt34=2;
					int LA34_0 = input.LA(1);
					if ( (LA34_0==K_EXISTS) ) {
						int LA34_1 = input.LA(2);
						if ( (LA34_1==EOF||LA34_1==K_APPLY||LA34_1==K_DELETE||LA34_1==K_INSERT||LA34_1==K_UPDATE||LA34_1==181) ) {
							alt34=1;
						}
						else if ( (LA34_1==K_IN||LA34_1==173||(LA34_1 >= 182 && LA34_1 <= 187)) ) {
							alt34=2;
						}

						else {
							int nvaeMark = input.mark();
							try {
								input.consume();
								NoViableAltException nvae =
									new NoViableAltException("", 34, 1, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

					}
					else if ( (LA34_0==IDENT||(LA34_0 >= K_AGGREGATE && LA34_0 <= K_ALL)||LA34_0==K_AS||LA34_0==K_ASCII||(LA34_0 >= K_BIGINT && LA34_0 <= K_BOOLEAN)||(LA34_0 >= K_CALLED && LA34_0 <= K_CLUSTERING)||(LA34_0 >= K_COMPACT && LA34_0 <= K_COUNTER)||(LA34_0 >= K_CUSTOM && LA34_0 <= K_DECIMAL)||(LA34_0 >= K_DISTINCT && LA34_0 <= K_DOUBLE)||(LA34_0 >= K_FILTERING && LA34_0 <= K_FLOAT)||LA34_0==K_FROZEN||(LA34_0 >= K_FUNCTION && LA34_0 <= K_FUNCTIONS)||LA34_0==K_INET||(LA34_0 >= K_INITCOND && LA34_0 <= K_INPUT)||LA34_0==K_INT||(LA34_0 >= K_JSON && LA34_0 <= K_KEYS)||(LA34_0 >= K_KEYSPACES && LA34_0 <= K_LIKE)||(LA34_0 >= K_LIST && LA34_0 <= K_MAP)||LA34_0==K_NOLOGIN||LA34_0==K_NOSUPERUSER||LA34_0==K_OPTIONS||(LA34_0 >= K_PARTITION && LA34_0 <= K_PERMISSIONS)||LA34_0==K_RETURNS||(LA34_0 >= K_ROLE && LA34_0 <= K_ROLES)||(LA34_0 >= K_SFUNC && LA34_0 <= K_TINYINT)||LA34_0==K_TRIGGER||(LA34_0 >= K_TTL && LA34_0 <= K_TYPE)||(LA34_0 >= K_USER && LA34_0 <= K_USERS)||(LA34_0 >= K_UUID && LA34_0 <= K_VARINT)||LA34_0==K_WRITETIME||LA34_0==QUOTED_NAME) ) {
						alt34=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 34, 0, input);
						throw nvae;
					}

					switch (alt34) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:449:16: K_EXISTS
							{
							match(input,K_EXISTS,FOLLOW_K_EXISTS_in_updateStatement2324); 
							 ifExists = true; 
							}
							break;
						case 2 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:449:48: conditions= updateConditions
							{
							pushFollow(FOLLOW_updateConditions_in_updateStatement2332);
							conditions=updateConditions();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}


			          return new UpdateStatement.ParsedUpdate(cf,
			                                                  attrs,
			                                                  operations,
			                                                  wclause.build(),
			                                                  conditions == null ? Collections.<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>>emptyList() : conditions,
			                                                  ifExists);
			     
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "updateStatement"



	// $ANTLR start "updateConditions"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:460:1: updateConditions returns [List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> conditions] : columnCondition[conditions] ( K_AND columnCondition[conditions] )* ;
	public final List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> updateConditions() throws RecognitionException {
		List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> conditions = null;


		 conditions = new ArrayList<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:462:5: ( columnCondition[conditions] ( K_AND columnCondition[conditions] )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:462:7: columnCondition[conditions] ( K_AND columnCondition[conditions] )*
			{
			pushFollow(FOLLOW_columnCondition_in_updateConditions2374);
			columnCondition(conditions);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:462:35: ( K_AND columnCondition[conditions] )*
			loop36:
			while (true) {
				int alt36=2;
				int LA36_0 = input.LA(1);
				if ( (LA36_0==K_AND) ) {
					alt36=1;
				}

				switch (alt36) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:462:37: K_AND columnCondition[conditions]
					{
					match(input,K_AND,FOLLOW_K_AND_in_updateConditions2379); 
					pushFollow(FOLLOW_columnCondition_in_updateConditions2381);
					columnCondition(conditions);
					state._fsp--;

					}
					break;

				default :
					break loop36;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return conditions;
	}
	// $ANTLR end "updateConditions"



	// $ANTLR start "deleteStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:473:1: deleteStatement returns [DeleteStatement.Parsed expr] : K_DELETE (dels= deleteSelection )? K_FROM cf= columnFamilyName ( usingClauseDelete[attrs] )? K_WHERE wclause= whereClause ( K_IF ( K_EXISTS |conditions= updateConditions ) )? ;
	public final DeleteStatement.Parsed deleteStatement() throws RecognitionException {
		DeleteStatement.Parsed expr = null;


		List<Operation.RawDeletion> dels =null;
		CFName cf =null;
		WhereClause.Builder wclause =null;
		List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> conditions =null;


		        Attributes.Raw attrs = new Attributes.Raw();
		        List<Operation.RawDeletion> columnDeletions = Collections.emptyList();
		        boolean ifExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:479:5: ( K_DELETE (dels= deleteSelection )? K_FROM cf= columnFamilyName ( usingClauseDelete[attrs] )? K_WHERE wclause= whereClause ( K_IF ( K_EXISTS |conditions= updateConditions ) )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:479:7: K_DELETE (dels= deleteSelection )? K_FROM cf= columnFamilyName ( usingClauseDelete[attrs] )? K_WHERE wclause= whereClause ( K_IF ( K_EXISTS |conditions= updateConditions ) )?
			{
			match(input,K_DELETE,FOLLOW_K_DELETE_in_deleteStatement2418); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:479:16: (dels= deleteSelection )?
			int alt37=2;
			int LA37_0 = input.LA(1);
			if ( (LA37_0==IDENT||(LA37_0 >= K_AGGREGATE && LA37_0 <= K_ALL)||LA37_0==K_AS||LA37_0==K_ASCII||(LA37_0 >= K_BIGINT && LA37_0 <= K_BOOLEAN)||(LA37_0 >= K_CALLED && LA37_0 <= K_CLUSTERING)||(LA37_0 >= K_COMPACT && LA37_0 <= K_COUNTER)||(LA37_0 >= K_CUSTOM && LA37_0 <= K_DECIMAL)||(LA37_0 >= K_DISTINCT && LA37_0 <= K_DOUBLE)||(LA37_0 >= K_EXISTS && LA37_0 <= K_FLOAT)||LA37_0==K_FROZEN||(LA37_0 >= K_FUNCTION && LA37_0 <= K_FUNCTIONS)||LA37_0==K_INET||(LA37_0 >= K_INITCOND && LA37_0 <= K_INPUT)||LA37_0==K_INT||(LA37_0 >= K_JSON && LA37_0 <= K_KEYS)||(LA37_0 >= K_KEYSPACES && LA37_0 <= K_LIKE)||(LA37_0 >= K_LIST && LA37_0 <= K_MAP)||LA37_0==K_NOLOGIN||LA37_0==K_NOSUPERUSER||LA37_0==K_OPTIONS||(LA37_0 >= K_PARTITION && LA37_0 <= K_PERMISSIONS)||LA37_0==K_RETURNS||(LA37_0 >= K_ROLE && LA37_0 <= K_ROLES)||(LA37_0 >= K_SFUNC && LA37_0 <= K_TINYINT)||LA37_0==K_TRIGGER||(LA37_0 >= K_TTL && LA37_0 <= K_TYPE)||(LA37_0 >= K_USER && LA37_0 <= K_USERS)||(LA37_0 >= K_UUID && LA37_0 <= K_VARINT)||LA37_0==K_WRITETIME||LA37_0==QUOTED_NAME) ) {
				alt37=1;
			}
			switch (alt37) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:479:18: dels= deleteSelection
					{
					pushFollow(FOLLOW_deleteSelection_in_deleteStatement2424);
					dels=deleteSelection();
					state._fsp--;

					 columnDeletions = dels; 
					}
					break;

			}

			match(input,K_FROM,FOLLOW_K_FROM_in_deleteStatement2437); 
			pushFollow(FOLLOW_columnFamilyName_in_deleteStatement2441);
			cf=columnFamilyName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:481:7: ( usingClauseDelete[attrs] )?
			int alt38=2;
			int LA38_0 = input.LA(1);
			if ( (LA38_0==K_USING) ) {
				alt38=1;
			}
			switch (alt38) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:481:9: usingClauseDelete[attrs]
					{
					pushFollow(FOLLOW_usingClauseDelete_in_deleteStatement2451);
					usingClauseDelete(attrs);
					state._fsp--;

					}
					break;

			}

			match(input,K_WHERE,FOLLOW_K_WHERE_in_deleteStatement2463); 
			pushFollow(FOLLOW_whereClause_in_deleteStatement2467);
			wclause=whereClause();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:483:7: ( K_IF ( K_EXISTS |conditions= updateConditions ) )?
			int alt40=2;
			int LA40_0 = input.LA(1);
			if ( (LA40_0==K_IF) ) {
				alt40=1;
			}
			switch (alt40) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:483:9: K_IF ( K_EXISTS |conditions= updateConditions )
					{
					match(input,K_IF,FOLLOW_K_IF_in_deleteStatement2477); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:483:14: ( K_EXISTS |conditions= updateConditions )
					int alt39=2;
					int LA39_0 = input.LA(1);
					if ( (LA39_0==K_EXISTS) ) {
						int LA39_1 = input.LA(2);
						if ( (LA39_1==EOF||LA39_1==K_APPLY||LA39_1==K_DELETE||LA39_1==K_INSERT||LA39_1==K_UPDATE||LA39_1==181) ) {
							alt39=1;
						}
						else if ( (LA39_1==K_IN||LA39_1==173||(LA39_1 >= 182 && LA39_1 <= 187)) ) {
							alt39=2;
						}

						else {
							int nvaeMark = input.mark();
							try {
								input.consume();
								NoViableAltException nvae =
									new NoViableAltException("", 39, 1, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

					}
					else if ( (LA39_0==IDENT||(LA39_0 >= K_AGGREGATE && LA39_0 <= K_ALL)||LA39_0==K_AS||LA39_0==K_ASCII||(LA39_0 >= K_BIGINT && LA39_0 <= K_BOOLEAN)||(LA39_0 >= K_CALLED && LA39_0 <= K_CLUSTERING)||(LA39_0 >= K_COMPACT && LA39_0 <= K_COUNTER)||(LA39_0 >= K_CUSTOM && LA39_0 <= K_DECIMAL)||(LA39_0 >= K_DISTINCT && LA39_0 <= K_DOUBLE)||(LA39_0 >= K_FILTERING && LA39_0 <= K_FLOAT)||LA39_0==K_FROZEN||(LA39_0 >= K_FUNCTION && LA39_0 <= K_FUNCTIONS)||LA39_0==K_INET||(LA39_0 >= K_INITCOND && LA39_0 <= K_INPUT)||LA39_0==K_INT||(LA39_0 >= K_JSON && LA39_0 <= K_KEYS)||(LA39_0 >= K_KEYSPACES && LA39_0 <= K_LIKE)||(LA39_0 >= K_LIST && LA39_0 <= K_MAP)||LA39_0==K_NOLOGIN||LA39_0==K_NOSUPERUSER||LA39_0==K_OPTIONS||(LA39_0 >= K_PARTITION && LA39_0 <= K_PERMISSIONS)||LA39_0==K_RETURNS||(LA39_0 >= K_ROLE && LA39_0 <= K_ROLES)||(LA39_0 >= K_SFUNC && LA39_0 <= K_TINYINT)||LA39_0==K_TRIGGER||(LA39_0 >= K_TTL && LA39_0 <= K_TYPE)||(LA39_0 >= K_USER && LA39_0 <= K_USERS)||(LA39_0 >= K_UUID && LA39_0 <= K_VARINT)||LA39_0==K_WRITETIME||LA39_0==QUOTED_NAME) ) {
						alt39=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 39, 0, input);
						throw nvae;
					}

					switch (alt39) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:483:16: K_EXISTS
							{
							match(input,K_EXISTS,FOLLOW_K_EXISTS_in_deleteStatement2481); 
							 ifExists = true; 
							}
							break;
						case 2 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:483:48: conditions= updateConditions
							{
							pushFollow(FOLLOW_updateConditions_in_deleteStatement2489);
							conditions=updateConditions();
							state._fsp--;

							}
							break;

					}

					}
					break;

			}


			          return new DeleteStatement.Parsed(cf,
			                                            attrs,
			                                            columnDeletions,
			                                            wclause.build(),
			                                            conditions == null ? Collections.<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>>emptyList() : conditions,
			                                            ifExists);
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "deleteStatement"



	// $ANTLR start "deleteSelection"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:494:1: deleteSelection returns [List<Operation.RawDeletion> operations] :t1= deleteOp ( ',' tN= deleteOp )* ;
	public final List<Operation.RawDeletion> deleteSelection() throws RecognitionException {
		List<Operation.RawDeletion> operations = null;


		Operation.RawDeletion t1 =null;
		Operation.RawDeletion tN =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:495:5: (t1= deleteOp ( ',' tN= deleteOp )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:495:7: t1= deleteOp ( ',' tN= deleteOp )*
			{
			 operations = new ArrayList<Operation.RawDeletion>(); 
			pushFollow(FOLLOW_deleteOp_in_deleteSelection2536);
			t1=deleteOp();
			state._fsp--;

			 operations.add(t1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:497:11: ( ',' tN= deleteOp )*
			loop41:
			while (true) {
				int alt41=2;
				int LA41_0 = input.LA(1);
				if ( (LA41_0==177) ) {
					alt41=1;
				}

				switch (alt41) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:497:12: ',' tN= deleteOp
					{
					match(input,177,FOLLOW_177_in_deleteSelection2551); 
					pushFollow(FOLLOW_deleteOp_in_deleteSelection2555);
					tN=deleteOp();
					state._fsp--;

					 operations.add(tN); 
					}
					break;

				default :
					break loop41;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return operations;
	}
	// $ANTLR end "deleteSelection"



	// $ANTLR start "deleteOp"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:500:1: deleteOp returns [Operation.RawDeletion op] : (c= cident |c= cident '[' t= term ']' );
	public final Operation.RawDeletion deleteOp() throws RecognitionException {
		Operation.RawDeletion op = null;


		ColumnIdentifier.Raw c =null;
		Term.Raw t =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:501:5: (c= cident |c= cident '[' t= term ']' )
			int alt42=2;
			alt42 = dfa42.predict(input);
			switch (alt42) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:501:7: c= cident
					{
					pushFollow(FOLLOW_cident_in_deleteOp2582);
					c=cident();
					state._fsp--;

					 op = new Operation.ColumnDeletion(c); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:502:7: c= cident '[' t= term ']'
					{
					pushFollow(FOLLOW_cident_in_deleteOp2609);
					c=cident();
					state._fsp--;

					match(input,187,FOLLOW_187_in_deleteOp2611); 
					pushFollow(FOLLOW_term_in_deleteOp2615);
					t=term();
					state._fsp--;

					match(input,189,FOLLOW_189_in_deleteOp2617); 
					 op = new Operation.ElementDeletion(c, t); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return op;
	}
	// $ANTLR end "deleteOp"



	// $ANTLR start "usingClauseDelete"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:505:1: usingClauseDelete[Attributes.Raw attrs] : K_USING K_TIMESTAMP ts= intValue ;
	public final void usingClauseDelete(Attributes.Raw attrs) throws RecognitionException {
		Term.Raw ts =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:506:5: ( K_USING K_TIMESTAMP ts= intValue )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:506:7: K_USING K_TIMESTAMP ts= intValue
			{
			match(input,K_USING,FOLLOW_K_USING_in_usingClauseDelete2637); 
			match(input,K_TIMESTAMP,FOLLOW_K_TIMESTAMP_in_usingClauseDelete2639); 
			pushFollow(FOLLOW_intValue_in_usingClauseDelete2643);
			ts=intValue();
			state._fsp--;

			 attrs.timestamp = ts; 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "usingClauseDelete"



	// $ANTLR start "batchStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:533:1: batchStatement returns [BatchStatement.Parsed expr] : K_BEGIN ( K_UNLOGGED | K_COUNTER )? K_BATCH ( usingClause[attrs] )? (s= batchStatementObjective ( ';' )? )* K_APPLY K_BATCH ;
	public final BatchStatement.Parsed batchStatement() throws RecognitionException {
		BatchStatement.Parsed expr = null;


		ModificationStatement.Parsed s =null;


		        BatchStatement.Type type = BatchStatement.Type.LOGGED;
		        List<ModificationStatement.Parsed> statements = new ArrayList<ModificationStatement.Parsed>();
		        Attributes.Raw attrs = new Attributes.Raw();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:539:5: ( K_BEGIN ( K_UNLOGGED | K_COUNTER )? K_BATCH ( usingClause[attrs] )? (s= batchStatementObjective ( ';' )? )* K_APPLY K_BATCH )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:539:7: K_BEGIN ( K_UNLOGGED | K_COUNTER )? K_BATCH ( usingClause[attrs] )? (s= batchStatementObjective ( ';' )? )* K_APPLY K_BATCH
			{
			match(input,K_BEGIN,FOLLOW_K_BEGIN_in_batchStatement2677); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:540:7: ( K_UNLOGGED | K_COUNTER )?
			int alt43=3;
			int LA43_0 = input.LA(1);
			if ( (LA43_0==K_UNLOGGED) ) {
				alt43=1;
			}
			else if ( (LA43_0==K_COUNTER) ) {
				alt43=2;
			}
			switch (alt43) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:540:9: K_UNLOGGED
					{
					match(input,K_UNLOGGED,FOLLOW_K_UNLOGGED_in_batchStatement2687); 
					 type = BatchStatement.Type.UNLOGGED; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:540:63: K_COUNTER
					{
					match(input,K_COUNTER,FOLLOW_K_COUNTER_in_batchStatement2693); 
					 type = BatchStatement.Type.COUNTER; 
					}
					break;

			}

			match(input,K_BATCH,FOLLOW_K_BATCH_in_batchStatement2706); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:541:15: ( usingClause[attrs] )?
			int alt44=2;
			int LA44_0 = input.LA(1);
			if ( (LA44_0==K_USING) ) {
				alt44=1;
			}
			switch (alt44) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:541:17: usingClause[attrs]
					{
					pushFollow(FOLLOW_usingClause_in_batchStatement2710);
					usingClause(attrs);
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:542:11: (s= batchStatementObjective ( ';' )? )*
			loop46:
			while (true) {
				int alt46=2;
				int LA46_0 = input.LA(1);
				if ( (LA46_0==K_DELETE||LA46_0==K_INSERT||LA46_0==K_UPDATE) ) {
					alt46=1;
				}

				switch (alt46) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:542:13: s= batchStatementObjective ( ';' )?
					{
					pushFollow(FOLLOW_batchStatementObjective_in_batchStatement2730);
					s=batchStatementObjective();
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:542:39: ( ';' )?
					int alt45=2;
					int LA45_0 = input.LA(1);
					if ( (LA45_0==181) ) {
						alt45=1;
					}
					switch (alt45) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:542:39: ';'
							{
							match(input,181,FOLLOW_181_in_batchStatement2732); 
							}
							break;

					}

					 statements.add(s); 
					}
					break;

				default :
					break loop46;
				}
			}

			match(input,K_APPLY,FOLLOW_K_APPLY_in_batchStatement2746); 
			match(input,K_BATCH,FOLLOW_K_BATCH_in_batchStatement2748); 

			          return new BatchStatement.Parsed(type, attrs, statements);
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "batchStatement"



	// $ANTLR start "batchStatementObjective"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:549:1: batchStatementObjective returns [ModificationStatement.Parsed statement] : (i= insertStatement |u= updateStatement |d= deleteStatement );
	public final ModificationStatement.Parsed batchStatementObjective() throws RecognitionException {
		ModificationStatement.Parsed statement = null;


		ModificationStatement.Parsed i =null;
		UpdateStatement.ParsedUpdate u =null;
		DeleteStatement.Parsed d =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:550:5: (i= insertStatement |u= updateStatement |d= deleteStatement )
			int alt47=3;
			switch ( input.LA(1) ) {
			case K_INSERT:
				{
				alt47=1;
				}
				break;
			case K_UPDATE:
				{
				alt47=2;
				}
				break;
			case K_DELETE:
				{
				alt47=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 47, 0, input);
				throw nvae;
			}
			switch (alt47) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:550:7: i= insertStatement
					{
					pushFollow(FOLLOW_insertStatement_in_batchStatementObjective2779);
					i=insertStatement();
					state._fsp--;

					 statement = i; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:551:7: u= updateStatement
					{
					pushFollow(FOLLOW_updateStatement_in_batchStatementObjective2792);
					u=updateStatement();
					state._fsp--;

					 statement = u; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:552:7: d= deleteStatement
					{
					pushFollow(FOLLOW_deleteStatement_in_batchStatementObjective2805);
					d=deleteStatement();
					state._fsp--;

					 statement = d; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return statement;
	}
	// $ANTLR end "batchStatementObjective"



	// $ANTLR start "createAggregateStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:555:1: createAggregateStatement returns [CreateAggregateStatement expr] : K_CREATE ( K_OR K_REPLACE )? K_AGGREGATE ( K_IF K_NOT K_EXISTS )? fn= functionName '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' K_SFUNC sfunc= allowedFunctionName K_STYPE stype= comparatorType ( K_FINALFUNC ffunc= allowedFunctionName )? ( K_INITCOND ival= term )? ;
	public final CreateAggregateStatement createAggregateStatement() throws RecognitionException {
		CreateAggregateStatement expr = null;


		FunctionName fn =null;
		CQL3Type.Raw v =null;
		String sfunc =null;
		CQL3Type.Raw stype =null;
		String ffunc =null;
		Term.Raw ival =null;


		        boolean orReplace = false;
		        boolean ifNotExists = false;

		        List<CQL3Type.Raw> argsTypes = new ArrayList<>();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:562:5: ( K_CREATE ( K_OR K_REPLACE )? K_AGGREGATE ( K_IF K_NOT K_EXISTS )? fn= functionName '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' K_SFUNC sfunc= allowedFunctionName K_STYPE stype= comparatorType ( K_FINALFUNC ffunc= allowedFunctionName )? ( K_INITCOND ival= term )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:562:7: K_CREATE ( K_OR K_REPLACE )? K_AGGREGATE ( K_IF K_NOT K_EXISTS )? fn= functionName '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' K_SFUNC sfunc= allowedFunctionName K_STYPE stype= comparatorType ( K_FINALFUNC ffunc= allowedFunctionName )? ( K_INITCOND ival= term )?
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createAggregateStatement2838); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:562:16: ( K_OR K_REPLACE )?
			int alt48=2;
			int LA48_0 = input.LA(1);
			if ( (LA48_0==K_OR) ) {
				alt48=1;
			}
			switch (alt48) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:562:17: K_OR K_REPLACE
					{
					match(input,K_OR,FOLLOW_K_OR_in_createAggregateStatement2841); 
					match(input,K_REPLACE,FOLLOW_K_REPLACE_in_createAggregateStatement2843); 
					 orReplace = true; 
					}
					break;

			}

			match(input,K_AGGREGATE,FOLLOW_K_AGGREGATE_in_createAggregateStatement2855); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:564:7: ( K_IF K_NOT K_EXISTS )?
			int alt49=2;
			int LA49_0 = input.LA(1);
			if ( (LA49_0==K_IF) ) {
				alt49=1;
			}
			switch (alt49) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:564:8: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createAggregateStatement2864); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createAggregateStatement2866); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createAggregateStatement2868); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_functionName_in_createAggregateStatement2882);
			fn=functionName();
			state._fsp--;

			match(input,174,FOLLOW_174_in_createAggregateStatement2890); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:567:9: (v= comparatorType ( ',' v= comparatorType )* )?
			int alt51=2;
			int LA51_0 = input.LA(1);
			if ( (LA51_0==IDENT||(LA51_0 >= K_AGGREGATE && LA51_0 <= K_ALL)||LA51_0==K_AS||LA51_0==K_ASCII||(LA51_0 >= K_BIGINT && LA51_0 <= K_BOOLEAN)||(LA51_0 >= K_CALLED && LA51_0 <= K_CLUSTERING)||(LA51_0 >= K_COMPACT && LA51_0 <= K_COUNTER)||(LA51_0 >= K_CUSTOM && LA51_0 <= K_DECIMAL)||(LA51_0 >= K_DISTINCT && LA51_0 <= K_DOUBLE)||(LA51_0 >= K_EXISTS && LA51_0 <= K_FLOAT)||LA51_0==K_FROZEN||(LA51_0 >= K_FUNCTION && LA51_0 <= K_FUNCTIONS)||LA51_0==K_INET||(LA51_0 >= K_INITCOND && LA51_0 <= K_INPUT)||LA51_0==K_INT||(LA51_0 >= K_JSON && LA51_0 <= K_KEYS)||(LA51_0 >= K_KEYSPACES && LA51_0 <= K_LIKE)||(LA51_0 >= K_LIST && LA51_0 <= K_MAP)||LA51_0==K_NOLOGIN||LA51_0==K_NOSUPERUSER||LA51_0==K_OPTIONS||(LA51_0 >= K_PARTITION && LA51_0 <= K_PERMISSIONS)||LA51_0==K_RETURNS||(LA51_0 >= K_ROLE && LA51_0 <= K_ROLES)||(LA51_0 >= K_SET && LA51_0 <= K_TINYINT)||LA51_0==K_TRIGGER||(LA51_0 >= K_TTL && LA51_0 <= K_TYPE)||(LA51_0 >= K_USER && LA51_0 <= K_USERS)||(LA51_0 >= K_UUID && LA51_0 <= K_VARINT)||LA51_0==K_WRITETIME||LA51_0==QUOTED_NAME||LA51_0==STRING_LITERAL) ) {
				alt51=1;
			}
			switch (alt51) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:568:11: v= comparatorType ( ',' v= comparatorType )*
					{
					pushFollow(FOLLOW_comparatorType_in_createAggregateStatement2914);
					v=comparatorType();
					state._fsp--;

					 argsTypes.add(v); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:569:11: ( ',' v= comparatorType )*
					loop50:
					while (true) {
						int alt50=2;
						int LA50_0 = input.LA(1);
						if ( (LA50_0==177) ) {
							alt50=1;
						}

						switch (alt50) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:569:13: ',' v= comparatorType
							{
							match(input,177,FOLLOW_177_in_createAggregateStatement2930); 
							pushFollow(FOLLOW_comparatorType_in_createAggregateStatement2934);
							v=comparatorType();
							state._fsp--;

							 argsTypes.add(v); 
							}
							break;

						default :
							break loop50;
						}
					}

					}
					break;

			}

			match(input,175,FOLLOW_175_in_createAggregateStatement2958); 
			match(input,K_SFUNC,FOLLOW_K_SFUNC_in_createAggregateStatement2966); 
			pushFollow(FOLLOW_allowedFunctionName_in_createAggregateStatement2972);
			sfunc=allowedFunctionName();
			state._fsp--;

			match(input,K_STYPE,FOLLOW_K_STYPE_in_createAggregateStatement2980); 
			pushFollow(FOLLOW_comparatorType_in_createAggregateStatement2986);
			stype=comparatorType();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:574:7: ( K_FINALFUNC ffunc= allowedFunctionName )?
			int alt52=2;
			int LA52_0 = input.LA(1);
			if ( (LA52_0==K_FINALFUNC) ) {
				alt52=1;
			}
			switch (alt52) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:575:9: K_FINALFUNC ffunc= allowedFunctionName
					{
					match(input,K_FINALFUNC,FOLLOW_K_FINALFUNC_in_createAggregateStatement3004); 
					pushFollow(FOLLOW_allowedFunctionName_in_createAggregateStatement3010);
					ffunc=allowedFunctionName();
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:577:7: ( K_INITCOND ival= term )?
			int alt53=2;
			int LA53_0 = input.LA(1);
			if ( (LA53_0==K_INITCOND) ) {
				alt53=1;
			}
			switch (alt53) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:578:9: K_INITCOND ival= term
					{
					match(input,K_INITCOND,FOLLOW_K_INITCOND_in_createAggregateStatement3037); 
					pushFollow(FOLLOW_term_in_createAggregateStatement3043);
					ival=term();
					state._fsp--;

					}
					break;

			}

			 expr = new CreateAggregateStatement(fn, argsTypes, sfunc, stype, ffunc, ival, orReplace, ifNotExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createAggregateStatement"



	// $ANTLR start "dropAggregateStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:583:1: dropAggregateStatement returns [DropAggregateStatement expr] : K_DROP K_AGGREGATE ( K_IF K_EXISTS )? fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )? ;
	public final DropAggregateStatement dropAggregateStatement() throws RecognitionException {
		DropAggregateStatement expr = null;


		FunctionName fn =null;
		CQL3Type.Raw v =null;


		        boolean ifExists = false;
		        List<CQL3Type.Raw> argsTypes = new ArrayList<>();
		        boolean argsPresent = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:589:5: ( K_DROP K_AGGREGATE ( K_IF K_EXISTS )? fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:589:7: K_DROP K_AGGREGATE ( K_IF K_EXISTS )? fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )?
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropAggregateStatement3090); 
			match(input,K_AGGREGATE,FOLLOW_K_AGGREGATE_in_dropAggregateStatement3092); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:590:7: ( K_IF K_EXISTS )?
			int alt54=2;
			int LA54_0 = input.LA(1);
			if ( (LA54_0==K_IF) ) {
				alt54=1;
			}
			switch (alt54) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:590:8: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropAggregateStatement3101); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropAggregateStatement3103); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_functionName_in_dropAggregateStatement3118);
			fn=functionName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:592:7: ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )?
			int alt57=2;
			int LA57_0 = input.LA(1);
			if ( (LA57_0==174) ) {
				alt57=1;
			}
			switch (alt57) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:593:9: '(' (v= comparatorType ( ',' v= comparatorType )* )? ')'
					{
					match(input,174,FOLLOW_174_in_dropAggregateStatement3136); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:594:11: (v= comparatorType ( ',' v= comparatorType )* )?
					int alt56=2;
					int LA56_0 = input.LA(1);
					if ( (LA56_0==IDENT||(LA56_0 >= K_AGGREGATE && LA56_0 <= K_ALL)||LA56_0==K_AS||LA56_0==K_ASCII||(LA56_0 >= K_BIGINT && LA56_0 <= K_BOOLEAN)||(LA56_0 >= K_CALLED && LA56_0 <= K_CLUSTERING)||(LA56_0 >= K_COMPACT && LA56_0 <= K_COUNTER)||(LA56_0 >= K_CUSTOM && LA56_0 <= K_DECIMAL)||(LA56_0 >= K_DISTINCT && LA56_0 <= K_DOUBLE)||(LA56_0 >= K_EXISTS && LA56_0 <= K_FLOAT)||LA56_0==K_FROZEN||(LA56_0 >= K_FUNCTION && LA56_0 <= K_FUNCTIONS)||LA56_0==K_INET||(LA56_0 >= K_INITCOND && LA56_0 <= K_INPUT)||LA56_0==K_INT||(LA56_0 >= K_JSON && LA56_0 <= K_KEYS)||(LA56_0 >= K_KEYSPACES && LA56_0 <= K_LIKE)||(LA56_0 >= K_LIST && LA56_0 <= K_MAP)||LA56_0==K_NOLOGIN||LA56_0==K_NOSUPERUSER||LA56_0==K_OPTIONS||(LA56_0 >= K_PARTITION && LA56_0 <= K_PERMISSIONS)||LA56_0==K_RETURNS||(LA56_0 >= K_ROLE && LA56_0 <= K_ROLES)||(LA56_0 >= K_SET && LA56_0 <= K_TINYINT)||LA56_0==K_TRIGGER||(LA56_0 >= K_TTL && LA56_0 <= K_TYPE)||(LA56_0 >= K_USER && LA56_0 <= K_USERS)||(LA56_0 >= K_UUID && LA56_0 <= K_VARINT)||LA56_0==K_WRITETIME||LA56_0==QUOTED_NAME||LA56_0==STRING_LITERAL) ) {
						alt56=1;
					}
					switch (alt56) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:595:13: v= comparatorType ( ',' v= comparatorType )*
							{
							pushFollow(FOLLOW_comparatorType_in_dropAggregateStatement3164);
							v=comparatorType();
							state._fsp--;

							 argsTypes.add(v); 
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:596:13: ( ',' v= comparatorType )*
							loop55:
							while (true) {
								int alt55=2;
								int LA55_0 = input.LA(1);
								if ( (LA55_0==177) ) {
									alt55=1;
								}

								switch (alt55) {
								case 1 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:596:15: ',' v= comparatorType
									{
									match(input,177,FOLLOW_177_in_dropAggregateStatement3182); 
									pushFollow(FOLLOW_comparatorType_in_dropAggregateStatement3186);
									v=comparatorType();
									state._fsp--;

									 argsTypes.add(v); 
									}
									break;

								default :
									break loop55;
								}
							}

							}
							break;

					}

					match(input,175,FOLLOW_175_in_dropAggregateStatement3214); 
					 argsPresent = true; 
					}
					break;

			}

			 expr = new DropAggregateStatement(fn, argsTypes, argsPresent, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "dropAggregateStatement"



	// $ANTLR start "createFunctionStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:604:1: createFunctionStatement returns [CreateFunctionStatement expr] : K_CREATE ( K_OR K_REPLACE )? K_FUNCTION ( K_IF K_NOT K_EXISTS )? fn= functionName '(' (k= noncol_ident v= comparatorType ( ',' k= noncol_ident v= comparatorType )* )? ')' ( ( K_RETURNS K_NULL ) | ( K_CALLED ) ) K_ON K_NULL K_INPUT K_RETURNS rt= comparatorType K_LANGUAGE language= IDENT K_AS body= STRING_LITERAL ;
	public final CreateFunctionStatement createFunctionStatement() throws RecognitionException {
		CreateFunctionStatement expr = null;


		Token language=null;
		Token body=null;
		FunctionName fn =null;
		ColumnIdentifier k =null;
		CQL3Type.Raw v =null;
		CQL3Type.Raw rt =null;


		        boolean orReplace = false;
		        boolean ifNotExists = false;

		        List<ColumnIdentifier> argsNames = new ArrayList<>();
		        List<CQL3Type.Raw> argsTypes = new ArrayList<>();
		        boolean calledOnNullInput = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:613:5: ( K_CREATE ( K_OR K_REPLACE )? K_FUNCTION ( K_IF K_NOT K_EXISTS )? fn= functionName '(' (k= noncol_ident v= comparatorType ( ',' k= noncol_ident v= comparatorType )* )? ')' ( ( K_RETURNS K_NULL ) | ( K_CALLED ) ) K_ON K_NULL K_INPUT K_RETURNS rt= comparatorType K_LANGUAGE language= IDENT K_AS body= STRING_LITERAL )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:613:7: K_CREATE ( K_OR K_REPLACE )? K_FUNCTION ( K_IF K_NOT K_EXISTS )? fn= functionName '(' (k= noncol_ident v= comparatorType ( ',' k= noncol_ident v= comparatorType )* )? ')' ( ( K_RETURNS K_NULL ) | ( K_CALLED ) ) K_ON K_NULL K_INPUT K_RETURNS rt= comparatorType K_LANGUAGE language= IDENT K_AS body= STRING_LITERAL
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createFunctionStatement3271); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:613:16: ( K_OR K_REPLACE )?
			int alt58=2;
			int LA58_0 = input.LA(1);
			if ( (LA58_0==K_OR) ) {
				alt58=1;
			}
			switch (alt58) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:613:17: K_OR K_REPLACE
					{
					match(input,K_OR,FOLLOW_K_OR_in_createFunctionStatement3274); 
					match(input,K_REPLACE,FOLLOW_K_REPLACE_in_createFunctionStatement3276); 
					 orReplace = true; 
					}
					break;

			}

			match(input,K_FUNCTION,FOLLOW_K_FUNCTION_in_createFunctionStatement3288); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:615:7: ( K_IF K_NOT K_EXISTS )?
			int alt59=2;
			int LA59_0 = input.LA(1);
			if ( (LA59_0==K_IF) ) {
				alt59=1;
			}
			switch (alt59) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:615:8: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createFunctionStatement3297); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createFunctionStatement3299); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createFunctionStatement3301); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_functionName_in_createFunctionStatement3315);
			fn=functionName();
			state._fsp--;

			match(input,174,FOLLOW_174_in_createFunctionStatement3323); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:618:9: (k= noncol_ident v= comparatorType ( ',' k= noncol_ident v= comparatorType )* )?
			int alt61=2;
			int LA61_0 = input.LA(1);
			if ( (LA61_0==IDENT||(LA61_0 >= K_AGGREGATE && LA61_0 <= K_ALL)||LA61_0==K_AS||LA61_0==K_ASCII||(LA61_0 >= K_BIGINT && LA61_0 <= K_BOOLEAN)||(LA61_0 >= K_CALLED && LA61_0 <= K_CLUSTERING)||(LA61_0 >= K_COMPACT && LA61_0 <= K_COUNTER)||(LA61_0 >= K_CUSTOM && LA61_0 <= K_DECIMAL)||(LA61_0 >= K_DISTINCT && LA61_0 <= K_DOUBLE)||(LA61_0 >= K_EXISTS && LA61_0 <= K_FLOAT)||LA61_0==K_FROZEN||(LA61_0 >= K_FUNCTION && LA61_0 <= K_FUNCTIONS)||LA61_0==K_INET||(LA61_0 >= K_INITCOND && LA61_0 <= K_INPUT)||LA61_0==K_INT||(LA61_0 >= K_JSON && LA61_0 <= K_KEYS)||(LA61_0 >= K_KEYSPACES && LA61_0 <= K_LIKE)||(LA61_0 >= K_LIST && LA61_0 <= K_MAP)||LA61_0==K_NOLOGIN||LA61_0==K_NOSUPERUSER||LA61_0==K_OPTIONS||(LA61_0 >= K_PARTITION && LA61_0 <= K_PERMISSIONS)||LA61_0==K_RETURNS||(LA61_0 >= K_ROLE && LA61_0 <= K_ROLES)||(LA61_0 >= K_SFUNC && LA61_0 <= K_TINYINT)||LA61_0==K_TRIGGER||(LA61_0 >= K_TTL && LA61_0 <= K_TYPE)||(LA61_0 >= K_USER && LA61_0 <= K_USERS)||(LA61_0 >= K_UUID && LA61_0 <= K_VARINT)||LA61_0==K_WRITETIME||LA61_0==QUOTED_NAME) ) {
				alt61=1;
			}
			switch (alt61) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:619:11: k= noncol_ident v= comparatorType ( ',' k= noncol_ident v= comparatorType )*
					{
					pushFollow(FOLLOW_noncol_ident_in_createFunctionStatement3347);
					k=noncol_ident();
					state._fsp--;

					pushFollow(FOLLOW_comparatorType_in_createFunctionStatement3351);
					v=comparatorType();
					state._fsp--;

					 argsNames.add(k); argsTypes.add(v); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:620:11: ( ',' k= noncol_ident v= comparatorType )*
					loop60:
					while (true) {
						int alt60=2;
						int LA60_0 = input.LA(1);
						if ( (LA60_0==177) ) {
							alt60=1;
						}

						switch (alt60) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:620:13: ',' k= noncol_ident v= comparatorType
							{
							match(input,177,FOLLOW_177_in_createFunctionStatement3367); 
							pushFollow(FOLLOW_noncol_ident_in_createFunctionStatement3371);
							k=noncol_ident();
							state._fsp--;

							pushFollow(FOLLOW_comparatorType_in_createFunctionStatement3375);
							v=comparatorType();
							state._fsp--;

							 argsNames.add(k); argsTypes.add(v); 
							}
							break;

						default :
							break loop60;
						}
					}

					}
					break;

			}

			match(input,175,FOLLOW_175_in_createFunctionStatement3399); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:7: ( ( K_RETURNS K_NULL ) | ( K_CALLED ) )
			int alt62=2;
			int LA62_0 = input.LA(1);
			if ( (LA62_0==K_RETURNS) ) {
				alt62=1;
			}
			else if ( (LA62_0==K_CALLED) ) {
				alt62=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 62, 0, input);
				throw nvae;
			}

			switch (alt62) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:9: ( K_RETURNS K_NULL )
					{
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:9: ( K_RETURNS K_NULL )
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:10: K_RETURNS K_NULL
					{
					match(input,K_RETURNS,FOLLOW_K_RETURNS_in_createFunctionStatement3410); 
					match(input,K_NULL,FOLLOW_K_NULL_in_createFunctionStatement3412); 
					}

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:30: ( K_CALLED )
					{
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:30: ( K_CALLED )
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:623:31: K_CALLED
					{
					match(input,K_CALLED,FOLLOW_K_CALLED_in_createFunctionStatement3418); 
					 calledOnNullInput=true; 
					}

					}
					break;

			}

			match(input,K_ON,FOLLOW_K_ON_in_createFunctionStatement3424); 
			match(input,K_NULL,FOLLOW_K_NULL_in_createFunctionStatement3426); 
			match(input,K_INPUT,FOLLOW_K_INPUT_in_createFunctionStatement3428); 
			match(input,K_RETURNS,FOLLOW_K_RETURNS_in_createFunctionStatement3436); 
			pushFollow(FOLLOW_comparatorType_in_createFunctionStatement3442);
			rt=comparatorType();
			state._fsp--;

			match(input,K_LANGUAGE,FOLLOW_K_LANGUAGE_in_createFunctionStatement3450); 
			language=(Token)match(input,IDENT,FOLLOW_IDENT_in_createFunctionStatement3456); 
			match(input,K_AS,FOLLOW_K_AS_in_createFunctionStatement3464); 
			body=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_createFunctionStatement3470); 
			 expr = new CreateFunctionStatement(fn, (language!=null?language.getText():null).toLowerCase(), (body!=null?body.getText():null),
			                                            argsNames, argsTypes, rt, calledOnNullInput, orReplace, ifNotExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createFunctionStatement"



	// $ANTLR start "dropFunctionStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:631:1: dropFunctionStatement returns [DropFunctionStatement expr] : K_DROP K_FUNCTION ( K_IF K_EXISTS )? fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )? ;
	public final DropFunctionStatement dropFunctionStatement() throws RecognitionException {
		DropFunctionStatement expr = null;


		FunctionName fn =null;
		CQL3Type.Raw v =null;


		        boolean ifExists = false;
		        List<CQL3Type.Raw> argsTypes = new ArrayList<>();
		        boolean argsPresent = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:637:5: ( K_DROP K_FUNCTION ( K_IF K_EXISTS )? fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:637:7: K_DROP K_FUNCTION ( K_IF K_EXISTS )? fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )?
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropFunctionStatement3508); 
			match(input,K_FUNCTION,FOLLOW_K_FUNCTION_in_dropFunctionStatement3510); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:638:7: ( K_IF K_EXISTS )?
			int alt63=2;
			int LA63_0 = input.LA(1);
			if ( (LA63_0==K_IF) ) {
				alt63=1;
			}
			switch (alt63) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:638:8: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropFunctionStatement3519); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropFunctionStatement3521); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_functionName_in_dropFunctionStatement3536);
			fn=functionName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:640:7: ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )?
			int alt66=2;
			int LA66_0 = input.LA(1);
			if ( (LA66_0==174) ) {
				alt66=1;
			}
			switch (alt66) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:641:9: '(' (v= comparatorType ( ',' v= comparatorType )* )? ')'
					{
					match(input,174,FOLLOW_174_in_dropFunctionStatement3554); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:642:11: (v= comparatorType ( ',' v= comparatorType )* )?
					int alt65=2;
					int LA65_0 = input.LA(1);
					if ( (LA65_0==IDENT||(LA65_0 >= K_AGGREGATE && LA65_0 <= K_ALL)||LA65_0==K_AS||LA65_0==K_ASCII||(LA65_0 >= K_BIGINT && LA65_0 <= K_BOOLEAN)||(LA65_0 >= K_CALLED && LA65_0 <= K_CLUSTERING)||(LA65_0 >= K_COMPACT && LA65_0 <= K_COUNTER)||(LA65_0 >= K_CUSTOM && LA65_0 <= K_DECIMAL)||(LA65_0 >= K_DISTINCT && LA65_0 <= K_DOUBLE)||(LA65_0 >= K_EXISTS && LA65_0 <= K_FLOAT)||LA65_0==K_FROZEN||(LA65_0 >= K_FUNCTION && LA65_0 <= K_FUNCTIONS)||LA65_0==K_INET||(LA65_0 >= K_INITCOND && LA65_0 <= K_INPUT)||LA65_0==K_INT||(LA65_0 >= K_JSON && LA65_0 <= K_KEYS)||(LA65_0 >= K_KEYSPACES && LA65_0 <= K_LIKE)||(LA65_0 >= K_LIST && LA65_0 <= K_MAP)||LA65_0==K_NOLOGIN||LA65_0==K_NOSUPERUSER||LA65_0==K_OPTIONS||(LA65_0 >= K_PARTITION && LA65_0 <= K_PERMISSIONS)||LA65_0==K_RETURNS||(LA65_0 >= K_ROLE && LA65_0 <= K_ROLES)||(LA65_0 >= K_SET && LA65_0 <= K_TINYINT)||LA65_0==K_TRIGGER||(LA65_0 >= K_TTL && LA65_0 <= K_TYPE)||(LA65_0 >= K_USER && LA65_0 <= K_USERS)||(LA65_0 >= K_UUID && LA65_0 <= K_VARINT)||LA65_0==K_WRITETIME||LA65_0==QUOTED_NAME||LA65_0==STRING_LITERAL) ) {
						alt65=1;
					}
					switch (alt65) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:643:13: v= comparatorType ( ',' v= comparatorType )*
							{
							pushFollow(FOLLOW_comparatorType_in_dropFunctionStatement3582);
							v=comparatorType();
							state._fsp--;

							 argsTypes.add(v); 
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:644:13: ( ',' v= comparatorType )*
							loop64:
							while (true) {
								int alt64=2;
								int LA64_0 = input.LA(1);
								if ( (LA64_0==177) ) {
									alt64=1;
								}

								switch (alt64) {
								case 1 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:644:15: ',' v= comparatorType
									{
									match(input,177,FOLLOW_177_in_dropFunctionStatement3600); 
									pushFollow(FOLLOW_comparatorType_in_dropFunctionStatement3604);
									v=comparatorType();
									state._fsp--;

									 argsTypes.add(v); 
									}
									break;

								default :
									break loop64;
								}
							}

							}
							break;

					}

					match(input,175,FOLLOW_175_in_dropFunctionStatement3632); 
					 argsPresent = true; 
					}
					break;

			}

			 expr = new DropFunctionStatement(fn, argsTypes, argsPresent, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "dropFunctionStatement"



	// $ANTLR start "createKeyspaceStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:655:1: createKeyspaceStatement returns [CreateKeyspaceStatement expr] : K_CREATE K_KEYSPACE ( K_IF K_NOT K_EXISTS )? ks= keyspaceName K_WITH properties[attrs] ;
	public final CreateKeyspaceStatement createKeyspaceStatement() throws RecognitionException {
		CreateKeyspaceStatement expr = null;


		String ks =null;


		        KeyspaceAttributes attrs = new KeyspaceAttributes();
		        boolean ifNotExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:660:5: ( K_CREATE K_KEYSPACE ( K_IF K_NOT K_EXISTS )? ks= keyspaceName K_WITH properties[attrs] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:660:7: K_CREATE K_KEYSPACE ( K_IF K_NOT K_EXISTS )? ks= keyspaceName K_WITH properties[attrs]
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createKeyspaceStatement3691); 
			match(input,K_KEYSPACE,FOLLOW_K_KEYSPACE_in_createKeyspaceStatement3693); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:660:27: ( K_IF K_NOT K_EXISTS )?
			int alt67=2;
			int LA67_0 = input.LA(1);
			if ( (LA67_0==K_IF) ) {
				alt67=1;
			}
			switch (alt67) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:660:28: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createKeyspaceStatement3696); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createKeyspaceStatement3698); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createKeyspaceStatement3700); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_keyspaceName_in_createKeyspaceStatement3709);
			ks=keyspaceName();
			state._fsp--;

			match(input,K_WITH,FOLLOW_K_WITH_in_createKeyspaceStatement3717); 
			pushFollow(FOLLOW_properties_in_createKeyspaceStatement3719);
			properties(attrs);
			state._fsp--;

			 expr = new CreateKeyspaceStatement(ks, attrs, ifNotExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createKeyspaceStatement"



	// $ANTLR start "createTableStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:671:1: createTableStatement returns [CreateTableStatement.RawStatement expr] : K_CREATE K_COLUMNFAMILY ( K_IF K_NOT K_EXISTS )? cf= columnFamilyName cfamDefinition[expr] ;
	public final CreateTableStatement.RawStatement createTableStatement() throws RecognitionException {
		CreateTableStatement.RawStatement expr = null;


		CFName cf =null;

		 boolean ifNotExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:673:5: ( K_CREATE K_COLUMNFAMILY ( K_IF K_NOT K_EXISTS )? cf= columnFamilyName cfamDefinition[expr] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:673:7: K_CREATE K_COLUMNFAMILY ( K_IF K_NOT K_EXISTS )? cf= columnFamilyName cfamDefinition[expr]
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createTableStatement3754); 
			match(input,K_COLUMNFAMILY,FOLLOW_K_COLUMNFAMILY_in_createTableStatement3756); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:673:31: ( K_IF K_NOT K_EXISTS )?
			int alt68=2;
			int LA68_0 = input.LA(1);
			if ( (LA68_0==K_IF) ) {
				alt68=1;
			}
			switch (alt68) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:673:32: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createTableStatement3759); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createTableStatement3761); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createTableStatement3763); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_columnFamilyName_in_createTableStatement3778);
			cf=columnFamilyName();
			state._fsp--;

			 expr = new CreateTableStatement.RawStatement(cf, ifNotExists); 
			pushFollow(FOLLOW_cfamDefinition_in_createTableStatement3788);
			cfamDefinition(expr);
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createTableStatement"



	// $ANTLR start "cfamDefinition"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:678:1: cfamDefinition[CreateTableStatement.RawStatement expr] : '(' cfamColumns[expr] ( ',' ( cfamColumns[expr] )? )* ')' ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )? ;
	public final void cfamDefinition(CreateTableStatement.RawStatement expr) throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:679:5: ( '(' cfamColumns[expr] ( ',' ( cfamColumns[expr] )? )* ')' ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:679:7: '(' cfamColumns[expr] ( ',' ( cfamColumns[expr] )? )* ')' ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )?
			{
			match(input,174,FOLLOW_174_in_cfamDefinition3807); 
			pushFollow(FOLLOW_cfamColumns_in_cfamDefinition3809);
			cfamColumns(expr);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:679:29: ( ',' ( cfamColumns[expr] )? )*
			loop70:
			while (true) {
				int alt70=2;
				int LA70_0 = input.LA(1);
				if ( (LA70_0==177) ) {
					alt70=1;
				}

				switch (alt70) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:679:31: ',' ( cfamColumns[expr] )?
					{
					match(input,177,FOLLOW_177_in_cfamDefinition3814); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:679:35: ( cfamColumns[expr] )?
					int alt69=2;
					int LA69_0 = input.LA(1);
					if ( (LA69_0==IDENT||(LA69_0 >= K_AGGREGATE && LA69_0 <= K_ALL)||LA69_0==K_AS||LA69_0==K_ASCII||(LA69_0 >= K_BIGINT && LA69_0 <= K_BOOLEAN)||(LA69_0 >= K_CALLED && LA69_0 <= K_CLUSTERING)||(LA69_0 >= K_COMPACT && LA69_0 <= K_COUNTER)||(LA69_0 >= K_CUSTOM && LA69_0 <= K_DECIMAL)||(LA69_0 >= K_DISTINCT && LA69_0 <= K_DOUBLE)||(LA69_0 >= K_EXISTS && LA69_0 <= K_FLOAT)||LA69_0==K_FROZEN||(LA69_0 >= K_FUNCTION && LA69_0 <= K_FUNCTIONS)||LA69_0==K_INET||(LA69_0 >= K_INITCOND && LA69_0 <= K_INPUT)||LA69_0==K_INT||(LA69_0 >= K_JSON && LA69_0 <= K_KEYS)||(LA69_0 >= K_KEYSPACES && LA69_0 <= K_LIKE)||(LA69_0 >= K_LIST && LA69_0 <= K_MAP)||LA69_0==K_NOLOGIN||LA69_0==K_NOSUPERUSER||LA69_0==K_OPTIONS||(LA69_0 >= K_PARTITION && LA69_0 <= K_PRIMARY)||LA69_0==K_RETURNS||(LA69_0 >= K_ROLE && LA69_0 <= K_ROLES)||(LA69_0 >= K_SFUNC && LA69_0 <= K_TINYINT)||LA69_0==K_TRIGGER||(LA69_0 >= K_TTL && LA69_0 <= K_TYPE)||(LA69_0 >= K_USER && LA69_0 <= K_USERS)||(LA69_0 >= K_UUID && LA69_0 <= K_VARINT)||LA69_0==K_WRITETIME||LA69_0==QUOTED_NAME) ) {
						alt69=1;
					}
					switch (alt69) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:679:35: cfamColumns[expr]
							{
							pushFollow(FOLLOW_cfamColumns_in_cfamDefinition3816);
							cfamColumns(expr);
							state._fsp--;

							}
							break;

					}

					}
					break;

				default :
					break loop70;
				}
			}

			match(input,175,FOLLOW_175_in_cfamDefinition3823); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:680:7: ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )?
			int alt72=2;
			int LA72_0 = input.LA(1);
			if ( (LA72_0==K_WITH) ) {
				alt72=1;
			}
			switch (alt72) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:680:9: K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )*
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_cfamDefinition3833); 
					pushFollow(FOLLOW_cfamProperty_in_cfamDefinition3835);
					cfamProperty(expr.properties);
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:680:46: ( K_AND cfamProperty[expr.properties] )*
					loop71:
					while (true) {
						int alt71=2;
						int LA71_0 = input.LA(1);
						if ( (LA71_0==K_AND) ) {
							alt71=1;
						}

						switch (alt71) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:680:48: K_AND cfamProperty[expr.properties]
							{
							match(input,K_AND,FOLLOW_K_AND_in_cfamDefinition3840); 
							pushFollow(FOLLOW_cfamProperty_in_cfamDefinition3842);
							cfamProperty(expr.properties);
							state._fsp--;

							}
							break;

						default :
							break loop71;
						}
					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "cfamDefinition"



	// $ANTLR start "cfamColumns"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:683:1: cfamColumns[CreateTableStatement.RawStatement expr] : (k= ident v= comparatorType ( K_STATIC )? ( K_PRIMARY K_KEY )? | K_PRIMARY K_KEY '(' pkDef[expr] ( ',' c= ident )* ')' );
	public final void cfamColumns(CreateTableStatement.RawStatement expr) throws RecognitionException {
		ColumnIdentifier k =null;
		CQL3Type.Raw v =null;
		ColumnIdentifier c =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:684:5: (k= ident v= comparatorType ( K_STATIC )? ( K_PRIMARY K_KEY )? | K_PRIMARY K_KEY '(' pkDef[expr] ( ',' c= ident )* ')' )
			int alt76=2;
			int LA76_0 = input.LA(1);
			if ( (LA76_0==IDENT||(LA76_0 >= K_AGGREGATE && LA76_0 <= K_ALL)||LA76_0==K_AS||LA76_0==K_ASCII||(LA76_0 >= K_BIGINT && LA76_0 <= K_BOOLEAN)||(LA76_0 >= K_CALLED && LA76_0 <= K_CLUSTERING)||(LA76_0 >= K_COMPACT && LA76_0 <= K_COUNTER)||(LA76_0 >= K_CUSTOM && LA76_0 <= K_DECIMAL)||(LA76_0 >= K_DISTINCT && LA76_0 <= K_DOUBLE)||(LA76_0 >= K_EXISTS && LA76_0 <= K_FLOAT)||LA76_0==K_FROZEN||(LA76_0 >= K_FUNCTION && LA76_0 <= K_FUNCTIONS)||LA76_0==K_INET||(LA76_0 >= K_INITCOND && LA76_0 <= K_INPUT)||LA76_0==K_INT||(LA76_0 >= K_JSON && LA76_0 <= K_KEYS)||(LA76_0 >= K_KEYSPACES && LA76_0 <= K_LIKE)||(LA76_0 >= K_LIST && LA76_0 <= K_MAP)||LA76_0==K_NOLOGIN||LA76_0==K_NOSUPERUSER||LA76_0==K_OPTIONS||(LA76_0 >= K_PARTITION && LA76_0 <= K_PERMISSIONS)||LA76_0==K_RETURNS||(LA76_0 >= K_ROLE && LA76_0 <= K_ROLES)||(LA76_0 >= K_SFUNC && LA76_0 <= K_TINYINT)||LA76_0==K_TRIGGER||(LA76_0 >= K_TTL && LA76_0 <= K_TYPE)||(LA76_0 >= K_USER && LA76_0 <= K_USERS)||(LA76_0 >= K_UUID && LA76_0 <= K_VARINT)||LA76_0==K_WRITETIME||LA76_0==QUOTED_NAME) ) {
				alt76=1;
			}
			else if ( (LA76_0==K_PRIMARY) ) {
				alt76=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 76, 0, input);
				throw nvae;
			}

			switch (alt76) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:684:7: k= ident v= comparatorType ( K_STATIC )? ( K_PRIMARY K_KEY )?
					{
					pushFollow(FOLLOW_ident_in_cfamColumns3868);
					k=ident();
					state._fsp--;

					pushFollow(FOLLOW_comparatorType_in_cfamColumns3872);
					v=comparatorType();
					state._fsp--;

					 boolean isStatic=false; 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:684:60: ( K_STATIC )?
					int alt73=2;
					int LA73_0 = input.LA(1);
					if ( (LA73_0==K_STATIC) ) {
						alt73=1;
					}
					switch (alt73) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:684:61: K_STATIC
							{
							match(input,K_STATIC,FOLLOW_K_STATIC_in_cfamColumns3877); 
							isStatic = true;
							}
							break;

					}

					 expr.addDefinition(k, v, isStatic); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:685:9: ( K_PRIMARY K_KEY )?
					int alt74=2;
					int LA74_0 = input.LA(1);
					if ( (LA74_0==K_PRIMARY) ) {
						alt74=1;
					}
					switch (alt74) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:685:10: K_PRIMARY K_KEY
							{
							match(input,K_PRIMARY,FOLLOW_K_PRIMARY_in_cfamColumns3894); 
							match(input,K_KEY,FOLLOW_K_KEY_in_cfamColumns3896); 
							 expr.addKeyAliases(Collections.singletonList(k)); 
							}
							break;

					}

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:686:7: K_PRIMARY K_KEY '(' pkDef[expr] ( ',' c= ident )* ')'
					{
					match(input,K_PRIMARY,FOLLOW_K_PRIMARY_in_cfamColumns3908); 
					match(input,K_KEY,FOLLOW_K_KEY_in_cfamColumns3910); 
					match(input,174,FOLLOW_174_in_cfamColumns3912); 
					pushFollow(FOLLOW_pkDef_in_cfamColumns3914);
					pkDef(expr);
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:686:39: ( ',' c= ident )*
					loop75:
					while (true) {
						int alt75=2;
						int LA75_0 = input.LA(1);
						if ( (LA75_0==177) ) {
							alt75=1;
						}

						switch (alt75) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:686:40: ',' c= ident
							{
							match(input,177,FOLLOW_177_in_cfamColumns3918); 
							pushFollow(FOLLOW_ident_in_cfamColumns3922);
							c=ident();
							state._fsp--;

							 expr.addColumnAlias(c); 
							}
							break;

						default :
							break loop75;
						}
					}

					match(input,175,FOLLOW_175_in_cfamColumns3929); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "cfamColumns"



	// $ANTLR start "pkDef"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:689:1: pkDef[CreateTableStatement.RawStatement expr] : (k= ident | '(' k1= ident ( ',' kn= ident )* ')' );
	public final void pkDef(CreateTableStatement.RawStatement expr) throws RecognitionException {
		ColumnIdentifier k =null;
		ColumnIdentifier k1 =null;
		ColumnIdentifier kn =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:690:5: (k= ident | '(' k1= ident ( ',' kn= ident )* ')' )
			int alt78=2;
			int LA78_0 = input.LA(1);
			if ( (LA78_0==IDENT||(LA78_0 >= K_AGGREGATE && LA78_0 <= K_ALL)||LA78_0==K_AS||LA78_0==K_ASCII||(LA78_0 >= K_BIGINT && LA78_0 <= K_BOOLEAN)||(LA78_0 >= K_CALLED && LA78_0 <= K_CLUSTERING)||(LA78_0 >= K_COMPACT && LA78_0 <= K_COUNTER)||(LA78_0 >= K_CUSTOM && LA78_0 <= K_DECIMAL)||(LA78_0 >= K_DISTINCT && LA78_0 <= K_DOUBLE)||(LA78_0 >= K_EXISTS && LA78_0 <= K_FLOAT)||LA78_0==K_FROZEN||(LA78_0 >= K_FUNCTION && LA78_0 <= K_FUNCTIONS)||LA78_0==K_INET||(LA78_0 >= K_INITCOND && LA78_0 <= K_INPUT)||LA78_0==K_INT||(LA78_0 >= K_JSON && LA78_0 <= K_KEYS)||(LA78_0 >= K_KEYSPACES && LA78_0 <= K_LIKE)||(LA78_0 >= K_LIST && LA78_0 <= K_MAP)||LA78_0==K_NOLOGIN||LA78_0==K_NOSUPERUSER||LA78_0==K_OPTIONS||(LA78_0 >= K_PARTITION && LA78_0 <= K_PERMISSIONS)||LA78_0==K_RETURNS||(LA78_0 >= K_ROLE && LA78_0 <= K_ROLES)||(LA78_0 >= K_SFUNC && LA78_0 <= K_TINYINT)||LA78_0==K_TRIGGER||(LA78_0 >= K_TTL && LA78_0 <= K_TYPE)||(LA78_0 >= K_USER && LA78_0 <= K_USERS)||(LA78_0 >= K_UUID && LA78_0 <= K_VARINT)||LA78_0==K_WRITETIME||LA78_0==QUOTED_NAME) ) {
				alt78=1;
			}
			else if ( (LA78_0==174) ) {
				alt78=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 78, 0, input);
				throw nvae;
			}

			switch (alt78) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:690:7: k= ident
					{
					pushFollow(FOLLOW_ident_in_pkDef3949);
					k=ident();
					state._fsp--;

					 expr.addKeyAliases(Collections.singletonList(k)); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:691:7: '(' k1= ident ( ',' kn= ident )* ')'
					{
					match(input,174,FOLLOW_174_in_pkDef3959); 
					 List<ColumnIdentifier> l = new ArrayList<ColumnIdentifier>(); 
					pushFollow(FOLLOW_ident_in_pkDef3965);
					k1=ident();
					state._fsp--;

					 l.add(k1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:691:101: ( ',' kn= ident )*
					loop77:
					while (true) {
						int alt77=2;
						int LA77_0 = input.LA(1);
						if ( (LA77_0==177) ) {
							alt77=1;
						}

						switch (alt77) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:691:103: ',' kn= ident
							{
							match(input,177,FOLLOW_177_in_pkDef3971); 
							pushFollow(FOLLOW_ident_in_pkDef3975);
							kn=ident();
							state._fsp--;

							 l.add(kn); 
							}
							break;

						default :
							break loop77;
						}
					}

					match(input,175,FOLLOW_175_in_pkDef3982); 
					 expr.addKeyAliases(l); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "pkDef"



	// $ANTLR start "cfamProperty"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:694:1: cfamProperty[CFProperties props] : ( property[props.properties] | K_COMPACT K_STORAGE | K_CLUSTERING K_ORDER K_BY '(' cfamOrdering[props] ( ',' cfamOrdering[props] )* ')' );
	public final void cfamProperty(CFProperties props) throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:695:5: ( property[props.properties] | K_COMPACT K_STORAGE | K_CLUSTERING K_ORDER K_BY '(' cfamOrdering[props] ( ',' cfamOrdering[props] )* ')' )
			int alt80=3;
			switch ( input.LA(1) ) {
			case IDENT:
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
			case QUOTED_NAME:
				{
				alt80=1;
				}
				break;
			case K_COMPACT:
				{
				int LA80_2 = input.LA(2);
				if ( (LA80_2==K_STORAGE) ) {
					alt80=2;
				}
				else if ( (LA80_2==184) ) {
					alt80=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 80, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case K_CLUSTERING:
				{
				int LA80_3 = input.LA(2);
				if ( (LA80_3==K_ORDER) ) {
					alt80=3;
				}
				else if ( (LA80_3==184) ) {
					alt80=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 80, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 80, 0, input);
				throw nvae;
			}
			switch (alt80) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:695:7: property[props.properties]
					{
					pushFollow(FOLLOW_property_in_cfamProperty4002);
					property(props.properties);
					state._fsp--;

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:696:7: K_COMPACT K_STORAGE
					{
					match(input,K_COMPACT,FOLLOW_K_COMPACT_in_cfamProperty4011); 
					match(input,K_STORAGE,FOLLOW_K_STORAGE_in_cfamProperty4013); 
					 props.setCompactStorage(); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:697:7: K_CLUSTERING K_ORDER K_BY '(' cfamOrdering[props] ( ',' cfamOrdering[props] )* ')'
					{
					match(input,K_CLUSTERING,FOLLOW_K_CLUSTERING_in_cfamProperty4023); 
					match(input,K_ORDER,FOLLOW_K_ORDER_in_cfamProperty4025); 
					match(input,K_BY,FOLLOW_K_BY_in_cfamProperty4027); 
					match(input,174,FOLLOW_174_in_cfamProperty4029); 
					pushFollow(FOLLOW_cfamOrdering_in_cfamProperty4031);
					cfamOrdering(props);
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:697:57: ( ',' cfamOrdering[props] )*
					loop79:
					while (true) {
						int alt79=2;
						int LA79_0 = input.LA(1);
						if ( (LA79_0==177) ) {
							alt79=1;
						}

						switch (alt79) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:697:58: ',' cfamOrdering[props]
							{
							match(input,177,FOLLOW_177_in_cfamProperty4035); 
							pushFollow(FOLLOW_cfamOrdering_in_cfamProperty4037);
							cfamOrdering(props);
							state._fsp--;

							}
							break;

						default :
							break loop79;
						}
					}

					match(input,175,FOLLOW_175_in_cfamProperty4042); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "cfamProperty"



	// $ANTLR start "cfamOrdering"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:700:1: cfamOrdering[CFProperties props] : k= ident ( K_ASC | K_DESC ) ;
	public final void cfamOrdering(CFProperties props) throws RecognitionException {
		ColumnIdentifier k =null;

		 boolean reversed=false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:702:5: (k= ident ( K_ASC | K_DESC ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:702:7: k= ident ( K_ASC | K_DESC )
			{
			pushFollow(FOLLOW_ident_in_cfamOrdering4070);
			k=ident();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:702:15: ( K_ASC | K_DESC )
			int alt81=2;
			int LA81_0 = input.LA(1);
			if ( (LA81_0==K_ASC) ) {
				alt81=1;
			}
			else if ( (LA81_0==K_DESC) ) {
				alt81=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 81, 0, input);
				throw nvae;
			}

			switch (alt81) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:702:16: K_ASC
					{
					match(input,K_ASC,FOLLOW_K_ASC_in_cfamOrdering4073); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:702:24: K_DESC
					{
					match(input,K_DESC,FOLLOW_K_DESC_in_cfamOrdering4077); 
					 reversed=true;
					}
					break;

			}

			 props.setOrdering(k, reversed); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "cfamOrdering"



	// $ANTLR start "createTypeStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:713:1: createTypeStatement returns [CreateTypeStatement expr] : K_CREATE K_TYPE ( K_IF K_NOT K_EXISTS )? tn= userTypeName '(' typeColumns[expr] ( ',' ( typeColumns[expr] )? )* ')' ;
	public final CreateTypeStatement createTypeStatement() throws RecognitionException {
		CreateTypeStatement expr = null;


		UTName tn =null;

		 boolean ifNotExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:715:5: ( K_CREATE K_TYPE ( K_IF K_NOT K_EXISTS )? tn= userTypeName '(' typeColumns[expr] ( ',' ( typeColumns[expr] )? )* ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:715:7: K_CREATE K_TYPE ( K_IF K_NOT K_EXISTS )? tn= userTypeName '(' typeColumns[expr] ( ',' ( typeColumns[expr] )? )* ')'
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createTypeStatement4116); 
			match(input,K_TYPE,FOLLOW_K_TYPE_in_createTypeStatement4118); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:715:23: ( K_IF K_NOT K_EXISTS )?
			int alt82=2;
			int LA82_0 = input.LA(1);
			if ( (LA82_0==K_IF) ) {
				alt82=1;
			}
			switch (alt82) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:715:24: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createTypeStatement4121); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createTypeStatement4123); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createTypeStatement4125); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_userTypeName_in_createTypeStatement4143);
			tn=userTypeName();
			state._fsp--;

			 expr = new CreateTypeStatement(tn, ifNotExists); 
			match(input,174,FOLLOW_174_in_createTypeStatement4156); 
			pushFollow(FOLLOW_typeColumns_in_createTypeStatement4158);
			typeColumns(expr);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:717:32: ( ',' ( typeColumns[expr] )? )*
			loop84:
			while (true) {
				int alt84=2;
				int LA84_0 = input.LA(1);
				if ( (LA84_0==177) ) {
					alt84=1;
				}

				switch (alt84) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:717:34: ',' ( typeColumns[expr] )?
					{
					match(input,177,FOLLOW_177_in_createTypeStatement4163); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:717:38: ( typeColumns[expr] )?
					int alt83=2;
					int LA83_0 = input.LA(1);
					if ( (LA83_0==IDENT||(LA83_0 >= K_AGGREGATE && LA83_0 <= K_ALL)||LA83_0==K_AS||LA83_0==K_ASCII||(LA83_0 >= K_BIGINT && LA83_0 <= K_BOOLEAN)||(LA83_0 >= K_CALLED && LA83_0 <= K_CLUSTERING)||(LA83_0 >= K_COMPACT && LA83_0 <= K_COUNTER)||(LA83_0 >= K_CUSTOM && LA83_0 <= K_DECIMAL)||(LA83_0 >= K_DISTINCT && LA83_0 <= K_DOUBLE)||(LA83_0 >= K_EXISTS && LA83_0 <= K_FLOAT)||LA83_0==K_FROZEN||(LA83_0 >= K_FUNCTION && LA83_0 <= K_FUNCTIONS)||LA83_0==K_INET||(LA83_0 >= K_INITCOND && LA83_0 <= K_INPUT)||LA83_0==K_INT||(LA83_0 >= K_JSON && LA83_0 <= K_KEYS)||(LA83_0 >= K_KEYSPACES && LA83_0 <= K_LIKE)||(LA83_0 >= K_LIST && LA83_0 <= K_MAP)||LA83_0==K_NOLOGIN||LA83_0==K_NOSUPERUSER||LA83_0==K_OPTIONS||(LA83_0 >= K_PARTITION && LA83_0 <= K_PERMISSIONS)||LA83_0==K_RETURNS||(LA83_0 >= K_ROLE && LA83_0 <= K_ROLES)||(LA83_0 >= K_SFUNC && LA83_0 <= K_TINYINT)||LA83_0==K_TRIGGER||(LA83_0 >= K_TTL && LA83_0 <= K_TYPE)||(LA83_0 >= K_USER && LA83_0 <= K_USERS)||(LA83_0 >= K_UUID && LA83_0 <= K_VARINT)||LA83_0==K_WRITETIME||LA83_0==QUOTED_NAME) ) {
						alt83=1;
					}
					switch (alt83) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:717:38: typeColumns[expr]
							{
							pushFollow(FOLLOW_typeColumns_in_createTypeStatement4165);
							typeColumns(expr);
							state._fsp--;

							}
							break;

					}

					}
					break;

				default :
					break loop84;
				}
			}

			match(input,175,FOLLOW_175_in_createTypeStatement4172); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createTypeStatement"



	// $ANTLR start "typeColumns"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:720:1: typeColumns[CreateTypeStatement expr] : k= noncol_ident v= comparatorType ;
	public final void typeColumns(CreateTypeStatement expr) throws RecognitionException {
		ColumnIdentifier k =null;
		CQL3Type.Raw v =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:721:5: (k= noncol_ident v= comparatorType )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:721:7: k= noncol_ident v= comparatorType
			{
			pushFollow(FOLLOW_noncol_ident_in_typeColumns4192);
			k=noncol_ident();
			state._fsp--;

			pushFollow(FOLLOW_comparatorType_in_typeColumns4196);
			v=comparatorType();
			state._fsp--;

			 expr.addDefinition(k, v); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "typeColumns"



	// $ANTLR start "createIndexStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:729:1: createIndexStatement returns [CreateIndexStatement expr] : K_CREATE ( K_CUSTOM )? K_INDEX ( K_IF K_NOT K_EXISTS )? ( idxName[name] )? K_ON cf= columnFamilyName '(' ( indexIdent[targets] ( ',' indexIdent[targets] )* )? ')' ( K_USING cls= STRING_LITERAL )? ( K_WITH properties[props] )? ;
	public final CreateIndexStatement createIndexStatement() throws RecognitionException {
		CreateIndexStatement expr = null;


		Token cls=null;
		CFName cf =null;


		        IndexPropDefs props = new IndexPropDefs();
		        boolean ifNotExists = false;
		        IndexName name = new IndexName();
		        List<IndexTarget.Raw> targets = new ArrayList<>();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:736:5: ( K_CREATE ( K_CUSTOM )? K_INDEX ( K_IF K_NOT K_EXISTS )? ( idxName[name] )? K_ON cf= columnFamilyName '(' ( indexIdent[targets] ( ',' indexIdent[targets] )* )? ')' ( K_USING cls= STRING_LITERAL )? ( K_WITH properties[props] )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:736:7: K_CREATE ( K_CUSTOM )? K_INDEX ( K_IF K_NOT K_EXISTS )? ( idxName[name] )? K_ON cf= columnFamilyName '(' ( indexIdent[targets] ( ',' indexIdent[targets] )* )? ')' ( K_USING cls= STRING_LITERAL )? ( K_WITH properties[props] )?
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createIndexStatement4231); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:736:16: ( K_CUSTOM )?
			int alt85=2;
			int LA85_0 = input.LA(1);
			if ( (LA85_0==K_CUSTOM) ) {
				alt85=1;
			}
			switch (alt85) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:736:17: K_CUSTOM
					{
					match(input,K_CUSTOM,FOLLOW_K_CUSTOM_in_createIndexStatement4234); 
					 props.isCustom = true; 
					}
					break;

			}

			match(input,K_INDEX,FOLLOW_K_INDEX_in_createIndexStatement4240); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:736:63: ( K_IF K_NOT K_EXISTS )?
			int alt86=2;
			int LA86_0 = input.LA(1);
			if ( (LA86_0==K_IF) ) {
				alt86=1;
			}
			switch (alt86) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:736:64: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createIndexStatement4243); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createIndexStatement4245); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createIndexStatement4247); 
					 ifNotExists = true; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:737:9: ( idxName[name] )?
			int alt87=2;
			int LA87_0 = input.LA(1);
			if ( (LA87_0==IDENT||(LA87_0 >= K_AGGREGATE && LA87_0 <= K_ALL)||LA87_0==K_AS||LA87_0==K_ASCII||(LA87_0 >= K_BIGINT && LA87_0 <= K_BOOLEAN)||(LA87_0 >= K_CALLED && LA87_0 <= K_CLUSTERING)||(LA87_0 >= K_COMPACT && LA87_0 <= K_COUNTER)||(LA87_0 >= K_CUSTOM && LA87_0 <= K_DECIMAL)||(LA87_0 >= K_DISTINCT && LA87_0 <= K_DOUBLE)||(LA87_0 >= K_EXISTS && LA87_0 <= K_FLOAT)||LA87_0==K_FROZEN||(LA87_0 >= K_FUNCTION && LA87_0 <= K_FUNCTIONS)||LA87_0==K_INET||(LA87_0 >= K_INITCOND && LA87_0 <= K_INPUT)||LA87_0==K_INT||(LA87_0 >= K_JSON && LA87_0 <= K_KEYS)||(LA87_0 >= K_KEYSPACES && LA87_0 <= K_LIKE)||(LA87_0 >= K_LIST && LA87_0 <= K_MAP)||LA87_0==K_NOLOGIN||LA87_0==K_NOSUPERUSER||LA87_0==K_OPTIONS||(LA87_0 >= K_PARTITION && LA87_0 <= K_PERMISSIONS)||LA87_0==K_RETURNS||(LA87_0 >= K_ROLE && LA87_0 <= K_ROLES)||(LA87_0 >= K_SFUNC && LA87_0 <= K_TINYINT)||LA87_0==K_TRIGGER||(LA87_0 >= K_TTL && LA87_0 <= K_TYPE)||(LA87_0 >= K_USER && LA87_0 <= K_USERS)||(LA87_0 >= K_UUID && LA87_0 <= K_VARINT)||LA87_0==K_WRITETIME||(LA87_0 >= QMARK && LA87_0 <= QUOTED_NAME)) ) {
				alt87=1;
			}
			switch (alt87) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:737:10: idxName[name]
					{
					pushFollow(FOLLOW_idxName_in_createIndexStatement4263);
					idxName(name);
					state._fsp--;

					}
					break;

			}

			match(input,K_ON,FOLLOW_K_ON_in_createIndexStatement4268); 
			pushFollow(FOLLOW_columnFamilyName_in_createIndexStatement4272);
			cf=columnFamilyName();
			state._fsp--;

			match(input,174,FOLLOW_174_in_createIndexStatement4274); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:737:55: ( indexIdent[targets] ( ',' indexIdent[targets] )* )?
			int alt89=2;
			int LA89_0 = input.LA(1);
			if ( (LA89_0==IDENT||(LA89_0 >= K_AGGREGATE && LA89_0 <= K_ALL)||LA89_0==K_AS||LA89_0==K_ASCII||(LA89_0 >= K_BIGINT && LA89_0 <= K_BOOLEAN)||(LA89_0 >= K_CALLED && LA89_0 <= K_CLUSTERING)||(LA89_0 >= K_COMPACT && LA89_0 <= K_COUNTER)||(LA89_0 >= K_CUSTOM && LA89_0 <= K_DECIMAL)||(LA89_0 >= K_DISTINCT && LA89_0 <= K_DOUBLE)||LA89_0==K_ENTRIES||(LA89_0 >= K_EXISTS && LA89_0 <= K_FLOAT)||(LA89_0 >= K_FROZEN && LA89_0 <= K_FUNCTIONS)||LA89_0==K_INET||(LA89_0 >= K_INITCOND && LA89_0 <= K_INPUT)||LA89_0==K_INT||(LA89_0 >= K_JSON && LA89_0 <= K_KEYS)||(LA89_0 >= K_KEYSPACES && LA89_0 <= K_LIKE)||(LA89_0 >= K_LIST && LA89_0 <= K_MAP)||LA89_0==K_NOLOGIN||LA89_0==K_NOSUPERUSER||LA89_0==K_OPTIONS||(LA89_0 >= K_PARTITION && LA89_0 <= K_PERMISSIONS)||LA89_0==K_RETURNS||(LA89_0 >= K_ROLE && LA89_0 <= K_ROLES)||(LA89_0 >= K_SFUNC && LA89_0 <= K_TINYINT)||LA89_0==K_TRIGGER||(LA89_0 >= K_TTL && LA89_0 <= K_TYPE)||(LA89_0 >= K_USER && LA89_0 <= K_USERS)||(LA89_0 >= K_UUID && LA89_0 <= K_VARINT)||LA89_0==K_WRITETIME||LA89_0==QUOTED_NAME) ) {
				alt89=1;
			}
			switch (alt89) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:737:56: indexIdent[targets] ( ',' indexIdent[targets] )*
					{
					pushFollow(FOLLOW_indexIdent_in_createIndexStatement4277);
					indexIdent(targets);
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:737:76: ( ',' indexIdent[targets] )*
					loop88:
					while (true) {
						int alt88=2;
						int LA88_0 = input.LA(1);
						if ( (LA88_0==177) ) {
							alt88=1;
						}

						switch (alt88) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:737:77: ',' indexIdent[targets]
							{
							match(input,177,FOLLOW_177_in_createIndexStatement4281); 
							pushFollow(FOLLOW_indexIdent_in_createIndexStatement4283);
							indexIdent(targets);
							state._fsp--;

							}
							break;

						default :
							break loop88;
						}
					}

					}
					break;

			}

			match(input,175,FOLLOW_175_in_createIndexStatement4290); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:738:9: ( K_USING cls= STRING_LITERAL )?
			int alt90=2;
			int LA90_0 = input.LA(1);
			if ( (LA90_0==K_USING) ) {
				alt90=1;
			}
			switch (alt90) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:738:10: K_USING cls= STRING_LITERAL
					{
					match(input,K_USING,FOLLOW_K_USING_in_createIndexStatement4301); 
					cls=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_createIndexStatement4305); 
					 props.customClass = (cls!=null?cls.getText():null); 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:739:9: ( K_WITH properties[props] )?
			int alt91=2;
			int LA91_0 = input.LA(1);
			if ( (LA91_0==K_WITH) ) {
				alt91=1;
			}
			switch (alt91) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:739:10: K_WITH properties[props]
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_createIndexStatement4320); 
					pushFollow(FOLLOW_properties_in_createIndexStatement4322);
					properties(props);
					state._fsp--;

					}
					break;

			}

			 expr = new CreateIndexStatement(cf, name, targets, props, ifNotExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createIndexStatement"



	// $ANTLR start "indexIdent"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:743:1: indexIdent[List<IndexTarget.Raw> targets] : (c= cident | K_VALUES '(' c= cident ')' | K_KEYS '(' c= cident ')' | K_ENTRIES '(' c= cident ')' | K_FULL '(' c= cident ')' );
	public final void indexIdent(List<IndexTarget.Raw> targets) throws RecognitionException {
		ColumnIdentifier.Raw c =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:744:5: (c= cident | K_VALUES '(' c= cident ')' | K_KEYS '(' c= cident ')' | K_ENTRIES '(' c= cident ')' | K_FULL '(' c= cident ')' )
			int alt92=5;
			switch ( input.LA(1) ) {
			case IDENT:
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
			case QUOTED_NAME:
				{
				alt92=1;
				}
				break;
			case K_VALUES:
				{
				int LA92_2 = input.LA(2);
				if ( (LA92_2==174) ) {
					alt92=2;
				}
				else if ( (LA92_2==175||LA92_2==177) ) {
					alt92=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 92, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case K_KEYS:
				{
				int LA92_3 = input.LA(2);
				if ( (LA92_3==174) ) {
					alt92=3;
				}
				else if ( (LA92_3==175||LA92_3==177) ) {
					alt92=1;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 92, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case K_ENTRIES:
				{
				alt92=4;
				}
				break;
			case K_FULL:
				{
				alt92=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 92, 0, input);
				throw nvae;
			}
			switch (alt92) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:744:7: c= cident
					{
					pushFollow(FOLLOW_cident_in_indexIdent4354);
					c=cident();
					state._fsp--;

					 targets.add(IndexTarget.Raw.simpleIndexOn(c)); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:745:7: K_VALUES '(' c= cident ')'
					{
					match(input,K_VALUES,FOLLOW_K_VALUES_in_indexIdent4382); 
					match(input,174,FOLLOW_174_in_indexIdent4384); 
					pushFollow(FOLLOW_cident_in_indexIdent4388);
					c=cident();
					state._fsp--;

					match(input,175,FOLLOW_175_in_indexIdent4390); 
					 targets.add(IndexTarget.Raw.valuesOf(c)); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:746:7: K_KEYS '(' c= cident ')'
					{
					match(input,K_KEYS,FOLLOW_K_KEYS_in_indexIdent4401); 
					match(input,174,FOLLOW_174_in_indexIdent4403); 
					pushFollow(FOLLOW_cident_in_indexIdent4407);
					c=cident();
					state._fsp--;

					match(input,175,FOLLOW_175_in_indexIdent4409); 
					 targets.add(IndexTarget.Raw.keysOf(c)); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:747:7: K_ENTRIES '(' c= cident ')'
					{
					match(input,K_ENTRIES,FOLLOW_K_ENTRIES_in_indexIdent4422); 
					match(input,174,FOLLOW_174_in_indexIdent4424); 
					pushFollow(FOLLOW_cident_in_indexIdent4428);
					c=cident();
					state._fsp--;

					match(input,175,FOLLOW_175_in_indexIdent4430); 
					 targets.add(IndexTarget.Raw.keysAndValuesOf(c)); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:748:7: K_FULL '(' c= cident ')'
					{
					match(input,K_FULL,FOLLOW_K_FULL_in_indexIdent4440); 
					match(input,174,FOLLOW_174_in_indexIdent4442); 
					pushFollow(FOLLOW_cident_in_indexIdent4446);
					c=cident();
					state._fsp--;

					match(input,175,FOLLOW_175_in_indexIdent4448); 
					 targets.add(IndexTarget.Raw.fullCollection(c)); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "indexIdent"



	// $ANTLR start "createMaterializedViewStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:759:1: createMaterializedViewStatement returns [CreateViewStatement expr] : K_CREATE K_MATERIALIZED K_VIEW ( K_IF K_NOT K_EXISTS )? cf= columnFamilyName K_AS K_SELECT sclause= selectClause K_FROM basecf= columnFamilyName ( K_WHERE wclause= whereClause )? K_PRIMARY K_KEY ( '(' '(' k1= cident ( ',' kn= cident )* ')' ( ',' c1= cident )* ')' | '(' k1= cident ( ',' cn= cident )* ')' ) ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )? ;
	public final CreateViewStatement createMaterializedViewStatement() throws RecognitionException {
		CreateViewStatement expr = null;


		CFName cf =null;
		List<RawSelector> sclause =null;
		CFName basecf =null;
		WhereClause.Builder wclause =null;
		ColumnIdentifier.Raw k1 =null;
		ColumnIdentifier.Raw kn =null;
		ColumnIdentifier.Raw c1 =null;
		ColumnIdentifier.Raw cn =null;


		        boolean ifNotExists = false;
		        List<ColumnIdentifier.Raw> partitionKeys = new ArrayList<>();
		        List<ColumnIdentifier.Raw> compositeKeys = new ArrayList<>();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:765:5: ( K_CREATE K_MATERIALIZED K_VIEW ( K_IF K_NOT K_EXISTS )? cf= columnFamilyName K_AS K_SELECT sclause= selectClause K_FROM basecf= columnFamilyName ( K_WHERE wclause= whereClause )? K_PRIMARY K_KEY ( '(' '(' k1= cident ( ',' kn= cident )* ')' ( ',' c1= cident )* ')' | '(' k1= cident ( ',' cn= cident )* ')' ) ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:765:7: K_CREATE K_MATERIALIZED K_VIEW ( K_IF K_NOT K_EXISTS )? cf= columnFamilyName K_AS K_SELECT sclause= selectClause K_FROM basecf= columnFamilyName ( K_WHERE wclause= whereClause )? K_PRIMARY K_KEY ( '(' '(' k1= cident ( ',' kn= cident )* ')' ( ',' c1= cident )* ')' | '(' k1= cident ( ',' cn= cident )* ')' ) ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )?
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createMaterializedViewStatement4485); 
			match(input,K_MATERIALIZED,FOLLOW_K_MATERIALIZED_in_createMaterializedViewStatement4487); 
			match(input,K_VIEW,FOLLOW_K_VIEW_in_createMaterializedViewStatement4489); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:765:38: ( K_IF K_NOT K_EXISTS )?
			int alt93=2;
			int LA93_0 = input.LA(1);
			if ( (LA93_0==K_IF) ) {
				alt93=1;
			}
			switch (alt93) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:765:39: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createMaterializedViewStatement4492); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createMaterializedViewStatement4494); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createMaterializedViewStatement4496); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_columnFamilyName_in_createMaterializedViewStatement4504);
			cf=columnFamilyName();
			state._fsp--;

			match(input,K_AS,FOLLOW_K_AS_in_createMaterializedViewStatement4506); 
			match(input,K_SELECT,FOLLOW_K_SELECT_in_createMaterializedViewStatement4516); 
			pushFollow(FOLLOW_selectClause_in_createMaterializedViewStatement4520);
			sclause=selectClause();
			state._fsp--;

			match(input,K_FROM,FOLLOW_K_FROM_in_createMaterializedViewStatement4522); 
			pushFollow(FOLLOW_columnFamilyName_in_createMaterializedViewStatement4526);
			basecf=columnFamilyName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:767:9: ( K_WHERE wclause= whereClause )?
			int alt94=2;
			int LA94_0 = input.LA(1);
			if ( (LA94_0==K_WHERE) ) {
				alt94=1;
			}
			switch (alt94) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:767:10: K_WHERE wclause= whereClause
					{
					match(input,K_WHERE,FOLLOW_K_WHERE_in_createMaterializedViewStatement4537); 
					pushFollow(FOLLOW_whereClause_in_createMaterializedViewStatement4541);
					wclause=whereClause();
					state._fsp--;

					}
					break;

			}

			match(input,K_PRIMARY,FOLLOW_K_PRIMARY_in_createMaterializedViewStatement4553); 
			match(input,K_KEY,FOLLOW_K_KEY_in_createMaterializedViewStatement4555); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:768:25: ( '(' '(' k1= cident ( ',' kn= cident )* ')' ( ',' c1= cident )* ')' | '(' k1= cident ( ',' cn= cident )* ')' )
			int alt98=2;
			int LA98_0 = input.LA(1);
			if ( (LA98_0==174) ) {
				int LA98_1 = input.LA(2);
				if ( (LA98_1==174) ) {
					alt98=1;
				}
				else if ( (LA98_1==IDENT||(LA98_1 >= K_AGGREGATE && LA98_1 <= K_ALL)||LA98_1==K_AS||LA98_1==K_ASCII||(LA98_1 >= K_BIGINT && LA98_1 <= K_BOOLEAN)||(LA98_1 >= K_CALLED && LA98_1 <= K_CLUSTERING)||(LA98_1 >= K_COMPACT && LA98_1 <= K_COUNTER)||(LA98_1 >= K_CUSTOM && LA98_1 <= K_DECIMAL)||(LA98_1 >= K_DISTINCT && LA98_1 <= K_DOUBLE)||(LA98_1 >= K_EXISTS && LA98_1 <= K_FLOAT)||LA98_1==K_FROZEN||(LA98_1 >= K_FUNCTION && LA98_1 <= K_FUNCTIONS)||LA98_1==K_INET||(LA98_1 >= K_INITCOND && LA98_1 <= K_INPUT)||LA98_1==K_INT||(LA98_1 >= K_JSON && LA98_1 <= K_KEYS)||(LA98_1 >= K_KEYSPACES && LA98_1 <= K_LIKE)||(LA98_1 >= K_LIST && LA98_1 <= K_MAP)||LA98_1==K_NOLOGIN||LA98_1==K_NOSUPERUSER||LA98_1==K_OPTIONS||(LA98_1 >= K_PARTITION && LA98_1 <= K_PERMISSIONS)||LA98_1==K_RETURNS||(LA98_1 >= K_ROLE && LA98_1 <= K_ROLES)||(LA98_1 >= K_SFUNC && LA98_1 <= K_TINYINT)||LA98_1==K_TRIGGER||(LA98_1 >= K_TTL && LA98_1 <= K_TYPE)||(LA98_1 >= K_USER && LA98_1 <= K_USERS)||(LA98_1 >= K_UUID && LA98_1 <= K_VARINT)||LA98_1==K_WRITETIME||LA98_1==QUOTED_NAME) ) {
					alt98=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 98, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 98, 0, input);
				throw nvae;
			}

			switch (alt98) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:769:9: '(' '(' k1= cident ( ',' kn= cident )* ')' ( ',' c1= cident )* ')'
					{
					match(input,174,FOLLOW_174_in_createMaterializedViewStatement4567); 
					match(input,174,FOLLOW_174_in_createMaterializedViewStatement4569); 
					pushFollow(FOLLOW_cident_in_createMaterializedViewStatement4573);
					k1=cident();
					state._fsp--;

					 partitionKeys.add(k1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:769:54: ( ',' kn= cident )*
					loop95:
					while (true) {
						int alt95=2;
						int LA95_0 = input.LA(1);
						if ( (LA95_0==177) ) {
							alt95=1;
						}

						switch (alt95) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:769:56: ',' kn= cident
							{
							match(input,177,FOLLOW_177_in_createMaterializedViewStatement4579); 
							pushFollow(FOLLOW_cident_in_createMaterializedViewStatement4583);
							kn=cident();
							state._fsp--;

							 partitionKeys.add(kn); 
							}
							break;

						default :
							break loop95;
						}
					}

					match(input,175,FOLLOW_175_in_createMaterializedViewStatement4590); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:769:104: ( ',' c1= cident )*
					loop96:
					while (true) {
						int alt96=2;
						int LA96_0 = input.LA(1);
						if ( (LA96_0==177) ) {
							alt96=1;
						}

						switch (alt96) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:769:106: ',' c1= cident
							{
							match(input,177,FOLLOW_177_in_createMaterializedViewStatement4594); 
							pushFollow(FOLLOW_cident_in_createMaterializedViewStatement4598);
							c1=cident();
							state._fsp--;

							 compositeKeys.add(c1); 
							}
							break;

						default :
							break loop96;
						}
					}

					match(input,175,FOLLOW_175_in_createMaterializedViewStatement4605); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:770:9: '(' k1= cident ( ',' cn= cident )* ')'
					{
					match(input,174,FOLLOW_174_in_createMaterializedViewStatement4615); 
					pushFollow(FOLLOW_cident_in_createMaterializedViewStatement4619);
					k1=cident();
					state._fsp--;

					 partitionKeys.add(k1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:770:50: ( ',' cn= cident )*
					loop97:
					while (true) {
						int alt97=2;
						int LA97_0 = input.LA(1);
						if ( (LA97_0==177) ) {
							alt97=1;
						}

						switch (alt97) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:770:52: ',' cn= cident
							{
							match(input,177,FOLLOW_177_in_createMaterializedViewStatement4625); 
							pushFollow(FOLLOW_cident_in_createMaterializedViewStatement4629);
							cn=cident();
							state._fsp--;

							 compositeKeys.add(cn); 
							}
							break;

						default :
							break loop97;
						}
					}

					match(input,175,FOLLOW_175_in_createMaterializedViewStatement4636); 
					}
					break;

			}


			             WhereClause where = wclause == null ? WhereClause.empty() : wclause.build();
			             expr = new CreateViewStatement(cf, basecf, sclause, where, partitionKeys, compositeKeys, ifNotExists);
			        
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:776:9: ( K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )* )?
			int alt100=2;
			int LA100_0 = input.LA(1);
			if ( (LA100_0==K_WITH) ) {
				alt100=1;
			}
			switch (alt100) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:776:11: K_WITH cfamProperty[expr.properties] ( K_AND cfamProperty[expr.properties] )*
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_createMaterializedViewStatement4668); 
					pushFollow(FOLLOW_cfamProperty_in_createMaterializedViewStatement4670);
					cfamProperty(expr.properties);
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:776:48: ( K_AND cfamProperty[expr.properties] )*
					loop99:
					while (true) {
						int alt99=2;
						int LA99_0 = input.LA(1);
						if ( (LA99_0==K_AND) ) {
							alt99=1;
						}

						switch (alt99) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:776:50: K_AND cfamProperty[expr.properties]
							{
							match(input,K_AND,FOLLOW_K_AND_in_createMaterializedViewStatement4675); 
							pushFollow(FOLLOW_cfamProperty_in_createMaterializedViewStatement4677);
							cfamProperty(expr.properties);
							state._fsp--;

							}
							break;

						default :
							break loop99;
						}
					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createMaterializedViewStatement"



	// $ANTLR start "createTriggerStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:782:1: createTriggerStatement returns [CreateTriggerStatement expr] : K_CREATE K_TRIGGER ( K_IF K_NOT K_EXISTS )? (name= cident ) K_ON cf= columnFamilyName K_USING cls= STRING_LITERAL ;
	public final CreateTriggerStatement createTriggerStatement() throws RecognitionException {
		CreateTriggerStatement expr = null;


		Token cls=null;
		ColumnIdentifier.Raw name =null;
		CFName cf =null;


		        boolean ifNotExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:786:5: ( K_CREATE K_TRIGGER ( K_IF K_NOT K_EXISTS )? (name= cident ) K_ON cf= columnFamilyName K_USING cls= STRING_LITERAL )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:786:7: K_CREATE K_TRIGGER ( K_IF K_NOT K_EXISTS )? (name= cident ) K_ON cf= columnFamilyName K_USING cls= STRING_LITERAL
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createTriggerStatement4715); 
			match(input,K_TRIGGER,FOLLOW_K_TRIGGER_in_createTriggerStatement4717); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:786:26: ( K_IF K_NOT K_EXISTS )?
			int alt101=2;
			int LA101_0 = input.LA(1);
			if ( (LA101_0==K_IF) ) {
				alt101=1;
			}
			switch (alt101) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:786:27: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createTriggerStatement4720); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createTriggerStatement4722); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createTriggerStatement4724); 
					 ifNotExists = true; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:786:74: (name= cident )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:786:75: name= cident
			{
			pushFollow(FOLLOW_cident_in_createTriggerStatement4734);
			name=cident();
			state._fsp--;

			}

			match(input,K_ON,FOLLOW_K_ON_in_createTriggerStatement4745); 
			pushFollow(FOLLOW_columnFamilyName_in_createTriggerStatement4749);
			cf=columnFamilyName();
			state._fsp--;

			match(input,K_USING,FOLLOW_K_USING_in_createTriggerStatement4751); 
			cls=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_createTriggerStatement4755); 
			 expr = new CreateTriggerStatement(cf, name.toString(), (cls!=null?cls.getText():null), ifNotExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "createTriggerStatement"



	// $ANTLR start "dropTriggerStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:794:1: dropTriggerStatement returns [DropTriggerStatement expr] : K_DROP K_TRIGGER ( K_IF K_EXISTS )? (name= cident ) K_ON cf= columnFamilyName ;
	public final DropTriggerStatement dropTriggerStatement() throws RecognitionException {
		DropTriggerStatement expr = null;


		ColumnIdentifier.Raw name =null;
		CFName cf =null;

		 boolean ifExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:796:5: ( K_DROP K_TRIGGER ( K_IF K_EXISTS )? (name= cident ) K_ON cf= columnFamilyName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:796:7: K_DROP K_TRIGGER ( K_IF K_EXISTS )? (name= cident ) K_ON cf= columnFamilyName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropTriggerStatement4796); 
			match(input,K_TRIGGER,FOLLOW_K_TRIGGER_in_dropTriggerStatement4798); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:796:24: ( K_IF K_EXISTS )?
			int alt102=2;
			int LA102_0 = input.LA(1);
			if ( (LA102_0==K_IF) ) {
				alt102=1;
			}
			switch (alt102) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:796:25: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropTriggerStatement4801); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropTriggerStatement4803); 
					 ifExists = true; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:796:63: (name= cident )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:796:64: name= cident
			{
			pushFollow(FOLLOW_cident_in_dropTriggerStatement4813);
			name=cident();
			state._fsp--;

			}

			match(input,K_ON,FOLLOW_K_ON_in_dropTriggerStatement4816); 
			pushFollow(FOLLOW_columnFamilyName_in_dropTriggerStatement4820);
			cf=columnFamilyName();
			state._fsp--;

			 expr = new DropTriggerStatement(cf, name.toString(), ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "dropTriggerStatement"



	// $ANTLR start "alterKeyspaceStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:803:1: alterKeyspaceStatement returns [AlterKeyspaceStatement expr] : K_ALTER K_KEYSPACE ks= keyspaceName K_WITH properties[attrs] ;
	public final AlterKeyspaceStatement alterKeyspaceStatement() throws RecognitionException {
		AlterKeyspaceStatement expr = null;


		String ks =null;

		 KeyspaceAttributes attrs = new KeyspaceAttributes(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:805:5: ( K_ALTER K_KEYSPACE ks= keyspaceName K_WITH properties[attrs] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:805:7: K_ALTER K_KEYSPACE ks= keyspaceName K_WITH properties[attrs]
			{
			match(input,K_ALTER,FOLLOW_K_ALTER_in_alterKeyspaceStatement4860); 
			match(input,K_KEYSPACE,FOLLOW_K_KEYSPACE_in_alterKeyspaceStatement4862); 
			pushFollow(FOLLOW_keyspaceName_in_alterKeyspaceStatement4866);
			ks=keyspaceName();
			state._fsp--;

			match(input,K_WITH,FOLLOW_K_WITH_in_alterKeyspaceStatement4876); 
			pushFollow(FOLLOW_properties_in_alterKeyspaceStatement4878);
			properties(attrs);
			state._fsp--;

			 expr = new AlterKeyspaceStatement(ks, attrs); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "alterKeyspaceStatement"



	// $ANTLR start "alterTableStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:817:1: alterTableStatement returns [AlterTableStatement expr] : K_ALTER K_COLUMNFAMILY cf= columnFamilyName ( K_ALTER id= cident K_TYPE v= comparatorType | K_ADD id= cident v= comparatorType ( K_STATIC )? | K_DROP id= cident | K_DROP id= cident K_USING K_TIMESTAMP t= INTEGER | K_WITH properties[attrs] | K_RENAME id1= cident K_TO toId1= cident ( K_AND idn= cident K_TO toIdn= cident )* ) ;
	public final AlterTableStatement alterTableStatement() throws RecognitionException {
		AlterTableStatement expr = null;


		Token t=null;
		CFName cf =null;
		ColumnIdentifier.Raw id =null;
		CQL3Type.Raw v =null;
		ColumnIdentifier.Raw id1 =null;
		ColumnIdentifier.Raw toId1 =null;
		ColumnIdentifier.Raw idn =null;
		ColumnIdentifier.Raw toIdn =null;


		        AlterTableStatement.Type type = null;
		        TableAttributes attrs = new TableAttributes();
		        Map<ColumnIdentifier.Raw, ColumnIdentifier.Raw> renames = new HashMap<ColumnIdentifier.Raw, ColumnIdentifier.Raw>();
		        boolean isStatic = false;
		        Long dropTimestamp = null;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:825:5: ( K_ALTER K_COLUMNFAMILY cf= columnFamilyName ( K_ALTER id= cident K_TYPE v= comparatorType | K_ADD id= cident v= comparatorType ( K_STATIC )? | K_DROP id= cident | K_DROP id= cident K_USING K_TIMESTAMP t= INTEGER | K_WITH properties[attrs] | K_RENAME id1= cident K_TO toId1= cident ( K_AND idn= cident K_TO toIdn= cident )* ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:825:7: K_ALTER K_COLUMNFAMILY cf= columnFamilyName ( K_ALTER id= cident K_TYPE v= comparatorType | K_ADD id= cident v= comparatorType ( K_STATIC )? | K_DROP id= cident | K_DROP id= cident K_USING K_TIMESTAMP t= INTEGER | K_WITH properties[attrs] | K_RENAME id1= cident K_TO toId1= cident ( K_AND idn= cident K_TO toIdn= cident )* )
			{
			match(input,K_ALTER,FOLLOW_K_ALTER_in_alterTableStatement4914); 
			match(input,K_COLUMNFAMILY,FOLLOW_K_COLUMNFAMILY_in_alterTableStatement4916); 
			pushFollow(FOLLOW_columnFamilyName_in_alterTableStatement4920);
			cf=columnFamilyName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:826:11: ( K_ALTER id= cident K_TYPE v= comparatorType | K_ADD id= cident v= comparatorType ( K_STATIC )? | K_DROP id= cident | K_DROP id= cident K_USING K_TIMESTAMP t= INTEGER | K_WITH properties[attrs] | K_RENAME id1= cident K_TO toId1= cident ( K_AND idn= cident K_TO toIdn= cident )* )
			int alt105=6;
			alt105 = dfa105.predict(input);
			switch (alt105) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:826:13: K_ALTER id= cident K_TYPE v= comparatorType
					{
					match(input,K_ALTER,FOLLOW_K_ALTER_in_alterTableStatement4934); 
					pushFollow(FOLLOW_cident_in_alterTableStatement4938);
					id=cident();
					state._fsp--;

					match(input,K_TYPE,FOLLOW_K_TYPE_in_alterTableStatement4940); 
					pushFollow(FOLLOW_comparatorType_in_alterTableStatement4944);
					v=comparatorType();
					state._fsp--;

					 type = AlterTableStatement.Type.ALTER; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:827:13: K_ADD id= cident v= comparatorType ( K_STATIC )?
					{
					match(input,K_ADD,FOLLOW_K_ADD_in_alterTableStatement4960); 
					pushFollow(FOLLOW_cident_in_alterTableStatement4966);
					id=cident();
					state._fsp--;

					pushFollow(FOLLOW_comparatorType_in_alterTableStatement4970);
					v=comparatorType();
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:827:48: ( K_STATIC )?
					int alt103=2;
					int LA103_0 = input.LA(1);
					if ( (LA103_0==K_STATIC) ) {
						alt103=1;
					}
					switch (alt103) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:827:49: K_STATIC
							{
							 isStatic=true; 
							match(input,K_STATIC,FOLLOW_K_STATIC_in_alterTableStatement4975); 
							}
							break;

					}

					 type = AlterTableStatement.Type.ADD; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:828:13: K_DROP id= cident
					{
					match(input,K_DROP,FOLLOW_K_DROP_in_alterTableStatement4993); 
					pushFollow(FOLLOW_cident_in_alterTableStatement4998);
					id=cident();
					state._fsp--;

					 type = AlterTableStatement.Type.DROP; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:829:13: K_DROP id= cident K_USING K_TIMESTAMP t= INTEGER
					{
					match(input,K_DROP,FOLLOW_K_DROP_in_alterTableStatement5044); 
					pushFollow(FOLLOW_cident_in_alterTableStatement5049);
					id=cident();
					state._fsp--;

					match(input,K_USING,FOLLOW_K_USING_in_alterTableStatement5051); 
					match(input,K_TIMESTAMP,FOLLOW_K_TIMESTAMP_in_alterTableStatement5053); 
					t=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_alterTableStatement5057); 
					 type = AlterTableStatement.Type.DROP;
					                                                              dropTimestamp = Long.parseLong(Constants.Literal.integer((t!=null?t.getText():null)).getText()); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:831:13: K_WITH properties[attrs]
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_alterTableStatement5073); 
					pushFollow(FOLLOW_properties_in_alterTableStatement5076);
					properties(attrs);
					state._fsp--;

					 type = AlterTableStatement.Type.OPTS; 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:832:13: K_RENAME id1= cident K_TO toId1= cident ( K_AND idn= cident K_TO toIdn= cident )*
					{
					match(input,K_RENAME,FOLLOW_K_RENAME_in_alterTableStatement5115); 
					 type = AlterTableStatement.Type.RENAME; 
					pushFollow(FOLLOW_cident_in_alterTableStatement5175);
					id1=cident();
					state._fsp--;

					match(input,K_TO,FOLLOW_K_TO_in_alterTableStatement5177); 
					pushFollow(FOLLOW_cident_in_alterTableStatement5181);
					toId1=cident();
					state._fsp--;

					 renames.put(id1, toId1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:834:16: ( K_AND idn= cident K_TO toIdn= cident )*
					loop104:
					while (true) {
						int alt104=2;
						int LA104_0 = input.LA(1);
						if ( (LA104_0==K_AND) ) {
							alt104=1;
						}

						switch (alt104) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:834:18: K_AND idn= cident K_TO toIdn= cident
							{
							match(input,K_AND,FOLLOW_K_AND_in_alterTableStatement5202); 
							pushFollow(FOLLOW_cident_in_alterTableStatement5206);
							idn=cident();
							state._fsp--;

							match(input,K_TO,FOLLOW_K_TO_in_alterTableStatement5208); 
							pushFollow(FOLLOW_cident_in_alterTableStatement5212);
							toIdn=cident();
							state._fsp--;

							 renames.put(idn, toIdn); 
							}
							break;

						default :
							break loop104;
						}
					}

					}
					break;

			}


			        expr = new AlterTableStatement(cf, type, id, v, attrs, renames, isStatic, dropTimestamp);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "alterTableStatement"



	// $ANTLR start "alterMaterializedViewStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:841:1: alterMaterializedViewStatement returns [AlterViewStatement expr] : K_ALTER K_MATERIALIZED K_VIEW name= columnFamilyName K_WITH properties[attrs] ;
	public final AlterViewStatement alterMaterializedViewStatement() throws RecognitionException {
		AlterViewStatement expr = null;


		CFName name =null;


		        TableAttributes attrs = new TableAttributes();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:845:5: ( K_ALTER K_MATERIALIZED K_VIEW name= columnFamilyName K_WITH properties[attrs] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:845:7: K_ALTER K_MATERIALIZED K_VIEW name= columnFamilyName K_WITH properties[attrs]
			{
			match(input,K_ALTER,FOLLOW_K_ALTER_in_alterMaterializedViewStatement5265); 
			match(input,K_MATERIALIZED,FOLLOW_K_MATERIALIZED_in_alterMaterializedViewStatement5267); 
			match(input,K_VIEW,FOLLOW_K_VIEW_in_alterMaterializedViewStatement5269); 
			pushFollow(FOLLOW_columnFamilyName_in_alterMaterializedViewStatement5273);
			name=columnFamilyName();
			state._fsp--;

			match(input,K_WITH,FOLLOW_K_WITH_in_alterMaterializedViewStatement5285); 
			pushFollow(FOLLOW_properties_in_alterMaterializedViewStatement5287);
			properties(attrs);
			state._fsp--;


			        expr = new AlterViewStatement(name, attrs);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "alterMaterializedViewStatement"



	// $ANTLR start "alterTypeStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:858:1: alterTypeStatement returns [AlterTypeStatement expr] : K_ALTER K_TYPE name= userTypeName ( K_ALTER f= noncol_ident K_TYPE v= comparatorType | K_ADD f= noncol_ident v= comparatorType | K_RENAME id1= noncol_ident K_TO toId1= noncol_ident ( K_AND idn= noncol_ident K_TO toIdn= noncol_ident )* ) ;
	public final AlterTypeStatement alterTypeStatement() throws RecognitionException {
		AlterTypeStatement expr = null;


		UTName name =null;
		ColumnIdentifier f =null;
		CQL3Type.Raw v =null;
		ColumnIdentifier id1 =null;
		ColumnIdentifier toId1 =null;
		ColumnIdentifier idn =null;
		ColumnIdentifier toIdn =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:859:5: ( K_ALTER K_TYPE name= userTypeName ( K_ALTER f= noncol_ident K_TYPE v= comparatorType | K_ADD f= noncol_ident v= comparatorType | K_RENAME id1= noncol_ident K_TO toId1= noncol_ident ( K_AND idn= noncol_ident K_TO toIdn= noncol_ident )* ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:859:7: K_ALTER K_TYPE name= userTypeName ( K_ALTER f= noncol_ident K_TYPE v= comparatorType | K_ADD f= noncol_ident v= comparatorType | K_RENAME id1= noncol_ident K_TO toId1= noncol_ident ( K_AND idn= noncol_ident K_TO toIdn= noncol_ident )* )
			{
			match(input,K_ALTER,FOLLOW_K_ALTER_in_alterTypeStatement5322); 
			match(input,K_TYPE,FOLLOW_K_TYPE_in_alterTypeStatement5324); 
			pushFollow(FOLLOW_userTypeName_in_alterTypeStatement5328);
			name=userTypeName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:860:11: ( K_ALTER f= noncol_ident K_TYPE v= comparatorType | K_ADD f= noncol_ident v= comparatorType | K_RENAME id1= noncol_ident K_TO toId1= noncol_ident ( K_AND idn= noncol_ident K_TO toIdn= noncol_ident )* )
			int alt107=3;
			switch ( input.LA(1) ) {
			case K_ALTER:
				{
				alt107=1;
				}
				break;
			case K_ADD:
				{
				alt107=2;
				}
				break;
			case K_RENAME:
				{
				alt107=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 107, 0, input);
				throw nvae;
			}
			switch (alt107) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:860:13: K_ALTER f= noncol_ident K_TYPE v= comparatorType
					{
					match(input,K_ALTER,FOLLOW_K_ALTER_in_alterTypeStatement5342); 
					pushFollow(FOLLOW_noncol_ident_in_alterTypeStatement5346);
					f=noncol_ident();
					state._fsp--;

					match(input,K_TYPE,FOLLOW_K_TYPE_in_alterTypeStatement5348); 
					pushFollow(FOLLOW_comparatorType_in_alterTypeStatement5352);
					v=comparatorType();
					state._fsp--;

					 expr = AlterTypeStatement.alter(name, f, v); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:861:13: K_ADD f= noncol_ident v= comparatorType
					{
					match(input,K_ADD,FOLLOW_K_ADD_in_alterTypeStatement5368); 
					pushFollow(FOLLOW_noncol_ident_in_alterTypeStatement5374);
					f=noncol_ident();
					state._fsp--;

					pushFollow(FOLLOW_comparatorType_in_alterTypeStatement5378);
					v=comparatorType();
					state._fsp--;

					 expr = AlterTypeStatement.addition(name, f, v); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:862:13: K_RENAME id1= noncol_ident K_TO toId1= noncol_ident ( K_AND idn= noncol_ident K_TO toIdn= noncol_ident )*
					{
					match(input,K_RENAME,FOLLOW_K_RENAME_in_alterTypeStatement5401); 
					 Map<ColumnIdentifier, ColumnIdentifier> renames = new HashMap<ColumnIdentifier, ColumnIdentifier>(); 
					pushFollow(FOLLOW_noncol_ident_in_alterTypeStatement5439);
					id1=noncol_ident();
					state._fsp--;

					match(input,K_TO,FOLLOW_K_TO_in_alterTypeStatement5441); 
					pushFollow(FOLLOW_noncol_ident_in_alterTypeStatement5445);
					toId1=noncol_ident();
					state._fsp--;

					 renames.put(id1, toId1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:865:18: ( K_AND idn= noncol_ident K_TO toIdn= noncol_ident )*
					loop106:
					while (true) {
						int alt106=2;
						int LA106_0 = input.LA(1);
						if ( (LA106_0==K_AND) ) {
							alt106=1;
						}

						switch (alt106) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:865:20: K_AND idn= noncol_ident K_TO toIdn= noncol_ident
							{
							match(input,K_AND,FOLLOW_K_AND_in_alterTypeStatement5468); 
							pushFollow(FOLLOW_noncol_ident_in_alterTypeStatement5472);
							idn=noncol_ident();
							state._fsp--;

							match(input,K_TO,FOLLOW_K_TO_in_alterTypeStatement5474); 
							pushFollow(FOLLOW_noncol_ident_in_alterTypeStatement5478);
							toIdn=noncol_ident();
							state._fsp--;

							 renames.put(idn, toIdn); 
							}
							break;

						default :
							break loop106;
						}
					}

					 expr = AlterTypeStatement.renames(name, renames); 
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "alterTypeStatement"



	// $ANTLR start "dropKeyspaceStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:874:1: dropKeyspaceStatement returns [DropKeyspaceStatement ksp] : K_DROP K_KEYSPACE ( K_IF K_EXISTS )? ks= keyspaceName ;
	public final DropKeyspaceStatement dropKeyspaceStatement() throws RecognitionException {
		DropKeyspaceStatement ksp = null;


		String ks =null;

		 boolean ifExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:876:5: ( K_DROP K_KEYSPACE ( K_IF K_EXISTS )? ks= keyspaceName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:876:7: K_DROP K_KEYSPACE ( K_IF K_EXISTS )? ks= keyspaceName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropKeyspaceStatement5545); 
			match(input,K_KEYSPACE,FOLLOW_K_KEYSPACE_in_dropKeyspaceStatement5547); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:876:25: ( K_IF K_EXISTS )?
			int alt108=2;
			int LA108_0 = input.LA(1);
			if ( (LA108_0==K_IF) ) {
				alt108=1;
			}
			switch (alt108) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:876:26: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropKeyspaceStatement5550); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropKeyspaceStatement5552); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_keyspaceName_in_dropKeyspaceStatement5561);
			ks=keyspaceName();
			state._fsp--;

			 ksp = new DropKeyspaceStatement(ks, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ksp;
	}
	// $ANTLR end "dropKeyspaceStatement"



	// $ANTLR start "dropTableStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:882:1: dropTableStatement returns [DropTableStatement stmt] : K_DROP K_COLUMNFAMILY ( K_IF K_EXISTS )? cf= columnFamilyName ;
	public final DropTableStatement dropTableStatement() throws RecognitionException {
		DropTableStatement stmt = null;


		CFName cf =null;

		 boolean ifExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:884:5: ( K_DROP K_COLUMNFAMILY ( K_IF K_EXISTS )? cf= columnFamilyName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:884:7: K_DROP K_COLUMNFAMILY ( K_IF K_EXISTS )? cf= columnFamilyName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropTableStatement5595); 
			match(input,K_COLUMNFAMILY,FOLLOW_K_COLUMNFAMILY_in_dropTableStatement5597); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:884:29: ( K_IF K_EXISTS )?
			int alt109=2;
			int LA109_0 = input.LA(1);
			if ( (LA109_0==K_IF) ) {
				alt109=1;
			}
			switch (alt109) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:884:30: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropTableStatement5600); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropTableStatement5602); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_columnFamilyName_in_dropTableStatement5611);
			cf=columnFamilyName();
			state._fsp--;

			 stmt = new DropTableStatement(cf, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "dropTableStatement"



	// $ANTLR start "dropTypeStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:890:1: dropTypeStatement returns [DropTypeStatement stmt] : K_DROP K_TYPE ( K_IF K_EXISTS )? name= userTypeName ;
	public final DropTypeStatement dropTypeStatement() throws RecognitionException {
		DropTypeStatement stmt = null;


		UTName name =null;

		 boolean ifExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:892:5: ( K_DROP K_TYPE ( K_IF K_EXISTS )? name= userTypeName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:892:7: K_DROP K_TYPE ( K_IF K_EXISTS )? name= userTypeName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropTypeStatement5645); 
			match(input,K_TYPE,FOLLOW_K_TYPE_in_dropTypeStatement5647); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:892:21: ( K_IF K_EXISTS )?
			int alt110=2;
			int LA110_0 = input.LA(1);
			if ( (LA110_0==K_IF) ) {
				alt110=1;
			}
			switch (alt110) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:892:22: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropTypeStatement5650); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropTypeStatement5652); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_userTypeName_in_dropTypeStatement5661);
			name=userTypeName();
			state._fsp--;

			 stmt = new DropTypeStatement(name, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "dropTypeStatement"



	// $ANTLR start "dropIndexStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:898:1: dropIndexStatement returns [DropIndexStatement expr] : K_DROP K_INDEX ( K_IF K_EXISTS )? index= indexName ;
	public final DropIndexStatement dropIndexStatement() throws RecognitionException {
		DropIndexStatement expr = null;


		IndexName index =null;

		 boolean ifExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:900:5: ( K_DROP K_INDEX ( K_IF K_EXISTS )? index= indexName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:900:7: K_DROP K_INDEX ( K_IF K_EXISTS )? index= indexName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropIndexStatement5695); 
			match(input,K_INDEX,FOLLOW_K_INDEX_in_dropIndexStatement5697); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:900:22: ( K_IF K_EXISTS )?
			int alt111=2;
			int LA111_0 = input.LA(1);
			if ( (LA111_0==K_IF) ) {
				alt111=1;
			}
			switch (alt111) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:900:23: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropIndexStatement5700); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropIndexStatement5702); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_indexName_in_dropIndexStatement5711);
			index=indexName();
			state._fsp--;

			 expr = new DropIndexStatement(index, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "dropIndexStatement"



	// $ANTLR start "dropMaterializedViewStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:907:1: dropMaterializedViewStatement returns [DropViewStatement expr] : K_DROP K_MATERIALIZED K_VIEW ( K_IF K_EXISTS )? cf= columnFamilyName ;
	public final DropViewStatement dropMaterializedViewStatement() throws RecognitionException {
		DropViewStatement expr = null;


		CFName cf =null;

		 boolean ifExists = false; 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:909:5: ( K_DROP K_MATERIALIZED K_VIEW ( K_IF K_EXISTS )? cf= columnFamilyName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:909:7: K_DROP K_MATERIALIZED K_VIEW ( K_IF K_EXISTS )? cf= columnFamilyName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropMaterializedViewStatement5751); 
			match(input,K_MATERIALIZED,FOLLOW_K_MATERIALIZED_in_dropMaterializedViewStatement5753); 
			match(input,K_VIEW,FOLLOW_K_VIEW_in_dropMaterializedViewStatement5755); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:909:36: ( K_IF K_EXISTS )?
			int alt112=2;
			int LA112_0 = input.LA(1);
			if ( (LA112_0==K_IF) ) {
				alt112=1;
			}
			switch (alt112) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:909:37: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropMaterializedViewStatement5758); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropMaterializedViewStatement5760); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_columnFamilyName_in_dropMaterializedViewStatement5769);
			cf=columnFamilyName();
			state._fsp--;

			 expr = new DropViewStatement(cf, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return expr;
	}
	// $ANTLR end "dropMaterializedViewStatement"



	// $ANTLR start "truncateStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:916:1: truncateStatement returns [TruncateStatement stmt] : K_TRUNCATE ( K_COLUMNFAMILY )? cf= columnFamilyName ;
	public final TruncateStatement truncateStatement() throws RecognitionException {
		TruncateStatement stmt = null;


		CFName cf =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:917:5: ( K_TRUNCATE ( K_COLUMNFAMILY )? cf= columnFamilyName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:917:7: K_TRUNCATE ( K_COLUMNFAMILY )? cf= columnFamilyName
			{
			match(input,K_TRUNCATE,FOLLOW_K_TRUNCATE_in_truncateStatement5800); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:917:18: ( K_COLUMNFAMILY )?
			int alt113=2;
			int LA113_0 = input.LA(1);
			if ( (LA113_0==K_COLUMNFAMILY) ) {
				alt113=1;
			}
			switch (alt113) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:917:19: K_COLUMNFAMILY
					{
					match(input,K_COLUMNFAMILY,FOLLOW_K_COLUMNFAMILY_in_truncateStatement5803); 
					}
					break;

			}

			pushFollow(FOLLOW_columnFamilyName_in_truncateStatement5809);
			cf=columnFamilyName();
			state._fsp--;

			 stmt = new TruncateStatement(cf); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "truncateStatement"



	// $ANTLR start "grantPermissionsStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:923:1: grantPermissionsStatement returns [GrantPermissionsStatement stmt] : K_GRANT permissionOrAll K_ON resource K_TO grantee= userOrRoleName ;
	public final GrantPermissionsStatement grantPermissionsStatement() throws RecognitionException {
		GrantPermissionsStatement stmt = null;


		RoleName grantee =null;
		Set<Permission> permissionOrAll1 =null;
		IResource resource2 =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:924:5: ( K_GRANT permissionOrAll K_ON resource K_TO grantee= userOrRoleName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:924:7: K_GRANT permissionOrAll K_ON resource K_TO grantee= userOrRoleName
			{
			match(input,K_GRANT,FOLLOW_K_GRANT_in_grantPermissionsStatement5834); 
			pushFollow(FOLLOW_permissionOrAll_in_grantPermissionsStatement5846);
			permissionOrAll1=permissionOrAll();
			state._fsp--;

			match(input,K_ON,FOLLOW_K_ON_in_grantPermissionsStatement5854); 
			pushFollow(FOLLOW_resource_in_grantPermissionsStatement5866);
			resource2=resource();
			state._fsp--;

			match(input,K_TO,FOLLOW_K_TO_in_grantPermissionsStatement5874); 
			pushFollow(FOLLOW_userOrRoleName_in_grantPermissionsStatement5888);
			grantee=userOrRoleName();
			state._fsp--;

			 stmt = new GrantPermissionsStatement(filterPermissions(permissionOrAll1, resource2), resource2, grantee); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "grantPermissionsStatement"



	// $ANTLR start "revokePermissionsStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:936:1: revokePermissionsStatement returns [RevokePermissionsStatement stmt] : K_REVOKE permissionOrAll K_ON resource K_FROM revokee= userOrRoleName ;
	public final RevokePermissionsStatement revokePermissionsStatement() throws RecognitionException {
		RevokePermissionsStatement stmt = null;


		RoleName revokee =null;
		Set<Permission> permissionOrAll3 =null;
		IResource resource4 =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:937:5: ( K_REVOKE permissionOrAll K_ON resource K_FROM revokee= userOrRoleName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:937:7: K_REVOKE permissionOrAll K_ON resource K_FROM revokee= userOrRoleName
			{
			match(input,K_REVOKE,FOLLOW_K_REVOKE_in_revokePermissionsStatement5919); 
			pushFollow(FOLLOW_permissionOrAll_in_revokePermissionsStatement5931);
			permissionOrAll3=permissionOrAll();
			state._fsp--;

			match(input,K_ON,FOLLOW_K_ON_in_revokePermissionsStatement5939); 
			pushFollow(FOLLOW_resource_in_revokePermissionsStatement5951);
			resource4=resource();
			state._fsp--;

			match(input,K_FROM,FOLLOW_K_FROM_in_revokePermissionsStatement5959); 
			pushFollow(FOLLOW_userOrRoleName_in_revokePermissionsStatement5973);
			revokee=userOrRoleName();
			state._fsp--;

			 stmt = new RevokePermissionsStatement(filterPermissions(permissionOrAll3, resource4), resource4, revokee); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "revokePermissionsStatement"



	// $ANTLR start "grantRoleStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:949:1: grantRoleStatement returns [GrantRoleStatement stmt] : K_GRANT role= userOrRoleName K_TO grantee= userOrRoleName ;
	public final GrantRoleStatement grantRoleStatement() throws RecognitionException {
		GrantRoleStatement stmt = null;


		RoleName role =null;
		RoleName grantee =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:950:5: ( K_GRANT role= userOrRoleName K_TO grantee= userOrRoleName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:950:7: K_GRANT role= userOrRoleName K_TO grantee= userOrRoleName
			{
			match(input,K_GRANT,FOLLOW_K_GRANT_in_grantRoleStatement6004); 
			pushFollow(FOLLOW_userOrRoleName_in_grantRoleStatement6018);
			role=userOrRoleName();
			state._fsp--;

			match(input,K_TO,FOLLOW_K_TO_in_grantRoleStatement6026); 
			pushFollow(FOLLOW_userOrRoleName_in_grantRoleStatement6040);
			grantee=userOrRoleName();
			state._fsp--;

			 stmt = new GrantRoleStatement(role, grantee); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "grantRoleStatement"



	// $ANTLR start "revokeRoleStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:960:1: revokeRoleStatement returns [RevokeRoleStatement stmt] : K_REVOKE role= userOrRoleName K_FROM revokee= userOrRoleName ;
	public final RevokeRoleStatement revokeRoleStatement() throws RecognitionException {
		RevokeRoleStatement stmt = null;


		RoleName role =null;
		RoleName revokee =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:961:5: ( K_REVOKE role= userOrRoleName K_FROM revokee= userOrRoleName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:961:7: K_REVOKE role= userOrRoleName K_FROM revokee= userOrRoleName
			{
			match(input,K_REVOKE,FOLLOW_K_REVOKE_in_revokeRoleStatement6071); 
			pushFollow(FOLLOW_userOrRoleName_in_revokeRoleStatement6085);
			role=userOrRoleName();
			state._fsp--;

			match(input,K_FROM,FOLLOW_K_FROM_in_revokeRoleStatement6093); 
			pushFollow(FOLLOW_userOrRoleName_in_revokeRoleStatement6107);
			revokee=userOrRoleName();
			state._fsp--;

			 stmt = new RevokeRoleStatement(role, revokee); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "revokeRoleStatement"



	// $ANTLR start "listPermissionsStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:968:1: listPermissionsStatement returns [ListPermissionsStatement stmt] : K_LIST permissionOrAll ( K_ON resource )? ( K_OF roleName[grantee] )? ( K_NORECURSIVE )? ;
	public final ListPermissionsStatement listPermissionsStatement() throws RecognitionException {
		ListPermissionsStatement stmt = null;


		IResource resource5 =null;
		Set<Permission> permissionOrAll6 =null;


		        IResource resource = null;
		        boolean recursive = true;
		        RoleName grantee = new RoleName();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:974:5: ( K_LIST permissionOrAll ( K_ON resource )? ( K_OF roleName[grantee] )? ( K_NORECURSIVE )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:974:7: K_LIST permissionOrAll ( K_ON resource )? ( K_OF roleName[grantee] )? ( K_NORECURSIVE )?
			{
			match(input,K_LIST,FOLLOW_K_LIST_in_listPermissionsStatement6145); 
			pushFollow(FOLLOW_permissionOrAll_in_listPermissionsStatement6157);
			permissionOrAll6=permissionOrAll();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:976:7: ( K_ON resource )?
			int alt114=2;
			int LA114_0 = input.LA(1);
			if ( (LA114_0==K_ON) ) {
				alt114=1;
			}
			switch (alt114) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:976:9: K_ON resource
					{
					match(input,K_ON,FOLLOW_K_ON_in_listPermissionsStatement6167); 
					pushFollow(FOLLOW_resource_in_listPermissionsStatement6169);
					resource5=resource();
					state._fsp--;

					 resource = resource5; 
					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:977:7: ( K_OF roleName[grantee] )?
			int alt115=2;
			int LA115_0 = input.LA(1);
			if ( (LA115_0==K_OF) ) {
				alt115=1;
			}
			switch (alt115) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:977:9: K_OF roleName[grantee]
					{
					match(input,K_OF,FOLLOW_K_OF_in_listPermissionsStatement6184); 
					pushFollow(FOLLOW_roleName_in_listPermissionsStatement6186);
					roleName(grantee);
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:978:7: ( K_NORECURSIVE )?
			int alt116=2;
			int LA116_0 = input.LA(1);
			if ( (LA116_0==K_NORECURSIVE) ) {
				alt116=1;
			}
			switch (alt116) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:978:9: K_NORECURSIVE
					{
					match(input,K_NORECURSIVE,FOLLOW_K_NORECURSIVE_in_listPermissionsStatement6200); 
					 recursive = false; 
					}
					break;

			}

			 stmt = new ListPermissionsStatement(permissionOrAll6, resource, grantee, recursive); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "listPermissionsStatement"



	// $ANTLR start "permission"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:982:1: permission returns [Permission perm] : p= ( K_CREATE | K_ALTER | K_DROP | K_SELECT | K_MODIFY | K_AUTHORIZE | K_DESCRIBE | K_EXECUTE ) ;
	public final Permission permission() throws RecognitionException {
		Permission perm = null;


		Token p=null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:983:5: (p= ( K_CREATE | K_ALTER | K_DROP | K_SELECT | K_MODIFY | K_AUTHORIZE | K_DESCRIBE | K_EXECUTE ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:983:7: p= ( K_CREATE | K_ALTER | K_DROP | K_SELECT | K_MODIFY | K_AUTHORIZE | K_DESCRIBE | K_EXECUTE )
			{
			p=input.LT(1);
			if ( input.LA(1)==K_ALTER||input.LA(1)==K_AUTHORIZE||input.LA(1)==K_CREATE||input.LA(1)==K_DESCRIBE||input.LA(1)==K_DROP||input.LA(1)==K_EXECUTE||input.LA(1)==K_MODIFY||input.LA(1)==K_SELECT ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			 perm = Permission.valueOf((p!=null?p.getText():null).toUpperCase()); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return perm;
	}
	// $ANTLR end "permission"



	// $ANTLR start "permissionOrAll"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:987:1: permissionOrAll returns [Set<Permission> perms] : ( K_ALL ( K_PERMISSIONS )? |p= permission ( K_PERMISSION )? );
	public final Set<Permission> permissionOrAll() throws RecognitionException {
		Set<Permission> perms = null;


		Permission p =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:988:5: ( K_ALL ( K_PERMISSIONS )? |p= permission ( K_PERMISSION )? )
			int alt119=2;
			int LA119_0 = input.LA(1);
			if ( (LA119_0==K_ALL) ) {
				alt119=1;
			}
			else if ( (LA119_0==K_ALTER||LA119_0==K_AUTHORIZE||LA119_0==K_CREATE||LA119_0==K_DESCRIBE||LA119_0==K_DROP||LA119_0==K_EXECUTE||LA119_0==K_MODIFY||LA119_0==K_SELECT) ) {
				alt119=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 119, 0, input);
				throw nvae;
			}

			switch (alt119) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:988:7: K_ALL ( K_PERMISSIONS )?
					{
					match(input,K_ALL,FOLLOW_K_ALL_in_permissionOrAll6293); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:988:13: ( K_PERMISSIONS )?
					int alt117=2;
					int LA117_0 = input.LA(1);
					if ( (LA117_0==K_PERMISSIONS) ) {
						alt117=1;
					}
					switch (alt117) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:988:15: K_PERMISSIONS
							{
							match(input,K_PERMISSIONS,FOLLOW_K_PERMISSIONS_in_permissionOrAll6297); 
							}
							break;

					}

					 perms = Permission.ALL; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:989:7: p= permission ( K_PERMISSION )?
					{
					pushFollow(FOLLOW_permission_in_permissionOrAll6318);
					p=permission();
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:989:20: ( K_PERMISSION )?
					int alt118=2;
					int LA118_0 = input.LA(1);
					if ( (LA118_0==K_PERMISSION) ) {
						alt118=1;
					}
					switch (alt118) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:989:22: K_PERMISSION
							{
							match(input,K_PERMISSION,FOLLOW_K_PERMISSION_in_permissionOrAll6322); 
							}
							break;

					}

					 perms = EnumSet.of(p); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return perms;
	}
	// $ANTLR end "permissionOrAll"



	// $ANTLR start "resource"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:992:1: resource returns [IResource res] : (d= dataResource |r= roleResource |f= functionResource );
	public final IResource resource() throws RecognitionException {
		IResource res = null;


		DataResource d =null;
		RoleResource r =null;
		FunctionResource f =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:993:5: (d= dataResource |r= roleResource |f= functionResource )
			int alt120=3;
			switch ( input.LA(1) ) {
			case K_ALL:
				{
				switch ( input.LA(2) ) {
				case EOF:
				case K_FROM:
				case K_KEYSPACES:
				case K_NORECURSIVE:
				case K_OF:
				case K_TO:
				case 179:
				case 181:
					{
					alt120=1;
					}
					break;
				case K_ROLES:
					{
					alt120=2;
					}
					break;
				case K_FUNCTIONS:
					{
					alt120=3;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 120, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case IDENT:
			case K_AGGREGATE:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COLUMNFAMILY:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACE:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
			case QMARK:
			case QUOTED_NAME:
				{
				alt120=1;
				}
				break;
			case K_ROLE:
				{
				int LA120_3 = input.LA(2);
				if ( (LA120_3==EOF||LA120_3==K_FROM||LA120_3==K_NORECURSIVE||LA120_3==K_OF||LA120_3==K_TO||LA120_3==179||LA120_3==181) ) {
					alt120=1;
				}
				else if ( (LA120_3==IDENT||(LA120_3 >= K_AGGREGATE && LA120_3 <= K_ALL)||LA120_3==K_AS||LA120_3==K_ASCII||(LA120_3 >= K_BIGINT && LA120_3 <= K_BOOLEAN)||(LA120_3 >= K_CALLED && LA120_3 <= K_CLUSTERING)||(LA120_3 >= K_COMPACT && LA120_3 <= K_COUNTER)||(LA120_3 >= K_CUSTOM && LA120_3 <= K_DECIMAL)||(LA120_3 >= K_DISTINCT && LA120_3 <= K_DOUBLE)||(LA120_3 >= K_EXISTS && LA120_3 <= K_FLOAT)||LA120_3==K_FROZEN||(LA120_3 >= K_FUNCTION && LA120_3 <= K_FUNCTIONS)||LA120_3==K_INET||(LA120_3 >= K_INITCOND && LA120_3 <= K_INPUT)||LA120_3==K_INT||(LA120_3 >= K_JSON && LA120_3 <= K_KEYS)||(LA120_3 >= K_KEYSPACES && LA120_3 <= K_LIKE)||(LA120_3 >= K_LIST && LA120_3 <= K_MAP)||LA120_3==K_NOLOGIN||LA120_3==K_NOSUPERUSER||LA120_3==K_OPTIONS||(LA120_3 >= K_PARTITION && LA120_3 <= K_PERMISSIONS)||LA120_3==K_RETURNS||(LA120_3 >= K_ROLE && LA120_3 <= K_ROLES)||(LA120_3 >= K_SFUNC && LA120_3 <= K_TINYINT)||LA120_3==K_TRIGGER||(LA120_3 >= K_TTL && LA120_3 <= K_TYPE)||(LA120_3 >= K_USER && LA120_3 <= K_USERS)||(LA120_3 >= K_UUID && LA120_3 <= K_VARINT)||LA120_3==K_WRITETIME||(LA120_3 >= QMARK && LA120_3 <= QUOTED_NAME)||LA120_3==STRING_LITERAL) ) {
					alt120=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 120, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case K_FUNCTION:
				{
				int LA120_4 = input.LA(2);
				if ( (LA120_4==EOF||LA120_4==K_FROM||LA120_4==K_NORECURSIVE||LA120_4==K_OF||LA120_4==K_TO||LA120_4==179||LA120_4==181) ) {
					alt120=1;
				}
				else if ( (LA120_4==IDENT||(LA120_4 >= K_AGGREGATE && LA120_4 <= K_ALL)||LA120_4==K_AS||LA120_4==K_ASCII||(LA120_4 >= K_BIGINT && LA120_4 <= K_BOOLEAN)||(LA120_4 >= K_CALLED && LA120_4 <= K_CLUSTERING)||(LA120_4 >= K_COMPACT && LA120_4 <= K_COUNTER)||(LA120_4 >= K_CUSTOM && LA120_4 <= K_DECIMAL)||(LA120_4 >= K_DISTINCT && LA120_4 <= K_DOUBLE)||(LA120_4 >= K_EXISTS && LA120_4 <= K_FLOAT)||LA120_4==K_FROZEN||(LA120_4 >= K_FUNCTION && LA120_4 <= K_FUNCTIONS)||LA120_4==K_INET||(LA120_4 >= K_INITCOND && LA120_4 <= K_INPUT)||LA120_4==K_INT||(LA120_4 >= K_JSON && LA120_4 <= K_KEYS)||(LA120_4 >= K_KEYSPACES && LA120_4 <= K_LIKE)||(LA120_4 >= K_LIST && LA120_4 <= K_MAP)||LA120_4==K_NOLOGIN||LA120_4==K_NOSUPERUSER||LA120_4==K_OPTIONS||(LA120_4 >= K_PARTITION && LA120_4 <= K_PERMISSIONS)||LA120_4==K_RETURNS||(LA120_4 >= K_ROLE && LA120_4 <= K_ROLES)||(LA120_4 >= K_SFUNC && LA120_4 <= K_TINYINT)||(LA120_4 >= K_TOKEN && LA120_4 <= K_TRIGGER)||(LA120_4 >= K_TTL && LA120_4 <= K_TYPE)||(LA120_4 >= K_USER && LA120_4 <= K_USERS)||(LA120_4 >= K_UUID && LA120_4 <= K_VARINT)||LA120_4==K_WRITETIME||(LA120_4 >= QMARK && LA120_4 <= QUOTED_NAME)) ) {
					alt120=3;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 120, 4, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 120, 0, input);
				throw nvae;
			}
			switch (alt120) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:993:7: d= dataResource
					{
					pushFollow(FOLLOW_dataResource_in_resource6350);
					d=dataResource();
					state._fsp--;

					 res = d; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:994:7: r= roleResource
					{
					pushFollow(FOLLOW_roleResource_in_resource6362);
					r=roleResource();
					state._fsp--;

					 res = r; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:995:7: f= functionResource
					{
					pushFollow(FOLLOW_functionResource_in_resource6374);
					f=functionResource();
					state._fsp--;

					 res = f; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "resource"



	// $ANTLR start "dataResource"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:998:1: dataResource returns [DataResource res] : ( K_ALL K_KEYSPACES | K_KEYSPACE ks= keyspaceName | ( K_COLUMNFAMILY )? cf= columnFamilyName );
	public final DataResource dataResource() throws RecognitionException {
		DataResource res = null;


		String ks =null;
		CFName cf =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:999:5: ( K_ALL K_KEYSPACES | K_KEYSPACE ks= keyspaceName | ( K_COLUMNFAMILY )? cf= columnFamilyName )
			int alt122=3;
			switch ( input.LA(1) ) {
			case K_ALL:
				{
				int LA122_1 = input.LA(2);
				if ( (LA122_1==K_KEYSPACES) ) {
					alt122=1;
				}
				else if ( (LA122_1==EOF||LA122_1==K_FROM||LA122_1==K_NORECURSIVE||LA122_1==K_OF||LA122_1==K_TO||LA122_1==179||LA122_1==181) ) {
					alt122=3;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 122, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case K_KEYSPACE:
				{
				alt122=2;
				}
				break;
			case IDENT:
			case K_AGGREGATE:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COLUMNFAMILY:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
			case QMARK:
			case QUOTED_NAME:
				{
				alt122=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 122, 0, input);
				throw nvae;
			}
			switch (alt122) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:999:7: K_ALL K_KEYSPACES
					{
					match(input,K_ALL,FOLLOW_K_ALL_in_dataResource6397); 
					match(input,K_KEYSPACES,FOLLOW_K_KEYSPACES_in_dataResource6399); 
					 res = DataResource.root(); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1000:7: K_KEYSPACE ks= keyspaceName
					{
					match(input,K_KEYSPACE,FOLLOW_K_KEYSPACE_in_dataResource6409); 
					pushFollow(FOLLOW_keyspaceName_in_dataResource6415);
					ks=keyspaceName();
					state._fsp--;

					 res = DataResource.keyspace(ks); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1001:7: ( K_COLUMNFAMILY )? cf= columnFamilyName
					{
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1001:7: ( K_COLUMNFAMILY )?
					int alt121=2;
					int LA121_0 = input.LA(1);
					if ( (LA121_0==K_COLUMNFAMILY) ) {
						alt121=1;
					}
					switch (alt121) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1001:9: K_COLUMNFAMILY
							{
							match(input,K_COLUMNFAMILY,FOLLOW_K_COLUMNFAMILY_in_dataResource6427); 
							}
							break;

					}

					pushFollow(FOLLOW_columnFamilyName_in_dataResource6436);
					cf=columnFamilyName();
					state._fsp--;

					 res = DataResource.table(cf.getKeyspace(), cf.getColumnFamily()); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "dataResource"



	// $ANTLR start "roleResource"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1005:1: roleResource returns [RoleResource res] : ( K_ALL K_ROLES | K_ROLE role= userOrRoleName );
	public final RoleResource roleResource() throws RecognitionException {
		RoleResource res = null;


		RoleName role =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1006:5: ( K_ALL K_ROLES | K_ROLE role= userOrRoleName )
			int alt123=2;
			int LA123_0 = input.LA(1);
			if ( (LA123_0==K_ALL) ) {
				alt123=1;
			}
			else if ( (LA123_0==K_ROLE) ) {
				alt123=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 123, 0, input);
				throw nvae;
			}

			switch (alt123) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1006:7: K_ALL K_ROLES
					{
					match(input,K_ALL,FOLLOW_K_ALL_in_roleResource6465); 
					match(input,K_ROLES,FOLLOW_K_ROLES_in_roleResource6467); 
					 res = RoleResource.root(); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1007:7: K_ROLE role= userOrRoleName
					{
					match(input,K_ROLE,FOLLOW_K_ROLE_in_roleResource6477); 
					pushFollow(FOLLOW_userOrRoleName_in_roleResource6483);
					role=userOrRoleName();
					state._fsp--;

					 res = RoleResource.role(role.getName()); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "roleResource"



	// $ANTLR start "functionResource"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1010:1: functionResource returns [FunctionResource res] : ( K_ALL K_FUNCTIONS | K_ALL K_FUNCTIONS K_IN K_KEYSPACE ks= keyspaceName | K_FUNCTION fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' ) );
	public final FunctionResource functionResource() throws RecognitionException {
		FunctionResource res = null;


		String ks =null;
		FunctionName fn =null;
		CQL3Type.Raw v =null;


		        List<CQL3Type.Raw> argsTypes = new ArrayList<>();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1014:5: ( K_ALL K_FUNCTIONS | K_ALL K_FUNCTIONS K_IN K_KEYSPACE ks= keyspaceName | K_FUNCTION fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' ) )
			int alt126=3;
			int LA126_0 = input.LA(1);
			if ( (LA126_0==K_ALL) ) {
				int LA126_1 = input.LA(2);
				if ( (LA126_1==K_FUNCTIONS) ) {
					int LA126_3 = input.LA(3);
					if ( (LA126_3==K_IN) ) {
						alt126=2;
					}
					else if ( (LA126_3==EOF||LA126_3==K_FROM||LA126_3==K_NORECURSIVE||LA126_3==K_OF||LA126_3==K_TO||LA126_3==181) ) {
						alt126=1;
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 126, 3, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 126, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA126_0==K_FUNCTION) ) {
				alt126=3;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 126, 0, input);
				throw nvae;
			}

			switch (alt126) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1014:7: K_ALL K_FUNCTIONS
					{
					match(input,K_ALL,FOLLOW_K_ALL_in_functionResource6515); 
					match(input,K_FUNCTIONS,FOLLOW_K_FUNCTIONS_in_functionResource6517); 
					 res = FunctionResource.root(); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1015:7: K_ALL K_FUNCTIONS K_IN K_KEYSPACE ks= keyspaceName
					{
					match(input,K_ALL,FOLLOW_K_ALL_in_functionResource6527); 
					match(input,K_FUNCTIONS,FOLLOW_K_FUNCTIONS_in_functionResource6529); 
					match(input,K_IN,FOLLOW_K_IN_in_functionResource6531); 
					match(input,K_KEYSPACE,FOLLOW_K_KEYSPACE_in_functionResource6533); 
					pushFollow(FOLLOW_keyspaceName_in_functionResource6539);
					ks=keyspaceName();
					state._fsp--;

					 res = FunctionResource.keyspace(ks); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1017:7: K_FUNCTION fn= functionName ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )
					{
					match(input,K_FUNCTION,FOLLOW_K_FUNCTION_in_functionResource6554); 
					pushFollow(FOLLOW_functionName_in_functionResource6558);
					fn=functionName();
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1018:7: ( '(' (v= comparatorType ( ',' v= comparatorType )* )? ')' )
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1019:9: '(' (v= comparatorType ( ',' v= comparatorType )* )? ')'
					{
					match(input,174,FOLLOW_174_in_functionResource6576); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1020:11: (v= comparatorType ( ',' v= comparatorType )* )?
					int alt125=2;
					int LA125_0 = input.LA(1);
					if ( (LA125_0==IDENT||(LA125_0 >= K_AGGREGATE && LA125_0 <= K_ALL)||LA125_0==K_AS||LA125_0==K_ASCII||(LA125_0 >= K_BIGINT && LA125_0 <= K_BOOLEAN)||(LA125_0 >= K_CALLED && LA125_0 <= K_CLUSTERING)||(LA125_0 >= K_COMPACT && LA125_0 <= K_COUNTER)||(LA125_0 >= K_CUSTOM && LA125_0 <= K_DECIMAL)||(LA125_0 >= K_DISTINCT && LA125_0 <= K_DOUBLE)||(LA125_0 >= K_EXISTS && LA125_0 <= K_FLOAT)||LA125_0==K_FROZEN||(LA125_0 >= K_FUNCTION && LA125_0 <= K_FUNCTIONS)||LA125_0==K_INET||(LA125_0 >= K_INITCOND && LA125_0 <= K_INPUT)||LA125_0==K_INT||(LA125_0 >= K_JSON && LA125_0 <= K_KEYS)||(LA125_0 >= K_KEYSPACES && LA125_0 <= K_LIKE)||(LA125_0 >= K_LIST && LA125_0 <= K_MAP)||LA125_0==K_NOLOGIN||LA125_0==K_NOSUPERUSER||LA125_0==K_OPTIONS||(LA125_0 >= K_PARTITION && LA125_0 <= K_PERMISSIONS)||LA125_0==K_RETURNS||(LA125_0 >= K_ROLE && LA125_0 <= K_ROLES)||(LA125_0 >= K_SET && LA125_0 <= K_TINYINT)||LA125_0==K_TRIGGER||(LA125_0 >= K_TTL && LA125_0 <= K_TYPE)||(LA125_0 >= K_USER && LA125_0 <= K_USERS)||(LA125_0 >= K_UUID && LA125_0 <= K_VARINT)||LA125_0==K_WRITETIME||LA125_0==QUOTED_NAME||LA125_0==STRING_LITERAL) ) {
						alt125=1;
					}
					switch (alt125) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1021:13: v= comparatorType ( ',' v= comparatorType )*
							{
							pushFollow(FOLLOW_comparatorType_in_functionResource6604);
							v=comparatorType();
							state._fsp--;

							 argsTypes.add(v); 
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1022:13: ( ',' v= comparatorType )*
							loop124:
							while (true) {
								int alt124=2;
								int LA124_0 = input.LA(1);
								if ( (LA124_0==177) ) {
									alt124=1;
								}

								switch (alt124) {
								case 1 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1022:15: ',' v= comparatorType
									{
									match(input,177,FOLLOW_177_in_functionResource6622); 
									pushFollow(FOLLOW_comparatorType_in_functionResource6626);
									v=comparatorType();
									state._fsp--;

									 argsTypes.add(v); 
									}
									break;

								default :
									break loop124;
								}
							}

							}
							break;

					}

					match(input,175,FOLLOW_175_in_functionResource6654); 
					}

					 res = FunctionResource.functionFromCql(fn.keyspace, fn.name, argsTypes); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "functionResource"



	// $ANTLR start "createUserStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1032:1: createUserStatement returns [CreateRoleStatement stmt] : K_CREATE K_USER ( K_IF K_NOT K_EXISTS )? u= username ( K_WITH userPassword[opts] )? ( K_SUPERUSER | K_NOSUPERUSER )? ;
	public final CreateRoleStatement createUserStatement() throws RecognitionException {
		CreateRoleStatement stmt = null;


		ParserRuleReturnScope u =null;


		        RoleOptions opts = new RoleOptions();
		        opts.setOption(IRoleManager.Option.LOGIN, true);
		        boolean superuser = false;
		        boolean ifNotExists = false;
		        RoleName name = new RoleName();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1040:5: ( K_CREATE K_USER ( K_IF K_NOT K_EXISTS )? u= username ( K_WITH userPassword[opts] )? ( K_SUPERUSER | K_NOSUPERUSER )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1040:7: K_CREATE K_USER ( K_IF K_NOT K_EXISTS )? u= username ( K_WITH userPassword[opts] )? ( K_SUPERUSER | K_NOSUPERUSER )?
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createUserStatement6702); 
			match(input,K_USER,FOLLOW_K_USER_in_createUserStatement6704); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1040:23: ( K_IF K_NOT K_EXISTS )?
			int alt127=2;
			int LA127_0 = input.LA(1);
			if ( (LA127_0==K_IF) ) {
				alt127=1;
			}
			switch (alt127) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1040:24: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createUserStatement6707); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createUserStatement6709); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createUserStatement6711); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_username_in_createUserStatement6719);
			u=username();
			state._fsp--;

			 name.setName((u!=null?input.toString(u.start,u.stop):null), true); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1041:7: ( K_WITH userPassword[opts] )?
			int alt128=2;
			int LA128_0 = input.LA(1);
			if ( (LA128_0==K_WITH) ) {
				alt128=1;
			}
			switch (alt128) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1041:9: K_WITH userPassword[opts]
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_createUserStatement6731); 
					pushFollow(FOLLOW_userPassword_in_createUserStatement6733);
					userPassword(opts);
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1042:7: ( K_SUPERUSER | K_NOSUPERUSER )?
			int alt129=3;
			int LA129_0 = input.LA(1);
			if ( (LA129_0==K_SUPERUSER) ) {
				alt129=1;
			}
			else if ( (LA129_0==K_NOSUPERUSER) ) {
				alt129=2;
			}
			switch (alt129) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1042:9: K_SUPERUSER
					{
					match(input,K_SUPERUSER,FOLLOW_K_SUPERUSER_in_createUserStatement6747); 
					 superuser = true; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1042:45: K_NOSUPERUSER
					{
					match(input,K_NOSUPERUSER,FOLLOW_K_NOSUPERUSER_in_createUserStatement6753); 
					 superuser = false; 
					}
					break;

			}

			 opts.setOption(IRoleManager.Option.SUPERUSER, superuser);
			        stmt = new CreateRoleStatement(name, opts, ifNotExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "createUserStatement"



	// $ANTLR start "alterUserStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1050:1: alterUserStatement returns [AlterRoleStatement stmt] : K_ALTER K_USER u= username ( K_WITH userPassword[opts] )? ( K_SUPERUSER | K_NOSUPERUSER )? ;
	public final AlterRoleStatement alterUserStatement() throws RecognitionException {
		AlterRoleStatement stmt = null;


		ParserRuleReturnScope u =null;


		        RoleOptions opts = new RoleOptions();
		        RoleName name = new RoleName();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1055:5: ( K_ALTER K_USER u= username ( K_WITH userPassword[opts] )? ( K_SUPERUSER | K_NOSUPERUSER )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1055:7: K_ALTER K_USER u= username ( K_WITH userPassword[opts] )? ( K_SUPERUSER | K_NOSUPERUSER )?
			{
			match(input,K_ALTER,FOLLOW_K_ALTER_in_alterUserStatement6798); 
			match(input,K_USER,FOLLOW_K_USER_in_alterUserStatement6800); 
			pushFollow(FOLLOW_username_in_alterUserStatement6804);
			u=username();
			state._fsp--;

			 name.setName((u!=null?input.toString(u.start,u.stop):null), true); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1056:7: ( K_WITH userPassword[opts] )?
			int alt130=2;
			int LA130_0 = input.LA(1);
			if ( (LA130_0==K_WITH) ) {
				alt130=1;
			}
			switch (alt130) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1056:9: K_WITH userPassword[opts]
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_alterUserStatement6816); 
					pushFollow(FOLLOW_userPassword_in_alterUserStatement6818);
					userPassword(opts);
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1057:7: ( K_SUPERUSER | K_NOSUPERUSER )?
			int alt131=3;
			int LA131_0 = input.LA(1);
			if ( (LA131_0==K_SUPERUSER) ) {
				alt131=1;
			}
			else if ( (LA131_0==K_NOSUPERUSER) ) {
				alt131=2;
			}
			switch (alt131) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1057:9: K_SUPERUSER
					{
					match(input,K_SUPERUSER,FOLLOW_K_SUPERUSER_in_alterUserStatement6832); 
					 opts.setOption(IRoleManager.Option.SUPERUSER, true); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1058:11: K_NOSUPERUSER
					{
					match(input,K_NOSUPERUSER,FOLLOW_K_NOSUPERUSER_in_alterUserStatement6846); 
					 opts.setOption(IRoleManager.Option.SUPERUSER, false); 
					}
					break;

			}

			  stmt = new AlterRoleStatement(name, opts); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "alterUserStatement"



	// $ANTLR start "dropUserStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1065:1: dropUserStatement returns [DropRoleStatement stmt] : K_DROP K_USER ( K_IF K_EXISTS )? u= username ;
	public final DropRoleStatement dropUserStatement() throws RecognitionException {
		DropRoleStatement stmt = null;


		ParserRuleReturnScope u =null;


		        boolean ifExists = false;
		        RoleName name = new RoleName();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1070:5: ( K_DROP K_USER ( K_IF K_EXISTS )? u= username )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1070:7: K_DROP K_USER ( K_IF K_EXISTS )? u= username
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropUserStatement6892); 
			match(input,K_USER,FOLLOW_K_USER_in_dropUserStatement6894); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1070:21: ( K_IF K_EXISTS )?
			int alt132=2;
			int LA132_0 = input.LA(1);
			if ( (LA132_0==K_IF) ) {
				alt132=1;
			}
			switch (alt132) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1070:22: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropUserStatement6897); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropUserStatement6899); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_username_in_dropUserStatement6907);
			u=username();
			state._fsp--;

			 name.setName((u!=null?input.toString(u.start,u.stop):null), true); stmt = new DropRoleStatement(name, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "dropUserStatement"



	// $ANTLR start "listUsersStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1076:1: listUsersStatement returns [ListRolesStatement stmt] : K_LIST K_USERS ;
	public final ListRolesStatement listUsersStatement() throws RecognitionException {
		ListRolesStatement stmt = null;


		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1077:5: ( K_LIST K_USERS )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1077:7: K_LIST K_USERS
			{
			match(input,K_LIST,FOLLOW_K_LIST_in_listUsersStatement6932); 
			match(input,K_USERS,FOLLOW_K_USERS_in_listUsersStatement6934); 
			 stmt = new ListUsersStatement(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "listUsersStatement"



	// $ANTLR start "createRoleStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1089:1: createRoleStatement returns [CreateRoleStatement stmt] : K_CREATE K_ROLE ( K_IF K_NOT K_EXISTS )? name= userOrRoleName ( K_WITH roleOptions[opts] )? ;
	public final CreateRoleStatement createRoleStatement() throws RecognitionException {
		CreateRoleStatement stmt = null;


		RoleName name =null;


		        RoleOptions opts = new RoleOptions();
		        boolean ifNotExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1094:5: ( K_CREATE K_ROLE ( K_IF K_NOT K_EXISTS )? name= userOrRoleName ( K_WITH roleOptions[opts] )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1094:7: K_CREATE K_ROLE ( K_IF K_NOT K_EXISTS )? name= userOrRoleName ( K_WITH roleOptions[opts] )?
			{
			match(input,K_CREATE,FOLLOW_K_CREATE_in_createRoleStatement6968); 
			match(input,K_ROLE,FOLLOW_K_ROLE_in_createRoleStatement6970); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1094:23: ( K_IF K_NOT K_EXISTS )?
			int alt133=2;
			int LA133_0 = input.LA(1);
			if ( (LA133_0==K_IF) ) {
				alt133=1;
			}
			switch (alt133) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1094:24: K_IF K_NOT K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_createRoleStatement6973); 
					match(input,K_NOT,FOLLOW_K_NOT_in_createRoleStatement6975); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_createRoleStatement6977); 
					 ifNotExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_userOrRoleName_in_createRoleStatement6985);
			name=userOrRoleName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1095:7: ( K_WITH roleOptions[opts] )?
			int alt134=2;
			int LA134_0 = input.LA(1);
			if ( (LA134_0==K_WITH) ) {
				alt134=1;
			}
			switch (alt134) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1095:9: K_WITH roleOptions[opts]
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_createRoleStatement6995); 
					pushFollow(FOLLOW_roleOptions_in_createRoleStatement6997);
					roleOptions(opts);
					state._fsp--;

					}
					break;

			}


			        // set defaults if they weren't explictly supplied
			        if (!opts.getLogin().isPresent())
			        {
			            opts.setOption(IRoleManager.Option.LOGIN, false);
			        }
			        if (!opts.getSuperuser().isPresent())
			        {
			            opts.setOption(IRoleManager.Option.SUPERUSER, false);
			        }
			        stmt = new CreateRoleStatement(name, opts, ifNotExists);
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "createRoleStatement"



	// $ANTLR start "alterRoleStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1119:1: alterRoleStatement returns [AlterRoleStatement stmt] : K_ALTER K_ROLE name= userOrRoleName ( K_WITH roleOptions[opts] )? ;
	public final AlterRoleStatement alterRoleStatement() throws RecognitionException {
		AlterRoleStatement stmt = null;


		RoleName name =null;


		        RoleOptions opts = new RoleOptions();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1123:5: ( K_ALTER K_ROLE name= userOrRoleName ( K_WITH roleOptions[opts] )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1123:7: K_ALTER K_ROLE name= userOrRoleName ( K_WITH roleOptions[opts] )?
			{
			match(input,K_ALTER,FOLLOW_K_ALTER_in_alterRoleStatement7041); 
			match(input,K_ROLE,FOLLOW_K_ROLE_in_alterRoleStatement7043); 
			pushFollow(FOLLOW_userOrRoleName_in_alterRoleStatement7047);
			name=userOrRoleName();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1124:7: ( K_WITH roleOptions[opts] )?
			int alt135=2;
			int LA135_0 = input.LA(1);
			if ( (LA135_0==K_WITH) ) {
				alt135=1;
			}
			switch (alt135) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1124:9: K_WITH roleOptions[opts]
					{
					match(input,K_WITH,FOLLOW_K_WITH_in_alterRoleStatement7057); 
					pushFollow(FOLLOW_roleOptions_in_alterRoleStatement7059);
					roleOptions(opts);
					state._fsp--;

					}
					break;

			}

			  stmt = new AlterRoleStatement(name, opts); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "alterRoleStatement"



	// $ANTLR start "dropRoleStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1131:1: dropRoleStatement returns [DropRoleStatement stmt] : K_DROP K_ROLE ( K_IF K_EXISTS )? name= userOrRoleName ;
	public final DropRoleStatement dropRoleStatement() throws RecognitionException {
		DropRoleStatement stmt = null;


		RoleName name =null;


		        boolean ifExists = false;
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1135:5: ( K_DROP K_ROLE ( K_IF K_EXISTS )? name= userOrRoleName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1135:7: K_DROP K_ROLE ( K_IF K_EXISTS )? name= userOrRoleName
			{
			match(input,K_DROP,FOLLOW_K_DROP_in_dropRoleStatement7103); 
			match(input,K_ROLE,FOLLOW_K_ROLE_in_dropRoleStatement7105); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1135:21: ( K_IF K_EXISTS )?
			int alt136=2;
			int LA136_0 = input.LA(1);
			if ( (LA136_0==K_IF) ) {
				alt136=1;
			}
			switch (alt136) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1135:22: K_IF K_EXISTS
					{
					match(input,K_IF,FOLLOW_K_IF_in_dropRoleStatement7108); 
					match(input,K_EXISTS,FOLLOW_K_EXISTS_in_dropRoleStatement7110); 
					 ifExists = true; 
					}
					break;

			}

			pushFollow(FOLLOW_userOrRoleName_in_dropRoleStatement7118);
			name=userOrRoleName();
			state._fsp--;

			 stmt = new DropRoleStatement(name, ifExists); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "dropRoleStatement"



	// $ANTLR start "listRolesStatement"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1142:1: listRolesStatement returns [ListRolesStatement stmt] : K_LIST K_ROLES ( K_OF roleName[grantee] )? ( K_NORECURSIVE )? ;
	public final ListRolesStatement listRolesStatement() throws RecognitionException {
		ListRolesStatement stmt = null;



		        boolean recursive = true;
		        RoleName grantee = new RoleName();
		    
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1147:5: ( K_LIST K_ROLES ( K_OF roleName[grantee] )? ( K_NORECURSIVE )? )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1147:7: K_LIST K_ROLES ( K_OF roleName[grantee] )? ( K_NORECURSIVE )?
			{
			match(input,K_LIST,FOLLOW_K_LIST_in_listRolesStatement7158); 
			match(input,K_ROLES,FOLLOW_K_ROLES_in_listRolesStatement7160); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1148:7: ( K_OF roleName[grantee] )?
			int alt137=2;
			int LA137_0 = input.LA(1);
			if ( (LA137_0==K_OF) ) {
				alt137=1;
			}
			switch (alt137) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1148:9: K_OF roleName[grantee]
					{
					match(input,K_OF,FOLLOW_K_OF_in_listRolesStatement7170); 
					pushFollow(FOLLOW_roleName_in_listRolesStatement7172);
					roleName(grantee);
					state._fsp--;

					}
					break;

			}

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1149:7: ( K_NORECURSIVE )?
			int alt138=2;
			int LA138_0 = input.LA(1);
			if ( (LA138_0==K_NORECURSIVE) ) {
				alt138=1;
			}
			switch (alt138) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1149:9: K_NORECURSIVE
					{
					match(input,K_NORECURSIVE,FOLLOW_K_NORECURSIVE_in_listRolesStatement7185); 
					 recursive = false; 
					}
					break;

			}

			 stmt = new ListRolesStatement(grantee, recursive); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return stmt;
	}
	// $ANTLR end "listRolesStatement"



	// $ANTLR start "roleOptions"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1153:1: roleOptions[RoleOptions opts] : roleOption[opts] ( K_AND roleOption[opts] )* ;
	public final void roleOptions(RoleOptions opts) throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1154:5: ( roleOption[opts] ( K_AND roleOption[opts] )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1154:7: roleOption[opts] ( K_AND roleOption[opts] )*
			{
			pushFollow(FOLLOW_roleOption_in_roleOptions7216);
			roleOption(opts);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1154:24: ( K_AND roleOption[opts] )*
			loop139:
			while (true) {
				int alt139=2;
				int LA139_0 = input.LA(1);
				if ( (LA139_0==K_AND) ) {
					alt139=1;
				}

				switch (alt139) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1154:25: K_AND roleOption[opts]
					{
					match(input,K_AND,FOLLOW_K_AND_in_roleOptions7220); 
					pushFollow(FOLLOW_roleOption_in_roleOptions7222);
					roleOption(opts);
					state._fsp--;

					}
					break;

				default :
					break loop139;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "roleOptions"



	// $ANTLR start "roleOption"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1157:1: roleOption[RoleOptions opts] : ( K_PASSWORD '=' v= STRING_LITERAL | K_OPTIONS '=' m= mapLiteral | K_SUPERUSER '=' b= BOOLEAN | K_LOGIN '=' b= BOOLEAN );
	public final void roleOption(RoleOptions opts) throws RecognitionException {
		Token v=null;
		Token b=null;
		Maps.Literal m =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1158:5: ( K_PASSWORD '=' v= STRING_LITERAL | K_OPTIONS '=' m= mapLiteral | K_SUPERUSER '=' b= BOOLEAN | K_LOGIN '=' b= BOOLEAN )
			int alt140=4;
			switch ( input.LA(1) ) {
			case K_PASSWORD:
				{
				alt140=1;
				}
				break;
			case K_OPTIONS:
				{
				alt140=2;
				}
				break;
			case K_SUPERUSER:
				{
				alt140=3;
				}
				break;
			case K_LOGIN:
				{
				alt140=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 140, 0, input);
				throw nvae;
			}
			switch (alt140) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1158:8: K_PASSWORD '=' v= STRING_LITERAL
					{
					match(input,K_PASSWORD,FOLLOW_K_PASSWORD_in_roleOption7244); 
					match(input,184,FOLLOW_184_in_roleOption7246); 
					v=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_roleOption7250); 
					 opts.setOption(IRoleManager.Option.PASSWORD, (v!=null?v.getText():null)); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1159:8: K_OPTIONS '=' m= mapLiteral
					{
					match(input,K_OPTIONS,FOLLOW_K_OPTIONS_in_roleOption7261); 
					match(input,184,FOLLOW_184_in_roleOption7263); 
					pushFollow(FOLLOW_mapLiteral_in_roleOption7267);
					m=mapLiteral();
					state._fsp--;

					 opts.setOption(IRoleManager.Option.OPTIONS, convertPropertyMap(m)); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1160:8: K_SUPERUSER '=' b= BOOLEAN
					{
					match(input,K_SUPERUSER,FOLLOW_K_SUPERUSER_in_roleOption7278); 
					match(input,184,FOLLOW_184_in_roleOption7280); 
					b=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_roleOption7284); 
					 opts.setOption(IRoleManager.Option.SUPERUSER, Boolean.valueOf((b!=null?b.getText():null))); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1161:8: K_LOGIN '=' b= BOOLEAN
					{
					match(input,K_LOGIN,FOLLOW_K_LOGIN_in_roleOption7295); 
					match(input,184,FOLLOW_184_in_roleOption7297); 
					b=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_roleOption7301); 
					 opts.setOption(IRoleManager.Option.LOGIN, Boolean.valueOf((b!=null?b.getText():null))); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "roleOption"



	// $ANTLR start "userPassword"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1165:1: userPassword[RoleOptions opts] : K_PASSWORD v= STRING_LITERAL ;
	public final void userPassword(RoleOptions opts) throws RecognitionException {
		Token v=null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1166:5: ( K_PASSWORD v= STRING_LITERAL )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1166:8: K_PASSWORD v= STRING_LITERAL
			{
			match(input,K_PASSWORD,FOLLOW_K_PASSWORD_in_userPassword7323); 
			v=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_userPassword7327); 
			 opts.setOption(IRoleManager.Option.PASSWORD, (v!=null?v.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "userPassword"



	// $ANTLR start "cident"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1174:1: cident returns [ColumnIdentifier.Raw id] : (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword );
	public final ColumnIdentifier.Raw cident() throws RecognitionException {
		ColumnIdentifier.Raw id = null;


		Token t=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1175:5: (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword )
			int alt141=3;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt141=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt141=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt141=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 141, 0, input);
				throw nvae;
			}
			switch (alt141) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1175:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_cident7358); 
					 id = new ColumnIdentifier.Literal((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1176:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_cident7383); 
					 id = new ColumnIdentifier.Literal((t!=null?t.getText():null), true); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1177:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_cident7402);
					k=unreserved_keyword();
					state._fsp--;

					 id = new ColumnIdentifier.Literal(k, false); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return id;
	}
	// $ANTLR end "cident"



	// $ANTLR start "ident"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1181:1: ident returns [ColumnIdentifier id] : (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword );
	public final ColumnIdentifier ident() throws RecognitionException {
		ColumnIdentifier id = null;


		Token t=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1182:5: (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword )
			int alt142=3;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt142=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt142=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt142=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 142, 0, input);
				throw nvae;
			}
			switch (alt142) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1182:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_ident7428); 
					 id = ColumnIdentifier.getInterned((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1183:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_ident7453); 
					 id = ColumnIdentifier.getInterned((t!=null?t.getText():null), true); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1184:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_ident7472);
					k=unreserved_keyword();
					state._fsp--;

					 id = ColumnIdentifier.getInterned(k, false); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return id;
	}
	// $ANTLR end "ident"



	// $ANTLR start "noncol_ident"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1188:1: noncol_ident returns [ColumnIdentifier id] : (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword );
	public final ColumnIdentifier noncol_ident() throws RecognitionException {
		ColumnIdentifier id = null;


		Token t=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1189:5: (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword )
			int alt143=3;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt143=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt143=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt143=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 143, 0, input);
				throw nvae;
			}
			switch (alt143) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1189:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_noncol_ident7498); 
					 id = new ColumnIdentifier((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1190:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_noncol_ident7523); 
					 id = new ColumnIdentifier((t!=null?t.getText():null), true); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1191:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_noncol_ident7542);
					k=unreserved_keyword();
					state._fsp--;

					 id = new ColumnIdentifier(k, false); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return id;
	}
	// $ANTLR end "noncol_ident"



	// $ANTLR start "keyspaceName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1195:1: keyspaceName returns [String id] : ksName[name] ;
	public final String keyspaceName() throws RecognitionException {
		String id = null;


		 CFName name = new CFName(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1197:5: ( ksName[name] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1197:7: ksName[name]
			{
			pushFollow(FOLLOW_ksName_in_keyspaceName7575);
			ksName(name);
			state._fsp--;

			 id = name.getKeyspace(); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return id;
	}
	// $ANTLR end "keyspaceName"



	// $ANTLR start "indexName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1200:1: indexName returns [IndexName name] : ( ksName[name] '.' )? idxName[name] ;
	public final IndexName indexName() throws RecognitionException {
		IndexName name = null;


		 name = new IndexName(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1202:5: ( ( ksName[name] '.' )? idxName[name] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1202:7: ( ksName[name] '.' )? idxName[name]
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1202:7: ( ksName[name] '.' )?
			int alt144=2;
			alt144 = dfa144.predict(input);
			switch (alt144) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1202:8: ksName[name] '.'
					{
					pushFollow(FOLLOW_ksName_in_indexName7609);
					ksName(name);
					state._fsp--;

					match(input,179,FOLLOW_179_in_indexName7612); 
					}
					break;

			}

			pushFollow(FOLLOW_idxName_in_indexName7616);
			idxName(name);
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return name;
	}
	// $ANTLR end "indexName"



	// $ANTLR start "columnFamilyName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1205:1: columnFamilyName returns [CFName name] : ( ksName[name] '.' )? cfName[name] ;
	public final CFName columnFamilyName() throws RecognitionException {
		CFName name = null;


		 name = new CFName(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1207:5: ( ( ksName[name] '.' )? cfName[name] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1207:7: ( ksName[name] '.' )? cfName[name]
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1207:7: ( ksName[name] '.' )?
			int alt145=2;
			alt145 = dfa145.predict(input);
			switch (alt145) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1207:8: ksName[name] '.'
					{
					pushFollow(FOLLOW_ksName_in_columnFamilyName7648);
					ksName(name);
					state._fsp--;

					match(input,179,FOLLOW_179_in_columnFamilyName7651); 
					}
					break;

			}

			pushFollow(FOLLOW_cfName_in_columnFamilyName7655);
			cfName(name);
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return name;
	}
	// $ANTLR end "columnFamilyName"



	// $ANTLR start "userTypeName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1210:1: userTypeName returns [UTName name] : (ks= noncol_ident '.' )? ut= non_type_ident ;
	public final UTName userTypeName() throws RecognitionException {
		UTName name = null;


		ColumnIdentifier ks =null;
		ColumnIdentifier ut =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1211:5: ( (ks= noncol_ident '.' )? ut= non_type_ident )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1211:7: (ks= noncol_ident '.' )? ut= non_type_ident
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1211:7: (ks= noncol_ident '.' )?
			int alt146=2;
			switch ( input.LA(1) ) {
				case IDENT:
					{
					int LA146_1 = input.LA(2);
					if ( (LA146_1==179) ) {
						alt146=1;
					}
					}
					break;
				case QUOTED_NAME:
					{
					int LA146_2 = input.LA(2);
					if ( (LA146_2==179) ) {
						alt146=1;
					}
					}
					break;
				case K_AGGREGATE:
				case K_ALL:
				case K_AS:
				case K_CALLED:
				case K_CLUSTERING:
				case K_COMPACT:
				case K_CONTAINS:
				case K_CUSTOM:
				case K_DISTINCT:
				case K_EXISTS:
				case K_FILTERING:
				case K_FINALFUNC:
				case K_FROZEN:
				case K_FUNCTION:
				case K_FUNCTIONS:
				case K_INITCOND:
				case K_INPUT:
				case K_JSON:
				case K_KEYS:
				case K_KEYSPACES:
				case K_LANGUAGE:
				case K_LIKE:
				case K_LIST:
				case K_LOGIN:
				case K_MAP:
				case K_NOLOGIN:
				case K_NOSUPERUSER:
				case K_OPTIONS:
				case K_PARTITION:
				case K_PASSWORD:
				case K_PER:
				case K_PERMISSION:
				case K_PERMISSIONS:
				case K_RETURNS:
				case K_ROLE:
				case K_ROLES:
				case K_SFUNC:
				case K_STATIC:
				case K_STORAGE:
				case K_STYPE:
				case K_SUPERUSER:
				case K_TRIGGER:
				case K_TUPLE:
				case K_TYPE:
				case K_USER:
				case K_USERS:
				case K_VALUES:
					{
					int LA146_3 = input.LA(2);
					if ( (LA146_3==179) ) {
						alt146=1;
					}
					}
					break;
				case K_ASCII:
				case K_BIGINT:
				case K_BLOB:
				case K_BOOLEAN:
				case K_COUNT:
				case K_COUNTER:
				case K_DATE:
				case K_DECIMAL:
				case K_DOUBLE:
				case K_FLOAT:
				case K_INET:
				case K_INT:
				case K_SMALLINT:
				case K_TEXT:
				case K_TIME:
				case K_TIMESTAMP:
				case K_TIMEUUID:
				case K_TINYINT:
				case K_TTL:
				case K_UUID:
				case K_VARCHAR:
				case K_VARINT:
				case K_WRITETIME:
					{
					alt146=1;
					}
					break;
				case K_KEY:
					{
					int LA146_5 = input.LA(2);
					if ( (LA146_5==179) ) {
						alt146=1;
					}
					}
					break;
			}
			switch (alt146) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1211:8: ks= noncol_ident '.'
					{
					pushFollow(FOLLOW_noncol_ident_in_userTypeName7680);
					ks=noncol_ident();
					state._fsp--;

					match(input,179,FOLLOW_179_in_userTypeName7682); 
					}
					break;

			}

			pushFollow(FOLLOW_non_type_ident_in_userTypeName7688);
			ut=non_type_ident();
			state._fsp--;

			 return new UTName(ks, ut); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return name;
	}
	// $ANTLR end "userTypeName"



	// $ANTLR start "userOrRoleName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1214:1: userOrRoleName returns [RoleName name] : roleName[name] ;
	public final RoleName userOrRoleName() throws RecognitionException {
		RoleName name = null;


		 name = new RoleName(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1216:5: ( roleName[name] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1216:7: roleName[name]
			{
			pushFollow(FOLLOW_roleName_in_userOrRoleName7720);
			roleName(name);
			state._fsp--;

			return name;
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return name;
	}
	// $ANTLR end "userOrRoleName"



	// $ANTLR start "ksName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1219:1: ksName[KeyspaceElementName name] : (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword | QMARK );
	public final void ksName(KeyspaceElementName name) throws RecognitionException {
		Token t=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1220:5: (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword | QMARK )
			int alt147=4;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt147=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt147=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt147=3;
				}
				break;
			case QMARK:
				{
				alt147=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 147, 0, input);
				throw nvae;
			}
			switch (alt147) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1220:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_ksName7743); 
					 name.setKeyspace((t!=null?t.getText():null), false);
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1221:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_ksName7768); 
					 name.setKeyspace((t!=null?t.getText():null), true);
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1222:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_ksName7787);
					k=unreserved_keyword();
					state._fsp--;

					 name.setKeyspace(k, false);
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1223:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_ksName7797); 
					addRecognitionError("Bind variables cannot be used for keyspace names");
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ksName"



	// $ANTLR start "cfName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1226:1: cfName[CFName name] : (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword | QMARK );
	public final void cfName(CFName name) throws RecognitionException {
		Token t=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1227:5: (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword | QMARK )
			int alt148=4;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt148=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt148=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt148=3;
				}
				break;
			case QMARK:
				{
				alt148=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 148, 0, input);
				throw nvae;
			}
			switch (alt148) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1227:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_cfName7819); 
					 name.setColumnFamily((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1228:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_cfName7844); 
					 name.setColumnFamily((t!=null?t.getText():null), true); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1229:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_cfName7863);
					k=unreserved_keyword();
					state._fsp--;

					 name.setColumnFamily(k, false); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1230:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_cfName7873); 
					addRecognitionError("Bind variables cannot be used for table names");
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "cfName"



	// $ANTLR start "idxName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1233:1: idxName[IndexName name] : (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword | QMARK );
	public final void idxName(IndexName name) throws RecognitionException {
		Token t=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1234:5: (t= IDENT |t= QUOTED_NAME |k= unreserved_keyword | QMARK )
			int alt149=4;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt149=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt149=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt149=3;
				}
				break;
			case QMARK:
				{
				alt149=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 149, 0, input);
				throw nvae;
			}
			switch (alt149) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1234:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_idxName7895); 
					 name.setIndex((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1235:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_idxName7920); 
					 name.setIndex((t!=null?t.getText():null), true);
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1236:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_idxName7939);
					k=unreserved_keyword();
					state._fsp--;

					 name.setIndex(k, false); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1237:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_idxName7949); 
					addRecognitionError("Bind variables cannot be used for index names");
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "idxName"



	// $ANTLR start "roleName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1240:1: roleName[RoleName name] : (t= IDENT |s= STRING_LITERAL |t= QUOTED_NAME |k= unreserved_keyword | QMARK );
	public final void roleName(RoleName name) throws RecognitionException {
		Token t=null;
		Token s=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1241:5: (t= IDENT |s= STRING_LITERAL |t= QUOTED_NAME |k= unreserved_keyword | QMARK )
			int alt150=5;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt150=1;
				}
				break;
			case STRING_LITERAL:
				{
				alt150=2;
				}
				break;
			case QUOTED_NAME:
				{
				alt150=3;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNT:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEY:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TTL:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
			case K_WRITETIME:
				{
				alt150=4;
				}
				break;
			case QMARK:
				{
				alt150=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 150, 0, input);
				throw nvae;
			}
			switch (alt150) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1241:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_roleName7971); 
					 name.setName((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1242:7: s= STRING_LITERAL
					{
					s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_roleName7996); 
					 name.setName((s!=null?s.getText():null), true); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1243:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_roleName8012); 
					 name.setName((t!=null?t.getText():null), true); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1244:7: k= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_roleName8031);
					k=unreserved_keyword();
					state._fsp--;

					 name.setName(k, false); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1245:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_roleName8041); 
					addRecognitionError("Bind variables cannot be used for role names");
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "roleName"



	// $ANTLR start "constant"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1248:1: constant returns [Constants.Literal constant] : (t= STRING_LITERAL |t= INTEGER |t= FLOAT |t= BOOLEAN |t= UUID |t= HEXNUMBER | ( '-' )? t= ( K_NAN | K_INFINITY ) );
	public final Constants.Literal constant() throws RecognitionException {
		Constants.Literal constant = null;


		Token t=null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1249:5: (t= STRING_LITERAL |t= INTEGER |t= FLOAT |t= BOOLEAN |t= UUID |t= HEXNUMBER | ( '-' )? t= ( K_NAN | K_INFINITY ) )
			int alt152=7;
			switch ( input.LA(1) ) {
			case STRING_LITERAL:
				{
				alt152=1;
				}
				break;
			case INTEGER:
				{
				alt152=2;
				}
				break;
			case FLOAT:
				{
				alt152=3;
				}
				break;
			case BOOLEAN:
				{
				alt152=4;
				}
				break;
			case UUID:
				{
				alt152=5;
				}
				break;
			case HEXNUMBER:
				{
				alt152=6;
				}
				break;
			case K_INFINITY:
			case K_NAN:
			case 178:
				{
				alt152=7;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 152, 0, input);
				throw nvae;
			}
			switch (alt152) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1249:7: t= STRING_LITERAL
					{
					t=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_constant8066); 
					 constant = Constants.Literal.string((t!=null?t.getText():null)); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1250:7: t= INTEGER
					{
					t=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_constant8078); 
					 constant = Constants.Literal.integer((t!=null?t.getText():null)); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1251:7: t= FLOAT
					{
					t=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constant8097); 
					 constant = Constants.Literal.floatingPoint((t!=null?t.getText():null)); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1252:7: t= BOOLEAN
					{
					t=(Token)match(input,BOOLEAN,FOLLOW_BOOLEAN_in_constant8118); 
					 constant = Constants.Literal.bool((t!=null?t.getText():null)); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1253:7: t= UUID
					{
					t=(Token)match(input,UUID,FOLLOW_UUID_in_constant8137); 
					 constant = Constants.Literal.uuid((t!=null?t.getText():null)); 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1254:7: t= HEXNUMBER
					{
					t=(Token)match(input,HEXNUMBER,FOLLOW_HEXNUMBER_in_constant8159); 
					 constant = Constants.Literal.hex((t!=null?t.getText():null)); 
					}
					break;
				case 7 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1255:7: ( '-' )? t= ( K_NAN | K_INFINITY )
					{
					 String sign=""; 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1255:27: ( '-' )?
					int alt151=2;
					int LA151_0 = input.LA(1);
					if ( (LA151_0==178) ) {
						alt151=1;
					}
					switch (alt151) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1255:28: '-'
							{
							match(input,178,FOLLOW_178_in_constant8177); 
							sign = "-"; 
							}
							break;

					}

					t=input.LT(1);
					if ( input.LA(1)==K_INFINITY||input.LA(1)==K_NAN ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					 constant = Constants.Literal.floatingPoint(sign + (t!=null?t.getText():null)); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return constant;
	}
	// $ANTLR end "constant"



	// $ANTLR start "mapLiteral"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1258:1: mapLiteral returns [Maps.Literal map] : '{' (k1= term ':' v1= term ( ',' kn= term ':' vn= term )* )? '}' ;
	public final Maps.Literal mapLiteral() throws RecognitionException {
		Maps.Literal map = null;


		Term.Raw k1 =null;
		Term.Raw v1 =null;
		Term.Raw kn =null;
		Term.Raw vn =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1259:5: ( '{' (k1= term ':' v1= term ( ',' kn= term ':' vn= term )* )? '}' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1259:7: '{' (k1= term ':' v1= term ( ',' kn= term ':' vn= term )* )? '}'
			{
			match(input,191,FOLLOW_191_in_mapLiteral8215); 
			 List<Pair<Term.Raw, Term.Raw>> m = new ArrayList<Pair<Term.Raw, Term.Raw>>(); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1260:11: (k1= term ':' v1= term ( ',' kn= term ':' vn= term )* )?
			int alt154=2;
			int LA154_0 = input.LA(1);
			if ( (LA154_0==BOOLEAN||LA154_0==FLOAT||LA154_0==HEXNUMBER||(LA154_0 >= IDENT && LA154_0 <= INTEGER)||(LA154_0 >= K_AGGREGATE && LA154_0 <= K_ALL)||LA154_0==K_AS||LA154_0==K_ASCII||(LA154_0 >= K_BIGINT && LA154_0 <= K_BOOLEAN)||(LA154_0 >= K_CALLED && LA154_0 <= K_CLUSTERING)||(LA154_0 >= K_COMPACT && LA154_0 <= K_COUNTER)||(LA154_0 >= K_CUSTOM && LA154_0 <= K_DECIMAL)||(LA154_0 >= K_DISTINCT && LA154_0 <= K_DOUBLE)||(LA154_0 >= K_EXISTS && LA154_0 <= K_FLOAT)||LA154_0==K_FROZEN||(LA154_0 >= K_FUNCTION && LA154_0 <= K_FUNCTIONS)||(LA154_0 >= K_INET && LA154_0 <= K_INPUT)||LA154_0==K_INT||(LA154_0 >= K_JSON && LA154_0 <= K_KEYS)||(LA154_0 >= K_KEYSPACES && LA154_0 <= K_LIKE)||(LA154_0 >= K_LIST && LA154_0 <= K_MAP)||(LA154_0 >= K_NAN && LA154_0 <= K_NOLOGIN)||LA154_0==K_NOSUPERUSER||LA154_0==K_NULL||LA154_0==K_OPTIONS||(LA154_0 >= K_PARTITION && LA154_0 <= K_PERMISSIONS)||LA154_0==K_RETURNS||(LA154_0 >= K_ROLE && LA154_0 <= K_ROLES)||(LA154_0 >= K_SFUNC && LA154_0 <= K_TINYINT)||(LA154_0 >= K_TOKEN && LA154_0 <= K_TRIGGER)||(LA154_0 >= K_TTL && LA154_0 <= K_TYPE)||(LA154_0 >= K_USER && LA154_0 <= K_USERS)||(LA154_0 >= K_UUID && LA154_0 <= K_VARINT)||LA154_0==K_WRITETIME||(LA154_0 >= QMARK && LA154_0 <= QUOTED_NAME)||LA154_0==STRING_LITERAL||LA154_0==UUID||LA154_0==174||LA154_0==178||LA154_0==180||LA154_0==187||LA154_0==191) ) {
				alt154=1;
			}
			switch (alt154) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1260:13: k1= term ':' v1= term ( ',' kn= term ':' vn= term )*
					{
					pushFollow(FOLLOW_term_in_mapLiteral8233);
					k1=term();
					state._fsp--;

					match(input,180,FOLLOW_180_in_mapLiteral8235); 
					pushFollow(FOLLOW_term_in_mapLiteral8239);
					v1=term();
					state._fsp--;

					 m.add(Pair.create(k1, v1)); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1260:65: ( ',' kn= term ':' vn= term )*
					loop153:
					while (true) {
						int alt153=2;
						int LA153_0 = input.LA(1);
						if ( (LA153_0==177) ) {
							alt153=1;
						}

						switch (alt153) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1260:67: ',' kn= term ':' vn= term
							{
							match(input,177,FOLLOW_177_in_mapLiteral8245); 
							pushFollow(FOLLOW_term_in_mapLiteral8249);
							kn=term();
							state._fsp--;

							match(input,180,FOLLOW_180_in_mapLiteral8251); 
							pushFollow(FOLLOW_term_in_mapLiteral8255);
							vn=term();
							state._fsp--;

							 m.add(Pair.create(kn, vn)); 
							}
							break;

						default :
							break loop153;
						}
					}

					}
					break;

			}

			match(input,192,FOLLOW_192_in_mapLiteral8271); 
			 map = new Maps.Literal(m); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return map;
	}
	// $ANTLR end "mapLiteral"



	// $ANTLR start "setOrMapLiteral"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1264:1: setOrMapLiteral[Term.Raw t] returns [Term.Raw value] : ( ':' v= term ( ',' kn= term ':' vn= term )* | ( ',' tn= term )* );
	public final Term.Raw setOrMapLiteral(Term.Raw t) throws RecognitionException {
		Term.Raw value = null;


		Term.Raw v =null;
		Term.Raw kn =null;
		Term.Raw vn =null;
		Term.Raw tn =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1265:5: ( ':' v= term ( ',' kn= term ':' vn= term )* | ( ',' tn= term )* )
			int alt157=2;
			int LA157_0 = input.LA(1);
			if ( (LA157_0==180) ) {
				alt157=1;
			}
			else if ( (LA157_0==177||LA157_0==192) ) {
				alt157=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 157, 0, input);
				throw nvae;
			}

			switch (alt157) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1265:7: ':' v= term ( ',' kn= term ':' vn= term )*
					{
					match(input,180,FOLLOW_180_in_setOrMapLiteral8295); 
					pushFollow(FOLLOW_term_in_setOrMapLiteral8299);
					v=term();
					state._fsp--;

					 List<Pair<Term.Raw, Term.Raw>> m = new ArrayList<Pair<Term.Raw, Term.Raw>>(); m.add(Pair.create(t, v)); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1266:11: ( ',' kn= term ':' vn= term )*
					loop155:
					while (true) {
						int alt155=2;
						int LA155_0 = input.LA(1);
						if ( (LA155_0==177) ) {
							alt155=1;
						}

						switch (alt155) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1266:13: ',' kn= term ':' vn= term
							{
							match(input,177,FOLLOW_177_in_setOrMapLiteral8315); 
							pushFollow(FOLLOW_term_in_setOrMapLiteral8319);
							kn=term();
							state._fsp--;

							match(input,180,FOLLOW_180_in_setOrMapLiteral8321); 
							pushFollow(FOLLOW_term_in_setOrMapLiteral8325);
							vn=term();
							state._fsp--;

							 m.add(Pair.create(kn, vn)); 
							}
							break;

						default :
							break loop155;
						}
					}

					 value = new Maps.Literal(m); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1268:7: ( ',' tn= term )*
					{
					 List<Term.Raw> s = new ArrayList<Term.Raw>(); s.add(t); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1269:11: ( ',' tn= term )*
					loop156:
					while (true) {
						int alt156=2;
						int LA156_0 = input.LA(1);
						if ( (LA156_0==177) ) {
							alt156=1;
						}

						switch (alt156) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1269:13: ',' tn= term
							{
							match(input,177,FOLLOW_177_in_setOrMapLiteral8360); 
							pushFollow(FOLLOW_term_in_setOrMapLiteral8364);
							tn=term();
							state._fsp--;

							 s.add(tn); 
							}
							break;

						default :
							break loop156;
						}
					}

					 value = new Sets.Literal(s); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "setOrMapLiteral"



	// $ANTLR start "collectionLiteral"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1273:1: collectionLiteral returns [Term.Raw value] : ( '[' (t1= term ( ',' tn= term )* )? ']' | '{' t= term v= setOrMapLiteral[t] '}' | '{' '}' );
	public final Term.Raw collectionLiteral() throws RecognitionException {
		Term.Raw value = null;


		Term.Raw t1 =null;
		Term.Raw tn =null;
		Term.Raw t =null;
		Term.Raw v =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1274:5: ( '[' (t1= term ( ',' tn= term )* )? ']' | '{' t= term v= setOrMapLiteral[t] '}' | '{' '}' )
			int alt160=3;
			int LA160_0 = input.LA(1);
			if ( (LA160_0==187) ) {
				alt160=1;
			}
			else if ( (LA160_0==191) ) {
				int LA160_2 = input.LA(2);
				if ( (LA160_2==192) ) {
					alt160=3;
				}
				else if ( (LA160_2==BOOLEAN||LA160_2==FLOAT||LA160_2==HEXNUMBER||(LA160_2 >= IDENT && LA160_2 <= INTEGER)||(LA160_2 >= K_AGGREGATE && LA160_2 <= K_ALL)||LA160_2==K_AS||LA160_2==K_ASCII||(LA160_2 >= K_BIGINT && LA160_2 <= K_BOOLEAN)||(LA160_2 >= K_CALLED && LA160_2 <= K_CLUSTERING)||(LA160_2 >= K_COMPACT && LA160_2 <= K_COUNTER)||(LA160_2 >= K_CUSTOM && LA160_2 <= K_DECIMAL)||(LA160_2 >= K_DISTINCT && LA160_2 <= K_DOUBLE)||(LA160_2 >= K_EXISTS && LA160_2 <= K_FLOAT)||LA160_2==K_FROZEN||(LA160_2 >= K_FUNCTION && LA160_2 <= K_FUNCTIONS)||(LA160_2 >= K_INET && LA160_2 <= K_INPUT)||LA160_2==K_INT||(LA160_2 >= K_JSON && LA160_2 <= K_KEYS)||(LA160_2 >= K_KEYSPACES && LA160_2 <= K_LIKE)||(LA160_2 >= K_LIST && LA160_2 <= K_MAP)||(LA160_2 >= K_NAN && LA160_2 <= K_NOLOGIN)||LA160_2==K_NOSUPERUSER||LA160_2==K_NULL||LA160_2==K_OPTIONS||(LA160_2 >= K_PARTITION && LA160_2 <= K_PERMISSIONS)||LA160_2==K_RETURNS||(LA160_2 >= K_ROLE && LA160_2 <= K_ROLES)||(LA160_2 >= K_SFUNC && LA160_2 <= K_TINYINT)||(LA160_2 >= K_TOKEN && LA160_2 <= K_TRIGGER)||(LA160_2 >= K_TTL && LA160_2 <= K_TYPE)||(LA160_2 >= K_USER && LA160_2 <= K_USERS)||(LA160_2 >= K_UUID && LA160_2 <= K_VARINT)||LA160_2==K_WRITETIME||(LA160_2 >= QMARK && LA160_2 <= QUOTED_NAME)||LA160_2==STRING_LITERAL||LA160_2==UUID||LA160_2==174||LA160_2==178||LA160_2==180||LA160_2==187||LA160_2==191) ) {
					alt160=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 160, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 160, 0, input);
				throw nvae;
			}

			switch (alt160) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1274:7: '[' (t1= term ( ',' tn= term )* )? ']'
					{
					match(input,187,FOLLOW_187_in_collectionLiteral8398); 
					 List<Term.Raw> l = new ArrayList<Term.Raw>(); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1275:11: (t1= term ( ',' tn= term )* )?
					int alt159=2;
					int LA159_0 = input.LA(1);
					if ( (LA159_0==BOOLEAN||LA159_0==FLOAT||LA159_0==HEXNUMBER||(LA159_0 >= IDENT && LA159_0 <= INTEGER)||(LA159_0 >= K_AGGREGATE && LA159_0 <= K_ALL)||LA159_0==K_AS||LA159_0==K_ASCII||(LA159_0 >= K_BIGINT && LA159_0 <= K_BOOLEAN)||(LA159_0 >= K_CALLED && LA159_0 <= K_CLUSTERING)||(LA159_0 >= K_COMPACT && LA159_0 <= K_COUNTER)||(LA159_0 >= K_CUSTOM && LA159_0 <= K_DECIMAL)||(LA159_0 >= K_DISTINCT && LA159_0 <= K_DOUBLE)||(LA159_0 >= K_EXISTS && LA159_0 <= K_FLOAT)||LA159_0==K_FROZEN||(LA159_0 >= K_FUNCTION && LA159_0 <= K_FUNCTIONS)||(LA159_0 >= K_INET && LA159_0 <= K_INPUT)||LA159_0==K_INT||(LA159_0 >= K_JSON && LA159_0 <= K_KEYS)||(LA159_0 >= K_KEYSPACES && LA159_0 <= K_LIKE)||(LA159_0 >= K_LIST && LA159_0 <= K_MAP)||(LA159_0 >= K_NAN && LA159_0 <= K_NOLOGIN)||LA159_0==K_NOSUPERUSER||LA159_0==K_NULL||LA159_0==K_OPTIONS||(LA159_0 >= K_PARTITION && LA159_0 <= K_PERMISSIONS)||LA159_0==K_RETURNS||(LA159_0 >= K_ROLE && LA159_0 <= K_ROLES)||(LA159_0 >= K_SFUNC && LA159_0 <= K_TINYINT)||(LA159_0 >= K_TOKEN && LA159_0 <= K_TRIGGER)||(LA159_0 >= K_TTL && LA159_0 <= K_TYPE)||(LA159_0 >= K_USER && LA159_0 <= K_USERS)||(LA159_0 >= K_UUID && LA159_0 <= K_VARINT)||LA159_0==K_WRITETIME||(LA159_0 >= QMARK && LA159_0 <= QUOTED_NAME)||LA159_0==STRING_LITERAL||LA159_0==UUID||LA159_0==174||LA159_0==178||LA159_0==180||LA159_0==187||LA159_0==191) ) {
						alt159=1;
					}
					switch (alt159) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1275:13: t1= term ( ',' tn= term )*
							{
							pushFollow(FOLLOW_term_in_collectionLiteral8416);
							t1=term();
							state._fsp--;

							 l.add(t1); 
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1275:36: ( ',' tn= term )*
							loop158:
							while (true) {
								int alt158=2;
								int LA158_0 = input.LA(1);
								if ( (LA158_0==177) ) {
									alt158=1;
								}

								switch (alt158) {
								case 1 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1275:38: ',' tn= term
									{
									match(input,177,FOLLOW_177_in_collectionLiteral8422); 
									pushFollow(FOLLOW_term_in_collectionLiteral8426);
									tn=term();
									state._fsp--;

									 l.add(tn); 
									}
									break;

								default :
									break loop158;
								}
							}

							}
							break;

					}

					match(input,189,FOLLOW_189_in_collectionLiteral8442); 
					 value = new Lists.Literal(l); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1277:7: '{' t= term v= setOrMapLiteral[t] '}'
					{
					match(input,191,FOLLOW_191_in_collectionLiteral8452); 
					pushFollow(FOLLOW_term_in_collectionLiteral8456);
					t=term();
					state._fsp--;

					pushFollow(FOLLOW_setOrMapLiteral_in_collectionLiteral8460);
					v=setOrMapLiteral(t);
					state._fsp--;

					 value = v; 
					match(input,192,FOLLOW_192_in_collectionLiteral8465); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1280:7: '{' '}'
					{
					match(input,191,FOLLOW_191_in_collectionLiteral8483); 
					match(input,192,FOLLOW_192_in_collectionLiteral8485); 
					 value = new Sets.Literal(Collections.<Term.Raw>emptyList()); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "collectionLiteral"



	// $ANTLR start "usertypeLiteral"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1283:1: usertypeLiteral returns [UserTypes.Literal ut] : '{' k1= noncol_ident ':' v1= term ( ',' kn= noncol_ident ':' vn= term )* '}' ;
	public final UserTypes.Literal usertypeLiteral() throws RecognitionException {
		UserTypes.Literal ut = null;


		ColumnIdentifier k1 =null;
		Term.Raw v1 =null;
		ColumnIdentifier kn =null;
		Term.Raw vn =null;

		 Map<ColumnIdentifier, Term.Raw> m = new HashMap<ColumnIdentifier, Term.Raw>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1287:5: ( '{' k1= noncol_ident ':' v1= term ( ',' kn= noncol_ident ':' vn= term )* '}' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1287:7: '{' k1= noncol_ident ':' v1= term ( ',' kn= noncol_ident ':' vn= term )* '}'
			{
			match(input,191,FOLLOW_191_in_usertypeLiteral8529); 
			pushFollow(FOLLOW_noncol_ident_in_usertypeLiteral8533);
			k1=noncol_ident();
			state._fsp--;

			match(input,180,FOLLOW_180_in_usertypeLiteral8535); 
			pushFollow(FOLLOW_term_in_usertypeLiteral8539);
			v1=term();
			state._fsp--;

			 m.put(k1, v1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1287:58: ( ',' kn= noncol_ident ':' vn= term )*
			loop161:
			while (true) {
				int alt161=2;
				int LA161_0 = input.LA(1);
				if ( (LA161_0==177) ) {
					alt161=1;
				}

				switch (alt161) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1287:60: ',' kn= noncol_ident ':' vn= term
					{
					match(input,177,FOLLOW_177_in_usertypeLiteral8545); 
					pushFollow(FOLLOW_noncol_ident_in_usertypeLiteral8549);
					kn=noncol_ident();
					state._fsp--;

					match(input,180,FOLLOW_180_in_usertypeLiteral8551); 
					pushFollow(FOLLOW_term_in_usertypeLiteral8555);
					vn=term();
					state._fsp--;

					 m.put(kn, vn); 
					}
					break;

				default :
					break loop161;
				}
			}

			match(input,192,FOLLOW_192_in_usertypeLiteral8562); 
			}

			 ut = new UserTypes.Literal(m); 
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ut;
	}
	// $ANTLR end "usertypeLiteral"



	// $ANTLR start "tupleLiteral"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1290:1: tupleLiteral returns [Tuples.Literal tt] : '(' t1= term ( ',' tn= term )* ')' ;
	public final Tuples.Literal tupleLiteral() throws RecognitionException {
		Tuples.Literal tt = null;


		Term.Raw t1 =null;
		Term.Raw tn =null;

		 List<Term.Raw> l = new ArrayList<Term.Raw>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1293:5: ( '(' t1= term ( ',' tn= term )* ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1293:7: '(' t1= term ( ',' tn= term )* ')'
			{
			match(input,174,FOLLOW_174_in_tupleLiteral8599); 
			pushFollow(FOLLOW_term_in_tupleLiteral8603);
			t1=term();
			state._fsp--;

			 l.add(t1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1293:34: ( ',' tn= term )*
			loop162:
			while (true) {
				int alt162=2;
				int LA162_0 = input.LA(1);
				if ( (LA162_0==177) ) {
					alt162=1;
				}

				switch (alt162) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1293:36: ',' tn= term
					{
					match(input,177,FOLLOW_177_in_tupleLiteral8609); 
					pushFollow(FOLLOW_term_in_tupleLiteral8613);
					tn=term();
					state._fsp--;

					 l.add(tn); 
					}
					break;

				default :
					break loop162;
				}
			}

			match(input,175,FOLLOW_175_in_tupleLiteral8620); 
			}

			 tt = new Tuples.Literal(l); 
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return tt;
	}
	// $ANTLR end "tupleLiteral"



	// $ANTLR start "value"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1296:1: value returns [Term.Raw value] : (c= constant |l= collectionLiteral |u= usertypeLiteral |t= tupleLiteral | K_NULL | ':' id= noncol_ident | QMARK );
	public final Term.Raw value() throws RecognitionException {
		Term.Raw value = null;


		Constants.Literal c =null;
		Term.Raw l =null;
		UserTypes.Literal u =null;
		Tuples.Literal t =null;
		ColumnIdentifier id =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1297:5: (c= constant |l= collectionLiteral |u= usertypeLiteral |t= tupleLiteral | K_NULL | ':' id= noncol_ident | QMARK )
			int alt163=7;
			alt163 = dfa163.predict(input);
			switch (alt163) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1297:7: c= constant
					{
					pushFollow(FOLLOW_constant_in_value8643);
					c=constant();
					state._fsp--;

					 value = c; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1298:7: l= collectionLiteral
					{
					pushFollow(FOLLOW_collectionLiteral_in_value8665);
					l=collectionLiteral();
					state._fsp--;

					 value = l; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1299:7: u= usertypeLiteral
					{
					pushFollow(FOLLOW_usertypeLiteral_in_value8678);
					u=usertypeLiteral();
					state._fsp--;

					 value = u; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1300:7: t= tupleLiteral
					{
					pushFollow(FOLLOW_tupleLiteral_in_value8693);
					t=tupleLiteral();
					state._fsp--;

					 value = t; 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1301:7: K_NULL
					{
					match(input,K_NULL,FOLLOW_K_NULL_in_value8709); 
					 value = Constants.NULL_LITERAL; 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1302:7: ':' id= noncol_ident
					{
					match(input,180,FOLLOW_180_in_value8733); 
					pushFollow(FOLLOW_noncol_ident_in_value8737);
					id=noncol_ident();
					state._fsp--;

					 value = newBindVariables(id); 
					}
					break;
				case 7 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1303:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_value8748); 
					 value = newBindVariables(null); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "value"



	// $ANTLR start "intValue"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1306:1: intValue returns [Term.Raw value] : (|t= INTEGER | ':' id= noncol_ident | QMARK );
	public final Term.Raw intValue() throws RecognitionException {
		Term.Raw value = null;


		Token t=null;
		ColumnIdentifier id =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1307:5: (|t= INTEGER | ':' id= noncol_ident | QMARK )
			int alt164=4;
			switch ( input.LA(1) ) {
			case EOF:
			case K_ALLOW:
			case K_AND:
			case K_APPLY:
			case K_DELETE:
			case K_INSERT:
			case K_LIMIT:
			case K_SET:
			case K_UPDATE:
			case K_WHERE:
			case 181:
				{
				alt164=1;
				}
				break;
			case INTEGER:
				{
				alt164=2;
				}
				break;
			case 180:
				{
				alt164=3;
				}
				break;
			case QMARK:
				{
				alt164=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 164, 0, input);
				throw nvae;
			}
			switch (alt164) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1308:5: 
					{
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1308:7: t= INTEGER
					{
					t=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_intValue8794); 
					 value = Constants.Literal.integer((t!=null?t.getText():null)); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1309:7: ':' id= noncol_ident
					{
					match(input,180,FOLLOW_180_in_intValue8808); 
					pushFollow(FOLLOW_noncol_ident_in_intValue8812);
					id=noncol_ident();
					state._fsp--;

					 value = newBindVariables(id); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1310:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_intValue8823); 
					 value = newBindVariables(null); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return value;
	}
	// $ANTLR end "intValue"



	// $ANTLR start "functionName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1313:1: functionName returns [FunctionName s] : (ks= keyspaceName '.' )? f= allowedFunctionName ;
	public final FunctionName functionName() throws RecognitionException {
		FunctionName s = null;


		String ks =null;
		String f =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1314:5: ( (ks= keyspaceName '.' )? f= allowedFunctionName )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1314:7: (ks= keyspaceName '.' )? f= allowedFunctionName
			{
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1314:7: (ks= keyspaceName '.' )?
			int alt165=2;
			alt165 = dfa165.predict(input);
			switch (alt165) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1314:8: ks= keyspaceName '.'
					{
					pushFollow(FOLLOW_keyspaceName_in_functionName8857);
					ks=keyspaceName();
					state._fsp--;

					match(input,179,FOLLOW_179_in_functionName8859); 
					}
					break;

			}

			pushFollow(FOLLOW_allowedFunctionName_in_functionName8865);
			f=allowedFunctionName();
			state._fsp--;

			 s = new FunctionName(ks, f); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return s;
	}
	// $ANTLR end "functionName"



	// $ANTLR start "allowedFunctionName"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1317:1: allowedFunctionName returns [String s] : (f= IDENT |f= QUOTED_NAME |u= unreserved_function_keyword | K_TOKEN | K_COUNT );
	public final String allowedFunctionName() throws RecognitionException {
		String s = null;


		Token f=null;
		String u =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1318:5: (f= IDENT |f= QUOTED_NAME |u= unreserved_function_keyword | K_TOKEN | K_COUNT )
			int alt166=5;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt166=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt166=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_ASCII:
			case K_BIGINT:
			case K_BLOB:
			case K_BOOLEAN:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_COUNTER:
			case K_CUSTOM:
			case K_DATE:
			case K_DECIMAL:
			case K_DISTINCT:
			case K_DOUBLE:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FLOAT:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INET:
			case K_INITCOND:
			case K_INPUT:
			case K_INT:
			case K_JSON:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_SMALLINT:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TEXT:
			case K_TIME:
			case K_TIMESTAMP:
			case K_TIMEUUID:
			case K_TINYINT:
			case K_TRIGGER:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_UUID:
			case K_VALUES:
			case K_VARCHAR:
			case K_VARINT:
				{
				alt166=3;
				}
				break;
			case K_TOKEN:
				{
				alt166=4;
				}
				break;
			case K_COUNT:
				{
				alt166=5;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 166, 0, input);
				throw nvae;
			}
			switch (alt166) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1318:7: f= IDENT
					{
					f=(Token)match(input,IDENT,FOLLOW_IDENT_in_allowedFunctionName8892); 
					 s = (f!=null?f.getText():null).toLowerCase(); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1319:7: f= QUOTED_NAME
					{
					f=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_allowedFunctionName8926); 
					 s = (f!=null?f.getText():null); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1320:7: u= unreserved_function_keyword
					{
					pushFollow(FOLLOW_unreserved_function_keyword_in_allowedFunctionName8954);
					u=unreserved_function_keyword();
					state._fsp--;

					 s = u; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1321:7: K_TOKEN
					{
					match(input,K_TOKEN,FOLLOW_K_TOKEN_in_allowedFunctionName8964); 
					 s = "token"; 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1322:7: K_COUNT
					{
					match(input,K_COUNT,FOLLOW_K_COUNT_in_allowedFunctionName8996); 
					 s = "count"; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return s;
	}
	// $ANTLR end "allowedFunctionName"



	// $ANTLR start "function"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1325:1: function returns [Term.Raw t] : (f= functionName '(' ')' |f= functionName '(' args= functionArgs ')' );
	public final Term.Raw function() throws RecognitionException {
		Term.Raw t = null;


		FunctionName f =null;
		List<Term.Raw> args =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1326:5: (f= functionName '(' ')' |f= functionName '(' args= functionArgs ')' )
			int alt167=2;
			alt167 = dfa167.predict(input);
			switch (alt167) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1326:7: f= functionName '(' ')'
					{
					pushFollow(FOLLOW_functionName_in_function9043);
					f=functionName();
					state._fsp--;

					match(input,174,FOLLOW_174_in_function9045); 
					match(input,175,FOLLOW_175_in_function9047); 
					 t = new FunctionCall.Raw(f, Collections.<Term.Raw>emptyList()); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1327:7: f= functionName '(' args= functionArgs ')'
					{
					pushFollow(FOLLOW_functionName_in_function9077);
					f=functionName();
					state._fsp--;

					match(input,174,FOLLOW_174_in_function9079); 
					pushFollow(FOLLOW_functionArgs_in_function9083);
					args=functionArgs();
					state._fsp--;

					match(input,175,FOLLOW_175_in_function9085); 
					 t = new FunctionCall.Raw(f, args); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return t;
	}
	// $ANTLR end "function"



	// $ANTLR start "functionArgs"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1330:1: functionArgs returns [List<Term.Raw> args] : t1= term ( ',' tn= term )* ;
	public final List<Term.Raw> functionArgs() throws RecognitionException {
		List<Term.Raw> args = null;


		Term.Raw t1 =null;
		Term.Raw tn =null;

		 args = new ArrayList<Term.Raw>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1332:5: (t1= term ( ',' tn= term )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1332:7: t1= term ( ',' tn= term )*
			{
			pushFollow(FOLLOW_term_in_functionArgs9118);
			t1=term();
			state._fsp--;

			args.add(t1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1332:32: ( ',' tn= term )*
			loop168:
			while (true) {
				int alt168=2;
				int LA168_0 = input.LA(1);
				if ( (LA168_0==177) ) {
					alt168=1;
				}

				switch (alt168) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1332:34: ',' tn= term
					{
					match(input,177,FOLLOW_177_in_functionArgs9124); 
					pushFollow(FOLLOW_term_in_functionArgs9128);
					tn=term();
					state._fsp--;

					 args.add(tn); 
					}
					break;

				default :
					break loop168;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return args;
	}
	// $ANTLR end "functionArgs"



	// $ANTLR start "term"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1335:1: term returns [Term.Raw term] : (v= value |f= function | '(' c= comparatorType ')' t= term );
	public final Term.Raw term() throws RecognitionException {
		Term.Raw term = null;


		Term.Raw v =null;
		Term.Raw f =null;
		CQL3Type.Raw c =null;
		Term.Raw t =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1336:5: (v= value |f= function | '(' c= comparatorType ')' t= term )
			int alt169=3;
			alt169 = dfa169.predict(input);
			switch (alt169) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1336:7: v= value
					{
					pushFollow(FOLLOW_value_in_term9156);
					v=value();
					state._fsp--;

					 term = v; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1337:7: f= function
					{
					pushFollow(FOLLOW_function_in_term9193);
					f=function();
					state._fsp--;

					 term = f; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1338:7: '(' c= comparatorType ')' t= term
					{
					match(input,174,FOLLOW_174_in_term9225); 
					pushFollow(FOLLOW_comparatorType_in_term9229);
					c=comparatorType();
					state._fsp--;

					match(input,175,FOLLOW_175_in_term9231); 
					pushFollow(FOLLOW_term_in_term9235);
					t=term();
					state._fsp--;

					 term = new TypeCast(c, t); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return term;
	}
	// $ANTLR end "term"



	// $ANTLR start "columnOperation"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1341:1: columnOperation[List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations] : key= cident columnOperationDifferentiator[operations, key] ;
	public final void columnOperation(List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations) throws RecognitionException {
		ColumnIdentifier.Raw key =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1342:5: (key= cident columnOperationDifferentiator[operations, key] )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1342:7: key= cident columnOperationDifferentiator[operations, key]
			{
			pushFollow(FOLLOW_cident_in_columnOperation9258);
			key=cident();
			state._fsp--;

			pushFollow(FOLLOW_columnOperationDifferentiator_in_columnOperation9260);
			columnOperationDifferentiator(operations, key);
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "columnOperation"



	// $ANTLR start "columnOperationDifferentiator"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1345:1: columnOperationDifferentiator[List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key] : ( '=' normalColumnOperation[operations, key] | '[' k= term ']' specializedColumnOperation[operations, key, k] );
	public final void columnOperationDifferentiator(List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key) throws RecognitionException {
		Term.Raw k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1346:5: ( '=' normalColumnOperation[operations, key] | '[' k= term ']' specializedColumnOperation[operations, key, k] )
			int alt170=2;
			int LA170_0 = input.LA(1);
			if ( (LA170_0==184) ) {
				alt170=1;
			}
			else if ( (LA170_0==187) ) {
				alt170=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 170, 0, input);
				throw nvae;
			}

			switch (alt170) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1346:7: '=' normalColumnOperation[operations, key]
					{
					match(input,184,FOLLOW_184_in_columnOperationDifferentiator9279); 
					pushFollow(FOLLOW_normalColumnOperation_in_columnOperationDifferentiator9281);
					normalColumnOperation(operations, key);
					state._fsp--;

					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1347:7: '[' k= term ']' specializedColumnOperation[operations, key, k]
					{
					match(input,187,FOLLOW_187_in_columnOperationDifferentiator9290); 
					pushFollow(FOLLOW_term_in_columnOperationDifferentiator9294);
					k=term();
					state._fsp--;

					match(input,189,FOLLOW_189_in_columnOperationDifferentiator9296); 
					pushFollow(FOLLOW_specializedColumnOperation_in_columnOperationDifferentiator9298);
					specializedColumnOperation(operations, key, k);
					state._fsp--;

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "columnOperationDifferentiator"



	// $ANTLR start "normalColumnOperation"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1350:1: normalColumnOperation[List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key] : (t= term ( '+' c= cident )? |c= cident sig= ( '+' | '-' ) t= term |c= cident i= INTEGER );
	public final void normalColumnOperation(List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key) throws RecognitionException {
		Token sig=null;
		Token i=null;
		Term.Raw t =null;
		ColumnIdentifier.Raw c =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1351:5: (t= term ( '+' c= cident )? |c= cident sig= ( '+' | '-' ) t= term |c= cident i= INTEGER )
			int alt172=3;
			alt172 = dfa172.predict(input);
			switch (alt172) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1351:7: t= term ( '+' c= cident )?
					{
					pushFollow(FOLLOW_term_in_normalColumnOperation9319);
					t=term();
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1351:14: ( '+' c= cident )?
					int alt171=2;
					int LA171_0 = input.LA(1);
					if ( (LA171_0==176) ) {
						alt171=1;
					}
					switch (alt171) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1351:15: '+' c= cident
							{
							match(input,176,FOLLOW_176_in_normalColumnOperation9322); 
							pushFollow(FOLLOW_cident_in_normalColumnOperation9326);
							c=cident();
							state._fsp--;

							}
							break;

					}


					          if (c == null)
					          {
					              addRawUpdate(operations, key, new Operation.SetValue(t));
					          }
					          else
					          {
					              if (!key.equals(c))
					                  addRecognitionError("Only expressions of the form X = <value> + X are supported.");
					              addRawUpdate(operations, key, new Operation.Prepend(t));
					          }
					      
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1364:7: c= cident sig= ( '+' | '-' ) t= term
					{
					pushFollow(FOLLOW_cident_in_normalColumnOperation9347);
					c=cident();
					state._fsp--;

					sig=input.LT(1);
					if ( input.LA(1)==176||input.LA(1)==178 ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_term_in_normalColumnOperation9361);
					t=term();
					state._fsp--;


					          if (!key.equals(c))
					              addRecognitionError("Only expressions of the form X = X " + (sig!=null?sig.getText():null) + "<value> are supported.");
					          addRawUpdate(operations, key, (sig!=null?sig.getText():null).equals("+") ? new Operation.Addition(t) : new Operation.Substraction(t));
					      
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1370:7: c= cident i= INTEGER
					{
					pushFollow(FOLLOW_cident_in_normalColumnOperation9379);
					c=cident();
					state._fsp--;

					i=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_normalColumnOperation9383); 

					          // Note that this production *is* necessary because X = X - 3 will in fact be lexed as [ X, '=', X, INTEGER].
					          if (!key.equals(c))
					              // We don't yet allow a '+' in front of an integer, but we could in the future really, so let's be future-proof in our error message
					              addRecognitionError("Only expressions of the form X = X " + ((i!=null?i.getText():null).charAt(0) == '-' ? '-' : '+') + " <value> are supported.");
					          addRawUpdate(operations, key, new Operation.Addition(Constants.Literal.integer((i!=null?i.getText():null))));
					      
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "normalColumnOperation"



	// $ANTLR start "specializedColumnOperation"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1380:1: specializedColumnOperation[List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key, Term.Raw k] : '=' t= term ;
	public final void specializedColumnOperation(List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key, Term.Raw k) throws RecognitionException {
		Term.Raw t =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1381:5: ( '=' t= term )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1381:7: '=' t= term
			{
			match(input,184,FOLLOW_184_in_specializedColumnOperation9409); 
			pushFollow(FOLLOW_term_in_specializedColumnOperation9413);
			t=term();
			state._fsp--;


			          addRawUpdate(operations, key, new Operation.SetElement(k, t));
			      
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "specializedColumnOperation"



	// $ANTLR start "columnCondition"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1387:1: columnCondition[List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> conditions] : key= cident (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) | '[' element= term ']' (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) ) ) ;
	public final void columnCondition(List<Pair<ColumnIdentifier.Raw, ColumnCondition.Raw>> conditions) throws RecognitionException {
		ColumnIdentifier.Raw key =null;
		Operator op =null;
		Term.Raw t =null;
		List<Term.Raw> values =null;
		AbstractMarker.INRaw marker =null;
		Term.Raw element =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1389:5: (key= cident (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) | '[' element= term ']' (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) ) ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1389:7: key= cident (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) | '[' element= term ']' (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) ) )
			{
			pushFollow(FOLLOW_cident_in_columnCondition9446);
			key=cident();
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1390:9: (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) | '[' element= term ']' (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) ) )
			int alt176=3;
			switch ( input.LA(1) ) {
			case 173:
			case 182:
			case 183:
			case 184:
			case 185:
			case 186:
				{
				alt176=1;
				}
				break;
			case K_IN:
				{
				alt176=2;
				}
				break;
			case 187:
				{
				alt176=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 176, 0, input);
				throw nvae;
			}
			switch (alt176) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1390:11: op= relationType t= term
					{
					pushFollow(FOLLOW_relationType_in_columnCondition9460);
					op=relationType();
					state._fsp--;

					pushFollow(FOLLOW_term_in_columnCondition9464);
					t=term();
					state._fsp--;

					 conditions.add(Pair.create(key, ColumnCondition.Raw.simpleCondition(t, op))); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1391:11: K_IN (values= singleColumnInValues |marker= inMarker )
					{
					match(input,K_IN,FOLLOW_K_IN_in_columnCondition9478); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1392:13: (values= singleColumnInValues |marker= inMarker )
					int alt173=2;
					int LA173_0 = input.LA(1);
					if ( (LA173_0==174) ) {
						alt173=1;
					}
					else if ( (LA173_0==QMARK||LA173_0==180) ) {
						alt173=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 173, 0, input);
						throw nvae;
					}

					switch (alt173) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1392:15: values= singleColumnInValues
							{
							pushFollow(FOLLOW_singleColumnInValues_in_columnCondition9496);
							values=singleColumnInValues();
							state._fsp--;

							 conditions.add(Pair.create(key, ColumnCondition.Raw.simpleInCondition(values))); 
							}
							break;
						case 2 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1393:15: marker= inMarker
							{
							pushFollow(FOLLOW_inMarker_in_columnCondition9516);
							marker=inMarker();
							state._fsp--;

							 conditions.add(Pair.create(key, ColumnCondition.Raw.simpleInCondition(marker))); 
							}
							break;

					}

					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1395:11: '[' element= term ']' (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) )
					{
					match(input,187,FOLLOW_187_in_columnCondition9544); 
					pushFollow(FOLLOW_term_in_columnCondition9548);
					element=term();
					state._fsp--;

					match(input,189,FOLLOW_189_in_columnCondition9550); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1396:13: (op= relationType t= term | K_IN (values= singleColumnInValues |marker= inMarker ) )
					int alt175=2;
					int LA175_0 = input.LA(1);
					if ( (LA175_0==173||(LA175_0 >= 182 && LA175_0 <= 186)) ) {
						alt175=1;
					}
					else if ( (LA175_0==K_IN) ) {
						alt175=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 175, 0, input);
						throw nvae;
					}

					switch (alt175) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1396:15: op= relationType t= term
							{
							pushFollow(FOLLOW_relationType_in_columnCondition9568);
							op=relationType();
							state._fsp--;

							pushFollow(FOLLOW_term_in_columnCondition9572);
							t=term();
							state._fsp--;

							 conditions.add(Pair.create(key, ColumnCondition.Raw.collectionCondition(t, element, op))); 
							}
							break;
						case 2 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1397:15: K_IN (values= singleColumnInValues |marker= inMarker )
							{
							match(input,K_IN,FOLLOW_K_IN_in_columnCondition9590); 
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1398:17: (values= singleColumnInValues |marker= inMarker )
							int alt174=2;
							int LA174_0 = input.LA(1);
							if ( (LA174_0==174) ) {
								alt174=1;
							}
							else if ( (LA174_0==QMARK||LA174_0==180) ) {
								alt174=2;
							}

							else {
								NoViableAltException nvae =
									new NoViableAltException("", 174, 0, input);
								throw nvae;
							}

							switch (alt174) {
								case 1 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1398:19: values= singleColumnInValues
									{
									pushFollow(FOLLOW_singleColumnInValues_in_columnCondition9612);
									values=singleColumnInValues();
									state._fsp--;

									 conditions.add(Pair.create(key, ColumnCondition.Raw.collectionInCondition(element, values))); 
									}
									break;
								case 2 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1399:19: marker= inMarker
									{
									pushFollow(FOLLOW_inMarker_in_columnCondition9636);
									marker=inMarker();
									state._fsp--;

									 conditions.add(Pair.create(key, ColumnCondition.Raw.collectionInCondition(element, marker))); 
									}
									break;

							}

							}
							break;

					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "columnCondition"



	// $ANTLR start "properties"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1405:1: properties[PropertyDefinitions props] : property[props] ( K_AND property[props] )* ;
	public final void properties(PropertyDefinitions props) throws RecognitionException {
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1406:5: ( property[props] ( K_AND property[props] )* )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1406:7: property[props] ( K_AND property[props] )*
			{
			pushFollow(FOLLOW_property_in_properties9698);
			property(props);
			state._fsp--;

			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1406:23: ( K_AND property[props] )*
			loop177:
			while (true) {
				int alt177=2;
				int LA177_0 = input.LA(1);
				if ( (LA177_0==K_AND) ) {
					alt177=1;
				}

				switch (alt177) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1406:24: K_AND property[props]
					{
					match(input,K_AND,FOLLOW_K_AND_in_properties9702); 
					pushFollow(FOLLOW_property_in_properties9704);
					property(props);
					state._fsp--;

					}
					break;

				default :
					break loop177;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "properties"



	// $ANTLR start "property"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1409:1: property[PropertyDefinitions props] : (k= noncol_ident '=' simple= propertyValue |k= noncol_ident '=' map= mapLiteral );
	public final void property(PropertyDefinitions props) throws RecognitionException {
		ColumnIdentifier k =null;
		String simple =null;
		Maps.Literal map =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1410:5: (k= noncol_ident '=' simple= propertyValue |k= noncol_ident '=' map= mapLiteral )
			int alt178=2;
			alt178 = dfa178.predict(input);
			switch (alt178) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1410:7: k= noncol_ident '=' simple= propertyValue
					{
					pushFollow(FOLLOW_noncol_ident_in_property9727);
					k=noncol_ident();
					state._fsp--;

					match(input,184,FOLLOW_184_in_property9729); 
					pushFollow(FOLLOW_propertyValue_in_property9733);
					simple=propertyValue();
					state._fsp--;

					 try { props.addProperty(k.toString(), simple); } catch (SyntaxException e) { addRecognitionError(e.getMessage()); } 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1411:7: k= noncol_ident '=' map= mapLiteral
					{
					pushFollow(FOLLOW_noncol_ident_in_property9745);
					k=noncol_ident();
					state._fsp--;

					match(input,184,FOLLOW_184_in_property9747); 
					pushFollow(FOLLOW_mapLiteral_in_property9751);
					map=mapLiteral();
					state._fsp--;

					 try { props.addProperty(k.toString(), convertPropertyMap(map)); } catch (SyntaxException e) { addRecognitionError(e.getMessage()); } 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "property"



	// $ANTLR start "propertyValue"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1414:1: propertyValue returns [String str] : (c= constant |u= unreserved_keyword );
	public final String propertyValue() throws RecognitionException {
		String str = null;


		Constants.Literal c =null;
		String u =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1415:5: (c= constant |u= unreserved_keyword )
			int alt179=2;
			int LA179_0 = input.LA(1);
			if ( (LA179_0==BOOLEAN||LA179_0==FLOAT||LA179_0==HEXNUMBER||LA179_0==INTEGER||LA179_0==K_INFINITY||LA179_0==K_NAN||LA179_0==STRING_LITERAL||LA179_0==UUID||LA179_0==178) ) {
				alt179=1;
			}
			else if ( ((LA179_0 >= K_AGGREGATE && LA179_0 <= K_ALL)||LA179_0==K_AS||LA179_0==K_ASCII||(LA179_0 >= K_BIGINT && LA179_0 <= K_BOOLEAN)||(LA179_0 >= K_CALLED && LA179_0 <= K_CLUSTERING)||(LA179_0 >= K_COMPACT && LA179_0 <= K_COUNTER)||(LA179_0 >= K_CUSTOM && LA179_0 <= K_DECIMAL)||(LA179_0 >= K_DISTINCT && LA179_0 <= K_DOUBLE)||(LA179_0 >= K_EXISTS && LA179_0 <= K_FLOAT)||LA179_0==K_FROZEN||(LA179_0 >= K_FUNCTION && LA179_0 <= K_FUNCTIONS)||LA179_0==K_INET||(LA179_0 >= K_INITCOND && LA179_0 <= K_INPUT)||LA179_0==K_INT||(LA179_0 >= K_JSON && LA179_0 <= K_KEYS)||(LA179_0 >= K_KEYSPACES && LA179_0 <= K_LIKE)||(LA179_0 >= K_LIST && LA179_0 <= K_MAP)||LA179_0==K_NOLOGIN||LA179_0==K_NOSUPERUSER||LA179_0==K_OPTIONS||(LA179_0 >= K_PARTITION && LA179_0 <= K_PERMISSIONS)||LA179_0==K_RETURNS||(LA179_0 >= K_ROLE && LA179_0 <= K_ROLES)||(LA179_0 >= K_SFUNC && LA179_0 <= K_TINYINT)||LA179_0==K_TRIGGER||(LA179_0 >= K_TTL && LA179_0 <= K_TYPE)||(LA179_0 >= K_USER && LA179_0 <= K_USERS)||(LA179_0 >= K_UUID && LA179_0 <= K_VARINT)||LA179_0==K_WRITETIME) ) {
				alt179=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 179, 0, input);
				throw nvae;
			}

			switch (alt179) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1415:7: c= constant
					{
					pushFollow(FOLLOW_constant_in_propertyValue9776);
					c=constant();
					state._fsp--;

					 str = c.getRawText(); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1416:7: u= unreserved_keyword
					{
					pushFollow(FOLLOW_unreserved_keyword_in_propertyValue9798);
					u=unreserved_keyword();
					state._fsp--;

					 str = u; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return str;
	}
	// $ANTLR end "propertyValue"



	// $ANTLR start "relationType"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1419:1: relationType returns [Operator op] : ( '=' | '<' | '<=' | '>' | '>=' | '!=' );
	public final Operator relationType() throws RecognitionException {
		Operator op = null;


		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1420:5: ( '=' | '<' | '<=' | '>' | '>=' | '!=' )
			int alt180=6;
			switch ( input.LA(1) ) {
			case 184:
				{
				alt180=1;
				}
				break;
			case 182:
				{
				alt180=2;
				}
				break;
			case 183:
				{
				alt180=3;
				}
				break;
			case 185:
				{
				alt180=4;
				}
				break;
			case 186:
				{
				alt180=5;
				}
				break;
			case 173:
				{
				alt180=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 180, 0, input);
				throw nvae;
			}
			switch (alt180) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1420:7: '='
					{
					match(input,184,FOLLOW_184_in_relationType9821); 
					 op = Operator.EQ; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1421:7: '<'
					{
					match(input,182,FOLLOW_182_in_relationType9832); 
					 op = Operator.LT; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1422:7: '<='
					{
					match(input,183,FOLLOW_183_in_relationType9843); 
					 op = Operator.LTE; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1423:7: '>'
					{
					match(input,185,FOLLOW_185_in_relationType9853); 
					 op = Operator.GT; 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1424:7: '>='
					{
					match(input,186,FOLLOW_186_in_relationType9864); 
					 op = Operator.GTE; 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1425:7: '!='
					{
					match(input,173,FOLLOW_173_in_relationType9874); 
					 op = Operator.NEQ; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return op;
	}
	// $ANTLR end "relationType"



	// $ANTLR start "relation"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1428:1: relation[WhereClause.Builder clauses] : (name= cident type= relationType t= term |name= cident K_IS K_NOT K_NULL | K_TOKEN l= tupleOfIdentifiers type= relationType t= term |name= cident K_IN marker= inMarker |name= cident K_IN inValues= singleColumnInValues |name= cident K_CONTAINS ( K_KEY )? t= term |name= cident '[' key= term ']' type= relationType t= term |ids= tupleOfIdentifiers ( K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples ) |type= relationType literal= tupleLiteral |type= relationType tupleMarker= markerForTuple ) | '(' relation[$clauses] ')' );
	public final void relation(WhereClause.Builder clauses) throws RecognitionException {
		ColumnIdentifier.Raw name =null;
		Operator type =null;
		Term.Raw t =null;
		List<ColumnIdentifier.Raw> l =null;
		AbstractMarker.INRaw marker =null;
		List<Term.Raw> inValues =null;
		Term.Raw key =null;
		List<ColumnIdentifier.Raw> ids =null;
		Tuples.INRaw tupleInMarker =null;
		List<Tuples.Literal> literals =null;
		List<Tuples.Raw> markers =null;
		Tuples.Literal literal =null;
		Tuples.Raw tupleMarker =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1429:5: (name= cident type= relationType t= term |name= cident K_IS K_NOT K_NULL | K_TOKEN l= tupleOfIdentifiers type= relationType t= term |name= cident K_IN marker= inMarker |name= cident K_IN inValues= singleColumnInValues |name= cident K_CONTAINS ( K_KEY )? t= term |name= cident '[' key= term ']' type= relationType t= term |ids= tupleOfIdentifiers ( K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples ) |type= relationType literal= tupleLiteral |type= relationType tupleMarker= markerForTuple ) | '(' relation[$clauses] ')' )
			int alt184=9;
			alt184 = dfa184.predict(input);
			switch (alt184) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1429:7: name= cident type= relationType t= term
					{
					pushFollow(FOLLOW_cident_in_relation9896);
					name=cident();
					state._fsp--;

					pushFollow(FOLLOW_relationType_in_relation9900);
					type=relationType();
					state._fsp--;

					pushFollow(FOLLOW_term_in_relation9904);
					t=term();
					state._fsp--;

					 clauses.add(new SingleColumnRelation(name, type, t)); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1430:7: name= cident K_IS K_NOT K_NULL
					{
					pushFollow(FOLLOW_cident_in_relation9916);
					name=cident();
					state._fsp--;

					match(input,K_IS,FOLLOW_K_IS_in_relation9918); 
					match(input,K_NOT,FOLLOW_K_NOT_in_relation9920); 
					match(input,K_NULL,FOLLOW_K_NULL_in_relation9922); 
					 clauses.add(new SingleColumnRelation(name, Operator.IS_NOT, Constants.NULL_LITERAL)); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1431:7: K_TOKEN l= tupleOfIdentifiers type= relationType t= term
					{
					match(input,K_TOKEN,FOLLOW_K_TOKEN_in_relation9932); 
					pushFollow(FOLLOW_tupleOfIdentifiers_in_relation9936);
					l=tupleOfIdentifiers();
					state._fsp--;

					pushFollow(FOLLOW_relationType_in_relation9940);
					type=relationType();
					state._fsp--;

					pushFollow(FOLLOW_term_in_relation9944);
					t=term();
					state._fsp--;

					 clauses.add(new TokenRelation(l, type, t)); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1433:7: name= cident K_IN marker= inMarker
					{
					pushFollow(FOLLOW_cident_in_relation9964);
					name=cident();
					state._fsp--;

					match(input,K_IN,FOLLOW_K_IN_in_relation9966); 
					pushFollow(FOLLOW_inMarker_in_relation9970);
					marker=inMarker();
					state._fsp--;

					 clauses.add(new SingleColumnRelation(name, Operator.IN, marker)); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1435:7: name= cident K_IN inValues= singleColumnInValues
					{
					pushFollow(FOLLOW_cident_in_relation9990);
					name=cident();
					state._fsp--;

					match(input,K_IN,FOLLOW_K_IN_in_relation9992); 
					pushFollow(FOLLOW_singleColumnInValues_in_relation9996);
					inValues=singleColumnInValues();
					state._fsp--;

					 clauses.add(SingleColumnRelation.createInRelation(name, inValues)); 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1437:7: name= cident K_CONTAINS ( K_KEY )? t= term
					{
					pushFollow(FOLLOW_cident_in_relation10016);
					name=cident();
					state._fsp--;

					match(input,K_CONTAINS,FOLLOW_K_CONTAINS_in_relation10018); 
					 Operator rt = Operator.CONTAINS; 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1437:67: ( K_KEY )?
					int alt181=2;
					int LA181_0 = input.LA(1);
					if ( (LA181_0==K_KEY) ) {
						int LA181_1 = input.LA(2);
						if ( (LA181_1==BOOLEAN||LA181_1==FLOAT||LA181_1==HEXNUMBER||(LA181_1 >= IDENT && LA181_1 <= INTEGER)||(LA181_1 >= K_AGGREGATE && LA181_1 <= K_ALL)||LA181_1==K_AS||LA181_1==K_ASCII||(LA181_1 >= K_BIGINT && LA181_1 <= K_BOOLEAN)||(LA181_1 >= K_CALLED && LA181_1 <= K_CLUSTERING)||(LA181_1 >= K_COMPACT && LA181_1 <= K_COUNTER)||(LA181_1 >= K_CUSTOM && LA181_1 <= K_DECIMAL)||(LA181_1 >= K_DISTINCT && LA181_1 <= K_DOUBLE)||(LA181_1 >= K_EXISTS && LA181_1 <= K_FLOAT)||LA181_1==K_FROZEN||(LA181_1 >= K_FUNCTION && LA181_1 <= K_FUNCTIONS)||(LA181_1 >= K_INET && LA181_1 <= K_INPUT)||LA181_1==K_INT||(LA181_1 >= K_JSON && LA181_1 <= K_KEYS)||(LA181_1 >= K_KEYSPACES && LA181_1 <= K_LIKE)||(LA181_1 >= K_LIST && LA181_1 <= K_MAP)||(LA181_1 >= K_NAN && LA181_1 <= K_NOLOGIN)||LA181_1==K_NOSUPERUSER||LA181_1==K_NULL||LA181_1==K_OPTIONS||(LA181_1 >= K_PARTITION && LA181_1 <= K_PERMISSIONS)||LA181_1==K_RETURNS||(LA181_1 >= K_ROLE && LA181_1 <= K_ROLES)||(LA181_1 >= K_SFUNC && LA181_1 <= K_TINYINT)||(LA181_1 >= K_TOKEN && LA181_1 <= K_TRIGGER)||(LA181_1 >= K_TTL && LA181_1 <= K_TYPE)||(LA181_1 >= K_USER && LA181_1 <= K_USERS)||(LA181_1 >= K_UUID && LA181_1 <= K_VARINT)||LA181_1==K_WRITETIME||(LA181_1 >= QMARK && LA181_1 <= QUOTED_NAME)||LA181_1==STRING_LITERAL||LA181_1==UUID||LA181_1==174||LA181_1==178||LA181_1==180||LA181_1==187||LA181_1==191) ) {
							alt181=1;
						}
					}
					switch (alt181) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1437:68: K_KEY
							{
							match(input,K_KEY,FOLLOW_K_KEY_in_relation10023); 
							 rt = Operator.CONTAINS_KEY; 
							}
							break;

					}

					pushFollow(FOLLOW_term_in_relation10039);
					t=term();
					state._fsp--;

					 clauses.add(new SingleColumnRelation(name, rt, t)); 
					}
					break;
				case 7 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1439:7: name= cident '[' key= term ']' type= relationType t= term
					{
					pushFollow(FOLLOW_cident_in_relation10051);
					name=cident();
					state._fsp--;

					match(input,187,FOLLOW_187_in_relation10053); 
					pushFollow(FOLLOW_term_in_relation10057);
					key=term();
					state._fsp--;

					match(input,189,FOLLOW_189_in_relation10059); 
					pushFollow(FOLLOW_relationType_in_relation10063);
					type=relationType();
					state._fsp--;

					pushFollow(FOLLOW_term_in_relation10067);
					t=term();
					state._fsp--;

					 clauses.add(new SingleColumnRelation(name, key, type, t)); 
					}
					break;
				case 8 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1440:7: ids= tupleOfIdentifiers ( K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples ) |type= relationType literal= tupleLiteral |type= relationType tupleMarker= markerForTuple )
					{
					pushFollow(FOLLOW_tupleOfIdentifiers_in_relation10079);
					ids=tupleOfIdentifiers();
					state._fsp--;

					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1441:7: ( K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples ) |type= relationType literal= tupleLiteral |type= relationType tupleMarker= markerForTuple )
					int alt183=3;
					alt183 = dfa183.predict(input);
					switch (alt183) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1441:9: K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples )
							{
							match(input,K_IN,FOLLOW_K_IN_in_relation10089); 
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1442:11: ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples )
							int alt182=4;
							int LA182_0 = input.LA(1);
							if ( (LA182_0==174) ) {
								switch ( input.LA(2) ) {
								case 175:
									{
									alt182=1;
									}
									break;
								case 174:
									{
									alt182=3;
									}
									break;
								case QMARK:
								case 180:
									{
									alt182=4;
									}
									break;
								default:
									int nvaeMark = input.mark();
									try {
										input.consume();
										NoViableAltException nvae =
											new NoViableAltException("", 182, 1, input);
										throw nvae;
									} finally {
										input.rewind(nvaeMark);
									}
								}
							}
							else if ( (LA182_0==QMARK||LA182_0==180) ) {
								alt182=2;
							}

							else {
								NoViableAltException nvae =
									new NoViableAltException("", 182, 0, input);
								throw nvae;
							}

							switch (alt182) {
								case 1 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1442:13: '(' ')'
									{
									match(input,174,FOLLOW_174_in_relation10103); 
									match(input,175,FOLLOW_175_in_relation10105); 
									 clauses.add(MultiColumnRelation.createInRelation(ids, new ArrayList<Tuples.Literal>())); 
									}
									break;
								case 2 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1444:13: tupleInMarker= inMarkerForTuple
									{
									pushFollow(FOLLOW_inMarkerForTuple_in_relation10137);
									tupleInMarker=inMarkerForTuple();
									state._fsp--;

									 clauses.add(MultiColumnRelation.createSingleMarkerInRelation(ids, tupleInMarker)); 
									}
									break;
								case 3 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1446:13: literals= tupleOfTupleLiterals
									{
									pushFollow(FOLLOW_tupleOfTupleLiterals_in_relation10171);
									literals=tupleOfTupleLiterals();
									state._fsp--;


									                  clauses.add(MultiColumnRelation.createInRelation(ids, literals));
									              
									}
									break;
								case 4 :
									// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1450:13: markers= tupleOfMarkersForTuples
									{
									pushFollow(FOLLOW_tupleOfMarkersForTuples_in_relation10205);
									markers=tupleOfMarkersForTuples();
									state._fsp--;

									 clauses.add(MultiColumnRelation.createInRelation(ids, markers)); 
									}
									break;

							}

							}
							break;
						case 2 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1453:9: type= relationType literal= tupleLiteral
							{
							pushFollow(FOLLOW_relationType_in_relation10247);
							type=relationType();
							state._fsp--;

							pushFollow(FOLLOW_tupleLiteral_in_relation10251);
							literal=tupleLiteral();
							state._fsp--;


							              clauses.add(MultiColumnRelation.createNonInRelation(ids, type, literal));
							          
							}
							break;
						case 3 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1457:9: type= relationType tupleMarker= markerForTuple
							{
							pushFollow(FOLLOW_relationType_in_relation10277);
							type=relationType();
							state._fsp--;

							pushFollow(FOLLOW_markerForTuple_in_relation10281);
							tupleMarker=markerForTuple();
							state._fsp--;

							 clauses.add(MultiColumnRelation.createNonInRelation(ids, type, tupleMarker)); 
							}
							break;

					}

					}
					break;
				case 9 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1460:7: '(' relation[$clauses] ')'
					{
					match(input,174,FOLLOW_174_in_relation10311); 
					pushFollow(FOLLOW_relation_in_relation10313);
					relation(clauses);
					state._fsp--;

					match(input,175,FOLLOW_175_in_relation10316); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "relation"



	// $ANTLR start "inMarker"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1463:1: inMarker returns [AbstractMarker.INRaw marker] : ( QMARK | ':' name= noncol_ident );
	public final AbstractMarker.INRaw inMarker() throws RecognitionException {
		AbstractMarker.INRaw marker = null;


		ColumnIdentifier name =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1464:5: ( QMARK | ':' name= noncol_ident )
			int alt185=2;
			int LA185_0 = input.LA(1);
			if ( (LA185_0==QMARK) ) {
				alt185=1;
			}
			else if ( (LA185_0==180) ) {
				alt185=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 185, 0, input);
				throw nvae;
			}

			switch (alt185) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1464:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_inMarker10337); 
					 marker = newINBindVariables(null); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1465:7: ':' name= noncol_ident
					{
					match(input,180,FOLLOW_180_in_inMarker10347); 
					pushFollow(FOLLOW_noncol_ident_in_inMarker10351);
					name=noncol_ident();
					state._fsp--;

					 marker = newINBindVariables(name); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return marker;
	}
	// $ANTLR end "inMarker"



	// $ANTLR start "tupleOfIdentifiers"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1468:1: tupleOfIdentifiers returns [List<ColumnIdentifier.Raw> ids] : '(' n1= cident ( ',' ni= cident )* ')' ;
	public final List<ColumnIdentifier.Raw> tupleOfIdentifiers() throws RecognitionException {
		List<ColumnIdentifier.Raw> ids = null;


		ColumnIdentifier.Raw n1 =null;
		ColumnIdentifier.Raw ni =null;

		 ids = new ArrayList<ColumnIdentifier.Raw>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1470:5: ( '(' n1= cident ( ',' ni= cident )* ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1470:7: '(' n1= cident ( ',' ni= cident )* ')'
			{
			match(input,174,FOLLOW_174_in_tupleOfIdentifiers10383); 
			pushFollow(FOLLOW_cident_in_tupleOfIdentifiers10387);
			n1=cident();
			state._fsp--;

			 ids.add(n1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1470:39: ( ',' ni= cident )*
			loop186:
			while (true) {
				int alt186=2;
				int LA186_0 = input.LA(1);
				if ( (LA186_0==177) ) {
					alt186=1;
				}

				switch (alt186) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1470:40: ',' ni= cident
					{
					match(input,177,FOLLOW_177_in_tupleOfIdentifiers10392); 
					pushFollow(FOLLOW_cident_in_tupleOfIdentifiers10396);
					ni=cident();
					state._fsp--;

					 ids.add(ni); 
					}
					break;

				default :
					break loop186;
				}
			}

			match(input,175,FOLLOW_175_in_tupleOfIdentifiers10402); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ids;
	}
	// $ANTLR end "tupleOfIdentifiers"



	// $ANTLR start "singleColumnInValues"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1473:1: singleColumnInValues returns [List<Term.Raw> terms] : '(' (t1= term ( ',' ti= term )* )? ')' ;
	public final List<Term.Raw> singleColumnInValues() throws RecognitionException {
		List<Term.Raw> terms = null;


		Term.Raw t1 =null;
		Term.Raw ti =null;

		 terms = new ArrayList<Term.Raw>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1475:5: ( '(' (t1= term ( ',' ti= term )* )? ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1475:7: '(' (t1= term ( ',' ti= term )* )? ')'
			{
			match(input,174,FOLLOW_174_in_singleColumnInValues10432); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1475:11: (t1= term ( ',' ti= term )* )?
			int alt188=2;
			int LA188_0 = input.LA(1);
			if ( (LA188_0==BOOLEAN||LA188_0==FLOAT||LA188_0==HEXNUMBER||(LA188_0 >= IDENT && LA188_0 <= INTEGER)||(LA188_0 >= K_AGGREGATE && LA188_0 <= K_ALL)||LA188_0==K_AS||LA188_0==K_ASCII||(LA188_0 >= K_BIGINT && LA188_0 <= K_BOOLEAN)||(LA188_0 >= K_CALLED && LA188_0 <= K_CLUSTERING)||(LA188_0 >= K_COMPACT && LA188_0 <= K_COUNTER)||(LA188_0 >= K_CUSTOM && LA188_0 <= K_DECIMAL)||(LA188_0 >= K_DISTINCT && LA188_0 <= K_DOUBLE)||(LA188_0 >= K_EXISTS && LA188_0 <= K_FLOAT)||LA188_0==K_FROZEN||(LA188_0 >= K_FUNCTION && LA188_0 <= K_FUNCTIONS)||(LA188_0 >= K_INET && LA188_0 <= K_INPUT)||LA188_0==K_INT||(LA188_0 >= K_JSON && LA188_0 <= K_KEYS)||(LA188_0 >= K_KEYSPACES && LA188_0 <= K_LIKE)||(LA188_0 >= K_LIST && LA188_0 <= K_MAP)||(LA188_0 >= K_NAN && LA188_0 <= K_NOLOGIN)||LA188_0==K_NOSUPERUSER||LA188_0==K_NULL||LA188_0==K_OPTIONS||(LA188_0 >= K_PARTITION && LA188_0 <= K_PERMISSIONS)||LA188_0==K_RETURNS||(LA188_0 >= K_ROLE && LA188_0 <= K_ROLES)||(LA188_0 >= K_SFUNC && LA188_0 <= K_TINYINT)||(LA188_0 >= K_TOKEN && LA188_0 <= K_TRIGGER)||(LA188_0 >= K_TTL && LA188_0 <= K_TYPE)||(LA188_0 >= K_USER && LA188_0 <= K_USERS)||(LA188_0 >= K_UUID && LA188_0 <= K_VARINT)||LA188_0==K_WRITETIME||(LA188_0 >= QMARK && LA188_0 <= QUOTED_NAME)||LA188_0==STRING_LITERAL||LA188_0==UUID||LA188_0==174||LA188_0==178||LA188_0==180||LA188_0==187||LA188_0==191) ) {
				alt188=1;
			}
			switch (alt188) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1475:13: t1= term ( ',' ti= term )*
					{
					pushFollow(FOLLOW_term_in_singleColumnInValues10440);
					t1=term();
					state._fsp--;

					 terms.add(t1); 
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1475:43: ( ',' ti= term )*
					loop187:
					while (true) {
						int alt187=2;
						int LA187_0 = input.LA(1);
						if ( (LA187_0==177) ) {
							alt187=1;
						}

						switch (alt187) {
						case 1 :
							// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1475:44: ',' ti= term
							{
							match(input,177,FOLLOW_177_in_singleColumnInValues10445); 
							pushFollow(FOLLOW_term_in_singleColumnInValues10449);
							ti=term();
							state._fsp--;

							 terms.add(ti); 
							}
							break;

						default :
							break loop187;
						}
					}

					}
					break;

			}

			match(input,175,FOLLOW_175_in_singleColumnInValues10458); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return terms;
	}
	// $ANTLR end "singleColumnInValues"



	// $ANTLR start "tupleOfTupleLiterals"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1478:1: tupleOfTupleLiterals returns [List<Tuples.Literal> literals] : '(' t1= tupleLiteral ( ',' ti= tupleLiteral )* ')' ;
	public final List<Tuples.Literal> tupleOfTupleLiterals() throws RecognitionException {
		List<Tuples.Literal> literals = null;


		Tuples.Literal t1 =null;
		Tuples.Literal ti =null;

		 literals = new ArrayList<>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1480:5: ( '(' t1= tupleLiteral ( ',' ti= tupleLiteral )* ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1480:7: '(' t1= tupleLiteral ( ',' ti= tupleLiteral )* ')'
			{
			match(input,174,FOLLOW_174_in_tupleOfTupleLiterals10488); 
			pushFollow(FOLLOW_tupleLiteral_in_tupleOfTupleLiterals10492);
			t1=tupleLiteral();
			state._fsp--;

			 literals.add(t1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1480:50: ( ',' ti= tupleLiteral )*
			loop189:
			while (true) {
				int alt189=2;
				int LA189_0 = input.LA(1);
				if ( (LA189_0==177) ) {
					alt189=1;
				}

				switch (alt189) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1480:51: ',' ti= tupleLiteral
					{
					match(input,177,FOLLOW_177_in_tupleOfTupleLiterals10497); 
					pushFollow(FOLLOW_tupleLiteral_in_tupleOfTupleLiterals10501);
					ti=tupleLiteral();
					state._fsp--;

					 literals.add(ti); 
					}
					break;

				default :
					break loop189;
				}
			}

			match(input,175,FOLLOW_175_in_tupleOfTupleLiterals10507); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return literals;
	}
	// $ANTLR end "tupleOfTupleLiterals"



	// $ANTLR start "markerForTuple"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1483:1: markerForTuple returns [Tuples.Raw marker] : ( QMARK | ':' name= noncol_ident );
	public final Tuples.Raw markerForTuple() throws RecognitionException {
		Tuples.Raw marker = null;


		ColumnIdentifier name =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1484:5: ( QMARK | ':' name= noncol_ident )
			int alt190=2;
			int LA190_0 = input.LA(1);
			if ( (LA190_0==QMARK) ) {
				alt190=1;
			}
			else if ( (LA190_0==180) ) {
				alt190=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 190, 0, input);
				throw nvae;
			}

			switch (alt190) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1484:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_markerForTuple10528); 
					 marker = newTupleBindVariables(null); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1485:7: ':' name= noncol_ident
					{
					match(input,180,FOLLOW_180_in_markerForTuple10538); 
					pushFollow(FOLLOW_noncol_ident_in_markerForTuple10542);
					name=noncol_ident();
					state._fsp--;

					 marker = newTupleBindVariables(name); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return marker;
	}
	// $ANTLR end "markerForTuple"



	// $ANTLR start "tupleOfMarkersForTuples"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1488:1: tupleOfMarkersForTuples returns [List<Tuples.Raw> markers] : '(' m1= markerForTuple ( ',' mi= markerForTuple )* ')' ;
	public final List<Tuples.Raw> tupleOfMarkersForTuples() throws RecognitionException {
		List<Tuples.Raw> markers = null;


		Tuples.Raw m1 =null;
		Tuples.Raw mi =null;

		 markers = new ArrayList<Tuples.Raw>(); 
		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1490:5: ( '(' m1= markerForTuple ( ',' mi= markerForTuple )* ')' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1490:7: '(' m1= markerForTuple ( ',' mi= markerForTuple )* ')'
			{
			match(input,174,FOLLOW_174_in_tupleOfMarkersForTuples10574); 
			pushFollow(FOLLOW_markerForTuple_in_tupleOfMarkersForTuples10578);
			m1=markerForTuple();
			state._fsp--;

			 markers.add(m1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1490:51: ( ',' mi= markerForTuple )*
			loop191:
			while (true) {
				int alt191=2;
				int LA191_0 = input.LA(1);
				if ( (LA191_0==177) ) {
					alt191=1;
				}

				switch (alt191) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1490:52: ',' mi= markerForTuple
					{
					match(input,177,FOLLOW_177_in_tupleOfMarkersForTuples10583); 
					pushFollow(FOLLOW_markerForTuple_in_tupleOfMarkersForTuples10587);
					mi=markerForTuple();
					state._fsp--;

					 markers.add(mi); 
					}
					break;

				default :
					break loop191;
				}
			}

			match(input,175,FOLLOW_175_in_tupleOfMarkersForTuples10593); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return markers;
	}
	// $ANTLR end "tupleOfMarkersForTuples"



	// $ANTLR start "inMarkerForTuple"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1493:1: inMarkerForTuple returns [Tuples.INRaw marker] : ( QMARK | ':' name= noncol_ident );
	public final Tuples.INRaw inMarkerForTuple() throws RecognitionException {
		Tuples.INRaw marker = null;


		ColumnIdentifier name =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1494:5: ( QMARK | ':' name= noncol_ident )
			int alt192=2;
			int LA192_0 = input.LA(1);
			if ( (LA192_0==QMARK) ) {
				alt192=1;
			}
			else if ( (LA192_0==180) ) {
				alt192=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 192, 0, input);
				throw nvae;
			}

			switch (alt192) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1494:7: QMARK
					{
					match(input,QMARK,FOLLOW_QMARK_in_inMarkerForTuple10614); 
					 marker = newTupleINBindVariables(null); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1495:7: ':' name= noncol_ident
					{
					match(input,180,FOLLOW_180_in_inMarkerForTuple10624); 
					pushFollow(FOLLOW_noncol_ident_in_inMarkerForTuple10628);
					name=noncol_ident();
					state._fsp--;

					 marker = newTupleINBindVariables(name); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return marker;
	}
	// $ANTLR end "inMarkerForTuple"



	// $ANTLR start "comparatorType"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1498:1: comparatorType returns [CQL3Type.Raw t] : (n= native_type |c= collection_type |tt= tuple_type |id= userTypeName | K_FROZEN '<' f= comparatorType '>' |s= STRING_LITERAL );
	public final CQL3Type.Raw comparatorType() throws RecognitionException {
		CQL3Type.Raw t = null;


		Token s=null;
		CQL3Type n =null;
		CQL3Type.Raw c =null;
		CQL3Type.Raw tt =null;
		UTName id =null;
		CQL3Type.Raw f =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1499:5: (n= native_type |c= collection_type |tt= tuple_type |id= userTypeName | K_FROZEN '<' f= comparatorType '>' |s= STRING_LITERAL )
			int alt193=6;
			alt193 = dfa193.predict(input);
			switch (alt193) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1499:7: n= native_type
					{
					pushFollow(FOLLOW_native_type_in_comparatorType10653);
					n=native_type();
					state._fsp--;

					 t = CQL3Type.Raw.from(n); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1500:7: c= collection_type
					{
					pushFollow(FOLLOW_collection_type_in_comparatorType10669);
					c=collection_type();
					state._fsp--;

					 t = c; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1501:7: tt= tuple_type
					{
					pushFollow(FOLLOW_tuple_type_in_comparatorType10681);
					tt=tuple_type();
					state._fsp--;

					 t = tt; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1502:7: id= userTypeName
					{
					pushFollow(FOLLOW_userTypeName_in_comparatorType10697);
					id=userTypeName();
					state._fsp--;

					 t = CQL3Type.Raw.userType(id); 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1503:7: K_FROZEN '<' f= comparatorType '>'
					{
					match(input,K_FROZEN,FOLLOW_K_FROZEN_in_comparatorType10709); 
					match(input,182,FOLLOW_182_in_comparatorType10711); 
					pushFollow(FOLLOW_comparatorType_in_comparatorType10715);
					f=comparatorType();
					state._fsp--;

					match(input,185,FOLLOW_185_in_comparatorType10717); 

					        try {
					            t = CQL3Type.Raw.frozen(f);
					        } catch (InvalidRequestException e) {
					            addRecognitionError(e.getMessage());
					        }
					      
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1511:7: s= STRING_LITERAL
					{
					s=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_comparatorType10735); 

					        try {
					            t = CQL3Type.Raw.from(new CQL3Type.Custom((s!=null?s.getText():null)));
					        } catch (SyntaxException e) {
					            addRecognitionError("Cannot parse type " + (s!=null?s.getText():null) + ": " + e.getMessage());
					        } catch (ConfigurationException e) {
					            addRecognitionError("Error setting type " + (s!=null?s.getText():null) + ": " + e.getMessage());
					        }
					      
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return t;
	}
	// $ANTLR end "comparatorType"



	// $ANTLR start "native_type"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1523:1: native_type returns [CQL3Type t] : ( K_ASCII | K_BIGINT | K_BLOB | K_BOOLEAN | K_COUNTER | K_DECIMAL | K_DOUBLE | K_FLOAT | K_INET | K_INT | K_SMALLINT | K_TEXT | K_TIMESTAMP | K_TINYINT | K_UUID | K_VARCHAR | K_VARINT | K_TIMEUUID | K_DATE | K_TIME );
	public final CQL3Type native_type() throws RecognitionException {
		CQL3Type t = null;


		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1524:5: ( K_ASCII | K_BIGINT | K_BLOB | K_BOOLEAN | K_COUNTER | K_DECIMAL | K_DOUBLE | K_FLOAT | K_INET | K_INT | K_SMALLINT | K_TEXT | K_TIMESTAMP | K_TINYINT | K_UUID | K_VARCHAR | K_VARINT | K_TIMEUUID | K_DATE | K_TIME )
			int alt194=20;
			switch ( input.LA(1) ) {
			case K_ASCII:
				{
				alt194=1;
				}
				break;
			case K_BIGINT:
				{
				alt194=2;
				}
				break;
			case K_BLOB:
				{
				alt194=3;
				}
				break;
			case K_BOOLEAN:
				{
				alt194=4;
				}
				break;
			case K_COUNTER:
				{
				alt194=5;
				}
				break;
			case K_DECIMAL:
				{
				alt194=6;
				}
				break;
			case K_DOUBLE:
				{
				alt194=7;
				}
				break;
			case K_FLOAT:
				{
				alt194=8;
				}
				break;
			case K_INET:
				{
				alt194=9;
				}
				break;
			case K_INT:
				{
				alt194=10;
				}
				break;
			case K_SMALLINT:
				{
				alt194=11;
				}
				break;
			case K_TEXT:
				{
				alt194=12;
				}
				break;
			case K_TIMESTAMP:
				{
				alt194=13;
				}
				break;
			case K_TINYINT:
				{
				alt194=14;
				}
				break;
			case K_UUID:
				{
				alt194=15;
				}
				break;
			case K_VARCHAR:
				{
				alt194=16;
				}
				break;
			case K_VARINT:
				{
				alt194=17;
				}
				break;
			case K_TIMEUUID:
				{
				alt194=18;
				}
				break;
			case K_DATE:
				{
				alt194=19;
				}
				break;
			case K_TIME:
				{
				alt194=20;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 194, 0, input);
				throw nvae;
			}
			switch (alt194) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1524:7: K_ASCII
					{
					match(input,K_ASCII,FOLLOW_K_ASCII_in_native_type10764); 
					 t = CQL3Type.Native.ASCII; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1525:7: K_BIGINT
					{
					match(input,K_BIGINT,FOLLOW_K_BIGINT_in_native_type10778); 
					 t = CQL3Type.Native.BIGINT; 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1526:7: K_BLOB
					{
					match(input,K_BLOB,FOLLOW_K_BLOB_in_native_type10791); 
					 t = CQL3Type.Native.BLOB; 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1527:7: K_BOOLEAN
					{
					match(input,K_BOOLEAN,FOLLOW_K_BOOLEAN_in_native_type10806); 
					 t = CQL3Type.Native.BOOLEAN; 
					}
					break;
				case 5 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1528:7: K_COUNTER
					{
					match(input,K_COUNTER,FOLLOW_K_COUNTER_in_native_type10818); 
					 t = CQL3Type.Native.COUNTER; 
					}
					break;
				case 6 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1529:7: K_DECIMAL
					{
					match(input,K_DECIMAL,FOLLOW_K_DECIMAL_in_native_type10830); 
					 t = CQL3Type.Native.DECIMAL; 
					}
					break;
				case 7 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1530:7: K_DOUBLE
					{
					match(input,K_DOUBLE,FOLLOW_K_DOUBLE_in_native_type10842); 
					 t = CQL3Type.Native.DOUBLE; 
					}
					break;
				case 8 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1531:7: K_FLOAT
					{
					match(input,K_FLOAT,FOLLOW_K_FLOAT_in_native_type10855); 
					 t = CQL3Type.Native.FLOAT; 
					}
					break;
				case 9 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1532:7: K_INET
					{
					match(input,K_INET,FOLLOW_K_INET_in_native_type10869); 
					 t = CQL3Type.Native.INET;
					}
					break;
				case 10 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1533:7: K_INT
					{
					match(input,K_INT,FOLLOW_K_INT_in_native_type10884); 
					 t = CQL3Type.Native.INT; 
					}
					break;
				case 11 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1534:7: K_SMALLINT
					{
					match(input,K_SMALLINT,FOLLOW_K_SMALLINT_in_native_type10900); 
					 t = CQL3Type.Native.SMALLINT; 
					}
					break;
				case 12 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1535:7: K_TEXT
					{
					match(input,K_TEXT,FOLLOW_K_TEXT_in_native_type10911); 
					 t = CQL3Type.Native.TEXT; 
					}
					break;
				case 13 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1536:7: K_TIMESTAMP
					{
					match(input,K_TIMESTAMP,FOLLOW_K_TIMESTAMP_in_native_type10926); 
					 t = CQL3Type.Native.TIMESTAMP; 
					}
					break;
				case 14 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1537:7: K_TINYINT
					{
					match(input,K_TINYINT,FOLLOW_K_TINYINT_in_native_type10936); 
					 t = CQL3Type.Native.TINYINT; 
					}
					break;
				case 15 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1538:7: K_UUID
					{
					match(input,K_UUID,FOLLOW_K_UUID_in_native_type10948); 
					 t = CQL3Type.Native.UUID; 
					}
					break;
				case 16 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1539:7: K_VARCHAR
					{
					match(input,K_VARCHAR,FOLLOW_K_VARCHAR_in_native_type10963); 
					 t = CQL3Type.Native.VARCHAR; 
					}
					break;
				case 17 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1540:7: K_VARINT
					{
					match(input,K_VARINT,FOLLOW_K_VARINT_in_native_type10975); 
					 t = CQL3Type.Native.VARINT; 
					}
					break;
				case 18 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1541:7: K_TIMEUUID
					{
					match(input,K_TIMEUUID,FOLLOW_K_TIMEUUID_in_native_type10988); 
					 t = CQL3Type.Native.TIMEUUID; 
					}
					break;
				case 19 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1542:7: K_DATE
					{
					match(input,K_DATE,FOLLOW_K_DATE_in_native_type10999); 
					 t = CQL3Type.Native.DATE; 
					}
					break;
				case 20 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1543:7: K_TIME
					{
					match(input,K_TIME,FOLLOW_K_TIME_in_native_type11014); 
					 t = CQL3Type.Native.TIME; 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return t;
	}
	// $ANTLR end "native_type"



	// $ANTLR start "collection_type"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1546:1: collection_type returns [CQL3Type.Raw pt] : ( K_MAP '<' t1= comparatorType ',' t2= comparatorType '>' | K_LIST '<' t= comparatorType '>' | K_SET '<' t= comparatorType '>' );
	public final CQL3Type.Raw collection_type() throws RecognitionException {
		CQL3Type.Raw pt = null;


		CQL3Type.Raw t1 =null;
		CQL3Type.Raw t2 =null;
		CQL3Type.Raw t =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1547:5: ( K_MAP '<' t1= comparatorType ',' t2= comparatorType '>' | K_LIST '<' t= comparatorType '>' | K_SET '<' t= comparatorType '>' )
			int alt195=3;
			switch ( input.LA(1) ) {
			case K_MAP:
				{
				alt195=1;
				}
				break;
			case K_LIST:
				{
				alt195=2;
				}
				break;
			case K_SET:
				{
				alt195=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 195, 0, input);
				throw nvae;
			}
			switch (alt195) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1547:7: K_MAP '<' t1= comparatorType ',' t2= comparatorType '>'
					{
					match(input,K_MAP,FOLLOW_K_MAP_in_collection_type11042); 
					match(input,182,FOLLOW_182_in_collection_type11045); 
					pushFollow(FOLLOW_comparatorType_in_collection_type11049);
					t1=comparatorType();
					state._fsp--;

					match(input,177,FOLLOW_177_in_collection_type11051); 
					pushFollow(FOLLOW_comparatorType_in_collection_type11055);
					t2=comparatorType();
					state._fsp--;

					match(input,185,FOLLOW_185_in_collection_type11057); 

					            // if we can't parse either t1 or t2, antlr will "recover" and we may have t1 or t2 null.
					            if (t1 != null && t2 != null)
					                pt = CQL3Type.Raw.map(t1, t2);
					        
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1553:7: K_LIST '<' t= comparatorType '>'
					{
					match(input,K_LIST,FOLLOW_K_LIST_in_collection_type11075); 
					match(input,182,FOLLOW_182_in_collection_type11077); 
					pushFollow(FOLLOW_comparatorType_in_collection_type11081);
					t=comparatorType();
					state._fsp--;

					match(input,185,FOLLOW_185_in_collection_type11083); 
					 if (t != null) pt = CQL3Type.Raw.list(t); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1555:7: K_SET '<' t= comparatorType '>'
					{
					match(input,K_SET,FOLLOW_K_SET_in_collection_type11101); 
					match(input,182,FOLLOW_182_in_collection_type11104); 
					pushFollow(FOLLOW_comparatorType_in_collection_type11108);
					t=comparatorType();
					state._fsp--;

					match(input,185,FOLLOW_185_in_collection_type11110); 
					 if (t != null) pt = CQL3Type.Raw.set(t); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return pt;
	}
	// $ANTLR end "collection_type"



	// $ANTLR start "tuple_type"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1559:1: tuple_type returns [CQL3Type.Raw t] : K_TUPLE '<' t1= comparatorType ( ',' tn= comparatorType )* '>' ;
	public final CQL3Type.Raw tuple_type() throws RecognitionException {
		CQL3Type.Raw t = null;


		CQL3Type.Raw t1 =null;
		CQL3Type.Raw tn =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1560:5: ( K_TUPLE '<' t1= comparatorType ( ',' tn= comparatorType )* '>' )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1560:7: K_TUPLE '<' t1= comparatorType ( ',' tn= comparatorType )* '>'
			{
			match(input,K_TUPLE,FOLLOW_K_TUPLE_in_tuple_type11141); 
			match(input,182,FOLLOW_182_in_tuple_type11143); 
			 List<CQL3Type.Raw> types = new ArrayList<>(); 
			pushFollow(FOLLOW_comparatorType_in_tuple_type11158);
			t1=comparatorType();
			state._fsp--;

			 types.add(t1); 
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1561:47: ( ',' tn= comparatorType )*
			loop196:
			while (true) {
				int alt196=2;
				int LA196_0 = input.LA(1);
				if ( (LA196_0==177) ) {
					alt196=1;
				}

				switch (alt196) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1561:48: ',' tn= comparatorType
					{
					match(input,177,FOLLOW_177_in_tuple_type11163); 
					pushFollow(FOLLOW_comparatorType_in_tuple_type11167);
					tn=comparatorType();
					state._fsp--;

					 types.add(tn); 
					}
					break;

				default :
					break loop196;
				}
			}

			match(input,185,FOLLOW_185_in_tuple_type11179); 
			 t = CQL3Type.Raw.tuple(types); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return t;
	}
	// $ANTLR end "tuple_type"


	public static class username_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "username"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1565:1: username : ( IDENT | STRING_LITERAL | QUOTED_NAME );
	public final CqlParser.username_return username() throws RecognitionException {
		CqlParser.username_return retval = new CqlParser.username_return();
		retval.start = input.LT(1);

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1566:5: ( IDENT | STRING_LITERAL | QUOTED_NAME )
			int alt197=3;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt197=1;
				}
				break;
			case STRING_LITERAL:
				{
				alt197=2;
				}
				break;
			case QUOTED_NAME:
				{
				alt197=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 197, 0, input);
				throw nvae;
			}
			switch (alt197) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1566:7: IDENT
					{
					match(input,IDENT,FOLLOW_IDENT_in_username11198); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1567:7: STRING_LITERAL
					{
					match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_username11206); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1568:7: QUOTED_NAME
					{
					match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_username11214); 
					 addRecognitionError("Quoted strings are are not supported for user names and USER is deprecated, please use ROLE");
					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "username"



	// $ANTLR start "non_type_ident"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1573:1: non_type_ident returns [ColumnIdentifier id] : (t= IDENT |t= QUOTED_NAME |k= basic_unreserved_keyword |kk= K_KEY );
	public final ColumnIdentifier non_type_ident() throws RecognitionException {
		ColumnIdentifier id = null;


		Token t=null;
		Token kk=null;
		String k =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1574:5: (t= IDENT |t= QUOTED_NAME |k= basic_unreserved_keyword |kk= K_KEY )
			int alt198=4;
			switch ( input.LA(1) ) {
			case IDENT:
				{
				alt198=1;
				}
				break;
			case QUOTED_NAME:
				{
				alt198=2;
				}
				break;
			case K_AGGREGATE:
			case K_ALL:
			case K_AS:
			case K_CALLED:
			case K_CLUSTERING:
			case K_COMPACT:
			case K_CONTAINS:
			case K_CUSTOM:
			case K_DISTINCT:
			case K_EXISTS:
			case K_FILTERING:
			case K_FINALFUNC:
			case K_FROZEN:
			case K_FUNCTION:
			case K_FUNCTIONS:
			case K_INITCOND:
			case K_INPUT:
			case K_JSON:
			case K_KEYS:
			case K_KEYSPACES:
			case K_LANGUAGE:
			case K_LIKE:
			case K_LIST:
			case K_LOGIN:
			case K_MAP:
			case K_NOLOGIN:
			case K_NOSUPERUSER:
			case K_OPTIONS:
			case K_PARTITION:
			case K_PASSWORD:
			case K_PER:
			case K_PERMISSION:
			case K_PERMISSIONS:
			case K_RETURNS:
			case K_ROLE:
			case K_ROLES:
			case K_SFUNC:
			case K_STATIC:
			case K_STORAGE:
			case K_STYPE:
			case K_SUPERUSER:
			case K_TRIGGER:
			case K_TUPLE:
			case K_TYPE:
			case K_USER:
			case K_USERS:
			case K_VALUES:
				{
				alt198=3;
				}
				break;
			case K_KEY:
				{
				alt198=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 198, 0, input);
				throw nvae;
			}
			switch (alt198) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1574:7: t= IDENT
					{
					t=(Token)match(input,IDENT,FOLLOW_IDENT_in_non_type_ident11241); 
					 if (reservedTypeNames.contains((t!=null?t.getText():null))) addRecognitionError("Invalid (reserved) user type name " + (t!=null?t.getText():null)); id = new ColumnIdentifier((t!=null?t.getText():null), false); 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1575:7: t= QUOTED_NAME
					{
					t=(Token)match(input,QUOTED_NAME,FOLLOW_QUOTED_NAME_in_non_type_ident11272); 
					 id = new ColumnIdentifier((t!=null?t.getText():null), true); 
					}
					break;
				case 3 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1576:7: k= basic_unreserved_keyword
					{
					pushFollow(FOLLOW_basic_unreserved_keyword_in_non_type_ident11297);
					k=basic_unreserved_keyword();
					state._fsp--;

					 id = new ColumnIdentifier(k, false); 
					}
					break;
				case 4 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1577:7: kk= K_KEY
					{
					kk=(Token)match(input,K_KEY,FOLLOW_K_KEY_in_non_type_ident11309); 
					 id = new ColumnIdentifier((kk!=null?kk.getText():null), false); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return id;
	}
	// $ANTLR end "non_type_ident"



	// $ANTLR start "unreserved_keyword"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1580:1: unreserved_keyword returns [String str] : (u= unreserved_function_keyword |k= ( K_TTL | K_COUNT | K_WRITETIME | K_KEY ) );
	public final String unreserved_keyword() throws RecognitionException {
		String str = null;


		Token k=null;
		String u =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1581:5: (u= unreserved_function_keyword |k= ( K_TTL | K_COUNT | K_WRITETIME | K_KEY ) )
			int alt199=2;
			int LA199_0 = input.LA(1);
			if ( ((LA199_0 >= K_AGGREGATE && LA199_0 <= K_ALL)||LA199_0==K_AS||LA199_0==K_ASCII||(LA199_0 >= K_BIGINT && LA199_0 <= K_BOOLEAN)||(LA199_0 >= K_CALLED && LA199_0 <= K_CLUSTERING)||(LA199_0 >= K_COMPACT && LA199_0 <= K_CONTAINS)||LA199_0==K_COUNTER||(LA199_0 >= K_CUSTOM && LA199_0 <= K_DECIMAL)||(LA199_0 >= K_DISTINCT && LA199_0 <= K_DOUBLE)||(LA199_0 >= K_EXISTS && LA199_0 <= K_FLOAT)||LA199_0==K_FROZEN||(LA199_0 >= K_FUNCTION && LA199_0 <= K_FUNCTIONS)||LA199_0==K_INET||(LA199_0 >= K_INITCOND && LA199_0 <= K_INPUT)||LA199_0==K_INT||LA199_0==K_JSON||LA199_0==K_KEYS||(LA199_0 >= K_KEYSPACES && LA199_0 <= K_LIKE)||(LA199_0 >= K_LIST && LA199_0 <= K_MAP)||LA199_0==K_NOLOGIN||LA199_0==K_NOSUPERUSER||LA199_0==K_OPTIONS||(LA199_0 >= K_PARTITION && LA199_0 <= K_PERMISSIONS)||LA199_0==K_RETURNS||(LA199_0 >= K_ROLE && LA199_0 <= K_ROLES)||(LA199_0 >= K_SFUNC && LA199_0 <= K_TINYINT)||LA199_0==K_TRIGGER||(LA199_0 >= K_TUPLE && LA199_0 <= K_TYPE)||(LA199_0 >= K_USER && LA199_0 <= K_USERS)||(LA199_0 >= K_UUID && LA199_0 <= K_VARINT)) ) {
				alt199=1;
			}
			else if ( (LA199_0==K_COUNT||LA199_0==K_KEY||LA199_0==K_TTL||LA199_0==K_WRITETIME) ) {
				alt199=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 199, 0, input);
				throw nvae;
			}

			switch (alt199) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1581:7: u= unreserved_function_keyword
					{
					pushFollow(FOLLOW_unreserved_function_keyword_in_unreserved_keyword11352);
					u=unreserved_function_keyword();
					state._fsp--;

					 str = u; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1582:7: k= ( K_TTL | K_COUNT | K_WRITETIME | K_KEY )
					{
					k=input.LT(1);
					if ( input.LA(1)==K_COUNT||input.LA(1)==K_KEY||input.LA(1)==K_TTL||input.LA(1)==K_WRITETIME ) {
						input.consume();
						state.errorRecovery=false;
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					 str = (k!=null?k.getText():null); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return str;
	}
	// $ANTLR end "unreserved_keyword"



	// $ANTLR start "unreserved_function_keyword"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1585:1: unreserved_function_keyword returns [String str] : (u= basic_unreserved_keyword |t= native_type );
	public final String unreserved_function_keyword() throws RecognitionException {
		String str = null;


		String u =null;
		CQL3Type t =null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1586:5: (u= basic_unreserved_keyword |t= native_type )
			int alt200=2;
			int LA200_0 = input.LA(1);
			if ( ((LA200_0 >= K_AGGREGATE && LA200_0 <= K_ALL)||LA200_0==K_AS||(LA200_0 >= K_CALLED && LA200_0 <= K_CLUSTERING)||(LA200_0 >= K_COMPACT && LA200_0 <= K_CONTAINS)||LA200_0==K_CUSTOM||LA200_0==K_DISTINCT||(LA200_0 >= K_EXISTS && LA200_0 <= K_FINALFUNC)||LA200_0==K_FROZEN||(LA200_0 >= K_FUNCTION && LA200_0 <= K_FUNCTIONS)||(LA200_0 >= K_INITCOND && LA200_0 <= K_INPUT)||LA200_0==K_JSON||LA200_0==K_KEYS||(LA200_0 >= K_KEYSPACES && LA200_0 <= K_LIKE)||(LA200_0 >= K_LIST && LA200_0 <= K_MAP)||LA200_0==K_NOLOGIN||LA200_0==K_NOSUPERUSER||LA200_0==K_OPTIONS||(LA200_0 >= K_PARTITION && LA200_0 <= K_PERMISSIONS)||LA200_0==K_RETURNS||(LA200_0 >= K_ROLE && LA200_0 <= K_ROLES)||LA200_0==K_SFUNC||(LA200_0 >= K_STATIC && LA200_0 <= K_SUPERUSER)||LA200_0==K_TRIGGER||(LA200_0 >= K_TUPLE && LA200_0 <= K_TYPE)||(LA200_0 >= K_USER && LA200_0 <= K_USERS)||LA200_0==K_VALUES) ) {
				alt200=1;
			}
			else if ( (LA200_0==K_ASCII||(LA200_0 >= K_BIGINT && LA200_0 <= K_BOOLEAN)||LA200_0==K_COUNTER||(LA200_0 >= K_DATE && LA200_0 <= K_DECIMAL)||LA200_0==K_DOUBLE||LA200_0==K_FLOAT||LA200_0==K_INET||LA200_0==K_INT||LA200_0==K_SMALLINT||(LA200_0 >= K_TEXT && LA200_0 <= K_TINYINT)||LA200_0==K_UUID||(LA200_0 >= K_VARCHAR && LA200_0 <= K_VARINT)) ) {
				alt200=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 200, 0, input);
				throw nvae;
			}

			switch (alt200) {
				case 1 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1586:7: u= basic_unreserved_keyword
					{
					pushFollow(FOLLOW_basic_unreserved_keyword_in_unreserved_function_keyword11407);
					u=basic_unreserved_keyword();
					state._fsp--;

					 str = u; 
					}
					break;
				case 2 :
					// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1587:7: t= native_type
					{
					pushFollow(FOLLOW_native_type_in_unreserved_function_keyword11419);
					t=native_type();
					state._fsp--;

					 str = t.toString(); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return str;
	}
	// $ANTLR end "unreserved_function_keyword"



	// $ANTLR start "basic_unreserved_keyword"
	// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1590:1: basic_unreserved_keyword returns [String str] : k= ( K_KEYS | K_AS | K_CLUSTERING | K_COMPACT | K_STORAGE | K_TYPE | K_VALUES | K_MAP | K_LIST | K_FILTERING | K_PERMISSION | K_PERMISSIONS | K_KEYSPACES | K_ALL | K_USER | K_USERS | K_ROLE | K_ROLES | K_SUPERUSER | K_NOSUPERUSER | K_LOGIN | K_NOLOGIN | K_OPTIONS | K_PASSWORD | K_EXISTS | K_CUSTOM | K_TRIGGER | K_DISTINCT | K_CONTAINS | K_STATIC | K_FROZEN | K_TUPLE | K_FUNCTION | K_FUNCTIONS | K_AGGREGATE | K_SFUNC | K_STYPE | K_FINALFUNC | K_INITCOND | K_RETURNS | K_LANGUAGE | K_JSON | K_CALLED | K_INPUT | K_LIKE | K_PER | K_PARTITION ) ;
	public final String basic_unreserved_keyword() throws RecognitionException {
		String str = null;


		Token k=null;

		try {
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1591:5: (k= ( K_KEYS | K_AS | K_CLUSTERING | K_COMPACT | K_STORAGE | K_TYPE | K_VALUES | K_MAP | K_LIST | K_FILTERING | K_PERMISSION | K_PERMISSIONS | K_KEYSPACES | K_ALL | K_USER | K_USERS | K_ROLE | K_ROLES | K_SUPERUSER | K_NOSUPERUSER | K_LOGIN | K_NOLOGIN | K_OPTIONS | K_PASSWORD | K_EXISTS | K_CUSTOM | K_TRIGGER | K_DISTINCT | K_CONTAINS | K_STATIC | K_FROZEN | K_TUPLE | K_FUNCTION | K_FUNCTIONS | K_AGGREGATE | K_SFUNC | K_STYPE | K_FINALFUNC | K_INITCOND | K_RETURNS | K_LANGUAGE | K_JSON | K_CALLED | K_INPUT | K_LIKE | K_PER | K_PARTITION ) )
			// /Users/tanmeshnm/dev/rocksandra/rocksandra/src/java/org/apache/cassandra/cql3/Cql.g:1591:7: k= ( K_KEYS | K_AS | K_CLUSTERING | K_COMPACT | K_STORAGE | K_TYPE | K_VALUES | K_MAP | K_LIST | K_FILTERING | K_PERMISSION | K_PERMISSIONS | K_KEYSPACES | K_ALL | K_USER | K_USERS | K_ROLE | K_ROLES | K_SUPERUSER | K_NOSUPERUSER | K_LOGIN | K_NOLOGIN | K_OPTIONS | K_PASSWORD | K_EXISTS | K_CUSTOM | K_TRIGGER | K_DISTINCT | K_CONTAINS | K_STATIC | K_FROZEN | K_TUPLE | K_FUNCTION | K_FUNCTIONS | K_AGGREGATE | K_SFUNC | K_STYPE | K_FINALFUNC | K_INITCOND | K_RETURNS | K_LANGUAGE | K_JSON | K_CALLED | K_INPUT | K_LIKE | K_PER | K_PARTITION )
			{
			k=input.LT(1);
			if ( (input.LA(1) >= K_AGGREGATE && input.LA(1) <= K_ALL)||input.LA(1)==K_AS||(input.LA(1) >= K_CALLED && input.LA(1) <= K_CLUSTERING)||(input.LA(1) >= K_COMPACT && input.LA(1) <= K_CONTAINS)||input.LA(1)==K_CUSTOM||input.LA(1)==K_DISTINCT||(input.LA(1) >= K_EXISTS && input.LA(1) <= K_FINALFUNC)||input.LA(1)==K_FROZEN||(input.LA(1) >= K_FUNCTION && input.LA(1) <= K_FUNCTIONS)||(input.LA(1) >= K_INITCOND && input.LA(1) <= K_INPUT)||input.LA(1)==K_JSON||input.LA(1)==K_KEYS||(input.LA(1) >= K_KEYSPACES && input.LA(1) <= K_LIKE)||(input.LA(1) >= K_LIST && input.LA(1) <= K_MAP)||input.LA(1)==K_NOLOGIN||input.LA(1)==K_NOSUPERUSER||input.LA(1)==K_OPTIONS||(input.LA(1) >= K_PARTITION && input.LA(1) <= K_PERMISSIONS)||input.LA(1)==K_RETURNS||(input.LA(1) >= K_ROLE && input.LA(1) <= K_ROLES)||input.LA(1)==K_SFUNC||(input.LA(1) >= K_STATIC && input.LA(1) <= K_SUPERUSER)||input.LA(1)==K_TRIGGER||(input.LA(1) >= K_TUPLE && input.LA(1) <= K_TYPE)||(input.LA(1) >= K_USER && input.LA(1) <= K_USERS)||input.LA(1)==K_VALUES ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			 str = (k!=null?k.getText():null); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return str;
	}
	// $ANTLR end "basic_unreserved_keyword"

	// Delegated rules


	protected DFA2 dfa2 = new DFA2(this);
	protected DFA14 dfa14 = new DFA14(this);
	protected DFA42 dfa42 = new DFA42(this);
	protected DFA105 dfa105 = new DFA105(this);
	protected DFA144 dfa144 = new DFA144(this);
	protected DFA145 dfa145 = new DFA145(this);
	protected DFA163 dfa163 = new DFA163(this);
	protected DFA165 dfa165 = new DFA165(this);
	protected DFA167 dfa167 = new DFA167(this);
	protected DFA169 dfa169 = new DFA169(this);
	protected DFA172 dfa172 = new DFA172(this);
	protected DFA178 dfa178 = new DFA178(this);
	protected DFA184 dfa184 = new DFA184(this);
	protected DFA183 dfa183 = new DFA183(this);
	protected DFA193 dfa193 = new DFA193(this);
	static final String DFA2_eotS =
		"\63\uffff";
	static final String DFA2_eofS =
		"\63\uffff";
	static final String DFA2_minS =
		"\1\34\7\uffff\2\31\1\53\2\24\1\32\10\uffff\1\160\22\uffff\1\145\2\uffff"+
		"\1\100\5\uffff\1\31";
	static final String DFA2_maxS =
		"\1\u008b\7\uffff\3\u008c\2\u00a3\1\u008d\10\uffff\1\160\22\uffff\1\u0082"+
		"\2\uffff\1\155\5\uffff\1\103";
	static final String DFA2_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\6\uffff\1\10\1\11\1\23\1\27\1\31"+
		"\1\40\1\46\1\12\1\uffff\1\34\1\36\1\13\1\14\1\15\1\25\1\30\1\33\1\35\1"+
		"\37\1\42\1\47\1\16\1\17\1\24\1\32\1\41\1\50\1\uffff\1\20\1\44\1\uffff"+
		"\1\21\1\45\1\26\1\43\1\22\1\uffff";
	static final String DFA2_specialS =
		"\63\uffff}>";
	static final String[] DFA2_transitionS = {
			"\1\12\7\uffff\1\4\13\uffff\1\10\3\uffff\1\5\4\uffff\1\11\13\uffff\1\13"+
			"\7\uffff\1\2\13\uffff\1\15\30\uffff\1\14\2\uffff\1\1\17\uffff\1\7\4\uffff"+
			"\1\3\1\6",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\30\21\uffff\1\17\5\uffff\1\25\21\uffff\1\27\4\uffff\1\25\13\uffff"+
			"\1\16\7\uffff\1\24\12\uffff\1\26\13\uffff\1\23\20\uffff\1\21\3\uffff"+
			"\1\22\3\uffff\1\20",
			"\1\40\21\uffff\1\32\27\uffff\1\37\4\uffff\1\33\13\uffff\1\31\7\uffff"+
			"\1\42\26\uffff\1\41\20\uffff\1\35\3\uffff\1\36\3\uffff\1\34",
			"\1\43\50\uffff\1\44\7\uffff\1\50\26\uffff\1\47\24\uffff\1\46\3\uffff"+
			"\1\45",
			"\1\53\4\uffff\1\53\1\51\1\uffff\1\52\2\uffff\1\53\1\uffff\1\53\1\52"+
			"\2\uffff\3\53\1\uffff\2\53\1\uffff\4\53\1\52\3\53\2\uffff\1\52\2\53\1"+
			"\52\1\uffff\1\52\4\53\1\uffff\1\53\1\uffff\2\53\4\uffff\1\53\1\uffff"+
			"\2\53\1\uffff\1\53\2\uffff\3\53\1\uffff\3\53\1\uffff\3\53\1\uffff\1\52"+
			"\1\uffff\1\53\1\uffff\1\53\4\uffff\1\53\2\uffff\5\53\3\uffff\1\53\1\uffff"+
			"\2\53\1\52\1\uffff\13\53\2\uffff\1\53\1\uffff\3\53\3\uffff\2\53\1\uffff"+
			"\4\53\3\uffff\1\53\10\uffff\2\53\2\uffff\1\53",
			"\1\56\4\uffff\1\56\1\54\1\uffff\1\55\2\uffff\1\56\1\uffff\1\56\1\55"+
			"\2\uffff\3\56\1\uffff\2\56\1\uffff\4\56\1\55\3\56\2\uffff\1\55\2\56\1"+
			"\55\1\uffff\1\55\4\56\1\uffff\1\56\1\uffff\2\56\4\uffff\1\56\1\uffff"+
			"\2\56\1\uffff\1\56\2\uffff\3\56\1\uffff\3\56\1\uffff\3\56\1\uffff\1\55"+
			"\1\uffff\1\56\1\uffff\1\56\4\uffff\1\56\2\uffff\5\56\3\uffff\1\56\1\uffff"+
			"\2\56\1\55\1\uffff\13\56\2\uffff\1\56\1\uffff\3\56\3\uffff\2\56\1\uffff"+
			"\4\56\3\uffff\1\56\10\uffff\2\56\2\uffff\1\56",
			"\1\61\1\uffff\1\61\5\uffff\1\61\15\uffff\1\61\5\uffff\1\61\2\uffff\1"+
			"\61\1\uffff\1\61\41\uffff\1\61\26\uffff\1\60\1\61\27\uffff\1\57",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\62",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\52\7\uffff\1\52\24\uffff\1\53",
			"",
			"",
			"\1\56\44\uffff\1\55\7\uffff\1\55",
			"",
			"",
			"",
			"",
			"",
			"\1\30\51\uffff\1\27"
	};

	static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
	static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
	static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
	static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
	static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
	static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
	static final short[][] DFA2_transition;

	static {
		int numStates = DFA2_transitionS.length;
		DFA2_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
		}
	}

	protected class DFA2 extends DFA {

		public DFA2(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 2;
			this.eot = DFA2_eot;
			this.eof = DFA2_eof;
			this.min = DFA2_min;
			this.max = DFA2_max;
			this.accept = DFA2_accept;
			this.special = DFA2_special;
			this.transition = DFA2_transition;
		}
		@Override
		public String getDescription() {
			return "235:1: cqlStatement returns [ParsedStatement stmt] : (st1= selectStatement |st2= insertStatement |st3= updateStatement |st4= batchStatement |st5= deleteStatement |st6= useStatement |st7= truncateStatement |st8= createKeyspaceStatement |st9= createTableStatement |st10= createIndexStatement |st11= dropKeyspaceStatement |st12= dropTableStatement |st13= dropIndexStatement |st14= alterTableStatement |st15= alterKeyspaceStatement |st16= grantPermissionsStatement |st17= revokePermissionsStatement |st18= listPermissionsStatement |st19= createUserStatement |st20= alterUserStatement |st21= dropUserStatement |st22= listUsersStatement |st23= createTriggerStatement |st24= dropTriggerStatement |st25= createTypeStatement |st26= alterTypeStatement |st27= dropTypeStatement |st28= createFunctionStatement |st29= dropFunctionStatement |st30= createAggregateStatement |st31= dropAggregateStatement |st32= createRoleStatement |st33= alterRoleStatement |st34= dropRoleStatement |st35= listRolesStatement |st36= grantRoleStatement |st37= revokeRoleStatement |st38= createMaterializedViewStatement |st39= dropMaterializedViewStatement |st40= alterMaterializedViewStatement );";
		}
	}

	static final String DFA14_eotS =
		"\73\uffff";
	static final String DFA14_eofS =
		"\73\uffff";
	static final String DFA14_minS =
		"\1\24\33\37\1\uffff\1\24\1\uffff\1\24\2\uffff\30\37\1\uffff";
	static final String DFA14_maxS =
		"\1\u00a0\33\u00b3\1\uffff\1\u00a0\1\uffff\1\u00bc\2\uffff\30\u00b3\1\uffff";
	static final String DFA14_acceptS =
		"\34\uffff\1\5\1\uffff\1\1\1\uffff\1\3\1\4\30\uffff\1\2";
	static final String DFA14_specialS =
		"\73\uffff}>";
	static final String[] DFA14_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\33\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\1\uffff\1\34\1\3\1\uffff\1\32\2\3\3\uffff"+
			"\2\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\31\10\uffff\1\34\1\2",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\37\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\40\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\155\uffff\1\41\1\36\1\uffff\1\36\1\uffff\1\35",
			"\1\36\40\uffff\1\36\156\uffff\1\36\1\uffff\1\36\1\uffff\1\35",
			"",
			"\1\42\4\uffff\2\44\4\uffff\1\44\1\uffff\1\45\3\uffff\1\46\1\47\1\50"+
			"\1\uffff\2\44\1\uffff\2\44\1\71\1\51\1\uffff\1\44\1\67\1\52\3\uffff\1"+
			"\44\1\53\3\uffff\3\44\1\54\1\uffff\1\44\1\uffff\2\44\4\uffff\1\55\1\uffff"+
			"\2\44\1\uffff\1\56\2\uffff\1\44\1\36\1\44\1\uffff\3\44\1\uffff\3\44\3"+
			"\uffff\1\44\1\uffff\1\44\4\uffff\1\44\2\uffff\5\44\3\uffff\1\44\1\uffff"+
			"\2\44\2\uffff\1\44\1\57\4\44\1\60\1\70\1\61\1\66\1\62\1\uffff\1\34\1"+
			"\44\1\uffff\1\36\2\44\3\uffff\2\44\1\uffff\1\63\1\44\1\64\1\65\3\uffff"+
			"\1\36\11\uffff\1\43",
			"",
			"\1\34\1\72\3\uffff\2\34\4\uffff\1\34\1\uffff\1\34\3\uffff\3\34\1\uffff"+
			"\2\34\1\uffff\4\34\1\uffff\3\34\3\uffff\2\34\3\uffff\4\34\1\uffff\1\34"+
			"\1\uffff\2\34\4\uffff\1\34\1\uffff\2\34\1\uffff\1\34\2\uffff\3\34\1\uffff"+
			"\3\34\1\uffff\3\34\3\uffff\1\34\1\uffff\1\34\4\uffff\1\34\2\uffff\5\34"+
			"\3\uffff\1\34\1\uffff\2\34\2\uffff\13\34\1\uffff\2\34\1\uffff\3\34\3"+
			"\uffff\2\34\1\uffff\4\34\3\uffff\1\34\10\uffff\2\34\16\uffff\1\34\14"+
			"\uffff\1\72",
			"",
			"",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			"\1\36\40\uffff\1\36\155\uffff\1\34\1\36\1\uffff\1\36\1\uffff\1\36",
			""
	};

	static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
	static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
	static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
	static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
	static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
	static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
	static final short[][] DFA14_transition;

	static {
		int numStates = DFA14_transitionS.length;
		DFA14_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
		}
	}

	protected class DFA14 extends DFA {

		public DFA14(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 14;
			this.eot = DFA14_eot;
			this.eof = DFA14_eof;
			this.min = DFA14_min;
			this.max = DFA14_max;
			this.accept = DFA14_accept;
			this.special = DFA14_special;
			this.transition = DFA14_transition;
		}
		@Override
		public String getDescription() {
			return "332:8: (c= cident | K_COUNT '(' countArgument ')' | K_WRITETIME '(' c= cident ')' | K_TTL '(' c= cident ')' |f= functionName args= selectionFunctionArgs )";
		}
	}

	static final String DFA42_eotS =
		"\33\uffff";
	static final String DFA42_eofS =
		"\33\uffff";
	static final String DFA42_minS =
		"\1\24\30\100\2\uffff";
	static final String DFA42_maxS =
		"\1\u00a0\30\u00bb\2\uffff";
	static final String DFA42_acceptS =
		"\31\uffff\1\1\1\2";
	static final String DFA42_specialS =
		"\33\uffff}>";
	static final String[] DFA42_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\30\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\2\uffff\1\3\1\uffff\1\30\2\3\3\uffff\2"+
			"\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\30\11\uffff\1\2",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"\1\31\160\uffff\1\31\11\uffff\1\32",
			"",
			""
	};

	static final short[] DFA42_eot = DFA.unpackEncodedString(DFA42_eotS);
	static final short[] DFA42_eof = DFA.unpackEncodedString(DFA42_eofS);
	static final char[] DFA42_min = DFA.unpackEncodedStringToUnsignedChars(DFA42_minS);
	static final char[] DFA42_max = DFA.unpackEncodedStringToUnsignedChars(DFA42_maxS);
	static final short[] DFA42_accept = DFA.unpackEncodedString(DFA42_acceptS);
	static final short[] DFA42_special = DFA.unpackEncodedString(DFA42_specialS);
	static final short[][] DFA42_transition;

	static {
		int numStates = DFA42_transitionS.length;
		DFA42_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA42_transition[i] = DFA.unpackEncodedString(DFA42_transitionS[i]);
		}
	}

	protected class DFA42 extends DFA {

		public DFA42(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 42;
			this.eot = DFA42_eot;
			this.eof = DFA42_eof;
			this.min = DFA42_min;
			this.max = DFA42_max;
			this.accept = DFA42_accept;
			this.special = DFA42_special;
			this.transition = DFA42_transition;
		}
		@Override
		public String getDescription() {
			return "500:1: deleteOp returns [Operation.RawDeletion op] : (c= cident |c= cident '[' t= term ']' );";
		}
	}

	static final String DFA105_eotS =
		"\40\uffff";
	static final String DFA105_eofS =
		"\6\uffff\30\36\2\uffff";
	static final String DFA105_minS =
		"\1\30\2\uffff\1\24\2\uffff\30\u008e\2\uffff";
	static final String DFA105_maxS =
		"\1\u0095\2\uffff\1\u00a0\2\uffff\30\u00b5\2\uffff";
	static final String DFA105_acceptS =
		"\1\uffff\1\1\1\2\1\uffff\1\5\1\6\30\uffff\1\3\1\4";
	static final String DFA105_specialS =
		"\40\uffff}>";
	static final String[] DFA105_transitionS = {
			"\1\2\3\uffff\1\1\34\uffff\1\3\65\uffff\1\5\45\uffff\1\4",
			"",
			"",
			"\1\6\4\uffff\2\10\4\uffff\1\10\1\uffff\1\11\3\uffff\1\12\1\13\1\14\1"+
			"\uffff\2\10\1\uffff\2\10\1\35\1\15\1\uffff\1\10\1\33\1\16\3\uffff\1\10"+
			"\1\17\3\uffff\3\10\1\20\1\uffff\1\10\1\uffff\2\10\4\uffff\1\21\1\uffff"+
			"\2\10\1\uffff\1\22\2\uffff\1\10\1\35\1\10\1\uffff\3\10\1\uffff\3\10\3"+
			"\uffff\1\10\1\uffff\1\10\4\uffff\1\10\2\uffff\5\10\3\uffff\1\10\1\uffff"+
			"\2\10\2\uffff\1\10\1\23\4\10\1\24\1\34\1\25\1\32\1\26\2\uffff\1\10\1"+
			"\uffff\1\35\2\10\3\uffff\2\10\1\uffff\1\27\1\10\1\30\1\31\3\uffff\1\35"+
			"\11\uffff\1\7",
			"",
			"",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"\1\37\46\uffff\1\36",
			"",
			""
	};

	static final short[] DFA105_eot = DFA.unpackEncodedString(DFA105_eotS);
	static final short[] DFA105_eof = DFA.unpackEncodedString(DFA105_eofS);
	static final char[] DFA105_min = DFA.unpackEncodedStringToUnsignedChars(DFA105_minS);
	static final char[] DFA105_max = DFA.unpackEncodedStringToUnsignedChars(DFA105_maxS);
	static final short[] DFA105_accept = DFA.unpackEncodedString(DFA105_acceptS);
	static final short[] DFA105_special = DFA.unpackEncodedString(DFA105_specialS);
	static final short[][] DFA105_transition;

	static {
		int numStates = DFA105_transitionS.length;
		DFA105_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA105_transition[i] = DFA.unpackEncodedString(DFA105_transitionS[i]);
		}
	}

	protected class DFA105 extends DFA {

		public DFA105(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 105;
			this.eot = DFA105_eot;
			this.eof = DFA105_eof;
			this.min = DFA105_min;
			this.max = DFA105_max;
			this.accept = DFA105_accept;
			this.special = DFA105_special;
			this.transition = DFA105_transition;
		}
		@Override
		public String getDescription() {
			return "826:11: ( K_ALTER id= cident K_TYPE v= comparatorType | K_ADD id= cident v= comparatorType ( K_STATIC )? | K_DROP id= cident | K_DROP id= cident K_USING K_TIMESTAMP t= INTEGER | K_WITH properties[attrs] | K_RENAME id1= cident K_TO toId1= cident ( K_AND idn= cident K_TO toIdn= cident )* )";
		}
	}

	static final String DFA144_eotS =
		"\34\uffff";
	static final String DFA144_eofS =
		"\1\uffff\31\33\2\uffff";
	static final String DFA144_minS =
		"\1\24\31\u00b3\2\uffff";
	static final String DFA144_maxS =
		"\1\u00a0\31\u00b5\2\uffff";
	static final String DFA144_acceptS =
		"\32\uffff\1\1\1\2";
	static final String DFA144_specialS =
		"\34\uffff}>";
	static final String[] DFA144_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\30\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\2\uffff\1\3\1\uffff\1\30\2\3\3\uffff\2"+
			"\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\30\10\uffff\1\31\1\2",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"\1\32\1\uffff\1\33",
			"",
			""
	};

	static final short[] DFA144_eot = DFA.unpackEncodedString(DFA144_eotS);
	static final short[] DFA144_eof = DFA.unpackEncodedString(DFA144_eofS);
	static final char[] DFA144_min = DFA.unpackEncodedStringToUnsignedChars(DFA144_minS);
	static final char[] DFA144_max = DFA.unpackEncodedStringToUnsignedChars(DFA144_maxS);
	static final short[] DFA144_accept = DFA.unpackEncodedString(DFA144_acceptS);
	static final short[] DFA144_special = DFA.unpackEncodedString(DFA144_specialS);
	static final short[][] DFA144_transition;

	static {
		int numStates = DFA144_transitionS.length;
		DFA144_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA144_transition[i] = DFA.unpackEncodedString(DFA144_transitionS[i]);
		}
	}

	protected class DFA144 extends DFA {

		public DFA144(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 144;
			this.eot = DFA144_eot;
			this.eof = DFA144_eof;
			this.min = DFA144_min;
			this.max = DFA144_max;
			this.accept = DFA144_accept;
			this.special = DFA144_special;
			this.transition = DFA144_transition;
		}
		@Override
		public String getDescription() {
			return "1202:7: ( ksName[name] '.' )?";
		}
	}

	static final String DFA145_eotS =
		"\34\uffff";
	static final String DFA145_eofS =
		"\1\uffff\31\33\2\uffff";
	static final String DFA145_minS =
		"\1\24\31\30\2\uffff";
	static final String DFA145_maxS =
		"\1\u00a0\31\u00b5\2\uffff";
	static final String DFA145_acceptS =
		"\32\uffff\1\1\1\2";
	static final String DFA145_specialS =
		"\34\uffff}>";
	static final String[] DFA145_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\30\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\2\uffff\1\3\1\uffff\1\30\2\3\3\uffff\2"+
			"\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\30\10\uffff\1\31\1\2",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"\1\33\2\uffff\2\33\2\uffff\1\33\31\uffff\1\33\6\uffff\1\33\20\uffff"+
			"\1\33\6\uffff\1\33\7\uffff\1\33\3\uffff\1\33\3\uffff\1\33\2\uffff\1\33"+
			"\2\uffff\2\33\6\uffff\1\33\13\uffff\1\33\13\uffff\1\33\5\uffff\2\33\30"+
			"\uffff\1\33\4\uffff\1\32\1\uffff\1\33",
			"",
			""
	};

	static final short[] DFA145_eot = DFA.unpackEncodedString(DFA145_eotS);
	static final short[] DFA145_eof = DFA.unpackEncodedString(DFA145_eofS);
	static final char[] DFA145_min = DFA.unpackEncodedStringToUnsignedChars(DFA145_minS);
	static final char[] DFA145_max = DFA.unpackEncodedStringToUnsignedChars(DFA145_maxS);
	static final short[] DFA145_accept = DFA.unpackEncodedString(DFA145_acceptS);
	static final short[] DFA145_special = DFA.unpackEncodedString(DFA145_specialS);
	static final short[][] DFA145_transition;

	static {
		int numStates = DFA145_transitionS.length;
		DFA145_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA145_transition[i] = DFA.unpackEncodedString(DFA145_transitionS[i]);
		}
	}

	protected class DFA145 extends DFA {

		public DFA145(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 145;
			this.eot = DFA145_eot;
			this.eof = DFA145_eof;
			this.min = DFA145_min;
			this.max = DFA145_max;
			this.accept = DFA145_accept;
			this.special = DFA145_special;
			this.transition = DFA145_transition;
		}
		@Override
		public String getDescription() {
			return "1207:7: ( ksName[name] '.' )?";
		}
	}

	static final String DFA163_eotS =
		"\42\uffff";
	static final String DFA163_eofS =
		"\42\uffff";
	static final String DFA163_minS =
		"\1\6\2\uffff\1\6\4\uffff\30\u00ae\1\u00b3\1\uffff";
	static final String DFA163_maxS =
		"\1\u00bf\2\uffff\1\u00c0\4\uffff\31\u00b4\1\uffff";
	static final String DFA163_acceptS =
		"\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\31\uffff\1\3";
	static final String DFA163_specialS =
		"\42\uffff}>";
	static final String[] DFA163_transitionS = {
			"\1\1\7\uffff\1\1\3\uffff\1\1\2\uffff\1\1\64\uffff\1\1\23\uffff\1\1\4"+
			"\uffff\1\5\73\uffff\1\7\3\uffff\1\1\2\uffff\1\1\7\uffff\1\4\3\uffff\1"+
			"\1\1\uffff\1\6\6\uffff\1\2\3\uffff\1\3",
			"",
			"",
			"\1\2\7\uffff\1\2\3\uffff\1\2\1\uffff\1\10\1\2\3\uffff\2\12\4\uffff\1"+
			"\12\1\uffff\1\13\3\uffff\1\14\1\15\1\16\1\uffff\2\12\1\uffff\2\12\1\37"+
			"\1\17\1\uffff\1\12\1\35\1\20\3\uffff\1\12\1\21\3\uffff\3\12\1\22\1\uffff"+
			"\1\12\1\uffff\2\12\4\uffff\1\23\1\2\2\12\1\uffff\1\24\2\uffff\1\12\1"+
			"\40\1\12\1\uffff\3\12\1\uffff\3\12\2\uffff\1\2\1\12\1\uffff\1\12\1\uffff"+
			"\1\2\2\uffff\1\12\2\uffff\5\12\3\uffff\1\12\1\uffff\2\12\2\uffff\1\12"+
			"\1\25\4\12\1\26\1\36\1\27\1\34\1\30\1\uffff\1\2\1\12\1\uffff\1\40\2\12"+
			"\3\uffff\2\12\1\uffff\1\31\1\12\1\32\1\33\3\uffff\1\40\10\uffff\1\2\1"+
			"\11\2\uffff\1\2\2\uffff\1\2\7\uffff\1\2\3\uffff\1\2\1\uffff\1\2\6\uffff"+
			"\1\2\3\uffff\2\2",
			"",
			"",
			"",
			"",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\4\uffff\1\2\1\41",
			"\1\2\1\41",
			""
	};

	static final short[] DFA163_eot = DFA.unpackEncodedString(DFA163_eotS);
	static final short[] DFA163_eof = DFA.unpackEncodedString(DFA163_eofS);
	static final char[] DFA163_min = DFA.unpackEncodedStringToUnsignedChars(DFA163_minS);
	static final char[] DFA163_max = DFA.unpackEncodedStringToUnsignedChars(DFA163_maxS);
	static final short[] DFA163_accept = DFA.unpackEncodedString(DFA163_acceptS);
	static final short[] DFA163_special = DFA.unpackEncodedString(DFA163_specialS);
	static final short[][] DFA163_transition;

	static {
		int numStates = DFA163_transitionS.length;
		DFA163_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA163_transition[i] = DFA.unpackEncodedString(DFA163_transitionS[i]);
		}
	}

	protected class DFA163 extends DFA {

		public DFA163(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 163;
			this.eot = DFA163_eot;
			this.eof = DFA163_eof;
			this.min = DFA163_min;
			this.max = DFA163_max;
			this.accept = DFA163_accept;
			this.special = DFA163_special;
			this.transition = DFA163_transition;
		}
		@Override
		public String getDescription() {
			return "1296:1: value returns [Term.Raw value] : (c= constant |l= collectionLiteral |u= usertypeLiteral |t= tupleLiteral | K_NULL | ':' id= noncol_ident | QMARK );";
		}
	}

	static final String DFA165_eotS =
		"\33\uffff";
	static final String DFA165_eofS =
		"\1\uffff\30\32\2\uffff";
	static final String DFA165_minS =
		"\1\24\30\u00ae\2\uffff";
	static final String DFA165_maxS =
		"\1\u00a0\30\u00b5\2\uffff";
	static final String DFA165_acceptS =
		"\31\uffff\1\1\1\2";
	static final String DFA165_specialS =
		"\33\uffff}>";
	static final String[] DFA165_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\31\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\1\uffff\1\32\1\3\1\uffff\1\31\2\3\3\uffff"+
			"\2\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\31\10\uffff\1\31\1\2",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"\1\32\4\uffff\1\31\1\uffff\1\32",
			"",
			""
	};

	static final short[] DFA165_eot = DFA.unpackEncodedString(DFA165_eotS);
	static final short[] DFA165_eof = DFA.unpackEncodedString(DFA165_eofS);
	static final char[] DFA165_min = DFA.unpackEncodedStringToUnsignedChars(DFA165_minS);
	static final char[] DFA165_max = DFA.unpackEncodedStringToUnsignedChars(DFA165_maxS);
	static final short[] DFA165_accept = DFA.unpackEncodedString(DFA165_acceptS);
	static final short[] DFA165_special = DFA.unpackEncodedString(DFA165_specialS);
	static final short[][] DFA165_transition;

	static {
		int numStates = DFA165_transitionS.length;
		DFA165_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA165_transition[i] = DFA.unpackEncodedString(DFA165_transitionS[i]);
		}
	}

	protected class DFA165 extends DFA {

		public DFA165(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 165;
			this.eot = DFA165_eot;
			this.eof = DFA165_eof;
			this.min = DFA165_min;
			this.max = DFA165_max;
			this.accept = DFA165_accept;
			this.special = DFA165_special;
			this.transition = DFA165_transition;
		}
		@Override
		public String getDescription() {
			return "1314:7: (ks= keyspaceName '.' )?";
		}
	}

	static final String DFA167_eotS =
		"\70\uffff";
	static final String DFA167_eofS =
		"\70\uffff";
	static final String DFA167_minS =
		"\1\24\30\u00ae\1\u00b3\1\u00ae\1\u00b3\1\24\1\6\30\u00ae\2\uffff";
	static final String DFA167_maxS =
		"\1\u00a0\31\u00b3\1\u00ae\1\u00b3\1\u00a0\1\u00bf\30\u00ae\2\uffff";
	static final String DFA167_acceptS =
		"\66\uffff\1\1\1\2";
	static final String DFA167_specialS =
		"\70\uffff}>";
	static final String[] DFA167_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\33\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\1\uffff\1\32\1\3\1\uffff\1\33\2\3\3\uffff"+
			"\2\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\33\10\uffff\1\31\1\2",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\35\4\uffff\1\34",
			"\1\34",
			"\1\35",
			"\1\34",
			"\1\36\4\uffff\2\40\4\uffff\1\40\1\uffff\1\41\3\uffff\1\42\1\43\1\44"+
			"\1\uffff\2\40\1\uffff\2\40\1\65\1\45\1\uffff\1\40\1\63\1\46\3\uffff\1"+
			"\40\1\47\3\uffff\3\40\1\50\1\uffff\1\40\1\uffff\2\40\4\uffff\1\51\1\uffff"+
			"\2\40\1\uffff\1\52\2\uffff\1\40\1\uffff\1\40\1\uffff\3\40\1\uffff\3\40"+
			"\3\uffff\1\40\1\uffff\1\40\4\uffff\1\40\2\uffff\5\40\3\uffff\1\40\1\uffff"+
			"\2\40\2\uffff\1\40\1\53\4\40\1\54\1\64\1\55\1\62\1\56\1\uffff\1\32\1"+
			"\40\2\uffff\2\40\3\uffff\2\40\1\uffff\1\57\1\40\1\60\1\61\15\uffff\1"+
			"\37",
			"\1\67\7\uffff\1\67\3\uffff\1\67\1\uffff\2\67\3\uffff\2\67\4\uffff\1"+
			"\67\1\uffff\1\67\3\uffff\3\67\1\uffff\2\67\1\uffff\4\67\1\uffff\3\67"+
			"\3\uffff\2\67\3\uffff\4\67\1\uffff\1\67\1\uffff\2\67\4\uffff\4\67\1\uffff"+
			"\1\67\2\uffff\3\67\1\uffff\3\67\1\uffff\3\67\2\uffff\2\67\1\uffff\1\67"+
			"\1\uffff\1\67\2\uffff\1\67\2\uffff\5\67\3\uffff\1\67\1\uffff\2\67\2\uffff"+
			"\13\67\1\uffff\2\67\1\uffff\3\67\3\uffff\2\67\1\uffff\4\67\3\uffff\1"+
			"\67\10\uffff\2\67\2\uffff\1\67\2\uffff\1\67\7\uffff\1\67\1\66\2\uffff"+
			"\1\67\1\uffff\1\67\6\uffff\1\67\3\uffff\1\67",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"\1\35",
			"",
			""
	};

	static final short[] DFA167_eot = DFA.unpackEncodedString(DFA167_eotS);
	static final short[] DFA167_eof = DFA.unpackEncodedString(DFA167_eofS);
	static final char[] DFA167_min = DFA.unpackEncodedStringToUnsignedChars(DFA167_minS);
	static final char[] DFA167_max = DFA.unpackEncodedStringToUnsignedChars(DFA167_maxS);
	static final short[] DFA167_accept = DFA.unpackEncodedString(DFA167_acceptS);
	static final short[] DFA167_special = DFA.unpackEncodedString(DFA167_specialS);
	static final short[][] DFA167_transition;

	static {
		int numStates = DFA167_transitionS.length;
		DFA167_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA167_transition[i] = DFA.unpackEncodedString(DFA167_transitionS[i]);
		}
	}

	protected class DFA167 extends DFA {

		public DFA167(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 167;
			this.eot = DFA167_eot;
			this.eof = DFA167_eof;
			this.min = DFA167_min;
			this.max = DFA167_max;
			this.accept = DFA167_accept;
			this.special = DFA167_special;
			this.transition = DFA167_transition;
		}
		@Override
		public String getDescription() {
			return "1325:1: function returns [Term.Raw t] : (f= functionName '(' ')' |f= functionName '(' args= functionArgs ')' );";
		}
	}

	static final String DFA169_eotS =
		"\105\uffff";
	static final String DFA169_eofS =
		"\3\uffff\1\1\41\uffff\1\1\6\uffff\31\41";
	static final String DFA169_minS =
		"\1\6\1\uffff\1\6\1\33\1\uffff\1\u00af\30\u00ae\1\u00af\2\u00ae\1\uffff"+
		"\1\u00ae\1\u00b3\1\u00ae\1\6\1\24\1\6\1\151\3\u00ae\31\33";
	static final String DFA169_maxS =
		"\1\u00bf\1\uffff\1\u00bf\1\u00c0\1\uffff\1\u00b1\2\u00b3\1\u00b6\26\u00b3"+
		"\2\u00b6\1\uffff\1\u00b6\2\u00b3\1\u00c0\1\u00a0\1\u00bf\1\u00b3\3\u00af"+
		"\31\u00c0";
	static final String DFA169_acceptS =
		"\1\uffff\1\1\2\uffff\1\2\34\uffff\1\3\43\uffff";
	static final String DFA169_specialS =
		"\105\uffff}>";
	static final String[] DFA169_transitionS = {
			"\1\1\7\uffff\1\1\3\uffff\1\1\1\uffff\1\4\1\1\3\uffff\2\4\4\uffff\1\4"+
			"\1\uffff\1\4\3\uffff\3\4\1\uffff\2\4\1\uffff\4\4\1\uffff\3\4\3\uffff"+
			"\2\4\3\uffff\4\4\1\uffff\1\4\1\uffff\2\4\4\uffff\1\4\1\1\2\4\1\uffff"+
			"\1\4\2\uffff\3\4\1\uffff\3\4\1\uffff\3\4\2\uffff\1\1\1\4\1\uffff\1\4"+
			"\1\uffff\1\1\2\uffff\1\4\2\uffff\5\4\3\uffff\1\4\1\uffff\2\4\2\uffff"+
			"\13\4\1\uffff\2\4\1\uffff\3\4\3\uffff\2\4\1\uffff\4\4\3\uffff\1\4\10"+
			"\uffff\1\3\1\4\2\uffff\1\1\2\uffff\1\1\7\uffff\1\2\3\uffff\1\1\1\uffff"+
			"\1\1\6\uffff\1\1\3\uffff\1\1",
			"",
			"\1\1\7\uffff\1\1\3\uffff\1\1\1\uffff\1\6\1\1\3\uffff\2\44\4\uffff\1"+
			"\44\1\uffff\1\11\3\uffff\1\12\1\13\1\14\1\uffff\2\44\1\uffff\2\44\1\35"+
			"\1\15\1\uffff\1\44\1\33\1\16\3\uffff\1\44\1\17\3\uffff\3\44\1\20\1\uffff"+
			"\1\42\1\uffff\2\44\4\uffff\1\21\1\1\2\44\1\uffff\1\22\2\uffff\1\44\1"+
			"\36\1\44\1\uffff\3\44\1\uffff\1\37\1\44\1\10\2\uffff\1\1\1\44\1\uffff"+
			"\1\44\1\uffff\1\1\2\uffff\1\44\2\uffff\5\44\3\uffff\1\44\1\uffff\2\44"+
			"\1\uffff\1\41\1\44\1\23\4\44\1\24\1\34\1\25\1\32\1\26\1\uffff\1\1\1\44"+
			"\1\uffff\1\43\1\40\1\44\3\uffff\2\44\1\uffff\1\27\1\44\1\30\1\31\3\uffff"+
			"\1\43\10\uffff\1\1\1\7\2\uffff\1\5\2\uffff\1\1\7\uffff\1\1\3\uffff\1"+
			"\1\1\uffff\1\1\6\uffff\1\1\3\uffff\1\1",
			"\1\1\1\uffff\2\1\25\uffff\1\1\21\uffff\1\1\6\uffff\1\1\12\uffff\1\1"+
			"\17\uffff\1\1\2\uffff\1\1\2\uffff\1\1\33\uffff\1\1\11\uffff\1\1\32\uffff"+
			"\3\1\1\uffff\1\4\2\1\7\uffff\1\1\2\uffff\1\1",
			"",
			"\1\45\1\uffff\1\1",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46\2\uffff\1\41",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\1\4\uffff\1\46",
			"\1\41\3\uffff\1\46",
			"\1\1\1\41\3\uffff\1\46\2\uffff\1\41",
			"\1\1\1\41\3\uffff\1\46\2\uffff\1\41",
			"",
			"\1\1\1\41\3\uffff\1\46\2\uffff\1\41",
			"\1\46",
			"\1\1\1\41\3\uffff\1\46",
			"\1\41\7\uffff\1\41\3\uffff\1\41\1\uffff\2\41\3\uffff\2\41\1\1\1\uffff"+
			"\2\1\1\41\1\uffff\1\41\3\uffff\3\41\1\uffff\2\41\1\uffff\4\41\1\uffff"+
			"\3\41\1\1\2\uffff\2\41\3\uffff\4\41\1\uffff\1\41\1\uffff\2\41\1\uffff"+
			"\1\1\2\uffff\4\41\1\1\1\41\2\uffff\3\41\1\uffff\3\41\1\1\3\41\2\uffff"+
			"\2\41\1\uffff\1\41\1\uffff\1\41\2\uffff\1\41\1\uffff\1\1\2\41\1\50\2"+
			"\41\1\1\2\uffff\1\41\1\uffff\2\41\2\uffff\13\41\1\uffff\2\41\1\uffff"+
			"\3\41\1\uffff\1\1\1\uffff\2\41\1\uffff\4\41\1\uffff\1\1\1\uffff\1\41"+
			"\10\uffff\2\41\2\uffff\1\41\2\uffff\1\41\7\uffff\1\41\3\1\1\41\1\uffff"+
			"\1\47\1\1\5\uffff\1\41\1\uffff\1\1\1\uffff\1\41\1\1",
			"\1\51\4\uffff\2\53\4\uffff\1\53\1\uffff\1\1\3\uffff\3\1\1\uffff\2\53"+
			"\1\uffff\2\53\2\1\1\uffff\1\53\2\1\3\uffff\1\53\1\1\3\uffff\3\53\1\1"+
			"\1\uffff\1\53\1\uffff\2\53\4\uffff\1\1\1\uffff\2\53\1\uffff\1\1\2\uffff"+
			"\1\53\1\41\1\53\1\uffff\3\53\1\uffff\3\53\3\uffff\1\53\1\uffff\1\53\4"+
			"\uffff\1\53\2\uffff\5\53\3\uffff\1\53\1\uffff\2\53\2\uffff\1\53\1\1\4"+
			"\53\5\1\1\uffff\1\1\1\53\2\uffff\2\53\3\uffff\2\53\1\uffff\1\1\1\53\2"+
			"\1\15\uffff\1\52",
			"\1\1\7\uffff\1\1\3\uffff\1\1\1\uffff\1\54\1\1\3\uffff\2\56\4\uffff\1"+
			"\56\1\uffff\1\57\3\uffff\1\60\1\61\1\62\1\uffff\2\56\1\uffff\2\56\1\103"+
			"\1\63\1\uffff\1\56\1\101\1\64\3\uffff\1\56\1\65\3\uffff\3\56\1\66\1\uffff"+
			"\1\56\1\uffff\2\56\4\uffff\1\67\1\1\2\56\1\uffff\1\70\2\uffff\1\56\1"+
			"\104\1\56\1\uffff\3\56\1\uffff\3\56\2\uffff\1\1\1\56\1\uffff\1\56\1\uffff"+
			"\1\1\2\uffff\1\56\2\uffff\5\56\3\uffff\1\56\1\uffff\2\56\2\uffff\1\56"+
			"\1\71\4\56\1\72\1\102\1\73\1\100\1\74\1\uffff\1\1\1\56\1\uffff\1\104"+
			"\2\56\3\uffff\2\56\1\uffff\1\75\1\56\1\76\1\77\3\uffff\1\104\10\uffff"+
			"\1\1\1\55\2\uffff\1\1\2\uffff\1\1\7\uffff\1\1\3\uffff\1\1\1\uffff\1\1"+
			"\6\uffff\1\1\3\uffff\1\1",
			"\1\1\104\uffff\1\41\4\uffff\1\41",
			"\1\1\1\41",
			"\1\1\1\41",
			"\1\1\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\31\uffff\1\1\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41",
			"\1\41\1\uffff\2\41\25\uffff\1\41\21\uffff\1\41\6\uffff\1\41\12\uffff"+
			"\1\41\17\uffff\1\41\2\uffff\1\41\2\uffff\1\41\33\uffff\1\41\11\uffff"+
			"\1\41\32\uffff\3\41\1\uffff\1\1\2\41\7\uffff\1\41\2\uffff\1\41"
	};

	static final short[] DFA169_eot = DFA.unpackEncodedString(DFA169_eotS);
	static final short[] DFA169_eof = DFA.unpackEncodedString(DFA169_eofS);
	static final char[] DFA169_min = DFA.unpackEncodedStringToUnsignedChars(DFA169_minS);
	static final char[] DFA169_max = DFA.unpackEncodedStringToUnsignedChars(DFA169_maxS);
	static final short[] DFA169_accept = DFA.unpackEncodedString(DFA169_acceptS);
	static final short[] DFA169_special = DFA.unpackEncodedString(DFA169_specialS);
	static final short[][] DFA169_transition;

	static {
		int numStates = DFA169_transitionS.length;
		DFA169_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA169_transition[i] = DFA.unpackEncodedString(DFA169_transitionS[i]);
		}
	}

	protected class DFA169 extends DFA {

		public DFA169(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 169;
			this.eot = DFA169_eot;
			this.eof = DFA169_eof;
			this.min = DFA169_min;
			this.max = DFA169_max;
			this.accept = DFA169_accept;
			this.special = DFA169_special;
			this.transition = DFA169_transition;
		}
		@Override
		public String getDescription() {
			return "1335:1: term returns [Term.Raw term] : (v= value |f= function | '(' c= comparatorType ')' t= term );";
		}
	}

	static final String DFA172_eotS =
		"\35\uffff";
	static final String DFA172_eofS =
		"\35\uffff";
	static final String DFA172_minS =
		"\1\6\1\uffff\31\25\2\uffff";
	static final String DFA172_maxS =
		"\1\u00bf\1\uffff\31\u00b3\2\uffff";
	static final String DFA172_acceptS =
		"\1\uffff\1\1\31\uffff\1\2\1\3";
	static final String DFA172_specialS =
		"\35\uffff}>";
	static final String[] DFA172_transitionS = {
			"\1\1\7\uffff\1\1\3\uffff\1\1\1\uffff\1\2\1\1\3\uffff\2\4\4\uffff\1\4"+
			"\1\uffff\1\5\3\uffff\1\6\1\7\1\10\1\uffff\2\4\1\uffff\2\4\1\31\1\11\1"+
			"\uffff\1\4\1\27\1\12\3\uffff\1\4\1\13\3\uffff\3\4\1\14\1\uffff\1\4\1"+
			"\uffff\2\4\4\uffff\1\15\1\1\2\4\1\uffff\1\16\2\uffff\1\4\1\32\1\4\1\uffff"+
			"\3\4\1\uffff\3\4\2\uffff\1\1\1\4\1\uffff\1\4\1\uffff\1\1\2\uffff\1\4"+
			"\2\uffff\5\4\3\uffff\1\4\1\uffff\2\4\2\uffff\1\4\1\17\4\4\1\20\1\30\1"+
			"\21\1\26\1\22\1\uffff\1\1\1\4\1\uffff\1\32\2\4\3\uffff\2\4\1\uffff\1"+
			"\23\1\4\1\24\1\25\3\uffff\1\32\10\uffff\1\1\1\3\2\uffff\1\1\2\uffff\1"+
			"\1\7\uffff\1\1\3\uffff\1\1\1\uffff\1\1\6\uffff\1\1\3\uffff\1\1",
			"",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u0098\uffff\1\1\1\uffff\1\33\1\uffff\1\33\1\1",
			"\1\34\u009a\uffff\1\33\1\uffff\1\33\1\1",
			"",
			""
	};

	static final short[] DFA172_eot = DFA.unpackEncodedString(DFA172_eotS);
	static final short[] DFA172_eof = DFA.unpackEncodedString(DFA172_eofS);
	static final char[] DFA172_min = DFA.unpackEncodedStringToUnsignedChars(DFA172_minS);
	static final char[] DFA172_max = DFA.unpackEncodedStringToUnsignedChars(DFA172_maxS);
	static final short[] DFA172_accept = DFA.unpackEncodedString(DFA172_acceptS);
	static final short[] DFA172_special = DFA.unpackEncodedString(DFA172_specialS);
	static final short[][] DFA172_transition;

	static {
		int numStates = DFA172_transitionS.length;
		DFA172_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA172_transition[i] = DFA.unpackEncodedString(DFA172_transitionS[i]);
		}
	}

	protected class DFA172 extends DFA {

		public DFA172(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 172;
			this.eot = DFA172_eot;
			this.eof = DFA172_eof;
			this.min = DFA172_min;
			this.max = DFA172_max;
			this.accept = DFA172_accept;
			this.special = DFA172_special;
			this.transition = DFA172_transition;
		}
		@Override
		public String getDescription() {
			return "1350:1: normalColumnOperation[List<Pair<ColumnIdentifier.Raw, Operation.RawUpdate>> operations, ColumnIdentifier.Raw key] : (t= term ( '+' c= cident )? |c= cident sig= ( '+' | '-' ) t= term |c= cident i= INTEGER );";
		}
	}

	static final String DFA178_eotS =
		"\34\uffff";
	static final String DFA178_eofS =
		"\34\uffff";
	static final String DFA178_minS =
		"\1\24\30\u00b8\1\6\2\uffff";
	static final String DFA178_maxS =
		"\1\u00a0\30\u00b8\1\u00bf\2\uffff";
	static final String DFA178_acceptS =
		"\32\uffff\1\1\1\2";
	static final String DFA178_specialS =
		"\34\uffff}>";
	static final String[] DFA178_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\30\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\2\uffff\1\3\1\uffff\1\30\2\3\3\uffff\2"+
			"\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\30\11\uffff\1\2",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\31",
			"\1\32\7\uffff\1\32\3\uffff\1\32\2\uffff\1\32\3\uffff\2\32\4\uffff\1"+
			"\32\1\uffff\1\32\3\uffff\3\32\1\uffff\2\32\1\uffff\4\32\1\uffff\3\32"+
			"\3\uffff\2\32\3\uffff\4\32\1\uffff\1\32\1\uffff\2\32\4\uffff\4\32\1\uffff"+
			"\1\32\2\uffff\3\32\1\uffff\3\32\1\uffff\3\32\2\uffff\2\32\1\uffff\1\32"+
			"\4\uffff\1\32\2\uffff\5\32\3\uffff\1\32\1\uffff\2\32\2\uffff\13\32\2"+
			"\uffff\1\32\1\uffff\3\32\3\uffff\2\32\1\uffff\4\32\3\uffff\1\32\14\uffff"+
			"\1\32\2\uffff\1\32\13\uffff\1\32\14\uffff\1\33",
			"",
			""
	};

	static final short[] DFA178_eot = DFA.unpackEncodedString(DFA178_eotS);
	static final short[] DFA178_eof = DFA.unpackEncodedString(DFA178_eofS);
	static final char[] DFA178_min = DFA.unpackEncodedStringToUnsignedChars(DFA178_minS);
	static final char[] DFA178_max = DFA.unpackEncodedStringToUnsignedChars(DFA178_maxS);
	static final short[] DFA178_accept = DFA.unpackEncodedString(DFA178_acceptS);
	static final short[] DFA178_special = DFA.unpackEncodedString(DFA178_specialS);
	static final short[][] DFA178_transition;

	static {
		int numStates = DFA178_transitionS.length;
		DFA178_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA178_transition[i] = DFA.unpackEncodedString(DFA178_transitionS[i]);
		}
	}

	protected class DFA178 extends DFA {

		public DFA178(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 178;
			this.eot = DFA178_eot;
			this.eof = DFA178_eof;
			this.min = DFA178_min;
			this.max = DFA178_max;
			this.accept = DFA178_accept;
			this.special = DFA178_special;
			this.transition = DFA178_transition;
		}
		@Override
		public String getDescription() {
			return "1409:1: property[PropertyDefinitions props] : (k= noncol_ident '=' simple= propertyValue |k= noncol_ident '=' map= mapLiteral );";
		}
	}

	static final String DFA184_eotS =
		"\74\uffff";
	static final String DFA184_eofS =
		"\74\uffff";
	static final String DFA184_minS =
		"\1\24\30\55\1\uffff\1\24\2\uffff\1\u009f\2\uffff\30\55\4\uffff";
	static final String DFA184_maxS =
		"\1\u00ae\30\u00bb\1\uffff\1\u00ae\2\uffff\1\u00b4\2\uffff\30\u00bb\4\uffff";
	static final String DFA184_acceptS =
		"\31\uffff\1\3\1\uffff\1\1\1\2\1\uffff\1\6\1\7\30\uffff\1\11\1\4\1\5\1"+
		"\10";
	static final String DFA184_specialS =
		"\74\uffff}>";
	static final String[] DFA184_transitionS = {
			"\1\1\4\uffff\2\3\4\uffff\1\3\1\uffff\1\4\3\uffff\1\5\1\6\1\7\1\uffff"+
			"\2\3\1\uffff\2\3\1\30\1\10\1\uffff\1\3\1\26\1\11\3\uffff\1\3\1\12\3\uffff"+
			"\3\3\1\13\1\uffff\1\3\1\uffff\2\3\4\uffff\1\14\1\uffff\2\3\1\uffff\1"+
			"\15\2\uffff\1\3\1\30\1\3\1\uffff\3\3\1\uffff\3\3\3\uffff\1\3\1\uffff"+
			"\1\3\4\uffff\1\3\2\uffff\5\3\3\uffff\1\3\1\uffff\2\3\2\uffff\1\3\1\16"+
			"\4\3\1\17\1\27\1\20\1\25\1\21\1\uffff\1\31\1\3\1\uffff\1\30\2\3\3\uffff"+
			"\2\3\1\uffff\1\22\1\3\1\23\1\24\3\uffff\1\30\11\uffff\1\2\15\uffff\1"+
			"\32",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"\1\36\31\uffff\1\35\10\uffff\1\34\134\uffff\1\33\10\uffff\5\33\1\37",
			"",
			"\1\40\4\uffff\2\42\4\uffff\1\42\1\uffff\1\43\3\uffff\1\44\1\45\1\46"+
			"\1\uffff\2\42\1\uffff\2\42\1\67\1\47\1\uffff\1\42\1\65\1\50\3\uffff\1"+
			"\42\1\51\3\uffff\3\42\1\52\1\uffff\1\42\1\uffff\2\42\4\uffff\1\53\1\uffff"+
			"\2\42\1\uffff\1\54\2\uffff\1\42\1\67\1\42\1\uffff\3\42\1\uffff\3\42\3"+
			"\uffff\1\42\1\uffff\1\42\4\uffff\1\42\2\uffff\5\42\3\uffff\1\42\1\uffff"+
			"\2\42\2\uffff\1\42\1\55\4\42\1\56\1\66\1\57\1\64\1\60\1\uffff\1\70\1"+
			"\42\1\uffff\1\67\2\42\3\uffff\2\42\1\uffff\1\61\1\42\1\62\1\63\3\uffff"+
			"\1\67\11\uffff\1\41\15\uffff\1\70",
			"",
			"",
			"\1\71\16\uffff\1\72\5\uffff\1\71",
			"",
			"",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"\1\70\31\uffff\1\70\10\uffff\1\70\134\uffff\1\70\1\uffff\1\73\1\uffff"+
			"\1\73\4\uffff\6\70",
			"",
			"",
			"",
			""
	};

	static final short[] DFA184_eot = DFA.unpackEncodedString(DFA184_eotS);
	static final short[] DFA184_eof = DFA.unpackEncodedString(DFA184_eofS);
	static final char[] DFA184_min = DFA.unpackEncodedStringToUnsignedChars(DFA184_minS);
	static final char[] DFA184_max = DFA.unpackEncodedStringToUnsignedChars(DFA184_maxS);
	static final short[] DFA184_accept = DFA.unpackEncodedString(DFA184_acceptS);
	static final short[] DFA184_special = DFA.unpackEncodedString(DFA184_specialS);
	static final short[][] DFA184_transition;

	static {
		int numStates = DFA184_transitionS.length;
		DFA184_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA184_transition[i] = DFA.unpackEncodedString(DFA184_transitionS[i]);
		}
	}

	protected class DFA184 extends DFA {

		public DFA184(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 184;
			this.eot = DFA184_eot;
			this.eof = DFA184_eof;
			this.min = DFA184_min;
			this.max = DFA184_max;
			this.accept = DFA184_accept;
			this.special = DFA184_special;
			this.transition = DFA184_transition;
		}
		@Override
		public String getDescription() {
			return "1428:1: relation[WhereClause.Builder clauses] : (name= cident type= relationType t= term |name= cident K_IS K_NOT K_NULL | K_TOKEN l= tupleOfIdentifiers type= relationType t= term |name= cident K_IN marker= inMarker |name= cident K_IN inValues= singleColumnInValues |name= cident K_CONTAINS ( K_KEY )? t= term |name= cident '[' key= term ']' type= relationType t= term |ids= tupleOfIdentifiers ( K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples ) |type= relationType literal= tupleLiteral |type= relationType tupleMarker= markerForTuple ) | '(' relation[$clauses] ')' );";
		}
	}

	static final String DFA183_eotS =
		"\12\uffff";
	static final String DFA183_eofS =
		"\12\uffff";
	static final String DFA183_minS =
		"\1\107\1\uffff\6\u009f\2\uffff";
	static final String DFA183_maxS =
		"\1\u00ba\1\uffff\6\u00b4\2\uffff";
	static final String DFA183_acceptS =
		"\1\uffff\1\1\6\uffff\1\2\1\3";
	static final String DFA183_specialS =
		"\12\uffff}>";
	static final String[] DFA183_transitionS = {
			"\1\1\145\uffff\1\7\10\uffff\1\3\1\4\1\2\1\5\1\6",
			"",
			"\1\11\16\uffff\1\10\5\uffff\1\11",
			"\1\11\16\uffff\1\10\5\uffff\1\11",
			"\1\11\16\uffff\1\10\5\uffff\1\11",
			"\1\11\16\uffff\1\10\5\uffff\1\11",
			"\1\11\16\uffff\1\10\5\uffff\1\11",
			"\1\11\16\uffff\1\10\5\uffff\1\11",
			"",
			""
	};

	static final short[] DFA183_eot = DFA.unpackEncodedString(DFA183_eotS);
	static final short[] DFA183_eof = DFA.unpackEncodedString(DFA183_eofS);
	static final char[] DFA183_min = DFA.unpackEncodedStringToUnsignedChars(DFA183_minS);
	static final char[] DFA183_max = DFA.unpackEncodedStringToUnsignedChars(DFA183_maxS);
	static final short[] DFA183_accept = DFA.unpackEncodedString(DFA183_acceptS);
	static final short[] DFA183_special = DFA.unpackEncodedString(DFA183_specialS);
	static final short[][] DFA183_transition;

	static {
		int numStates = DFA183_transitionS.length;
		DFA183_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA183_transition[i] = DFA.unpackEncodedString(DFA183_transitionS[i]);
		}
	}

	protected class DFA183 extends DFA {

		public DFA183(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 183;
			this.eot = DFA183_eot;
			this.eof = DFA183_eof;
			this.min = DFA183_min;
			this.max = DFA183_max;
			this.accept = DFA183_accept;
			this.special = DFA183_special;
			this.transition = DFA183_transition;
		}
		@Override
		public String getDescription() {
			return "1441:7: ( K_IN ( '(' ')' |tupleInMarker= inMarkerForTuple |literals= tupleOfTupleLiterals |markers= tupleOfMarkersForTuples ) |type= relationType literal= tupleLiteral |type= relationType tupleMarker= markerForTuple )";
		}
	}

	static final String DFA193_eotS =
		"\37\uffff";
	static final String DFA193_eofS =
		"\1\uffff\24\34\2\31\1\uffff\1\31\1\uffff\1\31\4\uffff";
	static final String DFA193_minS =
		"\1\24\26\76\1\uffff\1\76\1\uffff\1\76\4\uffff";
	static final String DFA193_maxS =
		"\1\u00a3\26\u00b9\1\uffff\1\u00b9\1\uffff\1\u00b9\4\uffff";
	static final String DFA193_acceptS =
		"\27\uffff\1\2\1\uffff\1\4\1\uffff\1\6\1\1\1\3\1\5";
	static final String DFA193_specialS =
		"\37\uffff}>";
	static final String[] DFA193_transitionS = {
			"\1\31\4\uffff\2\31\4\uffff\1\31\1\uffff\1\1\3\uffff\1\2\1\3\1\4\1\uffff"+
			"\2\31\1\uffff\3\31\1\5\1\uffff\1\31\1\23\1\6\3\uffff\1\31\1\7\3\uffff"+
			"\3\31\1\10\1\uffff\1\32\1\uffff\2\31\4\uffff\1\11\1\uffff\2\31\1\uffff"+
			"\1\12\2\uffff\3\31\1\uffff\3\31\1\uffff\1\26\1\31\1\25\3\uffff\1\31\1"+
			"\uffff\1\31\4\uffff\1\31\2\uffff\5\31\3\uffff\1\31\1\uffff\2\31\1\uffff"+
			"\1\27\1\31\1\13\4\31\1\14\1\24\1\15\1\22\1\16\2\uffff\1\31\1\uffff\1"+
			"\31\1\30\1\31\3\uffff\2\31\1\uffff\1\17\1\31\1\20\1\21\3\uffff\1\31\11"+
			"\uffff\1\31\2\uffff\1\33",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\34\14\uffff\1\34\12\uffff\1\34\27\uffff\1\34\12\uffff\1\34\65\uffff"+
			"\1\34\1\uffff\1\34\1\uffff\1\31\1\uffff\1\34\3\uffff\1\34",
			"\1\31\14\uffff\1\31\12\uffff\1\31\27\uffff\1\31\12\uffff\1\31\65\uffff"+
			"\1\31\1\uffff\1\31\1\uffff\1\31\1\uffff\1\31\1\27\2\uffff\1\31",
			"\1\31\14\uffff\1\31\12\uffff\1\31\27\uffff\1\31\12\uffff\1\31\65\uffff"+
			"\1\31\1\uffff\1\31\1\uffff\1\31\1\uffff\1\31\1\27\2\uffff\1\31",
			"",
			"\1\31\14\uffff\1\31\12\uffff\1\31\27\uffff\1\31\12\uffff\1\31\65\uffff"+
			"\1\31\1\uffff\1\31\1\uffff\1\31\1\uffff\1\31\1\35\2\uffff\1\31",
			"",
			"\1\31\14\uffff\1\31\12\uffff\1\31\27\uffff\1\31\12\uffff\1\31\65\uffff"+
			"\1\31\1\uffff\1\31\1\uffff\1\31\1\uffff\1\31\1\36\2\uffff\1\31",
			"",
			"",
			"",
			""
	};

	static final short[] DFA193_eot = DFA.unpackEncodedString(DFA193_eotS);
	static final short[] DFA193_eof = DFA.unpackEncodedString(DFA193_eofS);
	static final char[] DFA193_min = DFA.unpackEncodedStringToUnsignedChars(DFA193_minS);
	static final char[] DFA193_max = DFA.unpackEncodedStringToUnsignedChars(DFA193_maxS);
	static final short[] DFA193_accept = DFA.unpackEncodedString(DFA193_acceptS);
	static final short[] DFA193_special = DFA.unpackEncodedString(DFA193_specialS);
	static final short[][] DFA193_transition;

	static {
		int numStates = DFA193_transitionS.length;
		DFA193_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA193_transition[i] = DFA.unpackEncodedString(DFA193_transitionS[i]);
		}
	}

	protected class DFA193 extends DFA {

		public DFA193(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 193;
			this.eot = DFA193_eot;
			this.eof = DFA193_eof;
			this.min = DFA193_min;
			this.max = DFA193_max;
			this.accept = DFA193_accept;
			this.special = DFA193_special;
			this.transition = DFA193_transition;
		}
		@Override
		public String getDescription() {
			return "1498:1: comparatorType returns [CQL3Type.Raw t] : (n= native_type |c= collection_type |tt= tuple_type |id= userTypeName | K_FROZEN '<' f= comparatorType '>' |s= STRING_LITERAL );";
		}
	}

	public static final BitSet FOLLOW_cqlStatement_in_query72 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
	public static final BitSet FOLLOW_181_in_query75 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
	public static final BitSet FOLLOW_EOF_in_query79 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selectStatement_in_cqlStatement113 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_insertStatement_in_cqlStatement142 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_updateStatement_in_cqlStatement171 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_batchStatement_in_cqlStatement200 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_deleteStatement_in_cqlStatement230 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_useStatement_in_cqlStatement259 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_truncateStatement_in_cqlStatement291 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createKeyspaceStatement_in_cqlStatement318 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createTableStatement_in_cqlStatement339 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createIndexStatement_in_cqlStatement362 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropKeyspaceStatement_in_cqlStatement385 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropTableStatement_in_cqlStatement407 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropIndexStatement_in_cqlStatement432 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_alterTableStatement_in_cqlStatement457 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_alterKeyspaceStatement_in_cqlStatement481 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_grantPermissionsStatement_in_cqlStatement502 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_revokePermissionsStatement_in_cqlStatement520 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_listPermissionsStatement_in_cqlStatement537 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createUserStatement_in_cqlStatement556 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_alterUserStatement_in_cqlStatement580 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropUserStatement_in_cqlStatement605 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_listUsersStatement_in_cqlStatement631 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createTriggerStatement_in_cqlStatement656 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropTriggerStatement_in_cqlStatement677 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createTypeStatement_in_cqlStatement700 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_alterTypeStatement_in_cqlStatement724 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropTypeStatement_in_cqlStatement749 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createFunctionStatement_in_cqlStatement775 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropFunctionStatement_in_cqlStatement795 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createAggregateStatement_in_cqlStatement817 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropAggregateStatement_in_cqlStatement836 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createRoleStatement_in_cqlStatement857 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_alterRoleStatement_in_cqlStatement881 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropRoleStatement_in_cqlStatement906 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_listRolesStatement_in_cqlStatement932 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_grantRoleStatement_in_cqlStatement957 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_revokeRoleStatement_in_cqlStatement982 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_createMaterializedViewStatement_in_cqlStatement1006 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dropMaterializedViewStatement_in_cqlStatement1018 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_alterMaterializedViewStatement_in_cqlStatement1032 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_USE_in_useStatement1058 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_keyspaceName_in_useStatement1062 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_SELECT_in_selectStatement1096 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x100000018047B1DBL});
	public static final BitSet FOLLOW_K_JSON_in_selectStatement1107 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x100000018047B1DBL});
	public static final BitSet FOLLOW_K_DISTINCT_in_selectStatement1124 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x100000018047B1DBL});
	public static final BitSet FOLLOW_selectClause_in_selectStatement1133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_K_FROM_in_selectStatement1143 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_selectStatement1147 = new BitSet(new long[]{0x0000000008000002L,0x0000090001000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_WHERE_in_selectStatement1157 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x400040010047B1DBL});
	public static final BitSet FOLLOW_whereClause_in_selectStatement1161 = new BitSet(new long[]{0x0000000008000002L,0x0000090001000000L});
	public static final BitSet FOLLOW_K_ORDER_in_selectStatement1174 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_K_BY_in_selectStatement1176 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_orderByClause_in_selectStatement1178 = new BitSet(new long[]{0x0000000008000002L,0x0000080001000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_selectStatement1183 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_orderByClause_in_selectStatement1185 = new BitSet(new long[]{0x0000000008000002L,0x0000080001000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_K_PER_in_selectStatement1202 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
	public static final BitSet FOLLOW_K_PARTITION_in_selectStatement1204 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
	public static final BitSet FOLLOW_K_LIMIT_in_selectStatement1206 = new BitSet(new long[]{0x0000000008200000L,0x0000000001000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_intValue_in_selectStatement1210 = new BitSet(new long[]{0x0000000008000002L,0x0000000001000000L});
	public static final BitSet FOLLOW_K_LIMIT_in_selectStatement1225 = new BitSet(new long[]{0x0000000008200000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_intValue_in_selectStatement1229 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_K_ALLOW_in_selectStatement1244 = new BitSet(new long[]{0x2000000000000000L});
	public static final BitSet FOLLOW_K_FILTERING_in_selectStatement1246 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_selector_in_selectClause1283 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_selectClause1288 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_selector_in_selectClause1292 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_188_in_selectClause1304 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaliasedSelector_in_selector1337 = new BitSet(new long[]{0x0000000080000002L});
	public static final BitSet FOLLOW_K_AS_in_selector1340 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_selector1344 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_unaliasedSelector1385 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_K_COUNT_in_unaliasedSelector1431 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_unaliasedSelector1433 = new BitSet(new long[]{0x0000000000200000L,0x0000000000000000L,0x1000000000000000L});
	public static final BitSet FOLLOW_countArgument_in_unaliasedSelector1435 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_unaliasedSelector1437 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_K_WRITETIME_in_unaliasedSelector1462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_unaliasedSelector1464 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_unaliasedSelector1468 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_unaliasedSelector1470 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_K_TTL_in_unaliasedSelector1496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_unaliasedSelector1504 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_unaliasedSelector1508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_unaliasedSelector1510 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_functionName_in_unaliasedSelector1538 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_selectionFunctionArgs_in_unaliasedSelector1542 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_179_in_unaliasedSelector1557 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_unaliasedSelector1561 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_174_in_selectionFunctionArgs1589 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_selectionFunctionArgs1591 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_selectionFunctionArgs1601 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_unaliasedSelector_in_selectionFunctionArgs1605 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_selectionFunctionArgs1621 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_unaliasedSelector_in_selectionFunctionArgs1625 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_selectionFunctionArgs1638 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_188_in_countArgument1657 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_in_countArgument1667 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationOrExpression_in_whereClause1698 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_whereClause1702 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x400040010047B1DBL});
	public static final BitSet FOLLOW_relationOrExpression_in_whereClause1704 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_relation_in_relationOrExpression1726 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_customIndexExpression_in_relationOrExpression1735 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_190_in_customIndexExpression1763 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_idxName_in_customIndexExpression1765 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_customIndexExpression1768 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_customIndexExpression1772 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_customIndexExpression1774 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_orderByClause1804 = new BitSet(new long[]{0x0020000100000002L});
	public static final BitSet FOLLOW_K_ASC_in_orderByClause1807 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DESC_in_orderByClause1811 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_INSERT_in_insertStatement1840 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
	public static final BitSet FOLLOW_K_INTO_in_insertStatement1842 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_insertStatement1846 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L,0x0000400000000000L});
	public static final BitSet FOLLOW_normalInsertStatement_in_insertStatement1860 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_JSON_in_insertStatement1875 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L,0x0010000880004000L});
	public static final BitSet FOLLOW_jsonInsertStatement_in_insertStatement1879 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_normalInsertStatement1915 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_normalInsertStatement1919 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_normalInsertStatement1926 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_normalInsertStatement1930 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_normalInsertStatement1937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
	public static final BitSet FOLLOW_K_VALUES_in_normalInsertStatement1945 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_normalInsertStatement1953 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_normalInsertStatement1957 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_normalInsertStatement1963 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_normalInsertStatement1967 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_normalInsertStatement1974 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L,0x0000000000004000L});
	public static final BitSet FOLLOW_K_IF_in_normalInsertStatement1984 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_normalInsertStatement1986 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_normalInsertStatement1988 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_usingClause_in_normalInsertStatement2003 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_jsonValue_in_jsonInsertStatement2049 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L,0x0000000000004000L});
	public static final BitSet FOLLOW_K_IF_in_jsonInsertStatement2059 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_jsonInsertStatement2061 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_jsonInsertStatement2063 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_usingClause_in_jsonInsertStatement2078 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_jsonValue2119 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_jsonValue2129 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_jsonValue2133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_jsonValue2147 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_USING_in_usingClause2178 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L,0x0000000000000040L});
	public static final BitSet FOLLOW_usingClauseObjective_in_usingClause2180 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_usingClause2185 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L,0x0000000000000040L});
	public static final BitSet FOLLOW_usingClauseObjective_in_usingClause2187 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_TIMESTAMP_in_usingClauseObjective2209 = new BitSet(new long[]{0x0000000000200000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_intValue_in_usingClauseObjective2213 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TTL_in_usingClauseObjective2223 = new BitSet(new long[]{0x0000000000200000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_intValue_in_usingClauseObjective2227 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_UPDATE_in_updateStatement2261 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_updateStatement2265 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_usingClause_in_updateStatement2275 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_K_SET_in_updateStatement2287 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_columnOperation_in_updateStatement2289 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000100000L});
	public static final BitSet FOLLOW_177_in_updateStatement2293 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_columnOperation_in_updateStatement2295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000100000L});
	public static final BitSet FOLLOW_K_WHERE_in_updateStatement2306 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x400040010047B1DBL});
	public static final BitSet FOLLOW_whereClause_in_updateStatement2310 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
	public static final BitSet FOLLOW_K_IF_in_updateStatement2320 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_K_EXISTS_in_updateStatement2324 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_updateConditions_in_updateStatement2332 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_columnCondition_in_updateConditions2374 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_updateConditions2379 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_columnCondition_in_updateConditions2381 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_DELETE_in_deleteStatement2418 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1BL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_deleteSelection_in_deleteStatement2424 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_K_FROM_in_deleteStatement2437 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_deleteStatement2441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000104000L});
	public static final BitSet FOLLOW_usingClauseDelete_in_deleteStatement2451 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_WHERE_in_deleteStatement2463 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x400040010047B1DBL});
	public static final BitSet FOLLOW_whereClause_in_deleteStatement2467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
	public static final BitSet FOLLOW_K_IF_in_deleteStatement2477 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_K_EXISTS_in_deleteStatement2481 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_updateConditions_in_deleteStatement2489 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_deleteOp_in_deleteSelection2536 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_deleteSelection2551 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_deleteOp_in_deleteSelection2555 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_cident_in_deleteOp2582 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_deleteOp2609 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
	public static final BitSet FOLLOW_187_in_deleteOp2611 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_deleteOp2615 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
	public static final BitSet FOLLOW_189_in_deleteOp2617 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_USING_in_usingClauseDelete2637 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_K_TIMESTAMP_in_usingClauseDelete2639 = new BitSet(new long[]{0x0000000000200000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_intValue_in_usingClauseDelete2643 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_BEGIN_in_batchStatement2677 = new BitSet(new long[]{0x0000800800000000L,0x0000000000000000L,0x0000000000000200L});
	public static final BitSet FOLLOW_K_UNLOGGED_in_batchStatement2687 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_K_COUNTER_in_batchStatement2693 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_K_BATCH_in_batchStatement2706 = new BitSet(new long[]{0x0010000040000000L,0x0000000000002000L,0x0000000000004400L});
	public static final BitSet FOLLOW_usingClause_in_batchStatement2710 = new BitSet(new long[]{0x0010000040000000L,0x0000000000002000L,0x0000000000000400L});
	public static final BitSet FOLLOW_batchStatementObjective_in_batchStatement2730 = new BitSet(new long[]{0x0010000040000000L,0x0000000000002000L,0x0020000000000400L});
	public static final BitSet FOLLOW_181_in_batchStatement2732 = new BitSet(new long[]{0x0010000040000000L,0x0000000000002000L,0x0000000000000400L});
	public static final BitSet FOLLOW_K_APPLY_in_batchStatement2746 = new BitSet(new long[]{0x0000000800000000L});
	public static final BitSet FOLLOW_K_BATCH_in_batchStatement2748 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_insertStatement_in_batchStatementObjective2779 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_updateStatement_in_batchStatementObjective2792 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_deleteStatement_in_batchStatementObjective2805 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createAggregateStatement2838 = new BitSet(new long[]{0x0000000002000000L,0x0000008000000000L});
	public static final BitSet FOLLOW_K_OR_in_createAggregateStatement2841 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
	public static final BitSet FOLLOW_K_REPLACE_in_createAggregateStatement2843 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_K_AGGREGATE_in_createAggregateStatement2855 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_K_IF_in_createAggregateStatement2864 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createAggregateStatement2866 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createAggregateStatement2868 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_functionName_in_createAggregateStatement2882 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_createAggregateStatement2890 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000080090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_createAggregateStatement2914 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createAggregateStatement2930 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_createAggregateStatement2934 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createAggregateStatement2958 = new BitSet(new long[]{0x0000000000000000L,0x0080000000000000L});
	public static final BitSet FOLLOW_K_SFUNC_in_createAggregateStatement2966 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEA5A1AL,0x000000010007B19BL});
	public static final BitSet FOLLOW_allowedFunctionName_in_createAggregateStatement2972 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
	public static final BitSet FOLLOW_K_STYPE_in_createAggregateStatement2980 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_createAggregateStatement2986 = new BitSet(new long[]{0x4000000000000002L,0x0000000000000800L});
	public static final BitSet FOLLOW_K_FINALFUNC_in_createAggregateStatement3004 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEA5A1AL,0x000000010007B19BL});
	public static final BitSet FOLLOW_allowedFunctionName_in_createAggregateStatement3010 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
	public static final BitSet FOLLOW_K_INITCOND_in_createAggregateStatement3037 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_createAggregateStatement3043 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropAggregateStatement3090 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_K_AGGREGATE_in_dropAggregateStatement3092 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_K_IF_in_dropAggregateStatement3101 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropAggregateStatement3103 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_functionName_in_dropAggregateStatement3118 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_dropAggregateStatement3136 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000080090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_dropAggregateStatement3164 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_dropAggregateStatement3182 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_dropAggregateStatement3186 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_dropAggregateStatement3214 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createFunctionStatement3271 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000008L});
	public static final BitSet FOLLOW_K_OR_in_createFunctionStatement3274 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L});
	public static final BitSet FOLLOW_K_REPLACE_in_createFunctionStatement3276 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_K_FUNCTION_in_createFunctionStatement3288 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_K_IF_in_createFunctionStatement3297 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createFunctionStatement3299 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createFunctionStatement3301 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_functionName_in_createFunctionStatement3315 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_createFunctionStatement3323 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000080010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_createFunctionStatement3347 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_createFunctionStatement3351 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createFunctionStatement3367 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_createFunctionStatement3371 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_createFunctionStatement3375 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createFunctionStatement3399 = new BitSet(new long[]{0x0000020000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_K_RETURNS_in_createFunctionStatement3410 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_K_NULL_in_createFunctionStatement3412 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_CALLED_in_createFunctionStatement3418 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_ON_in_createFunctionStatement3424 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_K_NULL_in_createFunctionStatement3426 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
	public static final BitSet FOLLOW_K_INPUT_in_createFunctionStatement3428 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_K_RETURNS_in_createFunctionStatement3436 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_createFunctionStatement3442 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
	public static final BitSet FOLLOW_K_LANGUAGE_in_createFunctionStatement3450 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_createFunctionStatement3456 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_K_AS_in_createFunctionStatement3464 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_createFunctionStatement3470 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropFunctionStatement3508 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
	public static final BitSet FOLLOW_K_FUNCTION_in_dropFunctionStatement3510 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_K_IF_in_dropFunctionStatement3519 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropFunctionStatement3521 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_functionName_in_dropFunctionStatement3536 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_dropFunctionStatement3554 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000080090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_dropFunctionStatement3582 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_dropFunctionStatement3600 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_dropFunctionStatement3604 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_dropFunctionStatement3632 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createKeyspaceStatement3691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_KEYSPACE_in_createKeyspaceStatement3693 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createKeyspaceStatement3696 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createKeyspaceStatement3698 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createKeyspaceStatement3700 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_keyspaceName_in_createKeyspaceStatement3709 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_createKeyspaceStatement3717 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_properties_in_createKeyspaceStatement3719 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createTableStatement3754 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_K_COLUMNFAMILY_in_createTableStatement3756 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createTableStatement3759 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createTableStatement3761 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createTableStatement3763 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_createTableStatement3778 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_cfamDefinition_in_createTableStatement3788 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_cfamDefinition3807 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A7E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamColumns_in_cfamDefinition3809 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_cfamDefinition3814 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A7E428EEE5A1AL,0x000280010047B1D3L});
	public static final BitSet FOLLOW_cfamColumns_in_cfamDefinition3816 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_cfamDefinition3823 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_cfamDefinition3833 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamProperty_in_cfamDefinition3835 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_cfamDefinition3840 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamProperty_in_cfamDefinition3842 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_ident_in_cfamColumns3868 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_cfamColumns3872 = new BitSet(new long[]{0x0000000000000002L,0x0200400000000000L});
	public static final BitSet FOLLOW_K_STATIC_in_cfamColumns3877 = new BitSet(new long[]{0x0000000000000002L,0x0000400000000000L});
	public static final BitSet FOLLOW_K_PRIMARY_in_cfamColumns3894 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_K_KEY_in_cfamColumns3896 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_PRIMARY_in_cfamColumns3908 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_K_KEY_in_cfamColumns3910 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_cfamColumns3912 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000040010047B1D3L});
	public static final BitSet FOLLOW_pkDef_in_cfamColumns3914 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_cfamColumns3918 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_ident_in_cfamColumns3922 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_cfamColumns3929 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ident_in_pkDef3949 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_pkDef3959 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_ident_in_pkDef3965 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_pkDef3971 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_ident_in_pkDef3975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_pkDef3982 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_property_in_cfamProperty4002 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_COMPACT_in_cfamProperty4011 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
	public static final BitSet FOLLOW_K_STORAGE_in_cfamProperty4013 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CLUSTERING_in_cfamProperty4023 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
	public static final BitSet FOLLOW_K_ORDER_in_cfamProperty4025 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_K_BY_in_cfamProperty4027 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_cfamProperty4029 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamOrdering_in_cfamProperty4031 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_cfamProperty4035 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamOrdering_in_cfamProperty4037 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_cfamProperty4042 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ident_in_cfamOrdering4070 = new BitSet(new long[]{0x0020000100000000L});
	public static final BitSet FOLLOW_K_ASC_in_cfamOrdering4073 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DESC_in_cfamOrdering4077 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createTypeStatement4116 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_TYPE_in_createTypeStatement4118 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createTypeStatement4121 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createTypeStatement4123 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createTypeStatement4125 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_userTypeName_in_createTypeStatement4143 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_createTypeStatement4156 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_typeColumns_in_createTypeStatement4158 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createTypeStatement4163 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000280010047B1D3L});
	public static final BitSet FOLLOW_typeColumns_in_createTypeStatement4165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createTypeStatement4172 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_noncol_ident_in_typeColumns4192 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_typeColumns4196 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createIndexStatement4231 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_CUSTOM_in_createIndexStatement4234 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_INDEX_in_createIndexStatement4240 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E628EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createIndexStatement4243 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createIndexStatement4245 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createIndexStatement4247 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E628EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_idxName_in_createIndexStatement4263 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_ON_in_createIndexStatement4268 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_createIndexStatement4272 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_createIndexStatement4274 = new BitSet(new long[]{0xF58EF6E286100000L,0xFF9A3E428EEE5A1EL,0x000080010047B1D3L});
	public static final BitSet FOLLOW_indexIdent_in_createIndexStatement4277 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createIndexStatement4281 = new BitSet(new long[]{0xF58EF6E286100000L,0xFF9A3E428EEE5A1EL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_indexIdent_in_createIndexStatement4283 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createIndexStatement4290 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000204000L});
	public static final BitSet FOLLOW_K_USING_in_createIndexStatement4301 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_createIndexStatement4305 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_createIndexStatement4320 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_properties_in_createIndexStatement4322 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_indexIdent4354 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_VALUES_in_indexIdent4382 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_indexIdent4384 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_indexIdent4388 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_indexIdent4390 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_KEYS_in_indexIdent4401 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_indexIdent4403 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_indexIdent4407 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_indexIdent4409 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ENTRIES_in_indexIdent4422 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_indexIdent4424 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_indexIdent4428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_indexIdent4430 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_FULL_in_indexIdent4440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_indexIdent4442 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_indexIdent4446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_indexIdent4448 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createMaterializedViewStatement4485 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
	public static final BitSet FOLLOW_K_MATERIALIZED_in_createMaterializedViewStatement4487 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
	public static final BitSet FOLLOW_K_VIEW_in_createMaterializedViewStatement4489 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createMaterializedViewStatement4492 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createMaterializedViewStatement4494 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createMaterializedViewStatement4496 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_createMaterializedViewStatement4504 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_K_AS_in_createMaterializedViewStatement4506 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
	public static final BitSet FOLLOW_K_SELECT_in_createMaterializedViewStatement4516 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x100000018047B1DBL});
	public static final BitSet FOLLOW_selectClause_in_createMaterializedViewStatement4520 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_K_FROM_in_createMaterializedViewStatement4522 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_createMaterializedViewStatement4526 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_WHERE_in_createMaterializedViewStatement4537 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x400040010047B1DBL});
	public static final BitSet FOLLOW_whereClause_in_createMaterializedViewStatement4541 = new BitSet(new long[]{0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_K_PRIMARY_in_createMaterializedViewStatement4553 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_K_KEY_in_createMaterializedViewStatement4555 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_createMaterializedViewStatement4567 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_createMaterializedViewStatement4569 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_createMaterializedViewStatement4573 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createMaterializedViewStatement4579 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_createMaterializedViewStatement4583 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createMaterializedViewStatement4590 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createMaterializedViewStatement4594 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_createMaterializedViewStatement4598 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createMaterializedViewStatement4605 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_174_in_createMaterializedViewStatement4615 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_createMaterializedViewStatement4619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_createMaterializedViewStatement4625 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_createMaterializedViewStatement4629 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_createMaterializedViewStatement4636 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_createMaterializedViewStatement4668 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamProperty_in_createMaterializedViewStatement4670 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_createMaterializedViewStatement4675 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cfamProperty_in_createMaterializedViewStatement4677 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createTriggerStatement4715 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_K_TRIGGER_in_createTriggerStatement4717 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createTriggerStatement4720 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createTriggerStatement4722 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createTriggerStatement4724 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_createTriggerStatement4734 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_ON_in_createTriggerStatement4745 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_createTriggerStatement4749 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_K_USING_in_createTriggerStatement4751 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_createTriggerStatement4755 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropTriggerStatement4796 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_K_TRIGGER_in_dropTriggerStatement4798 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropTriggerStatement4801 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropTriggerStatement4803 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_dropTriggerStatement4813 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_ON_in_dropTriggerStatement4816 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_dropTriggerStatement4820 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALTER_in_alterKeyspaceStatement4860 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_KEYSPACE_in_alterKeyspaceStatement4862 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_keyspaceName_in_alterKeyspaceStatement4866 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_alterKeyspaceStatement4876 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_properties_in_alterKeyspaceStatement4878 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALTER_in_alterTableStatement4914 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_K_COLUMNFAMILY_in_alterTableStatement4916 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_alterTableStatement4920 = new BitSet(new long[]{0x0200000011000000L,0x0000800000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_ALTER_in_alterTableStatement4934 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement4938 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_TYPE_in_alterTableStatement4940 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_alterTableStatement4944 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ADD_in_alterTableStatement4960 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement4966 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_alterTableStatement4970 = new BitSet(new long[]{0x0000000000000002L,0x0200000000000000L});
	public static final BitSet FOLLOW_K_STATIC_in_alterTableStatement4975 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_alterTableStatement4993 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement4998 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_alterTableStatement5044 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement5049 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
	public static final BitSet FOLLOW_K_USING_in_alterTableStatement5051 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_K_TIMESTAMP_in_alterTableStatement5053 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_INTEGER_in_alterTableStatement5057 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_WITH_in_alterTableStatement5073 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_properties_in_alterTableStatement5076 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_RENAME_in_alterTableStatement5115 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement5175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_K_TO_in_alterTableStatement5177 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement5181 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_alterTableStatement5202 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement5206 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_K_TO_in_alterTableStatement5208 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_alterTableStatement5212 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_ALTER_in_alterMaterializedViewStatement5265 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
	public static final BitSet FOLLOW_K_MATERIALIZED_in_alterMaterializedViewStatement5267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
	public static final BitSet FOLLOW_K_VIEW_in_alterMaterializedViewStatement5269 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_alterMaterializedViewStatement5273 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_alterMaterializedViewStatement5285 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_properties_in_alterMaterializedViewStatement5287 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALTER_in_alterTypeStatement5322 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_TYPE_in_alterTypeStatement5324 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_userTypeName_in_alterTypeStatement5328 = new BitSet(new long[]{0x0000000011000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_K_ALTER_in_alterTypeStatement5342 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_alterTypeStatement5346 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_TYPE_in_alterTypeStatement5348 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_alterTypeStatement5352 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ADD_in_alterTypeStatement5368 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_alterTypeStatement5374 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_alterTypeStatement5378 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_RENAME_in_alterTypeStatement5401 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_alterTypeStatement5439 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_K_TO_in_alterTypeStatement5441 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_alterTypeStatement5445 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_alterTypeStatement5468 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_alterTypeStatement5472 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_K_TO_in_alterTypeStatement5474 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_alterTypeStatement5478 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropKeyspaceStatement5545 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_KEYSPACE_in_dropKeyspaceStatement5547 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropKeyspaceStatement5550 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropKeyspaceStatement5552 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_keyspaceName_in_dropKeyspaceStatement5561 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropTableStatement5595 = new BitSet(new long[]{0x0000080000000000L});
	public static final BitSet FOLLOW_K_COLUMNFAMILY_in_dropTableStatement5597 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropTableStatement5600 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropTableStatement5602 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_dropTableStatement5611 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropTypeStatement5645 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_TYPE_in_dropTypeStatement5647 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropTypeStatement5650 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropTypeStatement5652 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_userTypeName_in_dropTypeStatement5661 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropIndexStatement5695 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
	public static final BitSet FOLLOW_K_INDEX_in_dropIndexStatement5697 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropIndexStatement5700 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropIndexStatement5702 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_indexName_in_dropIndexStatement5711 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropMaterializedViewStatement5751 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
	public static final BitSet FOLLOW_K_MATERIALIZED_in_dropMaterializedViewStatement5753 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
	public static final BitSet FOLLOW_K_VIEW_in_dropMaterializedViewStatement5755 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropMaterializedViewStatement5758 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropMaterializedViewStatement5760 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_dropMaterializedViewStatement5769 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TRUNCATE_in_truncateStatement5800 = new BitSet(new long[]{0xF18EFEE286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_K_COLUMNFAMILY_in_truncateStatement5803 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_truncateStatement5809 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_GRANT_in_grantPermissionsStatement5834 = new BitSet(new long[]{0x0A41000414000000L,0x0020000020000000L});
	public static final BitSet FOLLOW_permissionOrAll_in_grantPermissionsStatement5846 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_ON_in_grantPermissionsStatement5854 = new BitSet(new long[]{0xF18EFEE286100000L,0xFF9A3E428EFE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_resource_in_grantPermissionsStatement5866 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_K_TO_in_grantPermissionsStatement5874 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_grantPermissionsStatement5888 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_REVOKE_in_revokePermissionsStatement5919 = new BitSet(new long[]{0x0A41000414000000L,0x0020000020000000L});
	public static final BitSet FOLLOW_permissionOrAll_in_revokePermissionsStatement5931 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
	public static final BitSet FOLLOW_K_ON_in_revokePermissionsStatement5939 = new BitSet(new long[]{0xF18EFEE286100000L,0xFF9A3E428EFE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_resource_in_revokePermissionsStatement5951 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_K_FROM_in_revokePermissionsStatement5959 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_revokePermissionsStatement5973 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_GRANT_in_grantRoleStatement6004 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_grantRoleStatement6018 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
	public static final BitSet FOLLOW_K_TO_in_grantRoleStatement6026 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_grantRoleStatement6040 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_REVOKE_in_revokeRoleStatement6071 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_revokeRoleStatement6085 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_K_FROM_in_revokeRoleStatement6093 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_revokeRoleStatement6107 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_LIST_in_listPermissionsStatement6145 = new BitSet(new long[]{0x0A41000414000000L,0x0020000020000000L});
	public static final BitSet FOLLOW_permissionOrAll_in_listPermissionsStatement6157 = new BitSet(new long[]{0x0000000000000002L,0x0000003100000000L});
	public static final BitSet FOLLOW_K_ON_in_listPermissionsStatement6167 = new BitSet(new long[]{0xF18EFEE286100000L,0xFF9A3E428EFE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_resource_in_listPermissionsStatement6169 = new BitSet(new long[]{0x0000000000000002L,0x0000001100000000L});
	public static final BitSet FOLLOW_K_OF_in_listPermissionsStatement6184 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_roleName_in_listPermissionsStatement6186 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
	public static final BitSet FOLLOW_K_NORECURSIVE_in_listPermissionsStatement6200 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_permission6236 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALL_in_permissionOrAll6293 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
	public static final BitSet FOLLOW_K_PERMISSIONS_in_permissionOrAll6297 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_permission_in_permissionOrAll6318 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
	public static final BitSet FOLLOW_K_PERMISSION_in_permissionOrAll6322 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_dataResource_in_resource6350 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_roleResource_in_resource6362 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionResource_in_resource6374 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALL_in_dataResource6397 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_KEYSPACES_in_dataResource6399 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_KEYSPACE_in_dataResource6409 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_keyspaceName_in_dataResource6415 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_COLUMNFAMILY_in_dataResource6427 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_columnFamilyName_in_dataResource6436 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALL_in_roleResource6465 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_K_ROLES_in_roleResource6467 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ROLE_in_roleResource6477 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_roleResource6483 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALL_in_functionResource6515 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_K_FUNCTIONS_in_functionResource6517 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALL_in_functionResource6527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
	public static final BitSet FOLLOW_K_FUNCTIONS_in_functionResource6529 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_K_IN_in_functionResource6531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
	public static final BitSet FOLLOW_K_KEYSPACE_in_functionResource6533 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_keyspaceName_in_functionResource6539 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_FUNCTION_in_functionResource6554 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1DBL});
	public static final BitSet FOLLOW_functionName_in_functionResource6558 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_functionResource6576 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000080090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_functionResource6604 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_functionResource6622 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_functionResource6626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_functionResource6654 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createUserStatement6702 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
	public static final BitSet FOLLOW_K_USER_in_createUserStatement6704 = new BitSet(new long[]{0x0000000000100000L,0x0000000000000040L,0x0000000900000000L});
	public static final BitSet FOLLOW_K_IF_in_createUserStatement6707 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createUserStatement6709 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createUserStatement6711 = new BitSet(new long[]{0x0000000000100000L,0x0000000000000000L,0x0000000900000000L});
	public static final BitSet FOLLOW_username_in_createUserStatement6719 = new BitSet(new long[]{0x0000000000000002L,0x1000000200000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_createUserStatement6731 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
	public static final BitSet FOLLOW_userPassword_in_createUserStatement6733 = new BitSet(new long[]{0x0000000000000002L,0x1000000200000000L});
	public static final BitSet FOLLOW_K_SUPERUSER_in_createUserStatement6747 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_NOSUPERUSER_in_createUserStatement6753 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALTER_in_alterUserStatement6798 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
	public static final BitSet FOLLOW_K_USER_in_alterUserStatement6800 = new BitSet(new long[]{0x0000000000100000L,0x0000000000000000L,0x0000000900000000L});
	public static final BitSet FOLLOW_username_in_alterUserStatement6804 = new BitSet(new long[]{0x0000000000000002L,0x1000000200000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_alterUserStatement6816 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
	public static final BitSet FOLLOW_userPassword_in_alterUserStatement6818 = new BitSet(new long[]{0x0000000000000002L,0x1000000200000000L});
	public static final BitSet FOLLOW_K_SUPERUSER_in_alterUserStatement6832 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_NOSUPERUSER_in_alterUserStatement6846 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropUserStatement6892 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
	public static final BitSet FOLLOW_K_USER_in_dropUserStatement6894 = new BitSet(new long[]{0x0000000000100000L,0x0000000000000040L,0x0000000900000000L});
	public static final BitSet FOLLOW_K_IF_in_dropUserStatement6897 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropUserStatement6899 = new BitSet(new long[]{0x0000000000100000L,0x0000000000000000L,0x0000000900000000L});
	public static final BitSet FOLLOW_username_in_dropUserStatement6907 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_LIST_in_listUsersStatement6932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
	public static final BitSet FOLLOW_K_USERS_in_listUsersStatement6934 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_CREATE_in_createRoleStatement6968 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_K_ROLE_in_createRoleStatement6970 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_createRoleStatement6973 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_createRoleStatement6975 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_createRoleStatement6977 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_createRoleStatement6985 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_createRoleStatement6995 = new BitSet(new long[]{0x0000000000000000L,0x1000044004000000L});
	public static final BitSet FOLLOW_roleOptions_in_createRoleStatement6997 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ALTER_in_alterRoleStatement7041 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_K_ROLE_in_alterRoleStatement7043 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_alterRoleStatement7047 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000200000L});
	public static final BitSet FOLLOW_K_WITH_in_alterRoleStatement7057 = new BitSet(new long[]{0x0000000000000000L,0x1000044004000000L});
	public static final BitSet FOLLOW_roleOptions_in_alterRoleStatement7059 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DROP_in_dropRoleStatement7103 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_K_ROLE_in_dropRoleStatement7105 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A5AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_K_IF_in_dropRoleStatement7108 = new BitSet(new long[]{0x1000000000000000L});
	public static final BitSet FOLLOW_K_EXISTS_in_dropRoleStatement7110 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_userOrRoleName_in_dropRoleStatement7118 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_LIST_in_listRolesStatement7158 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_K_ROLES_in_listRolesStatement7160 = new BitSet(new long[]{0x0000000000000002L,0x0000001100000000L});
	public static final BitSet FOLLOW_K_OF_in_listRolesStatement7170 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000098047B1D3L});
	public static final BitSet FOLLOW_roleName_in_listRolesStatement7172 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
	public static final BitSet FOLLOW_K_NORECURSIVE_in_listRolesStatement7185 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_roleOption_in_roleOptions7216 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_roleOptions7220 = new BitSet(new long[]{0x0000000000000000L,0x1000044004000000L});
	public static final BitSet FOLLOW_roleOption_in_roleOptions7222 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_PASSWORD_in_roleOption7244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_184_in_roleOption7246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_roleOption7250 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_OPTIONS_in_roleOption7261 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_184_in_roleOption7263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_mapLiteral_in_roleOption7267 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_SUPERUSER_in_roleOption7278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_184_in_roleOption7280 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_BOOLEAN_in_roleOption7284 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_LOGIN_in_roleOption7295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_184_in_roleOption7297 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_BOOLEAN_in_roleOption7301 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_PASSWORD_in_userPassword7323 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_userPassword7327 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_cident7358 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_cident7383 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_cident7402 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_ident7428 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_ident7453 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_ident7472 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_noncol_ident7498 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_noncol_ident7523 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_noncol_ident7542 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ksName_in_keyspaceName7575 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ksName_in_indexName7609 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_179_in_indexName7612 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_idxName_in_indexName7616 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ksName_in_columnFamilyName7648 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_179_in_columnFamilyName7651 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000018047B1D3L});
	public static final BitSet FOLLOW_cfName_in_columnFamilyName7655 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_noncol_ident_in_userTypeName7680 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_179_in_userTypeName7682 = new BitSet(new long[]{0x7082360086100000L,0x1E9A3E428EEE181AL,0x0000000100013190L});
	public static final BitSet FOLLOW_non_type_ident_in_userTypeName7688 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_roleName_in_userOrRoleName7720 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_ksName7743 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_ksName7768 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_ksName7787 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_ksName7797 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_cfName7819 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_cfName7844 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_cfName7863 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_cfName7873 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_idxName7895 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_idxName7920 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_idxName7939 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_idxName7949 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_roleName7971 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_roleName7996 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_roleName8012 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_roleName8031 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_roleName8041 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_constant8066 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_in_constant8078 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_constant8097 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_BOOLEAN_in_constant8118 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_UUID_in_constant8137 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_HEXNUMBER_in_constant8159 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_178_in_constant8177 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000400L});
	public static final BitSet FOLLOW_set_in_constant8186 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_191_in_mapLiteral8215 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL,0x0000000000000001L});
	public static final BitSet FOLLOW_term_in_mapLiteral8233 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_180_in_mapLiteral8235 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_mapLiteral8239 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_177_in_mapLiteral8245 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_mapLiteral8249 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_180_in_mapLiteral8251 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_mapLiteral8255 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_192_in_mapLiteral8271 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_setOrMapLiteral8295 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_setOrMapLiteral8299 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_setOrMapLiteral8315 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_setOrMapLiteral8319 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_180_in_setOrMapLiteral8321 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_setOrMapLiteral8325 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_setOrMapLiteral8360 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_setOrMapLiteral8364 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_187_in_collectionLiteral8398 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0xA81440498047B1DBL});
	public static final BitSet FOLLOW_term_in_collectionLiteral8416 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2002000000000000L});
	public static final BitSet FOLLOW_177_in_collectionLiteral8422 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_collectionLiteral8426 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2002000000000000L});
	public static final BitSet FOLLOW_189_in_collectionLiteral8442 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_191_in_collectionLiteral8452 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_collectionLiteral8456 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0012000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_setOrMapLiteral_in_collectionLiteral8460 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_192_in_collectionLiteral8465 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_191_in_collectionLiteral8483 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_192_in_collectionLiteral8485 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_191_in_usertypeLiteral8529 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_usertypeLiteral8533 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_180_in_usertypeLiteral8535 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_usertypeLiteral8539 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_177_in_usertypeLiteral8545 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_usertypeLiteral8549 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
	public static final BitSet FOLLOW_180_in_usertypeLiteral8551 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_usertypeLiteral8555 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L,0x0000000000000001L});
	public static final BitSet FOLLOW_192_in_usertypeLiteral8562 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_tupleLiteral8599 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_tupleLiteral8603 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_tupleLiteral8609 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_tupleLiteral8613 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_tupleLiteral8620 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_constant_in_value8643 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_collectionLiteral_in_value8665 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_usertypeLiteral_in_value8678 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tupleLiteral_in_value8693 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_NULL_in_value8709 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_value8733 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_value8737 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_value8748 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEGER_in_intValue8794 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_intValue8808 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_intValue8812 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_intValue8823 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_keyspaceName_in_functionName8857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
	public static final BitSet FOLLOW_179_in_functionName8859 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEA5A1AL,0x000000010007B19BL});
	public static final BitSet FOLLOW_allowedFunctionName_in_functionName8865 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_allowedFunctionName8892 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_allowedFunctionName8926 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_function_keyword_in_allowedFunctionName8954 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TOKEN_in_allowedFunctionName8964 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_COUNT_in_allowedFunctionName8996 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionName_in_function9043 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_function9045 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_function9047 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionName_in_function9077 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_174_in_function9079 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_functionArgs_in_function9083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_function9085 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_term_in_functionArgs9118 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_functionArgs9124 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_functionArgs9128 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_value_in_term9156 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_function_in_term9193 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_term9225 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_term9229 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_term9231 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_term9235 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_columnOperation9258 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0900000000000000L});
	public static final BitSet FOLLOW_columnOperationDifferentiator_in_columnOperation9260 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_184_in_columnOperationDifferentiator9279 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_normalColumnOperation_in_columnOperationDifferentiator9281 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_187_in_columnOperationDifferentiator9290 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_columnOperationDifferentiator9294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
	public static final BitSet FOLLOW_189_in_columnOperationDifferentiator9296 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_specializedColumnOperation_in_columnOperationDifferentiator9298 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_term_in_normalColumnOperation9319 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0001000000000000L});
	public static final BitSet FOLLOW_176_in_normalColumnOperation9322 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_normalColumnOperation9326 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_normalColumnOperation9347 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0005000000000000L});
	public static final BitSet FOLLOW_set_in_normalColumnOperation9351 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_normalColumnOperation9361 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_normalColumnOperation9379 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_INTEGER_in_normalColumnOperation9383 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_184_in_specializedColumnOperation9409 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_specializedColumnOperation9413 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_columnCondition9446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L,0x0FC0200000000000L});
	public static final BitSet FOLLOW_relationType_in_columnCondition9460 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_columnCondition9464 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_IN_in_columnCondition9478 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010400080000000L});
	public static final BitSet FOLLOW_singleColumnInValues_in_columnCondition9496 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inMarker_in_columnCondition9516 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_187_in_columnCondition9544 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_columnCondition9548 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
	public static final BitSet FOLLOW_189_in_columnCondition9550 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L,0x07C0200000000000L});
	public static final BitSet FOLLOW_relationType_in_columnCondition9568 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_columnCondition9572 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_IN_in_columnCondition9590 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010400080000000L});
	public static final BitSet FOLLOW_singleColumnInValues_in_columnCondition9612 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inMarker_in_columnCondition9636 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_property_in_properties9698 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_K_AND_in_properties9702 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_property_in_properties9704 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_noncol_ident_in_property9727 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_184_in_property9729 = new BitSet(new long[]{0xF18EF6E286244040L,0xFF9A3E42CEEE5E1AL,0x000400480047B1D3L});
	public static final BitSet FOLLOW_propertyValue_in_property9733 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_noncol_ident_in_property9745 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
	public static final BitSet FOLLOW_184_in_property9747 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
	public static final BitSet FOLLOW_mapLiteral_in_property9751 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_constant_in_propertyValue9776 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_keyword_in_propertyValue9798 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_184_in_relationType9821 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_182_in_relationType9832 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_183_in_relationType9843 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_185_in_relationType9853 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_186_in_relationType9864 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_173_in_relationType9874 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_relation9896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x07C0200000000000L});
	public static final BitSet FOLLOW_relationType_in_relation9900 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_relation9904 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_relation9916 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
	public static final BitSet FOLLOW_K_IS_in_relation9918 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
	public static final BitSet FOLLOW_K_NOT_in_relation9920 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
	public static final BitSet FOLLOW_K_NULL_in_relation9922 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TOKEN_in_relation9932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_tupleOfIdentifiers_in_relation9936 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x07C0200000000000L});
	public static final BitSet FOLLOW_relationType_in_relation9940 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_relation9944 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_relation9964 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_K_IN_in_relation9966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_inMarker_in_relation9970 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_relation9990 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
	public static final BitSet FOLLOW_K_IN_in_relation9992 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_singleColumnInValues_in_relation9996 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_relation10016 = new BitSet(new long[]{0x0000200000000000L});
	public static final BitSet FOLLOW_K_CONTAINS_in_relation10018 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_K_KEY_in_relation10023 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_relation10039 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_cident_in_relation10051 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
	public static final BitSet FOLLOW_187_in_relation10053 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_relation10057 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
	public static final BitSet FOLLOW_189_in_relation10059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x07C0200000000000L});
	public static final BitSet FOLLOW_relationType_in_relation10063 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_relation10067 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tupleOfIdentifiers_in_relation10079 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L,0x07C0200000000000L});
	public static final BitSet FOLLOW_K_IN_in_relation10089 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010400080000000L});
	public static final BitSet FOLLOW_174_in_relation10103 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_relation10105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_inMarkerForTuple_in_relation10137 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tupleOfTupleLiterals_in_relation10171 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tupleOfMarkersForTuples_in_relation10205 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationType_in_relation10247 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_tupleLiteral_in_relation10251 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_relationType_in_relation10277 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_markerForTuple_in_relation10281 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_relation10311 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000040010047B1DBL});
	public static final BitSet FOLLOW_relation_in_relation10313 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
	public static final BitSet FOLLOW_175_in_relation10316 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_inMarker10337 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_inMarker10347 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_inMarker10351 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_tupleOfIdentifiers10383 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_tupleOfIdentifiers10387 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_tupleOfIdentifiers10392 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_cident_in_tupleOfIdentifiers10396 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_tupleOfIdentifiers10402 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_singleColumnInValues10432 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x8814C0498047B1DBL});
	public static final BitSet FOLLOW_term_in_singleColumnInValues10440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_singleColumnInValues10445 = new BitSet(new long[]{0xF18EF6E286344040L,0xFF9A3E4ACEEE5E1AL,0x881440498047B1DBL});
	public static final BitSet FOLLOW_term_in_singleColumnInValues10449 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_singleColumnInValues10458 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_tupleOfTupleLiterals10488 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_tupleLiteral_in_tupleOfTupleLiterals10492 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_tupleOfTupleLiterals10497 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
	public static final BitSet FOLLOW_tupleLiteral_in_tupleOfTupleLiterals10501 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_tupleOfTupleLiterals10507 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_markerForTuple10528 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_markerForTuple10538 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_markerForTuple10542 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_174_in_tupleOfMarkersForTuples10574 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_markerForTuple_in_tupleOfMarkersForTuples10578 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_177_in_tupleOfMarkersForTuples10583 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000080000000L});
	public static final BitSet FOLLOW_markerForTuple_in_tupleOfMarkersForTuples10587 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002800000000000L});
	public static final BitSet FOLLOW_175_in_tupleOfMarkersForTuples10593 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QMARK_in_inMarkerForTuple10614 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_180_in_inMarkerForTuple10624 = new BitSet(new long[]{0xF18EF6E286100000L,0xFF9A3E428EEE5A1AL,0x000000010047B1D3L});
	public static final BitSet FOLLOW_noncol_ident_in_inMarkerForTuple10628 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_native_type_in_comparatorType10653 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_collection_type_in_comparatorType10669 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_tuple_type_in_comparatorType10681 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_userTypeName_in_comparatorType10697 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_FROZEN_in_comparatorType10709 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_182_in_comparatorType10711 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_comparatorType10715 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
	public static final BitSet FOLLOW_185_in_comparatorType10717 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_comparatorType10735 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_ASCII_in_native_type10764 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_BIGINT_in_native_type10778 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_BLOB_in_native_type10791 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_BOOLEAN_in_native_type10806 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_COUNTER_in_native_type10818 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DECIMAL_in_native_type10830 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DOUBLE_in_native_type10842 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_FLOAT_in_native_type10855 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_INET_in_native_type10869 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_INT_in_native_type10884 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_SMALLINT_in_native_type10900 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TEXT_in_native_type10911 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TIMESTAMP_in_native_type10926 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TINYINT_in_native_type10936 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_UUID_in_native_type10948 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_VARCHAR_in_native_type10963 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_VARINT_in_native_type10975 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TIMEUUID_in_native_type10988 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_DATE_in_native_type10999 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TIME_in_native_type11014 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_MAP_in_collection_type11042 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_182_in_collection_type11045 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_collection_type11049 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
	public static final BitSet FOLLOW_177_in_collection_type11051 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_collection_type11055 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
	public static final BitSet FOLLOW_185_in_collection_type11057 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_LIST_in_collection_type11075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_182_in_collection_type11077 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_collection_type11081 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
	public static final BitSet FOLLOW_185_in_collection_type11083 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_SET_in_collection_type11101 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_182_in_collection_type11104 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_collection_type11108 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
	public static final BitSet FOLLOW_185_in_collection_type11110 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_TUPLE_in_tuple_type11141 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
	public static final BitSet FOLLOW_182_in_tuple_type11143 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_tuple_type11158 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0202000000000000L});
	public static final BitSet FOLLOW_177_in_tuple_type11163 = new BitSet(new long[]{0xF18EF6E286100000L,0xFFDA3E428EEE5A1AL,0x000000090047B1D3L});
	public static final BitSet FOLLOW_comparatorType_in_tuple_type11167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0202000000000000L});
	public static final BitSet FOLLOW_185_in_tuple_type11179 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_username11198 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_LITERAL_in_username11206 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_username11214 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_non_type_ident11241 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_QUOTED_NAME_in_non_type_ident11272 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_basic_unreserved_keyword_in_non_type_ident11297 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_K_KEY_in_non_type_ident11309 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unreserved_function_keyword_in_unreserved_keyword11352 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_unreserved_keyword11368 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_basic_unreserved_keyword_in_unreserved_function_keyword11407 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_native_type_in_unreserved_function_keyword11419 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_basic_unreserved_keyword11457 = new BitSet(new long[]{0x0000000000000002L});
}
