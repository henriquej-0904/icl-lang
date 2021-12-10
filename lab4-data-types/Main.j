.class public Expression
.super java/lang/Object
;
; standard initializer
.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V
      ; set limits used by this method
      .limit locals 10
      .limit stack 256

      ;    1 - the PrintStream object held in java.lang.System.out
       ; place your bytecodes here between START and END
       ; START

            ; initialize frame pointer SL stored in local #4 to null
       aconst_null
       astore 4

getstatic java/lang/System/out Ljava/io/PrintStream;
sipush 5
sipush 6
ifcmplt L0
sipush 0;
goto L1;
L0:
sipush 1;
L1:
ifeq L2
ldc "true"
goto L3
L2: 
ldc "false"
L3:
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

       ; END

       return