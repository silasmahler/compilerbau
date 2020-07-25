/* NewAwkParser.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. NewAwkParser.java */
package de.compilerbau.NewAwkCompiler.generated;

import java.util.*;
import java.io.*;

public class NewAwkParser/*@bgen(jjtree)*/implements NewAwkParserTreeConstants, NewAwkParserConstants {/*@bgen(jjtree)*/
  protected static JJTNewAwkParserState jjtree = new JJTNewAwkParserState();public static void main(String[] args) throws ParseException, FileNotFoundException, TokenMgrError {
     File file = new File(".\\src\\main\\java\\de\\compilerbau\\NewAwkCompiler\\NewAwkTest.txt");
     FileInputStream is = new FileInputStream(file);
     NewAwkParser parser = new NewAwkParser(new BufferedInputStream(is));
     parser.program();

     ((SimpleNode)jjtree.rootNode()).dump(">");

     }

/*
    ==============================================================================================
    Parser Rules and AST generation are defined/handled in this section
    ==============================================================================================
*/
  static final public void program() throws ParseException {
    trace_call("program");
    try {
/*@bgen(jjtree) Program */
  SimpleNode jjtn000 = new SimpleNode(JJTPROGRAM);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        label_1:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case Type:{
            fieldDeclaration();
            break;
            }
          case VOID:{
            method();
            break;
            }
          default:
            jj_la1[0] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case Type:
          case VOID:{
            ;
            break;
            }
          default:
            jj_la1[1] = jj_gen;
            break label_1;
          }
        }
      } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("program");
    }
}

  static final public void fieldDeclaration() throws ParseException {
    trace_call("fieldDeclaration");
    try {
/*@bgen(jjtree) FieldDeclaration */
  SimpleNode jjtn000 = new SimpleNode(JJTFIELDDECLARATION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        jj_consume_token(Type);
        jj_consume_token(Bezeichner);
        jj_consume_token(Zuweisung);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Bezeichner:{
          jj_consume_token(Bezeichner);
          break;
          }
        case IntegerLiteral:{
          jj_consume_token(IntegerLiteral);
          break;
          }
        case DoubleLiteral:{
          jj_consume_token(DoubleLiteral);
          break;
          }
        case CharLiteral:{
          jj_consume_token(CharLiteral);
          break;
          }
        case BooleanValue:{
          jj_consume_token(BooleanValue);
          break;
          }
        case StringLiteral:{
          jj_consume_token(StringLiteral);
          break;
          }
        case NullLiteral:{
          jj_consume_token(NullLiteral);
          break;
          }
        default:
          jj_la1[2] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("fieldDeclaration");
    }
}

  static final public void method() throws ParseException {
    trace_call("method");
    try {
/*@bgen(jjtree) Method */
  SimpleNode jjtn000 = new SimpleNode(JJTMETHOD);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        methodSignature();
        methodBody();
      } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("method");
    }
}

  static final public void methodSignature() throws ParseException {
    trace_call("methodSignature");
    try {
/*@bgen(jjtree) MethodSignature */
  SimpleNode jjtn000 = new SimpleNode(JJTMETHODSIGNATURE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Type:{
          jj_consume_token(Type);
          break;
          }
        case VOID:{
          jj_consume_token(VOID);
          break;
          }
        default:
          jj_la1[3] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(Bezeichner);
        jj_consume_token(KlammerAuf);
        parameterList();
        jj_consume_token(KlammerZu);
      } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("methodSignature");
    }
}

  static final public void parameterList() throws ParseException {
    trace_call("parameterList");
    try {
/*@bgen(jjtree) ParameterList */
  SimpleNode jjtn000 = new SimpleNode(JJTPARAMETERLIST);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        jj_consume_token(Type);
        jj_consume_token(Bezeichner);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          jj_consume_token(COMMA);
          jj_consume_token(Type);
          jj_consume_token(Bezeichner);
          break;
          }
        default:
          jj_la1[4] = jj_gen;
          ;
        }
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("parameterList");
    }
}

  static final public void methodBody() throws ParseException {
    trace_call("methodBody");
    try {
/*@bgen(jjtree) MethodBody */
  SimpleNode jjtn000 = new SimpleNode(JJTMETHODBODY);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        jj_consume_token(BlockAuf);
        label_2:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case Type:{
            fieldDeclaration();
            break;
            }
          case Bezeichner:{
            methodCall();
            break;
            }
          case RETURN:{
            returnStatement();
            break;
            }
          default:
            jj_la1[5] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case RETURN:
          case Type:
          case Bezeichner:{
            ;
            break;
            }
          default:
            jj_la1[6] = jj_gen;
            break label_2;
          }
        }
        jj_consume_token(BlockZu);
      } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("methodBody");
    }
}

  static final public void methodCall() throws ParseException {
    trace_call("methodCall");
    try {
/*@bgen(jjtree) MethodCall */
  SimpleNode jjtn000 = new SimpleNode(JJTMETHODCALL);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        jj_consume_token(Bezeichner);
        jj_consume_token(KlammerAuf);
        jj_consume_token(KlammerZu);
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("methodCall");
    }
}

  static final public void returnStatement() throws ParseException {
    trace_call("returnStatement");
    try {
/*@bgen(jjtree) ReturnStatement */
  SimpleNode jjtn000 = new SimpleNode(JJTRETURNSTATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
System.out.println("ENTER: returnStatement()");
        jj_consume_token(RETURN);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Bezeichner:{
          jj_consume_token(Bezeichner);
          break;
          }
        case IntegerLiteral:{
          jj_consume_token(IntegerLiteral);
          break;
          }
        case DoubleLiteral:{
          jj_consume_token(DoubleLiteral);
          break;
          }
        case CharLiteral:{
          jj_consume_token(CharLiteral);
          break;
          }
        case BooleanValue:{
          jj_consume_token(BooleanValue);
          break;
          }
        case StringLiteral:{
          jj_consume_token(StringLiteral);
          break;
          }
        case NullLiteral:{
          jj_consume_token(NullLiteral);
          break;
          }
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(SEMICOLON);
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("returnStatement");
    }
}

  static final public void comparision() throws ParseException {
    trace_call("comparision");
    try {
/*@bgen(jjtree) Comparision */
  SimpleNode jjtn000 = new SimpleNode(JJTCOMPARISION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
      try {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Bezeichner:{
          jj_consume_token(Bezeichner);
          break;
          }
        case IntegerLiteral:{
          jj_consume_token(IntegerLiteral);
          break;
          }
        case DoubleLiteral:{
          jj_consume_token(DoubleLiteral);
          break;
          }
        case CharLiteral:{
          jj_consume_token(CharLiteral);
          break;
          }
        case BooleanValue:{
          jj_consume_token(BooleanValue);
          break;
          }
        case StringLiteral:{
          jj_consume_token(StringLiteral);
          break;
          }
        case NullLiteral:{
          jj_consume_token(NullLiteral);
          break;
          }
        default:
          jj_la1[8] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case EQUAL:{
          jj_consume_token(EQUAL);
          break;
          }
        case NOT_EQUAL:{
          jj_consume_token(NOT_EQUAL);
          break;
          }
        case SMALLER:{
          jj_consume_token(SMALLER);
          break;
          }
        case S_OR_EQUAL:{
          jj_consume_token(S_OR_EQUAL);
          break;
          }
        case GREATER:{
          jj_consume_token(GREATER);
          break;
          }
        case G_OR_EQUAL:{
          jj_consume_token(G_OR_EQUAL);
          break;
          }
        default:
          jj_la1[9] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Bezeichner:{
          jj_consume_token(Bezeichner);
          break;
          }
        case IntegerLiteral:{
          jj_consume_token(IntegerLiteral);
          break;
          }
        case DoubleLiteral:{
          jj_consume_token(DoubleLiteral);
          break;
          }
        case CharLiteral:{
          jj_consume_token(CharLiteral);
          break;
          }
        case BooleanValue:{
          jj_consume_token(BooleanValue);
          break;
          }
        case StringLiteral:{
          jj_consume_token(StringLiteral);
          break;
          }
        case NullLiteral:{
          jj_consume_token(NullLiteral);
          break;
          }
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
      }
    } finally {
      trace_return("comparision");
    }
}

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public NewAwkParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[11];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x0,0x0,0x0,0x40000000,0x400000,0x400000,0x0,0x0,0x1f800,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x2008,0x2008,0x5dc00,0x2008,0x0,0x8008,0x8008,0x5dc00,0x5dc00,0x0,0x5dc00,};
	}

  {
      enable_tracing();
  }
  /** Constructor with InputStream. */
  public NewAwkParser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public NewAwkParser(java.io.InputStream stream, String encoding) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser.  ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new NewAwkParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public NewAwkParser(java.io.Reader stream) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new NewAwkParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new NewAwkParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public NewAwkParser(NewAwkParserTokenManager tm) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(NewAwkParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 11; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	   trace_token(token, " (in getNextToken)");
	 return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  static private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[53];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 11; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 53; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  static private boolean trace_enabled;

/** Trace enabled. */
  static final public boolean trace_enabled() {
	 return trace_enabled;
  }

  static private int trace_indent = 0;
/** Enable tracing. */
  static final public void enable_tracing() {
	 trace_enabled = true;
  }

/** Disable tracing. */
  static final public void disable_tracing() {
	 trace_enabled = false;
  }

  static protected void trace_call(String s) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Call:	" + s);
	 }
	 trace_indent = trace_indent + 2;
  }

  static protected void trace_return(String s) {
	 trace_indent = trace_indent - 2;
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Return: " + s);
	 }
  }

  static protected void trace_token(Token t, String where) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Consumed token: <" + tokenImage[t.kind]);
	   if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t.image) + "\"");
	   }
	   System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
	 }
  }

  static protected void trace_scan(Token t1, int t2) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Visited token: <" + tokenImage[t1.kind]);
	   if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t1.image) + "\"");
	   }
	   System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
	 }
  }

}
