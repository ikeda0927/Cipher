
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RSA {
    public static final int ALPH_SUM=53;
    static final char[] alpha = {'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' '};//53 elements
    static List list = new ArrayList<Long>();
    public static void main(String[] args) {
	// write your code here//
        //p = 7 (prime number)
        //q = 19 (prime number)
        //e = 18 (the least common multiple)
        //public key = 5 (1 < public key < e)
        //secret key = 49 (public key * secret key = e * n + 1)
//        long p=7;
//        long q=19;
////        long n=getPublicKey1(p,q);
//        long e = lcm(p,q);
//        System.out.println("e:"+e);
//        long d = getSecretKey(e,(p-1)*(q-1));
//        int[] numArray = getString();
//        showNum(numArray);
//        showString(numArray);
//        long[] longArray = encrypt(numArray,e,p*q);
//        showNum(longArray);
//        int[] array = decrypt(longArray,d,p*q);
//        showNum(array);
//        showString(array);

        long a= 111;
        long b= 30;
        System.out.println("d="+gcdEx(a,b));
        System.out.println("x:"+x+" y:"+y);
    }
    public static long[] encrypt(int[] numArray, long e, long n){
        long[] longArray = new long[numArray.length];
        for(int i=0;i<numArray.length;i++){
            int k=numArray[i];
            longArray[i]=numArray[i];
            for(int j=0;j<e;j++){
//                int k=numArray[i];
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
            for(int j=0;j<d;j++){
//                long k=longArray[i];
                System.out.println("i:"+i+" l:"+l+" k:"+k);
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
    public static long gcdEx(long a, long b){

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
    public static void showString(int[] numArray){//test
        System.out.println("");
        for(int i=0;i<numArray.length;i++){
//            System.out.print(alpha[numArray[i]]);
            System.out.print(alpha[(numArray[i]>=ALPH_SUM)?ALPH_SUM-1:numArray[i]]);
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
    public static long getPublicKey1(long p, long q){
        return p*q;
    }
//    public static long getPublicKey2(long p, long q){
//        return lcm(p,q);
//    }
    public static long getSecretKey(long e, long totient){
        long d = 0;
//        while(d<1000000000){
//            if(d*e % totient == 1){
//                System.out.println("d="+d);
//                break;
//            }
//            d++;
//        }
        return d;
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
//            if(y%num != 1){
//                if(y%num != -1)break;
//                for(;r>=0;r--){
//                    y=y*y;
//                    if(y%num != -1)break;
//                }
//                return false;
//            }
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
}
