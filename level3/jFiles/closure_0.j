.class public closure_0
.super	java/lang/Object
.implements closure_interface__Integer
.field	public sl Lf1;
.method public	<init>()V
aload_0
invokenonvirtual	java/lang/Object/<init>()V
 return
.end method
.method public apply()I
.limit locals 2
.limit stack 256
 new f2
	dup
  invokespecial f2/<init>()V
	dup
aload 0
getfield closure_0/sl Lf1;
putfield f2/sl Lf1;
astore 1
aload 1
getfield f2/sl Lf1;
getfield f1/x0 Lref_of_Integer;
getfield ref_of_Integer/v I
sipush 1
iadd
dup
aload 1
getfield f2/sl Lf1;
getfield f1/x0 Lref_of_Integer;
swap
putfield ref_of_Integer/v I
ireturn
.end method
