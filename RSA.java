import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RSA {
    public static void main(String[] args) {
        System.out.println();
        if(args.length<1){
            System.out.println("To Generate Public key, put \"-g\" as first argument.\n\n" +
                    "To Encrypt, put \"-e\" as first argument and put \"pk(public key)\" as second arg and put \"n(p*q)\" as third arg.\n" +
                    "Enter Plain text. Then you can get Encrypted text(as number array). \n\n" +
                    "To Decrypt, put \"-d\" as first argument and put \"sk(secret key)\" as second arg and put \"n(p*q)\" as third arg\n" +
                    "Enter Encrypted text(number array with \",\"). Then you can get Decrypted text.\n\n" +
                    "To Encrypt Text in a file, put \"-fe\" as first argument and put \"File name(name only)\" as second arg and put \"pk(public key)\" as third arg and put \"n(p*q)\" as forth arg.\n" +
                    "Check Directory where run this program, there is \"Filename+Crypt.txt\" File.\n\n" +
                    "To Decrypt Text in a file, put \"-fd\" as first argument and put \"File name(name only)\" as second arg and put \"sk(secret key)\" as third arg and put \"n(p*q)\" as forth arg.\n" +
                    "Check Directory where run this program, there is \"Filename+Decrypted.txt\" File.\n\n" +
                    "To Get Hash(SHA-256) of a File, put \"-hash\" as first argument and put \"File name(name only)\"as second arg.\n" +
                    "Check Directory where run this program, there is \"Filename+_Hash.txt\" File\n\n" +
                    "To Get Electronic Signature, put \"-es\" as first argument and put \"File name(name only)\"as second arg and put \"sk(secret key)\" as third arg and put \"n(p*q)\" as forth arg.\n" +
                    "Check Directory where run this program, there is  \"Filename+_E_Signature.txt\" File\n\n" +
                    "To Compare E-Signature to Hash,  put \"-ces\" as first argument and put \"File name(name only)\"as second arg and put \"pk(public key)\" as third arg and put \"n(p*q)\" as forth arg." +
                    "And Enter  Encrypted text(number array with \",\"). If \"Same text\" displayed, the text wasn't altered. If\"NOT same text\" displayed, the text was altered\n\n");
        }else{
            if(String.valueOf(args[0]).equals("-g")){
                generate();
                return;
            }else if(String.valueOf(args[0]).equals("-e") && args.length==3){
                long pk=Long.valueOf(args[1]);
                long n=Long.valueOf(args[2]);
                char[] numArray = getString();
                showString(numArray);
                long[] longArray = encrypt(numArray,pk,n);
                showNum(longArray);
                return;
            }else if(String.valueOf(args[0]).equals("-d") && args.length==3){
                long sk=Long.valueOf(args[1]);
                long n=Long.valueOf(args[2]);
                long[] longArray = getNum();
                char[] array = decrypt(longArray,sk,n);
                showString(array);
                return;
            }else if(String.valueOf(args[0]).equals("-fe") && args.length==4){
                String fileName = args[1];
                String filePath1 = "./"+fileName+".txt";
                String filePath2 = "./"+fileName+"Crypt.txt";
                long pk=Long.valueOf(args[2]);
                long n=Long.valueOf(args[3]);
                File file1;
                FileReader fr;
                BufferedReader br;
                File file2;
                FileWriter fw;
                BufferedWriter bw;
                try{
                    file1 = new File(filePath1);
                    fr = new FileReader(file1);
                    br = new BufferedReader(fr);
                    file2 = new File(filePath2);
                    fw = new FileWriter(file2);
                    bw = new BufferedWriter(fw);
                }catch (FileNotFoundException e){
                    System.out.println("File Not Found : "+filePath1);
                    return;
                }catch (IOException e){
                    System.out.println("File IO Exception");
                    return;
                }
                String s;
                try{
                    while((s=br.readLine())!=null){
                        char[] numArray = s.toCharArray();
                        long[] longArray = encrypt(numArray,pk,n);
                        String temp = "";
                        for(int i=0;i<longArray.length;){
                            temp+=String.valueOf(longArray[i]);
                            i++;
                            if(i>longArray.length-1){
                                break;
                            }
                            temp+=",";
                        }
                        bw.write(temp);
                        bw.newLine();


                    }
                    System.out.println("Finish");
                    bw.close();
                    br.close();
                }catch (IOException e){
                    System.out.println("File IO Exception");
                    return;
                }
                return;
            }else if(String.valueOf(args[0]).equals("-fd") && args.length==4){
                String fileName = args[1];
                String filePath1 = "./"+fileName+".txt";
                String filePath2 = "./"+fileName+"Decrypted.txt";
                long sk=Long.valueOf(args[2]);
                long n=Long.valueOf(args[3]);
                File file1;
                FileReader fr;
                BufferedReader br;
                File file2;
                FileWriter fw;
                BufferedWriter bw;
                try{
                    file1 = new File(filePath1);
                    fr = new FileReader(file1);
                    br = new BufferedReader(fr);
                    file2 = new File(filePath2);
                    fw = new FileWriter(file2);
                    bw = new BufferedWriter(fw);
                }catch (FileNotFoundException e){
                    System.out.println("File Not Found : "+filePath1);
                    return;
                }catch (IOException e){
                    System.out.println("File IO Exception");
                    return;
                }
                String s;
                try{
                    while((s=br.readLine())!=null){
                        String numArray[] = s.split(",",0);
                        if(numArray.length == 1 && String.valueOf(numArray[0]).equals("")){
                            continue;
                        }
                        long longArray[] = new long[numArray.length];
                        for(int i=0;i<numArray.length;i++){
                            longArray[i]=Long.valueOf(numArray[i]);
                        }
                        char[] array = decrypt(longArray,sk,n);
                        String temp = String.valueOf(array);
                        bw.write(temp);
                        bw.newLine();
                    }
                    System.out.println("Finish");
                    bw.close();
                    br.close();
                }catch (IOException e){
                    System.out.println("File IO Exception");
                    return;
                }
                return;
            }else if(String.valueOf(args[0]).equals("-es") && args.length==4){
                String fileName = args[1];
                String filePath1 = "./"+fileName+".txt";
                String filePath2 = "./"+fileName+"_E_Signature.txt";
                long sk=Long.valueOf(args[2]);
                long n=Long.valueOf(args[3]);
                File file1;
                FileReader fr;
                BufferedReader br;
                File file2;
                FileWriter fw;
                BufferedWriter bw;
                try{
                    file1 = new File(filePath1);
                    fr = new FileReader(file1);
                    br = new BufferedReader(fr);
                    file2 = new File(filePath2);
                    fw = new FileWriter(file2);
                    bw = new BufferedWriter(fw);
                }catch (FileNotFoundException e){
                    System.out.println("File Not Found : "+filePath1);
                    return;
                }catch (IOException e){
                    System.out.println("File IO Exception");
                    return;
                }
                String s;
                String wholeText = "";
                try{
                    while((s=br.readLine())!=null){
                        wholeText+=s;
                    }
                    br.close();
                }catch (IOException e){
                }
                MessageDigest md;
                byte[] bytes;
                try{
                    md = MessageDigest.getInstance("SHA-256");
                }catch (NoSuchAlgorithmException e){
                    System.out.println("No SHA-256 algorithm");
                    return;
                }
                md.update(wholeText.getBytes());
                bytes=md.digest();
                String hexBinary = bytesToHexes(bytes);
                System.out.println("Hash is");
                System.out.println(hexBinary);
                System.out.println("E_Signature is");
                long[] eSig = encrypt(hexBinary.toCharArray(),sk,n);
                String strEESig = "";
                for(int i=0;i<eSig.length;){
                    strEESig+=String.valueOf(eSig[i]);
                    i++;
                    if(i>eSig.length-1){
                        break;
                    }
                    strEESig+=",";
                }
                System.out.println(strEESig);
                try{
                    bw.write("Hash is");
                    bw.newLine();
                    bw.newLine();
                    bw.write(hexBinary);
                    bw.newLine();
                    bw.newLine();
                    bw.write("E_Signature is");
                    bw.newLine();
                    bw.newLine();
                    bw.write(strEESig);
                    bw.close();
                }catch (IOException e){
                    System.out.println("IO Exception");
                    return;
                }
            }else if(String.valueOf(args[0]).equals("-ces") && args.length==4){
                String fileName = args[1];
                String filePath1 = "./"+fileName+".txt";
                long pk=Long.valueOf(args[2]);
                long n=Long.valueOf(args[3]);
                File file1;
                FileReader fr;
                BufferedReader br;
                try{
                    file1 = new File(filePath1);
                    fr = new FileReader(file1);
                    br = new BufferedReader(fr);
                }catch (FileNotFoundException e){
                    System.out.println("File Not Found : "+filePath1);
                    return;
                }
                String s;
                String wholeText = "";
                try{
                    while((s=br.readLine())!=null){
                        wholeText+=s;
                    }
                    br.close();
                }catch (IOException e){
                }
                MessageDigest md;
                byte[] bytes;
                try{
                    md = MessageDigest.getInstance("SHA-256");
                }catch (NoSuchAlgorithmException e){
                    System.out.println("No SHA-256 algorithm");
                    return;
                }
                md.update(wholeText.getBytes());
                bytes=md.digest();
                String hexBinary = bytesToHexes(bytes).toUpperCase();
                System.out.println("E_Signature is");
                System.out.println(hexBinary);

                long[] longArray = getNum();
                char[] dESig = decrypt(longArray,pk,n);
                String strDESig = String.valueOf(dESig).toUpperCase();
                System.out.println(strDESig);
                if(String.valueOf(hexBinary).equals(strDESig)){
                    System.out.println("Same text");
                }else{
                    System.out.println("NOT same text");
                }
                return;
            }else if(String.valueOf(args[0]).equals("-hash") && args.length==2){
                String fileName = args[1];
                String filePath1 = "./"+fileName+".txt";
                String filePath2 = "./"+fileName+"_Hash.txt";
                File file1;
                FileReader fr;
                BufferedReader br;
                File file2;
                FileWriter fw;
                BufferedWriter bw;
                try{
                    file1 = new File(filePath1);
                    fr = new FileReader(file1);
                    br = new BufferedReader(fr);
                    file2 = new File(filePath2);
                    fw = new FileWriter(file2);
                    bw = new BufferedWriter(fw);
                }catch (FileNotFoundException e){
                    System.out.println("File Not Found : "+filePath1);
                    return;
                }catch (IOException e){
                    System.out.println("File IO Exception");
                    return;
                }
                String s;
                String wholeText = "";
                try{
                    while((s=br.readLine())!=null){
                        wholeText+=s;
                    }
                    br.close();
                }catch (IOException e){
                }
                MessageDigest md;
                byte[] bytes;
                try{
                    md = MessageDigest.getInstance("SHA-256");
                }catch (NoSuchAlgorithmException e){
                    System.out.println("No SHA-256 algorithm");
                    return;
                }
                md.update(wholeText.getBytes());
                bytes=md.digest();
                String hexBinary = bytesToHexes(bytes);
                System.out.println("Hash is");
                System.out.println(hexBinary);
                try{
                    bw.write("Hash is");
                    bw.newLine();
                    bw.write(hexBinary);
                    bw.close();
                }catch (IOException e){
                    System.out.println("IO Exception");
                    return;
                }
            }else if(String.valueOf(args[0]).equals("-factoring") && args.length==2){
                System.out.println("Factoring...");
                long num = Long.valueOf(args[1]);
                for(long n=2;n<num;n++){
                    if(num%n==0){
                        System.out.println("p:"+n);
                        System.out.println("q:"+num/n);
                        return;
                    }
                }
            }else{//a:97, b:98, y:121, z:122, A:65, B:66, Y:89, Z:90
                char a = 'a';
                char b = 'b';
                char y = 'y';
                char z = 'z';
                char A = 'A';
                char B = 'B';
                char Y = 'Y';
                char Z = 'Z';
                int ai = a;
                int bi = b;
                int yi = y;
                int zi = z;
                int Ai = A;
                int Bi = B;
                int Yi = Y;
                int Zi = Z;
                System.out.println(a+":"+ai+", "+b+":"+bi+", "+y+":"+yi+", "+z+":"+zi+", "+A+":"+Ai+", "+B+":"+Bi+", "+Y+":"+Yi+", "+Z+":"+Zi);
                for(char i=65;i<123;i++){
                    System.out.println(i);
                }
            }
        }
    }
    public static long[] encrypt(char[] numArray, long pk, long n){
        long[] longArray = new long[numArray.length];
        for(int i=0;i<numArray.length;i++){
            int k=numArray[i];
            longArray[i]=numArray[i];
            for(int j=0;j<pk-1;j++){
                longArray[i]=(longArray[i]*k)%n;
            }
        }
        return longArray;
    }
    public static char[] decrypt(long[] longArray, long d, long n){
        char[] intArray = new char[longArray.length];
        long start = System.currentTimeMillis();
        List<Thread> tList = new ArrayList<>();
        int threadSum=50;
        int range=longArray.length/threadSum;
        int mod = (range!=0)?longArray.length%threadSum:1;
        if(range ==0){
            for(int i=0;i<longArray.length;i++){
                final int a=i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long k=longArray[a];
                        long l=longArray[a];
                        for(int j=0;j<d-1;j++){
                            l=((l*k)%n);
                        }
                        intArray[a]=(char)l;
                    }
                });
                tList.add(thread);
            }
        }else{
            for(int i=0;i<threadSum;i++){
                final int a=i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int m=a*range;m<a*range+range;m++){
                            long k=longArray[m];
                            long l=longArray[m];
                            for(int j=0;j<d-1;j++){
                                l=((l*k)%n);
                            }
                            intArray[m]=(char)l;
                        }
                    }
                });
                tList.add(thread);
            }
            for(int i=threadSum*range;i<threadSum*range+mod;i++){
                final int a=i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long k=longArray[a];
                        long l=longArray[a];
                        for(int j=0;j<d-1;j++){
                            l=((l*k)%n);
                        }
                        intArray[a]=(char)l;
                    }
                });
                tList.add(thread);
            }
        }
        try{
            for(int o=0;o<tList.size();o++){
                tList.get(o).start();
            }
            for(int o=0;o<tList.size();o++){
                tList.get(o).join();
            }
        }catch (InterruptedException e){
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start)+"ms");


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
    public static long gcdEx(long l, long pk, long n){
        for(long d=1;;d++){
            if((pk*d)%l==1){
                return d;
            }
        }
    }
    public static char[] getString(){
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


        char numArray[] = s1.toCharArray();


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
    public static void showString(char[] numArray){
        System.out.println("");
        String s="";
        for(int i=0;i<numArray.length;i++) {
            s+=String.valueOf(numArray[i]);
        }
        System.out.println(s);


    }
    public static void showNum(long[] numArray){
        System.out.println("num");
        for(int i=0;i<numArray.length;){
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
        long p=(long)rand.nextInt(5000000)+2;
        for(;p<l;p++){
            if(gcd(l,p)==1){
                return p;
            }
        }
        return 0;
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
        System.out.print("Generating...");
        int p;
        int q;
        long l;
        long pk;
        long sk;
        Random random = new Random();


        while(true){
            p=4;
            q=4;
            while(true){
                int randomWeight=10000;
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
            l =lcm(p,q);
            pk = getPublicKey(l);
            sk = gcdEx(l,pk,(p-1)*(q-1));
            if(verify(p*q,pk,sk))break;
        }
        System.out.println("\rGenerated       ");
        System.out.println("p:"+p+"\nq:"+q+"\nn:"+p*q);
        System.out.println("Public key:"+pk);
        System.out.println("Secret key:"+sk);
    }
    public static boolean verify(long n, long pk, long d){
        char numArray[] = {'t','e','s','t'};
        long[] longArray = encrypt(numArray,pk,n);
        char numArray2[] = decrypt(longArray,d,n);
        for(int i=0;i<numArray.length;i++){
            if(numArray[i]!=numArray2[i]){
                return false;//invalid
            }
        }
        return true;//valid
    }
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
    public static String bytesToHexes(byte[] num){
        String s="";
        for(int i=0;i<num.length;i++){
            s+=byteToHex(num[i]);
        }
        return s;
    }
}
