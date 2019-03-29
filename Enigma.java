import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Enigma {
	
	static final int ALP_SUM = 26;
	static final int KEY_WEIGHT = 3;
	public static final char[] list = {'a','b','c','d','e','f','g','h','i','j',
			'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static final char[] list2 = {'A','B','C','D','E','F','G','H','I','J',
			'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static int l1 = 0;
	private static int l2 = 0;
	private static int l3 = 0;
	private static int reciprocate = 0;
	private static Pair scrambler1;
	private static Pair scrambler2;
	private static Pair scrambler3;
	private static Pair reflector;
	
	public static void main(String[] args) {
		if(args.length != 1 || args[0].length() != KEY_WEIGHT) {
			System.out.println("Enter key(three characters) as an argument.");
			return;
		}
		String key = args[0];
		int[] keyNum = stringToNum(key);//key(String) -> key(int)
		l1=keyNum[0]+1;
		l2=keyNum[1]+1;
		l3=keyNum[2]+1;
		scrambler1= new Pair(l1);
		scrambler2= new Pair(l2);
		scrambler3= new Pair(l3);
		reflector= new Pair((l1+l2+l3)%ALP_SUM+1);
		boolean bool = true;//loop process
		BufferedReader br;
		String s1;
		while(bool) {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter string.");
			s1 = "";
			try {
				s1 = br.readLine();
			}catch(IOException e){
				System.out.println("IOExeption occurred.");
				return;
			}
			int[] s1Num = stringToNum(s1);//Plain text or cryptgram(String) -> Plain text or cryptgram(int)
			for(int l=0;l<s1.length();l++) {//Main process
				int n =converter(s1Num[l]);
				if(Integer.valueOf(n).equals(-1)) {
					System.out.print(" ");
					continue;
				}else {
					System.out.print(list[n]);
					continue;
				}
			}
			System.out.println("\nContinue? y/n");//Continue?
			br = new BufferedReader(new InputStreamReader(System.in));
			s1 = "";
			try {
				s1 = br.readLine();
			}catch(IOException e){
				System.out.println("IOExeption occurred.");
				return;
			}
			if(String.valueOf(s1).equals("y")) {
			}else {
				bool = false;
			}
		}
		
		return;
	}
	public static int[] stringToNum(String s) {
		int[] i = new int[s.length()];
		for(int j=0;j<s.length();j++) {
			i[j]=-1;//In case simbol or space entered.
			for(int k=0;k<ALP_SUM;k++) {
				if(s.charAt(j)==list[k]) {//lowerCase
					i[j]=k;
					break;
				}
				if(s.charAt(j)==list2[k]) {//upperCase
					i[j]=k;
					break;
				}
			}
		}
		return i;
	}

	public static int converter(int s1Num) {
		//Important
		int result = scrambler1.getPairNum(scrambler2.getPairNum(scrambler3.getPairNum(reflector.getPairNum(scrambler3.getPairNum(scrambler2.getPairNum(scrambler1.getPairNum(s1Num)))))));
		switch(reciprocate){
		case 0:
			if(l1<ALP_SUM) {
				l1++;
			}else {
				l1=1;
			}
			scrambler1=new Pair(l1);//Generate new scrambler
			reciprocate++;
			break;
		case 1:
			if(l2<ALP_SUM) {
				l2++;
			}else {
				l2=1;
			}
			scrambler2=new Pair(l2);
			reciprocate++;
			break;
		case 2:
			if(l3<ALP_SUM) {
				l3++;
			}else {
				l3=1;
			}
			scrambler3=new Pair(l3);
			reciprocate=0;
			break;
		}
		return result;
	}
}
class Pair{
	static final int ALP_SUM = 26;
	static final int ALP_HALF = 13;
	private int cList1[]= new int[13];//For example, cList1[0] <-> cList2[0]
	private int cList2[]= new int[13];
	Pair(int i){
		if(i>ALP_HALF) {
			i-=ALP_HALF;
		}
		int block = ALP_HALF/i;
		int rest = ALP_HALF%i;
		int cList1Index = 0;
		int cList2Index = 0;
		for(int j=0;j<ALP_SUM;j++) {
			if(j<block*i*2) {
				if(j==0 || evenOdd(j/i)) {
					cList1[cList1Index]=j;
					cList1Index++;
				}else {
					cList2[cList2Index]=j;
					cList2Index++;
				}
			}else {
				for(int k=0;k<rest;k++) {
					cList1[cList1Index]=j;
					cList1Index++;
					j++;
				}
				for(int k=0;k<rest;k++) {
					cList2[cList2Index]=j;
					cList2Index++;
					j++;
				}
			}
		}
	}
	int getPairNum(int i) {
		for(int j=0;j<ALP_HALF;j++) {
			if(Integer.valueOf(cList1[j]).equals(i)) {
				return cList2[j];
			}
			if(Integer.valueOf(cList2[j]).equals(i)) {
				return cList1[j];
			}
		}
		return -1;
	}
	boolean evenOdd(int i) {
		boolean bool;
		if(i==0 || i%2==0) {
			bool = true;
		}else {
			bool = false;
		}
		return bool;
	}
}
