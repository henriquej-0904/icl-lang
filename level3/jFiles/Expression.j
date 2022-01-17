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

 new f0
	dup
  invokespecial f0/<init>()V
	dup
	; store SL in new frame
aload 4
	putfield f0/sl Ljava/lang/Object;
	; update SL
astore 4
aload 4
new record0
dup
invokespecial record0/<init>()V
dup
new record1
dup
invokespecial record1/<init>()V
dup
sipush 20
putfield record1/num I
dup
pop
putfield record0/teste Ljava/lang/Object;
dup
ldc "gg"
putfield record0/name Ljava/lang/String;
dup
pop
putfield f0/x0 Ljava/lang/Object;
aload 4
getfield f0/x0 Ljava/lang/Object;
checkcast record0
getfield record0/teste Ljava/lang/Object;
checkcast record1
getfield record1/num I
dup
getstatic java/lang/System/out Ljava/io/PrintStream;
swap
  ; convert to String;
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
; call println 
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
aload 4
getfield f0/sl Ljava/lang/Object;
astore 4

       ; END

       return
.end method