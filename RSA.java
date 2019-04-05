import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RSA {
    public static final int ALPH_SUM=53;
    static final char[] alpha = {'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};//53 elements
    static List list = new ArrayList<Long>();
    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("To Generate Public key, put \"-g\" as first argument.\n\n" +
                    "To Encrypt, put \"-e\" as first argument and put \"pk(public key)\" as second arg and put \"n(p*q)\" as third arg.\n" +
                    "Enter Plain text. Then you can get Encrypted text(as number array). \n\n" +
                    "To Decrypt, put \"-d\" as first argument and put \"sk(secret key)\" as second arg and put \"n(p*q)\" as third arg\n" +
                    "Enter Encrypted text(number array with \",\"). Then you can get Decrypted text.");
        }else{
            if(String.valueOf(args[0]).equals("-g")){
                generate();
                return;
            }else if(String.valueOf(args[0]).equals("-e") && args.length==3){
                long pk=Long.valueOf(args[1]);
                long n=Long.valueOf(args[2]);
                int[] numArray = getString();
                showNum(numArray);
                showString(numArray);
                long[] longArray = encrypt(numArray,pk,n);
                showNum(longArray);
                showString(longArray);
                return;
            }else if(String.valueOf(args[0]).equals("-d") && args.length==3){
                long sk=Long.valueOf(args[1]);
                long n=Long.valueOf(args[2]);
                long[] longArray = getNum();
                int[] array = decrypt(longArray,sk,n);
                showNum(array);
                showString(array);
                return;
            }
        }
    }
    public static long[] encrypt(int[] numArray, long e, long n){
        long[] longArray = new long[numArray.length];
        for(int i=0;i<numArray.length;i++){
            int k=numArray[i];
            longArray[i]=numArray[i];
            for(int j=0;j<e-1;j++){
                longArray[i]=(longArray[i]*k)%n;
            }
        }
        return longArray;
    }
    public static int[] decrypt(long[] longArray, long d, long n){
        int[] intArray = new int[longArray.length];
        for(int i=0;i<longArray.length;i++){
            long k=longArray[i];
            long l=longArray[i];
            for(int j=0;j<d-1;j++){
                l=((l*k)%n);
            }
            intArray[i]=(int)l;
        }
        return intArray;
    }
    public static long lcm(long a, long b){
        a--;
        b--;
        return a*b/gcd(a,b);
    }
    public static long gcd(long a, long b){
        long temp;
        while(a%b!=0){
            temp = b;
            b=a%b;
            a=temp;
        }
        return b;
    }
    public static long[] gcdEx(long a, long b){
        long x0 = 1, x1 = 0;
        long y0 = 0, y1 = 1;

        while (b != 0) {
            long q = a / b;
            long r = a % b;
            long x2 = x0 - q * x1;
            long y2 = y0 - q * y1;

            a = b; b = r;
            x0 = x1; x1 = x2;
            y0 = y1; y1 = y2;
        }

        return new long[]{a, x0, y0};
    }
    public static long gcdEx2(long e, long a, long b){
        for(long d=1;;d++){
            long x;
            x=a*d/b;
            if((a*d-x*b)%e==1){
                return d;
            }
        }
    }
    public static int[] getString(){
        BufferedReader br;
        String s1;
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter string.");
        s1 = "";
        try {
            s1 = br.readLine();
        }catch(IOException e){
            System.out.println("IOExeption occurred.");
            return null;
        }
        int numArray[] = new int[s1.length()];
        for(int i=0;i<s1.length();i++){
            numArray[i]=ALPH_SUM-1;
            for(int j=0;j<ALPH_SUM;j++){
                if(s1.charAt(i)==alpha[j]){
                    numArray[i]=j;
                    break;
                }
            }
        }
        return numArray;
    }
    public static long[] getNum(){
        BufferedReader br;
        String s1;
        br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter nums.");
        s1 = "";
        try {
            s1 = br.readLine();
        }catch(IOException e){
            System.out.println("IOExeption occurred.");
            return null;
        }
        String numArray[] = s1.split(",",0);
        long array[] = new long[numArray.length];
        for(int i=0;i<numArray.length;i++){
            array[i]=Long.valueOf(numArray[i]);
        }
        return array;
    }
    public static void showString(int[] numArray){
        System.out.println("");
        for(int i=0;i<numArray.length;i++){
            System.out.print(alpha[(numArray[i]>=ALPH_SUM||numArray[i]<0)?ALPH_SUM-1:numArray[i]]);
        }
    }
    public static void showString(long[] numArray){
        System.out.println("");
        for(int i=0;i<numArray.length;i++){
            System.out.print(alpha[(numArray[i]>=ALPH_SUM||numArray[i]<0)?ALPH_SUM-1:(int)numArray[i]]);
        }
        System.out.println("");
    }
    public static void showNum(long[] numArray){
        System.out.println("num");
        for(int i=0;;){
            System.out.print(numArray[i]);
            i++;
            if(i>numArray.length-1){
                break;
            }
            System.out.print(",");
        }
    }
    public static void showNum(int[] numArray){
        System.out.println("num");
        for(int i=0;;){
            System.out.print(numArray[i]);
            i++;
            if(i>numArray.length-1){
                break;
            }
            System.out.print(",");
        }
    }
    public static long getPublicKey(long l){
		Random rand = new Random();
		int weight = 200;
		long p=(long)rand.nextInt(25000);
		if(p>l+weight){
			p=l-weight;
		}
        for(;p<l;p++){
            if(gcd(l,p)==1){
                return p;
            }
        }
        return 0;
    }
    public static long getSecretKey(long e, long totient){
        long d[]=gcdEx(e,totient);
        System.out.println("d[0]:"+d[0]+" d[1]:"+d[1]+" d[2]"+d[2]);
        if(d[2]>0)return d[2];
        return d[1];
    }
    public static boolean millerRabin(long num){
        //Prime : True
        //Not prime  : False
        int k = 20;//parameter
        if(num<0){
            num=num*-1;
        }
        if(num<2 || num%2 == 0){
            return false;
        }
        long n = num-1;
        long s = 0;
        long d = 0;
        while(n % 2 == 0){
            s++;
            n=n/2;
        }
        d = n;
        for(int j = 0;j<k;j++){
            int a = (int)(Math.random()*(num-2))+1;
            long t = d;
            long y = pow(a,d,num);
            long r = s-1;
            while(t != num-1 && y != 1 && y != num-1){
                y = (y * y) % num;
                t <<= 1;
            }
            if(y != num-1 && (t & 1) == 0){
                return false;
            }
        }
        return true;
    }
    public static long pow(long a, long d,long mod){
        long result = 1;
        while(d>0){
            if((d&1)==1){
                result=(result*a)%mod;
            }
            a=(a*a)%mod;
            d>>=1;
        }
        return result;
    }
    public static void generate(){
        System.out.println("Generate prime number p and q");
    	int p=4;
        int q=4;
		Random random = new Random();
        while(true){
	        int randomWeight=100000;
        	while(!millerRabin(p)){
        	    p=random.nextInt(randomWeight);
        	}
        	while(!millerRabin(q)){
        	    q=random.nextInt(randomWeight);
        	}
        	if(p<0)p=p*(-1);
        	if(q<0)q=q*(-1);
			if(p*q>0){
				break;
			}else{
				p=4;
				q=4;
			}
		}
        //to see p and q
        System.out.println("p:"+p+"\nq:"+q);

        System.out.println("Generate public key");
        long e =lcm(p,q);
        long pk = getPublicKey(e);
        System.out.println("Public key:"+pk+"\nn:"+p*q);

        System.out.println("Generate secret key");
        long sk = gcdEx2(e,pk,(p-1)*(q-1));
        System.out.println("Secret key:"+sk);
        p=0;q=0;pk=0;sk=0;
        p=p+q+(int)sk+(int)pk;
    }
}
