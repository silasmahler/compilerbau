/* NewAwkParser.java */
/* Generated By:JavaCC: Do not edit this line. NewAwkParser.java */
package de.compilerbau.NewAwkCompiler.generated;

import java.util.*;
import java.io.*;
import de.compilerbau.NewAwkCompiler.generated.Token;


public class NewAwkParser implements NewAwkParserConstants {
 public static void main(String[] args) throws ParseException, FileNotFoundException, TokenMgrError {
     /*File file = new File(".\\src\\main\\java\\de\\compilerbau\\NewAwkCompiler\\NewAwkTestAddNumbers.txt");
     FileInputStream is = new FileInputStream(file);
     NewAwkParser parser = new NewAwkParser(new BufferedInputStream(is));
     parser.Start();*/

     File file2 = new File(".\\src\\main\\java\\de\\compilerbau\\NewAwkCompiler\\NewAwkTest.txt");
     FileInputStream is2 = new FileInputStream(file2);
     NewAwkParser parser2 = new NewAwkParser(new BufferedInputStream(is2));
     parser2.program();

     }

  static final public void program() throws ParseException {
    trace_call("program");
    try {

System.out.println("ENTER: program()");
      label_1:
      while (true) {
        if (jj_2_1(49)) {
          ;
        } else {
          break label_1;
        }
        if (jj_2_2(49)) {
          method();
        } else if (jj_2_3(49)) {
          fieldDeclaration();
        } else if (jj_2_4(49)) {
          returnStatement();
        } else {
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } finally {
      trace_return("program");
    }
}

  static final public void returnStatement() throws ParseException {
    trace_call("returnStatement");
    try {

System.out.println("ENTER: returnStatement()");
      jj_consume_token(RETURN);
      if (jj_2_10(49)) {
        jj_consume_token(Bezeichner);
      } else if (jj_2_11(49)) {
        if (jj_2_5(49)) {
          jj_consume_token(IntegerLiteral);
        } else if (jj_2_6(49)) {
          jj_consume_token(DoubleLiteral);
        } else if (jj_2_7(49)) {
          jj_consume_token(CharLiteral);
        } else if (jj_2_8(49)) {
          jj_consume_token(BooleanExpression);
        } else if (jj_2_9(49)) {
          jj_consume_token(StringLiteral);
        } else {
          jj_consume_token(-1);
          throw new ParseException();
        }
        jj_consume_token(SEMICOLON);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("returnStatement");
    }
}

  static final public void method() throws ParseException {
    trace_call("method");
    try {

System.out.println("ENTER: method()");
      methodSignature();
      methodBody();
    } finally {
      trace_return("method");
    }
}

  static final public void methodCall() throws ParseException {
    trace_call("methodCall");
    try {

      jj_consume_token(Bezeichner);
      jj_consume_token(57);
    } finally {
      trace_return("methodCall");
    }
}

  static final public void fieldDeclaration() throws ParseException {
    trace_call("fieldDeclaration");
    try {

      jj_consume_token(Type);
      jj_consume_token(Bezeichner);
      jj_consume_token(Zuweisung);
      if (jj_2_12(49)) {
        jj_consume_token(IntegerLiteral);
      } else if (jj_2_13(49)) {
        jj_consume_token(DoubleLiteral);
      } else if (jj_2_14(49)) {
        jj_consume_token(CharLiteral);
      } else if (jj_2_15(49)) {
        jj_consume_token(BooleanExpression);
      } else if (jj_2_16(49)) {
        jj_consume_token(StringLiteral);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("fieldDeclaration");
    }
}

  static final public void methodBody() throws ParseException {
    trace_call("methodBody");
    try {

      jj_consume_token(BlockAuf);
      label_2:
      while (true) {
        if (jj_2_17(49)) {
          fieldDeclaration();
        } else if (jj_2_18(49)) {
          methodCall();
        } else {
          jj_consume_token(-1);
          throw new ParseException();
        }
        if (jj_2_19(49)) {
          ;
        } else {
          break label_2;
        }
      }
      jj_consume_token(BlockZu);
    } finally {
      trace_return("methodBody");
    }
}

  static final public void methodSignature() throws ParseException {
    trace_call("methodSignature");
    try {

      if (jj_2_20(49)) {
        jj_consume_token(Type);
      } else if (jj_2_21(49)) {
        jj_consume_token(VOID);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(Bezeichner);
      jj_consume_token(KlammerAuf);
      parameterListe();
      jj_consume_token(KlammerZu);
    } finally {
      trace_return("methodSignature");
    }
}

  static final public void parameterListe() throws ParseException {
    trace_call("parameterListe");
    try {

      jj_consume_token(Type);
      jj_consume_token(Bezeichner);
      label_3:
      while (true) {
        if (jj_2_22(49)) {
          ;
        } else {
          break label_3;
        }
        jj_consume_token(Commata);
        jj_consume_token(Type);
        jj_consume_token(Bezeichner);
      }
    } finally {
      trace_return("parameterListe");
    }
}

  static final public void booleanValue() throws ParseException {
    trace_call("booleanValue");
    try {

      jj_consume_token(BooleanExpression);
    } finally {
      trace_return("booleanValue");
    }
}

  static final public void intDoubleCharOperation() throws ParseException {
    trace_call("intDoubleCharOperation");
    try {

      if (jj_2_23(49)) {
        jj_consume_token(IntegerLiteral);
      } else if (jj_2_24(49)) {
        jj_consume_token(DoubleLiteral);
      } else if (jj_2_25(49)) {
        jj_consume_token(CharLiteral);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
      if (jj_2_26(49)) {
        jj_consume_token(PLUS);
      } else if (jj_2_27(49)) {
        jj_consume_token(MINUS);
      } else if (jj_2_28(49)) {
        jj_consume_token(MULTIPLICATION);
      } else if (jj_2_29(49)) {
        jj_consume_token(DIVISION);
      } else if (jj_2_30(49)) {
        jj_consume_token(MODULO);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
      if (jj_2_31(49)) {
        jj_consume_token(IntegerLiteral);
      } else if (jj_2_32(49)) {
        jj_consume_token(DoubleLiteral);
      } else if (jj_2_33(49)) {
        jj_consume_token(CharLiteral);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("intDoubleCharOperation");
    }
}

  static final public void test() throws ParseException {
    trace_call("test");
    try {

      jj_consume_token(BlockAuf);
    } finally {
      trace_return("test");
    }
}

  static final public void adderSubstracter() throws ParseException {
    trace_call("adderSubstracter");
    try {

      jj_consume_token(IntegerLiteral);
      label_4:
      while (true) {
        if (jj_2_34(49)) {
          ;
        } else {
          break label_4;
        }
        jj_consume_token(PLUS);
        jj_consume_token(IntegerLiteral);
      }
      label_5:
      while (true) {
        if (jj_2_35(49)) {
          ;
        } else {
          break label_5;
        }
        jj_consume_token(MINUS);
        jj_consume_token(IntegerLiteral);
      }
      jj_consume_token(0);
    } finally {
      trace_return("adderSubstracter");
    }
}

  static private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_3()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_4()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_5()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static private boolean jj_2_6(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_6()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  static private boolean jj_2_7(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_7()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(6, xla); }
  }

  static private boolean jj_2_8(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_8()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(7, xla); }
  }

  static private boolean jj_2_9(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_9()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(8, xla); }
  }

  static private boolean jj_2_10(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_10()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(9, xla); }
  }

  static private boolean jj_2_11(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_11()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(10, xla); }
  }

  static private boolean jj_2_12(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_12()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(11, xla); }
  }

  static private boolean jj_2_13(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_13()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(12, xla); }
  }

  static private boolean jj_2_14(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_14()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(13, xla); }
  }

  static private boolean jj_2_15(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_15()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(14, xla); }
  }

  static private boolean jj_2_16(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_16()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(15, xla); }
  }

  static private boolean jj_2_17(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_17()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(16, xla); }
  }

  static private boolean jj_2_18(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_18()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(17, xla); }
  }

  static private boolean jj_2_19(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_19()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(18, xla); }
  }

  static private boolean jj_2_20(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_20()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(19, xla); }
  }

  static private boolean jj_2_21(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_21()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(20, xla); }
  }

  static private boolean jj_2_22(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_22()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(21, xla); }
  }

  static private boolean jj_2_23(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_23()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(22, xla); }
  }

  static private boolean jj_2_24(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_24()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(23, xla); }
  }

  static private boolean jj_2_25(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_25()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(24, xla); }
  }

  static private boolean jj_2_26(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_26()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(25, xla); }
  }

  static private boolean jj_2_27(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_27()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(26, xla); }
  }

  static private boolean jj_2_28(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_28()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(27, xla); }
  }

  static private boolean jj_2_29(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_29()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(28, xla); }
  }

  static private boolean jj_2_30(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_30()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(29, xla); }
  }

  static private boolean jj_2_31(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_31()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(30, xla); }
  }

  static private boolean jj_2_32(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_32()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(31, xla); }
  }

  static private boolean jj_2_33(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_33()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(32, xla); }
  }

  static private boolean jj_2_34(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_34()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(33, xla); }
  }

  static private boolean jj_2_35(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_35()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(34, xla); }
  }

  static private boolean jj_3_34()
 {
    if (jj_scan_token(PLUS)) return true;
    if (jj_scan_token(IntegerLiteral)) return true;
    return false;
  }

  static private boolean jj_3_15()
 {
    if (jj_scan_token(BooleanExpression)) return true;
    return false;
  }

  static private boolean jj_3_8()
 {
    if (jj_scan_token(BooleanExpression)) return true;
    return false;
  }

  static private boolean jj_3_32()
 {
    if (jj_scan_token(DoubleLiteral)) return true;
    return false;
  }

  static private boolean jj_3_28()
 {
    if (jj_scan_token(MULTIPLICATION)) return true;
    return false;
  }

  static private boolean jj_3_24()
 {
    if (jj_scan_token(DoubleLiteral)) return true;
    return false;
  }

  static private boolean jj_3_14()
 {
    if (jj_scan_token(CharLiteral)) return true;
    return false;
  }

  static private boolean jj_3_7()
 {
    if (jj_scan_token(CharLiteral)) return true;
    return false;
  }

  static private boolean jj_3_18()
 {
    if (jj_3R_methodCall_120_5_9()) return true;
    return false;
  }

  static private boolean jj_3_27()
 {
    if (jj_scan_token(MINUS)) return true;
    return false;
  }

  static private boolean jj_3_22()
 {
    if (jj_scan_token(Commata)) return true;
    if (jj_scan_token(Type)) return true;
    if (jj_scan_token(Bezeichner)) return true;
    return false;
  }

  static private boolean jj_3_31()
 {
    if (jj_scan_token(IntegerLiteral)) return true;
    return false;
  }

  static private boolean jj_3_26()
 {
    if (jj_scan_token(PLUS)) return true;
    return false;
  }

  static private boolean jj_3_23()
 {
    if (jj_scan_token(IntegerLiteral)) return true;
    return false;
  }

  static private boolean jj_3_13()
 {
    if (jj_scan_token(DoubleLiteral)) return true;
    return false;
  }

  static private boolean jj_3_6()
 {
    if (jj_scan_token(DoubleLiteral)) return true;
    return false;
  }

  static private boolean jj_3_21()
 {
    if (jj_scan_token(VOID)) return true;
    return false;
  }

  static private boolean jj_3_17()
 {
    if (jj_3R_fieldDeclaration_124_5_7()) return true;
    return false;
  }

  static private boolean jj_3_19()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_17()) {
    jj_scanpos = xsp;
    if (jj_3_18()) return true;
    }
    return false;
  }

  static private boolean jj_3R_parameterListe_138_5_12()
 {
    if (jj_scan_token(Type)) return true;
    if (jj_scan_token(Bezeichner)) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_22()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static private boolean jj_3_4()
 {
    if (jj_3R_returnStatement_106_5_8()) return true;
    return false;
  }

  static private boolean jj_3_20()
 {
    if (jj_scan_token(Type)) return true;
    return false;
  }

  static private boolean jj_3R_methodSignature_133_5_10()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_20()) {
    jj_scanpos = xsp;
    if (jj_3_21()) return true;
    }
    if (jj_scan_token(Bezeichner)) return true;
    if (jj_scan_token(KlammerAuf)) return true;
    if (jj_3R_parameterListe_138_5_12()) return true;
    if (jj_scan_token(KlammerZu)) return true;
    return false;
  }

  static private boolean jj_3R_methodBody_129_5_11()
 {
    if (jj_scan_token(BlockAuf)) return true;
    Token xsp;
    if (jj_3_19()) return true;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_19()) { jj_scanpos = xsp; break; }
    }
    if (jj_scan_token(BlockZu)) return true;
    return false;
  }

  static private boolean jj_3_12()
 {
    if (jj_scan_token(IntegerLiteral)) return true;
    return false;
  }

  static private boolean jj_3_5()
 {
    if (jj_scan_token(IntegerLiteral)) return true;
    return false;
  }

  static private boolean jj_3R_fieldDeclaration_124_5_7()
 {
    if (jj_scan_token(Type)) return true;
    if (jj_scan_token(Bezeichner)) return true;
    if (jj_scan_token(Zuweisung)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_12()) {
    jj_scanpos = xsp;
    if (jj_3_13()) {
    jj_scanpos = xsp;
    if (jj_3_14()) {
    jj_scanpos = xsp;
    if (jj_3_15()) {
    jj_scanpos = xsp;
    if (jj_3_16()) return true;
    }
    }
    }
    }
    return false;
  }

  static private boolean jj_3_11()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_5()) {
    jj_scanpos = xsp;
    if (jj_3_6()) {
    jj_scanpos = xsp;
    if (jj_3_7()) {
    jj_scanpos = xsp;
    if (jj_3_8()) {
    jj_scanpos = xsp;
    if (jj_3_9()) return true;
    }
    }
    }
    }
    if (jj_scan_token(SEMICOLON)) return true;
    return false;
  }

  static private boolean jj_3R_methodCall_120_5_9()
 {
    if (jj_scan_token(Bezeichner)) return true;
    if (jj_scan_token(57)) return true;
    return false;
  }

  static private boolean jj_3R_method_114_5_6()
 {
    if (jj_3R_methodSignature_133_5_10()) return true;
    if (jj_3R_methodBody_129_5_11()) return true;
    return false;
  }

  static private boolean jj_3_3()
 {
    if (jj_3R_fieldDeclaration_124_5_7()) return true;
    return false;
  }

  static private boolean jj_3_35()
 {
    if (jj_scan_token(MINUS)) return true;
    if (jj_scan_token(IntegerLiteral)) return true;
    return false;
  }

  static private boolean jj_3_10()
 {
    if (jj_scan_token(Bezeichner)) return true;
    return false;
  }

  static private boolean jj_3_30()
 {
    if (jj_scan_token(MODULO)) return true;
    return false;
  }

  static private boolean jj_3R_returnStatement_106_5_8()
 {
    if (jj_scan_token(RETURN)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_10()) {
    jj_scanpos = xsp;
    if (jj_3_11()) return true;
    }
    return false;
  }

  static private boolean jj_3_16()
 {
    if (jj_scan_token(StringLiteral)) return true;
    return false;
  }

  static private boolean jj_3_9()
 {
    if (jj_scan_token(StringLiteral)) return true;
    return false;
  }

  static private boolean jj_3_1()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_2()) {
    jj_scanpos = xsp;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3_4()) return true;
    }
    }
    return false;
  }

  static private boolean jj_3_2()
 {
    if (jj_3R_method_114_5_6()) return true;
    return false;
  }

  static private boolean jj_3_29()
 {
    if (jj_scan_token(DIVISION)) return true;
    return false;
  }

  static private boolean jj_3_33()
 {
    if (jj_scan_token(CharLiteral)) return true;
    return false;
  }

  static private boolean jj_3_25()
 {
    if (jj_scan_token(CharLiteral)) return true;
    return false;
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
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[0];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {};
	}
  static final private JJCalls[] jj_2_rtns = new JJCalls[35];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

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
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
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
	 jj_gen = 0;
	 for (int i = 0; i < 0; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
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
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
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
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
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
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(NewAwkParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error {
    @Override
    public Throwable fillInStackTrace() {
      return this;
    }
  }
  static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
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
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[58];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 0; i++) {
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
	 for (int i = 0; i < 58; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
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

  static private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 35; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			   case 2: jj_3_3(); break;
			   case 3: jj_3_4(); break;
			   case 4: jj_3_5(); break;
			   case 5: jj_3_6(); break;
			   case 6: jj_3_7(); break;
			   case 7: jj_3_8(); break;
			   case 8: jj_3_9(); break;
			   case 9: jj_3_10(); break;
			   case 10: jj_3_11(); break;
			   case 11: jj_3_12(); break;
			   case 12: jj_3_13(); break;
			   case 13: jj_3_14(); break;
			   case 14: jj_3_15(); break;
			   case 15: jj_3_16(); break;
			   case 16: jj_3_17(); break;
			   case 17: jj_3_18(); break;
			   case 18: jj_3_19(); break;
			   case 19: jj_3_20(); break;
			   case 20: jj_3_21(); break;
			   case 21: jj_3_22(); break;
			   case 22: jj_3_23(); break;
			   case 23: jj_3_24(); break;
			   case 24: jj_3_25(); break;
			   case 25: jj_3_26(); break;
			   case 26: jj_3_27(); break;
			   case 27: jj_3_28(); break;
			   case 28: jj_3_29(); break;
			   case 29: jj_3_30(); break;
			   case 30: jj_3_31(); break;
			   case 31: jj_3_32(); break;
			   case 32: jj_3_33(); break;
			   case 33: jj_3_34(); break;
			   case 34: jj_3_35(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

}
