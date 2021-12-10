public class ASTNum extends ASTNodeAbstract {

int val;

        public int eval() { return val; }

        public ASTNum(int n)
        {
	   val = n;
        }

}

